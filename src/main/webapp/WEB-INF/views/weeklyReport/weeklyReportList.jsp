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
		<form id="exportForm" action="<%=basePath%>weeklyReport/exportWeeklyReport.json" method="post">
			<table>
				<tr>
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
					emptyText="支持按小组查询" style="width: 120px;"
					onvaluechanged="onTeamChanged" /></td>
				<td style="text-align: left;"><label for="project">项目：</label>
					<input id="projectId" name="project.projectId"
					class="mini-combobox"
					url="<%=basePath%>projectInfo/projectInfoListByDepartmentId.json"
					dataField="data" valueField="projectId" textField="projectName" 
					allowInput="true" emptyText="支持按项目查询" style="width: 120px;" /></td>
				<td style="text-align: left;"><label for="resource">责任人：</label>
						<input id="resource" name="resource" class="mini-textbox"
						emptyText="支持责任人查询" style="width: 120px;" /></td>
				</tr>
				<tr>
					<td style="text-align: left;"><label for="testStatus">类型：</label>
						<input id="type" name="type" class="mini-combobox"
						url="<%=basePath%>weeklyReport/typeList.json" dataField="data"
						valueField="type" textField="typeText" allowInput="true"
						emptyText="支持按类型查询" style="width: 120px;" /></td>
					<td style="text-align: left;"><label for="testStatus">进度：</label>
						<input id="testStatus" name="testStatus" class="mini-combobox"
						url="<%=basePath%>weeklyReport/testStatusList.json"
						dataField="data" valueField="testStatus"
						textField="testStatusText" allowInput="true"
						emptyText="支持按进度查询" style="width: 120px;" /></td>
					<td style="text-align: left;"><label for="startTime">录入起始时间：</label>
						<input id="startTime" name="startTime" class="mini-datepicker"
						allowInput="false" emptyText="请选择起始时间" showOkButton="true"
						errorMode="none" style="width: 120px;" /></td>
					<td style="text-align: left;"><label for="endTime">录入结束时间：</label>
						<input id="endTime" name="endTime" class="mini-datepicker"
						allowInput="false" emptyText="请选择结束时间" showOkButton="true"
						errorMode="none" style="width: 120px;" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="actionTb" class="mini-toolbar">
		<table style="width: 100%;">
			<tr>
				<td><a class="mini-button" iconCls="icon-search"
					style="width: 65px;" onclick="onSearch">查询</a> <a
					class="mini-button" iconCls="icon-cancel" style="width: 65px;"
					onclick="onCancel">重置</a> <a class="mini-button"
					iconCls="icon-save" style="width: 65px;" onclick="onExport">导出</a></td>
			</tr>
		</table>
	</div>
	<div id="actionTb" class="mini-toolbar">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;"><a class="mini-button"
					iconCls="icon-add" onclick="add">增加</a> <a class="mini-button"
					iconCls="icon-add" onclick="copy">复制</a> <a class="mini-button"
					iconCls="icon-edit" onclick="edit">编辑</a> <a class="mini-button"
					iconCls="icon-search" onclick="weeklyReportInfo">查看周报详情</a> <s:if
						test="#session.loginUser.isAdmin==0 || #session.loginUser.isAdmin==1">
						<a class="mini-button" iconCls="icon-remove" onclick="remove">删除</a>
					</s:if></td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="weeklyReportDg" class="mini-datagrid"
			url="<%=basePath%>weeklyReport/weeklyReportList.json"
			multiSelect="false" showEmptyText="true" emptyText="暂无数据！"
			totalField="data.totalElements" dataField="data.pageList"
			sizeList="[5,10]">
			<div property="columns">
				<div type="checkcolumn"></div>
				<div field="weeklyReportId" align="center" headerAlign="center">编号</div>
				<div field="department.name" align="center" headerAlign="center">部门</div>
				<div field="team.name" align="center" headerAlign="center">小组</div>
				<div field="project.projectName" align="center" headerAlign="center">项目</div>
				<div field="typeText" align="center" headerAlign="center">类型</div>
				<div field="versionInfo" align="center" headerAlign="center">版本信息</div>
				<div field="resource" align="center" headerAlign="center">责任人</div>
				<div field="testStatusText" align="center" headerAlign="center">进度</div>
				<div field="createTime" align="center" headerAlign="center"
					dateFormat="yyyy-MM-dd HH:mm:ss">录入时间</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var searchForm = new mini.Form('searchForm');
		var weeklyReportDg = mini.get("weeklyReportDg");
		weeklyReportDg.load();
		var department = mini.get("departmentId");
		var team = mini.get("teamId");
		var project = mini.get("projectId");
		var type = mini.get("type");
		var testStatus = mini.get("testStatus");
		
		function setDefaultValue() {
			if (isEmpty(department.getValue())) {
				department.setValue(0);
			}
			if (isEmpty(team.getValue())) {
				team.setValue(0);
			}
			if (isEmpty(project.getValue())) {
				project.setValue(0);
			}
			if (isEmpty(type.getValue())) {
				type.setValue(0);
			}
			if (isEmpty(testStatus.getValue())) {
				testStatus.setValue(0);
			}
		}
		
		function resetDefaultValue() {
			if (isEmpty(department.getValue())) {
				department.setValue();
			}
			if (isEmpty(team.getValue())) {
				team.setValue();
			}
			if (isEmpty(project.getValue())) {
				project.setValue();
			}
			if (isEmpty(type.getValue())) {
				type.setValue();
			}
			if (isEmpty(testStatus.getValue())) {
				testStatus.setValue();
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
			weeklyReportDg.load(searchForm.getData(true, false));
			resetDefaultValue();
		}

		function onCancel() {
			var teamUrl = '<%=basePath%>team/teamListByDepartmentId.json';
			team.setUrl(teamUrl);
			var projectUrl = '<%=basePath%>projectInfo/projectInfoListByDepartmentId.json';
			project.setUrl(projectUrl);
			searchForm.reset();
		}
		
		function onDepartmentChanged(e) {
            var departmentId=department.getValue();
            team.setValue();
            var url="<%=basePath%>team/teamListByDepartmentId.json?departmentId="+departmentId;
			team.setUrl(url);
		}
		
		function onTeamChanged(e) {
            var teamId=team.getValue();
            project.setValue();
            var url="<%=basePath%>projectInfo/projectInfoListByTeamId.json?teamId="+ teamId;
			project.setUrl(url);
		}

		function onExport() {
			setDefaultValue();
			var departmentId = mini.get("departmentId");
			var startTime = mini.get("startTime");
			var endTime = mini.get("endTime");

			if (departmentId.getValue() == null || departmentId.getValue() <= 0) {
				mini.alert("请选择部门");
			} else if (startTime.getValue() == null
					|| startTime.getValue() == "") {
				mini.alert("请选择录入起始时间");
			} else if (endTime.getValue() == null || endTime.getValue() == "") {
				mini.alert("请选择录入结束时间");
			} else {
				document.getElementById("exportForm").submit();
			}
			resetDefaultValue();
		}

		function add() {
			mini
					.open({
						url : '<%=basePath%>weeklyReport/addWeeklyReportUI',
						title : '添加周报',
						width : 800,
						height : 600,
						showMaxButton : true,
						allowResize : false,
						showFooter : true,
						ondestroy : function(action) {
							if (action == 'ok') {
								mini.alert("添加成功");
								weeklyReportDg.reload();
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

		function copy() {
			var row = weeklyReportDg.getSelected();
			if (row) {
				var weeklyReportId = row.weeklyReportId;
				mini
						.open({
							url : '<%=basePath%>weeklyReport/copyWeeklyReportUI',
							title : '添加周报',
							width : 800,
							height : 600,
							showMaxButton : true,
							allowResize : false,
							showFooter : true,
							onload : function() {
								var iframe = this.getIFrameEl();
								iframe.contentWindow.init(weeklyReportId);
							},
							ondestroy : function(action) {
								if (action == 'ok') {
									mini.alert("添加成功");
									weeklyReportDg.reload();
								} else if (action != 'close' && !isEmpty(action)) {
									mini.alert(action);
								}else {
									if (action != 'close' && isEmpty(action)){
										mini.alert("添加失败");
									}
								}
							}
						});
			} else {
				mini.alert("请选中一条记录");
			}
		}

		function edit() {
			var row = weeklyReportDg.getSelected();
			if (row) {
				var weeklyReportId = row.weeklyReportId;
				mini
						.open({
							url : '<%=basePath%>weeklyReport/editWeeklyReportUI',
							title : '修改周报',
							width : 800,
							height : 600,
							showMaxButton : true,
							allowResize : false,
							showFooter : true,
							onload : function() {
								var iframe = this.getIFrameEl();
								iframe.contentWindow.init(weeklyReportId);
							},
							ondestroy : function(action) {
								if (action == 'ok') {
									mini.alert("修改成功");
									weeklyReportDg.reload();
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

		function weeklyReportInfo() {
			var row = weeklyReportDg.getSelected();
			if (row) {
				var weeklyReportId = row.weeklyReportId;
				mini
						.open({
							url : '<%=basePath%>weeklyReport/weeklyReportInfoUI',
							title : '查看周报',
							width : 800,
							height : 600,
							showMaxButton : true,
							allowResize : false,
							showFooter : true,
							onload : function() {
								var iframe = this.getIFrameEl();
								iframe.contentWindow.init(weeklyReportId);
							},
							ondestroy : function(action) {
								weeklyReportDg.reload();
							}
						});
			} else {
				mini.alert("请选中一条记录");
			}
		}

		function remove() {
			var row = weeklyReportDg.getSelected();
			if (row) {
				mini
						.confirm(
								'您确定要删除吗？',
								'系统提示',
								function(action) {
									if (action == 'ok') {
										var weeklyReportId = row.weeklyReportId;
										$
												.ajax({
													url : '<%=basePath%>weeklyReport/deleteWeeklyReport.json',
													type : 'post',
													data : {
														weeklyReportId : weeklyReportId
													},
													success : function(data) {
														if (data.code == 200
																&& data.data) {
															mini.alert("删除成功");
															weeklyReportDg
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