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
                    <select class="drop drop-sl-v easyui-combobox  w-150" name="riskBussinessType" id="riskBussinessType"  data-options="valueField:'codeMasterTypeCode',textField:'codeMasterName',clearIcon:true"></select>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-l">
                    <label class="lab-item">风险编号：</label>
                    <input class="txt txt-validate" type="text" id="riskCode" name="riskCode" value="" autocomplete="off" data-options=""/>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-s">
                    <label class="lab-item">审计项目：</label>
                    <input class="txt txt-validate" type="text" id="riskProject" name="riskProject" value="" autocomplete="off" data-options=""/>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-s">
                    <label class="lab-item">风险类别：</label>
                    <input class="txt txt-validate" type="text" id="riskType" name="riskType" value="" autocomplete="off"  dataOptions="" noNull=""/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="p3">
                <div class="item-one solab-s">
                    <label class="lab-item">内控点：</label>
                    <input class="txt txt-validate" type="text" id="riskName" name="riskName" value="" autocomplete="off" data-options=""/>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-l">
                    <label class="lab-item">风险级别：</label>
                    <select class="drop drop-sl-v easyui-combobox  w-150" name="riskLevel" id="riskLevel"  data-options="valueField:'codeMasterTypeCode',textField:'codeMasterName',clearIcon:true"></select>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-s">
                    <label class="lab-item">分值：</label>
                    <input class="txt txt-validate" type="text" id="riskLevelScore" name="riskLevelScore" value="" autocomplete="off" data-options=""/>
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
                <textarea class="txta txt-validate adaptiveTextarea" style="width:1000px" dataOptions="" noNull="" id="riskEmergencyRef"
                          name="riskEmergencyRef" cols="200" row="10" placeholder="请填写扣分标准"></textarea>
            </span>
        </div>
        <div class="clearfix mar-l20 mar-b10">
            <span class="fl">
                <label class="lab-inline w-60">审计方法：</label>
                <textarea class="txta txt-validate adaptiveTextarea" style="width:1000px" dataOptions="" noNull="" id="riskMethod"
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
                <textarea class="txta txt-validate adaptiveTextarea" style="width:1000px" dataOptions="" noNull="" id="riskDigitalModel"
                          name="riskDigitalModel" cols="200" row="10" placeholder="请填写数字化模型"></textarea>
            </span>
        </div>
        <div class="clearfix mar-l20 mar-b10">
            <span class="fl">
                <label class="lab-inline w-60">制度支持：</label>
                <textarea class="txta txt-validate adaptiveTextarea" style="width:1000px" dataOptions="" noNull="" id="riskInstitution"
                          name="riskInstitution" cols="300" row="20" placeholder="请填写制度支持"></textarea>
            </span>
        </div>
        <div class="row">
            <div class="p3">
                <div class="item-one solab-l" style="padding-left: 80px !important;">
                    <label class="lab-item" style="text-align: left !important;">内控点类别：</label>
                    <select class="drop drop-sl-v easyui-combobox w-150" name="riskCategory" id="riskCategory"  data-options="valueField:'codeMasterTypeCode',textField:'codeMasterName',clearIcon:true"></select>
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
                    <input class="txt txt-validate" type="text" id="riskSenstivityDesc" name="riskSenstivityDesc" value="" autocomplete="off" data-options=""/>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-l">
                    <label class="lab-item">整改类型：</label>
                    <select class="drop drop-sl-v easyui-combobox w-150" name="riskRectifyType" id="riskRectifyType"  data-options="valueField:'codeMasterTypeCode',textField:'codeMasterName',clearIcon:true"></select>
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
                <textarea class="txta txt-validate adaptiveTextarea" style="width:1000px" dataOptions="" noNull="" id="riskRectifyAttribute"
                          name="riskRectifyAttribute" cols="200" row="10" placeholder="请填写内控点整改属性"></textarea>
            </span>
        </div>
        <div class="clearfix mar-l20 mar-b30" style="margin-left: 30px !important;">
            <div class="easyui-panel" title="体量" style="width:1068px;padding:10px 10px 10px 20px;">
                    <div class="item-one solab-s" style="float: left !important;">
                        <label class="lab-item">大体量：</label>
                        <input type="checkbox" id="big" name="big" value="true">
                    </div>
                    <div class="item-one solab-s" style="float: left !important;">
                        <label class="lab-item">中体量：</label>
                        <input type="checkbox" id="medium" name="medium" value="true">
                    </div>
                    <div class="item-one solab-s" style="float: left !important;">
                        <label class="lab-item">小体量：</label>
                        <input type="checkbox" id="small" name="small" value="true">
                    </div>
                    <div class="item-one solab-s" style="float: left !important;">
                        <label class="lab-item">新院：</label>
                        <input type="checkbox" id="newhosp" name="newhosp" value="true">
                    </div>
                    <div class="item-one solab-s" style="float: left !important;">
                        <label class="lab-item">县级：</label>
                        <input type="checkbox" id="county" name="county" value="true">
                    </div>
                    <div class="item-one solab-s" style="float: left !important;">
                        <label class="lab-item">视光/门诊：</label>
                        <input type="checkbox" id="outpatient" name="outpatient" value="true">
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
        <input type="button" class="btn btn-small btn-primary mar-5 so-template-add" style="margin: 5px 5px 5px 10px !important;" value="新增扣分标准"/>
        <div class="cont-grid-template" style="margin: 0px 0px 0px 11px !important;">
            <div id="gridBox-scoreStandrd"></div>
        </div>
    </div>
    <div class="queStandrd">
        <h2 class="h2-title-a">
            <span class="s-title">问题标准</span>
        </h2>
        <hr class="mar-l10 mar-r10 mar-t0 mar-b20" style="border-color:#b2def4;margin-bottom: 0px !important;">
        <input type="button" class="btn btn-small btn-primary mar-5 so-template-add" style="margin: 5px 5px 5px 10px !important;" value="新增问题标准"/>
        <div class="cont-grid-template" style="margin: 0px 0px 0px 11px !important;">
            <div id="gridBox-queStandrd"></div>
        </div>
    </div>
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
                    <input class="txt txt-validate" style="width: 58% !important;"  readonly="true" type="text" id="selfRiskid" name="selfRiskid" value="" autocomplete="off" data-options=""/>
                    <img class="selfRisk-prompt" style="vertical-align: middle !important;margin-bottom: 3px;margin-left: 2px;" src="${base}/static/images/base/prompt.png" />
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-s">
                    <label class="lab-item">业务板块：</label>
                    <select class="drop drop-sl-v easyui-combobox  w-150" name="selfRiskBussinessType" id="selfRiskBussinessType"  data-options="valueField:'codeMasterTypeCode',textField:'codeMasterName',clearIcon:true"></select>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-n">
                    <label class="lab-item">自评风险编号：</label>
                    <input class="txt txt-validate" type="text" id="selfRiskCode" name="selfRiskCode" value="" autocomplete="off" data-options=""/>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-s">
                    <label class="lab-item">风险点：</label>
                    <input class="txt txt-validate" type="text" id="selfRiskTitle" name="selfRiskTitle" value="" autocomplete="off"  dataOptions="" noNull=""/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="p3">
                <div class="item-one solab-l">
                    <label class="lab-item">风险类别：</label>
                    <input class="txt txt-validate" type="text" id="selfRiskType" name="selfRiskType" value="" autocomplete="off"  dataOptions="" noNull=""/>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-s">
                    <label class="lab-item">一级流程：</label>
                    <input class="txt txt-validate" type="text" id="selfRiskFirstLevel" name="selfRiskFirstLevel" value="" autocomplete="off" data-options=""/>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-n">
                    <label class="lab-item">二级流程：</label>
                    <input class="txt txt-validate" type="text" id="selfRiskSecondLevel" name="selfRiskSecondLevel" value="" autocomplete="off" data-options=""/>
                </div>
            </div>
            <div class="p3">
                <div class="item-one solab-s">
                    <label class="lab-item">分值：</label>
                    <input class="txt txt-validate" type="text" id="selfRiskScore" name="selfRiskScore" value="" autocomplete="off" data-options=""/>
                </div>
            </div>
        </div>
        <div class="clearfix mar-l20 mar-b10">
            <span class="fl">
                <label class="lab-inline w-60">风险描述：</label>
                <textarea class="txta txt-validate adaptiveTextarea" style="width:1000px" dataOptions="" noNull="" id="selfRiskDesc"
                          name="selfRiskDesc" cols="200" row="10" placeholder="请填写风险描述"></textarea>
            </span>
        </div>
        <div class="clearfix mar-l20 mar-b10">
            <span class="fl">
                <label class="lab-inline w-60">评估依据：</label>
                <textarea class="txta txt-validate adaptiveTextarea" style="width:1000px" dataOptions="" noNull="" id="selfRiskEvalucationBasis"
                          name="selfRiskEvalucationBasis" cols="300" row="20" placeholder="请填写评估依据"></textarea>
            </span>
        </div>
        <div class="clearfix mar-l20 mar-b10">
            <span class="fl">
                <label class="lab-inline w-60">自评方法：</label>
                <textarea class="txta txt-validate adaptiveTextarea" style="width:1000px" dataOptions="" noNull="" id="selfRiskMethod"
                          name="selfRiskMethod" cols="200" row="10" placeholder="请填写自评方法"></textarea>
            </span>
        </div>
        <div class="clearfix mar-l20 mar-b10">
            <span class="fl">
                <label class="lab-inline w-60">风险应对指引：</label>
                <textarea class="txta txt-validate adaptiveTextarea" style="width:1000px" dataOptions="" noNull="" id="selfRiskGuide"
                          name="selfRiskGuide" cols="300" row="20" placeholder="请填写风险应对指引"></textarea>
            </span>
        </div>
        <div class="clearfix mar-l20 mar-b10">
            <span class="fl">
                <label class="lab-inline w-60">扣分标准：</label>
                <textarea class="txta txt-validate adaptiveTextarea" style="width:1000px" dataOptions="" noNull="" id="selfRiskDeductCriterion"
                          name="selfRiskDeductCriterion" cols="200" row="10" placeholder="请填写扣分标准"></textarea>
            </span>
        </div>
        <div class="clearfix mar-l20 mar-b30" style="margin-left: 30px !important;">
            <div class="easyui-panel" title="体量" style="width:1068px;padding:10px 10px 10px 20px;">
                <div class="item-one solab-s" style="float: left !important;">
                    <label class="lab-item">医院：</label>
                    <input type="checkbox" id="hospital" name="hospital" value="true">
                </div>
                <div class="item-one solab-s" style="float: left !important;">
                    <label class="lab-item">眼科门诊：</label>
                    <input type="checkbox" id="outPatient" name="outPatient" value="true">
                </div>
                <div class="item-one solab-s" style="float: left !important;">
                    <label class="lab-item">眼视光诊所：</label>
                    <input type="checkbox" id="eyeOpticClinic" name="eyeOpticClinic" value="true">
                </div>
                <div class="item-one solab-s" style="float: left !important;">
                    <label class="lab-item">眼镜店：</label>
                    <input type="checkbox" id="spectacleStore" name="spectacleStore" value="true">
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

        // 数字化结论点击事件
        $('#riskRemote').click(function() {
            if ($(this).is(':checked')) {
                $('#digitalModelDiv').show();
                $('#riskDigitalModel').prop('disabled', false);
            } else {
                $('#digitalModelDiv').hide();
                $('#riskDigitalModel').prop('disabled', true);
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
            var data = $(".manualRiskForm").sovals();
            // 数据处理

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


        });

        // 审计发现模板Grid初始化
        $grid.newGrid("#gridBox-template", {
            pagination: true,
            fitColumns: false,
            columns: [[
                {title: 'auditRecordId', field: 'auditRecordId', hidden: true,width: 150,},
                {title: '省区', field: 'orgMasterRegion', width: 100},
                {title: '机构名称', field: 'orgMasterName', width: 200},
                {title: '主题', field: 'auditRecordTheme', width: 300},
                {title: '报告编号', field: 'auditRecordName', width: 150},
                {title: '日期', field: 'auditRecordYear', width: 150,formatter(v,row,index){
                        return v + '年' + row.auditRecordMonth + '月';
                    }},
                {title:'操作',field:'op',width:300,formatter :function (v,row,index) {
                        var s = '&nbsp;<span class="icon-plus_sign hand s-op-auth" rel="'+row.auditRecordId+'"  relIndex='+ index +' title="风险提示授权"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
                        s += '<span class="s-op  blue hand s-op-reply" rel="'+row.auditRecordId+'"  relIndex= ' +index + ' >风险提示回复</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class=" s-op-edit s-op-reportview" rel="'+row.id+'" relIndex= ' + index + 'title="">报告查看</span>'
                        return s;
                    }},
            ]],
            rowStyler: function (index, row) {
            },
            queryParams: {},
            onBeforeLoad: function (param) {
                return true;
            },
            onLoadSuccess: function (data) {

            },
            url: '${base}/ui/aams/auditRecordFxts/getAllFxts',
            height: 200,
            width: '80%',
            offset: -5
        });

        // 扣分标准Grid初始化
        $grid.newGrid("#gridBox-scoreStandrd", {
            pagination: true,
            fitColumns: false,
            columns: [[
                {title: 'auditRecordId', field: 'auditRecordId', hidden: true,width: 150,},
                {title: '省区', field: 'orgMasterRegion', width: 100},
                {title: '机构名称', field: 'orgMasterName', width: 200},
                {title: '主题', field: 'auditRecordTheme', width: 300},
                {title: '报告编号', field: 'auditRecordName', width: 150},
                {title: '日期', field: 'auditRecordYear', width: 150,formatter(v,row,index){
                        return v + '年' + row.auditRecordMonth + '月';
                    }},
                {title:'操作',field:'op',width:300,formatter :function (v,row,index) {
                        var s = '&nbsp;<span class="icon-plus_sign hand s-op-auth" rel="'+row.auditRecordId+'"  relIndex='+ index +' title="风险提示授权"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
                        s += '<span class="s-op  blue hand s-op-reply" rel="'+row.auditRecordId+'"  relIndex= ' +index + ' >风险提示回复</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class=" s-op-edit s-op-reportview" rel="'+row.id+'" relIndex= ' + index + 'title="">报告查看</span>'
                        return s;
                    }},
            ]],
            rowStyler: function (index, row) {
            },
            queryParams: {},
            onBeforeLoad: function (param) {
                return true;
            },
            onLoadSuccess: function (data) {

            },
            url: '${base}/ui/aams/auditRecordFxts/getAllFxts',
            height: 200,
            width: '80%',
            offset: -5
        });

        // 问题标准Grid初始化
        $grid.newGrid("#gridBox-queStandrd", {
            pagination: true,
            fitColumns: false,
            columns: [[
                {title: 'auditRecordId', field: 'auditRecordId', hidden: true,width: 150,},
                {title: '省区', field: 'orgMasterRegion', width: 100},
                {title: '机构名称', field: 'orgMasterName', width: 200},
                {title: '主题', field: 'auditRecordTheme', width: 300},
                {title: '报告编号', field: 'auditRecordName', width: 150},
                {title: '日期', field: 'auditRecordYear', width: 150,formatter(v,row,index){
                        return v + '年' + row.auditRecordMonth + '月';
                    }},
                {title:'操作',field:'op',width:300,formatter :function (v,row,index) {
                        var s = '&nbsp;<span class="icon-plus_sign hand s-op-auth" rel="'+row.auditRecordId+'"  relIndex='+ index +' title="风险提示授权"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
                        s += '<span class="s-op  blue hand s-op-reply" rel="'+row.auditRecordId+'"  relIndex= ' +index + ' >风险提示回复</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class=" s-op-edit s-op-reportview" rel="'+row.id+'" relIndex= ' + index + 'title="">报告查看</span>'
                        return s;
                    }},
            ]],
            rowStyler: function (index, row) {
            },
            queryParams: {},
            onBeforeLoad: function (param) {
                return true;
            },
            onLoadSuccess: function (data) {

            },
            url: '${base}/ui/aams/auditRecordFxts/getAllFxts',
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
                    $('#selfRiskid').val($store.get('selfRisk').selfRiskId);
                    $('#selfRiskBussinessType').combobox('setValue', $store.get('selfRisk').selfRiskBussinessType);
                    $('#selfRiskCode').val($store.get('selfRisk').selfRiskCode);
                    $('#selfRiskTitle').val($store.get('selfRisk').selfRiskTitle);
                    $('#selfRiskType').val($store.get('selfRisk').selfRiskType);
                    $('#selfRiskFirstLevel').val($store.get('selfRisk').selfRiskFirstLevel);
                    $('#selfRiskSecondLevel').val($store.get('selfRisk').selfRiskSecondLevel);
                    $('#selfRiskScore').val($store.get('selfRisk').selfRiskScore);
                    $('#selfRiskDesc').val($store.get('selfRisk').selfRiskDesc);
                    $('#selfRiskEvalucationBasis').val($store.get('selfRisk').selfRiskEvalucationBasis);
                    $('#selfRiskMethod').val($store.get('selfRisk').selfRiskMethod);
                    $('#selfRiskGuide').val($store.get('selfRisk').selfRiskGuide);
                    $('#selfRiskDeductCriterion').val($store.get('selfRisk').selfRiskDeductCriterion);
                    $store.clear();
                },
                sureback : function (iframeSendData){
                    //表单提交| 或成功 执行函数，子页面可通过 $pop.closePop 返回参数
                }
            },'');
        });

    });

</script>


