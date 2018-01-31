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
	<div id="searchForm">
		<form id="exportForm" action="<%=basePath%>dataCenter/SendKafka"
			method="post">
			<table>
	
				<tr>
			    <td style="text-align:left;"><label for="resource">kafka_topic：</label>
						<input id="kafka_topic" name="kafka_topic" class="mini-textbox"
						required = "true" allowInput="true" emptyText="请输入topic名称" style="width:210px;" /></td>	
			</tr>
			</table>
			<table>
	
				<tr>
				<td style="text-align:left;"><label for="resource">requestJson：</label>
						<input id="requestJson" name="requestJson" class="mini-textarea"
						required = "true" allowInput="true" emptyText="请输入kafka消息内容" style="width:600px; height: 100px;" /></td>		
			</tr>
			</table>
		</form>
	</div>
	<div id="actionTb" class="mini-toolbar">
		<table style="width:100%;">
			<tr>
				<td><a class="mini-button" iconCls="icon-search"
					style="width:65px;" onclick="onSearch">发送</a> <a
					class="mini-button" iconCls="icon-cancel" style="width:65px;"
					onclick="onCancel">重置</a>
					</td>
			</tr>
		</table>
		</form>
	</div>
	
	<div class="mini-fit">
		<div id="requirementInfoDg" class="mini-datagrid"
			url="<%=basePath%>dataCenter/SendKafka"
			multiSelect="false" showEmptyText="true" emptyText="暂无数据！"
			totalField="data.totalElements" dataField="data.pageList" sizeList="[5,10]">
			<div property="columns">
				<div type="checkcolumn"></div>
				<div field="kafka_topic" align="center" headerAlign="center">topic名称</div>
				<div field="requestJson" align="center" headerAlign="center">请求消息</div>
				<div field="res" align="center" headerAlign="center">响应消息</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var searchForm = new mini.Form('searchForm');
		var kafka_topic = mini.get("kafka_topic");
		var requestJson = mini.get("requestJson");
		var requirementInfoDg = mini.get("requirementInfoDg");
		requirementInfoDg.load();
		
		function setDefaultValue() {
			
		}
		
		function resetDefaultValue() {

		}
		
		function onLoad(e) {
			setDefaultValue();
			var grid = e.sender;
			grid.setShowPager(grid.totalCount > 0);
			resetDefaultValue();
		}

		//关键字模糊查询
		function onSearch() {
			setDefaultValue();
			requirementInfoDg.load(searchForm.getData(true, false));
			resetDefaultValue();
		}

		function onCancel() {
			searchForm.reset();
		}
		
	</script>
</body>
</html>