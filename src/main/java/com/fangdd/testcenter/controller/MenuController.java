package com.fangdd.testcenter.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fangdd.testcenter.bean.Menu;
import com.fangdd.testcenter.core.HttpResult;
import com.fangdd.testcenter.core.session.SessionCheck;
import com.fangdd.testcenter.service.MenuService;
import com.fangdd.testcenter.web.util.WebUtil;

@Controller
@RequestMapping(value = "/menu")
public class MenuController extends AbstractController {

	@Autowired
	private MenuService menuService;

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/loadMenus.json")
	public HttpResult loadMenus(@RequestParam(defaultValue = "0") long menuId) {
		return HttpResult.successWithData(menuService.loadMenus(menuId));
	}

	@SessionCheck
	@RequestMapping(value = "/menuListUI")
	public ModelAndView menuListUI() {
		return new ModelAndView("views/menu/menuList");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/menuList.json")
	public HttpResult menuList(Menu menu) {
		return HttpResult.successWithData(menuService.getMenuList(menu, getPageIndex() + 1, getPageSize()));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/selectMenuList.json")
	public HttpResult selectMenuList() {
		return HttpResult.successWithData(menuService.getSelectMenuList());
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/allMenuList.json")
	public HttpResult allMenuList() {
		return HttpResult.successWithData(menuService.getAllMenuList());
	}

	@SessionCheck
	@RequestMapping(value = "/selectMenuUI")
	public ModelAndView selectMenuUI() {
		return new ModelAndView("views/common/selectMenu");
	}

	@SessionCheck
	@RequestMapping(value = "/addMenuUI")
	public ModelAndView addMenuUI() {
		return new ModelAndView("views/menu/addMenu");
	}

	@SessionCheck
	@RequestMapping(value = "/editMenuUI")
	public ModelAndView editMenuUI() {
		return new ModelAndView("views/menu/editMenu");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/getMenuById.json")
	public HttpResult getMenuById(@RequestParam(defaultValue = "0") long menuId) {
		return HttpResult.successWithData(menuService.getMenuById(menuId));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/existMenu.json")
	public HttpResult getMenuById(String name) {
		Menu dbMenu = menuService.getMenuByName(name);
		boolean flag = true;
		if (dbMenu != null && name.equalsIgnoreCase(dbMenu.getName())) {
			flag = false;
		}
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/addMenu.json")
	public HttpResult addMenu(Menu menu) {
		boolean flag = menuService.addMenu(menu);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/editMenu.json")
	public HttpResult editMenu(Menu menu) {
		boolean flag = menuService.updateMenu(menu);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/deleteMenu.json")
	public HttpResult deleteMenu(@RequestParam(defaultValue = "0") long menuId) {
		boolean flag = false;
		// 0:删除成功 1:存在子菜单，不可删除 2：删除失败
		int result = menuService.deleteMenu(menuId);
		if (result == 0) {
			flag = true;
		}
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/statusList.json")
	public HttpResult statusList() {
		List<Map<String, Object>> statusList = new ArrayList<Map<String, Object>>();
		statusList.add(WebUtil.getMap(new String[] { "status", "statusText" }, new Object[] { 0, "启用" }));
		statusList.add(WebUtil.getMap(new String[] { "status", "statusText" }, new Object[] { 1, "禁用" }));
		return HttpResult.successWithData(statusList);
	}
}
