package cn.test.spring.mvc.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.test.spring.mvc.demo.model.Shop;

@Controller
@RequestMapping("/demo/*")
public class DemoController extends CrudControllerSupport{

	//返回整数
	//http://localhost:8080/spring-mvc-demo/demo/Int
	@RequestMapping()
	@ResponseBody
	public int Int() {
		return 25;
	}
	
	//返字符串
	//http://localhost:8080/spring-mvc-demo/demo/string
	@RequestMapping()
	@ResponseBody
	public String string() {
		return "abc";
	}
	
	//支持参数在url上  restful，返回json
	//http://localhost:8080/spring-mvc-demo/demo/data/ssss/22/
	@RequestMapping("data/{name}/{age}")
	@ResponseBody
	public Shop getShopInJSON(@PathVariable String name,@PathVariable double age,HttpServletRequest request) {
		//ServletRequestUtils.getStringParameter(request, name, defaultVal);
		Shop shop = new Shop();
		shop.setName(name);
		shop.setAge(age);
		shop.setStaffName(new String[] { "aaaaa", "bbbbb" });
		return shop;
	}

	//通过get或post提交数据返回json，map
	//http://localhost:8080/spring-mvc-demo/demo/userName?userName=aaaaa
	@RequestMapping()
	@ResponseBody
	public Map<String, Object> userName(@RequestParam String userName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		return map;
	}
	
	//通过get或post提交数据返回json  object
	//http://localhost:8080/spring-mvc-demo/demo/save?name=aaaaa&age=23
	@RequestMapping()
	@ResponseBody
	public Shop save(Shop shop) {
		System.out.println(shop);
		return shop;
	}

	//跳转到页面，view
	//http://localhost:8080/spring-mvc-demo/demo/list?name=aaaaa
	@RequestMapping()
	public ModelAndView list(Shop shop) {
		System.out.println(shop);
		return new ModelAndView("/test.jsp");
	}
	
	//通过get或post提交数据返回json httpservlet
	//http://localhost:8080/spring-mvc-demo/demo/list1?name=aaaaa
	@RequestMapping()
	@ResponseBody
	public Shop list1(Shop shop,HttpServletRequest request,HttpServletResponse response ,HttpSession session) {
		System.out.println(shop);
		System.out.println(request);
		System.out.println(response);
		System.out.println(session);
		return shop;
	}
}
