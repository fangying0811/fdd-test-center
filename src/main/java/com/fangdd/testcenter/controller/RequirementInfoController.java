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
import com.fangdd.testcenter.bean.RequirementInfo;
import com.fangdd.testcenter.bean.User;
import com.fangdd.testcenter.core.HttpResult;
import com.fangdd.testcenter.core.session.SessionCheck;
import com.fangdd.testcenter.service.RequirementInfoService;
import com.fangdd.testcenter.web.util.WebConstants;
import com.fangdd.testcenter.web.util.WebUtil;

@Controller
@RequestMapping(value = "/requirementInfo")
public class RequirementInfoController extends AbstractController {

	@Autowired
	private RequirementInfoService requirementInfoService;

	@SessionCheck
	@RequestMapping(value = "/requirementInfoListUI")
	public ModelAndView requirementInfoListUI() {
		return new ModelAndView("views/requirementInfo/requirementInfoList");
	}

	@SessionCheck
	@RequestMapping(value = "/addRequirementInfoUI")
	public ModelAndView addRequirementInfoUI() {
		return new ModelAndView("views/requirementInfo/addRequirementInfo");
	}

	@SessionCheck
	@RequestMapping(value = "/editRequirementInfoUI")
	public ModelAndView editRequirementInfoUI() {
		return new ModelAndView("views/requirementInfo/editRequirementInfo");
	}

	@SessionCheck
	@RequestMapping(value = "/requirementInfoUI")
	public ModelAndView requirementInfoUI() {
		return new ModelAndView("views/requirementInfo/requirementInfo");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/requirementInfoList.json")
	public HttpResult requirementInfoList(RequirementInfo requirementInfo) {
		if (!CommonUtil.isEmpty(requirementInfo.getStartTime())) {
			requirementInfo.setStartTime(DateTimeUtil.convertDate(requirementInfo.getStartTime(), true));
		}
		if (!CommonUtil.isEmpty(requirementInfo.getEndTime())) {
			requirementInfo.setEndTime(DateTimeUtil.convertDate(requirementInfo.getEndTime(), false));
		}
		return HttpResult.successWithData(requirementInfoService.getRequirementInfoListByPage(requirementInfo,
				getPageIndex() + 1, getPageSize()));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/statusList.json")
	public HttpResult statusList() {
		List<Map<String, Object>> statusList = new ArrayList<Map<String, Object>>();
		statusList.add(WebUtil.getMap(new String[] { "status", "statusText" }, new Object[] { 0, "待定" }));
		statusList.add(WebUtil.getMap(new String[] { "status", "statusText" }, new Object[] { 1, "已定" }));
		return HttpResult.successWithData(statusList);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/requirementInfo.json")
	public HttpResult requirementInfo(@RequestParam(defaultValue = "0") long requirementId) {
		return HttpResult.successWithData(requirementInfoService.getRequirementInfoById(requirementId));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/addRequirementInfo.json")
	public HttpResult addRequirementInfo(RequirementInfo requirementInfo) {
		User user = getSession(WebConstants.LOGIN_USER);
		requirementInfo.setUser(user);
		requirementInfo.setDepartment(user.getDepartment());
		boolean flag = requirementInfoService.addRequirementInfo(requirementInfo);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/editRequirementInfo.json")
	public HttpResult editRequirementInfo(RequirementInfo requirementInfo) {
		boolean flag = requirementInfoService.updateRequirementInfo(requirementInfo);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/deleteRequirementInfo.json")
	public HttpResult deleteRequirementInfo(@RequestParam(defaultValue = "0") long requirementId) {
		boolean flag = requirementInfoService.deleteRequirementInfo(requirementId);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/exportRequirementInfo.json")
	public HttpResult exportRequirementInfo(RequirementInfo requirementInfo) {
		requirementInfoService.exportRequirementInfo(requirementInfo, request, response);
		return HttpResult.successObject();
	}
}
