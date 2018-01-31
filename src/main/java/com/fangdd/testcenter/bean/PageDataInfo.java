package com.fangdd.testcenter.bean;

import java.util.List;

public class PageDataInfo<T> {
	/**
	 * 当前页数
	 */
	private Integer pageIndex;
	/**
	 * 每页记录数
	 */
	private Integer pageSize;
	/**
	 * 总记录数
	 */
	private Integer totalElements;
	/**
	 * 当前页列表记录
	 */
	private List<T> pageList;
	/**
	 * 总页数
	 */
	private Integer totalPage;

	public PageDataInfo(Integer pageIndex, Integer pageSize, Integer totalElements) {
		setTotalElements(totalElements);// 初始化总行数
		setPageSize(pageSize);// 设置页大小
		calcTotalPage();// 计算总页数
		setPageIndex(pageIndex);// 修正页码
	}

	public PageDataInfo(Integer pageIndex, Integer pageSize, Integer totalElements, List<T> pageList) {
		setTotalElements(totalElements);// 初始化总行数
		setPageSize(pageSize);// 设置页大小
		calcTotalPage();// 计算总页数
		setPageIndex(pageIndex);// 修正页码
		setPageList(pageList);// 初始化当前页数据
	}

	private void calcTotalPage() {
		totalPage = (totalElements + pageSize - 1) / pageSize;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		if (pageIndex < 1) {
			this.pageIndex = 1;
		} else if (pageIndex > totalPage && totalPage > 0) {
			this.pageIndex = totalPage;
		} else {
			this.pageIndex = pageIndex;
		}
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Integer totalElements) {
		this.totalElements = totalElements;
	}

	public List<T> getPageList() {
		return pageList;
	}

	public void setPageList(List<T> pageList) {
		this.pageList = pageList;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
}
