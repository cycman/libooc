package com.cyc.exception;

public class MyException extends Exception {

	
	public static final String ERROR_EBID_NOT_NUM = "001";//ebid 为非数字
	public static final String ERROR_EBID_NULL = "002";//数据库中缺失该记录
	
	
	private Exception exception;
	private String errorcode="";
	private Class class1;
	
	public MyException(Exception e,Class class1) {
		// TODO Auto-generated constructor stub
		super();
		this.exception=e;
		this.class1=class1;
	}
	public MyException(String error,Exception e,Class class1) {
		// TODO Auto-generated constructor stub
		super();
		this.errorcode=error;
		this.exception=e;
		this.class1=class1;
	}
	
	@Override
	public void printStackTrace() {
		// TODO Auto-generated method stub
		super.printStackTrace();
	    System.out.println("===================================="+class1.getName()+"出错啦！！！！error>>>>>>"+
	    		errorcode	    		
	    		);
	}

	public Class getClass1() {
		return class1;
	}



	public void setClass1(Class class1) {
		this.class1 = class1;
	}



	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String getERROR_CODE() {
		return errorcode;
	}

	public void setERROR_CODE(String eRROR_CODE) {
		errorcode=eRROR_CODE;	}
	

	
	
	
}
