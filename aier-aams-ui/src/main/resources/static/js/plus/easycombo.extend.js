/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI combo 扩展
* jeasyui.extensions.combo.autoShowPanel.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-11-04
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.combo.extensions");

    function hideAllComboPanel(target) {
        $.util.pageNestingExecute(function (win) {
            if (!win || !win.document || !win.jQuery) {
                return;
            }
            var jq = win.jQuery;
            if (target) {
                var p = jq(target).closest("span.combo,div.combo-p,div.menu");
                if (p.length) {
                    p.find(".combo-f").each(function () {
                        var pp = jq(this).combo("panel");
                        if (pp.is(":visible")) {
                            pp.panel("close");
                        }
                    });
                    if (target && target.ownerDocument == win.document) {
                        return;
                    }
                }
            }
            jq("body>div.combo-p>div.combo-panel:visible").panel("close");
        });
    }

    //  下面这段代码实现即使在跨 IFRAME 的情况下，一个 WEB-PAGE 中也只能同时显示一个 easyui-combo panel 控件。
    $.util.bindDocumentNestingEvent("mousedown.combo-nesting", function (doc, e) {
        hideAllComboPanel(e.target);
    });




    function initializeExtensions(target) {
        var t = $(target),
            state = $.data(target, "combo"),
            opts = state.options;
        t.combo("textbox").click(function () {
            var p = t.combo("panel");
            if (opts.autoShowPanel && p.is(":hidden")) {
                t.combo("showPanel");
            }
        });
    }


    var _combo = $.fn.combo.extensions._combo = $.fn.combo;
    $.fn.combo = function (options, param) {
        if (typeof options == "string") {
            return _combo.apply(this, arguments);
        }
        options = options || {};
        return this.each(function () {
            var jq = $(this),
                isInited = $.data(this, "combo") ? true : false,
                opts = isInited ? options : $.extend({},
                        $.fn.combo.parseOptions(this),
                        $.parser.parseOptions(this, [
                            { autoShowPanel: "boolean" }
                        ]), options);
            _combo.call(jq, opts, param);
            if (!isInited) {
                initializeExtensions(this);
            }
        });
    };
    $.union($.fn.combo, _combo);


    var defaults = $.fn.combo.extensions.defaults = {

        //  扩展 easyui-combo 的自定义属性；表示该 easyui-combo 组件是否在 textbox 文本框获取焦点时自动执行 showPanel 方法以显示下拉 panel 面板；
        //  Boolean 类型值，默认为 true。
        //  落阳注：该自定义属性的功能，其实 easyui-combo 源生就提供，但只有在 editable 属性为 false 的情况下才有效；
        //  而扩展了该自定义属性之后，在 editable 属性为 true 时，也可以使得自动下拉 panel 面板。
        autoShowPanel: true
    };

    var methods = $.fn.combo.extensions.methods = {

    };


    $.extend($.fn.combo.defaults, defaults);
    $.extend($.fn.combo.methods, methods);

})(jQuery);


/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI combo 扩展
* jeasyui.extensions.combo.setRequired.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-11-05
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.combo.extensions");

    function setRequired(target, required) {
        var t = $(target), opts = t.combo("options"), textbox = t.combo("textbox");
        opts.required = textbox.validatebox("options").required = required;
        t.combo("validate");
    };


    var defaults = $.fn.combo.extensions.defaults = {};

    var methods = $.fn.combo.extensions.methods = {

        //  扩展 easyui-combo 组件的自定义方法；用于启用或者禁用 easyui-combo 控件的非空验证功能，该方法定义如下参数：
        //      required:   Boolean 类型的值，表示启用或者禁用 easyui-combo 控件的非空验证功能。
        //  返回值：返回表示当前 easyui-combo 控件的 jQuery 链式对象。
        setRequired: function (jq, required) { return jq.each(function () { setRequired(this, required); }); }
    };


    $.extend($.fn.combo.defaults, defaults);
    $.extend($.fn.combo.methods, methods);

})(jQuery);

