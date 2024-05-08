;(function (factory) {
  if (typeof define === "function" && define.amd) {
      // AMD模式
      define([ "jquery" ], factory);
  } else {
      // 全局模式
      factory(jQuery);
  }
}(function ($) {

  $.fn.soUpOneImg = function (o) {
    o = $.extend({
      thumb: null, //缩略图对象
      input: null, //输入框对象
      autoSetValue: true, //是否直接设置thumb和input值
      accept: '.gif,.jpg,.jpeg,.bmp,.png', //可以接受的格式
      maxSize: '2m', //默认最大2m
      blankPic: '/images/base/blankPic.png', //默认无图时，thum图片
      clearFn: function (result) {}, //点击清除按钮
      sureback: function (result) {} //上传并设置完成后执行事件
    }, $T.data(this) || o || {});

    var $t = $(this),
      $thumb = $(o.thumb),
      $input = $(o.input);
    var radom = Math.round(new Date() / 1000);

    //算出用来比较的maxsize，以k为单位
    var sizeSet = o.maxSize.match(/^(\d+)(k|m|K|M)$/);
    if (!sizeSet) {
      $pop.msg('图片上传空间，最大上传大小设置不正确！');
      return;
    }
    var maxS = sizeSet[1];
    var maxU = sizeSet[2] ? sizeSet[2].toLowerCase() : 'k';
    if (maxU == 'm') {
      maxS = maxS * 1024
    };

    if (o.autoSetValue) {
      $thumb.attr("src", o.blankPic);
    };

    $t.click(function () {
      var $upload = $('<input id="txt-upload' + radom + '" style="display:none;" accept="' + o.accept + '" type="file" />');
      $t.after($upload);
      $upload.change(function () {
        // try {
        var file = this.files[0];
        var fileSize = Math.ceil(file.size / 1024);
        if (fileSize > maxS) {
          $pop.msg('文件大于' + o.maxSize + '，无法上传！');
          return;
        }

        var reader = new FileReader();
        reader.onload = function () {
          var img = new Image();
          img.src = reader.result;
          img.onload = function () {
            var w = img.width,
              h = img.height;
            var canvas = document.createElement('canvas');
            var ctx = canvas.getContext('2d');
            $(canvas).attr({
              width: w,
              height: h
            });
            ctx.drawImage(img, 0, 0, w, h);
            var base64 = canvas.toDataURL('image/jpeg', 0.5);
            var result = {
              url: window.URL.createObjectURL(file),
              base64: base64,
              clearBase64: base64.substr(base64.indexOf(',') + 1),
              suffix: base64.substring(base64.indexOf('/') + 1, base64.indexOf(';'))
            };

            if (o.autoSetValue) {
              $thumb.attr("src", result.base64);
              $input.val(result.base64);
            }
            $upload.next('.btn-uploadImg').remove();
            o.sureback && o.sureback(result);
            var $clearBtn = $('<span class="btn btn-warning btn-uploadImg btn-small" style="margin-left:5px;">清除</span>');
            $t.after($clearBtn);
            $upload.remove();
            $clearBtn.click(function () {
              if (o.autoSetValue) {
                $thumb.attr("src", o.blankPic);
                $input.val('');
                $(this).remove();
              } else {
                o.clearFn(result);
              }
            });

          }
        }
        reader.readAsDataURL(this.files[0]);
        // } catch(e) {
        //  console.log(e);

        // }
      });
      $upload.click();
    });
    return $t;
  };
})
);
