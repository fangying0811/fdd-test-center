package com.fangdd.testcenter.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;

    public Integer getPageIndex() {
        return Integer.parseInt(request.getParameter("pageIndex"));
    }

    public Integer getPageSize() {
        return Integer.parseInt(request.getParameter("pageSize"));
    }

    public String getSortField() {
        return request.getParameter("sortField");
    }

    public String getSortOrder() {
        return request.getParameter("sortOrder");
    }

    /**
     * 按指定的键和值保存到session作用域
     *
     * @param key   键
     * @param value 值
     */
    protected void setSession(String key, Object value) {
        request.getSession().setAttribute(key, value);
    }

    /**
     * 按指定的键在session作用域获取值
     *
     * @param key 键
     * @return 值
     */
    protected <T> T getSession(String key) {
        return (T) request.getSession().getAttribute(key);
    }

    /**
     * 按指定的键在session作用域移除对象
     *
     * @param key 键
     */
    protected void removeSession(String key) {
        request.getSession().removeAttribute(key);
    }
}
