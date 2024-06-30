package com.aier.cloud.biz.aams.dao;

import com.aier.cloud.biz.aams.entity.AuditFxtsReply;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Mapper
public interface AuditFxtsReplyMapper extends BaseMapper<AuditFxtsReply> {

    List<Map<String,Object>> selectByAuditRecordId(@Param("auditRecordId") Long auditRecordId);

}
