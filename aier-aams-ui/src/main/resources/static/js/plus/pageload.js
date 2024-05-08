define(['template'],function (template) {
    var pageLoad  ={
        load : function (pageUrl,opt){
            var me = this;
            var o = $.extend({
                $el : 'body',//加载目标dom对象，默认加载到body中
                inType : 'html',//怎么加载到父元素中
                title : null,//更改页面title，如果有赋值就更新页面title
                data : null,//用于渲染页面的数据，使用artTemplete渲染
                loadPageBack : function (){},//加载后返回函数
                loadedBack : function (){}//执行完成后返回函数
            },opt||{});
            var pageId = 'page-'+ Number(new Date());
            var $page = $("<div id='"+pageId+"'>");
            $(o.$el)[o.inType]($page);

            $('#'+pageId).load(pageUrl,function (){
                o.opt = {
                    pageId : pageId,
                    $page : $page,
                    $str : function (c){
                        return '#'+pageId+' '+c;
                    },
                    $$ : function (c){
                        return $page.find(c);
                    }
                }
                var _self = $(this);
                if(o.title){$('title').text(o.title);}//更新页面title
                o.$cont = _self.find('div:first');

                if(o.data){//如果有数据
                    var $wrap = $('<script id="stemplate" type="text/html"></script>');
                    $('body').append($wrap.append(o.$cont));
                    _self.append(template('stemplate',o.data));
                    // me.loadAfter(o);
                }
                //window.console&&console.log(o);
                me.loadAfter(o);
            });
        },
        loadAfter : function (o){
            var pageId = o.opt.pageId;
            var loadOpt = null;
            if(o.loadPageBack){
                loadOpt = o.loadPageBack(o.opt);//加载后返回函数
            };

            var opt = $.extend(o.opt,{loadOpt : loadOpt});//加入load处理后参数

            $page.contInit('#'+pageId);

            var initJs = o.$cont.attr('data-js');
            if(initJs){
                initJs = initJs.split('||');
                $.each(initJs,function(i,v){
                    var vArr = v.split(":");
                    var modId = vArr[0] , funcId = vArr[1]||'init';
                    window.console && console.log('page执行 app/'+modId+'.js中的'+funcId+'方法');//打开页面时，注意看控制台提示执行的是哪个页面的哪个方法
                    // require(['/js/app/'+modId+'.js'],function(mod){
                    require(['app/'+modId],function(mod){
                        // console.log(modId);
                        if(mod){
                            var init=mod[funcId];
                            if(init&&$.isFunction(init)){
                                mod[funcId](opt);
                            }else{
                                window.console && console.log("请在"+modId+".js文件中定义"+funcId+"方法");//不存在提示
                            }
                        }
                    });
                });
            }
            //window.console&&console.log(opt);
            o.loadedBack&&o.loadedBack(opt);
        }
    }
    return pageLoad;
  });