/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI combo 扩展
* jeasyui.extensions.combo.destroy.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2016-02-15
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.combo.extensions");

    $.extend($.fn.combo.extensions, {
        destroy: $.fn.combo.methods.destroy
    });

    function destroy(target) {
        var t = $(target), opts = t.combo("options");
        if ($.isFunction(opts.onBeforeDestroy) && opts.onBeforeDestroy.call(target) == false) { return; }
        $.fn.combo.extensions.destroy.call(target, t);
        if ($.isFunction(opts.onDestroy)) { opts.onDestroy.call(target); }
    };


    var defaults = $.fn.combo.extensions.defaults = {

        //  扩展 easyui-combo 组件的自定义事件；表示当调用 destroy 方法销毁当前 easyui-commbo 控件之前所触发的动作。
        //  如果该事件函数返回 false，将中断 destroy 方法的执行。
        onBeforeDestroy: function () { },

        //  扩展 easyui-combo 组件的自定义事件；表示当调用 destroy 方法销毁当前 easyui-commbo 控件之后所触发的动作。
        onDestroy: function () { }
    };

    var methods = $.fn.combo.extensions.methods = {

        //  重写 easyui-combo 组件的 destroy 方法，以支持相应扩展功能（onBeforeDestroy、onDestroy 事件）。
        destroy: function (jq) { return jq.each(function () { destroy(this); }); }
    };


    $.extend($.fn.combo.defaults, defaults);
    $.extend($.fn.combo.methods, methods);

})(jQuery);

/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI combobox 扩展
* jeasyui.extensions.combobox.getSelected.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-11-06
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.combobox.extensions");

    function getSelected(target) {
        var t = $(target), opts = t.combobox("options"),
            value = t.combobox("getValue"), data = t.combobox("getData");
        return $.array.first(data, function (val) { return val[opts.valueField] == value; });
    };

    var defaults = $.fn.combobox.extensions.defaults = {};

    var methods = $.fn.combobox.extensions.methods = {

        //  扩展 easyui-combobox 的自定义方法；该方法用于获取当前选择了的项；
        //  返回值：返回一个 JSON-Object，该 JSON-Object 为当前 easyui-combobox 数据源中的一个子项，包含 valueField 和 textField 的值；
        //      如果当前 easyui-combobox 没有选中任何值，则返回 null。
        getSelected: function (jq) { return getSelected(jq[0]); }
    };


    $.extend($.fn.combobox.defaults, defaults);
    $.extend($.fn.combobox.methods, methods);

})(jQuery);


/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI combobox 扩展
* jeasyui.extensions.combobox.getSelections.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-11-06
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.combobox.extensions");

    function getSelections(target) {
        var t = $(target), opts = t.combobox("options"),
            values = t.combobox("getValues"), data = t.combobox("getData");
        return $.array.filter(data, function (val) {
            return $.array.contains(values, val[opts.valueField]);
        });
    };

    var defaults = $.fn.combobox.extensions.defaults = {};

    var methods = $.fn.combobox.extensions.methods = {

        //  扩展 easyui-combobox 的自定义方法；该方法用于获取当前选择了的所有项集合；
        //  返回值：返回一个 Array，数组中的每个元素都是一个 JSON-Object 为当前 easyui-combobox 数据源中的一个子项，包含 valueField 和 textField 的值；
        //      如果当前 easyui-combobox 没有选中任何值，则返回一个空数组。
        getSelections: function (jq) { return getSelections(jq[0]); }
    };


    $.extend($.fn.combobox.defaults, defaults);
    $.extend($.fn.combobox.methods, methods);

})(jQuery);

