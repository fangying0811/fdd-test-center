package com.fangdd.testcenter.bean;

/**
 * 遗留问题
 * 
 * @author hexin
 * 
 */
public class OutstandingIssue {
	/**
	 * 遗留问题记录id
	 */
	private long outstandingIssueId;
	/**
	 * 录入人员
	 */
	private User user;
	/**
	 * 录入人员所属部门
	 */
	private Department department;
	/**
	 * 录入人员所属小组
	 */
	private Team team;
	/**
	 * 所属项目
	 */
	private ProjectInfo project;
	/**
	 * 关联redmine bug ID
	 */
	private long issueId;
	/**
	 * 问题描述
	 */
	private String issueDescription;
	/**
	 * 解决状态，1：未解决，2：已解决
	 */
	private int resolveStatus;
	/**
	 * 解决状态描述，1：未解决，2：已解决
	 */
	private String resolveStatusText;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 备注,用于详情显示
	 */
	private String remarkDetail;
	/**
	 * 录入时间
	 */
	private String createTime;
	/**
	 * 修改时间
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

	public long getOutstandingIssueId() {
		return outstandingIssueId;
	}

	public void setOutstandingIssueId(long outstandingIssueId) {
		this.outstandingIssueId = outstandingIssueId;
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

	public long getIssueId() {
		return issueId;
	}

	public void setIssueId(long issueId) {
		this.issueId = issueId;
	}

	public String getIssueDescription() {
		return issueDescription;
	}

	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}

	public int getResolveStatus() {
		return resolveStatus;
	}

	public void setResolveStatus(int resolveStatus) {
		this.resolveStatus = resolveStatus;
	}

	public String getResolveStatusText() {
		return resolveStatusText;
	}

	public void setResolveStatusText(String resolveStatusText) {
		this.resolveStatusText = resolveStatusText;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemarkDetail() {
		return remarkDetail;
	}

	public void setRemarkDetail(String remarkDetail) {
		this.remarkDetail = remarkDetail;
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
}
