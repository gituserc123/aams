package com.aier.cloud.biz.aams.service.impl;

import com.aier.cloud.aams.api.request.condition.RiskCondition;
import com.aier.cloud.aams.api.request.domain.SecUser;
import com.aier.cloud.biz.aams.dao.RiskMapper;
import com.aier.cloud.biz.aams.entity.Risk;
import com.aier.cloud.biz.aams.service.RiskService;
import com.aier.cloud.center.common.context.AierUser;
import com.aier.cloud.center.common.context.UserContext;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
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
public class RiskServiceImpl extends ServiceImpl<RiskMapper, Risk> implements RiskService {

    private static final Logger logger = LoggerFactory.getLogger(RiskServiceImpl.class);

    @Override
    public List<Risk> getAll(Page<Map<String, Object>> page, RiskCondition cond) {
        return this.baseMapper.getAll(page,cond);
    }

    @Override
    public Map<String,Object> save(Risk risk) {
        Map<String,Object> result = Maps.newHashMap();
        try{
            AierUser aamsUser = UserContext.getUser();
            String userCode;
            if(aamsUser.getMasterSlaveCookie()!=null && aamsUser.getMasterSlaveCookie().get("secUser")!=null && !aamsUser.getMasterSlaveCookie().get("secUser").equals("null")){
                SecUser secUser = JSON.parseObject(aamsUser.getMasterSlaveCookie().get("secUser"), SecUser.class);
                userCode=String.valueOf(secUser.getSecuserid());
            }else{
                userCode=aamsUser.getUserCode();
            }
            if(null == risk.getRiskId()){
                risk.setRiskcreateUser(userCode);
                risk.setRiskcreateTime(new Date());
                risk.setRiskDesc("  ");
                risk.setRiskIsdlt(false);
                risk.setRiskCapability(4L);
                if(risk.getRiskIsPost()==null){
                    risk.setRiskIsPost(false);
                }
                if(risk.getRiskRemote()==null){
                    risk.setRiskRemote(false);
                }
                if(risk.getRiskIsRandom()==null){
                    risk.setRiskIsRandom(false);
                }
                if(risk.getRiskSenstivity()==null){
                    risk.setRiskSenstivity(false);
                }
                if(risk.getRiskIsQueDescStd()==null){
                    risk.setRiskIsQueDescStd(false);
                }
            }
            risk.setRiskupdateUser(userCode);
            risk.setRiskupdateTime(new Date());
            this.insertOrUpdate(risk);
            result.put("msg","sucess");
            result.put("code","200");
            result.put("riskId",risk.getRiskId());
        }catch (Exception e){
            e.printStackTrace();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            logger.error("Risk保存报错：" + sw);
            result.put("msg","保存失败:" + sw);
            result.put("code","500");
        }
        return result;
    }
}
