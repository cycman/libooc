package com.cyc.hibernate.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BookTopic entity. @author MyEclipse Persistence Tools
 */
@Entity(name="book_topic")
@Table(name = "book_topic", catalog = "libooc")
public class BookTopic implements java.io.Serializable {

	// Fields

	private Integer id;
	private String topicDescr;
	private String lang;
	private String kolxozCode;
	private Integer topicId;
	private Integer topicIdHl;

	// Constructors

	/** default constructor */
	public BookTopic() {
	}

	/** minimal constructor */
	public BookTopic(Integer id, String topicDescr, String lang,
			String kolxozCode) {
		this.id = id;
		this.topicDescr = topicDescr;
		this.lang = lang;
		this.kolxozCode = kolxozCode;
	}

	/** full constructor */
	public BookTopic(Integer id, String topicDescr, String lang,
			String kolxozCode, Integer topicId, Integer topicIdHl) {
		this.id = id;
		this.topicDescr = topicDescr;
		this.lang = lang;
		this.kolxozCode = kolxozCode;
		this.topicId = topicId;
		this.topicIdHl = topicIdHl;
	}

	// Property accessors
	@Id
 	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "topic_descr", nullable = false, length = 500)
	public String getTopicDescr() {
		return this.topicDescr;
	}

	public void setTopicDescr(String topicDescr) {
		this.topicDescr = topicDescr;
	}

	@Column(name = "lang", nullable = false, length = 2)
	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	@Column(name = "kolxoz_code", nullable = false, length = 10)
	public String getKolxozCode() {
		return this.kolxozCode;
	}

	public void setKolxozCode(String kolxozCode) {
		this.kolxozCode = kolxozCode;
	}

	@Column(name = "topic_id")
	public Integer getTopicId() {
		return this.topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	@Column(name = "topic_id_hl")
	public Integer getTopicIdHl() {
		return this.topicIdHl;
	}

	public void setTopicIdHl(Integer topicIdHl) {
		this.topicIdHl = topicIdHl;
	}

}