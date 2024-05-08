## 添加 txt-validate  快捷验证class
> 添加：
- 71-73行：

```javascript
if(_d=='validatebox'){
r = $(".easyui-"+_d+",.txt-validate",_c);
}
```
- 6636-6643行：
```javascript
var needMsg = $(_4d1).attr('noNull');
if(needMsg){
$(_4d1).addClass('required');
opts.required = true;
opts.missingMessage = needMsg;
}
```



## 添加clearIcon 参数
> 修改：
- 6080行：
```javascript
clearIcon:false,
```

- 6863 - 6871 行：
```javascript
if(opts.clearIcon){
    opts.icons.push({
      iconCls:'easyui-clearIcon',
      handler:function(e){
        var $tar = $(e.handleObj.data.target);
        $tar.textbox('clear');
      }
  });
};
```

- 6948行：
```javascript
var _508=_507.find(".textbox-icon:not('.easyui-clearIcon')");
```



## 增加combo隐藏行的值，usingSign:"0"，不显示当前行
>修改：
- 14313行：
```javascript
//var cls="combobox-item"+(String(row.usingSign)==='0'?" combobox-item-hidden":"")+(row.disabled?" combobox-item-disabled":"")+(g?" combobox-gitem":"");
var cls="combobox-item"+(String(row.usingSign)==='0'?" combobox-item-disabled":"")+(row.disabled?" combobox-item-disabled":"")+(g?" combobox-gitem":"");
```
>修改，增加useAllData来判断 usingSign 是否可选中：
var cls="combobox-item"+((String(row.usingSign)==='0'&&!opts.useAllData)?" combobox-item-disabled":"")+(row.disabled?" combobox-item-disabled":"")+(g?" combobox-gitem":"");


## 修正remote 远程验证 ie下中文不请求的问题
>修改：
- 6830 - 6839行：
```javascript
var urlAp = _4f1[0].split('?');
var urlP = urlAp[1];
if(urlP){
  var strps = urlP.split("&");
  for(var i = 0; i < strps.length; i ++) {
    data[strps[i].split("=")[0]]=unescape(strps[i].split("=")[1]);
  }
}
data[_4f1[1]]=_4f0;
var _4f2=$.ajax({url:urlAp[0],dataType:"json",data:data,async:false,cache:false,type:"post"}).responseText;
```

## 修改 limitToList 属性为 true
>修改：
- 14315行：
```javascript
},mode:"local",method:"post",url:null,data:null,queryParams:{},showItemIcon:false,limitToList:true,view:_aa1,keyHandler:{up:function(e){
```

## 修改 combo 默认 panelHeight 为auto , panelMaxHeight 为200 ，reversed为true
>修改：
- 13802行：
```javascript
$.fn.combo.defaults=$.extend({},$.fn.textbox.defaults,{inputEvents:{click:_a1b,keydown:_a1f,paste:_a1f,drop:_a1f},panelWidth:null,panelHeight:'auto',panelMinWidth:null,panelMaxWidth:null,panelMinHeight:null,panelMaxHeight:200,panelAlign:"left",reversed:true,multiple:false,multivalue:true,selectOnNavigation:true,separator:",",hasDownArrow:true,delay:200,keyHandler:{up:function(e){
```

## 修改 combo 默认 panelHeight 为auto , panelMaxHeight 为200 ，reversed为true
>修改：
- 13802行：
```javascript
$.fn.combo.defaults=$.extend({},$.fn.textbox.defaults,{inputEvents:{click:_a1b,keydown:_a1f,paste:_a1f,drop:_a1f},panelWidth:null,panelHeight:'auto',panelMinWidth:null,panelMaxWidth:null,panelMinHeight:null,panelMaxHeight:200,panelAlign:"left",reversed:true,multiple:false,multivalue:true,selectOnNavigation:true,separator:",",hasDownArrow:true,delay:200,keyHandler:{up:function(e){
```


