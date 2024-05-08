/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI datagrid 组件扩展
* jeasyui.extensions.datagrid.findRow.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-11-21
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function () {

    $.util.namespace("$.fn.datagrid.extensions");


    function findRow(target, param, t, opts, rows) {
        t = t && t.length ? t : $(target);
        opts = opts ? opts : $.data(target, "datagrid").options;
        rows = rows && rows.length ? rows : t.datagrid("getRows");
        var type = $.type(param);
        if (type == "function") {
            return $.array.first(rows, function (row, index) {
                return param.call(target, row, index, rows);
            });
        } else if (type == "object") {
            return opts.idField && (opts.idField in param)
                ? findRowById(param[opts.idField])
                : $.array.first(rows, function (val) { return val == param; });
        } else {
            return findRowById(param);
        }
        function findRowById(id) {
            return $.array.first(rows, function (row) {
                return row[opts.idField] == id;
            });
        }
    }

    function findRows(target, param) {
        var t = $(target),
            state = $.data(target, "datagrid"),
            opts = state.options,
            rows = t.datagrid("getRows");
        if ($.isFunction(param)) {
            return $.array.filter(rows, function (val, index) {
                return param.call(target, val, index, rows);
            });
        } else if ($.array.likeArrayNotString(param)) {
            var array = $.array.map(param, function (val) {
                return findRow(target, val, t, opts, rows);
            });
            return $.array.filter(array, function (val) {
                return val != null && val != undefined;
            });
        } else {
            return [findRow(target, param, t, opts, rows)];
        }
    }

    var methods = $.fn.datagrid.extensions.methods = {

        //  扩展 easyui-datagrid 的自定义方法；查找当前数据页上的行数据，返回的是一个 JSON 对象；参数 param 表示查找的内容；该方法的参数 param 可以是以下三种类型：
        //      待查找的行数据的 idField(主键) 字段值；
        //      待查找的行数据对象 row ，若当前 easyui-datagrid 存在 idField 并且 row 对象中存在 idField 则根据 idField 查找，否则根据 row 对象查找；
        //      function 类型，该回调函数签名为 function(row, index, rows)，其中 row 表示行数据对象、index 表示行索引号、rows 表示当前 easyui-datagrid 调用 getRows 返回的结果集；
        //          如果 param 参数为 function 类型，则 findRow 方法会对当前 easyui-datagrid 的当前页的每一行数据调用该回调函数；
        //          当回调函数返回 true 时，则表示找到需要查找的结果，立即停止循环调用并返回该行数据；
        //          如果回调函数始终未返回 true，则该回调函数会一直遍历 rows 直到最后并返回 null。
        //  返回值：返回一个 JSON-Object，表示一个行数据对象；如果未找到相应数据，则返回 null。
        findRow: function (jq, param) { return findRow(jq[0], param); },

        //  扩展 easyui-datagrid 的自定义方法；查找当前数据页上的行数据；该方法的参数 param 可以是以下两种类型：
        //      Function 类型，该回调函数签名为 function(row, index, rows)，其中 row 表示行数据对象、index 表示行索引号、rows 表示当前 easyui-datagrid 调用 getRows 返回的结果集；
        //          如果 param 参数为 Function 类型，则 findRows 方法会对当前 easyui-datagrid 的当前页的每一行数据调用该回调函数；
        //          当回调函数返回 true 时，则返回的结果集中将会包含该行数据；
        //          如果该回调函数始终未返回 true，则该方法最终返回一个长度为 0 的数组对象。
        //      Array 类型，数组中的每一项都可以定义为如下类型：
        //          待查找的行数据的 idField(主键) 字段值；
        //          Function 类型；具体回调函数签名参考 findRow 方法中 param 参数为 function 类型时的定义；
        //          当 param 参数定义为 Array 类型时，则 findRows 方法会对数组中的每一项循环调用 findRow 方法，并过滤掉 findRow 方法返回 null 的结果行；
        //      落阳注：param 也可以是一个 idField(主键) 字段值，只是返回数据行时会组装成 Array 数组对象。
        //  返回值：返回一个 Array 数组对象；数组中的每一项都是 JSON-Object 类型，表示一个行数据对象；如果未找到相应数据，则返回一个长度为 0 的数组对象。
        findRows: function (jq, param) { return findRows(jq[0], param); }
    };

    var defaults = $.fn.datagrid.extensions.defaults = {

    };

    $.extend($.fn.datagrid.defaults, defaults);
    $.extend($.fn.datagrid.methods, methods);

})(jQuery);




/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI datagrid 组件扩展
* jeasyui.extensions.datagrid.deleteRow.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-11-21
*
* 依赖项：
*   1、jquery.jdirk.js
*   2、jeasyui.extensions.datagrid.findRow.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function () {

    $.util.namespace("$.fn.datagrid.extensions");

    $.fn.datagrid.extensions.deleteRow = $.fn.datagrid.methods.deleteRow;
    var deleteRow = function (target, param) {
        var t = $(target),
            state = $.data(target, "datagrid"),
            opts = state.options,
            row,index;
        if ($.util.isNumber(param)) {
            var rows = t.datagrid("getRows");
            row = $.array.first(rows, function (itemRow, itemIndex) { return itemIndex == param; });
            index = param;
        }
        else {
            row = t.datagrid("findRow", param);
            index = t.datagrid("getRowIndex", row);
        }
        if (index > -1 && $.isFunction(opts.onBeforeDeleteRow) && opts.onBeforeDeleteRow.call(target, index, row) == false) { return; }
        $.fn.datagrid.extensions.deleteRow.call(target, t, index);
        if ($.isFunction(opts.onDeleteRow)) { opts.onDeleteRow.call(target, index, row); }
    };

    var deleteRows = function (target, param) {
        var t = $(target),
            state = $.data(target, "datagrid"),
            opts = state.options;
        if ($.array.likeArrayNotString(param)) {
            $.each(param, function (index, val) { deleteRow(target, val); });
        } else if ($.isFunction(param)) {
            var rows = t.datagrid("getRows"), onBeforeDeleteRowExist = $.isFunction(opts.onBeforeDeleteRow), onDeleteRowExist = $.isFunction(opts.onDeleteRow);
            // 以下是“遍历rows，对每个row调用param回调函数，若返回true则删除行”的方式，但是每次deleteRow都将更新rows的索引
            //$.each(rows, function (index, row) {
            //    if (!row) { return; }
            //    if (param.call(target, row, index, rows) == true) {
            //        var i = t.datagrid("getRowIndex", row);
            //        if (i > -1 && onBeforeDeleteRowExist && opts.onBeforeDeleteRow.call(target, i, row) == false) { return; }
            //        $.fn.datagrid.extensions.deleteRow.call(target, t, i);
            //        if (onDeleteRowExist) { opts.onDeleteRow.call(target, i, row); }
            //    }
            //});
            // 以下是“调用findRows，将符合param回调函数的row组装成数组，最后遍历数组对每项进行删除行”的方式
            // 从常规逻辑来看，这种处理逻辑更合理
            var theRows = t.datagrid("findRows", param);
            $.each(theRows, function (index, row) {
                var i = t.datagrid("getRowIndex", row);
                if (i > -1 && onBeforeDeleteRowExist && opts.onBeforeDeleteRow.call(target, i, row) == false) { return; }
                $.fn.datagrid.extensions.deleteRow.call(target, t, i);
                if (onDeleteRowExist) { opts.onDeleteRow.call(target, i, row); }
            });
        }
    };

    var methods = $.fn.datagrid.extensions.methods = {

        //  重写 easyui-datagrid 的 deleteRow 方法；参数 param 表示要删除的内容；该参数可以是以下四种类型：
        //      Number 类型，表示要删除的行索引号，从 0 开始计数；
        //      表示要删除的行数据的 idField(主键) 字段值；
        //      行数据对象；
        //      Function 类型，该回调函数签名为 function(row, index, rows)，其中 row 表示行数据对象、index 表示行索引号、rows 表示当前 easyui-datagrid 调用 getRows 返回的结果集；
        //          如果 param 参数为 Function 类型，则 deleteRow 方法会对当前 easyui-datagrid 的当前页的每一行数据调用该回调函数；
        //          当回调函数返回 true 时，则表示查找到了需要被删除的行，deleteRow 方法将会删除该行数据并立即停止和跳出循环操作；
        //  返回值：返回表示当前 easyui-datagrid 组件的 jQuery 链式对象。
        deleteRow: function (jq, param) { return jq.each(function () { deleteRow(this, param); }); },

        //  扩展 easyui-datagrid 的自定义方法；参数 param 表示要删除的内容；该参数可以是以下两种类型：
        //      Function 类型，该回调函数签名为 function(row, index, rows)，其中 row 表示行数据对象、index 表示行索引号、rows 表示当前 easyui-datagrid 调用 getRows 返回的结果集；
        //          如果 param 参数为 Function 类型，则 deleteRows 方法会对当前 easyui-datagrid 的当前页的每一行数据调用该回调函数；
        //          当回调函数返回 true 时，则表示查找到了需要被删除的行，deleteRows 方法将会删除该行数据，并遍历下一行数据直至数数据集的末尾；
        //      Array 类型，数组中的每一项目均表示要删除的行的行索引号或者 idField(主键) 字段值或者行数据对象
        //  返回值：返回表示当前 easyui-datagrid 组件的 jQuery 链式对象。
        deleteRows: function (jq, param) { return jq.each(function () { deleteRows(this, param); }); }
    };

    var defaults = $.fn.datagrid.extensions.defaults = {

        //  扩展 easyui-datagrid 的自定义事件；该事件表示执行 deleteRow 方法前所触发的动作；该事件回调函数提供如下两个参数：
        //      index:  表示要进行 deleteRow 的行的索引号，从 0 开始计数；
        //      row:    表示要进行 deleteRow 操作的行数据对象；
        //  该事件函数中的 this 指向当前 easyui-datarid 的 DOM 对象(非 jQuery 对象)；
        //  备注：如果该事件回调函数返回 false，则立即取消即将要执行的 deleteRow 操作。
        onBeforeDeleteRow: function (index, row) { },

        //  扩展 easyui-datagrid 的自定义事件；该事件表示执行 deleteRow 方法后所触发的动作；该事件回调函数提供如下两个参数：
        //      index:  表示要进行 deleteRow 的行的索引号，从 0 开始计数；
        //      row:    表示要进行 deleteRow 操作的行数据对象；
        //  该事件函数中的 this 指向当前 easyui-datarid 的 DOM 对象(非 jQuery 对象)；
        onDeleteRow: function (index, row) { }
    };

    $.extend($.fn.datagrid.defaults, defaults);
    $.extend($.fn.datagrid.methods, methods);

})(jQuery);




/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI datagrid 组件扩展
* jeasyui.extensions.datagrid.getColumnInfo.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-12-02
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function () {

    $.util.namespace("$.fn.datagrid.extensions");



    $.fn.datagrid.extensions.getColumnFields = $.fn.datagrid.methods.getColumnFields;
    var getColumnFields = function (target, frozen) {
        var t = $(target);
        if (frozen == null || frozen == undefined) {
            return $.fn.datagrid.extensions.getColumnFields.call(t, t, frozen);
        }
        return $.type(frozen) == "string"
            ? $.array.merge([],
                $.fn.datagrid.extensions.getColumnFields.call(t, t, true),
                $.fn.datagrid.extensions.getColumnFields.call(t, t, false))
            : $.fn.datagrid.extensions.getColumnFields.call(t, t, frozen);
    };

    var getColumnOptions = function (target, frozen) {
        var t = $(target), fields = getColumnFields(target, frozen);
        return $.array.map(fields, function (val) { return t.datagrid("getColumnOption", val); });
    };

    var getNextColumnOption = function (target, field) {
        if (!field) {
            return undefined;
        }
        var fields = getColumnFields(target, "all");
        if (!fields || !fields.length) {
            return undefined;
        }
        var index = $.array.indexOf(fields, field);
        return index == -1 || index >= fields.length - 1
            ? undefined
            : $(target).datagrid("getColumnOption", fields[index + 1]);
    };

    var getPrevColumnOption = function (target, field) {
        if (!field) {
            return undefined;
        }
        var fields = getColumnFields(target, "all");
        if (!fields || !fields.length) {
            return undefined;
        }
        var index = $.array.indexOf(fields, field);
        return index < 1
            ? undefined
            : $(target).datagrid("getColumnOption", fields[index - 1]);
    };


    var methods = $.fn.datagrid.extensions.methods = {

        //  重写 easyui-datagrid 的 getColumnFields 方法；获取 easyui-datagrid 所有列的 field 所组成的一个数组集合；参数 frozen 可以定义为如下格式：
        //      Boolean 类型值：如果是 true，则表示返回的结果集中包含 frozen(冻结)列，如果是 false 则表示返回的结果集中不包含 frozen(冻结)列；
        //      String 类型值：如果该参数定义为任意 String 类型值，则返回所有列信息(包括 frozen 冻结列和非冻结列)；
        //  返回值：如果 frozen 参数定义为 Boolean 且为 true，则返回所有 frozen(冻结) 列的 field 所构成的一个 Array 数组对象；
        //      如果 frozen 参数定义为 false，则返回所有非 frozen(非冻结) 列的 field 所构成的一个 Array 数组对象；
        //      如果 frozen 定义为任意的 String 类型值，则返回所有列的 field 所构成的一个 Array 数组对象。
        //  落阳注：重写该方法是为了使方法能返回“冻结列与非冻结列所构成的一个 Array 数组对象”。
        getColumnFields: function (jq, frozen) { return getColumnFields(jq[0], frozen); },

        //  扩展 easyui-datagrid 的自定义方法；获取 easyui-datagrid 所有列的 columnOption 所组成的一个数组集合；参数 frozen 可以定义为如下格式：
        //      Boolean 类型值：如果是 true，则表示返回的结果集中包含 frozen(冻结)列，如果是 false 则表示返回的结果集中不包含 frozen(冻结)列；
        //      String 类型值：如果该参数定义为任意 String 类型值，则返回所有列信息(包括 frozen 冻结列和非冻结列)；
        //  返回值：如果 frozen 参数定义为 Boolean 且为 true，则返回所有 frozen(冻结) 列的 columnOption 所构成的一个 Array 数组对象；
        //      如果 frozen 参数定义为 false，则返回所有非 frozen(非冻结) 列的 columnOption 所构成的一个 Array 数组对象；
        //      如果 frozen 定义为任意的 String 类型值，则返回所有列的 columnOption 所构成的一个 Array 数组对象。
        getColumnOptions: function (jq, frozen) { return getColumnOptions(jq[0], frozen); },

        //  扩展 easyui-datagrid 的自定义方法；获取指定列的下一格位置列的 列属性(columnOption) 信息；该方法的参数 field 表示指定列的 field 值。
        //  返回值：当前指定列的下一格位置的列的 列属性(columnOption) 信息。
        //      如果不存在指定的列，或者指定列的下一格位置没有其他列，则返回 undefined。
        getNextColumnOption: function (jq, field) { return getNextColumnOption(jq[0], field); },

        //  扩展 easyui-datagrid 的自定义方法；获取指定列的上一格位置列的 列属性(columnOption) 信息；该方法的参数 field 表示指定列的 field 值。
        //  返回值：当前指定列的上一格位置的列的 列属性(columnOption) 信息。
        //      如果不存在指定的列，或者指定列的上一格位置没有其他列，则返回 undefined。
        getPrevColumnOption: function (jq, field) { return getPrevColumnOption(jq[0], field); }
    };

    var defaults = $.fn.datagrid.extensions.defaults = {

    };

    $.extend($.fn.datagrid.defaults, defaults);
    $.extend($.fn.datagrid.methods, methods);

})(jQuery);


