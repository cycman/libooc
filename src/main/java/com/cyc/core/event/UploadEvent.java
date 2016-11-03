package com.cyc.core.event;

import java.util.List;

import com.cyc.hibernate.domain.DownLoadRecordDomian;
import com.cyc.hibernate.domain.Ed2kStateDomain;

public class UploadEvent extends BaseEvent {

	private List<Ed2kStateDomain> domians;
	
	private Ed2kStateDomain domian;
	
	public UploadEvent(List<Ed2kStateDomain> domians) {
		
		this.event_state = EventType.UPLOADLIST;
		this.domians = domians;
		// TODO Auto-generated constructor stub
	}
	
	public UploadEvent (Ed2kStateDomain domian)
	{
		this.event_state = EventType.UPLOADDOMAIN;
		this.domian = domian;
		
	}
	
	
	public Ed2kStateDomain getDomian() {
		return domian;
	}

	public void setDomian(Ed2kStateDomain domian) {
		this.domian = domian;
	}

	public List<Ed2kStateDomain> getDomians() {
		return domians;
	}
	public void setDomians(List<Ed2kStateDomain> domians) {
		this.domians = domians;
	}
	
}
