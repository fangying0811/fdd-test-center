package com.fangdd.testcenter.core;

/**
 * 系统级异常定义. code范围: [10000-)
 *
 * @since 1.0.0
 */
public class SystemErrorCodeConstant {

	public static final ErrorCode SUCCESS = new ErrorCode(200, "成功");
	public static final ErrorCode NOT_FOUND_ERROR = new ErrorCode(404, "访问地址不存在");
	public static final ErrorCode INTERNAL_SERVER_ERROR = new ErrorCode(500, "服务器内部异常");
	public static final ErrorCode SYSTME_ERROR = new ErrorCode(10000, "系统异常");
	public static final ErrorCode JSON_FORMAT_ERROR = new ErrorCode(10001, "Json格式不正确");
	public static final ErrorCode ADD_FAILURE = new ErrorCode(10002, "添加失败");
	public static final ErrorCode UPDATE_FAILURE = new ErrorCode(10003, "修改失败");
	public static final ErrorCode DELETE_FAILURE = new ErrorCode(10004, "删除失败");
	public static final ErrorCode QUERY_FAILURE = new ErrorCode(10005, "查询失败");
	public static final ErrorCode USER_NOT_EXIST = new ErrorCode(10006, "用户不存在");
	public static final ErrorCode USER_LOCKED = new ErrorCode(10007, "用户被锁定！请联系管理员！");
	public static final ErrorCode USER_PASSWORD_ERROR = new ErrorCode(10008, "密码错误！");
	public static final ErrorCode DEPARTMENT_EXIST_USER = new ErrorCode(10009, "部门存在员工，不可删除");
	public static final ErrorCode DEPARTMENT_EXIST_TEAM = new ErrorCode(10010, "部门存在小组，不可删除");
	public static final ErrorCode TEAM_EXIST_USER = new ErrorCode(10011, "小组存在员工，不可删除");
	public static final ErrorCode EXPORT_ERROR = new ErrorCode(10012, "导出异常");
	public static final ErrorCode EXIST_SUB_MENU = new ErrorCode(10013, "存在子菜单，不可删除");
	public static final ErrorCode UPLOAD_FILE_ERROR = new ErrorCode(10014, "文件上传异常");
	public static final ErrorCode DOWNLOAD_FILE_ERROR = new ErrorCode(10015, "文件下载异常");
	public static final ErrorCode AUTOMATION_PROJECT_NOT_EXIST = new ErrorCode(10016, "自动化测试项目不存在");
	public static final ErrorCode INTERFACE_ERROR = new ErrorCode(9999, "访问接口异常,请稍后再试!");
	public static final ErrorCode SESSION_INVALID = new ErrorCode(9998, "登录已过期,请退出重新登录!");
}
