define(['pub'],function () {
  return function(dom,txt){
    $(dom).mouseup(function (e) {//获取词条选择内容
      var selection = window.getSelection();
      var contents = selection.getRangeAt(0).cloneContents();
      var tempDom = document.createElement("div");
      tempDom.appendChild(contents);
      txt =  tempDom.innerHTML.replace('\n','<br>');
      // console.log(contents,selectionTxt)
    });
  }
});