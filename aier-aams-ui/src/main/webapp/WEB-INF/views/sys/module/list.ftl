<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>模块管理 - 爱尔医院Ahis管理系统</title>
<link rel="icon" href="${base}/static/images/logo.ico" type="image/x-icon" />
[#include "/WEB-INF/views/common/include_resources.ftl"]
<style type="text/css">
.mainContBox .searchHead{margin-top:0;}
.s-dropC{position:absolute;top:-1px;right:5px;}
</style>
</head>
<body>

<div class="wrapper">
  <h2 class="h2-pageTitle">模块管理</h2>
  
  <div class="sideBarBox">
    <div class="sideTreeC">
      <ul id="ul-moduleTree"></ul>
    </div>
  </div>

  <div class="mainContBox">
    <div class="soform searchHead" style="padding-bottom:8px;">
      <form id="sbox" class="form-inline" method="post" action="${base}/ui/sys/module/page">
        <div class="item-group">
          <select name="searchType" class="drop inline easyui-combobox" data-options="limitToList:true,reversed:true,panelHeight:'auto'">
            <option value="module_name">模块名称</option>
            <option value="module_code">授权代码</option>
          </select><input type="text" class="txt inline" name="searchValue" placeholder="关键词">
          <button type="button" class="btn btn-small btn-primary so-search" data-opt="{grid:'#gridBox',scope:'#sbox'}">查 询</button>
          <div class="s-dropC">
          	  <span class="lh25" style="font-weight:bold;">切换系统：</span>
          	  <select name="platformCode" id="platform" class="drop inline easyui-combobox" data-options="limitToList:true,reversed:true,panelHeight:'auto'">
	            [#list platforms as p]
	            	<option value="${p.code}" [#if p.code=='apf']selected="selected"[/#if]>${p.name}</option>
	            [/#list]
	          </select>
          </div>
        </div>
      </form>
    </div>

    <div class="">
      <input type="hidden" id="parentId" name="parentId" value="${(rootModule.id)!}"/>
      <div id="gridBox"></div>
    </div>

  </div>
</div>
<div class="none"></div>

[#include "/WEB-INF/views/common/include_js.ftl"]
<script type="text/javascript">
requirejs(['pub'],function () {

var nowTreeNode  = null;

var popW = ($(window).width()-200)+'px';
  $(window).resize(function () {
    popW = ($(window).width()-200)+'px';
  });

var toolbars =
[
 [@shiro.hasPermission name = "Module:save"]
  [{iconCls:'plus_sign',text:'新增', click:function(){
	$pop.iframePop({
		  title:'模块管理 > 新增菜单 > '+$('#platform').find("option:selected").text(),
		  content : 'create/'+$('#parentId').val()+'/'+$('#platform').val(),
		  area : [popW,'100%'],
		  offset : 'rt',
          sureback : function(){
            $grid.reload('#gridBox');
            $('#ul-moduleTree').tree("reload");
          }
		});
  }}],
 [/@shiro.hasPermission]
  []
];


$grid.newGrid("#gridBox",{
    tools:toolbars,
    fitColumns : false,
    rownumbers : false,
    checkOnSelect : false,
    selectOnCheck : false,
    columns:[[
       {title:'id',field:'id',hidden:true},
       {title:'操作',field:'op',width:120,formatter: function (value,row,index) {
           return '[@shiro.hasPermission name = "Module:edit"]<span class="s-op s-op-edit icon-edit" rel="'+row.id+'" title="编辑"></span>　[/@shiro.hasPermission][@shiro.hasPermission name = "Module:delete"]<span class="s-op s-op-del icon-del"  rel="'+row.id+'" title="删除"></span>　[/@shiro.hasPermission][@shiro.hasPermission name = "Module:view"]<span class="s-op icon-eye s-op-look" rel="'+row.id+'" title="查看" style="margin-right:10px;"></span> [/@shiro.hasPermission]';
         }}
      ,{title:'模块名称',field:'modulename',width:120,align:'left'}
      ,{title:'授权代码',field:'modulecode',width:140,align:'left'}
      ,{title:'顺序',field:'orders',width:50}
      ,{title:'父模块',field:'PARENTNAME',width:120}
      ,{title:'树层级',field:'grade',width:50}
      ,{title:'描述',field:'remarks',align:'left',width:200}
      ,{title:'模块地址',field:'url',width:450,align:'left'}
    ]],
    url:$('#sbox').attr("action"),
    onLoadSuccess : function (data) {
      $('.s-op-look').click(function () {
        var id = $(this).attr('rel');
        $pop.iframePop({
          title:'模块管理 > 查看 > '+$('#platform').find("option:selected").text(),
          content : 'view/'+id,
          area : ['660px','540px']
        });
      });

      $('.s-op-edit').click(function () {
        var id = $(this).attr('rel');
        $pop.iframePop({
          title:'模块管理 > 修改菜单 > '+$('#platform').find("option:selected").text(),
          content : 'update/'+id,
          area : [popW,'100%'],
          offset : 'rt',
          sureback : function(){
            $grid.reload('#gridBox');
            $('#ul-moduleTree').tree("reload");
          }
        });
      });

      $('.s-op-del').click(function () {
        var id = $(this).attr('rel');
        $ajax.post('delete/'+id,null,'确定删除此模块吗？').done(function (rst) {
          if (rst.code==='201') {
            $grid.reload('#gridBox');
            $('#ul-moduleTree').tree("reload");
          }
        });
      });
    },
    offset : -2,
    onBeforeLoad : function(param){
      // 这里先注释掉  -- 20240427
      // if(!param.parentId){return false;}
    },
    url: '${base}/ui/sys/module/page'
  });

  [@shiro.hasPermission name = "Module:view"]
    var moduleTree = $('#ul-moduleTree').tree({
        animate : true,
        lines : true,
        url:'${base}/ui/sys/module/tree',
        flatData: true,
        onBeforeLoad : function(node,param){
          console.log('#ul-moduleTree');
          console.log(param);
    		if(!param.platformCode){return false;}
        },
        onClick : function (node) {
          var id = node.id;
          $('#parentId').val(id);
          $('#gridBox').datagrid('load',{parentId:id});
          nowTreeNode = node.id;
        },
        onLoadSuccess : function(node,data){
          if(nowTreeNode===null){
	          var parentId = data[0].id;
			  $('#gridBox').datagrid('load',{parentId:parentId});
		  }else{
		  	var node = $('#ul-moduleTree').tree('find', nowTreeNode);
		  	$('#ul-moduleTree').tree('select', node.target);
		  }
        }
    });
  [/@shiro.hasPermission]

$('#platform').combobox({    
    onSelect: function(record){
		var platform = record.value;
		// 刷新树，刷新右边list
		moduleTree.tree("options").queryParams = {platformCode : platform};
		nowTreeNode = null;
		$('#ul-moduleTree').tree("reload");
    }  
}); 

});
</script>

</body>
</html>
