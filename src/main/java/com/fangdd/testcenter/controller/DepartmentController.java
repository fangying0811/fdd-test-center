package com.fangdd.testcenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fangdd.testcenter.bean.Department;
import com.fangdd.testcenter.core.HttpResult;
import com.fangdd.testcenter.core.session.SessionCheck;
import com.fangdd.testcenter.service.DepartmentService;

@Controller
@RequestMapping(value = "/department")
public class DepartmentController extends AbstractController {

	@Autowired
	private DepartmentService departmentService;

	@SessionCheck
	@RequestMapping(value = "/departmentListUI")
	public ModelAndView departmentListUI() {
		return new ModelAndView("views/department/departmentList");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/departmentList.json")
	public HttpResult departmentList(Department department) {
		return HttpResult
				.successWithData(departmentService.getDepartmentList(department, getPageIndex() + 1, getPageSize()));
	}

	@ResponseBody
	@RequestMapping(value = "/departmentListAll.json")
	public HttpResult departmentListAll() {
		return HttpResult.successWithData(departmentService.getDepartmentListAll());
	}

	@SessionCheck
	@RequestMapping(value = "/addDepartmentUI")
	public ModelAndView addDepartmentUI() {
		return new ModelAndView("views/department/addDepartment");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/addDepartment.json")
	public HttpResult addDepartment(Department department) {
		boolean flag = departmentService.addDepartment(department);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@RequestMapping(value = "/editDepartmentUI")
	public ModelAndView editDepartmentUI() {
		return new ModelAndView("views/department/editDepartment");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/editDepartment.json")
	public HttpResult editDepartment(Department department) {
		boolean flag = departmentService.updateDepartment(department);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/getDepartmentById.json")
	public HttpResult getDepartmentById(@RequestParam(defaultValue = "0") long departmentId) {
		return HttpResult.successWithData(departmentService.getDepartmentById(departmentId));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/deleteDepartment.json")
	public HttpResult deleteDepartment(@RequestParam(defaultValue = "0") long departmentId) {
		boolean flag = false;
		// 0:删除成功 1:部门存在员工，不可删除 2：部门存在小组，不可删除 3：删除失败
		int result = departmentService.deleteDepartment(departmentId);
		if (result == 0) {
			flag = true;
		}
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/existDepartment.json")
	public HttpResult existDepartment(@RequestParam String name) {
		Department dbDepartment = departmentService.getDepartmentByName(name);
		boolean flag = true;
		if (dbDepartment != null && name.equalsIgnoreCase(dbDepartment.getName())) {
			flag = false;
		}
		return HttpResult.successWithData(flag);
	}
}
