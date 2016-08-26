package com.cyc.hibernate.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity(name="ed2k")
@Table(name="ed2k")
public class Ed2kStateDomain {

	
	private Integer id;
	
	@Column(name="id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	private String ebid;
	
	
	private String ed2k;
	
	//下载状态
	//准备下载 正在下载  下载完成 下载失败
	
	private String state;
	
	
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
