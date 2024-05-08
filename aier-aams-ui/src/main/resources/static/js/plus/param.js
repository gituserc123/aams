/**
 * 存放页面公用变量
 */
var $p = {
	cmp:'爱尔',
	submitTip : '您确定要提交吗?',
	exportMax : 6000
}

//公共表格列定义
var $cols={
	// userSite : [[
	// {title:'网站地址',field:'url',width:240}
	// ]]
}

/**
 * 定义状态初始值1
 */
var $state = {
	create : 1, //初始状态 新创建
	audit : 2   //已审核 没有审核操作的表示已执行
}


/**
 * 定义ws连接
 */

var wsConfig = {
     url : "ws://10.0.7.100:9527"
   //  url : "ws://localhost:9527" // 本机测试
}