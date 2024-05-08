<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>模块管理 - 爱尔医院AHIS管理系统</title>
[#include "/WEB-INF/views/common/include_resources.ftl"]
<style type="text/css">
.soform input.txt-order{position:relative;top:-1px;display:inline;width:55px;height:19px;line-height:21px;vertical-align:middle;}
.lab-val-o{display:block;padding:1px 0;overflow:hidden;}
</style>
</head>

<body>
<div class="pad-20">
  <form class="soform form-validate form-enter mar-b10" method="post" action="${base}/ui/sys/module/update">
    <input type="hidden" name="id" value="${(module.id)!}"/>
    <input type="hidden" name="treepath" value="${(module.treepath)!}" />
    <input type="hidden" name="platformCode" value="${(module.platformCode)!}"/>
    <h4 class="h4-form mar-b20"><span class="s-t">基础信息</span></h4>
    <div class="row">
      <div class="p6"><div class="item-one">
          <label class="lab-item">模块名称：</label>
          <input class="txt txt-validate so-pinyin" type="text" id="moduleName" name="moduleName" value="${(module.moduleName)!}" maxlength="40" noNull="请输入模块名称" validType="remote['${base}/ui/sys/module/checkName?previousName=${(module.moduleName)!"-"}','moduleName']" invalidMessage="模块名称已经存在" data-opt="{target:'#firstSpell',url:'${base}/ui/common/pinyin4j/getPinYinWord'}" />
      </div></div>
      <div class="p6"><div class="item-one">
         <label class="lab-item">模块代码：</label>
         <input class="txt txt-validate" type="text" name="moduleCode" value="${(module.moduleCode)!}" maxlength="30" placeholder="请输入模块代码" noNull="请输入模块代码" validType="remote['${base}/ui/sys/module/checkCode?previousCode=${(module.moduleCode)!"-"}','moduleCode']" invalidMessage="模块代码已经存在"/>
      </div></div>
    </div>
    <div class="row">
      <div class="p2"><div class="item-one">
          <label class="lab-item">顺序：</label>
          <input class="txt txt-validate" type="text" name="orders" value="${(module.orders)!}" maxlength="2" validType="pInt" noNull="请输入顺序" invalidMessage="顺序为正整数" />
      </div></div>
      <div class="p2"><div class="item-one solab-s">
          <label class="lab-item">是否外链：</label>
          <label class="lab-val"><input class="easyui-validatebox required" type="radio" value="true" name="outurl" [#if module.outurl] checked="checked" [/#if] maxlength="20" data-options="required:true" />是</label>
          <label class="lab-val"><input class="easyui-validatebox required" type="radio" value="false" name="outurl" [#if !module.outurl] checked="checked" [/#if] maxlength="20" data-options="required:true" />否</label>
          </div></div>
          <div class="p2"><div class="item-one solab-s">
          <label class="lab-item">是否显示：</label>
          <label class="lab-val"><input class="easyui-validatebox required" type="radio" value="true" name="display" [#if module.display] checked="checked" [/#if] data-options="required:true" />是</label>
          <label class="lab-val"><input class="easyui-validatebox required" type="radio" value="false" name="display" [#if !module.display] checked="checked" [/#if] data-options="required:true" />否</label>
      </div></div>
      <div class="p6"><div class="item-one">
          <label class="lab-item">URL：</label>
          <input class="txt easyui-validatebox required" type="text" name="url" value="${(module.url)!}"  placeholder="请输入URL" maxlength="200" data-options="required:true" missingMessage="请输入URL" />
      </div></div>
    </div>
    <div class="row">
      <div class="p6"><div class="item-one">
          <label class="lab-item">首拼码：</label>
        <input class="txt easyui-validatebox required" type="text" id="firstSpell" name="firstSpell" value="${(module.firstSpell)!}" maxlength="30" placeholder="请输入首拼码" data-options="required:true" missingMessage="请输入首拼码"/>
      </div></div>
      <div class="p6"><div class="item-one">
          <label class="lab-item">图标选择：</label>
          <span class="s-seledIcon [#if module.icon??]seledOne[/#if]" title="点击选择图片"><em id="em-mouduleIcon" class="${(module.icon)!}"></em></span> <span class="btn btn-small s-dropIcon"><b class="icon icon-pencil"></b> 选择</span>
          <input id="txt-selIcon" name="icon" type="hidden" value="${(module.icon)!}">
      </div></div>
    </div>
    
    <div class="row">
      <div class="p6"><div class="item-one">
        <label class="lab-item">机构：</label>
        <input class="easyui-combobox" type="text" id="insts" name="insts" value="${(module.insts)!}" style="width:100%" data-options="    
	        valueField: 'id',    
	        textField: 'name',
	        clearIcon : true,
	        url: '${base}/ui/sys/module/getHosps',  
	        formatter : function(row){
	           return '<span clsss=s-multi><em class=i-s-chk></em>'+row.name+'</span>';
	        },   
	    	multiple:true"
        />
      </div></div>
    </div>
    
    <div class="row">
      <div class="p6">
      <div class="p12">
      <div class="item-one">
          <label class="lab-item">父模块：</label>
        <input id="txt-deptTree" class="txt easyui-combotree" style="width:100%;" type="text" name="parDept" value="${(module.parent.moduleName)!}" data-options="
	        animate : true, 
	        panelHeight:'auto',
	        panelMaxHeight:'200px',
	        lines : true,
	        onlyLeafCheck : true,
	        url:'${base}/ui/sys/module/tree?platformCode=${(module.platformCode)!}', 
	        flatData: true,
	        required:true,
	        missingMessage : '请选择父模块',
	        onSelect : function(node) {
	        	$('#parentId').val(node.id);
	            $('#parentTreepath').val(node.treepath);
	        } 
        " />
             <input type="hidden" name="parent.id" id="parentId" value="${(module.parent.id)!}" />
             <input type="hidden" name="parent.treepath" id="parentTreepath" value="${(module.parent.treepath)!}" />
      </div></div>

      <div class="p12" style="margin-left:0;"><div id="moduleBox" class="item-one">
          <label class="lab-item">操作授权：</label>
           [#list module.permissions as p]
            <label class="lab-val lab-val-o">
            	<input type="checkbox" class="chk chk-module" name="permissions[${p_index}].permCode" value="${(p.permCode)!}" checked="checked"/>
            	<input class="txt txt-order easyui-validatebox required" type="text" name="permissions[${p_index}].orders" placeholder="顺序号" value="${(p.orders)!}" maxlength="2" validType="pInt" noNull="请输入顺序号" invalidMessage="顺序为正整数" />
            	${(p.permName)!}(${(p.permCode)!})
            	<input type="hidden" name="permissions[${p_index}].id" value="${(p.id)!}" />
            	<input type="hidden" name="permissions[${p_index}].permName" value="${(p.permName)!}" />
                <input type="hidden" name="permissions[${p_index}].remarks"  value="${(p.remarks)!}" />
                <input type="hidden" name="permissions[${p_index}].moduleId" value="${(p.moduleId)!}" />
            </label>
           [/#list]
      </div></div>
      
      </div>
      <div class="p6"><div class="item-one">
          <label class="lab-item">描述：</label>
          <textarea class="txta easyui-validatebox" name="remarks" placeholder="请输入描述">${(module.remarks)!}</textarea>
      </div></div>
    </div>
    
    <div class="row">
    </div>
    <p class="row-btn">
      <input type="button" class="btn btn-primary btn-easyFormSubmit" name="btnSubmit" value="保 存" />
      <input type="button" class="btn btn-cancel" name="btnCancel" value="取 消" />
    </p>
  </form>

  <form id="formModuleAdd" class="soform form-validate form-enter" method="post" data-opt="{beforeCallback:'addOneModule'}">
    <h4 class="h4-form mar-b20"><span class="s-t">动态新增</span></h4>
    <div class="row">
      <div class="p4"><div class="item-one">
          <label class="lab-item">名称：</label>
          <input class="txt easyui-validatebox required" type="text" name="permName" value="" placeholder="请输入名称" maxlength="50" data-options="required:true" missingMessage="请输入名称" />
      </div></div>
      <div class="p4"><div class="item-one">
          <label class="lab-item">授权代码：</label>
          <input class="txt easyui-validatebox required" type="text" name="permCode" placeholder="用来授权验证" value="" maxlength="50" data-options="required:true" missingMessage="请输入授权代码"/>
      </div></div>
      <div class="p4"><div class="item-one">
          <label class="lab-item">顺序：</label>
          <input class="txt easyui-validatebox required" type="text" name="orders" placeholder="请输入顺序" value="" maxlength="2" validType="pInt" noNull="请输入顺序" invalidMessage="顺序为正整数" />
      </div></div>
    </div>
    <div class="row">
      <div class="p12"><div class="item-one">
          <label class="lab-item">描述：</label>
          <textarea class="txta easyui-validatebox" name="remarks" placeholder="请输入描述"></textarea>
      </div></div>
    </div>
    <p class="row-btn">
      <button type="button" class="btn btn-warning btn-easyFormSubmit" msg="确定新增此数据？" noconfirm="true"><i class="icon icon-add"></i>新增</button>
    </p>
  </form>
</div>
[#include "/WEB-INF/views/common/include_icons.ftl"]
<script id="newModule" type="text/html">
  <label class="lab-val lab-val-o">
    <input type="checkbox" class="chk chk-module" name="permissions[{{num}}].permCode" value="{{permCode}}" checked="checked"/>
    <input class="txt txt-order easyui-validatebox required" type="text" name="permissions[{{num}}].orders" placeholder="顺序号" value="{{orders}}" maxlength="2" validType="pInt" noNull="请输入顺序号" invalidMessage="顺序为正整数" />
    {{permName}}({{permCode}})
    <input type="hidden" name="permissions[{{num}}].permName" value="{{permName}}" />
    <input type="hidden" name="permissions[{{num}}].remarks" value="{{remarks}}" />
  </label>
</script>

[#include "/WEB-INF/views/common/include_js.ftl"]

<script type="text/javascript">
requirejs(['template','pub'],function (template) {
  var moduleLen = $('.chk-module').length-1;
  window.addOneModule = function () {
      var vals = $('#formModuleAdd').sovals();
      moduleLen++;
      vals.num = moduleLen;
      $('#moduleBox').append(template('newModule',vals));
      return false;
  }
  $('.s-dropIcon').click(function () {
    lay = layer.open({
      title : '单击图标进行选择',
      type:1,
      content : $('.iconsbox'),
      area :['730px','510px']
    })
  });
  $('.ul-icons').find('li').click(function () {
    var iconCls = $(this).attr('class');
    $('.s-seledIcon').addClass('seledOne');
    $('#em-mouduleIcon').attr('class',iconCls);
    $('#txt-selIcon').val(iconCls);
    layer.close(lay);
  });
});
</script>
</body>

</html>
