package com.cyc.domain;

import java.io.UnsupportedEncodingException;

public class EbookIndex {

	
	//id
	public static  String EB_ID="id";
	public static String AUTHOR="author";
	public static String TITLE="title";
	public static String TOPIC="topic";
	public static String ISBN="isbn";
	public static String IMGURL="imgurl";
	public static String PUBLISHER="publisher";

	public static final String[] DEFAULTINDEXS = {"id","author","title","isbn","topic","imgurl","publisher"};

	
	
	
	private String author;
	private String title;
	private String e_id;
	private String topic;
	private String isbn;
	private String imgurl;
	private String publisher;
	
	
 
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
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
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	 
	
	
	
	
	
}
