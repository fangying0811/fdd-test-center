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
					class="mini-combobox"
					dataField="data" valueField="projectId" textField="projectName" allowInput="true"
					valueFromSelect="true" required="true" onvalidation="onValidation"
					errorMode="none" requiredErrorText="请选择项目" emptyText="请选择项目"
					style="width:300px;" /></td>
				<td><div id="projectId_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="versionInfo">版本信息<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="versionInfo" name="versionInfo"
					class="mini-textarea" emptyText="请输入版本信息" vtype="maxLength:30"
					required="true" onvalidation="onValidation" errorMode="none"
					requiredErrorText="版本信息不能为空" style="width:300px;height:40px;" /></td>
				<td><div id="versionInfo_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="resource">责任人<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="resource" name="resource"
					class="mini-buttonedit" allowInput="false" required="true"
					onbuttonclick="onResource" onvalidation="onValidation"  
					errorMode="none" emptyText="请选择责任人"
					requiredErrorText="责任人不能为空" style="width:300px;" /></td>
				<td><div id="resource_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="requirementInfo">需求信息<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="requirementInfo" name="requirementInfo"
					class="mini-textarea" emptyText="请输入需求信息" vtype="remote"
					required="true" onvalidation="onValidation" errorMode="none"
					requiredErrorText="需求信息不能为空" style="width:300px;height:150px;" /></td>
				<td><div id="requirementInfo_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="planStartTime">计划测试开始时间<span
						style="color:red">(*)</span>：
				</label></td>
				<td>
					<div id="status" name="status" class="mini-radiobuttonlist"
						dataField="data" valueField="status" textField="statusText"
						url="<%=basePath%>requirementInfo/statusList.json"
						required="true" allowInput="false" onvaluechanged="onValueChanged"
						onvalidation="onValidation" errorMode="none"
						requiredErrorText="请选择计划测试开始时间"></div> <input id="planStartTime"
					name="planStartTime" class="mini-datepicker" emptyText="请选择计划测试开始时间"
					showOkButton="true" readOnly="true" required="false"
					onvalidation="onValidation" errorMode="none"
					requiredErrorText="计划测试开始时间不能为空" style="width:300px;" />
				</td>
				<td>
					<div id="status_error"></div>
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="planStartTime">计划版本发布时间<span
						style="color:red">(*)</span>：
				</label></td>
				<td>
					<div id="versionStatus" name="status" class="mini-radiobuttonlist"
						dataField="data" valueField="status" textField="statusText"
						url="<%=basePath%>requirementInfo/statusList.json"
						required="true" allowInput="false"
						onvaluechanged="onVersionValueChanged" onvalidation="onValidation"
						errorMode="none" requiredErrorText="请选择计划版本发布时间"></div> <input
					id="versionReleaseTime" name="versionReleaseTime"
					class="mini-datepicker" emptyText="请选择计划版本发布时间" showOkButton="true"
					readOnly="true" required="false" onvalidation="onValidation"
					errorMode="none" requiredErrorText="计划版本发布时间不能为空"
					style="width:300px;" />
				</td>
				<td>
					<div id="versionStatus_error"></div>
				</td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</div>
	<div class="mini-toolbar"
		style="text-align:center;padding-top:8px;padding-bottom:8px;"
		borderStyle="border:0;">
		<a class="mini-button" iconCls="icon-add" style="width:71px;"
			onclick="onAdd">添加</a> <span style="display:inline-block;width:25px"></span>
		<a class="mini-button" iconCls="icon-cancel" style="width:71px;"
			onclick="onCancel">取消</a>
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
		
		function onResource(e) {
			var btnEdit = this;
			mini.open({
				url : '<%=basePath%>user/selectUserUI',
				title : "选择用户",
				width : 700,
				height : 600,
				ondestroy : function(action) {
					if (action == "ok") {
						var iframe = this.getIFrameEl();
						var data = iframe.contentWindow.getData();
						data = mini.clone(data);
						btnEdit.setValue(data.text);
						btnEdit.setText(data.text);
					}
				}
			});
		}

		function onAdd(e) {
			form.validate();
			if (form.isValid() == false)
				return;

			var messageId = mini.loading('处理中，请稍等......', '提示');

			var versionInfo = mini.get("versionInfo");
			versionInfo.setValue(TransferString(versionInfo.getValue()));

			$.ajax({
				url : '<%=basePath%>requirementInfo/addRequirementInfo.json',
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

		function onValueChanged() {
			var status = mini.get("status");
			var planStartTime = mini.get("planStartTime");
			if (status.getValue() == 0) {
				planStartTime.readOnly = true;
				planStartTime.setValue("");
			} else {
				planStartTime.readOnly = false;
			}
			return;
		}
		
		function onVersionValueChanged() {
			var versionStatus = mini.get("versionStatus");
			var versionReleaseTime = mini.get("versionReleaseTime");
			if (versionStatus.getValue() == 0) {
				versionReleaseTime.readOnly = true;
				versionReleaseTime.setValue("");
			} else {
				versionReleaseTime.readOnly = false;
			}
			return;
		}
	</script>
</body>
</html>