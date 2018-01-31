package com.fangdd.testcenter.service;

import java.util.List;

import org.pagination.Pagination;

import com.fangdd.testcenter.bean.Menu;

public interface MenuService {

	public List<Object> loadMenus(long parentId);

	public Pagination<Menu> getMenuList(Menu menu, Integer pageIndex, Integer pageSize);

	public Menu getMenuById(long menuId);

	public Menu getMenuByName(String name);

	public boolean updateMenu(Menu menu);

	public boolean addMenu(Menu menu);

	public List<Menu> getSelectMenuList();

	public List<Menu> getAllMenuList();

	public int deleteMenu(long menuId);

	public int getSubMenuCountByParentId(long parentMenuId);
}
