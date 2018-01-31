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
				<label for="name">部门名称：</label>
				<input id="name" name="name" class="mini-textbox"
				emptyText="支持部门名称查询"style="width:150px;" />
			</td>
		</tr>
		<tr>
		<td>
		<a class="mini-button" iconCls="icon-search" style="width:71px;"
			onclick="onSearch">查询</a>
			<a class="mini-button" iconCls="icon-cancel" style="width:71px;"
			onclick="onCancel">重置</a> <span
			style="display:inline-block;width:25px"></span>
	</td>
	</tr>
	</table>
	</div>
	<c:if test="${sessionScope.loginUser.isAdmin==0 || sessionScope.loginUser.isAdmin==1}"> 
		<div id="actionTb" class="mini-toolbar">
			<table style="width:100%;">
				<tr>
					<td style="width:100%;"><a class="mini-button"
						iconCls="icon-add" onclick="add">增加</a> <a class="mini-button"
						iconCls="icon-edit" onclick="edit">编辑</a> <a class="mini-button"
						iconCls="icon-remove" onclick="remove">删除</a></td>
				</tr>
			</table>
		</div>
	</c:if>
	<div class="mini-fit">
		<div id="departmentDg" class="mini-datagrid"
			url="<%=basePath%>department/departmentList.json"
			multiSelect="false" showEmptyText="true" emptyText="暂无数据！"
			totalField="data.totalElements" dataField="data.pageList" sizeList="[5,10]">
			<div property="columns">
				<div type="checkcolumn"></div>
				<div field="departmentId" align="center" headerAlign="center">编号</div>
				<div field="name" align="center" headerAlign="center">部门名称</div>
				<div field="remark" align="center" headerAlign="center">备注</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var searchForm = new mini.Form('searchForm');
		var departmentDg = mini.get("departmentDg");
		departmentDg.load();

		function onLoad(e) {
			var grid = e.sender;
			//没有菜单信息
			grid.setShowPager(grid.totalCount > 0);
		}

		//关键字模糊查询
		function onSearch() {
			departmentDg.load(searchForm.getData(true, false));
		}

		function onCancel() {
			searchForm.reset();
		}

		function add() {
			mini
					.open({
						url : '<%=basePath%>department/addDepartmentUI',
						title : '添加部门',
						width : 600,
						height : 200,
						showMaxButton : true,
						allowResize : false,
						ondestroy : function(action) {
							if (action == 'ok') {
								mini.alert("添加成功");
								departmentDg.reload();
							} else if (action != 'close' && !isEmpty(action)) {
								mini.alert(action);
							}else {
								if (action != 'close' && isEmpty(action)){
									mini.alert("添加失败");
								}
							}
						}
					});
		}

		function edit() {
			var row = departmentDg.getSelected();
			if (row) {
				var departmentId = row.departmentId;
				mini
						.open({
							url : '<%=basePath%>department/editDepartmentUI',
							title : '修改部门',
							width : 600,
							height : 200,
							showMaxButton : true,
							allowResize : false,
							onload : function() {
								var iframe = this.getIFrameEl();
								iframe.contentWindow.init(departmentId);
							},
							ondestroy : function(action) {
								if (action == 'ok') {
									mini.alert("修改成功");
									departmentDg.reload();
								} else if (action != 'close' && !isEmpty(action)) {
									mini.alert(action);
								}else {
									if (action != 'close' && isEmpty(action)){
										mini.alert("修改失败");
									}
								}
							}
						});
			} else {
				mini.alert("请选中一条记录");
			}
		}

		function remove() {
			var row = departmentDg.getSelected();
			if (row) {
				mini
						.confirm(
								'您确定要删除吗？',
								'系统提示',
								function(action) {
									if (action == 'ok') {
										var departmentId = row.departmentId;
										$
												.ajax({
													url : '<%=basePath%>department/deleteDepartment.json',
													type : 'post',
													data : {
														departmentId : departmentId
													},
													success : function(data) {
														if (data.code==200 && data.data) {
															mini.alert("删除成功");
															departmentDg
																	.reload();
														} else if (!isEmpty(data.msg)){
															mini.alert(data.msg);
														}else {
															if (isEmpty(data.msg)){
																mini.alert("删除失败");
															}
														}
													},
													dataType : 'json'
												});
									}
								});
			} else {
				mini.alert("请选中一条记录");
			}
		}
	</script>
</body>
</html>