package com.fangdd.testcenter.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.fangdd.testcenter.bean.EChartLineElement;
import com.fangdd.testcenter.bean.PageDataInfo;
import com.fangdd.testcenter.bean.ProjectWorkStatistic;
import com.fangdd.testcenter.bean.WorkStatistic;

public interface WorkStatisticService {
	/**
	 * 部门小组工作量统计
	 * 
	 * @param workStatistic
	 * @return
	 */
	public PageDataInfo<WorkStatistic> getTeamWorkStatisticList(WorkStatistic workStatistic);

	/**
	 * 部门项目工作量统计
	 * 
	 * @param projectWorkStatistic
	 * @return
	 */
	public PageDataInfo<ProjectWorkStatistic> getProjectWorkStatisticList(ProjectWorkStatistic projectWorkStatistic);

	/**
	 * 部门工作量统计
	 * 
	 * @param workStatistic
	 * @return
	 */
	public PageDataInfo<WorkStatistic> getDepartmentWorkStatisticList(WorkStatistic workStatistic);

	public WorkStatistic getTeamWorkStatistic(long teamId, int versionType, String startTime, String endTime);

	public EChartLineElement getTeamWorkStatisticEChartLine(long teamId, int versionType, int type);

	public JSONObject getTeamWorkStatisticEChartLine(WorkStatistic workStatistic);

	public EChartLineElement getProjectWorkStatisticEChartLine(long projectId, int versionType, int type);

	public JSONObject getProjectWorkStatisticEChartLine(ProjectWorkStatistic projectWorkStatistic);

	public void exportTeamWorkStatistic(WorkStatistic workStatistic, HttpServletRequest request,
			HttpServletResponse response);

	public void exportDepartmentWorkStatistic(WorkStatistic workStatistic, HttpServletRequest request,
			HttpServletResponse response);

	public void exportVersionReleaseList(WorkStatistic workStatistic, HttpServletRequest request,
			HttpServletResponse response);

	public void exportProjectWorkStatistic(ProjectWorkStatistic projectWorkStatistic, HttpServletRequest request,
			HttpServletResponse response);

	public ProjectWorkStatistic getProjectWorkStatistic(long projectId, int versionType, String startTime,
			String endTime);
}
