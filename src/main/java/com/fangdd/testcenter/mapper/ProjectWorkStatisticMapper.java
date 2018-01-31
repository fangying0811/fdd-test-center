package com.fangdd.testcenter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fangdd.testcenter.bean.ProjectWorkStatistic;

public interface ProjectWorkStatisticMapper {
	public List<ProjectWorkStatistic> getProjecWorkStatisticList(
			@Param("projectWorkStatistic") ProjectWorkStatistic projectWorkStatistic);

	public ProjectWorkStatistic getProjecWorkStatistic(@Param("projectId") long projectId,
			@Param("versionType") int versionType, @Param("versionTypeList") String versionTypeList,
			@Param("startTime") String startTime, @Param("endTime") String endTime);
}