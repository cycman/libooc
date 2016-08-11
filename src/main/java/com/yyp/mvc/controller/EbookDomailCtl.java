package com.yyp.mvc.controller;

import org.apache.commons.logging.Log;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.cyc.domain.EbookDomain;
import com.cyc.hibernate.EbookHibernateImp;
import com.cyc.service.EbookService;
import com.cyc.util.log.ControlLog;
import com.cyc.view.BaseView;
import com.cyc.view.EbookView;




@Controller
@RequestMapping("/ebook")
public class EbookDomailCtl {

	 @Autowired
	 private EbookService ebookService;
	 private Log ebLog= new ControlLog(EbookService.class);
	 
    @RequestMapping(value="/detail",produces="text/html;charset=gbk")  
   @ResponseBody
	 public String findebook(String ebid) throws Exception
	 {
		ebLog.info("ebid>>>>>>>"+ebid);
		EbookDomain ebookDomain= ebookService.findbyEid(ebid);
		EbookView ebookView= new EbookView(ebookDomain);
		
		 
		 
		 return JSON.toJSONString(ebookView);
	 }
	 
	
}
