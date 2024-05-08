define(['swfobject','web_socket'],function () {
    var config = {
        httpUrl : "http://localhost:9528"
    }

    // 浏览器端 OCX 控件的封装
    var OCX = function () {
        this.cacheOcx = {};
        this.csObj  =null;
        this.ocxName = null;
        var self =this;
        var convertName = function (classId) {
            var ocxname;
            if(!classId || typeof classId !== "string") {
                throw new Error("请传入OCX的classID");
            }
            ocxname = "o-" + classId.split(":")[1];
            return ocxname;
        };
        var _execute =  function (ocxName,ocxInput) {
            var ret = self.cacheOcx[ocxName].yb_call(ocxInput);
            return ret;
        };
        this.createOcx = function (classId) {
            var ocxName =  convertName(classId);
            if(this.cacheOcx.hasOwnProperty(ocxName)){
                return this.cacheOcx[ocxName]; // console.log("已存在Ocx对象" + classId );
            }
            var csObj = document.createElement("object");
            csObj.setAttribute("id", ocxName );
            csObj.setAttribute("height", 0);
            csObj.setAttribute("width", 0);
            csObj.setAttribute("classid", classId);
            document.body.appendChild(csObj);

            csObj.execute = function (ocxInput) {
                return _execute(ocxName,ocxInput);
            };
            return this.cacheOcx[ocxName] = csObj;
        };
        this.removeOcx  = function (classId) {
            var ocxName =  convertName(classId);
            var ocx = document.getElementById(ocxName);
            document.body.removeChild(ocx);
            window[ocxName] = null;
            var closureFn = this.cacheOcx[ocxName].execute();
            closureFn = null;
            delete this.cacheOcx[ocxName];
        };
        this.execute = function  (classId,ocxInput) {
            var ocxName = convertName(classId);
            return _execute(ocxName,ocxInput);
        };
    }

    //外部流程控制类
    var ocx = new OCX();
    // peroutput
    // WebSocket 请求 生命周期类
    var WsAction = function (cb,input,preoutput,ws,timeoutSeconds) {
        console.log(JSON.stringify({errCode : "js", errMsg: "创建WsAction 对象" ,insureCode: "x", insureHospCode: "x", func: "x",serialNo: "x" }));
        var self = this;
        this.input = input;
        this.serialNo = preoutput.serialNo;
        this.trueInsureCode = preoutput.trueInsureCode;
        this.doneParam = preoutput;
        this.timeoutSeconds = timeoutSeconds;
        this.status = "start"; // start  wait done
        this.timer = null;

        this.callback = cb;
        // 这里不在将input 传入ocx 而是传入整个 peroutput 对象
        this.send = function () {
            ws.send(JSON.stringify({ input : self.input , serialNo: self.serialNo, insureCode: self.trueInsureCode   }));
        }
        this.done = function () {
            console.log(JSON.stringify({errCode : "js", errMsg: "WsAction 对象 done 执行完成",insureCode: "x", insureHospCode: "x", func: "x",serialNo: "x" }));
            if(typeof this.timer == "number") {
                clearTimeout(this.timer);
            }
            self.callback(self.doneParam);
        }
    }
    // http 请求类
    var HttpAction = function (cb,input,preoutput,timeoutSeconds) {
        console.log("创建HttpAction 对象");
        var self = this;
        this.input = input;
        this.serialNo = preoutput.serialNo;
        this.trueInsureCode = preoutput.trueInsureCode;
        this.doneParam = preoutput;
        this.timeoutSeconds = timeoutSeconds || 60;
        this.status = "start"; // start  wait done
        this.timer = null;


        this.callback = cb;
        // 这里不在将input 传入ocx 而是传入整个 peroutput 对象

        this.promise = null;
        this.send = function () {
            //  ws.send(JSON.stringify({ input : self.input , serialNo: self.serialNo, insureCode: self.trueInsureCode   }));
            var self = this;
            this.timer =  setTimeout(function (){
                // 移除 wsaction 同时弹出 终止确认弹窗
                $pop.confirm('请求已超时是否继续等待',function(index){
                    layer.closeAll();
                    OcxControl.openMask("正在调用医保接口",6000);
                },function () {
                    console.log("超时移除遮罩并不再接受后续的消息");
                    OcxControl.closeMask();
                });
            },  self.timeoutSeconds * 1000);

            jQuery.support.cors = true;
            this.promise  = $ajax.post(config.httpUrl,JSON.stringify({ input : self.input , serialNo: self.serialNo, insureCode: self.trueInsureCode  }),false,{ jsonData: false}).done(function (message) {
                //  this.promise  = $ajax.post(config.httpUrl,JSON.stringify(self.input),false,{ jsonData: false}).done(function (message) {
                console.log("HttpAction收到消息",message);
                if(typeof self.timer == "number") {
                    clearTimeout(self.timer);
                }
                var serialNo = message.serialNo;
                var ocxOut = message.output ? JSON.parse(message.output) : undefined;
                var msg =  message.msg;
                // var msg =  message.errorMsg;
                self.doneParam.input = ocxOut;
                self.doneParam.errorMsg = msg;
                // if(message.code == "200") {
                //     ocxOutObj = JSON.parse(ocxOut);
                //     if( ocxOutObj.errCode == "-1" || ocxOutObj.errCode == "-2" ) {
                //         self.doneParam.errorMsg = ocxOutObj.errMsg;
                //     }
                // }
                self.callback(self.doneParam);
            }).error(function (err) {
                OcxControl.closeMask();
                self.doneParam.errorMsg = err;
                self.callback(self.doneParam);
                console.log("调用 应用的http 接口 ，请求错误",err);
                if(err.responseJSON) {
                    return;
                }
                $pop.alert('<p class="red">AierMain程序错误！</p>' + "请检查AierMain是否正确启动。");
            });


        }
        this.done = function () {
            // httpaction 中无需
            // console.log(JSON.stringify({errCode : "js", errMsg: "WsAction 对象 done 执行完成",insureCode: "x", insureHospCode: "x", func: "x",serialNo: "x" }));
            // if(typeof this.timer == "number") {
            //     clearTimeout(this.timer);
            // }
            this.callback(self.doneParam);
        }
    }


    // WebSocket生命周期类 队列控制类
    var ActionControl = function (pool) {
        this.list = pool;
    }
    // 发送消息到爱尔云前置应用
    ActionControl.prototype.onSend = function (action) {

        if(action instanceof WsAction) {
            // 执行action send 方法
            action.send();
            this.list.push(action);
            console.log(JSON.stringify({errCode : "js", errMsg: "ActionControl 对象 onsend, 后面是 action对象和 队列对象  ",insureCode: "x", insureHospCode: "x", func: "x",serialNo: "x" }),action,this.list);
            var self = this;
            // ws请求被发出后开始计时,超时则弹出提示窗口。
            // 终止则销毁对应的生命周期类，
            action.timer = setTimeout(function (){
                // 移除 action 同时弹出 终止确认弹窗
                if(action.status !== "done") {
                    $pop.confirm('请求已超时是否继续等待',function(index){
                        console.log("超时继续等待");
                        layer.closeAll();
                        OcxControl.openMask("正在调用医保接口",6000);

                    },function () {
                        console.log("超时移除遮罩并不再接受后续的消息");
                        self.remove(action);
                        OcxControl.closeMask();
                    });
                    //  top.layer.confirm( {icon: 3, title:'提示'}, function(index){
                    //     top.layer.close(index);
                    //     OcxControl.openMask("正在调用医保接口");
                    //  },function () {
                    //      self.remove(action);
                    //      OcxControl.closeMask();
                    // });
                }
            },action.timeoutSeconds * 1000);
        } else {
            action.send();

        }

    }
    // 从爱尔云前置应用收到消息
    ActionControl.prototype.onMsg = function (message) {
        var serialNo = message.serialNo;
        var ocxOut = message.output ? JSON.parse(message.output) : undefined;
        var msg =  message.msg;
        var finder = false;
        for(var i = 0; i< this.list.length; i++ ) {
            if(this.list[i].serialNo == serialNo) {
                var wsaction = this.list.splice(i,1)[0];
                wsaction.doneParam.input = ocxOut;
                wsaction.doneParam.errorMsg = msg;
                wsaction.done();
                finder = true;
                break;
            }
        }
        if(finder == false ) {
            // 消息收到，但是动作已经被用户终止不再弹出提示和操作 暂无动作。
        }
    }
    ActionControl.prototype.remove = function (wsaction) {
        var serialNo = wsaction.serialNo;
        for(var i = 0; i< this.list.length; i++ ) {
            if (this.list[i].serialNo == serialNo) {
                var wsaction = this.list.splice(i, 1)[0];
            }
        }
    }




    // 实现 关闭后只重连一次 需要测试！
    // 实现 针对非 func服务调用   wscall
    // 实现 增加终止 移除遮罩 和 移除WSaction 类并不提示后续消息
    // 暂缓 改造 ajaxobj.closeMask 调用在 def.resolve 时被触发

    // 全局 WS 连接
    var WS = function () {
        try{
            this.type = "wsInstance";
            //  调用队列的实现 每次完整的ws调用生命周期

            var self = this;
            //todo ws超时没有返回需要处理
            this.init = function () {
                var top_ws_ctl;
                try {
                    top_ws_ctl = top.TOP_SOCKET_CTL;

                } catch (e) {
                    // 浏览器不支持
                    console.log("浏览器错误",e );
                }
                // 这里做两种状态 ,portals中有 ws对象的调用 portals中有的ws
                // 没有ws 对象则使用自身创建 ws 连接
                if(!top_ws_ctl) {
                    console.log("缺少  top_ws_ctl ");
                    // self.ws = top.reconnct();
                    // top_ws_ctl = top.TOP_SOCKET_CTL;
                    //
                    this.ws = new WebSocket("ws://127.0.0.1:9527");
                    this.acCtl = new ActionControl([]);
                } else {
                    this.ws = top_ws_ctl.ws;
                    // 加载检查连接状态
                    if (!this.chkState()) {
                        // 调用发现未连接重连
                        self.ws = top.reconnct();
                        top_ws_ctl = top.TOP_SOCKET_CTL;

                        //  top_ws_ctl.reconnct(function (ws) {
                        //      self.ws = ws;
                        // });
                    }
                    this.acCtl = new ActionControl(top_ws_ctl.pool );
                }


                // if(top.WEB_SOCKET_) {
                //     this.ws = top.WEB_SOCKET_;
                // } else {
                //    this.ws = new WebSocket("ws://127.0.0.1:9527");
                //     top.WEB_SOCKET_ = this.ws;
                // }


                this.ws.onopen = function(){
                    console.log("开始创建连接",this);
                };
                this.ws.onmessage = function(evt){
                    console.log("执行this.ws.onmessage ",evt);
                    var message = JSON.parse(evt.data);
                    self.acCtl.onMsg(message);
                };
                this.ws.onclose = function(evt){
                    console.log(" this.ws.onclose  连接关闭  ！");
                    self.ws.close();
                };
                this.ws.onerror = function(evt){
                    console.log(" this.ws.onerror  ！");
                    layer.closeAll();
                    // self.reconnect(); 门诊挂号 读社保卡 有些依赖读取医保，有些不依赖，都尝试重连会导致，一致尝试重连。
                };


            }
            /**
             *
             * @param truelyCall  表示是否调用 ocx  ,false 表示不走ocx 直接进入done 接口
             * @param input  prepar 阶段返回的 前置 ocx 入参
             * @param doneParam  done 阶段参数
             * @param doneFunc  调用ocx 成功后回调函数
             */
            this.yb_call =  function(truelyCall, input, doneParam,timeoutSeconds, doneFunc){
                // OcxControl.openMask("正在调用医保接口");
                var wsAction = new WsAction(doneFunc, input, doneParam, self.ws,timeoutSeconds);
                if(truelyCall) {
                    if (self.chkState()) {
                        console.log("WS 中 yb_call调用 ，ws连接正常发送请求到消息队列中通知完成")
                        self.acCtl.onSend(wsAction);
                    } else {
                        // 调用发现未连接重连
                        console.log(JSON.stringify({errCode : "js", errMsg: "yb_call 调用时 连接丢失需要重连  ",insureCode: "x", insureHospCode: "x", func: "x",serialNo: "x" }),input,doneParam);
                        layer.closeAll();
                        self.reconnect();
                    }
                } else {
                    wsAction.done();
                }
                // _wsSelf.doneParam = doneParam;
                // var doneFunc;
                // if(truelyCall){
                //     wsAction.send(input);
                // }else{
                //     wsAction.done(doneParam);
                // }
            };

            this.chkState = function () {
                // 0 ：对应常量CONNECTING (numeric value 0)，
                // 正在建立连接连接，还没有完成。The connection has not yet been established.
                // 1 ：对应常量OPEN (numeric value 1)，
                // 连接成功建立，可以进行通信。The WebSocket connection is established and communication is possible.
                // 2 ：对应常量CLOSING (numeric value 2)
                // 连接正在进行关闭握手，即将关闭。The connection is going through the closing handshake.
                // 3 : 对应常量CLOSED (numeric value 3)
                //  连接已经关闭或者根本没有建立。The connection has been closed or could not be opened.
                var isConnected = false;
                console.log("状态检查 chkState 方法里面 readyState 异常",self);

                if(!self.ws) return isConnected ;

                switch(self.ws.readyState) {
                    case  0:
                        // console.log("ws正在连接中");
                        break;
                    case  1:
                        // console.log("ws连接成功");
                        isConnected = true;
                        break;
                    case 2:
                        // console.log("ws正在关闭");
                        break;
                    case 3:
                        // console.log("ws已经关闭");
                        break;
                }
                return isConnected;

            };
            this.reconnect = function () {
                return $pop.confirm('医保WS连接已断开是否重连？',function () {
                    self.init();
                    return true;
                });
            };

            this.init();


        }catch(err){
            // 浏览器不支持
            console.log("浏览器错误",err );
        }
    }


    // HTTP

    var Http = function () {
        try{
            this.type = "HttpInstance";
            //  调用队列的实现 每次完整的ws调用生命周期

            var self = this;
            //todo ws超时没有返回需要处理
            this.init = function () {
            }
            /**
             *
             * @param truelyCall  表示是否调用 ocx  ,false 表示不走ocx 直接进入done 接口
             * @param input  prepar 阶段返回的 前置 ocx 入参
             * @param doneParam  done 阶段参数
             * @param doneFunc  调用ocx 成功后回调函数
             */
            this.yb_call =  function(truelyCall, input, doneParam,timeoutSeconds, doneFunc){
                // OcxControl.openMask("正在调用医保接口");

                var httpAction = new HttpAction(doneFunc, input, doneParam, timeoutSeconds);
                if(truelyCall) {
                    console.log("WS 中 yb_call调用 ，ws连接正常发送请求到消息队列中通知完成")
                    // self.acCtl.onSend(wsAction);
                    httpAction.send();

                } else {
                    httpAction.done();
                }
            };
            // this.init();


        }catch(err){
            // 浏览器不支持
            console.log("浏览器错误",err );
        }
    }


    //  WS 连接只创建一次的功能
    // top 框架端口不一致 存在跨域
    // var ws = top.web_socket || undefined;
    // $(document).ready(function(){
    //
    //     if(ws && ws.ws.readyState == 1) {
    //         ws = top.web_socket;
    //     }
    //     ws = new WS();
    //     top.web_socket = ws;
    // });


    // new

    // var ws = top.getCache("websk");
    //
    // if( !ws ){
    //     ws = new WS();
    //     top.setCache("websk",ws);
    //     ws = top.getCache("websk");
    // }

    // console.log("----- ",ws);

    var ws = new Http(); // new WS();



    ////////////////////////////////////////初始化ws
    var layerIdx;
    var OcxControl = (function () {
        // 错误接口调用
        var erroReporte = function (errObj) {
            // 关闭提示
            $ajax.postJson(base + '/ui/insure/func/error',errObj,false).fail(function(rst){
                //医保调用成功
                console.error(rst);
                // $pop.alert('异常上报接口访问报错');
            });
        };
        /**
         *
         * @param func java 功能号
         * @param insureCode  ocx功能模块编码
         * @param input   入参
         * @param isWait  手动关闭提示 ( 传入 true ,则根据done 接口返回的开关来决定是否关闭弹窗，功能后台暂未支持)
         */

        var openMask = function (msg,timer) {
            closeMask();
            var opt = {
                title: false,
                content: "<div class='tip-icon'><h3>请勿关闭页面或进行其他操作...</h3>" +  (msg || '请耐心等待医保响应') + "</div>",
                closeBtn:0,
                skin:"mask-pop",
                btn:false,
                cancel: false,
                // time:60000 // 超时一分钟后关闭
            };
            if(timer) {
                opt.time = timer;
            }
            layerIdx = layer.open(opt);
        }
        var closeMask = function () {
            layer.close(layerIdx);
        }

        /**
         *  提供给外部调用医保的前端方法
         * @param func   功能号
         * @param insureCode   模块号
         * @param input   给 prepar 阶段的入参
         * @param isWait
         * maskMsg  弹出遮罩的提示文字
         */
        var insureCall = function (func,insureCode,input,isWait,maskMsg) {
            var inObjPrepare = {};
            inObjPrepare.func = func;
            inObjPrepare.insureCode = insureCode;
            inObjPrepare.input = JSON.stringify(input);
            var def = new jQuery.Deferred();
            var ret,insurObj,cid,ocxFn,serialNo,callStatus,nextStepInfo,insureHospCode; // func, insureCode,
            var func = func;
            openMask(maskMsg);
            console.log("insureCall 调用开始, 打开遮罩... 并调用prepare （func,insureCode, input)",func,insureCode, input);
            // prepare 阶段请求
            var ajaxObj = $ajax.ajaxWait({ url: base+'/ui/insure/func/prepare',data: JSON.stringify(inObjPrepare),jsonData:true }).done(function(rst){
                try {
                    ret =  rst.data;
                    insurObj = ret.output;
                    cid = ret.classid;
                    ocxFn = ret.method;
                    //  func =  ret.func;
                    serialNo = ret.serialNo;
                    //  insureCode = ret.insureCode;
                    callStatus = ret.callStatus;
                    nextStepInfo =  ret.nextStepInfo;
                    insureHospCode = ret.insureHospCode;
                    paramForDone =  ret.paramForDone;
                    console.log("insureCall prepare 成功返回, 调用状态:", callStatus );
                    /**
                     * Prepare阶段
                     */
                    // CS000("000", "prepare未执行，需先执行其他功能号"),
                    //  CS033("033", "prepare未执行，需先执行其他功能号,且这个功能号的call和done终止"),
                    //  CS100("100", "prepare成功，继续call"),
                    //  CS130("130", "prepare成功，但需终止call"),
                    //  CS140("140", "prepare成功，但需跳过call,去执行done"),
                    //  CS200("200", "prepare失败，call终止"),
                    /**
                     * Call阶段
                     */
                    // CS120("120", "prepare成功，call失败")

                    /**
                     * Done阶段
                     */
                    // CS111("111", "prepare成功，call成功，done成功"),
                    // CS112("112", "prepare成功，call成功，done失败");
                    if(callStatus == "000") {
                        // 嵌套调用的方式
                        insureCall(nextStepInfo.func,insureCode,nextStepInfo.input).then(function (ret) {
                            // 这执行后续 调用ocx控件 done
                            // 可以根据 ret 来判断是否执行
                            runOcxOrWs(true,ret.callType);
                        },function (err) {
                            // 失败 弹出提示
                            return def.reject(err);
                        });
                    } else if (callStatus == "033") {
                        insureCall(nextStepInfo.func,insureCode,nextStepInfo.input).then(function (ret) {
                            // 不在继续执行当前的ocxRUN 而是将签到信息返回
                            $pop.alert("因未签到，系统自动完成签到！请再尝试刚才的业务操作！"); // 暂时写死后期不需要处理 http 框架负责处理
                        },function (err) {
                            // 失败 弹出提示
                            return def.reject(err);
                        });
                    }
                    else if( callStatus == "100") {
                        runOcxOrWs(true,ret.callType);
                    } else if( callStatus == "140") {
                        //忽略call的调用,但需继续done的调用
                        //适用于一些需要走医保接口流程,但又不用真正调接口的情况
                        runOcxOrWs(false,ret.callType);
                    } else if( callStatus == "130") {
                        // 错误消息不为空 时 是可以弹出错误消息
                        closeMask(ret);
                        if(ret.stopMessage.length > 0 ) {
                            $pop.alert(ret.stopMessage);
                        }
                        def.reject(ret);
                    } else {
                        closeMask(ret);
                        def.reject(ret);
                    }

                    // 调用浏览器ocx 控件 或 ws 前置应用
                    function runOcxOrWs (truelyCall, callType) {
                        // 多次事务调用说明
                        // 第1次事务需要 第二次事务作为前置执行
                        // 第二次事务执行过程出现异常会 告知第二次事务的done接口
                        // 第二次事务可以通过done 返回 一个新标识来控制是否继续执行第1次事务的后续执行
                        if(ret.callType === 'OCX'){
                            var outParam = "[]";
                            // ocx 出参
                            if(truelyCall){
                                try {
                                    var ocxInstance = ocx.createOcx(cid);
                                    outParam = ocxInstance.execute(JSON.stringify(insurObj));
                                } catch (e) {
                                    throw new Error('请检查OCX插件已经成功安装!');
                                }
                            }
                            var inObjDone = {};
                            inObjDone.func = func;
                            inObjDone.insureCode = insureCode;
                            inObjDone.serialNo = serialNo;
                            inObjDone.input = JSON.parse(outParam);
                            inObjDone.paramForDone = paramForDone;
                            //返回医保调用结果
                            $ajax.ajaxWait({ url: base+'/ui/insure/func/done',type:"POST",data: JSON.stringify(inObjDone),jsonData:true
                            }).then(function(rst){
                                if(!isWait) closeMask(rst);
                                //  医保调用成功
                                return def.resolve(rst);
                            },function(err) {
                                return def.reject(err);
                            });
                        }else if(ret.callType === 'WS'){
                            var inObjDone = {};
                            inObjDone.func = func;
                            inObjDone.insureCode = insureCode;
                            inObjDone.serialNo =serialNo;
                            inObjDone.paramForDone = paramForDone;
                            inObjDone.trueInsureCode = ret.insureCode;
                            inObjDone.input = [];
                            // inObjDone.errorMsg  = ""; //收到错误消息
                            /**
                             * 依赖  inObjDone 的  serialNo trueInsureCode 属性参数
                             * 依赖 insurObj   =  ret.output;
                             */
                            ws.yb_call(truelyCall, insurObj, inObjDone,ret.timeoutSeconds,function(){
                                if(inObjDone.errorMsg) {
                                    // 异步无法抛出异常
                                    var e =  new Error(inObjDone.errorMsg);
                                    erroReporte({errCode : "120", errMsg: e.message ,insureCode: insureCode, insureHospCode: insureHospCode, func: func,serialNo:serialNo});
                                    closeMask();
                                    console.log("yb_call 调用出现异常，并移除遮罩",e.message);
                                    // $pop.alert('调用异常:' + e.message);
                                    return def.reject(e);
                                }
                                //返回医保调用结果 // 多次下载 问题
                                var openTips = !!isWait;
                                // // 是否开启提示 1在目录下载时不开启，2下载完成后开启
                                // // 这里涉及业务逻辑。。。
                                // var Input = JSON.parse(inObjDone.input);
                                // if(Input[0].ext.endPage == "0" ){
                                //     openTips = false;
                                // }
                                console.log("yb_call 调用完成 ,准备调用 done接口, 收到返回的消息对象是：",inObjDone);
                                $ajax.ajaxWait({ url: base+'/ui/insure/func/done',data: JSON.stringify(inObjDone),jsonData:true
                                },"done",openTips).then(function(rst){
                                    console.log("done接口调用完成 ,并移除遮罩 ,收到返回的消息对象是：",rst);
                                    // $ajax.postJson( base+'/ui/insure/func/done',pp).done(function(rst){
                                    //医保调用成功\
                                    // if(!isWait) closeMask(rst);
                                    closeMask(rst);
                                    return def.resolve(rst);
                                },function(err) {
                                    return def.reject(err);
                                });
                                // 除了done 以外的异常 由框架 ajaxComplete 控制
                                //.always(function (ret) {
                                //     console.log(ret);
                                // });

                            });
                        }else {
                            // 非 ocx 调用的情况
                            return def.resolve(null);
                        }
                    }
                } catch (e) {
                    // todo outParam 出现异常 上报错误日志接口
                    // 三种情况
                    //（目前ocx 浏览器不调用了 ）
                    // 1上报 ocx 调用报错 （可能由于环境问题）
                    //   2 js执行过程报错
                    //   3 ocx应用报错
                    //   几乎中断执行done 的异常这里都会上报
                    //   其他情况 例如 医保接口调用出错 返回给 done 去处理即可
                    erroReporte({errCode : "120", errMsg: e.message ,insureCode: insureCode, insureHospCode: insureHospCode, func: func,serialNo:serialNo});
                    $pop.alert('调用异常:' + e.message);
                    console.log("异常中移除遮罩");
                    closeMask();
                    // debugger;
                    def.reject(e);
                }
            });
            var promiseExt = def.promise();
            if(isWait) promiseExt.ajaxWait = ajaxObj;

            return promiseExt;

        };
        // 通过ws 发送消息 调用ocx
        /**
         *
         * @param func      功能号
         * @param insureCode   功能模块
         * @param input      ocx入参
         * @param serialNo   业务流水号
         * @param isWait
         * @param callback
         */
        var wsCall = function (inputArr, insureCode, serialNo,isWait,timeoutSeconds,callback) {
            console.log("非医保业务流程的WS方法调用开始");
            var def = new jQuery.Deferred();

            var inObjDone = {
                serialNo:serialNo,
                trueInsureCode: insureCode
            }
            var insurObj = inputArr;
            // var insurObj = {
            //     // func: inputArr[0].func,
            //     input: inputArr,
            //     serialNo:serialNo,
            //     insureCode: insureCode
            // }

            // 弹出遮罩
            console.log("非医保业务流程的WS方法调用开始 --- 弹出遮罩");
            openMask();
            console.log("非医保业务流程的WS方法调用开始 --- 调用ws.yb_call");
            // truelyCall, input, doneParam,timeoutSeconds, doneFunc
            ws.yb_call(true, insurObj, inObjDone,timeoutSeconds || 30 ,function(){
                console.log("非医保业务流程的WS方法调用  ws.yb_call完成回调 --- 移除遮罩");
                closeMask();
                console.log("非医保业务流程的WS方法调用ws.yb_call完成回调 --- 收到返回对象",inObjDone);
                if(inObjDone.errorMsg) {
                    erroReporte({errCode : "120", errMsg:inObjDone.errorMsg ,insureCode: insureCode, insureHospCode: "wsCall_HospCode", func: inputArr[0].func ,serialNo:serialNo});
                    $pop.alert('调用异常:' + inObjDone.errorMsg);
                    return def.reject(inObjDone);
                }
                //返回医保调用结果
                callback(inObjDone);
                console.log("非医保业务流程的WS方法调用完成",inObjDone);
                return def.resolve(inObjDone);
            });
            return def.promise();


        };


        return {
            OCX : ocx,
            erroReporte :erroReporte,
            insureCall : insureCall,
            wsCall : wsCall,
            openMask : openMask,
            closeMask: closeMask
        };

    })();
    return OcxControl;

});

