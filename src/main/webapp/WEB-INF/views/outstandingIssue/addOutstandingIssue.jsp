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
	<div id="addForm" style="padding:15px">
		<table border="0" style="width: 100%">
			<tr>
				<td style="text-align: right;"><label for="team">小组<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="teamId" name="team.teamId" class="mini-combobox"
					url="<%=basePath%>team/teamListByDepartmentId.json"
					dataField="data" valueField="teamId" textField="name" allowInput="true"
					valueFromSelect="true" required="true" onvalidation="onValidation"
					errorMode="none" requiredErrorText="请选择小组" emptyText="请选择小组"
					style="width:300px;" onvaluechanged="onTeamChanged" /></td>
				<td><div id="teamId_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="project">项目<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="projectId" name="project.projectId"
					class="mini-combobox" dataField="data" valueField="projectId"
					textField="projectName" allowInput="true" valueFromSelect="true"
					required="true" onvalidation="onValidation" errorMode="none"
					requiredErrorText="请选择项目" emptyText="请选择项目" style="width:300px;" /></td>
				<td><div id="projectId_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="issueId">关联bugID<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="issueId" name="issueId" class="mini-textbox"
					emptyText="请输入关联bugID" vtype="int;remote" required="true"
					onvalidation="onValidation" errorMode="none"
					requiredErrorText="关联bugID不能为空" style="width:300px;" /></td>
				<td><div id="issueId_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="resolveStatus">解决状态<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="resolveStatus" name="resolveStatus"
					class="mini-radiobuttonlist"
					url="<%=basePath%>outstandingIssue/resolveStatusList.json"
					valueField="resolveStatus" textField="resolveStatusText"
					required="true" onvalidation="onValidation" errorMode="none"
					requiredErrorText="请选择解决状态" style="width:300px;" /></td>
				<td><div id="resolveStatus_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="remark">备注：
				</label></td>
				<td><input id="remark" name="remark"
					class="mini-textarea"
					emptyText="请输入备注(如标记计划解决时间，若不解决，需备注不解决的原因等)"
					style="width:300px;height:150px;" /></td>
				<td><div id="remark_error"></div></td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<div class="mini-toolbar"
			style="text-align:center;padding-top:8px;padding-bottom:8px;"
			borderStyle="border:0;">
			<a class="mini-button" iconCls="icon-add" style="width:71px;"
				onclick="onAdd">添加</a> <span style="display:inline-block;width:25px"></span>
			<a class="mini-button" iconCls="icon-cancel" style="width:71px;"
				onclick="onCancel">取消</a>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var form = new mini.Form('addForm');
		var team = mini.get("teamId");
		var project = mini.get("projectId");

		function onTeamChanged(e) {
            var teamId=team.getValue();
            project.setValue();
            var url="<%=basePath%>projectInfo/projectInfoListByTeamId.json?teamId="+ teamId;
			project.setUrl(url);
			project.select(0);
		}

		function onAdd(e) {
			form.validate();
			if (form.isValid() == false)
				return;

			var messageId = mini.loading('处理中，请稍等......', '提示');

			$
					.ajax({
						url : '<%=basePath%>outstandingIssue/addOutstandingIssue.json',
						type : 'post',
						data : form.getData(true, false),
						success : function(data) {
							mini.hideMessageBox(messageId);
							if (data.code==200 && data.data) {
								closeWindow('ok');
							} else {
								closeWindow(data.msg);
							}
						},
						dataType : 'json'
					});
		}

		function onCancel() {
			closeWindow('close');
			return;
		}
		
		/*自定义vtype*/
		mini.VTypes["remoteErrorText"] = "bugId已经存在，不可重复添加";
		mini.VTypes["remote"] = function(issueId) {
			var flag = false;
			$.ajax({
				async : false,/*同步请求*/
				url : '<%=basePath%>outstandingIssue/existOutstandingIssue.json',
				type : 'post',
				data : {
					outstandingIssueId : issueId
				},
				success : function(data) {
					flag = data.data;
				},
				dataType : 'json'
			});
			return flag;
		};
	</script>
</body>
</html>