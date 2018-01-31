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
<title>质量管理平台</title>
<script type="text/javascript" src="<%=basePath%>scripts/boot.js"></script>
</head>

<body>
	<div id="addForm" style="padding:15px">
		<table border="0" style="width: 100%">
			<tr>
				<td style="width:300px;text-align: right;"><label
					for="parentId"> 上级菜单：</label></td>
				<td>
					<div id="parentId" name="parentId" class="mini-buttonedit"
						allowInput="false" emptyText="请选择上级菜单" onvalidation="onValidation"
						errorMode="none" onbuttonclick="onSelect"></div>
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="name">菜单名称<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="name" name="name" class="mini-textbox"
					emptyText="请输入菜单名称" vtype="remote" required="true"
					onvalidation="onValidation" errorMode="none"
					requiredErrorText="菜单名称不能为空" style="width:300px;" /></td>
				<td><div id="name_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="name">菜单访问URL：
				</label></td>
				<td><input id="url" name="url" class="mini-textbox"
					style="width:300px;" /></td>
				<td><div id="url_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="priority">菜单优先级<span
						style="color:red">(*,数字小优先级高)</span>：
				</label></td>
				<td><input id="priority" name="priority" class="mini-spinner"
					minValue="0" maxValue="100" required="true"
					onvalidation="onValidation" errorMode="none"
					requiredErrorText="菜单优先级不能为空" " style="width:300px;" /></td>
				<td><div id="priority_error"></div></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="status">状态<span
						style="color:red">(*)： </label></td>
				<td><div id="status" name="status" class="mini-radiobuttonlist"
						dataField="data"valueField="status" textField="statusText"
						url="<%=basePath%>menu/statusList.json"
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
		<a class="mini-button" iconCls="icon-add" style="width:71px;"
			onclick="onAdd">添加</a> <span style="display:inline-block;width:25px"></span>
		<a class="mini-button" iconCls="icon-cancel" style="width:71px;"
			onclick="onCancel">取消</a>
	</div>
	<script type="text/javascript">
		mini.parse();
		var form = new mini.Form('addForm');
		var parentId = mini.get("parentId");
		
		function setDefaultValue() {
			if (isEmpty(parentId.getValue())) {
				parentId.setValue(0);
			}
		}
		
		function resetDefaultValue() {
			if (isEmpty(parentId.getValue())) {
				parentId.setValue();
			}
		}

		//选择菜单
		function onSelect() {
			var btneSeleteMenu = this;
			mini.open({
				url : '<%=basePath%>menu/selectMenuUI',
				title : "选择菜单",
				allowResize : false,
				width : 450,
				height : 300,
				ondestroy : function(action) {
					if (action == "ok") {
						var iframe = this.getIFrameEl();
						var data = iframe.contentWindow.getData();
						data = mini.clone(data); //必须
						if (data) {
							btneSeleteMenu.setValue(data.value);
							btneSeleteMenu.setText(data.text);
						}
					}
				}
			});
		}

		/*自定义vtype*/
		mini.VTypes["remoteErrorText"] = "菜单已经存在，请更换";
		mini.VTypes["remote"] = function(name) {
			var flag = false;
			$.ajax({
				async : false,/*同步请求*/
				url : '<%=basePath%>menu/existMenu.json',
				type : 'post',
				data : {
					name : name
				},
				success : function(data) {
					flag = data.data;
				},
				dataType : 'json'
			});
			return flag;
		};

		//添加菜单
		function onAdd(e) {
			setDefaultValue();
			form.validate();
			if (form.isValid() == false)
				return;

			var messageId = mini.loading('处理中，请稍等......', '提示');

			$.ajax({
				url : '<%=basePath%>menu/addMenu.json',
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
			resetDefaultValue();
		}

		//重置
		function onCancel() {
			closeWindow('close');
			return;
		}
	</script>
</body>
</html>
