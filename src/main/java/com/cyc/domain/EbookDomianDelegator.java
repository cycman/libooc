package com.cyc.domain;

import javax.ejb.Init;

import com.cyc.config.ConfigManage;
import com.cyc.exception.MyException;

public class EbookDomianDelegator {

	
	public static String IMG_HEARD="img_heard";
	//作者
	private String author;
	private String isbn;
	private String title;
	private String year;
	private String publisher;
	private String topic;
	private String MD5;
	private String imgurl;
	private String size;
	private String extension;
	private String page;
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	private String edition;
	private String language;
	private String shorDescrib;
	private String volume;
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getMD5() {
		return MD5;
	}
	public void setMD5(String mD5) {
		MD5 = mD5;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getShorDescrib() {
		return shorDescrib;
	}
	public void setShorDescrib(String shorDescrib) {
		this.shorDescrib = shorDescrib;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	
	public EbookDomianDelegator(EbookDomain ebookDomain)  throws Exception
	{
		this.imgurl=ConfigManage.GLOBAL_STRING_CONFIG.get(IMG_HEARD)+ebookDomain.getImageUrl();
		InitDelegator(ebookDomain);
		
	}
	private void InitDelegator(EbookDomain ebookDomain) throws Exception  {
		// TODO Auto-generated method stub
		
		 
			try {
				this.author=ebookDomain.getAuthors();
				this.edition=ebookDomain.getEdition();
				this.title=ebookDomain.getTitle();
				this.extension=ebookDomain.getExtension();
				this.language=ebookDomain.getLanguage();
				this.MD5=ebookDomain.getMD5();
				this.publisher=ebookDomain.getPublisher();
				this.page=ebookDomain.getPages();
				this.isbn=ebookDomain.getIsbn();
				this.size=(Double.valueOf(ebookDomain.getSizeByte())/1024/1024)+"m";
				this.topic=ebookDomain.getTOPICANDORIGINFILENAME();
				this.year=ebookDomain.getYear();
				this.volume=ebookDomain.getVolume();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MyException myException = new MyException(e,EbookDomain.class);
				myException.setERROR_CODE("1000001");
				throw myException;
			}
		 
		
	}
	
	
	
	
}
