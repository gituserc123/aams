package com.aier.cloud.basic.starter.ui.shiro;

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
	

	/**
	 * 获取当前登陆账号
	 * @return
	 */
	public static AamsUiUser getAcrsUser() {
		Subject subject = SecurityUtils.getSubject();
		return (AamsUiUser)subject.getPrincipal();
	}
}
