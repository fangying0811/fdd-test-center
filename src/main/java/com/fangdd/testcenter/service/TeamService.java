package com.fangdd.testcenter.service;

import java.util.List;

import com.fangdd.testcenter.bean.PageDataInfo;
import com.fangdd.testcenter.bean.Team;

public interface TeamService {

	public boolean addTeam(Team team);

	public boolean updateTeam(Team team);

	public int deleteTeam(long teamId);

	public int getTeamCount(Team Team);

	public List<Team> getTeamList(Team team, Integer pageIndex, Integer pageSize);

	public PageDataInfo<Team> getTeamListByPage(Team team, Integer pageIndex, Integer pageSize);

	public List<Team> getTeamListByDepartmentId(long departmentId);

	public int getTeamCountByName(String name);

	public Team getTeamById(long teamId);

	public List<Team> getTeamListAll();

	public int getTeamCountByDepartmentId(long departmentId);
}
