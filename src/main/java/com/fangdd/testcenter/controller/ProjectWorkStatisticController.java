package com.fangdd.testcenter.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fangdd.qa.framework.utils.common.CommonUtil;
import com.fangdd.qa.framework.utils.common.DateTimeUtil;
import com.fangdd.testcenter.bean.ProjectWorkStatistic;
import com.fangdd.testcenter.bean.User;
import com.fangdd.testcenter.common.util.ConfigReader;
import com.fangdd.testcenter.core.HttpResult;
import com.fangdd.testcenter.core.session.SessionCheck;
import com.fangdd.testcenter.enums.VersionTypeEnum;
import com.fangdd.testcenter.service.WorkStatisticService;
import com.fangdd.testcenter.web.util.WebConstants;
import com.fangdd.testcenter.web.util.WebUtil;

@Controller
@RequestMapping(value = "/projectWorkStatistic")
public class ProjectWorkStatisticController extends AbstractController {

	@Autowired
	private WorkStatisticService workStatisticService;

	@SessionCheck
	@RequestMapping(value = "/projectWorkStatisticListUI")
	public ModelAndView projectWorkStatisticListUI() {
		return new ModelAndView("views/projectWorkStatistic/projectWorkStatisticList");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/projectWorkStatisticList.json")
	public HttpResult projectWorkStatisticList(ProjectWorkStatistic projectWorkStatistic) {
		if (projectWorkStatistic.getDepartment() == null
				|| projectWorkStatistic.getDepartment().getDepartmentId() <= 0) {
			User user = getSession(WebConstants.LOGIN_USER);
			projectWorkStatistic.setDepartment(user.getDepartment());
		}
		if (CommonUtil.isEmpty(projectWorkStatistic.getStartTime())) {
			projectWorkStatistic.setStartTime(ConfigReader.nowMonday);
		} else {
			projectWorkStatistic.setStartTime(DateTimeUtil
					.convertDate(DateTimeUtil.formatedTime("yyyy-MM-dd", projectWorkStatistic.getStartTime()), true));
		}
		if (CommonUtil.isEmpty(projectWorkStatistic.getEndTime())) {
			projectWorkStatistic.setEndTime(ConfigReader.nowSunday);
		} else {
			projectWorkStatistic.setEndTime(DateTimeUtil
					.convertDate(DateTimeUtil.formatedTime("yyyy-MM-dd", projectWorkStatistic.getEndTime()), false));
		}
		return HttpResult.successWithData(workStatisticService.getProjectWorkStatisticList(projectWorkStatistic));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/exportProjectWorkStatistic.json")
	public HttpResult exportProjectWorkStatistic(ProjectWorkStatistic projectWorkStatistic) {
		workStatisticService.exportProjectWorkStatistic(projectWorkStatistic, request, response);
		return HttpResult.successObject();
	}

	@SessionCheck
	@RequestMapping(value = "/projectWorkStatisticLineChartUI")
	public ModelAndView projectWorkStatisticLineChartUI() {
		return new ModelAndView("views/projectWorkStatistic/projectWorkStatisticLineChart");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/statisticTypeList.json")
	public HttpResult statisticTypeList() {
		List<Map<String, Object>> statisticTypeList = new ArrayList<Map<String, Object>>();
		statisticTypeList.add(
				WebUtil.getMap(new String[] { "statisticType", "statisticTypeText" }, new Object[] { "1", "BUG" }));
		statisticTypeList.add(
				WebUtil.getMap(new String[] { "statisticType", "statisticTypeText" }, new Object[] { "2", "测试用例" }));
		statisticTypeList.add(
				WebUtil.getMap(new String[] { "statisticType", "statisticTypeText" }, new Object[] { "3", "线上问题" }));
		statisticTypeList.add(
				WebUtil.getMap(new String[] { "statisticType", "statisticTypeText" }, new Object[] { "4", "遗留问题" }));
		statisticTypeList.add(
				WebUtil.getMap(new String[] { "statisticType", "statisticTypeText" }, new Object[] { "5", "发布版本" }));
		return HttpResult.successWithData(statisticTypeList);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/projectWorkStatisticEChartLine.json")
	public HttpResult projectWorkStatisticEChartLine(ProjectWorkStatistic projectWorkStatistic) {
		return HttpResult.successWithData(workStatisticService.getProjectWorkStatisticEChartLine(projectWorkStatistic));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/typeList.json")
	public HttpResult typeList() {
		List<Map<String, Object>> typeList = new ArrayList<Map<String, Object>>();
		typeList.add(WebUtil.getMap(new String[] { "type", "typeText" },
				new Object[] { VersionTypeEnum.VERSION.getType(), VersionTypeEnum.VERSION.getDescription() }));
		typeList.add(WebUtil.getMap(new String[] { "type", "typeText" },
				new Object[] { VersionTypeEnum.ACTIVITY.getType(), VersionTypeEnum.ACTIVITY.getDescription() }));
		typeList.add(WebUtil.getMap(new String[] { "type", "typeText" },
				new Object[] { VersionTypeEnum.TEMPVERSION.getType(), VersionTypeEnum.TEMPVERSION.getDescription() }));
		return HttpResult.successWithData(typeList);
	}
}