/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI combobox 扩展
* jeasyui.extensions.combobox.findItem.js
* 开发 落阳
* 最近更新：2015-11-06
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 Lixilin personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.combobox.extensions");

    var findItem = function (target, param) {
        var t = $(target), data = t.combobox("getData"), opts = t.combobox("options");
        return $.array.first(data, $.isFunction(param) ? param : function (val) { return val[opts.valueField] == param; });
    };

    var defaults = $.fn.combobox.extensions.defaults = {};

    var methods = $.fn.combobox.extensions.methods = {

        //  扩展 easyui-combobox 的自定义方法；获取符合查找内容的一项；该方法定义如下参数：
        //      param:  表示查找的内容；该方法的参数 param 可以是以下两种类型：
        //          待查找的项数据的 valueField 字段值；
        //          function 类型，该回调函数签名为 function(item, index, items)，其中 item 表示项数据对象、index 表示行索引号、items 表示当前 easyui-combobox 调用 getData 返回的结果集；
        //          如果 param 参数为 function 类型，则 findItem 方法会对当前 easyui-combobox 的每一项数据调用该回调函数；
        //          当回调函数返回 true 时，则表示找到需要查找的结果，立即停止循环调用并返回该项数据；
        //          如果回调函数始终未返回 true，则该回调函数会一直遍历 items 直到最后并返回 null。
        //  返回值：返回一个 JSON-Object，该 JSON-Object 为当前 easyui-combobox 数据源中的一个子项，包含 valueField 和 textField 的值；如果未找到相应数据，则返回 null。
        findItem: function (jq, param) {
            return findItem(jq[0], param);
        }
    };


    $.extend($.fn.combobox.defaults, defaults);
    $.extend($.fn.combobox.methods, methods);

})(jQuery);

/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI combobox 扩展
* jeasyui.extensions.combobox.findItems.js
* 开发 落阳
* 最近更新：2015-11-06
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 Lixilin personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.combobox.extensions");

    var findItems = function (target, param) {
        if (!$.util.isArray(param) && !$.isFunction(param)) { return []; }
        var t = $(target), data = t.combobox("getData"), opts = t.combobox("options");
        return $.array.filter(data, $.isFunction(param) ? param : function (val) { return $.array.contains(param, val[opts.valueField]); });
    };

    var defaults = $.fn.combobox.extensions.defaults = {};

    var methods = $.fn.combobox.extensions.methods = {

        //  扩展 easyui-combobox 的自定义方法；获取符合查找内容的项集合；该方法定义如下参数：
        //      param:  表示查找的内容；该方法的参数 param 可以是以下两种类型：
        //          array 类型，其中每个元素都是待查找的项数据的 valueField 字段值；
        //          function 类型，该回调函数签名为 function(item, index, items)，其中 item 表示项数据对象、index 表示行索引号、items 表示当前 easyui-combobox 调用 getData 返回的结果集；
        //          如果 param 参数为 function 类型，则 findItems 方法会对当前 easyui-combobox 的每一项数据调用该回调函数；
        //          当回调函数返回 true 时，则表示找到需要查找的结果；
        //  返回值：返回一个 Array，数组中的每个元素都是一个 JSON-Object 为当前 easyui-combobox 数据源中的一个子项，包含 valueField 和 textField 的值；如果未找到相应数据，则返回一个空数组。
        findItems: function (jq, param) {
            return findItems(jq[0], param);
        }
    };


    $.extend($.fn.combobox.defaults, defaults);
    $.extend($.fn.combobox.methods, methods);

})(jQuery);


