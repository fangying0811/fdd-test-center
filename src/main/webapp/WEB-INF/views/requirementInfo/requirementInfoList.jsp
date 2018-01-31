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
		<form id="exportForm" action="<%=basePath%>requirementInfo/exportRequirementInfo.json"
			method="post">
			<table>
				<tr>
					<!-- <td style="text-align:left;"><label for="username">录入人员：</label>
						<input id="username" name="user.trueName" class="mini-textbox"
						emptyText="支持录入人员模糊查询" style="width:120px;" /></td> -->
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
					<td style="text-align:left;"><label for="startTime">录入起始时间：</label>
						<input id="startTime" name="startTime" class="mini-datepicker"
						allowInput="false" emptyText="请选择起始时间" showOkButton="true"
						errorMode="none" style="width:120px;" /></td>
					<td style="text-align:left;"><label for="endTime">录入结束时间：</label>
						<input id="endTime" name="endTime" class="mini-datepicker"
						allowInput="false" emptyText="请选择结束时间" showOkButton="true"
						errorMode="none" style="width:120px;" /></td>
				</tr>
			</table>
			<table>
				<tr>
					<td style="text-align:left;"><label for="planTestStartTime">计划版本测试起始时间：</label>
						<input id="planTestStartTime" name="planTestStartTime"
						class="mini-datepicker" allowInput="false"
						emptyText="请选择计划版本测试起始时间" showOkButton="true" errorMode="none"
						style="width:180px;" /></td>
					<td style="text-align:left;"><label for="planTestEndTime">计划版本测试结束时间：</label>
						<input id="planTestEndTime" name="planTestEndTime"
						class="mini-datepicker" allowInput="false"
						emptyText="请选择计划版本测试结束时间" showOkButton="true" errorMode="none"
						style="width:180px;" /></td>
				</tr>
				<tr>
					<td style="text-align:left;"><label
						for="versionReleaseStartTime">计划版本发布起始时间：</label> <input
						id="versionReleaseStartTime" name="versionReleaseStartTime"
						class="mini-datepicker" allowInput="false"
						emptyText="请选择计划版本发布起始时间" showOkButton="true" errorMode="none"
						style="width:180px;" /></td>
					<td style="text-align:left;"><label
						for="versionReleaseEndTime">计划版本发布结束时间：</label> <input
						id="versionReleaseEndTime" name="versionReleaseEndTime"
						class="mini-datepicker" allowInput="false"
						emptyText="请选择计划版本发布结束时间" showOkButton="true" errorMode="none"
						style="width:180px;" /></td>
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
							onclick="onExport">导出</a>
					</td>
			</tr>
		</table>
		</form>
	</div>
	<div id="actionTb" class="mini-toolbar">
		<table style="width:100%;">
			<tr>
				<td style="width:100%;"><a class="mini-button"
					iconCls="icon-add" onclick="add">增加</a> <a class="mini-button"
					iconCls="icon-edit" onclick="edit">编辑</a> <a class="mini-button"
					iconCls="icon-search" onclick="requirementInfo">查看需求详情</a> <s:if
						test="#session.loginUser.isAdmin==0 || #session.loginUser.isAdmin==1">
						<a class="mini-button" iconCls="icon-remove" onclick="remove">删除</a>
					</s:if></td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="requirementInfoDg" class="mini-datagrid"
			url="<%=basePath%>requirementInfo/requirementInfoList.json"
			multiSelect="false" showEmptyText="true" emptyText="暂无数据！"
			totalField="data.totalElements" dataField="data.pageList" sizeList="[5,10]">
			<div property="columns">
				<div type="checkcolumn"></div>
				<div field="requirementId" align="center" headerAlign="center">编号</div>
				<!-- <div field="user.trueName" align="center" headerAlign="center">录入人员</div> -->
				<div field="department.name" align="center" headerAlign="center">部门</div>
				<div field="team.name" align="center" headerAlign="center">小组</div>
				<div field="project.projectName" align="center" headerAlign="center">项目</div>
				<div field="versionInfo" align="center" headerAlign="center">版本信息</div>
				<div field="resource" align="center" headerAlign="center">责任人</div>
				<div field="planStartTime" align="center" headerAlign="center"
					dateFormat="yyyy-MM-dd">计划测试开始时间</div>
				<div field="versionReleaseTime" align="center" headerAlign="center"
					dateFormat="yyyy-MM-dd">计划版本发布时间</div>
				<div field="createTime" align="center" headerAlign="center"
					dateFormat="yyyy-MM-dd HH:mm:ss">录入时间</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var searchForm = new mini.Form('searchForm');
		var requirementInfoDg = mini.get("requirementInfoDg");
		requirementInfoDg.load();
		var department = mini.get("departmentId");
		var team = mini.get("teamId");
		var project = mini.get("projectId");

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
			requirementInfoDg.load(searchForm.getData(true, false));
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
			var planTestStartTime = mini.get("planTestStartTime");
			var planTestEndTime = mini.get("planTestEndTime");
			var versionReleaseStartTime = mini.get("versionReleaseStartTime");
			var versionReleaseEndTime = mini.get("versionReleaseEndTime");

			if (departmentId.getValue() == null || departmentId.getValue() <= 0) {
				mini.alert("请选择部门");
			} else if ((startTime.getValue() == null || startTime.getValue() == "")
					&& (planTestStartTime.getValue() == null || planTestStartTime
							.getValue() == "")
					&& (versionReleaseStartTime.getValue() == null || versionReleaseStartTime
							.getValue() == "")) {
				mini.alert("录入起始时间、计划版本测试起始时间或计划版本发布起始时间请至少选择一项");
			} else if ((startTime.getValue() != null && startTime.getValue() != "")
					&& (endTime.getValue() == null || endTime.getValue() == "")) {
				mini.alert("请选择录入结束时间");
			} else if ((planTestStartTime.getValue() != null && planTestStartTime
					.getValue() != "")
					&& (planTestEndTime.getValue() == null || planTestEndTime
							.getValue() == "")) {
				mini.alert("请选择计划版本测试结束时间");
			} else if ((versionReleaseStartTime.getValue() != null && versionReleaseStartTime
					.getValue() != "")
					&& (versionReleaseEndTime.getValue() == null || versionReleaseEndTime
							.getValue() == "")) {
				mini.alert("请选择计划版本发布结束时间");
			} else {
				document.getElementById("exportForm").submit();
			}
			resetDefaultValue();
		}

		function add() {
			mini
					.open({
						url : '<%=basePath%>requirementInfo/addRequirementInfoUI',
						title : '添加测试需求',
						width : 800,
						height : 500,
						showMaxButton : true,
						allowResize : false,
						ondestroy : function(action) {
							if (action == 'ok') {
								mini.alert("添加成功");
								requirementInfoDg.reload();
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
			var row = requirementInfoDg.getSelected();
			if (row) {
				var requirementId = row.requirementId;
				mini
							.open({
								url : '<%=basePath%>requirementInfo/editRequirementInfoUI',
								title : '修改测试需求',
								width : 800,
								height : 500,
								showMaxButton : true,
								allowResize : false,
								onload : function() {
									var iframe = this.getIFrameEl();
									iframe.contentWindow.init(requirementId);
								},
								ondestroy : function(action) {
									if (action == 'ok') {
										mini.alert("修改成功");
										requirementInfoDg.reload();
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

		function requirementInfo() {
			var row = requirementInfoDg.getSelected();
			if (row) {
				var requirementId = row.requirementId;
				mini
						.open({
							url : '<%=basePath%>requirementInfo/requirementInfoUI',
							title : '查看测试需求',
							width : 800,
							height : 550,
							showMaxButton : true,
							allowResize : false,
							onload : function() {
								var iframe = this.getIFrameEl();
								iframe.contentWindow.init(requirementId);
							},
							ondestroy : function(action) {
								requirementInfoDg.reload();
							}
						});
			} else {
				mini.alert("请选中一条记录");
			}
		}

		function remove() {
			var row = requirementInfoDg.getSelected();
			if (row) {
				mini
						.confirm(
								'您确定要删除吗？',
								'系统提示',
								function(action) {
									if (action == 'ok') {
										var requirementId = row.requirementId;
										$
												.ajax({
													url : '<%=basePath%>requirementInfo/deleteRequirementInfo.json',
													type : 'post',
													data : {
														requirementId : requirementId
													},
													success : function(data) {
														if (data.code==200 && data.data) {
															mini.alert("删除成功");
															requirementInfoDg
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