/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI datagrid 组件扩展
* jeasyui.extensions.datagrid.getDom.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-12-02
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function () {

    $.util.namespace("$.fn.datagrid.extensions");


    var getRowDom = function (target, index) {
        if (!$.isNumeric(index) || index < 0) { return $(); }
        var t = $(target), panel = t.datagrid("getPanel");
        return panel.find("div.datagrid-view div.datagrid-body table tr.datagrid-row[datagrid-row-index=" + index + "]");
    };

    var getColumnDom = function (target, param) {
        if ($.string.isNullOrEmpty(param)) { return $(); }
        var t = $(target), panel = t.datagrid("getPanel"),
            isObject = !$.string.isString(param),
            field = isObject ? param.field : param,
            header = isObject ? param.header : false,
            dom = panel.find("div.datagrid-view tr.datagrid-row td[field=" + field + "]");
        if (header) { dom = dom.add(panel.find("div.datagrid-view tr.datagrid-header-row td[field=" + field + "]")); }
        return dom;
    };

    var getCellDom = function (target, pos) {
        if (!pos || !pos.field || !$.isNumeric(pos.index) || pos.index < 0) { return $(); }
        var t = $(target), tr = t.datagrid("getRowDom", pos.index);
        return tr.find("td[field=" + pos.field + "] .datagrid-cell");
    };

    var getCellHtml = function (target, pos) {
        var td = getCellDom(target, pos);
        return td && td.length ? td.html() : undefined;
    };

    var getCellText = function (target, pos) {
        var td = getCellDom(target, pos);
        return td && td.length ? td.text() : undefined;
    };

    var methods = $.fn.datagrid.extensions.methods = {

        //  扩展 easyui-datagrid 的自定义方法；获取 easyui-datagrid 中当前页指定行的 DOM-jQuery 对象元素集合；该函数定义如下参数：
        //      index: 表示要获取的 DOM-Jquery 对象元素集合所在当前页的行索引号；
        //  返回值：如果当前页存在 index 指示的行，则返回该行的 DOM-jQuery 对象集合，该集合中包含的 DOM 节点级别为一组 tr class="datagrid-row" 对象；
        //          否则返回一个空的 jQuery 对象。
        getRowDom: function (jq, index) { return getRowDom(jq[0], index); },

        //  扩展 easyui-datagrid 的自定义方法；获取 easyui-datagrid 中当前页指定列的 DOM-jQuery 元素对象；该函数定义如下参数：
        //      param: 该参数可以定义以下类型：
        //          String 类型：表示要获取的 DOM-jQuery 元素所在的列的 field 名；
        //          JSON-Object 类型：如果定义为该类型，则该参数定义如下属性：
        //              field:  表示要获取的 DOM-jQuery 元素所在的列的 field 名；
        //              header: Boolean 类型值，默认为 false，表示返回的 DOM-jQuery 对象中是否包含 field 表示的列的表头；
        //  返回值：如果当前页存在 field 值指定的列，则返回该列中指定行的 DOM-jQuery 对象，该对象中包含的 DOM 节点级别为一个 td[field=field] 对象；
        //          否则返回一个空的 jQuery 对象。
        //          如果 param 参数定义为 JSON-Object 类型，且 param.header = true，则返回的 DOM-jQuery 对象中将会包含列的表头元素；
        //          如果 param 参数定义为 String 类型或者即使定义为 JSON-Object 类型但 param.header = false，则返回的 DOM-jQuery 对象中不包含列的表头元素。
        getColumnDom: function (jq, param) { return getColumnDom(jq[0], param); },

        //  扩展 easyui-datagrid 的自定义方法；获取 easyui-datagrid 中当前页指定单元格的 Dom-jQuery 对象元素；该函数定义如下参数：
        //      pos：表示单元格的位置，为一个 JSON-Object 对象，该 JSON 定义如下属性：
        //          field:  表示要获取的单元格位于哪个列；
        //          index:  表示要获取的单元格位于哪个行的行索引号，从 0 开始；
        //  返回值：如果当前页存在指定列的指定行，则返回该列中指定行的 DOM-jQuery 对象，该对象中包含的 DOM 节点级别为一个 div class="datagrid-cell" 对象；
        //          否则返回一个空的 jQuery 对象。
        getCellDom: function (jq, pos) { return getCellDom(jq[0], pos); },

        //  扩展 easyui-datagrid 的自定义方法；获取 easyui-datagrid 中当前页指定单元格的显示数据(经过 formatter 格式化后的显示数据)；该函数定义如下参数：
        //  pos：表示单元格的位置，为一个 JSON-Object 对象，该 JSON 定义如下属性：
        //          field:  表示要获取的单元格位于哪个列；
        //          index:  表示要获取的单元格位于哪个行的行索引号，从 0 开始；
        //  返回值：如果当前页存在指定列的指定行，则返回该列中指定行的单元格的显示数据(经过 formatter 格式化后的显示数据)；否则返回 undefined。
        getCellHtml: function (jq, pos) { return getCellHtml(jq[0], pos); },

        //  扩展 easyui-datagrid 的自定义方法；获取 easyui-datagrid 中当前页指定单元格的显示文本(经过 formatter 格式化后的显示文本)；该函数定义如下参数：
        //  pos：表示单元格的位置，为一个 JSON-Object 对象，该 JSON 定义如下属性：
        //          field:  表示要获取的单元格位于哪个列；
        //          index:  表示要获取的单元格位于哪个行的行索引号，从 0 开始；
        //  返回值：如果当前页存在指定列的指定行，则返回该列中指定行的单元格的显示文本(经过 formatter 格式化后的显示文本)；否则返回 undefined。
        getCellText: function (jq, pos) { return getCellText(jq[0], pos); }
    };

    var defaults = $.fn.datagrid.extensions.defaults = {

    };

    $.extend($.fn.datagrid.defaults, defaults);
    $.extend($.fn.datagrid.methods, methods);

})(jQuery);


/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI datagrid 组件扩展
* jeasyui.extensions.datagrid.rowState.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-11-24
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function () {

    $.util.namespace("$.fn.datagrid.extensions");


    var isChecked = function (target, index) {
        if (index == null || index == undefined) {
            return false;
        }
        var t = $(target), rows = t.datagrid("getChecked"),
            list = $.array.map(rows, function (val) { return t.datagrid("getRowIndex", val); });
        return $.array.contains(list, index);
    };

    var isSelected = function (target, index) {
        if (index == null || index == undefined) {
            return false;
        }
        var t = $(target), rows = t.datagrid("getSelections"),
            list = $.array.map(rows, function (val) { return t.datagrid("getRowIndex", val); });
        return $.array.contains(list, index);
    };

    var isEditing = function (target, index) {
        if (index == null || index == undefined) {
            return false;
        }
        var t = $(target), panel = t.datagrid("getPanel");
        return panel.find("div.datagrid-view div.datagrid-body table tr.datagrid-row[datagrid-row-index=" + index + "]").hasClass("datagrid-row-editing");
    };

    var getEditingRowIndex = function (target) {
        var array = getEditingRowIndexs(target);
        return array.length ? array[0] : -1;
    };

    var getEditingRowIndexs = function (target) {
        var t = $(target), panel = t.datagrid("getPanel"),
            rows = panel.find("div.datagrid-view div.datagrid-body table tr.datagrid-row.datagrid-row-editing").map(function () {
                return window.parseInt($(this).attr("datagrid-row-index"));
            }),
            array = $.array.distinct($.array.clone(rows));
        return array;
    };



    var methods = $.fn.datagrid.extensions.methods = {

        //  扩展 easyui-datagrid 的自定义方法；判断指定的 data-row(数据行) 是否被 check；该方法的参数 index 表示要判断的行的索引号，从 0 开始计数；
        //  返回值：如果参数 index 所表示的 data-row(数据行) 被 check，则返回 true，否则返回 false。
        isChecked: function (jq, index) { return isChecked(jq[0], index); },

        //  扩展 easyui-datagrid 的自定义方法；判断指定的 data-row(数据行) 是否被 select；该方法的参数 index 表示要判断的行的索引号，从 0 开始计数；
        //  返回值：如果参数 index 所表示的 data-row(数据行) 被 select，则返回 true，否则返回 false。
        isSelected: function (jq, index) { return isSelected(jq[0], index); },

        //  扩展 easyui-datagrid 的自定义方法；判断指定的 data-row(数据行) 是否开启行编辑状态；该方法的参数 index 表示要判断的行的索引号，从 0 开始计数；
        //  返回值：如果参数 index 所表示的 data-row(数据行) 正开启行编辑状态，则返回 true，否则返回 false。
        isEditing: function (jq, index) { return isEditing(jq[0], index); },

        //  扩展 easyui-datagrid 的自定义方法；获取当前表格中第一个开启了编辑状态的数据行的索引号(从 0 开始计数)；
        //  返回值：如果当前表格中存在开启了行编辑状态的行，则返回第一个编辑行的行索引号(从 0 开始计数)；否则返回 -1。
        getEditingRowIndex: function (jq) { return getEditingRowIndex(jq[0]); },

        //  扩展 easyui-datagrid 的自定义方法；获取当前表格中所有开启了行编辑状态的行的索引号(从 0 开始计数)；
        //  返回值：返回一个数组，数组中包含当前表格中所有已经开启了行编辑状态的行的索引号(从 0 开始计数)。
        getEditingRowIndexs: function (jq) { return getEditingRowIndexs(jq[0]); }
    };

    var defaults = $.fn.datagrid.extensions.defaults = {

    };

    $.extend($.fn.datagrid.defaults, defaults);
    $.extend($.fn.datagrid.methods, methods);

})(jQuery);



/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI datagrid 组件扩展
* jeasyui.extensions.datagrid.getRowData.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-12-01
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function () {

    $.util.namespace("$.fn.datagrid.extensions");


    var getRowData = function (target, index) {
        if (!$.isNumeric(index) || index < 0) { return undefined; }
        var t = $(target), rows = t.datagrid("getRows");
        return rows[index];
    };

    var getNextRow = function (target, index) {
        return getRowData(target, index + 1);
    };

    var getPrevRow = function (target, index) {
        return getRowData(target, index - 1);
    };

    var popRow = function (target, index) {
        if (!$.isNumeric(index) || index < 0) { return undefined; }
        var t = $(target), rows = t.datagrid("getRows"), row = rows[index];
        if (row) { t.datagrid("deleteRow", index); }
        return row;
    };

    var methods = $.fn.datagrid.extensions.methods = {

        //  扩展 easyui-datagrid 的自定义方法；获取 easyui-datagrid 中当前页指定行的 rowData；该函数定义如下参数：
        //      index: 表示要获取的 rowData 所在当前页的行索引号，从 0 开始；
        //  返回值：如果当前页存在 index 指示的行，则返回该行的行数据对象（JSON Object 格式）；否则返回 undefined。
        getRowData: function (jq, index) { return getRowData(jq[0], index); },

        //  扩展 easyui-datagrid 的自定义方法；获取指定行的下一行数据；该方法的参数 index 表示指定行的行号(从 0 开始)；
        //  返回值：返回指定行的下一行数据，返回值是一个 JSON-Object 对象；
        //      如果指定的行没有下一行数据 (例如该行为最后一行的情况下)，则返回 undefined。
        getNextRow: function (jq, index) { return getNextRow(jq[0], index); },

        //  扩展 easyui-datagrid 的自定义方法；获取指定行的上一行数据；该方法的参数 index 表示指定行的行号(从 0 开始)；
        //  返回值：返回指定行的上一行数据，返回值是一个 JSON-Object 对象；
        //      如果指定的行没有上一行数据 (例如该行为第一行的情况下)，则返回 undefined。
        getPrevRow: function (jq, index) { return getPrevRow(jq[0], index); },

        //  扩展 easyui-datagrid 的自定义方法；从 easyui-datagrid 当前页中删除指定的行，并返回该行数据；
        //  该方法的参数 index 表示指定行的行号(从 0 开始)；
        //  返回值：返回 index 所表示的行的数据，返回值是一个 JSON-Object 对象；
        //      如果不存在指定的行(例如 easyui-datagrid 当前页没有数据或者 index 超出范围)，则返回 undefined。
        popRow: function (jq, index) { return popRow(jq[0], index); }
    };

    var defaults = $.fn.datagrid.extensions.defaults = {

    };

    $.extend($.fn.datagrid.defaults, defaults);
    $.extend($.fn.datagrid.methods, methods);

})(jQuery);


