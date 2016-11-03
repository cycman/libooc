package com.cyc.core.event;

import com.cyc.hibernate.domain.DownLoadRecordDomian;
import com.cyc.hibernate.domain.Ed2kStateDomain;

public class BaseEvent implements EventType{

	
	public String getEvent_state() {
		return event_state;
	}
	public void setEvent_state(String event_state) {
		this.event_state = event_state;
	}
	public Ed2kStateDomain getEd2kStateDomain() {
		return ed2k;
	}
	public void setEd2kStateDomain(Ed2kStateDomain domain) {
		this.ed2k = domain;
	}
	protected  String event_state="";
	protected Ed2kStateDomain ed2k;
	
	public String getState()
	{
		return event_state;
	}
	

	
	
}
