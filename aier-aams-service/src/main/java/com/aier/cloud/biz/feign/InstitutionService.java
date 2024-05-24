package com.aier.cloud.biz.feign;

import com.aier.cloud.basic.api.domain.enums.InstEnum;
import com.aier.cloud.basic.api.request.condition.sys.InstCondition;
import com.aier.cloud.basic.api.request.domain.sys.InstitutionDept;
import com.aier.cloud.basic.api.request.domain.sys.StaffInst;
import com.aier.cloud.basic.api.response.domain.sys.Institution;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


/**
 * 组织机构服务
 * @author xiaokek
 * @since 2018年1月29日 上午10:11:40
 */
@FeignClient(name="aier-service-system")
public interface InstitutionService {
    
    /**
     * 根据id查询组织机构
     * @param id
     * @return
     */
    @RequestMapping(value = "/api/sys/inst/getById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    Institution getById(@RequestParam(value="id",defaultValue="1") Long id);
    
	/**
	 * 根据id查询组织机构树
	 * @param id
	 * @return
	 */
    @RequestMapping(value = "/api/sys/inst/getForTree", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	Object getForTree(@RequestParam(value="id",defaultValue="1") Long id);

    
    /**
     * 根据id查询组织机构树
     * @param id
     * @return
     */
    @RequestMapping(value = "/api/sys/inst/getStaffDeptForTree", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Map<String,Object>> getStaffDeptForTree(@RequestParam(value="id",defaultValue="1") Long id);
    
    /**
     * 根据父id查询下属机构
     * @param instId
     * @return
     */
    @RequestMapping(value = "/api/sys/inst/getInstByParent", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	Object getInstByParent(@RequestParam(value="instId",defaultValue="1")Long instId);
    
    
    /**
     * 获取用户关联的所有医院信息，登录时，通过输入登录工号获取
     * @param staffCode
     * @return
     */
    @RequestMapping(value = "/api/sys/inst/getListByStaffCode", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Institution> getListByStaffCode(@RequestParam("staffCode") String staffCode);
    
    /**
     *  获取用户关联的所有医院科室，登录时，通过输入登录工号获取
     * @param staffCode
     * @param instId
     * @return
     */
    @RequestMapping(value = "/api/sys/inst/getDeptListByStaffCodeAndInst", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Institution> getDeptListByStaffCodeAndInst(@RequestParam("staffCode") String staffCode, @RequestParam("instId") Long instId);
    
    /**
     *  获取用户关联的所有医院科室，获取带dept详细信息的dept集合
     * @param staffCode
     * @param instId
     * @return
     */
    @RequestMapping(value = "/api/sys/inst/getDeptDetailListByStaffCodeAndInst", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Institution> getDeptDetailListByStaffCodeAndInst(@RequestParam("staffCode") String staffCode, @RequestParam("instId") Long instId);
    
    /**
     * 根据机构id 获取机构科室详细信息
     * @param instId
     * @return
     */
    @RequestMapping(value = "/api/sys/inst/getDeptDetailByInstId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    Institution getDeptDetailByInstId(@RequestParam("instId") Long instId);
    
    /**
     * 根据机构类型获取机构列表
     * @param instEnum
     * @return
     */
    @RequestMapping(value = "/api/sys/inst/getListByInstType", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Institution> getListByInstType(@RequestParam("instEnum") InstEnum instEnum);
    
    /**
     * 根据机构类型, 当前机构id 获取不包含当前机构的机构列表
     * @param instEnum
     * @param instId
     * @return
     */
    @RequestMapping(value = "/api/sys/inst/getListByInstTypeAndInstId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Institution> getListByInstTypeAndInstId(@RequestParam("instEnum") InstEnum instEnum, @RequestParam("instId") Long instId);
    
    /**
     * 根据机构父id获取机构列表
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/api/sys/inst/getTreeByParent", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Map<String,Object>> getTreeByParent(@RequestParam("parentId") Long parentId);
    
    /**
     * 根据筛选条件查机构
     * @param sc
     * @return
     */
    @RequestMapping(value = "/api/sys/inst/getInstByConditionForSelect", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	List<Map> getInstByConditionForSelect(@RequestBody InstCondition sc);
    
    /**
     * 创建科室部门
     * @param institutionDept
     * @return
     */
    @RequestMapping(value = "/api/sys/inst/createDept", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    Boolean createDept(@RequestBody InstitutionDept institutionDept);
    
    /**
     * 修改科室部门
     * @param institutionDept
     * @return
     */
    @RequestMapping(value = "/api/sys/inst/updateDept", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    Boolean updateDept(InstitutionDept institutionDept);
    
    /**
     * 更改人员科室部门
     * @param staffInst
     * @return
     */
    @RequestMapping(value = "/api/sys/inst/staffChangeDept", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    Boolean staffChangeDept(@RequestBody StaffInst staffInst);
    
    
    
    /**
     * 新增人员科室部门
     * @param staffInst
     * @return
     */
    @RequestMapping(value = "/api/sys/inst/staffAddDept", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    Boolean staffAddDept(@RequestBody StaffInst staffInst);
    
    
    /**
     * 移除人员科室部门
     * @param staffInstIds 人员科室关系
     * @return
     */
    @RequestMapping(value = "/api/sys/inst/staffRemoveDept", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    Boolean staffRemoveDept(@RequestParam("staffInstIds") String staffInstIds);
    
    /**
     * 删除部门
     * @param id
     * @return
     */
    @RequestMapping(value = "/api/sys/inst/removeDept", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    Boolean removeDept(@RequestParam("id") Long id);
    
    /**
     * 提交他院人员坐诊表单
     * @param staffInst
     * @return
     */
    @RequestMapping(value = "/api/sys/inst/staffInstCreate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    Boolean staffInstCreate(@RequestBody StaffInst staffInst);
    

}
