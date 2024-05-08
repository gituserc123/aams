var plusHtml =
//'<link rel="stylesheet" href="plus/plus.css">'+
'<div class="searchhead soform">'+
	'<div class="item-one item-group"><label class="lab-item">过滤图标：</label><input class="txt" id="txt-search" type="text" name="keyword" value="" placeholder="输入字母过滤图标" /></div>'+
	'<div class="item-one">'+
		'<label class="lab-item">图标大小：</label>'+
		'<label class="lab-val"><input class="rad rad-size" type="radio" value="12px" name="iconsize" />12px</label>'+
		'<label class="lab-val"><input class="rad rad-size" type="radio" value="14px" name="iconsize" />14px</label>'+
		'<label class="lab-val"><input class="rad rad-size" type="radio" value="16px" name="iconsize" checked="checked" />16px</label>'+
		'<label class="lab-val"><input class="rad rad-size" type="radio" value="20px" name="iconsize" />20px</label>'+
		'<label class="lab-val"><input class="rad rad-size" type="radio" value="32px" name="iconsize" />32px</label>'+
	'</div>'+
'</div>';

$(function () {
	$('body').prepend(plusHtml);
	$('#txt-search').keyup(function(e) {
	  var _self = $(this);
	  var val = $.trim(_self.val());
	  if(val){
		$('.glyph').addClass('none');
		$('.glyph:contains("'+val+'")').removeClass('none');
	  }else{
		$('.glyph').removeClass('none');
	  }
	});
	$('.rad-size').click(function() {
//	  window.console&&console.log($(this).val());
	  $('.fs1,.fs2,.fs3').css({fontSize:$(this).val()});
	});
})
