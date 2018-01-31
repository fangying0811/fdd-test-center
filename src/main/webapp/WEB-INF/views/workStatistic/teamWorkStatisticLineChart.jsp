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
					emptyText="支持按小组查询" style="width: 120px;"/></td>
				<td style="text-align: left;"><label for="versionType">版本类型：</label>
					<input id="versionType" name="type"
					class="mini-combobox"
					url="<%=basePath%>workStatistic/typeList.json"
					dataField="data" valueField="type" textField="typeText"
					allowInput="true" valueFromSelect="false" emptyText="支持按版本类型查询"
					style="width: 140px;" /></td>
				<td style="text-align: left;"><label for="statisticType">统计类型：</label>
					<input id="statisticType" name="statisticType"
					class="mini-combobox"
					url="<%=basePath%>workStatistic/statisticTypeList.json"
					dataField="data" valueField="statisticType" textField="statisticTypeText"
					allowInput="true" valueFromSelect="false" emptyText="支持按统计类型查询"
					style="width: 140px;" /></td>
			</tr>
		</table>
	</div>

	<div id="actionTb" class="mini-toolbar">
		<table style="width: 100%;">
			<tr>
				<td><a class="mini-button" iconCls="icon-search"
					style="width: 65px;" onclick="showChart">查看</a> <a
					class="mini-button" iconCls="icon-cancel" style="width: 65px;"
					onclick="onCancel">重置</a></td>
			</tr>
		</table>
	</div>

	<div class="mini-fit">
		<div id="chart" style="width: 100%;height:480px;"></div>
	</div>
	
	<script src="<%=basePath%>scripts/chart/echarts.min.js"
		type="text/javascript"></script>
	<script type="text/javascript">
		mini.parse();
		var form = new mini.Form('searchForm');
		var department = mini.get("departmentId");
		var team = mini.get("teamId");
		var versionType = mini.get("versionType");
		var statisticType = mini.get("statisticType");
		
		function setDefaultValue() {
			if (isEmpty(department.getValue())) {
				department.setValue(0);
			}
			if (isEmpty(team.getValue())) {
				team.setValue(0);
			}
			if (isEmpty(statisticType.getValue())) {
				statisticType.setValue(1);
			}
			if (isEmpty(versionType.getValue())) {
				versionType.setValue(0);
			}
		}
		
		function resetDefaultValue() {
			if (isEmpty(department.getValue())) {
				department.setValue();
			}
			if (isEmpty(team.getValue())) {
				team.setValue();
			}
			if (isEmpty(statisticType.getValue())) {
				statisticType.setValue(1);
			}
			if (isEmpty(versionType.getValue())) {
				versionType.setValue();
			}
		}

		showChart();

		function showChart(e) {
			setDefaultValue();
			// 基于准备好的dom，初始化echarts实例
			var myChart = echarts.init(document.getElementById('chart'));
			var messageId = mini.loading('处理中，请稍等......', '提示');
			$
					.ajax({
						url : '<%=basePath%>workStatistic/teamWorkStatisticEChartLine.json',
						type : 'post',
						data : form.getData(true, false),
						success : function(data) {
							mini.hideMessageBox(messageId);
							myChart.setOption(data.data);
						},
						dataType : 'json'
					});
			resetDefaultValue();
		}

		function onCancel() {
			form.reset();
			var teamUrl = '<%=basePath%>team/teamListByDepartmentId.json';
			team.setUrl(teamUrl);
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