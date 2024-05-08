var eyeIndex = {
      init : function () {
        var me = this;
//        window.console && console.log('index js init');
        // me.exScreen();//全屏
        me.exSide();//收缩侧边栏
        me.exSideInOther();//调用方法可以在子页面收缩侧边栏
        me.setIframeH();//设置iframeH
        me.sideSlide();//侧边栏点击展开
        me.sideNavE();//侧边导航点击链接事件
        me.repairPass();//修改密码
        me.loginOut();//退出登录
        me.noInWindow();//不包含在iframe中
        me.switchCompany();//切换公司医院
      },
      exScreen : function () {
        $('.s-exScreen').click(function () {
          if ($(this).hasClass('s-inScreen')) {
            $.fullscreen(false);
            $(this).removeClass('s-inScreen');
          }else {
            $.fullscreen(true);
            $(this).addClass('s-inScreen');
          }
        });
      },
    noInWindow : function () {
      if(window.top !== window.self){window.top.location = window.location;}
    },
      switchCompany : function () {
        var $comList = $('.ul-companyList');
        if ($comList.length) {
          $(document).on('click', function (e) {
              var $t = $(e.target);
              if ($t.hasClass('nowCompany')) {
                  $comList.show();
                  $t.addClass('nowCompany-over');
              } else {
                  $comList.hide();
                  $('.nowCompany').removeClass('nowCompany-over');
              };
          });
          $('.ul-companyList .s-c').click(function() {
            var rel = $(this).attr('rel');
            var url = $comList.attr('rel');
            $.post('index.html',{id:rel}).done(function () {
              window.location.reload();
            });
          });

          $('.allCompanyNav').mouseleave(function () {
              $('.nowCompany').removeClass('nowCompany-over');
              $comList.hide();
          });

          var $nav = $('.allCompanyNav');
          setTimeout(function () {$nav.addClass('allCompanyNav-over');}, 300);
          setTimeout(function () {$nav.removeClass('allCompanyNav-over');}, 600);
          setTimeout(function () {$nav.addClass('allCompanyNav-over');}, 900);
          setTimeout(function () {$nav.removeClass('allCompanyNav-over');}, 1200);
          setTimeout(function () {$nav.addClass('allCompanyNav-over');}, 1500);
          setTimeout(function () {$nav.removeClass('allCompanyNav-over');}, 1800);

        };
      },
      repairPass : function () {
//        window.console && console.log(111);
        var layerPass = null;
        window.$pop = {};
        $('.s-updataPass').click(function () {
          var url = $(this).attr('rel');
          layerPass = layer.open({
            type:2,
            content : url,
            title : '修改密码',
            area : ['400px','240px']
          });
          if (url.indexOf("/") != 0) {
              url = location.pathname.replace(/\/[^/]*$/, "/") + url;
          }
//          window.console && console.log(url);
          $pop[url] = layerPass;
        });
      },
      loginOut : function () {
     $('.a-loginOut').click(function () {
            var href = $(this).attr("href");
            layer.confirm('你确定退出系统吗？', {
                icon: 0, title:false,btnAlign: 'c'
                }, function(){
                  window.location.href=href;
            });
            return false;
        });
      },

      exSideInOther: function () {
        window.indexInSide =  function () {
            $(".mainCont").animate({left:'0px'});
            $(".sidebar").animate({marginLeft:'-188px'});
        }
        window.indexExSide=  function () {
            $(".mainCont").animate({left:'188px'});
            $(".sidebar").animate({marginLeft:'0px'});
            $('.li-sidenav').removeClass('li-sidenav-now').eq(0).addClass('li-sidenav-now');
            $('.s-sidenav').removeClass('s-sidenav-now');
            $('.s-subnav').removeClass('s-subnav-now');
        }
      },
      exSide : function () {
        $('.s-extendSide').click(function() {
          var _self = $(this);
          var showMenu=_self.hasClass('intendSide');
          if(showMenu){
            $(".mainCont").animate({left:'176px'});
            $(".sidebar").animate({marginLeft:'0px'});
            _self.removeClass('intendSide');
          }else{
            $(".mainCont").animate({left:'0px'});
            $(".sidebar").animate({marginLeft:'-176px'});
            _self.addClass('intendSide');
          }
        });
      },
      _setIframeH : function () {
          var iframeH = $('.mainCont').height();
          $('#mainIframe').height(iframeH);
      },
      setIframeH : function () {
        var me = this;
        me._setIframeH();
        $(window).resize(function() {
          me._setIframeH();
        });
      },
      sideSlide : function () {
        $('.li-sidenav').click(function() {
          var _self = $(this);
          if (!_self.hasClass('li-sidenav-now')) {
              $('.li-sidenav-now').removeClass('li-sidenav-now').find('.ul-subnav:first').slideUp('fast');
              _self.addClass('li-sidenav-now');
              _self.find('.ul-subnav:first').slideDown('fast');
            };
        });
      },
      sideNavE : function () {
        $('.s-subnav,.s-sidenav').click(function() {
          var url = $(this).attr('rel');
          if (url) {
            $('.s-subnav-now').removeClass('s-subnav-now');
            $(this).addClass('s-subnav-now');
            $('#mainIframe').attr('src',url);
          };
        });
      }
};

$(function () {
  eyeIndex.init();
});
