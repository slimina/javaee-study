// 表格组件例子
var Grid = function() {
	this.init();
	this.render();
	this.loadGird();
};
Grid.prototype = {
	init : function() {
	},
	render : function() {

	},
	initEvent : function() {
	},
	cache : {
		grid : {}
	},
	loadGird : function() {
		var _thisref = this;
		var toolbar = [ 
		{
			id : "add-new",
			text : '新增',
			iconCls : 'icon-add',
			handler : function() {
				self.parent.EU.messager.alert("提示", "新增事件");
			}
		}, 
		{
			text : '修改',
			iconCls : 'icon-save',
			handler : function() {
				self.parent.EU.messager.prompt("输入框", "请输入提货原因：");
			}
		}, 
		{
			text : '删除',
			iconCls : 'icon-cut',
			handler : function() {
				self.parent.EU.messager.confirm('确认', '您确认要删除该记录？', function(r) {
					if (r) {
						alert('ok');
					}
				});
			}
		}, 
		{
			text : '打印',
			iconCls : 'icon-print',
			handler : function() {
				self.parent.EU.messager.show({
						title : '提示框',
						msg : '弹出提示框',
						showType : 'show'
					});
			}
		}, 
		{
			text : '导出',
			iconCls : 'icon-search',
			handler : function() {
				self.parent.EU.messager.progress({
					title : '进度条',
					msg : '数据加载中...'
				});
				setTimeout(function() {
					self.parent.EU.messager.progress('close');
				}, 5000);
			}
		}
		];
		var columns = [ [ {
			field : 'code',
			title : '编码',
			width : 100
		}, {
			field : 'name',
			title : '名称',
			width : 100
		}, {
			field : 'price',
			title : '价格',
			width : 100
		} ] ];
		var options = {
			title : '数据列表',
			width : 900,
			height : 400,
			toolbar : toolbar,
			columns : columns,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			url : "./data1.json"
		};
		_thisref.cache.grid = $('#grid').datagrid(options);
	}
};
$(document).ready(function() {
	var _grid = new Grid();
	self.parent.GLOBAL["grid"] = _grid;
});
