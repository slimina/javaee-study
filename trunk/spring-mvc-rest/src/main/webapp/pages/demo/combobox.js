// 树组件例子
var Combobox = function() {
	this.init();
	this.render();
};
Combobox.prototype = {
	init : function() {

	},
	initEvent : function() {
	},
	cache : {},
	render : function() {
		$('#combobox').combobox({
			url : './data5.json',
			valueField : 'id',
			textField : 'text',
			width : 200,
			panelHeight : 'auto',
			onSelect : function(record) {
				alert(record.id + ":" + record.text);
			}
		});

		$('#combobox-tree').combotree({
			url : './data6.json',
			valueField : 'id',
			textField : 'text',
			width : 200,
			onSelect : function(record) {
				alert(record.id + ":" + record.text);
			}
		});

		$('#combobox-grid').combogrid({
			url : './data7.json',
			width : 200,
			panelWidth: 500,
			idField : 'itemid',
			textField : 'productname',
			columns : [ [ {
				field : 'itemid',
				title : 'Item ID',
				width : 80
			}, {
				field : 'productname',
				title : 'Product',
				width : 120
			}, {
				field : 'listprice',
				title : 'List Price',
				width : 80,
				align : 'right'
			} ] ],
			fitColumns : true,
			onSelect : function(index,rowData) {
				alert("index="+index+",productname="+rowData.productname);
			}
		});
	}
};
$(document).ready(function() {
	var _combobox = new Combobox();
	self.parent.GLOBAL["combobox"] = _combobox;
});
