package com.cyc.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.springframework.stereotype.Component;

import com.cyc.domain.EbookIndex;
import com.cyc.lucene.IndexUtil;
import com.cyc.lucene.SearchUtil;


@Component
public class IndexSearchSer {

	
	//缓存检索的索引的最大数目  
	public  static Map<String, Integer> INDEXNUMMAP= new HashMap<>();
	
	
	/**
	 * 
	 * 默认进行模糊搜索
	 * @return
	 */
	public  List<EbookIndex> IndexSearchDefault(String search,String page,String pagesize)	
	{
		   try {
			   		
		            //IndexUtil.numDocs();
		            //多条查找
		           String[] fileds={"author","title"};                                  
		           List<Document> docs= new ArrayList<>();
		           //获取搜索结果
		          int maxIndex= SearchUtil.multiFieldQuery(fileds, search, docs,page,pagesize);
		          
		          if(INDEXNUMMAP.get(search)==null)
		          {
		        	  INDEXNUMMAP.put(search, maxIndex);
		          }
		          
		          return ParamIndexToBean(docs);
		        } catch (Exception e) {
		          //  assertTrue(e.getMessage(), false);
		        }
		         return null;
		        
	}
	
	
	public static List<EbookIndex> ParamIndexToBean(List<Document>docs)
	{
		List<EbookIndex>indexs = new ArrayList<>();
		
		Iterator<Document> diIterator=	docs.iterator();
		while(diIterator.hasNext())
		{
			Document doc= diIterator.next();
			EbookIndex index= new EbookIndex();
			index.setAuthor(doc.get("author"));
			index.setE_id(doc.get("id"));
			index.setTitle(doc.get("title"));
			indexs.add(index);
		}
		
		
		return indexs;
		
		
	}
	
	
	
	
}
