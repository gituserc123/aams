/**
 * 
 */
package com.aier.cloud.ui.biz.common;

import com.aier.cloud.basic.common.util.HanyuPinyinUtils;
import com.aier.cloud.basic.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author rain_deng
 *
 */
@Controller
@RequestMapping("/ui/common/pinyin4j")
public class PinYinController extends BaseController {
    
    private static final String REGEX =  "[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】《》‘；：”“’。，、？]";

    @PostMapping(value="/getPinYinWord")
    public @ResponseBody Map<String, Object> getPinyinWord(String input) {
        if (StringUtils.isBlank(input)) {
            return null;
        }
        
        Pattern p = Pattern.compile(REGEX); 
        Matcher m = p.matcher(input);
        input = m.replaceAll("").trim();
        return  defaultSuccess("", "pinyin", HanyuPinyinUtils.getFirstLettersUp(input).toUpperCase());
    }
    
}