/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI combobox bug修复
* jeasyui.fixeds.combobox.groupField.js
* 开发 落阳
* 最近更新：2015-11-06
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 Lixilin personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.combobox.extensions");

    $.fn.combobox.extensions._loadData = $.fn.combobox.methods.loadData;
    var loadData = function (target, data, remainText) {
        var state = $.data(target, 'combobox');
        var opts = state.options;
        state.data = opts.loadFilter.call(target, data);
        state.groups = [];
        data = state.data;

        var selected = $(target).combobox('getValues');
        var dd = [];
        //添加一个记录group历史记录的数组
        var tempGroups = [];
        //添加存放组和组对应的项的对象
        var tempDataForGroups = {}, tempDataForGroupItems = {};
        for (var i = 0; i < data.length; i++) {
            var row = data[i];
            var v = row[opts.valueField] + '';
            var s = row[opts.textField];
            var g = row[opts.groupField];

            if (g) {
                if (!$.array.contains(tempGroups, g)) {
                    tempGroups.push(g);
                    state.groups.push(g);
                    //添加组信息
                    var temp1 = '
';
                    temp1 += opts.groupFormatter ? opts.groupFormatter.call(target, g) : g;
                    temp1 += '
';
                    tempDataForGroups[g] = temp1;
                }
            } else {
                //无分组
                g = "";
            }

            //添加组-项信息
            var cls = 'combobox-item' + (row.disabled ? ' combobox-item-disabled' : '') + (g ? ' combobox-gitem' : '');
            var temp2 = '
';
            temp2 += opts.formatter ? opts.formatter.call(target, row) : s;
            temp2 += '
';

            if (tempDataForGroupItems[g] == undefined) {
                tempDataForGroupItems[g] = [];
            }
            tempDataForGroupItems[g].push(temp2);

            if (row['selected'] && $.inArray(v, selected) == -1) {
                selected.push(v);
            }
        }
        //重组tempDataForGroups和tempDataForGroupItems
        var hasGroup = false;
        for (var c in tempDataForGroups) {
            var item = tempDataForGroups[c]; //组信息
            if ($.util.isString(item)) {
                hasGroup = true;
                dd.push(item);
                var items = tempDataForGroupItems[c];
                if (items && $.util.isArray(items)) {
                    items.forEach(function (row) {
                        dd.push(row);
                    });
                }
            }
        }

        if (!hasGroup) {
            //只组装tempDataForGroupItems
            var items = tempDataForGroupItems[""];
            if (items && $.util.isArray(items)) {
                items.forEach(function (row) {
                    dd.push(row);
                });
            }
        }

        $(target).combo('panel').html(dd.join(''));

        if (opts.multiple) {
            setValues(target, selected, remainText);
        } else {
            setValues(target, selected.length ? [selected[selected.length - 1]] : [], remainText);
        }

        opts.onLoadSuccess.call(target, data);
    };

    function setValues(target, values, remainText) {
        var opts = $.data(target, 'combobox').options;
        var panel = $(target).combo('panel');

        if (!$.isArray(values)) { values = values.split(opts.separator) }
        panel.find('div.combobox-item-selected').removeClass('combobox-item-selected');
        var vv = [], ss = [];
        for (var i = 0; i < values.length; i++) {
            var v = values[i];
            var s = v;
            opts.finder.getEl(target, v).addClass('combobox-item-selected');
            var row = opts.finder.getRow(target, v);
            if (row) {
                s = row[opts.textField];
            }
            vv.push(v);
            ss.push(s);
        }

        if (!remainText) {
            $(target).combo('setText', ss.join(opts.separator));
        }
        $(target).combo('setValues', vv);
    }


    //解析data，若该 combobox 存在 groupField ，则重组 data，使其按照 groupField 排序。
    function parseRourceData(groupField, data) {
        if ($.string.isNullOrWhiteSpace(groupField)) { return data; }

        var tempGroups = [], tempItems = [];
        var len = data.length;
        //遍历data，分离出有groupField的项和没有groupField的项，并且对groupField依次排序
        for (var index = 0; index < len; index++) {
            var item = data[index], groupName = item[groupField];
            if ($.string.isNullOrWhiteSpace(groupName)) { tempItems.push(item); data.removeAt(index); index--; len--; }
            else {
                if (!$.array.contains(tempGroups, groupName)) { tempGroups.push(groupName); }
            }
        }
        //遍历依次排序的groupName集合
        for (var index = 0; index < tempGroups.length; index++) {
            var groupName = tempGroups[index];
            var dataLen = data.length;
            for (var dataIndex = 0; dataIndex < dataLen; dataIndex++) {
                var item = data[dataIndex];
                if (groupName == item[groupField]) { tempItems.push(item); data.removeAt(dataIndex); dataIndex--; dataLen--; }
            }
        }
        return tempItems;
    }


    $.fn.combobox.extensions._loader = $.fn.combobox.defaults.loader;
    var loader = function (param, success, error) {
        var t = $(this), opts = t.combobox('options');
        if (!opts.url) return false;
        $.ajax({
            type: opts.method,
            url: opts.url,
            data: param,
            dataType: 'json',
            success: function (data) {
                //在这里重组data，使其按照 groupField 排序。
                var newData = parseRourceData(opts.groupField, data);
                success(newData);
            },
            error: function () {
                error.apply(this, arguments);
            }
        });
    };


    var defaults = $.fn.combobox.extensions.defaults = {
        // 重写 easyui-combobox 的 loader 属性，以修复“使用groupField对数据进行分组时，必须将同组数据按顺序排列，否则无法识别同组数据”的bug
        loader: loader
    };

    var methods = $.fn.combobox.extensions.methods = {

        //  重写 easyui-combobox 的 loadData 方法；修复其“使用groupField对数据进行分组时，必须将同组数据按顺序排列，否则无法识别同组数据”的bug；该方法定义如下参数：
        //      data:  表示要加载的数据集；
        //  返回值：返回表示当前 easyui-combobox 控件的 jQuery 链式对象。
        loadData: function (jq, data) {
            return jq.each(function () {
                loadData(this, data);
            });
        }
    };


    $.extend($.fn.combobox.defaults, defaults);
    $.extend($.fn.combobox.methods, methods);

})(jQuery);



