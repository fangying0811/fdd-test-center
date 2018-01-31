package com.fangdd.testcenter.bean;

public class User{
	/**
	 * 用户id
	 */
	private long userId;
	/**
	 * 登录用户名
	 */
	private String username;
	/**
	 * 旧密码
	 */
	private String oldPassword;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 真实姓名
	 */
	private String trueName;
	/**
	 * 箱邮
	 */
	private String email;
	/**
	 * 态状，0:启用,1:禁用
	 */
	private int status = -1;
	/**
	 * 状态文本描述，0:启用,1:禁用
	 */
	private String statusText;
	/**
	 * 否是管理员，0：系统管理员，1：部门管理员，2：小组管理员，3：普通用户
	 */
	private int isAdmin;
	/**
	 * 否是管理员，0：系统管理员，1：部门管理员，2：小组管理员，3：普通用户
	 */
	private String isAdminText;
	/**
	 * 用户所属部门信息
	 */
	private Department department;
	/**
	 * 用户所属部门信息
	 */
	private Team team;
	private String createTime;

	private String updateTime;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public String getIsAdminText() {
		return isAdminText;
	}

	public void setIsAdminText(String isAdminText) {
		this.isAdminText = isAdminText;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