/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI datagrid 组件扩展
* jeasyui.extensions.datagrid.liveSearch.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-11-24
*
* 依赖项：
*   1、jquery.jdirk.js
*   2、jeasyui.extensions.datagrid.css
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function () {

    $.util.namespace("$.fn.datagrid.extensions");


    var liveSearch = function (target, param) {
        var t = $(target), panel = t.datagrid("getPanel"), cells, field, value = param, regular = false, ignoreCase = true, regexp;
        if ($.isPlainObject(param)) {
            value = param.value;
            field = param.field;
            regular = param.regular;
            ignoreCase = param.ignoreCase;
            cells = panel.find("div.datagrid-body tr.datagrid-row td[" + (field ? "field=" + field : "field") + "] div.datagrid-cell");
        } else {
            cells = panel.find("div.datagrid-body tr.datagrid-row td[field] div.datagrid-cell");
        }

        regexp = regular ? new RegExp(value, ignoreCase ? "gm" : "igm") : value;
        cells.each(function () {
            var cell = $(this);
            cell.find("span.datagrid-cell-hightlight").replaceWith(function () { return $(this).text(); });

            if (!value) { return; }
            var text = cell.html(); if (!text) { return; }
            cell.html($.string.replaceAll(text, value, "" + value + ""));
        });
    };

    var clearLiveHighLight = function (target, field) {
        var t = $(target), panel = t.datagrid("getPanel"), cells;
        if (field == null || field == undefined) {
            cells = panel.find("div.datagrid-body tr.datagrid-row td[field] div.datagrid-cell");
        }
        else {
            cells = panel.find("div.datagrid-body tr.datagrid-row td[field=" + String(field) + "] div.datagrid-cell");
        }

        cells.each(function () {
            var cell = $(this);
            cell.find("span.datagrid-cell-hightlight").replaceWith(function () { return $(this).text(); });
        });
    };

    var methods = $.fn.datagrid.extensions.methods = {

        //  扩展 easyui-datagrid 的自定义方法；对当前 easyui-datagrid 中进行高亮关键词查询；该方法的 param 可以定义为如下两种类型：
        //      1、String 类型值：表示要对所有列进行的高亮查询关键词；
        //      2、JSON-Object：表示对特定列进行高亮查询的参数，该对象类型参数包含如下属性：
        //          field:      表示要进行高亮查询的列；
        //          value:      表示要进行高亮查询的关键词；
        //          regular:    Boolean 类型值，默认为 false；指示该关键词是否为正则表达式；
        //          ignoreCase: Boolean 类型值，默认为 true；指示高亮查询时是否忽略大小写。
        //  返回值：返回表示当前 easyui-datagrid 组件的 jQuery 链式对象。
        liveSearch: function (jq, param) { return jq.each(function () { liveSearch(this, param); }); },

        //  扩展 easyui-datagrid 的自定义方法；清除当前 easyui-datagrid 中进行高亮关键词查询后产生的高亮效果；该方法的 field 可以定义为如下类型：
        //      1、String 类型值：表示要对特定列的高亮效果进行清除，不提供该参数则表示要清除所有列的高亮效果；
        //  返回值：返回表示当前 easyui-datagrid 组件的 jQuery 链式对象。
        clearLiveHighLight: function (jq, field) { return jq.each(function () { clearLiveHighLight(this, field); }); }
    };

    var defaults = $.fn.datagrid.extensions.defaults = {

    };

    $.extend($.fn.datagrid.defaults, defaults);
    $.extend($.fn.datagrid.methods, methods);

})(jQuery);


/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI datagrid 组件扩展
* jeasyui.extensions.datagrid.updateColumn.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-11-25
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function () {

    $.util.namespace("$.fn.datagrid.extensions");


    var setColumnTitle = function (target, param) {
        if (param && param.field && param.title) {
            var t = $(target), colOpts = t.datagrid("getColumnOption", param.field),
                field = param.field, title = param.title,
                panel = t.datagrid("getPanel"),
                td = panel.find("div.datagrid-view div.datagrid-header tr.datagrid-header-row td[field=" + field + "]");
            if (td.length) { td.find("div.datagrid-cell span:first").html(title); colOpts.title = title; }
        }
    };

    var setColumnWidth = function (target, param) {
        if (param && param.field && param.width && $.isNumeric(param.width)) {
            var state = $.data(target, "datagrid"),
                t = $(target),
                opts = t.datagrid("options"),
                colOpts = t.datagrid("getColumnOption", param.field),
                field = param.field, width = param.width,
                cell = t.datagrid("getPanel").find("div.datagrid-view div.datagrid-header tr.datagrid-header-row td[field=" + field + "] div.datagrid-cell");
            if (cell.length) {

                var diff = $.string.isNullOrWhiteSpace(cell[0].style.width) ? (colOpts.width - colOpts.boxWidth) : cell._outerWidth() - parseInt(cell[0].style.width);
                cell.css("height", "");
                colOpts.width = width;
                colOpts.boxWidth = width - diff;
                colOpts.auto = undefined;
                cell.width(colOpts.boxWidth);
                t.datagrid("fixColumnSize", field);
                t.datagrid("fitColumns");
                opts.onResizeColumn.call(target, field, width);
            }
        }
    };

    var methods = $.fn.datagrid.extensions.methods = {

        //  扩展 easyui-datagrid 的自定义方法；设置 easyui-datagrid 中列的标题；参数 param 是一个 JSON-Object 对象，包含如下属性：
        //      field: 列字段名称；
        //      title: 列的新标题；
        //  返回值：返回表示当前 easyui-datagrid 组件的 jQuery 链式对象。
        setColumnTitle: function (jq, param) { return jq.each(function () { setColumnTitle(this, param); }); },

        //  扩展 easyui-datagrid 的自定义方法；设置 easyui-datagrid 中列的宽度；参数 param 是一个 JSON-Object 对象，该 JSON 对象定义如下属性：
        //      field: 要设置列宽的的列 field 值；
        //      width: 要设置的列宽度，Number 类型值；
        //  返回值：返回表示当前 easyui-datagrid 组件的 jQuery 链式对象。
        setColumnWidth: function (jq, param) { return jq.each(function () { setColumnWidth(this, param); }); }
    };

    var defaults = $.fn.datagrid.extensions.defaults = {

    };

    $.extend($.fn.datagrid.defaults, defaults);
    $.extend($.fn.datagrid.methods, methods);

})(jQuery);



/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI datagrid 组件扩展
* jeasyui.extensions.datagrid.getColumnData.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-11-25
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function () {

    $.util.namespace("$.fn.datagrid.extensions");


    var getColumnData = function (target, field) {
        var t = $(target), rows = t.datagrid("getRows");
        return $.array.map(rows, function (val) { return val[field]; });
    };

    var methods = $.fn.datagrid.extensions.methods = {

        //  扩展 easyui-datagrid 的自定义方法；获取 easyui-datagrid 中当前也指定列所有行的单元格数据所构成的一个数组；该函数定义如下参数：
        //      field: 要获取的数据的列的 field 名；
        //  返回值：返回一个数组，数组中每一个元素都是其数据行的该列的值，数组的长度等于 grid.datagrid("getRows") 的长度；
        //          如果传入的列名不存在，则返回数组的长度同样等于 grid.datagrid("getRows") 的长度，只是每个元素的值都为 undefined.
        getColumnData: function (jq, field) { return getColumnData(jq[0], field); }
    };

    var defaults = $.fn.datagrid.extensions.defaults = {

    };

    $.extend($.fn.datagrid.defaults, defaults);
    $.extend($.fn.datagrid.methods, methods);

})(jQuery);



/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI datagrid 组件扩展
* jeasyui.extensions.datagrid.getCellData.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-11-25
*
* 依赖项：
*   1、jquery.jdirk.js
*   2、jeasyui.extensions.datagrid.getRowData.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function () {

    $.util.namespace("$.fn.datagrid.extensions");


    var getCellData = function (target, pos) {
        if (!pos || !pos.field || !$.isNumeric(pos.index) || pos.index < 0) { return; }
        var t = $(target), row = t.datagrid("getRowData", pos.index);
        return row ? row[pos.field] : row;
    };

    var methods = $.fn.datagrid.extensions.methods = {

        //  扩展 easyui-datagrid 的自定义方法；获取 easyui-datagrid 中当前页指定单元格的数据；该函数定义如下参数：
        //  pos：表示单元格的位置，为一个 JSON-Object 对象，该 JSON 定义如下属性：
        //          field:  表示要获取的单元格位于哪个列；
        //          index:  表示要获取的单元格位于哪个行的行索引号，从 0 开始；
        //  返回值：如果当前页存在指定列的指定行，则返回该列中指定行及指定列的单元格数据；否则返回 undefined。
        getCellData: function (jq, pos) { return getCellData(jq[0], pos); }
    };

    var defaults = $.fn.datagrid.extensions.defaults = {

    };

    $.extend($.fn.datagrid.defaults, defaults);
    $.extend($.fn.datagrid.methods, methods);

})(jQuery);



/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI datagrid 组件扩展
* jeasyui.extensions.datagrid.moveRow.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-12-01
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function () {

    $.util.namespace("$.fn.datagrid.extensions");


    //  param: { target: number, source: number, point: string("top"/default, "bottom") }
    function moveRow(target, param) {
        if (!param || (param.source == null || param.source == undefined)
            || (param.target == null || param.target == undefined)
            || (param.point != "top" && param.point != "bottom")) {
            return;
        }
        var t = $(target),
            rows = t.datagrid("getRows");
        if (!rows || !rows.length) {
            return;
        }
        var sourceIndex = param.source,
            targetIndex = param.target,
            sourceRow = rows[sourceIndex],
            targetRow = rows[targetIndex];
        if (sourceIndex == targetIndex || sourceRow == undefined || targetRow == undefined) {
            return;
        }
        var state = $.data(target, "datagrid"),
            opts = state.options;
        if ($.isFunction(opts.onBeforeMoveRow) && opts.onBeforeMoveRow.call(target, targetRow, sourceRow, param.point) == false) {
            return;
        }
        t.datagrid("deleteRow", sourceIndex);

        var index = t.datagrid("getRowIndex", targetRow);
        if (param.point == "top") {
            t.datagrid("insertRow", { index: index, row: sourceRow });
        } else {
            rows = t.datagrid("getRows");
            if (index++ >= rows.length) {
                t.datagrid("appendRow", sourceRow);
            } else {
                t.datagrid("insertRow", { index: index, row: sourceRow });
            }
        }
        if ($.isFunction(opts.onMoveRow)) {
            opts.onMoveRow.call(target, targetRow, sourceRow, param.point);
        }
    }

    //  param: { index: number, type: string("up"/default, "down") }
    var shiftRow = function (target, param) {
        var sourceIndex = param.index,
            targetIndex = param.type == "up" ? param.index - 1 : param.index + 1,
            point = param.type == "up" ? "top" : "bottom";
        moveRow(target, {
            source: sourceIndex,
            target: targetIndex,
            point: point
        });
    };

    var methods = $.fn.datagrid.extensions.methods = {

        //  扩展 easyui-datagrid 的自定义方法；移动 easyui-datagrid 中的指定 data-row(数据行) ；该方法的参数 param 为 JSON-Object 类型，包含如下属性定义：
        //      target: 表示目标位置的 data-row(数据行) 索引号(从 0 开始计数)；
        //      source: 表示要移动的 data-row(数据行) 索引号(从 0 开始计数)；
        //      point:  表示移动到目标节点 target 的位置，String 类型，可能的值包括：
        //          "top":      表示移动到目标位置 target 的上一格位置；
        //          "bottom":   表示追加为目标位置 target 的下一格位置；
        //  返回值：返回表示当前 easyui-datagrid 的 jQuery 链式对象。
        //  备注：该方法会触发移动行数据的相应事件；
        moveRow: function (jq, param) { return jq.each(function () { moveRow(this, param); }); },

        //  扩展 easyui-datagrid 的自定义方法；移动 easyui-datagrid 中的指定 data-row(数据行) 一行位置；该方法的参数 param 为 JSON-Object 类型，包含如下属性定义：
        //      index: 表示要移动的 data-row(数据行) 索引号(从 0 开始计数)；
        //      type:  表示移动到目标节点 target 的位置，String 类型，可能的值包括：
        //          "up":      表示移动到上一格位置；
        //          "down":   表示移动到下一格位置；
        //  返回值：返回表示当前 easyui-datagrid 的 jQuery 链式对象。
        //  备注：该方法会触发移动行数据的相应事件；
        shiftRow: function (jq, param) { return jq.each(function () { shiftRow(this, param); }); }
    };

    var defaults = $.fn.datagrid.extensions.defaults = {

        //  扩展 easyui-datagrid 的自定义事件；该事件表示移动 data-row(数据行) 之前触发的动作；该事件回调函数提供如下三个参数：
        //      target: 表示目标位置的 data-row(数据行) 索引号(从 0 开始计数)；
        //      source: 表示要移动的 data-row(数据行) 索引号(从 0 开始计数)；
        //      point:  表示移动到目标节点 target 的位置，String 类型，可能的值包括：
        //          "top":      表示移动到目标位置 target 的上一格位置；
        //          "bottom":   表示追加为目标位置 target 的下一格位置；
        //  该事件函数中的 this 指向当前 easyui-datagrid 的 DOM 对象(非 jQuery 对象)；
        //  如果该事件函数返回 false，则会立即停止移动数据行操作；
        onBeforeMoveRow: function (target, source, point) { },

        //  扩展 easyui-datagrid 的自定义事件；该事件表示移动 data-row(数据行) 之后触发的动作；该事件回调函数提供如下三个参数：
        //      target: 表示目标位置的 data-row(数据行) 索引号(从 0 开始计数)；
        //      source: 表示要移动的 data-row(数据行) 索引号(从 0 开始计数)；
        //      point:  表示移动到目标节点 target 的位置，String 类型，可能的值包括：
        //          "top":      表示移动到目标位置 target 的上一格位置；
        //          "bottom":   表示追加为目标位置 target 的下一格位置；
        //  该事件函数中的 this 指向当前 easyui-datagrid 的 DOM 对象(非 jQuery 对象)；
        onMoveRow: function (target, source, point) { }
    };

    $.extend($.fn.datagrid.defaults, defaults);
    $.extend($.fn.datagrid.methods, methods);

})(jQuery);


