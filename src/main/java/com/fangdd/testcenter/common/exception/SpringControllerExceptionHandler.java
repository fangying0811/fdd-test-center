package com.fangdd.testcenter.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.alibaba.fastjson.JSONException;
import com.fangdd.testcenter.core.ErrorCode;
import com.fangdd.testcenter.core.HttpResult;
import com.fangdd.testcenter.core.SystemErrorCodeConstant;

/**
 * 全局异常拦截控制器.
 * 
 * @since 1.0.0
 */
@ControllerAdvice
public class SpringControllerExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(SpringControllerExceptionHandler.class);

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadRequestException.class)
	public void handle400() {
	}

	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(ForbiddenException.class)
	public void handle403() {
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class)
	public @ResponseBody HttpResult handle404() {
		return HttpResult.failureWithErrorCode(SystemErrorCodeConstant.NOT_FOUND_ERROR);
	}

	@ExceptionHandler(JSONException.class)
	public @ResponseBody HttpResult handleJsonException(JSONException e) {
		logger.error("JSON解析异常.", e);
		return HttpResult.failureWithErrorCode(SystemErrorCodeConstant.JSON_FORMAT_ERROR);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public @ResponseBody HttpResult handle500(Exception ex) {
		logger.error("服务器内部异常", ex);
		return HttpResult.failureWithErrorCode(SystemErrorCodeConstant.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(com.fangdd.qa.framework.exception.BusinessException.class)
	public @ResponseBody HttpResult handleBusiness(com.fangdd.qa.framework.exception.BusinessException e) {
		logger.error("访问接口异常", e);
		return HttpResult.failureWithErrorCode(new ErrorCode(9999, e.getMessage()));
	}

	@ExceptionHandler(BusinessException.class)
	public @ResponseBody HttpResult handleBusiness(BusinessException e) {
		logger.error("业务逻辑异常", e);
		HttpResult result = new HttpResult();
		result.setCode(e.getCode());
		result.setMsg(e.getMessage());
		return result;
	}
}
