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
<title>质量管理平台</title>
<script type="text/javascript" src="<%=basePath%>scripts/boot.js"></script>
</head>
<body>
	<div id="searchForm">
		<table>
			<tr>
				<td style="text-align: left;"><label for="projectName">项目名称：</label>
					<input id="projectName" name="projectName" class="mini-textbox"
					emptyText="支持项目名称查询" style="width: 120px;" /></td>
				<td style="text-align: left;"><label for="department">部门：</label>
					<input id="departmentId" name="department.departmentId"
					class="mini-combobox"
					url="<%=basePath%>department/departmentListAll.json"
					dataField="data" valueField="departmentId" textField="name" allowInput="true"
					emptyText="支持按部门查询" style="width: 120px;"
					onvaluechanged="onDepartmentChanged" /></td>
				<td style="text-align: left;"><label for="team">小组：</label> <input
					id="teamId" name="team.teamId" class="mini-combobox"
					url="<%=basePath%>team/teamListByDepartmentId.json"
					dataField="data" valueField="teamId" textField="name" allowInput="true"
					emptyText="支持按小组查询" style="width: 120px;" /></td>
				<td style="text-align: left;"><label for="status">状态：</label> <input
					id="status" name="status" class="mini-combobox"
					url="<%=basePath%>projectInfo/statusList.json"
					dataField="data" valueField="status" textField="statusText" allowInput="true"
					emptyText="支持按状态查询" style="width: 120px;" /></td>
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
						<a class="mini-button" iconCls="icon-add" onclick="add">增加</a>
						<a class="mini-button" iconCls="icon-edit" onclick="edit">编辑</a>
						<a class="mini-button" iconCls="icon-remove" onclick="remove">删除</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="projectInfoDg" class="mini-datagrid"
			url="<%=basePath%>projectInfo/projectInfoList.json"
			multiSelect="false" showEmptyText="true" emptyText="暂无数据！"
			totalField="data.totalElements" dataField="data.pageList" sizeList="[5,10]">
			<div property="columns">
				<div type="checkcolumn"></div>
				<div field="projectId" align="center" headerAlign="center">编号</div>
				<div field="department.name" align="center" headerAlign="center">部门</div>
				<div field="team.name" align="center" headerAlign="center">小组</div>
				<div field="projectName" align="center" headerAlign="center">项目</div>
				<div field="redmineProjectId" align="center" headerAlign="center">redmine项目Id</div>
				<div field="tester" align="center" headerAlign="center">测试总人数</div>
				<div field="developer" align="center" headerAlign="center">开发总人数</div>
				<div field="statusText" align="center" headerAlign="center">状态</div>
				<!-- <div field="user.trueName" align="center" headerAlign="center">录入人员</div> -->
				<div field="createTime" align="center" headerAlign="center"
					dateFormat="yyyy-MM-dd HH:mm:ss">录入时间</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var searchForm = new mini.Form('searchForm');
		var department = mini.get("departmentId");
		var team = mini.get("teamId");
		var projectStatus = mini.get("status");
		var projectInfoDg = mini.get("projectInfoDg");
		projectInfoDg.load();
		
		function setDefaultValue() {
			if (isEmpty(department.getValue())) {
				department.setValue(0);
			}
			if (isEmpty(team.getValue())) {
				team.setValue(0);
			}
			if (isEmpty(projectStatus.getValue())) {
				projectStatus.setValue(0);
			}
		}
		
		function resetDefaultValue() {
			if (isEmpty(department.getValue())) {
				department.setValue();
			}
			if (isEmpty(team.getValue())) {
				team.setValue();
			}
			if (isEmpty(projectStatus.getValue())) {
				projectStatus.setValue();
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
			projectInfoDg.load(searchForm.getData(true, false));
			resetDefaultValue();
		}

		function onCancel() {
            var url='<%=basePath%>team/teamListByDepartmentId.json';
            team.setUrl(url);
			searchForm.reset();
		}

		function add() {
			mini
					.open({
						url : '<%=basePath%>projectInfo/addProjectInfoUI',
						title : '添加项目',
						width : 800,
						height : 300,
						showMaxButton : true,
						allowResize : false,
						ondestroy : function(action) {
							if (action == 'ok') {
								mini.alert("添加成功");
								projectInfoDg.reload();
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
			var row = projectInfoDg.getSelected();
			if (row) {
				var projectId = row.projectId;
				mini
						.open({
							url : '<%=basePath%>projectInfo/editProjectInfoUI',
							title : '修改项目',
							width : 800,
							height : 300,
							showMaxButton : true,
							allowResize : false,
							onload : function() {
								var iframe = this.getIFrameEl();
								iframe.contentWindow.init(projectId);
							},
							ondestroy : function(action) {
								if (action == 'ok') {
									mini.alert("修改成功");
									projectInfoDg.reload();
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
			var row = projectInfoDg.getSelected();
			if (row) {
				mini
						.confirm(
								'您确定要删除吗？',
								'系统提示',
								function(action) {
									if (action == 'ok') {
										var projectId = row.projectId;
										$
												.ajax({
													url : '<%=basePath%>projectInfo/deleteProject.json',
													type : 'post',
													data : {
														projectId : projectId
													},
													success : function(data) {
														if (data.code==200 && data.data) {
															mini.alert("删除成功");
															projectInfoDg
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
            var url="<%=basePath%>team/teamListByDepartmentId.json?departmentId="+departmentId;
			team.setUrl(url);
		}
	</script>
</body>
</html>