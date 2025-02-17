package com.aier.cloud.biz.common.config;

import com.aier.cloud.aams.api.domain.constant.Constants;
import com.aier.cloud.biz.aams.entity.AuditRecordReply;
import com.aier.cloud.biz.common.util.SysUtil;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * jdbc HACKER
 *
 * @author xiaokek
 * @since 2018年8月29日 上午9:20:34
 */
public class JdbcHelper {

    private static Map<Class, String> insertSqlCache = Maps.newConcurrentMap();
    private static Map<Class, List<Method>> gettersCache = Maps.newConcurrentMap();
    private static Map<Class, Method> idCache = Maps.newConcurrentMap();

    public JdbcHelper(NamedParameterJdbcTemplate n) {
        this.n = n;
    }

    private NamedParameterJdbcTemplate n;

    public NamedParameterJdbcTemplate n() {
        return n;
    }

    public JdbcTemplate j() {
        return (JdbcTemplate) n.getJdbcOperations();
    }

    public <T> T get(Integer id, Class<T> clazz) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("select * from ")
                .append(tableName(clazz))
                .append(" where id = ?");
        return this.queryBean(selectSql.toString(), clazz, id);
    }

    public <T> void insert(T... entitys) {
        if (SysUtil.isEmpty(entitys)) {
            return;
        }

        //此处为了生成insert语句，同时用本地Cache缓存对应entity的insertSql
        Class clazz = entitys[0].getClass();
        int size = entitys.length;
        String sql;
        List<Method> methods;
        Method idMethod = null;
        if (insertSqlCache.containsKey(clazz)) {
            sql = insertSqlCache.get(clazz);
            methods = gettersCache.get(clazz);
            idMethod = idCache.get(clazz);
        } else {
            String tableName = tableName(clazz);
            PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(entitys[0]);
            methods = new LinkedList<>();
            StringBuilder insertSql = new StringBuilder();
            insertSql.append("insert into ").append(tableName).append(" (");
            StringBuilder questionMark = new StringBuilder();
            for (int i = 0; i < pds.length; i++) {
                if ("class".equalsIgnoreCase(pds[i].getName())) {
                    continue;
                }
                if ("id".equalsIgnoreCase(pds[i].getName())) {
                    idMethod = pds[i].getWriteMethod();
                    idCache.put(clazz, idMethod);
                    continue;
                }
                insertSql.append(underscoreName(pds[i].getName())).append(",");
                methods.add(pds[i].getReadMethod());
                questionMark.append("?,");
            }

            insertSql.deleteCharAt(insertSql.length() - 1);
            insertSql.append(") values (");
            questionMark.deleteCharAt(questionMark.length() - 1);
            insertSql.append(questionMark).append(")");
            sql = insertSql.toString();
            gettersCache.putIfAbsent(clazz, methods);
            insertSqlCache.putIfAbsent(clazz, sql);
        }
        //↑↑↑↑为了生成insert语句，同时用本地Cache缓存对应entity的insertSql


        final List<Object[]> args = new LinkedList<>();
        for (T en : entitys) {
            Object[] arg = new Object[methods.size()];
            for (int i = 0; i < methods.size(); i++) {
                arg[i] = ReflectionUtils.invokeMethod(methods.get(i), en);
            }
            args.add(arg);
        }

        //使用ConnectionCallback是为了能返回主键
        Integer[] keys = j().execute(new ConnectionCallback<Integer[]>() {
            @Override
            public Integer[] doInConnection(Connection conn) throws SQLException, DataAccessException {
                String[] columnNames = {"id"};
                PreparedStatement ps = null;
                ResultSet rs = null;
                Integer[] keys = new Integer[size];
                try {
                    ps = conn.prepareStatement(sql, columnNames);
                    for (Object[] arg : args) {
                        for (int i = 0; i < arg.length; i++) {
                            StatementCreatorUtils.setParameterValue(ps, i + 1, SqlTypeValue.TYPE_UNKNOWN, arg[i]);
                        }
                        ps.addBatch();
                    }
                    ps.executeBatch();

                    rs = ps.getGeneratedKeys();
                    int i = 0;
                    while (rs.next() && i < args.size()) {
                        keys[i] = rs.getInt(1);
                        i++;
                    }
                    return keys;
                } finally {
                    JdbcUtils.closeResultSet(rs);
                    JdbcUtils.closeStatement(ps);
                }
            }
        });

        //用返回的主键填充传进来的实体id字段
        for (int i = 0; i < size; i++) {
            ReflectionUtils.invokeMethod(idMethod, entitys[i], keys[i]);
        }
    }

    public int insert(String tableName, Map<String, Object> obj) {
        if (obj == null || obj.size() <= 0) {
            return 0;
        }

        StringBuilder insertSql = new StringBuilder();
        insertSql.append("insert into ").append(tableName).append(" (");
        StringBuilder questionMark = new StringBuilder();

        List<String> columns = new ArrayList<>();
        Map map = obj;
        columns.addAll(map.keySet());
        Object[] arg = new Object[map.size()];
        for (int i = 0; i < columns.size(); i++) {
            String column = columns.get(i);
            arg[i] = MapUtils.getObject(map, column, null);

            insertSql.append(column).append(",");
            questionMark.append("?,");
        }
        insertSql.deleteCharAt(insertSql.length() - 1);
        insertSql.append(") values (");
        questionMark.deleteCharAt(questionMark.length() - 1);
        insertSql.append(questionMark).append(")");
        String sql = insertSql.toString();
        return j().update(sql, arg);
    }

    public int batchInsert(String tableName, List<Map<String, Object>> objList) {
        if (SysUtil.isEmpty(objList)) {
            return 0;
        }

        final List<Object[]> args = new LinkedList<>();
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("insert into ").append(tableName).append(" (");
        StringBuilder questionMark = new StringBuilder();

        List<String> columns = new ArrayList<>();
        for (int idx = 0; idx < objList.size(); idx++) {
            Map map = objList.get(idx);
            if (idx == 0) {
                columns.addAll(map.keySet());
            }
            Object[] arg = new Object[map.size()];
            for (int i = 0; i < columns.size(); i++) {
                String column = columns.get(i);
                arg[i] = MapUtils.getObject(map, column, null);

                if (idx == 0) {
                    insertSql.append(column).append(",");
                    questionMark.append("?,");
                }
            }
            args.add(arg);
        }
        insertSql.deleteCharAt(insertSql.length() - 1);
        insertSql.append(") values (");
        questionMark.deleteCharAt(questionMark.length() - 1);
        insertSql.append(questionMark).append(")");
        String sql = insertSql.toString();
        j().batchUpdate(sql, args);
        return args.size();
    }

    private String tableName(Class<?> clazz) {
        TableName table = clazz.getAnnotation(TableName.class);
        if (table == null || StringUtils.isBlank(table.value())) {
            throw new IllegalArgumentException("实体必须用户@Table维护表名:" + clazz.getName());
        }
        return table.value();
    }

    /**
     * 驼峰名称 转 数据库下划线名称
     **/
    private String underscoreName(String name) {
        if (StringUtils.isBlank(name)) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        result.append(name.substring(0, 1).toLowerCase());
        for (int i = 1; i < name.length(); i++) {
            String s = name.substring(i, i + 1);
            String slc = s.toLowerCase();
            if (!s.equals(slc)) {
                result.append("_").append(slc);
            } else {
                result.append(s);
            }
        }
        return result.toString();
    }


    public Map<String, Object> queryMap(String sql, Object... args) {
        List<Map<String, Object>> list = this.queryMapList(sql, args);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        } else {
            return Collections.EMPTY_MAP;
        }
    }
    

    public Map<String, Object> queryMap(String sql, Map<String, ?> args) {
        List<Map<String, Object>> list = this.queryMapList(sql, args);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        } else {
            return Collections.EMPTY_MAP;
        }
    }

    public <T> T queryBean(String sql, Class<T> beanType, Object... args) {
        List<T> list = this.queryBeanList(sql, beanType, args);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public <T> T queryBean(String sql, Class<T> beanType, Map<String, ?> args) {
        List<T> list = this.queryBeanList(sql, beanType, args);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public Integer queryInt(String sql, Object... args) {
        return this.queryObject(sql, Integer.class, args);
    }

    public Integer queryInt(String sql, Map<String, ?> paramMap) {
        return this.queryObject(sql, Integer.class, paramMap);
    }

    public Long queryLong(String sql, Object... args) {
        return this.queryObject(sql, Long.class, args);
    }

    public Long queryLong(String sql, Map<String, ?> paramMap) {
        return this.queryObject(sql, Long.class, paramMap);
    }

    public String queryString(String sql, Object... args) {
        return this.queryObject(sql, String.class, args);
    }
    public String queryString(String sql, Map<String, ?> paramMap) {
        return this.queryObject(sql, String.class, paramMap);
    }

    public <T> T queryObject(String sql, Class<T> requiredType, Object... args) {
        List<T> result = j().queryForList(sql, requiredType, args);
        if (SysUtil.isEmpty(result)) {
            return null;
        } else {
            return result.get(0);
        }
    }

    public <T> T queryObject(String sql, Class<T> requiredType, Map<String, ?> paramMap) {
        List<T> result = n().queryForList(sql, paramMap, requiredType);
        if (SysUtil.isEmpty(result)) {
            return null;
        } else {
            return result.get(0);
        }
    }

    public <T> List<T> queryBeanList(String sql, Class<T> beanType, Object... args) {
        return j().query(sql, BeanPropertyRowMapper.newInstance(beanType), args);
    }

    public <T> List<T> queryBeanList(String sql, Class<T> beanType, Map<String, ?> paramMap) {
        return n().query(sql, paramMap, BeanPropertyRowMapper.newInstance(beanType));
    }

    public List<Map<String, Object>> queryMapList(String sql, Object... args) {
        return j().queryForList(sql, args);
    }
    
    public Page<Map<String, Object>> queryPage(int page, int size, String sql, Object... args) {
    	int total = this.queryInt("select count(1) from ("+sql+") tmp", args);
    	int first = (size*(page-1)) + 1;
        String finalSql = "select * from ("+sql+") ta_ limit " + (first - 1) + "," + size;
    	List<Map<String, Object>> record =  this.queryMapList(finalSql, args);
    	Page<Map<String, Object>> result = new Page<>(page, size);
    	result.setRecords(record);
    	result.setTotal(total);
    	return result;
    }

    public <T> Page<T> queryPage(int page, int size,String entityName, String sql, Object... args)  throws Exception{
        int total = this.queryInt("select count(1) from ("+sql+") tmp", args);
        int first = (size*(page-1)) + 1;
        String finalSql = "select * from ("+sql+") ta_ limit " + (first - 1) + "," + size;
        List<T> record =  this.queryBeanList(finalSql, (Class<T>) Class.forName(Constants.ENTITY_PREFIX.concat(entityName)), args);
        Page<T> result = new Page<>(page, size);
        result.setRecords(record);
        result.setTotal(total);
        return result;
    }
    
    public Page<Map<String, Object>> queryPage(int page, int size, String sql, Map<String, ?> paramMap) {
    	int total = this.queryInt("select count(1) from ("+sql+") tmp", paramMap);
    	int first = (size*(page-1)) + 1;
        String finalSql = "select * from ("+sql+") ta_ limit " + (first - 1) + "," + size;
        this.queryBeanList(finalSql, AuditRecordReply.class, paramMap);
    	List<Map<String, Object>> record =  this.queryMapList(finalSql, paramMap);
    	Page<Map<String, Object>> result = new Page<>(page, size);
    	result.setRecords(record);
    	result.setTotal(total);
    	return result;
    }

    public <T> Page<T> queryPage(int page, int size, String entityName,String sql, Map<String, ?> paramMap) throws Exception {
        int total = this.queryInt("select count(1) from ("+sql+") tmp", paramMap);
        int first = (size*(page-1)) + 1;
        String finalSql = "select * from ("+sql+") ta_ limit " + (first - 1) + "," + size;
        List<T> record =  this.queryBeanList(finalSql, (Class<T>) Class.forName(Constants.ENTITY_PREFIX.concat(entityName)), paramMap);
        Page<T> result = new Page<>(page, size);
        result.setRecords(record);
        result.setTotal(total);
        return result;
    }

    public List<Map<String, Object>> queryMapList(String sql, Map<String, ?> paramMap) {
        return n().queryForList(sql, paramMap);
    }

    public int update(String sql, Object... args) {
        return j().update(sql, args);
    }

    public int update(String sql, Map<String, ?> paramMap) {
        return n().update(sql, paramMap);
    }

    public int update(String tableName, Map<String, Object> paramMap, String... whereKeys) {
        if (StringUtils.isBlank(tableName)) {
            throw new IllegalArgumentException("update tableName should not be empty");
        }

        if (ArrayUtils.isEmpty(whereKeys)) {
            throw new IllegalArgumentException("update keys should not be empty");
        }

        if (MapUtils.isEmpty(paramMap)) {
            throw new IllegalArgumentException("update args should not be empty");
        }

        List<String> keys = Arrays.asList(whereKeys);
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("update ").append(tableName).append(" set ");
        paramMap.entrySet().stream().forEach(v -> {
            if (!keys.contains(v.getKey())){
                sqlBuilder.append(v.getKey()).append("=:").append(v.getKey()).append(",");
            }
        });
        /** 添加where条件*/
        sqlBuilder.deleteCharAt(sqlBuilder.length() - 1).append(" where 1=1");
        keys.forEach(v -> {
            if (StringUtils.isBlank(v) || !paramMap.containsKey(v)) {
                throw new IllegalArgumentException("update key[" + v + "] is empty or not exists");
            }
            sqlBuilder.append(" and ").append(v).append("=:").append(v);
        });

        String sqlStr = sqlBuilder.toString();
        return update(sqlStr, paramMap);
    }

    public int[] batchUpdate(String sql, List<?> batchArgs) {
        if (CollectionUtils.isEmpty(batchArgs)) {
            throw new IllegalArgumentException("batch update args should not be empty");
        }

        Object value = batchArgs.get(0);
        if (ClassUtils.isAssignableValue(Object[].class, value)) {
            return j().batchUpdate(sql, (List<Object[]>) batchArgs);
        } else if (ClassUtils.isAssignableValue(Map.class, value)) {
            return n().batchUpdate(sql, batchArgs.toArray(new Map[0]));
        }

        throw new IllegalArgumentException("batch update args is wrong" + batchArgs.toString());
    }


    public int[] batchUpdate(String... sql) {
        return j().batchUpdate(sql);
    }
}
