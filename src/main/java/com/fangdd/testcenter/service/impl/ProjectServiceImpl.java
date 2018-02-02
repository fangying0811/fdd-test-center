package com.fangdd.testcenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fangdd.qa.framework.utils.common.DateTimeUtil;
import com.fangdd.testcenter.bean.PageDataInfo;
import com.fangdd.testcenter.bean.ProjectInfo;
import com.fangdd.testcenter.common.exception.BusinessException;
import com.fangdd.testcenter.core.SystemErrorCodeConstant;
import com.fangdd.testcenter.mapper.ProjectMapper;
import com.fangdd.testcenter.service.ProjectService;

@Service(value = "projectService")
public class ProjectServiceImpl implements ProjectService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProjectMapper projectMapper;

	@Override
	public boolean addProject(ProjectInfo projectInfo) {
		try {
			return projectMapper.addProject(projectInfo) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.ADD_FAILURE, e);
		}
	}

	@Override
	public boolean updateProject(ProjectInfo projectInfo) {
		try {
			return projectMapper.updateProject(projectInfo) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.UPDATE_FAILURE, e);
		}
	}

	@Override
	public boolean deleteProject(long projectId) {
		try {
			return projectMapper.deleteProject(projectId) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.DELETE_FAILURE, e);
		}
	}

	public ProjectInfo setProjectInfo(ProjectInfo projectInfo) {
		if (projectInfo.getStatus() == 1) {
			projectInfo.setStatusText("启用");
		} else {
			projectInfo.setStatusText("禁用");
		}
		if (projectInfo != null) {
			projectInfo.setCreateTime(DateTimeUtil.formatedTime("yyyy-MM-dd HH:mm:ss", projectInfo.getCreateTime()));
			projectInfo.setUpdateTime(DateTimeUtil.formatedTime("yyyy-MM-dd HH:mm:ss", projectInfo.getUpdateTime()));
		}
		return projectInfo;
	}

	@Override
	public int getProjectCount(ProjectInfo projectInfo) {
		int count = 0;
		try {
			count = projectMapper.getProjectCount(projectInfo);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return count;
	}

	@Override
	public List<ProjectInfo> getProjectList(ProjectInfo projectInfo, Integer pageIndex, Integer pageSize) {
		List<ProjectInfo> projectListOld = null;
		try {
			projectListOld = projectMapper.getProjectList(projectInfo, pageSize * (pageIndex - 1), pageSize);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		List<ProjectInfo> projectListNew = new ArrayList<ProjectInfo>();
		for (ProjectInfo projectNew : projectListOld) {
			projectNew = setProjectInfo(projectNew);
			projectListNew.add(projectNew);
		}
		return projectListNew;
	}

	@Override
	public PageDataInfo<ProjectInfo> getProjectListByPage(ProjectInfo projectInfo, Integer pageIndex,
			Integer pageSize) {
		/**
		 * 获取记录总数
		 */
		int totalElements = getProjectCount(projectInfo);
		/**
		 * 纠正pageIndex值，防止出现查询第0页或查询页数超过总页数情况
		 */
		PageDataInfo<ProjectInfo> pageDataInfoTemp = new PageDataInfo<ProjectInfo>(pageIndex, pageSize, totalElements);
		/**
		 * 获取当前页列表记录
		 */
		List<ProjectInfo> pageList = getProjectList(projectInfo, pageDataInfoTemp.getPageIndex(),
				pageDataInfoTemp.getPageSize());
		/**
		 * 构造PageDataInfo对象
		 */
		PageDataInfo<ProjectInfo> pageDataInfo = new PageDataInfo<ProjectInfo>(pageDataInfoTemp.getPageIndex(),
				pageDataInfoTemp.getPageSize(), totalElements, pageList);
		return pageDataInfo;
	}

	@Override
	public List<ProjectInfo> getProjectListByDepartmentId(long departmentId) {
		try {
			List<ProjectInfo> ProjectInfoList = projectMapper.getProjectListByDepartmentId(departmentId);
			return ProjectInfoList;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
	}

	@Override
	public List<ProjectInfo> getProjectListByTeamId(long teamId) {
		try {
			List<ProjectInfo> ProjectInfoList = projectMapper.getProjectListByTeamId(teamId);
			return ProjectInfoList;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
	}

	@Override
	public int getProjectCountByName(String name) {
		int count = 0;
		try {
			count = projectMapper.getProjectCountByName(name);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return count;
	}

	@Override
	public ProjectInfo getProjectById(long projectId) {
		try {
			ProjectInfo projectInfo = projectMapper.getProjectById(projectId);
			return projectInfo;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
	}

	@Override
	public List<ProjectInfo> getProjectListByTeamIdAndKafka(long teamId) {
		// TODO Auto-generated method stub
		List<ProjectInfo> list= projectMapper.getProjectListByTeamIdAndKafka(teamId);
		return list;
	}
}
