package com.yyp.mvc.controller;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cyc.hibernate.domain.DownLoadRecordDomian;
import com.cyc.service.DLoadService;


@Controller
@RequestMapping("/hello")
public class Helloword {
	@Autowired
	DLoadService dLoadService;

	
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
		 domian.setEb2k("ed2k://|file|BC2FC77E177FFA553318039208EF39FA.pdf|87996685|77CA1D49A9694F79CCA2E0CF0E9B90CD|h=2RT44DP6F5K5T7224L4YVQK3YALZYV2P|/");
		 domian.setEmail1("asfdsadfsdf");
		 domian.setEmail2("asfsafd");
		 dLoadService.addDLoadRecord(domian);
		return  "hello";
	}
	
	
	
	
}
