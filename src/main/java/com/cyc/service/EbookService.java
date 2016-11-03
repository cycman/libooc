package com.cyc.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cyc.config.ConfigManage;
import com.cyc.exception.MyException;
import com.cyc.hibernate.domain.EbookDomain;
import com.cyc.hibernate.domain.Ed2kStateDomain;
import com.cyc.hibernate.imp.EbookHibernateImp;
import com.cyc.hibernate.imp.Ed2kStateHibernateImp;
import com.cyc.util.OssClientUtil;

@Component
public class EbookService {

	@Autowired
	private EbookHibernateImp ebookHibernateImp;

	@Autowired
	private Ed2kStateHibernateImp ed2kStateHibernateImp;

	public EbookDomain findbyEid(String ebid) throws Exception {
		// TODO Auto-generated method stub
		if (!isNumeric(ebid)) {
			MyException exception = new MyException(new Exception(), getClass());
			exception.setERROR_CODE(MyException.ERROR_EBID_NOT_NUM);
			throw exception;
		}

		EbookDomain domain = ebookHibernateImp.findByColum(
				EbookHibernateImp.EBOOK_COLUM_EID, ebid);
		String ed2k = null;

		return domain;
	}

	public static String ForEd2k(EbookDomain domain) {
		// TODO Auto-generated method stub
		String ed2k = "";
		String heard = "ed2k://|FILE|";
		String md5 = domain.getMD5();
		ed2k += md5;

		String extension = domain.getEdition();
		ed2k += "." + extension + "|";

		String filesize = domain.getSizeByte();
		ed2k += filesize + "|";

		String eDonkey = domain.geteDokey();
		ed2k += eDonkey + "|";

		String Aich = "h=" + domain.getAICH();
		ed2k += Aich + "|/";
		return ed2k;
	}

	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public boolean isFree(String ebid) throws Exception {
		// TODO Auto-generated method stub
		EbookDomain domain = ebookHibernateImp.findByColum("eb_id", ebid);
		List<Ed2kStateDomain> ed2kStateDomains = ed2kStateHibernateImp
				.findByColum("ebid", ebid);
		if (ed2kStateDomains != null
				&& ed2kStateDomains.size() > 0
				&& ed2kStateDomains.get(0).getState_upload()
						.equals(Ed2kStateDomain.UP_SUCCESSED)) {
			return false;
		}
		if (OssClientUtil.isUp(domain.getURL())) {
			if (ed2kStateDomains == null || ed2kStateDomains.size() == 0) {
				Ed2kStateDomain domain2 = new Ed2kStateDomain(domain,
						Ed2kStateDomain.UP_SUCCESSED);
				ed2kStateHibernateImp.save(domain2);

			} else {
				ed2kStateDomains.get(0).setState_upload(
						Ed2kStateDomain.UP_SUCCESSED);
				ed2kStateHibernateImp.update(ed2kStateDomains.get(0));
			}

			return false;

		}

		return true;
	}

	public String uploadImg(String ebid) throws Exception {
		// 获取id对应的书籍
		final EbookDomain domain = ebookHibernateImp.findByColum("eb_id", ebid);
		if ("".equals(domain.getImageUrl())) {
			throw new Exception("书籍不存在图片");

		}
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String path = domain.getImageUrl();
				// 从源网站总下载文件
				File sourceFile;
				try {
					sourceFile = DownLoadImg(
							ConfigManage.GLOBAL_STRING_CONFIG.get("source_img_heard")
									+ path,
							ConfigManage.GLOBAL_STRING_CONFIG.get("img_dest_dir") + path.split("/")[1]);
					OssClientUtil.UploadImg(sourceFile, path);
					 //不管有没有上传成功都删除源文件
					 sourceFile.delete();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				 // 上传文件到oss服务器
				 
			}
		}).start();
		
		 return ConfigManage.GLOBAL_STRING_CONFIG.get("img_heard") + domain.getImageUrl();
	}

	private File DownLoadImg(String source,String destpath)throws Exception {
		// TODO Auto-generated method stub
		InputStream stream= null;
		FileOutputStream outputStream =null;
		try {
			File sourceFile = new File(destpath);
			if(!sourceFile.exists())
			{
				
				sourceFile.createNewFile();
			}
			URL url = new URL(source);
			HttpURLConnection connection= (HttpURLConnection)url.openConnection();
			if(connection.getResponseCode()!=200)
			{
				return null;
			}
			byte [] data= new byte[1024];
			int len=0;
			  stream= connection.getInputStream();
			  outputStream= new FileOutputStream(sourceFile);
			while((len=stream.read(data))!=-1)				
			{
				outputStream.write(data,0,len);
			}
			
			
			return sourceFile;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		finally{
			if(stream!=null)
			{
				stream.close();
			}
			if(outputStream!=null)
			{
				outputStream.close();
			}
			//return null;
			
			
		}
	}

	public String getimg(String ebid) throws Exception {
		// TODO Auto-generated method stub
		EbookDomain domain = ebookHibernateImp.findByColum("eb_id", ebid);
 		
		if(OssClientUtil.ImgIsUpload(domain.getImageUrl()))
			return ConfigManage.GLOBAL_STRING_CONFIG.get("img_heard")+domain.getImageUrl();
		//如果不存在就上传图片后返回
		return uploadImg(ebid);
	}
}
