package com.fangdd.testcenter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fangdd.testcenter.bean.ProjectInfo;

public interface ProjectMapper {

	public int addProject(@Param("projectInfo") ProjectInfo projectInfo);

	public int updateProject(@Param("projectInfo") ProjectInfo projectInfo);

	public int deleteProject(@Param("projectId") long projectId);

	public int getProjectCount(@Param("projectInfo") ProjectInfo projectInfo);

	public List<ProjectInfo> getProjectList(@Param("projectInfo") ProjectInfo projectInfo,
			@Param("begin") Integer begin, @Param("size") Integer size);

	public List<ProjectInfo> getProjectListByDepartmentId(@Param("departmentId") long departmentId);

	public List<ProjectInfo> getProjectListByTeamId(@Param("teamId") long teamId);

	public int getProjectCountByName(@Param("name") String name);

	public ProjectInfo getProjectById(@Param("projectId") long projectId);
	
	public List<ProjectInfo> getProjectListByTeamIdAndKafka(@Param("teamId1") long teamId);
}
