package com.cyc.domain;

import com.cyc.exception.MyException;
import com.cyc.hibernate.domain.EbookDomain;
import com.cyc.hibernate.domain.Ed2kStateDomain;

public class Ed2kLinkDelegator {
	
	private boolean isFree;
	private String downLink;
	private String extention;
	private String title;
	private String author;
	
	public Ed2kLinkDelegator(Ed2kStateDomain ed2kStateDomain,String downlink,boolean isFree) throws Exception {
		// TODO Auto-generated constructor stub
		InitDelegator(ed2kStateDomain);
		this.downLink=downlink;
		this.isFree=isFree;
	     	
		
	}
	private void InitDelegator(Ed2kStateDomain ebookDomain) throws Exception  {
		// TODO Auto-generated method stub
		
		 
			try {
				this.author= new String(
						ebookDomain.getAuthors().getBytes("gbk"),"utf-8");
				 
				this.title=new String(
						ebookDomain.getTitle().getBytes("gbk"),"utf-8");
				this.extention=ebookDomain.getExtension();
				 
				 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MyException myException = new MyException(e,EbookDomain.class);
				myException.setERROR_CODE("1000001");
				throw myException;
			}
		 
		
	}
	
	
	
	
	public boolean isFree() {
		return isFree;
	}



	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}



	public String getDownLink() {
		return downLink;
	}
	public void setDownLink(String downLink) {
		this.downLink = downLink;
	}
	public String getExtention() {
		return extention;
	}
	public void setExtention(String extention) {
		this.extention = extention;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	
	
}
