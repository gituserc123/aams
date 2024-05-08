<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>模块管理 - 爱尔医院AHIS管理系统</title>
[#include "/WEB-INF/views/common/include_resources.ftl"]
</head>

<body>
<div class="pad-20">
  <form class="soform form-validate form-enter mar-b10" method="post" action="${base}/ui/sys/module/update">
    <input type="hidden" name="id" value="${(module.id)!}"/>
    <input type="hidden" name="treepath" value="${(module.treepath)!}" />
    <h4 class="h4-form mar-b20"><span class="s-t">基础信息</span></h4>
    <div class="row">
      <div class="p6"><div class="item-one">
          <label class="lab-item">名称：</label>
          <input class="txt easyui-validatebox required" type="text" id="moduleName" name="moduleName" value="${(module.moduleName)!}" placeholder="请输入模块名称" maxlength="20" data-options="required:true" missingMessage="请输入模块名称"/>
      </div></div>
      <div class="p6"><div class="item-one">
          <label class="lab-item">顺序：</label>
        <input class="txt easyui-validatebox required" type="text" name="orders" placeholder="请输入优先级" value="${(module.orders)!}" maxlength="20"  data-options="required:true" validType="pInt" missingMessage="请输入顺序" invalidMessage="顺序为正整数" />
      </div></div>
    </div>
    <div class="row">
      <div class="p6"><div class="item-one">
          <label class="lab-item">模块代码：</label>
        <input class="txt easyui-validatebox required" type="text" name="moduleCode" value="${(module.moduleCode)!}" placeholder="请输入模块代码" data-options="required:true" missingMessage="请输入模块代码" />
      </div></div>
      <div class="p6"><div class="item-one">
          <label class="lab-item">URL：</label>
          <input class="txt easyui-validatebox required" type="text" name="url" value="${(module.url)!}" placeholder="请输入URL" data-options="required:true" missingMessage="请输入URL" />
      </div></div>
    </div>
    <div class="row">
      <div class="p6"><div class="item-one">
          <label class="lab-item">首拼码：</label>
        <input class="txt easyui-validatebox required" type="text" id="firstSpell" name="firstSpell" value="${(module.firstSpell)!}" placeholder="请输入首拼码" data-options="required:true" missingMessage="请输入首拼码"/>
      </div></div>
      <div class="p6"><div class="item-one">
          <label class="lab-item">图标选择：</label>
          <span class="s-seledIcon [#if module.icon??]seledOne[/#if]" title="点击选择图片"><em id="em-mouduleIcon" class="${(module.icon)!}"></em></span> <span class="btn btn-small s-dropIcon"><b class="icon icon-pencil"></b> 选择</span>
          <input id="txt-selIcon" name="icon" type="hidden" value="${(module.icon)!}">
      </div></div>
    </div>
    <div class="row">
      <div class="p6"><div class="item-one">
          <label class="lab-item">父模块：</label>
             <input class="txt txt-validate so-pop" data-opt="{type:'tree',url:'${base}/ui/sys/module/tree',width:'80%',height:'90%',value:'name',valueId:'parentId',title:false}" type="text" name="modname" value="${(module.parent.moduleName)!}" maxlength="30" placeholder="请选择父模块" readonly="readonly" noNull="请选择父模块" />
             <input type="hidden" name="parent.id" id="parentId" value="${(module.parent.id)!}" />
      </div></div>
      <div class="p6"><div class="item-one">
          <label class="lab-item">描述：</label>
          <textarea class="txta easyui-validatebox" name="remarks" placeholder="请输入描述">${(module.remarks)!}</textarea>
      </div></div>
    </div>
    
    <div class="row">
      <div class="p12"><div id="moduleBox" class="item-one">
          <label class="lab-item">操作授权：</label>
           [#list module.permissions as p]
            <label class="lab-val">
            	<input type="text"  value="${(p.permName)!}" />
                <input type="text"  value="${(p.permCode)!}" />
                <input type="text"  value="${(p.remarks)!}" />
                <input type="text"  value="${(p.orders)!}" />
            </label><br/>
           [/#list]
      </div></div>
    </div>
  </form>
  
</div>
[#include "/WEB-INF/views/common/include_icons.ftl"]
[#include "/WEB-INF/views/common/include_js.ftl"]

</body>

</html>
