package com.fangdd.testcenter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fangdd.testcenter.bean.TestReport;

public interface TestReportMapper {

	public int addTestReport(@Param("testReport") TestReport testReport);

	public int updateTestReport(@Param("testReport") TestReport testReport);

	public int deleteTestReport(@Param("testReportId") long testReportId);

	public int getTestReportCount(@Param("testReport") TestReport testReport);

	public List<TestReport> getTestReportList(@Param("testReport") TestReport testReport, @Param("begin") Integer begin,
			@Param("size") Integer size);

	public TestReport getTestReportById(@Param("testReportId") long testReportId);
}
