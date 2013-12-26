// 树组件例子
var Tree = function() {
	this.init();
	this.render();
	this.loadTree();
};
Tree.prototype = {
	init : function() {
	},
	render : function() {

	},
	initEvent : function() {
	},
	cache : {
		tree : {}
	},
	getChecked : function(){
		var nodes = $('#tree').tree('getChecked');
		var s = '';
		for(var i=0; i<nodes.length; i++){
			if (s != '') s += ',';
			s += nodes[i].text;
		}
		alert(s);
	},
	loadTree : function() {
		var _thisref = this;
		var options = {
			animate:true,
			checkbox:true,
			cascadeCheck : true,
			url : "./data3.json"
		};
		_thisref.cache.tree = $('#tree').tree(options);
	}
};
$(document).ready(function() {
	var _tree = new Tree();
	self.parent.GLOBAL["tree"] = _tree;
});
