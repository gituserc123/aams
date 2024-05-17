<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>编辑用户角色 - 爱尔AHIS管理系统</title>
[#include "/WEB-INF/views/common/include_resources.ftl"]
</head>

<body>
<div class="wrapper-popForm">
  <form class="soform form-validate form-enter pad-t15 pad-r30" method="post" action="">
    <fieldset>
      <div class="h250 overauto pad-t20 pad-l20 mar-l30 bgc-f3 bor-rad-2">
          <ul id="ul-powerTree" class="ul-powerTree"></ul>
      </div>
      <p class="row-btn">
        <input type="button" class="btn btn-primary btn-submitRole" name="btnSubmit" value="保 存" />
        <input type="button" class="btn btn-cancel" name="btnCancel" value="取 消" />
      </p>
    </fieldset>
  </form>
</div>
[#include "/WEB-INF/views/common/include_js.ftl"]
<script type="text/javascript">
    requirejs(['pub'],function () {
        $('#ul-powerTree').tree({
            animate : true,
            url : '${base}/ui/sys/authorize/getRoleTreeByStaffInst/${staffId!}/${instId!}', //根据用户和医院id角色树
            onlyLeafCheck : true,
            flatData:true,
            checkbox : true,
            lines : true
        });

        $('.btn-submitRole').click(function () {
            var checked = $('#ul-powerTree').tree('getChecked');
            var chkIds = [];
            $.each(checked,function (i,v) {
              chkIds.push(v.id);
            });
            
            var sendData = {
		     	instId : '${instId!}',
		     	staffId : '${staffId!}',
		     	roleIds : chkIds.join(',')
		    };
		    
            $ajax.post('${base}/ui/sys/authorize/update',sendData,true).done(function (rst) {
              if (rst.code==='200'||rst.code==='201') {
                setTimeout(function () {
		           $pop.closePop({refreshGrid:true});
		          }, 400);
              };
            });

        });
    });
</script>

</html>
