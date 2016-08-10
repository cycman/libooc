package com.cyc.util.log;

import org.apache.commons.logging.Log;

public abstract class AbstracMyLog implements Log {

	
	private Class wclass;
	
	
	public AbstracMyLog(Class wClass) {
		// TODO Auto-generated constructor stub
		this.wclass=wClass;
	}
	
	
	 
	@Override
	public void info(Object arg0) {
		// TODO Auto-generated method stub
		System.out.println("login"+wclass.getName());
	}
	 

}
