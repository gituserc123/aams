package com.aier.cloud.biz.aams.controller;

import cn.hutool.core.util.ObjectUtil;
import com.aier.cloud.aams.api.request.condition.RiskCondition;
import com.aier.cloud.basic.api.response.domain.base.PageResponse;
import com.aier.cloud.biz.aams.dao.QueryMapper;
import com.aier.cloud.biz.aams.entity.Risk;
import com.aier.cloud.biz.aams.entity.RiskScoreStandard;
import com.aier.cloud.biz.aams.entity.SelfRisk;
import com.aier.cloud.biz.aams.service.RiskScoreStandardService;
import com.aier.cloud.biz.aams.service.RiskService;
import com.aier.cloud.biz.aams.service.SelfRiskService;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.aier.cloud.basic.starter.service.controller.BaseController;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Controller
@RequestMapping("/api/service/biz/aams/risk")
public class RiskController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(RiskController.class);

    @Autowired
    private RiskService riskService;

    @Autowired
    private SelfRiskService selfRiskService;

    @Resource
    private QueryMapper queryMapper;

    @Autowired
    private RiskScoreStandardService riskScoreStandardService;

    @RequestMapping(value = "/getRiskById")
    public @ResponseBody Risk getRiskById(@RequestParam("riskId") Long riskId) {
        return riskService.selectById(riskId);
    }

    @RequestMapping(value = "/getAll")
    @ResponseBody
    public PageResponse<Risk> getAll(@RequestBody RiskCondition cond){
        Page<Map<String, Object>> page = tranToPage(cond);
        List<Risk> retLists = riskService.getAll(page,cond);
        return  returnPageResponse(page,retLists);
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String,Object> save(@RequestBody Risk risk){
        return riskService.save(risk);
    }

    @PostMapping("/editSaveManual")
    @ResponseBody
    public Map<String, Object> editSaveManual(@RequestBody Map<String, Object> formData){
        Risk editRisk = JSONArray.parseObject(formData.get("risk").toString(), Risk.class);
        Risk oneRisk = riskService.selectById(editRisk.getRiskId());
        Long preSelfRiskId = oneRisk.getSelfRiskId();
        Boolean isNotEqual = false;
        // 1.保存selfRisk
        if(ObjectUtil.isNotEmpty(formData.get("selfRisk"))){
            SelfRisk selfRisk = JSONArray.parseObject(formData.get("selfRisk").toString(), SelfRisk.class);
            selfRisk.setSelfRiskFirstLevel(editRisk.getRiskProject());
            selfRisk.setSelfRiskSecondLevel(editRisk.getRiskType());
            // 先保存，再赋值
            selfRiskService.save(selfRisk);
            editRisk.setSelfRiskId(selfRisk.getSelfRiskId());
            if(ObjectUtil.isNotEmpty(preSelfRiskId) && preSelfRiskId.longValue() != selfRisk.getSelfRiskId().longValue()){
                isNotEqual = true;
            }
            try{
                if(isNotEqual){
                    // 1.1 判断risk关联的selfRisk是否同一个,不同则将RiskScoreStandard中的selfRisk对应记录逻辑删除
                    List<Map<String, Object>> delSelfRiskScoreLists = queryMapper.queryListSql(" * ",
                            "RiskScoreStandard"," RiskScoreStandardManualId = " + preSelfRiskId + " and RiskScoreStandardType = '2' ");
                    delSelfRiskScoreLists.stream().forEach(drsl -> riskScoreStandardService.deleteRiskScoreStandard(MapUtils.getLong(drsl,"riskscorestandardid")));
                }
                // 1.2 更新新的SelfRiskId对应的RiskScoreStandard表记录
                List<RiskScoreStandard> riskScoreLists = riskScoreStandardService.selectList(new EntityWrapper<RiskScoreStandard>().where("RiskScoreStandardManualId={0} and RiskScoreStandardType='1'", editRisk.getRiskId()));
                if(ObjectUtil.isNotEmpty(riskScoreLists) && riskScoreLists.size()>0){
                    List<RiskScoreStandard> selfRiskScoreLists = riskScoreStandardService.selectList(new EntityWrapper<RiskScoreStandard>().where("RiskScoreStandardManualId={0} and RiskScoreStandardType='2'", selfRisk.getSelfRiskId()));
                    riskScoreLists.stream().forEach(rsl -> {
                        Optional<RiskScoreStandard> opnlRs = selfRiskScoreLists.stream()
                                .filter(itemRs -> itemRs.getRiskScoreStandardCode().intValue() == rsl.getRiskScoreStandardCode().intValue())
                                .findFirst();
                        // 如果存在RiskScoreStandardCode相同的记录,修改RiskScoreStandardId后,再更新
                        if(opnlRs.isPresent()){
                            rsl.setRiskScoreStandardId(opnlRs.get().getRiskScoreStandardId());
                        }else{
                            rsl.setRiskScoreStandardId(null);
                        }
                        rsl.setRiskScoreStandardManualId(selfRisk.getSelfRiskId());
                        rsl.setRiskScoreStandardType("2");
                        riskScoreStandardService.saveByRisk(rsl);
                    });
                }
            }catch (Exception e){
                e.printStackTrace();
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                logger.error("保存RiskScoreStandard报错：" + sw);
            }
        }
        // 2.保存risk
        riskService.save(editRisk);

        return Maps.newHashMap();
    }

    @RequestMapping(value = "/unbindBangSelfRisk")
    @ResponseBody Object unbindBangSelfRisk(@RequestParam("riskId") Long riskId){
        Map<String, Object> map = Maps.newHashMap();
        Risk risk = riskService.selectById(riskId);
        Long selfRiskId = risk.getSelfRiskId();
        risk.setSelfRiskId(null);
        riskService.updateById(risk);
        List<RiskScoreStandard> selfRiskScoreLists = riskScoreStandardService.selectList(new EntityWrapper<RiskScoreStandard>().where("RiskScoreStandardManualId={0} and RiskScoreStandardType='2'", selfRiskId));
        selfRiskScoreLists.stream().forEach(rsl -> riskScoreStandardService.deleteRiskScoreStandard(rsl.getRiskScoreStandardId()));
        map.put("msg", "解绑成功！");
        map.put("code", "200");
        return map;
    }

}
