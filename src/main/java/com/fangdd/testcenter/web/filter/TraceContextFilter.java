package com.fangdd.testcenter.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.fangdd.qa.framework.utils.common.TraceContextHolder;

/**
 * 打印和记录请求处理耗时.
 *
 * @since 1.0.0
 */
public class TraceContextFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(TraceContextFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("初始化TraceContextFilter");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (TraceContextHolder.getTraceContext() != null) {
			MDC.put("traceId", TraceContextHolder.getTraceContext().getRequestId());
			MDC.put("caller", TraceContextHolder.getTraceContext().getCallerId());
			MDC.put("server", TraceContextHolder.getTraceContext().getServerId());
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
}