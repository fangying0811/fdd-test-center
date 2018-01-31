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
				<td style="text-align: left;"><label for="name">小组名称：</label> <input
					id="name" name="name" class="mini-textbox" emptyText="支持小组名称查询"
					style="width: 120px;" /></td>
				<td style="text-align: left;"><label for="department">部门：</label>
					<input id="departmentId" name="department.departmentId"
					class="mini-combobox"
					url="<%=basePath%>department/departmentListAll.json"
					dataField="data" valueField="departmentId" textField="name" allowInput="true"
					emptyText="支持按部门查询" style="width: 120px;" /></td>
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
				<td style="width: 100%;">
					<c:if test="${sessionScope.loginUser.isAdmin!=3}"> 
						<a class="mini-button" iconCls="icon-add" onclick="add">增加</a>
						<a class="mini-button" iconCls="icon-edit" onclick="edit">编辑</a>
					</c:if> 
					<c:if test="${sessionScope.loginUser.isAdmin==0 || sessionScope.loginUser.isAdmin==1}"> 
						<a class="mini-button" iconCls="icon-remove" onclick="remove">删除</a>
					</c:if></td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="teamDg" class="mini-datagrid"
			url="<%=basePath%>team/teamList.json"
			multiSelect="false" showEmptyText="true" emptyText="暂无数据！"
			totalField="data.totalElements" dataField="data.pageList" sizeList="[5,10]">
			<div property="columns">
				<div type="checkcolumn"></div>
				<div field="teamId" align="center" headerAlign="center">编号</div>
				<div field="department.name" align="center" headerAlign="center">部门</div>
				<div field="name" align="center" headerAlign="center">小组</div>
				<div field="tester" align="center" headerAlign="center">测试总人数</div>
				<div field="developer" align="center" headerAlign="center">开发总人数</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var searchForm = new mini.Form('searchForm');
		var teamDg = mini.get("teamDg");
		teamDg.load();
		var department = mini.get("departmentId");
		var statisticDate = mini.get("statisticDate");
		
		function setDefaultValue() {
			if (isEmpty(department.getValue())) {
				department.setValue(0);
			}
		}
		
		function resetDefaultValue() {
			if (isEmpty(department.getValue())) {
				department.setValue();
			}
		}

		function onLoad(e) {
			setDefaultValue();
			var grid = e.sender;
			grid.setShowPager(grid.totalCount > 0);
			resetDefaultValue();
		}

		//关键字模糊查询
		function onSearch() {
			setDefaultValue();
			teamDg.load(searchForm.getData(true, false));
			resetDefaultValue();
		}

		function onCancel() {
			searchForm.reset();
		}

		function add() {
			mini.open({
				url : '<%=basePath%>team/addTeamUI',
				title : '添加小组',
				width : 800,
				height : 300,
				showMaxButton : true,
				allowResize : false,
				ondestroy : function(action) {
					if (action == 'ok') {
						mini.alert("添加成功");
						teamDg.reload();
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
			var row = teamDg.getSelected();
			if (row) {
				var teamId = row.teamId;
				mini.open({
					url : '<%=basePath%>team/editTeamUI',
					title : '修改小组',
					width : 800,
					height : 300,
					showMaxButton : true,
					allowResize : false,
					onload : function() {
						var iframe = this.getIFrameEl();
						iframe.contentWindow.init(teamId);
					},
					ondestroy : function(action) {
						if (action == 'ok') {
							mini.alert("修改成功");
							teamDg.reload();
						} else if (action != 'close' && !isEmpty(action)) {
							mini.alert(action);
						}else {
							if (action != 'close' && isEmpty(action)){
								mini.alert("修改失败");
							}
						}
					}
				});
			} else {
				mini.alert("请选中一条记录");
			}
		}

		function remove() {
			var row = teamDg.getSelected();
			if (row) {
				mini
						.confirm(
								'您确定要删除吗？',
								'系统提示',
								function(action) {
									if (action == 'ok') {
										var teamId = row.teamId;
										$
												.ajax({
													url : '<%=basePath%>team/deleteTeam.json',
													type : 'post',
													data : {
														teamId : teamId
													},
													success : function(data) {
														if (data.code==200 && data.data) {
															mini.alert("删除成功");
															teamDg
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
	</script>
</body>
</html>