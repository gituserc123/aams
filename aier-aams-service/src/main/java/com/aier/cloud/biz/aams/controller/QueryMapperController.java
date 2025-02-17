package com.aier.cloud.biz.aams.controller;

import com.aier.cloud.aams.api.domain.constant.Constants;
import com.aier.cloud.basic.common.exception.BizException;
import com.aier.cloud.basic.api.response.domain.base.PageResponse;
import com.aier.cloud.biz.common.config.JdbcHelper;
import com.aier.cloud.basic.starter.service.controller.BaseController;
import com.aier.cloud.biz.aams.dao.QueryMapper;
import com.aier.cloud.biz.aams.utils.EntityUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Controller
@RequestMapping("/api/service/biz/aams/query")
public class QueryMapperController extends BaseController {

    @Resource(name = "aierViewJdbcHelper")
    private JdbcHelper db;

    @Resource
    private QueryMapper queryMapper;

    @Autowired
    private ApplicationContext applicationContext; // 用于从 Spring 容器中获取 Bean

    @RequestMapping(value = "/queryCount")
    @ResponseBody
    public Integer queryCount(@RequestParam("queryFeild") String queryFeild, @RequestParam("table") String table, @RequestParam("cond") String cond) {
        Integer count = queryMapper.queryCountSql(queryFeild,table,cond);
        return count;
    }

    @RequestMapping(value = "/queryList")
    @ResponseBody
    public List<Map<String, Object>> queryList(@RequestParam("queryFeild") String queryFeild, @RequestParam("table") String table, @RequestParam("cond") String cond){
        return queryMapper.queryListSql(queryFeild,table,cond);
    }

    /**
     * 通用查询接口;适用于单表及多表查询
     *
     * @param sql    SQL查询语句
     * @param params 可变参数列表，用于填充SQL中的占位符
     * @return 查询结果，返回一个包含Map的列表
     */
    @RequestMapping(value = "/commonQueryList", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> commonQueryList(@RequestParam("sql") String sql, @RequestParam("params") Object... params) {
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            // 参数验证
            validateSqlAndParams(sql, params);

            logger.info("Executing SQL: {}", sql);
            logger.info("With Parameters: {}", Arrays.toString(params));

            // 执行查询
            result = db.queryMapList(sql, params);
        } catch (IllegalArgumentException e) {
            logger.error("参数验证失败: {}", e.getMessage());
            throw new RuntimeException("参数错误: " + e.getMessage());
        } catch (Exception e) {
            logger.error("SQL执行失败: {}", e.getMessage());
            throw new RuntimeException("查询失败，请检查SQL语句和参数");
        }

        return result;
    }

    /**
     * 通用查询接口，返回泛型列表;适用于单表查询
     *
     * @param sql       SQL查询语句
     * @param entityName  返回对象类型
     * @param params    可变参数列表，用于填充SQL中的占位符
     * @param <T>       泛型类型
     * @return 查询结果，返回一个包含泛型对象的列表
     */
    @RequestMapping(value = "/commonQueryListBean", method = RequestMethod.POST)
    @ResponseBody
    public <T> List<T> commonQueryListBean(@RequestParam("sql") String sql,
                                         @RequestParam("entityName") String entityName,
                                         @RequestParam("params") Object... params) {
        List<T> result = new ArrayList<>();
        try {
            // 参数验证
            validateSqlAndParams(sql, params);

            logger.info("Executing SQL: {}", sql);
            logger.info("With Parameters: {}", Arrays.toString(params));

            // 将全类名字符串转换为 Class<T>
            Class<T> beanType;
            try {
                @SuppressWarnings("unchecked")
                Class<T> castedBeanType = (Class<T>) Class.forName(Constants.ENTITY_PREFIX.concat(entityName));
                beanType = castedBeanType;
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("指定的类未找到: " + entityName);
            }
            // 执行查询
            result = db.queryBeanList(sql, beanType, params);
        } catch (IllegalArgumentException e) {
            logger.error("参数验证失败: {}", e.getMessage());
            throw new RuntimeException("参数错误: " + e.getMessage());
        } catch (Exception e) {
            logger.error("SQL执行失败: {}", e.getMessage());
            throw new RuntimeException("查询失败，请检查SQL语句和参数");
        }

        return result;
    }

