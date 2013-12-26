package cn.test.spring.mvc.demo.model;

public class Goods {
	
	private String goodsCode;
	private String goodsName;
	private String goodsType;
	private String pakingcodeName;
	private int isusing;
	
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public String getPakingcodeName() {
		return pakingcodeName;
	}
	public void setPakingcodeName(String pakingcodeName) {
		this.pakingcodeName = pakingcodeName;
	}
	public int getIsusing() {
		return isusing;
	}
	public void setIsusing(int isusing) {
		this.isusing = isusing;
	}
	 
}
