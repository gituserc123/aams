/**
 * 
 */
package com.aier.cloud.ui.biz.common;

import com.aier.cloud.basic.api.request.domain.sys.MailRequest;
import com.aier.cloud.basic.common.util.SpringUtils;
import com.aier.cloud.ui.biz.sys.service.MailService;
import com.google.common.collect.Maps;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author rain_deng
 *
 */
@Service
public class MailTemplate {

    @Autowired
    private MailService mailService;
    
    /**
     * 
     * @param title 标题
     * @param templateName 模板文件全路径
     * @param params 版本参数
     * @param to 收件人
     * @return
     */
    public boolean sendMessageMail(String title, String templateName, Object params, List<String> to) {
        Map<String, Object> model = Maps.newHashMap();
        model.put("params", params);
        try{
            FreeMarkerConfigurer configurer = SpringUtils.getBean("freeMarkerConfigurer", FreeMarkerConfigurer.class);
            Template template = configurer.getConfiguration().getTemplate(templateName);
            try {
                String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
                MailRequest request = new MailRequest();
                request.setSubject(title);
                request.setTo(to);
                request.setContent(content);
                mailService.sendHtmlMail(request);
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    
    
}
