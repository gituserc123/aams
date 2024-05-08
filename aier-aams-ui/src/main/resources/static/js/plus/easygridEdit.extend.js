define(['pub'],function(){

  var edit = {
    eIndex : undefined,//当前编辑指针
    outEndInit : false,
    disableAdd : function (grid) {
      var me = this;
      var grid = grid || '#gridBox';//默认值 #gridBox
      $(grid).data('disadd',true);
    },
    canAdd : function (grid) {
      var me = this;
      var grid = grid || '#gridBox';//默认值 #gridBox
      $(grid).data('disadd',false);
    },
    disableEdit : function (grid){
      var me = this;
      var grid = grid || '#gridBox';//默认值 #gridBox
      $(grid).data('disedit',true);
    },
    canEdit : function (grid) {
      var me = this;
      var grid = grid || '#gridBox';//默认值 #gridBox
      $(grid).data('disedit',false);
    },
    outClickEndEdit : function (grid,onEndEdit) {
      var me = this;
      var grid = grid || '#gridBox';//默认值 #gridBox
      // window.console && console.log('outClickEndEdit running');
        $(document).on('click',function(e) {
          // window.console && console.log(e);
          if($(grid).data('gridState')==='add'){//新增状态不验证行，切换编辑状态为edit
            $(grid).data('gridState','edit');
            return;
          }

          var $p = $(e.target).parents('.datagrid-btable');
          var $p2 = $(e.target).parents('.datebox-calendar-inner');
            if($('body').find($(e.target)).length && !$p.length && !$p2.length){ // target存在 并且不在编辑区域范围内
                me.endEditing(grid,onEndEdit);
            }
        });
    },
    validIndex : null,//缓存无输入验证时当前行index
    validateGrid : function (grid,o) {//不进入编辑状态对grid的数据有效性进行验证
      var me = this;
      var o = o ||{};
      var grid = grid || '#gridBox';//默认值 #gridBox
      if (!me.endEditing(grid)){return false;}
      var rows = !o.checked?$(grid).datagrid('getData').rows:$(grid).datagrid('getChecked');
      var opt = $(grid).datagrid('options').columns;
      var fieldArr = [];
      $.each(opt,function () {//轮询columns，提取field和对应的验证条件
        var _self = this;
          $.each(_self , function (i,v) {
            if (v.editor&&v.editor.options) {
              var txtOpt = v.editor.options;
              var txtO = {};
              if (txtOpt.required && (!txtOpt.novalidate)) {
                txtO.required = true;
              };
              if (txtOpt.validType && (!txtOpt.novalidate)) {
                txtO.validType = txtOpt.validType;
              };
              // window.console && console.log(txtO.required,txtO.validType);
              if (txtO.required || txtO.validType) {
                txtO.field = v.field;
                fieldArr.push(txtO);
              };
            };
          });
      });
      if (fieldArr.length) {//如果有编辑和验证条件，开始进行验证
        var errRowIndex = -1;
        //var errFieldIndex = null;
        $.each(rows, function (j,row) {
            var vh = true;
        	me.validIndex = j;
            $(grid).datagrid('selectRow', j);
            $.each(fieldArr, function (i,v) {
                var val = row[v.field];
                var validate,v1=true,v2 =true;
                if (v.required&&(val===''||val===undefined)) {v1 =  false;};//必填
                if (v.validType) {//validdType验证
                	if(v.validType instanceof Array){
                		$.each(v.validType, function (k,m){
                    		var validOpt = m.split(/\[|\]/);
                            // window.console && console.log(validOpt);
                            var validRule = validOpt[0];
                            var validParam = validOpt[1]?eval('['+validOpt[1]+']'):'';
                            // window.console && console.log(validOpt[1],validParam);
                            v2 = v2 && $.fn.validatebox.defaults.rules[validRule].validator.call(null,val,validParam);
                    	});
                	}
                	else{
                		var validOpt = v.validType.split(/\[|\]/);
                        // window.console && console.log(validOpt);
                        var validRule = validOpt[0];
                        var validParam = validOpt[1]?eval('['+validOpt[1]+']'):'';
                        // window.console && console.log(validOpt[1],validParam);
                        v2 = v2 && $.fn.validatebox.defaults.rules[validRule].validator.call(null,val,validParam);
                	}
                };
                validate = v1&&v2;
                if (!validate) {
                    // window.console && console.log(j,row.item_name);
                    errRowIndex = j;//获取到第一个错误行的index
                    //errFieldIndex = i;
                     //window.console && console.log(errRowIndex);
                    vh = false;
                    return false;
                };
              });

        	me.validIndex = null;
             return vh;
        });
        if (errRowIndex>-1) {
        	//找到 grid对应的 datagrid-view2，对应的错误行的第一个td
        	setTimeout(function(){
        		var $p = $(grid).prev('div').find('.datagrid-btable').find('tr').eq(errRowIndex).find('td').eq(0).click();//trgiier错误行，进入编辑状态
        	},200);
          return false;//返回失败
        };
        $(grid).datagrid('clearSelections');
      };
      return true;
    },
    endEditing : function(grid,onEnd){//结束编辑

      var me = this;
      var grid = grid || '#gridBox';//默认值 #gridBox
      var $grid = $(grid);
      var disedit = $grid.data('disedit');
      var eIndex = $grid.data('eindex');
      var unvalidate = $('body').data('unValidEdit');
      if (unvalidate) {return true;}
      if (disedit) {return false;}
      if (eIndex == undefined){return true;}
      var $editing = $grid.parent('.datagrid-view').find('.datagrid-row-editing');
      eIndex = $editing.index();
      if ($grid.datagrid('validateRow', eIndex)){
        $grid.datagrid('endEdit', eIndex);//结束编辑
        $grid.datagrid('unselectRow', eIndex);//取消当前行选中状态
        $grid.removeData('eindex');//清除index
        // me.eIndex = undefined;
        if(onEnd){
            onEnd(eIndex);
        }
        // window.console && console.log('end doing');
        return true;
      } else {
        $editing.find('.validatebox-invalid:first').focus();
        return false;
      }
    },
    ifEndEdit : function (fn,grid) {//判断是否处在编辑状态，编辑状态退出执行事件
      var me = this;
      var grid = grid || '#gridBox';//默认值 #gridBox
      if (me.endEditing(grid)){
        fn&&fn();
      }else{
        layer.msg('请完成编辑！',{icon:0});
        // $pop.msg('请完成编辑！');
      }
    },
    getGridData : function (grid) {//获取grid当前行数据
      var me = this;
      var grid = grid || '#gridBox';//默认值 #gridBox
      var rows = null;
      me.ifEndEdit(function () {
        rows = $(grid).datagrid('getRows');
      },grid);
      return rows;
    },
    getChanges : function(grid){//获取grid edit的变化
      var me = this;
      var grid = grid || '#gridBox';//默认值 #gridBox
      var rows = null;
      me.ifEndEdit(function () {
        rows = $(grid).datagrid('getChanges');
      },grid);
      return rows;
    },
    rejectChanges : function(grid){//放弃编辑
      var me = this;
      var grid = grid || '#gridBox';//默认值 #gridBox
      $(grid).datagrid('rejectChanges');
    },
    delRows : function(grid,selected){//删除row，checked : true|false => getChecked|getSelections
      var me = this;
      var grid = grid || '#gridBox';//默认值 #gridBox
      var rows;
      // if (me.endEditing(grid)){
        if(Object.prototype.toString.call(selected) === '[object Array]'){
          rows = selected;
        }else{
          rows= $(grid).datagrid(selected?"getSelections":"getChecked");
        }
        // window.console&&console.log(rows);
        if(rows.length){
          $.each(rows,function(){
            var ix = $(grid).datagrid('getRowIndex',this)
            $(grid).datagrid('deleteRow',ix);
          });
        }else{
          $pop.msg('请'+(selected?'选择':'勾选')+'需要删除的行！');
        }
      // }
    },
    getTxt : function (target,cellkey) {//获取同行的另一个字段
        var $tar = $(target);
        var $tr = $tar.parents('.datagrid-row');
        return $tr.find('td[field="'+cellkey+'"]').find('input');
    },
    getVal : function (target,cellkey) {//获取同行的另一个字段
        var $tar = $(target);
        var $tr = $tar.parents('.datagrid-row');
        return $tr.find('td[field="'+cellkey+'"]').find('input').val();
    },
    setVal : function (target,cellkey,val) {//设置同行的另一个字段
        var $tar = $(target);
        var $tr = $tar.parents('.datagrid-row');
        $tr.find('td[field="'+cellkey+'"]').find('input').val(val);
    },
    addNewRow : function(opt){//增加新的行
      //debugger;
      var me = this;
      var o = $.extend({
        grid : '#gridBox',// 对应的grid，默认为#gridBox
        focusField : null,//默认focus的字段
        canAdd : true,//行末尾回车是否可以新增行
        rowCanEdit : null,//function ($row,o.index) {}判断某行是否可以编辑，如果有函数return true才可以编辑，否则不可以编辑
        canChangeRow : true,
        onEndOneCellEdit : function($cell){},
        onEnterCellFocus : function($cell){},
        onEnterNextRow : null,//回车换行回调函数
        onBeforeEdit : function (index,$grid) {},
        onEndEdit : null,//当行退出编辑时执行
        initData : {}//新增行初始化默认数据
      },opt||{});

      $(o.grid).data('gridState','add');//标记grid当前为新增行状态，当进入 outClickEndEdit 事件时，不即时触发验证 造成焦点错位或关闭编辑的现象
      var canAdd = (!$(o.grid).data('disadd'))&&o.canAdd;
      if (canAdd&&me.endEditing(o.grid,o.onEndEdit)){
        var initD = typeof(o.initData)== 'function' ? o.initData($(o.grid),o) : $.extend(true, {}, o.initData);
        $(o.grid).datagrid('appendRow',initD);
        var ix = $(o.grid).datagrid('getRows').length-1;
        me.editRow($.extend(true,o,{
          index : ix,
          cellField : null
        }));

        return ix;
      }
    },
    editRow : function(opt,returnRow){//编辑行
      var o = $.extend(true,{
        grid : '#gridBox',//对应的grid，默认为#gridBox
        index : null,//行index
        cellField : null,//当前cell 的field
        focusField : null,//默认focus的字段
        canAdd : true,//行末尾回车是否可以新增行
        rowCanEdit : null,//function ($row,o.index) {}判断某行是否可以编辑，如果有函数return true才可以编辑，否则不可以编辑
        canChangeRow : true,
        onEndOneCellEdit : function($cell){},
        onEnterCellFocus : function($cell){},
        onEnterNextRow : null,//回车换行回调函数
        onBeforeEdit : function (index,$grid) {},
        onEndEdit : null,//当行退出编辑时执行
        initData : {}//新增行初始化默认数据
      },opt||{});
      var me = this;
      // window.console && console.log(o);
      // window.console&&console.log(o.cellField);
      var $grid = $(o.grid);
      var outEndInit = $grid.data('outendedit');
      if(o.canChangeRow){//支持键盘换行
        o.onEnterCellFocus = function($cell){
          me.editCellField = $cell.parents('td.x-editor').attr('field');
          opt.onEnterCellFocus&&opt.onEnterCellFocus($cell);
        };
        me[o.grid+'editOpt'] = o;
      }
      if(!outEndInit){//外部点击结束编辑
        me.outClickEndEdit(o.grid,o.onEndEdit);
        $grid.data('outendedit',true);
        // me.outEndInit = true;
      };
      var eIndex = $grid.data('eindex');
      if (eIndex != o.index){

          var $gridWrap = $grid.parent('.datagrid-view');
          var $row = $gridWrap.find('.datagrid-row[datagrid-row-index="'+o.index+'"]');
          var canedit = $row.attr('canedit');//获取是否被合并不能编辑
          if (canedit==='merge') {//合并后的行不允许编辑，会造成列错乱
            layer.msg('合并后的行不能编辑！',{offset:'t'});
            return false;
          };

          if(o.rowCanEdit  && !o.rowCanEdit($grid.datagrid('getRows')[o.index],o.index)){//如果不满足行可以编辑条件，退出编辑
              // o.index = o.index+1;
              // if($grid.datagrid('getRows')[o.index]){//如果下一行存在，跳到下一行编辑
              //   me.editRow(o);
              // }
              return false;
          }
          if (me.endEditing(o.grid,o.onEndEdit)){//如果通过验证可以结束上一次编辑
            var rowLen = $grid.datagrid('getRows').length;
            // window.console&&console.log('index:'+index+',rowLen:'+rowLen);
            if(o.index>=rowLen){//如果行号大于现在所有行，此行不存在
              // me.addNewRow(o.focusField,o.grid,o.initData);
              if (o.canAdd) {
                  me.addNewRow(o);
              };
            }else{//此行存在
                $grid.datagrid('selectRow', o.index).datagrid('beginEdit', o.index);//开启选中行编辑
                eIndex = o.index;
                 $grid.data('eindex',o.index);
                 o.onBeforeEdit(eIndex,$grid);
                // window.console && console.log(eIndex);
                var ed = $grid.datagrid('getEditor', { index: eIndex, field: o.cellField});
                if (returnRow||!ed) { ed = $grid.datagrid('getEditor', { index: eIndex, field: o.focusField })}//换行或者没有 cellField，取 focusField 为默认focus 输入框
                if (ed) {
                    var $t = $(ed.target);
                    if ($t.hasClass('combo-f')||$t.hasClass('textbox-f')) {
                      $t = $t.parents('.x-editor').find('.textbox-text');
                    };
                    $t.focus();//选中行第一个字段focus
                    o.onEnterCellFocus($t);

                    // var $txt = $("input.datagrid-editable-input");
                    var $txt = $gridWrap.find(".datagrid-row-editing input:visible,.datagrid-row-editing textarea:visible").filter(function () {
                      if ($(this).hasClass('txt-editable-readonly')||$(this).hasClass('validatebox-readonly')||$(this).hasClass('validatebox-disabled')) {return false;};
                      return true;
                    });
                    $txt.bind('focus.k',function(){
                      o.onEnterCellFocus($(this));
                    });
                    $txt.bind('keyup.kr',function (e) {
                      txtKeyupDo(e,this);
                    });
                    return eIndex;
                }else{
                  window.console && console.log('焦点字段不存在或没有设置为编辑状态！');
                };
              }
          } else {
              $grid.datagrid('selectRow', eIndex);
              $('.validatebox-invalid,.textbox-invalid .textbox-text',$gridWrap).each(function(i,v){//所有没有通过验证的输入框
                if(i===0){$(this).focus();return false;}//第一个被focus
              });
          }

        };
        function txtKeyupDo(e,_self) {//输入框回车时间
          if(e.keyCode==13){//回车到下一个输入框事件
            txtChange(e,_self);
          }
          if(e.keyCode==39){//回车到下一个输入框事件,事件仍然有问题
            txtChange(e,_self,'right');
          }
          if(e.keyCode==37){//回车到下一个输入框事件,事件仍然有问题
            txtChange(e,_self,'left');
          }
        }

        function txtChange(e,_self,keyType) {
          var _self = $(_self);
          //window.console && console.log(_self);
          if(keyType && ($(e.target).hasClass('txt-editable-my97') || $(e.target).parents('.combo').length)){return;}
          // if(_self.hasClass('txt-editable-my97 validatebox-text')){//my97多次获得焦点死机现象处理
          if(!_self.validatebox('isValid')){return;}
          // }
          var $txt = $gridWrap.find(".datagrid-row-editing input:visible,.datagrid-row-editing textarea:visible").filter(function () {
            if ($(this).hasClass('txt-editable-readonly')||$(this).hasClass('validatebox-readonly')||$(this).hasClass('validatebox-disabled')) {return false;};
            return true;
          });
          var iIndex = $txt.index(_self);
          o.onEndOneCellEdit(_self);
          var $next = $txt.eq(iIndex+1);
          if((!keyType&&e.ctrlKey) || keyType=='left'){
            $next = $txt.eq(iIndex-1);
          }
          // window.console && console.log($txt,$next);
          if($next&&$next.length){//不是最后一个输入框
            if($next.hasClass('combobox-f')){$next = $txt.eq(iIndex+2)};//跳过下拉控件的隐藏输入框
            $next.focus();
            // o.onEnterCellFocus($next);
            $next.unbind('keyup.kr').bind('keyup.kr',function (e) {
              txtKeyupDo(e,this);
            });
          }else {//最后一个输入框，跳到下一行
            var ix = eIndex + 1;
            o.index = ix;
            if (o.onEnterNextRow) {
              if (o.onEnterNextRow(o, $(this))) {
                me.editRow(o,true)
              };
            } else {
              me.editRow(o,true);
            };
          }
        }
    },
    editCellField : null,
    keyUpDownChagneRow : function(grid){
      var me = this;
      var grid = grid || '#gridBox';//默认值 #gridBox
      var $grid = $(grid);

      $grid.datagrid('getPanel').panel('panel').attr('tabindex', 1).bind('keydown', function (e) {
        // if(e.ctrlKey){
        // window.console && console.log(e.target);
          switch (e.keyCode) {
            case 38: // up
              if($(e.target).parents('.combo').length && !e.ctrlKey){return;}//选择框不响应左右键
              var selected = $grid.datagrid('getSelected');
              if (selected) {
                var index = $grid.datagrid('getRowIndex', selected);
                // window.console && console.log(index);
                if(index-1>-1){
                  var editOpt = me[grid+'editOpt'];
                  editOpt.cellField = me.editCellField;
                  editOpt.index = index-1;
                  // window.console && console.log(editOpt);
                  me.editRow(editOpt);
                }
                return false;
              }
              break;
            case 40: // down
              if($(e.target).parents('.combo').length && !e.ctrlKey){return;}//选择框不响应左右键
              var selected = $grid.datagrid('getSelected');
              if (selected) {
                var index = $grid.datagrid('getRowIndex', selected);
                var rowLen = $grid.datagrid('getRows').length;
                if(index < rowLen-1){
                  var editOpt = me[grid+'editOpt'];
                  editOpt.cellField = me.editCellField;
                  editOpt.index = index+1;
                  // window.console && console.log(editOpt);
                  me.editRow(editOpt);
                }
                return false;
              }
              break;
          }
        // }
      });

    },
    setCellsVal : function (index,data,grid) {
      var grid = grid || '#gridBox';//默认值 #gridBox
      var  $grid = $(grid);
      $.each(data,function (k,v) {
          var $cell = $grid.datagrid('getEditor', {index:index,field:k});
          if($cell){
            $cell.actions.setValue($cell.target,v);
          }
      });
    },
    getCell : function(field,grid){
      var grid = grid || '#gridBox';//默认值 #gridBox
      var  $grid = $(grid);
      var index = $grid.prev().find('.datagrid-row-editing').attr('datagrid-row-index')*1;
      var seled = $grid.datagrid('getRows')[index];
      if(!index){
        seled = $grid.datagrid('getSelected');
        index = $grid.datagrid('getRowIndex',seled);
      }
      var $cell = $grid.datagrid('getEditor', {index:index,field:field});
      if($cell){
        return $cell.target;
      }
    },
    getCellVals : function (fields,grid) {
    	var me = this;
      var grid = grid || '#gridBox';//默认值 #gridBox
      var  $grid = $(grid);
      var index = $grid.prev().find('.datagrid-row-editing').attr('datagrid-row-index')*1;
      var gridRows = $grid.datagrid('getRows');
      var seled = gridRows[index];
      if(!index&&(index!==0)){
        seled = $grid.datagrid('getSelected');
        index = $grid.datagrid('getRowIndex',seled);
      }
      if(index===0&&me.validIndex!==null){//非输入验证时取值
    	  index = me.validIndex;
    	  seled = gridRows[index];
      };
      var isString = typeof(fields) === 'string';
      if(isString){fields = [fields];}
      var vals = {};
      vals.index = index;
      $.each(fields,function (i,v) {
          var $cell = $grid.datagrid('getEditor', {index:index,field:v});
          if($cell){
        	  vals[v] = $cell.actions.getValue($cell.target);
          }else{
        	  vals[v] = seled[v];
          }
      });
      return isString?vals[fields]:vals;
    },
    setCellVals : function (data,grid) {
      var grid = grid || '#gridBox';//默认值 #gridBox
      var  $grid = $(grid);
      var index = $grid.prev().find('.datagrid-row-editing').attr('datagrid-row-index');
      if(!index&&(index!==0)){
        var seled = $grid.datagrid('getSelected');
        index = $grid.datagrid('getRowIndex',seled);
      }
      $.each(data,function (k,v) {
          var $cell = $grid.datagrid('getEditor', {index:index,field:k});
          if($cell){
            $cell.actions.setValue($cell.target,v);
          }
      });
    },
    getGroupFieldByRow : function (opt) {//根据row信息返回field，主要用在有成组行，返回对应组行的所有field信息，默认返回数组
       var o = $.extend({
          grid : '#gridBox',//grid的id
          row : null,//需要传入被选择的行数据
          groupIds: 'groupId',//组标识
          field : 'id',//需要返回的field
          returnString : false,//返回格式是否是字符串，默认为数组
       },opt||{});
       //debugger;
        var  $grid = $(o.grid);
        var groupArr = [];
        if(o.row[o.groupIds]){
            var rowsData = $grid.datagrid('getRows');
            $.each(rowsData,function (i,v) {
                if(v[o.groupIds] == o.row[o.groupIds]){
                    groupArr.push(v[o.field]);
                }
            });
        }else{
          groupArr.push(o.row[o.field]);
        }
        return o.returnString?groupArr.join(','):groupArr;
    },
    getUDRow : function (type,grid,ix,len) {
        var me = this;
        var rix = null;
        if(type=='up'){
            while(ix>=0){
              var todown = $(grid).datagrid('getData').rows[ix];
              if(todown.rowMerge){
                ix--;
              }else{
                rix = ix;
                break;
              }
            }
        }

        if(type=='down'){
            while(ix<len){
              var todown = $(grid).datagrid('getData').rows[ix];
              if(todown.rowMerge){
                ix++;
              }else{
                rix = ix;
                break;
              }
            }
        }
        return rix;
    },
    _sortRow : function (index, type, grid) {//排序
      var me = this;
      if ("up" == type) {
        if (index != 0) {
           // var todownIx = index-1;

            var toup = $(grid).datagrid('getData').rows[index];
            if(toup.rowMerge){
              $pop.msg('不能移动已经合并过的组！');
              return false;
            }
            var todownIx = me.getUDRow('up',grid,index-1);
            if(todownIx!==null){
                var todown = $(grid).datagrid('getData').rows[todownIx];
                $(grid).datagrid('getData').rows[index] = todown;
                $(grid).datagrid('getData').rows[todownIx] = toup;
                $(grid).datagrid('refreshRow', index);
                $(grid).datagrid('refreshRow', todownIx);
                $(grid).datagrid('selectRow', todownIx);
              }
        }
      } else if ("down" == type) {
          var rows = $(grid).datagrid('getRows').length;
          if (index != rows - 1) {
              var todown = $(grid).datagrid('getData').rows[index];
              if(todown.rowMerge){
                  $pop.msg('不能移动已经合并过的组！');
                  return false;
              }
              var toupIx = me.getUDRow('down',grid,index+1,rows);
              if(toupIx!==null){
                  var toup = $(grid).datagrid('getData').rows[toupIx];
                  $(grid).datagrid('getData').rows[toupIx] = todown;
                  $(grid).datagrid('getData').rows[index] = toup;
                  $(grid).datagrid('refreshRow', index);
                  $(grid).datagrid('refreshRow', toupIx);
                  $(grid).datagrid('selectRow', toupIx);
              }
          }
      }
    },
    _nbSortRow : function (grid) {
        var me = this;
        var grid = grid || '#gridBox';//默认值 #gridBox
        var  $grid = $(grid);

        var chkedRows = $grid.datagrid("getChecked");
        var firstIx = $grid.datagrid("getRowIndex",chkedRows[0]);
        var rowsData = $grid.datagrid("getRows");
        // window.console && console.log('所有行数据：',rowsData,' ；被选择行数据：',chkedRows,'；被选择第一行index：',firstIx);
        // $grid.datagrid("loadData",chkedRows);
        var checkedIxArr = [];
        $.each(chkedRows,function (i,v) {
          if(i>0){
            checkedIxArr.push($grid.datagrid("getRowIndex",v));
          }
        });
        // window.console && console.log(checkedIxArr);
        var chkedLen = checkedIxArr.length;
        if(chkedLen>0){
            for (var i = chkedLen-1; i >= 0; i--) {
              rowsData.splice(checkedIxArr[i], 1);
            };
            for (var i = chkedLen ; i > 0; i--) {
              //window.console && console.log(i);
              rowsData.splice(firstIx+1,0,chkedRows[i]);
            };
        }
        // window.console && console.log(rowsData);
        $grid.datagrid("loadData",rowsData);
        callback&&callback(chkedRows);
        return chkedRows;
    },
    sgNO :0,
    getSgNO : function(groupNO,grid){
      var me = this;
      me.sgNO = 0;
      var grid = grid || '#gridBox';
      var $grid = $(grid);
      var rows = $grid.datagrid('getRows');
      $.each(rows,function (i, v) {
        var n = v[groupNO];
        if(n&&n*1>me.sgNO){me.sgNO =n*1;}
      });
    },
    sortMergeRows: function (opt) {
        var me = this;
        var o = $.extend({
          grid : '#gridBox',//grid
          groupNO : null,//为成组添加序列号
          merge : false,//是否合并对应的行
          callback : null//返回事件
        },opt||{});
        var  $grid = $(o.grid);
        var chkedRows = $grid.datagrid("getChecked");
        if(o.merge&&(!me.ifCanMerge(chkedRows))){//如果需要合并，判断是否可以合并
          return false;
        }
        // window.console && console.log(o);
        var firstIx = $grid.datagrid("getRowIndex",chkedRows[0]);
        // var rowsData = $grid.datagrid("getRows");
        // window.console && console.log('所有行数据：',rowsData,' ；被选择行数据：',chkedRows,'；被选择第一行index：',firstIx);
        $.each(chkedRows,function (i,v) {
            if(i>0){
              var ix = $grid.datagrid("getRowIndex",v);
              $grid.datagrid("deleteRow",ix);
            }
        });
        var chkedLen = chkedRows.length;
        if(chkedLen>1){
            if(o.groupNO){
              me.getSgNO(o.groupNO,o.grid);
              //window.console && console.log(me.sgNO);
              me.sgNO++;
              chkedRows[0][o.groupNO] = me.sgNO;
            }
            for (var i = chkedLen-1; i > 0; i--) {
              // window.console && console.log(i);
              if(o.groupNO){
                chkedRows[i][o.groupNO] = me.sgNO;
              }
              $grid.datagrid("insertRow",{
                index : firstIx+1,
                row : chkedRows[i]
              });
            };
        }

        if(o.merge){
            me.mergeRowsCells($.extend({
                grid : o.grid,
                data : chkedRows
            },o.merge ||{}));
        }

        $grid.datagrid('clearChecked');
        // window.console && console.log(rowsData);
        o.callback&&o.callback(chkedRows);
        return chkedRows;
    },
    moveRowUp : function (grid) {//往上
      var me = this;
      var grid = grid || '#gridBox';//默认值 #gridBox
      me.ifEndEdit(function () {
        var row = $(grid).datagrid('getSelected');
        var index = $(grid).datagrid('getRowIndex', row);
        me._sortRow(index, 'up', grid);
      },grid);
    },
    moveRowDown : function (grid) {//往下
      var me = this;
      var grid = grid || '#gridBox';//默认值 #gridBox
      me.ifEndEdit(function () {
        var row = $(grid).datagrid('getSelected');
        var index = $(grid).datagrid('getRowIndex', row);
        me._sortRow(index, 'down', grid);
      },grid);
    },
    geteRowsByRowIndex : function(opt){
      var o = $.extend({
        // grid : '#gridBox',//grid
        groupIds : 'combiSeq',//成组的标识
        move : 'up',
        allRows : [],
        rowIndex : null
      },opt);

      // var $grid = $(o.grid);
      var allRows = o.allRows;
      var seledRow = allRows[o.rowIndex];
      var idx = o.rowIndex;
      var rows = [];
      var groupId = seledRow[o.groupIds];
      var sId = [];
      var cIdx = o.rowIndex;
      if(groupId){
        $.each(allRows,function (i,v) {
          if(v[o.groupIds] == groupId){
            sId.push(i);
            rows.push(v);
          }
        });
        cIdx = sId[0];
        idx = o.move=='up'?sId[0]:sId[sId.length-1];//向上对应第一行index，向下对应最后一行index
      }else{
        rows = [seledRow];
      }
      // window.console && console.log(seledRows,seledIx);
      return {
        rows : rows,
        cIdx : cIdx,
        idx : idx
      }
    },
    moveRow : function (opt) {
      var me = this;
      var o = $.extend({
        grid : '#gridBox',//grid
        groupIds : 'combiSeq',//成组的标识
        move : 'up',
        afterFnLoadRows : true //计算完是否直接加载Rows
      },opt||{});
      var $grid = $(o.grid);
      var allRows = $grid.datagrid('getRows');
      var allRLen = allRows.length;
      var seledRow = $grid.datagrid('getSelected');
      var seledIdx = $grid.datagrid('getRowIndex',seledRow);

      var rData = me.geteRowsByRowIndex({//根据当前行index获取回对应行(包括成组的行)
        allRows : allRows,
        move : o.move,
        groupIds : o.groupIds,
        rowIndex : seledIdx
      });
      // window.console && console.log(rData);
      if(rData.idx == 0 && o.move=='up'){
        $pop.msg('已是第一行，无法上移！');
        return {rows:allRows,idx:0};
      }
      if(rData.idx == allRLen-1 && o.move=='down'){
        $pop.msg('已是最后一行，无法下移！');
        return {rows:allRows,idx:allRLen-1};
      }

      var chgRowIdx = o.move=='up'?rData.idx-1:rData.idx+1;
      var cData = me.geteRowsByRowIndex({//根据index获取回需要更换位置的对应行(包括成组的行)
        allRows : allRows,
        move : o.move,
        groupIds : o.groupIds,
        rowIndex : chgRowIdx
      });
      // window.console && console.log(cData);
      var aRArr,bRArr;
      if(o.move=='up'){
        aRArr = rData;
        bRArr = cData;
      };
      if(o.move=='down'){
        aRArr = cData;
        bRArr = rData;
      };
      allRows.splice(aRArr.cIdx,aRArr.rows.length);
      $.each(aRArr.rows,function(i,v){
        allRows.splice(bRArr.cIdx,0,v);
        bRArr.idx++;
      });
      // window.console && console.log(allRows);
      // o.afterFnLoadRows && $grid.datagrid('loadData',allRows);
      var idx = o.move=='up'?cData.cIdx:rData.cIdx+cData.rows.length;
      if(o.afterFnLoadRows){
        $grid.datagrid('loadData',allRows);
        $grid.datagrid('selectRow',idx);
      }
      return {rows:allRows,idx:idx};//返回重新排列的rows及操作行的新index
    },
    getMoveRows : function (opt) {
      var me = this;
      var o = $.extend({
          grid : '#gridBox',//grid
          groupIds : 'groupId',//成组的标识
          move : 'up',
          callback : null//返回事件
      },opt||{});
      var $grid = $(o.grid);
      var row = $grid.datagrid('getSelected');
      if(row&&row[o.groupIds]){
        var groupId = row[o.groupIds];
        var rows =$grid.datagrid('getRows');
        var ix = $grid.datagrid('getRowIndex',row);
        var otherRow = rows[o.move=='up'?(ix-1):(ix+1)];
        if(otherRow&&otherRow[o.groupIds]===groupId){
          var returnRows = o.move=='up'?[otherRow,row]:[row,otherRow];
          var nowIx = o.move=='up'?(ix-1):(ix+1);
          o.callback&&o.callback(returnRows,nowIx);
          return {data:returnRows,index:nowIx};
        }else{
          $pop.msg(o.move=='up'?'已经是组内第一条了！':'已经是组内最后一条了！');
        }
      }else{
          $pop.msg('只能移动同组内的医嘱');
      }
    },
    ifCanMerge : function (rows) {
        if(rows.length<=1){
            $pop.msg('请选择多行进行合并！');
            return false;
        }
        var canMerge = true;
        $.each(rows,function (i,v) {
            if(v.rowMerge){
                canMerge = false;
                return false;
            }
        });
        if(!canMerge) {
            $pop.msg('不能合并已经合并过的组！');
        };
        return canMerge;
    },
    initMergeRows : function (opt) {//根据数据初始化合并行成组
      var me = this;
      var o = $.extend({
          grid : '#gridBox',//grid
          groupIds : 'groupId',//成组的标识
          data : {},//如果有data，则直接使用data
          hideCheckbox : true,
          groupKeys : null,//成组key
          strArr : null,//需要合并的字段，为数组
          callback : null//返回事件
      },opt||{});

      var  $grid = $(o.grid);
       var rows = o.data.rows || $grid.datagrid("getData").rows;
       var groupO = {};
       $.each(rows,function (i,v) {
           if(v[o.groupIds]){
              var groupId = o.groupKeys?v[o.groupKeys]+v[o.groupIds]:v[o.groupIds];//如果有多出来的成组key
              if(groupId){//如果成组
                  if(!groupO[groupId]){
                   groupO[groupId] = [];
                  }
                  groupO[groupId].push(v);
              }
           }
       });
        // window.console && console.log(groupO);
       $.each(groupO,function (k,v) {
         // window.console && console.log(k);
         me.mergeRowsCells($.extend(o,{data:v,needMsg:false,hideCheckbox:o.hideCheckbox}));
       });
    },
    getCheckedRows : function (opt) {//获取含成组的行数据
      var me = this;
      var o = $.extend({
          grid : '#gridBox',//grid
          data : null,//根据数据来获取选择的组数据          callback : null//返回事件
      },opt||{});
      var  $grid = $(o.grid);
       var checked = o.data?o.data:[];
       var realChecked = [];
       var rows = $grid.datagrid("getData").rows;

       if(checked.length==0){//如果没有o.data 则从dom上获取
         var $wrap = $grid.parents('.datagrid-view').find('.datagrid-btable');
         var $chks = $wrap.find('.datagrid-cell-check').find(":checkbox:visible");
         $chks.each(function(i,v){
           if($(v).prop('checked')){
             var ri = $(v).parents('tr').attr('datagrid-row-index');
             checked.push(rows[ri]);
           }
         });
       }
       if(checked.length){
          $.each(checked,function (i,v) {
            realChecked.push(v);
            if(v.mergeLength && v.mergeLength>1){
//                realChecked.push(v);
                var rowIx = $grid.datagrid('getRowIndex',v);
                for (var i = 1; i < v.mergeLength; i++) {
                  realChecked.push(rows[rowIx+i]);
                };
            }
          });
//           window.console && console.log(realChecked);
          o.callback&&o.callback(realChecked,$grid);
          return realChecked;
       }else{
          $pop.msg('没有选择任何行！');
          return [];
       }
    },
    mergeRowsCells : function (opt) {//合并行单元格
      var me = this;
      var o = $.extend({
          grid : '#gridBox',//grid
          strArr : [],//需要合并的字段，为数组
          data : null,
          callback : null,//返回事件
          hideCheckbox : true,
          needMsg :true
      },opt||{});

      var  $grid = $(o.grid);


      me.ifEndEdit(function () {
          var rows = o.data || $grid.datagrid("getChecked");
          // window.console && console.log(rows);
          var rowsData = $grid.datagrid("getRows");
          if (rows&&rows.length>1) {
            // var canMerge = me.ifCanMerge(rows);
            // if(!canMerge) {return false;};
            var rowLen = rows.length;
            var firstIx = null;
            var $gridWrap = $grid.parent('.datagrid-view');

            var cellCommonData = {};//同组后，将合并值设置为第一行数据的对应值
            if(o.strArr&&o.strArr.length){
              $.each(o.strArr,function(j,v){
                cellCommonData[v] = rows[0][v];
              });
            }
            $.each(rows,function (i,v) {
                var ix = $grid.datagrid('getRowIndex',v);
                var rowData = $.extend(true,{
                  rowMerge : true,
                  mergeLength : i===0?rowLen:1
                },cellCommonData ||{});
                rowsData[ix] = $.extend(rowsData[ix],rowData);//更新grid行数据为合并数据并添加rowMerge和MergeLength属性
                //updateRow效率低，弃用
                // $grid.datagrid('updateRow',{
                //   index : ix,
                //   row : rowData
                // });
                var $row = $gridWrap.find('.datagrid-row[datagrid-row-index="'+ix+'"]');
                $row.find('.s-rowGroup').addClass('groupThis');
                if(i>0){//隐藏checkbox
                    o.hideCheckbox && $row.find(':checkbox').hide();
                }else{
                  firstIx = ix;
                  $row.find('.s-rowGroup').addClass('groupThisFirst');
                }
                if(i == (rows.length-1)){
                  $row.find('.s-rowGroup').addClass('groupThisLast');
                }
            });
            !o.data&&$grid.datagrid('clearChecked');
            if(o.strArr&&o.strArr.length){
                $.each(o.strArr,function(j,v){
                    $grid.datagrid('mergeCells',{
                        index: firstIx,
                        field: v,
                        rowspan: rowLen
                    });
                });
              }
          }else{
              if(o.needMsg){layer.msg('请选择需要合并的多行！',{icon:0})};
          };
      },o.grid);
    },
    splitRowsCells : function (opt) {//拆分已合并的单元格
      var me = this;
      var o = $.extend({
          grid : '#gridBox',//grid
          groupNO : null,//是否需要重置groupNO，需要则填写对应字段名，null则不需要
          data : null,
          callback : null//返回事件
      },opt||{});

      var  $grid = $(o.grid);

      var rows = o.data || $grid.datagrid("getChecked");
      // window.console && console.log(rows);
      // var $gridWrap = $grid.parent('.datagrid-view');
      if (rows&&rows.length) {
          $.each(rows, function (i,v) {
              var ni = $grid.datagrid("getRowIndex",v);
              // var $tr = $gridWrap.find('.datagrid-row[datagrid-row-index="'+ni+'"]');
              var mergeLength = v.mergeLength;//获取合并了多少行
              if (mergeLength) {
                for (var i = 0; i < mergeLength; i++) {//根据当前行往下找行并刷新
                  // var $row = $gridWrap.find('.datagrid-row[datagrid-row-index="'+ni+'"]');
                  var temR = {
                    rowMerge : false,
                    mergeLength : 1
                  };
                  if(o.groupNO){temR[o.groupNO]='';}
                  $grid.datagrid('updateRow',{
                      index : ni,
                      row : temR
                  });
                  $grid.datagrid("refreshRow" , ni );
                  ni++;
                };
              };
          });
          $grid.datagrid('clearChecked');
          o.callback&&o.callback();
      }else{
          layer.msg('请选择拆分行！',{icon:0});
      };
    }
  }
  return edit;
});