/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI datagrid 组件扩展
* jeasyui.extensions.datagrid.operateColumn.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-12-18
*
* 依赖项：
*   1、jquery.jdirk.js
*   2、jeasyui.extensions.datagrid.getColumnInfo.js
*   3、jeasyui.extensions.datagrid.getDom.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function () {

    $.util.namespace("$.fn.datagrid.extensions");



    function unfreezeColumn(target, field) {
        var state = $.data(target, "datagrid");
        if (state.multiLineHeaders) {
            $.error("不支持在多行表头情况下执行取消冻结表格列的操作。");
        }
        var t = $(target), fields = t.datagrid("getColumnFields", false)
        if ($.array.contains(fields, field)) {
            return;
        }
        t.datagrid("moveColumn", {
            target: fields[0],
            source: field,
            point: "before"
        });
    }

    function freezeColumn(target, field) {
        var state = $.data(target, "datagrid");
        if (state.multiLineHeaders) {
            $.error("不支持在多行表头情况下执行冻结表格列的操作。");
        }
        var t = $(target), frozenFields = t.datagrid("getColumnFields", true),
            isFrozen = isFrozenColumn(target, field, frozenFields);
        if (isFrozen) { return; }
        t.datagrid("moveColumn", {
            target: frozenFields[frozenFields.length - 1],
            source: field,
            point: "after"
        });
    }




    //  param: { field: string, point: string("before"/default, "after") }
    function shiftColumn(target, param) {
        if (!param || !param.field || (param.point != "before" && param.point != "after")) { return; }
        var state = $.data(target, "datagrid");
        if (state.multiLineHeaders) {
            $.error("不支持在多行表头情况下执行移动表格列的操作。");
        }
        var sourceField = param.field,
            targetColumn = param.point == "before" ? getPrevVisibleColumn(target, sourceField) : getNextVisibleColumn(target, sourceField);
        if (!targetColumn || !targetColumn.field) { return; }
        var t = $(target), targetField = targetColumn.field;
        t.datagrid("moveColumn", {
            target: targetField,
            source: sourceField,
            point: param.point
        });
    }

    function getPrevVisibleColumn(target, field) {
        if (!field) { return undefined; }
        var t = $(target), cols = t.datagrid("getColumnOptions", "all");
        if (!cols || !cols.length) { return undefined; }
        var index = -1;
        return $.array.last(cols, function (val, i) {
            if (val.field == field) {
                index = i;
            }
            return index != -1 && i < index && !val.hidden ? true : false;
        });
    }

    function getNextVisibleColumn(target, field) {
        if (!field) { return undefined; }
        var t = $(target), cols = t.datagrid("getColumnOptions", "all");
        if (!cols || !cols.length) { return undefined; }
        var index = -1;
        return $.array.first(cols, function (val, i) {
            if (val.field == field) {
                index = i;
            }
            return index != -1 && i > index && !val.hidden ? true : false;
        });
    }




    //  param: { target: string/field, source: string/field, point: string("before"/default, "after") }
    function moveColumn(target, param) {
        if (!param || !param.target || !param.source || param.target == param.source || (param.point != "before" && param.point != "after")) {
            return;
        }
        var state = $.data(target, "datagrid"),
            opts = state.options;
        if (state.multiLineHeaders) {
            $.error("不支持在多行表头情况下执行移动表格列的操作。");
        }
        var t = $(target),
            targetField = param.target,
            sourceField = param.source,
            fields = t.datagrid("getColumnFields", "all"),
            targetIndex = $.array.indexOf(fields, targetField),
            sourceIndex = $.array.indexOf(fields, sourceField);
        if (targetIndex == -1 || sourceIndex == -1 || targetIndex == sourceIndex) {
            return;
        }
        var sourceColumnOpt = t.datagrid("getColumnOption", sourceField);
        if (sourceColumnOpt.movable != undefined && sourceColumnOpt.movable == false) { return; }
        var frozenFields = t.datagrid("getColumnFields", true),
            targetFrozen = isFrozenColumn(target, targetField, frozenFields),
            sourceFrozen = isFrozenColumn(target, sourceField, frozenFields),
            point = param.point;
        if (sourceIndex == (point == "before" ? targetIndex - 1 : targetIndex + 1) && (targetFrozen == sourceFrozen)) {
            return;
        }
        if ($.isFunction(opts.onBeforeMoveColumn) && opts.onBeforeMoveColumn.call(target, sourceField, targetField, point) == false) {
            return;
        }
        var p = t.datagrid("getPanel"),
            v = p.find("div.datagrid-view"),
            v1 = v.find("div.datagrid-view1"),
            v2 = v.find("div.datagrid-view2"),
            hr1 = v1.find("div.datagrid-header table.datagrid-htable tr.datagrid-header-row"),
            hr2 = v2.find("div.datagrid-header table.datagrid-htable tr.datagrid-header-row"),
            br1 = v1.find("div.datagrid-body table.datagrid-btable tr.datagrid-row"),
            br2 = v2.find("div.datagrid-body table.datagrid-btable tr.datagrid-row"),
            targetHeaderTd = (targetFrozen ? hr1 : hr2).find("td[field=" + targetField + "]"),
            sourceHeaderTd = (sourceFrozen ? hr1 : hr2).find("td[field=" + sourceField + "]"),
            targetRow = targetFrozen ? br1 : br2,
            sourceRow = sourceFrozen ? br1 : br2,
            targetCopts = t.datagrid("getColumnOption", targetField),
            sourceCopts = sourceColumnOpt,
            targetColumns = targetFrozen ? opts.frozenColumns[0] : opts.columns[0],
            sourceColumns = sourceFrozen ? opts.frozenColumns[0] : opts.columns[0];

        targetHeaderTd[point](sourceHeaderTd);
        targetRow.each(function (i) {
            var targetTd = $(this).find("td[field=" + targetField + "]"),
                sourceTd = $(sourceRow[i]).find("td[field=" + sourceField + "]");
            targetTd[point](sourceTd);
        });

        $.array.remove(sourceColumns, sourceCopts);
        var targetCoptsIndex = $.array.indexOf(targetColumns, targetCopts);
        $.array.insert(targetColumns, point == "before" ? targetCoptsIndex : targetCoptsIndex + 1, sourceCopts);

        t.datagrid("fixColumnSize");
        if (sourceFrozen) {
            if (!targetFrozen) {
                var index = $.array.indexOf(state.columnOptions, targetCopts, function (col) { return col.field == targetField; });
                $.array.insert(state.columnOptions, point == "before" ? index : index + 1, sourceCopts);
                $.array.insert(state.originalColumnOptions, point == "before" ? index : index + 1, $.extend({}, sourceCopts));
            }
        } else {
            var index = $.array.indexOf(state.columnOptions, sourceCopts, function (col) { return col.field == sourceField; });
            if (targetFrozen) {
                $.array.removeAt(state.columnOptions, index);
                $.array.removeAt(state.originalColumnOptions, index);
            } else {
                var copts = state.columnOptions[index],
                    bcopts = state.originalColumnOptions[index];
                $.array.removeAt(state.columnOptions, index);
                $.array.removeAt(state.originalColumnOptions, index);

                var tindex = $.array.indexOf(state.columnOptions, targetCopts, function (col) { return col.field == targetField; });
                $.array.insert(state.columnOptions, point == "before" ? tindex : tindex + 1, copts);
                $.array.insert(state.originalColumnOptions, point == "before" ? tindex : tindex + 1, bcopts);
            }
        }

        if ($.isFunction(opts.onMoveColumn)) {
            opts.onMoveColumn.call(target, sourceField, targetField, point);
        }
    }

    function popColumn(target, field) {
        var state = $.data(target, "datagrid");
        if (state.multiLineHeaders) {
            $.error("不支持在多行表头情况下执行删除表格列的操作。");
        }
        var t = $(target),
            copts = t.datagrid("getColumnOption", field);
        if (copts) {
            t.datagrid("deleteColumn", field);
            return copts;
        } else {
            return undefined;
        }
    }



    function deleteColumn(target, field) {
        var state = $.data(target, "datagrid");
        if (state.multiLineHeaders) {
            $.error("不支持在多行表头情况下执行删除表格列的操作。");
        }
        var opts = state.options;
        if ($.isFunction(opts.onBeforeDeleteColumn) && opts.onBeforeDeleteColumn.call(target, field) == false) {
            return;
        }
        var frozen = isFrozenColumn(target, field), removed = removeField(target, field, frozen);
        if (removed) {
            var t = $(target);
            t.datagrid("getColumnDom", { field: field, header: true }).remove();
            if (frozen) {
                t.datagrid("fixColumnSize");
            }
            if ($.isFunction(opts.onDeleteColumn)) {
                opts.onDeleteColumn.call(target, field);
            }
        }
    }

    function removeField(target, field, frozen) {
        var t = $(target),
            state = $.data(target, "datagrid"),
            opts = state.options,
            frozen = frozen == undefined ? isFrozenColumn(target, field) : frozen;
        return remove(frozen ? opts.frozenColumns : opts.columns);
        function remove(columns) {
            if (!columns || !columns.length) {
                return false;
            }
            var ret = false;
            $.each(columns, function (index, cols) {
                var i = $.array.indexOf(cols, field, function (col) {
                    return col.field == field;
                });
                if (i > -1) {
                    $.array.removeAt(cols, i);
                    ret = true;
                    $.array.remove(state.columnOptions, field, function (col) {
                        return col.field == field;
                    });
                    $.array.remove(state.originalColumnOptions, field, function (col) {
                        return col.field == field;
                    });
                }
            });
            return ret;
        }
    }

    function isFrozenColumn(target, field, frozenFields) {
        if (!field) {
            return undefined;
        }
        var fields = frozenFields && frozenFields.length ? frozenFields : $(target).datagrid("getColumnFields", true);
        return $.array.contains(fields, field);
    }



    function isMultiLineHeaders(target) {
        var state = $.data(target, "datagrid"),
            opts = state.options;
        if (opts.columns && opts.columns.length > 1 && opts.columns[0].length && opts.columns[1].length) {
            return true;
        }
        if (opts.frozenColumns && opts.frozenColumns.length > 1 && opts.frozenColumns[0].length && opts.frozenColumns[1].length) {
            return true;
        }
        return false;
    }

    function initExtendColumnOptions(t, opts) {
        var target = t[0],
            state = $.data(target, "datagrid"),
            cols = t.datagrid("getColumnOptions", "all");
        $.each(cols, function (i, col) {
            $.union(col, $.fn.datagrid.extensions.columnOptions);
        });
        var columnOptions = t.datagrid("getColumnOptions", false);
        state.columnOptions = $.array.filter(columnOptions, function (col) {
            return col.title ? true : false;
        });
        state.originalColumnOptions = $.array.map(state.columnOptions, function (col) {
            return $.extend({}, col);
        });
        state.multiLineHeaders = isMultiLineHeaders(target);
    }

    function initializeExtensions(target) {
        var t = $(target),
            state = $.data(target, "datagrid"),
            opts = state.options;

        initExtendColumnOptions(t, opts);
    }

    var _datagrid = $.fn.datagrid.extensions._datagrid = $.fn.datagrid;
    $.fn.datagrid = function (options, param) {
        if (typeof options == "string") {
            return _datagrid.apply(this, arguments);
        }
        options = options || {};
        return this.each(function () {
            var jq = $(this),
                isInited = $.data(this, "datagrid") ? true : false,
                opts = isInited ? options : $.extend({},
                        $.fn.datagrid.parseOptions(this), options);
            _datagrid.call(jq, opts, param);
            if (!isInited) {
                initializeExtensions(this);
            }
        });
    };
    $.union($.fn.datagrid, _datagrid);



    //  增加了 easyui-datagrid 中列 columnOption 的部分自定义扩展属性
    var columnOptions = $.fn.datagrid.extensions.columnOptions = {

        // 表示该列是否可移动，其值可以是 Boolean 类型；
        // 默认为 true。
        // 该属性用户在“移动列”时判定
        movable: true
    };

    var methods = $.fn.datagrid.extensions.methods = {

        //  扩展 easyui-datagrid 的自定义方法；删除指定的列；该方法的参数 field 表示要删除的列的 field 值；
        //  返回值：返回表示当前 easyui-datagrid 的 jQuery 链式对象。
        deleteColumn: function (jq, field) { return jq.each(function () { deleteColumn(this, field); }); },

        //  扩展 easyui-datagrid 的自定义方法；删除指定的列并返回该列的 ColumnOption 值；该方法的参数 field 表示要删除的列的 field 值；
        //  返回值：返回参数 field 值所表示的列的 ColumnOption 值。如果当前 easyui-datagrid 不存在该列，则返回 undefined。
        popColumn: function (jq, field) { return popColumn(jq[0], field); },

        //  扩展 easyui-datagrid 的自定义方法；移动指定的列到另一位置；该方法的参数 param 为一个 JSON-Object，定义包含如下属性：
        //      target: 表示目标位置列的 field 值；
        //      source: 表示要移动的列的 field 值；
        //      point:  表示移动到目标列的位置，String 类型，可选的值包括：
        //          before: 表示将 source 列移动至 target 列的左侧；
        //          after:  表示将 source 列移动值 target 列的右侧；
        //  返回值：返回表示当前 easyui-datagrid 的 jQuery 链式对象。
        //  注意：此方法在多行表头情况下无效。
        moveColumn: function (jq, param) { return jq.each(function () { moveColumn(this, param); }); },

        //  扩展 easyui-datagrid 的自定义方法；移动指定的列挪动一格位置；该方法的参数 param 为一个 JSON-Object，定义包含如下属性：
        //      field:  表示要挪动的列的 field 值；
        //      porint: 表示挪动 field 列的方式，String 类型，可选的值包括：
        //          before: 表示将该列向左挪动一格；
        //          after:  表示将该列向右挪动一格；
        //  返回值：返回表示当前 easyui-datagrid 的 jQuery 链式对象。
        //  注意：此方法在多行表头情况下无效。
        shiftColumn: function (jq, param) { return jq.each(function () { shiftColumn(this, param); }); },

        //  扩展 easyui-datagrid 的自定义方法；冻结指定的列；该方法的参数 field 表示要冻结的列的 field 值。
        //  返回值：返回表示当前 easyui-datagrid 的 jQuery 链式对象。
        //  注意：此方法在多行表头情况下无效。
        //      当前表格在执行此方法前必须存在至少一个冻结列，否则此方法无效；
        freezeColumn: function (jq, field) { return jq.each(function () { freezeColumn(this, field); }); },

        //  扩展 easyui-datagrid 的自定义方法；取消冻结指定的列；该方法的参数 field 表示要取消冻结的列的 field 值。
        //  返回值：返回表示当前 easyui-datagrid 的 jQuery 链式对象。
        //  注意：此方法在多行表头情况下无效。
        //      当前表格在执行此方法前必须存在至少一个非冻结列，否则此方法无效；
        unfreezeColumn: function (jq, field) { return jq.each(function () { unfreezeColumn(this, field); }); }
    };

    var defaults = $.fn.datagrid.extensions.defaults = {

        //  扩展 easyui-datagrid 的自定义事件；该事件表示删除指定的列前触发的动作；该事件回调函数提供如下参数：
        //      field:  表示要被删除的列的 field 值。
        //  备注：如果该事件回调函数返回 false，则不进行删除列的操作。
        //  该事件函数中的 this 指向当前 easyui-datagrid 的 DOM 对象(非 jQuery 对象)；
        onBeforeDeleteColumn: function (field) { },

        //  扩展 easyui-datagrid 的自定义事件；该事件表示删除指定的列后触发的动作；该事件回调函数提供如下参数：
        //      field:  表示要被删除的列的 field 值。
        //  该事件函数中的 this 指向当前 easyui-datagrid 的 DOM 对象(非 jQuery 对象)；
        onDeleteColumn: function (field) { },

        //  扩展 easyui-datagrid 的自定义事件；该事件表示移动指定的列前触发的动作；该事件回调函数提供如下参数：
        //      source:  表示要被移动的列的 field 值。
        //      target:  表示目标位置的列的 field 值。
        //      point :  表示移动的方式；这是一个 String 类型值，可能的值包括：
        //          "before":   表示将列 source 移动至列 target 的前一格位置；
        //          "after" :   表示将列 source 移动至列 target 的后一格位置；
        //  备注：如果该事件回调函数返回 false，则不进行移动列的操作。
        //  该事件函数中的 this 指向当前 easyui-datagrid 的 DOM 对象(非 jQuery 对象)；
        onBeforeMoveColumn: function (source, target, point) { },

        //  扩展 easyui-datagrid 的自定义事件；该事件表示移动指定的列后触发的动作；该事件回调函数提供如下参数：
        //      source:  表示要被移动的列的 field 值。
        //      target:  表示目标位置的列的 field 值。
        //      point :  表示移动的方式；这是一个 String 类型值，可能的值包括：
        //          "before":   表示将列 source 移动至列 target 的前一格位置；
        //          "after" :   表示将列 source 移动至列 target 的后一格位置；
        //  该事件函数中的 this 指向当前 easyui-datagrid 的 DOM 对象(非 jQuery 对象)；
        onMoveColumn: function (source, target, point) { }
    };

    $.extend($.fn.datagrid.defaults, defaults);
    $.extend($.fn.datagrid.methods, methods);

})(jQuery);


