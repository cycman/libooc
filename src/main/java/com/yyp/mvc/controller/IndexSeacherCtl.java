package com.yyp.mvc.controller;

import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.commons.logging.impl.SimpleLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.cyc.domain.EbookIndex;
import com.cyc.hibernate.core.BaseHibernateDAOImp;
import com.cyc.hibernate.domain.EbookDomain;
import com.cyc.hibernate.imp.EbookHibernateImp;
import com.cyc.service.IndexSearchSer;
import com.cyc.view.SearchPageView;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/search")
public class IndexSeacherCtl extends BaseHandleExceptionControl {
	
 	public static String PAGEURL = "search/";

	// 创建controllog
	private Logger log = Logger.getLogger(this.getClass().getName());
	private IndexSearchSer seacher = new IndexSearchSer();

	@RequestMapping(value = "/default", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String defaultSearch(String search, String page, String pagesize)
			throws Exception {
		log.info("default" + "::::>" + "searchfor::::>" + search + "::::>"
				+ page + "::::>" + pagesize);

		SearchPageView view = new SearchPageView();
		List<EbookIndex> indexs = seacher.IndexSearchDIV(EbookIndex.DEFAULTINDEXS,search, page,
				pagesize);

		view.setIndexs(indexs);
		view.setPage(page);
		view.setPagesize(pagesize);
		view.setNextPageUrl(PAGEURL + "default?" + "search=" + search
				+ "&page=" + (Integer.valueOf(page) + 1) + "&pagesize="
				+ (Integer.valueOf(pagesize)));
		view.setPrviousPageUrl(PAGEURL + "default?" + "search=" + search
				+ "&page=" + (Integer.valueOf(page) - 1) + "&pagesize="
				+ (Integer.valueOf(pagesize)));
		view.setMaxIndexs(IndexSearchSer.INDEXNUMMAP.get(search));

		return JSON.toJSONString(view);
	}
	
	
	@RequestMapping(value = "/FiledsSearch", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String FiledsSearch(String fileds,String search, String page, String pagesize)
			throws Exception {
		log.info("FiledsSearch" + "::::>" + "searchfor::::>" + search + "::::>"
				+ page + "::::>" + pagesize);

		SearchPageView view = new SearchPageView();
		List<EbookIndex> indexs = seacher.IndexSearchDIV(fileds.split(","),search, page,
				pagesize);

		view.setIndexs(indexs);
		view.setPage(page);
		view.setPagesize(pagesize);
		view.setNextPageUrl(PAGEURL + "FiledsSearch?fileds="+fileds+"&search=" + search
				+ "&page=" + (Integer.valueOf(page) + 1) + "&pagesize="
				+ (Integer.valueOf(pagesize)));
		view.setPrviousPageUrl(PAGEURL + "FiledsSearch?fileds="+fileds+"&search=" + search
				+ "&page=" + (Integer.valueOf(page) - 1) + "&pagesize="
				+ (Integer.valueOf(pagesize)));
		view.setMaxIndexs(IndexSearchSer.INDEXNUMMAP.get(search));

		return JSON.toJSONString(view);
		
		
	}
	
	

}
