<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>角色管理 - ${setting("siteName")}</title>
[#include "/WEB-INF/views/common/include_resources.ftl"]
</head>
<body data-js="sys:role">
<div class="wrapper">
  <h2 class="h2-pageTitle">角色管理</h2>
    <div class="searchHead">
      <form id="sbox" class="sobox" method="post" action="${base}/admin/security/role/listPage">
        <div class="item-group">
          <select name="searchProperty" class="txt inline">
            <option value="name">角色名称</option>
          </select>
          <input type="text" class="txt inline" name="searchValue" placeholder="关键词">
          <button type="button" class="btn btn-small btn-primary so-search"  data-opt="{grid:'#gridBox',scope:'#sbox'}">查 询</button>
        </div>
      </form>
    </div>
    <div class="gridWrap">
      <div id="gridBox"></div>
    </div>
</div>

<div class="none"></div>
<script>
var toolbars =
[
   [@shiro.hasPermission name = "Role:save"]
   [{iconCls:'plus_sign',text:'新增',url:'create',noMax: true,popHeight:300,popWidth:420,title:'角色信息-新增'}],
   [/@shiro.hasPermission]
   [@shiro.hasPermission name = "Role:edit"]
   [{iconCls:'pencil',text:'修改',onlyOne:true,popHeight:400,popWidth:560,popMax:true,url:'update/{id}',title:'角色信息-修改',notNull:'请选择你要修改的记录!'}],
   [/@shiro.hasPermission]
   [@shiro.hasPermission name = "Role:delete"]
   [{iconCls:'trash',text:'删除',url:'delete/{id}',notNull:'请 <strong class="red">勾选</strong> 需要删除的一项或多项！', ajax:true}],
   [/@shiro.hasPermission]
   [@shiro.hasPermission name = "Role:view"]
   [{iconCls:'eye',text:'查看权限',url:'gotoRolePower/{id}',onlyOne:true,popMax:true,popHeight:420,popWidth:700,title:'角色信息-查看权限',notNull:'请选择你要查看权限的用户行！'}],
   [/@shiro.hasPermission]
   [@shiro.hasPermission name = "Role:assign"]
   [{iconCls:'user_md',text:'设置权限',url:'setRolePower/{id}',onlyOne:true,popMax:true,popHeight:420,popWidth:700,title:'角色信息-设置权限',notNull:'请选择你要设置权限的用户行！'}],
   [/@shiro.hasPermission]
   []
]
</script>
[#include "/WEB-INF/views/common/include_js.ftl"]
</body>
</html>