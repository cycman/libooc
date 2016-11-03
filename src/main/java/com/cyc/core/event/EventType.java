package com.cyc.core.event;

/**
 * @author Administrator
 *
 */
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
	
	//
//	上传状态
//
//	未上传  正在上传  上传成功  上传失败
	//上传成功
	public static String UPLOADSUCCESS = "uploadsuccess";
	//上传失败
	public static String UPLOADFAILD = "uploadfaild";
	//正在列表
	public static String UPLOADLIST = "uploadlist";
	//上传一个
	public static String UPLOADDOMAIN = "uploaddomain";
	
	public static String UPLOADING = "uploading";

	//Email 发送失败
	public static String EMAILFAILD = "emailfaild";
	
	
	//上传事件
	
 	
	public static String CHECKUPLOAD = "checkupload";
	
	public static String UPLOADEXCEPTION="uploadexception";

}
