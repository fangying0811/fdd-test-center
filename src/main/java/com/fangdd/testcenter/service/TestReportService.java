package com.fangdd.testcenter.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fangdd.testcenter.bean.PageDataInfo;
import com.fangdd.testcenter.bean.TestReport;

public interface TestReportService {
	public boolean addTestReport(TestReport testReport);

	public boolean updateTestReport(TestReport testReport);

	public boolean deleteTestReport(long testReportId);

	public int getTestReportCount(TestReport testReport);

	public List<TestReport> getTestReportList(TestReport testReport, Integer begin, Integer size);

	public PageDataInfo<TestReport> getTestReportListByPage(TestReport testReport, Integer pageIndex, Integer pageSize);

	public TestReport getTestReportById(long testReportId);

	public void exportTestReport(long testReportId, HttpServletRequest request, HttpServletResponse response);
}
