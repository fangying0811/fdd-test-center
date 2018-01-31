package com.fangdd.testcenter.service.impl;

import java.util.List;

import org.pagination.Pagination;
import org.pagination.QueryHandler;
import org.pagination.impl.DefaultPagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fangdd.testcenter.bean.Department;
import com.fangdd.testcenter.common.exception.BusinessException;
import com.fangdd.testcenter.core.SystemErrorCodeConstant;
import com.fangdd.testcenter.mapper.DepartmentMapper;
import com.fangdd.testcenter.service.DepartmentService;
import com.fangdd.testcenter.service.TeamService;
import com.fangdd.testcenter.service.UserService;

@Service(value = "departmentService")
public class DepartmentServiceImpl implements DepartmentService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DepartmentMapper departmentMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private TeamService teamService;

	@Override
	public Pagination<Department> getDepartmentList(final Department department, Integer pageIndex, Integer pageSize) {
		return new DefaultPagination<Department>(pageIndex, pageSize, new QueryHandler<Department>() {
			@Override
			public Integer getCount() {
				try {
					return departmentMapper.getDepartmentCount(department);
				} catch (Exception e) {
					throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
				}
			}

			@Override
			public List<Department> getData(Integer pageIndex, Integer pageSize) {
				try {
					return departmentMapper.getDepartmentListByPage(department, pageSize * (pageIndex - 1), pageSize);
				} catch (Exception e) {
					throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
				}
			}
		});
	}

	@Override
	public boolean addDepartment(Department department) {
		try {
			return departmentMapper.addDepartment(department) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.ADD_FAILURE, e);
		}
	}

	@Override
	public Department getDepartmentById(long departmentId) {
		try {
			Department department = departmentMapper.getDepartmentById(departmentId);
			return department;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
	}

	@Override
	public boolean updateDepartment(Department department) {
		try {
			return departmentMapper.updateDepartment(department) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.UPDATE_FAILURE, e);
		}
	}

	@Override
	public int deleteDepartment(long departmentId) {
		// 0:删除成功 1:部门存在员工，不可删除 2：部门存在小组，不可删除 3：删除失败
		int flag = 3;

		if (userService.getUserCountByDepartmentId(departmentId) > 0) {
			throw new BusinessException(SystemErrorCodeConstant.DEPARTMENT_EXIST_USER);
		}

		if (teamService.getTeamCountByDepartmentId(departmentId) > 0) {
			throw new BusinessException(SystemErrorCodeConstant.DEPARTMENT_EXIST_TEAM);
		}

		try {
			if (departmentMapper.deleteDepartment(departmentId) == 1) {
				flag = 0;
			}
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.DELETE_FAILURE, e);
		}
		return flag;
	}

	@Override
	public List<Department> getDepartmentListAll() {
		try {
			return departmentMapper.getDepartmentListAll();
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
	}

	@Override
	public Department getDepartmentByName(String name) {
		try {
			return departmentMapper.getDepartmentByName(name);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
	}
}
