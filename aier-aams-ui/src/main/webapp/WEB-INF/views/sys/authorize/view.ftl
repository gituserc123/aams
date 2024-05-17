<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>查看用户权限 - 爱尔医院</title>
[#include "/WEB-INF/views/common/include_resources.ftl"]
<style type="text/css">
.roleTreeGridBox{}
.roleTreeGridBox .datagrid-row{height:20px;}
.roleTreeGridBox .datagrid-header-row{height:25px;}
.roleTreeGridBox .chk,.roleTreeGridBox .chk-all{margin-top:0;vertical-align: middle;width:16px;height:16px;}
.ul-powerTree .tree-file{margin-top:7px;}
</style>
</head>

<body>

<div class="roleTreeGridBox ul-powerTree">
    <div id="gridBox"></div>
</div>
</body>

[#include "/WEB-INF/views/common/include_js.ftl"]

<script type="text/javascript">
requirejs(['pub'],function () {
  $grid.newGrid('#gridBox',{
            url:'${base}/ui/sys/authorize/lookUpAuth/${staffId!}/${instId!}',
            idField:'id',
            treeField:'name',
            fitColumns : true,
            flatData : true,
            columns:[[
                {title:'模块名称',field:'name',width:180,align:'left'},
                {title:'操作权限',field:'power',width:520,align:'left',formatter: function (value,row) {
                  var roleRowHtml = '';
                  if(row.power){
                    roleRowHtml += '<div class="roleRow">';
                    $.each(row.power,function (i,v) {
                      roleRowHtml += '<span class="s-chk-one"> '+v.text+'</span>';
                    });
                    roleRowHtml += '</div>';
                  }
                  return roleRowHtml;
                }}
            ]],
            onCheckNode : function (a,b) {
              // window.console && console.log(a,b);
            },
            onLoadSuccess : function (row,data) {
            }
      });


});

</script>
</body>

</html>
