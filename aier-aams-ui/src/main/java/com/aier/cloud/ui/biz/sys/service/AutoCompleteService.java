/**
 * 
 */
package com.aier.cloud.ui.biz.sys.service;

import com.aier.cloud.basic.api.request.condition.sys.AutoCompleteCondition;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * @author rain_deng
 *
 */

/**
*
*  @author rain_deng
* @since 2018年1月29日 上午10:11:09
*/
@FeignClient(name="aier-service-system")
public interface AutoCompleteService {

	/**
	 * getAutoCompleteService
	 * @param autoCompleteCondition
	 * @return
	 */
    @RequestMapping(value = "/api/sys/autoComplete/query", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    List<Map<String,Object>> getAutoCompleteService(@RequestBody AutoCompleteCondition autoCompleteCondition);
    
}
