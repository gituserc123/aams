<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>Error 服务器错误 </title>
<link type="text/css" rel="stylesheet" href="${base}/static/css/layer.css" />
<style type="text/css">
*{padding: 0;margin:0;}
body{
font-size:0.75em;
font-family:Helvetica Neue,Helvetica,PingFang SC,\5FAE\8F6F\96C5\9ED1,Tahoma,Arial,sans-serif,"Microsoft YaHei";
color:#555;
word-wrap:break-word;word-break:break-all;
}
.h2-err{font-size:28px;font-weight: normal;margin-bottom:10px;}
.strong-title{color:#F87453;font-weight: normal;font-size:32px;}
.p-err{line-height: 1.8em;font-size:14px;margin:1em 0;}
.err-404{position: absolute;width:400px;height:200px;padding:30px 0 0 280px;top:50%;left:50%;margin:-180px 0 0 -340px;background:url(${base}/static/images/err.png) no-repeat;}
.errInfoC{position: absolute;left:10%;right:10%;top:10%;bottom:10%;opacity:0.7;border:1px dotted #ccc;overflow: auto;font-size: 12px;line-height:24px;padding:10px;font-family:Verdana;color:#3f4df3;background-color: #f6fbff;}
.errInfoC:hover{opacity:1;}
#errInfoC{display: none;}
#showErrInfo{position: fixed;right:10px;bottom:10px;color:#999;z-index:10;cursor:hand;cursor:pointer;}
.p-btn{height:40px;}
.btn {
display: -moz-inline-stack;display:inline-block;*display:inline;*zoom:1;
padding: 7px 16px;
margin: 0 5px 0 0;
color: #333;
white-space: nowrap;
font-weight: bold;
font-size: 12px;
text-align: center;
vertical-align: top;
background-clip: padding-box;
background-color: #f3f3f3;
background-image: -webkit-gradient(linear, left top, left bottom, from(#f5f5f5), to(#f1f1f1));
background-image: linear-gradient(to bottom, #f5f5f5, #f1f1f1);
background-repeat: repeat-x;
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#fff5f5f5', endColorstr='#fff1f1f1', GradientType=0);
border: 1px solid #dcdcdc;
border-radius: 1px;
-webkit-box-shadow: none;
box-shadow: none;
cursor:hand;cursor:pointer;
}
.btn .icon{margin-right: 2px;}
.btn:hover {
border-color: #c6c6c6;
-webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
background-color: #f5f5f5;
background-image: -webkit-gradient(linear, left top, left bottom, from(#f8f8f8), to(#f1f1f1));
background-image: linear-gradient(to bottom, #f8f8f8, #f1f1f1);
background-repeat: repeat-x;
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#fff8f8f8', endColorstr='#fff1f1f1', GradientType=0);
background-position: 0 0;
-webkit-transition: none;
transition: none;
}
.btn.active,
.btn:active {
background-image: none;
outline: 0;
/*background: #e8e8e8;*/
-webkit-box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.1);
box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.1);
background-color: #f4f4f4;
background-image: -webkit-gradient(linear, left top, left bottom, from(#f6f6f6), to(#f1f1f1));
background-image: linear-gradient(to bottom, #f6f6f6, #f1f1f1);
background-repeat: repeat-x;
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#fff6f6f6', endColorstr='#fff1f1f1', GradientType=0);
}

.btn:focus {
position: relative;
/* Blue border on button focus. */
border-color: #4D90FE;
}

.btn-primary {
border: 1px solid #1DAFDA;
color: #ffffff;
background: #12C1F7;
filter: progid:DXImageTransform.Microsoft.gradient(enabled = false);
}
.btn-primary:hover,
.btn-primary:active,
.btn-primary.active {
border: 1px solid #1DAFDA;
color: #ffffff;
background: #12C1F7;
}
.btn-primary:active,
.btn-primary.active {
border: 1px solid #1DAFDA;
background: #07b0e4;
}

</style>
</head>

<body>
<div class="err-404">
  <h2 class="h2-err">对不起！<strong class="strong-title">${msg}...</strong></h2>
  <p class="p-err">请检查网络或发送邮件到管理员邮箱<br><a class="a-mail" href="mailto:servicer@aierchina.com">servicer@aierchina.com</a></p>
  <p class="p-btn">
    <span id="showErrInfo" rel="0" style="color:red">查看错误信息</span>
  </p>
</div>

<div id="errInfoC" class="errInfoC">
<pre>
${error!""}
</pre>
</div>
<script type="text/javascript">
var $showbtn = document.getElementById('showErrInfo');
var $infoCont = document.getElementById('errInfoC');
$showbtn.onclick= function(){
    var state = this.getAttribute('rel');
    if(state==='1'){
      $infoCont.style.display='none';
      this.setAttribute('rel','0');
      this.innerHTML = '查看错误信息';
    }else{
      $infoCont.style.display='block';
      this.setAttribute('rel','1');
      this.innerHTML = '关闭错误信息';
    }
}
</script>
</body>

</html>
