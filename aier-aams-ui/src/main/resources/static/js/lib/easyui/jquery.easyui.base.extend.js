/*---------easyui和requirejs集成---------*/
(function(){
  if(window.proEnvZip){return false;}//只在未打包模式下执行，如果打包也执行以下事件，会引起combobox 多次请求url
  var _9203 = setInterval(parse,10);
  function parse(){
    if($.parser && $.fn.slider && !window.renderedFlag){
      clearInterval(_9203);
      $.parser.parse();
      window.console && console.log('dev runing');
      window.renderedFlag = true;
    }
  }
})();
/*------------中文包-----------*/
if ($.fn.pagination){
  $.fn.pagination.defaults.beforePageText = '第';
  $.fn.pagination.defaults.afterPageText = '共{pages}页';
  $.fn.pagination.defaults.displayMsg = '显示{from}到{to},共{total}记录';
}
if ($.fn.datagrid){
  $.fn.datagrid.defaults.loadMsg = '玩命加载中，请稍待...';
}
if ($.fn.treegrid && $.fn.datagrid){
  $.fn.treegrid.defaults.loadMsg = $.fn.datagrid.defaults.loadMsg;
}
if ($.messager){
  $.messager.defaults.ok = '确定';
  $.messager.defaults.cancel = '取消';
}
$.map(['validatebox','textbox','passwordbox','filebox','searchbox',
    'combo','combobox','combogrid','combotree',
    'datebox','datetimebox','numberbox',
    'spinner','numberspinner','timespinner','datetimespinner'], function(plugin){
  if ($.fn[plugin]){
    $.fn[plugin].defaults.missingMessage = '此项必填';
  }
});
if ($.fn.validatebox){
  $.fn.validatebox.defaults.rules.email.message = '请输入有效的电子邮箱';
  $.fn.validatebox.defaults.rules.url.message = '请输入有效的URL地址';
  $.fn.validatebox.defaults.rules.length.message = '内容长度介于{0}和{1}之间';
  $.fn.validatebox.defaults.rules.remote.message = '该名称已存在';
}
if ($.fn.calendar){
  $.fn.calendar.defaults.weeks = ['日','一','二','三','四','五','六'];
  $.fn.calendar.defaults.months = ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'];
}
if ($.fn.datebox){
  $.fn.datebox.defaults.currentText = '今天';
  $.fn.datebox.defaults.closeText = '关闭';
  $.fn.datebox.defaults.okText = '确定';
  $.fn.datebox.defaults.formatter = function(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
  };
  $.fn.datebox.defaults.parser = function(s){
    if (!s) return new Date();
    var ss = s.split('-');
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
      return new Date(y,m-1,d);
    } else {
      return new Date();
    }
  };
}
if ($.fn.datetimebox && $.fn.datebox){
  $.extend($.fn.datetimebox.defaults,{
    currentText: $.fn.datebox.defaults.currentText,
    closeText: $.fn.datebox.defaults.closeText,
    okText: $.fn.datebox.defaults.okText
  });
}
if ($.fn.datetimespinner){
  $.fn.datetimespinner.defaults.selections = [[0,4],[5,7],[8,10],[11,13],[14,16],[17,19]]
}

