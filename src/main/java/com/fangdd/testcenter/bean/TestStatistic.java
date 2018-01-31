package com.fangdd.testcenter.bean;

public class TestStatistic {
	/**
	 * 构建id
	 */
	private long id;
	/**
	 * 构建ID
	 */
	private long buildId;
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
	 * 类名
	 */
	private String className;
	/**
	 * 方法名
	 */
	private String caseName;
	/**
	 * 方法描述
	 */
	private String caseDescription;
	/**
	 * 信息
	 */
	private String caseMessage;
	/**
	 * 日志
	 */
	private String caseLog;

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
	 * 状态 1：成功 2：失败 3:跳过
	 */
	private int status;
	/**
	 * 状态 1：成功 2：失败 3:跳过
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

	public long getBuildId() {
		return buildId;
	}

	public void setBuildId(long buildId) {
		this.buildId = buildId;
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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getCaseDescription() {
		return caseDescription;
	}

	public void setCaseDescription(String caseDescription) {
		this.caseDescription = caseDescription;
	}

	public String getCaseMessage() {
		return caseMessage;
	}

	public void setCaseMessage(String caseMessage) {
		this.caseMessage = caseMessage;
	}

	public String getCaseLog() {
		return caseLog;
	}

	public void setCaseLog(String caseLog) {
		this.caseLog = caseLog;
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
