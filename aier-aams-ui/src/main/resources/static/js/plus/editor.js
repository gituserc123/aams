define(['baidueditor', 'zeroclipboard', 'bdlang'], function (UE, zcl) {
  //百度编辑器默认支持脚本
  //页面html 如：
  //<script id="editor" class="so-editor so-editor_required editorkey_myue" name="myue" type="text/plain" style="height:300px;"></script>
  return function () {
    window.ZeroClipboard = zcl;
    if ($('.so-editor').length) {
      $('.so-editor').each(function () {
        var ueName = $(this).attr('class').match(/editorkey_.+/g) || ['editorkey_eyeUe'];
        ueName = ueName[0].split(/ |_/)[1];
        var id = $(this).attr('id');
        window[ueName] = UE.getEditor(id);
      });
    };
  }
});