package com.fangdd.testcenter.bean;

/**
 * 测试报告，基础专用
 * 
 * @author xielu
 *
 */

public class TestReport {

	/**
	 * 测试报告Id
	 */
	private long testReportId;

	/**
	 * 录入人员
	 */
	private User user;

	/**
	 * 所属部门
	 */
	private Department department;

	/**
	 * 所属小组
	 */
	private Team team;

	/**
	 * 所属项目
	 */
	private ProjectInfo project;

	/**
	 * 测试人员 允许多个
	 */
	private String resource;

	/**
	 * 查询开始时间
	 */
	private String startTime;

	/**
	 * 实际开始时间
	 */
	private String trueStartTime;

	/**
	 * 实际结束时间
	 */
	private String trueEndTime;

	/**
	 * 查询结束时间
	 */
	private String endTime;

	/**
	 * 测试结论 0 不通过 1 通过
	 */
	private Integer testResult;

	/**
	 * 列表展示测试结果
	 */
	private String testResultText;

	/**
	 * 总结
	 */
	private String summary;

	/**
	 * 版本功能列表
	 */
	private String versionDetail;

	/**
	 * 遗留问题
	 */
	private String issues;
	/**
	 * 线上验证点
	 */
	private String onlineVerification;

	/**
	 * 版本描述信息,用于列表展示
	 */
	private String versionInfo;

	/**
	 * 录入时间
	 */
	private String createTime;

	/**
	 * 修改时间
	 */
	private String updateTime;

	/**
	 * 用例数
	 */
	private Integer cases;

	/**
	 * bug数
	 */
	private Integer bugs;

	/**
	 * bug链接
	 */
	private String bugsLink;

	/**
	 * 需求链接
	 */
	private String requirementLink;
	/**
	 * 转测tag
	 */
	//private String devTag;
	/**
	 * 发布tag
	 */
	//private String releaseTag;

	/**
	 * 严重bug数
	 * @return
	 */
	private Integer bugCritical;

	public long getTestReportId() {
		return testReportId;
	}

	public void setTestReportId(long testReportId) {
		this.testReportId = testReportId;
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

	public ProjectInfo getProject() {
		return project;
	}

	public void setProject(ProjectInfo project) {
		this.project = project;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getTrueStartTime() {
		return trueStartTime;
	}

	public void setTrueStartTime(String trueStartTime) {
		this.trueStartTime = trueStartTime;
	}

	public String getTrueEndTime() {
		return trueEndTime;
	}

	public void setTrueEndTime(String trueEndTime) {
		this.trueEndTime = trueEndTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getTestResult() {
		return testResult;
	}

	public void setTestResult(Integer testResult) {
		this.testResult = testResult;
	}

	public String getTestResultText() {
		return testResultText;
	}

	public void setTestResultText(String testResultText) {
		this.testResultText = testResultText;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getVersionDetail() {
		return versionDetail;
	}

	public void setVersionDetail(String versionDetail) {
		this.versionDetail = versionDetail;
	}

	public String getIssues() {
		return issues;
	}

	public void setIssues(String issues) {
		this.issues = issues;
	}

	public String getVersionInfo() {
		return versionInfo;
	}

	public void setVersionInfo(String versionInfo) {
		this.versionInfo = versionInfo;
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

	public Integer getCases() {
		return cases;
	}

	public void setCases(Integer cases) {
		this.cases = cases;
	}

	public Integer getBugCritical() {
		return bugCritical;
	}

	public void setBugCritical(Integer bugCritical) {
		this.bugCritical = bugCritical;
	}

	public Integer getBugs() {
		return bugs;
	}

	public void setBugs(Integer bugs) {
		this.bugs = bugs;
	}

	public String getBugsLink() {
		return bugsLink;
	}

	public void setBugsLink(String bugsLink) {
		this.bugsLink = bugsLink;
	}

	public String getRequirementLink() {
		return requirementLink;
	}

	public void setRequirementLink(String requirementLink) {
		this.requirementLink = requirementLink;
	}

	public String getOnlineVerification() {
		return onlineVerification;
	}

	public void setOnlineVerification(String onlineVerification) {
		this.onlineVerification = onlineVerification;
	}
	
}
