package com.fangdd.testcenter.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 客户端请求无权限访问的资源(如：需要用户登录操作等） {@link HttpStatus#FORBIDDEN}
 */
public class ForbiddenException extends RuntimeException {
	public ForbiddenException() {
		super("客户端请求无权限访问的资源.");
	}
}
