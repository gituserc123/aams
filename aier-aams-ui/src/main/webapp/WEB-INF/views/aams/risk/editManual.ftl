<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <meta http-equiv="X-UA-Compatible" content="IE=9"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>编辑风险点 - 爱尔医院</title>
    [#include "/WEB-INF/views/common/include_resources.ftl"]
</head>
<style type="text/css">
    .selfRisk-prompt {
        cursor: pointer; /* 使鼠标悬停时光标显示为“手”的形状 */
    }

</style>

<script type="text/javascript">
</script>
<body>
<!-- <div class="row" style="margin-top: 5px;">-->
<form id="editManualForm"  class="soform formA form-validate form-enter pad-t3 manualRiskForm">
    <input class="txt hide" type="text" id="id" name="id" value=""/>
    <input type="hidden" id="hospId" name="hospId" value="" />
    <input type="hidden" id="instId" name="instId" value="" />
    <input type="hidden" name="riskId" value="${risk.riskId!}" />
    <div class="basicInfo">
        <h2 class="h2-title-a">
            <span class="s-title">基本信息</span>
        </h2>
        <hr class="mar-l10 mar-r10 mar-t0 mar-b20" style="border-color:#b2def4">
        <div class="row">
            <div class="p3">
                <div class="item-one solab-s">
                    <label class="lab-item">业务类别：</label>
                    <select class="drop drop-sl-v easyui-combobox  w-150" name="riskBussinessType" id="riskBussinessType"  data-options="valueField:'codeMasterTypeCode',textField:'codeMasterName',clearIcon:true,required:true"></select>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-l">
                    <label class="lab-item">风险编号：</label>
                    <input class="txt txt-validate required" type="text" id="riskCode" name="riskCode"  value="${risk.riskCode!}" autocomplete="off" data-options="required:true"/>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-s">
                    <label class="lab-item">审计项目：</label>
                    <input class="txt txt-validate required" type="text" id="riskProject" name="riskProject"  value="${risk.riskProject!}" autocomplete="off" data-options="required:true"/>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-s">
                    <label class="lab-item">风险类别：</label>
                    <input class="txt txt-validate" type="text" id="riskType" name="riskType" value="${risk.riskType!}" autocomplete="off"  dataOptions="required:true" noNull="风险类别必填"/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="p3">
                <div class="item-one solab-s">
                    <label class="lab-item">内控点：</label>
                    <input class="txt txt-validate" type="text" id="riskName" name="riskName" value="${risk.riskName!}" autocomplete="off" data-options="required:true"/>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-l">
                    <label class="lab-item">风险级别：</label>
                    <select class="drop drop-sl-v easyui-combobox  w-150" name="riskLevel" id="riskLevel"  data-options="valueField:'codeMasterTypeCode',textField:'codeMasterName',clearIcon:true,required:true,
                        onChange:((v)=>{
                            let filtered = $.grep($('#riskLevel').combobox('getData'), function(item) {
                                return item.codeMasterTypeCode === v;
                            });
                            $('#riskLevelScore').val(filtered[0].codeMasterNameDesc);
                            $('#editSelfRiskForm #selfRiskScore').numberbox('setValue',filtered[0].codeMasterNameDesc);
                        })"></select>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-s">
                    <label class="lab-item">分值：</label>
                    <input class="txt txt-validate" type="text" id="riskLevelScore" name="riskLevelScore" value="${risk.riskLevelScore!}" readonly="true" autocomplete="off" data-options="required:true"/>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-s">
                </div>
            </div>
        </div>
        <div class="clearfix mar-l20 mar-b10">
            <span class="fl">
                <label class="lab-inline w-60">扣分标准：</label>
                <textarea class="txta txt-validate adaptiveTextarea" style="width:1000px" dataOptions="required:true" noNull="扣分标准必填" id="riskEmergencyRef"
                          name="riskEmergencyRef" cols="200" row="10" placeholder="请填写扣分标准">${risk.riskEmergencyRef!}</textarea>
            </span>
        </div>
        <div class="clearfix mar-l20 mar-b10">
            <span class="fl">
                <label class="lab-inline w-60">审计方法：</label>
                <textarea class="txta txt-validate adaptiveTextarea" style="width:1000px" dataOptions="required:true" noNull="审计方法必填" id="riskMethod"
                          name="riskMethod" cols="300" row="20" placeholder="请填写审计方法">${risk.riskMethod!}</textarea>
            </span>
        </div>
        <div class="clearfix mar-l20 mar-b10">
            <span class="fl">
                <label class="lab-inline w-80">数字化结论：</label>
                <input type="checkbox" id="riskRemote" name="riskRemote" value="true" [#if '${risk.riskRemote }' == true ] checked="checked" [/#if]>
            </span>
        </div>
        <div  id="digitalModelDiv" class="clearfix mar-l20 mar-b10 hide">
            <span class="fl">
                <label class="lab-inline w-60">数字化模型：</label>
                <textarea class="txta txt-validate adaptiveTextarea" style="width:1000px" dataOptions="required:true" noNull="数字化模型必填" id="riskDigitalModel"
                          name="riskDigitalModel" cols="200" row="10" placeholder="请填写数字化模型">${risk.riskDigitalModel!}</textarea>
            </span>
        </div>
        <div class="clearfix mar-l20 mar-b10">
            <span class="fl">
                <label class="lab-inline w-60">制度支持：</label>
                <textarea class="txta txt-validate adaptiveTextarea" style="width:1000px" dataOptions="required:true" noNull="制度支持必填" id="riskInstitution"
                          name="riskInstitution" cols="300" row="20" placeholder="请填写制度支持">${risk.riskInstitution!}</textarea>
            </span>
        </div>
        <div class="row">
            <div class="p3">
                <div class="item-one solab-l" style="padding-left: 80px !important;">
                    <label class="lab-item" style="text-align: left !important;">内控点类别：</label>
                    <select class="drop drop-sl-v easyui-combobox w-150" name="riskCategory" id="riskCategory"  data-options="valueField:'codeMasterTypeCode',textField:'codeMasterName',clearIcon:true,required:true"></select>
                </div>
            </div>
            <div class="p6" style="margin-left: 1% !important;">
                <div class="item-one solab-l" style="float: left !important;">
                    <label class="lab-item">是否合规项目：</label>
                    <input type="checkbox" id="riskIsPost" name="riskIsPost" value="" [#if '${risk.riskIsPost }' == true ] checked="checked" [/#if]>
                </div>
                <div  id="riskIsRandomDiv" class="item-one solab-s hide" style="float: left !important;">
                    <label class="lab-item">是否随机：</label>
                    <input type="checkbox" id="riskIsRandom" name="riskIsRandom" value="" [#if '${risk.riskIsRandom }' == true ] checked="checked" [/#if]>
                </div>
                <div class="item-one solab-s" style="float: left !important;">
                    <label class="lab-item">敏感信息：</label>
                    <input type="checkbox" id="riskSenstivity" name="riskSenstivity" value="" [#if '${risk.riskSenstivity }' == true ] checked="checked" [/#if]>
                </div>
                <div class="item-one solab-lb" style="float: left !important;">
                    <label class="lab-item">是否问题描述标准化：</label>
                    <input type="checkbox" id="riskIsQueDescStd" name="riskIsQueDescStd" value="" [#if '${risk.riskIsQueDescStd }' == true ] checked="checked" [/#if]>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="p3">
                <div class="item-one solab-l" style="padding-left: 80px !important;">
                    <label class="lab-item" style="text-align: left !important;">敏感信息类别：</label>
                    <input class="txt txt-validate" type="text" id="riskSenstivityDesc" name="riskSenstivityDesc" value="${risk.riskSenstivityDesc!}" autocomplete="off" data-options="required:true"/>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-l">
                    <label class="lab-item">整改类型：</label>
                    <select class="drop drop-sl-v easyui-combobox w-150" name="riskRectifyType" id="riskRectifyType"  data-options="valueField:'codeMasterTypeCode',textField:'codeMasterName',clearIcon:true,required:true"></select>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-l">
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-l">
                </div>
            </div>
        </div>
        <div class="clearfix mar-l20 mar-b10">
            <span class="fl">
                <label class="lab-inline w-60">内控点整改属性：</label>
                <textarea class="txta txt-validate adaptiveTextarea" style="width:1000px" dataOptions="required:true" noNull="内控点整改属性必填" id="riskRectifyAttribute"
                          name="riskRectifyAttribute" cols="200" row="10" placeholder="请填写内控点整改属性">${risk.riskRectifyAttribute!}</textarea>
            </span>
        </div>
        <div class="clearfix mar-l20 mar-b30 orgCapabilityDiv" style="margin-left: 30px !important;">
            <div class="easyui-panel" title="体量" style="width:1068px;padding:10px 10px 10px 20px;">
                <div class="item-one solab-s" style="float: left !important;">
                    <label class="lab-item">大体量：</label>
                    <input type="checkbox" id="big" name="big" value="true" orgCapabilityEnum="32" [#if '${risk.big }' == true ] checked="checked" [/#if]>
                </div>
                <div class="item-one solab-s" style="float: left !important;">
                    <label class="lab-item">中体量：</label>
                    <input type="checkbox" id="medium" name="medium" value="true" orgCapabilityEnum="16"  [#if '${risk.medium }' == true ] checked="checked" [/#if]>
                </div>
                <div class="item-one solab-s" style="float: left !important;">
                    <label class="lab-item">小体量：</label>
                    <input type="checkbox" id="small" name="small" value="true" orgCapabilityEnum="8"  [#if '${risk.small }' == true ] checked="checked" [/#if]>
                </div>
                <div class="item-one solab-s" style="float: left !important;">
                    <label class="lab-item">新院：</label>
                    <input type="checkbox" id="newhosp" name="newhosp" value="true" orgCapabilityEnum="4"  [#if '${risk.newhosp }' == true ] checked="checked" [/#if]>
                </div>
                <div class="item-one solab-s" style="float: left !important;">
                    <label class="lab-item">县级：</label>
                    <input type="checkbox" id="county" name="county" value="true" orgCapabilityEnum="2"  [#if '${risk.county }' == true ] checked="checked" [/#if]>
                </div>
                <div class="item-one solab-s" style="float: left !important;">
                    <label class="lab-item">视光/门诊：</label>
                    <input type="checkbox" id="outpatient" name="outpatient" value="true" orgCapabilityEnum="1"  [#if '${risk.outpatient }' == true ] checked="checked" [/#if]>
                </div>
            </div>
        </div>
    </div>
    <div class="auditFindTemplate">
        <h2 class="h2-title-a">
            <span class="s-title">审计发现模板</span>
        </h2>
        <hr class="mar-l10 mar-r10 mar-t0 mar-b20" style="border-color:#b2def4;margin-bottom: 0px !important;">
        <input type="button" class="btn btn-small btn-primary mar-5 so-template-add" style="margin: 5px 5px 5px 10px !important;" value="保存模板"/>
        <div class="clearfix mar-l0 mar-b10">
            <span class="fl">
                <label class="lab-inline w-60">模板内容：</label>
                <textarea class="txta txt-validate adaptiveTextarea" style="width:1024px" dataOptions="" noNull="" id="riskFindTemplateDetail"
                          name="riskFindTemplateDetail" cols="200" row="10" placeholder="请填写模板内容"></textarea>
            </span>
            <input type="hidden" id="riskFindTemplateId" name="riskFindTemplateId" value="" />
        </div>
        <div class="cont-grid-template" style="margin: 0px 0px 0px 11px !important;">
            <div id="gridBox-template"></div>
        </div>
    </div>
    <div class="scoreStandrd">
        <h2 class="h2-title-a">
            <span class="s-title">扣分标准</span>
        </h2>
        <hr class="mar-l10 mar-r10 mar-t0 mar-b20" style="border-color:#b2def4;margin-bottom: 0px !important;">
        <input type="button" class="btn btn-small btn-primary mar-5 so-riskscorestandard-add" style="margin: 5px 5px 5px 10px !important;" value="新增扣分标准"/>
        <div class="cont-grid-template" style="margin: 0px 0px 0px 11px !important;">
            <div id="gridBox-scoreStandrd"></div>
        </div>
    </div>
    <div class="queStandrd">
        <h2 class="h2-title-a">
            <span class="s-title">问题标准</span>
        </h2>
        <hr class="mar-l10 mar-r10 mar-t0 mar-b20" style="border-color:#b2def4;margin-bottom: 0px !important;">
        <input type="button" class="btn btn-small btn-primary mar-5 so-riskquestionstandard-add" style="margin: 5px 5px 5px 10px !important;" value="新增问题标准"/>
        <div class="cont-grid-template" style="margin: 0px 0px 0px 11px !important;">
            <div id="gridBox-queStandrd"></div>
        </div>
    </div>
</form>
<form id="editSelfRiskForm"  class="soform formA form-validate form-enter pad-t3 selfRiskForm">
    <h2 class="h2-title-a">
        <span class="s-title">自评手册</span>
    </h2>
    <hr class="mar-l10 mar-r10 mar-t0 mar-b20" style="border-color:#b2def4;margin-bottom: 0px !important;">
    <input type="button" class="btn btn-small btn-primary mar-5 so-selfRisk-pop" style="margin: 5px 0px 5px 10px !important;" value="新 增"/>
    <!-- <input type="button" class="btn btn-small btn-primary mar-5 so-selfRisk-add" style="margin: 5px 0px 5px 10px !important;" value="保 存"/> -->
    <input type="button" class="btn btn-small btn-primary mar-5 so-selfRisk-cancel hide" style="margin: 5px 0px 5px 10px !important;" value="取 消"/>
    <input type="button" class="btn btn-small btn-primary mar-5 so-selfRisk-unbang hide" style="margin: 5px 0px 5px 10px !important;" value="解 绑"/>
    <div class="selfRisk hide">
        <div class="row">
            <div class="p3">
                <div class="item-one solab-l">
                    <label class="lab-item">关联自评风险点Id：</label>
                    <input class="txt txt-validate" style="width: 58% !important;"  readonly="true" type="text" id="selfRiskid" name="selfRiskid" value="${selfRisk.selfRiskId!}" autocomplete="off" data-options=""/>
                    <img class="selfRisk-prompt" style="vertical-align: middle !important;margin-bottom: 3px;margin-left: 2px;" src="${base}/static/images/base/prompt.png" />
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-s">
                    <label class="lab-item">业务板块：</label>
                    <select class="drop drop-sl-v easyui-combobox  w-150" name="selfRiskBussinessType" id="selfRiskBussinessType"  data-options="valueField:'codeMasterTypeCode',textField:'codeMasterName',clearIcon:true,required:true"></select>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-n">
                    <label class="lab-item">自评风险编号：</label>
                    <input class="txt txt-validate" type="text" id="selfRiskCode" name="selfRiskCode" value="${selfRisk.selfRiskCode!}" autocomplete="off" data-options="required:true"/>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-s">
                    <label class="lab-item">风险点：</label>
                    <input class="txt txt-validate" type="text" id="selfRiskTitle" name="selfRiskTitle" value="${selfRisk.selfRiskTitle!}" autocomplete="off"  data-options="required:true" noNull="风险点必填"/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="p3">
                <div class="item-one solab-l">
                    <label class="lab-item">风险类别：</label>
                    <input class="txt txt-validate" type="text" id="selfRiskType" name="selfRiskType" value="${selfRisk.selfRiskType!}" autocomplete="off"  dataOptions="required:true" noNull="风险类别必填"/>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-s">
                    <label class="lab-item">一级流程：</label>
                    <input class="txt txt-validate" type="text" id="selfRiskFirstLevel" name="selfRiskFirstLevel" value="${selfRisk.selfRiskFirstLevel!}" autocomplete="off" data-options="required:true"/>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-n">
                    <label class="lab-item">二级流程：</label>
                    <input class="txt txt-validate" type="text" id="selfRiskSecondLevel" name="selfRiskSecondLevel" value="${selfRisk.selfRiskSecondLevel!}" autocomplete="off" data-options="required:true"/>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-s">
                    <label class="lab-item">分值：</label>
                    <input class="txt txt-validate easyui-numberbox" type="text" id="selfRiskScore" name="selfRiskScore" value="${selfRisk.selfRiskScore!}" autocomplete="off" data-options="min:0,precision:0,required:true"/>
                </div>
            </div>
        </div>
        <div class="clearfix mar-l20 mar-b10">
            <span class="fl">
                <label class="lab-inline w-60">风险描述：</label>
                <textarea class="txta txt-validate adaptiveTextarea" style="width:1000px" dataOptions="required:true" noNull="风险描述必填" id="selfRiskDesc"
                          name="selfRiskDesc" cols="200" row="10" placeholder="请填写风险描述">${selfRisk.selfRiskDesc!}</textarea>
            </span>
        </div>
        <div class="clearfix mar-l20 mar-b10">
            <span class="fl">
                <label class="lab-inline w-60">评估依据：</label>
                <textarea class="txta txt-validate adaptiveTextarea" style="width:1000px" dataOptions="required:true" noNull="评估依据必填" id="selfRiskEvalucationBasis"
                          name="selfRiskEvalucationBasis" cols="300" row="20" placeholder="请填写评估依据">${selfRisk.selfRiskEvalucationBasis!}</textarea>
            </span>
        </div>
        <div class="clearfix mar-l20 mar-b10">
            <span class="fl">
                <label class="lab-inline w-60">自评方法：</label>
                <textarea class="txta txt-validate adaptiveTextarea" style="width:1000px" dataOptions="required:true" noNull="自评方法必填" id="selfRiskMethod"
                          name="selfRiskMethod" cols="200" row="10" placeholder="请填写自评方法">${selfRisk.selfRiskMethod!}</textarea>
            </span>
        </div>
        <div class="clearfix mar-l20 mar-b10">
            <span class="fl">
                <label class="lab-inline w-60">风险应对指引：</label>
                <textarea class="txta txt-validate adaptiveTextarea" style="width:1000px" dataOptions="required:true" noNull="风险应对指引必填" id="selfRiskGuide"
                          name="selfRiskGuide" cols="300" row="20" placeholder="请填写风险应对指引">${selfRisk.selfRiskGuide!}</textarea>
            </span>
        </div>
        <div class="clearfix mar-l20 mar-b10">
            <span class="fl">
                <label class="lab-inline w-60">扣分标准：</label>
                <textarea class="txta txt-validate adaptiveTextarea" style="width:1000px" dataOptions="required:true" noNull="扣分标准必填" id="selfRiskDeductCriterion"
                          name="selfRiskDeductCriterion" cols="200" row="10" placeholder="请填写扣分标准">${selfRisk.selfRiskDeductCriterion!}</textarea>
            </span>
        </div>
        <div class="clearfix mar-l20 mar-b30 forHospTypeDiv" style="margin-left: 30px !important;">
            <div class="easyui-panel" title="体量" style="width:1068px;padding:10px 10px 10px 20px;">
                <div class="item-one solab-s" style="float: left !important;">
                    <label class="lab-item">医院：</label>
                    <input type="checkbox" id="hospital" name="hospital" value="true" forHospTypeEnum="1" [#if '${selfRisk.hospital }' == true ] checked="checked" [/#if]>
                </div>
                <div class="item-one solab-s" style="float: left !important;">
                    <label class="lab-item">眼科门诊：</label>
                    <input type="checkbox" id="outPatient" name="outPatient" value="true" forHospTypeEnum="2" [#if '${selfRisk.outPatient }' == true ] checked="checked" [/#if]>
                </div>
                <div class="item-one solab-s" style="float: left !important;">
                    <label class="lab-item" style="width: 72px !important;">眼视光诊所：</label>
                    <input type="checkbox" id="eyeOpticClinic" name="eyeOpticClinic" value="true" forHospTypeEnum="4" [#if '${selfRisk.eyeOpticClinic }' == true ] checked="checked" [/#if]>
                </div>
                <div class="item-one solab-s" style="float: left !important;">
                    <label class="lab-item">眼镜店：</label>
                    <input type="checkbox" id="spectacleStore" name="spectacleStore" value="true" forHospTypeEnum="8" [#if '${selfRisk.spectacleStore }' == true ] checked="checked" [/#if]>
                </div>
            </div>
        </div>
    </div>
    <p class="row-btn pad-t5">
        <input type="button" class="btn btn-primary btn-risk-add" name="btnRiskAdd" value="保 存" />
        <input type="button" class="btn btn-cancel-cus" name="btnCancel" value="返 回" />
    </p>
    <p class="row-btn pad-t5" style="padding-top: 20px !important;"></p>
</form>

<script id="editRiskScoreStandard" type="text/html">
    <input type="text" style="position: absolute; left: -1000px;" id="hide-input"/>
    <form id="mainRiskScoreStandardForm" class="soform formA form-validate pad-t20 riskScoreStandardForm" method="post"
          action="${base}/ui/aams/riskscorestandard/save">
        <input class="txt hide" type="text" id="riskScoreStandardId" name="riskScoreStandardId" value=""/>
        <input class="txt hide" type="text" id="riskScoreStandardManualId" name="riskScoreStandardManualId" value="${risk.riskId!}"/>
        <input class="txt hide" type="text" id="riskScoreStandardType" name="riskScoreStandardType" value="1"/>
        <input class="txt hide" type="text" id="riskScoreStandardCode" name="riskScoreStandardCode" value=""/>
        <div class="row">
            <div class="p4">
                [#--<div class="item-one">
                    <label class="lab-item">分值：</label>
                    <input class="txt txt-validate easyui-numberbox" type="text" id="riskScoreStandardScore" name="riskScoreStandardScore" validType="number['必须为数字']" data-options="min:0,precision:0" value=""
                           autocomplete="off" data-options=""/>
                </div>--]
                <div class="item-one solab-c">
                    <label class="lab-item solab-c">分值：</label>
                    <input type="text" class="txt inline easyui-numberbox required" id="riskScoreStandardScore" data-options="min:0,precision:0,required:true" name="riskScoreStandardScore"  validType="number['必须为数字']">
                </div>
            </div>
            <div class="p8">
                <div class="item-one solab-c">
                    <label class="lab-item solab-c">扣分描述：</label>
                    <input class="txt txt-validate required " type="text" id="riskScoreStandardDesc" name="riskScoreStandardDesc" noNull="扣分描述必填" autocomplete="off"/>
                </div>
            </div>
        </div>

        <p class="row-btn pad-t5">
            <input type="button" class="btn btn-primary btn-save" name="btnSubmit" value="保 存"/>
            <input type="button" class="btn btn-cancel-cus-riskscorestandard" name="btnCancel" value="取 消"/>
        </p>
    </form>
</script>

<script id="editRiskQuestionStandard" type="text/html">
    <input type="text" style="position: absolute; left: -1000px;" id="hide-input"/>
    <form id="mainRiskQuestionStandardForm" class="soform formA form-validate pad-t20 riskQuestionStandardForm" method="post"
          action="${base}/ui/aams/riskquestionstandard/save">
        <input class="txt hide" type="text" id="riskQuestionStandardId" name="riskQuestionStandardId" value=""/>
        <input class="txt hide" type="text" id="riskId" name="riskId" value="${risk.riskId!}"/>
        <input class="txt hide" type="text" id="riskQuestionStandardCode" name="riskQuestionStandardCode" value=""/>
        <input class="txt hide" type="text" id="riskQuestionStandardIsDlt" name="riskQuestionStandardIsDlt" value=""/>
        <input class="txt hide" type="text" id="riskQuestionStandardValue" name="riskQuestionStandardValue" value=""/>
        <input class="txt hide" type="text" id="riskQuestionStandardRemark" name="riskQuestionStandardRemark" value=""/>
        <div class="row">
            <div class="p8">
                <div class="item-one solab-c">
                    <label class="lab-item solab-c">描述：</label>
                    <input class="txt txt-validate required " type="text" id="riskQuestionStandardDesc" name="riskQuestionStandardDesc" noNull="扣分描述必填" autocomplete="off"/>
                </div>
            </div>
        </div>

        <p class="row-btn pad-t5">
            <input type="button" class="btn btn-primary btn-save" name="btnSubmit" value="保 存"/>
            <input type="button" class="btn btn-cancel-cus-riskquestionstandard" name="btnCancel" value="取 消"/>
        </p>
    </form>
</script>

</body>

[#include "/WEB-INF/views/common/include_js.ftl"]

<script type="text/javascript">

    requirejs(['pub'], function () {
        $(document).ready(function() {
            // 下拉表初始化
            $ajax.postSync('${base}/ui/aams/codeMaster/getCodeMasterByType?riskBussinessType=RiskBussinessType',null,false,false).done(function (rst) {
                $('#riskBussinessType').combobox('loadData', rst);          // 业务类别
            });
            $ajax.postSync('${base}/ui/aams/codeMaster/getCodeMasterByType?riskBussinessType=RiskLevel',null,false,false).done(function (rst) {
                $('#riskLevel').combobox('loadData', rst);                   // 风险级别
            });
            $ajax.postSync('${base}/ui/aams/codeMaster/getCodeMasterByType?riskBussinessType=RiskCategory',null,false,false).done(function (rst) {
                $('#riskCategory').combobox('loadData', rst);               // 内控点类别
            });
            $ajax.postSync('${base}/ui/aams/codeMaster/getCodeMasterByType?riskBussinessType=RiskRectifyType',null,false,false).done(function (rst) {
                $('#riskRectifyType').combobox('loadData', rst);            // 整改类型
            });
            $("#riskBussinessType").combobox('setValue', '${risk.riskBussinessType!}')
            $("#riskLevel").combobox('setValue', '${risk.riskLevel!}');
            $("#riskCategory").combobox('setValue', '${risk.riskCategory!}');
            $("#riskRectifyType").combobox('setValue', '${risk.riskRectifyType!}');
            if(eval('${risk.riskRemote }') == true){
                $('#digitalModelDiv').show();
                $('#riskDigitalModel').prop('disabled', false);
                $('#riskDigitalModel').validatebox('enableValidation');
            }else{
                $('#digitalModelDiv').hide();
                $('#riskDigitalModel').prop('disabled', true);
                $('#riskDigitalModel').validatebox('disableValidation');
            }
            if(eval('${risk.riskIsPost }') == true){
                $('#riskIsRandomDiv').show();
                $('#riskIsRandom').prop('disabled', false);
            }
            if('${selfRisk}'){
                $('.so-selfRisk-pop').addClass("hide");
                if($('.so-selfRisk-cancel').hasClass("hide")){
                    $('.so-selfRisk-cancel').removeClass("hide")
                };
                if($('.so-selfRisk-unbang').hasClass("hide")){
                    $('.so-selfRisk-unbang').removeClass("hide")
                };
                if($('.selfRisk').hasClass("hide")){
                    $('.selfRisk').removeClass("hide")
                };
                $ajax.postSync('${base}/ui/aams/codeMaster/getCodeMasterByType?riskBussinessType=SelfEvaluationBussinessType',null,false,false).done(function (rst) {
                    $('#selfRiskBussinessType').combobox('loadData', rst);      // 自评业务类别
                });
                $("#selfRiskBussinessType").combobox('setValue', '${selfRisk.selfRiskBussinessType!}');
            }
        });

        // 数字化结论点击事件
        $('#riskRemote').click(function() {
            if ($(this).is(':checked')) {
                $('#digitalModelDiv').show();
                $('#riskDigitalModel').prop('disabled', false);
                $('#riskDigitalModel').validatebox('enableValidation');
            } else {
                $('#digitalModelDiv').hide();
                $('#riskDigitalModel').prop('disabled', true);
                $('#riskDigitalModel').validatebox('disableValidation');
            }
        });
        // 是否合规项目点击事件
        $('#riskIsPost').click(function() {
            if ($(this).is(':checked')) {
                $('#riskIsRandomDiv').show();
                $('#riskIsRandom').prop('disabled', false);
            } else {
                $('#riskIsRandomDiv').hide();
                $('#riskIsRandom').prop('disabled', true);
            }
        });
        // 默认勾选 class="easyui-panel" 内的所有复选框
        //$('.easyui-panel input[type="checkbox"]').prop('checked', true);
        // 扣分标准事件
        $('#riskEmergencyRef').on('input', function() {
            $('#editSelfRiskForm #selfRiskDeductCriterion').val($(this).val());
        });
        // 制度支持事件
        $('#riskInstitution').on('input', function() {
            $('#editSelfRiskForm #selfRiskEvalucationBasis').val($(this).val());
        });

        // 保存审计手册和自评手册
        $('.btn-risk-add').click(function () {
            let riskFormCheck = $('#editManualForm').form('validate');
            let selfRiskFormCheck = true;
            let boolSelfRiskForm = !$('.selfRisk').hasClass("hide");
            if(boolSelfRiskForm){
                selfRiskFormCheck = $('#editSelfRiskForm').form('validate');
            }
            if (riskFormCheck && selfRiskFormCheck){
                if($('#riskRemote').is(':checked')){
                    if($.isEmptyObject($('#riskDigitalModel').val())){
                        $pop.msg('数字化结论勾选时,数字化模型必须填写。');
                        return false;
                    }
                }
                if($('#riskIsQueDescStd').is(':checked')){
                    let queRowsData = $('#gridBox-queStandrd').datagrid('getRows');
                    if(!queRowsData.length>0){
                        $pop.msg('勾选了"是否问题描述标准化"时，需要新增标准问题。');
                        return false;
                    }
                }
                var data = $(".manualRiskForm").sovals();
                // 数据处理
                var total = 0;
                // 找到特定的div，并选择其中所有的checkbox
                $(".clearfix.mar-l20.mar-b30.orgCapabilityDiv input[type='checkbox']").each(function() {
                    // 检查该复选框是否被选中
                    if ($(this).is(':checked')) {
                        // 获取orgCapabilityEnum属性值并转换为整数
                        let orgCapabilityEnumValue = parseInt($(this).attr("orgCapabilityEnum"), 10);
                        // 累加orgCapabilityEnumValue
                        total += orgCapabilityEnumValue;
                    }
                });
                if(!total>0){
                    $pop.msg('体量必须选择。');
                    return false;
                }
                data.riskCapability=total;
                let formData = {"risk": data};
                // 自评手册数据处理
                if(boolSelfRiskForm){
                    let selfRiskData =  $(".selfRiskForm").sovals();
                    var selfRiskTotal = 0;
                    // 找到特定的div，并选择其中所有的checkbox
                    $(".clearfix.mar-l20.mar-b30.forHospTypeDiv input[type='checkbox']").each(function() {
                        // 检查该复选框是否被选中
                        if ($(this).is(':checked')) {
                            // 获取orgCapabilityEnum属性值并转换为整数
                            let forHospTypeEnum = parseInt($(this).attr("forHospTypeEnum"), 10);
                            // 累加orgCapabilityEnumValue
                            selfRiskTotal += forHospTypeEnum;
                        }
                    });
                    if(!selfRiskTotal>0){
                        $pop.msg('自评手册体量必须选择。');
                        return false;
                    }
                    selfRiskData.selfRiskForHospType=selfRiskTotal;
                    let selfRiskid = $('#selfRiskid').val();
                    let selfRiskBussinessType = $('#selfRiskBussinessType').combobox('getValue');
                    let selfRiskCode = $('#selfRiskCode').val();
                    let isUniqueSelfRisk = false;
                    $ajax.postSync('${base}/ui/aams/manualofaudit/getUniqueSelfRiskCode?selfRiskId='+ selfRiskid
                        + '&selfRiskBussinessType=' + selfRiskBussinessType
                        + '&selfRiskCode=' + selfRiskCode,null,false,false).done(function (rst){
                        if(rst > 0){
                            isUniqueSelfRisk=true;
                        }
                    });
                    if(isUniqueSelfRisk){
                        $pop.msg("自评风险编号在板块已存在。");
                        return false;
                    }
                    formData = {"risk": data,"selfRisk":selfRiskData};
                }
                let current = $(document).scrollTop();
                $ajax.post({
                    url: "${base}/ui/aams/manualofaudit/editSaveManual",
                    data: formData,
                    jsonData: true,
                    tip: "是否保存风险点？",
                    calltip: true,
                    success: function (rst) {
                        window.location.href='${base}/ui/aams/manualofaudit/editManual?riskId=' + rst.riskId;
                    },
                    cancelback: function (rst) {
                        //$pop.closePop();
                        // 页面动画滚动效果
                        $("body, html").stop().animate({
                            scrollTop: current
                        });
                    },
                })
            }
        });

        // 审计发现模板Grid初始化
        $grid.newGrid("#gridBox-template", {
            pagination: false,
            fitColumns: false,
            columns: [[
                {title: "操作", field: "op", width: 70, formatter: function (v, row, index) {
                        let opStr = '';
                        opStr = '<span class="s-op s-op-edit icon-edit" title="修改" rel="' + index + '"></span>';
                        opStr = opStr + '&nbsp;&nbsp;<span class="s-op s-op-del icon-del" title="删除" rel="' + index + '"></span>';
                        return opStr;
                    }
                },
                {title: 'riskfindtemplateid', field: 'riskfindtemplateid', hidden: true,width: 150},
                {title: 'riskid', field: 'riskid', hidden: true,width: 150},
                {title: '模板内容', field: 'riskfindtemplatedetail', width: 1000},
            ]],
            rowStyler: function (index, row) {
            },
            queryParams: {},
            onBeforeLoad: function (param) {
                return true;
            },
            onLoadSuccess: function (data) {
                $('.s-op-edit').click(function () {
                    var ix = $(this).attr('rel');
                    var row = data.rows[ix];
                    $('#riskFindTemplateDetail').val(row.riskfindtemplatedetail);
                    $('#riskFindTemplateId').val('');
                    $('#riskFindTemplateId').val(row.riskfindtemplateid);
                });

                $('.cont-grid-template').on('click', '.s-op-del', function () {
                    var index = $(this).attr("rel");
                    var rowData = $('#gridBox-template').datagrid("getRows")[index];
                    $pop.confirm('是否删除该记录？', function (r) {//确定
                        var riskTemplate = {riskFindTemplateIsdlt : 1,riskFindTemplateId:rowData.riskfindtemplateid,riskId:${risk.riskId!},riskFindTemplateDetail:rowData.riskfindtemplatedetail};
                        if(rowData.riskfindtemplateid){
                            $ajax.post('${base}/ui/aams/riskTemplate/save',JSON.stringify(riskTemplate),false, true).done(function (rst) {$('#gridBox-template').datagrid('reload');});
                        }
                        return true;
                    }, function () {//取消
                        return true;
                    });
                });
            },
            url: '${base}/ui/aams/riskTemplate/getRiskTemplatesByRiskId?riskId=' + ${risk.riskId!},
            height: 200,
            width: '80%',
            offset: -5
        });

        // 扣分标准Grid初始化
        $grid.newGrid("#gridBox-scoreStandrd", {
            pagination: false,
            fitColumns: false,
            columns: [[
                {title: "操作", field: "op", width: 70, formatter: function (v, row, index) {
                        let opStr = '';
                        opStr = '<span class="s-op s-op-score-edit icon-edit" title="修改" rel="' + row.riskScoreStandardId + '" relIndex="' + index +'"></span>';
                        opStr = opStr + '&nbsp;&nbsp;<span class="s-op s-op-score-del icon-del" style="color: #f16666;" title="删除" rel="' + row.riskScoreStandardId + '"></span>';
                        return opStr;
                    }
                },
                {title: 'riskScoreStandardId', field: 'riskScoreStandardId', hidden: true,width: 150},
                {title: 'riskScoreStandardManualId', field: 'riskScoreStandardManualId', hidden: true,width: 150},
                {title: 'riskScoreStandardType', field: 'riskScoreStandardType', hidden: true,width: 150},
                {title: 'riskScoreStandardIsDlt', field: 'riskScoreStandardIsDlt', hidden: true,width: 150},
                {title: 'riskScoreStandardCode', field: 'riskScoreStandardCode', hidden: true,width: 150},
                {title: '分值', field: 'riskScoreStandardScore', width: 100},
                {title: '扣分描述', field: 'riskScoreStandardDesc', width: 1000},
            ]],
            rowStyler: function (index, row) {
            },
            queryParams: {},
            onBeforeLoad: function (param) {
                return true;
            },
            onLoadSuccess: function (data) {
                $('.s-op-score-edit').click(function () {
                    var riskScoreStandardId = $(this).attr('rel');
                    var idx = $(this).attr('relIndex');
                    var rowData = $("#gridBox-scoreStandrd").datagrid("getRows")[idx];
                    let current = $(document).scrollTop();
                    var p = $pop.popTemForm({
                        title: "编辑扣分标准",
                        temId: 'editRiskScoreStandard',
                        area: ['810px', '300px'],
                        temData: rowData,
                        data: rowData,
                        zIndex: 2,
                        grid: '#gridBox',
                        onPop: function ($p) {
                            $('#riskScoreStandardType').val('1');
                            $('#riskScoreStandardId').val(riskScoreStandardId);
                            $('.btn-save').click(function () {
                                if ($('#mainRiskScoreStandardForm').form('validate')) {
                                    var data = $(".riskScoreStandardForm").sovals();
                                    $ajax.post({
                                        url: "${base}/ui/aams/riskscorestandard/save",
                                        data: data,
                                        tip: "是否保存扣分标准？",
                                        calltip: true,
                                        success: function (rst) {
                                            $pop.tips(rst.msg);
                                            setTimeout(function () {
                                                $pop.close(p);
                                                //$grid.load('#gridBox-scoreStandrd');
                                                $("#gridBox-scoreStandrd").datagrid("reload");
                                                // 页面动画滚动效果
                                                $("body, html").stop().animate({
                                                    scrollTop: current
                                                });
                                            }, 100);
                                        },
                                        cancelback: function (rst) {},
                                    })
                                }
                            });
                            $(".btn-cancel-cus-riskscorestandard").click(function () {
                                $pop.close(p);
                            });
                        }
                    });
                });

                $('.s-op-score-del').click(function () {
                    let current = $(document).scrollTop();
                    var riskScoreStandardId = $(this).attr('rel');
                    $ajax.post({
                        url: '${base}/ui/aams/riskscorestandard/delete?id='+riskScoreStandardId,
                        jsonData: true,
                        tip: '确定删除吗？',
                        callback: function (rst) {
                            $grid.reload('#gridBox-scoreStandrd');
                        },
                        success: function (rst) {
                            // 页面动画滚动效果
                            $("body, html").stop().animate({
                                scrollTop: current
                            });
                        },
                        cancelback: function (rst) {
                            // 页面动画滚动效果
                            $("body, html").stop().animate({
                                scrollTop: current
                            });
                        },
                    });
                });
            },
            url: '${base}/ui/aams/riskscorestandard/queryRiskScoreStandard?riskScoreStandardManualId=' + ${risk.riskId!} + '&riskScoreStandardType=1&riskScoreStandardIsDlt=0',
            height: 200,
            width: '80%',
            offset: -5
        });

        // 问题标准Grid初始化
        $grid.newGrid("#gridBox-queStandrd", {
            pagination: false,
            fitColumns: false,
            columns: [[
                {title: "操作", field: "op", width: 70, formatter: function (v, row, index) {
                        let opStr = '';
                        opStr = '<span class="s-op s-op-question-edit icon-edit" title="修改" rel="' + row.riskQuestionStandardId + '" relIndex="' + index +'"></span>';
                        opStr = opStr + '&nbsp;&nbsp;<span class="s-op s-op-question-del icon-del" style="color: #f16666;" title="删除" rel="' + row.riskQuestionStandardId + '"></span>';
                        return opStr;
                    }
                },
                {title: 'riskQuestionStandardId', field: 'riskQuestionStandardId', hidden: true,width: 150},
                {title: 'riskId', field: 'riskId', hidden: true,width: 150},
                {title: 'riskQuestionStandardCode', field: 'riskQuestionStandardCode', hidden: true,width: 150},
                {title: 'riskQuestionStandardIsDlt', field: 'riskQuestionStandardIsDlt', hidden: true,width: 150},
                {title: 'riskQuestionStandardValue', field: 'riskQuestionStandardValue', hidden: true,width: 150},
                {title: 'riskQuestionStandardRemark', field: 'riskQuestionStandardRemark', hidden: true,width: 150},
                {title: '描述', field: 'riskQuestionStandardDesc', width: 1000},
            ]],
            rowStyler: function (index, row) {
            },
            queryParams: {},
            onBeforeLoad: function (param) {
                return true;
            },
            onLoadSuccess: function (data) {
                $('.s-op-question-edit').click(function () {
                    var riskQuestionStandardId = $(this).attr('rel');
                    var idx = $(this).attr('relIndex');
                    var rowData = $("#gridBox-queStandrd").datagrid("getRows")[idx];
                    let current = $(document).scrollTop();
                    var p = $pop.popTemForm({
                        title: "编辑问题标准",
                        temId: 'editRiskQuestionStandard',
                        area: ['810px', '300px'],
                        temData: rowData,
                        data: rowData,
                        zIndex: 2,
                        grid: '#gridBox',
                        onPop: function ($p) {
                            $('#riskQuestionStandardId').val(riskQuestionStandardId);
                            $('.btn-save').click(function () {
                                if ($('#mainRiskQuestionStandardForm').form('validate')) {
                                    var data = $(".riskQuestionStandardForm").sovals();
                                    $ajax.post({
                                        url: "${base}/ui/aams/riskquestionstandard/save",
                                        data: data,
                                        tip: "是否保存问题标准？",
                                        calltip: true,
                                        success: function (rst) {
                                            $pop.tips(rst.msg);
                                            setTimeout(function () {
                                                $pop.close(p);
                                                //$grid.load('#gridBox-scoreStandrd');
                                                $("#gridBox-queStandrd").datagrid("reload");
                                                // 页面动画滚动效果
                                                $("body, html").stop().animate({
                                                    scrollTop: current
                                                });
                                            }, 100);
                                        },
                                        cancelback: function (rst) {},
                                    })
                                }
                            });
                            $(".btn-cancel-cus-riskquestionstandard").click(function () {
                                $pop.close(p);
                            });
                        }
                    });
                });

                $('.s-op-question-del').click(function () {
                    let current = $(document).scrollTop();
                    var riskQuestionStandardId = $(this).attr('rel');
                    $ajax.post({
                        url: '${base}/ui/aams/riskquestionstandard/delete?id='+riskQuestionStandardId,
                        jsonData: true,
                        tip: '确定删除吗？',
                        callback: function (rst) {
                            $grid.reload('#gridBox-queStandrd');
                        },
                        success: function (rst) {
                            // 页面动画滚动效果
                            $("body, html").stop().animate({
                                scrollTop: current
                            });
                        },
                        cancelback: function (rst) {
                            // 页面动画滚动效果
                            $("body, html").stop().animate({
                                scrollTop: current
                            });
                        },
                    });
                });
            },
            url: '${base}/ui/aams/riskquestionstandard/queryRiskQuestionStandard?riskId=' + ${risk.riskId!} + '&riskQuestionStandardIsDlt=0',
            height: 200,
            width: '80%',
            offset: -5
        });

        $(".btn-cancel-cus").click(function () {
            $pop.closePop();
        });

        // 新增自评手册按钮事件
        $('.so-selfRisk-pop').click(function () {
            if(!$(this).hasClass("hide")){
                $(this).addClass("hide")
            };
            if($('.so-selfRisk-cancel').hasClass("hide")){
                $('.so-selfRisk-cancel').removeClass("hide")
            };
            if($('.so-selfRisk-unbang').hasClass("hide")){
                $('.so-selfRisk-unbang').removeClass("hide")
            };
            if($('.selfRisk').hasClass("hide")){
                $('.selfRisk').removeClass("hide")
            };
            $ajax.postSync('${base}/ui/aams/codeMaster/getCodeMasterByType?riskBussinessType=SelfEvaluationBussinessType',null,false,false).done(function (rst) {
                $('#selfRiskBussinessType').combobox('loadData', rst);      // 自评业务类别
            });
        });

        // 取消自评手册按钮
        $('.so-selfRisk-cancel').click(function () {
            if(!$(this).hasClass("hide")){
                $(this).addClass("hide")
            };
            if($('.so-selfRisk-pop').hasClass("hide")){
                $('.so-selfRisk-pop').removeClass("hide")
            };
            if(!$('.so-selfRisk-unbang').hasClass("hide")){
                $('.so-selfRisk-unbang').addClass("hide")
            };
            if(!$('.selfRisk').hasClass("hide")){
                $('.selfRisk').addClass("hide")
            };
        });

        // 选择自评手册弹框
        $('.selfRisk-prompt').click(function() {
            $pop.iframePop({
                title: "选择自评风险点",//标题
                content: '${base}/ui/aams/manualofaudit/selfRiskList',//请求地址
                area:  ['860px', '550px'],//窗口大小
                // postData : {mainId:row.id},//往子页面传值
                end : function(iframeSendData){
                    //关闭执行函数，子页面可通过 $pop.closePop 返回参数
                    //console.log($store.get('selfRisk'));
                    // 赋值操作
                    if($store.get('selfRisk') != null){
                        $ajax.postSync('${base}/ui/aams/manualofaudit/getCountBySelfRiskId?selfRiskId=' + $store.get('selfRisk').selfRiskId,null,false,false).done(function (rst) {
                            if(rst > 0){
                                $pop.msg("该手册已被绑定。请重新选择！");
                            }else{
                                console.log($store.get('selfRisk'));
                                $('#selfRiskid').val($store.get('selfRisk').selfRiskId);
                                $('#selfRiskBussinessType').combobox('setValue', $store.get('selfRisk').selfRiskBussinessType);
                                $('#selfRiskCode').val($store.get('selfRisk').selfRiskCode);
                                $('#selfRiskTitle').val($store.get('selfRisk').selfRiskTitle);
                                $('#selfRiskType').val($store.get('selfRisk').selfRiskType);
                                $('#selfRiskFirstLevel').val($store.get('selfRisk').selfRiskFirstLevel);
                                $('#selfRiskSecondLevel').val($store.get('selfRisk').selfRiskSecondLevel);
                                $('#selfRiskScore').numberbox('setValue',$store.get('selfRisk').selfRiskScore);
                                $('#selfRiskDesc').val($store.get('selfRisk').selfRiskDesc);
                                $('#selfRiskEvalucationBasis').val($store.get('selfRisk').selfRiskEvalucationBasis);
                                $('#selfRiskMethod').val($store.get('selfRisk').selfRiskMethod);
                                $('#selfRiskGuide').val($store.get('selfRisk').selfRiskGuide);
                                $('#selfRiskDeductCriterion').val($store.get('selfRisk').selfRiskDeductCriterion);
                                var selfRiskForHospType = $store.get('selfRisk').selfRiskForHospType;
                                // 找到特定的div，并选择其中所有的checkbox
                                $(".clearfix.mar-l20.mar-b30.forHospTypeDiv input[type='checkbox']").each(function() {
                                    // 获取forHospTypeEnum属性值
                                    var forHospTypeEnumValue = parseInt($(this).attr("forHospTypeEnum"), 10);
                                    var selfRiskForHospTypeInt =  parseInt(selfRiskForHospType, 10);
                                    // 进行与运算并判断
                                    if ((forHospTypeEnumValue & selfRiskForHospTypeInt) === forHospTypeEnumValue) {
                                        // 如果满足条件，则选中复选框
                                        $(this).prop("checked", true);
                                    }
                                });
                            }
                        });
                    }
                    $store.clear();
                },
                sureback : function (iframeSendData){
                    //表单提交| 或成功 执行函数，子页面可通过 $pop.closePop 返回参数
                }
            },'');
        });

        // 保存审计发现模板
        $('.so-template-add').click(function () {
            if($.trim('${risk.riskId!}') == '' || '${risk.riskId!}'=='undefined' || '${risk.riskId!}'==null){
                $pop.msg('审计手册Id不能为空，请先保存审计手册！');
                return false;
            }
            if( $.trim($('#riskFindTemplateDetail').val()) === '' || $('#riskFindTemplateDetail').val() === undefined || $('#riskFindTemplateDetail').val() === null){
                $pop.msg('模板内容不能为空！');
                return false;
            }

            var riskTemplate = {riskFindTemplateDetail : $('#riskFindTemplateDetail').val(),riskFindTemplateId:$('#riskFindTemplateId').val(),riskId:${risk.riskId!}};
            $ajax.post({
                url: '${base}/ui/aams/riskTemplate/save',
                data: riskTemplate,
                tip: '你确定提交吗？',
                jsonData : true,//是否采用jsonData格式提交
                sync : false,//是否同步方式提交
                type : 'post',//采用post方式提交
                loadingMask : true,//进行异步请求中，是否显示mask
                calltip : true,//提交成功后显示请求成功信息
                success: function (rst) {
                    $('#riskFindTemplateDetail').val('');
                    $('#riskFindTemplateId').val('');
                    $('#gridBox-template').datagrid('reload');
                },//请求成功后，code===200或者201返回事件
                callback : function(rst){},//请求成功后返回事件
                cancelback : function(){},//确认框点取消返回事件
                errback : function(req, textStatus, errorThrown){
                    //$pop.alert('保存失败111！');
                }//出现错误时返回事件
            });
        });

        // 新增扣分标准
        $(".so-riskscorestandard-add").click(function ($e) {
            var pRiskScoreStandard = $pop.popTemForm({
                title: "新增扣分标准",
                temId: 'editRiskScoreStandard',
                area: ['810px', '300px'],
                temData: {},
                zIndex: 2,
                grid: '#gridBox-scoreStandrd',
                onPop: function ($p) {
                    let current = $(document).scrollTop();
                    $('#riskScoreStandardType').val('1');
                    $('.btn-save').click(function () {
                        if ($('#mainRiskScoreStandardForm').form('validate')) {
                            var data = $(".riskScoreStandardForm").sovals();
                            $ajax.post({
                                url: "${base}/ui/aams/riskscorestandard/save",
                                data: data,
                                tip: "是否保存扣分标准？",
                                calltip: true,
                                success: function (rst) {
                                    $pop.tips(rst.msg);
                                    setTimeout(function () {
                                        $pop.close(pRiskScoreStandard);
                                        $("#gridBox-scoreStandrd").datagrid("reload");
                                        // 页面动画滚动效果
                                        $("body, html").stop().animate({
                                            scrollTop: current
                                        });
                                    }, 100);
                                },
                                cancelback: function (rst) {},
                            })
                        }
                    });
                    $(".btn-cancel-cus-riskscorestandard").click(function () {
                        $pop.close(pRiskScoreStandard);
                    });
                },
                end: function () {

                },
                beforeSubmit: function (opt, $form, formData) {
                    return true;
                }
            });
        });

        // 新增问题标准
        $(".so-riskquestionstandard-add").click(function ($e) {
            var pRiskQuestionStandard = $pop.popTemForm({
                title: "新增扣分标准",
                temId: 'editRiskQuestionStandard',
                area: ['810px', '300px'],
                temData: {},
                zIndex: 2,
                grid: '#gridBox-queStandrd',
                onPop: function ($p) {
                    let current = $(document).scrollTop();
                    $('.btn-save').click(function () {
                        if ($('#mainRiskQuestionStandardForm').form('validate')) {
                            var data = $(".riskQuestionStandardForm").sovals();
                            $ajax.post({
                                url: "${base}/ui/aams/riskquestionstandard/save",
                                data: data,
                                tip: "是否保存问题标准？",
                                calltip: true,
                                success: function (rst) {
                                    $pop.tips(rst.msg);
                                    setTimeout(function () {
                                        $pop.close(pRiskQuestionStandard);
                                        $("#gridBox-queStandrd").datagrid("reload");
                                        // 页面动画滚动效果
                                        $("body, html").stop().animate({
                                            scrollTop: current
                                        });
                                    }, 100);
                                },
                                cancelback: function (rst) {},
                            })
                        }
                    });
                    $(".btn-cancel-cus-riskquestionstandard").click(function () {
                        $pop.close(pRiskQuestionStandard);
                    });
                },
                end: function () {

                },
                beforeSubmit: function (opt, $form, formData) {
                    return true;
                }
            });
        });

        // 自评解绑事件
        $('.so-selfRisk-unbang').click(function() {
            if($.isEmptyObject($('#selfRiskid').val())){
                $pop.msg('未绑定自评ID！');
                return false;
            }
            let riskId = '${risk.riskId!}';
            $pop.confirm('您确认想要解绑吗？',function(r){
                if (r){
                    $ajax.postSync('${base}/ui/aams/manualofaudit/unbindBangSelfRisk?riskId='+riskId,null,false,false).done(function (rst) {
                        if (rst.code === '200') {
                            window.location.href='${base}/ui/aams/manualofaudit/editManual?riskId=' + riskId;
                        }
                    });
                }
            });
        });

    });

</script>


