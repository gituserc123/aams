package com.aier.cloud.basic.starter.ui.feign;

import com.aier.cloud.basic.api.request.condition.sys.ModuleCondition;
import com.aier.cloud.basic.api.response.domain.base.PageResponse;
import com.aier.cloud.basic.api.response.domain.sys.Module;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


/**
 *
 *  @author rain_deng
 * @since 2018年1月29日 上午10:11:09
 */
@FeignClient(name="aier-service-system")
public interface ModuleService {
    
	/**
	 * 根据id获取菜单详细信息
	 * @param id
	 * @return
	 */
    @RequestMapping(value = "/api/sys/module/getById", method = RequestMethod.POST)
    Module getById(@RequestParam("id") Long id);
    
    /**
     * 获取根节点
     * @param platformCode
     * @return
     */
    @RequestMapping(value = "/api/sys/module/getRoot", method = RequestMethod.POST)
    Module getRoot(@RequestParam("platformCode") String platformCode);
    
    /**
     * 根据id获取菜单和操作信息
     * @param id
     * @param platformCode
     * @return
     */
    @RequestMapping(value = "/api/sys/module/getForTree", method = RequestMethod.POST)
    List<Map<String, Object>> getForTree(@RequestParam("platformCode") String platformCode);
    
    /**
     * 根据当前父根节点获取以下的子节点菜单
     * @param moduleCondition
     * @return
     */
    @RequestMapping(value = "/api/sys/module/getPageByParent", method = RequestMethod.POST)
    PageResponse<Map<String, Object>> getPageByParent(@RequestBody ModuleCondition moduleCondition);
    
    /**
     * 根据id获取菜单和操作信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/api/sys/module/getModulePermissionsById", method = RequestMethod.POST)
    Module getModulePermissionsById(@RequestParam("id") Long id);
    
    /**
     * 获取除根节点外的所有菜单
     * getModules 
     * @return List<Module>
     */
    @RequestMapping(value = "/api/sys/module/getModules", method = RequestMethod.POST)
    List<Module> getModules();
    
    /**
     * 获取除根节点外的所有菜单
     * @param platformCode 平台编码
     * @return List<Module>
     */
    @RequestMapping(value = "/api/sys/module/getModulesByPlatform", method = RequestMethod.POST)
    List<Module> getModulesByPlatform(@RequestParam("platformCode") String platformCode);
    
    /**
     * 获取除根节点外的所有菜单
     * getModules 
     * @param platformCode
     * @return List<Module>
     */
    @RequestMapping(value = "/api/sys/module/getAllList", method = RequestMethod.POST)
    List<Module> getAllList(@RequestParam("platformCode") String platformCode);
    
    
    /**
     * 根据id获取菜单详细信息
     * @param module
     * @return
     */
    @RequestMapping(value = "/api/sys/module/create", method = RequestMethod.POST)
    Boolean create(@RequestBody Module module);
    
    /**
     * 根据id获取菜单详细信息
     * @param module
     * @return
     */
    @RequestMapping(value = "/api/sys/module/update", method = RequestMethod.POST)
    Boolean update(@RequestBody Module module);
    
    /**
     * 根据id删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/api/sys/module/delete", method = RequestMethod.POST)
    Boolean delete(@RequestParam("id") Long id);
    
    /**
     * 判断菜单代码是否唯一
     * @param previousCode 之前站点代码
     * @param currentCode 当前菜单代码
     * @return boolean
     */
    @RequestMapping(value = "/api/sys/module/getCodeUnique", method = RequestMethod.POST)
    Boolean getCodeUnique(@RequestParam("previousCode") String previousCode, @RequestParam("currentCode") String currentCode);
    
}
