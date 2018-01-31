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
	<div class="mini-fit">
		<ul id="menuTree" class="mini-tree"
			url="<%=basePath%>menu/selectMenuList.json"
			style="width:300px;padding:5px;" showTreeIcon="true" dataField="data"
			textField="name" idField="menuId" parentField="parentId" resultAsTree="false"
			showArrow="true" expandOnNodeClick="true">
		</ul>
		</br>
		<div class="mini-toolbar"
			style="text-align:center;padding-top:8px;padding-bottom:8px;"
			borderStyle="border:0;">
			<a class="mini-button" style="width:60px;" onclick="onOk()">确定</a> <span
				style="display:inline-block;width:25px;"></span> <a
				class="mini-button" style="width:60px;" onclick="onCancel()">取消</a>
		</div>
	</div>

	<script type="text/javascript">
		mini.parse();

		function getData() {
			var tree = mini.get("menuTree");
			var node = tree.getSelectedNode();
			var data = {
				value : node.menuId,
				text : node.name
			};
			return data;
		}

		function onOk() {
			closeWindow("ok");
		}

		function onCancel() {
			closeWindow("cancel");
		}
	</script>
</body>
</html>