/*------------cellediting---------------------*/
(function($){$.extend($.fn.datagrid.defaults,{clickToEdit:true,dblclickToEdit:false,navHandler:{37:function(e){var opts=$(this).datagrid("options");return navHandler.call(this,e,opts.isRtl?"right":"left")},39:function(e){var opts=$(this).datagrid("options");return navHandler.call(this,e,opts.isRtl?"left":"right")},38:function(e){return navHandler.call(this,e,"up")},40:function(e){return navHandler.call(this,e,"down")},13:function(e){return enterHandler.call(this,e)},27:function(e){return escHandler.call(this,e)},8:function(e){return clearHandler.call(this,e)},46:function(e){return clearHandler.call(this,e)},keypress:function(e){if(e.metaKey||e.ctrlKey){return}var dg=$(this);var param=dg.datagrid("cell");if(!param){return}var input=dg.datagrid("input",param);if(!input){var tmp=$("<span></span>");tmp.html(String.fromCharCode(e.which));var c=tmp.text();tmp.remove();if(c){dg.datagrid("editCell",{index:param.index,field:param.field,value:c});return false}}}},onBeforeCellEdit:function(index,field){},onCellEdit:function(index,field,value){var input=$(this).datagrid("input",{index:index,field:field});if(input){if(value!=undefined){input.val(value)}}},onSelectCell:function(index,field){},onUnselectCell:function(index,field){}});function navHandler(e,dir){var dg=$(this);var param=dg.datagrid("cell");var input=dg.datagrid("input",param);if(!input){dg.datagrid("gotoCell",dir);return false}}function enterHandler(e){var dg=$(this);var cell=dg.datagrid("cell");if(!cell){return}var input=dg.datagrid("input",cell);if(input){endCellEdit(this,true)}else{dg.datagrid("editCell",cell)}return false}function escHandler(e){endCellEdit(this,false);return false}function clearHandler(e){var dg=$(this);var param=dg.datagrid("cell");if(!param){return}var input=dg.datagrid("input",param);if(!input){dg.datagrid("editCell",{index:param.index,field:param.field,value:""});return false}}function getCurrCell(target){var cell=$(target).datagrid("getPanel").find("td.datagrid-row-selected");if(cell.length){return{index:parseInt(cell.closest("tr.datagrid-row").attr("datagrid-row-index")),field:cell.attr("field")}}else{return null}}function unselectCell(target,p){var opts=$(target).datagrid("options");var cell=opts.finder.getTr(target,p.index).find('td[field="'+p.field+'"]');cell.removeClass("datagrid-row-selected");opts.onUnselectCell.call(target,p.index,p.field)}function unselectAllCells(target){var panel=$(target).datagrid("getPanel");panel.find("td.datagrid-row-selected").removeClass("datagrid-row-selected")}function selectCell(target,p){var opts=$(target).datagrid("options");if(opts.singleSelect){unselectAllCells(target)}var cell=opts.finder.getTr(target,p.index).find('td[field="'+p.field+'"]');cell.addClass("datagrid-row-selected");opts.onSelectCell.call(target,p.index,p.field)}function getSelectedCells(target){var cells=[];var panel=$(target).datagrid("getPanel");panel.find("td.datagrid-row-selected").each(function(){var td=$(this);cells.push({index:parseInt(td.closest("tr.datagrid-row").attr("datagrid-row-index")),field:td.attr("field")})});return cells}function gotoCell(target,p){var dg=$(target);var opts=dg.datagrid("options");var panel=dg.datagrid("getPanel").focus();var cparam=dg.datagrid("cell");if(cparam){var input=dg.datagrid("input",cparam);if(input){input.focus();return}}if(typeof p=="object"){_go(p);return}var cell=panel.find("td.datagrid-row-selected");if(!cell){return}var fields=dg.datagrid("getColumnFields",true).concat(dg.datagrid("getColumnFields"));var field=cell.attr("field");var tr=cell.closest("tr.datagrid-row");var rowIndex=parseInt(tr.attr("datagrid-row-index"));var colIndex=$.inArray(field,fields);if(p=="up"&&rowIndex>0){rowIndex--}else if(p=="down"){if(opts.finder.getRow(target,rowIndex+1)){rowIndex++}}else if(p=="left"){var i=colIndex-1;while(i>=0){var col=dg.datagrid("getColumnOption",fields[i]);if(!col.hidden){colIndex=i;break}i--}}else if(p=="right"){var i=colIndex+1;while(i<=fields.length-1){var col=dg.datagrid("getColumnOption",fields[i]);if(!col.hidden){colIndex=i;break}i++}}field=fields[colIndex];_go({index:rowIndex,field:field});function _go(p){dg.datagrid("scrollTo",p.index);unselectAllCells(target);selectCell(target,p);var td=opts.finder.getTr(target,p.index,"body",2).find('td[field="'+p.field+'"]');if(td.length){var body2=dg.data("datagrid").dc.body2;var left=td.position().left;if(left<0){body2._scrollLeft(body2._scrollLeft()+left*(opts.isRtl?-1:1))}else if(left+td._outerWidth()>body2.width()){body2._scrollLeft(body2._scrollLeft()+(left+td._outerWidth()-body2.width())*(opts.isRtl?-1:1))}}}}function endCellEdit(target,accepted){var dg=$(target);var cell=dg.datagrid("cell");if(cell){var input=dg.datagrid("input",cell);if(input){if(accepted){if(dg.datagrid("validateRow",cell.index)){dg.datagrid("endEdit",cell.index);dg.datagrid("gotoCell",cell)}else{dg.datagrid("gotoCell",cell);input.focus();return false}var opts=$(target).datagrid("options");var td=opts.finder.getTr(target,cell.index).find('td[field="'+cell.field+'"]');td.removeClass("x-editor");td.addClass("x-editor-edited")}else{dg.datagrid("cancelEdit",cell.index);dg.datagrid("gotoCell",cell)}}}return true}function editCell(target,param){var dg=$(target);var opts=dg.datagrid("options");var input=dg.datagrid("input",param);if(input){dg.datagrid("gotoCell",param);input.focus();return}if(!endCellEdit(target,true)){return}if(opts.onBeforeCellEdit.call(target,param.index,param.field)==false){return}var fields=dg.datagrid("getColumnFields",true).concat(dg.datagrid("getColumnFields"));$.map(fields,function(field){var col=dg.datagrid("getColumnOption",field);col.editor1=col.editor;if(field!=param.field){col.editor=null}});var col=dg.datagrid("getColumnOption",param.field);if(col.editor){dg.datagrid("beginEdit",param.index);var input=dg.datagrid("input",param);if(input){dg.datagrid("gotoCell",param);setTimeout(function(){input.unbind(".cellediting").bind("keydown.cellediting",function(e){if(e.keyCode==13){opts.navHandler["13"].call(target,e);return false}});input.focus()},0);opts.onCellEdit.call(target,param.index,param.field,param.value)}else{dg.datagrid("cancelEdit",param.index);dg.datagrid("gotoCell",param)}}else{dg.datagrid("gotoCell",param)}$.map(fields,function(field){var col=dg.datagrid("getColumnOption",field);col.editor=col.editor1})}function enableCellSelecting(target){var dg=$(target);var state=dg.data("datagrid");var panel=dg.datagrid("getPanel");var opts=state.options;var dc=state.dc;panel.attr("tabindex",1).css("outline-style","none").unbind(".cellediting").bind("keydown.cellediting",function(e){var h=opts.navHandler[e.keyCode];if(h){return h.call(target,e)}});dc.body1.add(dc.body2).unbind(".cellediting").bind("click.cellediting",function(e){var tr=$(e.target).closest(".datagrid-row");if(tr.length&&tr.parent().length){var td=$(e.target).closest("td[field]",tr);if(td.length){var index=parseInt(tr.attr("datagrid-row-index"));var field=td.attr("field");var p={index:index,field:field};if(opts.singleSelect){selectCell(target,p)}else{if(opts.ctrlSelect){if(e.ctrlKey){if(td.hasClass("datagrid-row-selected")){unselectCell(target,p)}else{selectCell(target,p)}}else{unselectAllCells(target);selectCell(target,p)}}else{if(td.hasClass("datagrid-row-selected")){unselectCell(target,p)}else{selectCell(target,p)}}}}}}).bind("mouseover.cellediting",function(e){var td=$(e.target).closest("td[field]");if(td.length){td.addClass("datagrid-row-over");td.closest("tr.datagrid-row").removeClass("datagrid-row-over")}}).bind("mouseout.cellediting",function(e){var td=$(e.target).closest("td[field]");td.removeClass("datagrid-row-over")});opts.isRtl=dg.datagrid("getPanel").css("direction").toLowerCase()=="rtl";opts.OldOnBeforeSelect=opts.onBeforeSelect;opts.onBeforeSelect=function(){return false};dg.datagrid("clearSelections")}function disableCellSelecting(target){var dg=$(target);var state=dg.data("datagrid");var panel=dg.datagrid("getPanel");var opts=state.options;opts.onBeforeSelect=opts.OldOnBeforeSelect||opts.onBeforeSelect;panel.unbind(".cellediting").find("td.datagrid-row-selected").removeClass("datagrid-row-selected");var dc=state.dc;dc.body1.add(dc.body2).unbind("cellediting")}function enableCellEditing(target){var dg=$(target);var opts=dg.datagrid("options");var panel=dg.datagrid("getPanel");panel.attr("tabindex",1).css("outline-style","none").unbind(".cellediting").bind("keydown.cellediting",function(e){var h=opts.navHandler[e.keyCode];if(h){return h.call(target,e)}}).bind("keypress.cellediting",function(e){return opts.navHandler["keypress"].call(target,e)});panel.panel("panel").unbind(".cellediting").bind("keydown.cellediting",function(e){e.stopPropagation()}).bind("keypress.cellediting",function(e){e.stopPropagation()}).bind("mouseover.cellediting",function(e){var td=$(e.target).closest("td[field]");if(td.length){td.addClass("datagrid-row-over");td.closest("tr.datagrid-row").removeClass("datagrid-row-over")}}).bind("mouseout.cellediting",function(e){var td=$(e.target).closest("td[field]");td.removeClass("datagrid-row-over")});opts.isRtl=dg.datagrid("getPanel").css("direction").toLowerCase()=="rtl";opts.oldOnClickCell=opts.onClickCell;opts.oldOnDblClickCell=opts.onDblClickCell;opts.onClickCell=function(index,field,value){if(opts.clickToEdit){$(this).datagrid("editCell",{index:index,field:field})}else{if(endCellEdit(this,true)){$(this).datagrid("gotoCell",{index:index,field:field})}}opts.oldOnClickCell.call(this,index,field,value)};if(opts.dblclickToEdit){opts.onDblClickCell=function(index,field,value){$(this).datagrid("editCell",{index:index,field:field});opts.oldOnDblClickCell.call(this,index,field,value)}}opts.OldOnBeforeSelect=opts.onBeforeSelect;opts.onBeforeSelect=function(){return false};dg.datagrid("clearSelections")}function disableCellEditing(target){var dg=$(target);var panel=dg.datagrid("getPanel");var opts=dg.datagrid("options");opts.onClickCell=opts.oldOnClickCell||opts.onClickCell;opts.onDblClickCell=opts.oldOnDblClickCell||opts.onDblClickCell;opts.onBeforeSelect=opts.OldOnBeforeSelect||opts.onBeforeSelect;panel.unbind(".cellediting").find("td.datagrid-row-selected").removeClass("datagrid-row-selected");panel.panel("panel").unbind(".cellediting")}$.extend($.fn.datagrid.methods,{editCell:function(jq,param){return jq.each(function(){editCell(this,param)})},isEditing:function(jq,index){var opts=$.data(jq[0],"datagrid").options;var tr=opts.finder.getTr(jq[0],index);return tr.length&&tr.hasClass("datagrid-row-editing")},gotoCell:function(jq,param){return jq.each(function(){gotoCell(this,param)})},enableCellEditing:function(jq){return jq.each(function(){enableCellEditing(this)})},disableCellEditing:function(jq){return jq.each(function(){disableCellEditing(this)})},enableCellSelecting:function(jq){return jq.each(function(){enableCellSelecting(this)})},disableCellSelecting:function(jq){return jq.each(function(){disableCellSelecting(this)})},input:function(jq,param){if(!param){return null}var ed=jq.datagrid("getEditor",param);if(ed){var t=$(ed.target);if(t.hasClass("textbox-f")){t=t.textbox("textbox")}return t}else{return null}},cell:function(jq){return getCurrCell(jq[0])},getSelectedCells:function(jq){return getSelectedCells(jq[0])}})})(jQuery);var detailview=$.extend({},$.fn.datagrid.defaults.view,{render:function(target,container,frozen){var state=$.data(target,"datagrid");var opts=state.options;if(frozen){if(!(opts.rownumbers||opts.frozenColumns&&opts.frozenColumns.length)){return}}var rows=state.data.rows;var fields=$(target).datagrid("getColumnFields",frozen);var table=[];table.push('<table class="datagrid-btable" cellspacing="0" cellpadding="0" border="0"><tbody>');for(var i=0;i<rows.length;i++){var css=opts.rowStyler?opts.rowStyler.call(target,i,rows[i]):"";var classValue="";var styleValue="";if(typeof css=="string"){styleValue=css}else if(css){classValue=css["class"]||"";styleValue=css["style"]||""}var cls='class="datagrid-row '+(i%2&&opts.striped?"datagrid-row-alt ":" ")+classValue+'"';var style=styleValue?'style="'+styleValue+'"':"";var rowId=state.rowIdPrefix+"-"+(frozen?1:2)+"-"+i;table.push('<tr id="'+rowId+'" datagrid-row-index="'+i+'" '+cls+" "+style+">");table.push(this.renderRow.call(this,target,fields,frozen,i,rows[i]));table.push("</tr>");table.push('<tr style="display:none;">');if(frozen){table.push("<td colspan="+(fields.length+(opts.rownumbers?1:0))+' style="border-right:0">')}else{table.push("<td colspan="+fields.length+">")}table.push('<div class="datagrid-row-detail">');if(frozen){table.push("&nbsp;")}else{table.push(opts.detailFormatter.call(target,i,rows[i]))}table.push("</div>");table.push("</td>");table.push("</tr>")}table.push("</tbody></table>");$(container).html(table.join(""))},renderRow:function(target,fields,frozen,rowIndex,rowData){var opts=$.data(target,"datagrid").options;var cc=[];if(frozen&&opts.rownumbers){var rownumber=rowIndex+1;if(opts.pagination){rownumber+=(opts.pageNumber-1)*opts.pageSize}cc.push('<td class="datagrid-td-rownumber"><div class="datagrid-cell-rownumber">'+rownumber+"</div></td>")}for(var i=0;i<fields.length;i++){var field=fields[i];var col=$(target).datagrid("getColumnOption",field);if(col){var value=rowData[field];var css=col.styler?col.styler(value,rowData,rowIndex)||"":"";var classValue="";var styleValue="";if(typeof css=="string"){styleValue=css}else if(cc){classValue=css["class"]||"";styleValue=css["style"]||""}var cls=classValue?'class="'+classValue+'"':"";var style=col.hidden?'style="display:none;'+styleValue+'"':styleValue?'style="'+styleValue+'"':"";cc.push('<td field="'+field+'" '+cls+" "+style+">");if(col.checkbox){style=""}else if(col.expander){style="text-align:center;height:16px;"}else{style=styleValue;if(col.align){style+=";text-align:"+col.align+";"}if(!opts.nowrap){style+=";white-space:normal;height:auto;"}else if(opts.autoRowHeight){style+=";height:auto;"}}cc.push('<div style="'+style+'" ');if(col.checkbox){cc.push('class="datagrid-cell-check ')}else{cc.push('class="datagrid-cell '+col.cellClass)}cc.push('">');if(col.checkbox){cc.push('<input type="checkbox" name="'+field+'" value="'+(value!=undefined?value:"")+'">')}else if(col.expander){cc.push('<span class="datagrid-row-expander datagrid-row-expand" style="display:inline-block;width:16px;height:16px;cursor:pointer;" />')}else if(col.formatter){cc.push(col.formatter(value,rowData,rowIndex))}else{cc.push(value)}cc.push("</div>");cc.push("</td>")}}return cc.join("")},insertRow:function(target,index,row){var opts=$.data(target,"datagrid").options;var dc=$.data(target,"datagrid").dc;var panel=$(target).datagrid("getPanel");var view1=dc.view1;var view2=dc.view2;var isAppend=false;var rowLength=$(target).datagrid("getRows").length;if(rowLength==0){$(target).datagrid("loadData",{total:1,rows:[row]});return}if(index==undefined||index==null||index>=rowLength){index=rowLength;isAppend=true;this.canUpdateDetail=false}$.fn.datagrid.defaults.view.insertRow.call(this,target,index,row);_insert(true);_insert(false);this.canUpdateDetail=true;function _insert(frozen){var v=frozen?view1:view2;var tr=v.find("tr[datagrid-row-index="+index+"]");if(isAppend){var newDetail=tr.next().clone();tr.insertAfter(tr.next())}else{var newDetail=tr.next().next().clone()}newDetail.insertAfter(tr);newDetail.hide();if(!frozen){newDetail.find("div.datagrid-row-detail").html(opts.detailFormatter.call(target,index,row))}}},deleteRow:function(target,index){var opts=$.data(target,"datagrid").options;var dc=$.data(target,"datagrid").dc;var tr=opts.finder.getTr(target,index);tr.next().remove();$.fn.datagrid.defaults.view.deleteRow.call(this,target,index);dc.body2.triggerHandler("scroll")},updateRow:function(target,rowIndex,row){var dc=$.data(target,"datagrid").dc;var opts=$.data(target,"datagrid").options;var cls=$(target).datagrid("getExpander",rowIndex).attr("class");$.fn.datagrid.defaults.view.updateRow.call(this,target,rowIndex,row);$(target).datagrid("getExpander",rowIndex).attr("class",cls);if(this.canUpdateDetail){var row=$(target).datagrid("getRows")[rowIndex];var detail=$(target).datagrid("getRowDetail",rowIndex);detail.html(opts.detailFormatter.call(target,rowIndex,row))}},bindEvents:function(target){var state=$.data(target,"datagrid");if(state.ss.bindDetailEvents){return}state.ss.bindDetailEvents=true;var dc=state.dc;var opts=state.options;var body=dc.body1.add(dc.body2);var clickHandler=($.data(body[0],"events")||$._data(body[0],"events")).click[0].handler;body.unbind("click").bind("click",function(e){var tt=$(e.target);var tr=tt.closest("tr.datagrid-row");if(!tr.length){return}if(tt.hasClass("datagrid-row-expander")){var rowIndex=parseInt(tr.attr("datagrid-row-index"));if(tt.hasClass("datagrid-row-expand")){$(target).datagrid("expandRow",rowIndex)}else{$(target).datagrid("collapseRow",rowIndex)}$(target).datagrid("fixRowHeight")}else{clickHandler(e)}e.stopPropagation()})},onBeforeRender:function(target){var state=$.data(target,"datagrid");var opts=state.options;var dc=state.dc;var t=$(target);var hasExpander=false;var fields=t.datagrid("getColumnFields",true).concat(t.datagrid("getColumnFields"));for(var i=0;i<fields.length;i++){var col=t.datagrid("getColumnOption",fields[i]);if(col.expander){hasExpander=true;break}}if(!hasExpander){if(opts.frozenColumns&&opts.frozenColumns.length){opts.frozenColumns[0].splice(0,0,{field:"_expander",expander:true,width:24,resizable:false,fixed:true})}else{opts.frozenColumns=[[{field:"_expander",expander:true,width:24,resizable:false,fixed:true}]]}var t=dc.view1.children("div.datagrid-header").find("table");var td=$('<td rowspan="'+opts.frozenColumns.length+'"><div class="datagrid-header-expander" style="width:24px;"></div></td>');if($("tr",t).length==0){td.wrap("<tr></tr>").parent().appendTo($("tbody",t))}else if(opts.rownumbers){td.insertAfter(t.find("td:has(div.datagrid-header-rownumber)"))}else{td.prependTo(t.find("tr:first"))}}},onAfterRender:function(target){var that=this;var state=$.data(target,"datagrid");var dc=state.dc;var opts=state.options;var panel=$(target).datagrid("getPanel");$.fn.datagrid.defaults.view.onAfterRender.call(this,target);if(!state.onResizeColumn){state.onResizeColumn=opts.onResizeColumn}if(!state.onResize){state.onResize=opts.onResize}function resizeDetails(){var ht=dc.header2.find("table");var fr=ht.find("tr.datagrid-filter-row").hide();var ww=ht.width()-1;var details=dc.body2.find(">table.datagrid-btable>tbody>tr>td>div.datagrid-row-detail:visible")._outerWidth(ww);details.find(".easyui-fluid").trigger("_resize");fr.show()}opts.onResizeColumn=function(field,width){if(!opts.fitColumns){resizeDetails()}var rowCount=$(target).datagrid("getRows").length;for(var i=0;i<rowCount;i++){$(target).datagrid("fixDetailRowHeight",i)}state.onResizeColumn.call(target,field,width)};opts.onResize=function(width,height){if(opts.fitColumns){resizeDetails()}state.onResize.call(panel,width,height)};this.canUpdateDetail=true;var footer=dc.footer1.add(dc.footer2);footer.find("span.datagrid-row-expander").css("visibility","hidden");$(target).datagrid("resize");this.bindEvents(target);var detail=dc.body1.add(dc.body2).find("div.datagrid-row-detail");detail.unbind().bind("mouseover mouseout click dblclick contextmenu scroll",function(e){e.stopPropagation()})}});$.extend($.fn.datagrid.methods,{fixDetailRowHeight:function(jq,index){return jq.each(function(){var opts=$.data(this,"datagrid").options;if(!(opts.rownumbers||opts.frozenColumns&&opts.frozenColumns.length)){return}var dc=$.data(this,"datagrid").dc;var tr1=opts.finder.getTr(this,index,"body",1).next();var tr2=opts.finder.getTr(this,index,"body",2).next();if(tr2.is(":visible")){tr1.css("height","");tr2.css("height","");var height=Math.max(tr1.height(),tr2.height());tr1.css("height",height);tr2.css("height",height)}dc.body2.triggerHandler("scroll")})},getExpander:function(jq,index){var opts=$.data(jq[0],"datagrid").options;return opts.finder.getTr(jq[0],index).find("span.datagrid-row-expander")},getRowDetail:function(jq,index){var opts=$.data(jq[0],"datagrid").options;var tr=opts.finder.getTr(jq[0],index,"body",2);return tr.next().find(">td>div.datagrid-row-detail")},expandRow:function(jq,index){return jq.each(function(){var opts=$(this).datagrid("options");var dc=$.data(this,"datagrid").dc;var expander=$(this).datagrid("getExpander",index);if(expander.hasClass("datagrid-row-expand")){expander.removeClass("datagrid-row-expand").addClass("datagrid-row-collapse");var tr1=opts.finder.getTr(this,index,"body",1).next();var tr2=opts.finder.getTr(this,index,"body",2).next();tr1.show();tr2.show();$(this).datagrid("fixDetailRowHeight",index);if(opts.onExpandRow){var row=$(this).datagrid("getRows")[index];opts.onExpandRow.call(this,index,row)}}})},collapseRow:function(jq,index){return jq.each(function(){var opts=$(this).datagrid("options");var dc=$.data(this,"datagrid").dc;var expander=$(this).datagrid("getExpander",index);if(expander.hasClass("datagrid-row-collapse")){expander.removeClass("datagrid-row-collapse").addClass("datagrid-row-expand");var tr1=opts.finder.getTr(this,index,"body",1).next();var tr2=opts.finder.getTr(this,index,"body",2).next();tr1.hide();tr2.hide();dc.body2.triggerHandler("scroll");if(opts.onCollapseRow){var row=$(this).datagrid("getRows")[index];opts.onCollapseRow.call(this,index,row)}}})}});$.extend($.fn.datagrid.methods,{subgrid:function(jq,conf){return jq.each(function(){createGrid(this,conf);function createGrid(target,conf,prow){var queryParams=$.extend({},conf.options.queryParams||{});queryParams[conf.options.foreignField]=prow?prow[conf.options.foreignField]:undefined;$(target).datagrid($.extend({},conf.options,{subgrid:conf.subgrid,view:conf.subgrid?detailview:undefined,queryParams:queryParams,detailFormatter:function(index,row){return'<div><table class="datagrid-subgrid"></table></div>'},onExpandRow:function(index,row){var opts=$(this).datagrid("options");var rd=$(this).datagrid("getRowDetail",index);var dg=getSubGrid(rd);if(!dg.data("datagrid")){createGrid(dg[0],opts.subgrid,row)}rd.find(".easyui-fluid").trigger("_resize");setHeight(this,index);if(conf.options.onExpandRow){conf.options.onExpandRow.call(this,index,row)}},onCollapseRow:function(index,row){setHeight(this,index);if(conf.options.onCollapseRow){conf.options.onCollapseRow.call(this,index,row)}},onResize:function(){var dg=$(this).children("div.datagrid-view").children("table");setParentHeight(this)},onResizeColumn:function(field,width){setParentHeight(this);if(conf.options.onResizeColumn){conf.options.onResizeColumn.call(this,field,width)}},onLoadSuccess:function(data){setParentHeight(this);if(conf.options.onLoadSuccess){conf.options.onLoadSuccess.call(this,data)}}}))}function getSubGrid(rowDetail){var div=$(rowDetail).children("div");if(div.children("div.datagrid").length){return div.find(">div.datagrid>div.panel-body>div.datagrid-view>table.datagrid-subgrid")}else{return div.find(">table.datagrid-subgrid")}}function setParentHeight(target){var tr=$(target).closest("div.datagrid-row-detail").closest("tr").prev();if(tr.length){var index=parseInt(tr.attr("datagrid-row-index"));var dg=tr.closest("div.datagrid-view").children("table");setHeight(dg[0],index)}}function setHeight(target,index){$(target).datagrid("fixDetailRowHeight",index);$(target).datagrid("fixRowHeight",index);var tr=$(target).closest("div.datagrid-row-detail").closest("tr").prev();if(tr.length){var index=parseInt(tr.attr("datagrid-row-index"));var dg=tr.closest("div.datagrid-view").children("table");setHeight(dg[0],index)}}})},getParentGrid:function(jq){var detail=jq.closest("div.datagrid-row-detail");if(detail.length){return detail.closest(".datagrid-view").children(".datagrid-f")}else{return null}},getParentRowIndex:function(jq){var detail=jq.closest("div.datagrid-row-detail");if(detail.length){var tr=detail.closest("tr").prev();return parseInt(tr.attr("datagrid-row-index"))}else{return-1}}});

