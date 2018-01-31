package com.fangdd.testcenter.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fangdd.qa.framework.utils.common.ArithUtil;
import com.fangdd.qa.framework.utils.common.CommonUtil;
import com.fangdd.qa.framework.utils.common.DateTimeUtil;
import com.fangdd.qa.framework.utils.common.FileUtil;
import com.fangdd.qa.framework.utils.common.RandomUtil;
import com.fangdd.testcenter.bean.Department;
import com.fangdd.testcenter.bean.EChartLineElement;
import com.fangdd.testcenter.bean.PageDataInfo;
import com.fangdd.testcenter.bean.ProjectInfo;
import com.fangdd.testcenter.bean.WeeklyReport;
import com.fangdd.testcenter.common.exception.BusinessException;
import com.fangdd.testcenter.common.util.ConfigReader;
import com.fangdd.testcenter.common.util.ExportExcel;
import com.fangdd.testcenter.common.util.ListSortUtil;
import com.fangdd.testcenter.common.util.StringUtil;
import com.fangdd.testcenter.core.SystemErrorCodeConstant;
import com.fangdd.testcenter.enums.TestProcessStatusEnum;
import com.fangdd.testcenter.enums.VersionTypeEnum;
import com.fangdd.testcenter.mapper.WeeklyReportMapper;
import com.fangdd.testcenter.service.DepartmentService;
import com.fangdd.testcenter.service.ProjectService;
import com.fangdd.testcenter.service.WeeklyReportService;
import com.fangdd.testcenter.web.util.WebConstants;

