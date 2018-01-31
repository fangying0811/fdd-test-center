package com.fangdd.testcenter.bean;

public class Team {
	/**
	 * 小组id
	 */
	private long teamId;
	/**
	 * '小组名称
	 */
	private String name;
	/**
	 * 小组所属部门
	 */
	private Department department;
	/**
	 * 开发总人数
	 */
	private int developer;
	/**
	 * 测试总人数
	 */
	private int tester;

	private String createTime;

	private String updateTime;

	public long getTeamId() {
		return teamId;
	}

	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
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
