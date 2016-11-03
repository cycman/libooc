package com.yyp.mvc.controller;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cyc.hibernate.domain.DownLoadRecordDomian;
import com.cyc.hibernate.domain.IpDomain;
import com.cyc.hibernate.imp.IpHibernateImp;
import com.cyc.service.DLoadService;


@Controller
@RequestMapping("/hello")
public class Helloword {
	@Autowired
	DLoadService dLoadService;
	@Autowired 
	IpHibernateImp iph;
	
	@RequestMapping(value="hello",produces="text/html;charset=gbk")  // 瀛恟equest璇锋眰url锛屾嫾鎺ュ悗绛変环浜�test3/login.do

     public String forjson()
 	{
        
 		
 		
 		
 		return  "hello";
 	}
	
	

	@RequestMapping(value="download",produces="text/html;charset=gbk")  // 瀛恟equest璇锋眰url锛屾嫾鎺ュ悗绛変环浜�test3/login.do
      
     public String download()
 	{
		Clipboard clip=Toolkit.getDefaultToolkit().getSystemClipboard(); 		
		Transferable t=new StringSelection("ed2k://|file|0784E4653EC1245E9AD9CF6DEB221733.pdf|2013463|BDE2CF04A562B8B954EA3940D3C26C59|h=7NIZOSMS3QBU77DT4KZ2AJ5ZJGMEGOFD|/");
		clip.setContents(t, null);
 		
 		return  "hello";
 	}
	
	
	@RequestMapping(value="downloads",produces="text/html;charset=gbk")  // 瀛恟equest璇锋眰url锛屾嫾鎺ュ悗绛変环浜�test3/login.do
    
    public String downloads() throws Exception
	{
		 DownLoadRecordDomian domian= new DownLoadRecordDomian();
		 domian.setEd2k("ed2k://|file|667F229EDBB51FFA0483AB884A3394E9.pdf|4269406|8D8DEB8C1992874691AC94AF5FE49A46|h=EZTBI4OCSMSFHW7DC2RNEHTVBWNU3VFW|/");
		 domian.setEmail1("597318121@qq.com");
		 domian.setEmail2("597318121@qq.com");
		 domian.setPathStored("1000/667F229EDBB51FFA0483AB884A3394E9.pdf");
		 dLoadService.addDLoadRecord(domian);
		return  "hello";
	}
	
@RequestMapping(value="ip",produces="text/html;charset=gbk")  // 瀛恟equest璇锋眰url锛屾嫾鎺ュ悗绛変环浜�test3/login.do
    
    public String ip(HttpServletRequest request) throws Exception
	{
		
		String ip= request.getRemoteAddr();		
		iph.save(new IpDomain(ip));
		return  "hello";
	}
	
	
}
