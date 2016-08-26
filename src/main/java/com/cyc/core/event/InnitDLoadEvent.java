package com.cyc.core.event;

import java.util.List;

import com.cyc.hibernate.domain.Ed2kStateDomain;

 
public class InnitDLoadEvent extends BaseEvent {

	private List<Ed2kStateDomain>domians;
	public	InnitDLoadEvent(List<Ed2kStateDomain>domians)
	{
		this.domians=domians;
		this.event_state=INNITDLOAD;
		
	}
	
	public List<Ed2kStateDomain> getDomians()
	{
		return domians;
	}
	
	
}
