package com.aier.cloud.biz.aams.service;

import com.aier.cloud.biz.aams.entity.ReportAuthorized;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
public interface ReportAuthorizedService extends IService<ReportAuthorized> {
    List<ReportAuthorized> selectByAuditRecordId(Long auditRecordId,Long secUserId,Boolean reportAuthorizedIsdlt);

    boolean save(ReportAuthorized reportAuthorized);
}
