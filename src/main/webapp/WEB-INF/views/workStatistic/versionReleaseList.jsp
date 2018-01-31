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
	<form id="exportForm" action="<%=basePath%>workStatistic/exportVersionReleaseList.json" method="post">
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
			</tr>
			<tr>
			<td style="text-align:left;"><label for="resource">责任人：</label>
					<input id="resource" name="resource" class="mini-textbox"
					emptyText="支持责任人查询" style="width:120px;" /></td>
				<td style="text-align:left;"><label for="startTime">版本发布起始时间：</label>
					<input id="startTime" name="startTime" class="mini-datepicker"
					 allowInput="false" emptyText="请选择起始时间" 
					showOkButton="true" errorMode="none" style="width:120px;" /></td>
				<td style="text-align:left;"><label for="endTime">版本发布结束时间：</label>
					<input id="endTime" name="endTime" class="mini-datepicker"
					 allowInput="false" emptyText="请选择结束时间" 
					showOkButton="true" errorMode="none" style="width:120px;" /></td>
			</tr>
		</table>
		</form>
	</div>
	<div id="actionTb" class="mini-toolbar">
		<table style="width:100%;">
			<tr>
				<td><a class="mini-button" iconCls="icon-search"
					style="width:65px;" onclick="onSearch">查询</a> <a
					class="mini-button" iconCls="icon-cancel" style="width:65px;"
					onclick="onCancel">重置</a>
					<a class="mini-button" iconCls="icon-save" style="width:65px;"
					onclick="onExport">导出</a></td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="weeklyReportDg" class="mini-datagrid"
			url="<%=basePath%>workStatistic/versionReleaseList.json"
			multiSelect="false" showEmptyText="true" emptyText="暂无数据！"
			totalField="data.totalElements" dataField="data.pageList" sizeList="[5,10]">
			<div property="columns">
				<div field="department.name" align="center" headerAlign="center">部门</div>
				<div field="team.name" align="center" headerAlign="center">小组</div>
				<div field="project.projectName" align="center" headerAlign="center">项目</div>
				<div field="typeText" align="center" headerAlign="center">类型</div>
				<div field="versionInfo" align="center" headerAlign="center">版本信息</div>
				<div field="caseNumber" align="center" headerAlign="center">用例总数</div>
				<div field="bugNumber" align="center" headerAlign="center">BUG总数</div>
				<div field="bugCritical" align="center" headerAlign="center">严重bug数</div>
				<!-- <div field="perBugs" align="center" headerAlign="center">开发人均BUG数</div> -->
				<div field="versionReleaseTime" align="center" headerAlign="center"
					dateFormat="yyyy-MM-dd">版本发布时间</div>
				<div field="resource" align="center" headerAlign="center">责任人</div>
				<div field="tester" align="center" headerAlign="center">测试总人数</div>
				<div field="developer" align="center" headerAlign="center">开发总人数</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var searchForm = new mini.Form('searchForm');
		var form = new mini.Form('searchForm');
		var department = mini.get("departmentId");
		var team = mini.get("teamId");
		var project = mini.get("projectId");
		var weeklyReportDg = mini.get("weeklyReportDg");
		weeklyReportDg.load();
		
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
			weeklyReportDg.load(searchForm.getData(true, false));
			resetDefaultValue();
		}

		function onCancel() {
			searchForm.reset();
			var teamUrl = '<%=basePath%>team/teamListByDepartmentId.json';
			team.setUrl(teamUrl);
			var projectUrl = '<%=basePath%>projectInfo/projectInfoListByDepartmentId.json';
			project.setUrl(projectUrl);
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
			var startTime = mini.get("startTime");
			var endTime = mini.get("endTime");
			if (department.getValue() == null || department.getValue() <= 0) {
				mini.alert("请选择部门");
			} else if (startTime.getValue() == null
					|| startTime.getValue() == "") {
				mini.alert("请选择起始时间");
			} else if (endTime.getValue() == null || endTime.getValue() == "") {
				mini.alert("请选择结束时间");
			} else {
				document.getElementById("exportForm").submit();
			}
			resetDefaultValue();
		}
	</script>
</body>
</html>