/*------------detailview---------------------*/
var detailview=$.extend({},$.fn.datagrid.defaults.view,{render:function(target,container,frozen){var state=$.data(target,"datagrid");var opts=state.options;if(frozen){if(!(opts.rownumbers||opts.frozenColumns&&opts.frozenColumns.length)){return}}var rows=state.data.rows;var fields=$(target).datagrid("getColumnFields",frozen);var table=[];table.push('<table class="datagrid-btable" cellspacing="0" cellpadding="0" border="0"><tbody>');for(var i=0;i<rows.length;i++){var css=opts.rowStyler?opts.rowStyler.call(target,i,rows[i]):"";var classValue="";var styleValue="";if(typeof css=="string"){styleValue=css}else if(css){classValue=css["class"]||"";styleValue=css["style"]||""}var cls='class="datagrid-row '+(i%2&&opts.striped?"datagrid-row-alt ":" ")+classValue+'"';var style=styleValue?'style="'+styleValue+'"':"";var rowId=state.rowIdPrefix+"-"+(frozen?1:2)+"-"+i;table.push('<tr id="'+rowId+'" datagrid-row-index="'+i+'" '+cls+" "+style+">");table.push(this.renderRow.call(this,target,fields,frozen,i,rows[i]));table.push("</tr>");table.push('<tr style="display:none;">');if(frozen){table.push("<td colspan="+(fields.length+(opts.rownumbers?1:0))+' style="border-right:0">')}else{table.push("<td colspan="+fields.length+">")}table.push('<div class="datagrid-row-detail">');if(frozen){table.push("&nbsp;")}else{table.push(opts.detailFormatter.call(target,i,rows[i]))}table.push("</div>");table.push("</td>");table.push("</tr>")}table.push("</tbody></table>");$(container).html(table.join(""))},renderRow:function(target,fields,frozen,rowIndex,rowData){var opts=$.data(target,"datagrid").options;var cc=[];if(frozen&&opts.rownumbers){var rownumber=rowIndex+1;if(opts.pagination){rownumber+=(opts.pageNumber-1)*opts.pageSize}cc.push('<td class="datagrid-td-rownumber"><div class="datagrid-cell-rownumber">'+rownumber+"</div></td>")}for(var i=0;i<fields.length;i++){var field=fields[i];var col=$(target).datagrid("getColumnOption",field);if(col){var value=rowData[field];var css=col.styler?col.styler(value,rowData,rowIndex)||"":"";var classValue="";var styleValue="";if(typeof css=="string"){styleValue=css}else if(cc){classValue=css["class"]||"";styleValue=css["style"]||""}var cls=classValue?'class="'+classValue+'"':"";var style=col.hidden?'style="display:none;'+styleValue+'"':styleValue?'style="'+styleValue+'"':"";cc.push('<td field="'+field+'" '+cls+" "+style+">");if(col.checkbox){style=""}else if(col.expander){style="text-align:center;height:16px;"}else{style=styleValue;if(col.align){style+=";text-align:"+col.align+";"}if(!opts.nowrap){style+=";white-space:normal;height:auto;"}else if(opts.autoRowHeight){style+=";height:auto;"}}cc.push('<div style="'+style+'" ');if(col.checkbox){cc.push('class="datagrid-cell-check ')}else{cc.push('class="datagrid-cell '+col.cellClass)}cc.push('">');if(col.checkbox){cc.push('<input type="checkbox" name="'+field+'" value="'+(value!=undefined?value:"")+'">')}else if(col.expander){cc.push('<span class="datagrid-row-expander datagrid-row-expand" style="display:inline-block;width:16px;height:16px;cursor:pointer;" />')}else if(col.formatter){cc.push(col.formatter(value,rowData,rowIndex))}else{cc.push(value)}cc.push("</div>");cc.push("</td>")}}return cc.join("")},insertRow:function(target,index,row){var opts=$.data(target,"datagrid").options;var dc=$.data(target,"datagrid").dc;var panel=$(target).datagrid("getPanel");var view1=dc.view1;var view2=dc.view2;var isAppend=false;var rowLength=$(target).datagrid("getRows").length;if(rowLength==0){$(target).datagrid("loadData",{total:1,rows:[row]});return}if(index==undefined||index==null||index>=rowLength){index=rowLength;isAppend=true;this.canUpdateDetail=false}$.fn.datagrid.defaults.view.insertRow.call(this,target,index,row);_insert(true);_insert(false);this.canUpdateDetail=true;function _insert(frozen){var v=frozen?view1:view2;var tr=v.find("tr[datagrid-row-index="+index+"]");if(isAppend){var newDetail=tr.next().clone();tr.insertAfter(tr.next())}else{var newDetail=tr.next().next().clone()}newDetail.insertAfter(tr);newDetail.hide();if(!frozen){newDetail.find("div.datagrid-row-detail").html(opts.detailFormatter.call(target,index,row))}}},deleteRow:function(target,index){var opts=$.data(target,"datagrid").options;var dc=$.data(target,"datagrid").dc;var tr=opts.finder.getTr(target,index);tr.next().remove();$.fn.datagrid.defaults.view.deleteRow.call(this,target,index);dc.body2.triggerHandler("scroll")},updateRow:function(target,rowIndex,row){var dc=$.data(target,"datagrid").dc;var opts=$.data(target,"datagrid").options;var cls=$(target).datagrid("getExpander",rowIndex).attr("class");$.fn.datagrid.defaults.view.updateRow.call(this,target,rowIndex,row);$(target).datagrid("getExpander",rowIndex).attr("class",cls);if(this.canUpdateDetail){var row=$(target).datagrid("getRows")[rowIndex];var detail=$(target).datagrid("getRowDetail",rowIndex);detail.html(opts.detailFormatter.call(target,rowIndex,row))}},bindEvents:function(target){var state=$.data(target,"datagrid");if(state.ss.bindDetailEvents){return}state.ss.bindDetailEvents=true;var dc=state.dc;var opts=state.options;var body=dc.body1.add(dc.body2);var clickHandler=($.data(body[0],"events")||$._data(body[0],"events")).click[0].handler;body.unbind("click").bind("click",function(e){var tt=$(e.target);var tr=tt.closest("tr.datagrid-row");if(!tr.length){return}if(tt.hasClass("datagrid-row-expander")){var rowIndex=parseInt(tr.attr("datagrid-row-index"));if(tt.hasClass("datagrid-row-expand")){$(target).datagrid("expandRow",rowIndex)}else{$(target).datagrid("collapseRow",rowIndex)}$(target).datagrid("fixRowHeight")}else{clickHandler(e)}e.stopPropagation()})},onBeforeRender:function(target){var state=$.data(target,"datagrid");var opts=state.options;var dc=state.dc;var t=$(target);var hasExpander=false;var fields=t.datagrid("getColumnFields",true).concat(t.datagrid("getColumnFields"));for(var i=0;i<fields.length;i++){var col=t.datagrid("getColumnOption",fields[i]);if(col.expander){hasExpander=true;break}}if(!hasExpander){if(opts.frozenColumns&&opts.frozenColumns.length){opts.frozenColumns[0].splice(0,0,{field:"_expander",expander:true,width:24,resizable:false,fixed:true})}else{opts.frozenColumns=[[{field:"_expander",expander:true,width:24,resizable:false,fixed:true}]]}var t=dc.view1.children("div.datagrid-header").find("table");var td=$('<td rowspan="'+opts.frozenColumns.length+'"><div class="datagrid-header-expander" style="width:24px;"></div></td>');if($("tr",t).length==0){td.wrap("<tr></tr>").parent().appendTo($("tbody",t))}else if(opts.rownumbers){td.insertAfter(t.find("td:has(div.datagrid-header-rownumber)"))}else{td.prependTo(t.find("tr:first"))}}},onAfterRender:function(target){var that=this;var state=$.data(target,"datagrid");var dc=state.dc;var opts=state.options;var panel=$(target).datagrid("getPanel");$.fn.datagrid.defaults.view.onAfterRender.call(this,target);if(!state.onResizeColumn){state.onResizeColumn=opts.onResizeColumn}if(!state.onResize){state.onResize=opts.onResize}function resizeDetails(){var ht=dc.header2.find("table");var fr=ht.find("tr.datagrid-filter-row").hide();var ww=ht.width()-1;var details=dc.body2.find(">table.datagrid-btable>tbody>tr>td>div.datagrid-row-detail:visible")._outerWidth(ww);details.find(".easyui-fluid").trigger("_resize");fr.show()}opts.onResizeColumn=function(field,width){if(!opts.fitColumns){resizeDetails()}var rowCount=$(target).datagrid("getRows").length;for(var i=0;i<rowCount;i++){$(target).datagrid("fixDetailRowHeight",i)}state.onResizeColumn.call(target,field,width)};opts.onResize=function(width,height){if(opts.fitColumns){resizeDetails()}state.onResize.call(panel,width,height)};this.canUpdateDetail=true;var footer=dc.footer1.add(dc.footer2);footer.find("span.datagrid-row-expander").css("visibility","hidden");$(target).datagrid("resize");this.bindEvents(target);var detail=dc.body1.add(dc.body2).find("div.datagrid-row-detail");detail.unbind().bind("mouseover mouseout click dblclick contextmenu scroll",function(e){e.stopPropagation()})}});$.extend($.fn.datagrid.methods,{fixDetailRowHeight:function(jq,index){return jq.each(function(){var opts=$.data(this,"datagrid").options;if(!(opts.rownumbers||opts.frozenColumns&&opts.frozenColumns.length)){return}var dc=$.data(this,"datagrid").dc;var tr1=opts.finder.getTr(this,index,"body",1).next();var tr2=opts.finder.getTr(this,index,"body",2).next();if(tr2.is(":visible")){tr1.css("height","");tr2.css("height","");var height=Math.max(tr1.height(),tr2.height());tr1.css("height",height);tr2.css("height",height)}dc.body2.triggerHandler("scroll")})},getExpander:function(jq,index){var opts=$.data(jq[0],"datagrid").options;return opts.finder.getTr(jq[0],index).find("span.datagrid-row-expander")},getRowDetail:function(jq,index){var opts=$.data(jq[0],"datagrid").options;var tr=opts.finder.getTr(jq[0],index,"body",2);return tr.next().find(">td>div.datagrid-row-detail")},expandRow:function(jq,index){return jq.each(function(){var opts=$(this).datagrid("options");var dc=$.data(this,"datagrid").dc;var expander=$(this).datagrid("getExpander",index);if(expander.hasClass("datagrid-row-expand")){expander.removeClass("datagrid-row-expand").addClass("datagrid-row-collapse");var tr1=opts.finder.getTr(this,index,"body",1).next();var tr2=opts.finder.getTr(this,index,"body",2).next();tr1.show();tr2.show();$(this).datagrid("fixDetailRowHeight",index);if(opts.onExpandRow){var row=$(this).datagrid("getRows")[index];opts.onExpandRow.call(this,index,row)}}})},collapseRow:function(jq,index){return jq.each(function(){var opts=$(this).datagrid("options");var dc=$.data(this,"datagrid").dc;var expander=$(this).datagrid("getExpander",index);if(expander.hasClass("datagrid-row-collapse")){expander.removeClass("datagrid-row-collapse").addClass("datagrid-row-expand");var tr1=opts.finder.getTr(this,index,"body",1).next();var tr2=opts.finder.getTr(this,index,"body",2).next();tr1.hide();tr2.hide();dc.body2.triggerHandler("scroll");if(opts.onCollapseRow){var row=$(this).datagrid("getRows")[index];opts.onCollapseRow.call(this,index,row)}}})}});$.extend($.fn.datagrid.methods,{subgrid:function(jq,conf){return jq.each(function(){createGrid(this,conf);function createGrid(target,conf,prow){var queryParams=$.extend({},conf.options.queryParams||{});queryParams[conf.options.foreignField]=prow?prow[conf.options.foreignField]:undefined;$(target).datagrid($.extend({},conf.options,{subgrid:conf.subgrid,view:conf.subgrid?detailview:undefined,queryParams:queryParams,detailFormatter:function(index,row){return'<div><table class="datagrid-subgrid"></table></div>'},onExpandRow:function(index,row){var opts=$(this).datagrid("options");var rd=$(this).datagrid("getRowDetail",index);var dg=getSubGrid(rd);if(!dg.data("datagrid")){createGrid(dg[0],opts.subgrid,row)}rd.find(".easyui-fluid").trigger("_resize");setHeight(this,index);if(conf.options.onExpandRow){conf.options.onExpandRow.call(this,index,row)}},onCollapseRow:function(index,row){setHeight(this,index);if(conf.options.onCollapseRow){conf.options.onCollapseRow.call(this,index,row)}},onResize:function(){var dg=$(this).children("div.datagrid-view").children("table");setParentHeight(this)},onResizeColumn:function(field,width){setParentHeight(this);if(conf.options.onResizeColumn){conf.options.onResizeColumn.call(this,field,width)}},onLoadSuccess:function(data){setParentHeight(this);if(conf.options.onLoadSuccess){conf.options.onLoadSuccess.call(this,data)}}}))}function getSubGrid(rowDetail){var div=$(rowDetail).children("div");if(div.children("div.datagrid").length){return div.find(">div.datagrid>div.panel-body>div.datagrid-view>table.datagrid-subgrid")}else{return div.find(">table.datagrid-subgrid")}}function setParentHeight(target){var tr=$(target).closest("div.datagrid-row-detail").closest("tr").prev();if(tr.length){var index=parseInt(tr.attr("datagrid-row-index"));var dg=tr.closest("div.datagrid-view").children("table");setHeight(dg[0],index)}}function setHeight(target,index){$(target).datagrid("fixDetailRowHeight",index);$(target).datagrid("fixRowHeight",index);var tr=$(target).closest("div.datagrid-row-detail").closest("tr").prev();if(tr.length){var index=parseInt(tr.attr("datagrid-row-index"));var dg=tr.closest("div.datagrid-view").children("table");setHeight(dg[0],index)}}})},getParentGrid:function(jq){var detail=jq.closest("div.datagrid-row-detail");if(detail.length){return detail.closest(".datagrid-view").children(".datagrid-f")}else{return null}},getParentRowIndex:function(jq){var detail=jq.closest("div.datagrid-row-detail");if(detail.length){var tr=detail.closest("tr").prev();return parseInt(tr.attr("datagrid-row-index"))}else{return-1}}});

