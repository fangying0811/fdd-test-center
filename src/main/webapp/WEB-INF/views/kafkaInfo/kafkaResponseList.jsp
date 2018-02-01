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
			 	<td style="text-align:left;"><label for="team">小组：</label>
				<input id="teamId" name="kafkaInfo.team.teamId"
					class="mini-combobox"
					url="<%=basePath%>team/teamListByDepartmentId.json"
					dataField="data" valueField="teamId" textField="name" allowInput="true"
					onvaluechanged="onTeamChanged"
					emptyText="请选择小组" style="width: 120px;"/></td>
				<td style="text-align:left;"><label for="team">项目：</label>
				<input id="projectId" name="kafkaInfo.project.projectId"
					class="mini-combobox"
					url="<%=basePath%>projectInfo/projectInfoListByTeamId.json"
					dataField="data" valueField="projectId" textField="projectName" allowInput="true"
					onvaluechanged="onProjectChanged"
					emptyText="请选择项目" style="width: 140px;"/></td>
				<td style="text-align:left;"><label for="kafkaTopic">kafka_topic：</label>
				<input id="kafkaInfoId" name="kafkaInfo.kafkaInfoId"
					class="mini-combobox"
					url="<%=basePath%>kafkaInfo/kafkaInfoListByProjectId.json"
					dataField="data" valueField="kafkaInfoId" textField="kafkaTopic" allowInput="true"
					required="true" onvalidation="onValidation" requiredErrorText="请选择 Topic"
					emptyText="请选择 Topic" style="width: 200px;"
					onvaluechanged="onSearchHistory"/></td>
			</tr>
			<tr>
			<td style="text-align:left;" colspan="3"><label for="resource">requestJson：</label>
					<input id="requestJson" name="requestJson" class="mini-textarea"
					allowInput="true" emptyText="请输入kafka消息内容" 
					style="width:600px; height: 100px;"/></td>	
			</tr>
		</table>
	</div>
	<div id="actionTb" class="mini-toolbar">
		<table style="width:100%;">
			<tr>
				<td><a class="mini-button" iconCls="icon-search"
					style="width:65px;" onclick="onSend">发送</a>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="mini-fit">
		<div id="requirementInfoDg" class="mini-datagrid"
			url="<%=basePath%>kafkaResponse/kafkaResponseList.json"
			onselectionchanged="selectionChanged"
			multiSelect="false" showEmptyText="true" emptyText="暂无数据！"
			totalField="data.totalElements" dataField="data.pageList" sizeList="[5,10]">
			<div property="columns">
				<div type="checkcolumn"></div>
				<div field="kafkaInfo.kafkaTopic" align="center" headerAlign="center">topic名称</div>
				<div field="requestJson" align="center" headerAlign="center">请求消息</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		mini.parse();
		var searchForm = new mini.Form('searchForm');
		var team = mini.get("teamId");
		var project = mini.get("projectId");
		var kafkaInfo = mini.get("kafkaInfoId");
		var requestJson=mini.get("requestJson");
		var requirementInfoDg=mini.get("requirementInfoDg");
		
		var requirementInfoDg=mini.get("requirementInfoDg");
		
		function onTeamChanged(e) {
            var teamId=team.getValue();
            project.setValue();
            var url="<%=basePath%>projectInfo/projectInfoListByTeamId.json?teamId="+teamId;
            project.setUrl(url);
		}
		
		function onProjectChanged(e){
			var projectId=project.getValue();
			kafkaInfo.setValue();
			var url="<%=basePath%>kafkaInfo/kafkaInfoListByProjectId.json?projectId="+projectId;
			kafkaInfo.setUrl(url);
		}
		
		function onSend(e) {
			searchForm.validate();
			if (searchForm.isValid() == false)
				return;
			$.ajax({    
				url: '<%=basePath%>kafkaResponse/SendKafka',
						type: "post",
						data: searchForm.getData(true, false),
						success: function (data) {  
							if (data != '') {
								mini.alert("发送结果:"+data);
								onSearchHistory();
							}
							
			}});
		}
		
		function onSearchHistory(){
			searchForm.validate();
			if (searchForm.isValid() == false)
				return;
			requirementInfoDg.load(searchForm.getData(true, false));
		}
		
		//取消
		function onCancel() {
			searchForm.reset();
		}
		
		function selectionChanged(){
			var row = requirementInfoDg.getSelected();
			if (row) {
				var requestJsonValue = row.requestJson;
				requestJson.setValue(requestJsonValue);
			}
		}
	</script>
</body>
</html>