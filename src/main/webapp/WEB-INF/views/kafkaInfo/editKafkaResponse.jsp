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
<body>
	<div id="editForm" style="padding:15px">
		<table border="0" style="width: 100%">
			<tr>
				<td><input id="kafkaResponseId" name="kafkaResponseId" class="mini-hidden" /></td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="kafkaTopic">topic名称：
				</label></td>
				<td><input id="kafkaInfoId" name="kafkaInfo.kafkaTopic" class="mini-textbox"  allowInput="false"
				   style="width: 300px;"/></td>
				<td></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;">
					<label for="describes">描述：</label>
				</td>
				<td>
					<input id="describes" name="describes" class="mini-textbox"
					allowInput="true" style="width:300px;"/>
				</td>
				<td></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label for="requestJson">请求消息<span
						style="color:red">(*)</span>：
				</label></td>
				<td><input id="requestJson" name="requestJson" class="mini-textarea"  allowInput="true"
					emptyText="请输入请求消息" vtype="remote"
					required="true" onvalidation="onValidation" errorMode="none"
					requiredErrorText="请求消息不能为空" style="width:500px; height: 100px;" /></td>
				<td><div id="requestJson_error"></div></td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</div>
	<div class="mini-toolbar"
		style="text-align:center;padding-top:8px;padding-bottom:8px;"
		borderStyle="border:0;">
		<a class="mini-button" iconCls="icon-add" style="width:71px;"
			onclick="onEdit">修改</a> <span style="display:inline-block;width:25px"></span>
		<a class="mini-button" iconCls="icon-cancel" style="width:71px;"
			onclick="onCancel">取消</a>
	<div/>
	<script type="text/javascript">
		mini.parse();
		var form = new mini.Form('editForm');
	
		//初始化表单数据
		function init(kafkaResponseId) {
			$
					.ajax({
						url : '<%=basePath%>kafkaResponse/kafkaResponseByID.json',
						type : 'get',
						data : {
							kafkaResponseId : kafkaResponseId
						},
						success : function(data) {
							form.setData(data.data);
							form.setChanged(false);
						},
						dataType : 'json'
					});
		}
			
		function onCancel() {
			closeWindow('close');
			return;
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

			$
					.ajax({
						url : '<%=basePath%>kafkaResponse/editKafkaResponse.json',
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
		
	</script>
</body>
</html>