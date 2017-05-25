package com.cyc.hibernate.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BehaviorStatus entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "behavior_status", catalog = "libooc")
public class BehaviorStatus implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;

	// Constructors

	/** default constructor */
	public BehaviorStatus() {
	}

	/** minimal constructor */
	public BehaviorStatus(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public BehaviorStatus(Integer id, String name) {
		this.id = id;
		this.name = name;
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

	@Column(name = "name", length = 32)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}