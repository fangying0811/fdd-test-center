<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>房多多质量管理平台-导航到登录页面</title>
</head>
<body>
	<script type="text/javascript">
		open('<%=basePath%>loginUI', '_top');
	</script>
</body>
</html>
