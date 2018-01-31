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
	<div id="detailForm" style="padding:15px">
		<table border="0" style="width: 100%">
			<tr>
				<td style="text-align: right;"><label for="id">编号：
				</label></td>
				<td><input id="id" name="id"
					class="mini-textbox" style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="departmentName">部门：
				</label></td>
				<td><input id="departmentName" name="departmentName"
					class="mini-textbox" style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="departmentName">小组： </label></td>
				<td><input id="teamName" name="teamName" class="mini-textbox"
					style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="serviceName">项目：
				</label></td>
				<td><input id="serviceName" name="serviceName"
					class="mini-textbox" style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="serviceDes">项目描述：
				</label></td>
				<td><input id="serviceDes" name="serviceDes"
					class="mini-textbox" style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="env">执行环境：
				</label></td>
				<td><input id="env" name="env"
					class="mini-textbox" style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="statusText">状态：
				</label></td>
				<td><input id="statusText" name="statusText"
					class="mini-textbox" style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="total">用例总数：
				</label></td>
				<td><input id="total" name="total"
					class="mini-textbox" style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="passed">通过用例数：
				</label></td>
				<td><input id="passed" name="passed"
					class="mini-textbox" style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="passedPercent">通过率：
				</label></td>
				<td><input id="passedPercent" name="passedPercent"
					class="mini-textbox" style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="failed">失败用例数：
				</label></td>
				<td><input id="failed" name="failed"
					class="mini-textbox" style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="failedPercent">失败率：
				</label></td>
				<td><input id="failedPercent" name="failedPercent"
					class="mini-textbox" style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="skipped">跳过未执行用例数：
				</label></td>
				<td><input id="skipped" name="skipped"
					class="mini-textbox" style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="skippedPercent">跳过未执行率：
				</label></td>
				<td><input id="skippedPercent" name="skippedPercent"
					class="mini-textbox" style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="startTime">开始时间：
				</label></td>
				<td><input id="startTime" name="startTime"
					class="mini-textbox" style="width:300px;" allowInput="false"
					dateFormat="yyyy-MM-dd HH:mm:ss" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="endTime">结束时间：
				</label></td>
				<td><input id="endTime" name="endTime"
					class="mini-textbox" style="width:300px;" allowInput="false"
					dateFormat="yyyy-MM-dd HH:mm:ss" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="durationTime">耗时：
				</label></td>
				<td><input id="durationTime" name="durationTime"
					class="mini-textbox" style="width:300px;" allowInput="false"/></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="createTime">录入时间：
				</label></td>
				<td><input id="createTime" name="createTime"
					class="mini-textbox" style="width:300px;" allowInput="false"
					dateFormat="yyyy-MM-dd HH:mm:ss" /></td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</div>
	<div class="mini-toolbar"
		style="text-align:center;padding-top:8px;padding-bottom:8px;"
		borderStyle="border:0;">
		<a class="mini-button" style="width:71px;" onclick="onOk">确定</a>
	</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var form = new mini.Form('detailForm');

		//初始化表单数据
		function init(id) {
			$
					.ajax({
						url : '<%=basePath%>testSuiteStatistic/testSuiteStatisticInfo.json',
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
