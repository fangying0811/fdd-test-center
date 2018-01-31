package com.fangdd.testcenter.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSON;
import com.fangdd.testcenter.common.exception.BusinessException;

/**
 * 将请求参数body的值转换为javaBean。
 *
 * @since 1.0.0
 */
public class FastjsonArgumentResolver implements HandlerMethodArgumentResolver {
	private static final Logger logger = LoggerFactory.getLogger(FastjsonArgumentResolver.class);

	private String parameterKey = "body";

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(JsonToBean.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		String jsonStr = webRequest.getParameter(parameterKey);
		if (jsonStr == null || jsonStr == "") {
			JsonToBean jsonToBean = parameter.getParameterAnnotation(JsonToBean.class);
			if (jsonToBean.nullable()) {
				return null;
			}
			throw new BusinessException(SystemErrorCodeConstant.JSON_FORMAT_ERROR);
		}
		try {
			return JSON.parseObject(jsonStr, parameter.getParameterType());
		} catch (Exception ex) {
			throw new BusinessException(SystemErrorCodeConstant.JSON_FORMAT_ERROR, ex);
		}
	}

	public String getParameterKey() {
		return parameterKey;
	}

	public void setParameterKey(String parameterKey) {
		this.parameterKey = parameterKey;
	}
}