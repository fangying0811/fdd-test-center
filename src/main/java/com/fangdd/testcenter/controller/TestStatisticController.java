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

import com.fangdd.testcenter.bean.TestStatistic;
import com.fangdd.testcenter.core.HttpResult;
import com.fangdd.testcenter.core.session.SessionCheck;
import com.fangdd.testcenter.enums.TestStatisticStatusEnum;
import com.fangdd.testcenter.service.TestStatisticService;
import com.fangdd.testcenter.web.util.WebConstants;
import com.fangdd.testcenter.web.util.WebUtil;

@Controller
@RequestMapping(value = "/testStatistic")
public class TestStatisticController extends AbstractController {

	@Autowired
	private TestStatisticService testStatisticService;

	@SessionCheck
	@RequestMapping(value = "/testStatisticListUI")
	public ModelAndView testStatisticListUI() {
		return new ModelAndView("views/testStatistic/testStatisticList");
	}

	@SessionCheck
	@RequestMapping(value = "/testStatisticInfoUI")
	public ModelAndView testStatisticInfoUI() {
		return new ModelAndView("views/testStatistic/testStatisticInfo");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/testStatisticList.json")
	public HttpResult testStatisticList(TestStatistic testStatistic) {
		if (testStatistic.getBuildId() > 0) {
			removeSession(WebConstants.BUILD_ID);
			setSession(WebConstants.BUILD_ID, testStatistic.getBuildId());
		} else if (getSession(WebConstants.BUILD_ID) != null && (long) getSession(WebConstants.BUILD_ID) > 0) {
			testStatistic.setBuildId((long) getSession(WebConstants.BUILD_ID));
		}
		return HttpResult.successWithData(
				testStatisticService.getTestStatisticListByPage(testStatistic, getPageIndex() + 1, getPageSize()));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/statusList.json")
	public HttpResult statusList() {
		List<Map<String, Object>> statusList = new ArrayList<Map<String, Object>>();
		statusList.add(WebUtil.getMap(new String[] { "status", "statusText" }, new Object[] {
				TestStatisticStatusEnum.PASSED.getStatus(), TestStatisticStatusEnum.PASSED.getDescription() }));
		statusList.add(WebUtil.getMap(new String[] { "status", "statusText" }, new Object[] {
				TestStatisticStatusEnum.FAILED.getStatus(), TestStatisticStatusEnum.FAILED.getDescription() }));
		statusList.add(WebUtil.getMap(new String[] { "status", "statusText" }, new Object[] {
				TestStatisticStatusEnum.SKIPPED.getStatus(), TestStatisticStatusEnum.SKIPPED.getDescription() }));
		return HttpResult.successWithData(statusList);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/testStatisticInfo.json")
	public HttpResult testStatisticInfo(@RequestParam(defaultValue = "0") long id) {
		return HttpResult.successWithData(testStatisticService.getTestStatisticById(id));
	}
}
