package com.fangdd.testcenter.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fangdd.qa.framework.utils.common.DateTimeUtil;
import com.fangdd.testcenter.bean.PageDataInfo;
import com.fangdd.testcenter.bean.TestReport;
import com.fangdd.testcenter.common.exception.BusinessException;
import com.fangdd.testcenter.common.util.ExportExcelFromTemplate;
import com.fangdd.testcenter.core.SystemErrorCodeConstant;
import com.fangdd.testcenter.mapper.TestReportMapper;
import com.fangdd.testcenter.service.TestReportService;
import com.fangdd.testcenter.web.util.WebConstants;

@Service(value = "TestReportService")
public class TestReportServiceImpl implements TestReportService {
	private Logger logger = LoggerFactory.getLogger(TestReportServiceImpl.class);

	@Autowired
	private TestReportMapper testReportMapper;
	private InputStream excelFile;
	private String downloadFileName;

	public InputStream getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(InputStream excelFile) {
		this.excelFile = excelFile;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	@Override
	public boolean addTestReport(TestReport testReport) {
		try {
			return testReportMapper.addTestReport(testReport) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.ADD_FAILURE, e);
		}
	}

	@Override
	public boolean updateTestReport(TestReport testReport) {
		try {
			return testReportMapper.updateTestReport(testReport) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.UPDATE_FAILURE, e);
		}
	}

	@Override
	public boolean deleteTestReport(long testReportId) {
		try {
			return testReportMapper.deleteTestReport(testReportId) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.DELETE_FAILURE, e);
		}
	}

	@Override
	public int getTestReportCount(TestReport testReport) {
		int count = 0;
		try {
			count = testReportMapper.getTestReportCount(testReport);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return count;
	}

	public TestReport setTestReport(TestReport testReport) {
		if (testReport.getTestResult() == 1) {
			testReport.setTestResultText("通过");
		} else if (testReport.getTestResult() == 0) {
			testReport.setTestResultText("不通过");
		}
		/**
		if (CommonUtil.isEmpty(testReport.getDevTag())) {
			testReport.setDevTag("暂无");
		}
		if (CommonUtil.isEmpty(testReport.getReleaseTag())) {
			testReport.setReleaseTag("暂无");
		}
		testReport.setDevTag(StringUtil.inTextArea(testReport.getDevTag()));
		testReport.setReleaseTag(StringUtil.inTextArea(testReport.getReleaseTag()));
		*/
		testReport.setCreateTime(DateTimeUtil.formatedTime("yyyy-MM-dd HH:mm:ss", testReport.getCreateTime()));
		testReport.setTrueStartTime(DateTimeUtil.formatedTime("yyyy-MM-dd", testReport.getTrueStartTime()));
		testReport.setTrueEndTime(DateTimeUtil.formatedTime("yyyy-MM-dd", testReport.getTrueEndTime()));
		return testReport;
	}

	@Override
	public List<TestReport> getTestReportList(TestReport testReport, Integer pageIndex, Integer pageSize) {
		List<TestReport> testReportListOld = null;
		try {
			testReportListOld = testReportMapper.getTestReportList(testReport, pageSize * (pageIndex - 1), pageSize);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		List<TestReport> testReportListNew = new ArrayList<TestReport>();
		for (TestReport testReportNew : testReportListOld) {
			testReportNew = setTestReport(testReportNew);
			testReportListNew.add(testReportNew);
		}
		return testReportListNew;
	}

	@Override
	public PageDataInfo<TestReport> getTestReportListByPage(TestReport testReport, Integer pageIndex,
			Integer pageSize) {
		/**
		 * 获取记录总数
		 */
		int totalElements = getTestReportCount(testReport);
		/**
		 * 纠正pageIndex值，防止出现查询第0页或查询页数超过总页数情况
		 */
		PageDataInfo<TestReport> pageDataInfoTemp = new PageDataInfo<TestReport>(pageIndex, pageSize, totalElements);
		/**
		 * 获取当前页列表记录
		 */
		List<TestReport> pageList = getTestReportList(testReport, pageDataInfoTemp.getPageIndex(),
				pageDataInfoTemp.getPageSize());
		/**
		 * 构造PageDataInfo对象
		 */
		PageDataInfo<TestReport> pageDataInfo = new PageDataInfo<TestReport>(pageDataInfoTemp.getPageIndex(),
				pageDataInfoTemp.getPageSize(), totalElements, pageList);
		return pageDataInfo;
	}

	@Override
	public TestReport getTestReportById(long testReportId) {
		TestReport testReport = null;
		try {
			testReport = testReportMapper.getTestReportById(testReportId);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return setTestReport(testReport);
	}

	@Override
	public void exportTestReport(long testReportId, HttpServletRequest request, HttpServletResponse response) {
		TestReport testReportExport = getTestReportById(testReportId);

		String title = "【测试报告】" + testReportExport.getProject().getProjectName() + "_"
				+ testReportExport.getVersionInfo();
		setDownloadFileName(title);

		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("projectName", testReportExport.getProject().getProjectName());
		beans.put("versionInfo", testReportExport.getVersionInfo());
		beans.put("testResultText", testReportExport.getTestResultText());
		beans.put("trueStartTime", testReportExport.getTrueStartTime());
		beans.put("trueEndTime", testReportExport.getTrueEndTime());
		beans.put("issues", testReportExport.getIssues());
		beans.put("onlineVerification", testReportExport.getOnlineVerification());
		beans.put("summary", testReportExport.getSummary());
		beans.put("versionDetail", testReportExport.getVersionDetail());
		beans.put("resource", testReportExport.getResource());
		beans.put("cases", testReportExport.getCases());
		beans.put("bugs", testReportExport.getBugs());
		beans.put("bugCritical", testReportExport.getBugCritical());
	//	beans.put("devTag", testReportExport.getDevTag());
	//	beans.put("releaseTag", testReportExport.getReleaseTag());
		beans.put("bugsLink", testReportExport.getBugsLink());
		beans.put("requirementLink", testReportExport.getRequirementLink());

		String filePath = "/exportTemplate/testReportTemplate.xls";

		excelFile = new ExportExcelFromTemplate().makeReportFromTemplet(request.getRealPath("/WEB-INF") + filePath,
				beans);
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		ServletOutputStream out = null;
		try {
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=" + WebConstants.CHARSET);
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(getDownloadFileName() + ".xls", WebConstants.CHARSET));
			out = response.getOutputStream();
			bis = new BufferedInputStream(excelFile);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[1024];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.EXPORT_ERROR, e);
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.flush();
				}
			} catch (IOException e) {
				throw new BusinessException(SystemErrorCodeConstant.EXPORT_ERROR, e);
			}
		}
	}
}
