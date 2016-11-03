package com.cyc.util;

import java.io.File;
import java.io.IOException;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.UploadFileRequest;
import com.cyc.config.ConfigManage;

public class OssClientUtil {

public static void main(String[] args)  {
		
		
//		// endpoint以杭州为例，其它region请按实际情况填写
//		String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
//		// accessKey请登录https://ak-console.aliyun.com/#/查看
//		String accessKeyId = "7OlAwP5eDKbOUQa9";
//		String accessKeySecret = "mk71mMarFIcP4XkcqnNePQF6sbXiHH";
//
//		// 创建OSSClient实例
//		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
// 		// 使用访问OSS
//		//ossClient.getObject(new GetObjectRequest("liboocboooks", "abc/sa.pdf"), new File("f://a.pdf"));
//
//		OSSObject ossObject = ossClient.getObject("liboocboooks", "abc/ad.pdf");
////		InputStream content = ossObject.getObjectContent();
////		if (content != null) {
////		    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
////		    while (true) {
////		        String line = reader.readLine();
////		        if (line == null) break;
////		        System.out.println("\n" + line);
////		    }
////		    content.close();
////		}
//		
//		
//		
//		
//		// 关闭ossClient
//		ossClient.shutdown();
//		
//		
	  System.out.println(	isUp("abc/a.pdf"));
	}

	public static boolean isUp(String path)
	{
 			//	String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
				// accessKey请登录https://ak-console.aliyun.com/#/查看
		OSSClient ossClient=null;
		        try {
					String endpoint =ConfigManage.GLOBAL_STRING_CONFIG.get("endpoint");//"http://oss-cn-shenzhen.aliyuncs.com";
					String accessKeyId =ConfigManage.GLOBAL_STRING_CONFIG.get("accessKeyId");
					String accessKeySecret =ConfigManage.GLOBAL_STRING_CONFIG.get("accessKeySecret"); //"mk71mMarFIcP4XkcqnNePQF6sbXiHH";

					// 创建OSSClient实例
					ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
					
					OSSObject ossObject = ossClient.getObject(ConfigManage.GLOBAL_STRING_CONFIG.get("bucket"), path);

					 
				} catch (OSSException e) {
					// TODO Auto-generated catch blo
					e.printStackTrace();
					return false;
				} catch (ClientException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;

				}finally{
					if(ossClient!=null)
					{
						ossClient.shutdown();
					}
				}
		        
				
				return true;
	}
	
	public static boolean UploadFile(String filePath,String destpath) throws Throwable
	{
		

        try {
			String endpoint ="http://oss-cn-shenzhen.aliyuncs.com";
			String accessKeyId = "7OlAwP5eDKbOUQa9";
			String accessKeySecret = "mk71mMarFIcP4XkcqnNePQF6sbXiHH";

			// 创建OSSClient实例
			OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			
 			ossClient.putObject("liboocboooks", destpath,new File(filePath));
 			Thread.sleep(3000);
			ossClient.shutdown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return false;
		} 
        		
		return true;
		
	}
	
	
	
	

	public static boolean UploadFile(File destfile,String destpath) throws Throwable {
		// TODO Auto-generated method stub
		return UploadFile(destfile.getAbsolutePath(),destpath);
	}

	public static boolean UploadImg(File sourceFile, String path) {
		// TODO Auto-generated method stub
		  try {
				String endpoint ="http://oss-cn-shanghai.aliyuncs.com";
				String accessKeyId = "7OlAwP5eDKbOUQa9";
				String accessKeySecret = "mk71mMarFIcP4XkcqnNePQF6sbXiHH";

				// 创建OSSClient实例
				OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
				
	 			ossClient.putObject("libooc", path,sourceFile);
 				ossClient.shutdown();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				return false;
			} 
	        		
		
		  return true;
	}
	public static boolean ImgIsUpload( String path) {
		// TODO Auto-generated method stub
		OSSClient ossClient =null;
		  try {
				String endpoint ="http://oss-cn-shanghai.aliyuncs.com";
				String accessKeyId = "7OlAwP5eDKbOUQa9";
				String accessKeySecret = "mk71mMarFIcP4XkcqnNePQF6sbXiHH";

				// 创建OSSClient实例
				ossClient	= new OSSClient(endpoint, accessKeyId, accessKeySecret);
				
	 			ossClient.getObject("libooc", path);
				ossClient.shutdown();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				return false;
			} 
		  finally{
			  if(ossClient!=null)
			  {
				  ossClient.shutdown();
			  }
		  }
	        		
		
		  return true;
	}


}
