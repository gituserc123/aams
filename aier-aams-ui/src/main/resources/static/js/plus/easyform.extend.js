/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI validatebox 扩展
* jeasyui.extensions.validatebox.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-10-28
*
* 依赖项：
*   1、jquery.jdirk.js
*   2、jeasyui.extensions.validatebox.css
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function ($, undefined) {

    $.util.namespace("$.fn.validatebox.extensions");


    function setPrompt(target, prompt) {
        var t = $(target),
            state = $.data(target, "validatebox"),
            opts = state.options;
        opts.prompt = prompt || "";
        t.attr("placeholder", opts.prompt);
        if (!$.html5.testProp("placeholder", t[0].nodeName)) {
            t.unbind("blur.validatebox-extensions focus.validatebox-extensions").bind({
                "focus.validatebox-extensions": function () {
                    t.removeClass("validatebox-prompt");
                    if (t.val() == opts.prompt) {
                        t.val("");
                    }
                },
                "blur.validatebox-extensions": function () {
                    if (!t.val() && opts.prompt) {
                        t.addClass("validatebox-prompt").val(opts.prompt);
                    } else {
                        t.removeClass("validatebox-prompt");
                    }
                }
            }).trigger(t.is(":focus") ? "focus" : "blur");
        }
    };

    function setValue(target, value) {
        var t = $(target),
            state = $.data(target, "validatebox"),
            opts = state.options,
            val = t.val();
        if (val != value) {
            t.val(opts.value = (value ? value : ""));
        }
        t.validatebox("validate");
    };

    function getValue(target) {
        return $(target).val();
    };

    function clear(target) {
        $(target).validatebox("setValue", "");
    };

    function reset(target) {
        var t = $(target),
            state = $.data(target, "validatebox"),
            opts = state.options;
        t.validatebox("setValue", opts.originalValue ? opts.originalValue : "");
    };

    function resize(target, width) {
        var t = $(target),
            state = $.data(target, "validatebox"),
            opts = state.options;
        t._outerWidth(opts.width = width);
    };

    function setDisabled(target, disabled) {
        var t = $(target),
            state = $.data(target, "validatebox");
        if (disabled) {
            if (state && state.options) { state.options.disabled = true; }
            t.attr("disabled", true);
        } else {
            if (state && state.options) { state.options.disabled = false; }
            t.removeAttr("disabled");
        }
    };

    function setReadonly(target, readonly) {
        var t = $(target),
            state = $.data(target, "validatebox");
        readonly = readonly == null || readonly == undefined ? true : !!readonly;

        if (state && state.options) {
            state.options.readonly = readonly;
        }
        if (readonly) {
            t.addClass("validatebox-readonly").attr("readonly", true);
        } else {
            t.removeClass("validatebox-readonly").removeAttr("readonly");
        }
    };





    var _validatebox = $.fn.validatebox;
    $.fn.validatebox = function (options, param) {
        if (typeof options == "string") {
            return _validatebox.apply(this, arguments);
        }
        options = options || {};
        return this.each(function () {
            var t = $(this),
                isInited = $.data(this, "validatebox") ? true : false,
                opts = isInited ? options : $.extend({},
                    $.fn.validatebox.parseOptions(this),
                    $.parser.parseOptions(this,
                        ["prompt", "width", "cls", { autoFocus: "boolean", autoValidate: "boolean" }]
                    ),
                    {
                        value: (t.val() || undefined),
                        disabled: (t.attr("disabled") ? true : undefined),
                        readonly: (t.attr("readonly") ? true : undefined)
                    },
                    options
                );
            _validatebox.call(t, opts);
            if (!isInited) {
                initialize(this);
            }
        });
    };
    $.union($.fn.validatebox, _validatebox);


    function initialize(target) {
        var t = $(target),
            state = $.data(target, "validatebox"),
            opts = state.options;
        if (!opts._initialized) {
            t.addClass("validatebox-f").change(function () {
                opts.value = $(this).val();
                if ($.isFunction(opts.onChange)) {
                    opts.onChange.call(target, opts.value);
                }
            });
            opts.originalValue = opts.value;
            if (opts.value) {
                setValue(target, opts.value);
            }
            if (opts.width && !t.parent().is("span.textbox,span.spinner,span.searchbox")) {
                resize(target, opts.width);
            }
            setPrompt(target, opts.prompt);
            if (opts.autoFocus) {
                $.util.delay(function () { t.focus(); });
            }
            if (!opts.autoValidate) {
                t.removeClass("validatebox-invalid");
                hideTip(target);
            }
            if (opts.cls) {
                t.addClass(opts.cls);
            }
            if (opts.editable != undefined && !opts.editable) {
                opts.readonly = true;
            }
            setDisabled(target, opts.disabled);
            setReadonly(target, opts.readonly);
            opts._initialized = true;
        }
    };

    function hideTip(target) {
        var state = $.data(target, "validatebox");
        state.tip = false;
        $(target).tooltip("hide");
    };



    var defaults = $.fn.validatebox.extensions.defaults = {

        // 扩展 easyui-validatebox 控件的自定义属性；该属性功能类似于 easyui-searchbox 的 prompt 属性，表示该验证输入框的提示文本；
        // String 类型值，默认为 null。
        prompt: null,

        // 扩展 easyui-validatebox 控件的自定义属性；该属性表示在当前页面加载完成后，该 easyui-validatebox 控件是否自动获得焦点。
        // Boolean 类型值，默认为 false。
        autoFocus: false,

        // 扩展 easyui-validatebox 控件的自定义属性；表示是否在该控件初始化完成后立即进行一次验证；默认为 true。
        // Boolean 类型值，默认为 true。
        autoValidate: true,

        // 扩展 easyui-validatebox 控件的自定义属性；表示其初始化时的值。
        // String 类型值，默认为 null。
        value: null,

        // 扩展 easyui-validatebox 控件的自定义属性；表示其初始化时的宽度值。
        // Number 类型值，默认为 null。
        width: null,

        // 扩展 easyui-validatebox 控件的自定义属性；表示该控件在初始化完成后是否设置其为禁用状态(disabled)；默认为 false。
        // Boolean 类型值，默认为 false。
        disabled: false,

        // 扩展 easyui-validatebox 控件的自定义属性；表示该控件在初始化完成后是否设置其为只读状态(readonly)；默认为 false。
        // Boolean 类型值，默认为 false。
        readonly: false,

        // 扩展 easyui-validatebox 控件的自定义属性；表示 easyui-validatebox 初始化时默认需要加载的样式类名；
        // String 类型值，默认为 null。
        // 该值将会被作为 html-class 属性在 easyui-validatebox 初始化完成后加载至 html 标签上。
        cls: null,

        // 扩展 easyui-validatebox 控件的自定义扩展事件，表示输入框在值改变时所触发的事件。
        onChange: function (value) { }
    };

    var methods = $.fn.validatebox.extensions.methods = {

        // 扩展 easyui-validatebox 控件的自定义方法；
        // 设置当前 easyui-validatebox 控件的 prompt 值；
        // 该方法的参数 prompt 表示将被设置的 prompt 值；
        // 返回值：返回表示当前 easyui-validatebox 控件的 jQuery 链式对象；
        setPrompt: function (jq, prompt) {
            return jq.each(function () { setPrompt(this, prompt); });
        },

        // 扩展 easyui-validatebox 控件的自定义方法；
        // 设置当前 easyui-validatebox 控件的输入值。
        // 该方法的参数 value 表示要被设置的值；为一个 String 类型值。
        // 返回值：返回表示当前 easyui-validatebox 控件的 jQuery 链式对象；
        setValue: function (jq, value) {
            return jq.each(function () { setValue(this, value); });
        },

        // 扩展 easyui-validatebox 控件的自定义方法；
        // 获取当前 easyui-validatebox 控件的输入值。
        // 返回值：返回当前 easyui-validatebox 控件输入值；
        getValue: function (jq) {
            return getValue(jq[0]);
        },

        // 扩展 easyui-validatebox 控件的自定义方法；
        // 清除当前 easyui-validatebox 控件的输入值，使其为空。
        // 返回值：返回表示当前 easyui-validatebox 控件的 jQuery 链式对象；
        clear: function (jq) {
            return jq.each(function () { clear(this); });
        },

        // 扩展 easyui-validatebox 控件的自定义方法；
        // 重置当前 easyui-validatebox 控件的输入值，使其为初始化时的值。
        // 返回值：返回表示当前 easyui-validatebox 控件的 jQuery 链式对象；
        reset: function (jq) {
            return jq.each(function () { reset(this); });
        },

        // 扩展 easyui-validatebox 控件的自定义方法；
        // 设置当前 easyui-validatebox 控件的宽度（width）值。
        // 该方法的参数 width 表示将被设置的宽度（width）值；为一个 Number 类型值；该参数可选。
        // 返回值：返回表示当前 easyui-validatebox 控件的 jQuery 链式对象；
        resize: function (jq, width) {
            return jq.each(function () { resize(this, width); });
        },

        // 扩展 easyui-validatebox 控件的自定义方法；
        // 启用当前 easyui-validatebox 控件的输入状态。
        // 返回值：返回表示当前 easyui-validatebox 控件的 jQuery 链式对象；
        enable: function (jq) {
            return jq.each(function () { setDisabled(this, false); });
        },

        // 扩展 easyui-validatebox 控件的自定义方法；
        // 禁用当前 easyui-validatebox 控件的输入状态。
        // 返回值：返回表示当前 easyui-validatebox 控件的 jQuery 链式对象；
        disable: function (jq) {
            return jq.each(function () { setDisabled(this, true); });
        },

        // 扩展 easyui-validatebox 控件的自定义方法；
        // 设置当前 easyui-validatebox 控件的为只读状态。
        // 该方法的参数 readonly 是一个可选的 bool 类型值，默认值为 true，表示启用或禁用该控件的只读状态。
        // 返回值：返回表示当前 easyui-validatebox 控件的 jQuery 链式对象；
        readonly: function (jq, readonly) {
            return jq.each(function () { setReadonly(this, readonly); });
        }
    };

    $.extend($.fn.validatebox.defaults, defaults);
    $.extend($.fn.validatebox.methods, methods);



    // 修改 jQuery 本身的成员方法 val；使之支持 easyui-validatebox 的扩展属性 prompt。
    var core_val = $.fn.val;
    $.fn.val = function (value) {
        if (this.length && this.is(".validatebox-text.validatebox-prompt") && !$.html5.testProp("placeholder", this[0].nodeName)) {
            var val, opts = this.validatebox("options");
            if (arguments.length == 0) {
                val = core_val.apply(this, arguments);
                return val == opts.prompt ? "" : val;
            }
            if (value && value != opts.prompt) {
                this.removeClass("validatebox-prompt");
            }
        }
        return core_val.apply(this, arguments);
    };


})(jQuery);


