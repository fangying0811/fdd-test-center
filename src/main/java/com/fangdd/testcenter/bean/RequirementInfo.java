package com.fangdd.testcenter.bean;

/**
 * 测试需求相关
 * 
 * @author hexin
 * 
 */
public class RequirementInfo {
	/**
	 * 测试需求id
	 */
	private long requirementId;
	/**
	 * 测试需求录入人员
	 */
	private User user;
	/**
	 * 测试需求录入人员所属部门
	 */
	private Department department;
	/**
	 * 测试需求录入人员所属小组
	 */
	private Team team;
	/**
	 * 测试需求所属项目
	 */
	private ProjectInfo project;
	/**
	 * 版本描述信息，用于列表展示
	 */
	private String versionInfo;
	/**
	 * 版本描述信息，用于详情展示
	 */
	private String versionInfoDetail;
	/**
	 * 测试人力资源
	 */
	private String resource;
	/**
	 * 测试需求描述信息，用于列表展示
	 */
	private String requirementInfo;
	/**
	 * 测试需求描述信息，用于详情展示
	 */
	private String requirementInfoDetail;
	/**
	 * 计划测试开始时间，null或空表示待定
	 */
	private String planStartTime;
	/**
	 * 计划版本发布时间，null或空表示待定
	 */
	private String versionReleaseTime;
	/**
	 * 测试需求录入时间
	 */
	private String createTime;
	/**
	 * 测试需求修改时间
	 */
	private String updateTime;
	/**
	 * 录入查询起始时间
	 */
	private String startTime;
	/**
	 * 录入查询结束时间
	 */
	private String endTime;
	/**
	 * 计划测试查询起始时间
	 */
	private String planTestStartTime;
	/**
	 * 计划测试查询结束时间
	 */
	private String planTestEndTime;
	/**
	 * 计划发布查询起始时间
	 */
	private String versionReleaseStartTime;
	/**
	 * 计划发布查询结束时间
	 */
	private String versionReleaseEndTime;

	public long getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(long requirementId) {
		this.requirementId = requirementId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ProjectInfo getProject() {
		return project;
	}

	public void setProject(ProjectInfo project) {
		this.project = project;
	}

	public String getVersionInfo() {
		return versionInfo;
	}

	public void setVersionInfo(String versionInfo) {
		this.versionInfo = versionInfo;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getRequirementInfo() {
		return requirementInfo;
	}

	public void setRequirementInfo(String requirementInfo) {
		this.requirementInfo = requirementInfo;
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

	public String getPlanStartTime() {
		return planStartTime;
	}

	public void setPlanStartTime(String planStartTime) {
		this.planStartTime = planStartTime;
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

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getVersionInfoDetail() {
		return versionInfoDetail;
	}

	public void setVersionInfoDetail(String versionInfoDetail) {
		this.versionInfoDetail = versionInfoDetail;
	}

	public String getRequirementInfoDetail() {
		return requirementInfoDetail;
	}

	public void setRequirementInfoDetail(String requirementInfoDetail) {
		this.requirementInfoDetail = requirementInfoDetail;
	}

	public String getVersionReleaseTime() {
		return versionReleaseTime;
	}

	public void setVersionReleaseTime(String versionReleaseTime) {
		this.versionReleaseTime = versionReleaseTime;
	}

	public String getPlanTestStartTime() {
		return planTestStartTime;
	}

	public void setPlanTestStartTime(String planTestStartTime) {
		this.planTestStartTime = planTestStartTime;
	}

	public String getPlanTestEndTime() {
		return planTestEndTime;
	}

	public void setPlanTestEndTime(String planTestEndTime) {
		this.planTestEndTime = planTestEndTime;
	}

	public String getVersionReleaseStartTime() {
		return versionReleaseStartTime;
	}

	public void setVersionReleaseStartTime(String versionReleaseStartTime) {
		this.versionReleaseStartTime = versionReleaseStartTime;
	}

	public String getVersionReleaseEndTime() {
		return versionReleaseEndTime;
	}

	public void setVersionReleaseEndTime(String versionReleaseEndTime) {
		this.versionReleaseEndTime = versionReleaseEndTime;
	}
}
