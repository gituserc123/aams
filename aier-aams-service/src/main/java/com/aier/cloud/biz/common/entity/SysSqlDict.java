/*
 * Copyright © 2004-2018 Aier EYE Hospital Group.
 * 爱尔眼科医院集团 版权所有
 *
 * Licensed under the Aier EYE Hospital Group License;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.aierchina.com/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aier.cloud.biz.common.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.aier.cloud.basic.core.base.BaseEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;

/**
 * T_SYS_SQL_DICT
 *
 * @author 爱尔眼科
 * @since 2018-02-08 18:21:20
 */
@TableName("T_SYS_SQL_DICT")
public class SysSqlDict extends BaseEntity<SysSqlDict> {

    private static final long serialVersionUID = 7828757356129635649L;

    /** sql标识*/
	@TableField("code")
	@NotBlank @Length(max=100) private String code;

	/** sql配置的类型（auto:自动联想组件,dict:数据量较小的下拉码表/字典组件,dict_lazy:用于数据量较大的延迟翻译场景）*/
	@TableField("type")
	@NotBlank @Length(max=12) private String type;

	/** 创建人*/
	@TableField(value="creator")
	@NotBlank private Long creator;

	/** 创建时间*/
	@TableField(value="create_date", fill=FieldFill.INSERT)
	@NotBlank private Date createDate;

	/** sql字符串*/
	@TableField("sql_str")
	@NotBlank @Length(max=10240) private String sqlStr;

	/** sql的标题*/
	@TableField("title")
	@NotBlank @Length(max=200) private String title;

	/** sql的描述*/
	@TableField("remarks")
	@Length(max=200) private String remarks;

	/** 启停标识*/
	@TableField(value="USING_SIGN")
	@NotBlank private Long usingSign;


	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSqlStr() {
		return sqlStr;
	}
	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getUsingSign() {
		return usingSign;
	}

	public void setUsingSign(Long usingSign) {
		this.usingSign = usingSign;
	}
}