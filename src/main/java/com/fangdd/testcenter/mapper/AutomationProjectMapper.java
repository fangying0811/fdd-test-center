package com.fangdd.testcenter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fangdd.testcenter.bean.AutomationProject;

public interface AutomationProjectMapper {

	public int addAutomationProject(@Param("automationProject") AutomationProject automationProject);

	public int updateAutomationProject(@Param("automationProject") AutomationProject automationProject);

	public int deleteAutomationProject(@Param("id") long id);

	public int getAutomationProjectCount(@Param("automationProject") AutomationProject automationProject);

	public List<AutomationProject> getAutomationProjectList(
			@Param("automationProject") AutomationProject automationProject, @Param("begin") Integer begin,
			@Param("size") Integer size);

	public AutomationProject getAutomationProjectById(@Param("id") long id);

	public List<AutomationProject> getAutomationProjectListByDepartmentId(@Param("departmentId") long departmentId);

	public List<AutomationProject> getAutomationProjectListByTeamId(@Param("teamId") long teamId);
}
