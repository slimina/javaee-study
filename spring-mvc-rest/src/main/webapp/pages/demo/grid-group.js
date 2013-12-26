// 表格组件例子
var GridGroup = function() {
	this.init();
	this.render();
	this.loadGird();
};
GridGroup.prototype = {
	init : function() {
	},
	render : function() {

	},
	initEvent : function() {
	},
	cache : {
		grid : {}
	},
	_groupFormatter : function(fvalue, rows){
		return fvalue + ' - <span style="color:red">' + rows.length + ' rows</span>';
	},
	loadGird : function() {
		var _thisref = this;
		var columns = [ [ {
			field : 'name',
			title : '属性',
			width : 100,
			sortable:true
		}, {
			field : 'value',
			title : '值',
			width : 100
		} ] ];
		
		var options = {
			title : '分组数据列表（可编辑）',
			width : 900,
			height : 400,
			url: './data2.json',
			showGroup: true,
			scrollbarSize: 0,
			columns : columns,
			groupFormatter: _thisref._groupFormatter
		};
		_thisref.cache.grid = $('#grid-group').propertygrid(options);
	}
};
$(document).ready(function() {
	var _gridGroup = new GridGroup();
	self.parent.GLOBAL["gridGroup"] = _gridGroup;
});
