package com.fangdd.testcenter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fangdd.testcenter.bean.OutstandingIssue;

public interface OutstandingIssueMapper {

	public int addOutstandingIssue(@Param("outstandingIssue") OutstandingIssue outstandingIssue);

	public int updateOutstandingIssue(@Param("outstandingIssue") OutstandingIssue outstandingIssue);

	public int deleteOutstandingIssue(@Param("outstandingIssueId") long outstandingIssueId);

	public int getOutstandingIssueCount(@Param("outstandingIssue") OutstandingIssue outstandingIssue);

	public List<OutstandingIssue> getOutstandingIssueList(@Param("outstandingIssue") OutstandingIssue outstandingIssue,
			@Param("begin") Integer begin, @Param("size") Integer size);

	public OutstandingIssue getOutstandingIssueById(@Param("outstandingIssueId") long outstandingIssueId);

	public List<OutstandingIssue> getOutstandingIssueExportList(
			@Param("outstandingIssue") OutstandingIssue outstandingIssue);

	public int getOutstandingIssueCountByIssueId(@Param("issueId") long issueId);
}
