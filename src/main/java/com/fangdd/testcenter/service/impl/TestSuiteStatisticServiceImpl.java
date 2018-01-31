package com.fangdd.testcenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fangdd.qa.framework.utils.common.CommonUtil;
import com.fangdd.qa.framework.utils.common.DateTimeUtil;
import com.fangdd.testcenter.bean.AutomationProject;
import com.fangdd.testcenter.bean.Department;
import com.fangdd.testcenter.bean.PageDataInfo;
import com.fangdd.testcenter.bean.Team;
import com.fangdd.testcenter.bean.TestSuiteStatistic;
import com.fangdd.testcenter.common.exception.BusinessException;
import com.fangdd.testcenter.common.util.ConfigReader;
import com.fangdd.testcenter.core.SystemErrorCodeConstant;
import com.fangdd.testcenter.enums.TestSuiteStatisticStatusEnum;
import com.fangdd.testcenter.mapper.TestSuiteStatisticMapper;
import com.fangdd.testcenter.service.AutomationProjectService;
import com.fangdd.testcenter.service.DepartmentService;
import com.fangdd.testcenter.service.TeamService;
import com.fangdd.testcenter.service.TestStatisticService;
import com.fangdd.testcenter.service.TestSuiteStatisticService;

@Service(value = "testSuiteStatisticService")
public class TestSuiteStatisticServiceImpl implements TestSuiteStatisticService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TestSuiteStatisticMapper testSuiteStatisticMapper;
	@Autowired
	private AutomationProjectService automationProjectService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private TestStatisticService testStatisticService;

	@Override
	public boolean addTestSuiteStatistic(TestSuiteStatistic testSuiteStatistic) {
		boolean flag = false;
		AutomationProject automationProject = automationProjectService
				.getAutomationProjectById(testSuiteStatistic.getServiceId());
		if (automationProject == null) {
			throw new BusinessException(SystemErrorCodeConstant.AUTOMATION_PROJECT_NOT_EXIST);
		}
		testSuiteStatistic.setDepartmentId(automationProject.getDepartmentId());
		testSuiteStatistic.setTeamId(automationProject.getTeamId());
		try {
			flag = testSuiteStatisticMapper.addTestSuiteStatistic(testSuiteStatistic) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.ADD_FAILURE, e);
		}
		return flag;
	}

	@Override
	public boolean updateTestStatisticBuildId(long serviceId, String startTime, String endTime) {
		boolean flag = false;
		long buildId = getTestSuiteStatisticId(serviceId, startTime, endTime);
		flag = testStatisticService.updateTestStatisticBuildId(serviceId, buildId, startTime, endTime);
		return flag;
	}

	@Override
	public int getTestSuiteStatisticCount(TestSuiteStatistic testSuiteStatistic) {
		int count = 0;
		try {
			count = testSuiteStatisticMapper.getTestSuiteStatisticCount(testSuiteStatistic);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return count;
	}

	public TestSuiteStatistic setTestSuiteStatistic(TestSuiteStatistic testSuiteStatistic) {
		if (testSuiteStatistic != null) {
			Department department = departmentService.getDepartmentById(testSuiteStatistic.getDepartmentId());
			Team team = teamService.getTeamById(testSuiteStatistic.getTeamId());
			AutomationProject automationProject = automationProjectService
					.getAutomationProjectById(testSuiteStatistic.getServiceId());
			testSuiteStatistic.setDepartmentId(department.getDepartmentId());
			testSuiteStatistic.setDepartmentName(department.getName());
			testSuiteStatistic.setTeamId(team.getTeamId());
			testSuiteStatistic.setTeamName(team.getName());
			testSuiteStatistic.setServiceId(automationProject.getId());
			testSuiteStatistic.setServiceName(automationProject.getServiceName());
			testSuiteStatistic.setServiceDes(automationProject.getRemark());
			int passed = testSuiteStatistic.getPassed();
			int failed = testSuiteStatistic.getFailed();
			int skipped = testSuiteStatistic.getSkipped();
			int total = testSuiteStatistic.getTotal();
			testSuiteStatistic.setPassedPercent(CommonUtil.getPercent(passed, total));
			testSuiteStatistic.setFailedPercent(CommonUtil.getPercent(failed, total));
			testSuiteStatistic.setSkippedPercent(CommonUtil.getPercent(skipped, total));
			testSuiteStatistic.setStatusText(
					TestSuiteStatisticStatusEnum.findByValue(testSuiteStatistic.getStatus()).getDescription());
			testSuiteStatistic.setStartTime(
					DateTimeUtil.formatedTime(ConfigReader.DATE_TIME_FORMAT, testSuiteStatistic.getStartTime()));
			testSuiteStatistic.setEndTime(
					DateTimeUtil.formatedTime(ConfigReader.DATE_TIME_FORMAT, testSuiteStatistic.getEndTime()));
			testSuiteStatistic.setCreateTime(
					DateTimeUtil.formatedTime(ConfigReader.DATE_TIME_FORMAT, testSuiteStatistic.getCreateTime()));
			testSuiteStatistic.setUpdateTime(
					DateTimeUtil.formatedTime(ConfigReader.DATE_TIME_FORMAT, testSuiteStatistic.getUpdateTime()));
		}
		return testSuiteStatistic;
	}

	@Override
	public List<TestSuiteStatistic> getTestSuiteStatisticList(TestSuiteStatistic testSuiteStatistic, Integer pageIndex,
			Integer pageSize) {
		List<TestSuiteStatistic> testSuiteStatisticTempList = null;
		List<TestSuiteStatistic> testSuiteStatisticList = new ArrayList<TestSuiteStatistic>();
		try {
			testSuiteStatisticTempList = testSuiteStatisticMapper.getTestSuiteStatisticList(testSuiteStatistic,
					pageSize * (pageIndex - 1), pageSize);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		for (TestSuiteStatistic testSuiteStatisticTemp : testSuiteStatisticTempList) {
			testSuiteStatisticList.add(setTestSuiteStatistic(testSuiteStatisticTemp));
		}
		return testSuiteStatisticList;
	}

	@Override
	public PageDataInfo<TestSuiteStatistic> getTestSuiteStatisticListByPage(TestSuiteStatistic testSuiteStatistic,
			Integer pageIndex, Integer pageSize) {
		/**
		 * 获取记录总数
		 */
		int totalElements = getTestSuiteStatisticCount(testSuiteStatistic);
		/**
		 * 纠正pageIndex值，防止出现查询第0页或查询页数超过总页数情况
		 */
		PageDataInfo<TestSuiteStatistic> pageDataInfoTemp = new PageDataInfo<TestSuiteStatistic>(pageIndex, pageSize,
				totalElements);
		/**
		 * 获取当前页列表记录
		 */
		List<TestSuiteStatistic> pageList = getTestSuiteStatisticList(testSuiteStatistic,
				pageDataInfoTemp.getPageIndex(), pageDataInfoTemp.getPageSize());
		/**
		 * 构造PageDataInfo对象
		 */
		PageDataInfo<TestSuiteStatistic> pageDataInfo = new PageDataInfo<TestSuiteStatistic>(
				pageDataInfoTemp.getPageIndex(), pageDataInfoTemp.getPageSize(), totalElements, pageList);
		return pageDataInfo;
	}

	@Override
	public TestSuiteStatistic getTestSuiteStatisticById(long id) {
		TestSuiteStatistic testSuiteStatistic = null;
		try {
			testSuiteStatistic = testSuiteStatisticMapper.getTestSuiteStatisticById(id);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return setTestSuiteStatistic(testSuiteStatistic);
	}

	@Override
	public long getTestSuiteStatisticId(long serviceId, String startTime, String endTime) {
		long id = 0;
		TestSuiteStatistic testSuiteStatistic = new TestSuiteStatistic();
		testSuiteStatistic.setServiceId(serviceId);
		testSuiteStatistic.setStartTime(startTime);
		testSuiteStatistic.setEndTime(endTime);
		try {
			id = testSuiteStatisticMapper.getTestSuiteStatisticId(testSuiteStatistic);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return id;
	}

	@Override
	public boolean deleteTestSuiteStatisticById(long id) {
		boolean flag = false;
		flag = testStatisticService.deleteTestStatisticByBuildId(id);
		try {
			flag = testSuiteStatisticMapper.deleteTestSuiteStatisticById(id) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.DELETE_FAILURE, e);
		}
		return flag;
	}
}
