package com.fangdd.testcenter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fangdd.testcenter.bean.WeeklyReport;

public interface WeeklyReportMapper {
	public int addWeeklyReport(@Param("weeklyReport") WeeklyReport weeklyReport);

	public int updateWeeklyReport(@Param("weeklyReport") WeeklyReport weeklyReport);

	public int deleteWeeklyReport(@Param("weeklyReportId") long weeklyReportId);

	public int getWeeklyReportCount(@Param("weeklyReport") WeeklyReport weeklyReport);

	public List<WeeklyReport> getWeeklyReportList(@Param("weeklyReport") WeeklyReport weeklyReport,
			@Param("begin") Integer begin, @Param("size") Integer size);

	public List<WeeklyReport> getWeeklyReportStatisticList(@Param("weeklyReport") WeeklyReport weeklyReport,
			@Param("begin") Integer begin, @Param("size") Integer size);

	public WeeklyReport getWeeklyReportById(@Param("weeklyReportId") long weeklyReportId);

	public List<WeeklyReport> getWeeklyReportExportList(@Param("weeklyReport") WeeklyReport weeklyReport);
}
