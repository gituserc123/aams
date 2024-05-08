/*! JSON v3.3.2 | http://bestiejs.github.io/json3 | Copyright 2012-2014, Kit Cambridge | http://kit.mit-license.org */
(function(){function N(p,r){function q(a){if(q[a]!==w)return q[a];var c;if("bug-string-char-index"==a)c="a"!="a"[0];else if("json"==a)c=q("json-stringify")&&q("json-parse");else{var e;if("json-stringify"==a){c=r.stringify;var b="function"==typeof c&&s;if(b){(e=function(){return 1}).toJSON=e;try{b="0"===c(0)&&"0"===c(new t)&&'""'==c(new A)&&c(u)===w&&c(w)===w&&c()===w&&"1"===c(e)&&"[1]"==c([e])&&"[null]"==c([w])&&"null"==c(null)&&"[null,null,null]"==c([w,u,null])&&'{"a":[1,true,false,null,"\\u0000\\b\\n\\f\\r\\t"]}'==
c({a:[e,!0,!1,null,"\x00\b\n\f\r\t"]})&&"1"===c(null,e)&&"[\n 1,\n 2\n]"==c([1,2],null,1)&&'"-271821-04-20T00:00:00.000Z"'==c(new C(-864E13))&&'"+275760-09-13T00:00:00.000Z"'==c(new C(864E13))&&'"-000001-01-01T00:00:00.000Z"'==c(new C(-621987552E5))&&'"1969-12-31T23:59:59.999Z"'==c(new C(-1))}catch(f){b=!1}}c=b}if("json-parse"==a){c=r.parse;if("function"==typeof c)try{if(0===c("0")&&!c(!1)){e=c('{"a":[1,true,false,null,"\\u0000\\b\\n\\f\\r\\t"]}');var n=5==e.a.length&&1===e.a[0];if(n){try{n=!c('"\t"')}catch(d){}if(n)try{n=
1!==c("01")}catch(g){}if(n)try{n=1!==c("1.")}catch(m){}}}}catch(X){n=!1}c=n}}return q[a]=!!c}p||(p=k.Object());r||(r=k.Object());var t=p.Number||k.Number,A=p.String||k.String,H=p.Object||k.Object,C=p.Date||k.Date,G=p.SyntaxError||k.SyntaxError,K=p.TypeError||k.TypeError,L=p.Math||k.Math,I=p.JSON||k.JSON;"object"==typeof I&&I&&(r.stringify=I.stringify,r.parse=I.parse);var H=H.prototype,u=H.toString,v,B,w,s=new C(-0xc782b5b800cec);try{s=-109252==s.getUTCFullYear()&&0===s.getUTCMonth()&&1===s.getUTCDate()&&
10==s.getUTCHours()&&37==s.getUTCMinutes()&&6==s.getUTCSeconds()&&708==s.getUTCMilliseconds()}catch(Q){}if(!q("json")){var D=q("bug-string-char-index");if(!s)var x=L.floor,M=[0,31,59,90,120,151,181,212,243,273,304,334],E=function(a,c){return M[c]+365*(a-1970)+x((a-1969+(c=+(1<c)))/4)-x((a-1901+c)/100)+x((a-1601+c)/400)};(v=H.hasOwnProperty)||(v=function(a){var c={},e;(c.__proto__=null,c.__proto__={toString:1},c).toString!=u?v=function(a){var c=this.__proto__;a=a in(this.__proto__=null,this);this.__proto__=
c;return a}:(e=c.constructor,v=function(a){var c=(this.constructor||e).prototype;return a in this&&!(a in c&&this[a]===c[a])});c=null;return v.call(this,a)});B=function(a,c){var e=0,b,f,n;(b=function(){this.valueOf=0}).prototype.valueOf=0;f=new b;for(n in f)v.call(f,n)&&e++;b=f=null;e?B=2==e?function(a,c){var e={},b="[object Function]"==u.call(a),f;for(f in a)b&&"prototype"==f||v.call(e,f)||!(e[f]=1)||!v.call(a,f)||c(f)}:function(a,c){var e="[object Function]"==u.call(a),b,f;for(b in a)e&&"prototype"==
b||!v.call(a,b)||(f="constructor"===b)||c(b);(f||v.call(a,b="constructor"))&&c(b)}:(f="valueOf toString toLocaleString propertyIsEnumerable isPrototypeOf hasOwnProperty constructor".split(" "),B=function(a,c){var e="[object Function]"==u.call(a),b,h=!e&&"function"!=typeof a.constructor&&F[typeof a.hasOwnProperty]&&a.hasOwnProperty||v;for(b in a)e&&"prototype"==b||!h.call(a,b)||c(b);for(e=f.length;b=f[--e];h.call(a,b)&&c(b));});return B(a,c)};if(!q("json-stringify")){var U={92:"\\\\",34:'\\"',8:"\\b",
12:"\\f",10:"\\n",13:"\\r",9:"\\t"},y=function(a,c){return("000000"+(c||0)).slice(-a)},R=function(a){for(var c='"',b=0,h=a.length,f=!D||10<h,n=f&&(D?a.split(""):a);b<h;b++){var d=a.charCodeAt(b);switch(d){case 8:case 9:case 10:case 12:case 13:case 34:case 92:c+=U[d];break;default:if(32>d){c+="\\u00"+y(2,d.toString(16));break}c+=f?n[b]:a.charAt(b)}}return c+'"'},O=function(a,c,b,h,f,n,d){var g,m,k,l,p,r,s,t,q;try{g=c[a]}catch(z){}if("object"==typeof g&&g)if(m=u.call(g),"[object Date]"!=m||v.call(g,
"toJSON"))"function"==typeof g.toJSON&&("[object Number]"!=m&&"[object String]"!=m&&"[object Array]"!=m||v.call(g,"toJSON"))&&(g=g.toJSON(a));else if(g>-1/0&&g<1/0){if(E){l=x(g/864E5);for(m=x(l/365.2425)+1970-1;E(m+1,0)<=l;m++);for(k=x((l-E(m,0))/30.42);E(m,k+1)<=l;k++);l=1+l-E(m,k);p=(g%864E5+864E5)%864E5;r=x(p/36E5)%24;s=x(p/6E4)%60;t=x(p/1E3)%60;p%=1E3}else m=g.getUTCFullYear(),k=g.getUTCMonth(),l=g.getUTCDate(),r=g.getUTCHours(),s=g.getUTCMinutes(),t=g.getUTCSeconds(),p=g.getUTCMilliseconds();
g=(0>=m||1E4<=m?(0>m?"-":"+")+y(6,0>m?-m:m):y(4,m))+"-"+y(2,k+1)+"-"+y(2,l)+"T"+y(2,r)+":"+y(2,s)+":"+y(2,t)+"."+y(3,p)+"Z"}else g=null;b&&(g=b.call(c,a,g));if(null===g)return"null";m=u.call(g);if("[object Boolean]"==m)return""+g;if("[object Number]"==m)return g>-1/0&&g<1/0?""+g:"null";if("[object String]"==m)return R(""+g);if("object"==typeof g){for(a=d.length;a--;)if(d[a]===g)throw K();d.push(g);q=[];c=n;n+=f;if("[object Array]"==m){k=0;for(a=g.length;k<a;k++)m=O(k,g,b,h,f,n,d),q.push(m===w?"null":
m);a=q.length?f?"[\n"+n+q.join(",\n"+n)+"\n"+c+"]":"["+q.join(",")+"]":"[]"}else B(h||g,function(a){var c=O(a,g,b,h,f,n,d);c!==w&&q.push(R(a)+":"+(f?" ":"")+c)}),a=q.length?f?"{\n"+n+q.join(",\n"+n)+"\n"+c+"}":"{"+q.join(",")+"}":"{}";d.pop();return a}};r.stringify=function(a,c,b){var h,f,n,d;if(F[typeof c]&&c)if("[object Function]"==(d=u.call(c)))f=c;else if("[object Array]"==d){n={};for(var g=0,k=c.length,l;g<k;l=c[g++],(d=u.call(l),"[object String]"==d||"[object Number]"==d)&&(n[l]=1));}if(b)if("[object Number]"==
(d=u.call(b))){if(0<(b-=b%1))for(h="",10<b&&(b=10);h.length<b;h+=" ");}else"[object String]"==d&&(h=10>=b.length?b:b.slice(0,10));return O("",(l={},l[""]=a,l),f,n,h,"",[])}}if(!q("json-parse")){var V=A.fromCharCode,W={92:"\\",34:'"',47:"/",98:"\b",116:"\t",110:"\n",102:"\f",114:"\r"},b,J,l=function(){b=J=null;throw G();},z=function(){for(var a=J,c=a.length,e,h,f,k,d;b<c;)switch(d=a.charCodeAt(b),d){case 9:case 10:case 13:case 32:b++;break;case 123:case 125:case 91:case 93:case 58:case 44:return e=
D?a.charAt(b):a[b],b++,e;case 34:e="@";for(b++;b<c;)if(d=a.charCodeAt(b),32>d)l();else if(92==d)switch(d=a.charCodeAt(++b),d){case 92:case 34:case 47:case 98:case 116:case 110:case 102:case 114:e+=W[d];b++;break;case 117:h=++b;for(f=b+4;b<f;b++)d=a.charCodeAt(b),48<=d&&57>=d||97<=d&&102>=d||65<=d&&70>=d||l();e+=V("0x"+a.slice(h,b));break;default:l()}else{if(34==d)break;d=a.charCodeAt(b);for(h=b;32<=d&&92!=d&&34!=d;)d=a.charCodeAt(++b);e+=a.slice(h,b)}if(34==a.charCodeAt(b))return b++,e;l();default:h=
b;45==d&&(k=!0,d=a.charCodeAt(++b));if(48<=d&&57>=d){for(48==d&&(d=a.charCodeAt(b+1),48<=d&&57>=d)&&l();b<c&&(d=a.charCodeAt(b),48<=d&&57>=d);b++);if(46==a.charCodeAt(b)){for(f=++b;f<c&&(d=a.charCodeAt(f),48<=d&&57>=d);f++);f==b&&l();b=f}d=a.charCodeAt(b);if(101==d||69==d){d=a.charCodeAt(++b);43!=d&&45!=d||b++;for(f=b;f<c&&(d=a.charCodeAt(f),48<=d&&57>=d);f++);f==b&&l();b=f}return+a.slice(h,b)}k&&l();if("true"==a.slice(b,b+4))return b+=4,!0;if("false"==a.slice(b,b+5))return b+=5,!1;if("null"==a.slice(b,
b+4))return b+=4,null;l()}return"$"},P=function(a){var c,b;"$"==a&&l();if("string"==typeof a){if("@"==(D?a.charAt(0):a[0]))return a.slice(1);if("["==a){for(c=[];;b||(b=!0)){a=z();if("]"==a)break;b&&(","==a?(a=z(),"]"==a&&l()):l());","==a&&l();c.push(P(a))}return c}if("{"==a){for(c={};;b||(b=!0)){a=z();if("}"==a)break;b&&(","==a?(a=z(),"}"==a&&l()):l());","!=a&&"string"==typeof a&&"@"==(D?a.charAt(0):a[0])&&":"==z()||l();c[a.slice(1)]=P(z())}return c}l()}return a},T=function(a,b,e){e=S(a,b,e);e===
w?delete a[b]:a[b]=e},S=function(a,b,e){var h=a[b],f;if("object"==typeof h&&h)if("[object Array]"==u.call(h))for(f=h.length;f--;)T(h,f,e);else B(h,function(a){T(h,a,e)});return e.call(a,b,h)};r.parse=function(a,c){var e,h;b=0;J=""+a;e=P(z());"$"!=z()&&l();b=J=null;return c&&"[object Function]"==u.call(c)?S((h={},h[""]=e,h),"",c):e}}}r.runInContext=N;return r}var K=typeof define==="function"&&define.amd,F={"function":!0,object:!0},G=F[typeof exports]&&exports&&!exports.nodeType&&exports,k=F[typeof window]&&
window||this,t=G&&F[typeof module]&&module&&!module.nodeType&&"object"==typeof global&&global;!t||t.global!==t&&t.window!==t&&t.self!==t||(k=t);if(G&&!K)N(k,G);else{var L=k.JSON,Q=k.JSON3,M=!1,A=N(k,k.JSON3={noConflict:function(){M||(M=!0,k.JSON=L,k.JSON3=Q,L=Q=null);return A}});k.JSON={parse:A.parse,stringify:A.stringify}}K&&define(function(){return A})}).call(this);

