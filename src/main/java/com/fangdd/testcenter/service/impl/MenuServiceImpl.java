package com.fangdd.testcenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.pagination.Pagination;
import org.pagination.QueryHandler;
import org.pagination.impl.DefaultPagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fangdd.testcenter.bean.Menu;
import com.fangdd.testcenter.common.exception.BusinessException;
import com.fangdd.testcenter.common.util.DynamicBean;
import com.fangdd.testcenter.core.SystemErrorCodeConstant;
import com.fangdd.testcenter.mapper.MenuMapper;
import com.fangdd.testcenter.service.MenuService;

@Service(value = "menuService")
public class MenuServiceImpl implements MenuService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MenuMapper menuMapper;

	public Menu setMenu(Menu menu) {
		if (menu != null) {
			if (menu.getParentId() != 0) {
				Menu parentMenu = getMenuById(menu.getParentId());
				menu.setParentName(parentMenu.getName());
			}
			if (menu.getStatus() == 0) {
				menu.setStatusText("启用");
			} else {
				menu.setStatusText("禁用");
			}
		}
		return menu;
	}

	@Override
	public List<Object> loadMenus(long parentId) {
		List<Object> objects = new ArrayList<Object>();
		List<Menu> menus;
		try {
			if (parentId <= 0) {
				menus = menuMapper.getParentMenuList();
			} else {
				menus = menuMapper.getMenuListByParentId(parentId);
			}
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		for (Menu menu : menus) {
			DynamicBean bean = new DynamicBean();
			bean.addProperty("menuId", Long.class, menu.getMenuId()).addProperty("name", String.class, menu.getName())
					.addProperty("url", String.class, menu.getUrl())
					.addProperty("priority", Integer.class, menu.getPriority());
			boolean flag = getSubMenuCountByParentId(menu.getMenuId()) > 0;
			if (flag) {
				bean.addProperty("expanded", Boolean.class, false).addProperty("isLeaf", Boolean.class, false);
			}
			objects.add(bean.getObject());
		}
		return objects;
	}

	@Override
	public Pagination<Menu> getMenuList(final Menu menu, Integer pageIndex, Integer pageSize) {
		return new DefaultPagination<Menu>(pageIndex, pageSize, new QueryHandler<Menu>() {
			@Override
			public Integer getCount() {
				try {
					return menuMapper.getMenuCount(menu);
				} catch (Exception e) {
					throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
				}
			}

			@Override
			public List<Menu> getData(Integer pageIndex, Integer pageSize) {
				List<Menu> menuListOld = null;
				try {
					menuListOld = menuMapper.getMenuListByPage(menu, pageSize * (pageIndex - 1), pageSize);
				} catch (Exception e) {
					throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
				}
				List<Menu> menuListNew = new ArrayList<Menu>();
				for (Menu menu : menuListOld) {
					menu = setMenu(menu);
					menuListNew.add(menu);
				}
				return menuListNew;
			}
		});
	}

	@Override
	public Menu getMenuById(long menuId) {
		Menu menu = null;
		try {
			menu = menuMapper.getMenuById(menuId);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		menu = setMenu(menu);
		return menu;
	}

	@Override
	public boolean addMenu(Menu menu) {
		try {
			return menuMapper.addMenu(menu) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.ADD_FAILURE, e);
		}
	}

	@Override
	public boolean updateMenu(Menu menu) {
		try {
			return menuMapper.updateMenu(menu) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.UPDATE_FAILURE, e);
		}
	}

	@Override
	public List<Menu> getSelectMenuList() {
		try {
			return menuMapper.getSelectMenuList();
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
	}

	@Override
	public List<Menu> getAllMenuList() {
		List<Menu> menuListOld = null;
		try {
			menuListOld = menuMapper.getAllMenuList();
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		List<Menu> menuListNew = new ArrayList<Menu>();
		for (Menu menu : menuListOld) {
			menu = setMenu(menu);
			menuListNew.add(menu);
		}
		return menuListNew;
	}

	@Override
	public int getSubMenuCountByParentId(long parentMenuId) {
		try {
			return menuMapper.getSubMenuCountByParentId(parentMenuId);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
	}

	@Override
	public int deleteMenu(long menuId) {
		// 0:删除成功 1:存在子菜单，不可删除 2：删除失败
		int flag = 2;
		if (getSubMenuCountByParentId(menuId) > 0) {
			throw new BusinessException(SystemErrorCodeConstant.EXIST_SUB_MENU);
		}
		try {
			if (menuMapper.deleteMenu(menuId) == 1) {
				flag = 0;
			}
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.DELETE_FAILURE, e);
		}
		return flag;
	}

	@Override
	public Menu getMenuByName(String name) {
		Menu menu = null;
		try {
			menu = menuMapper.getMenuByName(name);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return setMenu(menu);
	}
}
