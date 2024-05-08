

/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI combotree 扩展
* jeasyui.extensions.combotree.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2016-02-29
*
* 依赖项：
*   1、jquery.jdirk.js
*   2、jeasyui.extensions.tree.xxx.js (想继承 easyui-tree 的什么扩展，就引用相应的扩展文件。)
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.combotree.extensions");

    var defaults = $.fn.combotree.extensions.defaults = ($.fn.tree && $.fn.tree.extensions) ? $.extend({}, $.fn.tree.extensions.defaults, {

        //  扩展 easyui-combotree 的自定义属性；更改继承于 easyui-tree 的自定义属性 toggleOnClick 的默认值，使得 easyui-combotree 中 tree 组件的页节点在点击后不自动展开/折叠子节点；
        //  Boolean 类型，默认为 false。
        toggleOnClick: false
    }) : {};

    var methods = $.fn.combotree.extensions.methods = {


    };


    $.extend($.fn.combotree.defaults, defaults);
    $.extend($.fn.combotree.methods, methods);

})(jQuery);


/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI tree 扩展
* jeasyui.extensions.tree.getNodes.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-10-20
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.tree.extensions");

    var getNodes = function (target, cascade) {
        var t = $(target), roots = t.tree("getRoots"), opts = t.tree("options");
        roots = roots && roots.length ? roots : [];
        return cascade ? $.array.reduce(roots, function (prev, val, index) {
            prev.push(val);
            var cc = t.tree("getChildren", val.target);
            if (cc && cc.length) { $.array.merge(prev, cc); }
            return prev;
        }, []) : roots;
    };

    var defaults = $.fn.tree.extensions.defaults = {

    };

    var methods = $.fn.tree.extensions.methods = {

        //  扩展 easyui-tree 的自定义方法；获取 easyui-tree 中的所有节点数据(包括根节点和子节点)所构成的一个集合；该方法的参数 cascade 定义为如下类型：
        //      Boolean 类型，默认为 false，表示是否连同子级节点数据一并返回；
        //  返回值：返回一个 Array 数组对象，数组中的每一个元素都表示一个 node；
        //      如果 cascade 为 true，则返回所有根节点以及子节点合并所构成的一个数组；
        //      如果 cascade 为 false，则仅返回所有根节点数据，同 getRoots 方法；
        //      如果 easyui-tree 中没有数据，则返回一个长度为 0 的数组。
        getNodes: function (jq, cascade) { return getNodes(jq[0], cascade); }
    };


    $.extend($.fn.tree.defaults, defaults);
    $.extend($.fn.tree.methods, methods);

})(jQuery);


/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI tree 扩展
* jeasyui.extensions.tree.findNodes.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-10-20
*
* 依赖项：
*   1、jquery.jdirk.js
*   2、jeasyui.extensions.tree.getNodes.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.tree.extensions");

    var findNodes = function (target, param) {
        var t = $(target), ret;
        if ($.isFunction(param)) {
            ret = $.array.filter(t.tree("getNodes", true), param);
        } else if ($.array.likeArray(param) && !$.util.isString(param)) {
            ret = $.array.map(param, function (val) { return t.tree("find", val); });
            ret = $.array.filter(ret, function (val) { return val != undefined && val != null; });
        } else {
            ret = [];
        }
        return ret;
    };

    var defaults = $.fn.tree.extensions.defaults = {

    };

    var methods = $.fn.tree.extensions.methods = {

        //  扩展 easyui-tree 的自定义方法；获取当前 easyui-tree 中的指定节点数据集合并返回；该方法的参数 param 可以定义为如下两种类型：
        //      Function 类型，该回调函数签名为 function(node, index, nodes)，其中 node 表示节点数据对象、index 表示行索引号、nodes 表示当前 easyui-tree 所有节点对象集合；
        //          如果 param 参数为 Function 类型，则 findNodes 方法会对当前 easyui-tree 中的每一个节点数据调用该回调函数；
        //          当回调函数返回 true 时，则返回的结果集中将会包含该行数据；
        //          如果该回调函数始终未返回 true，则该方法最终返回一个长度为 0 的数组对象。
        //      Array 类型，数组中的每一项都可以定义为如下类型：
        //          待查找的行数据的 idField(主键) 字段值；
        //          当 param 参数定义为 Array 类型时，则 findNodes 方法会对数组中的每一项循环调用 find 方法，并过滤掉 find 方法返回 null 的结果行；
        //  返回值：返回一个 Array 数组对象；数组中的每一项都是 JSON-Object 类型，表示一个节点数据对象；如果未找到相应数据，则返回一个长度为 0 的数组对象。
        findNodes: function (jq, param) { return findNodes(jq[0], param); }
    };


    $.extend($.fn.tree.defaults, defaults);
    $.extend($.fn.tree.methods, methods);

})(jQuery);

