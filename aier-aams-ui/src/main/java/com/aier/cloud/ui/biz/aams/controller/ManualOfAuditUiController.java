package com.aier.cloud.ui.biz.aams.controller;


import cn.hutool.core.util.StrUtil;
import com.aier.cloud.aams.api.domain.enums.ForHospTypeEnum;
import com.aier.cloud.aams.api.domain.enums.OrgCapabilityEnum;
import com.aier.cloud.aams.api.request.condition.RiskCondition;
import com.aier.cloud.aams.api.request.condition.SelfRiskCondition;
import com.aier.cloud.aams.api.request.domain.CodeMaster;
import com.aier.cloud.aams.api.request.domain.Risk;
import com.aier.cloud.aams.api.request.domain.SelfRisk;
import com.aier.cloud.basic.api.response.domain.base.PageResponse;
import com.aier.cloud.basic.common.exception.BizException;
import com.aier.cloud.basic.web.controller.BaseController;
import com.aier.cloud.ui.biz.aams.feign.CodeMasterFeignService;
import com.aier.cloud.ui.biz.aams.feign.QueryMapperFeignService;
import com.aier.cloud.ui.biz.aams.feign.RiskFeignService;
import com.aier.cloud.ui.biz.aams.feign.SelfRiskFeignService;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ui/aams/manualofaudit")
public class ManualOfAuditUiController  extends BaseController {

    private static final String MANUAL_OF_AUDIT = "aams/risk/manualofaudit";

    private static final String MANUAL_OF_AUDIT_ADD = "aams/risk/addManual";

    private static final String MANUAL_OF_AUDIT_EDIT = "aams/risk/editManual";

    private static final String SELF_RISK_POP_PAGE = "aams/risk/selfRiskList";

    @Autowired
    private CodeMasterFeignService codeMasterFeignService;

    @Autowired
    private RiskFeignService riskFeignService;

    @Autowired
    private SelfRiskFeignService selfRiskFeignService;

    @Autowired
    private QueryMapperFeignService queryMapperFeignService;

    @RequestMapping(value = "/manualList", method = { RequestMethod.GET, RequestMethod.POST })
    public String manualofaudit(Map<String, Object> map, Model model) {
        List<CodeMaster> codeMasters = codeMasterFeignService.getCodeMasterByType("RiskBussinessType");
        model.addAttribute("codeMasters",codeMasters);
        return MANUAL_OF_AUDIT;
    }

