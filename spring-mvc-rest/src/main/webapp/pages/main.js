/**
 * 主页
 */
(function() {
	var MainPage = function() {
		this.init();
		this.render();
	};

	MainPage.prototype = {
		init : function() {
			 this.cache.indexPage = $("#mainPanel").html();
			 this._createMenus();
		},
		render : function() {
			$($(".panel-tool")[0]).html("");
		},
		initEvent : function() {
		},
		cache : {
			indexPage :{}
		},
		_createFrame : function(url)
		{
			var s = '<iframe name="mainFrame" scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
			return s;
		},
		renderMain : function(parentName,url,menuname){
			var content = this._createFrame(url);
			$("#mainPanel").html(content);
			var len = $(".panel-title").length;
			$($(".panel-title")[len-1]).html(parentName+" --> "+menuname);
		},
		resize : function() {

		},
		setLayout : function() {

		},
		_createMenus : function() {
			var _menus = {
				"menus" : [ {
					"menuid" : "1",
					"icon" : "icon-sys",
					"menuname" : "控件实例",
					"menus" : [ {
						"menuname" : "表格控件",
						"icon" : "icon-nav",
						"url" : REQUEST_URL+"/pages/demo/grid.jsp"
					}, {
						"menuname" : "分组表格",
						"icon" : "icon-log",
						"url" : REQUEST_URL+"/pages/demo/grid-group.jsp"
					}, {
						"menuname" : "树控件",
						"icon" : "icon-add",
						"url" : REQUEST_URL+"/pages/demo/tree.jsp"
					}, {
						"menuname" : "表格树",
						"icon" : "icon-log",
						"url" : REQUEST_URL+"/pages/demo/grid-tree.jsp"
					}, {
						"menuname" : "下拉列表",
						"icon" : "icon-log",
						"url" : REQUEST_URL+"/pages/demo/combobox.jsp"
					}, {
						"menuname" : "省市县控件",
						"icon" : "icon-log",
						"url" : REQUEST_URL+"/test.jsp"
					} ]
				}, {
					"menuid" : "2",
					"icon" : "icon-sys",
					"menuname" : "货物管理",
					"menus" : [ {
						"menuname" : "货物列表",
						"icon" : "icon-nav",
						"url" : REQUEST_URL+"/pages/goods/goods-list.jsp"
					} ]
				} ]
			};

			$("#main-function-menus").empty();
			var menulist = "";
			$.each(_menus.menus, function(i, n) {
				menulist += '<div title="' + n.menuname + '"  data-options="iconCls:\'' + n.icon
						+ '\'" style="overflow:auto;padding:10px;">';
				menulist += '<div>';
				$.each(n.menus, function(j, o) {
					menulist += '<div><a  href="javascript:void(0);" onclick=\'javascript:self.parent.GLOBAL.mainPage.renderMain("'+n.menuname+'","'+o.url+'","'+o.menuname+'");\' ><span class="icon ' + o.icon + '" ></span>'
							+ o.menuname + '</a></div> ';
				});
				menulist += '</div></div>';
			});
			$("#main-function-menus").append(menulist);
			$('#main-function-menus li a').hover(function() {
				$(this).parent().addClass("hover");
			}, function() {
				$(this).parent().removeClass("hover");
			});
			$("#main-function-menus").accordion();
		},
		destory : function() {

		},
	};
	$(document).ready(function() {
		var _mainPage = new MainPage();
		self.parent.GLOBAL["mainPage"] = _mainPage;
	});
})();