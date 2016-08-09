package com.yyp.mvc.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.commons.logging.impl.SimpleLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cyc.hibernate.BaseHibernateDAO;
import com.cyc.service.IndexSearchSer;
import com.cyc.util.log.ControlLog;




/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/search")
public class IndexSeacherCtl {

	
     //创建controllog
	  private ControlLog controllog =new ControlLog(this.getClass());
	  private IndexSearchSer seacher= new IndexSearchSer();
	
	  
	  @Autowired
	  private BaseHibernateDAO BaseHibernateDAO;
	
	@RequestMapping(value="/default",produces="text/html;charset=gbk")  
    @ResponseBody
    public String defaultSearch(String search)
	{
         controllog.info("default"+"::::>"+"searchfor::::>"+search);
		  
          controllog.info( BaseHibernateDAO.getSession());
        
         seacher.IndexSearchDefault(search);
		
		 return  "hello";
	}

	
	
	
	
}
