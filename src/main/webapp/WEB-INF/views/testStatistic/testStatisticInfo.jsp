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
	<div class="mini-fit">
		<div id="detailForm" style="padding: 15px">
				<table border="0" style="width: 100%">
					<tr>
						<td style="text-align: right;">编号：</td>
						<td><input id="id" name="id"
							class="mini-textbox" style="width: 250px;" allowInput="false" /></td>
						<td style="text-align: right;">项目：</td>
						<td><input id="serviceName" name="serviceName"
							class="mini-textbox" style="width: 250px;" allowInput="false" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">项目描述：</td>
						<td><input id="serviceDes" name="serviceDes"
							class="mini-textbox" style="width: 250px;" allowInput="false" /></td>
						<td style="text-align: right;">类名：</td>
						<td><input id="className" name="className"
							class="mini-textbox" style="width: 250px;" allowInput="false" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">方法名：</td>
						<td><input id="caseName" name="caseName"
							class="mini-textbox" style="width: 250px;" allowInput="false" /></td>
						<td style="text-align: right;">方法描述：</td>
						<td><input id="caseDescription" name="caseDescription"
							class="mini-textbox" allowInput="false" style="width: 250px;" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">开始时间：</td>
						<td><input id="startTime" name="startTime" class="mini-textbox"
							style="width: 250px;" allowInput="false" dateFormat="yyyy-MM-dd HH:mm:ss"/></td>
						<td style="text-align: right;">结束时间：</td>
						<td><input id="endTime" name="endTime" class="mini-textbox" 
						style="width: 250px;" allowInput="false" dateFormat="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">耗时：</td>
						<td><input id="durationTime" name="durationTime" class="mini-textbox"
							style="width: 250px;" allowInput="false" /></td>
						<td style="text-align: right;">状态 ：</td>
						<td><input id="statusText" name="statusText" class="mini-textbox"
							style="width: 250px;" allowInput="false" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">异常信息：</td>
						<td colspan="3"><input id="caseMessage" name="caseMessage"
							class="mini-textarea" allowInput="false"
							style="width: 600px; height: 180px;" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">日志：</td>
						<td colspan="3"><input id="caseLog" name="caseLog"
							class="mini-textarea" allowInput="false"
							style="width: 600px; height: 320px;" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">录入时间：</td>
						<td colspan="3"><input id="createTime" name="createTime"
							class="mini-textbox" dateFormat="yyyy-MM-dd HH:mm:ss"
							style="width: 250px;" allowInput="false" /></td>
					</tr>
				</table>
			<div property="footer"
				style="text-align: center; padding-top: 8px; padding-bottom: 8px;"
				borderStyle="border:0;">
				<a class="mini-button" iconCls="icon-ok" style="width: 65px;"
					onclick="onOk">确定</a>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var form = new mini.Form('detailForm');

		//初始化表单数据
		function init(id) {
			$
					.ajax({
						url : '<%=basePath%>testStatistic/testStatisticInfo.json',
						type : 'get',
						data : {
							id : id
						},
						success : function(data) {
							form.setData(data.data);
						},
						dataType : 'json'
					});
		}

		function onOk(e) {
			closeWindow('ok');
		}
	</script>
</body>
</html>
