<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/pages/common/common.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/pages/demo/tree.js"></script>
</head>
<body>
	<div style="margin: 10px 0;"></div>
	<div id="tree"></div>
	<input type="button" value="获取被选中的项" onclick="self.parent.GLOBAL['tree'].getChecked();"/>
</body>
</html>