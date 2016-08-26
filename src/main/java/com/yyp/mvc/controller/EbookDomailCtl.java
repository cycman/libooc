package com.yyp.mvc.controller;

import java.util.logging.Logger;

import javax.json.Json;

import org.apache.commons.logging.Log;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.cyc.exception.MyException;
import com.cyc.hibernate.domain.EbookDomain;
import com.cyc.hibernate.imp.EbookHibernateImp;
import com.cyc.service.EbookService;
 import com.cyc.view.BaseView;
import com.cyc.view.EbookView;
import com.cyc.view.ErrorView;




@Controller
@RequestMapping("/ebook")
public class EbookDomailCtl extends BaseHandleExceptionControl {

	 @Autowired
	 private EbookService ebookService;
	 private Logger log= Logger.getLogger(this.getClass().getName());
	 
    @RequestMapping(value="/detail",produces="text/html;charset=gbk")  
   @ResponseBody
	 public String findebook(String ebid) throws Exception 
	 {
    	log.info("ebid>>>>>>>"+ebid);
		EbookDomain ebookDomain;
		EbookView ebookView = null;
		try {
			ebookDomain = ebookService.findbyEid(ebid);
			ebookView	= new EbookView(ebookDomain);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 
				
		   throw e;
			
			
		
		}
		
		
		 
		 return JSON.toJSONString(ebookView);
	 }
	 
	
}
