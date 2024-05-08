package com.aier.cloud.ui.template.directive;

import com.aier.cloud.basic.web.template.directive.BaseSelectDirective;
import com.aier.cloud.basic.web.template.directive.DirectiveHandler;
import com.aier.cloud.ui.biz.common.service.CommonService;
import com.aier.cloud.ui.biz.common.service.ProxyAutoCompleteService;
import com.aier.cloud.ui.biz.sys.service.DictService;
import com.google.common.collect.Lists;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 标准下拉组件
 * 通过<select></select>元素创建一个预定义结构的下拉列表框。
 * 支持数据量不大的码表
 *
 * @author xiaokek
 * @since 2018年2月6日 下午5:07:18
 */
@Component("ui_select")
public class SelectDirective extends BaseSelectDirective {
//    @Override
//    protected String filter() {
//        return "valueDesc";
//    }

    @Override
    protected String valueField() {
        return "valueCode";
    }

    @Override
    protected String valueDesc() {
        return "valueDesc";
    }

    @Override
    protected String filter() {
        return "valueDesc,firstSpell";
    }

    @Resource
    EnumDictResovler r;

    @Autowired
    ProxyAutoCompleteService ps;

    @Autowired
    DictService dictService;

    /**
     * 子类可通过覆盖数据得到
     *
     * @param handler
     * @return
     * @throws TemplateException
     */
    protected List<Map> remoteData(DirectiveHandler handler) throws TemplateException {

        String tag = handler.getString("tag");
        String filter = handler.getString("elf", "");

        if (StringUtils.isNoneBlank(tag)) {
            //直接用sql定制的业务数据下拉框,非码表
            String dot = ".";
            String prefix = "com";
            if (tag.indexOf(dot) >= 0 && !tag.startsWith(prefix)) {
                // 查询平台基础码表
                CommonService cs = ps.selectService(tag);
                return cs.getCustomList(tag, filter);
            } else if (tag.startsWith("@mw@")) {
                tag = tag.replace("@mw@", "");
                //List<Map> list =  ms.getList(tag,filter);
                // list.sort(Comparator.comparing((Map h) -> (Integer.valueOf(String.valueOf(h.get("valueCode"))))));
                List<Map> list = Lists.newArrayList();
                return list;
            } else if(tag.startsWith(prefix)){
                // 查询枚举com.aier.cloud.aams.api.enums.UsingSignEnum
                return Lists.newArrayList(r.getDictList(tag));
            }else{
                List<Map> list = dictService.getList(tag,filter);
                return list;
            }
        }
        return Collections.EMPTY_LIST;
    }

}

