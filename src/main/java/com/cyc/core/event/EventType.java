package com.cyc.core.event;

public interface EventType {

	//下载记录储存完毕
	public static String DLRECORDSTORED  = "dlrecordstord";
	//准备下载
    public static String PREPAREDLR = "preparedlr";
	//下载中
	public static String DOWONING = "downing";
	//下载失败
	public static String DOWOFAILD = "downfaild";
	public static String DOWNLOADSUCCESS = "downloadsuccess";
	//正在下载但是为检测
	public static String DOWONINGUNCHECK = "downinggununcheck";
	
	
	public static String INNITDLOAD="innitdload";
	//public static String EMAILSENDINNIT = "emailsendinnit";
	public static String SENDEMIALS = "sendemails";
	public static String EMAIL1SENDSUCCESSED = "email1sendsuccessed";
	public static String EMAIL2SENDSUCCESSED = "emailsendsuccessed";
	
	public static String EMAIL1SENDFAILD = "email1Sendfaild";
	public static String EMAIL2SENDFAILD = "email2Sendfaild";

	
	

}