$.fn.tree.defaults.loadFilter = function (data, parent) {
    var opt = $(this).data().tree.options;
    if (opt.flatData) {
        var idFiled,textFiled,parentField;
        idFiled = opt.idFiled || 'id';
        textFiled = opt.textFiled || 'name';
        parentField = opt.parentField || 'pid';

        var i,l,treeData = [],tmpMap = [];

        for (i = 0, l = data.length; i < l; i++) {
            tmpMap[data[i][idFiled]] = data[i];
        }

        for (i = 0, l = data.length; i < l; i++) {
            if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
                if (!tmpMap[data[i][parentField]]['children'])
                    tmpMap[data[i][parentField]]['children'] = [];
                data[i]['text'] = data[i][textFiled];
                tmpMap[data[i][parentField]]['children'].push(data[i]);
            } else {
                data[i]['text'] = data[i][textFiled];
                treeData.push(data[i]);
            }
        }
        return treeData;
    }
    return data;
};
$.fn.treegrid.defaults.loadFilter = function (data, parent) {
    var opt = $(this).data().treegrid.options;
    if (opt.flatData) {
        var idFiled,textFiled,parentField;
        idFiled = opt.idFiled || 'id';
        textFiled = opt.textFiled || 'name';
        parentField = opt.parentField || 'pid';

        var i,l,treeData = [],tmpMap = [];

        for (i = 0, l = data.length; i < l; i++) {
            tmpMap[data[i][idFiled]] = data[i];
        }

        for (i = 0, l = data.length; i < l; i++) {
            if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
                if (!tmpMap[data[i][parentField]]['children'])
                    tmpMap[data[i][parentField]]['children'] = [];
                data[i]['text'] = data[i][textFiled];
                tmpMap[data[i][parentField]]['children'].push(data[i]);
            } else {
                data[i]['text'] = data[i][textFiled];
                treeData.push(data[i]);
            }
        }
        return treeData;
    }
    return data;
};
$.fn.combotree.defaults.loadFilter = function(data, parent) {
    var opt = $(this).data().tree.options;
    if (opt.flatData) {
        var idFiled, textFiled, parentField;
        idFiled = opt.idFiled || 'id';
        textFiled = opt.textFiled || 'name';
        parentField = opt.parentField || 'pid';

        var i, l, treeData = [], tmpMap = [];

        for (i = 0, l = data.length; i < l; i++) {
            tmpMap[data[i][idFiled]] = data[i];
        }

        for (i = 0, l = data.length; i < l; i++) {
            if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
                if (!tmpMap[data[i][parentField]]['children'])
                    tmpMap[data[i][parentField]]['children'] = [];
                data[i]['text'] = data[i][textFiled];
                tmpMap[data[i][parentField]]['children'].push(data[i]);
            } else {
                data[i]['text'] = data[i][textFiled];
                treeData.push(data[i]);
            }
        }
        return treeData;
    }
    return data;
};



