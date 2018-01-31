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
			action="<%=basePath%>sonarStatistic/exportSonarTopCountIncreasesReport.json"
			method="post">
			<table>
				<tr>
					<td style="text-align: left;"><label for="department">部门：</label>
						<input id="departmentId" name="departmentId" class="mini-combobox"
						url="<%=basePath%>department/departmentListAll.json"
						dataField="data" valueField="departmentId" textField="name"
						allowInput="true" emptyText="支持按部门查询" style="width: 120px;"
						onvaluechanged="onDepartmentChanged" /></td>
					<td style="text-align: left;"><label for="team">小组：</label> <input
						id="teamId" name="teamId" class="mini-combobox"
						url="<%=basePath%>team/teamListByDepartmentId.json"
						dataField="data" valueField="teamId" textField="name"
						allowInput="true" emptyText="支持按小组查询" style="width: 120px;" /></td>
					<td style="text-align: left;"><label for="statisticDate">统计日期：</label>
						<input id="statisticDate" name="statisticDate"
						class="mini-datepicker" allowInput="false" emptyText="请选择统计日期"
						showOkButton="true" errorMode="none" style="width: 120px;" /></td>
					<td><input id="top" name="top" type="hidden" /></td>
					<td><input id="sortField" name="sortField" type="hidden" /></td>
					<td><input id="sortOrder" name="sortOrder" type="hidden" /></td>
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
					iconCls="icon-save" style="width: 65px;" onclick="onExport">导出</a>
					<label for="topNumber">排名<span style="color: red">(*)</span>：
				</label> <input id="topNumber" name="topNumber" class="mini-spinner"
					minValue="3" maxValue="100" required="true" style="width: 50px;" />
				</td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="sonarStatisticDg" class="mini-datagrid"
			url="<%=basePath%>sonarStatistic/sonarTopCountIncreasesReport.json"
			multiSelect="false" showEmptyText="true" emptyText="暂无数据！"
			totalField="data.totalElements" dataField="data.pageList" pageSize=50
			sizeList="[50,100]" showPager="false">
			<div property="columns">
				<div field="departmentName" align="center" headerAlign="center">部门</div>
				<div field="teamName" align="center" headerAlign="center">小组</div>
				<div field="statisticProjectName" align="center"
					headerAlign="center">项目</div>
				<div field="pm" align="center" headerAlign="center">开发负责人</div>
				<div field="blockerIncreases" align="center" headerAlign="center">Blocker增幅</div>
				<div field="criticalIncreases" align="center" headerAlign="center">Critical增幅</div>
				<div field="countIncreases" align="center" headerAlign="center">总增幅</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var searchForm = new mini.Form('searchForm');
		var sonarStatisticDg = mini.get("sonarStatisticDg");
		sonarStatisticDg.load();
		sonarStatisticDg.sortBy("countIncreases","desc");
		var topNumber = mini.get("topNumber");
		var statisticDate = mini.get("statisticDate");
		var department = mini.get("departmentId");
		var team = mini.get("teamId");
		
		topNumber.setValue(3);

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
			sonarStatisticDg.load(searchForm.getData(true, false));
			resetDefaultValue();
		}

		function onCancel() {
			var teamUrl = '<%=basePath%>team/teamListByDepartmentId.json';
			team.setUrl(teamUrl);
			searchForm.reset();
		}
		
		function onDepartmentChanged(e) {
            var departmentId=department.getValue();
            team.setValue();
            var url="<%=basePath%>team/teamListByDepartmentId.json?departmentId="+ departmentId;
			team.setUrl(url);
		}

		function onExport() {
			setDefaultValue();
			document.getElementById("top").value=topNumber.getValue();
			document.getElementById("sortField").value="count";
			document.getElementById("sortOrder").value="desc";
			if (isEmpty(department.getValue())) {
				mini.alert("请选择部门");
			} else if (isEmpty(statisticDate.getValue())) {
				mini.alert("请选择统计日期");
			} else {
				document.getElementById("exportForm").submit();
			}
			resetDefaultValue();
		}
	</script>
</body>
</html>