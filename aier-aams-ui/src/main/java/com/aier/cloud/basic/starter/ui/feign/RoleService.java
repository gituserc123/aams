package com.aier.cloud.basic.starter.ui.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * 角色服务
 * @author rain_deng
 * @since 2018年7月22日 下午4:40:39
 */
@FeignClient(name="aier-service-system")
public interface RoleService{

    /**
     * 获取角色权限
     * @param roleId
     * @param isEdit
     * @param platformCode
     * @return
     */
    @RequestMapping(value="/api/sys/role/getPermByRoleId", method=RequestMethod.POST)
	Object getPermByRoleId(@RequestParam(value="roleId") Long roleId,@RequestParam(value="isEdit") boolean isEdit, @RequestParam(value="platformCode") String platformCode);

    
    /**
     * 获取角色权限
     * @param roleId
     * @return
     */
    @RequestMapping(value="/api/sys/role/delete", method=RequestMethod.POST)
	Void delete(@RequestParam(value="roleId") Long roleId);

    
    /**
     * 根据登录账号id和登录机构id获取其对应的所有权限角色列表
     * @param staffId 登录用户id
     * @param instId 登录机构id
     * @return
     */
    @RequestMapping(value="/api/sys/role/selectRolesByStaff", method=RequestMethod.POST)
    List<String> selectRolesByStaff(@RequestParam(value="staffId") Long staffId, @RequestParam(value="instId") Long instId);
}
