package com.fangdd.testcenter.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fangdd.testcenter.bean.PageDataInfo;
import com.fangdd.testcenter.bean.Team;
import com.fangdd.testcenter.common.exception.BusinessException;
import com.fangdd.testcenter.core.SystemErrorCodeConstant;
import com.fangdd.testcenter.mapper.TeamMapper;
import com.fangdd.testcenter.service.TeamService;
import com.fangdd.testcenter.service.UserService;

@Service(value = "teamService")
public class TeamServiceImpl implements TeamService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TeamMapper teamMapper;
	@Autowired
	private UserService userService;

	@Override
	public boolean addTeam(Team team) {
		try {
			return teamMapper.addTeam(team) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.ADD_FAILURE, e);
		}
	}

	@Override
	public boolean updateTeam(Team team) {
		try {
			return teamMapper.updateTeam(team) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.UPDATE_FAILURE, e);
		}
	}

	@Override
	public int deleteTeam(long teamId) {
		// 0:删除成功 1:小组存在员工，不可删除 2：删除失败
		int flag = 2;

		if (userService.getUserCountByTeamId(teamId) > 0) {
			throw new BusinessException(SystemErrorCodeConstant.TEAM_EXIST_USER);
		}

		try {
			if (teamMapper.deleteTeam(teamId) == 1) {
				flag = 0;
			}
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.DELETE_FAILURE, e);
		}
		return flag;
	}

	@Override
	public int getTeamCount(Team team) {
		int count = 0;
		try {
			count = teamMapper.getTeamCount(team);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return count;
	}

	@Override
	public List<Team> getTeamList(Team team, Integer pageIndex, Integer pageSize) {
		try {
			List<Team> TeamList = teamMapper.getTeamList(team, pageSize * (pageIndex - 1), pageSize);
			return TeamList;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
	}

	@Override
	public PageDataInfo<Team> getTeamListByPage(Team team, Integer pageIndex, Integer pageSize) {
		/**
		 * 获取记录总数
		 */
		int totalElements = getTeamCount(team);
		/**
		 * 纠正pageIndex值，防止出现查询第0页或查询页数超过总页数情况
		 */
		PageDataInfo<Team> pageDataInfoTemp = new PageDataInfo<Team>(pageIndex, pageSize, totalElements);
		/**
		 * 获取当前页列表记录
		 */
		List<Team> pageList = getTeamList(team, pageDataInfoTemp.getPageIndex(), pageDataInfoTemp.getPageSize());
		/**
		 * 构造PageDataInfo对象
		 */
		PageDataInfo<Team> pageDataInfo = new PageDataInfo<Team>(pageDataInfoTemp.getPageIndex(),
				pageDataInfoTemp.getPageSize(), totalElements, pageList);
		return pageDataInfo;

	}

	@Override
	public List<Team> getTeamListByDepartmentId(long departmentId) {
		try {
			List<Team> TeamList = teamMapper.getTeamListByDepartmentId(departmentId);
			return TeamList;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
	}

	@Override
	public List<Team> getTeamListAll() {
		try {
			List<Team> TeamList = teamMapper.getTeamListAll();
			return TeamList;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
	}

	@Override
	public int getTeamCountByName(String name) {
		int count = 0;
		try {
			count = teamMapper.getTeamCountByName(name);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return count;
	}

	@Override
	public Team getTeamById(long teamId) {
		try {
			Team team = teamMapper.getTeamById(teamId);
			return team;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
	}

	@Override
	public int getTeamCountByDepartmentId(long departmentId) {
		int count = 0;
		try {
			count = teamMapper.getTeamCountByDepartmentId(departmentId);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return count;
	}
}
