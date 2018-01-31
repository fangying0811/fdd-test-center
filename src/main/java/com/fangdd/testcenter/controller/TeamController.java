package com.fangdd.testcenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fangdd.testcenter.bean.Team;
import com.fangdd.testcenter.bean.User;
import com.fangdd.testcenter.core.HttpResult;
import com.fangdd.testcenter.core.session.SessionCheck;
import com.fangdd.testcenter.service.TeamService;
import com.fangdd.testcenter.web.util.WebConstants;

@Controller
@RequestMapping(value = "/team")
public class TeamController extends AbstractController {

	@Autowired
	private TeamService teamService;

	@SessionCheck
	@RequestMapping(value = "/teamListUI")
	public ModelAndView teamListUI() {
		return new ModelAndView("views/team/teamList");
	}

	@SessionCheck
	@RequestMapping(value = "/addTeamUI")
	public ModelAndView addTeamUI() {
		return new ModelAndView("views/team/addTeam");
	}

	@SessionCheck
	@RequestMapping(value = "/editTeamUI")
	public ModelAndView editTeamUI() {
		return new ModelAndView("views/team/editTeam");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/teamList.json")
	public HttpResult departmentList(Team team) {
		return HttpResult.successWithData(teamService.getTeamListByPage(team, getPageIndex() + 1, getPageSize()));
	}

	@ResponseBody
	@RequestMapping(value = "/teamListByDepartmentId.json")
	public HttpResult teamListByDepartmentId(@RequestParam(defaultValue = "0") long departmentId) {
		if (departmentId > 0) {
			return HttpResult.successWithData(teamService.getTeamListByDepartmentId(departmentId));
		} else {
			User user = getSession(WebConstants.LOGIN_USER);
			return HttpResult
					.successWithData(teamService.getTeamListByDepartmentId(user.getDepartment().getDepartmentId()));
		}
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/existTeam.json")
	public HttpResult existTeam(@RequestParam String name) {
		boolean flag = false;
		int count = teamService.getTeamCountByName(name);
		if (count <= 0) {
			flag = true;
		}
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/teamInfo.json")
	public HttpResult teamInfo(@RequestParam(defaultValue = "0") long teamId) {
		return HttpResult.successWithData(teamService.getTeamById(teamId));
	}
	
	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/addTeam.json")
	public HttpResult addTeam(Team team) {
		boolean flag = teamService.addTeam(team);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/editTeam.json")
	public HttpResult editTeam(Team team) {
		boolean flag = teamService.updateTeam(team);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/deleteTeam.json")
	public HttpResult deleteTeam(@RequestParam(defaultValue = "0") long teamId) {
		boolean flag = false;
		// 0:删除成功 1:小组存在员工，不可删除 2：删除失败
		int result = teamService.deleteTeam(teamId);
		if (result == 0) {
			flag = true;
		}
		return HttpResult.successWithData(flag);
	}
}
