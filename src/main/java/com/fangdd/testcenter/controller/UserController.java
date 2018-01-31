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

import com.fangdd.qa.framework.utils.common.CommonUtil;
import com.fangdd.qa.framework.utils.common.Md5Tool;
import com.fangdd.testcenter.bean.User;
import com.fangdd.testcenter.common.exception.BusinessException;
import com.fangdd.testcenter.core.HttpResult;
import com.fangdd.testcenter.core.SystemErrorCodeConstant;
import com.fangdd.testcenter.core.session.SessionCheck;
import com.fangdd.testcenter.service.UserService;
import com.fangdd.testcenter.web.util.WebConstants;
import com.fangdd.testcenter.web.util.WebUtil;

@Controller
@RequestMapping(value = "/user")
public class UserController extends AbstractController {
	@Autowired
	private UserService userService;

	// 导航到登录页面
	@RequestMapping(value = "/loginUI")
	public ModelAndView loginUI() {
		return new ModelAndView("views/login");
	}

	@ResponseBody
	@RequestMapping(value = "/login.json")
	public HttpResult<User> login(User user) {
		User loginUser = userService.getUserByUsername(user.getUsername());
		if (loginUser == null) {
			throw new BusinessException(SystemErrorCodeConstant.USER_NOT_EXIST);
		} else {
			if (loginUser.getPassword().equals(Md5Tool.encode(user.getPassword(), WebConstants.CHARSET))) {
				if (loginUser.getStatus() == 0) {
					loginUser = userService.getUserById(loginUser.getUserId());
					setSession(WebConstants.LOGIN_USER, loginUser);
				} else {
					throw new BusinessException(SystemErrorCodeConstant.USER_LOCKED);
				}
			} else {
				throw new BusinessException(SystemErrorCodeConstant.USER_PASSWORD_ERROR);
			}
		}
		return new HttpResult<User>(200, "登录成功，即刻转入系统，请稍候...", loginUser);
	}

	// 导航到系统主页面
	@SessionCheck
	@RequestMapping(value = "/mainUI")
	public ModelAndView mainUI() {
		return new ModelAndView("views/main");
	}

	// 导航到系统欢迎页面
	@RequestMapping(value = "/welcomeUI")
	public ModelAndView welcomeUI() {
		return new ModelAndView("views/welcome");
	}

	@RequestMapping(value = "/logoutUI")
	public ModelAndView logout() {
		removeSession(WebConstants.LOGIN_USER);
		return new ModelAndView("views/login");
	}

