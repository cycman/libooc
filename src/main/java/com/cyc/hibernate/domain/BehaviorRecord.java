package com.cyc.hibernate.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BehaviorRecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "behavior_record", catalog = "libooc")
public class BehaviorRecord implements java.io.Serializable {

	// Fields

	private Integer id;
	private Long uid;
	private Long eid;
	private Integer typeId;
	private Integer statusId;
	private Integer boost;
	private Integer createTime;

	// Constructors

	/** default constructor */
	public BehaviorRecord() {
	}

	/** minimal constructor */
	public BehaviorRecord(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public BehaviorRecord(Integer id, Long uid, Long eid, Integer typeId,
			Integer statusId, Integer boost, Integer createTime) {
		this.id = id;
		this.uid = uid;
		this.eid = eid;
		this.typeId = typeId;
		this.statusId = statusId;
		this.boost = boost;
		this.createTime = createTime;
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

	@Column(name = "uid")
	public Long getUid() {
		return this.uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	@Column(name = "eid")
	public Long getEid() {
		return this.eid;
	}

	public void setEid(Long eid) {
		this.eid = eid;
	}

	@Column(name = "type_id")
	public Integer getTypeId() {
		return this.typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	@Column(name = "status_id")
	public Integer getStatusId() {
		return this.statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	@Column(name = "boost")
	public Integer getBoost() {
		return this.boost;
	}

	public void setBoost(Integer boost) {
		this.boost = boost;
	}

	@Column(name = "create_time")
	public Integer getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

}