## 修改 分页默认一页20行
>修改：
- 1326行：
```javascript
$.fn.pagination.defaults={total:1,pageSize:20,pageNumber:1,pageList:[10,20,30,50],loading:false,buttons:null,showPageList:true,showPageInfo:true,showRefresh:true,links:10,layout:["list","sep","first","prev","sep","manual","sep","next","last","sep","refresh","info"],onSelectPage:function(_d2,_d3){
```
- 11547行：
```javascript
$.fn.datagrid.defaults=$.extend({},$.fn.panel.defaults,{sharedStyleSheet:false,frozenColumns:undefined,columns:undefined,fitColumns:false,resizeHandle:"right",resizeEdge:5,autoRowHeight:true,toolbar:null,striped:false,method:"post",nowrap:true,idField:null,url:null,data:null,loadMsg:"Processing, please wait ...",emptyMsg:"",rownumbers:false,singleSelect:false,ctrlSelect:false,selectOnCheck:true,checkOnSelect:true,pagination:false,pagePosition:"bottom",pageNumber:1,pageSize:20,pageList:[10,20,30,40,50],queryParams:{},sortName:null,sortOrder:"asc",multiSort:false,remoteSort:true,showHeader:true,showFooter:false,scrollOnSelect:true,scrollbarSize:18,rownumberWidth:30,editorHeight:24,headerEvents:{mouseover:_6af(true),mouseout:_6af(false),click:_6b3,dblclick:_6b8,contextmenu:_6bb},rowEvents:{mouseover:_6bd(true),mouseout:_6bd(false),click:_6c4,dblclick:_6ce,contextmenu:_6d2},rowStyler:function(_86d,_86e){
```


## 修正datagrid updateRow时，行号显示不正确
>修改：
- 11332行：
```javascript
var _83d=parseInt(_83b)+1;
```




## 修正 grid col price功能 ， titletip功能
- 11347行：
```javascript
var _8402="";
if(col.price){col.align = 'right';}
if(!col.checkbox){
if(col.align){
_8402+="text-align:"+col.align+";";
}
if(!opts.nowrap){
_8402+="white-space:normal;height:auto;";
}else{
if(opts.autoRowHeight){
_8402+="height:auto;";
}
}
}

if(col.price){
col.formatter= col.formatter || function(r){
if(r!==undefined){return (Math.round(r*100)/100).toFixed(2);}
}
}
var titleCls = col.titletip?' nowrap':'';
var tdTxt = col.formatter?col.formatter(_83f,_83c,_83b):_83f;
// var tdTitle = col.titletip?(' title='+tdTxt+''):'';
var tdTitle = '';
if(_83f){
var formatTitle = (_83f+'').replace(/[\r\n]/g,"&#13;");
formatTitle = formatTitle.replace(/[\t ]/g,"&nbsp;");
formatTitle = formatTitle.replace(/</g,"&lt;");
formatTitle = formatTitle.replace(/>/g,"&gt;");
formatTitle = formatTitle.replace(/"/g,"&quot;");
tdTitle = !col.formatter?(' title="'+formatTitle+'"'):'';
}
// var tdTitle = ' title='+tdTxt;
cc.push("<td field=\""+_83e+"\" "+cls+" "+_840+" "+tdTitle+">");
cc.push("<div style=\""+_8402+"\" ");
cc.push(col.checkbox?"class=\"datagrid-cell-check\"":"class=\"datagrid-cell "+col.cellClass+titleCls+"\"");
cc.push(">");
if(col.checkbox){
cc.push("<input type=\"checkbox\" "+(_83c.checked?"checked=\"checked\"":""));
cc.push(" name=\""+_83e+"\" value=\""+(_83f!=undefined?_83f:"")+"\">");
}else{
cc.push(tdTxt);
}
```


## 优化datagrid 在IE的渲染性能

