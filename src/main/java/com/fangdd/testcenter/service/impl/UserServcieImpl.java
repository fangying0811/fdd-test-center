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

import com.fangdd.testcenter.bean.User;
import com.fangdd.testcenter.common.exception.BusinessException;
import com.fangdd.testcenter.core.SystemErrorCodeConstant;
import com.fangdd.testcenter.mapper.UserMapper;
import com.fangdd.testcenter.service.UserService;

@Service(value = "userService")
public class UserServcieImpl implements UserService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserMapper userMapper;

	public User setUser(User user) {
		if (user != null) {
			if (user.getIsAdmin() == 0) {
				user.setIsAdminText("系统管理员");
			} else if (user.getIsAdmin() == 1) {
				user.setIsAdminText("部门管理员");
			} else if (user.getIsAdmin() == 2) {
				user.setIsAdminText("小组管理员");
			} else {
				user.setIsAdminText("普通用户");
			}
			if (user.getStatus() == 0) {
				user.setStatusText("启用");
			} else {
				user.setStatusText("禁用");
			}
		}
		return user;
	}

	@Override
	public User getUserByUsername(String username) {
		User user = null;
		try {
			user = userMapper.getUserByUsername(username);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		user = setUser(user);
		return user;
	}

	@Override
	public User getUserById(long userId) {
		User user = null;
		try {
			user = userMapper.getUserById(userId);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		user = setUser(user);
		return user;
	}

	@Override
	public Pagination<User> getUserList(final User user, Integer pageIndex, Integer pageSize) {
		return new DefaultPagination<User>(pageIndex, pageSize, new QueryHandler<User>() {
			@Override
			public Integer getCount() {
				try {
					return userMapper.getUserCount(user);
				} catch (Exception e) {
					throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
				}
			}

			@Override
			public List<User> getData(Integer pageIndex, Integer pageSize) {
				List<User> userListOld = null;
				try {
					userListOld = userMapper.getUserListByPage(user, pageSize * (pageIndex - 1), pageSize);
				} catch (Exception e) {
					throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
				}
				List<User> userListNew = new ArrayList<User>();
				for (User user : userListOld) {
					user = setUser(user);
					userListNew.add(user);
				}
				return userListNew;
			}
		});

	}

	@Override
	public Pagination<User> getSelcetUserList(final User user, Integer pageIndex, Integer pageSize) {
		return new DefaultPagination<User>(pageIndex, pageSize, new QueryHandler<User>() {
			@Override
			public Integer getCount() {
				try {
					return userMapper.getSelcetUserCount(user);
				} catch (Exception e) {
					throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
				}
			}

			@Override
			public List<User> getData(Integer pageIndex, Integer pageSize) {
				List<User> userListOld = null;
				try {
					userListOld = userMapper.getSelcetUserListByPage(user, pageSize * (pageIndex - 1), pageSize);
				} catch (Exception e) {
					throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
				}
				List<User> userListNew = new ArrayList<User>();
				for (User user : userListOld) {
					user = setUser(user);
					userListNew.add(user);
				}
				return userListNew;
			}
		});
	}

	@Override
	public boolean addUser(User user) {
		try {
			return userMapper.addUser(user) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.ADD_FAILURE, e);
		}
	}

	@Override
	public boolean updateUser(User user) {
		try {
			return userMapper.updateUser(user) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.UPDATE_FAILURE, e);
		}
	}

	@Override
	public boolean deleteUser(long userId) {
		try {
			return userMapper.deleteUser(userId) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.DELETE_FAILURE, e);
		}
	}

	@Override
	public boolean updateUserPwd(User user) {
		try {
			return userMapper.updateUserPwd(user) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.UPDATE_FAILURE, e);
		}
	}

	@Override
	public List<User> getUserListByDepartmentId(long departmentId) {
		List<User> userListOld = null;
		try {
			userListOld = userMapper.getUserListByDepartmentId(departmentId);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		List<User> userListNew = new ArrayList<User>();
		for (User user : userListOld) {
			user = setUser(user);
			userListNew.add(user);
		}
		return userListNew;
	}

	@Override
	public int getUserCountByTeamId(long teamId) {
		try {
			return userMapper.getUserCountByTeamId(teamId);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
	}

	@Override
	public List<User> getUserListAll() {
		List<User> userListOld = null;
		try {
			userListOld = userMapper.getUserListAll();
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		List<User> userListNew = new ArrayList<User>();
		for (User user : userListOld) {
			user = setUser(user);
			userListNew.add(user);
		}
		return userListNew;
	}

	@Override
	public int getUserCountByDepartmentId(long departmentId) {
		try {
			return userMapper.getUserCountByDepartmentId(departmentId);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
	}
}
