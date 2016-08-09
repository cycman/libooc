package com.cyc.util.log;

public class ControlLog extends AbstracMyLog {

	public ControlLog(Class wClass) {
		super(wClass);
		// TODO Auto-generated constructor stub
	}
	
	public void info(String map,String content)
	{
			
			System.out.println(map+"::::>"+content);
	}
	

}
