$.fn.hoverClass=function(b){var a=this;a.each(function(c){a.eq(c).mouseenter(function(){$(this).addClass(b)});a.eq(c).mouseleave(function(){$(this).removeClass(b)})});return a};

var eyeIndex = {
      init : function () {
        var me = this;
        $('.li-mainnav').hoverClass('li-mainnav-over');
        // me.exScreen();//全屏
        me.setIframeH();//设置iframeH
        me.mainnavClick();
        me.sideNavE('.s-subnav','s-subnav-now');//侧边导航点击链接事件
        me.sideNavE('.s-hnav','s-hnav-now');//侧边导航点击链接事件
        me.sideNavE('.s-feedback','s-feedback');//顶部问题反馈点击链接事件
        me.repairPass();//修改密码
        me.noInWindow();//不包含在iframe中
        me.loginOut();//退出登录
        me.switchCompany();//切换公司医院
        me.tabCloseEven();
        me.menuClick();
        me.rightmenu();
        me.searchKey();
        me.extendArea();
		me.mainResize();
        // me.runNowTime();//头部区域动态当前时间
      },
      onlyOpenTitle: "功能导航",//不允许关闭的标签的标题
      runNowTime : function(){
        function rzero(v) {
          return v<10?('0'+v):v;
        }

        function toDateVal(dateS){
          var dt = new Date(dateS);
          var y = dt.getFullYear();    //获取完整的年份(4位,1970-????)
          var mo = rzero(dt.getMonth()+1);       //获取当前月份(0-11,0代表1月)
          var d = rzero(dt.getDate());        //获取当前日(1-31)
          // var da = dt.getDay();         //获取当前星期X(0-6,0代表星期天)
          // var t = myDate.getTime();        //获取当前时间(从1970.1.1開始的毫秒数)
          var h = rzero(dt.getHours());       //获取当前小时数(0-23)
          var m = rzero(dt.getMinutes());     //获取当前分钟数(0-59)
          var s = rzero(dt.getSeconds());     //获取当前秒数(0-59)

          return y+'-'+mo+'-'+d+' '+h+':'+m+':'+s;
        }

          sysNowTime = sysNowTime.replace(/-/g,"/");
          var timestamp = Date.parse(new Date(sysNowTime));
          var localteimStr = Date.parse(new Date());
          var timeL = timestamp-localteimStr;

          setInterval(function () {
            var nowTimeStr = Date.parse(new Date())+timeL;
            var nowtimeV = toDateVal(nowTimeStr);
            // timestamp = timestamp*1+1000;
            $('#sysTime').text(nowtimeV);
          },1000);

      },
      
      exEditFav : function(){
        $('.s-quickEdit').click(function () {
          var _self =$(this);
          var $ul = $('.ul-menuNav');
          var active = _self.hasClass('active');
          if(active){
            _self.removeClass('active').attr('title','编辑快捷图标');
            $ul.removeClass('ul-active');
          }else{
            _self.addClass('active').attr('title','退出编辑');
            $ul.addClass('ul-active');
          }
        });
      },
      delFavItem : function(){//取消收藏
        $('.delMaskHandler').click(function () {
          var _self = $(this);
          var itemId = _self.attr('rel');
            $.ajax({
              url : baseUrl +'/ui/sys/sysModuleFavorite/delete',
              dataType : 'json',
              type : 'POST',
              data : {moduleId:itemId},
              success : function(rst){
                // window.console && console.log(rst);
                if(rst.code==='200'){
                  var $li = _self.parents('.li-hnav');
                  $li.remove();
                  layer.msg('从我的快捷中移除成功!',{icon:1,time:2000});
                }
              },
              error : function(XMLHttpRequest, textStatus, errorThrown){
                var rst = JSON.parse(XMLHttpRequest.responseText);
                var msg = '删除请求失败，请检查网络，仍有问题请联系管理员！';
                if(rst&&rst.msg){msg= rst.msg;}
                layer.msg(msg,{icon:7,time:3000});
              }
          });
        });
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
    extendArea : function(){
      $('.handlerAreaSide').click(function () {
        var _self = $(this);
         var ex = _self.hasClass('extend');
         if(ex){
           _self.removeClass('extend');
           $('.index-area').removeClass('areaIntend');
           $('.mainnav').removeClass('mainnavIntend');
         }else{
           _self.addClass('extend');
           $('.index-area').addClass('areaIntend');
           $('.mainnav').addClass('mainnavIntend');
         };
      });
    },
    noInWindow : function () {
      if(window.top !== window.self){window.top.location = window.location;}
    },
	mainResize : function(){
		$('.s-winResize').click(function () {
			var _self = $(this);
			if (_self.hasClass('inWinsize')) {
				_self.removeClass('inWinsize');
                $('#topHead').panel('expand');
                $('#mainCont').panel('restore');
				// $('.mainCont').animate({top:'46px'},function () {
				// 	$('.mainCont').css({zIndex:0});
				// });
			}else {
				_self.addClass('inWinsize');
                $('#topHead').panel('collapse');
                $('#mainCont').panel('maximize');
				// $('.mainCont').css({zIndex:8}).animate({top:0});
			}

		});
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
        var me = this;
        $('.s-repairPass').click(function () {
          var url = $(this).attr('rel');
          me.popRepairPass(url);
        });
      },
      popRepairPass : function(url,mustRepair){
          var layerOpt = {
            type:2,
            content : url,
            title : '修改密码',
            area : ['400px','220px']
          };
          if(mustRepair){
            layerOpt.title='修改初始密码';
            layerOpt.skin='mustRepairPop';
          }
          layer.open(layerOpt);
        },
      loginOut : function () {
		    $('.a-loginOut').click(function () {
	          var href = $(this).attr("rel");
	          layer.confirm('你确定退出系统吗？', {
	              icon: 0, title:false,btnAlign: 'c'
	              }, function(){
	                window.location.href=href;
	          });
	          return false;
	      });
      },
      searchKey : function () {
        var me = this;
        var nowI = -1;
        var $ul = $('.ul-searchList');
        var $li = $('.li-searchlist');
        var offset = $(".txt-search").offset();
        $ul.css({
              top :offset.top + 25,
              left: offset.left
         });


        $(document).on('click','.li-searchlist',function () {//点击列表处理
            //
            var url  = $(this).find("span").attr('url');
            if(url == undefined ) {
              return false;
            }
            $('.txt-search').val($(this).text());
            me.addTab($(this).text(),url);
            $ul.hide();
            nowI = -1;
            return false;
        });

        $(document).on('click',function(e){
            var $target = $(e.target);
            // window.console && console.log($target);
            if (!($target.hasClass('li-searchlist')||$target.hasClass('s-searchkey'))) {
                $ul.hide();
            };
          });
        function  buildSearchList(val) {
          $ul.html("");
          var finder = false;
          for(var i=0;i< searchMenus.length; i++ ) {
            if( searchMenus[i].moduleName.indexOf(val)  >= 0  ) {
                finder = true;
                var $li = $('<li class="li-searchlist"><span class="s-searchkey" url="' +  searchMenus[i].url+ '">' + searchMenus[i].moduleName+ '</span></li>');
                $ul.append($li);
            }
          }
          if(!finder) {
              var $li = $('<li class="li-searchlist"><span class="s-searchkey" >没有查到符合的菜单...</span></li>');
              $ul.append($li);
          }

        }

        $('.txt-search').keyup(function (e) {
          var _self = $(this);
          var val = $.trim(_self.val());
          var keycode = e.keyCode;
          // window.console && console.log(keycode);
          if (val!=='') {

              buildSearchList(val);
              $ul.show();
            var  lock = (keycode>36&&keycode<41||keycode==13)?true:false;//是否在操作光标键
            if (lock) {
                var keyLen = $li.length;
                if (keycode==38) {//键盘上键事件
                  nowI = (nowI>0)?(nowI-1):0;
                  liEvent(_self);
                }
                if (keycode==40) {//键盘下键事件
                  nowI = (nowI<(keyLen-1))?(nowI+1):(keyLen-1);
                  liEvent(_self);
                }
                if (keycode==13) {//键盘回车键事件
                  if (nowI!=-1) {
                    var v = $li.eq(nowI).text();
                    // window.console && console.log(nowI);
                    _self.val(v);
                    me.addTab(v,'booking.html');
                    $ul.hide();
                    nowI = -1;
                  }
                }
            };

          };
        });

      function liEvent(_self) {//键盘上下处理
        $li.removeClass('li-searchlist-now');
        var nowLi = $li.eq(nowI);
        _self.val(nowLi.text());
        nowLi.addClass('li-searchlist-now');
      }



      },
      mainnavClick : function () {
        var me = this;
        var delayE = null;
        $('.s-mainnav').mouseenter(function() {
          var _self = $(this);
          delayE = setTimeout(function(){
            if (!_self.hasClass('s-mainnav-now')) {
              var rel = _self.attr('rel');
              $('.s-mainnav-now').removeClass('s-mainnav-now');
              _self.addClass('s-mainnav-now');
              // if (rel=='#subnav-my') {
              //     $('.subnav').show();
              //   }else{
                  $('#submain-my,.ul-submainnav').hide();
                  $('.subnav').hide();
                  $(rel).show();
                // };
            };
          },300);
        }).mouseleave(function(){
          delayE&&clearTimeout(delayE);
        });

        $('.s-submainnav').click(function() {
          if (!$(this).hasClass('s-submainnav-now')) {
            var rel = $(this).attr('rel');
            // window.console && console.log(rel);
            if (rel) {
              $('.s-submainnav-now').removeClass('s-submainnav-now');
              $(this).addClass('s-submainnav-now');
              $('.subnav').hide();
              $(rel).show();
            };
          };
        });


      },

    menuTarget : null,
      rightmenu : function () {
        var me = this;
        $(".s-hnav").bind('contextmenu',function(e){
          $('#mm2').menu('show', {
            left: e.pageX,
            top: e.pageY
          });
          me.menuTarget = $(this).parent('li');
          // window.console && console.log(me.menuTarget);
          e.preventDefault();
          // window.console && console.log(this);
          return false;
        });
      },
      menuClick : function () {
        var me = this;
          $('#mm2').menu({
              onClick: function (item) {
                var $li = me.menuTarget;
                // window.console && console.log(me.menuTarget);
                // window.console && console.log(item.id);
                switch (item.id) {
                  case  'up':
                    var $prev = $li.prev('li');
                    // window.console && console.log($prev);
                    if ($prev) {
                      $li.after($prev);
                    };
                    break;
                  case  'down':
                    var $next = $li.next('li');
                    // window.console && console.log($next);
                    if ($next) {
                      $li.before($next);
                    };
                    break;
                  case  'first':
                    var $ul = $li.parent();
                    $ul.prepend($li);
                    break;
                  case  'last':
                    var $ul = $li.parent();
                    $ul.append($li);
                    break;
                  case  'del':
                    $li.remove();
                    break;

                }
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
      sideNavE : function ($t,nowCls) {
        var me = this;
        $($t).click(function() {
          var _self = $(this);
          var url = _self.attr('rel');
          if (url&&url!='/#') {
            $('.'+nowCls).removeClass(nowCls);
            _self.addClass(nowCls);

            var tabTitle = _self.attr('title');
            var tabTitle = tabTitle||_self.find('.em-nav').text()||_self.text();
            me.addTab(tabTitle,url);
            // $('#mainIframe').attr('src',url);
          }else{
            layer.msg('没有为菜单配置url地址!',{icon:7,time:3000});
          };
        });
      },
      tabIfExit : function(title,url){
        var tabI = $('#tabs').tabs('getTab',tabTitle)
        var ta = $('#tabs').tabs('exists',tabTitle);
        if(ta){
          var tab
        }
      },
      urlPKey :'pgv',
      addTab : function(tabTitle,url,unselected){
        var me = this;
        var randomNum = Math.floor(Math.random()*10000000000);
        var linkStr = url.indexOf('?')>-1?'&':'?';
        var newUrl = url+linkStr+me.urlPKey+'='+randomNum;
        if(!$('#tabs').tabs('exists',tabTitle)){
          var $iframe = me._createFrame(newUrl);
          $('#tabs').tabs('add',{
            title:tabTitle,
            content:$iframe,
            selected : !unselected,
            closable:true
          });
        }else if(unselected){
          var $tab = $('#tabs').tabs('getTab',tabTitle);
          $tab.find('.iframe-page').attr('src',newUrl);
        }else{
          $('#tabs').tabs('select',tabTitle);
          $('#refresh').click();
        }
        me.tabClose();
      },
      closeTab : function(tabTitle){
        $('#tabs').tabs('close',tabTitle);
      },
      tabClose : function(){
        /*双击关闭TAB选项卡*/
        $(".tabs-inner").dblclick(function(){
          var subtitle = $(this).children(".tabs-closable").text();
          $('#tabs').tabs('close',subtitle);
        })
        /*为选项卡绑定右键*/
        $(".tabs-inner").bind('contextmenu',function(e){
          var ix = $(".tabs-inner").index(this);
          if(ix>0){
            $('#mm').menu('show', {
              left: e.pageX,
              top: e.pageY
            });
          }

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
        var $iframe = '<iframe class="iframe-page" scrolling="auto" frameborder="0" src="'+url+'" style="width:100%;height:100%;"></iframe>';
        return $iframe;
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
            var ifOpt = currentTab.panel('options');
            if(ifOpt.title==='单据打印')return;//如果是打印页面，不允许右键菜单刷新
            var iframe = $(ifOpt.content);
            var src = iframe.attr('src');
            var newSrc = src;
            var splitStr = me.urlPKey+'=';
            var randomNum = Math.floor(Math.random()*10000000000);
            if(newSrc.indexOf(splitStr)>-1){
              newSrc = src.split(splitStr)[0]+splitStr+randomNum;
            }
            $('#tabs').tabs('update', {
              tab: currentTab,
              options: {
                content: me._createFrame(newSrc)
              }
            })
            break;
          case "close":
            var currtab_title = currentTab.panel('options').title;
              if (currtab_title != me.onlyOpenTitle){
                  $('#tabs').tabs('close', currtab_title);
              }
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

var eyeStore = {//数据中心
  comData : {}//暂存子页面间通讯数据
}

$(function () {
  eyeIndex.init();
});
