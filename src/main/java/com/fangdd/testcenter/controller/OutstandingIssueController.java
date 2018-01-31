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
import com.fangdd.qa.framework.utils.common.DateTimeUtil;
import com.fangdd.testcenter.bean.OutstandingIssue;
import com.fangdd.testcenter.bean.User;
import com.fangdd.testcenter.core.HttpResult;
import com.fangdd.testcenter.core.session.SessionCheck;
import com.fangdd.testcenter.service.OutstandingIssueService;
import com.fangdd.testcenter.web.util.WebConstants;
import com.fangdd.testcenter.web.util.WebUtil;

@Controller
@RequestMapping(value = "/outstandingIssue")
public class OutstandingIssueController extends AbstractController {

	@Autowired
	private OutstandingIssueService outstandingIssueService;

	@SessionCheck
	@RequestMapping(value = "/outstandingIssueListUI")
	public ModelAndView outstandingIssueListUI() {
		return new ModelAndView("views/outstandingIssue/outstandingIssueList");
	}

	@SessionCheck
	@RequestMapping(value = "/addOutstandingIssueUI")
	public ModelAndView addOutstandingIssueUI() {
		return new ModelAndView("views/outstandingIssue/addOutstandingIssue");
	}

	@SessionCheck
	@RequestMapping(value = "/editOutstandingIssueUI")
	public ModelAndView editOutstandingIssueUI() {
		return new ModelAndView("views/outstandingIssue/editOutstandingIssue");
	}

	@SessionCheck
	@RequestMapping(value = "/outstandingIssueInfoUI")
	public ModelAndView outstandingIssueInfoUI() {
		return new ModelAndView("views/outstandingIssue/outstandingIssueInfo");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/outstandingIssueList.json")
	public HttpResult outstandingIssueList(OutstandingIssue outstandingIssue) {
		if (!CommonUtil.isEmpty(outstandingIssue.getStartTime())) {
			outstandingIssue.setStartTime(DateTimeUtil.convertDate(outstandingIssue.getStartTime(), true));
		}
		if (!CommonUtil.isEmpty(outstandingIssue.getEndTime())) {
			outstandingIssue.setEndTime(DateTimeUtil.convertDate(outstandingIssue.getEndTime(), false));
		}
		return HttpResult.successWithData(outstandingIssueService.getOutstandingIssueListByPage(outstandingIssue,
				getPageIndex() + 1, getPageSize()));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/resolveStatusList.json")
	public HttpResult resolveStatusList() {
		List<Map<String, Object>> statusList = new ArrayList<Map<String, Object>>();
		statusList
				.add(WebUtil.getMap(new String[] { "resolveStatus", "resolveStatusText" }, new Object[] { 1, "未解决" }));
		statusList
				.add(WebUtil.getMap(new String[] { "resolveStatus", "resolveStatusText" }, new Object[] { 2, "已解决" }));
		statusList
				.add(WebUtil.getMap(new String[] { "resolveStatus", "resolveStatusText" }, new Object[] { 3, "不解决" }));
		return HttpResult.successWithData(statusList);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/outstandingIssueInfo.json")
	public HttpResult outstandingIssueInfo(@RequestParam(defaultValue = "0") long outstandingIssueId) {
		return HttpResult.successWithData(outstandingIssueService.getOutstandingIssueById(outstandingIssueId));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/addOutstandingIssue.json")
	public HttpResult addOutstandingIssue(OutstandingIssue outstandingIssue) {
		User user = getSession(WebConstants.LOGIN_USER);
		outstandingIssue.setUser(user);
		outstandingIssue.setDepartment(user.getDepartment());
		boolean flag = outstandingIssueService.addOutstandingIssue(outstandingIssue);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/editOutstandingIssue.json")
	public HttpResult editOutstandingIssue(OutstandingIssue outstandingIssue) {
		boolean flag = outstandingIssueService.updateOutstandingIssue(outstandingIssue);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/deleteOutstandingIssue.json")
	public HttpResult deleteOutstandingIssue(@RequestParam(defaultValue = "0") long outstandingIssueId) {
		boolean flag = outstandingIssueService.deleteOutstandingIssue(outstandingIssueId);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/exportOutstandingIssue.json")
	public HttpResult exportOutstandingIssue(OutstandingIssue outstandingIssue) {
		outstandingIssueService.exportOutstandingIssue(outstandingIssue, request, response);
		return HttpResult.successObject();
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/existOutstandingIssue.json")
	public HttpResult existOutstandingIssue(@RequestParam(defaultValue = "0") long issueId) {
		boolean flag = true;
		int count = outstandingIssueService.getOutstandingIssueCountByIssueId(issueId);
		if (count > 0) {
			flag = false;
		}
		return HttpResult.successWithData(flag);
	}
}
