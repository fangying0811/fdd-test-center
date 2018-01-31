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
<title>美利金融质量管理平台</title>
<script type="text/javascript" src="<%=basePath%>scripts/boot.js"></script>
</head>
<body>
	<div class="mini-fit">
		<center>
			<h2>质量管理Dashboard</h2>
			<!-- 
			<h4>上周sonar统计</h4>
		</center>
		<div id="teamSonarStatisticLastWeekDg" class="mini-datagrid"
			url="<%=basePath%>dashboard/teamSonarStatisticLastWeek.json"
			multiSelect="false" showEmptyText="true" emptyText="暂无数据！"
			totalField="data.totalElements" dataField="data.pageList" pageSize=50
			sizeList="[50,100]" showPager="false">
			<div property="columns">
				<div field="departmentName" align="center"
					headerAlign="center">部门</div>
				<div field="teamName" align="center" headerAlign="center">小组</div>
				<div header="Blocker" align="center" headerAlign="center">
					<div property="columns">
						<div field="blockerLastWeek" align="center" headerAlign="center">上周</div>
						<div field="blockerNowWeek" align="center" headerAlign="center">本周</div>
						<div field="blockerIncreases" align="center" headerAlign="center">增幅</div>
					</div>
				</div>
				<div header="Critical" align="center" headerAlign="center">
					<div property="columns">
						<div field="criticalLastWeek" align="center" headerAlign="center">上周</div>
						<div field="criticalNowWeek" align="center" headerAlign="center">本周</div>
						<div field="criticalIncreases" align="center" headerAlign="center">增幅</div>
					</div>
				</div>
				<div field="count" align="center" headerAlign="center">总数目</div>
				<div field="countIncreases" align="center" headerAlign="center">总增幅</div>
			</div>
		</div>
		<a
			href="<%=basePath%>sonarStatistic/sonarStatisticListReportUI">点击查看详情</a>
		<c:if test="${sessionScope.loginUser.isAdmin==0 || sessionScope.loginUser.isAdmin==1}">  
		<center>
			<h4>本周sonar统计</h4>
		</center>
		<div id="teamSonarStatisticCurrentWeekDg" class="mini-datagrid"
			url="<%=basePath%>dashboard/teamSonarStatisticCurrentWeek.json"
			multiSelect="false" showEmptyText="true" emptyText="暂无数据！"
			totalField="data.totalElements" dataField="data.pageList" pageSize=50
			sizeList="[50,100]" showPager="false">
			<div property="columns">
				<div field="departmentName" align="center"
					headerAlign="center">部门</div>
				<div field="teamName" align="center" headerAlign="center">小组</div>
				<div header="Blocker" align="center" headerAlign="center">
					<div property="columns">
						<div field="blockerLastWeek" align="center" headerAlign="center">上周</div>
						<div field="blockerNowWeek" align="center" headerAlign="center">本周</div>
						<div field="blockerIncreases" align="center" headerAlign="center">增幅</div>
					</div>
				</div>
				<div header="Critical" align="center" headerAlign="center">
					<div property="columns">
						<div field="criticalLastWeek" align="center" headerAlign="center">上周</div>
						<div field="criticalNowWeek" align="center" headerAlign="center">本周</div>
						<div field="criticalIncreases" align="center" headerAlign="center">增幅</div>
					</div>
				</div>
				<div field="count" align="center" headerAlign="center">总数目</div>
				<div field="countIncreases" align="center" headerAlign="center">总增幅</div>
			</div>
		</div>
		<a
			href="<%=basePath%>sonarStatistic/sonarStatisticListReportUI">点击查看详情</a>
		</c:if>
		
		<div id="chart_team_sonar_count" style="width: 100%;height:250px;"></div>
		<a href="<%=basePath%>sonarStatistic/teamSonarStatisticLineChartUI">点击查看详情</a></td>
		 
		<center>
			<h4>上周线上问题统计</h4>
		</center>
		<div id="onlineIssueLastWeekDg" class="mini-datagrid"
			url="<%=basePath%>dashboard/onlineIssueLastWeek.json"
			multiSelect="false" showEmptyText="true" emptyText="暂无数据！"
			totalField="data.totalElements" dataField="data.pageList" sizeList="[5,10]"
			showPager="false">
			<div property="columns">
				<div field="department.name" align="center" headerAlign="center">部门</div>
				<div field="team.name" align="center" headerAlign="center">小组</div>
				<div field="project.projectName" align="center" headerAlign="center">项目</div>
				<div field="issueId" renderer="onIssueIdRenderer" align="center"
					headerAlign="center">关联bugID</div>
				<div field="issueDescription" align="center" headerAlign="center">问题描述</div>
				<div field="resolveStatusText" align="center" headerAlign="center">解决状态</div>
			</div>
		</div>
		<a href="<%=basePath%>onlineIssue/onlineIssueListUI">点击查看详情</a>
		-->
		<center>
			<h4>本周线上问题统计</h4>
		</center>
		<div id="onlineIssueDgCurrentWeek" class="mini-datagrid"
			url="<%=basePath%>dashboard/onlineIssueCurrentWeek.json"
			multiSelect="false" showEmptyText="true" emptyText="暂无数据！"
			totalField="data.totalElements" dataField="data.pageList" sizeList="[5,10]"
			showPager="false">
			<div property="columns">
				<div field="department.name" align="center" headerAlign="center">部门</div>
				<div field="team.name" align="center" headerAlign="center">小组</div>
				<div field="project.projectName" align="center" headerAlign="center">项目</div>
				<div field="issueId" renderer="onIssueIdRenderer" align="center"
					headerAlign="center">关联bugID</div>
				<div field="issueDescription" align="center" headerAlign="center">问题描述</div>
				<div field="resolveStatusText" align="center" headerAlign="center">解决状态</div>
			</div>
		</div>
		<!-- 
		<a href="<%=basePath%>onlineIssue/onlineIssueListUI">点击查看详情</a>
		 
		<div id="chart_online_issues" style="width: 100%;height:250px;"></div>
		<a href="<%=basePath%>workStatistic/teamWorkStatisticLineChartUI">点击查看详情</a></td>
		-->
		<center>
			<h4>本周项目发布统计</h4>
		</center>
		<div id="workStatisticCurrentWeekDg" class="mini-datagrid"
			url="<%=basePath%>dashboard/projectWorkStatisticCurrentWeek.json"
			multiSelect="false" showEmptyText="true" emptyText="暂无数据！"
			totalField="data.totalElements" dataField="data.pageList" pageSize=50
			sizeList="[50,100]" showPager="false">
			<div property="columns">
				<div field="department.name" align="center" headerAlign="center">部门</div>
				<div field="team.name" align="center" headerAlign="center">小组</div>
				<div field="project.projectName" align="center" headerAlign="center">项目</div>
				<div field="versionCount" align="center" headerAlign="center">上线版本数</div>
				<div field="bugCount" align="center" headerAlign="center">BUG总数</div>
				<div field="bugCritical" align="center" headerAlign="center">严重bug数</div>
			</div>
		</div>
		<a
			href="<%=basePath%>projectWorkStatistic/projectWorkStatisticListUI">点击查看详情</a>
	</div>
	<script src="<%=basePath%>scripts/chart/echarts.min.js"
		type="text/javascript"></script>
	<script type="text/javascript">
		mini.parse();
		var teamSonarStatisticLastWeekDg = mini
				.get("teamSonarStatisticLastWeekDg");
		var teamSonarStatisticCurrentWeekDg = mini.get("teamSonarStatisticCurrentWeekDg");
		var workStatisticCurrentWeekDg = mini.get("workStatisticCurrentWeekDg");
		var onlineIssueLastWeekDg = mini.get("onlineIssueLastWeekDg");
		var onlineIssueDgCurrentWeek = mini.get("onlineIssueDgCurrentWeek");
		teamSonarStatisticLastWeekDg.load();
		if(teamSonarStatisticCurrentWeekDg!=null){
			teamSonarStatisticCurrentWeekDg.load();
		}
		var jsonUrl="<%=basePath%>sonarStatistic/teamSonarStatisticLineChart.json?statisticType=1&team.teamId=0";
		showChart(jsonUrl,"chart_team_sonar_count");
		workStatisticCurrentWeekDg.load();
		onlineIssueLastWeekDg.load();
		onlineIssueDgCurrentWeek.load();
		jsonUrl="<%=basePath%>workStatistic/teamWorkStatisticEChartLine.json?statisticType=3&team.teamId=0";
		showChart(jsonUrl,"chart_online_issues");

		function onIssueIdRenderer(e) {
			return '<a href="https://redmine.fangdd.net/issues/'+e.value+'" target="_blank">'
					+ e.value + '</a>';
		}
		
		function showChart(jsonUrl,chartId) {
			// 基于准备好的dom，初始化echarts实例
			var myChart = echarts.init(document.getElementById(chartId));
			$
					.ajax({
						url : jsonUrl,
						type : 'get',
						success : function(data) {
							myChart.setOption(data.data);
						},
						dataType : 'json'
					});
		}
	</script>
</body>
</html>
