<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>房多多质量管理平台</title>
	<script type="text/javascript" src="<%=basePath%>scripts/boot.js"></script>
</head>
<body>
<div id="searchForm">
	<form id="exportForm" action="<%=basePath%>feedback/issueInfo.json" method="get">
		<table>
			<tr>
				<td style="text-align: left;"><label for="department">部门：</label>
					<input id="departmentId" name="departmentId" class="mini-combobox"
						   url="<%=basePath%>department/departmentListAll.json"
						   dataField="data" valueField="departmentId" textField="name"
						   allowInput="true" emptyText="支持按部门查询" style="width: 120px;"
						   onvaluechanged="onDepartmentChanged" /></td>
				<td style="text-align: left;"><label for="team">小组：</label> <input
						id="teamId" name=teamId class="mini-combobox"
						url="<%=basePath%>team/teamListByDepartmentId.json"
						dataField="data" valueField="teamId" textField="name"
						allowInput="true" emptyText="支持按小组查询" style="width: 120px;"/>
                </td>
				</tr>
			</tr>
		</table>
    </form>
</div>
<div id="actionTb" class="mini-toolbar">
	<table style="width: 100%;">
		<tr>
			<td>
                <a class="mini-button"
                   iconCls="icon-edit" onclick="edit">编辑</a>
                <a class="mini-button" iconCls="icon-search" style="width: 65px;" onclick="onSearch">查询</a>
                <a class="mini-button" iconCls="icon-cancel" style="width: 65px;" onclick="onCancel">重置</a>
				<a class="mini-button" iconCls="icon-reload" onclick="syncFeedbackIssue()">获取用户反馈缺陷</a>
				<a class="mini-button" iconCls="icon-reload" onclick="syncToRedmineIssue()">同步缺陷到redmine</a>

		</tr>
	</table>
</div>
<div class="mini-fit">
	<div id="feedback" class="mini-datagrid"
		 url="<%=basePath%>feedback/issueInfo.json"
		 multiSelect="false" showEmptyText="true" emptyText="暂无数据！"
		 totalField="data.totalElements" dataField="data.pageList" pageSize=10
		 sizeList="[5,10]">
		<div property="columns">
            <div type="checkcolumn"></div>
			<div field="feedbackIssueId" align="center" headerAlign="center" renderer="feedbackdRenderer">缺陷ID</div>
			<div field="redmineIssueId" align="center" headerAlign="center" renderer="redminedRenderer">关联缺陷ID</div>
			<div field="team.name" align="center" headerAlign="center">所属小组</div>
			<div field="feedbackProjectName" align="center" headerAlign="center">项目名称</div>
			<div field="feedbackIssueTitle" align="center" headerAlign="center">反馈标题</div>
			<div field="feedbackIssuesContent" align="center" headerAlign="center">反馈详情</div>
			<div field="resolveStatusName" align="center" headerAlign="center">状态</div>
			<div field="calssifyName" align="center" headerAlign="center">分类</div>
			<div field="synStautsName" align="center" headerAlign="center">同步状态</div>
            <div field="createTime" align="center" headerAlign="center"
                 dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	mini.parse();
	var searchForm = new mini.Form('searchForm');
	var feedback = mini.get("feedback");
	var team = mini.get("teamId");
	var department = mini.get("departmentId");
    team.select(1)
    feedback.load(searchForm.getData(true, false));
    resetDefaultValue();


	function setDefaultValue() {
		if (isEmpty(department.getValue())) {
			department.setValue(0);
		}
		if (isEmpty(team.getValue())) {
			team.setValue(0);
		}
	}

	function resetDefaultValue() {
		if (isEmpty(department.getValue())) {
			department.setValue();
		}
		if (isEmpty(team.getValue())) {
			team.setValue();
		}
	}

	function onLoad(e) {
		var grid = e.sender;
		grid.setShowPager(grid.totalCount > 0);

	}

	function feedbackdRenderer(e) {
		return '<a href="https://feedback.fangdd.com/issues/'+e.value+'" target="_blank">'
			   + e.value + '</a>';
	}
	function redminedRenderer(e) {
		return '<a href="https://redmine.fangdd.net/issues/'+e.value+'" target="_blank">'
			   + e.value + '</a>';
	}

	//关键字模糊查询
	function onSearch() {
		setDefaultValue();
		feedback.load(searchForm.getData(true, false));
		resetDefaultValue();
	}

	function onCancel() {
		var url='<%=basePath%>team/teamListByDepartmentId.json';
		team.setUrl(url);
		searchForm.reset();
	}

	function onDepartmentChanged(e) {
		var departmentId=department.getValue();
		team.setValue();
		var url="<%=basePath%>team/teamListByDepartmentId.json?departmentId="+departmentId;
		team.setUrl(url);
	}

    function edit() {
        var row = feedback.getSelected();
        if (row) {
            var id = row.feedbackIssueId;
            mini
                    .open({
                              url : '<%=basePath%>feedback/editFeedbackInfoUI',
                              title : '修改项目',
                              width : 650,
                              height : 600,
                              showMaxButton : true,
                              allowResize : false,
                              onload : function() {
                                  var iframe = this.getIFrameEl();
                                  iframe.contentWindow.init(id);
                              },
                              ondestroy : function(action) {
                                  if (action == 'ok') {
                                      mini.alert("修改成功");
									  feedback.reload();
                                  } else if (action != 'close' && !isEmpty(action)) {
                                      mini.alert(action);
                                  }else {
                                      if (action != 'close' && isEmpty(action)){
                                          mini.alert("修改失败");
                                      }
                                  }
                              }
                          });
        } else {
            mini.alert("请选中一条记录");
        }
    }

	function syncToRedmineIssue() {
		var row = feedback.getSelected();
		if (row) {
			var redmineIssueId = row.redmineIssueId;
			if(redmineIssueId > 0){
				mini.alert("该条记录已同步");
				return;
			}
			var id = row.feedbackIssueId;
			$.ajax({
					   url: '<%=basePath%>feedback/syncToRedmineIssue.json?feedbackIssueId=' + id,
					   type: 'post',
					   data: {},
					   success: function (data) {
						   if (data.code == 200 && data.data) {
							   mini.alert("同步成功");
							   feedback.reload();
						   } else {
							   mini.alert("同步失败");
						   }
					   },
					   dataType: 'json'
				   });
			}else{
				mini.alert("请选择一条记录！")
			}
		}

	function syncFeedbackIssue() {
		$.ajax({
			  url : '<%=basePath%>feedback/syncFeedbackIssue.json',
			  type : 'post',
			  data : {
			  },
			  success : function(data) {
				  if (data.code==200 && data.data) {
					  mini.alert("获取成功");
					  feedback.reload();
				  } else {
					  mini.alert("获取失败");
				  }
			  },
			  dataType : 'json'
		  });
	}
</script>
</body>
</html>