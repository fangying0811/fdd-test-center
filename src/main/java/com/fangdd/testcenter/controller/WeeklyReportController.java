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
import com.fangdd.testcenter.bean.User;
import com.fangdd.testcenter.bean.WeeklyReport;
import com.fangdd.testcenter.common.util.ConfigReader;
import com.fangdd.testcenter.core.HttpResult;
import com.fangdd.testcenter.core.session.SessionCheck;
import com.fangdd.testcenter.enums.TestProcessStatusEnum;
import com.fangdd.testcenter.enums.VersionTypeEnum;
import com.fangdd.testcenter.service.WeeklyReportService;
import com.fangdd.testcenter.web.util.WebConstants;
import com.fangdd.testcenter.web.util.WebUtil;

@Controller
@RequestMapping(value = "/weeklyReport")
public class WeeklyReportController extends AbstractController {

	@Autowired
	private WeeklyReportService weeklyReportService;

	@SessionCheck
	@RequestMapping(value = "/weeklyReportListUI")
	public ModelAndView weeklyReportListUI() {
		return new ModelAndView("views/weeklyReport/weeklyReportList");
	}

	@SessionCheck
	@RequestMapping(value = "/addWeeklyReportUI")
	public ModelAndView addWeeklyReportUI() {
		return new ModelAndView("views/weeklyReport/addWeeklyReport");
	}

	@SessionCheck
	@RequestMapping(value = "/copyWeeklyReportUI")
	public ModelAndView copyWeeklyReportUI() {
		return new ModelAndView("views/weeklyReport/copyWeeklyReport");
	}

	@SessionCheck
	@RequestMapping(value = "/editWeeklyReportUI")
	public ModelAndView editWeeklyReportUI() {
		return new ModelAndView("views/weeklyReport/editWeeklyReport");
	}

	@SessionCheck
	@RequestMapping(value = "/selectVersionInfoUI")
	public ModelAndView selectVersionInfoUI() {
		return new ModelAndView("views/common/selectVersionInfo");
	}

