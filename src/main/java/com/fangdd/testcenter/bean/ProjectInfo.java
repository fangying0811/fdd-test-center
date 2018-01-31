package com.fangdd.testcenter.bean;

/**
 * 项目相关
 * 
 * @author hexin
 * 
 */
public class ProjectInfo {
	/**
	 * 项目id
	 */
	private long projectId;
	/**
	 * 项目名称
	 */
	private String projectName;
	/**
	 * redmine项目id
	 */
	private long redmineProjectId;
	/**
	 * 项目录入人员
	 */
	private User user;
	/**
	 * 项目所属部门
	 */
	private Department department;
	/**
	 * 项目所属小组
	 */
	private Team team;
	/**
	 * 开发总人数
	 */
	private int developer;
	/**
	 * 测试总人数
	 */
	private int tester;
	/**
	 * 态状，1:启用,2:禁用
	 */
	private int status;
	/**
	 * 态状，1:启用,2:禁用
	 */
	private String statusText;
	/**
	 * 项目录入时间
	 */
	private String createTime;
	/**
	 * 项目修改时间
	 */
	private String updateTime;

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public long getRedmineProjectId() {
		return redmineProjectId;
	}

	public void setRedmineProjectId(long redmineProjectId) {
		this.redmineProjectId = redmineProjectId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
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

	public int getDeveloper() {
		return developer;
	}

	public void setDeveloper(int developer) {
		this.developer = developer;
	}

	public int getTester() {
		return tester;
	}

	public void setTester(int tester) {
		this.tester = tester;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
}
