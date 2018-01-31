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
						style="color: red">(*)</span>：
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
						style="color: red">(*)</span>：
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
				<td style="text-align: right;"><label for="serviceName">项目名称<span
						style="color: red">(*)</span>：
				</label></td>
				<td><input id="serviceName" name="serviceName"
					class="mini-textbox" emptyText="请输入项目名称"
					required="true" onvalidation="onValidation" errorMode="none"
					requiredErrorText="项目名称不能为空" style="width: 300px;" /></td>
				<td><div id="serviceName_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="remark">项目描述<span
						style="color: red">(*)</span>：
				</label></td>
				<td><input id="remark" name="remark"
					class="mini-textbox" required="true"
					onvalidation="onValidation" errorMode="none"
					requiredErrorText="项目描述不能为空" emptyText="请输入项目描述"
					style="width: 300px;" /></td>
				<td><div id="remark_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="pm">责任人<span
						style="color: red">(*)</span>：
				</label></td>
				<td><input id="pm" name="pm"
						class="mini-buttonedit" required="true" allowInput="false"
						onvalidation="onValidation" errorMode="none"
						onbuttonclick="onResource" emptyText="请选择责任人"
						requiredErrorText="责任人不能为空" style="width: 300px;" /></td>
				<td><div id="pm_error"></div></td>
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
		var pm = mini.get("pm");
		
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
		function init(id) {
			$
					.ajax({
						url : '<%=basePath%>automationProject/automationProjectInfo.json',
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
							pm.setValue(data.data.pm);
							pm.setText(data.data.pm);
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
						url : '<%=basePath%>automationProject/editAutomationProject.json',
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
