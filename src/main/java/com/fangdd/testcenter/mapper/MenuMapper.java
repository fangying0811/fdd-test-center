package com.fangdd.testcenter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fangdd.testcenter.bean.Menu;

public interface MenuMapper {

	public List<Menu> getParentMenuList();

	public List<Menu> getMenuListByParentId(@Param("parentId") long parentId);

	public int getSubMenuCountByParentId(@Param("parentId") long parentId);

	public List<Menu> getMenuListByPage(@Param("menu") Menu menu, @Param("begin") Integer begin,
			@Param("size") Integer size);

	public int getMenuCount(@Param("menu") Menu menu);

	public Menu getMenuById(@Param("menuId") long menuId);

	public Menu getMenuByName(@Param("name") String name);

	public int updateMenu(@Param("menu") Menu menu);

	public int addMenu(@Param("menu") Menu menu);

	public List<Menu> getSelectMenuList();

	public List<Menu> getAllMenuList();

	public int deleteMenu(@Param("menuId") long menuId);
}
