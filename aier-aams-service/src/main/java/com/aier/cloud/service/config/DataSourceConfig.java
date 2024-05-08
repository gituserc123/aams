package com.aier.cloud.service.config;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.generator.config.rules.DbType;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.MapWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.aier.cloud.basic.common.util.ToolUtils;
import com.aier.cloud.basic.core.schema.AierTenantSqlParser;
import com.aier.cloud.basic.starter.service.config.properties.DatasourceProperties;
import com.aier.cloud.center.common.context.UserContext;
import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.parser.ISqlParser;
import com.baomidou.mybatisplus.plugins.parser.ISqlParserFilter;
import com.baomidou.mybatisplus.plugins.parser.tenant.TenantHandler;
import com.baomidou.mybatisplus.spring.boot.starter.ConfigurationCustomizer;
import com.google.common.collect.Sets;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;

/**
 *
 *
 * <b>类名称：</b>DataSourceConfig<br/>
 * <b>类描述：</b>数据库配置类，设置单数据源，多数据源，初始化连接池，多schema<br/>
 * <b>创建人：</b>rain_deng<br/>
 * <b>修改人：</b>rain_deng<br/>
 * <b>修改时间：</b>2017年11月15日 下午2:10:09<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 * @author rain_deng
 */
@Configuration
@EnableTransactionManagement(order = 2)
@MapperScan(basePackages = {"com.aier.cloud.**.dao"})
public class DataSourceConfig {

	private final static Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

	/**
	 * 默认数据源
	 */
	public final static String DATA_SOURCE_BASE = "dataSourceBase";

	/**
	 * 其他业务的数据源
	 */
	public final static String DATA_SOURCE_BIZ = "dataSourceBiz";

	/** 需要走租户的表 */
	private static final Set<String> TENANT_TABLES = Sets.newHashSet();

	static {
		TENANT_TABLES.add("t_amcs_aps_rule".toUpperCase());
		TENANT_TABLES.add("t_amcs_aps_rule_detail".toUpperCase());
		TENANT_TABLES.add("t_amcs_aps_rule_detail_range".toUpperCase());
		//lrz
		TENANT_TABLES.add("T_AMCS_IDR_AFP".toUpperCase());
		TENANT_TABLES.add("T_AMCS_IDR_AIDS".toUpperCase());
		TENANT_TABLES.add("T_AMCS_IDR_BASE_INFO".toUpperCase());
		TENANT_TABLES.add("T_AMCS_IDR_DICT_TYPE".toUpperCase());
		TENANT_TABLES.add("T_AMCS_IDR_HB".toUpperCase());
		TENANT_TABLES.add("T_AMCS_IDR_HFMD".toUpperCase());

	}

	@Autowired
	DatasourceProperties datasourceProperties;

	/**
	 * 基础平台的数据源
	 */
	private DruidDataSource dataSourceBase(){
		DruidDataSource dataSource = new DruidDataSource();
		datasourceProperties.config(dataSource);
		return dataSource;
	}

	/**
	 * 单数据源连接池配置
	 */
	@Bean
	@ConditionalOnProperty(prefix = "aier", name = "muti-datasource-open", havingValue = "false")
	public DruidDataSource singleDatasource() {
		DruidDataSource druidDataSource = dataSourceBase();
		try {
			druidDataSource.init();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return druidDataSource;
	}


	/**
	 * mybatis-plus分页插件
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		paginationInterceptor.setLocalPage(true);
		paginationInterceptor.setDialectType(DbType.MYSQL.getValue());

		List<ISqlParser> sqlParserList = new ArrayList<>();
		AierTenantSqlParser tenantSqlParser = new AierTenantSqlParser();
		tenantSqlParser.setTenantHandler(new TenantHandler() {
			@Override
			public Expression getTenantId() {
				return new LongValue(UserContext.getTenantId());
			}

			@Override
			public String getTenantIdColumn() {
				return "HOSP_ID";
			}

			@Override
			public boolean doTableFilter(String tableName) {
				if(StringUtils.isBlank(tableName)){
					return true;
				}
				String upCaseTableName = tableName.toUpperCase();
				if(!TENANT_TABLES.contains(upCaseTableName)){
					return true;
				}

				return false;
			}
		});
		sqlParserList.add(tenantSqlParser);
		paginationInterceptor.setSqlParserList(sqlParserList);
		paginationInterceptor.setSqlParserFilter(new ISqlParserFilter() {
			@Override
			public boolean doFilter(MetaObject metaObject) {
				// 过滤自定义查询此时无租户信息约束出现
                /*MappedStatement ms = PluginUtils.getMappedStatement(metaObject);
                if ("com.baomidou.springboot.mapper.UserMapper.selectListBySQL".equals(ms.getId())) {
                    return true;
                }*/
				return false;
			}
		});
		return paginationInterceptor;
	}

	/**
	 * mybatis返回的map结果集的key下划线转驼峰
	 * @return
	 */
	@Bean
	public ConfigurationCustomizer mybatisConfigurationCustomizer(){
		return new ConfigurationCustomizer() {
			@Override
			public void customize(org.apache.ibatis.session.Configuration configuration) {
				configuration.setObjectWrapperFactory(new ObjectWrapperFactory() {
					@Override
					public boolean hasWrapperFor(Object object) {
						return object != null && object instanceof Map;
					}

					@Override
					public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
						return new MapWrapper(metaObject, (Map)object) {
							@Override
							public String findProperty(String name, boolean useCamelCaseMapping) {
								if(useCamelCaseMapping){
									return ToolUtils.camel(name);
								}else {
									return name;
								}
							}
						};
					}
				});
			}
		};
	}

}