/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI validatebox 扩展
* jeasyui.extensions.validatebox.rules.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-10-27
*
* 依赖项：
*   1、jquery.jdirk.js
*   2、jeasyui.extensions.validatebox.css
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function () {

    $.util.namespace("$.fn.validatebox.extensions");

    $.extend($.fn.validatebox.extensions, {
        validate: $.fn.validatebox.methods.isValid,
        rules: {
            //  只允许输入英文字母或数字
            engNum: {
                validator: function (value) {
                    return /^[0-9a-zA-Z]*$/.test(value);
                },
                message: '请输入英文字母或数字'
            },
            //  只允许汉字、英文字母或数字
            chsEngNum: {
                validator: function (value, param) {
                    return /^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]|[a-zA-Z0-9])*$/.test(value);
                },
                message: '只允许汉字、英文字母或数字。'
            },
            //  只允许汉字、英文字母、数字及下划线
            code: {
                validator: function (value, param) {
                    return /^[\u0391-\uFFE5\w]+$/.test(value);
                },
                message: '只允许汉字、英文字母、数字及下划线.'
            },
            //  验证是否为合法的用户名
            name: {
                validator: function (value) { return value.isUserName(); },
                message: "用户名不合法(字母开头，允许6-16字节，允许字母数字下划线)"
            },
            //  指定字符最小长度
            minLength: {
                validator: function (value, param) { return $.string.trim(value).length >= param[0]; },
                message: "最少输入 {0} 个字符."
            },
            //  指定字符最大长度
            maxLength: {
                validator: function (value, param) { return $.string.trim(value).length <= param[0]; },
                message: "最多输入 {0} 个字符."
            },
            //  必须包含指定的内容
            contains: {
                validator: function (value, param) { return $.string.contains(value, param[0]); },
                message: "输入的内容必须包含 {0}."
            },
            //  以指定的字符开头
            startsWith: {
                validator: function (value, param) { return $.string.startsWith(value, param[0]); },
                message: "输入的内容必须以 {0} 作为起始字符."
            },
            //  以指定的字符结束
            endsWith: {
                validator: function (value, param) { return $.string.endsWith(value, param[0]); },
                message: "输入的内容必须以 {0} 作为结尾字符."
            },
            //  长日期时间(yyyy-MM-dd hh:mm:ss)格式
            longDate: {
                validator: function (value) { return $.string.isLongDate(value); },
                message: "输入的内容必须是长日期时间(yyyy-MM-dd hh:mm:ss)格式."
            },
            //  短日期(yyyy-MM-dd)格式
            shortDate: {
                validator: function (value) { return $.string.isShortDate(value); },
                message: "输入的内容必须是短日期(yyyy-MM-dd)格式."
            },
            //  长日期时间(yyyy-MM-dd hh:mm:ss)或短日期(yyyy-MM-dd)格式
            date: {
                validator: function (value) { return $.string.isDate(value); },
                message: "输入的内容必须是长日期时间(yyyy-MM-dd hh:mm:ss)或短日期(yyyy-MM-dd)格式."
            },
            //  电话号码(中国)格式
            tel: {
                validator: function (value) { return $.string.isTel(value); },
                message: "输入的内容必须是电话号码(中国)格式."
            },
            //  移动电话号码(中国)格式
            mobile: {
                validator: function (value) { return $.string.isMobile(value); },
                message: "输入的内容必须是移动电话号码(中国)格式."
            },
            //  电话号码(中国)或移动电话号码(中国)格式
            telOrMobile: {
                validator: function (value) { return $.string.isTelOrMobile(value); },
                message: "输入的内容必须是电话号码(中国)或移动电话号码(中国)格式."
            },
            //  传真号码(中国)格式
            fax: {
                validator: function (value) { return $.string.isFax(value); },
                message: "输入的内容必须是传真号码(中国)格式."
            },
            //  邮政编码(中国)格式
            zipCode: {
                validator: function (value) { return $.string.isZipCode(value); },
                message: "输入的内容必须是邮政编码(中国)格式."
            },
            //  必须包含中文汉字
            existChinese: {
                validator: function (value) { return $.string.existChinese(value); },
                message: "输入的内容必须是包含中文汉字."
            },
            //  必须是纯中文汉字
            chinese: {
                validator: function (value) { return $.string.isChinese(value); },
                message: "输入的内容必须是纯中文汉字."
            },
            //  必须是纯英文字母
            english: {
                validator: function (value) { return $.string.isEnglish(value); },
                message: "输入的内容必须是纯英文字母."
            },
            //  必须是合法的文件名(不能包含字符 \\/:*?\"<>|)
            fileName: {
                validator: function (value) { return $.string.isFileName(value); },
                message: "输入的内容必须是合法的文件名(不能包含字符 \\/:*?\"<>|)."
            },
            //  必须是正确的 IP地址v4 格式
            ip: {
                validator: function (value) { return $.string.isIPv4(value); },
                message: "输入的内容必须是正确的 IP地址v4 格式."
            },
            //  必须是正确的 url 格式
            url: {
                validator: function (value) { return $.string.isUrl(value); },
                message: "输入的内容必须是正确的 url 格式."
            },
            //  必须是正确的 IP地址v4 或 url 格式
            ipurl: {
                validator: function (value) { return $.string.isUrlOrIPv4(value); },
                message: "输入的内容必须是正确的 IP地址v4 或 url 格式."
            },
            //  必须是正确的货币金额(阿拉伯数字表示法)格式
            currency: {
                validator: function (value) { return $.string.isCurrency(value); },
                message: "输入的内容必须是正确的货币金额(阿拉伯数字表示法)格式."
            },
            //  必须是正确 QQ 号码格式
            qq: {
                validator: function (value) { return $.string.isQQ(value); },
                message: "输入的内容必须是正确 QQ 号码格式."
            },
            //  必须是正确 MSN 账户名格式
            msn: {
                validator: function (value) { return $.string.isMSN(value); },
                message: "输入的内容必须是正确 MSN 账户名格式."
            },
            unNormal: {
                validator: function (value) { return $.string.isUnNormal(value); },
                message: "输入的内容必须是不包含空格和非法字符Z."
            },
            //  必须是合法的汽车车牌号码格式
            carNo: {
                validator: function (value) { return $.string.isCarNo(value); },
                message: "输入的内容必须是合法的汽车车牌号码格式."
            },
            //  必须是合法的汽车发动机序列号格式
            carEngineNo: {
                validator: function (value) { return $.string.isCarEngineNo(value); },
                message: "输入的内容必须是合法的汽车发动机序列号格式."
            },
            //  必须是合法的身份证号码(中国)格式
            idCard: {
                validator: function (value) { return $.string.isIDCard(value); },
                message: "输入的内容必须是合法的身份证号码(中国)格式."
            },
            //  必须是合法的整数格式
            integer: {
                validator: function (value) { return $.string.isInteger(value); },
                message: "输入的内容必须是合法的整数格式."
            },
            //  必须是合法的整数格式且值介于 {0} 与 {1} 之间
            integerRange: {
                validator: function (value, param) {
                    return $.string.isInteger(value) && ((param[0] && value >= param[0]) && (param[1] && value <= param[1]));
                },
                message: "输入的内容必须是合法的整数格式且值介于 {0} 与 {1} 之间."
            },
            //  必须是指定类型的数字格式
            numeric: {
                validator: function (value, param) { return $.string.isNumeric(value, param ? param[0] : undefined); },
                message: "输入的内容必须是指定类型的数字格式."
            },
            //  必须是指定类型的数字格式且介于 {0} 与 {1} 之间
            numericRange: {
                validator: function (value, param) {
                    return $.string.isNumeric(value, param ? param[2] : undefined) && ((param[0] || value >= param[0]) && (param[1] || value <= param[1]));
                },
                message: "输入的内容必须是指定类型的数字格式且介于 {0} 与 {1} 之间."
            },
            //  必须是正确的 颜色(#FFFFFF形式) 格式
            color: {
                validator: function (value) { return $.string.isColor(value); },
                message: "输入的内容必须是正确的 颜色(#FFFFFF形式) 格式."
            },
            //  必须是安全的密码字符(由字符和数字组成，至少 6 位)格式
            password: {
                validator: function (value) { return $.string.isSafePassword(value); },
                message: "输入的内容必须是安全的密码字符(由字符和数字组成，至少 6 位)格式."
            },
            //  必须是安全的密码字符(由字符或数字或特殊字符(~!@#$%^&*.)组成， 6-16 位)格式
            password2: {
                validator: function (value) {
                    str = $.string.isNullOrEmpty(value) ? "" : String(value);
                    return (/^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{6,16}$/.test(str));
                },
                message: "输入的内容必须是安全的密码字符(由字符、数字或特殊字符(~!@#$%^&*.)组成，6 至 16 位)格式."
            },
            //  输入的字符必须是指定的内容相同
            equals: {
                validator: function (value, param) {
                    var val = param[0], type = param[1];
                    if (type) {
                        switch (String(type).toLowerCase()) {
                            case "jquery":
                            case "dom":
                                val = $(val).val();
                                break;
                            case "id":
                                val = $("#" + val).val();
                                break;
                            case "string":
                            default:
                                break;
                        }
                    }
                    return value === val;
                },
                message: "输入的内容不匹配."
            }
        }
    });

    $.extend($.fn.validatebox.defaults.rules, $.fn.validatebox.extensions.rules);

})(jQuery);


