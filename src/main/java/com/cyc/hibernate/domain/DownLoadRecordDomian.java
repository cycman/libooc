package com.cyc.hibernate.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity(name="DLoadRecord")
@Table(name="DLoadRecord")
public class DownLoadRecordDomian {

	//id
	private  Integer id;
	//创建时间
	private  Date timeCreated;
	
	//ebid
	
	private  String eb_id;
	
	private  String ed2k;
	
	private  String filesize;
	
	private  String email1;
	
	private  String email2;
	
	//下载任务状态	未下载		正在下载	下载成功	下载失败 	
	private  String stateDload;
	
	//存储位置	
	private  String pathStored;
	
	
	//上传状态
	private String upLoadState;
	
	//是否发送emai1	
	private boolean email1IsSend;
	//是否发送email2
	private boolean email2IsSend;
	
	private boolean email1IsSuccessed;
	private boolean email2IsSuccessed;
	
	//购买的表示符唯一
	private boolean uuid;
	
	
	@Column(name="id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getTimeCreated() {
		return timeCreated;
	}
	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}
	public String getEb_id() {
		return eb_id;
	}
	public void setEb_id(String eb_id) {
		this.eb_id = eb_id;
	}
	public String getEd2k() {
		return ed2k;
	}
	public void setEd2k(String ed2k) {
		this.ed2k = ed2k;
	}
	public String getFilesize() {
		return filesize;
	}
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public String getStateDload() {
		return stateDload;
	}
	public void setStateDload(String stateDload) {
		this.stateDload = stateDload;
	}
	public String getPathStored() {
		return pathStored;
	}
	public void setPathStored(String pathStored) {
		this.pathStored = pathStored;
	}
	public String getUpLoadState() {
		return upLoadState;
	}
	public void setUpLoadState(String upLoadState) {
		this.upLoadState = upLoadState;
	}
	public boolean isEmail1IsSend() {
		return email1IsSend;
	}
	public void setEmail1IsSend(boolean email1IsSend) {
		this.email1IsSend = email1IsSend;
	}
	public boolean isEmail2IsSend() {
		return email2IsSend;
	}
	public void setEmail2IsSend(boolean email2IsSend) {
		this.email2IsSend = email2IsSend;
	}
	public boolean isEmail1IsSuccessed() {
		return email1IsSuccessed;
	}
	public void setEmail1IsSuccessed(boolean email1IsSuccessed) {
		this.email1IsSuccessed = email1IsSuccessed;
	}
	public boolean isEmail2IsSuccessed() {
		return email2IsSuccessed;
	}
	public void setEmail2IsSuccessed(boolean email2IsSuccessed) {
		this.email2IsSuccessed = email2IsSuccessed;
	}
	public boolean isUuid() {
		return uuid;
	}
	public void setUuid(boolean uuid) {
		this.uuid = uuid;
	}

	
	
	
	
	 
	
}
