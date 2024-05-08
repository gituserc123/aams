(function () {

  //从规则集合中获取“存在于现有validatebox规则集合”中的那部分规则
  function getEffectValidType(rs) {
    var thisRules = [];
    var allRules = $.fn.validatebox.defaults.rules;
    rs.forEach(function (validType) {
        var types = /([a-zA-Z_]+)(.*)/.exec(validType);
        var tempRules = allRules[types[1]];
        //var validParams = eval(types[2]);
        if (tempRules != undefined) { thisRules.push(validType); }
    });
    return thisRules;
  }
//临时添加验证规则
//例如：
//var idcardRule = 'idCardNo["birthday,sex,age"]';
//$cardNo.validatebox('addRule',idcardRule);
  function addRule(target, rs) {
    var currentRules = [];
    if ($.type(rs)=="string") { currentRules.push(rs); }
    else if ($.isArray(rs)) { currentRules = rs; }
    else { return; }
    if (currentRules.length == 0) { return; }

    var thisRules = getEffectValidType(currentRules);
    if (thisRules.length == 0) { return; }

    var t = $(target), opts = t.validatebox("options");
    var _validType = opts.validType ? ($.type(opts.validType)=="string"?[opts.validType]:opts.validType) : [];
    if (_validType.length && _validType.length >= 0) {
      $.each(thisRules,function(k,rule){
        _validType.push(rule);
      });
    }else {
        _validType = thisRules;
    }
    opts.validType = _validType;
    t.validatebox("validate");
  };
//临时移除验证规则
//例如：
//var idcardRule = 'idCardNo["birthday,sex,age"]';
//$cardNo.validatebox('removeRule',idcardRule);
  function removeRule(target, rs) {
    var currentRules = [];
    if ($.type(rs)=="string") { currentRules.push(rs); }
    else if ($.isArray(rs)) { currentRules = rs; }
    else { return; }
    if (currentRules.length == 0) { return; }

    var thisRules = getEffectValidType(currentRules);
    if (thisRules.length == 0) { return; }

    var t = $(target), opts = t.validatebox("options");
    var _validType = opts.validType;
    if (_validType&&_validType.length && _validType.length > 0) {
        $.each(thisRules,function (k,rule) {
          var index = _validType.indexOf(rule);
          if (index > -1) {
            _validType.splice(index, 1);
          }
        });
        opts.validType = _validType;
        t.validatebox("validate");
    }
  }


  $.extend($.fn.validatebox.methods,{
      //   rules:表示要动态添加的规则，该参数可以是如下类型
      //   1、String类型：表示一个要动态添加的规则；
      //   2、Array类型：表示一组要动态添加的规则；
      //  返回值：返回表示当前 easyui-validatebox 控件的 jQuery 链式对象。
//例如：
//var idcardRule = 'idCardNo["birthday,sex,age"]';
//$cardNo.validatebox('addRule',idcardRule);
      addRule: function (jq, rules) { return jq.each(function () { addRule(this, rules); }); },
      //   rules:表示要动态移除的规则，该参数可以是如下类型
      //   1、String类型：表示一个要动态移除的规则；
      //   2、Array类型：表示一组要动态移除的规则；
      //  返回值：返回表示当前 easyui-validatebox 控件的 jQuery 链式对象。
//例如：
//var idcardRule = 'idCardNo["birthday,sex,age"]';
//$cardNo.validatebox('removeRule',idcardRule);
      removeRule: function (jq, rules) { return jq.each(function () { removeRule(this, rules); }); }
  });


  var $rules = $.rules = $.fn.validatebox.defaults.rules;
  $.extend($rules, {
    // multipleValidType :{//多个校验的使用
    many :{//多个校验的使用,ep: validType= "many['pFloatFix','range[0,20]']"
        validator: function(value, param){
            var options = $.fn.validatebox.defaults;//获取校验属性
            var returnFlag = true;
            for(var i = 0 ; i < param.length ; i++){
                var result = /([a-zA-Z_0-9]+)(.*)/.exec(param[i]); //匹配校验方法
                var rule = options.rules[result[1]];  //获取校验方法
                if(value && rule){
                    var ruleParam = eval(result[2]);  //获取校验参数
                    if(!rule["validator"].call(this,value, ruleParam)){
                    // if(!rule["validator"](value, ruleParam,this)){
                        var message = rule["message"];
                        // window.console&&console.log(ruleParam,message);
                        if (ruleParam) {
                            for ( var i = 0; i < ruleParam.length; i++) {
                              message = String(message).replace(new RegExp("\\{" + i + "\\}", "g"), ruleParam[i]);
                            }
                        }
                      //  $.fn.validatebox.defaults.rules.multipleValidType.message = message;
                       $.fn.validatebox.defaults.rules.many.message = message;
                       returnFlag = false;
                       break;
                    }
                }
            }
            return returnFlag;
        },
        message:""
    },
    complexValid  : {//自定义正则表达式验证
        validator: function (value, param) {
        if(param) $rules.complexValid.message = param[1];//自定义提示
          var m_reg = new RegExp(param[0]); //传递过来的正则字符串中的"\"必须是"\\"
          return m_reg.test(value);
        },
        message: ""
    },
    fnValid : {
        validator: function (value, param) {//函数验证
          if(window[param[0]]){
            var rst = window[param[0]].call(this,value,param);
            if (typeof(rst) == 'boolean'){
              return rst;
            }else{
              $rules.fnValid.message = rst.msg;
              return rst.result;
            }
          }
          return false;
        },
        message: "值不正确"
    },
    forceValidFail: {//强行验证失败
        validator: function (value, param) {
            var result = param[1];
            return result;
        },
        message: "{0}"
    },
    required3 : {//自定义必填
      validator: function (value, param) {
        if(param) $rules.required3.message = param[0];//自定义提示
        return $.trim(value)!=='';
      },
      message: "{0}"
    },
    minlength : {
      validator: function (value,param) {
        if(param[1]) $rules.minlength.message = param[1];
        var len = value.length;
        return len >= param[0];
      },
      message: '最小长度{0}'
    },
    maxlength : {
      validator: function (value,param) {
        if(param[1]) $rules.maxlength.message = param[1];
        var len = value.length;
        return len <= param[0];
      },
      message: '最大长度{0}'
    },
    rangelength : {
      validator: function (value,param) {
        if(param[2]) $rules.rangelength.message = param[2];
        var len = value.length;
  			return len >= param[0] && len <= param[1];
      },
      message: '最小长度{0}，最大长度{1}'
    },
    min : {
      validator: function (value,param) {
        if(param[1]) $rules.min.message = param[1];
        return value >= param[0];
      },
      message: '数字最小为{0}'
    },
    max : {
      validator: function (value,param) {
        if(param[1]) $rules.max.message = param[1];
        return value <= param[0];
      },
      message: '数字最大为{0}'
    },
    range : {
      validator: function (value,param) {
        if(param[2]) $rules.range.message = param[2];
        return  value >= param[0] && value <= param[1];
      },
      message: '数字只能在{0}与{1}之间'
    },
    rangeNum : {
      validator: function (value,param) {//0,9999999999.999999,6,false,false,message  param[0]-param[1]:数据范围，param[2]:小数位，param[3]:不能等于，只能大于 param[4]:不能等于，只能小于
        if(param[5]) $rules.rangeNum.message = param[5];
         var r1,value = value+'';
        if(!param[3]&&!param[4]){
            r1 = value >= param[0] && value <= param[1];
        }
        if(param[3]&&!param[4]){
            r1 = value > param[0] && value <= param[1];
        }
        if(!param[3]&&param[4]){
            r1 = value >= param[0] && value < param[1];
        }
        if(param[3]&&param[4]){
            r1 = value > param[0] && value < param[1];
        }
        var r2 = /^([1-9]\d+\.\d+)|([0-9]\.\d+)$/.test(value);
        var r3 = true,r4 = true;
        if(r2){
          r3 = (value+'').split('.')[1].length<=param[2];
        }else{
          r4 = value.charAt(value.length - 1)!=='.';
        }
        return  r1&&r3&&r4;
      },
      message: '只能为{0}与{1}之间小于{2}位小数位的数字'

    },
    precision : {
      validator: function (value,param) {
        if(param[1]) $rules.precision.message = param[1];
        var r2 = false;
        var r = /^([1-9]\d+\.\d+)|(0\.\d+)$/.test(value);
        if(r){
          r2 = (value+'').split('.')[1].length<=param[0];
        }else{
          return true;
        }
        return r2;
      },
      message: '最大{0}位小数'
    },
    //  只允许输入英文字母或数字
    username : {
      validator: function (value,param) {
        if(param) $rules.username.message = param[0];
        return /^[0-9a-zA-Z_]{1,}$/.test(value);
      },
      message: '请输入字母、数字或下划线'
    },
    normalPass : {
      validator: function (value,param) {
          if(param) $rules.normalPass.message = param[0];
          return /^(?=.*?[a-zA-Z])(?=.*?\d).*$/.test(value);
      },
      message: '须含字母及数字'
    },
    complexPass : {
      validator: function (value,param) {
          if(param) $rules.complexPass.message = param[0];
          return /^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\d)(?=.*?[-`=\\\[\];',./~!@#$%^&*()_+|{}:"<>?]).*$/.test(value);
      },
      message: '须含大小写字母、数字及特殊字符'
    },
    cn : {
      validator: function (value,param) {
          if(param) $rules.cn.message = param[0];
          return /^[\u0391-\uFFE5]+$/.test(value);
      },
      message: '请输入中文'
    },
    en : {// 判断英文字符
      validator: function (value,param) {
        if(param&&param[0]) $rules.isEnglish.message = param[0];
        return /^[A-Za-z]+$/.test(value);
      },
      message: '只能包含英文字符'
    },
    engNum: {
      validator: function (value,param) {
        if(param&&param[0]) $rules.engNum.message = param[0];
        return /^[0-9a-zA-Z]*$/.test(value);
      },
      message: '请输入英文字母或数字'
    },
    //  只允许汉字、英文字母或数字
    chsEngNum: {
      validator: function (value, param) {
          if(param&&param[0]) $rules.engNum.message = param[0];
          return /^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]|[a-zA-Z0-9])*$/.test(value);
      },
      message: '只允许汉字、英文字母或数字。'
    },
    //  只允许汉字、英文字母或数字
    chsEngNumB: {
      validator: function (value, param) {
        if(param&&param[0]) $rules.engNum.message = param[0];
        return /^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]|[a-zA-Z0-9])*$/.test(value) && !(/^([0-9]\d*)$/.test(value));
      },
      message: '只能为汉字、字母或数字且不能为纯数字。'
    },
    number : {// 匹配数值类型，包括整数和浮点数
      validator: function (value,param) {
        if(param&&param[0]) $rules.number.message = param[0];
        return /^[-\+]?\d+$/.test(value) || /^[-\+]?\d+(\.\d+)?$/.test(value);
      },
      message: '请输入正确的数字'
    },
    pNumber : {
      validator: function (value,param) {
          if(param) $rules.pNumber.message = param[0];
          return /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/.test(value);
      },
      message: '请输入一个正数'
    },
    sNumber : {
      validator: function (value,param) {
          if(param) $rules.sNumber.message = param[0];
          var r = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/.test(value);
          return r&&(value>0);
      },
      message: '请输入一个大于0的正数'
    },
    pInt : {
      validator: function (value,param) {
          if(param) $rules.pInt.message = param[0];
          return /^(0|[1-9]\d*)$/.test(value);
      },
      message: '请输入一个正整数'
    },
    sInt : {
      validator: function (value,param) {
          if(param) $rules.sInt.message = param[0];
          return /^([1-9]\d*)$/.test(value);
      },
      message: '请输入一个大于0的正整数'
    },
    jInt : {
      validator: function (value,param) {
        if(param) $rules.jInt.message = param[0];
        return /^([0-9]\d*)$/.test(value);
      },
      message: '请输入一个整数'
    },
    int : {
      validator: function (value,param) {
          if(param) $rules.int.message = param[0];
          return /^-?\d+$/.test(value);
      },
      message: '请输入一个整数'
    },
    nInt : {
      validator: function (value,param) {
          if(param) $rules.nInt.message = param[0];
          return /^-\d+$/.test(value);
      },
      message: '请输入一个负整数'
    },
    unZeroInt : {//非零整数
      validator: function (value,param) {
          if(param) $rules.unZeroInt.message = param[0];
          return /^-?\d+$/.test(value)&&value*1!==0;
      },
      message: '请输入一个非零的整数'
    },
    float : {// 判断浮点型
      validator: function (value,param) {
        if(param&&param[0]) $rules.float.message = param[0];
        return /^[-\+]?\d+(\.\d+)?$/.test(value);
      },
      message: '请输入正确的小数'
    },
    floatNEqZero : {// 判断浮点数value是否不等于0
      validator: function (value,param) {
        if(param&&param[0]) $rules.floatNEqZero.message = param[0];
        var value = parseFloat(value);
        return value!=0;
      },
      message: '浮点数不可以等于0'
    },
    floatLtZero : {
     validator: function (value,param) {
       if(param&&param[0]) $rules.floatLtZero.message = param[0];
       var value = parseFloat(value);
       return value<0;
     },
     message: '浮点数必须小于0'
   },
   floatLteZero : { // 判断浮点数value是否小于或等于0
    validator: function (value,param) {
      if(param&&param[0]) $rules.floatLteZero.message = param[0];
      var value = parseFloat(value);
      return value<=0;
    },
    message: '浮点数必须小于或等于0'
  },
  isFloatGteZero : {
    validator: function (value,param) {
        if(param) $rules.isFloatGteZero.message = param[0];
        return /^\d+(\.\d+)?$/.test(value);
    },
    message: '浮点数必须大于或等于0'
  },
  pFloatFix : {
    validator: function (value,param) {
      if(param&&param[1]) $rules.pFloatFix.message = param[1];
      var state = /^\d+(\.\d+)?$/.test(value);
      if(value&&state){
        var _self = $(this);
        if(_self instanceof Array){_self = _self[0]};
        var opt = param[0] || 2;
        _self.blur(function () {
         _self.val(new Number(value).toFixed(opt)).unbind('blur');
        });
      }
      return state;
    },
    message: '请填写正确的正数'
  },
  floatFix : {
    validator: function (value,param) {
      if(param&&param[1]) $rules.floatFix.message = param[1];
      var state = /^[-\+]?\d+(\.\d+)?$/.test(value);
      if(value&&state){
        var _self = $(this);
        if(_self instanceof Array){_self = _self[0]};
        var opt = param[0] || 2;
        _self.blur(function () {
         _self.val(new Number(value).toFixed(opt)).unbind('blur');
        });
      }
      return state;
    },
    message: '请填写正确的正数'
  },
  diymonth : {
    validator: function (value,param) {
        if(param) $rules.diymonth.message = param[0];
        return /^[0-9]+(\.[0-9]{1})?$/.test(value);
    },
    message: '月数为正整数或一位小数'
  },
  commonDate : {
    validator: function (value,param) {
        if(value==''){return true;}
        if(param) $rules.commonDate.message = param[0];
        return /^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])( (([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])(:([0-5]?[0-9]))?)?$/.test(value);
    },
    message: '日期格式如：1980-01-01 12:12:01'
  },
  dateTime : {
    validator: function (value,param) {
        if(param) $rules.dateTime.message = param[0];
        return /^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01]) (([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])(:([0-5]?[0-9]))?$/.test(value);
    },
    message: '日期格式如：1980-01-01 12:12:01'
  },
    dateTimeSecond : {
      validator: function (value,param) {
        if(param) $rules.dateTime.message = param[0];
        return /^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01]) (([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9]):([0-5]?[0-9])$/.test(value);
      },
      message: '日期格式如：1980-01-01 12:12:01'
    },
  normalDate : {
    validator: function (value,param) {
        if(param) $rules.normalDate.message = param[0];
        return /^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/.test(value);
    },
    message: '日期格式如：1980-01-01'
  },
  minDate : {
    validator: function (value,param) {
      if(value==''){return true;}
      var v = value.replace(/-/g, '/');
      if(param[0] =='now'){
        d = new Date().getTime();
        $rules.minDate.message = '日期不能小于现在';
      }else if(param[0] =='today'){
        d = new Date().getTime()-86400000;
        $rules.minDate.message = '日期不能小于今天';
      }else{
        d = new Date(param[0]).getTime();
      }
      param[1]&&($rules.minDate.message = param[1]);
      return (new Date(v).getTime() - d)>0;
    },
    message: '日期不能小于{0}'
  },
  maxDate : {
    validator: function (value,param) {
        if(value==''){return true;}
        if(param[0] =='now'){
          d = new Date().getTime();
          $rules.maxDate.message = '日期不能大于现在';
        }else if(param[0] =='today'){
          d = new Date().getTime();
          $rules.maxDate.message = '日期不能大于今天';
        }else{
          d = new Date(param[0]).getTime();
        }
        param[1]&&($rules.maxDate.message = param[1]);
        return (new Date(value).getTime() - d)<0;
    },
    message: '日期不能大于{0}'
  },
  age : {
    validator: function (value,param) {
      if(param&&param[0]) $rules.age.message = param[0];
      return /^[-\+]?\d+$/.test(value) && parseInt(value)>0&&parseInt(value)<120;
    },
    message: '请输入正确的年龄'
  },
  digits : {// 只能输入[0-9]数字
    validator: function (value,param) {
      if(param&&param[0]) $rules.digits.message = param[0];
      return /^\d+$/.test(value);
    },
    message: '只能输入0-9数字'
  },
  mobile : {// 判断英文字符
    validator: function (value) {
      return /^1[0-9]{10}$/.test(value);
    },
    message: '请填写正确的手机号码'
  },
  phone : {// 电话号码验证
    validator: function (value) {
      var tel = /^(\d{3,4}-?)?\d{7,9}$/g;
      return tel.test(value);
    },
    message: '请填写正确的电话号码'
  },
  phoneB : {
      validator: function (value,param) {
        if(param&&param[0]) $rules.phoneB.message = param[0];
        var tel = /^[0-9\-]{7,20}$/g;
        return tel.test(value) || $.trim(value) == '-';
      },
      message: '电话号码为7-20位的数字'
  },
  phoneArea : {
    validator: function (value) {
      var tel = /^[08]\d{2,3}$/g;
      return tel.test(value);
    },
    message: '请填写正确的区号'
  },
  phoneNumber : {
    validator: function (value) {
      var tel = /^\d{7,9}$/g;
      return tel.test(value);
    },
    message: '请填写正确的电话号码'
  },
  tel : {// 联系电话(手机/电话皆可)验证
    validator: function (value) {
      var length = value.length;
      var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
      var tel = /^(\d{3,4}-?)?\d{7,9}$/g;
      return tel.test(value) || (length==11 && mobile.test(value));
    },
    message: '请填写正确的联系方式'
  },
  qq : {//匹配qq
    validator: function (value,param) {
      if(param&&param[0]) $rules.qq.message = param[0];
      return /^[1-9]\d{4,12}$/.test(value);
    },
    message: '请填写正确的QQ号码'
  },
  zipCode : {//邮政编码验证
    validator: function (value,param) {
      if(param&&param[0]) $rules.zipCode.message = param[0];
      var zip = /^[0-9]{6}$/;
      return zip.test(value);
    },
    message: '请填写正确的邮政编码'
  },
    zipCodeB : {//邮政编码验证
      validator: function (value,param) {
        if(param&&param[0]) $rules.zipCodeB.message = param[0];
        var zip = /^[0-9]{6}$/;
        return zip.test(value) || $.trim(value) == '-';
      },
      message: '请填写正确的邮政编码'
    },
  ip : {
    validator: function (value,param) {
        if(param) $rules.ip.message = param[0];
        return /^[0-2]?[0-9]?[0-9]\.[0-2]?[0-9]?[0-9]\.[0-2]?[0-9]?[0-9]\.[0-2]?[0-9]?[0-9]$/.test(value);
    },
    message: '请输入合法的IP'
  },
  idCardNo : {//身份证号码验证
    validator: function (value,param) {
      if(param&&param[1]) $rules.idCardNo.message = param[1];
      var exParam = (param&&param[0]) || '';
      return isIdCardNo(value,exParam);
    },
    message: '请填写正确的身份证号码'
  },
  containsSpecialChar : {//判断是否包含中英文特殊字符，除英文"-_"字符外
    validator: function (value,param) {
      if(param&&param[0]) $rules.containsSpecialChar.message = param[0];
      var reg = RegExp(/[(\ )(\`)(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\+)(\=)(\|)(\{)(\})(\')(\:)(\;)(\')(',)(\[)(\])(\.)(\<)(\>)(\/)(\?)(\~)(\！)(\@)(\#)(\￥)(\%)(\…)(\&)(\*)(\（)(\）)(\—)(\+)(\|)(\{)(\})(\【)(\】)(\‘)(\；)(\：)(\”)(\“)(\’)(\。)(\，)(\、)(\？)]+/);
      return reg.test(value);
    },
    message: '含有中英文特殊字符'
  },
  //  只允许汉字、英文字母、数字及下划线
  code: {
    validator: function (value, param) {
        return /^[\u0391-\uFFE5\w]+$/.test(value);
    },
    message: '只允许汉字、英文字母、数字及下划线.'
  },
  //  输入的字符必须是指定的内容相同
  numEquals : {
    validator: function (value, param) {
      if(param[1]) $rules.numEquals.message = param[1];
      return value*10000 == $(param[0]).val()*10000;
    },
    message: "输入的内容不匹配."
  },
  equals: {
    validator: function (value, param) {
      if(param[1]) $rules.equals.message = param[1];
      return value == $(param[0]).val();
    },
    message: "输入的内容不匹配."
  }
});

})(jQuery);


//身份证号码的验证规则
function isIdCardNo(code,opt){
  var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
  var tip = "";
  var pass= true;

  // if(!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)){
    // if (!code || !/^[1-9][0-9]{5}(19[0-9]{2}|200[0-9]|2010)(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])[0-9]{3}[0-9xX]$/i.test(code)) {
    if (!code || !/^[1-9]\d{5}((1[89]|20)\d{2})(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{3}[\dx]$/i.test(code)) {
      tip = "身份证号格式错误";
      pass = false;
  }

  else if(!city[code.substr(0,2)]){
      tip = "地址编码错误";
      pass = false;
  }
  else{
      //18位身份证需要验证最后一位校验位
      if(code.length == 18){
          codeArr = code.split('');
          //∑(ai×Wi)(mod 11)
          //加权因子
          var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
          //校验位
          var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
          var sum = 0;
          var ai = 0;
          var wi = 0;
          for (var i = 0; i < 17; i++)
          {
              ai = codeArr[i];
              wi = factor[i];
              sum += ai * wi;
          }
          var last = parity[sum % 11];
          if(parity[sum % 11] != codeArr[17]){
              tip = "校验位错误";
              pass =false;
          }
      }
  }
  // window.console && console.log(pass,tip);

  if (opt) {
    var optArr = opt.split(',');
    var $bir = $('#'+optArr[0]);
    var $sex = $('#'+optArr[1]);
    var $age = $('#'+optArr[2]);
    if (pass) {
      var birthday = GetBirthday(code,optArr[2],optArr[3]);
      $bir.easyVal(birthday);
      $sex.easyVal(Getsex(code));
    }else{
      $bir.easyVal('');
      $sex.easyVal('');
      $age.easyVal('');
    };
  };
  return pass;
}

function GetBirthday(psidno,ageboxId,endDatebox){
  var birthdayno,birthdaytemp;
  if(psidno.length==18){
      birthdayno=psidno.substring(6,14)
  }else if(psidno.length==15){
      birthdaytemp=psidno.substring(6,12)
      birthdayno="19"+birthdaytemp
  }else{
      window.console&&console.log("错误的身份证号码，请核对！")
      return false
  }
  var birY = birthdayno.substring(0,4);
  var birM = birthdayno.substring(4,6);
  var birD = birthdayno.substring(6,8);
  var birthday=birY+"-"+birM+"-"+birD;
  var now = new Date();
  now =  $.getFullDate(now);
  if(endDatebox && $(endDatebox).length && $(endDatebox).easyVal()){//如果有结束日期输入框
    now = $(endDatebox).easyVal();
  }
  // window.console && console.log(now);
  now = now.split(' ')[0].split('-');
  // var age = (now[0]-birY)*100+(now[1]-birM)/12*10*10+(now[2]-birD)/30*10;
  var age = now[0]-birY-1;
  if(birM < now[1] || birM == now[1] && birD <= now[2]){//(生日 月小于当前月  || 月相同时，生日 日 小于等于当前日)+1岁
    age++;
  }
  age = age <= 0?1:age;
  $('#'+ageboxId).easyVal(age);
  return birthday;
}

function Getsex(psidno){
  var sexno,sex;
  if(psidno.length==18){
      sexno=psidno.substring(16,17)
  }else if(psidno.length==15){
      sexno=psidno.substring(14,15)
  }else{
      window.console&&console.log("错误的身份证号码，请核对！")
      return false
  }
  var tempid=sexno%2;
  sex = (tempid==0)?'2':'1';
  return sex;
}