/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI tree 扩展
* jeasyui.extensions.tree.isRoot.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-10-21
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.tree.extensions");

    function isRoot(target, nodeTarget) {
        var t = $(target),
            nodeDOM = $(nodeTarget)[0],
            roots = t.tree("getRoots");
        return $.array.some(roots, function (node) {
            return node.target == nodeDOM;
        });
    }

    var defaults = $.fn.tree.extensions.defaults = {

    };

    var methods = $.fn.tree.extensions.methods = {

        //  扩展 easyui-tree 的自定义方法；判断指定的 tree-node 是否为根节点；该方法定义如下参数：
        //      target: 用于判断的 tree-node 的 jQuery 或 DOM 对象。
        //  返回值：如果指定的 jQuery 对象是该 easyui-tree 的根节点，则返回 true，否则返回 false。
        isRoot: function (jq, target) { return isRoot(jq[0], target); }
    };


    $.extend($.fn.tree.defaults, defaults);
    $.extend($.fn.tree.methods, methods);

})(jQuery);


/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI tree 扩展
* jeasyui.extensions.tree.next.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-10-21
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.tree.extensions");

    function next(target, nodeTarget) {
        var node = $(nodeTarget);
        if (!node.is(".tree-node")) {
            return null;
        }
        var other = node.closest("li").next().children(".tree-node");
        if (!other.length) {
            return null;
        }
        return other.length ? $(target).tree("getNode", other[0]) : null;
    }

    var defaults = $.fn.tree.extensions.defaults = {

    };

    var methods = $.fn.tree.extensions.methods = {

        //  扩展 easyui-tree 的自定义方法；获取指定节点的平级下一格位置的 tree-node 节点；该方法定义如下参数：
        //      target:  指定的表示 tree-node 的 jQuery 或 DOM 对象。
        //  返回值：返回 tree-node target 的同级别下一格位置的 tree-node 节点 node 对象；该 node 对象含有如下属性：
        //      id、text、iconCls、checked、state、attributes、target；
        //      如果该 tree-node target 为当前级别的最后一个节点即没有下一格节点；则返回 null。
        next: function (jq, target) { return next(jq[0], target); }
    };


    $.extend($.fn.tree.defaults, defaults);
    $.extend($.fn.tree.methods, methods);

})(jQuery);


/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI tree 扩展
* jeasyui.extensions.tree.prev.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-10-21
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.tree.extensions");

    function prev(target, nodeTarget) {
        var node = $(nodeTarget);
        if (!node.is(".tree-node")) {
            return null;
        }
        var other = node.closest("li").prev().children(".tree-node");
        if (!other.length) {
            return null;
        }
        return other.length ? $(target).tree("getNode", other[0]) : null;
    }

    var defaults = $.fn.tree.extensions.defaults = {

    };

    var methods = $.fn.tree.extensions.methods = {

        //  扩展 easyui-tree 的自定义方法；获取指定节点的平级上一格位置的 tree-node 节点；该方法定义如下参数：
        //      target:  指定的表示 tree-node 的 jQuery 或 DOM 对象。
        //  返回值：返回 tree-node target 的同级别上一格位置的 tree-node 节点对象；该 tree-node 对象含有如下属性：
        //      id、text、iconCls、checked、state、attributes、target；
        //      如果该 tree-node target 为当前级别的第一个节点即没有上一格节点；则返回 null。
        prev: function (jq, target) { return prev(jq[0], target); }
    };


    $.extend($.fn.tree.defaults, defaults);
    $.extend($.fn.tree.methods, methods);

})(jQuery);


