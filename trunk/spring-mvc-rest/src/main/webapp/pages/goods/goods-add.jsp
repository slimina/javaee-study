<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/pages/common/common.jsp"%>
<script type="text/javascript"
	src="<%=contextPath%>/pages/goods/goods-add.js"></script>
</head>
<body>
	<div>
		<form id="goods-add-form">
			<table>
				<tr>
					<td>货物编码</td>
					<td><input name="goodsCode" class="easyui-validatebox"  required="true" id="goodsCode-add-input"/></td>
				</tr>
				<tr>
					<td>货物名称</td>
					<td><input name="goodsName" class="easyui-validatebox"  required="true" id="goodsName-add-input"/></td>
				</tr>
				<tr>
					<td>货物类型</td>
					<td><input name="goodsType" class="easyui-validatebox"  id="goodsType-add-input"/></td>
				</tr>
				<tr>
					<td>包装名称</td>
					<td><input name="pakingcodeName" class="easyui-validatebox"  id="pakingcodeName-add-input"/></td>
				</tr>
				<tr>
					<td>是否可用</td>
					<td>
					<select name="isusing" id="isusing-add-input" required="true">
						<option value="0">可用</option>
						<option value="1">不可用</option>
					</select>
					</td>
				</tr>
			</table>	
		</form>
	</div>
	<div style="padding: 5px; text-align: center;">
		   <button id="goods-win-save" class="easyui-linkbutton" >保存</button> 
		   <button id="goods-win-close" class="easyui-linkbutton">关闭</button>
	</div>
</body>
</html>