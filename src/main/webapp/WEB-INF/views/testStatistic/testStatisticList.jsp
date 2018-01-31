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
		<table>
			<tr>
				<td style="text-align: left;"><label for="className">类名 ：</label>
					<input id="className" name="className" class="mini-textbox"
					allowInput="true" emptyText="支持按类名查询" style="width: 120px;" /></td>
				<td style="text-align: left;"><label for="caseName">方法名 ：</label>
					<input id="caseName" name="caseName" class="mini-textbox"
					allowInput="true" emptyText="支持按方法名查询" style="width: 120px;" /></td>
				<td style="text-align: left;"><label for="status">状态 ：</label>
					<input id="testStatisticStatus" name="status" class="mini-combobox"
					url="<%=basePath%>testStatistic/statusList.json"
					dataField="data" valueField="status" textField="statusText"
					allowInput="true" emptyText="支持按状态查询" style="width: 120px;" /></td>
			</tr>
		</table>
	</div>
	<div id="actionTb" class="mini-toolbar">
		<table style="width: 100%;">
			<tr>
				<td><a class="mini-button" iconCls="icon-search"
					style="width: 65px;" onclick="onSearch">查询</a> <a
					class="mini-button" iconCls="icon-cancel" style="width: 65px;"
					onclick="onCancel">重置</a>
				</td>
			</tr>
		</table>
	</div>
	<div id="actionTb" class="mini-toolbar">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;">
				<a class="mini-button"
					iconCls="icon-search" onclick="detail">查看详情</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="statisticDg" class="mini-datagrid"
			url="<%=basePath%>testStatistic/testStatisticList.json"
			multiSelect="false" showEmptyText="true" emptyText="暂无数据！"
			totalField="data.totalElements" dataField="data.pageList" pageSize=15
			sizeList="[5,10,15]">
			<div property="columns">
				<div type="checkcolumn"></div>
				<div field="buildId" align="center" headerAlign="center">构建编号</div> -->
				<!-- <div field="serviceId" align="center" headerAlign="center">服务id</div> -->
				<div field="serviceName" align="center" headerAlign="center">项目</div>
				<div field="serviceDes" align="center" headerAlign="center">项目描述</div>
				<div field="statusText" align="center" headerAlign="center">状态</div>
				<div field="className" align="center" headerAlign="center">类名</div>
				<div field="caseName" align="center" headerAlign="center">方法名</div>
				<div field="caseDescription" align="center" headerAlign="center">方法描述</div>
				<div field="durationTime" align="center" headerAlign="center">耗时</div>
				<div field="createTime" align="center" headerAlign="center"
					dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var searchForm = new mini.Form('searchForm');
		var statisticDg = mini.get("statisticDg");
		var className = mini.get("className");
		var caseName = mini.get("caseName");
		var testStatisticStatus = mini.get("testStatisticStatus");
		
		//初始化
		function init(buildId) {
			statisticDg.load({
				buildId: buildId
				});
		}
		
		function setDefaultValue() {
			if (isEmpty(testStatisticStatus.getValue())) {
				testStatisticStatus.setValue(0);
			}
		}
		
		function resetDefaultValue() {
			if (isEmpty(testStatisticStatus.getValue())) {
				testStatisticStatus.setValue();
			}
		}

		//关键字模糊查询
		function onSearch() {
			setDefaultValue();
			var classNameValue=className.getValue();
			var caseNameValue=caseName.getValue();
			var statusValue=testStatisticStatus.getValue();
			statisticDg.load({
				className:classNameValue,
				caseName:caseNameValue,
				status:statusValue
				});
			resetDefaultValue();
		}

		function onCancel() {
			var teamUrl = '<%=basePath%>team/teamListByDepartmentId.json';
			team.setUrl(teamUrl);
			var projectUrl = '<%=basePath%>automationProject/automationProjectListByDepartmentId.json';
			project.setUrl(projectUrl);
			searchForm.reset();
		}
			
		function onDepartmentChanged(e) {
	    	var departmentId=department.getValue();
	    	team.setValue();
	    	var url="<%=basePath%>team/teamListByDepartmentId.json?departmentId="+departmentId;
	    	team.setUrl(url);
		}
			
		function onTeamChanged(e) {
	    	var teamId=team.getValue();
	   		project.setValue();
	    	var url="<%=basePath%>automationProject/automationProjectListByTeamId.json?teamId="+ teamId;
			project.setUrl(url);
		}
		
		function detail() {
			var row = statisticDg.getSelected();
			if (row) {
				var id = row.id;
				mini
						.open({
							url : '<%=basePath%>testStatistic/testStatisticInfoUI',
							title : '查看测试用例详情',
							width : 760,
							height : 600,
							showMaxButton : true,
							allowResize : false,
							onload : function() {
								var iframe = this.getIFrameEl();
								iframe.contentWindow.init(id);
							},
							ondestroy : function(action) {
								statisticDg.reload();
							}
						});
			} else {
				mini.alert("请选中一条记录");
			}
		}
	</script>
</body>
</html>