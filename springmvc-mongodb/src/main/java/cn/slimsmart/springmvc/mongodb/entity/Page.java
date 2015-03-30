package cn.slimsmart.springmvc.mongodb.entity;

import java.io.Serializable;

/**
 * 分页实体
 */
public class Page implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer current;// 当前页码
	private Long count;// 记录总数
	private Integer pageCount;// 每页记录数
	private String param;// 传入的参数

	public Page() {
		this.current = 1; // 默认第一页
		this.count = 0L; // 共多少条记录
		this.pageCount = 10; // 默认每页10条记录
	}

	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		try {
			if (current <= 0) {
				this.current = 1;
			} else {
				this.current = current;
			}
		} catch (Exception e) {
			this.current = 1;
		}
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		String[] x = param.split("&");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < x.length; i++) {
			String[] y = x[i].split("=");
			if (y.length > 1 && !"".equals(y[1].trim())) {
				sb.append(x[i]);
				sb.append("&");
			}
		}
		this.param = sb.toString().substring(0, sb.toString().lastIndexOf("&"));
	}

	/**
	 * 获取总页数
	 * 
	 * @return
	 */
	public int getPages() {
		if (this.count % this.pageCount == 0) {
			return (int) (this.count / this.pageCount);
		} else {
			return (int) (this.count / this.pageCount + 1);
		}
	}

	/**
	 * 是否为第一页
	 * 
	 * @return
	 */
	public boolean firstEnable() {
		return previoEnable();
	}

	/**
	 * 是否为最后一页
	 * 
	 * @return
	 */
	public boolean lastEnable() {
		return nextEnable();
	}

	/**
	 * 是否有下一页
	 * 
	 * @return
	 */
	public boolean nextEnable() {
		return this.current * this.pageCount < this.count;
	}

	/**
	 * 是否有上一页
	 * 
	 * @return
	 */
	public boolean previoEnable() {
		return this.current > 1;
	}

	/**
	 * 获取任一页第一条数据在数据集的位置.
	 * 
	 * @param pageNo
	 *            从1开始的页号
	 * @param pageSize
	 *            每页记录条数
	 * @return 该页第一条数据
	 */
	public int getStartOfPage(long pageNo, long pageSize) {
		return (int) ((pageNo - 1) * pageSize);
	}
}