	@SessionCheck
	@RequestMapping(value = "/weeklyReportInfoUI")
	public ModelAndView weeklyReportInfoUI() {
		return new ModelAndView("views/weeklyReport/weeklyReportInfo");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/weeklyReportList.json")
	public HttpResult weeklyReportList(WeeklyReport weeklyReport) {
		if (!CommonUtil.isEmpty(weeklyReport.getStartTime())) {
			weeklyReport.setStartTime(DateTimeUtil.convertDate(weeklyReport.getStartTime(), true));
		}
		if (!CommonUtil.isEmpty(weeklyReport.getEndTime())) {
			weeklyReport.setEndTime(DateTimeUtil.convertDate(weeklyReport.getEndTime(), false));
		}
		return HttpResult.successWithData(
				weeklyReportService.getWeeklyReportListByPage(weeklyReport, getPageIndex() + 1, getPageSize()));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/weeklyReportDoneList.json")
	public HttpResult weeklyReportDoneList(WeeklyReport weeklyReport) {
		if (!CommonUtil.isEmpty(weeklyReport.getStartTime())) {
			weeklyReport.setStartTime(DateTimeUtil.convertDate(weeklyReport.getStartTime(), true));
		}
		if (!CommonUtil.isEmpty(weeklyReport.getEndTime())) {
			weeklyReport.setEndTime(DateTimeUtil.convertDate(weeklyReport.getEndTime(), false));
		}
		// 测试进度：1：未开始，2：进行中，3：已完成
		weeklyReport.setTestStatus(TestProcessStatusEnum.DONE.getStatus());
		if (weeklyReport.getType() > 0) {
			weeklyReport.setTypeListStr("");
		} else {
			// 类型：1，版本测试 2，其他 3，活动测试 4，临时需求测试
			weeklyReport.setTypeListStr(ConfigReader.TYPE_LIST_STR);
		}
		return HttpResult.successWithData(
				weeklyReportService.getWeeklyReportListByPage(weeklyReport, getPageIndex() + 1, getPageSize()));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/weeklyReportDoneListByTeamId.json")
	public HttpResult weeklyReportDoneListByTeamId(WeeklyReport weeklyReport) {
		if (!CommonUtil.isEmpty(weeklyReport.getStartTime())) {
			weeklyReport.setStartTime(DateTimeUtil.convertDate(weeklyReport.getStartTime(), true));
		}
		if (!CommonUtil.isEmpty(weeklyReport.getEndTime())) {
			weeklyReport.setEndTime(DateTimeUtil.convertDate(weeklyReport.getEndTime(), false));
		}
		// 测试进度：1：未开始，2：进行中，3：已完成
		weeklyReport.setTestStatus(TestProcessStatusEnum.DONE.getStatus());
		if (weeklyReport.getType() > 0) {
			weeklyReport.setTypeListStr("");
		} else {
			// 类型：1，版本测试 2，其他 3，活动测试 4，临时需求测试
			weeklyReport.setTypeListStr(ConfigReader.TYPE_LIST_STR);
		}
		if (weeklyReport.getTeam() != null && weeklyReport.getTeam().getTeamId() > 0) {
			weeklyReport.setTeam(weeklyReport.getTeam());
		} else {
			User user = getSession(WebConstants.LOGIN_USER);
			weeklyReport.setTeam(user.getTeam());
		}
		return HttpResult.successWithData(weeklyReportService.getWeeklyReportExportList(weeklyReport));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/testStatusList.json")
	public HttpResult testStatusList() {
		List<Map<String, Object>> testStatusList = new ArrayList<Map<String, Object>>();
		testStatusList.add(WebUtil.getMap(new String[] { "testStatus", "testStatusText" }, new Object[] {
				TestProcessStatusEnum.NOT_START.getStatus(), TestProcessStatusEnum.NOT_START.getDescription() }));
		testStatusList.add(WebUtil.getMap(new String[] { "testStatus", "testStatusText" }, new Object[] {
				TestProcessStatusEnum.DOING.getStatus(), TestProcessStatusEnum.DOING.getDescription() }));
		testStatusList.add(WebUtil.getMap(new String[] { "testStatus", "testStatusText" },
				new Object[] { TestProcessStatusEnum.DONE.getStatus(), TestProcessStatusEnum.DONE.getDescription() }));
		return HttpResult.successWithData(testStatusList);
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
		typeList.add(WebUtil.getMap(new String[] { "type", "typeText" },
				new Object[] { VersionTypeEnum.OTHER.getType(), VersionTypeEnum.OTHER.getDescription() }));
		return HttpResult.successWithData(typeList);
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
	@RequestMapping(value = "/weeklyReportInfo.json")
	public HttpResult weeklyReportInfo(@RequestParam(defaultValue = "0") long weeklyReportId) {
		return HttpResult.successWithData(weeklyReportService.getWeeklyReportById(weeklyReportId));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/addWeeklyReport.json")
	public HttpResult addWeeklyReport(WeeklyReport weeklyReport) {
		User user = getSession(WebConstants.LOGIN_USER);
		weeklyReport.setUser(user);
		weeklyReport.setDepartment(user.getDepartment());
		boolean flag = weeklyReportService.addWeeklyReport(weeklyReport);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/editWeeklyReport.json")
	public HttpResult editWeeklyReport(WeeklyReport weeklyReport) {
		boolean flag = weeklyReportService.updateWeeklyReport(weeklyReport);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/deleteWeeklyReport.json")
	public HttpResult deleteWeeklyReport(@RequestParam(defaultValue = "0") long weeklyReportId) {
		boolean flag = weeklyReportService.deleteWeeklyReport(weeklyReportId);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/exportWeeklyReport.json")
	public HttpResult exportWeeklyReport(WeeklyReport weeklyReport) {
		weeklyReportService.exportWeeklyReport(weeklyReport, request, response);
		return HttpResult.successObject();
	}
}