>修改：
- 去除了row 高度计算，注释掉 9216-9233行：
```javascript

//if(!dc.body1.is(":empty")&&(!opts.nowrap||opts.autoRowHeight||_674)){
//if(_673!=undefined){
//var tr1=opts.finder.getTr(_672,_673,"body",1);
//var tr2=opts.finder.getTr(_672,_673,"body",2);
//_675(tr1,tr2);
//}else{
//var tr1=opts.finder.getTr(_672,0,"allbody",1);
//var tr2=opts.finder.getTr(_672,0,"allbody",2);
//_675(tr1,tr2);
//if(opts.showFooter){
//var tr1=opts.finder.getTr(_672,0,"allfooter",1);
//var tr2=opts.finder.getTr(_672,0,"allfooter",2);
//_675(tr1,tr2);
//}
//}
//}
```

 - 将部分$(obj).html()方法替换成原生的 $(obj)[0].innerHTML =
 修改11306行：
 ```javascript
 $(_82c)[0].innerHTML = _82f.join("");
 ```

 修改11554行：
 ```javascript
_86c[0].innerHTML = this.renderTable(_86a,0,[{}],false);
 ```

 修改11762行：
 ```javascript
$(_8ba)[0].innerHTML = _8bc.join("");
 ```



## 去除combogrid鼠标上下控制时，高亮的同时选中行

>修改：
- 注释掉 14745行：
```javascript
//grid.datagrid("selectRow",_aff);
```

## combo 控件 focus时自动下拉列表

>修改：
- 添加 13546-13552行：
```javascript
function inputFocusHandler (e) {
var target = e.data.target;
var state = $.data(target, 'combo');
var opts = state.options;
//window.console && console.log(this,opts);
if (opts.onFocus) { opts.onFocus(target, opts)}
}
```

- 修改 添加 13827行：
```javascript
focus:inputFocusHandler,
```

- 修改 13836-13838行：
```javascript
},onFocus : function(target,opts){
  if (opts.noFocusDrop !=='true') {
    $(target).combo('showPanel');
  }
//window.console && console.log(target);
```
- 添加修改14101行,14435行,14663行,14999行,15260行：
```javascript
  if (opts.mode == 'remote') { opts.noFocusDrop = opts.noFocusDrop || 'true'; }
```

## 修正 combodatagrid 点击行选择 先回滚到已选择行，需要再点击才选中的bug
- 修改 14967行，添加：
```javascript
scrollOnSelect: false,
```


## 修正 表单reset或者clear后第一个输入框会自动聚焦下拉
- 注释 8030行和8043行：
```javascript
// form.form("validate");
```

## 修正 clearIcon 对combobox和combgrid在多选时候的clear事件
- 修改 6874行-6883行：
```javascript
      handler:function(e){
        var $tar = $(e.handleObj.data.target);
				if($tar.hasClass('combobox-f')){
					$tar.combobox('clear');
				}else if($tar.hasClass('combogrid-f')){
					$tar.combogrid('clear');
				}else{
					$tar.textbox('clear');
        }
      }
```

## 修改numberbox，当precision小于0时，不做精度处理
- 修改 8318行-8330行：
```javascript
var p = opts.precision>=0;
var val=p?parseFloat(s).toFixed(opts.precision):parseFloat(s);
if(isNaN(val)){
val="";
}else{
if(typeof (opts.min)=="number"&&val<opts.min){
val=p?opts.min.toFixed(opts.precision):opts.min;
}else{
if(typeof (opts.max)=="number"&&val>opts.max){
val=p?opts.max.toFixed(opts.precision):opts.max;
}
}
}
```


## combobox和 combogrid 中修改 remote 模式下超过2个字符才返回
- 修改 13596- 13605行：
```javascript
//remote模式，字符长度大于1才请求
if(opts.mode =='remote'){
  if(q.length>=opts.minRemoteKeyLen){
    t.combo('showPanel');
    opts.keyHandler.query.call(_a20, q, e);
  }
}else{
  t.combo('showPanel');
  opts.keyHandler.query.call(_a20, q, e);
}
```

