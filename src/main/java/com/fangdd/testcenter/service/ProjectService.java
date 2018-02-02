package com.fangdd.testcenter.service;

import java.util.List;

import com.fangdd.testcenter.bean.PageDataInfo;
import com.fangdd.testcenter.bean.ProjectInfo;

public interface ProjectService {
	public boolean addProject(ProjectInfo projectInfo);

	public boolean updateProject(ProjectInfo projectInfo);

	public boolean deleteProject(long projectId);

	public int getProjectCount(ProjectInfo projectInfo);

	public List<ProjectInfo> getProjectList(ProjectInfo projectInfo, Integer pageIndex, Integer pageSize);

	public PageDataInfo<ProjectInfo> getProjectListByPage(ProjectInfo projectInfo, Integer pageIndex, Integer pageSize);

	public List<ProjectInfo> getProjectListByDepartmentId(long departmentId);

	public List<ProjectInfo> getProjectListByTeamId(long teamId);

	public int getProjectCountByName(String name);

	public ProjectInfo getProjectById(long projectId);
	
	public List<ProjectInfo> getProjectListByTeamIdAndKafka(long teamId);
}
