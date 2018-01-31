package com.fangdd.testcenter.bean;

public class TestSuiteStatistic {
	/**
	 * 构建id
	 */
	private long id;
	/**
	 * 环境
	 */
	private String env;
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
	 * 服务ID
	 */
	private long serviceId;
	/**
	 * 服务名
	 */
	private String serviceName;
	/**
	 * 服务描述
	 */
	private String serviceDes;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 耗时
	 */
	private String durationTime;
	/**
	 * 通过用例数
	 */
	private int passed;
	/**
	 * 通过率
	 */
	private String passedPercent;
	/**
	 * 失败用例数
	 */
	private int failed;
	/**
	 * 失败率
	 */
	private String failedPercent;
	/**
	 * 跳过未执行用例数
	 */
	private int skipped;
	/**
	 * 跳过未执行率
	 */
	private String skippedPercent;
	/**
	 * 用例总数
	 */
	private int total;
	/**
	 * 状态 1：成功 2：失败
	 */
	private int status;
	/**
	 * 状态 1：成功 2：失败
	 */
	private String statusText;
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

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
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

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceDes() {
		return serviceDes;
	}

	public void setServiceDes(String serviceDes) {
		this.serviceDes = serviceDes;
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

	public String getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(String durationTime) {
		this.durationTime = durationTime;
	}

	public int getPassed() {
		return passed;
	}

	public void setPassed(int passed) {
		this.passed = passed;
	}

	public int getFailed() {
		return failed;
	}

	public void setFailed(int failed) {
		this.failed = failed;
	}

	public int getSkipped() {
		return skipped;
	}

	public void setSkipped(int skipped) {
		this.skipped = skipped;
	}

	public String getPassedPercent() {
		return passedPercent;
	}

	public void setPassedPercent(String passedPercent) {
		this.passedPercent = passedPercent;
	}

	public String getFailedPercent() {
		return failedPercent;
	}

	public void setFailedPercent(String failedPercent) {
		this.failedPercent = failedPercent;
	}

	public String getSkippedPercent() {
		return skippedPercent;
	}

	public void setSkippedPercent(String skippedPercent) {
		this.skippedPercent = skippedPercent;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
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
