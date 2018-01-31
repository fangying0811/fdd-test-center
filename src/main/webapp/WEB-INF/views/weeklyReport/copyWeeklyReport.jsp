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
<title>美利金融质量管理平台</title>
<script type="text/javascript" src="<%=basePath%>scripts/boot.js"></script>
</head>

<body>
	<div class="mini-fit">
	<div id="editForm" style="padding:15px">
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
					style="width: 300px;" onvaluechanged="onTeamChanged" /></td>
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
				<td style="text-align: right;"><label for="type">类型<span
						style="color:red">(*,请选择正确的类型)</span>：
				</label></td>
				<td><input id="type" name="type" class="mini-combobox"
					url="<%=basePath%>weeklyReport/typeList.json"
					dataField="data" valueField="type" textField="typeText" allowInput="true"
					valueFromSelect="true" required="true" onvalidation="onValidation"
					errorMode="none" requiredErrorText="请选择类型" emptyText="请选择类型"
					style="width: 300px;" /></td>
				<td><div id="type_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="versionInfo">版本信息<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="versionInfo" name="versionInfo"
					class="mini-textarea" vtype="maxLength:30" emptyText="请输入版本信息"
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
					class="mini-buttonedit" required="true" allowInput="false"
					onbuttonclick="onResource" emptyText="请选择责任人"
					requiredErrorText="责任人不能为空"  style="width:300px;" /></td>
				<td><div id="resource_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="caseNumber">版本用例总数<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="caseNumber" name="caseNumber" class="mini-spinner"
					minValue="0" maxValue="10000" required="true"
					onvalidation="onValidation" errorMode="none"
					requiredErrorText="请选择版本用例总数" emptyText="请选择版本用例总数"
					style="width:300px;" /></td>
				<td><div id="caseNumber_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="versionTestTime">测试开始时间<span
						style="color:red">(*)</span>：
				</label></td>
				<td>
					<div id="versionTestTimeStatus" name="versionTestTimeStatus"
						class="mini-radiobuttonlist" valueField="status"
						textField="statusText" dataField="data"
						url="<%=basePath%>weeklyReport/statusList.json"
						allowInput="false" required="true"
						onvaluechanged="onVersionTestTimeStatus"
						onvalidation="onValidation" errorMode="none"
						requiredErrorText="请选择测试开始时间"></div> <input id="versionTestTime"
					name="versionTestTime" class="mini-datepicker" allowInput="false"
					emptyText="请选择测试开始时间" showOkButton="true" readOnly="true"
					required="false" onvalidation="onValidation" errorMode="none"
					requiredErrorText="测试开始时间不能为空" style="width:300px;" />
				</td>
				<td>
					<div id="versionTestTimeStatus_error"></div>
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="testTime">版本测试时间<span
						style="color:red">(*,单位：天)</span>：
				</label></td>
				<td>
					<div id="testTimeStatus" name="testTimeStatus"
						class="mini-radiobuttonlist" valueField="status"
						textField="statusText" dataField="data"
						url="<%=basePath%>weeklyReport/statusList.json"
						required="true" onvaluechanged="onTestTimeStatus"
						onvalidation="onValidation" errorMode="none"
						requiredErrorText="请选择版本测试时间"></div> <input id="testTime"
					name="testTime" class="mini-textbox" vtype="float" required="true"
					onvalidation="onValidation" errorMode="none"
					requiredErrorText="版本测试时间不能为空" emptyText="请输入版本测试时间"
					style="width:300px;" />
				</td>
				<td><div id="testTime_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="testStatus">进度<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="testStatus" name="testStatus"
					class="mini-combobox"
					url="<%=basePath%>weeklyReport/testStatusList.json"
					dataField="data" valueField="testStatus" textField="testStatusText"
					allowInput="true" valueFromSelect="true" required="true"
					onvalidation="onValidation" errorMode="none"
					requiredErrorText="请选择进度" emptyText="请选择进度" style="width: 300px;" /></td>
				<td><div id="testStatus_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="testTime">版本bug总数<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="bugNumber" name="bugNumber" class="mini-spinner"
					minValue="0" maxValue="10000" required="true"
					onvalidation="onValidation" errorMode="none"
					requiredErrorText="请选择版本测试当前bug数" emptyText="请选择版本测试当前bug数"
					style="width:300px;" /></td>
				<td><div id="bugNumber_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="testTime">严重bug数<span
						style="color: red">(*)</span>：
				</label></td>
				<td><input id="bugCritical" name="bugCritical" class="mini-spinner"
						   minValue="0" maxValue="10000" required="true"
						   onvalidation="onValidation" errorMode="none"
						   requiredErrorText="请选择版本测试当前严重bug数" emptyText="请选择版本测试当前严重bug数"
						   style="width: 300px;" /></td>
				<td><div id="bugCritical_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="versionReleaseRime">测试完成时间<span
						style="color:red">(*)</span>：
				</label></td>
				<td>
					<div id="versionReleaseTimeStatus" name="versionReleaseTimeStatus"
						class="mini-radiobuttonlist" valueField="status"
						textField="statusText" dataField="data"
						url="<%=basePath%>weeklyReport/statusList.json"
						allowInput="false" required="true"
						onvaluechanged="onVersionReleaseTimeStatus"
						onvalidation="onValidation" errorMode="none"
						requiredErrorText="请选择测试完成时间"></div> <input
					id="versionReleaseTime" name="versionReleaseTime"
					class="mini-datepicker" allowInput="false" emptyText="请选择测试完成时间"
					showOkButton="true" readOnly="true" required="false"
					onvalidation="onValidation" errorMode="none"
					requiredErrorText="测试完成时间不能为空" style="width:300px;" />
				</td>
				<td>
					<div id="versionReleaseTimeStatus_error"></div>
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="remark">备注<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="remark" name="remark" class="mini-textarea"
					emptyText="请输入备注(如版本主要需求功能说明等)" required="true"
					onvalidation="onValidation" errorMode="none"
					requiredErrorText="备注不能为空" style="width:300px;height:120px;" /></td>
				<td><div id="remark_error"></div></td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<div property="footer"
			style="text-align: center; padding-top: 8px; padding-bottom: 8px;"
			borderStyle="border:0;">
			<a class="mini-button" iconCls="icon-add" style="width:71px;"
				onclick="onAdd">添加</a> <span style="display:inline-block;width:25px"></span>
			<a class="mini-button" iconCls="icon-cancel" style="width:71px;"
				onclick="onCancel">取消</a>
		</div>
	</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var form = new mini.Form('editForm');
		var team = mini.get("teamId");
		var project = mini.get("projectId");
		var testTimeStatus = mini.get("testTimeStatus");
		testTimeStatus.setValue(1);	

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
						form.setChanged(true);
					}
				}
			});
		}
		
		var _data;
		//初始化表单数据
		function init(weeklyReportId) {
			$
					.ajax({
						url : '<%=basePath%>weeklyReport/weeklyReportInfo.json',
						type : 'get',
						data : {
							weeklyReportId : weeklyReportId
						},
						success : function(data) {
							_data = data.data;
							var versionTestTimeStatus = mini
									.get("versionTestTimeStatus");
							var versionTestTime = mini.get("versionTestTime");
							var testTimeStatus = mini.get("testTimeStatus");
							var versionReleaseTimeStatus = mini
									.get("versionReleaseTimeStatus");
							var versionReleaseTime = mini
									.get("versionReleaseTime");
							var versionInfo = mini.get("versionInfo");
							var remark = mini.get("remark");
							var resource = mini.get("resource");
							var teamId=data.data.team.teamId;
					        var url="<%=basePath%>projectInfo/projectInfoListByTeamId.json?teamId="+ teamId;
							project.setUrl(url);
							form.setData(data.data);
							if (data.data.versionTestTime == "待定") {
								versionTestTimeStatus.setValue(0);
								versionTestTime.readOnly = true;
								versionTestTime.setValue(null);
							} else {
								versionTestTimeStatus.setValue(1);
								versionTestTime.readOnly = false;
							}
							if (data.data.testTime <= 0) {
								testTimeStatus.setValue(0);
								testTime.readOnly = true;
							} else {
								testTimeStatus.setValue(1);
								testTime.readOnly = false;
							}
							if (data.data.versionReleaseTime == "待定") {
								versionReleaseTimeStatus.setValue(0);
								versionReleaseTime.readOnly = true;
								versionReleaseTime.setValue(null);
							} else {
								versionReleaseTimeStatus.setValue(1);
								versionReleaseTime.readOnly = false;
							}
							versionInfo.setValue(data.data.versionInfoDetail);
							remark.setValue(data.data.remarkDetail);
							resource.setValue(data.data.resource);
							resource.setText(data.data.resource);
							form.setChanged(false);
						},
						dataType : 'json'
					});
		}

		function onAdd(e) {
			form.validate();
			if (form.isValid() == false)
				return;

			var messageId = mini.loading('处理中，请稍等......', '提示');

			var versionInfo = mini.get("versionInfo");
			versionInfo.setValue(TransferString(versionInfo.getValue()));

			$
					.ajax({
						url : '<%=basePath%>weeklyReport/addWeeklyReport.json',
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

		function onVersionTestTimeStatus() {
			var status = mini.get("versionTestTimeStatus");
			var versionTestTime = mini.get("versionTestTime");
			if (status.getValue() == 0) {
				versionTestTime.readOnly = true;
				versionTestTime.setValue("");
			} else {
				versionTestTime.readOnly = false;
			}
			return;
		}

		function onTestTimeStatus() {
			var status = mini.get("testTimeStatus");
			var testTime = mini.get("testTime");
			if (status.getValue() == 0) {
				testTime.readOnly = true;
				testTime.setValue("0");
			} else {
				testTime.readOnly = false;
			}
			return;
		}

		function onVersionReleaseTimeStatus() {
			var status = mini.get("versionReleaseTimeStatus");
			var versionReleaseTime = mini.get("versionReleaseTime");
			if (status.getValue() == 0) {
				versionReleaseTime.readOnly = true;
				versionReleaseTime.setValue(null);
			} else {
				versionReleaseTime.readOnly = false;
			}
			return;
		}
	</script>
</body>
</html>
