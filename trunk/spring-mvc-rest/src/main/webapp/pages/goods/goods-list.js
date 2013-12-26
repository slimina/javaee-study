// 表格组件例子
var GoodsList = function() {
	this.init();
	this.render();
	this.loadGird();
};
GoodsList.prototype = {
	init : function() {
	},
	render : function() {

	},
	initEvent : function() {
	},
	actionType : "add",
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
				self.parent.EU("#openWindowIframe")[0].src= REQUEST_URL+"/pages/goods/goods-add.jsp"; 
				self.parent.EU("#main-window-div").window({   
					title : "添加货物",
					collapsible : false,
					minimizable : false,
					width : 600,
					height : 400,
					modal : true,
					onOpen : function(){
						_thisref.actionType = "add";
					}
				}); 
			}
		}, 
		{
			text : '修改',
			iconCls : 'icon-save',
			handler : function() {
				self.parent.EU("#openWindowIframe")[0].src= REQUEST_URL+"/pages/goods/goods-add.jsp"; 
				self.parent.EU("#main-window-div").window({   
					title : "修改货物",
					collapsible : false,
					minimizable : false,
					width : 600,
					height : 400,
					modal : true,
					onOpen : function(){
						_thisref.actionType = "update";
					}
				}); 
			}
		}, 
		{
			text : '删除',
			iconCls : 'icon-cut',
			handler : function() {
				self.parent.EU.messager.confirm('确认', '您确认要删除该记录？', function(r) {
					if (r) {
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
			field : 'goodsCode',
			title : '货物编码',
			width : 100
		}, {
			field : 'goodsName',
			title : '名称',
			width : 100
		}, {
			field : 'goodsType',
			title : '货物类别',
			width : 100
		}, {
			field : 'pakingcodeName',
			title : '包装单位',
			width : 100
		}, {
			field : 'isusing',
			title : '是否启用',
			width : 100,
			formatter: function(value,row,index){
				return  value == 0 ? "可用" : "不可用";
			},
			styler :  function(value,row,index){
				return value ==0 ? "color:red;":"color:green;";
			}
		} ] ];
		var options = {
			title : '货物列表',
			width : 800,
			height : 400,
			toolbar : toolbar,
			columns : columns,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			url : REQUEST_URL+"/data/goods/list"
		};
		_thisref.cache.grid = $('#goods-list-table').datagrid(options);
	}
};
$(document).ready(function() {
	var _goodsList = new GoodsList();
	self.parent.GLOBAL["goodsList"] = _goodsList;
});
