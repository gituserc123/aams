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
 package com.aier.cloud.biz.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aier.cloud.basic.api.request.condition.sys.SysSqlDictCondition;
import com.aier.cloud.biz.common.entity.SysSqlDict;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * 
 * 
 * @author 爱尔眼科
 * @since 2018-02-08 18:21:20
 */
public interface SysSqlDictMapper extends BaseMapper<SysSqlDict> {

 /**
  * 根据条件查询 Liuyi
  * @param page
  * @param cond
  * @return
  */
 List<SysSqlDict> searchByCondForPage(Page<Map<String, Object>> page, @Param("cond") SysSqlDictCondition cond);
}