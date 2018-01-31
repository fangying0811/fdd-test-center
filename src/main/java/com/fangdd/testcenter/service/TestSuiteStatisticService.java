package com.fangdd.testcenter.service;

import java.util.List;

import com.fangdd.testcenter.bean.PageDataInfo;
import com.fangdd.testcenter.bean.TestSuiteStatistic;

public interface TestSuiteStatisticService {

	public boolean addTestSuiteStatistic(TestSuiteStatistic testSuiteStatistic);

	public int getTestSuiteStatisticCount(TestSuiteStatistic testSuiteStatistic);

	public List<TestSuiteStatistic> getTestSuiteStatisticList(TestSuiteStatistic testSuiteStatistic, Integer pageIndex,
			Integer pageSize);

	public TestSuiteStatistic getTestSuiteStatisticById(long id);

	public PageDataInfo<TestSuiteStatistic> getTestSuiteStatisticListByPage(TestSuiteStatistic testSuiteStatistic,
			Integer pageIndex, Integer pageSize);

	public long getTestSuiteStatisticId(long serviceId, String startTime, String endTime);

	public boolean updateTestStatisticBuildId(long serviceId, String startTime, String endTime);

	public boolean deleteTestSuiteStatisticById(long id);
}
