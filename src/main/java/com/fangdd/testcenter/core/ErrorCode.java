package com.fangdd.testcenter.core;

import java.io.Serializable;

/**
 * 业务异常编码
 *
 * @since 1.0.0
 */
public class ErrorCode implements Serializable {
	private static final long serialVersionUID = 4661206403858374344L;
	private int code;
	private String msg;

	public ErrorCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
