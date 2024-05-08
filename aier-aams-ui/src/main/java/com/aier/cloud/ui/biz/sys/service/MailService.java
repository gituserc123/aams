package com.aier.cloud.ui.biz.sys.service;

import com.aier.cloud.basic.api.request.domain.sys.MailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 邮件服务
 * @author rain_deng
 * @since 2018年6月7日 下午4:40:39
 */
@FeignClient(name="aier-service-system")
public interface MailService{
  
    /**
     * 发送html格式邮件
     * @param subject 主题
     * @param content 内容
     * @param to 收件人
     */
    @RequestMapping(value="/api/sys/mail/sendHtmlMail", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    Boolean sendHtmlMail(@RequestBody MailRequest mailRequest);
    
    
    
}
