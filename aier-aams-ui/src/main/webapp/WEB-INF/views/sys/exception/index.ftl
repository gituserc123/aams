<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>Spring-Boot-Welcome-Page!</title>
[#include "/WEB-INF/views/common/include_resources.ftl"]

<script type="text/javascript">
handleUrl = '${base}/handle';
</script>
</head>
<body data-js="sys:err">
<h2>Hello, Spring-Boot-ExceptionHandler-Page!</h2>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <span id="s-back" class="btn btn-primary">String "ss" to Integer.valueOf(ss)</span>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <span id="s-sync" class="btn">异步报错</span>
</body>
[#include "/WEB-INF/views/common/include_js.ftl"]
</html>
