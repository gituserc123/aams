<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>参数配置</title>
[#include "/WEB-INF/views/common/include_resources.ftl"]
<style type="text/css">
.cont-grid{overflow: hidden;zoom: 1;}
.pop-addOneItem {width: 496px;position:absolute;left:0px;top:-4000px;}
.layui-layer .pop-addOneItem {position: relative;top: 0;}
</style>
</head>
<div></div>
<body>
<div class="cont-grid">
		<div class="searchHead">
	          <form id="sbox" class="soform form-enter">
	                <input class="txt inline w-250" type="text" name="key" placeholder="参数名称/备注">
	                <button type="button" class="btn btn-small btn-primary so-search"  data-opt="{grid:'#gridBox',scope:'#sbox'}">查 询</button>
	            </form>
	      </div>
	<div class="cont-grid">
		<div id="gridBox"></div>
	</div>
  </div>


	<div class="pop-addOneItem">
		<form id="updateForm" class="soform form-validate form-enter pad-t30" method="post"
			action="json/true.js" data-opt="{beforeCallback:'addOneHao'}">
			<input name="confCode" type="hidden" />
			<div class="row">
				<div class="p11">
					<div class="item-one">
						<label class="lab-item">参数名称：</label> 
						<input class="txt" name="confCode" type="text" readonly="readonly" disabled="disabled"/>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="p11">
					<div class="item-one">
						<label class="lab-item">参数描述：</label> 
						<textarea class="txta txt-validate" id="remarks" name="confName" style="height:100px"data-options="validType:['length[0,256]']" readonly="readonly" disabled="disabled"></textarea>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="p11">
					<div class="item-one">
						<label class="lab-item">参数值：</label> 
						<input class="txt txt-validate" id="numberV" type="text" name="confValue" maxLength="30" noNull="参数值必填" validType="fnValid['checkConfig']" />
					</div>
				</div>
			</div>
			<p class="row-btn center">
				<input type="button" class="btn btn-primary btn-easyFormSubmit" name="btnSubmit" value="确定" />
				<input type="button" class="btn btn-closePop" name="btnCancel" value="取 消" />
			</p>
		</form>
	</div>

</body>


[#include "/WEB-INF/views/common/include_js.ftl"]

<script type="text/javascript">
require(["pub"],function(){

    $grid.newGrid("#gridBox",{
        // method:'post',
        columns:[[
		  {title:'编辑',field:'editPermit',width:'64px',formatter: function(value,row,index){
			  var chkHtml = '<span class="s-op s-op-edit icon-edit" title="修改"></span>';
			  return chkHtml;
			}},
          {title:'参数编码',field:'confCode',width:'150px',titletip:true},
          {title:'参数描述',field:'confName',width:'600px',align:'left',titletip:true},
          {title:'参数值',field:'confValue',width:'150px',titletip:true},
          
              {title:'修改人',field:'modiferName',width:'130px'},
              {title:'修改时间',field:'modifyDate',width:'150px',titletip:true},
        ]],
        onLoadSuccess : function (data) {
          var formData = data.rows;
        },
        url:'${base}/ui/sys/config/getForList',
        pagination:true,
        // height: 'auto',
        offset :0
    });
    
    var id;
    var cRow = {};
	function open(row){
		$pop.popForm({
			title: '编辑',
			target : $('.pop-addOneItem'),
			area : ['500px','310px'],
			beforePop : function ($formBox) {
				$('#updateForm').form('load',row);
			},
			beforeSubmit : function (formData,$form,popid) {
			    var  data = $form.sovals();
			    
			    $ajax.post('${base}/ui/sys/config/updateValue',{
			    	'confCode':data.confCode,
			    	'value':data.confValue
			    }).done(function(rst){
			    	$('#gridBox').datagrid('reload');    
			        layer.close(popid);
			    });          
			
			}
	    });
	}
	
	function getValue(confCode){
    	if(Object.prototype.toString.call(confCode)=='[object Array]'){
    		return confCode[0];
    	}else{
    		return confCode;
    	}
    }

    window.checkConfig = function (value) {
		var rt;
		// window.console && console.log(id,value);
    	$ajax.postSync('${base}/ui/sys/config/check',{confCode:cRow.confCode, value:value}).done(function (rst) {
   			rt = rst.data;
   	    });
        return rt;
    }

    //绑定修改事件
    $('.cont-grid').on('click','.s-op-edit',function() {
		var row = $('#gridBox').datagrid('getSelected');
		id = row.id;
		cRow = row;
		var canEdit = checkConfig(row.confValue);
		if (canEdit) {//如果是后台已存在数据，提交到后台
		  open(row);
		};
          return false;
      });




});

</script>
</body>

</html>
