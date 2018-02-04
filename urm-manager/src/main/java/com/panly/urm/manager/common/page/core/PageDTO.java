package com.panly.urm.manager.common.page.core;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * <p> 对分页的基本数据进行一个简单的封装 </p> 
 *
 * @project 	core-api
 * @class 		Page
 * @author 		a@panly.me
 * @date 		2017年5月27日 下午4:07:54
 */
public class PageDTO<T> implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 页码，从1开始
	 */
	private int pageNum;
	
	/**
	 * 页面大小
	 */
	private int pageSize;
	
	/**
	 * 起始行
	 */
	private int startRow;
	
	/**
	 * 总数
	 */
	private long total;
	
	/**
	 * 总页数
	 */
	private int pages;
	
	/**
	 * 返回数据
	 */
	private List<T> resultData;
	
	
	//datatable 数据
	private int draw;

	/**
	 * 请求第一页数据
	 */
	public void firstPage() {
		pageNum = 1;
		calculateStartAndEndRow();
	}

	/**
	 * 请求最后一页数据
	 */
	public void lastPage() {
		pageNum = pages;
		calculateStartAndEndRow();
	}

	/**
	 * 请求前一页数据
	 */
	public void previousPage() {
		if (pageNum > 1) {
			pageNum--;
			calculateStartAndEndRow();
		}
	}

	/**
	 * 请求后一页数据
	 */
	public void nextPage() {
		if (pageNum < pages) {
			pageNum++;
			calculateStartAndEndRow();
		}
	}

	/**
	 * 请求转到某页
	 */
	public void gotoPage(int nPage) {
		if (nPage > 0 && nPage <= pages) {
			pageNum = nPage;
			calculateStartAndEndRow();
		}
	}

	/**
	 * 是否是第一页
	 * 
	 * @return boolean
	 */
	public boolean isFirstPage() {
		return pageNum == 1;
	}

	/**
	 * 是否为最后一页
	 * 
	 * @return boolean
	 */
	public boolean isLastPage() {
		return pageNum == pages;
	}

	public PageDTO() {
	}

	public PageDTO(int pageNum, int pageSize) {
		if (pageNum == 1 && pageSize == Integer.MAX_VALUE) {
			pageSize = 0;
		}
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		calculateStartAndEndRow();
	}
	
	public PageDTO(int pageNum, int pageSize,List<T> resultData) {
		if (pageNum == 1 && pageSize == Integer.MAX_VALUE) {
			pageSize = 0;
		}
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.resultData=resultData;
		calculateStartAndEndRow();
	}

	public List<T> getResultData() {
		return resultData;
	}

	public PageDTO<T> setResultData(List<T> resultData) {
		this.resultData = resultData;
		return this;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = (pageNum <= 0) ? 1 : pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public long getTotal() {
		return total;
	}

	public PageDTO<T> setTotal(long total) {
		this.total = total;
		if (total == -1) {
			pages = 1;
			return this;
		}
		if (pageSize > 0) {
			pages = (int) (total / pageSize + ((total % pageSize == 0) ? 0 : 1));
		} else {
			pages = 0;
		}
		// 分页合理化，针对不合理的页码自动处理
		if (pageNum > pages) {
			pageNum = pages;
			calculateStartAndEndRow();
		}
		return this;
	}

	/**
	 * 计算起止行号
	 */
	private void calculateStartAndEndRow() {
		this.startRow = this.pageNum > 0 ? (this.pageNum - 1) * this.pageSize : 0;
	}
	

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}