(function () {
//计算数值列总和，
//列需要添加 sum : true
//{title:'出库数量',field:'outStoreQty',price:true,sum:true}
//在onLoadSuccess里添加：$('#gridBox').datagrid('totalNumber',{precision:3,totalTxtField:'unitName'});
//type : 'footer|append',//sum数据是作为footer显示还作为末行插入显示，默认footer
//extendData : {},//除了合计数据，其他需要显示的数据,可以通过function返回json对象
//showSum : true,//是否显示sum数据，可以通过function返回true或者false
//precision:2,//默认为2，取2为小数
//totalTxt : '<b>合计:</b>',//合计标题文字，为空则不显示
// totalTxtField:''//默认为第一个field
  $.extend($.fn.datagrid.methods, {
    totalNumber: function(grid,o) {
      var opt = $(grid).datagrid('options').columns;
      var rows = $(grid).datagrid("getRows");
      var o = $.extend({
        type : 'footer',
        extendData : {},
        precision:2,
        showSum : true,
        totalTxt : '<b class=\'b-totalT\' style=\'color:#000;\'>合计:</b>',
        totalTxtField:opt[0][0].field
      },o||{});
      var footer = new Array();

      footer['sum'] = "";
      for (var i = 0; i < opt[0].length; i++) {
        if (opt[0][i].sum) {
          footer['sum'] = footer['sum'] + sum(opt[0][i].field, 1) + ',';
        }
      };

      if (footer['sum'] != "") {
        var tmp = '{' + footer['sum'].substring(0, footer['sum'].length - 1) + "}";
        var sumData = eval('(' + tmp + ')');
        if(o.totalTxt){
          if (sumData[o.totalTxtField] == undefined) {
            footer['sum'] += '"' + o.totalTxtField + '":"'+o.totalTxt+'"';//第0列显示为合计
            sumData = eval('({' + footer['sum'] + '})');
          } else {
            sumData[o.totalTxtField] = o.totalTxt + sumData[o.totalTxtField];
          }
        }
        if(typeof(o.extendData)== 'function'){
          o.extendData = o.extendData(sumData);
        }
        $.extend(sumData,o.extendData||{});
        // sumData.push(obj);
        if(typeof(o.showSum)== 'function'){
          o.showSum = o.showSum(sumData);
        }
        if(o.showSum){
          if(o.type === 'footer'){
            $(grid).datagrid('reloadFooter', [sumData]);
          }else{
            $(grid).datagrid('appendRow',sumData);
          }
        }

      };
      function sum(filed) {
        var sumNum = 0;
        var str = "";
        for (var i = 0; i < rows.length; i++) {
          var num = rows[i][filed];
          sumNum += Number(num);
        }
        return '"' + filed + '_s":"'+sumNum+'","' + filed + '":"' + sumNum.toFixed(o.precision) + '"';
      }
    }
  });

// 行号显示不全
    $.extend($.fn.datagrid.methods, {
        fixRownumber : function (jq) {
            return jq.each(function () {
                var panel = $(this).datagrid("getPanel");
                //获取最后一行的number容器,并拷贝一份
                var clone = $(".datagrid-cell-rownumber", panel).last().clone();
                //由于在某些浏览器里面,是不支持获取隐藏元素的宽度,所以取巧一下
                clone.css({
                    "position" : "absolute",
                    left : -1000
                }).appendTo("body");
                var width = clone.width("auto").width();
                //默认宽度是25,所以只有大于25的时候才进行fix
                if (width > 25) {
                    //多加5个像素,保持一点边距
                    $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);
                    //修改了宽度之后,需要对容器进行重新计算,所以调用resize
                    $(this).datagrid("resize");
                    //一些清理工作
                    clone.remove();
                    clone = null;
                } else {
                    //还原成默认状态
                    $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");
                }
            });
        }
    });
})(jQuery);
/**
 * 1）扩展jquery easyui tree的节点检索方法。使用方法如下：
 * $("#treeId").tree("search", searchText);
 * 其中，treeId为easyui tree的根UL元素的ID，searchText为检索的文本。
 * 如果searchText为空或""，将恢复展示所有节点为正常状态
 */
