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
<div class="wrapper pad-10">
<div class="searchHead bob-line">
    <form id="sbox" class="soform form-enter">
        <input type="hidden" class="txt" name="init" value="1" />
        <input type="hidden" class="txt" name="roleId" id="roleId" value="${role.id!}" />
        <label class="lab-inline">医院/总部：</label>
        ${institutionName!}
        <label class="lab-inline">科室/部门：</label>
        <input id="txt-deptTree" class="txt w-150" type="text" name="dept"/>
        <input class="txt inline w-150" type="text" name="keyword" placeholder="工号、姓名、手机号码">
       
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

	
	$('#txt-deptTree').combotree({
		animate : true, 
        panelWidth:'200px',
        panelHeight:'auto',
        panelMaxHeight:'200px',
        lines : true,
        clearIcon :true,
        onlyLeafCheck : true,
        flatData: true,
        url:'${base}/ui/sys/authorize/hospital/getTreeByParent?parentId=${institution!}'
	});

	/** 查询出还未关联当前医院当前角色的用户 */
	$grid.newGrid("#gridBox",{
	      checkOnSelect : false,
	      selectOnCheck : false,
	      singleSelect : false,
	      ctrlSelect : true,
	      pagination : false,
	      fitColumns : false,
	      columns:[[
	        {title:'id',field:'id',checkbox:true},
            {title:'工号',field:'code',width:120},
            {title:'姓名',field:'staffname',width:100},
            {title:'联系方式',field:'tel',width:100},
            {title:'科室/部门',field:'deptname',width:280,titletip:true},
            {title:'所属机构',field:'instname',width:180}
	      ]],
	      onBeforeLoad: function (param) {
	          if(!param.roleId){
                return false;
              }
	      },
	      url:'${base}/ui/sys/authorize/hospital/getStaffListNotAuthorize', 
	      offset : -55
	});

    $('.btn-ok').click(function () {

      var chkRows = $('#gridBox').datagrid('getChecked');
      var idArr = [];
      $.each(chkRows,function(i,v){
      	idArr.push(v.id);
      });
      var sendData = {
     	roleId : $('#roleId').val(),
     	staffIds : idArr.join(',')
      };
      
      if (!sendData.staffIds) {
      	$pop.alert('请选择用户添加到当前角色！');
      	return false;
      }
      
      $ajax.post('${base}/ui/sys/authorize/hospital/create',sendData,true).done(function (rst) {
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
