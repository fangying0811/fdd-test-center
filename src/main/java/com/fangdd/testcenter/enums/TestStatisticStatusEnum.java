package com.fangdd.testcenter.enums;

public enum TestStatisticStatusEnum {
	PASSED(1, "通过"), FAILED(2, "失败"), SKIPPED(3, "未执行");

	private int status;
	private String description;

	private TestStatisticStatusEnum(int status, String description) {
		this.status = status;
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public String getDescription() {
		return description;
	}

	public static TestStatisticStatusEnum findByValue(int value) {
		for (TestStatisticStatusEnum status : values()) {
			if (status.getStatus() == value) {
				return status;
			}
		}
		return null;
	}
}
