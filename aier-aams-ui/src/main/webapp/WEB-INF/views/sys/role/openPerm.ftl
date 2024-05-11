<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>角色赋权- 爱尔医院AHIS管理系统</title>
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
		$(".chk").each(function(){
			var checked = $(this).prop('checked');
			if(checked === true){
				param.push($(this).prop('id'));
			}
		});
	      
		$ajax.post('${base}/ui/sys/role/updateRolePerm',{
		  	'roleId':roleId,
		  	'permIds':param
		  },true,false).done(function(rst){
		  	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		  	//parent.layer.close(index);
		});        
	}
	var pretime;
	$('#mainGrid').treegrid({
		//toolbar:toolbar,
		showFooter : true,
		method:'post',
	    url:'${base}/ui/sys/role/getPermByRoleId',
	    idField:'id',
	    treeField:'name',
	    fitColumns : true,
	    queryParams:{'roleId':roleId,'isEdit':isEdit},
        flatData : true,onlyLeafCheck:true,
	    checkbox:isEdit,
	    columns:[[
	        {title:'模块名称',field:'name', width:'20%'},
	        {title:'操作权限',field:'power',width:'80%',formatter: function (value,row) {
	        	if(isEdit){
			          var roleRowHtml = '';
			          if(row.power){
			            roleRowHtml += '<div class="roleRow">';
			            $.each(row.power,function (i,v) {
			              roleRowHtml += '<label class="mar-r10"><input type="checkbox" class="chk'+(v.status?" chked":"")+'" id="'+v.id+'" '+(v.status?"checked":"")+' /> '+v.permName+'</label>';
			            });
			            roleRowHtml += '</div>';
			          }
			          return roleRowHtml;s
		        }else{
			          var roleRowHtml = '';
	                  if(row.power){
	                    roleRowHtml += '<div class="roleRow">';
	                    $.each(row.power,function (i,v) {
	                      roleRowHtml += '<span class="s-chk-one"> '+v.permName+'</span>';
	                    });
	                    roleRowHtml += '</div>';
	                  }
	                  return roleRowHtml;
                }
	        }}
	    ]],
	    onBeforeLoad : function(node, param){
	    	if(param.id) return false;
	    },
	    onCheckNode : function (row,checked) {
	    	checkAll(row, checked);
	    },
	    //checkState : "indeterminate"
	    onLoadSuccess : function (row,data) {
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
