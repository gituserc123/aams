<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <meta http-equiv="X-UA-Compatible" content="IE=9"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>新增风险点 - 爱尔医院</title>
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
<form id="addManualForm"  class="soform formA form-validate form-enter pad-t3 manualRiskForm">
    <input class="txt hide" type="text" id="id" name="id" value=""/>
    <input type="hidden" id="hospId" name="hospId" value="" />
    <input type="hidden" id="instId" name="instId" value="" />
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
                    <input class="txt txt-validate required" type="text" id="riskCode" name="riskCode" value="" autocomplete="off" data-options="required:true"/>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-s">
                    <label class="lab-item">审计项目：</label>
                    <input class="txt txt-validate required" type="text" id="riskProject" name="riskProject" value="" autocomplete="off" data-options="required:true"/>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-s">
                    <label class="lab-item">风险类别：</label>
                    <input class="txt txt-validate" type="text" id="riskType" name="riskType" value="" autocomplete="off"  dataOptions="required:true" noNull="风险类别必填"/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="p3">
                <div class="item-one solab-s">
                    <label class="lab-item">内控点：</label>
                    <input class="txt txt-validate" type="text" id="riskName" name="riskName" value="" autocomplete="off" data-options="required:true"/>
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
                        })"></select>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-s">
                    <label class="lab-item">分值：</label>
                    <input class="txt txt-validate" type="text" id="riskLevelScore" name="riskLevelScore" value="" autocomplete="off" data-options="required:true"/>
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
                          name="riskEmergencyRef" cols="200" row="10" placeholder="请填写扣分标准"></textarea>
            </span>
        </div>
        <div class="clearfix mar-l20 mar-b10">
            <span class="fl">
                <label class="lab-inline w-60">审计方法：</label>
                <textarea class="txta txt-validate adaptiveTextarea" style="width:1000px" dataOptions="required:true" noNull="审计方法必填" id="riskMethod"
                          name="riskMethod" cols="300" row="20" placeholder="请填写审计方法"></textarea>
            </span>
        </div>
        <div class="clearfix mar-l20 mar-b10">
            <span class="fl">
                <label class="lab-inline w-80">数字化结论：</label>
                <input type="checkbox" id="riskRemote" name="riskRemote" value="true">
            </span>
        </div>
        <div  id="digitalModelDiv" class="clearfix mar-l20 mar-b10 hide">
            <span class="fl">
                <label class="lab-inline w-60">数字化模型：</label>
                <textarea class="txta txt-validate adaptiveTextarea" style="width:1000px" dataOptions="required:true" noNull="数字化模型必填" id="riskDigitalModel"
                          name="riskDigitalModel" cols="200" row="10" placeholder="请填写数字化模型"></textarea>
            </span>
        </div>
        <div class="clearfix mar-l20 mar-b10">
            <span class="fl">
                <label class="lab-inline w-60">制度支持：</label>
                <textarea class="txta txt-validate adaptiveTextarea" style="width:1000px" dataOptions="required:true" noNull="制度支持必填" id="riskInstitution"
                          name="riskInstitution" cols="300" row="20" placeholder="请填写制度支持"></textarea>
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
                    <input type="checkbox" id="riskIsPost" name="riskIsPost" value="">
                </div>
                <div  id="riskIsRandomDiv" class="item-one solab-s hide" style="float: left !important;">
                    <label class="lab-item">是否随机：</label>
                    <input type="checkbox" id="riskIsRandom" name="riskIsRandom" value="">
                </div>
                <div class="item-one solab-s" style="float: left !important;">
                    <label class="lab-item">敏感信息：</label>
                    <input type="checkbox" id="riskSenstivity" name="riskSenstivity" value="">
                </div>
                <div class="item-one solab-lb" style="float: left !important;">
                    <label class="lab-item">是否问题描述标准化：</label>
                    <input type="checkbox" id="riskIsQueDescStd" name="riskIsQueDescStd" value="">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="p3">
                <div class="item-one solab-l" style="padding-left: 80px !important;">
                    <label class="lab-item" style="text-align: left !important;">敏感信息类别：</label>
                    <input class="txt txt-validate" type="text" id="riskSenstivityDesc" name="riskSenstivityDesc" value="" autocomplete="off" data-options="required:true"/>
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
                          name="riskRectifyAttribute" cols="200" row="10" placeholder="请填写内控点整改属性"></textarea>
            </span>
        </div>
        <div class="clearfix mar-l20 mar-b30 orgCapabilityDiv" style="margin-left: 30px !important;">
            <div class="easyui-panel" title="体量" style="width:1068px;padding:10px 10px 10px 20px;">
                <div class="item-one solab-s" style="float: left !important;">
                    <label class="lab-item">大体量：</label>
                    <input type="checkbox" id="big" name="big" value="true" orgCapabilityEnum="32" >
                </div>
                <div class="item-one solab-s" style="float: left !important;">
                    <label class="lab-item">中体量：</label>
                    <input type="checkbox" id="medium" name="medium" value="true" orgCapabilityEnum="16" >
                </div>
                <div class="item-one solab-s" style="float: left !important;">
                    <label class="lab-item">小体量：</label>
                    <input type="checkbox" id="small" name="small" value="true" orgCapabilityEnum="8" >
                </div>
                <div class="item-one solab-s" style="float: left !important;">
                    <label class="lab-item">新院：</label>
                    <input type="checkbox" id="newhosp" name="newhosp" value="true" orgCapabilityEnum="4" >
                </div>
                <div class="item-one solab-s" style="float: left !important;">
                    <label class="lab-item">县级：</label>
                    <input type="checkbox" id="county" name="county" value="true" orgCapabilityEnum="2" >
                </div>
                <div class="item-one solab-s" style="float: left !important;">
                    <label class="lab-item">视光/门诊：</label>
                    <input type="checkbox" id="outpatient" name="outpatient" value="true" orgCapabilityEnum="1" >
                </div>
            </div>
        </div>
    </div>
    <p class="row-btn pad-t5">
        <input type="button" class="btn btn-primary btn-risk-add" name="btnCancel" value="保 存" />
        <input type="button" class="btn btn-cancel-cus" name="btnCancel" value="返 回" />
    </p>
    <p class="row-btn pad-t5" style="padding-top: 20px !important;"></p>
