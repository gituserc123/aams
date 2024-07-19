<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <meta http-equiv="X-UA-Compatible" content="IE=9"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>审计手册 - 爱尔医院</title>
    [#include "/WEB-INF/views/common/include_resources.ftl"]
    <style type="text/css">

    </style>
</head>

<body>
<div class="tabContBox">
    <ul class="tabs likeTabs">
        [#list codeMasters as cm]
            <li rel="${cm.codeMasterId}" cmtc="${cm.codeMasterTypeCode}">
                <a href="#" class="tabs-inner"><span class="tabs-title">${cm.codeMasterName}</span></a>
            </li>
        [/#list]
    </ul>
</div>
<div class="searchHead">
    <form id="sbox" class="soform form-enter">
        <label class="lab-inline">业务类别：</label>
        <select class="drop drop-sl-v easyui-combobox  w-150" name="riskBussinessType" id="riskBussinessType"  data-options="valueField:'codeMasterTypeCode',textField:'codeMasterName',clearIcon:true"></select>
        <label class="lab-inline">审计项目：</label>
        <input type="text" class="txt inline" id="riskProject"  name="riskProject" value="">
        <label class="lab-inline">风险类别：</label>
        <input type="text" class="txt inline" id="riskType"  name="riskType" value="">
        <label class="lab-inline">内控点：</label>
        <input type="text" class="txt inline" id="riskName"  name="riskName" value="">
        <button type="button" class="btn btn-small btn-primary so-search"  data-opt="{grid:'#gridBox', scope:'#sbox'}">查 询</button>
        <button type="button" class="btn btn-small btn-primary s-export">导 出</button>
    </form>
</div>
<div class="cont-grid">
    <div id="gridBox"></div>
</div>
</body>

[#include "/WEB-INF/views/common/include_js.ftl"]
<script type="text/javascript" src='${base}/static/js/config.require.js?12441'></script>
<script type="text/javascript">
    requirejs(['pub'], function () {

        $ajax.postSync('${base}/ui/aams/codeMaster/getCodeMasterByType?riskBussinessType=RiskBussinessType',null,false,false).done(function (rst) {
            $('#riskBussinessType').combobox('loadData', rst);
        });

        var tabIndex = 99;
        var $tabLi = $('.likeTabs li');
        //页签点击事件
        $tabLi.click(function() {
            var $this = this;
            var ix = $tabLi.index(this);
            if (tabIndex !== ix) {
                tabIndex = ix;
                $tabLi.removeClass('tabs-selected');
                $($this).addClass('tabs-selected');
                $('.tabCont').addClass('tabContHide').eq(ix).removeClass('tabContHide');
                var codeMasterTypeCode = $($this).attr('cmtc');
                $("#riskBussinessType").combobox('setValue', codeMasterTypeCode);
                var formData = $('#sbox').sovals();
                $grid.load('#gridBox',formData);
            }
        });

        $grid.newGrid("#gridBox", {
            pagination: true,
            fitColumns: false,
            tools: [
                [
                    {
                        iconCls: 'plus',//图标
                        text: '新增',//文字
                    },
                    {
                        iconCls: 'plus_sign',//图标
                        text: '测试',//文字
                    }
                ]
            ],
            columns: [[
                {title: 'riskId', field: 'riskId', hidden: true,width: 150,},
                {
                    title: '操作', field: 'op', width: 70, formatter: function (v, row, index) {
                        var chkHtml = '';
                        chkHtml += '<span rel=' + row.riskId + ' relIndex=' + index  + ' class="s-op s-op-edit  icon-edit" title="编辑" aier="edit" ></span>';
                        return chkHtml;
                    }
                },
                {title: '风险编号', field: 'riskCode', width: 100},
                {title: '业务类别', field: 'riskBussinessTypeDesc', width: 150},
                {title: '审计项目', field: 'riskProject', width: 150},
                {title: '风险类别', field: 'riskType', width: 150},
                {title: '风险级别', field: 'riskLevelDesc', width: 100},
                {title: '分值', field: 'riskLevelScore', width: 100},
                {title: '扣分标准', field: 'riskEmergencyRef', width: 100},
                {title: '内控点', field: 'riskName', width: 100},
                {title: '审计方法', field: 'riskMethod', width: 100},
                {title: '制度支持', field: 'riskInstitution', width: 100},
                {title: '适用体量', field: 'riskCapabilityDesc', width: 100},
                {title: '内控点整改属性', field: 'riskRectifyAttribute', width: 100},
            ]],
            rowStyler: function (index, row) {
            },
            queryParams: {
            },
            onBeforeLoad: function (param) {
                return true;
            },
            onLoadSuccess: function (data) {

            },
            url: '${base}/ui/aams/manualofaudit/getAll',
            // height: 'auto',
            offset: -5
        });

        $(".s-tool:contains('新增')").click(function() {
            // 执行新增操作的代码
            $pop.iframePop({
                title: "编辑风险点",//标题
                content: '${base}/ui/aams/manualofaudit/addManual',//请求地址
                area: ['100%', '100%'],//窗口大小
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

        $('.cont-grid').on('click', '.s-op-edit', function () {
            var id = $(this).attr('rel');
            $pop.iframePop({
                title: "编辑风险点",//标题
                content: '${base}/ui/aams/manualofaudit/editManual?riskId='+id,//请求地址
                area: ['100%', '100%'],//窗口大小
                // postData : {mainId:row.id},//往子页面传值
                end : function(iframeSendData){
                    //关闭执行函数，子页面可通过 $pop.closePop 返回参数
                    var formData = $('#sbox').sovals();
                    $grid.load('#gridBox',formData);
                },
                sureback : function (iframeSendData){
                    //表单提交| 或成功 执行函数，子页面可通过 $pop.closePop 返回参数
                }
            },'#gridBox');
        });

        $(".s-tool:contains('测试')").click(function() {
            // 执行新增操作的代码
            console.log('测试操作');
        });


    });
</script>
</body>

</html>
