package com.cyc.domain;

import java.io.UnsupportedEncodingException;

public class EbookIndex {

	private String author;
	private String title;
	private String e_id;
 
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) throws UnsupportedEncodingException {
		this.author=new String(author.getBytes("gbk"),"utf-8");
		//this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) throws UnsupportedEncodingException  {
		this.title=new String(title.getBytes("gbk"),"utf-8");

		//this.title = title;
	}
	public String getE_id() {
		return e_id;
	}
	public void setE_id(String e_id) {
		this.e_id = e_id;
	}
	 
	
	
	
	
	
}
