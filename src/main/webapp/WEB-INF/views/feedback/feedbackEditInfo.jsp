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
<div id="editForm" style="padding:15px">
	<table border="0" style="width: 100%">
		<tr>
			<td><input id="feedbackIssueId" name="feedbackIssueId"
					   class="mini-hidden" /></td>
		</tr>
		<tr>
			<td style="text-align: right;"><label for="teamId">小组<span
					style="color:red">(*)</span>：
			</label></td>
			<td><input id="teamId" name="team.teamId" class="mini-combobox"
					   url="<%=basePath%>team/teamListByDepartmentId.json"
					   dataField="data" valueField="teamId" textField="name" allowInput="true"
					   valueFromSelect="true" required="false" onvalidation="onValidation"
					   errorMode="none" requiredErrorText="请选择小组" emptyText="请选择小组"
					   style="width:300px;" onvaluechanged="onTeamChanged" /></td>
			<td><div id="teamId_error"></div></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td style="text-align: right;"><label for="feedbackProjectId">项目名称<span
					style="color:red">(*)</span>：
			</label></td>
			<td><input id="feedbackProjectId" name="feedbackProjectId" class="mini-combobox"
					   dataField="data" valueField="feedbackProjectId"
					   textField="feedbackProjectName" allowInput="true" valueFromSelect="true"
					   required="true" onvalidation="onValidation" errorMode="none"
					   requiredErrorText="请选择项目" emptyText="请选择项目" style="width:300px;"/></td>
			<td><div id="feedbackProjectId_error"></div></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td style="text-align: right;"><label for="feedbackIssuesContent">反馈详情<span
					style="color:red">(*)</span>：
			</label></td>
			<td><input id="feedbackIssuesContent" name="feedbackIssuesContent"
					   class="mini-textarea" emptyText="请输入问题描述" required="true"
					   allowInput="false" onvalidation="onValidation" errorMode="none"
					   requiredErrorText="反馈详情不能为空" style="width:300px;height:200px;" /></td>
			<td><div id="feedbackIssuesContent_error"></div></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td style="text-align: right;"><label for="reason">问题原因<span
					style="color:red">(*)</span>：
			</label></td>
			<td><input id="reason" name="reason"
					   class="mini-textarea" emptyText="请输入问题原因"
					   required="true" onvalidation="onValidation" errorMode="none"
					   requiredErrorText="问题原因不能为空" style="width:300px;height:70px;" /></td>
			<td><div id="reason_error"></div></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td style="text-align: right;"><label for="solution">解决方案：
			</label></td>
			<td><input id="solution" name="solution"
					   class="mini-textarea" emptyText="请输入解决方案"
					   required="false" onvalidation="onValidation" errorMode="none"
					   requiredErrorText="解决方案不能为空" style="width:300px;height:50px;" /></td>
			<td><div id="solution_error"></div></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td style="text-align: right;"><label for="improvement">改进措施<span
					style="color:red">(*)</span>：
			</label></td>
			<td><input id="improvement" name="improvement"
					   class="mini-textarea" emptyText="请输入改进措施"
					   required="true" onvalidation="onValidation" errorMode="none"
					   requiredErrorText="改进措施不能为空" style="width:300px;height:50px;" /></td>
			<td><div id="improvement_error"></div></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td style="text-align: right;"><label for="calssify">问题分类<span
					style="color:red">(*)</span>：
			</label></td>
			<td><input id="calssify" name="calssify"
					   class="mini-radiobuttonlist"
					   url="<%=basePath%>feedback/classfiyList.json"
					   dataField="data" valueField="classfiy" textField="classfiyText"
					   required="true" onvalidation="onValidation" errorMode="none"
					   requiredErrorText="请选择问题分类" style="width:300px;;" /></td>
			<td><div id="classfify_error"></div></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td style="text-align: right;"><label for="resolveStatus">解决状态<span
					style="color:red">(*)</span>：
			</label></td>
			<td><input id="resolveStatus" name="resolveStatus"
					   class="mini-radiobuttonlist"
					   url="<%=basePath%>feedback/statusList.json"
					   dataField="data" valueField="status" textField="statusText"
					   required="true" onvalidation="onValidation" errorMode="none"
					   requiredErrorText="请选择解决状态" style="width:300px;;" /></td>
			<td><div id="resolveStatus_error"></div></td>
			<td>&nbsp;</td>
		</tr>
	</table>
	<div class="mini-toolbar"
		 style="text-align:center;padding-top:8px;padding-bottom:8px;"
		 borderStyle="border:0;">
		<a class="mini-button" iconCls="icon-edit" style="width:71px;"
		   onclick="onEdit">修改</a> <span
			style="display:inline-block;width:25px"></span> <a
			class="mini-button" iconCls="icon-cancel" style="width:71px;"
			onclick="onCancel">取消</a>
	</div>
</div>

<script type="text/javascript">
	mini.parse();
	var form = new mini.Form('editForm');
	var team = mini.get("teamId");
	var project = mini.get("feedbackProjectId");

	function onTeamChanged(e) {
		var teamId=team.getValue();
		project.setValue();
		var url="<%=basePath%>feedback/feedbackRelationInfoListByTeamId.json?teamId="+ teamId;
		project.setUrl(url);
		project.select(0);
	}


	var _data;
	//初始化表单数据
	function init(feedbackIssueId) {
		$.ajax({
			  url : '<%=basePath%>feedback/feedbackIssueInfo.json',
			  type : 'get',
			  data : {
				  feedbackIssueId : feedbackIssueId
			  },
			  success : function(data) {
				  _data = data.data;
				  var reason = mini.get("reason");
				  var solution = mini.get("solution");
				  var improvement = mini.get("improvement");
				  var teamId=data.data.team.teamId;
				  var url="<%=basePath%>feedback/feedbackRelationInfoListByTeamId.json?teamId="+ teamId;
				  project.setUrl(url);

				  form.setData(data.data);
				  reason.setValue(data.data.reason);
				  solution.setValue(data.data.solution);
				  improvement.setValue(data.data.improvement);
				  form.setChanged(false);
			  },
			  dataType : 'json'
		  });
	}

	function onEdit(e) {
		//没有改变，不用保存，直接关闭窗口
		if (!form.isChanged()) {
			closeWindow('close');
			return;
		}

		form.validate();
		if (form.isValid() == false)
			return;

		var messageId = mini.loading('处理中，请稍等......', '提示');

		$.ajax({
				  url : '<%=basePath%>feedback/editFeedbackIssue.json',
				  type : 'post',
				  data : form.getData(true, false),
				  success : function(data) {
					  mini.hideMessageBox(messageId);
					  if (data.code==200 && data.data) {
						  closeWindow('ok');
					  } else {
						  closeWindow(data.msg);
					  }
				  },
				  dataType : 'json'
			  });
	}

	function onCancel() {
		closeWindow('close');
		return;
	}

</script>
</body>
</html>
