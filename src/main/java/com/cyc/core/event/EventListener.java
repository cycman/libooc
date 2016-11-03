package com.cyc.core.event;

import org.apache.commons.io.monitor.FileAlterationListener;

import com.cyc.core.CoreControlJob;

public interface EventListener extends FileAlterationListener {

	 	
	void handelEvent(BaseEvent event);

	void regist(EventListener listener);
	
	void fireEvent(BaseEvent event);
	
	String getMonitorPath();
	
	
	
}
