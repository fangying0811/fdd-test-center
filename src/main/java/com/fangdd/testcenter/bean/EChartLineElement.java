package com.fangdd.testcenter.bean;

import java.util.List;

public class EChartLineElement {
	private String type = "line";
	private List<Integer> data;
	private String name;
	private int max = 0;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Integer> getData() {
		return data;
	}

	public void setData(List<Integer> data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}
}
