<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
    <meta http-equiv="X-UA-Compatible" content="IE=9" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>超时登录</title>
    [#include "/WEB-INF/views/common/include_resources.ftl"]
    <style type="text/css">
.p-showTip{font-weight:bold;color:#c00;}
    </style>
</head>
<body>
<div class="reloginPop">
    <form class="soform form-validate form-enter form-reloginPop pad-t10" method="post" action="/login">
        <input type="hidden" name="ajax" value="true"/>
        <div class="pad-20 pad-b0">
            <p class="pad-l20 pad-b20 p-showTip bold red">业务操作超时，请重新输入账号信息：</p>
            <p class="pad-l20 pad-r20 pad-b10"><input type="text" class="txt txt-validate txt-popLoginName" name="username" maxlength="20" placeholder="请输入用户名" noNull="请输入用户名" /></p>
            <p class="pad-l20 pad-r20"><input type="password" class="txt txt-validate txt-popLoginPassword" id="password" name="password" maxlength="20" placeholder="请输入密码" noNull="请输入密码" /></p>
            <input id="enPassword" type="hidden" name="enPassword" value=""/>
            <p class="row-btn pad-t20"><button type="button" class="btn btn-primary btn-popLoinIn">确认登录</button><button type="button" class="btn btn-popLoginOut">注销退出</button></p>
        </div>
    </form>
</div>
</body>

[#include "/WEB-INF/views/common/include_js.ftl"]
<script type="text/javascript">
require(["pub","rsa"],function(){
  $('.btn-popLoginOut').click(function () {
    window.location.href="login.html";
  });
  var reloginFn = {
    login : function () {
      var me = this;
      var $form = $('.form-reloginPop');
      var formValidate = $form.form('validate');
      if(formValidate) {
        var sData = $form.sovals();
        var password = sData.password;
        [@ui_rsa /]
        sData.password = enPassword;
        sData.enPassword = enPassword;
        $ajax.post('/login', sData).done(function (rst) {
          if (rst.code === '200' || rst.code === '201') {
            layer.msg('登录成功，欢迎回来~', {icon: 1});
            $pop.closePop();
          }
        });
      }
    },
    _showMsg : function (msg) {
      $('.p-showTip').text(msg);
    }
  };

  $('.btn-popLoinIn').click(function () {
    reloginFn.login();
  });

});
</script>
</html>