/**--jQuery.json-2.3.js--**/
(function($){var escapeable=/["\\\x00-\x1f\x7f-\x9f]/g,meta={'\b':'\\b','\t':'\\t','\n':'\\n','\f':'\\f','\r':'\\r','"':'\\"','\\':'\\\\'};$.toJSON=typeof JSON==='object'&&JSON.stringify?JSON.stringify:function(o){if(o===null){return'null';}
    var type=typeof o;if(type==='undefined'){return undefined;}
    if(type==='number'||type==='boolean'){return''+o;}
    if(type==='string'){return $.quoteString(o);}
    if(type==='object'){if(typeof o.toJSON==='function'){return $.toJSON(o.toJSON());}
        if(o.constructor===Date){var month=o.getUTCMonth()+1,day=o.getUTCDate(),year=o.getUTCFullYear(),hours=o.getUTCHours(),minutes=o.getUTCMinutes(),seconds=o.getUTCSeconds(),milli=o.getUTCMilliseconds();if(month<10){month='0'+month;}
            if(day<10){day='0'+day;}
            if(hours<10){hours='0'+hours;}
            if(minutes<10){minutes='0'+minutes;}
            if(seconds<10){seconds='0'+seconds;}
            if(milli<100){milli='0'+milli;}
            if(milli<10){milli='0'+milli;}
            return'"'+year+'-'+month+'-'+day+'T'+
                hours+':'+minutes+':'+seconds+'.'+milli+'Z"';}
        if(o.constructor===Array){var ret=[];for(var i=0;i<o.length;i++){ret.push($.toJSON(o[i])||'null');}
            return'['+ret.join(',')+']';}
        var name,val,pairs=[];for(var k in o){type=typeof k;if(type==='number'){name='"'+k+'"';}else if(type==='string'){name=$.quoteString(k);}else{continue;}
            type=typeof o[k];if(type==='function'||type==='undefined'){continue;}
            val=$.toJSON(o[k]);pairs.push(name+':'+val);}
        return'{'+pairs.join(',')+'}';}};$.evalJSON=typeof JSON==='object'&&JSON.parse?JSON.parse:function(src){return eval('('+src+')');};$.secureEvalJSON=typeof JSON==='object'&&JSON.parse?JSON.parse:function(src){var filtered=src.replace(/\\["\\\/bfnrtu]/g,'@').replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,']').replace(/(?:^|:|,)(?:\s*\[)+/g,'');if(/^[\],:{}\s]*$/.test(filtered)){return eval('('+src+')');}else{throw new SyntaxError('Error parsing JSON, source is not valid.');}};$.quoteString=function(string){if(string.match(escapeable)){return'"'+string.replace(escapeable,function(a){var c=meta[a];if(typeof c==='string'){return c;}
        c=a.charCodeAt();return'\\u00'+Math.floor(c/16).toString(16)+(c%16).toString(16);})+'"';}
    return'"'+string+'"';};})(jQuery);

