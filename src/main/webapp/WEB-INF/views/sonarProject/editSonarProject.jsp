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
					style="width:300px;" /></td>
				<td><div id="teamId_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="projectName">项目名称<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="projectName" name="projectName"
					class="mini-textbox" emptyText="请输入项目名称" vtype="remote"
					required="true" onvalidation="onValidation" errorMode="none"
					requiredErrorText="项目名称不能为空" style="width:300px;" /></td>
				<td><div id="projectName_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="componentKey">sonar项目查询key<span
						style="color: red">(*)</span>：
				</label></td>
				<td><input id="componentKey" name="componentKey"
					class="mini-textbox" required="true"
					onvalidation="onValidation" errorMode="none"
					requiredErrorText="sonar项目查询key不能为空" emptyText="请输入sonar项目查询key"
					style="width: 300px;" /></td>
				<td><div id="componentKey_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="pm">开发负责人<span
						style="color: red">(*)</span>：
				</label></td>
				<td><input id="pm" name="pm" class="mini-textbox"
					required="true" onvalidation="onValidation"
					errorMode="none" requiredErrorText="开发负责人不能为空" emptyText="请输入开发负责人"
					style="width: 300px;" /></td>
				<td><div id="pm_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="status">状态<span
						style="color:red">(*)： </label></td>
				<td><div id="status" name="status" class="mini-radiobuttonlist"
						dataField="data" valueField="status" textField="statusText"
						url="<%=basePath%>sonarProject/statusList.json"
						required="true" onvalidation="onValidation" errorMode="none"
						requiredErrorText="请选择状态"></div></td>
				<td><div id="status_error"></div></td>
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

		var _data;
		//初始化表单数据
		function init(id) {
			$
					.ajax({
						url : '<%=basePath%>sonarProject/sonarProject.json',
						type : 'get',
						data : {
							id : id
						},
						success : function(data) {
							_data = data.data;
							var departmentId=data.data.departmentId;
							var url="<%=basePath%>team/teamListByDepartmentId.json?department.departmentId="+departmentId;
					        team.setUrl(url);
							form.setData(data.data);
							form.setChanged(false);
						},
						dataType : 'json'
					});
		}

		/*自定义vtype*/
		mini.VTypes["remoteErrorText"] = "项目名称已经存在，请更换";
		mini.VTypes["remote"] = function(projectName) {
			var flag = false;
			if (projectName == _data.projectName) {
				flag = true;
			} else {
				$
						.ajax({
							async : false,/*同步请求*/
							url : '<%=basePath%>sonarProject/existProject.json',
							type : 'post',
							data : {
								projectName : projectName
							},
							success : function(data) {
								flag = data.data;
							},
							dataType : 'json'
						});
			}
			return flag;
		};

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
						url : '<%=basePath%>sonarProject/editSonarProject.json',
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
            team.select(0);
		}
	</script>
</body>
</html>
