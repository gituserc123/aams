
package com.aier.cloud.ui.biz.common.service;

import com.aier.cloud.basic.api.request.condition.sys.AutoCompleteCondition;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface CommonService {
	List<Map<String,Object>> getAutoCompleteService(@RequestBody AutoCompleteCondition autoCompleteCondition);
	
    List<Map> getCustomList(@RequestParam("type") String paraType, @RequestParam("filter") String filter);
}
