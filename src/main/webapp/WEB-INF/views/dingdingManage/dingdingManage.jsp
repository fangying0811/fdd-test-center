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
				<td><input id="dManageId" name="dManageId" class="mini-hidden" /></td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="userMobile">群主手机号<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="userMobile" name="userMobile"
					class="mini-textbox" allowInput="false" style="width:300px;" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="userId">群主钉钉Id<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="userId" name="userId"
					class="mini-textbox" allowInput="false" style="width:300px;" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="chatId">chatId<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="chatId" name="chatId"
					class="mini-textbox" allowInput="false" style="width:300px;" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="groupName">钉钉群名<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="groupName" name="groupName"
					class="mini-textbox" allowInput="false" style="width:300px;" /></td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</div>
	<div class="mini-toolbar"
		style="text-align:center;padding-top:8px;padding-bottom:8px;"
		borderStyle="border:0;">
		<a class="mini-button" iconCls="icon-cancel" style="width:71px;"
			onclick="onCancel">确定</a>
	</div>
	<script type="text/javascript">
		mini.parse();
		var form = new mini.Form('editForm');

		var _data;
		//初始化表单数据
		function init(dManageId) {
			$
					.ajax({
						url : '<%=basePath%>dingdingManage/dingdingManage.json',
						type : 'get',
						data : {
							dManageId : dManageId
						},
						success : function(data) {
							_data = data.data;
							form.setData(data.data);
							form.setChanged(false);
						},
						dataType : 'json'
					});
		}

		function onCancel() {
			closeWindow('close');
			return;
		}
	</script>
</body>
</html>
