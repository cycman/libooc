package com.cyc.hibernate.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BehaviorType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "behavior_type", catalog = "libooc")
public class BehaviorType implements java.io.Serializable {

	// Fields

	private Integer id;
	private String enName;
	private String cnName;
	private String uri;
	private Float boost;

	// Constructors

	/** default constructor */
	public BehaviorType() {
	}

	/** minimal constructor */
	public BehaviorType(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public BehaviorType(Integer id, String enName, String cnName, String uri,
			Float boost) {
		this.id = id;
		this.enName = enName;
		this.cnName = cnName;
		this.uri = uri;
		this.boost = boost;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "en_name", length = 32)
	public String getEnName() {
		return this.enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	@Column(name = "cn_name", length = 32)
	public String getCnName() {
		return this.cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	@Column(name = "uri", length = 32)
	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Column(name = "boost", precision = 12, scale = 0)
	public Float getBoost() {
		return this.boost;
	}

	public void setBoost(Float boost) {
		this.boost = boost;
	}

}