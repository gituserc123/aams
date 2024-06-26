package com.aier.cloud.biz.aams.dao;

import com.aier.cloud.biz.aams.entity.ReportAuthorized;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Mapper
public interface ReportAuthorizedMapper extends BaseMapper<ReportAuthorized> {

    List<ReportAuthorized> selectByAuditRecordId(@Param("auditRecordId") Long auditRecordId,@Param("secUserId") Long secUserId,
                                                 @Param("reportAuthorizedIsdlt") Boolean reportAuthorizedIsdlt);
}