//jQuery.cookie
jQuery.cookie=function(a,b,c){var d,e,f,g,h,i,j,k,l;if("undefined"==typeof b){if(i=null,document.cookie&&""!=document.cookie)for(j=document.cookie.split(";"),k=0;k<j.length;k++)if(l=jQuery.trim(j[k]),l.substring(0,a.length+1)==a+"="){i=decodeURIComponent(l.substring(a.length+1));break}return i}c=c||{},null===b&&(b="",c.expires=-1),d="",c.expires&&("number"==typeof c.expires||c.expires.toUTCString)&&("number"==typeof c.expires?(e=new Date,e.setTime(e.getTime()+1e3*60*60*24*c.expires)):e=c.expires,d="; expires="+e.toUTCString()),f=c.path?"; path="+c.path:"",g=c.domain?"; domain="+c.domain:"",h=c.secure?"; secure":"",document.cookie=[a,"=",encodeURIComponent(b),d,f,g,h].join("")};

//jQuery.color
(function(d){d.each(["backgroundColor","borderBottomColor","borderLeftColor","borderRightColor","borderTopColor","color","outlineColor"],function(f,e){d.fx.step[e]=function(g){if(!g.colorInit){g.start=c(g.elem,e);g.end=b(g.end);g.colorInit=true}g.elem.style[e]="rgb("+[Math.max(Math.min(parseInt((g.pos*(g.end[0]-g.start[0]))+g.start[0]),255),0),Math.max(Math.min(parseInt((g.pos*(g.end[1]-g.start[1]))+g.start[1]),255),0),Math.max(Math.min(parseInt((g.pos*(g.end[2]-g.start[2]))+g.start[2]),255),0)].join(",")+")"}});function b(f){var e;if(f&&f.constructor==Array&&f.length==3){return f}if(e=/rgb\(\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*\)/.exec(f)){return[parseInt(e[1]),parseInt(e[2]),parseInt(e[3])]}if(e=/rgb\(\s*([0-9]+(?:\.[0-9]+)?)\%\s*,\s*([0-9]+(?:\.[0-9]+)?)\%\s*,\s*([0-9]+(?:\.[0-9]+)?)\%\s*\)/.exec(f)){return[parseFloat(e[1])*2.55,parseFloat(e[2])*2.55,parseFloat(e[3])*2.55]}if(e=/#([a-fA-F0-9]{2})([a-fA-F0-9]{2})([a-fA-F0-9]{2})/.exec(f)){return[parseInt(e[1],16),parseInt(e[2],16),parseInt(e[3],16)]}if(e=/#([a-fA-F0-9])([a-fA-F0-9])([a-fA-F0-9])/.exec(f)){return[parseInt(e[1]+e[1],16),parseInt(e[2]+e[2],16),parseInt(e[3]+e[3],16)]}if(e=/rgba\(0, 0, 0, 0\)/.exec(f)){return a.transparent}return a[d.trim(f).toLowerCase()]}function c(g,e){var f;do{f=d.css(g,e);if(f!=""&&f!="transparent"||d.nodeName(g,"body")){break}e="backgroundColor"}while(g=g.parentNode);return b(f)}var a={aqua:[0,255,255],azure:[240,255,255],beige:[245,245,220],black:[0,0,0],blue:[0,0,255],brown:[165,42,42],cyan:[0,255,255],darkblue:[0,0,139],darkcyan:[0,139,139],darkgrey:[169,169,169],darkgreen:[0,100,0],darkkhaki:[189,183,107],darkmagenta:[139,0,139],darkolivegreen:[85,107,47],darkorange:[255,140,0],darkorchid:[153,50,204],darkred:[139,0,0],darksalmon:[233,150,122],darkviolet:[148,0,211],fuchsia:[255,0,255],gold:[255,215,0],green:[0,128,0],indigo:[75,0,130],khaki:[240,230,140],lightblue:[173,216,230],lightcyan:[224,255,255],lightgreen:[144,238,144],lightgrey:[211,211,211],lightpink:[255,182,193],lightyellow:[255,255,224],lime:[0,255,0],magenta:[255,0,255],maroon:[128,0,0],navy:[0,0,128],olive:[128,128,0],orange:[255,165,0],pink:[255,192,203],purple:[128,0,128],violet:[128,0,128],red:[255,0,0],silver:[192,192,192],white:[255,255,255],yellow:[255,255,0],transparent:[255,255,255]}})(jQuery);

/* soChange 1.6.2 - simple object change with jQuery */
/* 使用方法参考：http://sojs.bujichong.com/soChange/index.html */
/*
$(obj).soChange({
    thumbObj:null, // 导航对象，默认为空
    btnPrev:null, // 按钮上一个，默认为空
    btnNext:null, // 按钮下一个。默认为空
    changeType:'fade', // 切换方式，可选：fade,slide，默认为fade，1.6版新增方法，详见例3-定义对象切换方式为slide
    thumbNowClass:'now', // 导航对象当前的class,默认为now
    thumbOverEvent:true, // 鼠标经过thumbObj时是否切换对象，默认为true，为false时，只有鼠标点击thumbObj才切换对象
    slideTime:1000, // 平滑过渡时间，默认为1000ms，为0或负值时，忽略changeType方式，切换效果为直接显示隐藏
    autoChange:true, // 是否自动切换，默认为true
    clickFalse:true, // 导航对象点击是否链接无效，默认是return false链接无效，当thumbOverEvent为false时，此项必须为true，否则鼠标点击事件冲突
    overStop:true, // 鼠标经过切换对象时，是否停止切换，并于鼠标离开后重启自动切换，前提是已开启自动切换
    changeTime:5000, // 对象自动切换时间，默认为5000ms，即5秒
    delayTime:300 // 鼠标经过时对象切换迟滞时间，推荐值为300ms
    callback:function(prev,now){} // 切换返回函数，内部提供2个参数:切换前 index值 prev,当前 index值 now 1.6版新增方法
});
 */
(function(a){a.fn.extend({soChange:function(b){b=a.extend({thumbObj:null,btnPrev:null,btnNext:null,changeType:"fade",thumbNowClass:"now",thumbOverEvent:true,slideTime:1000,autoChange:true,clickFalse:true,overStop:true,changeTime:5000,delayTime:300,callback:function(){}},b||{});var h=a(this);var i;var k=h.size();var e=0;var g;var c;var f;function d(){if(e!=g){if(b.thumbObj){a(b.thumbObj).removeClass(b.thumbNowClass).eq(g).addClass(b.thumbNowClass)}if(b.slideTime<=0){h.eq(e).hide();h.eq(g).show()}else{if(b.changeType=="fade"){h.eq(e).fadeOut(b.slideTime);h.eq(g).fadeIn(b.slideTime)}else{h.eq(e).slideUp(b.slideTime);h.eq(g).slideDown(b.slideTime)}}if(b.callback){b.callback(e,g)}e=g}}function j(){g=(e+1)%k;d()}h.hide().eq(0).show();if(b.thumbObj){i=a(b.thumbObj);i.removeClass(b.thumbNowClass).eq(0).addClass(b.thumbNowClass);i.click(function(){g=i.index(a(this));d();if(b.clickFalse){return false}});if(b.thumbOverEvent){i.hover(function(){g=i.index(a(this));f=setTimeout(d,b.delayTime)},function(){clearTimeout(f)})}}if(b.btnNext){a(b.btnNext).click(function(){if(h.queue().length<1){j()}return false})}if(b.btnPrev){a(b.btnPrev).click(function(){if(h.queue().length<1){g=(e+k-1)%k;d()}return false})}if(b.autoChange){c=setInterval(j,b.changeTime);if(b.overStop){h.hover(function(){clearInterval(c)},function(){c=setInterval(j,b.changeTime)})}}}})})(jQuery);

$.extend({
    /**
     * 地址转换 tree:dept -> /base/treeUrl?_code=dept
     */
    url : function(url) {
        var rst = '';
        if (url.indexOf('/') == -1) {
            var ar = url.split(":");
            rst+=$p[ar[0] + "Url"];
            if(ar[1]) rst+= "?_code=" + ar[1];
        } else {
            rst = url;
        }
        return rst;
    },
    /**
     * 默认值赋值
     * @param o 目标对象
     * @param c 默认值
     * @returns
     */
    applyIf : function(o, c) {
        if (o && c) {
            for ( var p in c) {
                if (typeof o[p] == "undefined") {
                    o[p] = c[p];
                }
            }
        }
        return o;
    },
    getUrlParams : function (param) {
      var query = window.location.search.substring(1);
      var vars = query.split("&");
      if(typeof(param) == 'string'){
        param = [param];
      };
      var vl = vars.length , pl = param.length,kv={};
      for (var j=0;j<pl;j++) {
        kv[param[j]] = '';
        for (var i=0;i<vl;i++) {
          var pair = vars[i].split("=");
          if(pair[0] == param[j]){kv[param[j]] = pair[1];break;};
        }
      };
      return pl==1?kv[param[0]]:kv;
    },
    /**
     * 格式化日期函数
     * @param format 格式化规则，如 "YYYY-MM-dd HH:mm:ss"
     * @param date 日期对象，可是是Date类型，也可以是时间戳字符串
     * @returns 对应的日期格式字符串
     */
    fmtDate: function (format, date) {
        var nowTime = window.sysNowTime || '';
        var format = format || "YYYY-MM-dd HH:mm:ss";
        date = date || (nowTime?nowTime:new Date());
        var str = date.toString();
        str = str.replace(/-/g, "/");
        if(/^([1-9]\d*)$/.test(str)){str = str*1};//时间戳处理
        date = new Date(str);
        var o = {
            "M+": date.getMonth() + 1, //month
            "d+": date.getDate(), //day
            "h+": date.getHours() % 12 == 0 ? 12 : date.getHours() % 12, //hour
            "H+": date.getHours(), //hour
            "m+": date.getMinutes(), //minute
            "s+": date.getSeconds(), //second
            "q+": Math.floor((date.getMonth() + 3) / 3), //quarter
            "S": date.getMilliseconds() //millisecond
        };
        var week = {
            "0": "\u65e5",
            "1": "\u4e00",
            "2": "\u4e8c",
            "3": "\u4e09",
            "4": "\u56db",
            "5": "\u4e94",
            "6": "\u516d"
        };
        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
        }

        if (/(E+)/.test(format)) {
            format = format.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "\u661f\u671f" : "\u5468") : "") + week[this.getDay() + ""]);
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    },
    getNowServeTime : function(callback,format){
      $ajax.post({//更新医嘱 开始时间
        url : base +'/ui/common/common/getSystemTime',
        loadingMask : false,
        success : function (rst) {
          callback();
          var format = format || 'yyyy-MM-dd HH:mm:ss';
          callback($.fmtDate(format,rst.data));
        }
      });
    },
    /**
     * 格式化日期函数
     * @param date 格式化规则，如 "YYYY-MM-dd HH:mm:ss"
     * @param type  null("long")/"short"，为short只返回年月日，否则返回完整时间
     * @returns 对应的日期格式字符串
     */
    getFullDate : function (date,type) {// Date,'long/short'
        var that = this;
        if (!(date instanceof Date)) {
            date = new Date(date);
        }
        var year = date.getFullYear();
        var month = date.getMonth()+1;
        var day = date.getDate();
        var hh = date.getHours();// 时
        var mm = date.getMinutes();// 分
         var ss = date.getSeconds();//秒

        month = ('0'+month).slice(-2);
        day = ('0'+day).slice(-2);
        hh = ('0'+hh).slice(-2);
        mm = ('0'+mm).slice(-2);
        ss = ('0'+ss).slice(-2);
        if (type=='short') {
            //return year+'-'+month+'-'+day;
            return month+'-'+day+' '+hh+':'+mm+':'+ss;
        }else {
            return year+'-'+month+'-'+day+' '+hh+':'+mm;
        }
    },
    getLocalTime :function (nS) {// 转时间戳为本地时间，nS为时间戳字符串，错误格式无法返回
        return new Date(nS*1).toLocaleString().replace(/年|月/g, "-").replace(/日/g," ");
    },
    toHour: function (v) {//转换成小时，返回整数小时
        v = v * 1 || 0;
        return Math.ceil((v * 100) / (1000 * 60 * 60)) / 100;
    },
    toDay: function (v) {//转换成天，返回整数小时
        v = v * 1 || 0;
        return Math.ceil((v * 100) / (1000 * 60 * 60 * 24)) / 100;
    },
    getTimeLong : function (s) {//将秒转化成倒计时的时分秒
        var h = Math.floor(s/3600);
        h = h>9?h:('0'+h);
        var m = Math.floor(s%3600/60);
        m = m>9?m:('0'+m);
        var s = s%3600%60;
        s = s>9?s:('0'+s);
        return h==0?(m+':'+s):(h+':'+m+':'+s);
    },
    arrHasVal : function (arr,val) {//数组是否有某个值
        var l = arr.length;
        for (i = 0; i < l; i++) {
            if (arr[i] === val) {
                return i;
            }
        }
        return -1;
    },
    fullscreen : function (tofull) {//全屏辅助函数
      if(tofull){
        var docElm = document.documentElement;
        if (docElm.requestFullscreen) {
          docElm.requestFullscreen();
        }
        else if (docElm.mozRequestFullScreen) {
          docElm.mozRequestFullScreen();
        }
        else if (docElm.webkitRequestFullScreen) {
          docElm.webkitRequestFullScreen();
        }
      }else{
        if (document.exitFullscreen) {
          document.exitFullscreen();
        }
        else if (document.mozCancelFullScreen) {
          document.mozCancelFullScreen();
        }
        else if (document.webkitCancelFullScreen) {
          document.webkitCancelFullScreen();
        }
      }
    },
    winW : $(window).width(),
    winH :$(window).height()
});


/**
 * 扩充方法
 */
$.fn.extend({
    //hoverClass $() 如： $(".ul_nav li").hoverClass("over");
    hoverClass:function(b){var a=this;a.each(function(c){a.eq(c).mouseenter(function(){$(this).addClass(b)});a.eq(c).mouseleave(function(){$(this).removeClass(b)})});return a},
    focusChangeStyle : function(b){var a=this;var b=(b==null)?"txt_focus":b;a.focus(function(){$(this).addClass(b)});a.blur(function(){$(this).removeClass(b)});return a},
    tabChange:function (o) {
        o= $.extend({
            thumbObj:null,//导航对象
            thumbNowClass:'now',//导航对象当前的class,默认为now
            eventClass:'eventStat',
            defaultEvent:function(){},
            callback:function () {},
            oneback:function(){}
        }, o || {});
        var _self = $(this);
        var size = _self.size();
        var thumbObj = $(o.thumbObj);
        _self.removeClass(o.eventClass).eq(0).addClass(o.eventClass);
        thumbObj.eq(0).addClass(o.thumbNowClass);
        o.defaultEvent();

        thumbObj.click(function () {
            var indx = thumbObj.index($(this));
            thumbObj.removeClass(o.thumbNowClass);
            $(this).addClass(o.thumbNowClass);
            _self.removeClass(o.eventClass).eq(indx).addClass(o.eventClass);
            return o.callback(indx);
        });
        thumbObj.each(function (i) {
            if (i>0) {
                $(this).one('click',function () {
                    return o.oneback(i);
                });
            }
        });
    },
    switchShow:function(cls,scope){//对象切换简易版，切换对象为cls dom对象组，只有对象的data-value 值vl，结合 cls+"_"+vl 显示
        $(this).click(function(){
            var $scope=scope?$(scope):$("body");
            $scope.find(cls).hide();
            var vl=$(this).val()||$(this).attr("data-value");
            $scope.find(cls+"_"+vl).show();
        });
        return $(this);
    },
    // clear : function(data) {
    //     $(":input:not(:submit)", this).val("");
    //     if (data)
    //         $(this).vals(data);
    // },
    sovals : function(dataToString) {//返回表单的序列，dataToString为true，将多选框的值以字符串的方式返回
        var o = {};
        var a = this.serializeArray();
        $.each(a, function() {
            if (o[this.name]!==undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [ o[this.name] ];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        if (dataToString) {
            $.each(o,function (k,v) {
                if (v.push) {
                    o[k] = v.join(',');
                }
            });
        };
        return o;
    },
  setDate : function(val,format){//给日历控件设置值
    var dateFmtType = {
      date : 'yyyy-MM-dd',
      time : 'yyyy-MM-dd HH:mm:ss',
      month : 'yyyy-MM'
    };
    var format = format || 'date';
    var sv = val?$.fmtDate(dateFmtType[format],val):'';
    $(this).val(sv);
  },
  setCurPos : function(option) {
      var settings = $.extend({
        index: 0
      }, option);
      return this.each(function() {
        var elem  = this
        var val   = elem.value
        var len   = val.length
        var index = settings.index

        // 非input和textarea直接返回
        var $elem = $(elem)
        if (!$elem.is('input,textarea')) return
        // 超过文本长度直接返回
        if (len < index) return

        setTimeout(function() {
          elem.focus()
          if (elem.setSelectionRange) { // 标准浏览器
            elem.setSelectionRange(index, index)
          } else { // IE9-
            var range = elem.createTextRange()
            range.moveStart("character", -len)
            range.moveEnd("character", -len)
            range.moveStart("character", index)
            range.moveEnd("character", 0)
            range.select()
          }
        }, 10)
      });
    },
    soSelect : function(o){//简易下拉框，使用系统的select来初始化
      var defOpt = {//所有参数
        muti: false,//是否多选
        nullVal : true,//是否添加'请选择...'
        appendMode : false,//html还是append到元素中，默认直接html替换
        url : null,//远程请求地址
        data : null,//请求附加数据
        value : null,//被选中值，为字符串，多选用逗号隔开
        success : null,//初始化完成后 function(val,_self,opt){}
        change : null//change执行事件，字符串为全局函数，function为function(val,_self,opt){}函数
      };
      var opt = $T.data(this) || {};
      opt = $.extend(defOpt,opt,o||{});

      var mySelf = $(this);
      mySelf.each(function () {
        var _self = $(this);
        if (opt.value!==null) {
          var val =opt.value.toString();
          val = val.split(',');
        }
        var data = opt.data ||{};
        var rstData = null;
        if(opt.url){
          $.ajax({
            url :opt.url,
            data : data,
            type : 'post',
            dataType : 'json',
            async : false,
            success : function(rstData){
              // window.console&&console.log(rstData);
              if(rstData&&rstData.length){
                var optHtml = (opt.nullVal)?'<option value="">请选择...</option>':'';
                $.each(rstData,function(i,v){
                  if(v.selected||v.checked){val.push(v.value)};//json数据selected或checked为true也可以标示为选中
                  optHtml += '<option value="'+v.value+'">'+v.text+'</option>';
                });
                _self[opt.appendMode?'append':'html'](optHtml);//添加dom
              }else{
 //               window.console&&console.log('so-drop远程初始化失败或数据为空..');
              }
            }
          });
        }
        if(opt.muti){
          _self.attr('multiple','multiple');
        }
        if (val&&val.length) {//赋值
          if(!opt.muti){
            val=val.pop();
            _self.find('option[value='+val+']').attr('selected',true);
          }else{
            $.each(val,function(i,v){
              _self.find('option[value='+v+']').attr('selected',true);
            })
          }
        }
        if(opt.success){
          if(typeof opt.success === 'string'){//字符串为函数名
            window[opt.success](val,_self,opt);
          }else{//函数
            opt.success(val,_self,opt);
          }
        }
        if(opt.change){
          _self.change(function(){
            val = _self.val();
            if(typeof opt.change === 'string'){//字符串为函数名
              window[opt.change](val,_self,opt);
            }else{//函数
              opt.change(val,_self,opt);
            }
          })
        }
      });
      return mySelf;
    }
});

var $event = {
    stopBubble: function (e) {
      if (e && e.stopPropagation) {//非IE浏览器
        e.stopPropagation();
      } else {
        window.event.cancelBubble = true;
      }
    },
    stopDefault: function (e) {
      // 阻止默认浏览器动作(W3C)
      if (e && e.preventDefault) {
        e.preventDefault();
      } else {
          // IE中阻止函数器默认动作的方式
        window.event.returnValue = false;
      }
      return false;
    }
};

var $store = {//数据中心
    set : function (k,v){
        var comData = null;
        try{
            comData = window.top.eyeStore.comData;
        }catch(e){
            window.eyeStore = window.eyeStore || {
                comData :{}
            };
            comData = eyeStore.comData;
        }
        comData[k] = v;
    },
    get : function (k){
        try{
            return window.top.eyeStore.comData[k];
        }catch(e){
          try{
            return window.parent.eyeStore.comData[k];
          }catch(e){
            // window.console && console.log('获取失败，存在多层跨域~');
            return '';
          }
        }
    },
    getAll : function (){
        try{
            return window.top.eyeStore.comData;
        }catch(e){
          try{
            return window.parent.eyeStore.comData;
          }catch(e){
            // window.console && console.log('获取失败，存在多层跨域~');
            return '';
          }
        }
    },
    getPostData : function (){
        var postDIndex = parent.postDIndex;
        return postDIndex?$store.get(postDIndex):'';
    },
    clear : function (){
        try{
            window.top.eyeStore.comData = {};
        }catch(e){
          try{
            window.parent.eyeStore.comData ={};
          }catch(e){
            // window.console && console.log('清空失败，存在多层跨域~');
          }
        }

    }
};

var $pop = {
    alertType : {
        warning: {icon:0},
        success : {icon:1},
        err : {icon:2},
        ask : {icon:3}
    },
    alert : function (msg,yes,opt,type) {//icon: {0:'叹号',1:'对勾',2:'叉叉',3:'问号'}
        var me = this;
        if(typeof yes === 'object'&&yes !== null) {
            opt = yes;
            yes = null;
        };
        var type = type?type:'warning';
        var opt = $.extend({title:false,btnAlign: 'c'},me.alertType[type],opt||{});
        return layer.alert(msg,opt,yes);
    },
	warning : function(msg,yes,opt){
    return $pop.alert(msg,yes,opt);
  },
  success : function(msg,yes,opt){
    return $pop.alert(msg,yes,opt,'success');
  },
  err : function(msg,yes,opt){
    return $pop.alert(msg,yes,opt,'err');
  },
  ask : function(msg,yes,opt){
    return $pop.alert(msg,yes,opt,'ask');
  },
  msgType :{
    warning: {icon:0},
    success : {icon:1,skin:'layui-layer-msg layer-msg-success'},
    err : {icon:2,skin:'layui-layer-msg layer-msg-err'},
    ask : {icon:3,skin:'layui-layer-msg layer-msg-ask'}
  },
  msg : function (msg,end,opt,type) {//icon: {0:'叹号',1:'对勾',2:'叉叉',3:'问号'}
        var me = this;
        var o = opt;
        if((typeof end === 'object'&&end !== null)||(typeof end === 'number')) {
            o = opt = end;
            end = null;
        };
        if(typeof opt === 'number'){
            o = {};
            o.time = opt;
        }
        var type = type?type:'warning';
        var opt = $.extend({time:2000},me.msgType[type],o);
        return layer.msg(msg , opt , end)
  },
  confirm : function (msg,yes,cancel,opt) {//icon: {0:'叹号',1:'对勾',2:'叉叉',3:'问号'}
      var msg = msg || '你确定此操作吗？';
      var opt = $.extend({icon: 0, title:false,btnAlign: 'c',closeBtn :0},opt||{});
      return layer.confirm(msg , opt ,yes,cancel);
  },
  tips : function (content, follow, options) {
    return  layer.tips(content, follow, options);
  },
  load : function (icon, options){
      return layer.load(icon, options);
  },
  open : function (opt){
    var opt = $.extend({type:1,btn:[]},opt||{});
    return layer.open(opt);
  },
  close : function (index){
      layer.close(index);
  },
  center : function (index){
      var $layer = $("#layui-layer"+index);
      var w = $layer.outerWidth();
      var h = $layer.outerHeight();
      $layer.css({
        left:($(document).width()-w)/2,
        top:($(window).height()-h)/2
      });
      return index;
  },
  newTabWindow : function (tabTitle,url,unselected) {
    try{
        window.top.eyeIndex&&window.top.eyeIndex.addTab(tabTitle,url,unselected);
    }catch(e){
        window.parent.postMessage(tabTitle+'^^^'+url,'*');//向父页面发送信息
    }
  },
  closeTabWindow : function (tabTitle){
    var tabTitle = tabTitle || $(document).attr("title");
    //window.console && console.log(tabTitle);
    window.top.eyeIndex&&window.top.eyeIndex.closeTab(tabTitle);
  },
  closePop : function (opt) {//统一的关闭pop方法
      var opt = $.extend({
          popIndex : null,
          callback : function () {},
          refresh : false,
          success : false,
          refreshGrid : false,
          time : false
      },opt||{});

      if (opt.popIndex) {//如果关闭当前window下的pop
          opt.callback();
          layer.close(opt.popIndex);
          return;
      }else{//关闭父级pop
          var p = parent.window;

          if (opt.refreshGrid || opt.refresh || opt.success) {
              p._refreshParent = true;
          };

          try {//试运行callback
              opt.callback(p);
          } catch (e) {}

          try {//试关闭open
            var index = parent.layer.getFrameIndex(window.name);
            if(opt.time){
              setTimeout(function(){
                p.layer.close(index);
              },opt.time);
            }else{
              p.layer.close(index);
            }
          } catch (e) {
              //window.console && console.log(e);
          }
      };
  }
};

$pop.alert.warning = $pop.alert;
$pop.alert.success = $pop.success;
$pop.alert.err = $pop.err;
$pop.alert.ask = $pop.ask;

$pop.msg.warning = function (msg,end,opt){
    return $pop.msg(msg,end,opt);
}
$pop.msg.success = function (msg,end,opt){
    return $pop.msg(msg,end,opt,'success');
}
$pop.msg.err = function (msg,end,opt){
    return $pop.msg(msg,end,opt,'err');
}
$pop.msg.ask = function (msg,end,opt){
    return $pop.msg(msg,end,opt,'ask');
}



var $ajax = {//统一的异步post请求
    _private : {
        layerIdx : null,
    },
    alertErr : function (rst,msg) {
      // window.console && console.log(rst);
      // if(rst.timeout&&rst.timeout===301){
      //   $pop.reLogin();
      //   return;
      // }
    	var errDatails = rst.error ||'';
    	var msg = rst.msg || msg ||'信息请求失败';
            var showErr = '<i class="layui-layer-ico layui-layer-ico2"></i><h2 class="h2-err">对不起，信息请求失败！</h2><p>'+msg+'</p>';
            if (errDatails) {
                showErr = '<i class="layui-layer-ico layui-layer-ico2"></i><h2 class="h2-err">对不起！'+ msg +'</h2><p>有问题请检查网络或联系医院管理员！</p><span class="s-errDetails">查看错误信息</span> <div class="errPopInfoBox"><div class="errPopInfo">'+errDatails+'</div></div>';
            };
    	$pop.open({
    	  type: 1,
    	  skin:'soerrPop',
    	  icon: 2, title:false,
    	  btnAlign: 'c',
    	  area:['320px','auto'],
    	  content: showErr,
    	  btn: ['确定'],
    	  yes : function(index, layero){
    		layer.close(index);
    	  },
    	  success : function($layer, index){
          $layer.find('.layui-layer-btn0').focus();//提交按钮获取焦点
          var errHtml = $layer.find('.errPopInfoBox').html();
          $('.s-errDetails').click(function(){
            $pop.open({
            title :'',
            type: 1,
            skin:'soerrPopInfo',
            area : ['80%','80%'],
            content: errHtml
            });
          });
    	  }
    	});
    },
    postJson : function (url, data, tip) {
        return this.post(url, data, tip, true);
    },
    postSync : function (url, data, tip, jsonData) {
        return this.post(url, data, tip, jsonData,{sync:true});
    },
    post: function (url, data, tip, jsonData,opt) {
        var  dtd = null,canAjax = true;//用来过滤异步可能造成的多次提交
        //简单重载参数
        var inOpt = {};
        var gtype = Object.prototype.toString;
        if($.type(opt)=='object'){inOpt = opt;}
        if($.type(jsonData)=='object'){inOpt = jsonData;}else{inOpt.jsonData = jsonData;};
        if($.type(tip)=='object'){inOpt = tip;}else{inOpt.tip = tip;};
        if(data){inOpt.data = data;};
        if($.type(url)=='object'){inOpt = url;}else{inOpt.url = url;};

        var o = $.extend({
          url:null,//请求地址
          data : null,//请求数据
          tip : false,//提交是否需要确认，true或string需要确认
          jsonData : false,//是否采用jsonData格式提交
          sync : false,//是否同步方式提交
          type : 'post',//采用post方式提交
          loadingBar : true,//loading进度条
          loadingMask : true,//进行异步请求中，是否显示mask
          calltip : true,//提交成功后显示请求成功信息
          success : function(){},//请求成功后，code===200或者201返回事件
          callback : function(){},//请求成功后返回事件
          cancelback : function(){},//确认框点取消返回事件
          errback : function(){}//出现错误时返回事件
        },inOpt||{});

        if(o.jsonData&&typeof(o.data)=='object'){//如果用json方式传递，并没有转化stringify
          o.data = JSON.stringify(o.data);
        };

        // window.console && console.log(o.data,typeof(o.data));
        // window.console && console.log(o);
        if (o.tip) {//提示
          var msg = (o.tip === true ? $p.submitTip : o.tip);
          dtd = $.Deferred();
          var event = function (dtd) {
            $pop.confirm(msg,function(index){
                $ajax.doing(o,canAjax,dtd,true,index);
            },function(){
              o.cancelback();
            });
            return dtd.promise();
          };
          return $.when(event(dtd));
        } else {
            dtd = $ajax.doing(o,canAjax);
        }
        return dtd;
    },
    doing : function (o,canAjax,dtd,confirm,index){//{o:'参数对象',canAjax：'重复提交处理',confirm:'是否确认模式',index:'弹窗index'}
        if(canAjax){

            confirm&&(canAjax = false);//只有comfirm状态下才生效
            var loadingIndex = null;
            return $.ajax({
                url: o.url, type: o.type, data: o.data, dataType: 'json', async : !o.sync,
                headers: {
                    Accept: "application/json; charset=utf-8",
                    'Content-Type' : o.jsonData ? 'application/json' : 'application/x-www-form-urlencoded'
                },
                beforeSend: function (jqXHR, settings) {
                    index&&$pop.close(index);
                    if(o.loadingBar){loadingIndex = $pop.load(0, o.loadingMask?{shade: 0.3}:{});}
                },
                complete: function (jqXHR, textStatus) {
                  confirm&&setTimeout(function () {//防止comfirm确定按钮多次被点击
                        canAjax = true;
                    },200);
                },
                success: function (rst) {
                  o.loadingBar && $pop.close(loadingIndex);
                    o.callback(rst);
                    confirm&&dtd.resolve(rst);//确认模式 resolve
                    if(rst){
                        // var msg = (rst.code === '200' ?"信息提交成功": rst.msg );
                        if(rst.code==='200'){
                            if(confirm&&o.calltip){
                                $pop.msg.success('信息提交成功',{time: 1500});
                            }
                            o.success(rst);
                            return;
                        }
                        if (rst.code==='201') {
                            o.success(rst);
                            if(o.calltip){$pop.msg.success(rst.msg,{time: 1500});}
                        }
                        if(rst.code==='500') {
                            // $ajax.alertErr(rst);
                        }
                    }
                },
                error: function (req, textStatus, errorThrown) {
                    o.loadingBar && $pop.close(loadingIndex);
                    o.errback(req, textStatus, errorThrown);
                    confirm&&dtd.reject();//确认模式 reject
                }
            });
        }
    },
    // 弹出提示直到手动取消提示 用于队列请求和ws请求
     /**
     * opt == jquery.ajax 中的option 参数对象
     */
    ajaxWait : function (o,action,openTip) {

        var ret = {};
        var layerIdx = $ajax._private.layerIdx;
        ret = $.ajax({
            url: o.url, type: o.type || "POST", data: o.data, async : !o.sync,// dataType: 'json', // 这里导致 f207 返回的responsText 解析报错
            headers: {
                Accept: "application/json; charset=utf-8",
                'Content-Type' : o.jsonData ? 'application/json' : 'application/x-www-form-urlencoded'
            },
            beforeSend: function (jqXHR, settings) {
                // if(typeof layerIdx !== "number") {
                //     layerIdx = top.layer.open({
                //         title: false,
                //         content:'请稍候,请勿关闭页面...',
                //         closeBtn:0,
                //         btn:false,
                //         cancel: false,
                //         time:60000 // 超时一分钟后关闭
                //     });
                // }
                // $ajax._private.layerIdx =  layerIdx;
            },
            complete: o.complete,
            success: function (ret) {
                if(action == "done") {
                    if(ret){
                        if( ret.code ==='200'){
                            if(openTip){  $pop.msg.success('信息提交成功',{time: 1500}) };
                            return;
                        }
                        if ( ret.code ==='201') {
                            $pop.msg.success(ret.msg,{time: 1500});
                        }
                        if(ret.code==='500') {
                            // $ajax.alertErr(rst);
                        }
                    }
                }
                 // success 这里改了 没用因为 ajaxWait 在 close 里面才是最后的返回
                 // if(rst){
                 //     if(rst.code==='200'){
                 //         if(confirm&&o.calltip){
                 //             $pop.msg.success('信息提交成功',{time: 1500});
                 //         }
                 //         o.success(rst);
                 //         return;
                 //     }
                 //     if (rst.code==='201') {
                 //         o.success(rst);
                 //         if(o.calltip){$pop.msg.success(rst.msg,{time: 2000});}
                 //     }
                 // }
            },
            error: function (req, textStatus, errorThrown) {
                  layer.closeAll();
                  top.layer.closeAll();
                  $ajax._private.layerIdx = null;
                  typeof o.error == "function" && o.error(req, textStatus, errorThrown);
            }
        });
        // ret.closeMask  = function(isOpenTip) {
        //     top.layer.close(layerIdx);
        //     $ajax._private.layerIdx = null;
        //     var ret =  this.responseJSON;
        //     if(ret){
        //         if( ret.code ==='200'){
        //             if(!isOpenTip){  $pop.msg.success('信息提交成功',{time: 1500}) };
        //             return;
        //         }
        //         if ( ret.code ==='201') {
        //             $pop.msg.success(ret.msg,{time: 1500});
        //         }
        //         if(rst.code==='500') {
        //             // $ajax.alertErr(rst);
        //         }
        //     }
        //
        // }

        return ret;


    }

};

$(document).ajaxComplete(function(evt, request){//全局异步请求拦截，拦截获取登录超时，获取业务报错，获取服务器报错
  // window.console && console.log( request);
  var req = request.responseJSON;
  if(request.status===500 || request.status===404){//获取全局报错
      if(req&&req.code==='500'&&req.timeout===301){//登录超时
        // $pop.reLogin();
        $pop.alert('<div class="pop-loginout"><p class="p-reInfo">登录已超时，请点击确定重新登录!</p><p class="p-second"><b class="b-second red">5</b> 秒后自动退出</p></div>',function (){
          window.location.href="/";//退出登录
        },{
          skin : 'popReloginBox',
          success : function () {
            var s = 5;
            var toRelogin = setInterval(function (){
              if(s>0){
                s--;
                $('.pop-loginout .b-second').text(s);
              }else{
                clearInterval(toRelogin);
                window.location.href="/";//退出登录
              }
            }, 1000);
          }
        });
        return;
      }
      if(req&&req.msg){//服务器程序或业务报错
    	if(req.level == 'info'){
    		$pop.success(req.msg);
    	}else if(req.level == 'warn'){
    		$pop.alert(req.msg);
    	}else{
            $ajax.alertErr(req);   //业务报错交给 $ajax.post处理
    	}
        return;
      }
      //服务器请求失败
      // $pop.alert('<p class="red">对不起，数据连接失败！</p>请检查网络或发送邮件到管理员邮箱 <br /><a class="a-mail" href="mailto:servicer@aierchina.com">servicer@aierchina.com</a>');
      $pop.alert('<p class="red">对不起，数据连接失败！</p>有问题请检查网络或联系医院管理员！');
  }

  if(request.status===401){
    $pop.alert('<div class="pop-loginout"><p class="p-reInfo"><span class="red">权限已过期</span>，想继续操作请重新登录!</p></div>',function (){
    },{
      skin : 'popReloginBox',
      success : function () {}
    });
    return;
  }

  if(request.status===200){//状态200，code 500 业务报错
    if(req&&req.code==='500'&&req.msg){
      $ajax.alertErr(req);
      return;
    }
  }
});

var $T = {
    mul : function (a, b) {//修正版乘法
        var c = 0,
            d = a.toString(),
            e = b.toString();
        try {
            c += d.split(".")[1].length;
        } catch (f) {}
        try {
            c += e.split(".")[1].length;
        } catch (f) {}
        return Number(d.replace(".", "")) * Number(e.replace(".", "")) / Math.pow(10, c);
    },
    add : function (a,b) {//修正版加法
        var me = this;
        var c, d, e;
        try {
            c = a.toString().split(".")[1].length;
        } catch (f) {
            c = 0;
        }
        try {
            d = b.toString().split(".")[1].length;
        } catch (f) {
            d = 0;
        }
        return e = Math.pow(10, Math.max(c, d)), (me.mul(a, e) + me.mul(b, e)) / e;
    },
    sub : function (a,b) {//修正版减法
        var me = this;
        var c, d, e;
        try {
            c = a.toString().split(".")[1].length;
        } catch (f) {
            c = 0;
        }
        try {
            d = b.toString().split(".")[1].length;
        } catch (f) {
            d = 0;
        }
        return e = Math.pow(10, Math.max(c, d)), (me.mul(a, e) - me.mul(b, e)) / e;
    },
    div : function (a,b) {//修正版除法
        var me = this;
        return me.mul(a,100000)/me.mul(b,100000);
        // var c, d, e = 0,
        //     f = 0;
        // try {
        //     e = (a.toString().split(".")[1]).length;
        // } catch (g) {}
        // try {
        //     f = b.toString().split(".")[1].length;
        // } catch (g) {}
        // window.console && console.log(e,f,a.toString().replace(".", ""),b.toString().replace(".", ""));
        // return c = Number(a.toString().replace(".", "")), d = Number(b.toString().replace(".", "")), me.mul(c / d, Math.pow(10, f - e));
    },
    toFixed : function (num,p){//修正版 toFixed
      var t = Math.pow(10,p);
      return (Math.round(Number(num)*t)/t).toFixed(p);
    },
    getCookie : function (key,co) {//增强版取cookie
        var co = co||'aso',$co = $.cookie(co);
        if ($co!==null) {
            var coArr = $co.split('||');
            var coLen = coArr.length;
            for (i = 0; i < coLen; i++) {
                var vk = coArr[i].split(':=');
                if (vk[0] === key) {
                    return vk[1];
                }
            }
        }else {
            return null;
        }
    },
    setCookie : function (key,value,co,root) {//增强版设置cookie
        root = root==undefined?true:root;//默认cookie都定义在root目录下，不到子目录下
        var co = co||'aso',$co = $.cookie(co);
        var coVal;
        if ($co!==null) {
            if ($co.indexOf(key)>-1) {
                var coArr = $co.split('||');
                var coLen = coArr.length;
                for (i = 0; i < coLen; i++) {
                    var vk = coArr[i].split(':=');
                    if (vk[0] === key) {
                        coArr[i] = key+':='+value;
                    }
                }
                coVal = coArr.join('||');
            }else {
                coVal = $co+'||'+key+':='+value;
            }
        }else {
            coVal = key+':='+value;
        }
        if (root) {
            $.cookie(co,coVal,{ path: '/'});
        }else {
            $.cookie(co,coVal);
        }
        //window.console && console.log('cooke中 '+co+'更新为 " '+$.cookie(co)+' " ');
    },
    data: function (target, attrName) {//获取对象数据，支持非标准json格式写法，数据放在 data-opt中
        attrName = attrName || 'data-opt';
        var options = {};
        var s = $.trim($(target).attr(attrName));
        s = s?s:$.trim($(target).attr('data-options'));
        if (s){
            if (s.substring(0, 1) != '{'){
                s = '{' + s + '}';
            }
            options = (new Function('return ' + s))();
        }
        return options;
    },
    format: function (tpl, params) {//简单的模板格式方法
        if (arguments.length > 2 && params.constructor != Array) {
            params = $.makeArray(arguments).slice(1);
        }
        if (params.constructor == String || params.constructor == Number) {
            params = [params];
        }
        function _replace(m, word) {
            var rst;
            if (Boolean(word.match(/^[0-9]+$/)) && params.constructor == Array) {
                rst = params[word * 1];
            } else {
                rst = params[word];
            }
            return rst === undefined || rst === null ? "" : rst;
        }

        return tpl.replace(/#?\{([A-Za-z_0-9]+)\}/g, _replace);
    },
    parseParam : function(param, key){
        var paramStr="";
        if(param instanceof String||param instanceof Number||param instanceof Boolean){
            paramStr+="&"+key+"="+encodeURIComponent(param);
        }else{
            $.each(param,function(i){
                var k=key==null?i:key+(param instanceof Array?"["+i+"]":"."+i);
                paramStr+='&'+$T.parseParam(this, k);
            });
        }
        return paramStr.substr(1);
    },
    notNull: function (obj, msg) {
        if (!$(obj).val()) {
            $pop.msg( msg || '不能为空!');
            return false;
        }
        return true;
    },
    getMonthDay : function (year, month) {
      var days = new Date(year, month, 0).getDate();
      return days;
    },
    getAgeByBir : function (date,monthAndDay,startDate) {//根据日期返回年龄，返回满岁，小于1岁算1岁;monthAndDay 为true时，小于1岁返回 月 和 天
      var me = this;
      if(date==='')return '';
      var sD = $.fmtDate('yyyy-MM-dd', date).split('-');
      var nD = $.fmtDate('yyyy-MM-dd',startDate).split('-');
      var age = nD[0] - sD[0] - 1;//年相减
      var m,d,bm;
      if(sD[1] < nD[1] || sD[1] == nD[1] && sD[2] <= nD[2]){//(生日 月小于当前月  || 月相同时，生日 日 小于等于当前日)+1岁
        age++;
      }
      if(monthAndDay){
        age = {type:1,value:age};//默认返回多少岁,type:1
        if(age.value>0 && age.value<6){//1-6岁间
          age.type = 2;
          if(sD[1] <= nD[1]){//(生日 月小于等于当前月
            m = nD[1]*1 - sD[1]*1;
          }else{
            m = 12 + nD[1]*1 - sD[1]*1;
          }
          if(sD[2] > nD[2]){m--;}//生日 日小于当前日
          m<0 && (m=m+12);
          age.value = age.value+ '岁' + (m==0?'': (m+ '月'));
        }
        if(age.value == 0){//小于1岁
          m = (nD[0] - sD[0])*12 + (nD[1] - sD[1]);//年相减*12 + 月相减
          d = nD[2] - sD[2];//日相减
          bm = me.getMonthDay(sD[0],sD[1]);//获取生日当前月
          if(d<0){//如果 生日 日 小于当前日期 日，则月-1,并且算出日差
            m --;
            d = bm + d;
          }
          //小于一岁，返回 M月D天，并且type:2
          age.type = 2;
          age.value = (m==0?'':(m+ '月')) + d + '天';
        }
        // console.log(m,'月',d,'天');
      }else{
        age = age <= 0?1:age;//小于1岁算1岁
      }
      return age;
    },
    setBirVal : function(bir){
      var age = $T.getAgeByBir(bir,true);
      if(age){
        $('.agebox').hide();
        $('.txt-age-1').val('0');//默认为0
        $('.agebox-'+age.type).show();
        $('.txt-age-'+age.type).val(age.value);
        $form.formAEnterFun(null,'.form-userInfo');
      }else{
        $('.agebox-1').show();
        $('.txt-age-1').val('');
        $('.agebox-2').hide();
      }
    },
    coverTel : function (tel,name,isReact) {
      if(tel){
        tel = tel+"";
        var reg = /^(\d{3})\d{4}(\d{4})$/;
        var msg = name?(name+ "的电话是："+tel): ('电话号码是：'+tel);
        if(isReact !== false){
          return "<span style='cursor: pointer;' title='"+tel+"' onclick='$pop.alert(\""+msg+"\" )'>"+tel.replace(reg,"$1****$2")+"</span>";
        }else{
          return "<span title='"+tel+"' >"+tel.replace(reg,"$1****$2")+"</span>";
        }
      }else{
        return '';
      }
    },
    placeHolder : {//对低版本浏览器placeholder属性的兼容
        init : function(par){//初始化
            if(!this._check()){
                this.fix(par);
            }
        },
        _check : function(){//检测
            return 'placeholder' in document.createElement('input');
        },
        fix : function(par){//修复
            $(par).find(':input[placeholder]').each(function(index, element) {
                var self = $(this), txt = self.attr('placeholder');
                self.wrap($('<span></span>').css({position:'relative', zoom:'1', border:'none', background:'none', padding:'none', margin:'none'}));
                var pos = self.position(), h = self.outerHeight(true), paddingleft = self.css('padding-left');
                var holder = $('<span class="s-placeholder"></span>').text(txt).css({position:'absolute', left:(pos.left+8), top:(pos.top+1), height:h, lineHeight:h+'px', paddingLeft:paddingleft, color:'#aaa'}).appendTo(self.parent());
                if (self.val()) {holder.hide();};
                self.focusin(function(e) {
                    holder.hide();
                }).focusout(function(e) {
                    if(!self.val()){
                        holder.show();
                    }
                });
                holder.click(function(e) {
                    holder.hide();
                    self.focus();
                });
            });
        }
    }

}
