package cn.test.spring.mvc.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.test.spring.mvc.demo.model.Goods;

@Controller
@RequestMapping("/goods/*")
public class GoodsController extends CrudControllerSupport{
	
	@RequestMapping()
	@ResponseBody
	public Map<String,Object> list(Goods goods,int page,int rows) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("total", 125);
		List<Goods> list = new ArrayList<Goods>();
		int startIndex = (page-1)*rows +1;
		Goods gd = null;
		for(;startIndex < 125 & startIndex < (page-1)*rows +1+rows ; startIndex ++){
			gd = new Goods();
			gd.setGoodsCode("G"+startIndex);
			gd.setGoodsName("N001"+startIndex);
			gd.setGoodsType("电子原件");
			gd.setPakingcodeName("PC00XX");
			gd.setIsusing(startIndex%2);
			list.add(gd);
		}
		resultMap.put("rows", list);
		return resultMap;
	}
	
	@RequestMapping()
	@ResponseBody
	public int add(Goods goods){
		 System.out.println(goods.getGoodsName());
		 return 0;
	}
	
	@RequestMapping()
	@ResponseBody
	public int update(Goods goods){
		 System.out.println(goods.getGoodsName());
		 return 0;
	}
	
	@RequestMapping()
	@ResponseBody
	public int delete(Goods goods){
		 System.out.println(goods.getGoodsCode());
		 return 0;
	}
	
	@RequestMapping()
	@ResponseBody
	public Goods get(Goods goods){
		Goods gd = new Goods();
		gd.setGoodsCode("G999");
		gd.setGoodsName("N001999");
		gd.setGoodsType("电子原件");
		gd.setPakingcodeName("PC00XX99");
		gd.setIsusing(1);
		return gd;
	}
	
	
}
