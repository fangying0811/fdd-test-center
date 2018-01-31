package com.fangdd.testcenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fangdd.testcenter.bean.AutomationProject;
import com.fangdd.testcenter.bean.User;
import com.fangdd.testcenter.core.HttpResult;
import com.fangdd.testcenter.core.session.SessionCheck;
import com.fangdd.testcenter.service.AutomationProjectService;
import com.fangdd.testcenter.web.util.WebConstants;

@Controller
@RequestMapping(value = "/automationProject")
public class AutomationProjectController extends AbstractController {

	@Autowired
	private AutomationProjectService automationProjectService;

	@SessionCheck
	@RequestMapping(value = "/automationProjectListUI")
	public ModelAndView automationProjectListUI() {
		return new ModelAndView("views/automationProject/automationProjectList");
	}

	@SessionCheck
	@RequestMapping(value = "/addAutomationProjectUI")
	public ModelAndView addAutomationProjectUI() {
		return new ModelAndView("views/automationProject/addAutomationProject");
	}

	@SessionCheck
	@RequestMapping(value = "/editAutomationProjectUI")
	public ModelAndView editAutomationProjectUI() {
		return new ModelAndView("views/automationProject/editAutomationProject");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/automationProjectList.json")
	public HttpResult automationProjectList(AutomationProject automationProject) {
		return HttpResult.successWithData(automationProjectService.getAutomationProjectListByPage(automationProject,
				getPageIndex() + 1, getPageSize()));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/addAutomationProject.json")
	public HttpResult addAutomationProject(AutomationProject automationProject) {
		boolean flag = automationProjectService.addAutomationProject(automationProject);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/editAutomationProject.json")
	public HttpResult editAutomationProject(AutomationProject automationProject) {
		boolean flag = automationProjectService.updateAutomationProject(automationProject);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/deleteAutomationProject.json")
	public HttpResult deleteAutomationProject(@RequestParam(defaultValue = "0") long id) {
		boolean flag = automationProjectService.deleteAutomationProject(id);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/automationProjectInfo.json")
	public HttpResult automationProjectInfo(@RequestParam(defaultValue = "0") long id) {
		return HttpResult.successWithData(automationProjectService.getAutomationProjectById(id));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/automationProjectListByDepartmentId.json")
	public HttpResult automationProjectListByDepartmentId(@RequestParam(defaultValue = "0") long departmentId) {
		if (departmentId > 0) {
			return HttpResult
					.successWithData(automationProjectService.getAutomationProjectListByDepartmentId(departmentId));
		} else {
			User user = getSession(WebConstants.LOGIN_USER);
			return HttpResult.successWithData(automationProjectService
					.getAutomationProjectListByDepartmentId(user.getDepartment().getDepartmentId()));
		}
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/automationProjectListByTeamId.json")
	public HttpResult automationProjectListByTeamId(@RequestParam(defaultValue = "0") long teamId) {
		if (teamId > 0) {
			return HttpResult.successWithData(automationProjectService.getAutomationProjectListByTeamId(teamId));
		} else {
			User user = getSession(WebConstants.LOGIN_USER);
			return HttpResult.successWithData(
					automationProjectService.getAutomationProjectListByTeamId(user.getTeam().getTeamId()));
		}
	}
}
