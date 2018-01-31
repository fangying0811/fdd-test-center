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
	<div id="searchForm">
	<table>
		<tr>
			<td style="text-align:left;">
				<label for="department">部门：</label>
				<input id="departmentId" name="department.departmentId"
				class="mini-combobox"
    			url="<%=basePath%>department/departmentListAll.json"
				dataField="data" valueField="departmentId" textField="name" allowInput="true"
    			emptyText="支持按部门查询" style="width:120px;"/>
			</td>
			<td style="text-align:left;"><label for="team">小组：</label> <input
					id="teamId" name="team.teamId" class="mini-combobox"
					url="<%=basePath%>team/teamListByDepartmentId.json"
					dataField="data" valueField="teamId" textField="name" allowInput="true"
					emptyText="支持按小组查询" style="width:120px;" /></td>
		</tr>
		<tr>
		<td>
		<a class="mini-button" iconCls="icon-search" style="width:71px;"
			onclick="onSearch">查询</a>
			<a class="mini-button" iconCls="icon-cancel" style="width:71px;"
			onclick="onReset">重置</a> <span
			style="display:inline-block;width:25px"></span>
	</td>
	</tr>
	</table>
	</div>
	<div class="mini-fit">
		<div id="userDg" class="mini-datagrid"
			url="<%=basePath%>user/selectUserList.json"
			multiSelect="true" showEmptyText="true" emptyText="暂无数据！"
			totalField="data.totalElements" dataField="data.pageList" onload="onLoad"
			onselectionchanged="onSelectoinChanged" pageSize="20" sizeList="[5,10,20]">
			<div property="columns">
				<div type="checkcolumn"></div>
	<div field="userId" align="center" headerAlign="center">编号</div>
	<div field="trueName" align="center" headerAlign="center">姓名</div>
	<div field="department.name" align="center" headerAlign="center">部门</div>
	<div field="team.name" align="center" headerAlign="center">小组</div>
	</div>
	</div>
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
		var searchForm = new mini.Form('searchForm');
		var grid = mini.get("userDg");
		grid.load();
		var department = mini.get("departmentId");
		var team = mini.get("teamId");
		
		function setDefaultValue() {
			if (department.getValue() == null || department.getValue() <= 0) {
				department.setValue(0);
			}
			if (team.getValue() == null || team.getValue() <= 0) {
				team.setValue(0);
			}
		}
		
		function resetDefaultValue() {
			if (department.getValue() == null || department.getValue() <= 0) {
				department.setValue();
			}
			if (team.getValue() == null || team.getValue() <= 0) {
				team.setValue();
			}
		}

		function onLoad(e) {
			setDefaultValue();
			var grid = e.sender;
			grid.setShowPager(grid.totalCount > 0);
			resetDefaultValue();
		}
		
		function GetSelecteds() {
			var rows = grid.getSelecteds();
			return rows;
		}

		var selectMaps = {};
		function GetAllSelecteds() {
			var data = [];
			for ( var pageIndex in selectMaps) {
				var rows = selectMaps[pageIndex];
				data.addRange(rows);
			}
			return data;
		}

		function getData() {
			var rows = GetAllSelecteds();
			var ids = [], texts = [];
			for (var i = 0, l = rows.length; i < l; i++) {
				var row = rows[i];
				ids.push(row.userId);
				texts.push(row.trueName);
			}
			var data = {};
			data.id = ids.join(",");
			data.text = texts.join(",");
			return data;
		}

		//关键字模糊查询
		function onSearch() {
			setDefaultValue();
			grid.load(searchForm.getData(true, false));
			resetDefaultValue();
		}

		function onReset() {
			searchForm.reset();
		}

		function onLoad(e) {
			var rows = selectMaps[grid.pageIndex];
			if (rows)
				grid.selects(rows);
		}

		function onSelectoinChanged(e) {
			var rows = grid.getSelecteds();
			selectMaps[grid.pageIndex] = rows;
		}

		function onOk() {
			closeWindow("ok");
		}
		function onCancel() {
			closeWindow("close");
		}
	</script>
</body>
</html>