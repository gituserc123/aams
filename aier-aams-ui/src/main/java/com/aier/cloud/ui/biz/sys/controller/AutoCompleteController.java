/**
 * 
 */
package com.aier.cloud.ui.biz.sys.controller;

import com.aier.cloud.basic.api.request.condition.sys.AutoCompleteCondition;
import com.aier.cloud.basic.web.controller.BaseController;
import com.aier.cloud.basic.web.shiro.ShiroUtils;
import com.aier.cloud.ui.biz.common.service.ProxyAutoCompleteService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 联想查询
 * 通过<select>元素创建一个预定义结构的ComboGrid。
 * 后期可针对性的优化查询，可走es等搜索引擎进行海量数据检索
 * @author rain_deng
 * @since 2018年2月6日 下午5:07:18
 */
@RestController
@RequestMapping("/ui/sys/autoComplete")
public class AutoCompleteController extends BaseController {

    @Autowired
    private ProxyAutoCompleteService autoCompleteService;
    
    //private static final String REGEX =  "['~!@#$%^&【】《》‘；：”“’。，、~！@#￥%……&*（）]";
    private static final String REGEX =  "['~!@#$%^&‘；：”“’。，、~！@#￥%……&*]";
    
    @PostMapping(value = "/query")
    public List<Map<String,Object>> getAutoCompleteService(AutoCompleteCondition autoCompleteCondition) {
        /** 不输入任何查询关键字，不请求查询结果 */
        if (StringUtils.isBlank(autoCompleteCondition.getQ())) {
            return Collections.emptyList();
        }else {
            Pattern p = Pattern.compile(REGEX); 
            Matcher m = p.matcher(autoCompleteCondition.getQ());
            autoCompleteCondition.setQ(m.replaceAll("").trim());
        	//把反斜杠替换成java识别的真反斜杠,避免错误:IllegalArgumentException: character to be escaped is missing
        	autoCompleteCondition.setQ(autoCompleteCondition.getQ().replaceAll("\\\\", "\\\\\\\\"));
        }
        
        if (autoCompleteCondition.getDeptEnable()) {
            autoCompleteCondition.setDept(ShiroUtils.getDeptId());
        }
        
        if (autoCompleteCondition.getTenantIdEnable()) {
            autoCompleteCondition.setTenantId(ShiroUtils.getTenantId());
        }
        
        return autoCompleteService.getAutoCompleteService(autoCompleteCondition);
    }
    
}
