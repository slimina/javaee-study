<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/pages/common/common.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/pages/demo/combobox.js"></script>
</head>
<body>
	<div style="margin: 10px 0;"></div>
	<select class="easyui-combobox" name="state" style="width:200px;">
		<option value="AL">Alabama</option>
		<option value="AK">Alaska</option>
		<option value="AZ">Arizona</option>
		<option value="AR">Arkansas</option>
		<option value="CA">California</option>
		<option value="CO">Colorado</option>
		<option value="CT">Connecticut</option>
		<option value="DE">Delaware</option>
		<option value="FL">Florida</option>
		<option value="GA">Georgia</option>
		<option value="HI">Hawaii</option>
		<option value="ID">Idaho</option>
		<option value="IL">Illinois</option>
		<option value="IN">Indiana</option>
	</select>
		<br/>	<br/>
	<div id="combobox"></div>
	<br/>	<br/>
	<div id="combobox-tree"></div>
	<br/>	<br/>
	<div id="combobox-grid"></div>
</body>
</html>