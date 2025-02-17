package com.aier.cloud.biz.common.util;

import com.aier.cloud.basic.common.exception.BizAssert;
import com.baomidou.mybatisplus.entity.Columns;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 数据库操作工具类
 *
 * @author xiaokek
 * @since 2018年3月15日 上午8:40:37
 */
public class DbUtil {
	/**
	 * mybatis-plus的insertBatch方法在插入为空的情况下会抛无意义的异常,用此方法避免掉
	 * @param s
	 * @param ens
	 * @return
	 */
	public static <T> boolean insertBatch(IService<T> s, List<T> ens) {
		if(CollectionUtils.isEmpty(ens)) {
			return false;
		}
		return s.insertBatch(ens, 100);
	}
	
	/**
	 * 返回驼峰的列别名配置
	 * camel是骆驼的意思
	 * @param ca 输入的字符串请填驼峰
	 * @return
	 */
	public static Columns camel(String... ca) {
		BizAssert.notEmpty(ca);
		Columns cs = Columns.create();
		for(String c : ca) {
			cs.column(SysUtil.underscore(c), "\""+c+"\"");
		}
		return cs;
	}
	
	private static final int MAX_SIZE = 1000;
	
	/**
	 * 解决in超过1000个时的问题, 超过1000 会用 or 分割 in语句
	 * @param w
	 * @param column
	 * @param value
	 */
	public static void in(EntityWrapper w, String column, Collection value) {
		if(SysUtil.isNotEmpty(value)) {
			if(value.size() <= MAX_SIZE) {
				w.in(column, value);
			}else {
				w.andNew();
				List in = null;
				Iterator it = value.iterator();
				int i = 0;
				while(it.hasNext()) {
					Object o = it.next();
					if(in == null) {
						in = Lists.newArrayList();
					}
					in.add(o);
					i++;
					if(in.size() == MAX_SIZE) {
						w.in(column, in);
						in = null;
						if(i != value.size()) {
							w.or();
						}
					}else {
						if(i == value.size()) {
							w.in(column, in);
						}
					}
				}
			}
		}
	}
}
