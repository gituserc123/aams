<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <meta http-equiv="X-UA-Compatible" content="IE=9"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>风险提示查看 - 爱尔医院</title>
    [#include "/WEB-INF/views/common/include_resources.ftl"]
</head>
<style type="text/css">

</style>

<script type="text/javascript">
</script>
<body>
<!--  风险提示审计主题  -->
<div class="row">
    <div class="p6">
        <div class="item-one solab-l">
            <label class="lab-item">主题：</label>
            <span style="font-family: 微软雅黑, Poppins, sans-serif;font-size: 10px;font-weight: bold;color: #269abc;line-height: 46px;">&nbsp;主题测试:</span>
        </div>
    </div>
    <div class="p6">
        <div class="item-one solab-s">
            <label class="lab-item">业务板块/职能中心：</label>
            <span style="font-family: 微软雅黑, Poppins, sans-serif;font-size: 10px;font-weight: bold;color: #269abc;line-height: 46px;">&nbsp;业务板块/职能中心测试:</span>
        </div>
    </div>
</div>
<!--  风险提示审计报告基本信息  -->
<h2 class="h2-title-a">
    <span class="s-title">报告基本信息</span>
</h2>
<hr class="mar-l10 mar-r10 mar-t0 mar-b20" style="border-color:#b2def4">
<div class="row">
    <div class="p6">
        <div class="item-one solab-l">
            <label class="lab-item">医院名称：</label>
            <span style="font-family: 微软雅黑, Poppins, sans-serif;font-size: 10px;font-weight: bold;color: #269abc;line-height: 46px;">&nbsp;医院名称测试:</span>
        </div>
    </div>
    <div class="p6">
        <div class="item-one solab-s">
            <label class="lab-item">报告编号：</label>
            <span style="font-family: 微软雅黑, Poppins, sans-serif;font-size: 10px;font-weight: bold;color: #269abc;line-height: 46px;">&nbsp;报告编号测试:</span>
        </div>
    </div>
</div>
<div class="row">
    <div class="p6">
        <div class="item-one solab-l">
            <label class="lab-item">类型：</label>
            <span style="font-family: 微软雅黑, Poppins, sans-serif;font-size: 10px;font-weight: bold;color: #269abc;line-height: 46px;">&nbsp;主题测试:</span>
        </div>
    </div>
    <div class="p6">
        <div class="item-one solab-s">
            <label class="lab-item">省区：</label>
            <span style="font-family: 微软雅黑, Poppins, sans-serif;font-size: 10px;font-weight: bold;color: #269abc;line-height: 46px;">&nbsp;省区测试:</span>
        </div>
    </div>
</div>
<div class="row">
    <div class="p6">
        <div class="item-one solab-l">
            <label class="lab-item">负责人：</label>
            <span style="font-family: 微软雅黑, Poppins, sans-serif;font-size: 10px;font-weight: bold;color: #269abc;line-height: 46px;">&nbsp;负责人:</span>
        </div>
    </div>
    <div class="p6">
        <div class="item-one solab-s">
        </div>
    </div>
</div>
<div class="row">
<input type="button" class="btn btn-primary btn-to-risk-warn-letter" style="float: right;margin-right: 100px;" name="btnToRiskWarnLetter" value="风险提示函"/>
</div>
<!--  风险提示审计回复  -->
<h2 class="h2-title-a">
    <span class="s-title">风险提示回复</span>
</h2>
<hr class="mar-l10 mar-r10 mar-t0 mar-b20" style="border-color:#b2def4;margin-bottom: 1px !important;">
<div class="cont-grid">
    <div id="gridBox-reply"></div>
</div>
<div class="row" style="margin-top: 5px;">
<span style="position: relative;left: 50%;margin-left: -120px;"><input type="button" class="btn btn-cancel-cus" name="btnCancel" value="返 回" /></span>
</div>

<script id="editFxtsReply" type="text/html">
    <input type="text" style="position: absolute; left: -1000px;" id="hide-input"/>
    <form id="popFxtsReplyForm"  class="soform formA form-validate form-enter pad-t20 fxtsReplyForm">
        <input class="txt hide" type="text" id="auditFxtsReplyId" name="auditFxtsReplyId" value=""/>
        <div class="row">
            <div class="p12">
                <div class="item-one solab-s">
                    <label class="lab-item solab-s" style="width: 120px;">回复内容：</label>
                    <textarea class="txt txt-validate" id="auditFxtsReplyText" name="auditFxtsReplyText"  dataOptions="required:ture" noNull="请填写回复内容" validType=""  style="height: 50px;text-indent: 0;width:60%;position: relative;right: -50px;"/>
                </div>
            </div>
        </div>
        <p class="row-btn pad-t5">
            <input type="button" class="btn btn-primary btn-reply-save" name="btnSubmit" value="保 存"/>
            <input type="button" class="btn btn-cancel-reply" name="btnCancel" value="取 消"/>
        </p>
    </form>
</script>

</body>

[#include "/WEB-INF/views/common/include_js.ftl"]

<script type="text/javascript">

    requirejs(['pub'], function () {
        $grid.newGrid("#gridBox-reply",{
            pagination: true,
            fitColumns: false,
            tools: [
                [
                    {
                        iconCls: 'plus',//图标
                        text: '新增',//文字
                        click: function () {
                            window.pReply = $pop.popTemForm({
                                title: "新增回复",
                                temId: 'editFxtsReply',
                                area: ['700px', '200px'],
                                temData: {},
                                zIndex: 2,
                                grid: '#gridBox-reply',
                                onPop: function ($p) {
                                    $('form.fxtsReplyForm').form('reset');
                                    // 保存按钮事件
                                    $('.btn-reply-save').click(function () {
                                        if ($('#popFxtsReplyForm').form('validate')) {
                                            var data = $(".fxtsReplyForm").sovals();
                                            data.auditRecordId = '${auditRecordId}';
                                            data.auditFxtsReplyNum = 0;
                                            $ajax.post({
                                                url: "${base}/ui/aams/auditRecordFxts/saveReply",
                                                data: data,
                                                tip: "是否保存回复？",
                                                calltip: true,
                                                success: function (rst) {
                                                    $pop.close(window.pReply);
                                                },
                                                cancelback: function (rst) {
                                                    $pop.close(window.pReply);
                                                },
                                            })
                                        };
                                    });
                                    $(".btn-cancel-reply").click(function () {
                                        $pop.close(window.pReply);
                                    });
                                },
                                end: function () {
                                    $grid.reload("#gridBox-reply");
                                },
                                beforeSubmit: function (opt, $form, formData) {
                                    return true;
                                }
                            });
                        }
                    }
                ]
            ],
            columns:[[
                {
                    title: '操作',
                    field: 'op',
                    width: 100,
                    formatter: function(v, row, index) {
                        var chkHtml = '';
                        if(row.auditfxtsreplyuser == '${secUserId}'){
                            chkHtml += '<span rel=' + row.id + ' relIndex=' + index + ' class="s-op s-op-edit  icon-edit" title="编辑" aier="edit" ></span>';
                        }
                        return chkHtml;
                    }
                },
                {title:'auditfxtsreplyid',field:'auditfxtsreplyid',hidden: true},
                {title:'auditfxtsreplyuser',field:'auditfxtsreplyuser',hidden: true},
                {title:'回复人',field:'secusername',width:120},
                {title:'回复人部门',field:'deptmastername',width:100},
                {title:'回复内容',field:'auditfxtsreplytext',width:280},
                {title:'回复时间',field:'auditfxtsreplyupdtime',width:280},
            ]],
            onBeforeLoad: function (param) {
            },
            onLoadSuccess: function (data) {
            },
            url:'${base}/ui/aams/auditRecordFxts/selectReply?auditRecordId=${auditRecordId}',
            offset : -55
        });

        $('.cont-grid').on('click', '.s-op-edit',function () {
            var id = $(this).attr('rel');
            var idx = $(this).attr('relIndex');
            var rowData = $("#gridBox-reply").datagrid("getRows")[idx];
            window.pReply = $pop.popTemForm({
                title: "编辑回复",
                temId: 'editFxtsReply',
                area: ['700px', '200px'],
                temData: rowData,
                data: rowData,
                zIndex: 2,
                grid: '#gridBox-reply',
                onPop: function ($p) {
                    $('form.fxtsReplyForm').form('reset');
                    $('#auditFxtsReplyId').val(rowData.auditfxtsreplyid);
                    $('#auditFxtsReplyText').val(rowData.auditfxtsreplytext);
                    // 保存按钮事件
                    $('.btn-reply-save').click(function () {
                        if ($('#popFxtsReplyForm').form('validate')) {
                            var data = $(".fxtsReplyForm").sovals();
                            $ajax.post({
                                url: "${base}/ui/aams/auditRecordFxts/saveReply",
                                data: data,
                                tip: "是否保存回复？",
                                calltip: true,
                                success: function (rst) {
                                    $pop.close(window.pReply);
                                },
                                cancelback: function (rst) {
                                    $pop.close(window.pReply);
                                },
                            })
                        };
                    });
                    $(".btn-cancel-reply").click(function () {
                        $pop.close(window.pReply);
                    });
                },
                end: function () {
                    $grid.reload("#gridBox-reply");
                },
                beforeSubmit: function (opt, $form, formData) {
                    return true;
                },
                afterSubmit : function (rst,$formBox) {
                    //提交之后的事件, function (rst,$formBox) {}
                    // $pop.close(p);
                }
            });
        });

        $(".btn-to-risk-warn-letter").click(function () {
            $pop.alert("正在开发中...");
        });

        $(".btn-cancel-cus").click(function () {
            $pop.closePop();
        });
    });

</script>


