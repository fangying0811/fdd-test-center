package com.fangdd.testcenter.bean;

/**
 * 周报相关
 * 
 * @author hexin
 * 
 */
public class WeeklyReport {
	/**
	 * 周报id
	 */
	private long weeklyReportId;
	/**
	 * 周报录入人员
	 */
	private User user;
	/**
	 * 周报录入人员所属部门
	 */
	private Department department;
	/**
	 * 周报录入人员所属小组
	 */
	private Team team;
	/**
	 * 周报所属项目
	 */
	private ProjectInfo project;
	/**
	 * 类型：1，版本测试 2，其他 3，活动测试 4，临时需求测试
	 */
	private int type;
	/**
	 * 类型：1，版本测试 2，其他 3，活动测试 4，临时需求测试
	 */
	private String typeText;
	/**
	 * 类型：1，版本测试 2，其他 3，活动测试 4，临时需求测试
	 */
	private String typeListStr;
	/**
	 * 版本描述信息,用于列表展示
	 */
	private String versionInfo;
	/**
	 * 版本描述信息,用于详情展示
	 */
	private String versionInfoDetail;
	/**
	 * 测试责任人
	 */
	private String resource;
	/**
	 * 开发总人数
	 */
	private int developer;
	/**
	 * 测试总人数
	 */
	private int tester;
	/**
	 * 版本用例总数
	 */
	private int caseNumber;
	/**
	 * 版本转测时间，null或空表示待定
	 */
	private String versionTestTime;
	/**
	 * 版本测试时间，0：待定，非0：具体测试时间天数(如3.5天)
	 */
	private double testTime;
	/**
	 * 版本测试时间文本描述，0：待定，非0：具体测试时间天数(如3.5)
	 */
	private String testTimeText;
	/**
	 * 测试进度：1：未开始，2：进行中，3：已完成
	 */
	private int testStatus;
	/**
	 * 测试进度文本描述：1：未开始，2：进行中，3：已完成
	 */
	private String testStatusText;
	/**
	 * 版本bug总数
	 */
	private int bugNumber;
	/**
	 * 人均bug数
	 */
	private double perBugs;
	/**
	 * 版本发布时间，null或空表示待定
	 */
	private String versionReleaseTime;
	/**
	 * 备注,用于列表展示
	 */
	private String remark;
	/**
	 * 备注,用于详情展示
	 */
	private String remarkDetail;
	/**
	 * 周报录入时间
	 */
	private String createTime;
	/**
	 * 周报修改时间
	 */
	private String updateTime;
	/**
	 * 查询起始时间
	 */
	private String startTime;
	/**
	 * 查询结束时间
	 */
	private String endTime;
	/**
	 * 版本发布起始时间
	 */
	private String releaseStartTime;
	/**
	 * 版本发布结束时间
	 */
	private String releaseEndTime;
	/**
	 * 排序 asc:升序 desc:倒叙
	 */
	private String orderBy;

	/**
	 * 严重bug数
	 */
	public int bugCritical;

	public int getBugCritical() {
		return bugCritical;
	}

	public void setBugCritical(int bugCritical) {
		this.bugCritical = bugCritical;
	}

	public long getWeeklyReportId() {
		return weeklyReportId;
	}

	public void setWeeklyReportId(long weeklyReportId) {
		this.weeklyReportId = weeklyReportId;
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

	public int getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(int caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getVersionTestTime() {
		return versionTestTime;
	}

	public void setVersionTestTime(String versionTestTime) {
		this.versionTestTime = versionTestTime;
	}

	public double getTestTime() {
		return testTime;
	}

	public void setTestTime(double testTime) {
		this.testTime = testTime;
	}

	public String getTestTimeText() {
		return testTimeText;
	}

	public void setTestTimeText(String testTimeText) {
		this.testTimeText = testTimeText;
	}

	public int getTestStatus() {
		return testStatus;
	}

	public void setTestStatus(int testStatus) {
		this.testStatus = testStatus;
	}

	public int getBugNumber() {
		return bugNumber;
	}

	public void setBugNumber(int bugNumber) {
		this.bugNumber = bugNumber;
	}

	public String getVersionReleaseTime() {
		return versionReleaseTime;
	}

	public void setVersionReleaseTime(String versionReleaseTime) {
		this.versionReleaseTime = versionReleaseTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getTestStatusText() {
		return testStatusText;
	}

	public void setTestStatusText(String testStatusText) {
		this.testStatusText = testStatusText;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
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

	public String getVersionInfoDetail() {
		return versionInfoDetail;
	}

	public void setVersionInfoDetail(String versionInfoDetail) {
		this.versionInfoDetail = versionInfoDetail;
	}

	public String getRemarkDetail() {
		return remarkDetail;
	}

	public void setRemarkDetail(String remarkDetail) {
		this.remarkDetail = remarkDetail;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTypeText() {
		return typeText;
	}

	public void setTypeText(String typeText) {
		this.typeText = typeText;
	}

	public String getReleaseStartTime() {
		return releaseStartTime;
	}

	public void setReleaseStartTime(String releaseStartTime) {
		this.releaseStartTime = releaseStartTime;
	}

	public String getReleaseEndTime() {
		return releaseEndTime;
	}

	public void setReleaseEndTime(String releaseEndTime) {
		this.releaseEndTime = releaseEndTime;
	}

	public double getPerBugs() {
		return perBugs;
	}

	public void setPerBugs(double perBugs) {
		this.perBugs = perBugs;
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

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getTypeListStr() {
		return typeListStr;
	}

	public void setTypeListStr(String typeListStr) {
		this.typeListStr = typeListStr;
	}
}
