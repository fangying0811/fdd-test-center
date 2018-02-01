package com.fangdd.testcenter.bean;

/**
 * @author huangfangying
 *
 */
public class KafkaInfo {
	/**
	 * kafka_topic ID
	 */
	private long kafkaInfoId;

	/**
	 * 项目所属部门
	 */
	private Department department;
	/**
	 * 项目所属小组
	 */
	private Team team;
	/**
	 * 所属项目
	 */
	private ProjectInfo project;
	/**
	 * kafka_topic
	 */
	private String kafkaTopic;
	/**
	 * kafkaInfo录入时间
	 */
	private String createTime;

	public long getKafkaInfoId() {
		return kafkaInfoId;
	}

	public void setKafkaInfoId(long kafkaInfoId) {
		this.kafkaInfoId = kafkaInfoId;
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

	public String getKafkaTopic() {
		return kafkaTopic;
	}

	public void setKafkaTopic(String kafkaTopic) {
		this.kafkaTopic = kafkaTopic;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public ProjectInfo getProject() {
		return project;
	}

	public void setProject(ProjectInfo project) {
		this.project = project;
	}
}