    /**
     * 通用查询接口，返回泛型列表;适用于单表查询
     *
     * @param sql       SQL查询语句
     * @param entityName  返回对象类型
     * @param paramMap    可变参数列表，用于填充SQL中的占位符
     * @param <T>       泛型类型
     * @return 查询结果，返回一个包含泛型对象的列表
     */
    @RequestMapping(value = "/commonQueryListBeanByMap", method = RequestMethod.POST)
    @ResponseBody
    public <T> List<T> commonQueryListBeanByMap(@RequestParam("sql") String sql,
                                           @RequestParam("entityName") String entityName,
                                           @RequestBody Map<String, ?> paramMap) {
        List<T> result = new ArrayList<>();
        try {
            // 参数验证
            validateSqlAndParams(sql, paramMap);

            logger.info("Executing SQL: {}", sql);
            logger.info("With Parameters: {}", paramMap);

            // 将全类名字符串转换为 Class<T>
            Class<T> beanType;
            try {
                @SuppressWarnings("unchecked")
                Class<T> castedBeanType = (Class<T>) Class.forName(Constants.ENTITY_PREFIX.concat(entityName));
                beanType = castedBeanType;
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("指定的类未找到: " + entityName);
            }
            // 执行查询
            result = db.queryBeanList(sql, beanType, paramMap);
        } catch (IllegalArgumentException e) {
            logger.error("参数验证失败: {}", e.getMessage());
            throw new RuntimeException("参数错误: " + e.getMessage());
        } catch (Exception e) {
            logger.error("SQL执行失败: {}", e.getMessage());
            throw new RuntimeException("查询失败，请检查SQL语句和参数");
        }

        return result;
    }

