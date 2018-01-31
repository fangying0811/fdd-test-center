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

import com.fangdd.testcenter.bean.TestSuiteStatistic;
import com.fangdd.testcenter.core.HttpResult;
import com.fangdd.testcenter.core.session.SessionCheck;
import com.fangdd.testcenter.enums.TestSuiteStatisticStatusEnum;
import com.fangdd.testcenter.service.TestSuiteStatisticService;
import com.fangdd.testcenter.web.util.WebUtil;

@Controller
@RequestMapping(value = "/testSuiteStatistic")
public class TestSuiteStatisticController extends AbstractController {

	@Autowired
	private TestSuiteStatisticService testSuiteStatisticService;

	@SessionCheck
	@RequestMapping(value = "/testSuiteStatisticListUI")
	public ModelAndView testSuiteStatisticListUI() {
		return new ModelAndView("views/testSuiteStatistic/testSuiteStatisticList");
	}

	@SessionCheck
	@RequestMapping(value = "/testSuiteStatisticInfoUI")
	public ModelAndView testSuiteStatisticInfoUI() {
		return new ModelAndView("views/testSuiteStatistic/testSuiteStatisticInfo");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/testSuiteStatisticList.json")
	public HttpResult testSuiteStatisticList(TestSuiteStatistic testSuiteStatistic) {
		return HttpResult.successWithData(testSuiteStatisticService.getTestSuiteStatisticListByPage(testSuiteStatistic,
				getPageIndex() + 1, getPageSize()));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/statusList.json")
	public HttpResult statusList() {
		List<Map<String, Object>> statusList = new ArrayList<Map<String, Object>>();
		statusList.add(WebUtil.getMap(new String[] { "status", "statusText" },
				new Object[] { TestSuiteStatisticStatusEnum.PASSED.getStatus(),
						TestSuiteStatisticStatusEnum.PASSED.getDescription() }));
		statusList.add(WebUtil.getMap(new String[] { "status", "statusText" },
				new Object[] { TestSuiteStatisticStatusEnum.FAILED.getStatus(),
						TestSuiteStatisticStatusEnum.FAILED.getDescription() }));
		return HttpResult.successWithData(statusList);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/testSuiteStatisticInfo.json")
	public HttpResult testSuiteStatisticInfo(@RequestParam(defaultValue = "0") long id) {
		return HttpResult.successWithData(testSuiteStatisticService.getTestSuiteStatisticById(id));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/deleteTestSuiteStatisticById.json")
	public HttpResult deleteTestSuiteStatisticById(@RequestParam(defaultValue = "0") long id) {
		return HttpResult.successWithData(testSuiteStatisticService.deleteTestSuiteStatisticById(id));
	}
}
