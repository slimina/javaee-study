/**
 * 常用工具类定义
 */
var util = function() {
};
util.prototype = {
	// form input 传化为json
	formDataToJson : function(formId) {
		var ov = $("#" + formId).serializeArray();
		var data = {};
		for ( var i = 0; i < ov.length; i++) {
			var datai = ov[i];
			var name = datai.name;
			var value = datai.value;
			if (data[name] && value && value != "") {
				data[name] += "," + value;
			} else {
				data[name] = value;
			}
		}
		return data;
	},
	// form 重置
	formDataResetting : function() {
		var inputList = $("#" + formname + " input");
		var selectList = $("#" + formname + " select");
		for ( var j = 0; j < selectList.length; j++) {
			if (selectList[j].options[0]) {
				$(selectList[j]).find("option")[0].selected = true;
				$(selectList[j]).trigger('change');
			}
		}
		for ( var i = 0; i < inputList.length; i++) {
			if (inputList[i].type != "button"
					&& inputList[i].type != "submit"
					&& $("input[name='" + inputList[i].name + "']").attr(
							"isResetting") == null) {
				if (inputList[i].type == "checkbox"
						|| inputList[i].type == "radio") {
					inputList[i].checked = false;
				} else if (inputList[i].id.indexOf("_txt") >= 0
						|| $("input[name='" + inputList[i].name + "']").attr(
								"class") == "ctfoSelect") {
					inputList[i].value = "全部";
				} else {
					inputList[i].value = "";
				}
			}
		}
	}
};

// ----- 公共代码

// Ajax请求
function JAjax(u, d, h, fn, er, sync, scope, time) {
	$.ajax({
		url : u,
		type : "POST",
		timeout : time || 30000,
		data : d || {},
		dataType : h || "html",
		cache : false,
		async : (sync == true || sync == false) ? sync : true,
		success : function(req, err) {
			if (fn)
				if (fn instanceof Function) {
					if (!scope) {
						fn.call(this, req);
					} else {
						fn.call(scope, req);
					}
				}
		},
		error : function(e, s) {
			if (er)
				if (er instanceof Function) {
					er.call(this, e);
				}
		}
	});
};
// 日期格式化
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	};
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
};
