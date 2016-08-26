package com.cyc.core.event;

import com.cyc.core.CoreControlJob;

public interface EventListener {

	 	
	void handelEvent(BaseEvent event);

	void regist(EventListener listener);
	
	void fireEvent(BaseEvent event);
	
	
	
	
}