/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI validatebox 扩展
* jeasyui.extensions.validatebox.setRequired.js
* 开发 落阳
* 最近更新：2015-10-29
*
* 依赖项：
*   1、jquery.jdirk.js
*   2、jeasyui.extensions.validatebox.css
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.validatebox.extensions");

    function setRequired(target, required) {
        var state = $.data(target, "validatebox"),
            opts = state.options;
        opts.required = required;
        $(target).validatebox("validate");
    }

    var defaults = $.fn.validatebox.extensions.defaults = {

    };

    var methods = $.fn.validatebox.extensions.methods = {

        // 扩展 easyui-validatebox 控件的自定义方法；设置 easyui-validatebox 控件的 required 属性；该属性表示表单输入值是否允许为空。
        // 该方法的参数 required 表示被设置的 bool 类型值。
        // 返回值：返回表示当前 easyui-validatebox 控件的 jQuery 链式对象。
        setRequired: function (jq, required) { return jq.each(function () { setRequired(this, required); }); }
    };

    $.extend($.fn.validatebox.defaults, defaults);
    $.extend($.fn.validatebox.methods, methods);

})(jQuery);

/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI validatebox 扩展
* jeasyui.extensions.validatebox.rules.remote.js
* 开发 落阳
* 最近更新：2015-11-08
*
* 依赖项：
*   1、jquery.jdirk.js
*   2、jeasyui.extensions.validatebox.css
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.validatebox.extensions");

    $.extend($.fn.validatebox.extensions, {
        validate: $.fn.validatebox.methods.isValid,
        rules: {
            // 访问远程地址进行验证；参数 param 中不传入查询的目标字段名称，只传入查询的目标字段的值，后台的参数名只能是 Value 和 Key 。
            // 该规则可用于常规的重复性检验，如“判定用户名是否重复”。
            // param参数对象中各参数次序：
            //      0:string 格式，表示正在校验的内容名称，如“用户名”、“账号”
            //      1:string 格式，表示提供验证的远程地址
            //      2:string 格式，表示要排除校验的主键值（如正在编辑的数据的主键值） 或 该主键值所在的控件的 ID；不传则默认为空值
            //      3:string 格式，表示 param[2] 所表示内容的类型，其值可以是“string”或“id”；不传则默认为 string
            remoteValidWithoutField: {
                validator: function (value, param) {
                    var url = param[1];         //提供验证的远程地址
                    var va = value;             //当前控件的值
                    var type = param.length > 3 ? param[3] : "string";
                    var key = param.length > 2 ? (type == "id" ? $("#" + param[2]).val() : param[2]) : "";         //要排除校验的主键值
                    var pa = {
                        Key: key,
                        Value: va
                    };
                    var result = $.util.requestAjaxBoolean(url, pa);
                    return !result;
                },
                message: '{0}有重复记录'
            }
        }
    });

    $.extend($.fn.validatebox.defaults.rules, $.fn.validatebox.extensions.rules);

})(jQuery);



