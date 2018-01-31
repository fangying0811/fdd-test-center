package com.fangdd.testcenter.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fangdd.qa.framework.utils.common.DateTimeUtil;
import com.fangdd.testcenter.bean.TestStatistic;
import com.fangdd.testcenter.bean.TestSuiteStatistic;
import com.fangdd.testcenter.common.exception.BusinessException;
import com.fangdd.testcenter.common.util.ConfigReader;
import com.fangdd.testcenter.core.HttpResult;
import com.fangdd.testcenter.core.JsonToBean;
import com.fangdd.testcenter.core.SystemErrorCodeConstant;
import com.fangdd.testcenter.service.TestStatisticService;
import com.fangdd.testcenter.service.TestSuiteStatisticService;

@Controller
@RequestMapping(value = "/report")
public class ReportController extends AbstractController {

	@Autowired
	private TestSuiteStatisticService testSuiteStatisticService;
	@Autowired
	private TestStatisticService testStatisticService;

	@ResponseBody
	@RequestMapping(value = "/testSuiteStatistic.json", method = RequestMethod.PUT)
	public HttpResult addTestSuiteStatistic(@RequestBody TestSuiteStatistic testSuiteStatistic) {
		boolean flag = testSuiteStatisticService.addTestSuiteStatistic(testSuiteStatistic);
		if (flag) {
			flag = testSuiteStatisticService.updateTestStatisticBuildId(testSuiteStatistic.getServiceId(),
					testSuiteStatistic.getStartTime(), testSuiteStatistic.getEndTime());
		}
		return HttpResult.successWithData(flag);
	}

	@ResponseBody
	@RequestMapping(value = "/testStatistic.json", method = RequestMethod.PUT)
	public HttpResult addTestStatistic(@RequestBody TestStatistic testStatistic) {
		boolean flag = testStatisticService.addTestStatistic(testStatistic);
		return HttpResult.successWithData(flag);
	}

	@ResponseBody
	@RequestMapping(value = "/testStatistic.json", method = RequestMethod.DELETE)
	public HttpResult deleteTestStatistic(@JsonToBean TestStatistic testStatistic) {
		boolean flag = testStatisticService.deleteTestStatistic(testStatistic);
		return HttpResult.successWithData(flag);
	}

	@ResponseBody
	@RequestMapping(value = "/uploadFile.json", method = RequestMethod.POST)
	public HttpResult uploadFile(@RequestParam("serviceId") long serviceId,
			@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) {
		String dirName = "files";
		String fileName = "debug." + DateTimeUtil.getTimestamp(ConfigReader.DATE_TIME_FORMAT) + ".log";
		String path = request.getRealPath(request.getServletContext().getContextPath()) + File.separator + dirName
				+ File.separator + serviceId + File.separator + fileName;
		try {
			File newFile = new File(path);
			newFile.delete();
			newFile.createNewFile();
			file.transferTo(newFile);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.UPLOAD_FILE_ERROR);
		}
		return HttpResult.successWithData(fileName);
	}
}
