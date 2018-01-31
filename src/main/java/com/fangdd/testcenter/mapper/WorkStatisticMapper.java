package com.fangdd.testcenter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fangdd.testcenter.bean.WorkStatistic;

public interface WorkStatisticMapper {
	public List<WorkStatistic> getTeamWorkStatisticList(@Param("workStatistic") WorkStatistic workStatistic);

	//
	public List<WorkStatistic> getDepartmentWorkStatisticList(@Param("workStatistic") WorkStatistic workStatistic);

	public WorkStatistic getTeamWorkStatistic(@Param("teamId") long teamId, @Param("versionType") int versionType,
			@Param("versionTypeList") String versionTypeList, @Param("startTime") String startTime,
			@Param("endTime") String endTime);
}
