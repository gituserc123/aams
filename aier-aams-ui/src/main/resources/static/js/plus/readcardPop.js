//院内提醒 页面逻辑
// $('#target').html(require("text!目标按钮对应的页面.html"));
define(["readCard"], function($r) {
    // var  initStatus = false;
    var b = {};
    b.init = function (callback) {
        // if(initStatus) return;
        // initStatus = true;
        if($('#readcardPop').length==0){
            var temp = "<div id=\"readcardPop\" style=\"display: none;\">\n" +
              "    <p>请将卡片放在读卡器上~</p>\n" +
              "    <span class=\"btn btn-small btn-warning btn-medicalCard readCard-in-pop\">读卡</span>\n" +
              "</div>"
            $("body").append(temp);
        }
        // 绑定读卡
        var $btn = $(".readCard-in-pop");
        $r.readCardInit();//插入读卡ocx html
        $btn.unbind('click').bind("click",function () {
            $r.readMacNumber(function(cardInfo){
                //if(!cardNumber) return alert("卡号不存在");
                // alert("cardNumber="+cardNumber);
                //1为m1卡标志，2为二代证标志，3为社保卡标志，4为其他卡标志
                var cardTypeArr =['','m1卡','二代身份证','社保卡','其他卡']
                var cardType = cardInfo.type;
                var cardNumber = cardInfo.no;
                $.ajax({
                    url : base + "/ui/outp/patientCard/readVisitCard",
                    type: "POST",
                    data: { cardNumber : cardNumber },
                    success : function (res) {
                        // console.log(res);
                        var state = res.data.state;
                        if(state == '1'){
                            // var patId = res.data.list.id;
                            callback && callback(res.data.list);
                        }else{
                            $pop.alert('该卡未登记！');
                        }

                    },
                    error : function (err) {
                        $pop.alert('该卡未登记！');
                    }
                });
            });
            $(this).unbind('click');//解绑点击事件，避免重复点击
            //
        });


    };

    b.open = function (cb) {
        // if(!initStatus)
        b.init(cb);
        $pop.open({
            skin : 'showReadcardPop',
            // title : '读卡',
            title : null,
            area: ['360px', '140px'],
            fixed: true,
            content : $("#readcardPop")
        });
    };
    return b;
});
