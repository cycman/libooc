package com.cyc.hibernate.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity(name="BuyPermit")
@Table(name="BuyPermit")
public class BuyPermitDomain {

	public static final String CODE_USEFUL="可以使用";
	public static final String CODE_USELESS="不可使用";
	public static final String CODE_USEED="已经使用";
	private Integer id;
	
	private String code;
	
	private String state_use="未使用";
	
	private String ebid;
	
	@Column(name="id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	
	
	public String getEbid() {
		return ebid;
	}


	public void setEbid(String ebid) {
		this.ebid = ebid;
	}


	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getState_use() {
		return state_use;
	}
	public void setState_use(String state_use) {
		this.state_use = state_use;
	}
	
	
	
	
	
	
}
