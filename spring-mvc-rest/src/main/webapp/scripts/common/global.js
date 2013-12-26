/**
 * 全局缓存对象
 */
var GLOBAL={
		mainPage:{},
		mainSize:{
			width:0,
			height:0
		}
};

var EU = $;

/**
 * 系统异常码
 * errorCode:失败状态码      
 * msg：具体描述信息
 * {status:1,errorCode:1,msg:''}
 */
var ERROR_CODE={

};
/**
 * 提示信息
 */
var MESSAGE={
		ADD_SUCCESS:'添加成功',
		ADD_FAILED:'添加失败',
		DELETE_SUCCESS:'删除成功',
		DELETE_FAILED:'删除失败',
		UPDATE_SUCCESS:'更新成功',
		UPDATE_FAILED:'更新失败'
};

//直辖市(省市县联动用)
var CENTRAL_CITY={'110000':'北京市','120000':'天津市','310000':'上海市','500000':'重庆市'};
