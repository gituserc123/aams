
$btnSearchInp =  (function () {

    var btnSet = {};
    var initFn = function (id,opt) {
        btnSet[id] = new BtnSearch($("#"+id),opt);
        return btnSet[id];
    };
    // $(function () {
    //     // dom ready
    //    $(".search-btn").each(function () {
    //        if(btnSet[this.id]) { throw new Error("按钮组件有重名的ID:" + this.id + "请检查"); }
    //        btnSet[this.id] = new BtnSearch($(this));
    //     });
    // });




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
        this.$grid = null;
        var self = this;
        var init = function () {
            self.$pop= $("<div class='searchPop' />");
            self.$gridbox = $("<div class='searchgrid' />");
            self.$pop.append(self.$gridbox);
            $("body").append(self.$pop);
            var _opt = {
                fit: true,
                pagination : false,
                //pageSize: 10,
                // method : "get",
                columns:[[
                    {title:'id',field:'id',width:50,hidden:true},
                    {title:'住院号',field:'inpNumber',width:100},
                    {title:'患者姓名',field:'patientName',width:100},
                    {title:'patientId',field:'patientId',width:80,hidden:true},
                    {title:'入院科室ID',field:'inpDeptId',width:50,hidden:true},
                    {title:'入院科室',field:'inpDeptName',width:150},
                    {title:'当前科室ID',field:'currentDeptId',width:50,hidden:true},
                    {title:'当前科室',field:'currentDeptName',width:150}

                ]],
                onClickRow : function (idx,row) {
                    self.hide();
                    self.onGridSelect(idx,row);

                },
                // onBeforeLoad : function(param){
                //     if(!param.searchValue){
                //         return false;
                //     }
                // },
                onLoadSuccess : function (d) {
                    console.log(d);
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
            },500);
        };
        init();


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
                    // if(self.ipt.is(':focus')) {
                         // console.log(self.ipt.is(':focus'));
                         self.open();
                         self.ipt.blur();
                         return false;
                   //  }

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

            if( e.target !== self.btn[0]   && self.$pop.is(":visible") && !$.contains(self.$pop[0], e.target) ) {
                self.hide();
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
        return this;
    }

    BtnSearch.prototype.search = function () {
        var self = this;
        var val = $.trim(self.ipt.val());
        if( val == "" )  return false;
        this.$gridbox.datagrid({
            url: self.url,
            queryParams:{
                searchValue :  this.ipt.val()
            },
            onLoadSuccess : function () {
             //   self.$gridbox.datagrid("selectRow", self.selectRowIdx);
            }
        });

        // this.$gridbox.datagrid('load',{ searchValue :  this.ipt.val() });
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
        get : function (id) {
            return btnSet[id];
        }
    }

})();
