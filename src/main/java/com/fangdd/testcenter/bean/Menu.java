package com.fangdd.testcenter.bean;

public class Menu {
	/**
	 * 菜单id
	 */
	private long menuId;
	/**
	 * 菜单名
	 */
	private String name;
	/**
	 * 菜单访问URL
	 */
	private String url;
	/**
	 * 优先级，数字越小优先级越高
	 */
	private int priority;
	/**
	 * 父菜单ID
	 */
	private long parentId;
	/**
	 * 父菜单名
	 */
	private String parentName;
	/**
	 * 态状，0:启用,1:禁用
	 */
	private int status = -1;
	/**
	 * 状态文本描述，0:启用,1:禁用
	 */
	private String statusText;

	public long getMenuId() {
		return menuId;
	}

	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
}
