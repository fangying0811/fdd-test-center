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
				<td style="text-align: left;"><label for="groupName">钉钉群名称：</label>
					<input id="groupName" name="groupName" class="mini-textbox"
					emptyText="支持钉钉群名称查询" style="width: 120px;" /></td>
			</tr>
			<tr>
				<td><a class="mini-button" iconCls="icon-search"
					style="width: 71px;" onclick="onSearch">查询</a> <a
					class="mini-button" iconCls="icon-cancel" style="width: 71px;"
					onclick="onCancel">重置</a> <span
					style="display: inline-block; width: 25px"></span></td>
			</tr>
		</table>
	</div>
	<div id="actionTb" class="mini-toolbar">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;">
						<a class="mini-button" iconCls="icon-add" onclick="add">增加</a>
						<a class="mini-button" iconCls="icon-search" onclick="dingdingManage">查看</a>
						<a class="mini-button" iconCls="icon-remove" onclick="remove">删除</a>
					</td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="dingdingManageDg" class="mini-datagrid"
			url="<%=basePath%>dingdingManage/dingdingManageList.json"
			multiSelect="false" showEmptyText="true" emptyText="暂无数据！"
			totalField="data.totalElements" dataField="data.pageList" sizeList="[5,10]">
			<div property="columns">
				<div type="checkcolumn"></div>
				<div field="dManageId" align="center" headerAlign="center">编号</div>
				<div field="groupName" align="center" headerAlign="center">钉钉群名</div>
				<div field="chatId" align="center" headerAlign="center">ChatId</div>
				<div field="userId" align="center" headerAlign="center">钉钉群主</div>
				<div field="userMobile" align="center" headerAlign="center">钉钉群主手机号</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var searchForm = new mini.Form('searchForm');
		var dingdingManageDg = mini.get("dingdingManageDg");
		dingdingManageDg.load();

		function onLoad(e) {
			var grid = e.sender;
			grid.setShowPager(grid.totalCount > 0);
		}

		//关键字模糊查询
		function onSearch() {
			dingdingManageDg.load(searchForm.getData(true, false));
		}

		function onCancel() {
			searchForm.reset();
		}

		function add() {
			mini
					.open({
						url : '<%=basePath%>dingdingManage/addDingdingManageUI',
						title : '添加钉钉群',
						width : 800,
						height : 300,
						showMaxButton : true,
						allowResize : false,
						ondestroy : function(action) {
							if (action == 'ok') {
								mini.alert("添加成功");
								dingdingManageDg.reload();
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

		function remove() {
			var row = dingdingManageDg.getSelected();
			if (row) {
				mini
						.confirm(
								'您确定要删除吗？',
								'系统提示',
								function(action) {
									if (action == 'ok') {
										var dManageId = row.dManageId;
										$
												.ajax({
													url : '<%=basePath%>dingdingManage/deleteDingdingManage.json',
													type : 'post',
													data : {
														dManageId : dManageId
													},
													success : function(data) {
														if (data.code==200 && data.data) {
															mini.alert("删除成功");
															dingdingManageDg
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
		
		function dingdingManage() {
			var row = dingdingManageDg.getSelected();
			if (row) {
				var dManageId = row.dManageId;
				mini
						.open({
							url : '<%=basePath%>dingdingManage/dingdingManageUI',
							title : '查看详情',
							width : 700,
							height : 300,
							showMaxButton : true,
							allowResize : false,
							onload : function() {
								var iframe = this.getIFrameEl();
								iframe.contentWindow.init(dManageId);
							},
							ondestroy : function(action) {
								dingdingManageDg.reload();
							}
						});
			} else {
				mini.alert("请选中一条记录");
			}
		}
		
	</script>
</body>
</html>