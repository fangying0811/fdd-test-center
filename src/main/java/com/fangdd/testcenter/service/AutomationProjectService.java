package com.fangdd.testcenter.service;

import java.util.List;

import com.fangdd.testcenter.bean.AutomationProject;
import com.fangdd.testcenter.bean.PageDataInfo;

public interface AutomationProjectService {

	public boolean addAutomationProject(AutomationProject automationProject);

	public boolean updateAutomationProject(AutomationProject automationProject);

	public boolean deleteAutomationProject(long id);

	public int getAutomationProjectCount(AutomationProject automationProject);

	public List<AutomationProject> getAutomationProjectList(AutomationProject automationProject, Integer pageIndex,
			Integer pageSize);

	public AutomationProject getAutomationProjectById(long id);

	public PageDataInfo<AutomationProject> getAutomationProjectListByPage(AutomationProject automationProject,
			Integer pageIndex, Integer pageSize);

	public List<AutomationProject> getAutomationProjectListByDepartmentId(long departmentId);

	public List<AutomationProject> getAutomationProjectListByTeamId(long teamId);
}
