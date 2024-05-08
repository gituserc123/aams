package com.aier.cloud.ui.biz.common.service;

import com.aier.cloud.basic.api.request.condition.sys.AutoCompleteCondition;
import com.aier.cloud.basic.common.exception.BizException;
import com.aier.cloud.basic.common.util.SpringUtils;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 代理转发万能sql的请求
 *
 *
 * @author xiaokek
 * @since 2018年12月3日 下午3:10:07
 */
@Service
public class ProxyAutoCompleteService {
	public static final String AIER_SERVICE_SYSTEM = "aier-service-system";
	public static final String AIER_SERVICE_BASED = "aier-service-based";
	public static final String AIER_SERVICE_MEDICAL = "aier-service-medical";
	public List<Map<String,Object>> getAutoCompleteService(@RequestBody AutoCompleteCondition autoCompleteCondition){
		String tag = autoCompleteCondition.getTag();
		CommonService cs = selectService(tag);
		return cs.getAutoCompleteService(autoCompleteCondition);
	}
	public CommonService selectService(String tag) {
		if(StringUtils.isEmpty(tag)) {
			throw new BizException("万能sql服务不存在");
		}
		tag = tag.toLowerCase();
		
		if(tag.startsWith("sys")) {
			return SpringUtils.getBean(SysAutoCompleteService.class);
		}else if(tag.startsWith("base") || tag.startsWith("dch") || tag.startsWith("dcg")) {
			return SpringUtils.getBean(BasedAutoCompleteService.class);
		}else {
			return SpringUtils.getBean(MeidcalAutoCompleteService.class);
		}
	}
}


@FeignClient(name= ProxyAutoCompleteService.AIER_SERVICE_SYSTEM)
interface SysAutoCompleteService  extends CommonService {

    @RequestMapping(value = "/api/"+ ProxyAutoCompleteService.AIER_SERVICE_SYSTEM+"/autoComplete/query", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    List<Map<String,Object>> getAutoCompleteService(@RequestBody AutoCompleteCondition autoCompleteCondition);
    
    @RequestMapping("/api/"+ ProxyAutoCompleteService.AIER_SERVICE_SYSTEM+"/autoComplete/getCustomList")
    List<Map> getCustomList(@RequestParam("type") String type, @RequestParam("filter") String filter);
    
}

@FeignClient(name= ProxyAutoCompleteService.AIER_SERVICE_BASED)
interface BasedAutoCompleteService extends CommonService {

    @RequestMapping(value = "/api/"+ ProxyAutoCompleteService.AIER_SERVICE_BASED+"/autoComplete/query", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    List<Map<String,Object>> getAutoCompleteService(@RequestBody AutoCompleteCondition autoCompleteCondition);

    @RequestMapping("/api/"+ ProxyAutoCompleteService.AIER_SERVICE_BASED+"/autoComplete/getCustomList")
    List<Map> getCustomList(@RequestParam("type") String type, @RequestParam("filter") String filter);
    
}

@FeignClient(name= ProxyAutoCompleteService.AIER_SERVICE_MEDICAL)
interface MeidcalAutoCompleteService  extends CommonService {

    @RequestMapping(value = "/api/"+ ProxyAutoCompleteService.AIER_SERVICE_MEDICAL+"/autoComplete/query", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    List<Map<String,Object>> getAutoCompleteService(@RequestBody AutoCompleteCondition autoCompleteCondition);

    @RequestMapping("/api/"+ ProxyAutoCompleteService.AIER_SERVICE_MEDICAL+"/autoComplete/getCustomList")
    List<Map> getCustomList(@RequestParam("type") String type, @RequestParam("filter") String filter);
    
}