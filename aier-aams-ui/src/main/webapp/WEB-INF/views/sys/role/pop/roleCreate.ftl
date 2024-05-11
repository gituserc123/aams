<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>角色管理</title>
[#include "/WEB-INF/views/common/include_resources.ftl"]
</head>

<body>
<div class="wrapper-popForm">
    <form class="soform form-validate form-enter pad-t15 solab-l" method="post" action="${base}/admin/security/role/create">

      <input name="id" type="hidden" />
		<input name="pid" type="hidden" />
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
					<label class="lab-item">角色名称：</label> 
					<input name="name" type="text" style="width:350px"  required="required"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="p11">
				<div class="item-one">
					<label class="lab-item">备注：</label> 
					<input name="remarks" type="text" style="width:350px"/>
				</div>
			</div>
		</div>
		
		<p class="row-btn center">
			<input type="button" class="btn btn-primary btn-easyFormSubmit"
				lay-submit name="btnSubmit" value="保存" /> <input type="button"
				class="btn btn-closePop" name="btnCancel" value="取 消" />
		</p>

    </form>
    
		<input id="_param" name="_param" type="hidden" />
</div>
[#include "/WEB-INF/views/common/include_js.ftl"]
<script type="text/javascript">
requirejs(['pub'],function () {
	//注意：parent 是 JS 自带的全局对象，可用于操作父页面
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	
	//让层自适应iframe
	$('#add').on('click', function(){
		var  data = $('form').sovals();
	    var param = {};
	    param.id = data.id;
	    param.parent = {};
	    param.parent.id = data.pid;
	    param.roleType = data.roleType;
	    param.roleName = data.name;
	    param.remarks = data.remarks;
	    $ajax.post('${base}/ui/sys/role/update',JSON.stringify(param),true, true).done(function(rst){
	    	parent.$('#ul-kindTree').tree('reload');    
	        parent.layer.close(index);
	    });        
	});
	//在父层弹出一个层
	$('#new').on('click', function(){
	    parent.layer.msg('Hi, man', {shade: 0.3})
	});
	//给父页面传值
	$('#transmit').on('click', function(){
	    parent.$('#parentIframe').text('我被改变了');
	    parent.layer.tips('Look here', '#parentIframe', {time: 5000});
	    parent.layer.close(index);
	});
	//关闭iframe
	$('#closeIframe').click(function(){
	    var val = $('#name').val();
	    if(val === ''){
	        parent.layer.msg('请填写标记');
	        return;
	    }
	    parent.layer.msg('您将标记 [ ' +val + ' ] 成功传送给了父窗口');
	    parent.layer.close(index);
	});

});
</script>
</body>

</html>
