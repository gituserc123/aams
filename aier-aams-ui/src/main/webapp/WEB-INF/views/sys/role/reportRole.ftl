<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>报表赋权- 爱尔医院AHIS管理系统</title>
<style type="text/css">
.roleTreeGridBox{}
.roleTreeGridBox .datagrid-row{height:20px;}
.roleTreeGridBox .datagrid-header-row{height:25px;}
.roleTreeGridBox .chk,.roleTreeGridBox .chk-all{margin-top:0;vertical-align: middle;width:16px;height:16px;}
.ul-powerTree .tree-file{margin-top:7px;}
.roleTreeGridBox label{font-weight: normal;cursor:hand;cursor:pointer;}
.roleTreeGridBox label:hover{color:#1DAFDA;font-weight: bold;}
.highlight { background: yellow; color: red; }
.pop-addOneItem {
	width: 496px;
	position: absolute;
	left: -4000px;
}
</style>
[#include "/WEB-INF/views/common/include_resources.ftl"]
</head>

<body>

<div>
	 <!-- 
	 <div class="soform searchHead">
        <div class="item-group"><input type="text" class="txt inline" id="moduleName" placeholder="模块名称">
         <button id="search" type="button" class="btn btn-small btn-primary " onclick="scrollToRole()">搜 索</button>
        </div>
    </div> 
    -->
    <div class="roleTreeGridBox ul-powerTree">
        <div id="mainGrid"></div>
    </div>
    <p class="row-btn">
        <input id="saveRole" type="button" class="btn btn-primary " name="btnSubmit" value="保 存" />

       <!--<input type="button" class="btn btn-cancel" name="btnCancel" value="取 消" /> -->
    </p>
</div>
<div class="pop-addOneItem">
	<div id="subGrid"></div>
</div>

[#include "/WEB-INF/views/common/include_js.ftl"]

<script type="text/javascript">
requirejs(['template','pub'],function (template) {
	var gridH = $(window).height()-60;
	var roleId = '${id}';
	var isEdit = ${isEdit};

	var toolbar = [
		[{iconCls:'plus_sign',text:'保存', click:function(){
			saveRole();
		}},'-',
		{iconCls:'eye',text:'查看', click:function(){
			open('add',treeNode);
		}}],
	];
	
	if(!isEdit){
		$('#saveRole').hide();
	}
	$('#saveRole').click(function(){
		saveRole();
	})
		
	function saveRole(){
		var param = [];
		var rowData = $("#mainGrid").datagrid("getChecked");//如果数据行为已选中则选中改行
		$.each(rowData,function(idx,val){//遍历JSON
			param.push(val.id);
        });
	      
		$ajax.post('${base}/ui/sys/role/updateRoleReport',{
		  	'roleId':roleId,
		  	'permIds':param
		  },true,false).done(function(rst){
		  	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		  	//parent.layer.close(index);
		});        
	}
	var pretime;
	 $grid.newGrid("#mainGrid",{
		//toolbar:toolbar,
		pagination: false,
		 checkOnSelect : false,
		selectOnCheck : false,
		singleSelect : false,
		fitColumns : false,
		showFooter : true,
		offset : 0,
	    url:'${base}/ui/sys/role/getReportByRoleId',
	    fitColumns : true,
	    queryParams:{'roleId':roleId,'isEdit':isEdit},
	    checkbox:isEdit,
	    columns:[[
		  {title:'id',field:'status',width:80,checkbox:isEdit,hidden:!isEdit},
          {title:'中文名称',field:'NAME',width:'200px',align:'left',titletip:true},
          {title:'平台',field:'PLATFORMCODE',width:'100px',align:'center',titletip:true},
          {title:'类型',field:'REPORTTYPEName',width:'100px',align:'center',titletip:true},
          {title:'路径',field:'PATH',width:'250px',align:'left',titletip:true},
          //{title:'参数',field:'PARAM',width:'200px',align:'left',titletip:true},
	    ]],
	    onBeforeLoad : function(node, param){
	    },
	    onCheckNode : function (row,checked) {
	    	checkAll(row, checked);
	    },
	    //checkState : "indeterminate"
	    onLoadSuccess : function (rowData) {
            $.each(rowData.rows,function(idx,val){//遍历JSON
                  if(val.status==true){
                    $("#mainGrid").datagrid("checkRow", idx);//如果数据行为已选中则选中改行
                  }
            });     
	        checkChkState();
	    },
	    height:gridH,
	});
	
	
	window.scrollToRole = function() {
		 findInPage();
	}
	function checkAll (row,checked) {
		if(row && row.power){
    		$.each(row.power,function (i,v) {
    			$('#'+v.id).prop('checked',checked); 
            });
        }
        if(row.children){
    		$.each(row.children,function (i,v) {
    			checkAll(v, checked);
            });
        }
	}
	
	function checkChkState () {
        $('.chk').click(function() {
          var _self = $(this);
          var chked = _self.prop('checked');
          _self[chked?'addClass':'removeClass']('chked');
          var $thisRow = _self.parents('.datagrid-row');
          var $rowAllChk = $thisRow.find('.chk-all');
          var tree = $('#mainGrid').treegrid('getData');
        });
	}
	
     
    var first = true;
    function findInPage() {  
    	$('#gridWrap span').each(function () {
           	$(this).removeClass('highlight');
        });
    	var flag;
        var searchText = $('#moduleName').val();
        if (searchText.length == 0) {
            $('#moduleName').focus();
            return false;
        }
        var regExp = new RegExp(searchText, 'g');
        $('#gridWrap span').each(function () {
            var html = $(this).html();
            if (regExp.test(html)){
            	flag = 1;
            	$(this).addClass('highlight');
            }
        });
        if (flag) {
            if ($(".highlight").size() > 0) {
                var _top = $(".highlight").eq(0).offset().top;
                $("html,body").animate({
                    "scrollTop": _top
                }, 100)
            }
        }
    }  
 
});
</script>
</body>

</html>