(function($) {
	$.extend($.fn.tree.methods, {
		/**
		 * 扩展easyui tree的搜索方法
		 * @param tree easyui tree的根DOM节点(UL节点)的jQuery对象
		 * @param searchText 检索的文本
		 * @param this-context easyui tree的tree对象
		 */
		search: function(jqTree, searchText) {
			//easyui tree的tree对象。可以通过tree.methodName(jqTree)方式调用easyui tree的方法
			var tree = this;

			//获取所有的树节点
			var nodeList = getAllNodes(jqTree, tree);

	 		//如果没有搜索条件，则展示所有树节点
			searchText = $.trim(searchText);
	 		if (searchText == "") {
	 			for (var i=0; i<nodeList.length; i++) {
	 				$(".tree-node-targeted", nodeList[i].target).removeClass("tree-node-targeted");
	 	 			$(nodeList[i].target).show();
	 	 		}
	 			//展开已选择的节点（如果之前选择了）
	 			var selectedNode = tree.getSelected(jqTree);
	 			if (selectedNode) {
	 				tree.expandTo(jqTree, selectedNode.target);
	 			}
	 			return;
	 		}

	 		//搜索匹配的节点并高亮显示
	 		var matchedNodeList = [];
	 		if (nodeList && nodeList.length>0) {
	 			var node = null;
	 			for (var i=0; i<nodeList.length; i++) {
	 				node = nodeList[i];
	 				if (isMatch(searchText, node.text)) {
	 					matchedNodeList.push(node);
	 				}
	 			}

	 			//隐藏所有节点
	 	 		for (var i=0; i<nodeList.length; i++) {
	 	 			$(".tree-node-targeted", nodeList[i].target).removeClass("tree-node-targeted");
	 	 			$(nodeList[i].target).hide();
	 	 		}

	 			//折叠所有节点
	 	 		tree.collapseAll(jqTree);

	 			//展示所有匹配的节点以及父节点
	 			for (var i=0; i<matchedNodeList.length; i++) {
	 				showMatchedNode(jqTree, tree, matchedNodeList[i]);
	 			}
	 		}
      jqTree.removeData('allNodeList');
		},
		/**
		 * 展示节点的子节点（子节点有可能在搜索的过程中被隐藏了）
		 * @param node easyui tree节点
		 */
		showChildren: function(jqTree, node) {
			//easyui tree的tree对象。可以通过tree.methodName(jqTree)方式调用easyui tree的方法
			var tree = this;

			//展示子节点
			if (!tree.isLeaf(jqTree, node.target)) {
				var children = tree.getChildren(jqTree, node.target);
				if (children && children.length>0) {
					for (var i=0; i<children.length; i++) {
						if ($(children[i].target).is(":hidden")) {
							$(children[i].target).show();
						}
					}
				}
			}
		},
		/**
		 * 将滚动条滚动到指定的节点位置，使该节点可见（如果有滚动条才滚动，没有滚动条就不滚动）
		 * @param param {
		 * 	 treeContainer: easyui tree的容器（即存在滚动条的树容器）。如果为null，则取easyui tree的根UL节点的父节点。
		 *  targetNode: 将要滚动到的easyui tree节点。如果targetNode为空，则默认滚动到当前已选中的节点，如果没有选中的节点，则不滚动
		 * }
		 */
		scrollTo: function(jqTree, param) {
			//easyui tree的tree对象。可以通过tree.methodName(jqTree)方式调用easyui tree的方法
			var tree = this;

			//如果node为空，则获取当前选中的node
			var targetNode = param && param.targetNode ? param.targetNode : tree.getSelected(jqTree);

			if (targetNode != null) {
				//判断节点是否在可视区域
				var root = tree.getRoot(jqTree);
				var $targetNode = $(targetNode.target);
				var container = param && param.treeContainer ? param.treeContainer : jqTree.parent();
				var containerH = container.height();
				var nodeOffsetHeight = $targetNode.offset().top - container.offset().top;
				if (nodeOffsetHeight > (containerH - 30)) {
					var scrollHeight = container.scrollTop() + nodeOffsetHeight - containerH + 30;
					container.scrollTop(scrollHeight);
				}
			}
		}
	});

	/**
	 * 展示搜索匹配的节点
	 */
	function showMatchedNode(jqTree, tree, node) {
 		//展示所有父节点
 		$(node.target).show();
 		$(".tree-title", node.target).addClass("tree-node-targeted");
 		var pNode = node;
 		while ((pNode = tree.getParent(jqTree, pNode.target))) {
 			$(pNode.target).show();
 		}
 		//展开到该节点
 		tree.expandTo(jqTree, node.target);
 		//如果是非叶子节点，需折叠该节点的所有子节点
 		if (!tree.isLeaf(jqTree, node.target)) {
 			tree.collapse(jqTree, node.target);
 		}
 	}

	/**
	 * 判断searchText是否与targetText匹配
	 * @param searchText 检索的文本
	 * @param targetText 目标文本
	 * @return true-检索的文本与目标文本匹配；否则为false.
	 */
	function isMatch(searchText, targetText) {
 		return $.trim(targetText)!="" && targetText.indexOf(searchText)!=-1;
 	}

	/**
	 * 获取easyui tree的所有node节点
	 */
	function getAllNodes(jqTree, tree) {
		var allNodeList = jqTree.data("allNodeList");
		if (!allNodeList) {
			var roots = tree.getRoots(jqTree);
 			allNodeList = getChildNodeList(jqTree, tree, roots);
 			jqTree.data("allNodeList", allNodeList);
		}
 		return allNodeList;
 	}

	/**
	 * 定义获取easyui tree的子节点的递归算法
	 */
 	function getChildNodeList(jqTree, tree, nodes) {
 		var childNodeList = [];
 		if (nodes && nodes.length>0) {
 			var node = null;
 			for (var i=0; i<nodes.length; i++) {
 				node = nodes[i];
 				childNodeList.push(node);
 				if (!tree.isLeaf(jqTree, node.target)) {
 					var children = tree.getChildren(jqTree, node.target);
 					childNodeList = childNodeList.concat(getChildNodeList(jqTree, tree, children));
 				}
 			}
 		}
 		return childNodeList;
 	}
})(jQuery);


