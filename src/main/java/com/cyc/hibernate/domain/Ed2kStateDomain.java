package com.cyc.hibernate.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.omg.CORBA.PUBLIC_MEMBER;


@Entity(name="ed2k")
@Table(name="ed2k")
public class Ed2kStateDomain {

	public static final String UP_SUCCESSED="上传成功";
	public static final String UP_FAILED="上传失败";
	public static final String UP_NOT="未上传";
	public static final String UP_ING="正在上传";


	
	private Integer id;
	
	@Column(name="id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public Ed2kStateDomain(EbookDomain domain,String state){
		this.setAuthors(domain.getAuthors());
		this.setEbid(domain.getEb_ID().toString());
		this.setExtension(domain.getExtension());
		this.setFilePath(domain.getURL());
		this.setTitle(domain.getTitle());
		this.setState_upload(state);
		this.ed2k=domain.Ed2k();
	}
	private String authors;
	public void setId(Integer id) {
		this.id = id;
	}

	private String ebid;
	
	
	private String ed2k;
	
	//下载状态
	//未下载  准备下载 正在下载  下载完成 下载失败
	
	private String state="未下载";
	
	private String state_upload="未上传";
	
	private String filePath;
	
	private String title;
	private String extension;
	
	
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getExtension() {
		return extension;
	}


	public void setExtension(String extention) {
		this.extension = extention;
	}


	public String getAuthors() {
		return authors;
	}


	public void setAuthors(String authors) {
		this.authors = authors;
	}


	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public String getState_upload() {
		return state_upload;
	}


	public void setState_upload(String state_upload) {
		this.state_upload = state_upload;
	}


	public Ed2kStateDomain(){
		
	}

	public Ed2kStateDomain(String ebid, String ed2k, String state) {
		super();
		this.ebid = ebid;
		this.ed2k = ed2k;
		this.state = state;
	}

	
	public String getEbid() {
		return ebid;
	}

	public void setEbid(String ebid) {
		this.ebid = ebid;
	}

	 
  	public String getEd2k() {
		return ed2k;
	}

	public void setEd2k(String ed2k) {
		this.ed2k = ed2k;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return ((Ed2kStateDomain)obj).getEd2k().equals(ed2k);
	}
	
	
	
	
	
}
