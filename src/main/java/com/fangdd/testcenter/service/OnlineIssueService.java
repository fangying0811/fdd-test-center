package com.fangdd.testcenter.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fangdd.testcenter.bean.OnlineIssue;
import com.fangdd.testcenter.bean.PageDataInfo;

public interface OnlineIssueService {

	public boolean addOnlineIssue(OnlineIssue onlineIssue);

	public boolean updateOnlineIssue(OnlineIssue onlineIssue);

	public boolean deleteOnlineIssue(long onlineIssueId);

	public int getOnlineIssueCount(OnlineIssue onlineIssue);

	public List<OnlineIssue> getOnlineIssueList(OnlineIssue onlineIssue, Integer pageIndex, Integer pageSize);

	public PageDataInfo<OnlineIssue> getOnlineIssueListByPage(OnlineIssue onlineIssue, Integer pageIndex,
			Integer pageSize);

	public OnlineIssue getOnlineIssueById(long onlineIssueId);

	public List<OnlineIssue> getOnlineIssueExportList(OnlineIssue onlineIssue);

	public int getOnlineIssueCountByIssueId(long issueId);

	public void exportOnlineIssue(OnlineIssue onlineIssue, HttpServletRequest request, HttpServletResponse response);
}
