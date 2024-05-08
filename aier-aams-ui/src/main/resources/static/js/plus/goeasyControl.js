/**
 * GEcontrol 创建 GO EASY 连接
 *
 * @function init ( channle,@callback )
 * 1生成channle
 * 2建立连接
 * 3.订阅
 * @return GoEasy 实例
 *
 * @function disconnect (void)
 * 关闭连接
 * @function unsubscribe()
 * 取消订阅
 */

var GEcontrol = (function () {
    var _goEasy;
    var eventTypes = {
        connect :["connected","disconnected","connectFailed"],//连接
        subscribe: ["subMsg","subOk","subFailed"],//订阅
        unsubscribe:["unsubOk","unsubFailed"],//取消订阅
        send : ['sendOk','sendFailed']//发送
    }
    var _eventMsg = {
        type:"",
    }
    var _channel;
    var _feedback;
    var  setEventMsg = function (t,m) {
        _eventMsg = { type:t, msg:m}
        typeof _feedback =="function" && _feedback(_eventMsg);
    }

    var init = function (appkey,channel,feedback,userId,userData) {
        _feedback =feedback;
        _channel =channel
        if(!_goEasy) {
            _goEasy = new GoEasy({
                host:'hangzhou.goeasy.io',//应用所在的区域地址，杭州：hangzhou.goeasy.io，新加坡：singapore.goeasy.io
                appkey:appkey,//替换为您的应用appkey
                // appkey:"BS-fce4fc90cc3b4337b713b942d2549d62",//替换为您的应用appkey
                forceTLS:true, //如果需要使用HTTPS/WSS，请设置为true，默认为false
                // userId:userId, //  "用户唯一标识，如 user-001", //必须指定，否则无法实现客户端上下线监听功能
                // userData:userData || "", // "用户的附加信息，比如性别，年龄"
                onConnected: function() {
                    setEventMsg(eventTypes.connect[0],"连接成功");
                },
                onDisconnected: function() {
                    setEventMsg(eventTypes.connect[1],"连接已断开");
                },
                onConnectFailed: function(error) {
                    setEventMsg(eventTypes.connect[3],error); // 错误编码："+error.code+"错误信息："+error.content
                }
            });
        }

        _goEasy.subscribe({// 订阅 消息
            channel: _channel,
            onMessage: function (message) {
                setEventMsg(eventTypes.subscribe[0],message);
            },
            onSuccess: function () {
                setEventMsg(eventTypes.subscribe[1],"订阅成功");
            },
            onFailed: function (error) {
                setEventMsg(eventTypes.subscribe[2],error);
            }
        });
    }


    var unsubscribe =function () {// 取消订阅
        _goEasy.unsubscribe({
            channel: _channel,
            onSuccess: function () {
                setEventMsg(eventTypes.unsubscribe[0], "退订成功");
            },
            onFailed: function (error) {
                setEventMsg(eventTypes.unsubscribe[1], error);
            }
        });
    }

    var disconnect = function () {//关闭连接
        _goEasy.disconnect();
    }
    var send = function (msg) {//发送消息
        _goEasy.publish({
            channel: _channel,
            message: msg,
            onSuccess: function(){
                setEventMsg(eventTypes.send[0], '发送成功');
            },
            onFailed: function (error) {
                setEventMsg(eventTypes.send[1], error);
            }
        });
    }


    return {
        init: init,
        unsubscribe: unsubscribe,
        disconnect: disconnect,
        send: send
    }



})();

