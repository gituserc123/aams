package com.aier.cloud.service.config;

import com.aier.cloud.basic.common.exception.BizException;

import java.util.HashMap;
import java.util.Map;

/**
 * job参数上下文,框架专用
 *
 * @author xiaokek
 * @since 2019年1月9日 下午4:55:59
 */
public class JobContext {

	public static final String LOG = "__job_log__";
	public static final String JOB = "__job__";
	public static final String SIZE = "__job_success_size__";
	public static final String JDBC_COLUMN = "__jdbc_column__";
	
	/**
	 * 是否处于自动作业中
	 */
	public static final String AUTO = "__job_auto__";
	private static final ThreadLocal<Map<Object, Object>> mycontext = new ThreadLocal<Map<Object, Object>>() {
		@Override
		protected Map<Object, Object> initialValue() {
			Map m = new HashMap<Object, Object>();
			m.put(LOG, new StringBuilder());
			m.put(JDBC_COLUMN, JdbcColumnStrategy.CAMEL);
			return m;
		}

	};
	

	public static StringBuilder getLog() {
		Object value = getValue(LOG);
		if(value == null) {
			throw new BizException("系统在不合适的位置获取参数,请管理员检查!");
		}
		StringBuilder log = (StringBuilder)value;
		return log;
	}
	
	/**
	 * 根据key获取值
	 * 
	 * @param key
	 * @return
	 */
	public static Object getValue(Object key) {
		if(mycontext.get() == null){
			return null;
		}
		return mycontext.get().get(key);
	}
	
	public static boolean isAuto() {
		Object value = getValue(AUTO);
		if(value == null || ((Boolean)value) == false) {
			return false;
		}
		return true;
	}

	/**
	 * 存储
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static Object setValue(Object key, Object value) {
		Map<Object, Object> cacheMap = mycontext.get();
		if(cacheMap == null){
			cacheMap = new HashMap<Object, Object>();
			mycontext.set(cacheMap);
		}
		return cacheMap.put(key, value);
	}

	/**
	 * 根据key移除值
	 * 
	 * @param key
	 */
	public static void removeValue(Object key) {
		Map<Object, Object> cacheMap = mycontext.get();
		if(cacheMap != null){
			cacheMap.remove(key);
		}
	}

	/**
	 * 重置
	 */
	public static void reset() {
		if(mycontext.get() != null){
			mycontext.get().clear();
			mycontext.get().put(LOG, new StringBuilder());
			mycontext.get().put(JDBC_COLUMN, JdbcColumnStrategy.CAMEL);
		}
	}
	/**
	 * 销毁
	 */
	public static void destroy() {
		mycontext.set(null);
		mycontext.remove();
	}

	public static JdbcColumnStrategy currentJdbcColumnStrategy() {
		Object v = getValue(JDBC_COLUMN);
		if(v == null || !(v instanceof JdbcColumnStrategy)) {
			return JdbcColumnStrategy.NORMAL;
		}
		
		return (JdbcColumnStrategy)v;
	}
/*	public static Job currentJob() {
		Object v = getValue(JOB);
		if(v == null || !(v instanceof Job)) {
			throw BizException.error("当前job不存在！");
		}
		return (Job)v;
	}*/

}