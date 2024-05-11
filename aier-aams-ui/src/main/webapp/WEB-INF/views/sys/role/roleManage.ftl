<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>角色管理</title>
[#include "/WEB-INF/views/common/include_resources.ftl"]
<style type="text/css">
.mainContBox .searchHead{margin-top:0;padding:0px 0px 0px 0px;}
.likeTabs{width:auto;}
.likeTabs .tab-last{border-right:0;}
.tabs li a.tabs-inner{padding:0 45px;}

.tabCont{position: absolute;left:0;right:0;top:5px;bottom:5px;}
.tabContHide{visibility: hidden;}
.cont-grid{overflow: hidden;zoom: 1;border-bottom: 5px solid #dfe2e6;}
.btn-dataSync,.btn-dataSync:focus{position: absolute;top:4px;right:5px;}


.roleTreeGridBox{}
.roleTreeGridBox .datagrid-row{height:20px;}
.roleTreeGridBox .datagrid-header-row{height:25px;}
.roleTreeGridBox .chk,.roleTreeGridBox .chk-all{margin-top:0;vertical-align: middle;width:16px;height:16px;}
.ul-powerTree .tree-file{margin-top:7px;}
.roleTreeGridBox label{font-weight: normal;cursor:hand;cursor:pointer;}
.roleTreeGridBox label:hover{color:#1DAFDA;font-weight: bold;}

.pop-addOneItem {
	width: 496px;
	position: absolute;
	left: -4000px;
}
.layui-layer .pop-addOneItem {
	position: relative;
	left: 0;
}
</style>
</head>

<body>
<div class="sideBarBox">
    <input id="roleId" type="hidden" class="txt" name="roleId" value="-1" />
    <div class="sideTreeC">
      <ul id="ul-kindTree"></ul>
    </div>
</div>


<div class="mainContBox">
    <!-- <ul class="tabs likeTabs">
      <li class="tabs-first tabs-selected" rel="0"><a href="#" class="tabs-inner"><span class="tabs-title">角色赋权</span></a></li>
      <li rel="1"><a href="#" class="tabs-inner"><span class="tabs-title">用户列表</span></a></li>
    </ul>-->
      <div class="tabCont tabCont-0">
          <div class="soform searchHead bob-line">
		      <form id="sbox" class="form-inline form-enter soform form-validate" method="post">
		        <div class="item-group">
					<label class="lab-inline">角色名称：</label> 
        			<input type="text" class="txt w-210 canedit" name="roleName" required="required" data-options="required:true" maxlength="20">

        			<label class="lab-inline">类别：</label> 
        			[@ui_select class="drop easyui-combobox w-70" style="width:100px" name="roleType" tag="com.aier.cloud.basic.api.domain.enums.RoleTypeEnum"  dataOptions="editable:false"/]
      				<button type="button" class="btn btn-small btn-primary so-search" data-opt="{grid:'#mainGrid',scope:'#sbox'}">查 询</button>
		        </div>
		      </form>
		    </div>
		    
	      	<div id="mainGrid"></div>
      </div>
      <div class="tabCont tabCont-1 tabContHide">
        <div class="searchHead">
            <form id="sbox" class="soform form-enter">
                  <input id="roleIdB" type="hidden" class="txt" name="roleId" value="" />
                  <label class="lab-inline">省区：</label>
                  <select class="drop easyui-combobox w-90" name="province">
                        <option value=" "></option>
                        <option value="1">湖南省区</option>
                        <option value="2">广州省区</option>
                  </select>
                  <label class="lab-inline">医院：</label>
                  <select class="drop easyui-combobox w-180" name="hospital">
                      <option value=" "></option>
                      <option value="1">长沙爱尔眼科医院</option>
                      <option value="2">衡阳爱尔眼科医院</option>
                  </select>
                  <label class="lab-inline">部门：</label>
                  <select class="drop easyui-combobox w-90" name="dept">
                      <option value=" "></option>
                      <option value="1">视光科</option>
                      <option value="2">白内障</option>
                  </select>
                  <input class="txt inline w-250" type="text" name="key" value="" placeholder="工号、姓名、手机号码">
                  <button type="button" class="btn btn-small btn-primary so-search"  data-opt="{grid:'#gridBox-1',scope:'#sbox'}">查 询</button>
              </form>
        </div>
        <div id="gridBox-1"></div>
      </div>
</div>

<div id="tm_ignore" class="easyui-menu treeMenu" style="width:150px;display: none;">
[@shiro.hasPermission name = "Role:save"]
  <div id="mm-addKind">新增</div>
[/@shiro.hasPermission]
[@shiro.hasPermission name = "Role:edit"]
  <div id="mm-editKind">编辑</div>
[/@shiro.hasPermission]
[@shiro.hasPermission name = "Role:delete"]
  <div id="mm-delete">删除</div>
[/@shiro.hasPermission]
</div>


<!-- 编辑窗口-->
<div class="pop-addOneItem">
	<form id="updateForm" class="soform form-validate form-enter pad-t30" method="post"
		action="json/true.js" data-opt="{beforeCallback:'addOneHao'}">
		<input name="id" type="hidden" />
		<input name="pid" type="hidden" />
		<input name="pTreepath" type="hidden" />
		
		<div class="row">
			<div class="p11">
				<div class="item-one">
					<label class="lab-item">上级分类：</label> 
					<input name="parentName" type="text" readonly="true" disabled="true" style="width:350px"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="p11">
				<div class="item-one item-group">
					<label class="lab-item">名称：</label> 
					<input name="roleName" type="text" class="easyui-textbox" style="width:350px"  required="required" data-options="validType:['length[0,20]']"/>
				</div>
			</div>
		</div>
		<div class="row" id="showOrHide">
			<div class="p6">
				<div class="item-one">
					<label class="lab-item">类型：</label> 
					[@ui_select class="drop easyui-combobox w-70" style="width:100px" name="roleType" required="required" tag="com.aier.cloud.basic.api.domain.enums.RoleTypeEnum" uiShowDefault=false uiShowFirst=true dataOptions="editable:false"/]
				</div>
			</div>
			<!-- 
			<div class="p6">
				<div class="item-one">
					<label class="lab-item">数据范围：</label> 
					[@ui_select class="drop easyui-combobox w-70" style="width:100px" name="dataScope" required="required" tag="data_scope" uiShowDefault=false uiShowFirst=true dataOptions="editable:false"/]
				</div>
			</div>
			-->
		</div>
		<div class="row">
			<div class="p11">
				<div class="item-one">
					<label class="lab-item">备注：</label> 
					<input name="remarks" type="text"  class="easyui-textbox" style="width:350px" data-options="validType:['length[0,100]']"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="p11">
				<div class="item-one">
					<label class="lab-item">排序号：</label> 
					<input name="orders" class="easyui-numberspinner" style="width:80px;" required="required" data-options="min:1,max:999" value="1"/>  
				</div>
			</div>
		</div>
		
		<p class="row-btn center">
			<input type="button" class="btn btn-primary btn-easyFormSubmit"
				lay-submit name="btnSubmit" value="保存" /> <input type="button"
				class="btn btn-closePop" name="btnCancel" value="取 消" />
		</p>
	</form>
</div>

</body>


[#include "/WEB-INF/views/common/include_js.ftl"]

<script type="text/javascript">
requirejs(['pub'],function () {
	var toolbars =
	[
		[@shiro.hasPermission name = "Role:edit"]
		 [{iconCls:'plus_sign',text:'新增', click:function(){
	    	if(!treeNode){
	    		$pop.alert('请先在左侧选择角色');
	    		return;
	    	}
	    	if(treeNode.grade > 1){
              $pop.alert('无法在最后一级上添加子角色');
	    		return;
	    	}
	    	$('#parentId').val(treeNode.id);
			open('add',treeNode);
		  }}],
		[/@shiro.hasPermission]
	];
      var treeData = [];
      var tabroleId = ['',''];
      var roleId = $('#roleId').val();
      var treeNode = null;
      $('#roleIdB').val(roleId);
      $('#ul-kindTree').tree({
          animate : true,
          lines : true,
          url:'${base}/ui/sys/role/getForTree?_easyui_=COMB',
          queryParams:{id:roleId},
          flatData: true,
          onClick : function (node) {
          	treeNode = node;
          	$('#sbox').form('clear');
              $grid.load('#mainGrid',{id:node.id, grade:node.grade});
          },
          onContextMenu: function(e, node){
              e.preventDefault();
              // 查找节点
              $('#ul-kindTree').tree('select', node.target);
              treeNode = node;
	            $('#tm1').menu('show', {
	                left: e.pageX,
	                top: e.pageY
	            });
              // 显示快捷菜单

          },
          onLoadSuccess: function(node, data){
          	treeData = data[0];
          }
      });

      var $tabLi = $('.likeTabs li');
      $tabLi.click(function () {
          var ix = $tabLi.index(this);
          tabIndex = $(this).attr('rel');
          $tabLi.removeClass('tabs-selected');
          $(this).addClass('tabs-selected');
          $('.tabCont').addClass('tabContHide').eq(ix).removeClass('tabContHide');
          changeOrgLoadGrid();
          return false;
      });

	  //右键菜单
      $('#mm-addKind').click(function () {
         open('add', treeNode);
      });
      $('#mm-editKind').click(function () {
          open('edit', treeNode);
      });
      $('#mm-delete').click(function () {
      	  var param = {};
      	  param.roleId = treeNode.id;
          $ajax.post('${base}/ui/sys/role/delete',param,true, false).done(function(rst){
	    	$('#ul-kindTree').tree('reload');    
		  });       
      });
      //右键菜单

	$grid.newGrid("#mainGrid",{
		tools:toolbars,	
        url:'${base}/ui/sys/role/getForTree?_easyui_=COMB',
        pagination : false,
        method:'post',
        queryParams:{id:-1},
        columns:[[
          {title:'操作',field:'editPermit',width:40,formatter: function(value,row,index){
              var chkHtml = '';
              [@shiro.hasPermission name = "Role:edit"]if(row.id!=0)chkHtml += '<span class="s-op s-op-edit icon-edit" title="编辑" onClick="updateRole(\''+row.id+'\',\''+row.grade+'\')"></span>&nbsp;&nbsp;&nbsp;&nbsp;';[/@shiro.hasPermission]
              [@shiro.hasPermission name = "Role:delete"]if(row.id!=0)chkHtml += '<span class="s-op s-op-del icon-del" title="删除" onClick="deleteRole(\''+row.id+'\',\''+row.pid+'\')"></span>&nbsp;&nbsp;&nbsp;&nbsp;';[/@shiro.hasPermission]
              if(row.grade != 1 && row.grade != 0) {
	              [@shiro.hasPermission name = "Role:edit"]chkHtml += '<span class="s-op icon-list22" title="分配菜单" onClick="permRole(\''+row.id+'\',\''+row.name+'\',true)"></span>&nbsp;&nbsp;&nbsp;&nbsp;';[/@shiro.hasPermission]
	              [@shiro.hasPermission name = "Role:view"]chkHtml += '<span class="s-op icon-eye" title="查看菜单" onClick="permRole(\''+row.id+'\',\''+row.name+'\',false)"></span>&nbsp;&nbsp;&nbsp;&nbsp;';[/@shiro.hasPermission]
	              //[@shiro.hasPermission name = "Role:edit"]chkHtml += '<span class="s-op icon-table" title="分配报表" onClick="reportRole(\''+row.id+'\',\''+row.name+'\',true)"></span>&nbsp;&nbsp;&nbsp;&nbsp;';[/@shiro.hasPermission]
	              //[@shiro.hasPermission name = "Role:view"]chkHtml += '<span class="s-op icon-yulan1" title="查看报表" onClick="reportRole(\''+row.id+'\',\''+row.name+'\',false)"></span>&nbsp;&nbsp;&nbsp;&nbsp;';[/@shiro.hasPermission]
              }
              return chkHtml;
            }},
          {title:'上级',field:'parentName',width:30,titletip:true},
          {title:'角色名称',field:'name',width:30,titletip:true},
          {title:'类别',field:'roleTypeName',width:20,titletip:true},
          //{title:'数据范围',field:'dataScopeName',width:20},
          {title:'排序号',field:'orders',width:20,titletip:true},
          {title:'上次修改人',field:'modifer',width:20,titletip:true},
          {title:'上次修改时间',field:'modifyDate',width:40,titletip:true},
          {title:'备注',field:'remarks',width:80,align:'left',titletip:true},
        ]],
        onLoadSuccess : function (data) {
          var formData = data.rows;
        },
        offset :-50
    });

      $('#gridBox-0').treegrid({
            url:'${base}/ui/sys/role/getPermByRoleId',
            idField:'id',
            treeField:'name',
            fitColumns : false,
            columns:[[
                {title:'模块名称',field:'name',width:120},
                {title:'模块权限',field:'status',width:40,align:'center',formatter: function (value,row) {
                    return '<label class="pad-l10 pad-r10"><input type="checkbox" name="chkAll" class="chk-all" '+(value?"checked":"")+' /></label>';
                }},
                {title:'操作权限',field:'power',width:250,formatter: function (value,row) {
                  // window.console && console.log(row.power);
                  var roleRowHtml = '';
                  if(row.power){
                    roleRowHtml += '<div class="roleRow">';
                    $.each(row.power,function (i,v) {
                      roleRowHtml += '<label class="mar-r10"><input type="checkbox" class="chk'+(v.status?" chked":"")+'" name="'+v.id+'" '+(v.status?"checked":"")+' /> '+v.permName+'</label>';
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
                checkChkState();
            }
      });

      function checkChkState () {
        $('.chk-all').click(function () {
          var _self = $(this);
          var chked = _self.prop('checked');
          _self[chked?'addClass':'removeClass']('allChked');
          var $thisRow = _self.parents('.datagrid-row');
          var $treeRow = $thisRow.next('.treegrid-tr-tree');
          var $chks_a = $thisRow.find('.chk');//当前节点下
          var $chks_b = $treeRow.find('.chk');//子节点下
          var $chks_c = $treeRow.find('.chk-all');//子节点下
          $chks_a.prop('checked',chked);
          $chks_b.prop('checked',chked);
          $chks_c.prop('checked',chked);
          $chks_c[chked?'addClass':'removeClass']('allChked');
        });

        $('.chk').click(function() {
          var _self = $(this);
          var chked = _self.prop('checked');
          _self[chked?'addClass':'removeClass']('chked');
          var $thisRow = _self.parents('.datagrid-row');
          var $rowAllChk = $thisRow.find('.chk-all');
          if (chked) {
            $rowAllChk.prop('checked',chked);
          };
          if ($thisRow.find('.chked').length == 0) {
              $rowAllChk.prop('checked',false);
          };
        });
        
      }
      
      //保存事件
      $('#saveRole').on('click',function() {
        var param = [];
		$(".chk").each(function(){
			var checked = $(this).prop('checked');
		  if(checked === true){
		 		param.push($(this).prop('name'));
		 	}
		});
          
          $ajax.post('${base}/ui/sys/role/updateRolePerm',{
		    	'roleId':roleId,
		    	'permIds':param
		    },true,false).done(function(rst){
		  });        
      });
      
      window.updateRole= function (id, grade) {
        $ajax.post('${base}/ui/sys/role/getById',{id:id, grade:grade},false, false).done(function(rst){
	    	var node = rst.data;
	    	open('edit', node);
		});     
      }
      
      window.deleteRole = function(id, pid) {
         var param = {};
      	  param.roleId = id;
          $ajax.post('${base}/ui/sys/role/delete',param,true, false).done(function(rst){
            $('#ul-kindTree').tree('reload');  
	    	var node = $('#ul-kindTree').tree('find',pid);  
			$('#ul-kindTree').tree('expandTo',node);  
			$grid.load('#mainGrid',{id:pid});
		  });      
      }

	  window.permRole = function(id, name, isEdit) {
         layer.open({
          title: isEdit?'分配菜单- 当前角色：'+name:'查询已分配菜单 - 当前角色：'+name,
		  type: 2,
		  area: ['95%', '95%'],
		  fixed: true, //不固定
		  maxmin: false,
		  content: '${base}/ui/sys/role/openPerm?id='+id+'&isEdit='+isEdit
		});
      }
	  window.reportRole = function(id, name, isEdit) {
         layer.open({
          title: isEdit?'分配报表- 当前角色：'+name:'查询已分配报表 - 当前角色：'+name,
		  type: 2,
		  area: ['730px', '500px'],
		  fixed: true, //不固定
		  maxmin: false,
		  content: '${base}/ui/sys/role/reportRole?id='+id+'&isEdit='+isEdit
		});
      }
    function open(type, row){
    	if(type=='add'){
	    	if(row.grade == 1){
	    		$('#showOrHide').show();
	    	}else{
	    		$('#showOrHide').hide();
	    	}
    	}else{
    		if(row.grade == 2){
	    		$('#showOrHide').show();
	    	}else{
	    		$('#showOrHide').hide();
	    	}
    	}
		$pop.popForm({
			title: type=='add'?'新增':'编辑',
			target : $('.pop-addOneItem'),
			area: ['500px','320px'],
			beforePop : function ($formBox) {
				if(type=='add'){
					var param = {};
					param.parent= {};
					param.pid = row.id;
					param.parentName = row.name;
					param.orders = 1
					$('#updateForm').form('clear');
					$('#updateForm').form('load',param);
				}else{
					row.pid = row.parentId;
					$('#updateForm').form('clear');
					$('#updateForm').form('load',row);
				}
			},
			beforeSubmit : function (formData,$form,popid) {
			    var  data = $form.sovals();
			    var param = {};
			    param.id = data.id;
			    param.parent = {};
			    param.parent.id = data.pid;
			    param.roleType = data.roleType;
			    param.roleName = data.roleName;
			    param.remarks = data.remarks;
			    param.orders = data.orders;
			    param.dataScope = data.dataScope;
			    $ajax.post('${base}/ui/sys/role/update',JSON.stringify(param),true, true).done(function(rst){
			    	var paramId;
			    	if(type=='add'){
			    		paramId = row.id;
			    	}else{
			    		paramId = row.pid ? row.pid : row.parentId;
			    	}
			    	
			    	$('#ul-kindTree').tree('reload'); 
			    	var node = $('#ul-kindTree').tree('find',row.id);  
			    	$('#ul-kindTree').tree('expandTo',node);  
			    	$grid.load('#mainGrid',{id:paramId});
			    	// console.log('id:'+paramId);
			        $pop.close(popid);
			    });          
			
			}
	    });
	}

});

</script>
</body>

</html>
