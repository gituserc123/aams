/**
 * 
 */
package com.aier.cloud.biz.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * @author rain_deng
 *
 */
public interface AutoCompleteMapper{

    /**
     * 查询复杂联想数据 
     * @param sql
     * @param limit
     * @return
     */
    List<Map<String, Object>> selectAutoComplete(@Param("sql") String sql, @Param("limit") Integer limit);
    
}
