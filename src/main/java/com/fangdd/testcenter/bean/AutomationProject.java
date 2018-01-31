package com.fangdd.testcenter.bean;

public class AutomationProject {
	/**
	 * 测试项目Id
	 */
	private long id;
	/**
	 * 部门ID
	 */
	private long departmentId;
	/**
	 * 部门名
	 */
	private String departmentName;
	/**
	 * 小组id
	 */
	private long teamId;
	/**
	 * 小组名称
	 */
	private String teamName;
	/**
	 * 项目名
	 */
	private String serviceName;

	/**
	 * 项目描述
	 */
	private String remark;

	/**
	 * 责任人
	 */
	private String pm;

	/**
	 * 录入时间
	 */
	private String createTime;

	/**
	 * 修改时间
	 */
	private String updateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public long getTeamId() {
		return teamId;
	}

	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPm() {
		return pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
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
