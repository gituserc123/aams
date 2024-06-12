<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>新增用户授权 - 当前角色：${role.roleName!}</title>
[#include "/WEB-INF/views/common/include_resources.ftl"]
<style type="text/css">
.mainfooter{padding:12px 10px 0;text-align: right;}
</style>
</head>

<body>
<div class="wrapper">
<div class="searchHead">
    <form id="sbox" class="soform form-enter">
        <input type="hidden" class="txt" name="init" value="1" />
        <input type="hidden" class="txt" name="roleId" id="roleId" value="${role.id!}" />
        <label class="lab-inline">医院/总部：</label>
        <select id="instId" name="instId" style="width:150px;" data-options="required:false"></select>
        
        <!--<label class="lab-inline">科室/部门：</label>
        <input id="txt-deptTree" class="txt w-150" type="text" name="dept"/>-->
        <input class="txt inline w-150" type="text" name="staffKey" placeholder="工号、姓名、手机号码">
       
        <button type="button" class="btn btn-small btn-primary so-search" data-opt="{grid:'#gridBox',scope:'#sbox'}">查 询</button>
    </form>
</div>

<div class="gridCont">
  <div id="gridBox"></div>
</div>

<div class="mainfooter">
    <span class="btn btn-primary btn-ok">添加</span>
    <span class="btn btn-cancel">取消</span>
</div>
</div>
</body>

[#include "/WEB-INF/views/common/include_js.ftl"]

<script type="text/javascript">
require(["pub"],function(){

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
			$('#txt-deptTree').combotree('clear');
			$('#txt-deptTree').combotree('reload', "${base}/ui/sys/authorize/getTreeByParent?parentId="+record.ID);	
		},
		columns:[[    
			{field:'NAME',title:'名称',width:100},
			{field:'SHORT_NAME',title:'简称',width:60}
		]]  
	});
	
	
	$('#txt-deptTree').combotree({
		animate : true, 
        panelWidth:'200px',
        panelHeight:'auto',
        panelMaxHeight:'200px',
        lines : true,
        clearIcon :true,
        onlyLeafCheck : true,
        flatData: true
	});

	/** 查询出还未关联当前医院当前角色的用户 */
	$grid.newGrid("#gridBox",{
	      checkOnSelect : false,
	      selectOnCheck : false,
	      singleSelect : false,
	      ctrlSelect : true,
	      pagination : true,
	      fitColumns : false,
	      columns:[[
	        {title:'id',field:'ID',checkbox:true},
            {title:'工号',field:'CODE',width:120},
            {title:'姓名',field:'NAME',width:100},
            {title:'性别',field:'SEX',width:100, formatter: function(val,row,index){if(val == 1){return '男';}else{return '女';}}},
            {title:'科室/部门',field:'DEPTNAME',width:120},
            {title:'所属机构',field:'INSTNAME',width:180}
	      ]],
	      onBeforeLoad: function (param) {
              param.instId=100002;
	          if(!param.instId){
                return false;
              }
	      },
	      // url:'${base}/ui/sys/authorize/getStaffListNotAuthorize',
         url:'${base}/ui/sys/staff/getStaffByCondition',
         offset : -55
	});

    $('.btn-ok').click(function () {

      var chkRows = $('#gridBox').datagrid('getChecked');
      var idArr = [];
      $.each(chkRows,function(i,v){
          if (!idArr.includes(v.ID)) {
              idArr.push(v.ID);
          }
      });
      var sendData = {
     	roleId : $('#roleId').val(),
     	//instId : $('#instId').combobox('getValue'),
        instId : 100002,
     	staffIds : idArr.join(',')
      };
      
      if (!sendData.staffIds) {
      	$pop.alert('请选择用户添加到当前角色！');
      	return false;
      }
      
      $ajax.post('${base}/ui/sys/authorize/create',sendData,true).done(function (rst) {
        if (rst.code==='200'||rst.code==='201') {
          setTimeout(function () {
            $pop.closePop({ refreshGrid: true });//关闭弹窗
          }, 400);
        };
      });
    });


});

</script>
</body>

</html>