    @RequestMapping(value = "/getAll", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    protected PageResponse<Risk> getAll(RiskCondition cond){
        PageResponse<Risk> ldLists = riskFeignService.getAll(cond);
        // 业务类别
        List<CodeMaster> cmRiskBussinessType = codeMasterFeignService.getCodeMasterByType("RiskBussinessType");
        // 风险级别
        List<CodeMaster> cmRiskLevel = codeMasterFeignService.getCodeMasterByType("RiskLevel");
        // 显示数据处理
        ldLists.getRows().stream().forEach(ldl -> {
            // 适用体量
            String oceDesc = Arrays.stream(OrgCapabilityEnum.values())
                    .filter(oce -> (ldl.getRiskCapability().intValue() & oce.getValue()) == oce.getValue())
                    .map(OrgCapabilityEnum::getContent)
                    .collect(Collectors.joining(","));
            if(Objects.nonNull(oceDesc) && oceDesc.length()>0){
                ldl.setRiskCapabilityDesc(oceDesc);
            }
            ldl.setRiskBussinessTypeDesc(cmRiskBussinessType.stream().filter(cm -> cm.getCodeMasterTypeCode().equals(ldl.getRiskBussinessType())).findFirst().get().getCodeMasterName());
            ldl.setRiskLevelDesc(cmRiskLevel.stream().filter(cm -> cm.getCodeMasterTypeCode().equals(ldl.getRiskLevel())).findFirst().get().getCodeMasterName());
        });

        return ldLists;
    }

    @RequestMapping(value = "/addManual", method = { RequestMethod.GET, RequestMethod.POST })
    public String addManual(Map<String, Object> map, Model model) {
        return MANUAL_OF_AUDIT_ADD;
    }

    @RequestMapping(value = "/editManual", method = { RequestMethod.GET, RequestMethod.POST })
    public String editManual(Model model,Long riskId){
        Risk risk = riskFeignService.getRiskById(riskId);
        // 体量赋值
        Arrays.stream(OrgCapabilityEnum.values()).forEach(oce -> {
            if((risk.getRiskCapability().intValue() & oce.getValue()) == oce.getValue()){
                try {
                    setBooleanField(risk,oce.getCode().toLowerCase(),true);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        // 分值赋值
        List<CodeMaster> cmRiskLevel = codeMasterFeignService.getCodeMasterByType("RiskLevel");
        risk.setRiskLevelScore(new BigDecimal(cmRiskLevel.stream().filter(cmr->cmr.getCodeMasterTypeCode().equals(risk.getRiskLevel())).findFirst().get().getCodeMasterNameDesc()));
        if(risk.getSelfRiskId()!=null){
          SelfRisk selfRisk = selfRiskFeignService.getSelfRiskById(risk.getSelfRiskId());
          if(selfRisk.getSelfRiskForHospType()!=null && selfRisk.getSelfRiskForHospType().intValue()>0){
              Arrays.stream(ForHospTypeEnum.values()).forEach(fhte -> {
                  if((selfRisk.getSelfRiskForHospType().intValue() & fhte.getValue()) == fhte.getValue()){
                      try {
                          setBooleanField(selfRisk, StrUtil.lowerFirst(fhte.getCode()),true);
                      } catch (Exception e) {
                          throw new RuntimeException(e);
                      }
                  }
              });
          }
          model.addAttribute("selfRisk",selfRisk);
        }
        model.addAttribute("riskId",riskId);
        model.addAttribute("risk",risk);
        return MANUAL_OF_AUDIT_EDIT;
    }

    @PostMapping(value = "/saveManual")
    @ResponseBody Object saveManual(Risk risk){
        Map<String, Object> retMap = riskFeignService.save(risk);
        if(retMap.get("code").equals("200")){
            return this.success("保存成功!","riskId",retMap.get("riskId"));
        }else{
            throw new BizException(MapUtils.getString(retMap,"msg"));
        }
    }

    @RequestMapping(value = "/selfRiskList", method = { RequestMethod.GET, RequestMethod.POST })
    public String selfRiskPop(Map<String, Object> map, Model model) {
        return SELF_RISK_POP_PAGE;
    }

    @PostMapping(value = "/getSelfRiskAll")
    @ResponseBody Object getSelfRiskAll(SelfRiskCondition cond){
        PageResponse<SelfRisk> pageResponse = selfRiskFeignService.getAll(cond);
        List<CodeMaster> codeMasters = codeMasterFeignService.getCodeMasterByType("SelfEvaluationBussinessType");
        pageResponse.getRows().stream().forEach(sr -> {
            sr.setSelfRiskIdShow(sr.getSelfRiskId());
            CodeMaster srbt = codeMasters.stream().filter(cm -> cm.getCodeMasterTypeCode().equals(sr.getSelfRiskBussinessType())).findFirst().get();
            sr.setSelfRiskBussinessTypeTxt(Objects.nonNull(srbt)?srbt.getCodeMasterName():"");
        });
        return pageResponse;
    }

    @PostMapping(value = "/editSaveManual")
    @ResponseBody Object editSaveManual(@RequestBody Map<String, Object> formData){
        System.out.println(formData.size());
        Risk editRisk = JSONArray.parseObject(formData.get("risk").toString(), Risk.class);
        riskFeignService.editSaveManual(formData);
        return this.success("保存成功!","riskId",editRisk.getRiskId());
    }

    @RequestMapping(value = "/getCountBySelfRiskId")
    public @ResponseBody Integer getCountBySelfRiskId(String selfRiskId){
        return queryMapperFeignService.queryCount(" count(*) ","Risk","SelfRiskId=" + selfRiskId);
    }

    @RequestMapping(value = "/getUniqueSelfRiskCode")
    public @ResponseBody Integer getUniqueSelfRiskCode(String selfRiskId,String selfRiskBussinessType,String selfRiskCode){
        if(StrUtil.isEmptyIfStr(selfRiskId)){
            return queryMapperFeignService.queryCount(" count(*) ","SelfRisk"," SelfRiskBussinessType='" + selfRiskBussinessType
                    + "' and SelfRiskCode='" + selfRiskCode + "'");
        }else{
            return queryMapperFeignService.queryCount(" count(*) ","SelfRisk","SelfRiskId <> " + selfRiskId + " and SelfRiskBussinessType='" + selfRiskBussinessType
                    + "' and SelfRiskCode='" + selfRiskCode + "'");
        }
    }

    @RequestMapping(value = "/unbindBangSelfRisk")
    @ResponseBody
    public Object unbindBangSelfRisk(Long riskId){
        return riskFeignService.unbindBangSelfRisk(riskId);
    }

    // 反射设置Boolean属性值的方法
    private static void setBooleanField(Object obj, String fieldName, Boolean value) throws Exception {
        Class<?> clazz = obj.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true); // 设置可访问
        field.set(obj, value); // 给属性赋值
    }


}
