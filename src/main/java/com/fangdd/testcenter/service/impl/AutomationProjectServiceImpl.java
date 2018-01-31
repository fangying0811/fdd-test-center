package com.fangdd.testcenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fangdd.qa.framework.utils.common.DateTimeUtil;
import com.fangdd.testcenter.bean.AutomationProject;
import com.fangdd.testcenter.bean.Department;
import com.fangdd.testcenter.bean.PageDataInfo;
import com.fangdd.testcenter.bean.Team;
import com.fangdd.testcenter.common.exception.BusinessException;
import com.fangdd.testcenter.common.util.ConfigReader;
import com.fangdd.testcenter.core.SystemErrorCodeConstant;
import com.fangdd.testcenter.mapper.AutomationProjectMapper;
import com.fangdd.testcenter.service.AutomationProjectService;
import com.fangdd.testcenter.service.DepartmentService;
import com.fangdd.testcenter.service.TeamService;

@Service(value = "automationProjectService")
public class AutomationProjectServiceImpl implements AutomationProjectService {
	@Autowired
	private AutomationProjectMapper automationProjectMapper;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private TeamService teamService;

	@Override
	public boolean addAutomationProject(AutomationProject automationProject) {
		try {
			return automationProjectMapper.addAutomationProject(automationProject) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.ADD_FAILURE, e);
		}
	}

	@Override
	public boolean updateAutomationProject(AutomationProject automationProject) {
		try {
			return automationProjectMapper.updateAutomationProject(automationProject) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.UPDATE_FAILURE, e);
		}
	}

	@Override
	public boolean deleteAutomationProject(long id) {
		try {
			return automationProjectMapper.deleteAutomationProject(id) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.DELETE_FAILURE, e);
		}
	}

	@Override
	public int getAutomationProjectCount(AutomationProject automationProject) {
		int count = 0;
		try {
			count = automationProjectMapper.getAutomationProjectCount(automationProject);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return count;
	}

	public AutomationProject setAutomationProject(AutomationProject automationProject) {
		if (automationProject != null) {
			Department department = departmentService.getDepartmentById(automationProject.getDepartmentId());
			Team team = teamService.getTeamById(automationProject.getTeamId());
			automationProject.setDepartmentId(department.getDepartmentId());
			automationProject.setDepartmentName(department.getName());
			automationProject.setTeamId(team.getTeamId());
			automationProject.setTeamName(team.getName());
			automationProject.setCreateTime(
					DateTimeUtil.formatedTime(ConfigReader.DATE_TIME_FORMAT, automationProject.getCreateTime()));
			automationProject.setUpdateTime(
					DateTimeUtil.formatedTime(ConfigReader.DATE_TIME_FORMAT, automationProject.getUpdateTime()));
		}
		return automationProject;
	}

	@Override
	public List<AutomationProject> getAutomationProjectList(AutomationProject automationProject, Integer pageIndex,
			Integer pageSize) {
		List<AutomationProject> automationProjectTmpList = null;
		List<AutomationProject> automationProjectList = new ArrayList<AutomationProject>();
		try {
			automationProjectTmpList = automationProjectMapper.getAutomationProjectList(automationProject,
					pageSize * (pageIndex - 1), pageSize);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		for (AutomationProject automationProjectTmp : automationProjectTmpList) {
			automationProjectList.add(setAutomationProject(automationProjectTmp));
		}
		return automationProjectList;
	}

	@Override
	public PageDataInfo<AutomationProject> getAutomationProjectListByPage(AutomationProject automationProject,
			Integer pageIndex, Integer pageSize) {
		/**
		 * 获取记录总数
		 */
		int totalElements = getAutomationProjectCount(automationProject);
		/**
		 * 纠正pageIndex值，防止出现查询第0页或查询页数超过总页数情况
		 */
		PageDataInfo<AutomationProject> pageDataInfoTemp = new PageDataInfo<AutomationProject>(pageIndex, pageSize,
				totalElements);
		/**
		 * 获取当前页列表记录
		 */
		List<AutomationProject> pageList = getAutomationProjectList(automationProject, pageDataInfoTemp.getPageIndex(),
				pageDataInfoTemp.getPageSize());
		/**
		 * 构造PageDataInfo对象
		 */
		PageDataInfo<AutomationProject> pageDataInfo = new PageDataInfo<AutomationProject>(
				pageDataInfoTemp.getPageIndex(), pageDataInfoTemp.getPageSize(), totalElements, pageList);
		return pageDataInfo;
	}

	@Override
	public AutomationProject getAutomationProjectById(long id) {
		AutomationProject automationProject = null;
		try {
			automationProject = automationProjectMapper.getAutomationProjectById(id);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return setAutomationProject(automationProject);
	}

	@Override
	public List<AutomationProject> getAutomationProjectListByDepartmentId(long departmentId) {
		List<AutomationProject> automationProjectTmpList = null;
		List<AutomationProject> automationProjectList = new ArrayList<AutomationProject>();
		try {
			automationProjectTmpList = automationProjectMapper.getAutomationProjectListByDepartmentId(departmentId);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		for (AutomationProject automationProjectTmp : automationProjectTmpList) {
			automationProjectList.add(setAutomationProject(automationProjectTmp));
		}
		return automationProjectList;
	}

	@Override
	public List<AutomationProject> getAutomationProjectListByTeamId(long teamId) {
		List<AutomationProject> automationProjectTmpList = null;
		List<AutomationProject> automationProjectList = new ArrayList<AutomationProject>();
		try {
			automationProjectTmpList = automationProjectMapper.getAutomationProjectListByTeamId(teamId);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		for (AutomationProject automationProjectTmp : automationProjectTmpList) {
			automationProjectList.add(setAutomationProject(automationProjectTmp));
		}
		return automationProjectList;
	}
}
