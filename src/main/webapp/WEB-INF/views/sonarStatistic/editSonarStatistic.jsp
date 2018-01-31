<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>质量管理平台</title>
<script type="text/javascript" src="<%=basePath%>scripts/boot.js"></script>
</head>

<body>
	<div id="editForm" style="padding:15px">
		<table border="0" style="width: 100%">
			<tr>
				<td><input id="id" name="id" class="mini-hidden" /></td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="department">部门<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="departmentId" name="departmentId"
					class="mini-combobox"
					url="<%=basePath%>department/departmentListAll.json"
					dataField="data" valueField="departmentId" textField="name" allowInput="true"
					valueFromSelect="true" required="true" onvalidation="onValidation"
					errorMode="none" requiredErrorText="请选择部门" emptyText="请选择部门"
					style="width:300px;" onvaluechanged="onDepartmentChanged" /></td>
				<td><div id="departmentId_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="team">小组<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="teamId" name="teamId" class="mini-combobox"
					dataField="data" valueField="teamId" textField="name" allowInput="true"
					valueFromSelect="true" required="true" onvalidation="onValidation"
					errorMode="none" requiredErrorText="请选择小组" emptyText="请选择小组"
					style="width:300px;"  onvaluechanged="onTeamChanged"/></td>
				<td><div id="teamId_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="sonarProject">项目<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="sonarProjectId" name="sonarProjectId" class="mini-combobox"
					dataField="data" valueField="id" textField="projectName" allowInput="true"
					valueFromSelect="true" required="true" onvalidation="onValidation"
					errorMode="none" requiredErrorText="请选择项目" emptyText="请选择项目"
					style="width:300px;" /></td>
				<td><div id="sonarProjectId_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="blocker">blocker<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="blocker" name="blocker" class="mini-spinner"
					minValue="0" maxValue="10000" required="true"
					onvalidation="onValidation" errorMode="none"
					requiredErrorText="请选择blocker" emptyText="请选择blocker"
					style="width:300px;" /></td>
				<td><div id="blocker_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="critical">critical<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="critical" name="critical" class="mini-spinner"
					minValue="0" maxValue="10000" required="true"
					onvalidation="onValidation" errorMode="none"
					requiredErrorText="请选择critical" emptyText="请选择critical"
					style="width:300px;" /></td>
				<td><div id="critical_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="major">major<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="major" name="major" class="mini-spinner"
					minValue="0" maxValue="10000" required="true"
					onvalidation="onValidation" errorMode="none"
					requiredErrorText="请选择major" emptyText="请选择major"
					style="width:300px;" /></td>
				<td><div id="major_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="info">info<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="info" name="info" class="mini-spinner"
					minValue="0" maxValue="10000" required="true"
					onvalidation="onValidation" errorMode="none"
					requiredErrorText="请选择info" emptyText="请选择info"
					style="width:300px;" /></td>
				<td><div id="info_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="minor">minor<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="minor" name="minor" class="mini-spinner"
					minValue="0" maxValue="10000" required="true"
					onvalidation="onValidation" errorMode="none"
					requiredErrorText="请选择minor" emptyText="请选择minor"
					style="width:300px;" /></td>
				<td><div id="minor_error"></div></td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</div>
	<div class="mini-toolbar"
		style="text-align:center;padding-top:8px;padding-bottom:8px;"
		borderStyle="border:0;">
		<a class="mini-button" iconCls="icon-edit" style="width:71px;"
			onclick="onEdit">修改</a> <span style="display:inline-block;width:25px"></span>
		<a class="mini-button" iconCls="icon-cancel" style="width:71px;"
			onclick="onCancel">取消</a>
	</div>
	<script type="text/javascript">
		mini.parse();
		var form = new mini.Form('editForm');
		var department = mini.get("departmentId");
		var team = mini.get("teamId");
		var sonarProject = mini.get("sonarProjectId");

		var _data;
		//初始化表单数据
		function init(id) {
			$
					.ajax({
						url : '<%=basePath%>sonarStatistic/sonarStatisticInfo.json',
						type : 'get',
						data : {
							id : id
						},
						success : function(data) {
							_data = data.data;
							var departmentId=data.data.departmentId;
							var teamId=data.data.teamId;
							var teamUrl="<%=basePath%>team/teamListByDepartmentId.json?departmentId="+departmentId;
							var sonarProjectUrl="<%=basePath%>sonarProject/sonarProjectListByTeamId.json?teamId="+teamId;
					        team.setUrl(teamUrl);
					        sonarProject.setUrl(sonarProjectUrl);
							form.setData(data.data);
							form.setChanged(false);
						},
						dataType : 'json'
					});
		}

		function onEdit(e) {
			//没有改变，不用保存，直接关闭窗口
			if (!form.isChanged()) {
				closeWindow('close');
				return;
			}

			form.validate();
			if (form.isValid() == false)
				return;

			var messageId = mini.loading('处理中，请稍等......', '提示');

			$
					.ajax({
						url : '<%=basePath%>sonarStatistic/editStatistic.json',
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
		
		function onDepartmentChanged(e) {
            var departmentId=department.getValue();
            team.setValue();
            var url="<%=basePath%>team/teamListByDepartmentId.json?departmentId="+departmentId;
            team.setUrl(url);
		}
		
		function onTeamChanged(e) {
            var teamId=team.getValue();
            sonarProject.setValue("");
            var url="<%=basePath%>sonarProject/sonarProjectListByTeamId.json?teamId="+teamId;
            sonarProject.setUrl(url);
		}
	</script>
</body>
</html>
