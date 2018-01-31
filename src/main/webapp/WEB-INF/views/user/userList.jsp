<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>房多多质量管理平台</title>
<script type="text/javascript" src="<%=basePath%>scripts/boot.js"></script>
</head>
<body>
	<div id="searchForm">
		<table>
			<tr>
				<td style="text-align: left;"><label for="username">登录用户名：</label>
					<input id="username" name="username" class="mini-textbox"
					emptyText="支持登录用户名查询" style="width: 140px;" /></td>
				<td style="text-align: left;"><label for="trueName">姓名：</label>
					<input id="trueName" name="trueName" class="mini-textbox"
					emptyText="支持姓名查询" style="width: 120px;" /></td>
				<td style="text-align: left;"><label for="department">部门：</label>
					<input id="departmentId" name="department.departmentId"
					class="mini-combobox"
					url="<%=basePath%>department/departmentListAll.json"
					dataField="data" valueField="departmentId" textField="name"
					allowInput="true" emptyText="支持按部门查询" style="width: 120px;"
					onvaluechanged="onDepartmentChanged" /></td>
				<td style="text-align: left;"><label for="team">小组：</label> <input
					id="teamId" name="team.teamId" class="mini-combobox"
					url="<%=basePath%>team/teamListByDepartmentId.json"
					dataField="data" valueField="teamId" textField="name" allowInput="true"
					emptyText="支持按小组查询" style="width: 120px;"></td>
			</tr>
			<tr>
				<td><a class="mini-button" iconCls="icon-search"
					style="width: 71px;" onclick="onSearch">查询</a> <a
					class="mini-button" iconCls="icon-cancel" style="width: 71px;"
					onclick="onCancel">重置</a> <span
					style="display: inline-block; width: 25px"></span></td>
			</tr>
		</table>
	</div>
	<div id="actionTb" class="mini-toolbar">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;"><c:if
						test="${sessionScope.loginUser.isAdmin==0 || sessionScope.loginUser.isAdmin==1}">
						<a class="mini-button" iconCls="icon-add" onclick="add">增加</a>
					</c:if> <a class="mini-button" iconCls="icon-edit" onclick="edit">编辑</a> <a
					class="mini-button" iconCls="icon-edit" onclick="editUserPwd">重置登录密码</a>
					<a class="mini-button" iconCls="icon-search" onclick="userInfo">查看用户信息</a>
					<c:if
						test="${sessionScope.loginUser.isAdmin==0 || sessionScope.loginUser.isAdmin==1}">
						<a class="mini-button" iconCls="icon-remove" onclick="remove">删除</a>
					</c:if></td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="userDg" class="mini-datagrid"
			url="<%=basePath%>user/userList.json" multiSelect="false"
			showEmptyText="true" emptyText="暂无数据！"
			totalField="data.totalElements" dataField="data.pageList"
			sizeList="[5,10]">
			<div property="columns">
				<div type="checkcolumn"></div>
				<div field="userId" align="center" headerAlign="center">编号</div>
				<div field="username" align="center" headerAlign="center">登录用户名</div>
				<div field="trueName" align="center" headerAlign="center">姓名</div>
				<div field="department.name" align="center" headerAlign="center">部门</div>
				<div field="team.name" align="center" headerAlign="center">小组</div>
				<div field="isAdminText" align="center" headerAlign="center">角色</div>
				<div field="statusText" align="center" headerAlign="center">状态</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var searchForm = new mini.Form('searchForm');
		var department = mini.get("departmentId");
		var team = mini.get("teamId");
		var userDg = mini.get("userDg");
		userDg.load();
		
		function setDefaultValue() {
			if (isEmpty(department.getValue())) {
				department.setValue(0);
			}
			if (isEmpty(team.getValue())) {
				team.setValue(0);
			}
		}
		
		function resetDefaultValue() {
			if (isEmpty(department.getValue())) {
				department.setValue();
			}
			if (isEmpty(team.getValue())) {
				team.setValue();
			}
		}
		
		function onLoad(e) {
			setDefaultValue();
			var grid = e.sender;
			//没有菜单信息
			grid.setShowPager(grid.totalCount > 0);
			resetDefaultValue();
		}

		//关键字模糊查询
		function onSearch() {
			setDefaultValue();
			userDg.load(searchForm.getData(true, false));
			resetDefaultValue();
		}

		function onCancel() {
            var url='<%=basePath%>team/teamListByDepartmentId.json';
            team.setUrl(url);
			searchForm.reset();
		}

		function add() {
			mini.open({
				url : '<%=basePath%>user/addUserUI',
				title : '添加用户',
				width : 800,
				height : 400,
				showMaxButton : true,
				allowResize : false,
				ondestroy : function(action) {
					if (action == 'ok') {
						mini.alert("添加成功");
						userDg.reload();
					} else if (action != 'close' && !isEmpty(action)) {
						mini.alert(action);
					}else {
						if (action != 'close' && isEmpty(action)){
							mini.alert("添加失败");
						}
					}
				}
			});
		}

		function edit() {
			var row = userDg.getSelected();
			if (row) {
				var userId = row.userId;
				if (<c:out value="${sessionScope.loginUser.isAdmin}"/>== 3
						&& userId != <c:out value="${sessionScope.loginUser.userId}"/>) {
					mini.alert("普通用户只可修改自己");
				} else {
					mini
							.open({
								url : '<%=basePath%>user/editUserUI',
								title : '修改用户',
								width : 800,
								height : 400,
								showMaxButton : true,
								allowResize : false,
								onload : function() {
									var iframe = this.getIFrameEl();
									iframe.contentWindow.init(userId);
								},
								ondestroy : function(action) {
									if (action == 'ok') {
										mini.alert("修改成功");
										userDg.reload();
									} else if (action != 'close' && !isEmpty(action)) {
										mini.alert(action);
									}else {
										if (action != 'close' && isEmpty(action)){
											mini.alert("修改失败");
										}
									}
								}
							});
				}
			} else {
				mini.alert("请选中一条记录");
			}
		}

		function editUserPwd() {
			var row = userDg.getSelected();
			if (row) {
				var userId = row.userId;
				if (<c:out value="${sessionScope.loginUser.isAdmin}"/>== 2
						&& userId != <c:out value="${sessionScope.loginUser.userId}"/>) {
					mini.alert("普通用户只可修改自己");
				} else {
					mini
							.open({
								url : '<%=basePath%>user/editUserPwdUI',
								title : '重置用户登录密码',
								width : 800,
								height : 200,
								showMaxButton : true,
								allowResize : false,
								onload : function() {
									var iframe = this.getIFrameEl();
									iframe.contentWindow.init(userId);
								},
								ondestroy : function(action) {
									if (action == 'ok') {
										mini.alert("重置用户登录密码成功");
										userDg.reload();
									} else if (action != 'close' && !isEmpty(action)) {
										mini.alert(action);
									}else {
										if (action != 'close' && isEmpty(action)){
											mini.alert("重置用户登录密码失败");
										}
									}
								}
							});
				}
			} else {
				mini.alert("请选中一条记录");
			}
		}

		function userInfo() {
			var row = userDg.getSelected();
			if (row) {
				var userId = row.userId;
				mini.open({
					url : '<%=basePath%>user/userInfoUI',
					title : '查看用户信息',
					width : 500,
					height : 400,
					showMaxButton : true,
					allowResize : false,
					onload : function() {
						var iframe = this.getIFrameEl();
						iframe.contentWindow.init(userId);
					},
					ondestroy : function(action) {
						userDg.reload();
					}
				});
			} else {
				mini.alert("请选中一条记录");
			}
		}

		function remove() {
			var row = userDg.getSelected();
			if (row) {
				mini
						.confirm(
								'您确定要删除吗？',
								'系统提示',
								function(action) {
									if (action == 'ok') {
										var userId = row.userId;
										$
												.ajax({
													url : '<%=basePath%>user/deleteUser.json',
													type : 'post',
													data : {
														userId : userId
													},
													success : function(data) {
														if (data.code==200 && data.data) {
															mini.alert("删除成功");
															userDg
																	.reload();
														} else if (!isEmpty(data.msg)){
															mini.alert(data.msg);
														}else {
															if (isEmpty(data.msg)){
																mini.alert("删除失败");
															}
														}
													},
													dataType : 'json'
												});
									}
								});
			} else {
				mini.alert("请选中一条记录");
			}
		}
		
		function onDepartmentChanged(e) {
            var departmentId=department.getValue();
            team.setValue();
            var url="<%=basePath%>team/teamListByDepartmentId.json?departmentId="+ departmentId;
			team.setUrl(url);
		}
	</script>
</body>
</html>