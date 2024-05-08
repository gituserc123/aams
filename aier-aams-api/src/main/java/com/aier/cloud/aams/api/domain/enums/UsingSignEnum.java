package com.aier.cloud.aams.api.domain.enums;

import com.aier.cloud.basic.common.convert.EnumDict;
import com.baomidou.mybatisplus.enums.IEnum;

/**
 * 表達是否停用的枚舉
 *
 * @author rain_deng
 * 1.启用  0.停用 
 * @since 2018年3月28日 下午3:34:05
 */
public enum UsingSignEnum implements IEnum, EnumDict<UsingSignEnum> {
	/**
	 * 启用
	 */
    ENABLE(1, "启用"), 
	
	/**
	 * 停用
	 */
    DISABLE(0, "停用");

	private Integer value;
	private String content;

	private UsingSignEnum(Integer value, String content) {
		this.value = value;
		this.content = content;
	}
	
	@Override
	public Integer getValue() {
        return value;
    }

    public String getContent() {
        return content;
    }


    @Override
	public String getEnumCode() {
		return String.valueOf(value);
	}

	@Override
	public String getEnumDesc() {
		return content;
	}
}