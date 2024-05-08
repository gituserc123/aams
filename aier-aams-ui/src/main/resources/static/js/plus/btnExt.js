$btnExt =  (function () {
    var btnSet = {};
    // dom ready
   // $(".ui-btn").each(function () {
   //     if(btnSet[this.id] instanceof BtnExt) {
   //         btnSet[this.id] = new BtnExt($(this));
   //     }
   //  });
    var getBtnSet = function (id,opt) {
        if(!(btnSet[id] instanceof BtnExt)) {
            btnSet[id] = new BtnExt($("#"+id));
        }
        return  btnSet[id]
    }


   function BtnExt (btn) {

        this.set = btnSet;
        this.el = btn;
        this.url = this.el.attr("url");
    }
    BtnExt.prototype.send = function (cb) {
        var reqUrl = this.url;
        if(!$.trim(reqUrl)) return cb("未配置url");
        $.ajax({
            url : reqUrl,
            type: "POST",
            success : function (res) {
                cb(res);
            },
            error : function (err) {
                cb(err);
            }
        });
        return this;
    }
    BtnExt.prototype.req = function (cb) {
        var self = this;
        self.el.unbind("click");
        self.el.on("click",function () {
            self.send(cb);
        });
        return self;
    }

    //
    // $(".ui-btn").each(function () {
    //     if(btnSet[this.id]) { console.log("按钮组件有重名的ID:" + this.id + "请检查"); }
    //     btnSet[this.id] = new BtnExt($(this));
    // });

    return {
        get : getBtnSet
    }

})();
