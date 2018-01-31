package com.fangdd.testcenter.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fangdd.testcenter.bean.OutstandingIssue;
import com.fangdd.testcenter.bean.PageDataInfo;

public interface OutstandingIssueService {

	public boolean addOutstandingIssue(OutstandingIssue outstandingIssue);

	public boolean updateOutstandingIssue(OutstandingIssue outstandingIssue);

	public boolean deleteOutstandingIssue(long outstandingIssueId);

	public int getOutstandingIssueCount(OutstandingIssue outstandingIssue);

	public List<OutstandingIssue> getOutstandingIssueList(OutstandingIssue outstandingIssue, Integer pageIndex,
			Integer pageSize);

	public PageDataInfo<OutstandingIssue> getOutstandingIssueListByPage(OutstandingIssue outstandingIssue,
			Integer pageIndex, Integer pageSize);

	public OutstandingIssue getOutstandingIssueById(long outstandingIssueId);

	public List<OutstandingIssue> getOutstandingIssueExportList(OutstandingIssue outstandingIssue);

	public int getOutstandingIssueCountByIssueId(long issueId);

	public void exportOutstandingIssue(OutstandingIssue outstandingIssue, HttpServletRequest request,
			HttpServletResponse response);
}
