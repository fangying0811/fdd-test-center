package com.fangdd.testcenter.service;

import java.util.List;

import org.pagination.Pagination;

import com.fangdd.testcenter.bean.User;
import com.fangdd.testcenter.common.exception.BusinessException;

public interface UserService {

	public User getUserByUsername(String username) throws BusinessException;

	public User getUserById(long userId);

	public Pagination<User> getUserList(User user, Integer pageIndex, Integer pageSize);

	public Pagination<User> getSelcetUserList(User user, Integer pageIndex, Integer pageSize);

	public boolean addUser(User user);

	public boolean updateUser(User user);

	public boolean deleteUser(long userId);

	public boolean updateUserPwd(User user);

	public List<User> getUserListByDepartmentId(long departmentId);

	public int getUserCountByTeamId(long teamId);

	public List<User> getUserListAll();

	public int getUserCountByDepartmentId(long departmentId);
}