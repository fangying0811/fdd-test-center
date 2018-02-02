package com.fangdd.testcenter.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fangdd.qa.framework.utils.common.CommonUtil;
import com.fangdd.testcenter.bean.ProjectInfo;
import com.fangdd.testcenter.bean.User;
import com.fangdd.testcenter.core.HttpResult;
import com.fangdd.testcenter.core.session.SessionCheck;
import com.fangdd.testcenter.service.ProjectService;
import com.fangdd.testcenter.web.util.WebConstants;
import com.fangdd.testcenter.web.util.WebUtil;

@Controller
@RequestMapping(value = "/projectInfo")
public class ProjectInfoController extends AbstractController {

	@Autowired
	private ProjectService projectService;

	@SessionCheck
	@RequestMapping(value = "/projectInfoListUI")
	public ModelAndView projectInfoListUI() {
		return new ModelAndView("views/projectInfo/projectInfoList");
	}

	@SessionCheck
	@RequestMapping(value = "/addProjectInfoUI")
	public ModelAndView addProjectInfoUI() {
		return new ModelAndView("views/projectInfo/addProjectInfo");
	}

	@SessionCheck
	@RequestMapping(value = "/editProjectInfoUI")
	public ModelAndView editProjectInfoUI() {
		return new ModelAndView("views/projectInfo/editProjectInfo");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/projectInfoList.json")
	public HttpResult projectInfoList(ProjectInfo projectInfo) {
		return HttpResult
				.successWithData(projectService.getProjectListByPage(projectInfo, getPageIndex() + 1, getPageSize()));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/projectInfoListByTeamId.json")
	public HttpResult projectInfoListByTeamId(@RequestParam(defaultValue = "0") long teamId) {
		List<ProjectInfo> projectInfos;
		if (teamId > 0) {
			projectInfos = projectService.getProjectListByTeamId(teamId);
			if (CommonUtil.isEmpty(projectInfos)) {
				User user = getSession(WebConstants.LOGIN_USER);
				projectInfos = projectService.getProjectListByDepartmentId(user.getDepartment().getDepartmentId());
			}
		} else {
			User user = getSession(WebConstants.LOGIN_USER);
			projectInfos = projectService.getProjectListByDepartmentId(user.getDepartment().getDepartmentId());
		}
		return HttpResult.successWithData(projectInfos);
	}
	
	@ResponseBody
	@RequestMapping(value = "/projectInfoListByTeamAndKafka.json")
	public HttpResult projectInfoListByTeamAndKafka(@RequestParam(defaultValue = "0") long teamId) {
		List<ProjectInfo> projectInfos=null;
		if (teamId > 0) {
			projectInfos=projectService.getProjectListByTeamIdAndKafka(teamId);
		}
		return HttpResult.successWithData(projectInfos);
	}



	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/projectInfoListByDepartmentId.json")
	public HttpResult projectInfoListByDepartmentId(@RequestParam(defaultValue = "0") long departmentId) {
		List<ProjectInfo> projectInfos;
		if (departmentId > 0) {
			projectInfos = projectService.getProjectListByDepartmentId(departmentId);
		} else {
			User user = getSession(WebConstants.LOGIN_USER);
			projectInfos = projectService.getProjectListByDepartmentId(user.getDepartment().getDepartmentId());
		}
		return HttpResult.successWithData(projectInfos);
	}
	
	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/existProject.json")
	public HttpResult existProject(@RequestParam String projectName) {
		boolean flag = false;
		int count = projectService.getProjectCountByName(projectName);
		if (count <= 0) {
			flag = true;
		}
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/statusList.json")
	public HttpResult statusList() {
		List<Map<String, Object>> statusList = new ArrayList<Map<String, Object>>();
		statusList.add(WebUtil.getMap(new String[] { "status", "statusText" }, new Object[] { 1, "启用" }));
		statusList.add(WebUtil.getMap(new String[] { "status", "statusText" }, new Object[] { 2, "禁用" }));
		return HttpResult.successWithData(statusList);
	}
	
	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/addProject.json")
	public HttpResult addProject(ProjectInfo projectInfo) {
		User user = getSession(WebConstants.LOGIN_USER);
		projectInfo.setUser(user);
		boolean flag = projectService.addProject(projectInfo);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/projectInfo.json")
	public HttpResult projectInfo(@RequestParam(defaultValue = "0") long projectId) {
		return HttpResult.successWithData(projectService.getProjectById(projectId));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/editProject.json")
	public HttpResult editProject(ProjectInfo projectInfo) {
		boolean flag = projectService.updateProject(projectInfo);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/deleteProject.json")
	public HttpResult deleteProject(@RequestParam(defaultValue = "0") long projectId) {
		boolean flag = projectService.deleteProject(projectId);
		return HttpResult.successWithData(flag);
	}
}