/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI validatebox 扩展
* jeasyui.extensions.validatebox.updateRules.js
* 开发 落阳
* 最近更新：2016-03-10
*
* 依赖项：
*   1、jquery.jdirk.js
*   2、jeasyui.extensions.rules.js（可以不依赖）
*
* Copyright (c) 2016 Lixilin personal All rights reserved.
*/
(function ($, undefined) {

    $.util.namespace("$.fn.validatebox.extensions");

    var rules = {

        // 强行验证失败，有些特殊的情况下需要用到。结合扩展方法“addValidType、removeValidType”使用。
        forceValidFail: {
            validator: function (value, param) {
                var result = param[1];
                return result;
            },
            message: "{0}"
        }
    };
    $.extend($.fn.validatebox.defaults.rules, rules);


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

    function addValidType(target, rs) {
        var currentRules = [];
        if ($.util.isString(rs)) { currentRules.push(rs); }
        else if ($.util.isArray(rs)) { currentRules = rs; }
        else { return; }
        if (currentRules.length == 0) { return; }

        var thisRules = getEffectValidType(currentRules);
        if (thisRules.length == 0) { return; }

        var t = $(target), opts = t.validatebox("options");
        var _validType = opts.validType ? ($.array.likeArrayNotString(opts.validType) ? opts.validType : [opts.validType]) : [];
        if (_validType.length && _validType.length >= 0) {
            thisRules.forEach(function (rule) {
                _validType.push(rule);
            });
        }
        else {
            _validType = thisRules;
        }
        opts.validType = _validType;
        t.validatebox("validate");
    };

    function removeValidType(target, rs) {
        var currentRules = [];
        if ($.util.isString(rs)) { currentRules.push(rs); }
        else if ($.util.isArray(rs)) { currentRules = rs; }
        else { return; }
        if (currentRules.length == 0) { return; }

        var thisRules = getEffectValidType(currentRules);
        if (thisRules.length == 0) { return; }

        var t = $(target), opts = t.validatebox("options");
        var _validType = opts.validType;
        if (_validType.length && _validType.length > 0) {
            thisRules.forEach(function (rule) {
                $.array.remove(_validType, rule, function (item) { return rule == item; });
            });
            opts.validType = _validType;
            t.validatebox("validate");
        }
    }

    var methods = $.fn.validatebox.extensions.methods = {

        // 扩展 easyui-validatebox 控件的自定义方法；用于添加 easyui-validatebox 控件的验证规则，该方法定义如下参数：
        //      rules:   表示要动态添加的规则，该参数可以是如下类型
        //          1、String类型：表示一个要动态添加的规则；
        //          2、Array类型：表示一组要动态添加的规则；
        //  返回值：返回表示当前 easyui-validatebox 控件的 jQuery 链式对象。
        addValidType: function (jq, rules) { return jq.each(function () { addValidType(this, rules); }); },

        // 扩展 easyui-validatebox 控件的自定义方法；用于移除 easyui-validatebox 控件的验证规则，该方法定义如下参数：
        //      rules:   表示要动态移除的规则，该参数可以是如下类型
        //          1、String类型：表示一个要动态移除的规则；
        //          2、Array类型：表示一组要动态移除的规则；
        //  返回值：返回表示当前 easyui-validatebox 控件的 jQuery 链式对象。
        removeValidType: function (jq, rules) { return jq.each(function () { removeValidType(this, rules); }); }
    };


    $.extend($.fn.validatebox.methods, methods);

})(jQuery);

