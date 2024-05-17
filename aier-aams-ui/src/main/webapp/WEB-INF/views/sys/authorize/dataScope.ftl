<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
    <meta http-equiv="X-UA-Compatible" content="IE=9" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>设置数据权限 - 爱尔医院</title>
    [#include "/WEB-INF/views/common/include_resources.ftl"]
    <style type="text/css">
    .mainWrap{position: absolute;top:0;left:0;right:0;bottom:0;}
    /*.main-w{position: absolute;top:0;left:0;width:160px;bottom:0;border-right:5px solid #dfe2e6;padding:10px 0 10px 20px;}*/
    .main-t{position: absolute;top:0;left:0px;right:0;height:35px;padding:10px 0 0 15px;}
    .main-e{position: absolute;top:50px;left:0px;right:0;bottom:0;overflow: auto;display:none;}
    .p-info{line-height:1.4em;padding:5px 0;font-size:14px;}
    .p-info .s-t{color:#00A0E9;}
    .p-info .s-info{font-weight: bold;}
    #ul-kindTree{margin:10px 0 0 10px;}
    </style>
</head>

<body>
<div class="mainWrap">
[#--    <div class="main-w">--]
[#--        <p class="p-info"><span class="s-t">姓名：</span><span class="s-info">${staff.name}(${staff.code})</span></p>--]
[#--    </div>--]

    <div class="main-t searchHead bob-line">
        <form id="sbox" class="soform form-validate form-enter">
            <span class="fl mar-r20 bold"><label class="lab-inline">姓名：</label><em class="blue bold">${staff.name}(${staff.code})</em></span>
            <span class="fl">
                <label class="lab-inline bold">数据权限范围：</label>
            	<select id="dataScopeType" name="dataScopeType"  style="width:120px;">
                    <option [#if staff.dataScopeType == '1']selected="selected"[/#if] value="1">当前医院</option>
                    <option [#if staff.dataScopeType == '2']selected="selected"[/#if] value="2">当前省区</option>
                    <option [#if staff.dataScopeType == '3']selected="selected"[/#if] value="3">全集团</option>
                    <option [#if staff.dataScopeType == '4']selected="selected"[/#if] value="4">跨机构</option>
            	</select>
            </span>
            <span class="fl mar-l5">
                <button type="button" class="btn btn-small btn-primary btn-save" >保存</button>
            </span>
        </form>
    </div>

    <div class="main-e">
        <ul id="ul-kindTree"></ul>
    </div>
</div>
</body>

[#include "/WEB-INF/views/common/include_js.ftl"]
<script type="text/javascript">
require(["pub"],function(){
    var scopeType = '';

    $('#ul-kindTree').tree({
        animate : true,
        lines : true,
        url:'${base}/ui/sys/dataScope/getForTree/${staff.id}',
        flatData: true,
        checkbox : true,
        onClick : function (node) {
          
        }
      });

    $('#dataScopeType').combobox({
        onSelect : function(record) {
          scopeType = record.value;
          if(scopeType==='4'){
            $('.main-e').show();
          }else{
            $('.main-e').hide();
          }
        }
    });

    $('.btn-save').click(function() {//保存修改数据
        var dataScopeType = $('#dataScopeType').val();
        var treeChkedData,instCodes=[],instIds=[];
        if(scopeType == '4'){
          treeChkedData = $('#ul-kindTree').tree('getChecked', ['checked','indeterminate']);
          $.each(treeChkedData,function(i,v){
            instCodes.push(v.instSchema);
            instIds.push(v.id);
          });
        }

        var sendData = {
            staffId : '${staff.id}',
            dataScopeType : dataScopeType,
            instCode : instCodes.join(","),
            instId : instIds.join(",")
        };

        // window.console && console.log(sendData);

        $ajax.post('${base}/ui/sys/dataScope/save',sendData,true).done(function (rst) {
          if (rst.code==='200'||rst.code==='201') {
            setTimeout(function () {
	           $pop.closePop({refreshGrid:true});
	        }, 400);
          };
        });
        
      });

});
</script>
</body>

</html>
