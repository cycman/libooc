package com.cyc.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CompleteMultipartUploadRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.UploadFileRequest;
import com.cyc.config.ConfigManage;

public class OssClientUtil {

	
	
 	static Map<String, OSSClient> clietsPools = new HashMap<>();
	
	 static {
		String endpointhanzhou = "http://oss-cn-hangzhou.aliyuncs.com";
		String endpointshanghai = "http://oss-cn-shanghai.aliyuncs.com";
		String accessKeyId = ConfigManage.GLOBAL_STRING_CONFIG
				.get("accessKeyId");
		String accessKeySecret = ConfigManage.GLOBAL_STRING_CONFIG
				.get("accessKeySecret");
		ClientConfiguration clientConfiguration = new ClientConfiguration();
		clientConfiguration.setIdleConnectionTime(4000);
		clientConfiguration.setConnectionTimeout(10*60*1000);
		clietsPools.put("hanzhou",  new OSSClient(endpointhanzhou, accessKeyId,
				accessKeySecret,clientConfiguration));
		clietsPools.put("shanghai",  new OSSClient(endpointshanghai, accessKeyId,
				accessKeySecret,clientConfiguration));
	}
	
	public static boolean fileIsUp(String place,String bukete,String path) {
 		
 		try { 
 			OSSClient ossClient =  clietsPools.get(place);
			OSSObject ossObject = ossClient.getObject(
					ConfigManage.GLOBAL_STRING_CONFIG.get("bucket"), path);

		} catch (OSSException e) {
			// TODO Auto-generated catch blo
			
			e.printStackTrace();
			return false;
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;

		} 

		return true;
	}
	
	
	public static boolean isUp(String path) {
		// String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
		// accessKey请登录https://ak-console.aliyun.com/#/查看
		OSSClient ossClient = null;
		try {
			String endpoint = ConfigManage.GLOBAL_STRING_CONFIG.get("endpoint");// "http://oss-cn-shenzhen.aliyuncs.com";
			String accessKeyId = ConfigManage.GLOBAL_STRING_CONFIG
					.get("accessKeyId");
			String accessKeySecret = ConfigManage.GLOBAL_STRING_CONFIG
					.get("accessKeySecret"); // "mk71mMarFIcP4XkcqnNePQF6sbXiHH";

			// 创建OSSClient实例
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

			OSSObject ossObject = ossClient.getObject(
					ConfigManage.GLOBAL_STRING_CONFIG.get("bucket"), path);

		} catch (OSSException e) {
			// TODO Auto-generated catch blo
			e.printStackTrace();
			return false;
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;

		} finally {
			if (ossClient != null) {
				ossClient.shutdown();
			}
		}

		return true;
	}

	public static boolean UploadFile(String filePath, String destpath)
			throws Throwable {

		OSSClient ossClient = null;
		try {
			String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
			String accessKeyId = "7OlAwP5eDKbOUQa9";
			String accessKeySecret = "mk71mMarFIcP4XkcqnNePQF6sbXiHH";

			// 创建OSSClient实例
			ClientConfiguration clientConfiguration = new ClientConfiguration();
			clientConfiguration.setIdleConnectionTime(300000);
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret,
					clientConfiguration);

			ossClient.putObject("liboocboooks", destpath, new File(filePath));
			Thread.sleep(3000);
			ossClient.shutdown();
		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return false;
		} finally {
			if (ossClient != null) {
				ossClient.shutdown();
			}
		}

		return true;

	}

	public static boolean UploadFile(String place,String bucket, File source, String destpath) {

		OSSClient ossClient = null;
		try {
			 			 
			ossClient = clietsPools.get(place);
 			ossClient.putObject(bucket, destpath, source);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}  
		return true;

	}

	public static boolean UploadFile(File destfile, String destpath)
			throws Throwable {
		// TODO Auto-generated method stub
		return UploadFile(destfile.getAbsolutePath(), destpath);
	}

	public static boolean UploadImg(File sourceFile, String path) {
		// TODO Auto-generated method stub
		try { 
			clietsPools.get("shanghai").putObject("libooc", path, sourceFile);
 		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public static boolean ImgIsUpload(String path) {
		// TODO Auto-generated method stub
		OSSClient ossClient = null;
		try {
			String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
			String accessKeyId = "7OlAwP5eDKbOUQa9";
			String accessKeySecret = "mk71mMarFIcP4XkcqnNePQF6sbXiHH";

			// 创建OSSClient实例
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

			ossClient.getObject("libooc", path);
			ossClient.shutdown();
		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return false;
		} finally {
			if (ossClient != null) {
				ossClient.shutdown();
			}
		}

		return true;
	}

}