</form>


</body>

[#include "/WEB-INF/views/common/include_js.ftl"]

<script type="text/javascript">

    requirejs(['pub'], function () {

        $(document).ready(function() {
            // 下拉表初始化
            $ajax.postSync('${base}/ui/aams/codeMaster/getCodeMasterByType?codeMasterType=RiskBussinessType', null, false, false).done(function (rst) {
                $('#riskBussinessType').combobox('loadData', rst);          // 业务类别
            });
            $ajax.postSync('${base}/ui/aams/codeMaster/getCodeMasterByType?codeMasterType=RiskLevel', null, false, false).done(function (rst) {
                $('#riskLevel').combobox('loadData', rst);                   // 风险级别
            });
            $ajax.postSync('${base}/ui/aams/codeMaster/getCodeMasterByType?codeMasterType=RiskCategory', null, false, false).done(function (rst) {
                $('#riskCategory').combobox('loadData', rst);               // 内控点类别
            });
            $ajax.postSync('${base}/ui/aams/codeMaster/getCodeMasterByType?codeMasterType=RiskRectifyType', null, false, false).done(function (rst) {
                $('#riskRectifyType').combobox('loadData', rst);            // 整改类型
            });
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
        $('.easyui-panel input[type="checkbox"]').prop('checked', true);

        // 保存审计手册和自评手册
        $('.btn-risk-add').click(function () {
            if ($('#addManualForm').form('validate')){
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
                $ajax.post({
                    url: "${base}/ui/aams/manualofaudit/saveManual",
                    data: data,
                    tip: "是否保存风险点？",
                    calltip: true,
                    success: function (rst) {
                        window.location.href='${base}/ui/aams/manualofaudit/editManual?riskId=' + rst.riskId;
                    },
                    cancelback: function (rst) {
                        $pop.closePop();
                    },
                })
            }
        });

        $(".btn-cancel-cus").click(function () {
            $pop.closePop();
        });

    });

</script>


