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
	<div id="requirementInfoForm" style="padding:15px">
		<table border="0" style="width: 100%">
			<tr>
				<td style="text-align: right;"><label for="requirementId">编号：
				</label></td>
				<td><input id="requirementId" name="requirementId"
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
				<td style="text-align: right;"><label for="versionInfo">版本信息：
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
				<td style="text-align: right;"><label for="requirementInfo">需求信息：
				</label></td>
				<td><input id="requirementInfoDetail"
					name="requirementInfoDetail" class="mini-textarea"
					allowInput="false" style="width:300px;height:130px;" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="planStartTime">计划测试开始时间：
				</label></td>
				<td><input id="planStartTime" name="planStartTime"
					class="mini-textbox" style="width:300px;" allowInput="false"
					dateFormat="yyyy-MM-dd" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="versionReleaseTime">计划版本发布时间：
				</label></td>
				<td><input id="versionReleaseTime" name="versionReleaseTime"
					class="mini-textbox" style="width:300px;" allowInput="false"
					dateFormat="yyyy-MM-dd" /></td>
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
		<a class="mini-button" style="width:71px;" onclick="onEdit">确定</a>
	</div>
	<script type="text/javascript">
		mini.parse();
		var form = new mini.Form('requirementInfoForm');

		//初始化表单数据
		function init(requirementId) {
			$
					.ajax({
						url : '<%=basePath%>requirementInfo/requirementInfo.json',
						type : 'get',
						data : {
							requirementId : requirementId
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
