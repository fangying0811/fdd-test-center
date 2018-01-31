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
				<td style="text-align: left;"><label for="testStatus">类型：</label>
						<input id="type" name="type" class="mini-combobox"
						url="<%=basePath%>weeklyReport/typeList.json" dataField="data"
						valueField="type" textField="typeText" allowInput="true"
						emptyText="支持按类型查询" style="width: 120px;" /></td>
			</tr>
			<tr>
				<td><a class="mini-button" iconCls="icon-search"
					style="width: 71px;" onclick="onSearch">查询</a> <a
					class="mini-button" iconCls="icon-cancel" style="width: 71px;"
					onclick="onReset">重置</a> <span
					style="display: inline-block; width: 25px"></span></td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="weeklyReportDoneDg" class="mini-datagrid"
			url="<%=basePath%>weeklyReport/weeklyReportDoneList.json"
			multiSelect="false" showEmptyText="true" emptyText="暂无数据！"
			totalField="data.totalElements" dataField="data.pageList" sizeList="[5,10]">
			<div property="columns">
				<div type="checkcolumn"></div>
				<div field="weeklyReportId" align="center" headerAlign="center">编号</div>
				<!-- <div field="department.departmentId" align="center" headerAlign="center">部门ID</div> -->
				<div field="department.name" align="center" headerAlign="center">部门</div>
				<!-- <div field="team.teamId" align="center" headerAlign="center">小组ID</div> -->
				<div field="team.name" align="center" headerAlign="center">小组</div>
				<!-- <div field="project.projectId" align="center" headerAlign="center">项目ID</div> -->
				<div field="project.projectName" align="center" headerAlign="center">项目</div>
				<div field="typeText" align="center" headerAlign="center">类型</div>
				<div field="versionInfo" align="center" headerAlign="center">版本信息</div>
				<div field="resource" align="center" headerAlign="center">责任人</div>
				<div field="caseNumber" align="center" headerAlign="center">用例数</div>
				<div field="bugNumber" align="center" headerAlign="center">bug数</div>
				<div field="bugCritical" align="center" headerAlign="center">严重bug数</div>
				<div field="testStatusText" align="center" headerAlign="center">进度</div>
				<div field="versionTestTime" align="center" headerAlign="center"
				dateFormat="yyyy-MM-dd HH:mm:ss">测试开始时间</div>
				<div field="versionReleaseTime" align="center" headerAlign="center"
				dateFormat="yyyy-MM-dd HH:mm:ss">测试完成时间</div>
				<div field="createTime" align="center" headerAlign="center"
				dateFormat="yyyy-MM-dd HH:mm:ss">录入时间</div>
			</div>
		</div>
		<div class="mini-toolbar"
			style="text-align: center; padding-top: 8px; padding-bottom: 8px;"
			borderStyle="border:0;">
			<a class="mini-button" style="width: 60px;" onclick="onOk()">确定</a> <span
				style="display: inline-block; width: 25px;"></span> <a
				class="mini-button" style="width: 60px;" onclick="onCancel()">取消</a>
		</div>
	</div>

	<script type="text/javascript">
		mini.parse();
		var searchForm = new mini.Form('searchForm');
		var grid = mini.get("weeklyReportDoneDg");
		grid.load();
		var department = mini.get("departmentId");
		var team = mini.get("teamId");
		var project = mini.get("projectId");
		var type = mini.get("type");
		
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
		}

		function getData() {
			var row = grid.getSelected();
			var data = {
					weeklyReportId : row.weeklyReportId,
					versionInfo : row.versionInfo,
					departmentId : row.department.departmentId,
					teamId : row.team.teamId,
					projectId : row.project.projectId,
					resource : row.resource,
					cases : row.caseNumber,
					bugs : row.bugNumber,
					bugCritical : row.bugCritical,
					versionTestTime : row.versionTestTime,
					versionReleaseTime : row.versionReleaseTime
				};
			return data;
		}
		
		//关键字模糊查询
		function onSearch() {
			setDefaultValue();
			grid.load(searchForm.getData(true, false));
			resetDefaultValue();
		}

		var department=mini.get("departmentId");
		var team=mini.get("teamId");
		var project=mini.get("projectId");
		
		function onReset() {
			var teamUrl = '<%=basePath%>team/teamListByDepartmentId.json';
			team.setUrl(teamUrl);
			var projectUrl = '<%=basePath%>projectInfo/projectInfoListByDepartmentId.json';
			project.setUrl(projectUrl);
			searchForm.reset();
		}

		function onOk() {
			closeWindow("ok");
		}
		function onCancel() {
			closeWindow("close");
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
	</script>
</body>
</html>