/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI tree 扩展
* jeasyui.extensions.tree.getNears.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-10-21
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.tree.extensions");

    function getNears(treeTarget, target) {
        var t = $(treeTarget); target = $(target);
        if (!$.contains(t[0], target[0]) || !target.is("div.tree-node")) { return null; }

        return target.closest("ul").children("li").children("div.tree-node").map(function () {
            return t.tree("getNode", this);
        });
    };

    var defaults = $.fn.tree.extensions.defaults = {

    };

    var methods = $.fn.tree.extensions.methods = {

        //  扩展 easyui-tree 的自定义方法；获取指定节点的同级所有节点(包含自身)；该方法定义如下参数：
        //      target:  指定的表示 tree-node 的 jQuery 或 DOM 对象。
        //  返回值：返回 tree-node target 的同级别(具有和当前 target 同一个父级节点)所有节点构成的一个数组对象；
        //      数组中每一个元素都是一个包含属性 id、text、iconCls、checked、state、attributes、target 的 tree-node 对象。
        //      如果传入的参数 target 是根节点或者未定义 target 参数，则该方法和 getRoots 方法返回的值相同；
        //      如果传入的参数 target 不是一个 div.tree-node 或者其不包含在当前 easyui-tree 中，则返回 null。
        getNears: function (jq, target) { return getNears(jq[0], target); }
    };


    $.extend($.fn.tree.defaults, defaults);
    $.extend($.fn.tree.methods, methods);

})(jQuery);



/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI tree 扩展
* jeasyui.extensions.tree.unselect.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-10-21
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.tree.extensions");

    function unselect(treeTarget, target) {
        $(target).removeClass("tree-node-selected");
    };

    var defaults = $.fn.tree.extensions.defaults = {

    };

    var methods = $.fn.tree.extensions.methods = {

        //  扩展 easyui-tree 的自定义方法；用于取消指定树节点的选择状态；该方法定义如下参数：
        //      target:  指定的表示 tree-node 的 jQuery 或 DOM 对象。
        //  返回值：返回表示当前 easyui-tree 组件的 jQuery 对象。
        unselect: function (jq, target) { return jq.each(function () { unselect(this, target); }); }
    };


    $.extend($.fn.tree.defaults, defaults);
    $.extend($.fn.tree.methods, methods);

})(jQuery);



/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI tree 扩展
* jeasyui.extensions.tree.setText.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-10-21
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.tree.extensions");

    function setNodeText(target, param) {
        if (!param || !param.target || !param.text) { return; }
        $(target).tree("update", { target: param.target, text: param.text })
    };

    var defaults = $.fn.tree.extensions.defaults = {

    };

    var methods = $.fn.tree.extensions.methods = {

        //  扩展 easyui-tree 的自定义方法；设置指定节点的显示文本；该方法定义如下参数：
        //      param: JSON-Object 类型值，该对象包含如下属性定义：
        //          target: 表示要设置图标的 easyui-tree node HTML-DOM 对象；
        //          text  : 表示要设置的显示文本值；
        //  返回值：返回表示当前 easyui-tree 组件的 jQuery 对象。
        setText: function (jq, param) { return jq.each(function () { setNodeText(this, param); }); }
    };


    $.extend($.fn.tree.defaults, defaults);
    $.extend($.fn.tree.methods, methods);

})(jQuery);



/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI tree 扩展
* jeasyui.extensions.tree.setIcon.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-10-21
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.tree.extensions");

    function setNodeIcon(target, param) {
        if (!param || !param.target || !param.iconCls) { return; }
        $(target).tree("update", { target: param.target, iconCls: param.iconCls })
    };

    var defaults = $.fn.tree.extensions.defaults = {

    };

    var methods = $.fn.tree.extensions.methods = {

        //  扩展 easyui-tree 的自定义方法；设置指定节点的图标；该方法定义如下参数：
        //      param: JSON-Object 类型值，该对象包含如下属性定义：
        //          target: 表示要设置图标的 easyui-tree node HTML-DOM 对象；
        //          iconCls:表示要设置的节点样式；
        //  返回值：返回表示当前 easyui-tree 组件的 jQuery 对象。
        setIcon: function (jq, param) { return jq.each(function () { setNodeIcon(this, param); }); }
    };


    $.extend($.fn.tree.defaults, defaults);
    $.extend($.fn.tree.methods, methods);

})(jQuery);


