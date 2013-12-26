<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/pages/common/common.jsp"%>
<script type="text/javascript"
	src="<%=contextPath%>/scripts/common/global.js"></script>
<script type="text/javascript" src="<%=contextPath%>/pages/main.js"></script>
</head>
<body class="easyui-layout">
	<div region="north"
		style="height: 80px; padding: 5px; background-color: #3366cc;">
		<span style="float: left;"> <img
			src="http://www.sinotrucker.com/images/common/main_logo.png"
			alt="智能物流" />
		</span>
	</div>
	<div region="west" title="导航菜单" border="true" style="width: 160px;">
		<div id="main-function-menus" border="false">
		</div>
	</div>
	<div id="mainPanel" region="center" title="功能内容" border="true">
		<div id="tabs" class="easyui-tabs" fit="true" border="false">
			<div title="欢迎使用" style="padding: 20px; overflow: hidden;" id="home">
				<h1>Welcome to WL !</h1>
			</div>
		</div>
	</div>
	<div id="main-window-div">
		<iframe scrolling="auto" id="openWindowIframe" frameborder="0"  src="" style="width:100%;height:100%;"></iframe>
	</div>
	<div id="main-window-div1">
		<iframe scrolling="auto" id="openWindowIframe1" frameborder="0"  src="" style="width:100%;height:100%;"></iframe>
	</div>
	<div id="main-window-div2">
		<iframe scrolling="auto" id="openWindowIframe2" frameborder="0"  src="" style="width:100%;height:100%;"></iframe>
	</div>
	<div region="south" border="true" style="height: 40px; padding: 10px;">
		<div style="text-align: center; width: 100%">版本v1.0.0 ©2013
			北京中交兴路供应链管理有限公司 版权所有</div>
	</div>
</body>
</html>