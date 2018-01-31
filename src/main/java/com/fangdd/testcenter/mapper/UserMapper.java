package com.fangdd.testcenter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fangdd.testcenter.bean.User;

public interface UserMapper {

	public User getUserByUsername(@Param("username") String username);

	public User getUserById(@Param("userId") long userId);

	public int getUserCountByDepartmentId(@Param("departmentId") long departmentId);

	public int getUserCountByTeamId(@Param("teamId") long teamId);

	public int getUserCount(@Param("user") User user);

	public List<User> getUserListByPage(@Param("user") User user, @Param("begin") Integer begin,
			@Param("size") Integer size);

	public int getSelcetUserCount(@Param("user") User user);

	public List<User> getSelcetUserListByPage(@Param("user") User user, @Param("begin") Integer begin,
			@Param("size") Integer size);

	public int addUser(@Param("user") User user);

	public int updateUser(@Param("user") User user);

	public int deleteUser(@Param("userId") long userId);

	public int updateUserPwd(@Param("user") User user);

	public List<User> getUserListByDepartmentId(@Param("departmentId") long departmentId);

	public List<User> getUserListAll();
}
