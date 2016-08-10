package com.cyc.view;

import java.util.List;

import com.cyc.domain.EbookIndex;

public class SearchPageView extends BaseView {

	
     private String pagesize;
     private String page;
     private List<EbookIndex> indexs;
     private String nextPageUrl="";
     private String prviousPageUrl="";
     private int maxIndexs=0;
     
     
	public String getPagesize() {
		return pagesize;
	}
	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public List<EbookIndex> getIndexs() {
		return indexs;
	}
	public void setIndexs(List<EbookIndex> indexs) {
		this.indexs = indexs;
	}
	public String getNextPageUrl() {
		return nextPageUrl;
	}
	public void setNextPageUrl(String nextPageUrl) {
		this.nextPageUrl = nextPageUrl;
	}
	public String getPrviousPageUrl() {
		return prviousPageUrl;
	}
	public void setPrviousPageUrl(String prviousPageUrl) {
		this.prviousPageUrl = prviousPageUrl;
	}
	public void setMaxIndexs(Integer integer) {
		// TODO Auto-generated method stub
		this.maxIndexs=integer;
		
	}
	public int getMaxIndexs()
	{
		return maxIndexs;
	}
	
	
	
	
}