/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI datagrid 组件扩展
* jeasyui.extensions.datagrid.operateRow.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-12-19
*
* 依赖项：
*   1、jquery.jdirk.js
*   2、jeasyui.extensions.datagrid.findRow.js
*   3、jeasyui.extensions.datagrid.getDom.js
*   4、jeasyui.extensions.datagrid.getRowData.js
*   5、jeasyui.extensions.datagrid.moveRow.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function () {

    $.util.namespace("$.fn.datagrid.extensions");


    //  param: idField | row | function (row, index, rows)
    function showRow(target, param) {
        var state = $.data(target, "datagrid"),
            t = $(target),
            opts = state.options,
            rows = t.datagrid("getRows");

        var row = t.datagrid("findRow", param),
            index = t.datagrid("getRowIndex", row);
        if (index > -1) {
            $.array.remove(state.hiddenRows, row);
            t.datagrid("getRowDom", index).removeClass("datagrid-row-hidden");
        }
    }

    //  param: idField | row | function (row, index, rows)
    function hideRow(target, param) {
        var state = $.data(target, "datagrid"),
            t = $(target),
            opts = state.options,
            rows = t.datagrid("getRows");

        var row = t.datagrid("findRow", param),
            index = t.datagrid("getRowIndex", row);
        if (index > -1) {
            $.array.attach(state.hiddenRows, row);
            t.datagrid("unselectRow", index).datagrid("uncheckRow", index);
            t.datagrid("getRowDom", index).addClass("datagrid-row-hidden");
        }
    }

    //  param: function (row, index, rows) | array (idField | row | function (row, index, rows)) | boolean (false)
    function hideRows(target, param) {
        var t = $(target),
            state = $.data(target, "datagrid"),
            opts = state.options;
        if (param == true) {
            var rows = t.datagrid("getRows");
            $.array.clear(state.hiddenRows);
            $.array.copy(state.hiddenRows, rows);
            $.each(rows, function (i, n) {
                var rowIndex = t.datagrid("getRowIndex", n);
                if (rowIndex > -1) {
                    t.datagrid("getRowDom", rowIndex).addClass("datagrid-row-hidden");
                }
            });
        } else {
            var array = t.datagrid("findRows", param);
            if (array.length) {
                $.each(array, function (i, n) {
                    hideRow(target, n);
                });
            }
        }
    }

    //  param: function (index, row, rows) | array (idField | row | function (index, row, rows)) | boolean (true)
    function showRows(target, param) {
        var t = $(target),
            state = $.data(target, "datagrid"),
            opts = state.options;
        if (param == true) {
            var rows = t.datagrid("getRows");
            $.array.clear(state.hiddenRows);
            $.each(rows, function (i, n) {
                var rowIndex = t.datagrid("getRowIndex", n);
                if (rowIndex > -1) {
                    t.datagrid("getRowDom", rowIndex).removeClass("datagrid-row-hidden");
                }
            });
        } else {
            var array = t.datagrid("findRows", param);
            if (array.length) {
                $.each(array, function (i, n) {
                    showRow(target, n);
                });
            }
        }
    }

    function getHiddenRows(target) {
        var state = $.data(target, "datagrid");
        return state.hiddenRows;
    }

    function getVisibleRows(target) {
        var state = $.data(target, "datagrid"), t = $(target),
            rows = t.datagrid("getRows"), hiddenRows = state.hiddenRows;

        return $.array.filter(rows, function (val) { return $.array.contains(hiddenRows, val) ? false : true; });
    }




    function setDraggableStatus(source, state) {
        var icon = source.draggable("proxy").find("span.tree-dnd-icon");
        icon.removeClass("tree-dnd-yes tree-dnd-no").addClass(state ? "tree-dnd-yes" : "tree-dnd-no");
    }

    function getTrIndex(tr) {
        if (!tr) {
            return -1;
        }
        tr = $.util.isJqueryObject(tr) ? tr : $(tr);
        var attr = tr.attr("datagrid-row-index");
        return (attr == null || attr == undefined || attr == "") ? -1 : window.parseInt(attr, 10);
    }

    function getEditingRowIndexes(target) {
        var t = $(target),
            p = t.datagrid("getPanel"),
            indexes = [];
        p.find("div.datagrid-view div.datagrid-body table.datagrid-btable:first tr.datagrid-row.datagrid-row-editing").each(function () {
            var index = getTrIndex(this);
            if (index != -1) {
                indexes.push(index);
            }
        });
        return indexes;
    }

    function resetRowDnd(target, index, t, opts, row, tr) {
        t = t || $(target);
        opts = opts || $.data(target, "datagrid").options;
        if (!opts.rowDnd) { return; }
        row = row || t.datagrid("getRowData", index);
        tr = tr || t.datagrid("getRowDom", index);
        tr.each(function () {
            if ($.data(this, "draggable")) { return; }
            $(this).draggable({
                disabled: false, revert: true, edge: 5, delay: 300, cursor: "default", deltaX: 10, deltaY: 5,
                proxy: function (source) {
                    var tr = $("<tr><td><span class='tree-dnd-icon tree-dnd-no' >&nbsp;</span></td></tr>").addClass("datagrid-row datagrid-row-selected"),
                        cells = t.datagrid("getRowDom", index).clone().find("td").removeClass("datagrid-row-over").each(function (i) {
                            if (i < 8) { tr.append(this); }
                        });
                    if (cells.length > 8) {
                        $("<td style='width: 40px;'>...</td>").appendTo(tr);
                    }
                    return $("<table class='tree-node-proxy'></table>").append(tr).appendTo("body").hide();
                },
                onBeforeDrag: function (e) {
                    if (!opts.rowDnd || e.which != 1 || e.target.type == "checkbox" || getEditingRowIndexes(target).length) {
                        return false;
                    }
                    if ($.isFunction(opts.onRowBeforeDrag) && opts.onRowBeforeDrag.call(target, index, row) == false) {
                        return false;
                    }
                    setRowsDroppable();
                },
                onStartDrag: function (e) {
                    $(this).draggable("proxy").css({
                        left: -10000, top: -10000
                    });
                    if ($.isFunction(opts.onRowStartDrag)) {
                        opts.onRowStartDrag.call(target, index, row);
                    }
                },
                onStopDrag: function () {
                    if ($.isFunction(opts.onRowStopDrag)) {
                        opts.onRowStopDrag.call(target, index, row);
                    }
                },
                onDrag: function (e) {
                    var x1 = e.pageX, y1 = e.pageY,
                        x2 = e.data.startX, y2 = e.data.startY,
                        d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
                    if (d > 15) {
                        $(this).draggable("proxy").show();
                    }
                    this.pageY = e.pageY;
                }
            });
        });
        function setRowsDroppable() {
            t.datagrid("getPanel").find("div.datagrid-view div.datagrid-body table.datagrid-btable tr.datagrid-row[datagrid-row-index]").each(function () {
                if ($.data(this, "droppable")) { return; }
                $(this).droppable({
                    accept: "tr.datagrid-row[datagrid-row-index]",
                    onDragEnter: function (e, source) {
                        var dragger = $(source),
                            dropper = $(this),
                            dragIndex = window.parseInt(dragger.attr("datagrid-row-index"), 10),
                            dropIndex = window.parseInt(dropper.attr("datagrid-row-index"), 10),
                            rows = t.datagrid("getRows"),
                            dragRow = rows[dragIndex],
                            dropRow = rows[dropIndex],
                            dropTr = t.datagrid("getRowDom", dropIndex),
                            mark = dropTr.find("td"),
                            dnd = {
                                dragger: dragger,
                                dropper: dropper,
                                dragIndex: dragIndex,
                                dropIndex: dropIndex,
                                dragRow: dragRow,
                                dropRow: dropRow,
                                mark: mark
                            };
                        $.data(this, "datagrid-row-dnd", dnd);
                        if ($.isFunction(opts.onRowDragEnter) && opts.onRowDragEnter.call(target, dropRow, dragRow) == false) {
                            setDraggableStatus(dragger, false);
                            mark.removeClass("datagrid-row-dnd-top datagrid-row-dnd-bottom");
                            dropper.droppable("disable");
                        }
                    },
                    onDragOver: function (e, source) {
                        var dropper = $(this),
                            dopts = dropper.droppable("options");
                        if (dopts.disabled) { return; }
                        var dnd = $.data(this, "datagrid-row-dnd"),
                            dragger = dnd.dragger,
                            mark = dnd.mark,
                            pageY = source.pageY,
                            top = dropper.offset().top,
                            height = top + dropper.outerHeight(),
                            cls = pageY > top + (height - top) / 2
                                ? "datagrid-row-dnd-bottom"
                                : "datagrid-row-dnd-top";
                        setDraggableStatus(dragger, true);
                        mark.removeClass("datagrid-row-dnd-top datagrid-row-dnd-bottom").addClass(cls);

                        if ($.isFunction(opts.onRowDragOver) && opts.onRowDragOver.call(target, dnd.dropDow, dnd.dragRow) == false) {
                            setDraggableStatus(dragger, false);
                            mark.removeClass("datagrid-row-dnd-top datagrid-row-dnd-bottom");
                            dropper.droppable("disable");
                        }
                    },
                    onDragLeave: function (e, source) {
                        var dnd = $.data(this, "datagrid-row-dnd"),
                            dragger = dnd.dragger,
                            mark = dnd.mark;
                        setDraggableStatus(dragger, false);
                        mark.removeClass("datagrid-row-dnd-top datagrid-row-dnd-bottom");
                        if ($.isFunction(opts.onRowDragLeave)) {
                            opts.onRowDragLeave.call(target, dnd.dropDow, dnd.dragRow);
                        }
                    },
                    onDrop: function (e, source) {
                        var dnd = $.data(this, "datagrid-row-dnd"),
                            mark = dnd.mark,
                            point = mark.hasClass("datagrid-row-dnd-top") ? "top" : "bottom";
                        if ($.isFunction(opts.onRowBeforeDrop) && opts.onRowBeforeDrop.call(target, dnd.dropDow, dnd.dragRow, point) == false) {
                            mark.removeClass("datagrid-row-dnd-top datagrid-row-dnd-bottom");
                            return false;
                        }
                        t.datagrid("moveRow", {
                            target: dnd.dropIndex,
                            source: dnd.dragIndex,
                            point: point
                        });
                        mark.removeClass("datagrid-row-dnd-top datagrid-row-dnd-bottom");
                        if ($.isFunction(opts.onRowDrop)) {
                            opts.onRowDrop.call(target, dnd.dropDow, dnd.dragRow, point);
                        }
                    }
                });
            });
        }
    }

    function initRowMouseEvent(t, opts) {
        var target = t[0];
        t.datagrid("getPanel").panel("body").delegate("tr.datagrid-row", {
            "mouseenter.datagrid-extensions": function (e) {
                var tr = $(this);
                if (tr.is(".datagrid-row-editing")) {
                    return;
                }
                var index = getTrIndex(tr);
                if (index == -1) {
                    return;
                }
                var row = t.datagrid("getRowData", index);
                resetRowDnd(target, index, t, opts, row, tr);
            },
            "mouseleave.datagrid-extensions": function () {
                //var tr = $(this),
                //    index = getTrIndex(tr);
                //if (index == -1) {
                //    return;
                //}
            }
        });
    }

    function initHeaderFiltersData(t, opts) {
        var target = t[0],
            state = $.data(target, "datagrid");
        state.hiddenRows = [];
    }

    function initializeExtensions(target) {
        var t = $(target),
            state = $.data(target, "datagrid"),
            opts = state.options;

        initHeaderFiltersData(t, opts);
        initRowMouseEvent(t, opts);
    }

    var _datagrid = $.fn.datagrid.extensions._datagrid = $.fn.datagrid;
    $.fn.datagrid = function (options, param) {
        if (typeof options == "string") {
            return _datagrid.apply(this, arguments);
        }
        options = options || {};
        return this.each(function () {
            var jq = $(this),
                isInited = $.data(this, "datagrid") ? true : false,
                opts = isInited ? options : $.extend({},
                        $.fn.datagrid.parseOptions(this), options);
            _datagrid.call(jq, opts, param);
            if (!isInited) {
                initializeExtensions(this);
            }
        });
    };
    $.union($.fn.datagrid, _datagrid);



    var methods = $.fn.datagrid.extensions.methods = {

        //  扩展 easyui-datagrid 的自定义方法；隐藏当前 easyui-datagrid 当前页数据中指定行的数据；该方法的参数 param 可以是以下两种类型：
        //      待查找的行数据的 idField(主键) 字段值；
        //      function 类型，该回调函数签名为 function(row, index, rows)，其中 row 表示行数据对象、index 表示行索引号、rows 表示当前 easyui-datagrid 调用 getRows 返回的结果集；
        //          如果 param 参数为 function 类型，则 findRow 方法会对当前 easyui-datagrid 的当前页的每一行数据调用该回调函数；
        //          当回调函数返回 true 时，则表示找到需要查找的结果，立即停止循环调用并隐藏该行数据；
        //          如果回调函数始终未返回 true，则该回调函数会一直遍历 rows 直到最后。
        //  返回值：返回表示当前 easyui-datagrid 组件的 jQuery 链式对象。
        hideRow: function (jq, param) { return jq.each(function () { hideRow(this, param); }); },

        //  扩展 easyui-datagrid 的自定义方法；显示当前 easyui-datagrid 当前页数据中指定行的数据；该方法的参数 param 可以是以下两种类型：
        //      待查找的行数据的 idField(主键) 字段值；
        //      function 类型，该回调函数签名为 function(row, index, rows)，其中 row 表示行数据对象、index 表示行索引号、rows 表示当前 easyui-datagrid 调用 getRows 返回的结果集；
        //          如果 param 参数为 function 类型，则 findRow 方法会对当前 easyui-datagrid 的当前页的每一行数据调用该回调函数；
        //          当回调函数返回 true 时，则表示找到需要查找的结果，立即停止循环调用并显示该行数据；
        //          如果回调函数始终未返回 true，则该回调函数会一直遍历 rows 直到最后。
        //  返回值：返回表示当前 easyui-datagrid 组件的 jQuery 链式对象。
        showRow: function (jq, param) { return jq.each(function () { showRow(this, param); }); },

        //  扩展 easyui-datagrid 的自定义方法；隐藏当前 easyui-datagrid 当前页数据中指定多行的数据；该方法的参数 param 可以是以下三种类型：
        //      Function 类型，该回调函数签名为 function(row, index, rows)，其中 row 表示行数据对象、index 表示行索引号、rows 表示当前 easyui-datagrid 调用 getRows 返回的结果集；
        //          如果 param 参数为 Function 类型，则 hideRows 方法会对当前 easyui-datagrid 的当前页的每一行数据调用该回调函数；
        //          当回调函数返回 true 时，则该行数据将会被隐藏；
        //      Array 类型，数组中的每一项都可以定义为如下类型：
        //          待查找的行数据的 idField(主键) 字段值；
        //          Function 类型；具体回调函数签名参考 hideRow 方法中 param 参数为 function 类型时的定义；
        //          当 param 参数定义为 Array 类型时，则 hideRows 方法会对数组中的每一项循环调用 hideRow 方法；
        //      Boolean 类型且为 true：则 hideRows 将会隐藏 easyui-datagrid 当前页的所有数据。
        //  返回值：返回表示当前 easyui-datagrid 组件的 jQuery 链式对象。
        hideRows: function (jq, param) { return jq.each(function () { hideRows(this, param); }); },

        //  扩展 easyui-datagrid 的自定义方法；显示当前 easyui-datagrid 当前页数据中指定多行的数据；该方法的参数 param 可以是以下三种类型：
        //      Function 类型，该回调函数签名为 function(row, index, rows)，其中 row 表示行数据对象、index 表示行索引号、rows 表示当前 easyui-datagrid 调用 getRows 返回的结果集；
        //          如果 param 参数为 Function 类型，则 showRows 方法会对当前 easyui-datagrid 的当前页的每一行数据调用该回调函数；
        //          当回调函数返回 true 时，则该行数据将会被显示；
        //      Array 类型，数组中的每一项都可以定义为如下类型：
        //          待查找的行数据的 idField(主键) 字段值；
        //          Function 类型；具体回调函数签名参考 showRow 方法中 param 参数为 function 类型时的定义；
        //          当 param 参数定义为 Array 类型时，则 showRows 方法会对数组中的每一项循环调用 showRow 方法；
        //      Boolean 类型且为 true：则 showRows 将会显示 easyui-datagrid 当前页的所有数据。
        //  返回值：返回表示当前 easyui-datagrid 组件的 jQuery 链式对象。
        showRows: function (jq, param) { return jq.each(function () { showRows(this, param); }); },

        //  扩展 easyui-datagrid 的自定义方法；获取当前 easyui-datagrid 当前页所有隐藏的行数据所构成的一个 Array 对象。
        getHiddenRows: function (jq) { return getHiddenRows(jq[0]); },

        //  扩展 easyui-datagrid 的自定义方法；获取当前 easyui-datagrid 当前页所有显示的行数据所构成的一个 Array 对象。
        getVisibleRows: function (jq) { return getVisibleRows(jq[0]); }
    };

    var defaults = $.fn.datagrid.extensions.defaults = {

        //  扩展 easyui-datagrid 的自定义属性，该属性表示是否启用表格的行节点拖动功能；
        //  Boolean 类型值，默认为 false。
        rowDnd: false,

        //  扩展 easyui-datagrid 的自定义事件；该事件表示拖动 data-row(数据行) 之前触发的动作；该事件回调函数提供如下两个参数：
        //      index: 表示要拖动的 data-row(数据行) 索引号(从 0 开始计数)；
        //      row:   表示被拖动的 data-row(数据行) 的行数据对象，是一个 JSON-Object；
        //  该事件函数中的 this 指向当前 easyui-datagrid 的 DOM 对象(非 jQuery 对象)；
        //  备注：如果该事件函数返回 false，则取消当前的拖动 data-row(数据行) 操作。
        onRowBeforeDrag: function (index, row) { },

        //  扩展 easyui-datagrid 的自定义事件；该事件表示开始拖动 data-row(数据行) 时触发的动作；该事件回调函数提供如下两个参数：
        //      index:  表示被拖动的 data-row(数据行) 的索引号，从 0 开始计数；
        //      row:    表示被拖动的 data-row(数据行) 的行数据对象，是一个 JSON-Object。
        //  该事件函数中的 this 指向当前 easyui-datagrid 的 DOM 对象(非 jQuery 对象)；
        onRowStartDrag: function (index, row) { },

        //  扩展 easyui-datagrid 的自定义事件；该事件表示结束拖动 data-row(数据行) 时触发的动作；该事件回调函数提供如下两个参数：
        //      index:  表示被拖动的 data-row(数据行) 的索引号，从 0 开始计数；
        //      row:    表示被拖动的 data-row(数据行) 的行数据对象，是一个 JSON-Object。
        //  该事件函数中的 this 指向当前 easyui-datagrid 的 DOM 对象(非 jQuery 对象)；
        onRowStopDrag: function (index, row) { },

        //  扩展 easyui-datagrid 的自定义事件；该事件表示当有其他的 data-row(数据行) 被拖动至当前 data-row(数据行) 时所触发的动作；该事件回调函数提供如下两个参数：
        //      targetRow: 表示当前 data-row(数据行) 的行数据对象，是一个 JSON-Object；
        //      sourceRow: 表示拖动过来的 data-row(数据行) 行数据对象，是一个 JSON-Object。
        //  该事件函数中的 this 指向当前 easyui-datagrid 的 DOM 对象(非 jQuery 对象)；
        //  备注：如果该事件函数返回 false，则立即取消当前的 data-row(数据行) 接收拖动过来对象的操作，并禁用当前 data-row(数据行) 的 droppable 效果；
        onRowDragEnter: function (targetRow, sourceRow) { },

        //  扩展 easyui-datagrid 的自定义事件；该事件表示当有其他的 data-row(数据行) 被拖动至当前 data-row(数据行) 后并在上面移动时所触发的动作；该事件回调函数提供如下两个参数：
        //      targetRow: 表示当前 data-row(数据行) 的行数据对象，是一个 JSON-Object；
        //      sourceRow: 表示拖动过来的 data-row(数据行) 行数据对象，是一个 JSON-Object。
        //  该事件函数中的 this 指向当前 easyui-datagrid 的 DOM 对象(非 jQuery 对象)；
        //  备注：如果该事件函数返回 false，则立即取消当前的 data-row(数据行) 接收拖动过来对象的操作，并禁用当前 data-row(数据行) 的 droppable 效果；
        onRowDragOver: function (targetRow, sourceRow) { },

        //  扩展 easyui-datagrid 的自定义事件；该事件表示当有其他的 data-row(数据行) 被拖动至当前 data-row(数据行) 后并拖动离开时所触发的动作；该事件回调函数提供如下两个参数：
        //      targetRow: 表示当前 data-row(数据行) 的行数据对象，是一个 JSON-Object；
        //      sourceRow: 表示拖动过来的 data-row(数据行) 行数据对象，是一个 JSON-Object。
        //  该事件函数中的 this 指向当前 easyui-datagrid 的 DOM 对象(非 jQuery 对象)；
        onRowDragLeave: function (targetRow, sourceRow) { },

        //  扩展 easyui-datagrid 的自定义事件；该事件表示放置 data-row(数据行) 之前触发的动作；该事件回调函数提供如下三个参数：
        //      targetRow: 表示目标位置的 data-row(数据行) 索引号(从 0 开始计数)；
        //      sourceRow: 表示要放置的 data-row(数据行) 索引号(从 0 开始计数)；
        //      point:  表示放置到目标节点 target 的位置，String 类型，可能的值包括：
        //          "top":      表示放置到目标位置 target 的上一格位置；
        //          "bottom":   表示追加为目标位置 target 的下一格位置；
        //  该事件函数中的 this 指向当前 easyui-datagrid 的 DOM 对象(非 jQuery 对象)；
        //  如果该事件函数返回 false，则会立即停止放置数据行操作；
        onRowBeforeDrop: function (targetRow, sourceRow, point) { },

        //  扩展 easyui-datagrid 的自定义事件；该事件表示放置 data-row(数据行) 之后触发的动作；该事件回调函数提供如下三个参数：
        //      targetRow: 表示目标位置的 data-row(数据行) 索引号(从 0 开始计数)；
        //      sourceRow: 表示要放置的 data-row(数据行) 索引号(从 0 开始计数)；
        //      point:  表示放置到目标节点 target 的位置，String 类型，可能的值包括：
        //          "top":      表示放置到目标位置 target 的上一格位置；
        //          "bottom":   表示追加为目标位置 target 的下一格位置；
        //  该事件函数中的 this 指向当前 easyui-datagrid 的 DOM 对象(非 jQuery 对象)；
        onRowDrop: function (targetRow, sourceRow, point) { }
    };

    $.extend($.fn.datagrid.defaults, defaults);
    $.extend($.fn.datagrid.methods, methods);

})(jQuery);


