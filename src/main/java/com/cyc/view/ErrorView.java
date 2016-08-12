package com.cyc.view;

import com.cyc.exception.MyException;

public class ErrorView extends BaseView {

	private  String errorcode="";
	
	
	public ErrorView(Exception e) {
		// TODO Auto-generated constructor stub
		if(e instanceof MyException)
		{
			this.errorcode= ((MyException)e).getERROR_CODE();
		}
		else {
			this.errorcode= "未知异常";
		}
	
	}


	public String getErrorcode() {
		return errorcode;
	}


	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}


	 
	
	

	
	
	
}
