[#macro ui_button_ext id url text class]
<button id="${id}" class="ui-btn ${class}" type="button"  url="${url}">${text}</button>
[/#macro]

[#-- 
	前端打印组件
	author： rain_deng
	createDate: 2018-05-11
	
	参数说明：
	isPopUp:     静默打印:false  预览打印:true  非必填，默认值是false
	template:    对应的打印模板文件名称(其实就是帆软报表中的cpt文件名),命名规则： 业务服务域_模板名 如：outp_operation、drug_store；
	business:    业务编号参数,确保唯一性（帆软平台不会走租户查询）, 多个可用 | 号分隔，会在帆软平台中查询相关业务打印数据
	printerName: 指定打印机名称
	isCommon:    true: 是公共报表，会访问帆软公共目录的报表； false：医院个性化报表，会访问医院目录下的报表 ；默认值是false
	
	tenant : 	  租户id，帆软报表cpt文件可以根据该名字获取到租户id
	
	使用说明： 在需要调用打印的地方加入该宏，如：
	1. 引入标签
	[#include "/WEB-INF/views/common/macros/include_common_macros.ftl"]
	
	注意：如果在页面中引入了 [#include "/WEB-INF/views/common/macros/include_resources.ftl"]，
	则无需引入 [#include "/WEB-INF/views/common/macros/include_common_macros.ftl"]
	
	2. 页面js中使用该标签
	[@ui_print template="outp_trans" business="10080912312" isPopUp="true" /]
	[@ui_print template="outp_trans" business="10080912312"  printerName="doctor"/]
--]
[#macro ui_print template business printerName isPopUp="false" isCommon="false"]
	[#if isPopUp == "false" && isCommon =="false"]
		var url = "${fruri}/print.html?cpt=/ahis/${tenant}/${template}.cpt&t=${isPopUp}&b=${business}&h=${hospname}&p=${printerName}&c=${tenant}";
		$pop.newTabWindow("单据打印",encodeURI(url),true);
		//setTimeout(function(){
		//	$pop.closeTabWindow("单据打印");
		//},5000);
	[#elseif isPopUp == "false" && isCommon =="true"]
		var url = "${fruri}/print.html?cpt=/ahis/0/${template}.cpt&t=${isPopUp}&b=${business}&h=${hospname}&p=${printerName}&c=${tenant}";
		$pop.newTabWindow("单据打印",encodeURI(url),true);
		//setTimeout(function(){
		//	$pop.closeTabWindow("单据打印");
		//},5000);
	[#elseif isPopUp == "true" && isCommon =="false"]
		var url = "${fruri}/print.html?cpt=/ahis/${tenant}/${template}.cpt&t=${isPopUp}&b=${business}&h=${hospname}&p=${printerName}&c=${tenant}";
		window.open(encodeURI(url));
	[#else]
		var url = "${fruri}/print.html?cpt=/ahis/0/${template}.cpt&t=${isPopUp}&b=${business}&h=${hospname}&p=${printerName}&c=${tenant}";
		window.open(encodeURI(url));
	[/#if]
[/#macro]


[#macro ui_button_search id url text class placeholder]
<span id="${id}" class="search-btn  ${class}"  url="${url}">
    <input class="txt inline wp-45" name="searchKey"  type="text"  placeholder="${placeholder}" maxlength="100" /><button  class="btn btn-small btn-small-b btn-primary btn-searchPatient" type="button">查询</button>
</span>
[/#macro]

