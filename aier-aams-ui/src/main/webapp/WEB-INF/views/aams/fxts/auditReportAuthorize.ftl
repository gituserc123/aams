<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
    <meta http-equiv="X-UA-Compatible" content="IE=9" />
    [#include '/WEB-INF/views/common/include_resources.ftl']
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>审计报告授权管理 - 爱尔医院</title>
    <style type="text/css">
        .left-panel{
            position: absolute;height: 100%;width: 485px;box-sizing: border-box;border-right: 5px solid #dfe2e6;z-index: 1;
        }
        .left-panel:hover .fixgrid-refresh{
            display: block;
        }
        /* 定义授权按钮的基础样式 */
        .authorize-button a {
            display: inline-block; /* 内联块级元素 */
            height: 85%;
            padding: 0px 10px 3px 10px ; /* 内边距 */
            border: 0px solid #1890ff; /* 边框颜色 */
            border-radius: 10px; /* 圆角 */
            background-color: #e6f7ff; /* 背景颜色 */
            color: #1890ff; /* 文字颜色 */
            text-decoration: none; /* 去掉下划线 */
            font-size: 14px; /* 字体大小 */
            transition: background-color 0.3s, color 0.3s; /* 过渡效果 */
        }

        /* 定义授权按钮在悬停时的样式 */
        .authorize-button a:hover {
            background-color: #1890ff; /* 悬停时的背景颜色 */
            color: #fff; /* 悬停时的文字颜色 */
        }

    </style>
</head>

<body>
    <div style="position: absolute;left: 0;right: 0;top: 0px;bottom: 0;">
        <div class="left-panel cont-grid">
            <span style="font-family: 微软雅黑, Poppins, sans-serif;font-size: 18px;font-weight: bold;color: #269abc;line-height: 46px;">&nbsp;已授权用户:</span>
            <div id="gridBox-0"></div>
        </div>
        <div style="position: absolute;height: 100%;width: 100%;box-sizing: border-box;padding-left: 486px;overflow-y: auto;overflow-x: hidden;">
            <div class="searchHead">
                <form id="sbox" class="soform form-enter">
                    <label class="lab-inline">医院/总部：</label>
                    <select id="instId" name="instId" style="width:150px;" data-options="required:false"></select>
                    <input class="txt inline w-150" type="text" name="staffKey" placeholder="工号、姓名、手机号码">
                    <button type="button" class="btn btn-small btn-primary so-search" data-opt="{grid:'#gridBox-1',scope:'#sbox'}">查 询</button>
                </form>
            </div>
            <div class="gridCont">
                <div id="gridBox-1"></div>
            </div>
        </div>
    </div>
    <script id="chooseAuthUser" type="text/html">
        <div class="cont-grid">
            <div id="gridAuthUserBox"></div>
        </div>
        <p class="row-btn center">
            <input type="button" class="btn btn-primary btnSubmitChoose" name="btnSubmit" value="确定" />
            <input type="button" class="btn btn-cancel-cus" name="btnCancel" value="取 消" />
        </p>
    </script>

</body>
[#include '/WEB-INF/views/common/include_js.ftl']
<script type="text/javascript">
requirejs(['lodash',"pub"], function(_) {

    $('#instId').combogrid({
        delay: 500,
        mode: 'remote',
        panelWidth:250,
        fitColumns:true,
        clearIcon:true,
        idField:'ID',
        textField:'NAME',
        url:'${base}/ui/sys/autoComplete/query',
        queryParams: {
            tag:'sys.institution',
        },
        onSelect: function(v,record){

        },
        columns:[[
            {field:'NAME',title:'名称',width:100},
            {field:'SHORT_NAME',title:'简称',width:60}
        ]]
    });

    //左侧 datagrid
    $grid.newGrid("#gridBox-0", {
        fitColumns: false,
        pagination:false,
        offset:-50,
        columns: [
            [
                {
                    title: '操作',
                    field: 'op',
                    width: 50,
                    formatter: function(v, row, index) {
                        return '<span  rel=' + row.id + ' relIndex=' + index  + ' class="s-op s-op-del icon-del" title="删除此项目" rel="'+index+'"></span>';
                    }
                },
                {title: 'ID', field: 'reportAuthorizedId', hidden: true,width: 150},
                {title: '工号', field: 'secUserMainCode', width: 150},
                {title: '姓名', field: 'secUserName', width: 150},
            ]
        ],
        onDblClickRow:function (index, row) {

        },
        url: '${base}/ui/aams/auditRecordFxts/selectByAuditRecordId?auditRecordId=${auditRecordId}'
    });

    $('.btn-save').click(function(){
        //右侧表格上方 保存按钮事件
        var listLabelDict= $("#gridBox-1").datagrid("getSelections");
        if(_.isEmpty(listLabelDict)){
            $pop.alert.err('请选择标签！');
            return false;
        }
        $pop.confirm('是否保存所有选中项目',function(){
            $ajax.post({
                url: '${base}/ui/astds/dcgOperation/saveDcgOperation?dcgOperationId=${dcgOperation.id}&dcgOperationCode=${dcgOperation.icd9}&dcgOperationVersion=${dcgOperation.oprVersions}',
                data: listLabelDict,
                jsonData: true,
                calltip: true,
                success: function (rst) {
                    //setTimeout($pop.closePop({refreshGrid:true}),1000);
                    $grid.reload('#gridBox-0');
                    $grid.reload('#gridBox-1');
                }
            });
            return true;//return true关闭窗口
        },function(){
            return true;//return true关闭窗口
        });
    });

    //右侧 datagrid--------------------------------------------------
    $grid.newGrid("#gridBox-1",{
        pagination: true,
        fitColumns: false,
        columns:[[
            {title:'id',field:'ID',hidden: true},
            {title:'工号',field:'CODE',width:120},
            {title:'姓名',field:'NAME',width:100},
            {title:'性别',field:'SEX',width:100, formatter: function(val,row,index){if(val == 1){return '男';}else{return '女';}}},
            {title:'科室/部门',field:'DEPTNAME',width:280},
            {title:'所属机构',field:'INSTNAME',width:280},
            {
                title: '操作',
                field: 'op',
                width: 100,
                formatter: function(v, row, index) {
                    return '<span  rel=' + row.id + ' relIndex=' + index  + ' class="s-op s-op-auth authorize-button" title="授 权" onclick="authOpration(this);" ><a href="#">授 权</a></span>';
                }
            },
        ]],
        onBeforeLoad: function (param) {
            // param.instId=100002;
            if(!param.instId){
                return false;
            }
        },
        url:'${base}/ui/aams/auditRecordFxts/getStaffByCondition?auditRecordId=${auditRecordId}',
        offset : -55
    });

    $('.cont-grid').on('click', '.s-op-del', function () {
        var id = $(this).attr('rel');
        var idx = $(this).attr('relIndex');
        var rowData = $("#gridBox-0").datagrid("getRows")[idx];
        $pop.confirm('是否删除该记录？', function () {//确定
            $ajax.post('${base}/ui/aams/auditRecordFxts/saveReportAuthorized', rowData).done(function (rst) {
                $grid.reload('#gridBox-0');
                $grid.reload('#gridBox-1');
            });
            return true;
        }, function () {
            return true;
        });
    });

});

function authOpration(obj) {
    var idx = $(obj).attr('relIndex');
    var rowData = $("#gridBox-1").datagrid("getRows")[idx];
    var pDept = $pop.popTemForm({
        title: "请选择科室/部门",
        temId: 'chooseAuthUser',
        area: ['400px', '400px'],
        onPop: function ($p) {
            $grid.newGrid("#gridAuthUserBox",{
                pagination: false,
                fitColumns: false,
                singleSelect: true,
                columns:[[
                    {title:'id',field:'ID',hidden: true},
                    {title:'科室/部门',field:'name',width:280},
                ]],
                loader: function (params, success, err) {
                    success(rowData.Institutions);
                },
                onLoadSuccess: function (){

                },
                offset : -55,
                height: 350,
            });
            $('.btnSubmitChoose').click(function () {
                var rowNav = $('#gridAuthUserBox').datagrid('getSelected');
                if(!rowNav){
                    $pop.alert.err('请选择！');
                    return false;
                }
                rowNav.auditRecordId='${auditRecordId}';
                rowNav.secUserMainCode=rowData.CODE;
                rowNav.deptMasterCode=rowNav.id;
                $pop.confirm('是否保存所有选中项目',function(){
                    $ajax.postSync('${base}/ui/aams/auditRecordFxts/saveReportAuthorized', rowNav, false, false).done(function (rst) {
                        if (rst.code === 200 || rst.code === 201) {
                            $pop.success('保存成功!', function (index) {
                                //$pop.close(p);
                                $pop.closePop({popIndex: pDept});
                                //$grid.reload('#gridBox-0');
                                //$grid.reload('#gridBox-1');
                            });
                        } else {
                            $pop.alert(rst.msg);
                        }
                    });
                    return true;//return true关闭窗口
                },function(){
                    return true;//return true关闭窗口
                });
            });
            $(".btn-cancel-cus").click(function () {
                $pop.close(pDept);
            });
        },
        end: function () {
            // 只要弹窗关闭，就会触发
            $grid.reload("#gridBox-0");
            $grid.reload("#gridBox-1");
        },
        beforeSubmit: function (opt, $form, formData) {}
    });
};

</script>
</body>

</html>
