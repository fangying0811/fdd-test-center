package com.fangdd.testcenter.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 资源未找到异常, {@link HttpStatus#NOT_FOUND}
 *
 * @author wiflish
 * @since 1.0.0
 */
public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException() {
		super("不存在的资源.");
	}
}
