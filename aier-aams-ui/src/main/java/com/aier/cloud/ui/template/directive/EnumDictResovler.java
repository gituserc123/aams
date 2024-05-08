package com.aier.cloud.ui.template.directive;

import com.aier.cloud.basic.common.convert.EnumDict;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.EnumUtils;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * 本地字典类。
 * 對於那些数量很少，被t_dcg_code_dict所遗弃但又犯不着新建表的，统统放这里面来
 * 比如说一些审批状态啊，表达“是否”的呀，表达“对错”的呀 等等
 * @author xiaokek
 * @since 2018年3月7日 上午10:19:20
 */
@Component
public class EnumDictResovler {
	
	private final ConcurrentMap<String, List> local = Maps.newConcurrentMap();
	
	/**
	 * 添加一個字典配置項
	 * @param clazz 
	 */
    public final void add(Class<? extends EnumDict> clazz) {
		if(local.containsKey(clazz.getName())) {
			return;
		}
		List<EnumDict> enums = EnumUtils.getEnumList((Class)clazz);
		List<Map> result = Lists.newArrayList();
		for(EnumDict e : enums){
			Map value = Maps.newHashMap();
			value.put("valueCode", e.getEnumCode());
			value.put("valueDesc", e.getEnumDesc());
			value.put("frstSpell", e.getFirstSpell());
			result.add(value);
		}
		local.putIfAbsent(clazz.getName(), result);
	}
	
	public List getDictList(String type) {
		List value = local.get(type);
		if(value == null) {
			try{
				Class c = Class.forName(type);
				this.add(c);
			} catch(Exception e){
				local.putIfAbsent(type, Collections.emptyList());
			}
			return local.get(type);
		}else {
			return value;
		}
	}
}
