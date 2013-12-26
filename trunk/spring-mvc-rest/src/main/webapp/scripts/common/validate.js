//验证常用属性
//(1)required:true               必输字段
//(2)remote:"check.php"          使用ajax方法调用check.php验证输入值
//(3)email:true                  必须输入正确格式的电子邮件
//(4)url:true                    必须输入正确格式的网址
//(5)date:true                   必须输入正确格式的日期
//(6)dateISO:true                必须输入正确格式的日期(ISO)，例如：2009-06-23，1998/01/22 只验证格式，不验证有效性
//(7)number:true                 必须输入合法的数字(负数，小数)
//(8)digits:true                 必须输入整数
//(9)creditcard:                 必须输入合法的信用卡号
//(10)equalTo:"#field"           输入值必须和#field相同
//(11)accept:                    输入拥有合法后缀名的字符串（上传文件的后缀）
//(12)maxlength:5                输入长度最多是5的字符串(汉字算一个字符)
//(13)minlength:10               输入长度最小是10的字符串(汉字算一个字符)
//(14)rangelength:[5,10]         输入长度必须介于 5 和 10 之间的字符串")(汉字算一个字符)
//(15)range:[5,10]               输入值必须介于 5 和 10 之间
//(16)max:5                      输入值不能大于5
//(17)min:10                     输入值不能小于10
/**
 * regex 正则表达式验证使用
 */
var regexEnum = {
	intege : "^-?[1-9]\\d*$", // 整数
	intege1 : "^[1-9]\\d*$", // 正整数
	intege2 : "^-[1-9]\\d*$", // 负整数
	num : "^([+-]?)\\d*\\.?\\d+$", // 数字
	num1 : "^[1-9]\\d*|0$", // 正数（正整数 + 0）
	num2 : "^-[1-9]\\d*|0$", // 负数（负整数 + 0）
	decmal : "^([+-]?)\\d*\\.\\d+$", // 浮点数
	decmal1 : "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$", // 正浮点数
	decmal2 : "^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$", // 负浮点数
	decmal3 : "^-?([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0)$", // 浮点数
	decmal4 : "^(((0|[1-9]\\d*)(\\.(\\d{1,2})?)?))$", // 非负浮点数（正浮点数 + 0）
	decmal6 : "^(-((0|[1-9]\\d*)(\\.(\\d{1,2})?)?))$", // 负浮点数（正浮点数 + 0）
	decmal5 : "^(-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$",// 非正浮点数（负浮点数 +// 0）
	email : "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$", // 邮件
	color : "^[a-fA-F0-9]{6}$", // 颜色
	url : "^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$", // url
	chinese : "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$", // 仅中文
	ascii : "^[\\x00-\\xFF]+$", // 仅ACSII字符
	zipcode : "^\\d{6}$", // 邮编
	mobile : "^0?\\d{11}$", // 手机
	ip4 : "^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$", // ip地址
	notempty : "^\\S+$", // 非空
	picture : "(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$", // 图片
	rar : "(.*)\\.(rar|zip|7zip|tgz)$", // 压缩文件
	date : "^\\d{4}(\\-|\\/|\.)\\d{1,2}\\1\\d{1,2}$", // 日期
	qq : "^[1-9]*[1-9][0-9]*$", // QQ号码
	tel : "^\\(?\\d{3,4}[-\\)]?\\d{7,8}$", // 电话号码的函数(包括验证国内区号,国际区号,分机号)
	username : "^\\w+$", // 用来用户注册。匹配由数字、26个英文字母或者下划线组成的字符串
	letter : "^[A-Za-z]+$", // 字母
	letter_u : "^[A-Z]+$", // 大写字母
	letter_l : "^[a-z]+$", // 小写字母
	idcard : "^\d{15}$|^\d{17}([0-9]|X|x)$",//"(^\d{15}$)(^[1-9]([0-9]{14}|[0-9]{16}(\d|X|x))$", // 身份证
	ishourminute : "^([0-1][0-9]|[2][0-3]):([0-5][0-9]):([0-5][0-9])$", // 时分秒验证
	specialword : "^[\u4E00-\u9FA5A-Za-z0-9_ ]+$" // 中文英文和空格
};
/**
 * 登录用户名验证
 */
jQuery.validator.addMethod("username", function(value, element) {
	return this.optional(element) || (new RegExp(regexEnum.username).test(value));
}, "用户名由数字、字母、下划线组成");

/**
 * 组织简称
 */
jQuery.validator.addMethod("orgShortName", function(value, element) {
	return this.optional(element) || (new RegExp(regexEnum.username).test(value));
}, "组织简称由数字、字母组成");
/**
 * 手机号码验证
 */
jQuery.validator.addMethod("mobile", function(value, element) {
	return this.optional(element) || (new RegExp(regexEnum.mobile).test(value));
}, "移动电话格式错误");
/**
 * 电话号码验证
 */
jQuery.validator.addMethod("phone", function(value, element) {
	return this.optional(element) || (new RegExp(regexEnum.tel).test(value));
}, "电话号码格式错误");

/**
 * 邮政编码验证
 */ 
jQuery.validator.addMethod("zipCode", function(value, element) {
	return this.optional(element) || (new RegExp(regexEnum.zipcode).test(value));
}, "邮政编码格式错误");
/**
 * 验证不能为空字符串，或者null
 */
jQuery.validator.addMethod("isEmpty", function(value, element) {
	if(value==""||value==null||value=="请选择"){
		return false;
	}else{
		return true;
	}
	
}, "必须填写一项");

/**
 * 字母和数字的验证
 */
jQuery.validator.addMethod("chrnum", function(value, element) {
	var chrnum = /^([a-zA-Z0-9]+)$/;
	return this.optional(element) || (chrnum.test(value));
}, "只能输入数字和字母(字符A-Z, a-z, 0-9)");
/***
 * 验证不能大于当前时间  传入的值，2012-11-19 年月日
 */
