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
import com.fangdd.testcenter.bean.OnlineIssue;
import com.fangdd.testcenter.bean.User;
import com.fangdd.testcenter.core.HttpResult;
import com.fangdd.testcenter.core.session.SessionCheck;
import com.fangdd.testcenter.service.OnlineIssueService;
import com.fangdd.testcenter.web.util.WebConstants;
import com.fangdd.testcenter.web.util.WebUtil;

@Controller
@RequestMapping(value = "/onlineIssue")
public class OnlineIssueController extends AbstractController {

	@Autowired
	private OnlineIssueService onlineIssueService;

	@SessionCheck
	@RequestMapping(value = "/onlineIssueListUI")
	public ModelAndView onlineIssueListUI() {
		return new ModelAndView("views/onlineIssue/onlineIssueList");
	}

	@SessionCheck
	@RequestMapping(value = "/addOnlineIssueUI")
	public ModelAndView addOnlineIssueUI() {
		return new ModelAndView("views/onlineIssue/addOnlineIssue");
	}

	@SessionCheck
	@RequestMapping(value = "/editOnlineIssueUI")
	public ModelAndView editOnlineIssueUI() {
		return new ModelAndView("views/onlineIssue/editOnlineIssue");
	}

	@SessionCheck
	@RequestMapping(value = "/onlineIssueInfoUI")
	public ModelAndView onlineIssueInfoUI() {
		return new ModelAndView("views/onlineIssue/onlineIssueInfo");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/onlineIssueList.json")
	public HttpResult onlineIssueList(OnlineIssue onlineIssue) {
		if (!CommonUtil.isEmpty(onlineIssue.getStartTime())) {
			onlineIssue.setStartTime(DateTimeUtil.convertDate(onlineIssue.getStartTime(), true));
		}
		if (!CommonUtil.isEmpty(onlineIssue.getEndTime())) {
			onlineIssue.setEndTime(DateTimeUtil.convertDate(onlineIssue.getEndTime(), false));
		}
		return HttpResult.successWithData(
				onlineIssueService.getOnlineIssueListByPage(onlineIssue, getPageIndex() + 1, getPageSize()));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/processList.json")
	public HttpResult processList() {
		List<Map<String, Object>> processList = new ArrayList<Map<String, Object>>();
		processList.add(WebUtil.getMap(new String[] { "process", "processText" }, new Object[] { 1, "未分析" }));
		processList.add(WebUtil.getMap(new String[] { "process", "processText" }, new Object[] { 2, "已分析" }));
		return HttpResult.successWithData(processList);
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
	@RequestMapping(value = "/onlineIssueInfo.json")
	public HttpResult onlineIssueInfo(@RequestParam(defaultValue = "0") long onlineIssueId) {
		return HttpResult.successWithData(onlineIssueService.getOnlineIssueById(onlineIssueId));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/addOnlineIssue.json")
	public HttpResult addOnlineIssue(OnlineIssue onlineIssue) {
		User user = getSession(WebConstants.LOGIN_USER);
		onlineIssue.setUser(user);
		onlineIssue.setDepartment(user.getDepartment());
		boolean flag = onlineIssueService.addOnlineIssue(onlineIssue);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/editOnlineIssue.json")
	public HttpResult editOnlineIssue(OnlineIssue onlineIssue) {
		boolean flag = onlineIssueService.updateOnlineIssue(onlineIssue);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/deleteOnlineIssue.json")
	public HttpResult deleteOnlineIssue(@RequestParam(defaultValue = "0") long onlineIssueId) {
		boolean flag = onlineIssueService.deleteOnlineIssue(onlineIssueId);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/exportOnlineIssue.json")
	public HttpResult exportOnlineIssue(OnlineIssue onlineIssue) {
		onlineIssueService.exportOnlineIssue(onlineIssue, request, response);
		return HttpResult.successObject();
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/existOnlineIssue.json")
	public HttpResult existOnlineIssue(@RequestParam(defaultValue = "0") long issueId) {
		boolean flag = true;
		int count = onlineIssueService.getOnlineIssueCountByIssueId(issueId);
		if (count > 0) {
			flag = false;
		}
		return HttpResult.successWithData(flag);
	}
}
