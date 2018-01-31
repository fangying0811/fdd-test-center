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
		<form id="exportForm"
			action="<%=basePath%>/workStatistic/exportTeamWorkStatistic.json"
			method="post">
			<table>
				<tr>
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
						dataField="data" valueField="teamId" textField="name"
						allowInput="true" emptyText="支持按小组查询" style="width: 120px;" /></td>
					<td style="text-align: left;"><label for="startTime">版本发布起始时间：</label>
						<input id="startTime" name="startTime" class="mini-datepicker"
						allowInput="false" emptyText="请选择起始时间" showOkButton="true"
						errorMode="none" style="width: 120px;" /></td>
					<td style="text-align: left;"><label for="endTime">版本发布结束时间：</label>
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
	<div class="mini-fit">
		<div id="workStatisticDg" class="mini-datagrid"
			url="<%=basePath%>workStatistic/teamWorkStatisticList.json"
			multiSelect="false" showEmptyText="true" emptyText="暂无数据！"
			totalField="data.totalElements" dataField="data.pageList" pageSize=50
			sizeList="[50,100]" showPager="false">
			<div property="columns">
				<div field="department.name" align="center" headerAlign="center">部门</div>
				<div field="team.name" align="center" headerAlign="center">小组</div>
				<div field="versionCount" align="center" headerAlign="center">上线版本数</div>
				<!-- <div field="versionPercent" align="center" headerAlign="center">测试人均版本数</div> -->
				<div field="caseCount" align="center" headerAlign="center">用例总数</div>
				<div field="bugCount" align="center" headerAlign="center">BUG总数</div>
				<div field="bugCritical" align="center" headerAlign="center">严重bug数</div>
				<div field="outstandingIssueCount" align="center"
					headerAlign="center">遗留问题数</div>
				<div field="onlineIssueCount" align="center" headerAlign="center">外网问题数</div>
				<div field="tester" align="center" headerAlign="center">测试总人数</div>
				<div field="developer" align="center" headerAlign="center">开发总人数</div>
				<div field="percent" align="center" headerAlign="center">测试开发比例</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var searchForm = new mini.Form('searchForm');
		var department = mini.get("departmentId");
		var team = mini.get("teamId");
		var workStatisticDg = mini.get("workStatisticDg");
		workStatisticDg.load();
		
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
			grid.setShowPager(grid.totalCount > 0);
			resetDefaultValue();
		}

		//关键字模糊查询
		function onSearch() {
			setDefaultValue();
			workStatisticDg.load(searchForm.getData(true, false));
			resetDefaultValue();
		}

		function onCancel() {
			searchForm.reset();
			var teamUrl = '<%=basePath%>team/teamListByDepartmentId.json';
			team.setUrl(teamUrl);
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