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
<script type="text/javascript" src="<%=basePath%>scripts/boot.js">
</script>
</head>

<body>
	<div id="loginWin" class="mini-window" title="用户登录"
		style="width: 480px; height: 180px;" showModal="true"
		showCloseButton="false" allowDrag="false">
		<div id="loginForm" style="padding: 15px;">
			<table border="0">
				<tr>
					<tr>
						<td style="text-align: right;"><label for="username$text">账号：</label></td>
						<td><input id="username" name="username" class="mini-textbox"
							emptyText="请输入登录用户名" required="true" onvalidation="onValidation"
							errorMode="none" requiredErrorText="登录用户名不能为空"
							style="width: 200px;" /></td>
						<td><div id="username_error"></div></td>
						<td>&nbsp;</td>
					</tr>
				</tr>
				<tr>
					<td style="text-align: right;"><label for="pwd$text">密码：</label></td>
					<td><input id="password" name="password" class="mini-password"
						emptyText="请输入密码" required="true" onvalidation="onValidation"
						errorMode="none" requiredErrorText="密码不能为空" style="width: 200px;"
						onenter="onLoginClick" /></td>
					<td><div id="password_error"></div></td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td></td>
					<td style="padding-top: 5px; text-align: right;"><a
						onclick="onLoginClick" iconCls="icon-unlock" class="mini-button"
						style="width: 71px;">登录</a><span
						style="display: inline-block; width: 25px"></span><a
						onclick="onRegister" class="mini-button" style="width: 71px;">注册</a></td>
					<td></td>

				</tr>
			</table>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();

		var loginWin = mini.get('loginWin');
		loginWin.show();

		//浏览器大小调整时，让登录window总是居中
		$(window).resize(function() {
			loginWin.show();
		});

		function onRegister() {
			mini.open({
				url : '<%=basePath%>user/registerUserUI',
				title : '注册用户',
				width : 800,
				height : 600,
				showMaxButton : true,
				allowResize : false,
				ondestroy : function(action) {
					if (action == 'ok') {
						mini.alert("注册成功");
					} else if (action == 'fail') {
						mini.alert("注册失败");
					}
				}
			});
		}

		//登录
		function onLoginClick(e) {
			var form = new mini.Form('#loginForm');

			form.validate();
			if (form.isValid() == false)
				return;
			
			var messageId = mini.loading('登录中，请稍等......', '提示');

			$
					.ajax({
						url : '<%=basePath%>user/login.json',
						type : 'post',
						data : form.getData(true, false),
						success : function(data) {
							mini.hideMessageBox(messageId);
							if (data.code==200) {
								loginWin.hide();
								//显示“登录成功loadding” 
								mini.mask({
									el : document.body,
									cls : 'mini-mask-loading',
									html : data.msg
								});
								setTimeout(
										function() {
											//取消“登录成功loadding” 
											mini.unmask(document.body);
											//导航到系统主页面
											location.href = '<%=basePath%>user/mainUI';
										}, 500);
							} else {
								mini.get("password").setValue('');
								mini.showMessageBox({
									width : 250,
									title : '提示',
									buttons : [ 'ok' ],
									message : data.msg,
									iconCls : 'mini-messagebox-warning'
								});
							}
						},
						dataType : 'json'
					});
		}
	</script>
</body>
</html>