- 修改 13840- 13842行，添加 **minRemoteKeyLen** 属性：
```javascript
return $.extend({},$.fn.textbox.parseOptions(_a4e),$.parser.parseOptions(_a4e,["separator","panelAlign",{panelWidth:"number",hasDownArrow:"boolean",minRemoteKeyLen:'number',delay:"number",reversed:"boolean",multivalue:"boolean",selectOnNavigation:"boolean"},{panelMinWidth:"number",panelMaxWidth:"number",panelMinHeight:"number",panelMaxHeight:"number"}]),{panelHeight:(t.attr("panelHeight")=="auto"?"auto":parseInt(t.attr("panelHeight"))||undefined),multiple:(t.attr("multiple")?true:undefined)});
};
$.fn.combo.defaults=$.extend({},$.fn.textbox.defaults,{inputEvents:{focus:inputFocusHandler,click:_a1b,keydown:_a1f,paste:_a1f,drop:_a1f},panelWidth:null,panelHeight:'auto',panelMinWidth:null,panelMaxWidth:null,panelMinHeight:null,panelMaxHeight:200,panelAlign:"left",reversed:true,multiple:false,multivalue:true,selectOnNavigation:true,separator:",",hasDownArrow:true,minRemoteKeyLen:2,delay:200,keyHandler:{up:function(e){
```


## combobox 设置值在下拉里无对应时，设置为空
- 修改 13980- 13982行，添加行：
```javascript
}else{//如果没有找到对应的值
  s = opts.limitToList?'':v;
}
```


## form('load')时，不focus未填项目
- 修改 7942行：
```javascript
form.form("validate",!data.errFocus);
```

- 修改 8087-8096行：
```javascript
function _5b2(_5b3,noErrFocus){
if($.fn.validatebox){
var t=$(_5b3);
t.find(".validatebox-text:not(:disabled)").validatebox("validate");
var _5b4=t.find(".validatebox-invalid");
if(noErrFocus){_5b4.filter(":not(:disabled):first").focus();}
return _5b4.length==0;
}
return true;
};
```

- 修改 8132-8133行：
```javascript
},validate:function(jq,noErrFocus){
return _5b2(jq[0],noErrFocus);
```

## 修复 设置了readonly的输入框在验证时弹出提示信息
- 修改 8092行：
```javascript
if(!noErrFocus){_5b4.filter(":not(:disabled):first").focus().triggerHandler('mouseenter');}
```

## combobox 回车时 非limitToList状态下仍然清空输入值 的 问题修正
- 修改增加 14088行：
```javascript
if (opts.limitToList){
```
- 修改增加 14112-14119行：
```javascript
  }else{
  var v = $.trim(t.combobox('getText'));
  if (v !== ''){
    var values = t.combobox('getValues');
    values.push(v);
    t.combobox('setValues', values);
  }
}
```


## datagrid行编辑 更改默认上下键事件屏蔽 ctrl+上下键
- 修改 13568 行：
```javascript
if(!e.ctrlKey){opts.keyHandler.up.call(_a20,e)};
```
- 修改 13571行：
```javascript
if(!e.ctrlKey){opts.keyHandler.down.call(_a20,e)};
```


## combo 增加 onBeforeChange 方法
- 修改 13690 行：
```javascript
if(opts.onBeforeChange){//增加 onBeforeChange 方法，return true才可以继续
  var a=_a37[0],b=_a3a[0];
  if(opts.multiple){
    a =_a37,b =_a3a;
  }
  if(opts.onBeforeChange.call(_a36,a,b)===false){
    $(_a36).textbox('textbox').focus();
    return false;
  }
}
```

