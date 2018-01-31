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
				<td style="text-align: left;"><label for="department">部门：</label>
					<input id="departmentId" name="departmentId"
					class="mini-combobox"
					url="<%=basePath%>department/departmentListAll.json"
					dataField="data" valueField="departmentId" textField="name" allowInput="true"
					emptyText="支持按部门查询" style="width: 120px;"
					onvaluechanged="onDepartmentChanged" /></td>
				<td style="text-align: left;"><label for="team">小组：</label> <input
					id="teamId" name="teamId" class="mini-combobox"
					url="<%=basePath%>team/teamListByDepartmentId.json"
					dataField="data" valueField="teamId" textField="name" allowInput="true"
					emptyText="支持按小组查询" style="width: 120px;" onvaluechanged="onTeamChanged" /></td>
				<td style="text-align: left;"><label for="project">项目：</label>
					<input id="projectId" name="sonarProjectId" class="mini-combobox"
					url="<%=basePath%>sonarProject/enabledSonarProjectListByDepartmentId.json"
					dataField="data" valueField="id" textField="projectName" allowInput="true"
					emptyText="支持按项目查询" style="width: 120px;" /></td>
			</tr>
			<tr>
				<td style="text-align: left;"><label for="startTime">统计起始时间：</label>
					<input id="startTime" name="startTime" class="mini-datepicker"
					allowInput="false" emptyText="请选择起始时间" showOkButton="true"
					errorMode="none" style="width: 120px;" /></td>
				<td style="text-align: left;"><label for="endTime">统计结束时间：</label>
					<input id="endTime" name="endTime" class="mini-datepicker"
					allowInput="false" emptyText="请选择结束时间" showOkButton="true"
					errorMode="none" style="width: 120px;" /></td>
			</tr>
		</table>
	</div>
	<div id="actionTb" class="mini-toolbar">
		<table style="width: 100%;">
			<tr>
				<td><a class="mini-button" iconCls="icon-search"
					style="width: 65px;" onclick="onSearch">查询</a> <a
					class="mini-button" iconCls="icon-cancel" style="width: 65px;"
					onclick="onCancel">重置</a>
			</tr>
		</table>
	</div>
	<div id="actionTb" class="mini-toolbar">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;">
					<a class="mini-button"
					iconCls="icon-edit" onclick="edit">编辑</a></td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="sonarStatisticDg" class="mini-datagrid"
			url="<%=basePath%>sonarStatistic/sonarStatisticList.json"
			multiSelect="false" showEmptyText="true" emptyText="暂无数据！"
			totalField="data.totalElements" dataField="data.pageList" pageSize=10
			sizeList="[5,10]">
			<div property="columns">
				<div type="checkcolumn"></div>
				<div field="id" align="center"
					headerAlign="center">编号</div>
				<div field="departmentName" align="center"
					headerAlign="center">部门</div>
				<div field="teamName" align="center"
					headerAlign="center">小组</div>
				<div field="projectName" align="center"
					headerAlign="center">项目</div>
				<div field="sonarProjectName" align="center"
					headerAlign="center">sonar项目</div>
				<div field="blocker" align="center" headerAlign="center">blocker</div>
				<div field="critical" align="center" headerAlign="center">critical</div>
				<!-- <div field="major" align="center" headerAlign="center">major</div> -->
				<div field="count" align="center" headerAlign="center">总数量</div>
				<div field="countIncreases" align="center" headerAlign="center">总增幅</div>
				<div field="statisticDate" align="center" headerAlign="center"
					dateFormat="yyyy-MM-dd">统计日期</div>
				<div field="createTime" align="center" headerAlign="center"
					dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var searchForm = new mini.Form('searchForm');
		var sonarStatisticDg = mini.get("sonarStatisticDg");
		var department = mini.get("departmentId");
		var team = mini.get("teamId");
		var project = mini.get("projectId");
		sonarStatisticDg.load();
		
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
			sonarStatisticDg.load(searchForm.getData(true, false));
			resetDefaultValue();
		}

		function onCancel() {
			var teamUrl = '<%=basePath%>team/teamListByDepartmentId.json';
			team.setUrl(teamUrl);
			var projectUrl = '<%=basePath%>sonarProject/enabledSonarProjectListByDepartmentId.json';
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
	            var url="<%=basePath%>sonarProject/enabledSonarProjectListByTeamId.json?teamId="+teamId;
	            project.setUrl(url);
			}
		
		function edit() {
			var row = sonarStatisticDg.getSelected();
			if (row) {
				var id = row.id;
				mini
						.open({
							url : '<%=basePath%>sonarStatistic/editSonarStatisticUI',
							title : '修改',
							width : 800,
							height : 400,
							showMaxButton : true,
							allowResize : false,
							onload : function() {
								var iframe = this.getIFrameEl();
								iframe.contentWindow.init(id);
							},
							ondestroy : function(action) {
								if (action == 'ok') {
									mini.alert("修改成功");
									sonarStatisticDg.reload();
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
	</script>
</body>
</html>