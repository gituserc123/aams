package com.aier.cloud.biz.common.util;

import com.aier.cloud.basic.common.exception.BizException;
import com.aier.cloud.basic.core.base.BaseEntity;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.ReflectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


/**
 * 工程内部使用最多的工具类
 * 含时间-字符处理，从List Map里取东西，判断空等
 * 使用频率低的方法不要放这里面来
 * @author xiaokek
 * @date 2017年10月25日 上午10:35:52
 */
public class SysUtil {

	public static String getString(Map map, Object key, String defaultValue) {
		return MapUtils.getString(map, key, defaultValue);
	}

	public static String getString(Map map, Object key) {
		return MapUtils.getString(map, key);
	}

	public static Map getMap(Map map, Object key) {
		return MapUtils.getMap(map, key);
	}

	public static Integer getInteger(Map map, Object key) {
		return MapUtils.getInteger(map, key);
	}

	public static Integer getInteger(Map map, Object key, Integer defaultValue) {
		return MapUtils.getInteger(map, key, defaultValue);
	}
	
	public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }
	
	public static boolean isEmpty(Object[] a){
		return ArrayUtils.isEmpty(a);
	}
	public static boolean isNotEmpty(Object[] a){
		return !ArrayUtils.isEmpty(a);
	}
	public static boolean isBlank(CharSequence cs){
		return StringUtils.isBlank(cs);
	}
	public static boolean isNotBlank(CharSequence cs){
		return !StringUtils.isBlank(cs);
	}
	public static boolean isEmpty(CharSequence cs){
		return StringUtils.isEmpty(cs);
	}
	public static boolean isNotEmpty(CharSequence cs){
		return !StringUtils.isEmpty(cs);
	}
	public static boolean isEmpty(Map map){
		return MapUtils.isEmpty(map);
	}
	public static boolean isNotEmpty(Map map){
		return !MapUtils.isEmpty(map);
	}
	public static boolean isEmpty(Collection c){
		return CollectionUtils.isEmpty(c);
	}
	public static boolean isNotEmpty(Collection c){
		return !CollectionUtils.isEmpty(c);
	}
	
	public static <K, V> HashMap<K, V> newHashMap(K key, V value) {
		HashMap<K, V> m = new HashMap<K, V>();
		m.put(key, value);
		return m;
	  }
	
	public static Date toDate(LocalDateTime ldt) {
		Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}
	public static Date toDate(String dateString, String pattern) {
		try {
			return DateUtils.parseDate(dateString, pattern);
		} catch (ParseException e) {
			throw new BizException("时间格式不符合标准："+dateString);
		}
	}
	public static String toString(Date date, String pattern) {
		return DateFormatUtils.format(date, pattern);
	}
	
	/**
	 * 驼峰转下划线
	 * @param name
	 * @return
	 */
	public static String underscore(String name) {
		if (StringUtils.isBlank(name)) {
			return "";
		}
		StringBuilder result = new StringBuilder();
		char[] ca = name.toCharArray();
		for(int i = 0; i < ca.length;i++) {
			char c = ca[i];
			if(Character.isUpperCase(c) && i != 0) {
				result.append("_").append(c);
			}else {
				result.append(c);
			}
		}
		return result.toString();
	}
	
	private static final String UNDER_SCORE_CHAR = "_";
	/**
	 * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
	 * 例如：HELLO_WORLD->helloWorld
	 * @param name 转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static String camel(String name) {
	    // 快速检查
	    if (isEmpty(name)) {
	        // 没必要转换
	        return "";
	    } else if (!name.contains(UNDER_SCORE_CHAR)) {
	    	return name.toLowerCase();
	    }
	    // 用下划线将原始字符串分割
	    String[] camels = name.split(UNDER_SCORE_CHAR);
	    StringBuilder result = new StringBuilder();
	    for (String camel :  camels) {
	        // 跳过原始字符串中开头、结尾的下换线或双重下划线
	        if (isEmpty(camel)) {
	            continue;
	        }
	        // 处理真正的驼峰片段
	        if (result.length() == 0) {
	            // 第一个驼峰片段，全部字母都小写
	        	result.append(camel.toLowerCase());
	        } else {
	            // 其他的驼峰片段，首字母大写
	            result.append(camel.substring(0, 1).toUpperCase());
	            if(camel.length()>1){
	            	result.append(camel.substring(1).toLowerCase());
	            }
	        }
	    }
	    return result.toString();
	}
	
	public static List<Long> getIds(List<Map> list,String key){
		if(CollectionUtils.isEmpty(list)){
			return Collections.emptyList();
		}
		
		List<Long> result = Lists.newArrayListWithCapacity(list.size());
 		for(int i=0; i<list.size(); i++){ 
 			result.add(MapUtils.getLong(list.get(i), key,0L));
		}
		return result;
	}
	
	public static Set<Long> getDistinctIds(List<Map> list,String key){
		if(CollectionUtils.isEmpty(list)){
			return Collections.emptySet();
		}
		
		Set<Long> result = Sets.newHashSet();
 		for(int i=0; i<list.size(); i++){ 
 			result.add(MapUtils.getLong(list.get(i), key,0L));
		}
		return result;
	}
	
	public static Set<Long> getEntityIds(List<? extends BaseEntity> list, String getter){
		if(CollectionUtils.isEmpty(list) || isEmpty(getter)){
			return Collections.emptySet();
		}
		
		Set<Long> result = Sets.newHashSet();
 		for(int i=0; i<list.size(); i++){ 
 			BaseEntity b = list.get(i);
 			Object v = ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(b.getClass(), getter), b);
 			if(v != null) {
 	 			result.add(NumberUtils.toLong(v.toString()));
 			}
		}
		return result;
	}
	

	/**
	 * orders从小到大的排序器
	 */
	public static final Comparator<Map> ORDERS_SORT = new Comparator<Map>() {
		@Override
		public int compare(Map o1, Map o2) {
			int order1 = SysUtil.getInteger(o1, "orders", Integer.MIN_VALUE);
			int order2 = SysUtil.getInteger(o2, "orders", Integer.MIN_VALUE);
			return order1 > order2 ? 1 : (order1 == order2 ? 0 : -1);
		}
	};
}
