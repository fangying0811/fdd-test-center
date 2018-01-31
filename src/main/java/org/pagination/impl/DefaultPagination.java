package org.pagination.impl;

import java.util.List;

import org.pagination.Pagination;
import org.pagination.QueryHandler;

public class DefaultPagination<T> implements Pagination<T> {
	private Integer totalElements;
	private List<T> pageList;
	private QueryHandler<T> queryHandler;
	private Integer pageIndex;
	private Integer pageSize;
	private Integer totalPage;

	public DefaultPagination(Integer pageIndex, Integer pageSize, QueryHandler<T> queryHandler) {
		this.queryHandler = queryHandler;
		setTotalElements();// 初始化总行数
		setPageSize(pageSize);// 修正页大小
		calcTotalPage();// 计算总页数
		setPageIndex(pageIndex);// 修正页码
		setPageList();// 初始化当前页数据
	}

	private void setTotalElements() {
		totalElements = queryHandler.getCount();
	}

	private void setPageSize(Integer pageSize) {
		if (pageSize < 1) {
			this.pageSize = 1;
		} else if (pageSize > totalElements && totalElements > 0) {
			this.pageSize = totalElements;
		} else {
			this.pageSize = pageSize;
		}
	}

	private void calcTotalPage() {
		totalPage = (totalElements + pageSize - 1) / pageSize;
	}

	private void setPageIndex(Integer pageIndex) {
		if (pageIndex < 1) {
			this.pageIndex = 1;
		} else if (pageIndex > totalPage && totalPage > 0) {
			this.pageIndex = totalPage;
		} else {
			this.pageIndex = pageIndex;
		}
	}

	private void setPageList() {
		pageList = queryHandler.getData(pageIndex, pageSize);
	}

	@Override
	public Integer getTotalElements() {
		return totalElements;
	}

	@Override
	public List<T> getPageList() {
		return pageList;
	}
}
