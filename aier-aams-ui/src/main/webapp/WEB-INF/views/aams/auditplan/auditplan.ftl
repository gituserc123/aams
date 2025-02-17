<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <meta http-equiv="X-UA-Compatible" content="IE=9"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>审计计划列表 - 爱尔医院</title>
    [#include "/WEB-INF/views/common/include_resources.ftl"]
</head>
<style type="text/css">
    .org-prompt {
        cursor: pointer; /* 使鼠标悬停时光标显示为“手”的形状 */
    }
    .p-tip{background-color:var(--shallow);padding:0px 12px;}
</style>

<script type="text/javascript">
</script>
<body>
<div class="bob-line p-tip">
    [@shiro.hasPermission name = "Btn_AddAuditProject"]
    <input type="button" class="btn btn-small btn-primary mar-5 so-audit-add" value="新增计划"/>
    <input type="button" class="btn btn-small btn-primary mar-5 so-audit-batch-add" value="批量新增"/>
    [/@shiro.hasPermission]
    [@shiro.hasPermission name = "Btn_AddFxtsAudit"]
    <input type="button" class="btn btn-small btn-primary mar-5 so-audit-fxts-add" value="新增风险提示"/>
    [/@shiro.hasPermission]
</div>
<div class="searchHead">
    <form id="sbox" class="soform form-enter">
        <label class="lab-inline">年：</label><select id="auditRecordYear" name="auditRecordYear"  class="easyui-combobox" style="width:200px;" data-options="valueField:'valueCode',textField:'valueDesc',clearIcon:true"></select>
        <label class="lab-inline">月：</label><select id="auditRecordMonth" name="auditRecordMonth"  class="easyui-combobox" style="width:200px;" data-options="valueField:'valueCode',textField:'valueDesc',clearIcon:true"></select>
        <label class="lab-inline">医院：</label><select class="form-control easyui-combogrid" style="width:200px;" id="orgMasterName" type="text" name="orgMasterName" data-options="" ></select>
        <!-- <input type="text" class="txt inline" name="orgMasterName" value=""><img class="org-prompt" style="vertical-align: middle !important;margin-bottom: 3px;margin-left: 5px;" src="${base}/static/images/base/prompt.png" /> -->
        <label class="lab-inline">是否完成：</label><select id="auditRecordIsFinished" name="auditRecordIsFinished"  class="easyui-combobox" style="width:200px;" data-options="valueField:'valueCode',textField:'valueDesc',clearIcon:true"></select>
        <label class="lab-inline">审批状态：</label><select id="auditRecordStatus" name="auditRecordStatus"  class="easyui-combobox" style="width:200px;" data-options="valueField:'valueCode',textField:'valueDesc',clearIcon:true"></select>
        <button type="button" class="btn btn-small btn-primary so-search" data-opt="{grid:'#gridBox',scope:'#sbox'}">查询</button>
    </form>
</div>

<div class="cont-grid">
    <div id="gridBox"></div>
</div>

</body>

[#include "/WEB-INF/views/common/include_js.ftl"]

<script type="text/javascript">

    requirejs(['pub'], function () {
        // 初始化,生成年份下拉框(当前年,当前年前5年,当前年后2年)，月份下拉框
        var currentYear = new Date().getFullYear();
        var years = [];
        for (var i = currentYear - 5; i <= currentYear + 2; i++) {
            years.push({ valueDesc: i.toString(), valueCode: i.toString() });
        }
        var months = [
            { valueCode: '1', valueDesc: '一月' },{ valueCode: '2', valueDesc: '二月' },{ valueCode: '3', valueDesc: '三月' },{ valueCode: '4', valueDesc: '四月' },{ valueCode: '5', valueDesc: '五月' }, { valueCode: '6', valueDesc: '六月' },
            { valueCode: '7', valueDesc: '七月' },{ valueCode: '8', valueDesc: '八月' },{ valueCode: '9', valueDesc: '九月' },{ valueCode: '10', valueDesc: '十月' },{ valueCode: '11', valueDesc: '十一月' }, { valueCode: '12', valueDesc: '十二月' }
        ];

        $(document).ready(function() {
            $('#auditRecordYear').combobox('loadData', years);
            $('#auditRecordMonth').combobox('loadData', months);
            $('#auditRecordIsFinished').combobox('loadData',eval('[{"valueDesc":"未完成","valueCode":"0",},{"valueDesc":"已完成","valueCode":"1",}]'));
            $('#auditRecordStatus').combobox('loadData',eval('[{"valueDesc":"待审批","valueCode":"0",},{"valueDesc":"已审批","valueCode":"1",}]'));
        });

        // 医院下拉框加载
        let orgMasterData;
        $ajax.post({
            url: '${base}/ui/aams/orgMaster/getOrgMasterByCond',
            sync:true
        }).success (function (rst) {
                orgMasterData = rst;
            }
        );
        $('#orgMasterName').combogrid({
            panelWidth:500,
            delay: 500,//用户输入请求间隙时间
            //mode: 'remote',//根据输入向远端请求
            //url: JSON.stringify(gridData),//请求url
            idField: 'orgMasterName',//输入框返回value
            textField: 'orgMasterName',//输入框返回字符
            striped: true,
            required: false,
            editable: true,
            columns: [[//grid设置
                {field:'orgMasterId',title:'机构Id',width:80},
                {field:'orgMasterName',title:'机构名称',width:180},
                {field:'orgMasterShort',title:'机构简称',width:80},
            ]],
            onSelect:function (idx,item) {
            },
            onChange:function (idx,item) {
            },
            keyHandler: {
                up: function() {},
                down: function() {},
                enter: function() {},
                query: function(q) {
                    doSearch(q,orgMasterData,['orgMasterName'],$(this));
                    $('#orgMasterName').combogrid('setValue', q);
                }
            }
        });

        $("#orgMasterName").combogrid("grid").datagrid("loadData", orgMasterData);


        $grid.newGrid("#gridBox", {
            pagination: true,
            fitColumns: false,
            tools: [
/*                [
                    {
                        iconCls: 'plus',//图标
                        text: '新增',//文字
                    },
                        {
                            iconCls: 'plus_sign',//图标
                            text: '导出',//文字
                        }
                ]*/
            ],
            columns: [[
                {title: 'ID', field: 'id', hidden: true,width: 150,},
                {
                    title: '操作', field: 'op', width: 80, formatter: function (v, row, index) {
                        var chkHtml = '';
                        [@shiro.hasPermission name = "Btn_AuditPlan_Update"]
                            chkHtml += '<span rel=' + row.id + ' relIndex=' + index  + ' class="s-op s-op-edit  icon-edit" title="编辑" aier="edit" ></span>';
                        [/@shiro.hasPermission]
                        [@shiro.hasPermission name = "Btn_AuditPlan_Delete"]
                            chkHtml += '&nbsp;&nbsp;&nbsp;<span class="s-op s-op-del icon-del" title="删除" rel="'+row.id+'"></span>';
                        [/@shiro.hasPermission]
                        [@shiro.hasPermission name = "Btn_AuditPlan_Archive"]
                            chkHtml += '&nbsp;&nbsp;&nbsp;<span class="s-op s-op-archive icon-dateright" title="归档" rel="'+row.id+'"></span>'
                        [/@shiro.hasPermission]
                        return chkHtml;
                    }
                },
                {title: '省区', field: 'orgMasterRegion', width: 200},
                {title: '机构名称', field: 'orgMasterName', width: 220},
                {title: '机构类型', field: 'orgMasterType', width: 80},
                {title: '机构级别', field: 'auditRecordCapabilityDesc', width: 100},
                {title: '审计报告编号', field: 'auditRecordName', width: 150},
                {title: '项目人员', field: 'auditPlanPerson', width: 200},
                {title: '审计类型', field: 'auditRecordTypeName', width: 120},
                {title: '是否完成', field: 'auditRecordfinish', width: 80,formatter: function(value, row, index) {
                        if (value == 'false') {
                            return '未完成';
                        }else {
                            return '已完成';
                        }
                    }},
                {title: '审核状态', field: 'auditRecordStatus', width: 80,formatter: function(value, row, index) {
                        if (value != '0') {
                            if(value == '4'){
                                return '<span style="color: #808080;">已拒绝</span>';
                            }else{
                                return '<span style="color: #000000;">已审批</span>';
                            }
                        }else{
                            return '<span style="color: #FF0000;">待审批</span>';
                        }
                    }
                },
                {title: '', field: 'visitAdviceTxt', width: 150,hidden: false,formatter: function(value, row, index) { return '同意'}},
                {title: '', field: 'modifyDate', width: 150,hidden: false,formatter: function(value, row, index) { return '拒绝'}},
                {title: '', field: 'modiferName', width: 150,hidden: false,formatter: function(value, row, index) { return '医院团队'}},
            ]],
            rowStyler: function (index, row) {
                if (index % 2 == 0) {
                    return 'background-color:#f2f2f2;'; // 设置偶数行背景色
                }
            },
            queryParams: {},
            onBeforeLoad: function (param) {
                return true;
            },
            onLoadSuccess: function (data) {
            },
            url: '${base}/ui/aams/auditplan/getAuditPlanList',
            // height: 'auto',
            offset: -5
        });

        $(".so-audit-add").click(function ($e) {
            $pop.iframePop({
                title: '加入审计计划',//标题
                content: '${base}/ui/aams/auditplan/addAuditProject?inMode=Insert',//请求地址
                area: ['1200px', '900px'],//窗口大小
                // postData : {mainId:row.id},//往子页面传值
                end : function(iframeSendData){
                    //关闭执行函数，子页面可通过 $pop.closePop 返回参数
                    $grid.load('#gridBox');
                },
                sureback : function (iframeSendData){
                    //表单提交| 或成功 执行函数，子页面可通过 $pop.closePop 返回参数
                }
            },'#gridBox');
        });
    });

    function doSearch(q,data,searchList,ele){
        ele.combogrid('grid').datagrid('loadData', []);
        if(q == ""){
            ele.combogrid('grid').datagrid('loadData', data);
            return;
        }
        var rows = [];
        $.each(data,function(i,obj){
            for(var p in searchList){
                var v = obj[searchList[p]];
                if (!!v && v.toString().indexOf(q) >= 0){
                    rows.push(obj);
                    break;
                }
            }
        });
        if(rows.length == 0){
            ele.combogrid('grid').datagrid('loadData', []);
            return;
        }
        ele.combogrid('grid').datagrid('loadData', rows);
    };

</script>


