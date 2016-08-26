package com.cyc.core.event;

 import com.cyc.hibernate.domain.*;
public class DLRecordEvent extends BaseEvent {

	
	
	
	public DLRecordEvent(String state,Ed2kStateDomain recordDomian)
	{
		this.ed2k=recordDomian;
		this.event_state=state;
	}
	
	
	
	
}