/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI datagrid 组件扩展
* jeasyui.extensions.datagrid.editors.js
* 开发 流云
* 由 落阳 整理和二次扩展
* 最近更新：2015-12-03
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function ($) {

    var editors = $.fn.datagrid.defaults.editors,
        text_init = editors.text.init,
        textarea_init = editors.textarea.init,
        checkbox_init = editors.checkbox.init,
        textbox_init = editors.textbox.init,
        validatebox_init = editors.validatebox.init,
        numberbox_init = editors.numberbox.init,
        datebox_init = editors.datebox.init,
        combobox_init = editors.combobox.init,
        combotree_init = editors.combotree.init;


    //  扩展 easyui-datagrid editors-text 的自定义方法；
    //  setFocus，该方法用于聚焦当前编辑控件；
    //  focusable，该方法用于检查当前编辑控件是否可聚焦；
    //  input，该方法用于返回输入框对象；
    $.extend(editors.text, {
        setFocus: function (target) {
            $(target).focus();
        },
        focusable: function (target) {
            return true;
        },
        input: function (target) {
            return $(target);
        }
    });

    //  扩展 easyui-datagrid editors-textarea 的自定义方法；
    //  setFocus，该方法用于聚焦当前编辑控件；
    //  focusable，该方法用于检查当前编辑控件是否可聚焦；
    //  input，该方法用于返回输入框对象；
    $.extend(editors.textarea, {
        setFocus: function (target) {
            $(target).focus();
        },
        focusable: function (target) {
            return true;
        },
        input: function (target) {
            return $(target);
        }
    });

    //  扩展 easyui-datagrid editors-checkbox 的自定义方法；
    //  setFocus，该方法用于聚焦当前编辑控件；
    //  focusable，该方法用于检查当前编辑控件是否可聚焦；
    //  input，该方法用于返回输入框对象；
    $.extend(editors.checkbox, {
        init: function (container, options) {
            return checkbox_init.apply(this, arguments).addClass("datagrid-editable-input datagrid-editable-checkbox");
        },
        setFocus: function (target) {
            $(target).focus();
        },
        focusable: function (target) {
            return true;
        },
        input: function (target) {
            return $(target);
        }
    });

    //  扩展 easyui-datagrid editors-validatebox 的自定义方法；
    //  setFocus，该方法用于聚焦当前编辑控件；
    //  focusable，该方法用于检查当前编辑控件是否可聚焦；
    //  input，该方法用于返回输入框对象；
    $.extend(editors.validatebox, {
        setFocus: function (target) {
            $(target).focus();
        },
        focusable: function (target) {
            var t = $(target), opts = t.validatebox("options");
            return !(opts.disabled || opts.readonly);
        },
        input: function (target) {
            return $(target);
        }
    });

    //  扩展 easyui-datagrid editors-textbox 的自定义方法；
    //  setFocus，该方法用于聚焦当前编辑控件；
    //  focusable，该方法用于检查当前编辑控件是否可聚焦；
    //  input，该方法用于返回输入框对象；
    $.extend(editors.textbox, {
        init: function (container, options) {
            var box = textbox_init.apply(this, arguments);
            box.textbox("textbox").addClass("datagrid-editable-input");
            return box;
        },
        setFocus: function (target) {
            $(target).textbox("textbox").focus();
        },
        focusable: function (target) {
            var t = $(target), opts = t.textbox("options");
            return !(opts.disabled || !opts.editable || opts.readonly);
        },
        input: function (target) {
            return $(target).textbox("textbox");
        }
    });

    //  扩展 easyui-datagrid editors-numberbox 的自定义方法；
    //  setFocus，该方法用于聚焦当前编辑控件；
    //  focusable，该方法用于检查当前编辑控件是否可聚焦；
    //  input，该方法用于返回输入框对象；
    $.extend(editors.numberbox, {
        init: function (container, options) {
            var box = numberbox_init.apply(this, arguments);
            box.numberbox("textbox").addClass("datagrid-editable-input");
            return box;
        },
        setFocus: function (target) {
            $(target).numberbox("textbox").focus();
        },
        focusable: function (target) {
            var t = $(target), opts = t.numberbox("options");
            return !(opts.disabled || !opts.editable || opts.readonly);
        },
        input: function (target) {
            return $(target).numberbox("textbox");
        }
    });

    //  扩展 easyui-datagrid editors-datebox 的自定义方法；
    //  setFocus，该方法用于聚焦当前编辑控件；
    //  focusable，该方法用于检查当前编辑控件是否可聚焦；
    //  input，该方法用于返回输入框对象；
    $.extend(editors.datebox, {
        init: function (container, options) {
            var box = datebox_init.apply(this, arguments);
            box.datebox("textbox").addClass("datagrid-editable-input");
            return box;
        },
        setFocus: function (target) {
            $(target).datebox("textbox").focus();
        },
        focusable: function (target) {
            var t = $(target), opts = t.datebox("options");
            return !(opts.disabled || !opts.editable || opts.readonly);
        },
        input: function (target) {
            return $(target).datebox("textbox");
        }
    });

    //  扩展 easyui-datagrid editors-combobox 的自定义方法；
    //  setFocus，该方法用于聚焦当前编辑控件；
    //  focusable，该方法用于检查当前编辑控件是否可聚焦；
    //  input，该方法用于返回输入框对象；
    $.extend(editors.combobox, {
        init: function (container, options) {
            var box = combobox_init.apply(this, arguments);
            box.combobox("textbox").addClass("datagrid-editable-input");
            return box;
        },
        setFocus: function (target) {
            $(target).combobox("textbox").focus();
        },
        focusable: function (target) {
            var t = $(target), opts = t.combobox("options");
            return !(opts.disabled || !opts.editable || opts.readonly);
        },
        input: function (target) {
            return $(target).combobox("textbox");
        }
    });

    //  扩展 easyui-datagrid editors-combotree 的自定义方法；
    //  setFocus，该方法用于聚焦当前编辑控件；
    //  focusable，该方法用于检查当前编辑控件是否可聚焦；
    //  input，该方法用于返回输入框对象；
    $.extend(editors.combotree, {
        init: function (container, options) {
            var box = combotree_init.apply(this, arguments);
            box.combotree("textbox").addClass("datagrid-editable-input");
            return box;
        },
        setFocus: function (target) {
            $(target).combotree("textbox").focus();
        },
        focusable: function (target) {
            var t = $(target), opts = t.combotree("options");
            return !(opts.disabled || !opts.editable || opts.readonly);
        },
        input: function (target) {
            return $(target).combotree("textbox");
        }
    });

})(jQuery);