jQuery.validator.addMethod("validateCurrentDate", function(value, element) {
	var date = new Date();
	var newDate =eval('new Date(' + value.replace(/\d+(?=-[^-]+$)/, function (a){ return parseInt(a, 10) - 1; }).match(/\d+/g) + ')'); 
	var curYear = date.getFullYear(), newYear =newDate.getFullYear();
	var curMonth = date.getMonth(),newMonth = newDate.getMonth();
	var curDay = date.getDate(),newDay = newDate.getDate();
	
	if(curYear>newYear){
		return true;
	}
	if(curYear==newYear){
		if(curMonth>newMonth){
			return true;
		}
		if(curMonth==newMonth){
			if(curDay>=newDay){
				return true;
			}
			return false;
		}
		return false;
	}
	return false;
	//return this.optional(element) || (chrnum.test(value));
}, "不能超过当前时间 ");

/**
 * 中文的验证
 */ 
jQuery.validator.addMethod("chinese", function(value, element) {
	return this.optional(element) || (new RegExp(regexEnum.zipcode).test(value));
}, "只能输入中文");
/**
 * 正则表达式验证
 */ 
jQuery.validator.addMethod("regex", // addMethod第1个参数:方法名称
function(value, element, params) { // addMethod第2个参数:验证方法，参数（被验证元素的值，被验证元素，参数）
	var exp = new RegExp(params); // 实例化正则对象，参数为传入的正则表达式
	return exp.test(value); // 测试是否匹配
}, "格式错误");

/**
 * 下拉框验证
 */
jQuery.validator.addMethod("selectNone", function(value, element) {
	
	//modify by YaoJ 2012-11-17 at 13:16 下拉验证
	if(($(element).attr("districtdata")&&$(element).attr("districtdata")=="has")||!$(element).attr("districtdata")){
		if(value==""||value==null||value=="请选择"){
			return false;
		}
		if (value == "") {
			return false;
		} else {
			return true;
		}
	}else{
		return true;
	}

}, "必须选择一项");

/**
 * ligercombox 下拉框验证
 */
jQuery.validator.addMethod("comboxSelectNone", function(value, element) {
	if(value==""||value==null||value=="请选择"){
		return false;
	}
	if (value == "") {
		return false;
	} else {
		return true;
	}

}, "必须选择一项");

/**
 *  字节长度验证
 */
jQuery.validator.addMethod("byteRangeLength",
		function(value, element, param) {
			var length = value.length;
			for ( var i = 0; i < value.length; i++) {
				if (value.charCodeAt(i) > 127) {
					length++;
				}
			}
			return this.optional(element)
					|| (length >= param[0] && length <= param[1]);
		}, $.validator.format("请确保输入的值在{0}-{1}个字节之间(一个中文字算2个字节)"));

/**
 * 浮点型
 */
jQuery.validator.addMethod("decmal", // addMethod第1个参数:方法名称
		function(value, element) { // addMethod第2个参数:验证方法，参数（被验证元素的值，被验证元素，参数）
			var exp = new RegExp(regexEnum.decmal); // 实例化正则对象，参数为传入的正则表达式
			return exp.test(value); // 测试是否匹配
		}, "请输入浮点数");

/**
 * 保留两位数的浮点型，正数，非0
 */
jQuery.validator.addMethod("decmal2", // addMethod第1个参数:方法名称
		function(value, element) { // addMethod第2个参数:验证方法，参数（被验证元素的值，被验证元素，参数）
	        if(value==0){//去掉0的验证
	        	return true;
	        }
			var exp = new RegExp('^[1-9]\\d*.\\d{1,2}$|^0.\\d{1,2}$|^[1-9]\\d*$|^$'); // 实例化正则对象，参数为传入的正则表达式
			return exp.test(value); // 测试是否匹配
		}, "请输入最多两位小数的数字");
/**
 * 保留两位数的浮点型，正数，非0
 */
jQuery.validator.addMethod("decmal4", // addMethod第1个参数:方法名称
		function(value, element) { // addMethod第2个参数:验证方法，参数（被验证元素的值，被验证元素，参数）
			var exp = new RegExp(regexEnum.decmal4); // 实例化正则对象，参数为传入的正则表达式
			return exp.test(value); // 测试是否匹配
		}, "请输入最多两位小数的数字");

jQuery.validator.addMethod("isCardId", // addMethod第1个参数:方法名称         身份证验证
		function(value, element) { // addMethod第2个参数:验证方法，参数（被验证元素的值，被验证元素，参数）
	return this.optional(element) ||(/^\d{15}$|^\d{17}([0-9]|X|x)$/.test(value)); // 实例化正则对象，参数为传入的正则表达式
		}, "身份证号码格式有误");
jQuery.validator.addMethod("num1", // addMethod第1个参数:方法名称         身份证验证
		function(value, element) { // addMethod第2个参数:验证方法，参数（被验证元素的值，被验证元素，参数）
	return this.optional(element) ||(/^[1-9]\\d*$/.test(value)); // 实例化正则对象，参数为传入的正则表达式
}, "请输入正整数");
jQuery.validator.addMethod("requireSelect", function(value, element) { 
	if(value==""||value==null||value=="请选择"){
		return false;
	}
		return true;
		}, "请输入值");
//验证是否是正数  保留小数点后两位
jQuery.validator.addMethod("num",  
		function(value, element) { 
	var flag=this.optional(element) ||(/^([+]?)\d*\.\d+$|^[0-9]+$/.test(value));
	if(flag){
		element.value=Math.round(value*Math.pow(10, 2))/Math.pow(10, 2);
	}
	return flag;
}, "请输入正数");