package com.fangdd.testcenter.enums;

public enum TestSuiteStatisticStatusEnum {
	PASSED(1, "通过"), FAILED(2, "失败");

	private int status;
	private String description;

	private TestSuiteStatisticStatusEnum(int status, String description) {
		this.status = status;
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public String getDescription() {
		return description;
	}

	public static TestSuiteStatisticStatusEnum findByValue(int value) {
		for (TestSuiteStatisticStatusEnum status : values()) {
			if (status.getStatus() == value) {
				return status;
			}
		}
		return null;
	}
}
