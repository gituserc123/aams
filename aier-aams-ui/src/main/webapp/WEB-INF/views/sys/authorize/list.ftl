<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>授权管理</title>
[#include "/WEB-INF/views/common/include_resources.ftl"]
<style type="text/css">
.mainContBox .searchHead{margin-top:0;padding:10px 0 5px 10px;}
.likeTabs{width:auto;}
.likeTabs .tab-last{border-right:0;}
.tabs li a.tabs-inner{padding:0 45px;}

.tabCont{position: absolute;left:0;right:0;top:34px;bottom:5px;overflow: hidden;}
.tabContHide{visibility: hidden;}
.cont-grid{overflow: hidden;zoom: 1;border-bottom: 5px solid #dfe2e6;}
.h2-title{line-height:25px;color: #00A0E9;}
.h2-title-a{background-color: #f9f9f9;}
.btn-dataSync,.btn-dataSync:focus{position: absolute;top:4px;right:5px;}

</style>
</head>

<body>
    <ul class="tabs likeTabs">
      <li class="tabs-first tabs-selected" rel="0"><a href="#" class="tabs-inner"><span class="tabs-title">按角色</span></a></li>
      <li rel="1"><a href="#" class="tabs-inner"><span class="tabs-title">按用户</span></a></li>
    </ul>
    <div class="tabCont tabCont-0">
          <div class="sideBarBox">
              <div class="sideTreeC">
                <ul id="ul-kindTree"></ul>
              </div>
          </div>
          <div class="mainContBox">
              <div class="searchHead">
                  <form id="sbox" class="soform form-enter">
                    <input type="hidden" class="txt" name="roleId" id="roleId"/>
                    <label class="lab-inline">医院/总部：</label>
					<select id="institution" name="institution" style="width:150px;"></select>
					<!-- 
                    <label class="lab-inline">科室/部门：</label>
                    <input id="txt-deptTree" class="txt w-150" type="text" name="dept"/>
                    -->
                    <input class="txt inline w-150" type="text" name="keyword" placeholder="工号、姓名、手机号码">
                    [@shiro.hasPermission name = "Authorize:view"]
                    <button type="button" class="btn btn-small btn-primary so-search"  data-opt="{grid:'#gridBox-0',scope:'#sbox',beforeSearch:'sideNodeChecked'}">查 询</button>
                    [/@shiro.hasPermission]
                  </form>
              </div>
              <div id="gridBox-0"></div>
          </div>
    </div>

    <div class="tabCont tabCont-1 tabContHide">
          <div class="searchHead">
              <form id="sbox2" class="soform form-enter">
                    <input type="hidden" class="txt" name="init" value="1" />
                    <label class="lab-inline">医院/总部：</label>
                    <select id="institution-staff" name="instId" style="width:150px;"></select>
                    <!-- 
                    <label class="lab-inline">科室/部门：</label>
                    <input id="txt-deptTree-staff" class="txt w-150" type="text" name="dept"/>
                    -->
                    <input class="txt inline w-150" type="text" name="staffKey" placeholder="工号、姓名、手机号码">
                    [@shiro.hasPermission name = "Authorize:view"]
                    <button type="button" class="btn btn-small btn-primary so-search"  data-opt="{grid:'#gridBox-1',scope:'#sbox2'}">查 询</button>
                    [/@shiro.hasPermission]
                </form>
          </div>
          <div id="gridBox-1"></div>
     </div>

</body>
[#include "/WEB-INF/views/common/include_js.ftl"]
<script type="text/javascript">
requirejs(['pub'],function () {

  var tabIndex = 0;
  var tabInit = ['',''];
  var $roleId = $('#roleId');
  var $txtDeptTree = $('#txt-deptTree');
  var $txtDeptTreeStaff = $('#txt-deptTree-staff');
  var roleName = null;

  var tabInitE = [function one() {
    tabInit[0] = true;
  },function two () {
    tabInit[1] = true;
    $('#gridBox-1').datagrid('load',{init:1});
  }];

  window.sideNodeChecked = function (){
        if($roleId.val()==''){
            $pop.alert('请在左边的角色树上选择需要新增用户的角色(非角色组),再进行查询操作');
            return false;
        }
        var sboxForm = $('#sbox').sovals();
        if(!sboxForm.institution){
            $pop.alert('请选择医院/总部,再进行查询操作');
            return false;
        }
        return true;
      }

  /** 左边角色树 */
  $('#ul-kindTree').tree({	
      animate : true,
      lines : true,
      url:'${base}/ui/sys/authorize/getRoleForTree',
      flatData: true,
        onBeforeSelect: function (node) {
            if (node.children) {
                return false;
            }
        },
      onClick : function (node) {
        $('#sbox').form('reset');
          $roleId.val(node.id);
          if(!node.children){
          	roleName = node.name;
            $('#gridBox-0').datagrid('load',{roleId:$roleId.val()});
          }
      }
  });
  
  var $tabLi = $('.likeTabs li');
  $tabLi.click(function () {
	      var ix = $tabLi.index(this);
	      tabIndex = $(this).attr('rel');
	      $tabLi.removeClass('tabs-selected');
	      $(this).addClass('tabs-selected');
	      $('.tabCont').addClass('tabContHide').eq(ix).removeClass('tabContHide');
	      if (!tabInit[ix]){//初始化
	      	  if(ix!=1){
	      	  	tabInitE[ix]();
	      	  }
	      }
	      return false;
	});

	
	$('#institution').combogrid({
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
            debugger;
			$txtDeptTree.combotree('clear');
			$txtDeptTree.combotree('reload', "${base}/ui/sys/authorize/getTreeByParent?parentId="+record.ID);	
		},
		columns:[[    
			{field:'NAME',title:'名称',width:100},
			{field:'SHORT_NAME',title:'简称',width:60}
		]]  
	});
	
	
	$txtDeptTree.combotree({
		animate : true, 
        panelWidth:'200px',
        panelHeight:'auto',
        panelMaxHeight:'200px',
        lines : true,
        clearIcon :true,
        onlyLeafCheck : true,
        flatData: true
	});
	
	$('#institution-staff').combogrid({
		delay: 500,    	  
		mode: 'remote',   
		panelWidth:250, 
		clearIcon:true,
		fitColumns:true,  
		required: false,
		idField:'ID',   
		textField:'NAME', 
		url:'${base}/ui/sys/autoComplete/query', 
		queryParams: {
			tag:'sys.institution',   
		},
		onSelect: function(v,record){
			$txtDeptTreeStaff.combotree('clear');
			$txtDeptTreeStaff.combotree('reload', "${base}/ui/sys/authorize/getTreeByParent?parentId="+record.ID);	
		},
		columns:[[    
			{field:'NAME',title:'名称',width:100},
			{field:'SHORT_NAME',title:'简称',width:60}
		]]  
	});
	
	
	$txtDeptTreeStaff.combotree({
		animate : true, 
        panelWidth:'200px',
        panelHeight:'auto',
        panelMaxHeight:'200px',
        lines : true,
        clearIcon :true,
        onlyLeafCheck : true,
        flatData: true
	});
	
	var toolbars = [
	  [@shiro.hasPermission name = "Authorize:save"]
	  [{iconCls: 'plus_sign', text: '新增', btnTitle:'新增用户角色授权', click: function () {
        var treeNode = $('#ul-kindTree').tree('getSelected');
        if(treeNode && !treeNode.children){
            $pop.iframePop({
                title: '新增用户角色授权 - 当前角色：'+roleName,
                content: '${base}/ui/sys/authorize/create/'+ treeNode.id,
                area: ['860px', '550px']
            }, '#gridBox-0');
        }else{
            $pop.alert('请在左侧的角色树上选择需要新增用户的角色(非角色组)！');
        }
       }
      }],
	  [/@shiro.hasPermission]
	  [@shiro.hasPermission name = "Role:view"]
	  [{iconCls: 'eye', text: '查看', btnTitle:'查看左侧选中角色已授权功能', click: function () {
	  	var treeNode = $('#ul-kindTree').tree('getSelected');
        if(treeNode && !treeNode.children){
            layer.open({
	          title: treeNode.name+'：已授权功能详情',
			  type: 2,
			  area: ['90%', '90%'],
			  fixed: true, //不固定
			  maxmin: false,
			  content: '${base}/ui/sys/role/openPerm?id='+$roleId.val()+'&isEdit=false'
			});
        }else{
            $pop.alert('请在左侧的角色树上选择需要查看的角色(非角色组)！');
        }
         
       }
      }],
	  [/@shiro.hasPermission]
	  []
	];
				
	
    $grid.newGrid("#gridBox-0",{
      	  tools: toolbars,
          checkOnSelect : false,
          selectOnCheck : false,
          fitColumns : false,
          columns:[[
            {title:'id',field:'id',hidden:true},
            [@shiro.hasPermission name = "Authorize:delete"]
            {title:'操作',field:'op',width:60,formatter :function (v,row,index) {
                 return '<span class="icon-del s-op-del" rel="'+row.id+'" title="解除角色授权"></span>'
            }},
            [/@shiro.hasPermission]
            {title:'所属机构',field:'instName',width:180},
            //{title:'所属科室/部门',field:'deptname',width:320,titletip:true},
            {title:'姓名',field:'staffName',width:100},
            {title:'角色名称',field:'roleId',width:120,formatter: function(value,index,row){
            	return roleName;
            }},
            {title:'授权时间',field:'modifyDate',width:150},
          ]],
          onBeforeLoad : function (param) {
              if(!param.roleId){
                return false;
              }
          },
          onLoadSuccess : function (data) {
          	$('.tabCont-0 .s-op-del').click(function () {
                  var id = $(this).attr('rel');
                  $ajax.post('${base}/ui/sys/authorize/delete',{id:id},'确定解除角色授权吗？').done(function (rst) {
                      if (rst.code==='200'||rst.code==='201') {
                          $('#gridBox-0').datagrid('reload');
                      };
                  });
              });
          },
          [@shiro.hasPermission name = "Authorize:view"]
          url:'${base}/ui/sys/authorize/search', // 根据角色查询 当前医院下，当前部门的用户，也可不用全部选择
          [/@shiro.hasPermission]
          offset :-40
      });
     
     [@shiro.hasPermission name = "Authorize:view"]
     tabInitE[0]();//初始化tab 0
     [/@shiro.hasPermission]
     

	 var staffParam = {};
      $grid.newGrid("#gridBox-1",{
          checkOnSelect : false,
          selectOnCheck : false,
          fitColumns : false,
          columns:[[
            {title:'id',field:'ID',hidden:true},
            {title:'操作',field:'op',width:90,formatter :function (v,row,index) {
                   var institution = staffParam.instId
                 return '[@shiro.hasPermission name = "Authorize:view"]<span class="s-op icon-eye s-op-look" rel="'+row.ID+'" longsrc="'+institution+'" title="查看权限"></span>[/@shiro.hasPermission] [@shiro.hasPermission name = "Authorize:view"]&nbsp;&nbsp;&nbsp;<span class="icon-users s-op-edit s-op-role" rel="'+row.ID+'" longsrc="'+institution+'" title="分配角色"></span>[/@shiro.hasPermission][@shiro.hasPermission name = "Authorize:dataScope"]&nbsp;&nbsp;&nbsp;<span class="icon-key21 s-op-edit s-op-dataScope" rel="'+row.id+'" title="分配数据权限"></span>[/@shiro.hasPermission]'
            }},
            {title:'工号',field:'CODE',width:120},
            {title:'姓名',field:'NAME',width:120,titletip:true},
            {title:'性别',field:'SEX',width:100, formatter: function(val,row,index){if(val == 1){return '男';}else{return '女';}}},
            {title:'联系方式',field:'TEL',width:100},
            {title:'角色名称',field:'roleNames',align:'left',width:300,titletip:true},
            {title:'所属科室/部门',field:'DEPTNAME',align:'left',width:300,titletip:true},
            {title:'所属机构',field:'INSTNAME',align:'left',width:150},
            //{title:'授权人',field:'creator',width:220,titletip:true},
            {title:'授权时间',field:'MODIFYDATE',width:220,titletip:true},
          ]],
          onBeforeLoad : function (param) {
              if(!param.init){
                return false;
              }
              staffParam = param;
              // staffParam.instId=100002;
          },
          
          onLoadSuccess : function (data) {
            $('.tabCont-1 .s-op-look').click(function () {
              var id = $(this).attr('rel');
              var instid = $(this).attr('longsrc');
              $pop.iframePop({
                title :'查看用户权限',
                content : '${base}/ui/sys/authorize/lookUpAuth/'+id+'/'+instid,
                area : ['90%','90%']
              });
            });
          
           $('.tabCont-1 .s-op-role').click(function () {
              var id = $(this).attr('rel');
              var instid = $(this).attr('longsrc');
              $pop.iframePop({
                title :'编辑用户权限',
                content : '${base}/ui/sys/authorize/update/'+id+'/'+instid, //加载用户已授权角色
                area : ['500px','400px']
              },'#gridBox-1');
            });
            
           [@shiro.hasPermission name = "Authorize:dataScope"]
           $('.tabCont-1 .s-op-dataScope').click(function () {
              var id = $(this).attr('rel');
              $pop.iframePop({
                title :'分配数据权限',
                content : '${base}/ui/sys/dataScope/load/'+id, //加载用户数据权限
                area : ['600px','500px']
              },'#gridBox-1');
            });
           [/@shiro.hasPermission]
          },
         
          url:'${base}/ui/sys/staff/getStaffByCondition',
          offset :-36
      });

});

</script>
</body>

</html>
