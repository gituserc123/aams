<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
</head>
<body>
</body>
<script type="text/javascript" src="${base}/static/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${frUrlCont}?op=emb&resource=finereport.js&inter=zh_CN&__fr_locale__="></script>
<script type="text/javascript">
jQuery.ajax({
     url:"${frUrlCont}?op=fs_load&cmd=sso",
     dataType:"jsonp",
     data:{"fr_username":'${fruserReport}',"fr_password":'${frpwdReport}'},
     jsonp:"callback",
     timeout:8000,//超时时间（单位：毫秒）
     success:function(data) {
        if (data.status === "success") {    //登录成功   
            var encodeUrl = encodeURI('${url!}');
            window.location.href=encodeUrl; // 跳转
        } else if (data.status === "fail"){
            //登录失败（用户名或密码错误）
            alert("用户名或密码错误，请检查系统设置！");
        }
     },
     error:function(){
           // 登录失败（超时或服务器其他错误）
            alert("超时或服务器其他错误！");
     }
});
</script>
</html>
