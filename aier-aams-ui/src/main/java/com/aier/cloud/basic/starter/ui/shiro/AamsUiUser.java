package com.aier.cloud.basic.starter.ui.shiro;

import com.aier.cloud.aams.api.request.domain.*;
import com.aier.cloud.basic.web.shiro.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * 临床科研登陆用户，与项目有关系
 *
 *
 * @author xiaokek
 * @since 2021年8月10日 下午4:29:32
 */
public class AamsUiUser extends ShiroUser{
	private Long projectId = 0L;
	private String projectName = "";

	private Long secUserId;

	private String secUserMainCode;

	private String deptMasterCode;

	private SecUser secUser;

	// 登录用户Genexus审计系统所有角色
	List<SecRole> auditRoles;

	// 登录用户Genexus审计系统所有部门
	List<DeptMaster> deptMasters;

	List<SecFunctionality> secFunctionalities;

	List<OrgMaster> orgMasters;

	public AamsUiUser(Long id, String loginCode, String loginName, Long instId, String instName, Long tenantId, Boolean isChangePassword, Boolean isAdmin, Boolean isHosp, List<?> depts, Long deptId) {
		super(id, loginCode, loginName, instId, instName, tenantId, isChangePassword, isAdmin,isHosp, depts, deptId);
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Long getSecUserId() {
		return secUserId;
	}

	public void setSecUserId(Long secUserId) {
		this.secUserId = secUserId;
	}

	public String getSecUserMainCode() {
		return secUserMainCode;
	}

	public void setSecUserMainCode(String secUserMainCode) {
		this.secUserMainCode = secUserMainCode;
	}

	public String getDeptMasterCode() {
		return deptMasterCode;
	}

	public void setDeptMasterCode(String deptMasterCode) {
		this.deptMasterCode = deptMasterCode;
	}

	public List<SecRole> getAuditRoles() {
		return auditRoles;
	}

	public void setAuditRoles(List<SecRole> auditRoles) {
		this.auditRoles = auditRoles;
	}

	public List<DeptMaster> getDeptMasters() {
		return deptMasters;
	}

	public void setDeptMasters(List<DeptMaster> deptMasters) {
		this.deptMasters = deptMasters;
	}

	public List<SecFunctionality> getSecFunctionalities() {
		return secFunctionalities;
	}

	public void setSecFunctionalities(List<SecFunctionality> secFunctionalities) {
		this.secFunctionalities = secFunctionalities;
	}

	public SecUser getSecUser() {
		return secUser;
	}

	public void setSecUser(SecUser secUser) {
		this.secUser = secUser;
	}

	public List<OrgMaster> getOrgMasters() {
		return orgMasters;
	}

	public void setOrgMasters(List<OrgMaster> orgMasters) {
		this.orgMasters = orgMasters;
	}

	/**
	 * 获取当前登陆账号
	 * @return
	 */
	public static AamsUiUser getAamsUser() {
		Subject subject = SecurityUtils.getSubject();
		return (AamsUiUser)subject.getPrincipal();
	}
}
