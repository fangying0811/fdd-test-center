package com.fangdd.testcenter.bean;

import java.util.List;

public class LineChartElement {
	private String type = "line";
	private List<Integer> values;
	private String colour;
	private String text;
	private String tip;
	private int max = 0;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Integer> getValues() {
		return values;
	}

	public void setValues(List<Integer> values) {
		this.values = values;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip + ": #val#";
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}
}
