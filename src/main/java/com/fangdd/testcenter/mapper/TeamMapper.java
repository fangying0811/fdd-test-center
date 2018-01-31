package com.fangdd.testcenter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fangdd.testcenter.bean.Team;

public interface TeamMapper {

	public int addTeam(@Param("team") Team team);

	public int updateTeam(@Param("team") Team team);

	public int deleteTeam(@Param("teamId") long teamId);

	public int getTeamCount(@Param("team") Team team);

	public List<Team> getTeamList(@Param("team") Team team, @Param("begin") Integer begin, @Param("size") Integer size);

	public List<Team> getTeamListByDepartmentId(@Param("departmentId") long departmentId);

	public List<Team> getTeamListAll();

	public int getTeamCountByName(@Param("name") String name);

	public Team getTeamById(@Param("teamId") long teamId);

	public int getTeamCountByDepartmentId(@Param("departmentId") long departmentId);
}