/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI validatebox 扩展
* jeasyui.extensions.validatebox.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-10-28
*
* 依赖项：
*   1、jquery.jdirk.js
*   2、jeasyui.extensions.validatebox.css
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function ($, undefined) {

    $.util.namespace("$.fn.validatebox.extensions");


    function setPrompt(target, prompt) {
        var t = $(target),
            state = $.data(target, "validatebox"),
            opts = state.options;
        opts.prompt = prompt || "";
        t.attr("placeholder", opts.prompt);
        if (!$.html5.testProp("placeholder", t[0].nodeName)) {
            t.unbind("blur.validatebox-extensions focus.validatebox-extensions").bind({
                "focus.validatebox-extensions": function () {
                    t.removeClass("validatebox-prompt");
                    if (t.val() == opts.prompt) {
                        t.val("");
                    }
                },
                "blur.validatebox-extensions": function () {
                    if (!t.val() && opts.prompt) {
                        t.addClass("validatebox-prompt").val(opts.prompt);
                    } else {
                        t.removeClass("validatebox-prompt");
                    }
                }
            }).trigger(t.is(":focus") ? "focus" : "blur");
        }
    };

    function setValue(target, value) {
        var t = $(target),
            state = $.data(target, "validatebox"),
            opts = state.options,
            val = t.val();
        if (val != value) {
            t.val(opts.value = (value ? value : ""));
        }
        t.validatebox("validate");
    };

    function getValue(target) {
        return $(target).val();
    };

    function clear(target) {
        $(target).validatebox("setValue", "");
    };

    function reset(target) {
        var t = $(target),
            state = $.data(target, "validatebox"),
            opts = state.options;
        t.validatebox("setValue", opts.originalValue ? opts.originalValue : "");
    };

    function resize(target, width) {
        var t = $(target),
            state = $.data(target, "validatebox"),
            opts = state.options;
        t._outerWidth(opts.width = width);
    };

    function setDisabled(target, disabled) {
        var t = $(target),
            state = $.data(target, "validatebox");
        if (disabled) {
            if (state && state.options) { state.options.disabled = true; }
            t.attr("disabled", true);
        } else {
            if (state && state.options) { state.options.disabled = false; }
            t.removeAttr("disabled");
        }
    };

    function setReadonly(target, readonly) {
        var t = $(target),
            state = $.data(target, "validatebox");
        readonly = readonly == null || readonly == undefined ? true : !!readonly;

        if (state && state.options) {
            state.options.readonly = readonly;
        }
        if (readonly) {
            t.addClass("validatebox-readonly").attr("readonly", true);
        } else {
            t.removeClass("validatebox-readonly").removeAttr("readonly");
        }
    };





    var _validatebox = $.fn.validatebox;
    $.fn.validatebox = function (options, param) {
        if (typeof options == "string") {
            return _validatebox.apply(this, arguments);
        }
        options = options || {};
        return this.each(function () {
            var t = $(this),
                isInited = $.data(this, "validatebox") ? true : false,
                opts = isInited ? options : $.extend({},
                    $.fn.validatebox.parseOptions(this),
                    $.parser.parseOptions(this,
                        ["prompt", "width", "cls", { autoFocus: "boolean", autoValidate: "boolean" }]
                    ),
                    {
                        value: (t.val() || undefined),
                        disabled: (t.attr("disabled") ? true : undefined),
                        readonly: (t.attr("readonly") ? true : undefined)
                    },
                    options
                );
            _validatebox.call(t, opts);
            if (!isInited) {
                initialize(this);
            }
        });
    };
    $.union($.fn.validatebox, _validatebox);


    function initialize(target) {
        var t = $(target),
            state = $.data(target, "validatebox"),
            opts = state.options;
        if (!opts._initialized) {
            t.addClass("validatebox-f").change(function () {
                opts.value = $(this).val();
                if ($.isFunction(opts.onChange)) {
                    opts.onChange.call(target, opts.value);
                }
            });
            opts.originalValue = opts.value;
            if (opts.value) {
                setValue(target, opts.value);
            }
            if (opts.width && !t.parent().is("span.textbox,span.spinner,span.searchbox")) {
                resize(target, opts.width);
            }
            setPrompt(target, opts.prompt);
            if (opts.autoFocus) {
                $.util.delay(function () { t.focus(); });
            }
            if (!opts.autoValidate) {
                t.removeClass("validatebox-invalid");
                hideTip(target);
            }
            if (opts.cls) {
                t.addClass(opts.cls);
            }
            if (opts.editable != undefined && !opts.editable) {
                opts.readonly = true;
            }
            setDisabled(target, opts.disabled);
            setReadonly(target, opts.readonly);
            opts._initialized = true;
        }
    };

    function hideTip(target) {
        var state = $.data(target, "validatebox");
        state.tip = false;
        $(target).tooltip("hide");
    };



    var defaults = $.fn.validatebox.extensions.defaults = {

        // 扩展 easyui-validatebox 控件的自定义属性；该属性功能类似于 easyui-searchbox 的 prompt 属性，表示该验证输入框的提示文本；
        // String 类型值，默认为 null。
        prompt: null,

        // 扩展 easyui-validatebox 控件的自定义属性；该属性表示在当前页面加载完成后，该 easyui-validatebox 控件是否自动获得焦点。
        // Boolean 类型值，默认为 false。
        autoFocus: false,

        // 扩展 easyui-validatebox 控件的自定义属性；表示是否在该控件初始化完成后立即进行一次验证；默认为 true。
        // Boolean 类型值，默认为 true。
        autoValidate: true,

        // 扩展 easyui-validatebox 控件的自定义属性；表示其初始化时的值。
        // String 类型值，默认为 null。
        value: null,

        // 扩展 easyui-validatebox 控件的自定义属性；表示其初始化时的宽度值。
        // Number 类型值，默认为 null。
        width: null,

        // 扩展 easyui-validatebox 控件的自定义属性；表示该控件在初始化完成后是否设置其为禁用状态(disabled)；默认为 false。
        // Boolean 类型值，默认为 false。
        disabled: false,

        // 扩展 easyui-validatebox 控件的自定义属性；表示该控件在初始化完成后是否设置其为只读状态(readonly)；默认为 false。
        // Boolean 类型值，默认为 false。
        readonly: false,

        // 扩展 easyui-validatebox 控件的自定义属性；表示 easyui-validatebox 初始化时默认需要加载的样式类名；
        // String 类型值，默认为 null。
        // 该值将会被作为 html-class 属性在 easyui-validatebox 初始化完成后加载至 html 标签上。
        cls: null,

        // 扩展 easyui-validatebox 控件的自定义扩展事件，表示输入框在值改变时所触发的事件。
        onChange: function (value) { }
    };

    var methods = $.fn.validatebox.extensions.methods = {

        // 扩展 easyui-validatebox 控件的自定义方法；
        // 设置当前 easyui-validatebox 控件的 prompt 值；
        // 该方法的参数 prompt 表示将被设置的 prompt 值；
        // 返回值：返回表示当前 easyui-validatebox 控件的 jQuery 链式对象；
        setPrompt: function (jq, prompt) {
            return jq.each(function () { setPrompt(this, prompt); });
        },

        // 扩展 easyui-validatebox 控件的自定义方法；
        // 设置当前 easyui-validatebox 控件的输入值。
        // 该方法的参数 value 表示要被设置的值；为一个 String 类型值。
        // 返回值：返回表示当前 easyui-validatebox 控件的 jQuery 链式对象；
        setValue: function (jq, value) {
            return jq.each(function () { setValue(this, value); });
        },

        // 扩展 easyui-validatebox 控件的自定义方法；
        // 获取当前 easyui-validatebox 控件的输入值。
        // 返回值：返回当前 easyui-validatebox 控件输入值；
        getValue: function (jq) {
            return getValue(jq[0]);
        },

        // 扩展 easyui-validatebox 控件的自定义方法；
        // 清除当前 easyui-validatebox 控件的输入值，使其为空。
        // 返回值：返回表示当前 easyui-validatebox 控件的 jQuery 链式对象；
        clear: function (jq) {
            return jq.each(function () { clear(this); });
        },

        // 扩展 easyui-validatebox 控件的自定义方法；
        // 重置当前 easyui-validatebox 控件的输入值，使其为初始化时的值。
        // 返回值：返回表示当前 easyui-validatebox 控件的 jQuery 链式对象；
        reset: function (jq) {
            return jq.each(function () { reset(this); });
        },

        // 扩展 easyui-validatebox 控件的自定义方法；
        // 设置当前 easyui-validatebox 控件的宽度（width）值。
        // 该方法的参数 width 表示将被设置的宽度（width）值；为一个 Number 类型值；该参数可选。
        // 返回值：返回表示当前 easyui-validatebox 控件的 jQuery 链式对象；
        resize: function (jq, width) {
            return jq.each(function () { resize(this, width); });
        },

        // 扩展 easyui-validatebox 控件的自定义方法；
        // 启用当前 easyui-validatebox 控件的输入状态。
        // 返回值：返回表示当前 easyui-validatebox 控件的 jQuery 链式对象；
        enable: function (jq) {
            return jq.each(function () { setDisabled(this, false); });
        },

        // 扩展 easyui-validatebox 控件的自定义方法；
        // 禁用当前 easyui-validatebox 控件的输入状态。
        // 返回值：返回表示当前 easyui-validatebox 控件的 jQuery 链式对象；
        disable: function (jq) {
            return jq.each(function () { setDisabled(this, true); });
        },

        // 扩展 easyui-validatebox 控件的自定义方法；
        // 设置当前 easyui-validatebox 控件的为只读状态。
        // 该方法的参数 readonly 是一个可选的 bool 类型值，默认值为 true，表示启用或禁用该控件的只读状态。
        // 返回值：返回表示当前 easyui-validatebox 控件的 jQuery 链式对象；
        readonly: function (jq, readonly) {
            return jq.each(function () { setReadonly(this, readonly); });
        }
    };

    $.extend($.fn.validatebox.defaults, defaults);
    $.extend($.fn.validatebox.methods, methods);



    // 修改 jQuery 本身的成员方法 val；使之支持 easyui-validatebox 的扩展属性 prompt。
    var core_val = $.fn.val;
    $.fn.val = function (value) {
        if (this.length && this.is(".validatebox-text.validatebox-prompt") && !$.html5.testProp("placeholder", this[0].nodeName)) {
            var val, opts = this.validatebox("options");
            if (arguments.length == 0) {
                val = core_val.apply(this, arguments);
                return val == opts.prompt ? "" : val;
            }
            if (value && value != opts.prompt) {
                this.removeClass("validatebox-prompt");
            }
        }
        return core_val.apply(this, arguments);
    };


})(jQuery);




