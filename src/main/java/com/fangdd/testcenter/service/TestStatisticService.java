package com.fangdd.testcenter.service;

import java.util.List;

import com.fangdd.testcenter.bean.PageDataInfo;
import com.fangdd.testcenter.bean.TestStatistic;

public interface TestStatisticService {

	public boolean addTestStatistic(TestStatistic testStatistic);

	public boolean deleteTestStatistic(TestStatistic testStatistic);
	
	public boolean deleteInvalidTestStatistic();

	public int getTestStatisticCount(TestStatistic testStatistic);

	public List<TestStatistic> getTestStatisticList(TestStatistic testStatistic, Integer pageIndex, Integer pageSize);

	public TestStatistic getTestStatisticById(long id);

	public PageDataInfo<TestStatistic> getTestStatisticListByPage(TestStatistic testStatistic, Integer pageIndex,
			Integer pageSize);

	public boolean updateTestStatisticBuildId(long serviceId, long buildId, String suiteStartTime, String suiteEndTime);
	
	public boolean deleteTestStatisticByBuildId(long buildId);
}
