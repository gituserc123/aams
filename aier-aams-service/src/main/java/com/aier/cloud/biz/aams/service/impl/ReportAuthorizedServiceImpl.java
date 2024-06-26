package com.aier.cloud.biz.aams.service.impl;

import com.aier.cloud.biz.aams.dao.ReportAuthorizedMapper;
import com.aier.cloud.biz.aams.entity.ReportAuthorized;
import com.aier.cloud.biz.aams.service.ReportAuthorizedService;
import com.aier.cloud.center.common.context.UserContext;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Service
public class ReportAuthorizedServiceImpl extends ServiceImpl<ReportAuthorizedMapper, ReportAuthorized> implements ReportAuthorizedService {

    private static final Logger logger = LoggerFactory.getLogger(ReportAuthorizedServiceImpl.class);

    @Override
    public List<ReportAuthorized> selectByAuditRecordId(Long auditRecordId,Long secUserId,Boolean reportAuthorizedIsdlt) {
        return this.baseMapper.selectByAuditRecordId(auditRecordId,secUserId,reportAuthorizedIsdlt);
    }

    @Override
    public boolean save(ReportAuthorized reportAuthorized) {
        boolean ret = true;
        try{
            if(null == reportAuthorized.getReportAuthorizedId()){
                reportAuthorized.setReportauthorizedcreateUser(String.valueOf(UserContext.getUserId()));
                reportAuthorized.setReportauthorizedcreateTime(new Date());
            }
            reportAuthorized.setReportauthorizedupdateUser(String.valueOf(UserContext.getUserId()));
            reportAuthorized.setReportauthorizedupdateTime(new Date());
            this.insertOrUpdate(reportAuthorized);
        }catch (Exception e){
            e.printStackTrace();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            logger.error("ReportAuthorized保存报错：" + sw);
            ret = false;
        }
        return ret;
    }
}
