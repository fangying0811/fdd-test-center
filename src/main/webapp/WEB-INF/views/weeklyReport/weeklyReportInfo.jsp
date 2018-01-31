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
	<div id="weeklyReportForm" style="padding:15px">
		<table border="0" style="width: 100%">
			<tr>
				<td style="text-align: right;"><label for="weeklyReportId">编号：
				</label></td>
				<td><input id="weeklyReportId" name="weeklyReportId"
					class="mini-textbox" style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="user">录入人员：
				</label></td>
				<td><input id="userTrueName" name="user.trueName"
					class="mini-textbox" style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="department">部门：
				</label></td>
				<td><input id="departmentName" name="department.name"
					class="mini-textbox" style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="team">小组： </label></td>
				<td><input id="teamName" name="team.name" class="mini-textbox"
					style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="project">项目：
				</label></td>
				<td><input id="projectName" name="project.projectName"
					class="mini-textbox" style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="type">类型：
				</label></td>
				<td><input id="typeText" name="typeText" class="mini-textbox"
					style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="versionInfoDetail">版本信息：
				</label></td>
				<td><input id="versionInfoDetail" name="versionInfoDetail"
					class="mini-textarea" allowInput="false"
					style="width:300px;height:40px;" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="resource">责任人：
				</label></td>
				<td><input id="resource" name="resource" class="mini-textbox"
					style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="caseNumber">
						版本用例总数： </label></td>
				<td><input id="caseNumber" name="caseNumber"
					class="mini-textbox" style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="versionTestTime">测试开始时间：
				</label></td>
				<td><input id="versionTestTime" name="versionTestTime"
					class="mini-textbox" dateFormat="yyyy-MM-dd" style="width:300px;"
					allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="testTimeText">版本测试时间(单位：天)：
				</label></td>
				<td><input id="testTimeText" name="testTimeText"
					class="mini-textbox" style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="testStatus">进度：
				</label></td>
				<td><input id="testStatusText" name="testStatusText"
					class="mini-textbox" style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="bugNumber">
						版本bug总数： </label></td>
				<td><input id="bugNumber" name="bugNumber" class="mini-textbox"
					style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="bugCritical">
					严重bug数： </label></td>
				<td><input id="bugCritical" name="bugCritical" class="mini-textbox"
						   style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="versionReleaseTime">测试完成时间：
				</label></td>
				<td><input id="versionReleaseTime" name="versionReleaseTime"
					class="mini-textbox" dateFormat="yyyy-MM-dd" style="width:300px;"
					allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="remarkDetail">备注：
				</label></td>
				<td><input id="remarkDetail" name="remarkDetail"
					class="mini-textarea" allowInput="false"
					style="width:300px;height:120px;" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="createTime">录入时间：
				</label></td>
				<td><input id="createTime" name="createTime"
					class="mini-textbox" dateFormat="yyyy-MM-dd HH:mm:ss"
					style="width:300px;" allowInput="false" /></td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<div property="footer"
			style="text-align: center; padding-top: 8px; padding-bottom: 8px;"
			borderStyle="border:0;">
			<a class="mini-button" style="width:71px;" onclick="onEdit">确定</a>
		</div>
	</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var form = new mini.Form('weeklyReportForm');

		//初始化表单数据
		function init(weeklyReportId) {
			$
					.ajax({
						url : '<%=basePath%>weeklyReport/weeklyReportInfo.json',
						type : 'get',
						data : {
							weeklyReportId : weeklyReportId
						},
						success : function(data) {
							form.setData(data.data);
						},
						dataType : 'json'
					});
		}

		function onEdit(e) {
			closeWindow('ok');
		}
	</script>
</body>
</html>
