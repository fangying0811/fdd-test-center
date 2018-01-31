package com.fangdd.testcenter.core.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fangdd.testcenter.bean.User;
import com.fangdd.testcenter.common.exception.BusinessException;
import com.fangdd.testcenter.core.SystemErrorCodeConstant;
import com.fangdd.testcenter.web.util.WebConstants;

/**
 * 处理Session验证
 *
 */
public class SessionCheckHandlerInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(SessionCheckHandlerInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod) handler;
			// 只对标有SessionCheck的接口进行session验证
			if (method.getMethodAnnotation(SessionCheck.class) != null) {
				if (!checkSession(request)) {
					String uri = request.getRequestURI();
					if (uri.indexOf("UI") != -1) {
						response.sendRedirect(request.getServletContext().getContextPath() + "/nav.jsp");
					} else if (uri.indexOf(".json") != -1) {
						throw new BusinessException(SystemErrorCodeConstant.SESSION_INVALID);
					} else {
						response.sendRedirect(request.getServletContext().getContextPath() + "/nav.jsp");
					}
				}
			}
		}
		return true;
	}

	/**
	 * 验证session
	 *
	 * @param request
	 * @return
	 */
	private boolean checkSession(HttpServletRequest request) {
		boolean flag = true;
		User user = (User) request.getSession().getAttribute(WebConstants.LOGIN_USER);
		if (user == null || user.getUserId() <= 0) {
			flag = false;
		}
		logger.info("checkSession flag:{}", flag);
		return flag;
	}
}
