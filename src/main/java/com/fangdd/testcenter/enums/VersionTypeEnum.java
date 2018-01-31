package com.fangdd.testcenter.enums;

public enum VersionTypeEnum {
	VERSION(1, "版本测试"), ACTIVITY(3, "活动测试"), TEMPVERSION(4, "临时需求测试"), OTHER(2, "其他");

	private int type;
	private String description;

	private VersionTypeEnum(int type, String description) {
		this.type = type;
		this.description = description;
	}

	public int getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

	public static VersionTypeEnum findByValue(int value) {
		for (VersionTypeEnum types : values()) {
			if (types.getType() == value) {
				return types;
			}
		}
		return null;
	}
}