/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI datagrid 组件扩展
* jeasyui.extensions.datagrid.navigating.js
* 开发 糖古屋
* 由 落阳 整理
* 最近更新：2016-05-10
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 Lixilin personal All rights reserved.
*/
(function () {

    $.util.namespace("$.fn.datagrid.extensions");

    function onNavigatePaginate(paginateType, data) {
        if (paginateType == "prev") {
            $(this).datagrid("selectRow", $(this).datagrid("getRows").length - 1);
        } else {
            $(this).datagrid("selectRow", 0);
        }
    }

    function prevPage(t, opts, callback) {
        if (opts.pagination) {
            var pager = t.datagrid("getPager"), currentPageNumber = pager.pagination("options").pageNumber;
            if (currentPageNumber > 1) {
                //缓存回调给datagrid对象
                opts.successCallBackForNavigating = function (data) {
                    if ($.isFunction(opts.onNavigatePaginate)) { opts.onNavigatePaginate.call(t[0], "prev", data); }
                    if (callback && $.isFunction(callback)) {
                        callback.call(t[0], data);
                    }
                };
                pager.pagination("select", currentPageNumber - 1);
            }
        }
    }

    function nextPage(t, opts, callback) {
        if (opts.pagination) {
            var pager = t.datagrid("getPager"), pagerOpts = pager.pagination("options"),
                currentPageNumber = pagerOpts.pageNumber, recordsTotal = pagerOpts.total, pageSize = pagerOpts.pageSize;
            var totalPage = Math.ceil(recordsTotal / pageSize) || 1;
            if (currentPageNumber < totalPage) {
                //缓存回调给datagrid对象
                opts.successCallBackForNavigating = function (data) {
                    if ($.isFunction(opts.onNavigatePaginate)) { opts.onNavigatePaginate.call(t[0], "next", data); }
                    if (callback && $.isFunction(callback)) {
                        callback.call(t[0]);
                    }
                };
                pager.pagination("select", currentPageNumber + 1);
            }
        }
    }

    function initKeyNavigatingEvent(t, opts) {
        if (!opts.navigatingWithKey) { return; }
        if (opts.pagination) {
            var po = t.datagrid("getPager"), popts = po.pagination("options");
            var _onSelectPage = popts.onSelectPage;
            popts.onSelectPage = function (pn, ps) {
                /*重写 datagrid 的 onSelectPage */
                opts.pageNumber = pn || 1;
                opts.pageSize = ps;
                po.pagination("refresh", { pageNumber: pn, pageSize: ps });

                var queryParams = $.extend({ page: opts.pageNumber, rows: opts.pageSize }, opts.queryParams);
                if (opts.sortName) {
                    $.extend(queryParams, { sort: opts.sortName, order: opts.sortOrder });
                }
                if (opts.onBeforeLoad.call(t[0], queryParams) == false) {
                    return;
                }
                t.datagrid("loading");
                var loadResult = opts.loader.call(t[0], queryParams, function (data) {
                    t.datagrid("loaded");
                    t.datagrid("loadData", data);
                    //执行回调
                    if ($.isFunction(opts.successCallBackForNavigating)) {
                        opts.successCallBackForNavigating.call(t[0], data);
                        opts.successCallBackForNavigating = undefined;
                    }
                }, function () {
                    t.datagrid("loaded");
                    opts.onLoadError.apply(t[0], arguments);
                });
                if (loadResult == false) {
                    t.datagrid("loaded");
                }
            };
        }
        t.datagrid("getPanel").panel("panel").attr('tabindex', 1).off('keydown.navigating').on('keydown.navigating', function (e) {
            switch (e.keyCode) {
                // Up
                case 38:
                    e.preventDefault();
                    var selected = t.datagrid("getSelections");
                    var targetIndex = -1;
                    if (selected && selected.length) {
                        var indexs = $.array.map(selected, function (item) {
                            return t.datagrid("getRowIndex", item);
                        });
                        var index = $.array.min(indexs);
                        if (index > 0) {
                            targetIndex = index - 1;
                            t.datagrid("selectRow", targetIndex);
                            if (opts.navigateHandler && opts.navigateHandler.up && $.isFunction(opts.navigateHandler.up)) {
                                opts.navigateHandler.up.call(t[0], targetIndex);
                            }
                        }
                        else {
                            prevPage(t, opts, function (data) {
                                if ($.util.isObject(data)) {
                                    targetIndex = data.rows.length - 1;
                                } else {
                                    targetIndex = data.length - 1;
                                }
                                if (opts.navigateHandler && opts.navigateHandler.up && $.isFunction(opts.navigateHandler.up)) {
                                    opts.navigateHandler.up.call(t[0], targetIndex);
                                }
                            });
                        }
                    } else {
                        targetIndex = t.datagrid("getRows").length - 1;
                        t.datagrid("selectRow", targetIndex);
                        if (opts.navigateHandler && opts.navigateHandler.up && $.isFunction(opts.navigateHandler.up)) {
                            opts.navigateHandler.up.call(t[0], targetIndex);
                        }
                    }
                    break;
                // Down
                case 40:
                    e.preventDefault();
                    var selected = t.datagrid("getSelections");
                    var targetIndex = -1;
                    if (selected && selected.length) {
                        var indexs = $.array.map(selected, function (item) {
                            return t.datagrid("getRowIndex", item);
                        });
                        var index = $.array.max(indexs), rows = t.datagrid("getRows");
                        if (index < rows.length - 1) {
                            targetIndex = index + 1;
                            t.datagrid("selectRow", targetIndex);
                            if (opts.navigateHandler && opts.navigateHandler.down && $.isFunction(opts.navigateHandler.down)) {
                                opts.navigateHandler.down.call(t[0], targetIndex);
                            }
                        }
                        else {
                            targetIndex = 0;
                            nextPage(t, opts, function () {
                                if (opts.navigateHandler && opts.navigateHandler.down && $.isFunction(opts.navigateHandler.down)) {
                                    opts.navigateHandler.down.call(t[0], targetIndex);
                                }
                            });
                        }
                    } else {
                        targetIndex = 0;
                        t.datagrid("selectRow", targetIndex);
                        if (opts.navigateHandler && opts.navigateHandler.down && $.isFunction(opts.navigateHandler.down)) {
                            opts.navigateHandler.down.call(t[0], targetIndex);
                        }
                    }
                    break;
                // Left
                case 37:
                    e.preventDefault(); prevPage(t, opts);
                    break;
                // Right
                case 39:
                    e.preventDefault(); nextPage(t, opts);
                    break;
                // Enter
                case 13:
                    if (opts.navigateHandler && opts.navigateHandler.enter && $.isFunction(opts.navigateHandler.enter)) {
                        e.preventDefault();
                        var selected = t.datagrid("getSelections");
                        if (selected) {
                            opts.navigateHandler.enter.call(t[0], selected);
                        }
                    }
                    break;
            }
        });
    };

    function initializeExtensions(target) {
        var t = $(target),
            state = $.data(target, "datagrid"),
            opts = state.options;

        initKeyNavigatingEvent(t, opts);
        if (opts.navigatingWithKey) { t.datagrid("getPanel").panel("panel").focus(); }
    }

    var _datagrid = $.fn.datagrid.extensions._datagrid = $.fn.datagrid;
    $.fn.datagrid = function (options, param) {
        if (typeof options == "string") {
            return _datagrid.apply(this, arguments);
        }
        options = options || {};
        return this.each(function () {
            var jq = $(this),
                isInited = $.data(this, "datagrid") ? true : false,
                opts = isInited ? options : $.extend({},
                        $.fn.datagrid.parseOptions(this),
                        $.parser.parseOptions(this, [
                            {
                                navigatingWithKey: "boolean"
                            }
                        ]), options);
            _datagrid.call(jq, opts, param);
            if (!isInited) {
                initializeExtensions(this);
            }
        });
    };
    $.union($.fn.datagrid, _datagrid);

    var methods = $.fn.datagrid.extensions.methods = {

    };

    var defaults = $.fn.datagrid.extensions.defaults = {

        //  扩展 easyui-datagrid 的自定义属性；表示是否开启按键导航功能；
        //      Up 键：selected 数据行上移
        //      Down 键：selected 数据行下移
        //      Left 键：上一页，仅在有上一页时有效
        //      Right 键：下一页，仅在有下一页时有效
        //      Enter 键：触发 datagrid onDblClickRow 事件，仅在有 selected 数据行时有效
        navigatingWithKey: true,

        //  扩展 easyui-datagrid 的自定义事件；表示通过按键导航进行翻页后触发的事件；该事件回调函数提供如下参数：
        //      paginateType:  表示翻页类型，其值可以是 prev、next，分别表示上一页、下一页；
        //      data: 表示翻页后的数据对象，该数据对象可能是 object（含 total、rows 属性），也可能是 array；
        //  该事件函数中的 this 指向当前 easyui-datarid 的 DOM 对象(非 jQuery 对象)；
        onNavigatePaginate: onNavigatePaginate,

        //  扩展 easyui-datagrid 的自定义对象；表示按键导航执行后触发的事件集合；该对象支持如下事件属性：
        //      up:  表示“上”按键导航后触发的事件，该事件的函数签名 targetIndex 表示导航后的数据行的索引；
        //      down:  表示“下”按键导航后触发的事件，该事件的函数签名 targetIndex 表示导航后的数据行的索引；
        //      enter:  表示“回车”按键导航后触发的事件，该事件的函数签名 selectedData 表示已经 selected 的数据行；
        //              注意：selectedData 的类型为 object 还是 array 取决于当前 easyui-datagrid 是单选还是多选；
        //  以上事件函数中的 this 指向当前 easyui-datarid 的 DOM 对象(非 jQuery 对象)；
        navigateHandler: {
            up: function (targetIndex) { },
            down: function (targetIndex) { },
            enter: function (selectedData) { }
        }
    };

    $.extend($.fn.datagrid.defaults, defaults);
    $.extend($.fn.datagrid.methods, methods);

})(jQuery);


