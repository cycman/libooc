package com.cyc.core.event;

import java.util.List;

import com.cyc.hibernate.domain.DownLoadRecordDomian;

public class EmailEvent extends BaseEvent {

	private List<DownLoadRecordDomian> domians ;
	private DownLoadRecordDomian domian;
	
	public EmailEvent(List<DownLoadRecordDomian> emailunSend) {
		// TODO Auto-generated constructor stub
		this.domians = emailunSend;
	}
	public EmailEvent(DownLoadRecordDomian domian)
	{
		this.domian=domian;
	}

	
	public DownLoadRecordDomian getDomian() {
		return domian;
	}
	public void setDomian(DownLoadRecordDomian domian) {
		this.domian = domian;
	}
	public List<DownLoadRecordDomian> getDomians() {
		return domians;
	}

	public void setDomians(List<DownLoadRecordDomian> domians) {
		this.domians = domians;
	}

	
	
}
