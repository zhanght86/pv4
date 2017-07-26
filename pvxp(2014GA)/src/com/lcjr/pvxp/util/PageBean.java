package com.lcjr.pvxp.util;

//import org.apache.log4j.*;

public class PageBean {
	// Logger log = Logger.getLogger("pvxp");
	
	private int totalRow;
	
	private int totalPage;
	
	private int pageSize;
	
	private int currentPage;
	
	private boolean hasPrevious;
	
	private boolean hasNext;
	
	private boolean hasMultiPage;
	
	
	public int beginRow;
	
	public int endRow;
	
	
	private String queryString;
	
	
	
	public PageBean() {
	}
	
	
	public PageBean(int totalRow, int pageSize) {
		this.totalRow = totalRow;
		this.pageSize = pageSize;
		
		totalPage = (totalRow / pageSize) + (totalRow % pageSize == 0 ? 0 : 1);
		
		if (totalPage > 1) {
			hasMultiPage = true;
		} else {
			hasMultiPage = false;
		}
		
		currentPage = 1;
		
		beginRow = 0;
		endRow = beginRow + pageSize;
		if (endRow > totalRow) {
			endRow = totalRow;
		}
		
		hasPrevious = false;
		if (totalPage > 1) {
			hasNext = true;
		} else {
			hasNext = false;
		}
	}
	
	
	
	// getter
	public int getTotalRow() {
		return this.totalRow;
	}
	
	
	public int getTotalPage() {
		return this.totalPage;
	}
	
	
	public int getPageSize() {
		return this.pageSize;
	}
	
	
	public int getCurrentPage() {
		return this.currentPage;
	}
	
	
	public boolean getHasPrevious() {
		return this.hasPrevious;
	}
	
	
	public boolean getHasNext() {
		return this.hasNext;
	}
	
	
	public boolean getHasMultiPage() {
		return this.hasMultiPage;
	}
	
	
	public String getQueryString() {
		return this.queryString;
	}
	
	
	
	// setter
	public void setCurrentPage(int page) {
		this.currentPage = page;
		if (currentPage > totalPage) {
			currentPage = totalPage;
		}
		if (currentPage < 1) {
			currentPage = 1;
		}
		
		beginRow = (currentPage - 1) * pageSize;
		endRow = beginRow + pageSize;
		if (endRow > totalRow) {
			endRow = totalRow;
		}
		
		if (currentPage <= 1) {
			hasPrevious = false;
		} else {
			hasPrevious = true;
		}
		
		if (currentPage >= totalPage) {
			hasNext = false;
		} else {
			hasNext = true;
		}
	}
	
	
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
}
