package com.fangdd.testcenter.bean;

/**
 * 线上问题
 * 
 * @author hexin
 * 
 */
public class OnlineIssue {
	/**
	 * 线上问题记录id
	 */
	private long onlineIssueId;
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
	 * 问题原因
	 */
	private String reason;
	/**
	 * 问题原因
	 */
	private String reasonDetail;
	/**
	 * 解决方案
	 */
	private String solution;
	/**
	 * 解决方案
	 */
	private String solutionDetail;
	/**
	 * 改进措施
	 */
	private String improvement;
	/**
	 * 改进措施
	 */
	private String improvementDetail;
	/**
	 * 分析进度，1：未分析，2：已分析
	 */
	private int process;
	/**
	 * 分析进度描述，1：未分析，2：已分析
	 */
	private String processText;
	/**
	 * 解决状态，1：未解决，2：已解决
	 */
	private int resolveStatus;
	/**
	 * 解决状态描述，1：未解决，2：已解决
	 */
	private String resolveStatusText;
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

	public long getOnlineIssueId() {
		return onlineIssueId;
	}

	public void setOnlineIssueId(long onlineIssueId) {
		this.onlineIssueId = onlineIssueId;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReasonDetail() {
		return reasonDetail;
	}

	public void setReasonDetail(String reasonDetail) {
		this.reasonDetail = reasonDetail;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getSolutionDetail() {
		return solutionDetail;
	}

	public void setSolutionDetail(String solutionDetail) {
		this.solutionDetail = solutionDetail;
	}

	public String getImprovement() {
		return improvement;
	}

	public void setImprovement(String improvement) {
		this.improvement = improvement;
	}

	public String getImprovementDetail() {
		return improvementDetail;
	}

	public void setImprovementDetail(String improvementDetail) {
		this.improvementDetail = improvementDetail;
	}

	public int getProcess() {
		return process;
	}

	public void setProcess(int process) {
		this.process = process;
	}

	public String getProcessText() {
		return processText;
	}

	public void setProcessText(String processText) {
		this.processText = processText;
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
