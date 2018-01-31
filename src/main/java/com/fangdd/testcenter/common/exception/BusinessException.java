package com.fangdd.testcenter.common.exception;

import com.fangdd.testcenter.core.ErrorCode;

/**
 * 业务异常,异常消息提示给接口调用方.
 *
 * @since 1.0.0
 */
public class BusinessException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code = 500; // 默认服务器内部异常.
	private ErrorCode errorCode; // 默认服务器内部异常.

	public BusinessException(ErrorCode errorCode) {
		super(errorCode.getMsg());
		this.code = errorCode.getCode();
		this.errorCode = errorCode;
	}

	public BusinessException(int code, String message) {
		super(message);
		this.code = code;
		this.errorCode = new ErrorCode(code, message);
	}

	public BusinessException(ErrorCode errorCode, Throwable cause) {
		super(errorCode.getMsg(), cause);
		this.code = errorCode.getCode();
		this.errorCode = errorCode;
	}

	public BusinessException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
		this.errorCode = new ErrorCode(code, message);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
