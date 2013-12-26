// 表格组件例子
var GoodsAdd = function(actionType) {
	this.actionType = actionType;
	this.init();
	this.render();
	this.initEvent();
	this.loadForm();
};
GoodsAdd.prototype = {
	addUrl:REQUEST_URL+"/data/goods/add",
	updateUrl :REQUEST_URL+"/data/goods/update",
	init : function() {
	},
	render : function() {
	},
	cache : {},
	initEvent : function() {
		$("#goods-win-save").bind(
				"click",
				function() {
					$('#goods-add-form').submit();
				});

		$("#goods-win-close").bind("click", function() {
			self.parent.EU("#main-window-div").window("close");
		});

	},
	loadForm : function(){
		var that = this;
		 $('#goods-add-form').form({   
		       url:that.actionType == "add" ? that.addUrl : that.updateUrl, 
		       onSubmit: function(){   
		           // 做某些检查   
		           // 返回 false 来阻止提交   
		    	   return $('#goods-add-form').form("validate");
		       },   
		       success:function(data){   
		    	   if(data ==0){
		    		   self.parent.EU("#main-window-div").window("close");
		    		   self.parent.EU.messager.alert('提示','添加成功','info');
		    	   }else{
		    		   self.parent.EU.messager.alert('提示','添加失败','error');
		    	   }
		      },
		      onBeforeLoad : function(param){
		    	  //发出请求加载数据之前触发。返回 false 就取消这个动作。
		      },
		      onLoadSuccess : function(data){
		    	  //当表单数据加载时触发。
		      },
		      onLoadError : function(){
		    	  //加载表单数据时发生某些错误的时候触发。
		      }
		 });
		 alert(that.actionType);
		 if(that.actionType != "add"){
			 $('#goods-add-form').form("load",REQUEST_URL+"/data/goods/get");
		 }
	}
};
$(document).ready(function() {
	var _goodsAdd = new GoodsAdd(self.parent.GLOBAL["goodsList"].actionType);
	self.parent.GLOBAL["goodsAdd"] = _goodsAdd;
});