@Service(value = "weeklyReportService")
public class WeeklyReportServiceImpl implements WeeklyReportService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WeeklyReportMapper weeklyReportMapper;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private DepartmentService departmentService;
	
	ProjectInfo project = null;
	
	private InputStream excelFile;
	
	private String downloadFileName;

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public InputStream getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(InputStream excelFile) {
		this.excelFile = excelFile;
	}

	@Override
	public boolean addWeeklyReport(WeeklyReport weeklyReport) {
		try {
			return weeklyReportMapper.addWeeklyReport(weeklyReport) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.ADD_FAILURE, e);
		}
	}

	@Override
	public boolean updateWeeklyReport(WeeklyReport weeklyReport) {
		try {
			return weeklyReportMapper.updateWeeklyReport(weeklyReport) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.UPDATE_FAILURE,
					e);
		}
	}

	@Override
	public boolean deleteWeeklyReport(long weeklyReportId) {
		try {
			return weeklyReportMapper.deleteWeeklyReport(weeklyReportId) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.DELETE_FAILURE,
					e);
		}
	}

	/**
	 * 根据数据库返回对应属性值重置weeklyReport属性值(UI展示需要)
	 * 
	 * @param weeklyReport
	 * @return
	 */
	public WeeklyReport setWeeklyReport(WeeklyReport weeklyReport) {
		if (weeklyReport != null) {
			if (CommonUtil.isEmpty(weeklyReport.getVersionTestTime())) {
				weeklyReport.setVersionTestTime("待定");
			} else {
				weeklyReport.setVersionTestTime(DateTimeUtil.formatedTime(
						"yyyy-MM-dd", weeklyReport.getVersionTestTime()));
			}

			if (weeklyReport.getTestTime() <= 0) {
				weeklyReport.setTestTimeText("待定");
			} else {
				weeklyReport.setTestTimeText(weeklyReport.getTestTime() + "");
			}
			weeklyReport.setTestStatusText(TestProcessStatusEnum.findByValue(
					weeklyReport.getTestStatus()).getDescription());
			weeklyReport.setTypeText(VersionTypeEnum.findByValue(
					weeklyReport.getType()).getDescription());
			if (CommonUtil.isEmpty(weeklyReport.getVersionReleaseTime())) {
				weeklyReport.setVersionReleaseTime("待定");
			} else {
				weeklyReport.setVersionReleaseTime(DateTimeUtil.formatedTime(
						"yyyy-MM-dd", weeklyReport.getVersionReleaseTime()));
			}
			if (!CommonUtil.isEmpty(weeklyReport.getVersionInfo())) {
				weeklyReport.setVersionInfoDetail(StringUtil
						.inTextArea(weeklyReport.getVersionInfo()));
			}

			if (!CommonUtil.isEmpty(weeklyReport.getRemark())) {
				weeklyReport.setRemarkDetail(StringUtil.inTextArea(weeklyReport
						.getRemark()));
			}
			int developer = 0;
			int tester = 0;
			if (weeklyReport.getProject() != null) {
				project = projectService.getProjectById(weeklyReport
						.getProject().getProjectId());
				if (project != null) {
					developer = project.getDeveloper();
					tester = project.getTester();
				}

			}

			weeklyReport.setTester(tester);
			weeklyReport.setDeveloper(developer);
			if (developer == 0) {
				weeklyReport.setPerBugs(0);
			} else {
				weeklyReport.setPerBugs(ArithUtil.div(
						weeklyReport.getBugNumber(), developer, 1));
			}

			weeklyReport.setCreateTime(DateTimeUtil.formatedTime(
					"yyyy-MM-dd HH:mm:ss", weeklyReport.getCreateTime()));
			weeklyReport.setUpdateTime(DateTimeUtil.formatedTime(
					"yyyy-MM-dd HH:mm:ss", weeklyReport.getUpdateTime()));
		}
		return weeklyReport;
	}

	@Override
	public int getWeeklyReportCount(WeeklyReport weeklyReport) {
		int count = 0;
		try {
			count = weeklyReportMapper.getWeeklyReportCount(weeklyReport);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE,
					e);
		}
		return count;
	}

	@Override
	public List<WeeklyReport> getWeeklyReportList(WeeklyReport weeklyReport,
			Integer pageIndex, Integer pageSize) {
		List<WeeklyReport> weeklyReportListOld = null;
		try {
			weeklyReportListOld = weeklyReportMapper.getWeeklyReportList(
					weeklyReport, pageSize * (pageIndex - 1), pageSize);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE,
					e);
		}
		List<WeeklyReport> weeklyReportListNew = new ArrayList<WeeklyReport>();
		for (WeeklyReport weeklyReportNew : weeklyReportListOld) {
			weeklyReportNew = setWeeklyReport(weeklyReportNew);
			weeklyReportListNew.add(weeklyReportNew);
		}
		return weeklyReportListNew;
	}

	@Override
	public List<WeeklyReport> getWeeklyReportStatisticList(
			WeeklyReport weeklyReport, Integer pageIndex, Integer pageSize) {
		List<WeeklyReport> weeklyReportListOld = null;
		try {
			weeklyReportListOld = weeklyReportMapper
					.getWeeklyReportStatisticList(weeklyReport, pageSize
							* (pageIndex - 1), pageSize);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE,
					e);
		}
		List<WeeklyReport> weeklyReportListNew = new ArrayList<WeeklyReport>();
		for (WeeklyReport weeklyReportNew : weeklyReportListOld) {
			weeklyReportNew = setWeeklyReport(weeklyReportNew);
			weeklyReportListNew.add(weeklyReportNew);
		}
		return weeklyReportListNew;
	}

	@Override
	public PageDataInfo<WeeklyReport> getWeeklyReportListByPage(
			WeeklyReport weeklyReport, Integer pageIndex, Integer pageSize) {
		/**
		 * 获取记录总数
		 */
		int totalElements = getWeeklyReportCount(weeklyReport);
		/**
		 * 纠正pageIndex值，防止出现查询第0页或查询页数超过总页数情况
		 */
		PageDataInfo<WeeklyReport> pageDataInfoTemp = new PageDataInfo<WeeklyReport>(
				pageIndex, pageSize, totalElements);
		/**
		 * 获取当前页列表记录
		 */
		List<WeeklyReport> pageList = getWeeklyReportList(weeklyReport,
				pageDataInfoTemp.getPageIndex(), pageDataInfoTemp.getPageSize());
		/**
		 * 构造PageDataInfo对象
		 */
		PageDataInfo<WeeklyReport> pageDataInfo = new PageDataInfo<WeeklyReport>(
				pageDataInfoTemp.getPageIndex(),
				pageDataInfoTemp.getPageSize(), totalElements, pageList);
		return pageDataInfo;
	}

	@Override
	public PageDataInfo<WeeklyReport> getWeeklyReportStatisticListByPage(
			WeeklyReport weeklyReport, Integer pageIndex, Integer pageSize) {
		/**
		 * 获取记录总数
		 */
		int totalElements = getWeeklyReportCount(weeklyReport);
		/**
		 * 纠正pageIndex值，防止出现查询第0页或查询页数超过总页数情况
		 */
		PageDataInfo<WeeklyReport> pageDataInfoTemp = new PageDataInfo<WeeklyReport>(
				pageIndex, pageSize, totalElements);
		/**
		 * 获取当前页列表记录
		 */
		List<WeeklyReport> pageList = getWeeklyReportStatisticList(
				weeklyReport, pageDataInfoTemp.getPageIndex(),
				pageDataInfoTemp.getPageSize());
		/**
		 * 构造PageDataInfo对象
		 */
		PageDataInfo<WeeklyReport> pageDataInfo = new PageDataInfo<WeeklyReport>(
				pageDataInfoTemp.getPageIndex(),
				pageDataInfoTemp.getPageSize(), totalElements, pageList);
		return pageDataInfo;
	}

	@Override
	public WeeklyReport getWeeklyReportById(long weeklyReportId) {
		WeeklyReport weeklyReport = null;
		try {
			weeklyReport = weeklyReportMapper
					.getWeeklyReportById(weeklyReportId);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE,
					e);
		}
		return setWeeklyReport(weeklyReport);
	}

	@Override
	public List<WeeklyReport> getWeeklyReportExportList(
			WeeklyReport weeklyReport) {
		List<WeeklyReport> weeklyReportListOld = null;
		try {
			weeklyReportListOld = weeklyReportMapper
					.getWeeklyReportExportList(weeklyReport);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE,
					e);
		}
		List<WeeklyReport> weeklyReportListNew = new ArrayList<WeeklyReport>();
		for (WeeklyReport weeklyReportNew : weeklyReportListOld) {
			weeklyReportNew = setWeeklyReport(weeklyReportNew);
			weeklyReportListNew.add(weeklyReportNew);
		}
		return weeklyReportListNew;
	}

	@Override
	public void exportWeeklyReport(WeeklyReport weeklyReport,
			HttpServletRequest request, HttpServletResponse response) {
		Department department = departmentService
				.getDepartmentById(weeklyReport.getDepartment()
						.getDepartmentId());
		String title;
		if (DateTimeUtil.getIntervalDays(weeklyReport.getEndTime(),
				weeklyReport.getStartTime(), "yyyy-MM-dd") >= 30) {
			setDownloadFileName(department.getName()
					+ weeklyReport.getStartTime() + "至"
					+ weeklyReport.getEndTime() + "-质量月报");
			title = department.getName() + weeklyReport.getStartTime() + "至"
					+ weeklyReport.getEndTime() + "-质量月报";
		} else {
			setDownloadFileName(department.getName()
					+ weeklyReport.getStartTime() + "至"
					+ weeklyReport.getEndTime() + "-周报");
			title = department.getName() + weeklyReport.getStartTime() + "至"
					+ weeklyReport.getEndTime() + "-周报";
		}
		logger.info(getDownloadFileName());
		String[] rowName = new String[] { "小组", "项目名称", "版本信息", "类型", "责任人",
				"版本转测", "测试时间(天)", "版本用例总数", "版本BUG总数", "进度", "版本发布", "备注" };
		List<Object[]> dataList = new ArrayList<Object[]>();
		weeklyReport.setStartTime(DateTimeUtil.convertDate(
				weeklyReport.getStartTime(), true));
		weeklyReport.setEndTime(DateTimeUtil.convertDate(
				weeklyReport.getEndTime(), false));
		List<WeeklyReport> weeklyReportList = getWeeklyReportExportList(weeklyReport);
		for (WeeklyReport weeklyReportTmp : weeklyReportList) {
			Object[] objects = new Object[rowName.length];
			if (weeklyReportTmp.getTeam() != null) {
				objects[0] = weeklyReportTmp.getTeam().getName();
			} else {
				objects[0] = "";
			}
			if (weeklyReportTmp.getProject() != null) {
				objects[1] = weeklyReportTmp.getProject().getProjectName();
			} else {
				objects[1] = "";
			}
			objects[2] = weeklyReportTmp.getVersionInfoDetail();
			objects[3] = weeklyReportTmp.getTypeText();
			objects[4] = weeklyReportTmp.getResource();
			objects[5] = weeklyReportTmp.getVersionTestTime();
			objects[6] = weeklyReportTmp.getTestTimeText();
			objects[7] = weeklyReportTmp.getCaseNumber();
			objects[8] = weeklyReportTmp.getBugNumber();
			objects[9] = weeklyReportTmp.getTestStatusText();
			objects[10] = weeklyReportTmp.getVersionReleaseTime();
			if (weeklyReportTmp.getRemarkDetail() != null) {
				objects[11] = weeklyReportTmp.getRemarkDetail();
			} else {
				objects[11] = "";
			}
			dataList.add(objects);
		}
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		ServletOutputStream out = null;
		ByteArrayOutputStream output = null;
		try {
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset="
					+ WebConstants.CHARSET);
			response.setHeader(
					"Content-Disposition",
					"attachment;filename="
							+ URLEncoder.encode(getDownloadFileName() + ".xls",
									WebConstants.CHARSET));
			out = response.getOutputStream();
			output = new ByteArrayOutputStream();
			ExportExcel exportExcel = new ExportExcel(title, rowName, dataList);
			HSSFWorkbook workbook = exportExcel.export();
			workbook.write(output);
			byte[] buf = output.toByteArray();
			excelFile = new ByteArrayInputStream(buf);
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
				if (output != null) {
					output.close();
				}
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.flush();
				}
			} catch (IOException e) {
				throw new BusinessException(
						SystemErrorCodeConstant.EXPORT_ERROR, e);
			}
		}
	}

	@Override
	public JSONObject getVersionReleaseLineChart(WeeklyReport weeklyReport,
			int statisticType) {
		List<WeeklyReport> weeklyReports = getWeeklyReportStatisticList(
				weeklyReport, 1, 6);
		ListSortUtil<WeeklyReport> listSortUtil = new ListSortUtil<WeeklyReport>();
		listSortUtil.sort(weeklyReports, "versionReleaseTime", "asc");
		List<EChartLineElement> eChartLineElements = new ArrayList<EChartLineElement>();
		EChartLineElement eChartLineElement = new EChartLineElement();
		List<Integer> dataList = new ArrayList<Integer>();
		List<String> labelList = new ArrayList<String>();
		List<String> legendList = new ArrayList<String>();
		eChartLineElement.setName(projectService.getProjectById(
				weeklyReport.getProject().getProjectId()).getProjectName());
		legendList.add(eChartLineElement.getName());
		String title = "";
		// 统计类型 1:BUG 2:测试用例
		switch (statisticType) {
		case 1:
			title = "项目版本BUG趋势图";
			for (WeeklyReport weeklyReportTmp : weeklyReports) {
				dataList.add(weeklyReportTmp.getBugNumber());
				labelList.add(weeklyReportTmp.getVersionInfo());
			}
			break;
		case 2:
			title = "项目版本测试用例趋势图";
			for (WeeklyReport weeklyReportTmp : weeklyReports) {
				dataList.add(weeklyReportTmp.getCaseNumber());
				labelList.add(weeklyReportTmp.getVersionInfo());
			}
			break;
		default:
			title = "项目BUG趋势图";
			for (WeeklyReport weeklyReportTmp : weeklyReports) {
				dataList.add(weeklyReportTmp.getBugNumber());
				labelList.add(weeklyReportTmp.getVersionInfo());
			}
			break;
		}
		eChartLineElement.setData(dataList);
		eChartLineElements.add(eChartLineElement);
		String legend = JSONObject.toJSONString(legendList);
		String xAxisData = JSONObject.toJSONString(labelList);
		String series = JSONObject.toJSONString(eChartLineElements);
		List<String> colorList = new ArrayList<String>();
		colorList.add(ConfigReader.COLORS[RandomUtil.getRandomNumber(0,
				ConfigReader.COLORS.length)]);
		String color = JSONObject.toJSONString(colorList);

		// 统计类型 1:BUG 2:测试用例 3：线上问题 4：遗留问题 5：发布版本
		switch (statisticType) {
		case 1:
			title = "项目BUG趋势图";
			break;
		case 2:
			title = "项目测试用例趋势图";
			break;
		case 3:
			title = "项目线上问题趋势图";
			break;
		case 4:
			title = "项目遗留问题趋势图";
			break;
		case 5:
			title = "项目发布版本趋势图";
			break;
		default:
			title = "项目BUG趋势图";
			break;
		}
		String lineReport = FileUtil.getContent("config",
				"echarts-line-report.txt");
		return JSONObject
				.parseObject(String
						.format(lineReport,
								new Object[] { title, legend, xAxisData,
										series, color }).replace("\r\n", "")
						.replace("\n", "").replace("\t", ""));
	}
}