/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI combobox 扩展
* jeasyui.fixeds.combobox.setValues.js
* 开发 落阳
* 最近更新：2016-03-14
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2016 Lixilin personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.combobox.extensions");

    function setValues(target, values, remainText) {
        var opts = $.data(target, 'combobox').options;
        var panel = $(target).combo('panel');
        if (typeof (values) == "string" && values.length == 0) {
            var data = $(target).combobox("getData"), containEmpty = false;
            for (var i = 0; i < data.length; i++) {
                var item = data[i], val = item[opts.valueField];
                if (val == values) { containEmpty = true; break; }
            }
            if (!containEmpty) {
                values = [];
            }
        }
        if (!$.isArray(values)) { values = values.split(opts.separator) }
        panel.find('div.combobox-item-selected').removeClass('combobox-item-selected');
        var vv = [], ss = [];
        for (var i = 0; i < values.length; i++) {
            var v = values[i];
            var s = v;
            opts.finder.getEl(target, v).addClass('combobox-item-selected');
            var row = opts.finder.getRow(target, v);
            if (row) {
                s = row[opts.textField];
            }
            vv.push(v);
            ss.push(s);
        }

        if (!remainText) {
            $(target).combo('setText', ss.join(opts.separator));
        }
        $(target).combo('setValues', vv);
    }

    var defaults = $.fn.combobox.extensions.defaults = {};

    var methods = $.fn.combobox.extensions.methods = {

        //  重写 easyui-combobox 的 setValues 方法；以修复 reset 后可能导致 input 中显示多余的分割符的 bug ；该方法定义如下参数：
        //      values:  表示要设置的项数据的 valueField 字段值；可以是以下两种类型：
        //          array 类型，其中每个元素都是要设置的项数据的 valueField 字段值；
        //          string 类型，表示要设置的项数据的 valueField 字段值；
        //  返回值：返回表示当前 easyui-combobox jQuery 链式对象。
        setValues: function (jq, values) { return jq.each(function () { setValues(this, values); });}
    };


    $.extend($.fn.combobox.defaults, defaults);
    $.extend($.fn.combobox.methods, methods);

})(jQuery);
