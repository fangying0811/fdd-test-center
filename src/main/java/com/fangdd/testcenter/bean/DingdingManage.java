package com.fangdd.testcenter.bean;

public class DingdingManage {

	private long dManageId;

	/**
	 * dingding服务端返回的chatId，只有发起会话时拿到
	 */
	private String chatId;

	/**
	 * 钉钉会话名字
	 */
	private String groupName;

	/**
	 * 群主 dingding的userId
	 */
	private String userId;

	/**
	 * 群主手机号
	 */
	private String userMobile;

	/**
	 * 群成员手机号
	 */
	private String memberMobile;

	private String createTime;

	private String updateTime;

	/**
	 * Generate by IDE
	 */
	public String getMemberMobile() {
		return memberMobile;
	}

	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public long getdManageId() {
		return dManageId;
	}

	public void setdManageId(long dManageId) {
		this.dManageId = dManageId;
	}
}
