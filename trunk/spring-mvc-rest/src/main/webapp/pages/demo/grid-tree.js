// 表格组件例子
var GridTree = function() {
	this.init();
	this.render();
	this.loadGird();
};
GridTree.prototype = {
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
			title : '名称',
			width : 200,
			sortable:true
		}, {
			field : 'size',
			title : '大小',
			width : 100
		}, {
			field : 'date',
			title : '日期',
			width : 160
		} ] ];
		
		var options = {
			title : '分组数据列表（可编辑）',
			width : 900,
			height : 400,
			url: './data4.json',
			columns : columns,
			rownumbers: true,
			idField: 'id',
			treeField: 'name'
		};
		_thisref.cache.grid = $('#grid-tree').treegrid(options);
	}
};
$(document).ready(function() {
	var _gridTree = new GridTree();
	self.parent.GLOBAL["gridTree"] = _gridTree;
});
