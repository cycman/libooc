package com.cyc.hibernate.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="ip")
@Table(name="ip")
public class IpDomain {

	private String ip;

	private Integer downTimes=0;
	
	private Date date=new Date();
	
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public IpDomain(String ip) {
	 
		this.ip = ip;
	}

	public IpDomain() {
		// TODO Auto-generated constructor stub
	}
	
	

	public IpDomain(String ip2, Date date2) {
		// TODO Auto-generated constructor stub
		this.ip=ip2;
		this.date=date2;
	}

	public Integer getDownTimes() {
		return downTimes;
	}

	public void setDownTimes(Integer downTimes) {
		this.downTimes = downTimes;
	}

	@Column(name="ip")
	@Id
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void addTimes() {
		// TODO Auto-generated method stub
		downTimes++;
		
	}
	
	
	
}