/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI datagrid 编辑器扩展-回车聚焦
* jeasyui.extensions.datagrid.edit.enterFocus.js
* 开发 落阳
* 最近更新：2017-03-21
*
* 依赖项：
*   1、jquery.jdirk.js
*   2、jeasyui.extensions.datagrid.rowState.js
*   3、jeasyui.extensions.datagrid.editors.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function () {

    $.util.namespace("$.fn.datagrid.extensions");

    $.fn.datagrid.extensions.getColumnFields = $.fn.datagrid.methods.getColumnFields;
    var getColumnFields = function (target, frozen) {
        var t = $(target);
        if (frozen == null || frozen == undefined) {
            return $.fn.datagrid.extensions.getColumnFields.call(t, t, frozen);
        }
        return $.type(frozen) == "string"
            ? $.array.merge([],
                $.fn.datagrid.extensions.getColumnFields.call(t, t, true),
                $.fn.datagrid.extensions.getColumnFields.call(t, t, false))
            : $.fn.datagrid.extensions.getColumnFields.call(t, t, frozen);
    };

    var getColumnOptions = function (target, frozen) {
        var t = $(target), fields = getColumnFields(target, frozen);
        return $.array.map(fields, function (val) { return t.datagrid("getColumnOption", val); });
    };

    var editorAvailable = function (target, field) {
        var t = $(target), opts = t.datagrid('options'), col = t.datagrid('getColumnOption', field);
        if (!col.editor) { return false; }

        var flag = typeof (col.editor) == "string", editorType = flag ? col.editor : col.editor.type, editorAction = $.fn.datagrid.defaults.editors[editorType];
        if (!editorAction) { editorType = undefined; }
        if (!editorType) { return false; }

        if (flag) { return true; } else {
            return col.editor.options == undefined ? true : (!(col.editor.options.readonly || col.editor.disabled));
        }
    };

    //找到第一个设置了编辑器并且编辑器可用的列的列名
    var getFirstAvailableEditorField = function (target) {
        return getColumnFields(target, "all").first(function (item) { return editorAvailable(target, item); });
    };

    //自动聚焦到下一个列编辑器
    //t：datagrid-jquery对象
    //opts：datagrid-options对象
    //thisArg：jQ对象，可以是 editor、editor的容器（因为editor可能不存在）
    //field：列名
    //index：索引号，寻找同行的下一列编辑器时可传递，其他时候不能传递，需根据thisArg去获取
    var autoNextFieldEditorFocus = function (t, opts, thisArg, field, index) {
        //console.log("整行编辑：定位下一个编辑器");
        var panel = t.datagrid("getPanel"),
            index = index == undefined ? window.parseInt(thisArg.closest("tr[datagrid-row-index]").attr("datagrid-row-index")) : index;
        //console.log("当前列名：" + field);
        //console.log("当前索引：" + index);
        var cellTd = panel.find("div.datagrid-view div.datagrid-body table tr.datagrid-row[datagrid-row-index=" + index + "] td[field=" + field + "]");
        var nextCellTd = cellTd.next();
        if (nextCellTd.length == 0) {
            //下一个列的容器td不存在，视为本行编辑列到达结尾，结束本行编辑
            //console.log("下一个列的容器td不存在，视为本行编辑列到达结尾，结束本行编辑");
            t.datagrid("endEdit", index);

            var rows = t.datagrid("getRows");
            if (!rows || !rows.length) { return; }
            var len = rows.length;
            if (index < len - 1) {
                if (!t.datagrid("isEditing", index + 1)) {
                    t.datagrid("beginEdit", index + 1);
                } else {
                    var editableField = getFirstAvailableEditorField(t);
                    if (!editableField) {
                        return;
                    }
                    var editorNextRow = t.datagrid("getEditor", { field: editableField, index: index + 1 });
                    if (editorNextRow.actions && $.isFunction(editorNextRow.actions.setFocus)) {
                        if ($.isFunction(editorNextRow.actions.focusable) && editorNextRow.actions.focusable(editorNextRow.target[0]) == true) {
                            editorNextRow.actions.setFocus(editorNextRow.target[0]);
                        }
                        else {
                            //不可聚焦，这不科学
                        }
                    } else {
                        editorNextRow.target.focus();
                    }
                }
            } else {
                opts.onAfterFoucsLastEditor.call(t[0], index, field);
            }

            return;
        }
        var nextField = nextCellTd.attr("field"),
            nextCell = nextCellTd.find("div.datagrid-cell"),
            editor = t.datagrid("getEditor", { field: nextField, index: index });
        if (!editor) {
            //下一个列的editor不存在，说明该列无编辑器，寻找下一个列的editor
            //console.log("下一个列的editor不存在，说明该列无编辑器，寻找下一个列的editor");
            autoNextFieldEditorFocus(t, opts, nextCell, nextField, index);
        }
        else {
            if (editor.actions && $.isFunction(editor.actions.setFocus)) {
                if ($.isFunction(editor.actions.focusable) && editor.actions.focusable(editor.target[0]) == true) {
                    editor.actions.setFocus(editor.target[0]);
                }
                else {
                    //不可聚焦，跳过，寻找下一个列的editor
                    //console.log("不可聚焦，跳过，寻找下一个列的editor");
                    autoNextFieldEditorFocus(t, opts, editor.target, nextField, index);
                }
            } else {
                editor.target.focus();
            }
        }
    }

    //绑定“自动聚焦下一个列的编辑器”事件
    //t：datagrid-jquery对象
    //opts：datagrid-options对象
    //input：输入框jquery对象
    //field：当前在编辑的列名
    var bindAutoFocusNextFieldEditor = function (t, opts, input, field) {
        input.keydown(function (e) {
            if (e.which == 13) {
                var val = $(this).val();
                if (opts.stopEnterFocusWhenEmpty && $.string.isNullOrWhiteSpace(val)) { return; }
                autoNextFieldEditorFocus(t, opts, $(this), field);
            }
        });
    };

    // 初始化事件绑定
    function initExtendEventBind(t, opts) {

        if (opts.enterFocusNextEditor) {
            //开始编辑事件
            var _onBeginEdit = opts.onBeginEdit;
            opts.onBeginEdit = function (index, row) {
                if ($.isFunction(_onBeginEdit)) { _onBeginEdit.call(this, index, row); }

                var fields = getColumnFields(t[0], "all"), editors = fields.map(function (item) { return t.datagrid("getEditor", { index: index, field: item }); });
                if (editors.length == 0) { return; }

                editors.forEach(function (item) {
                    if (!item) { return; }
                    if (item.actions && $.isFunction(item.actions.input)) {
                        var theInput = item.actions.input(item.target[0]);
                        bindAutoFocusNextFieldEditor(t, opts, theInput, item.field);
                    } else {
                        bindAutoFocusNextFieldEditor(t, opts, item.target, item.field);
                    }
                });

                $.util.delay(function () {
                    //自动聚焦第一个可聚焦的编辑器
                    for (var i = 0; i < editors.length; i++) {
                        var editor = editors[i];
                        if (!editor) { continue; }
                        if (editor.actions && $.isFunction(editor.actions.setFocus)) {
                            if ($.isFunction(editor.actions.focusable) && editor.actions.focusable(editor.target[0]) == true) {
                                editor.actions.setFocus(editor.target[0]); break;
                            }
                            else {
                                //不可聚焦，跳过
                            }
                        } else {
                            editor.target.focus(); break;
                        }
                    }
                });
            };
        }
    }

    function initializeExtensions(target) {
        var t = $(target),
            state = $.data(target, "datagrid"),
            opts = state.options;

        initExtendEventBind(t, opts);
    }

    var _datagrid = $.fn.datagrid.extensions._datagrid = $.fn.datagrid;
    $.fn.datagrid = function (options, param) {
        if (typeof options == "string") {
            return _datagrid.apply(this, arguments);
        }
        options = options || {};
        return this.each(function () {
            var jq = $(this),
                isInited = $.data(this, "datagrid") ? true : false,
                opts = isInited ? options : $.extend({},
                        $.fn.datagrid.parseOptions(this),
                        $.parser.parseOptions(this, [
                            {
                                enterFocusNextEditor: "boolean",
                                stopEnterFocusWhenEmpty: "boolean"
                            }
                        ]), options);
            _datagrid.call(jq, opts, param);
            if (!isInited) {
                initializeExtensions(this);
            }
        });
    };
    $.union($.fn.datagrid, _datagrid);


    var methods = $.fn.datagrid.extensions.methods = {

        //  扩展 easyui-datagrid 的自定义方法；检查指定列是否配置了可用（非只读且未禁用）编辑器；该方法定义如下参数：
        //      field: 列的 field 名；
        //  返回值：若指定列配置了可用（非只读且未禁用）编辑器则返回 true ，否则返回 false 。
        editorAvailable: function (jq, field) { return editorAvailable(jq[0], field); }
    };

    var defaults = $.fn.datagrid.extensions.defaults = {


        //  扩展 easyui-datagrid 的自定义属性；表示开启编辑后是否启用回车后自动聚焦下一个列编辑器的功能；
        //  Boolean 类型值，默认 true。
        enterFocusNextEditor: true,

        //  扩展 easyui-datagrid 的自定义属性；表示在 autoFocusNextFieldEditor 为 true 的前提下，当前编辑器内容若为空，是否停止回车聚焦功能；
        //  Boolean 类型值，默认 false。
        stopEnterFocusWhenEmpty: false,

        //  扩展 easyui-datagrid 的自定义事件；表示聚焦到最后一个单元格编辑器之后触发的事件；该事件定义如下参数：
        //      index: 数据行的索引；
        //      field: 列的 field 名；
        //  注意，该事件接口是指“聚焦到最后一行最后一个单元格编辑器之后触发的事件”，并不是每个数据行的最后一个单元格编辑器聚焦后触发。
        onAfterFoucsLastEditor: function (index, field) { }
    };

    $.extend($.fn.datagrid.defaults, defaults);
    $.extend($.fn.datagrid.methods, methods);

})(jQuery);


// 行号显示不全
(function () {
    $.extend($.fn.datagrid.methods, {
        fixRownumber : function (jq) {
            return jq.each(function () {
                var panel = $(this).datagrid("getPanel");
                //获取最后一行的number容器,并拷贝一份
                var clone = $(".datagrid-cell-rownumber", panel).last().clone();
                //由于在某些浏览器里面,是不支持获取隐藏元素的宽度,所以取巧一下
                clone.css({
                    "position" : "absolute",
                    left : -1000
                }).appendTo("body");
                var width = clone.width("auto").width();
                //默认宽度是25,所以只有大于25的时候才进行fix
                if (width > 25) {
                    //多加5个像素,保持一点边距
                    $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);
                    //修改了宽度之后,需要对容器进行重新计算,所以调用resize
                    $(this).datagrid("resize");
                    //一些清理工作
                    clone.remove();
                    clone = null;
                } else {
                    //还原成默认状态
                    $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");
                }
            });
        }
    });
})(jQuery);







