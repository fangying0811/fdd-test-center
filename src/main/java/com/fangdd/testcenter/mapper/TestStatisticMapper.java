package com.fangdd.testcenter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fangdd.testcenter.bean.TestStatistic;

public interface TestStatisticMapper {

	public int addTestStatistic(@Param("testStatistic") TestStatistic testStatistic);
	
	public int deleteTestStatistic(@Param("testStatistic") TestStatistic testStatistic);

	public int getTestStatisticCount(@Param("testStatistic") TestStatistic testStatistic);

	public List<TestStatistic> getTestStatisticList(@Param("testStatistic") TestStatistic testStatistic,
			@Param("begin") Integer begin, @Param("size") Integer size);

	public TestStatistic getTestStatisticById(@Param("id") long id);
	
	public int updateTestStatisticBuildId(@Param("testStatistic") TestStatistic testStatistic);
	
	public int deleteInvalidTestStatistic();
	
	public int deleteTestStatisticByBuildId(@Param("buildId") long buildId);
}
