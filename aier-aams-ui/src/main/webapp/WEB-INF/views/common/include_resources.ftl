<link rel='stylesheet' type='text/css' media='all' href='${base}/static/js/lib/easyui/themes/gray/easyui.css' />
<link type='text/css' rel='stylesheet' href='${base}/static/css/easy-plus.css?619b6' />
<link type='text/css' rel='stylesheet' href='${base}/static/css/socss.css?61d65' />
<link type='text/css' rel='stylesheet' href='${base}/static/css/plugins.css?fc284' />
<link type='text/css' rel='stylesheet' href='${base}/static/css/base.css?c4ac3' />
[#include '/WEB-INF/views/common/macros/include_common_macros.ftl']
<script type='text/javascript'>
    var portalUri = '${portalUri}', base = '${base}';
    var sysNowTime = '${.now?string("yyyy-MM-dd HH:mm:ss")}';
    var sysNowTimeB = '${.now?string("yyyy/MM/dd HH:mm:ss")}';
    //异步时间通过 '${base}/ui/common/common/getSystemTime' 获取
    var env = '${env}';
</script>