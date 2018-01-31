package com.fangdd.testcenter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fangdd.testcenter.bean.TestSuiteStatistic;

public interface TestSuiteStatisticMapper {

	public int addTestSuiteStatistic(@Param("testSuiteStatistic") TestSuiteStatistic testSuiteStatistic);

	public int getTestSuiteStatisticCount(@Param("testSuiteStatistic") TestSuiteStatistic testSuiteStatistic);

	public List<TestSuiteStatistic> getTestSuiteStatisticList(
			@Param("testSuiteStatistic") TestSuiteStatistic testSuiteStatistic, @Param("begin") Integer begin,
			@Param("size") Integer size);

	public TestSuiteStatistic getTestSuiteStatisticById(@Param("id") long id);

	public long getTestSuiteStatisticId(@Param("testSuiteStatistic") TestSuiteStatistic testSuiteStatistic);

	public int deleteTestSuiteStatisticById(@Param("id") long id);
}
