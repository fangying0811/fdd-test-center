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
	<div id="editForm" style="padding:15px">
		<table border="0" style="width: 100%">
			<tr>
				<td><input id="userId" name="userId" class="mini-hidden" /></td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="department">部门<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="departmentId" name="department.departmentId"
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
				<td><input id="teamId" name="team.teamId" class="mini-combobox"
					dataField="data" valueField="teamId" textField="name" allowInput="true"
					valueFromSelect="true" required="true" onvalidation="onValidation"
					errorMode="none" requiredErrorText="请选择小组" emptyText="请选择小组"
					style="width:300px;"/></td>
				<td><div id="teamId_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="username">登录用户名<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="username" name="username" class="mini-textbox"
					emptyText="请输入登录用户名" vtype="remote" required="true"
					onvalidation="onValidation" errorMode="none"
					requiredErrorText="登录用户名不能为空" style="width:300px;" /></td>
				<td><div id="username_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="trueName">姓名<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="trueName" name="trueName" class="mini-textbox"
					emptyText="请输入姓名" required="true" onvalidation="onValidation"
					errorMode="none" requiredErrorText="姓名不能为空" style="width:300px;" /></td>
				<td><div id="trueName_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="email">邮箱<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="email" name="email" class="mini-textbox"
					emptyText="请输入邮箱" vtype="email" required="true"
					onvalidation="onValidation" errorMode="none"
					requiredErrorText="邮箱不能为空" style="width:300px;" /></td>
				<td><div id="email_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<c:if test="${sessionScope.loginUser.isAdmin==0 || sessionScope.loginUser.isAdmin==1}">  
				<tr>
				<td style="text-align: right;"><label for="isAdmin">角色<span
						style="color: red">(*)： </label></td>
				<td><div id="isAdmin" name="isAdmin"
						class="mini-radiobuttonlist" dataField="data" valueField="isAdmin"
						textField="isAdminText" url="<%=basePath%>user/isAdminList.json"
						required="true" onvalidation="onValidation" errorMode="none"
						requiredErrorText="请选择角色"></div></td>
				<td><div id="isAdmin_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="status">状态<span
						style="color: red">(*)： </label></td>
				<td><div id="status" name="status" class="mini-radiobuttonlist"
						dataField="data" valueField="status" textField="statusText"
						url="<%=basePath%>user/statusList.json" required="true"
						onvalidation="onValidation" errorMode="none"
						requiredErrorText="请选择状态"></div></td>
				<td><div id="status_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			</c:if>
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
		var department = mini.get('departmentId');
		var team = mini.get('teamId');

		var _data;
		//初始化表单数据
		function init(userId) {
			$.ajax({
				url : '<%=basePath%>user/userInfo.json',
				type : 'get',
				data : {
					userId : userId
				},
				success : function(data) {
					_data = data.data;
					var departmentId=data.data.department.departmentId;
					var url="<%=basePath%>team/teamListByDepartmentId.json?departmentId="+departmentId;
			        team.setUrl(url);
			        form.setData(data.data);
					form.setChanged(false);
				},
				dataType : 'json'
			});
		}

		/*自定义vtype*/
		mini.VTypes["remoteErrorText"] = "登录用户名已经存在，请更换";
		mini.VTypes["remote"] = function(username) {
			var flag = false;
			if (username == _data.username) {
				flag = true;
			} else {
				$.ajax({
					async : false,/*同步请求*/
					url : '<%=basePath%>user/existUser.json',
					type : 'post',
					data : {
						name : name
					},
					success : function(data) {
						if(data.code==200){
							flag = data.data;
						}
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

			$.ajax({
				url : '<%=basePath%>user/editUser.json',
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
