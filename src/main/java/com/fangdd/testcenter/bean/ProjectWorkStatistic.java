package com.fangdd.testcenter.bean;

/**
 * 部门项目工作量统计
 * 
 * @author hexin
 * 
 */
public class ProjectWorkStatistic {
	/**
	 * 部门
	 */
	private Department department;
	/**
	 * 小组
	 */
	private Team team;
	/**
	 * 项目
	 */
	private ProjectInfo project;
	/**
	 * 开发总人数
	 */
	private int developer;
	/**
	 * 测试总人数
	 */
	private int tester;
	/**
	 * 测试开发比例
	 */
	private String percent;
	/**
	 * 上线版本数
	 */
	private int versionCount;
	/**
	 * 测试人均版本数
	 */
	private double versionPercent;
	/**
	 * 用例数
	 */
	private int caseCount;
	/**
	 * BUG数
	 */
	private int bugCount;
	/**
	 * 严重bug数
	 */
	public int bugCritical;
	/**
	 * 人均bug数
	 */
	private double perBugs;
	/**
	 * 外网问题数
	 */
	private int onlineIssueCount;
	/**
	 * 遗留问题数
	 */
	private int outstandingIssueCount;
	/**
	 * 查询起始时间
	 */
	private String startTime;
	/**
	 * 查询结束时间
	 */
	private String endTime;
	/**
	 * 统计类型
	 */
	private int statisticType;
	/**
	 * 类型 1:版本测试 3:活动测试 4：临时需求测试
	 */
	private int type;
	/**
	 * 类型：1，版本测试 3，活动测试 4，临时需求测试
	 */
	private String typeListStr;

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

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public int getVersionCount() {
		return versionCount;
	}

	public void setVersionCount(int versionCount) {
		this.versionCount = versionCount;
	}

	public double getVersionPercent() {
		return versionPercent;
	}

	public void setVersionPercent(double versionPercent) {
		this.versionPercent = versionPercent;
	}

	public int getCaseCount() {
		return caseCount;
	}

	public void setCaseCount(int caseCount) {
		this.caseCount = caseCount;
	}

	public int getBugCount() {
		return bugCount;
	}

	public void setBugCount(int bugCount) {
		this.bugCount = bugCount;
	}

	public int getBugCritical() {
		return bugCritical;
	}

	public void setBugCritical(int bugCritical) {
		this.bugCritical = bugCritical;
	}

	public int getOnlineIssueCount() {
		return onlineIssueCount;
	}

	public void setOnlineIssueCount(int onlineIssueCount) {
		this.onlineIssueCount = onlineIssueCount;
	}

	public int getOutstandingIssueCount() {
		return outstandingIssueCount;
	}

	public void setOutstandingIssueCount(int outstandingIssueCount) {
		this.outstandingIssueCount = outstandingIssueCount;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public ProjectInfo getProject() {
		return project;
	}

	public void setProject(ProjectInfo project) {
		this.project = project;
	}

	public double getPerBugs() {
		return perBugs;
	}

	public void setPerBugs(double perBugs) {
		this.perBugs = perBugs;
	}

	public int getStatisticType() {
		return statisticType;
	}

	public void setStatisticType(int statisticType) {
		this.statisticType = statisticType;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTypeListStr() {
		return typeListStr;
	}

	public void setTypeListStr(String typeListStr) {
		this.typeListStr = typeListStr;
	}
}
