<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
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
	<form id="exportForm" action="<%=basePath%>redmine/exportProjectClassification.json"
			method="get">
		<table>
			<tr>
				<td style="text-align: left;"><label for="department">部门：</label>
					<input id="departmentId" name="departmentId" class="mini-combobox"
					url="<%=basePath%>department/departmentListAll.json"
					dataField="data" valueField="departmentId" textField="name"
					allowInput="true" emptyText="支持按部门查询" style="width: 120px;"
					onvaluechanged="onDepartmentChanged" /></td>
				<td style="text-align: left;"><label for="team">小组：</label> <input
					id="teamId" name=teamId class="mini-combobox"
					url="<%=basePath%>team/teamListByDepartmentId.json"
					dataField="data" valueField="teamId" textField="name"
					allowInput="true" emptyText="支持按小组查询" style="width: 120px;"
					onvaluechanged="onTeamChanged" /></td>
				<td style="text-align: left;"><label for="project">项目：</label>
					<input id="projectId" name="testcenterProjectId"
					class="mini-combobox"
					url="<%=basePath%>projectInfo/projectInfoListByDepartmentId.json"
					dataField="data" valueField="projectId" textField="projectName"
					allowInput="true" emptyText="支持按项目查询" style="width: 120px;" 
					onvaluechanged="onProjectChanged"/></td>
				<td style="text-align: left;"><label for="versions">版本：</label>
					<input id="versionId" name="versionId"
					class="mini-combobox"
					dataField="data" valueField="id" textField="name"
					allowInput="true" emptyText="支持按版本查询" style="width: 120px;"/td>
					</td>
			<tr>
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
					<a class="mini-button" iconCls="icon-save" style="width: 65px;"
							onclick="onExport">导出</a>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="redmineProject" class="mini-datagrid"
			url="<%=basePath%>redmine/projectClassification.json"
			multiSelect="false" showEmptyText="true" emptyText="暂无数据！"
			showSummaryRow="true"
			ondrawsummarycell="onDrawSummaryCell"
			showPager="false"
			totalField="data.totalElements" dataField="data.pageList" pageSize=10
			sizeList="[5,10]">
			<div property="columns">
		        <div field="testcenterProjectName" align="center" headerAlign="center">项目</div>
				<div name = "versionName" field="versionName" align="center" headerAlign="center">版本</div>
				<div field="redmineProjectName" align="center" headerAlign="center">分类</div>
				<div field="unresolved" align="center" headerAlign="center">未解决</div>
				<div field="resolved" align="center" headerAlign="center">已解决</div>
				<div field="closed" align="center" headerAlign="center">已关闭</div>
				<div field="total" summaryType="sum" align="center" headerAlign="center">合计</div>
				
			</div>
		</div>
	</div>
<script type="text/javascript">
		mini.parse();
		var searchForm = new mini.Form('searchForm');
		var redmineProject = mini.get("redmineProject");
		var team = mini.get("teamId");
		var department = mini.get("departmentId");
		var project = mini.get("projectId");
		var versions = mini.get("versionId");
		project.select(0);
		initVersion();
		redmineProject.load(searchForm.getData(true, false));
		resetDefaultValue();
		showVersionColumn();
		
		function initVersion() {
            var projectId=project.getValue();
            versions.setValue();
            var url="<%=basePath%>redmine/redmineVersionList.json?testcenterProjectId="+ projectId;
            versions.setUrl(url);	
            versions.setValue(0);
           }
		
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
			if (isEmpty(versions.getValue())) {
				versions.setValue(0);
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
			if (isEmpty(versions.getValue())) {
				versions.setValue();
			}
		}

		function onLoad(e) {
			var grid = e.sender;
			grid.setShowPager(grid.totalCount > 0);
			
		}
		
		//关键字模糊查询
		function onSearch() {
			if (isEmpty(project.getValue())) {
				project.select(0);
			}
			showVersionColumn();
			setDefaultValue();
			redmineProject.load(searchForm.getData(true, false));
			resetDefaultValue();
			
		}

		function onCancel() {
            var url='<%=basePath%>team/teamListByDepartmentId.json';
            team.setUrl(url);
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
		
		function onProjectChanged(e) {
            var projectId=project.getValue();
            versions.setValue();
            var url="<%=basePath%>redmine/redmineVersionList.json?testcenterProjectId="+ projectId;
            versions.setUrl(url);
		}
		
		function showVersionColumn() {        
			//判断版本是否为空。为空则隐藏版本列,不为空则显示版本列
			if (isEmpty(versions.getValue())) {
				redmineProject.hideColumn("versionName");
				}
			else{
				redmineProject.showColumn("versionName");
			}
	        }
		
		 function onDrawSummaryCell(e) {
	            var result = e.result;
	            var grid = e.sender;
	          //客户端汇总计算
	            if (e.field == "total") {
	                e.cellHtml ="<center>BUG总数:" + e.cellHtml+"</center>"           	
	            }
	        }
		 function onExport() {
				setDefaultValue();
				var projectId = mini.get("projectId");
				if (projectId.getValue() == null || projectId.getValue() <= 0) {
					mini.alert("请选择项目");
				} else {
					document.getElementById("exportForm").submit();
				}
				resetDefaultValue();
			}
		
	</script>
</body>
</html>