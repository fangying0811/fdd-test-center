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
				<td style="text-align: left;"><label for="kafkaTopic">kafka_topic 名称：</label>
					<input id="kafkaTopic" name="kafkaTopic" class="mini-textbox"
					emptyText="支持topic名称查询" style="width: 120px;" /></td>
				<td style="text-align: left;"><label for="project"> 小组：</label>
					<input id="teamId" name="team.teamId"
					class="mini-combobox"
					url="<%=basePath%>team/teamListByDepartmentId.json"
					dataField="data" valueField="teamId" textField="name" allowInput="true"
					onvaluechanged="onTeamChanged" 
					emptyText="支持按小组查询" style="width: 120px;"/></td>
				<td style="text-align: left;"><label for="project"> 项目：</label>
					<input id="projectId" name="project.projectId"
					class="mini-combobox"
					url="<%=basePath%>projectInfo/projectInfoListByTeamId.json"
					dataField="data" valueField="projectId" textField="projectName" allowInput="true"
					emptyText="支持按项目查询" style="width: 120px;"/></td>
			</tr>
			<tr>
				<td><a class="mini-button" iconCls="icon-search"
					style="width: 71px;" onclick="onSearch">查询</a> <a
					class="mini-button" iconCls="icon-cancel" style="width: 71px;"
					onclick="onCancel">重置</a> <span
					style="display: inline-block; width: 25px"></span></td>
			</tr>
		</table>
	</div>
	<div id="actionTb" class="mini-toolbar">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;">
						<a class="mini-button" iconCls="icon-add" onclick="add">增加</a>
						<a class="mini-button" iconCls="icon-edit" onclick="edit">编辑</a>
						<a class="mini-button" iconCls="icon-remove" onclick="remove">删除</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="kafkaInfoDg" class="mini-datagrid"
			url="<%=basePath%>kafkaInfo/kafkaInfoList.json"
			multiSelect="false" showEmptyText="true" emptyText="暂无数据！"
			totalField="data.totalElements" dataField="data.pageList" sizeList="[5,10]">
			<div property="columns">
				<div type="checkcolumn"></div>
				<div field="kafkaInfoId" align="center" headerAlign="center">ID</div>
				<div field="team.name" align="center" headerAlign="center">小组</div>
				<div field="project.projectName" align="center" headerAlign="center">项目</div>
				<div field="kafkaTopic" align="center" headerAlign="center">kafka_topic</div>
				<div field="createTime" align="center" headerAlign="center"
					dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var searchForm = new mini.Form('searchForm');
		var team = mini.get("teamId");
		var project = mini.get("projectId");
		var kafkaInfoDg=mini.get("kafkaInfoDg");
		kafkaInfoDg.load();
		
		function setDefaultValue() {
			if (isEmpty(team.getValue())) {
				team.setValue(0);
			}
			if (isEmpty(project.getValue())) {
				project.setValue(0);
			}
		}
		
		function resetDefaultValue() {
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
			kafkaInfoDg.load(searchForm.getData(true, false));
			resetDefaultValue();
		}
		//取消
		function onCancel() {
			searchForm.reset();
		}
		
		//添加
		function add() {
			mini.open({
						url : '<%=basePath%>kafkaInfo/addKafkaInfoUI',
						title : '添加 kafka',
						width : 800,
						height : 200,
						showMaxButton : true,
						allowResize : false,
						ondestroy : function(action) {
							if (action == 'ok') {
								mini.alert("添加成功");
								kafkaInfoDg.reload();
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
		
		//编辑
		function edit() {
			var row = kafkaInfoDg.getSelected();
			if (row) {
				var kafkaInfoId = row.kafkaInfoId;
				mini
						.open({
							url : '<%=basePath%>kafkaInfo/editKafkaInfoUI',
							title : '修改kafkaTopic',
							width : 800,
							height : 200,
							showMaxButton : true,
							allowResize : false,
							onload : function() {
								var iframe = this.getIFrameEl();
								iframe.contentWindow.init(kafkaInfoId);
							},
							ondestroy : function(action) {
								if (action == 'ok') {
									mini.alert("修改成功");
									kafkaInfoDg.reload();
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
		
		function remove() {
			var row = kafkaInfoDg.getSelected();
			if (row) {
				mini.confirm(
								'您确定要删除吗？',
								'系统提示',
								function(action) {
									if (action == 'ok') {
										var kafkaInfoId = row.kafkaInfoId;
										$.ajax({
													url : '<%=basePath%>kafkaInfo/deleteKafka.json',
													type : 'post',
													data : {
														kafkaInfoId : kafkaInfoId
													},
													success : function(data) {
														if (data.code==200 && data.data) {
															mini.alert("删除成功");
															kafkaInfoDg.reload();
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
		
		function onTeamChanged(e) {
            var teamId=team.getValue();
            project.setValue();
            var url="<%=basePath%>projectInfo/projectInfoListByTeamId.json?teamId="+teamId;
            project.setUrl(url);
		}
	</script>
</body>
</html>