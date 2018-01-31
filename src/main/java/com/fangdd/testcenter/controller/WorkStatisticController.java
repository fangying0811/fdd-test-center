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
import com.fangdd.testcenter.bean.WeeklyReport;
import com.fangdd.testcenter.bean.WorkStatistic;
import com.fangdd.testcenter.common.util.ConfigReader;
import com.fangdd.testcenter.core.HttpResult;
import com.fangdd.testcenter.core.session.SessionCheck;
import com.fangdd.testcenter.enums.TestProcessStatusEnum;
import com.fangdd.testcenter.enums.VersionTypeEnum;
import com.fangdd.testcenter.service.WeeklyReportService;
import com.fangdd.testcenter.service.WorkStatisticService;
import com.fangdd.testcenter.web.util.WebUtil;

@Controller
@RequestMapping(value = "/workStatistic")
public class WorkStatisticController extends AbstractController {

	@Autowired
	private WorkStatisticService workStatisticService;
	@Autowired
	private WeeklyReportService weeklyReportService;

	@SessionCheck
	@RequestMapping(value = "/departmentWorkStatisticListUI")
	public ModelAndView departmentWorkStatisticListUI() {
		return new ModelAndView("views/workStatistic/departmentWorkStatisticList");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/departmentWorkStatisticList.json")
	public HttpResult departmentWorkStatisticList(WorkStatistic workStatistic) {
		if (CommonUtil.isEmpty(workStatistic.getStartTime())) {
			workStatistic.setStartTime(ConfigReader.nowMonday);
		} else {
			workStatistic.setStartTime(DateTimeUtil
					.convertDate(DateTimeUtil.formatedTime("yyyy-MM-dd", workStatistic.getStartTime()), true));
		}
		if (CommonUtil.isEmpty(workStatistic.getEndTime())) {
			workStatistic.setEndTime(ConfigReader.nowSunday);
		} else {
			workStatistic.setEndTime(DateTimeUtil
					.convertDate(DateTimeUtil.formatedTime("yyyy-MM-dd", workStatistic.getEndTime()), false));
		}
		return HttpResult.successWithData(workStatisticService.getDepartmentWorkStatisticList(workStatistic));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/exportDepartmentWorkStatistic.json")
	public HttpResult exportDepartmentWorkStatistic(WorkStatistic workStatistic) {
		workStatisticService.exportDepartmentWorkStatistic(workStatistic, request, response);
		return HttpResult.successObject();
	}

	@SessionCheck
	@RequestMapping(value = "/teamWorkStatisticListUI")
	public ModelAndView teamWorkStatisticListUI() {
		return new ModelAndView("views/workStatistic/teamWorkStatisticList");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/teamWorkStatisticList.json")
	public HttpResult weeklyReportList(WorkStatistic workStatistic) {
		if (CommonUtil.isEmpty(workStatistic.getStartTime())) {
			workStatistic.setStartTime(ConfigReader.nowMonday);
		} else {
			workStatistic.setStartTime(DateTimeUtil
					.convertDate(DateTimeUtil.formatedTime("yyyy-MM-dd", workStatistic.getStartTime()), true));
		}
		if (CommonUtil.isEmpty(workStatistic.getEndTime())) {
			workStatistic.setEndTime(ConfigReader.nowSunday);
		} else {
			workStatistic.setEndTime(DateTimeUtil
					.convertDate(DateTimeUtil.formatedTime("yyyy-MM-dd", workStatistic.getEndTime()), false));
		}
		return HttpResult.successWithData(workStatisticService.getTeamWorkStatisticList(workStatistic));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/exportTeamWorkStatistic.json")
	public HttpResult exportTeamWorkStatistic(WorkStatistic workStatistic) {
		workStatisticService.exportTeamWorkStatistic(workStatistic, request, response);
		return HttpResult.successObject();
	}

	@SessionCheck
	@RequestMapping(value = "/versionReleaseListUI")
	public ModelAndView versionReleaseListUI() {
		return new ModelAndView("views/workStatistic/versionReleaseList");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/versionReleaseList.json")
	public HttpResult versionReleaseList(WorkStatistic workStatistic) {
		workStatistic.setStartTime(DateTimeUtil.convertDate(workStatistic.getStartTime(), true));
		workStatistic.setEndTime(DateTimeUtil.convertDate(workStatistic.getEndTime(), false));
		WeeklyReport weeklyReport = new WeeklyReport();
		// 测试进度：1：未开始，2：进行中，3：已完成
		weeklyReport.setTestStatus(TestProcessStatusEnum.DONE.getStatus());
		if (workStatistic.getType() > 0) {
			weeklyReport.setType(workStatistic.getType());
		} else {
			// 类型：1，版本测试 2，其他 3，活动测试 4，临时需求测试
			weeklyReport.setTypeListStr(ConfigReader.TYPE_LIST_STR);
		}
		weeklyReport.setDepartment(workStatistic.getDepartment());
		weeklyReport.setTeam(workStatistic.getTeam());
		weeklyReport.setProject(workStatistic.getProject());
		weeklyReport.setResource(workStatistic.getResource());
		weeklyReport.setReleaseStartTime(workStatistic.getStartTime());
		weeklyReport.setReleaseEndTime(workStatistic.getEndTime());
		return HttpResult.successWithData(weeklyReportService.getWeeklyReportStatisticListByPage(weeklyReport,
				getPageIndex() + 1, getPageSize()));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/exportVersionReleaseList.json")
	public HttpResult exportVersionReleaseList(WorkStatistic workStatistic) {
		workStatisticService.exportVersionReleaseList(workStatistic, request, response);
		return HttpResult.successObject();
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
	@RequestMapping(value = "/versionReleaseTypeList.json")
	public HttpResult versionReleaseTypeList() {
		List<Map<String, Object>> statisticTypeList = new ArrayList<Map<String, Object>>();
		statisticTypeList.add(
				WebUtil.getMap(new String[] { "statisticType", "statisticTypeText" }, new Object[] { "1", "BUG" }));
		statisticTypeList.add(
				WebUtil.getMap(new String[] { "statisticType", "statisticTypeText" }, new Object[] { "2", "测试用例" }));
		return HttpResult.successWithData(statisticTypeList);
	}

	@SessionCheck
	@RequestMapping(value = "/teamWorkStatisticLineChartUI")
	public ModelAndView teamWorkStatisticLineChartUI() {
		return new ModelAndView("views/workStatistic/teamWorkStatisticLineChart");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/teamWorkStatisticEChartLine.json")
	public HttpResult teamWorkStatisticEChartLine(WorkStatistic workStatistic) {
		return HttpResult.successWithData(workStatisticService.getTeamWorkStatisticEChartLine(workStatistic));
	}

	@SessionCheck
	@RequestMapping(value = "/versionReleaseLineChartUI")
	public ModelAndView versionReleaseLineChartUI() {
		return new ModelAndView("views/workStatistic/versionReleaseLineChart");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/versionReleaseLineChart.json")
	public HttpResult versionReleaseLineChart(WorkStatistic workStatistic) {
		WeeklyReport weeklyReport = new WeeklyReport();
		// 测试进度：0：未开始，1：进行中，2：已完成
		weeklyReport.setTestStatus(TestProcessStatusEnum.DONE.getStatus());
		if (workStatistic.getType() > 0) {
			weeklyReport.setType(workStatistic.getType());

		} else {
			// 类型：1，版本测试 2，其他 3，活动测试 4，临时需求测试
			weeklyReport.setTypeListStr(ConfigReader.TYPE_LIST_STR);
		}
		weeklyReport.setProject(workStatistic.getProject());
		weeklyReport.setOrderBy("desc");
		return HttpResult.successWithData(
				weeklyReportService.getVersionReleaseLineChart(weeklyReport, workStatistic.getStatisticType()));
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
