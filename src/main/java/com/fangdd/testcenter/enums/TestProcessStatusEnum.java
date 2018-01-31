package com.fangdd.testcenter.enums;

public enum TestProcessStatusEnum {
	NOT_START(1, "未开始"), DOING(2, "进行中"), DONE(3, "已完成");

	private int status;
	private String description;

	private TestProcessStatusEnum(int status, String description) {
		this.status = status;
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public String getDescription() {
		return description;
	}

	public static TestProcessStatusEnum findByValue(int value) {
		for (TestProcessStatusEnum status : values()) {
			if (status.getStatus() == value) {
				return status;
			}
		}
		return null;
	}
}
