if(top !== window ) throw new Error("ws连接放置在顶层页面中");

var TopWs = function (ws) {
    this.init(ws);
}
TopWs.prototype.init = function (ws){
    this.pool = [];
    if(this.ws) this.ws.close();
    this.ws = new WebSocket("ws://127.0.0.1:9527");
}
// TopWs.prototype.reconnct = function () {
//     this.ws.close();
//     this.init();
//      return this;
// }

top.reconnct = function () {
    top.TOP_SOCKET_CTL = new TopWs();
    return  top.TOP_SOCKET_CTL.ws;
}
top.TOP_SOCKET_CTL = new TopWs();