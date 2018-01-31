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
	<div id="userInfoForm" style="padding:15px">
		<table border="0" style="width: 100%">
			<tr style="height: 30px">
				<td style="text-align: right;"><label for="userId">编号：
				</label></td>
				<td><input id="userId" name="userId" class="mini-textbox"
					style="width:300px;" allowInput="false" /></td>
			</tr>
			<tr style="height: 30px">
				<td style="text-align: right;"><label for="username">登录用户名：
				</label></td>
				<td><input id="username" name="username" class="mini-textbox"
					style="width:300px;" allowInput="false" /></td>
			</tr>
			<tr>
				<tr style="height: 30px">
					<td style="text-align: right;"><label for="trueName">姓名：
					</label></td>
					<td><input id="trueName" name="trueName" class="mini-textbox"
						style="width:300px;" allowInput="false" /></td>
				</tr>
				<tr style="height: 30px">
					<td style="text-align: right;"><label for="email">邮箱：
					</label></td>
					<td><input id="email" name="email" class="mini-textbox"
						style="width:300px;" allowInput="false" /></td>
				</tr>
				<tr style="height: 30px">
					<td style="text-align: right;"><label for="statusText">部门：
					</label></td>
					<td><input id="department.name" name="department.name"
						class="mini-textbox" style="width:300px;" allowInput="false" /></td>
				</tr>
				<tr>
				<td style="text-align: right;"><label for="team">小组： </label></td>
				<td><input id="teamName" name="team.name" class="mini-textbox"
					style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
				</tr>
				<tr style="height: 30px">
					<td style="text-align: right;"><label for="isAdminText">角色：
					</label></td>
					<td><input id="isAdminText" name="isAdminText"
						class="mini-textbox" style="width:300px;" allowInput="false" /></td>
				</tr>
				<tr style="height: 30px">
					<td style="text-align: right;"><label for="statusText">状态：
					</label></td>
					<td><input id="statusText" name="statusText"
						class="mini-textbox" style="width:300px;" allowInput="false" /></td>
				</tr>
		</table>
	</div>
	<div class="mini-toolbar"
		style="text-align:center;padding-top:8px;padding-bottom:8px;"
		borderStyle="border:0;">
		<a class="mini-button" style="width:71px;" onclick="onEdit">确定</a>
	</div>
	<script type="text/javascript">
		mini.parse();
		var form = new mini.Form('userInfoForm');

		//初始化表单数据
		function init(userId) {
			$.ajax({
				url : '<%=basePath%>user/userInfo.json',
				type : 'get',
				data : {
					userId : userId
				},
				success : function(data) {
					form.setData(data.data);
				},
				dataType : 'json'
			});
		}

		function onEdit(e) {
			closeWindow('ok');
		}
	</script>
</body>
</html>