    @RequestMapping(value = "/deleteById")
    @ResponseBody
    public Boolean deleteById(@RequestParam("entityName") String entityName, @RequestParam("id") Long id) {
        try {
            /*// 动态加载 Mapper 类
            Class<?> mapperClass = Class.forName(entityName);
            // 从 Spring 容器中获取对应的 Mapper Bean
            Object mapper = applicationContext.getBean(mapperClass);
            // 检查是否是 BaseMapper 的子类
            if (!(mapper instanceof BaseMapper)) {
                throw new IllegalArgumentException("目标类未继承 BaseMapper: " + entityName);
            }
            // 强制转换为 BaseMapper
            @SuppressWarnings("unchecked")
            BaseMapper<?> baseMapper = (BaseMapper<?>) mapper;
            // 调用 BaseMapper 的 deleteById 方法
            int result = baseMapper.deleteById(id);*/
            Class<?> eService = Class.forName(Constants.SERVICE_PREFIX.concat(entityName).concat(Constants.SERVICE_SUFFIX));
            Object service = applicationContext.getBean(eService);
            // 检查是否是 ServiceImpl 的子类
            if (!(service instanceof ServiceImpl)) {
                throw new IllegalArgumentException("目标类未继承 ServiceImpl: " + entityName);
            }
            // 强制转换为 ServiceImpl
            @SuppressWarnings("unchecked")
            ServiceImpl<?,?> baseService = (ServiceImpl<?,?>) service;

            Boolean bResult = baseService.deleteById(id);

            return bResult;
        } catch (ClassNotFoundException e) {
            throw new BizException("未找到指定的 Mapper 类: " + entityName, e);
        } catch (Exception e) {
            throw new BizException("删除失败: " + e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/deleteByMap")
    @ResponseBody
    public Boolean deleteByMap(@RequestParam("entityName") String entityName,@RequestBody Map<String, Object> conditions) {
        try {
            /*// 动态加载 Mapper 类
            Class<?> mapperClass = Class.forName(Constants.MAPPER_PREFIX.concat(entityName).concat(Constants.MAPPER_SUFFIX));
            // 从 Spring 容器中获取对应的 Mapper Bean
            Object mapper = applicationContext.getBean(mapperClass);
            // 检查是否是 BaseMapper 的子类
            if (!(mapper instanceof BaseMapper)) {
                throw new IllegalArgumentException("目标类未继承 BaseMapper: " + entityName);
            }
            // 强制转换为 BaseMapper
            @SuppressWarnings("unchecked")
            BaseMapper<?> baseMapper = (BaseMapper<?>) mapper;
            // 调用 BaseMapper 的 deleteByMap 方法
            int result = baseMapper.deleteByMap(conditions);*/
            Class<?> eService = Class.forName(Constants.SERVICE_PREFIX.concat(entityName).concat(Constants.SERVICE_SUFFIX));
            Object service = applicationContext.getBean(eService);
            // 检查是否是 ServiceImpl 的子类
            if (!(service instanceof ServiceImpl)) {
                throw new IllegalArgumentException("目标类未继承 ServiceImpl: " + entityName);
            }
            // 强制转换为 ServiceImpl
            @SuppressWarnings("unchecked")
            ServiceImpl<?,?> baseService = (ServiceImpl<?,?>) service;

            // 检查 Map 是否符合删除条件
            if (conditions == null || conditions.isEmpty()) {
                throw new IllegalArgumentException("删除条件不能为空");
            }

            Boolean bResult = baseService.deleteByMap(conditions);

            return bResult;
        } catch (ClassNotFoundException e) {
            throw new BizException("未找到指定的类: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new BizException("删除失败: " + e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/insertOrUpdate")
    public @ResponseBody Map<String, Object> insertOrUpdate(@RequestBody Map<String, Object> mData,@RequestParam("entityName") String entityName) throws Exception {
        Map<String,Object> result = Maps.newHashMap();
        Object retVal = null;
        try{
            Class<?> eClass = Class.forName(Constants.ENTITY_PREFIX.concat(entityName));
            Object eObject = eClass.newInstance();
            eObject =  EntityUtils.transMapToObject(mData, eClass);
            /*ApplicationContext applicationContext = SpringBootBeanUtil.getApplicationContext();
            Class<?> curMapper = Class.forName(Constants.MAPPER_PREFIX.concat(entityName).concat(Constants.MAPPER_SUFFIX));
            Class<?> curSuperMapper = curMapper.getInterfaces()[0];
            Method method = curSuperMapper.getDeclaredMethod("insert", Object.class);
            method.invoke(applicationContext.getBean(curMapper), eObject);*/
            Class<?> eService = Class.forName(Constants.SERVICE_PREFIX.concat(entityName).concat(Constants.SERVICE_SUFFIX));
            Class<?>  eSuperService = eService.getSuperclass();
            Method method = eSuperService.getDeclaredMethod("insertOrUpdate", Object.class);
            retVal = method.invoke(applicationContext.getBean(eService), eObject);
        }catch (Exception e){
            logger.error("保存"+entityName+"报错:",e.getMessage());
            throw new BizException("保存"+entityName+"报错:",e.getMessage());
        }

        result.put("code",200);
        result.put("data",retVal);
        result.put("msg","成功");
        return result;
    }

    @RequestMapping(value = "/queryPageParamMap",method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public PageResponse<Map<String, Object>> queryPageParamMap(int page, int size, String sql,@RequestBody Map<String, ?> paramMap){
        Page<Map<String, Object>> pageResult = db.queryPage(page, size, sql, paramMap);
        return this.returnPageResponse(pageResult);
    }
    @RequestMapping(value = "/queryPageParamObject",method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public PageResponse<Map<String, Object>> queryPageParamObject(int page, int size, String sql,  @RequestParam("args") Object... args){
        Page<Map<String, Object>> pageResult = db.queryPage(page, size, sql, args);
        return this.returnPageResponse(pageResult);
    }

    @RequestMapping(value = "/queryPageParamMapT",method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public <T> PageResponse<T> queryPageParamMapT(String entityName, int page, int size, String sql, @RequestBody Map<String, ?> paramMap) throws Exception {
        Page<T> pageResult = db.queryPage(page, size,entityName, sql, paramMap);
        return this.returnPageResponse(pageResult);
    }
    @RequestMapping(value = "/queryPageParamObjectT",method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public <T> PageResponse<T> queryPageParamObjectT(String entityName, int page, int size, String sql, @RequestParam("args") Object... args) throws Exception {
        Page<T> pageResult = db.queryPage(page, size, entityName,sql, args);
        return this.returnPageResponse(pageResult);
    }

        /**
         * 验证SQL和参数的合法性
         *
         * @param sql    SQL语句
         * @param params 可变参数
         */
    private void validateSqlAndParams(String sql, Object[] params) {
        if (sql == null || sql.trim().isEmpty()) {
            throw new IllegalArgumentException("SQL语句不能为空");
        }
        if (!sql.toLowerCase().contains("?") && params.length > 0) {
            throw new IllegalArgumentException("SQL中不存在占位符，但传入了参数");
        }
        if (sql.chars().filter(ch -> ch == '?').count() != params.length) {
            throw new IllegalArgumentException("占位符数量与参数个数不匹配");
        }
    }

    private void validateSqlAndParams(String sql, Map<String, ?> paramMap) {
        if (sql == null || sql.trim().isEmpty()) {
            throw new IllegalArgumentException("SQL语句不能为空");
        }
        for (String key : paramMap.keySet()) {
            if (!sql.contains(":" + key)) {
                throw new IllegalArgumentException("SQL中不存在参数占位符: " + key);
            }
        }
    }

    public <T> EntityWrapper<T> createEntityWrapper(String className) {
        try {
            // 使用反射加载类
            Class<?> clazz = Class.forName(className);

            // 确保类有无参构造方法
            Object instance = clazz.getDeclaredConstructor().newInstance();

            // 将实例强制转换为泛型T
            @SuppressWarnings("unchecked")
            T entity = (T) instance;

            // 使用EntityWrapper构造方法
            return new EntityWrapper<>(entity);
        } catch (Exception e) {
            // 处理异常，例如类未找到或实例化失败
            throw new RuntimeException("无法创建 EntityWrapper: " + e.getMessage(), e);
        }
    }

}
