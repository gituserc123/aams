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
        me.noInWindow();//不包含在iframe中
        me.loginOut();//退出登录
        me.switchCompany();//切换公司医院
        me.tabCloseEven();
        $('.s-sidenav:first').trigger('click');
      },
      onlyOpenTitle: "系统首页",//不允许关闭的标签的标题
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
          var iframeH = $('.mainCont').height()-40;
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
        var me = this;
        $('.s-subnav,.s-sidenav').click(function() {
          var _self = $(this);
          var url = _self.attr('rel');
          if (url) {
            $('.s-subnav-now').removeClass('s-subnav-now');
            _self.addClass('s-subnav-now');

            var tabTitle = _self.attr('title');
            var tabTitle = tabTitle||_self.text();
            me.addTab(tabTitle,url);
            // $('#mainIframe').attr('src',url);
          };
        });
      },
      addTab : function(tabTitle,url){
        var me = this;
//        window.console&&console.log(url);
        if(!$('#tabs').tabs('exists',tabTitle)){
          $('#tabs').tabs('add',{
            title:tabTitle,
            content:me._createFrame(url),
            closable:true
          });
        }else{
          $('#tabs').tabs('select',tabTitle);
          $('#refresh').click();
        }
        me.tabClose();
      },
      tabClose : function(){
        /*双击关闭TAB选项卡*/
        $(".tabs-inner").dblclick(function(){
          var subtitle = $(this).children(".tabs-closable").text();
          $('#tabs').tabs('close',subtitle);
        })
        /*为选项卡绑定右键*/
        $(".tabs-inner").bind('contextmenu',function(e){
          $('#mm').menu('show', {
            left: e.pageX,
            top: e.pageY
          });

          var subtitle =$(this).children(".tabs-closable").text();

          $('#mm').data("currtab",subtitle);
          $('#tabs').tabs('select',subtitle);
          return false;
        });
      },
      tabCloseEven : function() {//绑定右键菜单事件
        var me = this;
        $('#mm').menu({
          onClick: function (item) {
            me._closeTab(item.id);
          }
        });
        return false;
      },
      _createFrame : function(url){
        var iframe = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        return iframe;
      },
      _closeTab : function(action){
        var me = this;
        var alltabs = $('#tabs').tabs('tabs');
        var currentTab =$('#tabs').tabs('getSelected');
        var allTabtitle = [];
        $.each(alltabs,function(i,n){
          allTabtitle.push($(n).panel('options').title);
        })

        switch (action) {
          case "refresh":
            var iframe = $(currentTab.panel('options').content);
            var src = iframe.attr('src');
            $('#tabs').tabs('update', {
              tab: currentTab,
              options: {
                content: me._createFrame(src)
              }
            })
            break;
          case "close":
            var currtab_title = currentTab.panel('options').title;
            $('#tabs').tabs('close', currtab_title);
            break;
          case "closeall":
            $.each(allTabtitle, function (i, n) {
              if (n != me.onlyOpenTitle){
                $('#tabs').tabs('close', n);
              }
            });
            break;
          case "closeother":
            var currtab_title = currentTab.panel('options').title;
            $.each(allTabtitle, function (i, n) {
              if (n != currtab_title && n != me.onlyOpenTitle){
                $('#tabs').tabs('close', n);
              }
            });
            break;
          case "closeright":
            var tabIndex = $('#tabs').tabs('getTabIndex', currentTab);

            if (tabIndex == alltabs.length - 1){
//              window.console&&console.log('亲，后边没有啦 ^@^!!');
              return false;
            }
            $.each(allTabtitle, function (i, n) {
              if (i > tabIndex) {
                if (n != me.onlyOpenTitle){
                  $('#tabs').tabs('close', n);
                }
              }
            });

            break;
          case "closeleft":
            var tabIndex = $('#tabs').tabs('getTabIndex', currentTab);
            if (tabIndex == 1) {
//              window.console&&console.log('亲，前边那个上头有人，咱惹不起哦。 ^@^!!');
              return false;
            }
            $.each(allTabtitle, function (i, n) {
              if (i < tabIndex) {
                if (n != me.onlyOpenTitle){
                  $('#tabs').tabs('close', n);
                }
              }
            });

            break;
          case "exit":
            $('#closeMenu').menu('hide');
            break;
        }
      }
};

$(function () {
  eyeIndex.init();
});
