<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>自评风险点</title>
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
        <label class="lab-inline">搜索：</label>
        <input class="txt inline w-150" type="text" name="searchTxt" placeholder="...">
       
        <button type="button" class="btn btn-small btn-primary so-search" data-opt="{grid:'#gridBox',scope:'#sbox'}">查 询</button>
    </form>
</div>

<div class="gridCont">
  <div id="gridBox"></div>
</div>

<div class="mainfooter">
    <span class="btn btn-primary btn-ok">选择</span>
    <span class="btn btn-cancel">取消</span>
</div>
</div>
</body>

[#include "/WEB-INF/views/common/include_js.ftl"]

<script type="text/javascript">
require(["pub"],function(){

	/** 查询出还未关联当前医院当前角色的用户 */
	$grid.newGrid("#gridBox",{
	      singleSelect : true,
          checkOnSelect : true,
	      pagination : true,
	      fitColumns : false,
	      columns:[[
	        {title:'id',field:'selfRiskId',checkbox:true},
            {title:'自评ID',field:'selfRiskIdShow',width:60},
            {title:'板块',field:'selfRiskBussinessType',width:120,hidden:true},
            {title:'板块',field:'selfRiskBussinessTypeTxt',width:120},
            {title:'编号',field:'selfRiskCode',width:100},
            {title:'风险类别',field:'selfRiskType',width:120},
            {title:'风险点',field:'selfRiskTitle',width:220}
	      ]],
	      onBeforeLoad: function (param) {

	      },
         url:'${base}/ui/aams/manualofaudit/getSelfRiskAll',
         offset : -55
	});

    $('.btn-ok').click(function () {

      var chkRows = $('#gridBox').datagrid('getChecked');
      if (chkRows.length == 0) {
         $pop.alert('请选择自评风险点!');
         return false;
      }
      $store.set('selfRisk',chkRows[0]);
      $pop.closePop();
    });


});

</script>
</body>

</html>
