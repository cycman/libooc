package com.yyp.mvc.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.commons.logging.impl.SimpleLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.cyc.domain.EbookDomain;
import com.cyc.domain.EbookIndex;
import com.cyc.hibernate.EbookHibernateImp;
import com.cyc.hibernate.core.BaseHibernateDAOImp;
import com.cyc.service.IndexSearchSer;
import com.cyc.util.log.ControlLog;
import com.cyc.view.SearchPageView;




/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/search")
public class IndexSeacherCtl {

	
	
	  public static String PAGEURL="search/";
	  
     	//创建controllog
	  private ControlLog controllog =new ControlLog(this.getClass());
	  private IndexSearchSer seacher= new IndexSearchSer();
	  
	  @Autowired
	  EbookHibernateImp hibernateImp;
	 
	 
	@RequestMapping(value="/default",produces="text/html;charset=gbk")  
    @ResponseBody
    public String defaultSearch(String search,String page,String pagesize)
	{
		 controllog.info("default"+"::::>"+"searchfor::::>"+search+"::::>"+page+"::::>"+pagesize);
		  
         SearchPageView view= new SearchPageView();       
         List<EbookIndex> indexs= seacher.IndexSearchDefault(search,page,pagesize);
		
         view.setIndexs(indexs);
         view.setPage(page);
         view.setPagesize(pagesize);
         view.setNextPageUrl(PAGEURL+"default?"+"search="+search+"&page="+(Integer.valueOf(page)+1)+"&pagesize="+Integer.valueOf(pagesize)+1);
         view.setPrviousPageUrl(PAGEURL+"default?"+"search="+search+"&page="+(Integer.valueOf(page)-1)+"&pagesize="+Integer.valueOf(pagesize)+1);
         view.setMaxIndexs(IndexSearchSer.INDEXNUMMAP.get(search));
		 EbookHibernateImp ebookHibernateImp= new EbookHibernateImp();
		 
		 EbookDomain ebookDomain=ebookHibernateImp .findByColum("eb_ID", "27002");
         return  JSON.toJSONString(view);
	}

	
	
	
	
}