## combo 中的setValues方法增加 处理 combobox和combogrid 的onSelect方法，当情况清空输入框时 onSelect返回 (index:-1,record:{})
- 修改 13731-13733 行：
```javascript
if(opts.clearCanSelect&&opts.onSelect&&_a37.length==1&&_a37[0]===''){//combobox及comboxgrid清空时 如果clearCanSelect:true 则触发onSelect事件
  if($(_a36).hasClass('combobox-f')){opts.onSelect.call(_a36,{});}//返回空数组
  else if($(_a36).hasClass('combogrid-f')){opts.onSelect.call(_a36,-1,{});}//返回空数组
}
```

## combobox 下拉项添加title属性
- 修改 14376-13385 行：
```javascript
var itemOne = opts.formatter?opts.formatter.call(_aa2,row):s;
var itemTxt = $('<div />').html(itemOne).text();
//var cls="combobox-item"+(String(row.usingSign)==='0'?" combobox-item-hidden":"")+(row.disabled?" combobox-item-disabled":"")+(g?" combobox-gitem":"");
var cls="combobox-item"+((String(row.usingSign)==='0'&&!opts.useAllData)?" combobox-item-disabled":"")+(row.disabled?" combobox-item-disabled":"")+(g?" combobox-gitem":"");
dd.push("<div id=\""+(_aa4.itemIdPrefix+"_"+i)+"\" class=\""+cls+"\"  title=\""+itemTxt+"\">");
if(opts.showItemIcon&&row.iconCls){
dd.push("<span class=\"combobox-icon "+row.iconCls+"\"></span>");
}
dd.push(itemOne);
dd.push("</div>");
}
```

## 为所有行编辑输入框添加 focus,blur,keyup事件
- 修改 10905 行：
```javascript
_7d7.data('opt',_7d6);//添加opt
```

- 修改 10927-10929 行：
```javascript
var $input = $(_7da);
if(isA(_7da,name)){
$input = $(_7da).next().find('input').eq(0);
```

- 修改 10927-10929 行：
```javascript
var $input = $(_7da);
if(isA(_7da,name)){
$input = $(_7da).next().find('input').eq(0);
```

- 修改 10941-10967 行：
```javascript
$(_7da).val($.trim(_7db));
}

var opt = $(_7da).data('opt');

if(!opt.initSet){//防止反复调用
  if (opt.focus) {
    $input.bind('focus.edit',function () {
      var v = $.trim($input.val());
      opt.focus($(_7da),v);
    });
  };
  if (opt.keyup) {
    $input.bind('keyup.edit',function () {
      var v = $.trim($input.val());
      opt.keyup($(_7da),v);
    });
  };
  if (opt.blur) {
    $input.bind('blur.edit',function () {
      var v = $.trim($input.val());
      opt.blur($(_7da),v);
    });
  };
  opt.initSet = true;
}
```


- 修改 11007-11040 行：
```javascript
_7f0.data('opt',_7ef);
return _7f0;
},destroy:function(_7f1){
$(_7f1).validatebox("destroy");
},getValue:function(_7f2){
return $(_7f2).val();
},

setValue:function(target,value){
  $(target).val($.trim(value));
  var opt = $(target).data('opt');
  if(!opt.initSet){//防止反复调用
    if (opt.focus) {
      $(target).bind('focus.edit',function () {
        var v = $.trim($(target).val());
        opt.focus(target,v);
      });
    };
    if (opt.keyup) {
      $(target).bind('keyup.edit',function () {
        var v = $.trim($(target).val());
        opt.keyup(target,v);
      });
    };
    if (opt.blur) {
      $(target).bind('blur.edit',function () {
        var v = $.trim($(target).val());
        opt.blur(target,v);
      });
    };
    opt.initSet = true;
  }
},
```

## combotree 添加 enterFree属性，为true时，回车不响应 回到原来值，结合 editable使用
- 修改 14663 行：
```javascript
(!_ad9.options.enterFree) && $(_ad8).combotree("setValues",$(_ad8).combotree("getValues"));
```



