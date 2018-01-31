//验证提示
function onValidation(e) {
	var id = e.sender.id + "_error";
	var el = $('#' + id);

	if (el.size() > 0) {
		var errorText = e.errorText;
		if (errorText) {
			el.addClass('errorText');
			el.html(e.errorText);
		} else {
			el.removeClass('errorText');
			el.html('');
		}
	}
}

// 替换所有的回车换行
function TransferString(content) {
	var string = content;
	try {
		string = string.replace(/\r\n/g, "<br>")
		string = string.replace(/\n/g, "<br>");
	} catch (e) {
		alert(e.message);
	}
	return string;
}

function isEmpty(content) {
	if (content == null || content <= 0 || content.length == 0) {
		return true;
	}
	return false;
}

function compareTime(startTime, endTime) {
	var sDate = new Date(startTime)
	var eDate = new Date(endTime);
	mini.alert(sDate);
	mini.alert(eDate);
	if (sDate > eDate) {
		return false;
	}
	return true;
}

// 关闭添加窗口
function closeWindow(action) {
	if (window.CloseOwnerWindow) {
		return window.CloseOwnerWindow(action);
	} else {
		window.close();
	}
}

// 展开数节点时，折叠其它同级节点
function onBeforeExpand(e) {
	var tree = e.sender;
	var nowNode = e.node;
	var root = tree.getRootNode();

	tree.cascadeChild(root, function(node) {
		if (tree.isExpandedNode(node)) {
			if (node != nowNode && !tree.isAncestor(node, nowNode)) {
				tree.collapseNode(node, true);
			}
		}
	});
}

// 重置表单
function onReset(form) {
	form.reset();
	var fs = form.getFields();
	for (var i = 0; i < fs.length; i++) {
		var id = fs[i].id + "_error";
		var el = $('#' + id);
		if (el.size() > 0) {
			el.removeClass('errorText');
			el.html('');
		}
	}
}