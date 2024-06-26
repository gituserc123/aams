<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <meta http-equiv="X-UA-Compatible" content="IE=9"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>省区/医院风险提示函授权 - 爱尔医院</title>
    [#include "/WEB-INF/views/common/include_resources.ftl"]
</head>
<style type="text/css">

</style>

<script type="text/javascript">
</script>
<body>
<div class="searchHead bob-line">
    <div class="baseToobar topToobar hide">
        <span class="s-tool op-addIndicator"><b class="icon icon-plus"></b>新增</span>
    </div>

    <form id="sbox" class="soform form-enter">
        <label class="lab-inline">年:</label><select class="drop drop-sl-v easyui-combobox  w-150" name="auditRecordYear" id="auditRecordYear"  data-options="valueField:'id',textField:'name',clearIcon:true"></select>
        <label class="lab-inline">月:</label><select class="drop drop-sl-v easyui-combobox  w-150" name="auditRecordMonth" id="auditRecordMonth"  data-options="valueField:'id',textField:'name',clearIcon:true"></select>
        <label class="lab-inline">省区:</label><select class="drop drop-sl-v easyui-combobox  w-150" name="OrgMasterId" id="OrgMasterId"  data-options="valueField:'id',textField:'name',clearIcon:true"></select>
        <label class="lab-inline">机构名称:</label><input type="text" class="txt inline" id="orgMasterName"  name="orgMasterName" value="">
        <button type="button" class="btn btn-small btn-primary so-search" data-opt="{grid:'#gridBox',scope:'#sbox'}">查
            询
        </button>
    </form>

</div>

<div class="cont-grid">
    <div id="gridBox"></div>
</div>

</body>

[#include "/WEB-INF/views/common/include_js.ftl"]

<script type="text/javascript">

    requirejs(['pub'], function () {

         $grid.newGrid("#gridBox", {
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
            // height: 'auto',
            offset: -5
        });

        $('.cont-grid').on('click', '.s-op-auth', function () {
            var idx = $(this).attr('relIndex');
            var rowData = $("#gridBox").datagrid("getRows")[idx];
            $pop.iframePop({
                title:"审计报告授权管理(报告编号:"+rowData.auditRecordName+ ";主题:" + rowData.auditRecordTheme + ")",
                area: ['80%', '90%'],
                content:'${base}/ui/aams/auditRecordFxts/auditReportAuthorize?auditRecordId='+rowData.auditRecordId,
                sureback:function(){
                    //$pop.closePop({refreshGrid:true});
                },
                end:function(){
                    $grid.reload("#gridBox");
                }
            },["#gridBox"]);
        });

        $('.cont-grid').on('click', '.s-op-reportview', function () {
            var idx = $(this).attr('relIndex');
            var rowData = $("#gridBox").datagrid("getRows")[idx];
            console.log(rowData);
            $pop.alert("正在开发中...");
        });



    });

</script>