$.extend($.fn.datagrid.methods,{
  columnMoving: function(jq){
    return jq.each(function(){
      var target = this;
      var cells = $(this).datagrid('getPanel').find('div.datagrid-header td[field]');
      cells.draggable({
        revert:true,
        cursor:'pointer',
        edge:5,
        proxy:function(source){
          var p = $('<div class="tree-node-proxy tree-dnd-no" style="position:absolute;border:1px solid #ff0000"/>').appendTo('body');
          p.html($(source).text());
          p.hide();
          return p;
        },
        onBeforeDrag:function(e){
          e.data.startLeft = $(this).offset().left;
          e.data.startTop = $(this).offset().top;
        },
        onStartDrag: function(){
          $(this).draggable('proxy').css({
            left:-10000,
            top:-10000
          });
        },
        onDrag:function(e){
          $(this).draggable('proxy').show().css({
            left:e.pageX+15,
            top:e.pageY+15
          });
          return false;
        }
      }).droppable({
        accept:'td[field]',
        onDragOver:function(e,source){
          $(source).draggable('proxy').removeClass('tree-dnd-no').addClass('tree-dnd-yes');
          $(this).css('border-left','1px solid #ff0000');
        },
        onDragLeave:function(e,source){
          $(source).draggable('proxy').removeClass('tree-dnd-yes').addClass('tree-dnd-no');
          $(this).css('border-left',0);
        },
        onDrop:function(e,source){
          $(this).css('border-left',0);
          var fromField = $(source).attr('field');
          var toField = $(this).attr('field');
          setTimeout(function(){
            swapField(fromField,toField);
            $(target).datagrid();
            $(target).datagrid('columnMoving');
          },0);
        }
      });

      // swap Field to another location
      function swapField(from,to){
        var columns = $(target).datagrid('options').columns;
        var cc = columns[0];
        _swap(from,to);
        function _swap(fromfiled,tofiled){
          var fromtemp;
          var totemp;
          var fromindex = 0;
          var toindex = 0;
          for(var i=0; i<cc.length; i++){
            if (cc[i].field == fromfiled){
              fromindex = i;
              fromtemp = cc[i];
            }
            if(cc[i].field == tofiled){
              toindex = i;
              totemp = cc[i];
            }
          }
          cc.splice(fromindex,1,totemp);
          cc.splice(toindex,1,fromtemp);
        }
      }
    });
  }
});


