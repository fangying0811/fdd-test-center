package com.fangdd.testcenter.service;

import java.util.List;

import org.pagination.Pagination;

import com.fangdd.testcenter.bean.Department;

public interface DepartmentService {

	public Pagination<Department> getDepartmentList(Department department, Integer pageIndex, Integer pageSize);

	public List<Department> getDepartmentListAll();

	public boolean addDepartment(Department department);

	public Department getDepartmentById(long departmentId);

	public Department getDepartmentByName(String name);

	public boolean updateDepartment(Department department);

	public int deleteDepartment(long departmentId);
}
