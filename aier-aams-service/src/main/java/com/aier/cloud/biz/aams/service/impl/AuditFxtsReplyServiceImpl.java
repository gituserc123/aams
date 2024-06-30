package com.aier.cloud.biz.aams.service.impl;

import com.aier.cloud.aams.api.request.domain.SecUser;
import com.aier.cloud.biz.aams.dao.AuditFxtsReplyMapper;
import com.aier.cloud.biz.aams.entity.AuditFxtsReply;
import com.aier.cloud.biz.aams.service.AuditFxtsReplyService;
import com.aier.cloud.center.common.context.AierUser;
import com.aier.cloud.center.common.context.UserContext;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Service
public class AuditFxtsReplyServiceImpl extends ServiceImpl<AuditFxtsReplyMapper, AuditFxtsReply> implements AuditFxtsReplyService {

    private static final Logger logger = LoggerFactory.getLogger(AuditFxtsReplyServiceImpl.class);

    @Override
    public List<Map<String,Object>> selectByAuditRecordId(Long auditRecordId){
        /*EntityWrapper<AuditFxtsReply> wrapper = new EntityWrapper<>();
        wrapper.eq("AuditRecordId",auditRecordId);
        return this.baseMapper.selectList(wrapper);*/
        return this.baseMapper.selectByAuditRecordId(auditRecordId);
    }

    @Override
    public boolean save(AuditFxtsReply auditFxtsReply) {
        boolean ret = true;
        try{
            if(null == auditFxtsReply.getAuditFxtsReplyId()){
                AierUser aamsUser = UserContext.getUser();
                if(aamsUser.getMasterSlaveCookie()!=null && aamsUser.getMasterSlaveCookie().get("secUser")!=null){
                    SecUser secUser = JSON.parseObject(aamsUser.getMasterSlaveCookie().get("secUser"), SecUser.class);
                    auditFxtsReply.setAuditFxtsReplyUser(secUser.getSecuserid());
                }else{
                    auditFxtsReply.setAuditFxtsReplyUser(Long.parseLong(aamsUser.getUserCode()));
                }
                auditFxtsReply.setAuditFxtsReplyCrtTime(new Date());
            }
            auditFxtsReply.setAuditFxtsReplyUpdTime(new Date());
            this.insertOrUpdate(auditFxtsReply);
        }catch (Exception e){
            e.printStackTrace();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            logger.error("AuditFxtsReply保存报错：" + sw);
            ret = false;
        }
        return ret;
    }

}