$.extend($.fn.datagrid.defaults.editors, {
    readonly: {
        init: function(container, options){
            var input = $('<input type="text" class="datagrid-editable-input txt-editable-readonly" readonly="readonly">').appendTo(container);
            return input;
        },
        getValue: function(target){
            return $(target).val();
        },
        setValue: function(target, value){
            $(target).val(value);
        },
        resize: function(target, width){
            var input = $(target);
            var width = width -14;
            if ($.boxModel == true){
                input.width(width - (input.outerWidth() - input.width()));
            } else {
                input.width(width);
            }
        }
    },
    diy : {
        init: function(container, options){
          // window.console && console.log(container, options);
          var me = this;
          me.opt = options;
          // window.console && console.log(options);
          var input = $('<input type="text" class="datagrid-editable-input txt-editable-diy">').appendTo(container);
          input.data('opt',options);
          input.validatebox(options);
          return input;
        },
        // opt : null,
        // target : null,
        getValue: function(target){
            return $(target).val();
        },
        getVal : function (target,cellkey) {
            var me = this;
            var $tr = $(target).parents('.datagrid-row');
            return $tr.find('td[field="'+cellkey+'"]').find('input').val();
        },
        setVal : function (target,cellkey,val) {
            var me = this;
            var $tr = $(target).parents('.datagrid-row');
            $tr.find('td[field="'+cellkey+'"]').find('input').val(val);
        },
        setValue: function(target, value){
          $(target).val($.trim(value));
          var opt = $(target).data('opt');
          if(!opt.initSet){//防止反复调用
            // me.target  = $(target);
            // window.console && console.log(value);
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
        resize: function(target, width){
            var input = $(target);
            var width = width -14;
            if ($.boxModel == true){
                input.width(width - (input.outerWidth() - input.width()));
            } else {
                input.width(width);
            }
        }
    },
    my97 : {
      init: function(container, opt){
        var me = this;
        me.opt = opt;
        var $td = $(container.context);
        var $tr = $td.parents('tr');
        var ix = $tr.attr('datagrid-row-index');
        var input = $('<input type="text" class="datagrid-editable-input txt-editable-my97" rel="'+ix+'">').appendTo(container);
        var dateOpt = opt.opt||{};
        if(dateOpt&&typeof(dateOpt)=='function'){
          dateOpt = dateOpt(input,$(container.context));
        }
        opt.dateOpt = dateOpt;
        // setTimeout(function () {
          $form.soDate(input,dateOpt);
        // },200);
        input.data('opt',opt);
        input.validatebox(opt);
        return input;
      },
      getValue: function(target){
          return $(target).val();
      },
      setValue: function(target, value){
          $(target).val(value);
          var opt = $(target).data('opt');
          if(!opt.initSet){//防止反复调用
            // me.target  = $(target);
            // window.console && console.log(value);
              $(target).bind('focus.edit',function () {
                // opt.dateOpt.bindShow = true;
                // $form.soDate(this,opt.dateOpt);
                if (opt.focus) {
                  var v = $(target).val();
                  opt.focus(target,v);
                };
              });
            if (opt.keyup) {
              $(target).bind('keyup.edit',function () {
                var v = $(target).val();
                opt.keyup(target,v);
              });
            };
            if (opt.blur) {
              $(target).bind('blur.edit',function () {
                var v = $(target).val();
                opt.blur(target,v);
              });
            };
            opt.initSet = true;
          }
      },
      resize: function(target, width){
          var input = $(target);
          if ($.boxModel == true){
              input.width(width - (input.outerWidth() - input.width()-10));
          } else {
              input.width(width-10);
          }
      }
    }

});
