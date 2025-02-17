<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <meta http-equiv="X-UA-Compatible" content="IE=9"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    [#include "/WEB-INF/views/common/include_resources.ftl"]
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>加入审计计划</title>
</head>
<style>
    .basic-info {
        width: 98%;
        display: flex;
        padding: 10px;
        align-items: stretch;  /* Ensures child elements stretch to the same height */
    }

    .audit-info {
        width: 50%;
        border-radius: 5px;
        border: 1px solid #ddd;
        background-color: #f9f9f9;
        display: flex;
        flex-direction: column;  /* Ensure content inside is stacked vertically */
        justify-content: space-between;  /* Ensure space is distributed evenly */
    }

    .audit-person {
        width: 52%;
        border-radius: 5px;
        margin: 0px 2px;
        border: 1px solid #ddd;
        background-color: #f9f9f9;
        display: flex;
        flex-direction: column;  /* Align contents inside vertically */
        justify-content: space-between;  /* Align content evenly */
    }
</style>
<body>
<div class="basic-info">
    <div class="audit-info">
        <form id="auditInfoForm" class="soform formA form-validate pad-t20 auditInfoCls" method="post"
              action="${base}/ui/amcs/fin/hospInsuranceCheck/save">
            <div class="row">
                <div class="p6">
                    <div class="item-one">
                        <label class="lab-item">机构代码</label>
                        <input class="txt txt-validate" type="text" id="orgMasterId" name="orgMasterId"
                               value="" validType="" autocomplete="off" readonly="true" data-options="required:true"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="p6">
                    <div class="item-one">
                        <label class="lab-item">机构名称</label>
                        <input type="text" id="checkDate" class="txt so-date required txt-validate" data-options="required:true, maxDate:'${sysdate}',format:'yyyy-MM-dd',type:'date'" name="checkDate" value="" />
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="p6">
                    <div class="item-one">
                        <label class="lab-item">审计类型</label>
                        <input type="text" id="checkDate" class="txt so-date required txt-validate" data-options="required:true, maxDate:'${sysdate}',format:'yyyy-MM-dd',type:'date'" name="checkDate" value="" />
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="p6">
                    <div class="item-one">
                        <label class="lab-item">机构级别</label>
                        <input type="text" id="checkDate" class="txt so-date required txt-validate" data-options="required:true, maxDate:'${sysdate}',format:'yyyy-MM-dd',type:'date'" name="checkDate" value="" />
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="p6">
                    <div class="item-one">
                        <label class="lab-item">计划年度</label>
                        <input type="text" id="checkDate" class="txt so-date required txt-validate" data-options="required:true, maxDate:'${sysdate}',format:'yyyy-MM-dd',type:'date'" name="checkDate" value="" />
                    </div>
                </div>
                <div class="p6">
                    <div class="item-one">
                        <label class="lab-item">月度</label>
                        <input type="text" id="checkDate" class="txt so-date required txt-validate" data-options="required:true, maxDate:'${sysdate}',format:'yyyy-MM-dd',type:'date'" name="checkDate" value="" />
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="p6">
                    <div class="item-one">
                        <label class="lab-item">审计报告编号</label>
                        <input class="txt txt-validate" type="text" id="auditRecordName" name="auditRecordName"
                               value="" validType="" autocomplete="off" readonly="true" data-options="required:true"/>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <div class="audit-person">右列自适应啊沙发沙发safe我热热</div>
</div>


</body>

[#include "/WEB-INF/views/common/include_js.ftl"]

<script type="text/javascript">

    requirejs(['pub'], function () {

    });



</script>

</html>