/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI form 扩展
* jeasyui.extensions.form.getData.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-11-08
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.form.extensions");

    function getData(target, param) {
        if (!param) {
            var t = $(target), state = $.data(target, "form"), opts = state ? state.options : $.fn.form.defaults;
            param = opts.serializer;
        }
        return $(target).serializeObject(param);
    };


    var defaults = $.fn.form.extensions.defaults = {

        // 扩展 easyui-form 的自定义属性；表示在使用自定义方法 getData 获取 easyui-form 控件容器内所有表单控件的 JSON 序列化数据时的序列化方式；
        // JSON-Object 类型，该对象定义如下属性：
        //          onlyEnabled:    表示返回的结果数据中是否仅包含启用(disabled == false)的 HTML 表单控件；Boolean 类型值，默认为 true。
        //          transcript :    表示当范围内存在重名(name 相同时)的 DOM 元素时，对重复元素的取值规则；
        ///                 这是一个 String 类型值，可选的值限定在以下范围：
        //              cover  :    覆盖方式，只取后面元素 的值，丢弃前面元素的值；
        //              discard:    丢弃后面元素的值，只取前面元素的值；
        //              overlay:    将所有元素的值进行叠加；默认值；
        //          overtype   :    元素叠加方式，当 transcript 的值定义为 "overlay" 时，此属性方有效；
        //                  这是一个 String 类型值，可选的值限定在以下范围：
        //              array  :    将所有重复的元素叠加为一个数组；
        //              append :    将所有的重复元素叠加为一个字符串；默认值；
        //          separator  :    元素叠加的分隔符，定义将所有重名元素叠加为一个字符串时用于拼接字符串的分隔符；
        //                  这是一个 String 类型值，默认为 ","；当 transcript 的值定义为 "overlay" 且 overtype 的值定义为 "append" 时，此属性方有效。
        //              其取值范围和当参数格式为 JSON-Object 时的属性 transcript 一样。
        serializer: { onlyEnabled: true, transcript: "overlay", overtype: "append", separator: "," }
    };

    var methods = $.fn.form.extensions.methods = {

        //  扩展 easyui-form 的自定义方法；获取 easyui-form 控件容器内所有表单控件的 JSON 序列化数据；该方法的参数 param 可以定义为如下格式：
        //      1、JSON-Object  ：该对象定义如下属性：
        //          onlyEnabled:    表示返回的结果数据中是否仅包含启用(disabled == false)的 HTML 表单控件；Boolean 类型值，默认为 false。
        //          transcript :    表示当范围内存在重名(name 相同时)的 DOM 元素时，对重复元素的取值规则；
        ///                 这是一个 String 类型值，可选的值限定在以下范围：
        //              cover  :    覆盖方式，只取后面元素 的值，丢弃前面元素的值；默认值；
        //              discard:    丢弃后面元素的值，只取前面元素的值；
        //              overlay:    将所有元素的值进行叠加；
        //          overtype   :    元素叠加方式，当 transcript 的值定义为 "overlay" 时，此属性方有效；
        //                  这是一个 String 类型值，可选的值限定在以下范围：
        //              array  :    将所有重复的元素叠加为一个数组；
        //              append :    将所有的重复元素叠加为一个字符串；默认值；
        //          separator  :    元素叠加的分隔符，定义将所有重名元素叠加为一个字符串时用于拼接字符串的分隔符；
        //                  这是一个 String 类型值，默认为 ","；当 transcript 的值定义为 "overlay" 且 overtype 的值定义为 "append" 时，此属性方有效。
        //      注：未定义 param 参数时，将以新增的扩展属性 serializer 作为 param 参数。
        //      2、String 类型值:   表示当范围内存在重名(name 相同时)的 DOM 元素时，对重复元素的取值规则；
        //              其取值范围和当参数格式为 JSON-Object 时的属性 transcript 一样。
        //  返回值：该方法返回一个 JSON Object，返回对象中的每个数据都表示一个表单控件值。
        getData: function (jq, param) { return getData(jq[0], param); }
    };


    $.extend($.fn.form.defaults, defaults);
    $.extend($.fn.form.methods, methods);

})(jQuery);
