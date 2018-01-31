package com.fangdd.testcenter.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 客户端非法请求异常(如：请求参数格式不正确等） {@link HttpStatus#BAD_REQUEST}
 */
public class BadRequestException extends RuntimeException {
	public BadRequestException() {
		super("非法请求.");
	}
}
