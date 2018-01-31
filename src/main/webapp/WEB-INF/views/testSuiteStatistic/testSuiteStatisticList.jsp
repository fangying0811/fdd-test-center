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
					<input id="departmentId" name="departmentId" class="mini-combobox"
					url="<%=basePath%>department/departmentListAll.json"
					dataField="data" valueField="departmentId" textField="name"
					allowInput="true" emptyText="支持按部门查询" style="width: 120px;"
					onvaluechanged="onDepartmentChanged" /></td>
				<td style="text-align: left;"><label for="team">小组：</label> <input
					id="teamId" name="teamId" class="mini-combobox"
					url="<%=basePath%>team/teamListByDepartmentId.json"
					dataField="data" valueField="teamId" textField="name"
					allowInput="true" emptyText="支持按小组查询" style="width: 120px;"
					onvaluechanged="onTeamChanged" /></td>
				<td style="text-align: left;"><label for="project">项目：</label>
					<input id="serviceId" name="serviceId" class="mini-combobox"
					url="<%=basePath%>automationProject/automationProjectListByDepartmentId.json"
					dataField="data" valueField="id" textField="serviceName"
					allowInput="true" emptyText="支持按服务查询" style="width: 120px;" /></td>
				<td style="text-align: left;"><label for="status">状态 ：</label>
					<input id="testSuiteStatus" name="status" class="mini-combobox"
					url="<%=basePath%>testSuiteStatistic/statusList.json"
					dataField="data" valueField="status" textField="statusText"
					allowInput="true" emptyText="支持按状态查询" style="width: 120px;" /></td>
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
					<c:if
						test="${sessionScope.loginUser.isAdmin==0}">
						<a class="mini-button" iconCls="icon-remove" onclick="remove">删除</a>
					</c:if>
				</td>
			</tr>
		</table>
	</div>
	<div id="actionTb" class="mini-toolbar">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;">
				<a class="mini-button"
					iconCls="icon-search" onclick="detail">查看详情</a>
				<a class="mini-button"
					iconCls="icon-search" onclick="buildDetail">查看构建详情</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="statisticDg" class="mini-datagrid"
			url="<%=basePath%>testSuiteStatistic/testSuiteStatisticList.json"
			multiSelect="false" showEmptyText="true" emptyText="暂无数据！"
			totalField="data.totalElements" dataField="data.pageList" pageSize=15
			sizeList="[5,10,15]">
			<div property="columns">
				<div type="checkcolumn"></div>
				<div field="id" align="center" headerAlign="center">编号</div>
				<div field="departmentName" align="center" headerAlign="center">部门</div>
				<div field="teamName" align="center" headerAlign="center">小组</div>
				<!-- <div field="serviceId" align="center" headerAlign="center">服务id</div> -->
				<div field="serviceName" align="center" headerAlign="center">项目</div>
				<div field="serviceDes" align="center" headerAlign="center">项目描述</div>
				<div field="env" align="env" headerAlign="center">执行环境</div>
				<div field="statusText" align="center" headerAlign="center">状态</div>
				<div field="total" align="center" headerAlign="center">用例总数</div>
				<div field="passed" align="center" headerAlign="center">通过用例数</div>
				<div field="failed" align="center" headerAlign="center">失败用例数</div>
				<!-- <div field="skipped" align="center" headerAlign="center">跳过未执行用例数</div> -->
				<!-- <div field="startTime" align="center" headerAlign="center"
					dateFormat="yyyy-MM-dd HH:mm:ss">开始时间</div> -->
				<!-- <div field="endTime" align="center" headerAlign="center"
					dateFormat="yyyy-MM-dd HH:mm:ss">结束时间</div> -->
				<div field="durationTime" align="center" headerAlign="center">耗时</div>
				<div field="createTime" align="center" headerAlign="center"
					dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var searchForm = new mini.Form('searchForm');
		var statisticDg = mini.get("statisticDg");
		var department = mini.get("departmentId");
		var team = mini.get("teamId");
		var project = mini.get("serviceId");
		var testSuiteStatus = mini.get("testSuiteStatus");
		statisticDg.load();
		
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
			if (isEmpty(testSuiteStatus.getValue())) {
				testSuiteStatus.setValue(0);
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
			if (isEmpty(testSuiteStatus.getValue())) {
				testSuiteStatus.setValue();
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
			statisticDg.load(searchForm.getData(true, false));
			resetDefaultValue();
		}

		function onCancel() {
			var teamUrl = '<%=basePath%>team/teamListByDepartmentId.json';
			team.setUrl(teamUrl);
			var projectUrl = '<%=basePath%>automationProject/automationProjectListByDepartmentId.json';
			project.setUrl(projectUrl);
			searchForm.reset();
		}
			
		function onDepartmentChanged(e) {
	    	var departmentId=department.getValue();
	    	team.setValue();
	    	var url="<%=basePath%>team/teamListByDepartmentId.json?department.departmentId="+departmentId;
	    	team.setUrl(url);
		}
			
		function onTeamChanged(e) {
	    	var teamId=team.getValue();
	   		project.setValue();
	    	var url="<%=basePath%>automationProject/automationProjectListByTeamId.json?teamId="+ teamId;
			project.setUrl(url);
		}
		
		function detail() {
			var row = statisticDg.getSelected();
			if (row) {
				var id = row.id;
				mini
						.open({
							url : '<%=basePath%>testSuiteStatistic/testSuiteStatisticInfoUI',
							title : '查看测试集详情',
							width : 600,
							height : 550,
							showMaxButton : true,
							allowResize : false,
							onload : function() {
								var iframe = this.getIFrameEl();
								iframe.contentWindow.init(id);
							},
							ondestroy : function(action) {
								statisticDg.reload();
							}
						});
			} else {
				mini.alert("请选中一条记录");
			}
		}
		
		function buildDetail() {
			var row = statisticDg.getSelected();
			if (row) {
				var id = row.id;
				mini
						.open({
							url : '<%=basePath%>testStatistic/testStatisticListUI',
							title : '查看测试集构建详情',
							width : 1100,
							height : 550,
							showMaxButton : true,
							allowResize : false,
							onload : function() {
								var iframe = this.getIFrameEl();
								iframe.contentWindow.init(id);
							},
							ondestroy : function(action) {
								statisticDg.reload();
							}
						});
			} else {
				mini.alert("请选中一条记录");
			}
		}
		
		function remove() {
			var row = statisticDg.getSelected();
			if (row) {
				mini
						.confirm(
								'您确定要删除吗？',
								'系统提示',
								function(action) {
									if (action == 'ok') {
										var id = row.id;
										$
												.ajax({
													url : '<%=basePath%>testSuiteStatistic/deleteTestSuiteStatisticById.json',
													type : 'post',
													data : {
														id : id
													},
													success : function(data) {
														if (data.code==200 && data.data) {
															mini.alert("删除成功");
															statisticDg.reload();
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