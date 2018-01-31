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
				<td style="text-align: right;"><label for="userMobile">群主手机号<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="userMobile" name="userMobile"
					class="mini-textbox" emptyText="群主手机号"
					required="true" onvalidation="onValidation" errorMode="none"
					requiredErrorText="群主手机号不能为空" style="width:300px;" /></td>
				<td><div id="userMobile_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="groupName">钉钉群名<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="groupName" name="groupName"
					class="mini-textbox" emptyText="请输入钉钉群名称"
					required="true" onvalidation="onValidation" errorMode="none"
					requiredErrorText="钉钉群名称不能为空" style="width:300px;" /></td>
				<td><div id="groupName_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="userMobile">群成员手机号<span
						style="color:red">(*，多个逗号分隔)</span>：
				</label></td>
				<td><input id="memberMobile" name="memberMobile"
					class="mini-textbox" emptyText="群成员手机号"
					required="true" onvalidation="onValidation" errorMode="none"
					requiredErrorText="群成员手机号不能为空" style="width:300px;" /></td>
				<td><div id="memberMobile_error"></div></td>
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

		function onAdd(e) {
			form.validate();
			if (form.isValid() == false)
				return;

			var messageId = mini.loading('处理中，请稍等......', '提示');

			$.ajax({
				url : '<%=basePath%>dingdingManage/addDingdingManage.json',
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
	</script>
</body>
</html>
