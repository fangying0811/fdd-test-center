package com.fangdd.testcenter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fangdd.testcenter.bean.OnlineIssue;

public interface OnlineIssueMapper {

	public int addOnlineIssue(@Param("onlineIssue") OnlineIssue onlineIssue);

	public int updateOnlineIssue(@Param("onlineIssue") OnlineIssue onlineIssue);

	public int deleteOnlineIssue(@Param("onlineIssueId") long onlineIssueId);

	public int getOnlineIssueCount(@Param("onlineIssue") OnlineIssue onlineIssue);

	public List<OnlineIssue> getOnlineIssueList(@Param("onlineIssue") OnlineIssue onlineIssue,
			@Param("begin") Integer begin, @Param("size") Integer size);

	public OnlineIssue getOnlineIssueById(@Param("onlineIssueId") long onlineIssueId);

	public List<OnlineIssue> getOnlineIssueExportList(@Param("onlineIssue") OnlineIssue onlineIssue);

	public int getOnlineIssueCountByIssueId(@Param("issueId") long issueId);

}