/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI tree 扩展
* jeasyui.extensions.tree.toggleOnClick.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-10-22
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.tree.extensions");

    function initToggleOnClick(t, opts) {
        t.delegate(".tree-node", "click", function (e) {
            if (!$(e.target).is(".tree-hit") && opts.toggleOnClick) {
                t.tree("toggle", this);
            }
        });
    }

    function initializeExtensions(target) {
        var t = $(target),
            state = $.data(target, "tree"),
            opts = t.tree("options");
        initToggleOnClick(t, opts);
    }

    var _tree = $.fn.tree.extensions._tree = $.fn.tree;
    $.fn.tree = function (options, param) {
        if (typeof options == "string") {
            return _tree.apply(this, arguments);
        }
        options = options || {};
        return this.each(function () {
            var jq = $(this),
                isInited = $.data(this, "tree") ? true : false,
                opts = isInited ? options : $.extend({},
                        $.fn.tree.parseOptions(this),
                        $.parser.parseOptions(this, [
                            {
                                toggleOnClick: "boolean"
                            }
                        ]), options);
            _tree.call(jq, opts, param);
            if (!isInited) {
                initializeExtensions(this);
            }
        });
    };
    $.union($.fn.tree, _tree);

    var defaults = $.fn.tree.extensions.defaults = {

        //  扩展 easyui-tree 的自定义属性，表示当左键点击带有子节点的条目时，是否自动展开/折叠相应节点。
        //  Boolean 类型，默认为 false。
        //  备注：该功能不会影响到 easyui-tree 的原生事件 onClick。
        toggleOnClick: false
    };

    var methods = $.fn.tree.extensions.methods = {


    };


    $.extend($.fn.tree.defaults, defaults);
    $.extend($.fn.tree.methods, methods);

})(jQuery);


/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI tree 扩展
* jeasyui.extensions.tree.getTheRoot.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-10-22
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.tree.extensions");

    var getTheRoot = function (treeTarget, target) {
        if (target == undefined) { return null; }
        var t = $(treeTarget); node = $(target);
        if (!$.contains(t[0], target) || !node.is("div.tree-node")) { return null; }

        var parentNode = t.tree("getParent", target);
        if (parentNode == null) { return t.tree("getNode", target); }
        var rootNode;
        while (parentNode != null) {
            var temp = t.tree("getParent", parentNode.target);
            if (temp != null) { parentNode = temp; }
            else { rootNode = parentNode; parentNode = null; }
        }
        return rootNode;
    };

    var defaults = $.fn.tree.extensions.defaults = {

    };

    var methods = $.fn.tree.extensions.methods = {

        //  扩展 easyui-tree 的自定义方法；获取指定节点的根节点；该方法定义如下参数：
        //      target:  指定的表示 tree-node 的 jQuery 或 DOM 对象。
        //  返回值：返回 tree-node target 的根节点(具有和当前 target 同一个关系树)对象；
        //      如果传入的参数 target 是根节点，则返回 target 节点对象；
        //      如果传入的参数 target 未定义，则返回 null；
        //      如果传入的参数 target 不是一个 div.tree-node 或者其不包含在当前 easyui-tree 中，则返回 null。
        getTheRoot: function (jq, target) {
            return getTheRoot(jq[0], target);
        }
    };


    $.extend($.fn.tree.defaults, defaults);
    $.extend($.fn.tree.methods, methods);

})(jQuery);


/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI tree 扩展
* jeasyui.extensions.tree.getLevel.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-10-21
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function ($) {

    $.util.namespace("$.fn.tree.extensions");

    function getLevel(target, nodeTarget) {
        var t = $(target),
            node = $(nodeTarget);
        if (!t[0] || !node[0] || !node.is(".tree-node") || !$.contains(t[0], node[0])) {
            return -1;
        }
        return node.parentsUntil("ul.tree", "ul").length;
    }

    var defaults = $.fn.tree.extensions.defaults = {

    };

    var methods = $.fn.tree.extensions.methods = {

        // 扩展 easyui-tree 的自定义方法；用于获取指定节点的级别；该方法的参数 target 表示要获取级别的 tree-node 节点的 jQuery 或 DOM 对象；
        // 返回值：如果 nodeTarget 表示的 DOM 对象存在于此 easyui-tree，则返回表示其所在节点级别的数字(从 0 开始计数)，否则返回 -1。
        getLevel: function (jq, nodeTarget) { return getLevel(jq[0], nodeTarget); }
    };


    $.extend($.fn.tree.defaults, defaults);
    $.extend($.fn.tree.methods, methods);

})(jQuery);
