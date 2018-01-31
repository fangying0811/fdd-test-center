package com.fangdd.testcenter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fangdd.testcenter.bean.Department;

public interface DepartmentMapper {

	public List<Department> getDepartmentListByPage(@Param("department") Department department,
			@Param("begin") Integer begin, @Param("size") Integer size);

	public int getDepartmentCount(@Param("department") Department department);

	public List<Department> getDepartmentListAll();

	public int addDepartment(@Param("department") Department department);

	public Department getDepartmentById(@Param("departmentId") long departmentId);

	public Department getDepartmentByName(@Param("name") String name);

	public int updateDepartment(@Param("department") Department department);

	public int deleteDepartment(@Param("departmentId") long departmentId);

}