	@SessionCheck
	@RequestMapping(value = "/userListUI")
	public ModelAndView userListUI() {
		return new ModelAndView("views/user/userList");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/userList.json")
	public HttpResult userList(User user) {
		logger.info("PageIndex->{},PageSize->{}", new Object[] { getPageIndex(), getPageSize() });
		return HttpResult.successWithData(userService.getUserList(user, getPageIndex() + 1, getPageSize()));
	}

	@SessionCheck
	@RequestMapping(value = "/selectUserUI")
	public ModelAndView selectUserUI() {
		return new ModelAndView("views/common/selectUser");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/selectUserList.json")
	public HttpResult selectUserList(User user) {
		logger.info("PageIndex->{},PageSize->{}", new Object[] { getPageIndex(), getPageSize() });
		return HttpResult.successWithData(userService.getSelcetUserList(user, getPageIndex() + 1, getPageSize()));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/isAdminList.json")
	public HttpResult isAdminList() {
		List<Map<String, Object>> isadminList = new ArrayList<Map<String, Object>>();
		isadminList.add(WebUtil.getMap(new String[] { "isAdmin", "isAdminText" }, new Object[] { "0", "系统管理员" }));
		isadminList.add(WebUtil.getMap(new String[] { "isAdmin", "isAdminText" }, new Object[] { "1", "部门管理员" }));
		isadminList.add(WebUtil.getMap(new String[] { "isAdmin", "isAdminText" }, new Object[] { "2", "小组管理员" }));
		isadminList.add(WebUtil.getMap(new String[] { "isAdmin", "isAdminText" }, new Object[] { "3", "普通用户" }));
		return HttpResult.successWithData(isadminList);
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

	@ResponseBody
	@RequestMapping(value = "/existUser.json")
	public HttpResult existUser(@RequestParam String username) {
		User dbUser = userService.getUserByUsername(username);
		boolean flag = true;
		if (dbUser != null && username.equalsIgnoreCase(dbUser.getUsername())) {
			flag = false;
		}
		return HttpResult.successWithData(flag);
	}

	@RequestMapping(value = "/registerUserUI")
	public ModelAndView registerUserUI() {
		return new ModelAndView("views/user/registerUser");
	}

	@ResponseBody
	@RequestMapping(value = "/registerUser.json")
	public HttpResult registerUser(User user) {
		user.setStatus(0);
		/**
		 * 否是管理员，0：系统管理员，1：部门管理员，2：小组管理员，3：普通用户
		 */
		user.setIsAdmin(3);
		logger.info("user->{}", new Object[] { user });
		if (user.getPassword() != null) {
			user.setPassword(Md5Tool.encode(user.getPassword(), WebConstants.CHARSET));
		}
		boolean flag = userService.addUser(user);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@RequestMapping(value = "/addUserUI")
	public ModelAndView addUserUI() {
		return new ModelAndView("views/user/addUser");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/addUser.json")
	public HttpResult addUser(User user) {
		if (user.getPassword() != null) {
			user.setPassword(Md5Tool.encode(user.getPassword(), WebConstants.CHARSET));
		}
		boolean flag = userService.addUser(user);
		logger.info("flag->{}", new Object[] { flag });
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@RequestMapping(value = "/editUserUI")
	public ModelAndView editUserUI() {
		return new ModelAndView("views/user/editUser");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/editUser.json")
	public HttpResult editUser(User user) {
		if (user.getPassword() != null) {
			user.setPassword(Md5Tool.encode(user.getPassword(), WebConstants.CHARSET));
		}
		boolean flag = userService.updateUser(user);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/deleteUser.json")
	public HttpResult deleteUser(@RequestParam(defaultValue = "0") long userId) {
		boolean flag = userService.deleteUser(userId);
		logger.info("flag->{}", new Object[] { flag });
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@RequestMapping(value = "/userInfoUI")
	public ModelAndView userInfoUI() {
		return new ModelAndView("views/user/userInfo");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/userInfo.json")
	public HttpResult userInfo(@RequestParam(defaultValue = "0") long userId) {
		return HttpResult.successWithData(userService.getUserById(userId));
	}

	@SessionCheck
	@RequestMapping(value = "/editUserPwdUI")
	public ModelAndView editUserPwdUI() {
		return new ModelAndView("views/user/editUserPwd");
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/checkUserPwd.json")
	public HttpResult checkUserPwd(User user) {
		if (CommonUtil.isEmpty(user.getOldPassword())) {
			user.setOldPassword(Md5Tool.encode(user.getOldPassword(), WebConstants.CHARSET));
		}
		boolean flag = false;
		User dbUser = userService.getUserById(user.getUserId());
		if (dbUser.getPassword().equalsIgnoreCase(user.getOldPassword())) {
			flag = true;
		}
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/editUserPwd.json")
	public HttpResult editUserPwd(User user) {
		logger.info("user->{}", new Object[] { user });
		if (user.getPassword() != null) {
			user.setPassword(Md5Tool.encode(user.getPassword(), WebConstants.CHARSET));
		}
		boolean flag = userService.updateUserPwd(user);
		return HttpResult.successWithData(flag);
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/getUserListByDepartmentId.json")
	public HttpResult getUserListByDepartmentId() {
		User userSession = getSession(WebConstants.LOGIN_USER);
		return HttpResult
				.successWithData(userService.getUserListByDepartmentId(userSession.getDepartment().getDepartmentId()));
	}

	@SessionCheck
	@ResponseBody
	@RequestMapping(value = "/userListByDepartmentId.json")
	public HttpResult userListByDepartmentId(@RequestParam(defaultValue = "0") long departmentId) {
		return HttpResult.successWithData(userService.getUserListByDepartmentId(departmentId));
	}
}
