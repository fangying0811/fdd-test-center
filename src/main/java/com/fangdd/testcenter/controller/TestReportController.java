package com.fangdd.testcenter.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fangdd.qa.framework.utils.common.CommonUtil;
import com.fangdd.qa.framework.utils.common.DateTimeUtil;
import com.fangdd.testcenter.bean.TestReport;
import com.fangdd.testcenter.bean.User;
import com.fangdd.testcenter.core.HttpResult;
import com.fangdd.testcenter.core.session.SessionCheck;
import com.fangdd.testcenter.service.TestReportService;
import com.fangdd.testcenter.web.util.WebConstants;
import com.fangdd.testcenter.web.util.WebUtil;

@Controller
@RequestMapping(value = "/testReport")
public class TestReportController extends AbstractController {

	private Logger logger = LoggerFactory.getLogger(TestReportController.class);

	@Autowired
	private TestReportService testReportService;

	@SessionCheck
	@RequestMapping(value = "/testReportListUI")
	public ModelAndView testReportListUI() {
		return new ModelAndView("views/testReport/testReportList");
	}

	@SessionCheck
	@RequestMapping(value = "/addTestReportUI")
	public ModelAndView addTestReportUI() {
		return new ModelAndView("views/testReport/addTestReport");
	}

	@SessionCheck
	@RequestMapping(value = "/editTestReportUI")
	public ModelAndView editTestReportUI() {
		return new ModelAndView("views/testReport/editTestReport");
	}

	@SessionCheck
	@RequestMapping(value = "/testReportUI")
	public ModelAndView testReportUI() {
		return new ModelAndView("views/testReport/testReport");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/testReportList.json")
	public HttpResult testReportList(TestReport testReport) {
		if (!CommonUtil.isEmpty(testReport.getStartTime())) {
			testReport.setStartTime(DateTimeUtil.convertDate(testReport.getStartTime(), true));
		}
		if (!CommonUtil.isEmpty(testReport.getEndTime())) {
			testReport.setEndTime(DateTimeUtil.convertDate(testReport.getEndTime(), false));
		}
		return HttpResult.successWithData(
				testReportService.getTestReportListByPage(testReport, getPageIndex() + 1, getPageSize()));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/testReport.json")
	public HttpResult testReport(@RequestParam(defaultValue = "0") long testReportId) {
		return HttpResult.successWithData(testReportService.getTestReportById(testReportId));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/addTestReport.json")
	public HttpResult addTestReport(TestReport testReport) {
		User user = getSession(WebConstants.LOGIN_USER);
		testReport.setUser(user);
		testReport.setDepartment(user.getDepartment());
		boolean flag = testReportService.addTestReport(testReport);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/editTestReport.json")
	public HttpResult editTestReport(TestReport testReport) {
		boolean flag = testReportService.updateTestReport(testReport);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/deleteTestReport.json")
	public HttpResult deleteTestReport(@RequestParam(defaultValue = "0") long testReportId) {
		boolean flag = testReportService.deleteTestReport(testReportId);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/testResultList.json")
	public HttpResult testResultList() {
		List<Map<String, Object>> testResultList = new ArrayList<Map<String, Object>>();
		testResultList.add(WebUtil.getMap(new String[] { "testResult", "testResultText" }, new Object[] { 0, "不通过" }));
		testResultList.add(WebUtil.getMap(new String[] { "testResult", "testResultText" }, new Object[] { 1, "通过" }));
		return HttpResult.successWithData(testResultList);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/exportTestReport.json")
	public HttpResult exportTestReport(@RequestParam(defaultValue = "0") long testReportId) {
		testReportService.exportTestReport(testReportId, request, response);
		return HttpResult.successObject();
	}
}
