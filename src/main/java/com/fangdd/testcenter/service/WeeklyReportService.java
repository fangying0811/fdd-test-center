package com.fangdd.testcenter.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.fangdd.testcenter.bean.PageDataInfo;
import com.fangdd.testcenter.bean.WeeklyReport;

public interface WeeklyReportService {

	public boolean addWeeklyReport(WeeklyReport weeklyReport);

	public boolean updateWeeklyReport(WeeklyReport weeklyReport);

	public boolean deleteWeeklyReport(long weeklyReportId);

	public int getWeeklyReportCount(WeeklyReport weeklyReport);

	public List<WeeklyReport> getWeeklyReportList(WeeklyReport weeklyReport, Integer begin, Integer size);

	public List<WeeklyReport> getWeeklyReportStatisticList(WeeklyReport weeklyReport, Integer begin, Integer size);

	public PageDataInfo<WeeklyReport> getWeeklyReportListByPage(WeeklyReport weeklyReport, Integer pageIndex,
			Integer pageSize);

	public PageDataInfo<WeeklyReport> getWeeklyReportStatisticListByPage(WeeklyReport weeklyReport, Integer pageIndex,
			Integer pageSize);

	public WeeklyReport getWeeklyReportById(long weeklyReportId);

	public List<WeeklyReport> getWeeklyReportExportList(WeeklyReport weeklyReport);

	public JSONObject getVersionReleaseLineChart(WeeklyReport weeklyReport, int statisticType);

	public void exportWeeklyReport(WeeklyReport weeklyReport, HttpServletRequest request, HttpServletResponse response);
}