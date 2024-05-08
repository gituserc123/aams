$btnSearch =  (function () {
    var btnSet = {};
    var initFn = function (id,opt) {
        btnSet[id] = new BtnSearch($("#"+id),opt);
        return btnSet[id];
    };
    var getBtnSet = function (id,opt) {
        if(!(btnSet[id] instanceof BtnSearch)) {
            btnSet[id] = new BtnSearch($("#"+id),opt);
        }
        return  btnSet[id]
    }

    // dom ready
   // $(".search-btn").each(function () {
   //      if(btnSet[this.id]) { console.log("按钮组件有重名的ID组件"); }
   //      btnSet[this.id] = new BtnSearch($(this));
   //  });

     function BtnSearch (dom,opt) {
        this.set = btnSet;
        this.el = dom;
        this.url = this.el.attr("url");
        this.ipt = this.el.find(".txt");
        this.btn = this.el.find(".btn");
        this.isOpen = false;

        this.selectRowIdx = -1;

        this.onGridSelect = function () {};
        this.onLoadSuccess= function () {};
        this.onPopHide = function () {};
        this.$grid = null;
        var self = this;

        var init = function () {
            self.$pop= $("<div class='searchPop' />");
            self.$gridbox = $("<div class='searchgrid' />");
            var $hideBtn = $("<button type=\"button\"style='display: none;'>提交</button>");
            self.$pop.append(self.$gridbox);
            self.el.append($hideBtn);
            $("body").append(self.$pop);
	    var _opt = { 
	    	fit: true,
                    pagination : true,
                    pageSize: 10,
                    // method : "get",
                    columns:[[
                        {title:'id',field:'id',width:80,hidden:true},
                        {title:'modifyDate',field:'modifyDate',width:80,hidden:true},
                        {title:'accountBalance',field:'accountBalance',width:80,hidden:true},
                        {title:'sex',field:'sex',width:60,hidden:true},
                        {title:'姓名',field:'name',width:80},
                        {title:'性别',field:'sexName',width:40,align:'center'},
                        {title:'出生日期',field:'birthday',width:80 , formatter: function(value,row,index){
                            return value.substring(0,10);
                        }},
                        {title:'年龄',field:'ageTxt',width:60,align:'center'},
                        {title:'联系电话',field:'tel1',width:100},
                        {title:'身份证号',field:'idNumber',width:150},
                        {title:'建档ID',field:'archiveHospId',width:80,hidden:true},
                        {title:'建档机构',field:'archiveHospName',width:180,align:'left'},
                        {title:'联系地址',field:'contactsAddr',width:180,align:'left'}
                    ]],
                    onClickRow : function (idx,row) {
                        self.hide();
                        self.onGridSelect(idx,row);
                        // window.console && console.log(row);
                        // hideInfoSearchBox();
                        // $('#tabsA').tabs('select',0);
                        // $('.form-userInfo').form('load',row);
                        // //cardAddValidate();
                        //
                        // patientId = row.id;
                    },
                    // onBeforeLoad : function(param){
                    //     if(!param.searchValue){
                    //         return false;
                    //     }
                    // },
                    onLoadSuccess : function (d) {
                        self.onLoadSuccess(d);
                    },
                    // url: self.url, //'${base}/ui/outp/patientInfo/search'
                    height: 'auto'
                    // ,offset :-55
                }
	        if(opt) {
                $.extend(_opt,opt);
            }
            setTimeout(function () {
                self.$grid = self.$gridbox.datagrid(_opt);
            },400);
        };
        init();

        // event
        //  this.ipt.keyup(function(event){
        //      if(event.keyCode ==13){
        //          self.open();
        //      }
        //  });

         $(document).keyup(function (e) {
             // 回车打开 下来弹出层
             //  todo 需要增加 判断是否已经打开 如果打开则需要选中行

             if (e.keyCode == 13) {
                 // console.log(self.ipt.is(':focus'));
                 if(self.isOpen && self.selectRowIdx > -1  ) {
                     var row = self.$gridbox.datagrid("getSelected");
                     self.hide();
                     self.onGridSelect(self.selectRowIdx,row);
                 } else {
                     if(document.activeElement== self.ipt[0]) {
                         // console.log(self.ipt.is(':focus'));
                         // 新增修改需要验证 activeElement 浏览器支持性
                         self.open();
                         self.ipt.blur();
                         return false;
                     }
                 }

             } else if ( e.keyCode== 38 || e.keyCode== 40 ){

                 // 上下键盘控制选中行
                 // 上38  下40

                 self.keyUpSelectRow(e.keyCode);
             }

         });
         this.btn.on("click",function () {
            self.open();
        });
        //
        $(document).on('click',function (e) {

            if( e.target !== self.btn[0]   && self.$pop.is(":visible") && !$.contains(self.$pop[0], e.target) && !$(e.target).hasClass("l-btn-icon") ) {
                if(self.isOpen) {
                    self.onPopHide();
                }
                self.hide();
                // 提供给外部

            }
        });


    };
    // BtnSearch.prototype.
    BtnSearch.prototype.open = function () {
        var result = this.search();
        if(!result) return;
        var o =  this.el.offset();
        o.top += this.ipt.height();
        this.$pop.css({ left : o.left , top : o.top });
        this.isOpen = true;
        return this;
    }
    BtnSearch.prototype.hide = function () {
        this.$pop.css("left","-999999px");
        this.isOpen = false;
        this.selectRowIdx = -1;
        this.$gridbox.datagrid('loadData',{ total: 0, rows: [] });
        return this;
    }




    BtnSearch.prototype.search = function () {
        var self = this;
        var val = $.trim(self.ipt.val());
        if( val == "" )  return false;
        // this.$gridbox.datagrid({
        //     url: self.url,
        //     queryParams:{
        //         searchValue :  this.ipt.val(),
        //         page :1
        //     },
        //     onLoadSuccess : function () {
        //      //   self.$gridbox.datagrid("selectRow", self.selectRowIdx);
        //     }
        // });
        // 注释以上grid加载代码，修改如下，修正 bug 4589 搜索患者，搜索出很多结果时，翻页后，再次搜索，不是从第一页开始显示  的问题 by longx 2020-03-30
        this.$gridbox.datagrid("options").url = self.url;
        this.$gridbox.datagrid('load',{ searchValue :  this.ipt.val() });
        return this;
    }
    BtnSearch.prototype.onSelect = function (cb) {
        this.onGridSelect = cb;
        return this;
    }
    BtnSearch.prototype.onLoad = function (cb) {
        this.onLoadSuccess = cb;
        return this;
    }
    BtnSearch.prototype.onHide = function (cb) {
        this.onPopHide = cb;
        return this;
    }

    BtnSearch.prototype.keyUpSelectRow = function (keyCode) {
        if(!this.isOpen) return;
        this.$gridbox.datagrid("clearSelections");
        var rows = this.$gridbox.datagrid('getRows');
        var maxLen = rows.length - 1;

            if(keyCode == 38 && this.selectRowIdx > 0 ) {
                this.selectRowIdx--;
            } else   if(keyCode == 40 &&  this.selectRowIdx < maxLen )  {
                this.selectRowIdx++;
            }


        this.$gridbox.datagrid("selectRow",this.selectRowIdx);
    }







    return {
        init : function (id,opt) {
            return initFn(id,opt);
        },
        get : function (id,opt) {
            return getBtnSet(id,opt);
        }
    }

})();
