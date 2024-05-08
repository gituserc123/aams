package com.aier.cloud.ui.biz.sys.service;

import com.aier.cloud.basic.api.request.condition.sys.RoleCondition;
import com.aier.cloud.basic.api.response.domain.sys.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * 角色服务
 * @author xiaokek
 * @since 2018年3月12日 下午4:40:39
 */
@FeignClient(name="aier-service-system")
public interface RoleService{

    
    /**
	 * 获取角色值List
     * @param r 
	 * @return
	 */
    @RequestMapping(value="/api/sys/role/getForTree", method=RequestMethod.POST)
    List getForTree(Role r);
    
    
    /**
     * 修改角色值
     * @param id
     * @param value
     * @return
     */
    @RequestMapping(value="/api/sys/role/updateValue", method=RequestMethod.POST)
    Void updateValue(@RequestParam(value="id")Long id, @RequestParam(value="value")String value);

    /**
     * 获取角色权限
     * @param roleId
     * @param isEdit
     * @return
     */
    @RequestMapping(value="/api/sys/role/getPermByRoleId", method=RequestMethod.POST)
	Object getPermByRoleId(@RequestParam(value="roleId") Long roleId,@RequestParam(value="isEdit") boolean isEdit, @RequestParam(value="platformCode") String platformCode);

    /**
     * 角色赋权
     * @param roleId
     * @param permIds
     * @return
     */
    @RequestMapping(value="/api/sys/role/updateRolePerm", method=RequestMethod.POST)
    Void updateRolePerm(RoleCondition rc);
    
    /**
     * 修改角色
     * @param role
     * @return
     */
    @RequestMapping(value="/api/sys/role/update", method=RequestMethod.POST, produces=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Void update(Role role);
    
    /**
     * 获取角色权限
     * @param roleId
     * @return
     */
    @RequestMapping(value="/api/sys/role/delete", method=RequestMethod.POST)
	Void delete(@RequestParam(value="roleId") Long roleId);


    /**
     * getById
     * @param id
     * @return
     */
    @RequestMapping(value="/api/sys/role/getById", method=RequestMethod.POST)
	Object getById(@RequestParam(value="id")Long id);
    
}
