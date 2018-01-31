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
<!-- 清空浏览器缓存 -->
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<title>美利金融质量管理平台</title>
<script type="text/javascript" src="<%=basePath%>scripts/boot.js"></script>
</head>
<body>
	<div id="layout" class="mini-layout" style="width: 100%; height: 100%;">
		<div class="header" region="north" height="70" showSplit="false"
			showHeader="false">
			<h1 style="margin: 0; padding: 15px 0 0 15px;">美利金融质量管理平台</h1>
			<h4 id="loginUser" style="margin: 0; padding: 5px 0 0 15px;">
				欢迎您：<span style="color: #00F">${sessionScope.loginUser.trueName}(${sessionScope.loginUser.isAdminText})</span>
			</h4>
			<div style="position: absolute; top: 18px; right: 10px;">
				皮肤： <input id="skinCbo" class="mini-combobox" style="width: 80px;"
					textField="text" valueField="id"
					url="<%=basePath%>scripts/skin.txt" onvaluechanged="onChangeSkin" />
				<a class="mini-button mini-button-iconTop" iconCls="icon-user"
					onclick="onHomePageClick" plain="true">首页</a> <a
					class="mini-button mini-button-iconTop" iconCls="icon-close"
					onclick="onExitClick" plain="true">退出</a>
			</div>
		</div>

		<div region="south" showSplit="false" showHeader="false" height="30">
			<div style="line-height: 28px; text-align: center;">Copyright ©
				美利金融质量管理团队版权所有</div>
		</div>
		<div region="west" title="管理菜单" showHeader="true" showSplitIcon="true"
			bodyStyle="padding-left:0px;" width="200" minWidth="150"
			maxWidth="250">
			<div id="leftTree" class="mini-tree" style="height: 100%"
				url="<%=basePath%>menu/loadMenus.json" showTreeIcon="true"
				dataField="data" idField="menuId" textField="name" onnodeclick="onNodeClick"
				onbeforeexpand="onBeforeExpand"></div>
		</div>
		<div region="center" title="center" bodyStyle="overflow:hidden;"
			style="border: 0;">
			<div id="mainTabs" class="mini-tabs" activeIndex="0"
				style="width: 100%; height: 100%;">
				<div title="首页" url="<%=basePath%>user/welcomeUI"></div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();

		var tabs = mini.get('mainTabs');

		//首页
		function onHomePageClick() {
			tabs.activeTab(tabs.getTab(0));
			tabs.reloadTab(tabs.getTab(0));
		}

		//退出
		function onExitClick() {
			mini
					.confirm(
							'您确定要退出吗？',
							'系统提示',
							function(action) {
								if (action == 'ok') {
									window.location.href = '<%=basePath%>user/logoutUI';
								}
							});
		}

		/**
		 * 显示选项卡
		 * @param node 被单击的菜单项对象
		 * @param isRemove 选项卡显示模式，true-每次只显示一个，false-每次显示多个
		 * @param isAllPath node对象中的url是否是完整路径
		 */
		function showTab(node, isRemove, isAllPath) {
			var id = 'tab$' + node.menuId;//命别名
			var tab = tabs.getTab(id);
			if (!tab) {
				if (isRemove) {
					//把除第一个选项卡以外的所有选项卡全部删除
					tabs.removeAll(tabs.getTab(0));
				}
				//定义选项卡对象
				tab = {};
				//设置选项卡名称
				tab.name = id;
				//设置选项卡标题
				tab.title = node.name;
				//设置选项卡可以被关闭
				tab.showCloseButton = true;
				//设置选项卡URL，JQuery MINIUI会根据这个URL异步加载网页内容
				if (isAllPath) {
					tab.url = node.url;
				} else {
					tab.url = '<%=basePath%>' + node.url;
				}
				//把新创建的选项卡添加到选项卡控件中
				tabs.addTab(tab);
			}
			//激活当前选项卡
			tabs.activeTab(tab);
			return tab;
		}

		//单击菜单
		function onNodeClick(e) {
			var node = e.node;
			var isLeaf = e.isLeaf;
			if (isLeaf) {//单击的是树的叶子，才显示选项卡
				showTab(node, false, false);
			}
		}

		//窗体加载结束后回选皮肤
		$(function() {
			var skin = mini.Cookie.get('miniuiSkin');
			if (!skin) {
				skin = 'default';
			}
			mini.get('skinCbo').setValue(skin);
		});

		//选择皮肤
		function onChangeSkin(e) {
			mini.Cookie.set('miniuiSkin', e.value);
			window.location.reload();
		}
	</script>
</body>
</html>