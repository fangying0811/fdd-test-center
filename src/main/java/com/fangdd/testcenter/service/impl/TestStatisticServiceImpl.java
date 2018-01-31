package com.fangdd.testcenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fangdd.qa.framework.utils.common.DateTimeUtil;
import com.fangdd.testcenter.bean.AutomationProject;
import com.fangdd.testcenter.bean.PageDataInfo;
import com.fangdd.testcenter.bean.TestStatistic;
import com.fangdd.testcenter.common.exception.BusinessException;
import com.fangdd.testcenter.common.util.ConfigReader;
import com.fangdd.testcenter.core.SystemErrorCodeConstant;
import com.fangdd.testcenter.enums.TestStatisticStatusEnum;
import com.fangdd.testcenter.mapper.TestStatisticMapper;
import com.fangdd.testcenter.service.AutomationProjectService;
import com.fangdd.testcenter.service.TestStatisticService;

@Service(value = "testStatisticService")
public class TestStatisticServiceImpl implements TestStatisticService {
	@Autowired
	private TestStatisticMapper testStatisticMapper;
	@Autowired
	private AutomationProjectService automationProjectService;

	@Override
	public boolean addTestStatistic(TestStatistic testStatistic) {
		AutomationProject automationProject = automationProjectService
				.getAutomationProjectById(testStatistic.getServiceId());
		if (automationProject == null) {
			throw new BusinessException(SystemErrorCodeConstant.AUTOMATION_PROJECT_NOT_EXIST);
		}
		try {
			return testStatisticMapper.addTestStatistic(testStatistic) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.ADD_FAILURE, e);
		}
	}

	@Override
	public boolean deleteTestStatistic(TestStatistic testStatistic) {
		try {
			return testStatisticMapper.deleteTestStatistic(testStatistic) >= 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.DELETE_FAILURE, e);
		}
	}

	@Override
	public boolean deleteInvalidTestStatistic() {
		try {
			return testStatisticMapper.deleteInvalidTestStatistic() >= 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.DELETE_FAILURE, e);
		}
	}

	@Override
	public int getTestStatisticCount(TestStatistic testStatistic) {
		int count = 0;
		try {
			count = testStatisticMapper.getTestStatisticCount(testStatistic);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return count;
	}

	public TestStatistic setTestStatistic(TestStatistic testStatistic) {
		if (testStatistic != null) {
			AutomationProject automationProject = automationProjectService
					.getAutomationProjectById(testStatistic.getServiceId());
			testStatistic.setServiceId(automationProject.getId());
			testStatistic.setServiceName(automationProject.getServiceName());
			testStatistic.setServiceDes(automationProject.getRemark());
			testStatistic
					.setStatusText(TestStatisticStatusEnum.findByValue(testStatistic.getStatus()).getDescription());
			testStatistic.setStartTime(
					DateTimeUtil.formatedTime(ConfigReader.DATE_TIME_FORMAT, testStatistic.getStartTime()));
			testStatistic
					.setEndTime(DateTimeUtil.formatedTime(ConfigReader.DATE_TIME_FORMAT, testStatistic.getEndTime()));
			testStatistic.setCreateTime(
					DateTimeUtil.formatedTime(ConfigReader.DATE_TIME_FORMAT, testStatistic.getCreateTime()));
			testStatistic.setUpdateTime(
					DateTimeUtil.formatedTime(ConfigReader.DATE_TIME_FORMAT, testStatistic.getUpdateTime()));
		}
		return testStatistic;
	}

	@Override
	public List<TestStatistic> getTestStatisticList(TestStatistic testStatistic, Integer pageIndex, Integer pageSize) {
		List<TestStatistic> testStatisticTempList = null;
		List<TestStatistic> testStatisticList = new ArrayList<TestStatistic>();
		try {
			testStatisticTempList = testStatisticMapper.getTestStatisticList(testStatistic, pageSize * (pageIndex - 1),
					pageSize);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		for (TestStatistic testStatisticTemp : testStatisticTempList) {
			testStatisticList.add(setTestStatistic(testStatisticTemp));
		}
		return testStatisticList;
	}

	@Override
	public PageDataInfo<TestStatistic> getTestStatisticListByPage(TestStatistic testStatistic, Integer pageIndex,
			Integer pageSize) {
		/**
		 * 获取记录总数
		 */
		int totalElements = getTestStatisticCount(testStatistic);
		/**
		 * 纠正pageIndex值，防止出现查询第0页或查询页数超过总页数情况
		 */
		PageDataInfo<TestStatistic> pageDataInfoTemp = new PageDataInfo<TestStatistic>(pageIndex, pageSize,
				totalElements);
		/**
		 * 获取当前页列表记录
		 */
		List<TestStatistic> pageList = getTestStatisticList(testStatistic, pageDataInfoTemp.getPageIndex(),
				pageDataInfoTemp.getPageSize());
		/**
		 * 构造PageDataInfo对象
		 */
		PageDataInfo<TestStatistic> pageDataInfo = new PageDataInfo<TestStatistic>(pageDataInfoTemp.getPageIndex(),
				pageDataInfoTemp.getPageSize(), totalElements, pageList);
		return pageDataInfo;
	}

	@Override
	public TestStatistic getTestStatisticById(long id) {
		TestStatistic testStatistic = null;
		try {
			testStatistic = testStatisticMapper.getTestStatisticById(id);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return setTestStatistic(testStatistic);
	}

	@Override
	public boolean updateTestStatisticBuildId(long serviceId, long buildId, String suiteStartTime,
			String suiteEndTime) {
		TestStatistic testStatistic = new TestStatistic();
		testStatistic.setServiceId(serviceId);
		testStatistic.setBuildId(buildId);
		testStatistic.setStartTime(suiteStartTime);
		testStatistic.setEndTime(suiteEndTime);
		try {
			return testStatisticMapper.updateTestStatisticBuildId(testStatistic) > 0;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.UPDATE_FAILURE, e);
		}
	}

	@Override
	public boolean deleteTestStatisticByBuildId(long buildId) {
		try {
			return testStatisticMapper.deleteTestStatisticByBuildId(buildId) > 0;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.DELETE_FAILURE, e);
		}
	}
}
