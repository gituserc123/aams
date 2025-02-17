package com.aier.cloud.service.config;

/**
 * JDBCHelper查询返回的Map中, 其字段命名的转换策略
 *
 * @author xiaokek
 * @since 2020年3月20日 上午10:47:41
 */
public enum JdbcColumnStrategy {
	
	//下划线转驼峰
	CAMEL,
	
	//数据库字段原始值, 如不用双引号, ORACLE返回的全是大写
	NORMAL,
}
