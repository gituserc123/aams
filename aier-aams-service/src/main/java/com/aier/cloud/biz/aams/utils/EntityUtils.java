package com.aier.cloud.biz.aams.utils;

import com.aier.cloud.aams.api.domain.constant.Constants;
import com.aier.cloud.basic.common.exception.BizException;
import com.aier.cloud.center.common.context.UserContext;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ObjectUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EntityUtils {

	private final static Logger log = LoggerFactory.getLogger(EntityUtils.class);
	
	private final static String TIME_SPLIT_SYMBOL = ":";

	public static<T> T addOperatorInfo(T entity) {
		try {
			Long userId = 0L;
			if(!ObjectUtils.isEmpty(UserContext.getUserId())) userId = UserContext.getUserId();

			Class<?> itemClass = entity.getClass();
			Method getId = itemClass.getMethod("getId");
			if(ObjectUtils.isEmpty(getId.invoke(entity))){
				try {
					Method setCreator = itemClass.getMethod("setCreator", Long.class);
					setCreator.invoke(entity, userId);
					Method setCreateDate = itemClass.getMethod("setCreateDate", Date.class);
					setCreateDate.invoke(entity, new Date());
				}catch(NoSuchMethodException e) {
					log.info("Not found set method");
				}
			}

			Method setModifer = itemClass.getMethod("setModifer", Long.class);
			Method setModifyDate = itemClass.getMethod("setModifyDate", Date.class);
			setModifer.invoke(entity, userId);
			setModifyDate.invoke(entity, new Date());

		}catch(Exception ex) {
			log.error(ex.getMessage());
		}
		return entity;
	}

	/**
	 * 将对象转换为Map
	 * @param obj
	 * @return
	 */
	public static Map<String,Object> transObjToMap(Object obj){

		Map<String,Object> returnMap = new HashMap<String,Object>(30);
		if(ObjectUtils.isEmpty(obj)) return returnMap;
		//先获取obj的所有get方法
		Method[] methods = obj.getClass().getMethods();
		//存放所有的getter方法
		//Map<String,Method> methodsMap = new HashMap<String,Method>();
		if(methods == null || methods.length == 0){
			throw new BizException("该实体类没有定义getter和setter方法！" );
		}
		//把所有的getter方法都put进methodsMap中
		String colName = "";
		try{
			for(int i=0;i<methods.length;i++){
				if(methods[i].getName().startsWith("get") && !"getClass".equals(methods[i].getName())){
					//属性名首字母变小写
					colName = methods[i].getName().substring(3);
					colName = colName.substring(0,1).toLowerCase() + colName.substring(1);
					//methodsMap.put(colName, methods[i]);
					//调用get方法，将值复制到returnMap中
					Object value = methods[i].invoke(obj);
					if(value instanceof Long) {
						returnMap.put(colName, String.valueOf(value));
					}else {
						returnMap.put(colName, value);
					}

				}
			}
		}catch(Exception ex){
			throw new BizException(obj.getClass().getName()+"调用getter方法时出错！"+ex.getMessage());
		}
		return returnMap;
	}

	public static <T> T transMapToObject(Map<String, Object> map, Class<T> c) {
		// 1、通过字节码对象创建空的实例
		try {
			T o = c.newInstance();
			// 2、通过 Introspector 类把bean对象信息封装到 beanInfo 中
			BeanInfo beanInfo = Introspector.getBeanInfo(c, Object.class);
			// 3、通过 getPropertyDescriptors() 获取一个属性(get/set)数组
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			// 4、遍历该数组，把获取的名字作为 map 的 key，通过 key 取出对应的 value 值
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				String key = lowerFirstCase(propertyDescriptor.getName());
				Method writeMethod = propertyDescriptor.getWriteMethod();
				if (map.keySet().stream().anyMatch(ki -> ki.equalsIgnoreCase(key))){
					Object value = map.get(key);
					if(!ObjectUtils.isEmpty(value)) {
						// 解决 argument type mismatch 的问题，转换成对应的javaBean属性类型
						String typeName = propertyDescriptor.getPropertyType().getTypeName();
						// System.out.println(key +"<==>"+ typeName);
						if ("java.lang.Integer".equals(typeName)){
							value = Integer.parseInt(value.toString());
						}else if("java.lang.Long".equals(typeName)){
							value = Long.parseLong(value.toString());
						}else if ("java.util.Date".equals(typeName)){
							String valueStr = value.toString();
							String[] timeArr = valueStr.split(TIME_SPLIT_SYMBOL);
							if(timeArr.length == 2) {
								value = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(valueStr);
							}else if(timeArr.length == 3) {
								value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(valueStr);	
							}else {
								value = new SimpleDateFormat("yyyy-MM-dd").parse(valueStr);
							}
						}else if("java.sql.Date".equals(typeName)){
							value = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(value.toString()).getTime());
						}else if ("java.math.BigDecimal".equals(typeName)) {
							value = new BigDecimal(value.toString());
						}else{
							value = String.valueOf(value);
						}

						writeMethod.invoke(o, value);
					}
				}else {
					continue;
				}

			}
			return o;
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
	public static void mapToObject(Map<String,Object> source,Object target){
		if(source==null){
			return;
		}
		Map<Class,Class> classMap=new HashMap<>();
		classMap.put(int.class,Integer.class);
		classMap.put(char.class,Character.class);
		classMap.put(byte.class,Byte.class);
		classMap.put(short.class,Short.class);
		classMap.put(long.class,Long.class);
		classMap.put(float.class,Float.class);
		classMap.put(double.class,Double.class);
		classMap.put(boolean.class,Boolean.class);
		Class clz=target.getClass();
		Method []mths=clz.getMethods();
		for(Method mth:mths){
			for(String k:source.keySet()){
				if(mth.getName().toUpperCase().equals("SET"+k.toUpperCase())||mth.getName().toUpperCase().equals("SET"+k.toUpperCase().replaceAll("_|-",""))){
					if(source.get(k)!=null && (mth.getParameters()[0].getType().isAssignableFrom(source.get(k).getClass())||source.get(k).getClass().equals(mth.getParameters()[0].getType())))
					try {
						mth.invoke(target,source.get(k));
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}

		}
	}

	public static String generatUUID(Integer length) {
		String uuid = String.valueOf(System.currentTimeMillis());
		Integer randomLen = length - uuid.length();
		if(randomLen > 0) {
			uuid += RandomStringUtils.randomNumeric(randomLen);
		}
		return uuid;
	}

	/**
	 * @desc 如果首字母为大写,则将首字母改为小写
	 * @param string
	 * @return
	 */
	public static String lowerFirstCase(String string) {
		char[] methodName = string.toCharArray();
		if (65 <= methodName[0] && methodName[0] <= 90) {
			methodName[0] ^= 32;
		}
		return String.valueOf(methodName);
	}
	
	
	public static Object findEntityById(Long id, String name) throws Exception{ 
		Class<?> eClass = Class.forName(Constants.ENTITY_PREFIX.concat(name));
		ApplicationContext applicationContext = SpringBootBeanUtil.getApplicationContext();
	    Class<?> eService = Class.forName(Constants.SERVICE_PREFIX.concat(name).concat(Constants.SERVICE_SUFFIX));
	    Class<?>  eSuperService = eService.getSuperclass();
	    Method method = eSuperService.getDeclaredMethod("selectById", Serializable.class);
	    Object eObject = method.invoke(applicationContext.getBean(eService), id);
		return eObject;
	}
}
