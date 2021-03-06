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
		<form id="exportForm" action="<%=basePath%>outstandingIssue/exportOutstandingIssue.json"
			method="post">
			<table>
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
				<td style="text-align: left;"><label for="issueId">bugID：</label>
						<input id="issueId" name="issueId" class="mini-textbox"
						emptyText="支持bugID查询" style="width: 120px;" /></td>
				</tr>
				<tr>
					<td style="text-align: left;"><label for="resolveStatus">解决状态：</label>
						<input id="resolveStatus" name="resolveStatus"
						class="mini-combobox"
						url="<%=basePath%>outstandingIssue/resolveStatusList.json"
						valueField="resolveStatus" textField="resolveStatusText"
						allowInput="true" emptyText="支持按解决状态查询"
						style="width: 140px;" /></td>
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
					onclick="onCancel">重置</a>
						<a class="mini-button" iconCls="icon-save" style="width: 65px;"
							onclick="onExport">导出</a>
				</td>
			</tr>
		</table>
	</div>
	<div id="actionTb" class="mini-toolbar">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;"><a class="mini-button"
					iconCls="icon-add" onclick="add">增加</a> <a class="mini-button"
					iconCls="icon-edit" onclick="edit">编辑</a> <a class="mini-button"
					iconCls="icon-search" onclick="outstandingIssueInfo">查看详情</a>
						<a class="mini-button" iconCls="icon-remove" onclick="remove">删除</a>
					</td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="outstandingIssueDg" class="mini-datagrid"
			url="<%=basePath%>outstandingIssue/outstandingIssueList.json"
			multiSelect="false" showEmptyText="true" emptyText="暂无数据！"
			totalField="data.totalElements" dataField="data.pageList" sizeList="[5,10]">
			<div property="columns">
				<div type="checkcolumn"></div>
				<div field="outstandingIssueId" align="center" headerAlign="center">编号</div>
				<!-- <div field="user.trueName" align="center" headerAlign="center">录入人员</div> -->
				<div field="department.name" align="center" headerAlign="center">部门</div>
				<div field="team.name" align="center" headerAlign="center">小组</div>
				<div field="project.projectName" align="center" headerAlign="center">项目</div>
				<div field="issueId" renderer="onIssueIdRenderer" align="center"
					headerAlign="center">关联bugID</div>
				<div field="issueDescription" align="center" headerAlign="center">问题描述</div>
				<div field="resolveStatusText" align="center" headerAlign="center">解决状态</div>
				<div field="createTime" align="center" headerAlign="center"
					dateFormat="yyyy-MM-dd HH:mm:ss">录入时间</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var searchForm = new mini.Form('searchForm');
		var outstandingIssueDg = mini.get("outstandingIssueDg");
		outstandingIssueDg.load();
		var department = mini.get("departmentId");
		var team = mini.get("teamId");
		var project = mini.get("projectId");
		var issueId = mini.get("issueId");
		var resolveStatus = mini.get("resolveStatus");
		
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
			if (isEmpty(issueId.getValue())) {
				issueId.setValue(0);
			}
			if (isEmpty(resolveStatus.getValue())) {
				resolveStatus.setValue(0);
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
			if (isEmpty(issueId.getValue())) {
				issueId.setValue();
			}
			if (isEmpty(resolveStatus.getValue())) {
				resolveStatus.setValue();
			}
		}

		function onIssueIdRenderer(e) {
			return '<a href="https://redmine.fangdd.net/issues/'+e.value+'" target="_blank">'
					+ e.value + '</a>';
		}

		//关键字模糊查询
		function onSearch() {
			setDefaultValue();
			outstandingIssueDg.load(searchForm.getData(true, false));
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
						url : '<%=basePath%>outstandingIssue/addOutstandingIssueUI',
						title : '添加遗留问题',
						width : 600,
						height : 400,
						showMaxButton : true,
						allowResize : false,
						ondestroy : function(action) {
							if (action == 'ok') {
								mini.alert("添加成功");
								outstandingIssueDg.reload();
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
			var row = outstandingIssueDg.getSelected();
			if (row) {
				var outstandingIssueId = row.outstandingIssueId;
				mini
						.open({
							url : '<%=basePath%>outstandingIssue/editOutstandingIssueUI',
							title : '修改遗留问题',
							width : 600,
							height : 500,
							showMaxButton : true,
							allowResize : false,
							onload : function() {
								var iframe = this.getIFrameEl();
								iframe.contentWindow.init(outstandingIssueId);
							},
							ondestroy : function(action) {
								if (action == 'ok') {
									mini.alert("修改成功");
									outstandingIssueDg.reload();
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

		function outstandingIssueInfo() {
			var row = outstandingIssueDg.getSelected();
			if (row) {
				var outstandingIssueId = row.outstandingIssueId;
				mini
						.open({
							url : '<%=basePath%>outstandingIssue/outstandingIssueInfoUI',
							title : '查看详情',
							width : 700,
							height : 600,
							showMaxButton : true,
							allowResize : false,
							onload : function() {
								var iframe = this.getIFrameEl();
								iframe.contentWindow.init(outstandingIssueId);
							},
							ondestroy : function(action) {
								outstandingIssueDg.reload();
							}
						});
			} else {
				mini.alert("请选中一条记录");
			}
		}

		function remove() {
			var row = outstandingIssueDg.getSelected();
			if (row) {
				mini
						.confirm(
								'您确定要删除吗？',
								'系统提示',
								function(action) {
									if (action == 'ok') {
										var outstandingIssueId = row.outstandingIssueId;
										$
												.ajax({
													url : '<%=basePath%>outstandingIssue/deleteOutstandingIssue.json',
													type : 'post',
													data : {
														outstandingIssueId : outstandingIssueId
													},
													success : function(data) {
														if (data.code==200 && data.data) {
															mini.alert("删除成功");
															outstandingIssueDg
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