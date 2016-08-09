package com.cyc.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import com.cyc.domain.EbookIndex;
import com.cyc.lucene.IndexUtil;
import com.cyc.lucene.SearchUtil;

public class IndexSearchSer {

	
	
	
	/**
	 * 
	 * 默认进行模糊搜索
	 * @return
	 */
	public  List<EbookIndex> IndexSearchDefault(String search)	
	{
		   try {
		            IndexUtil.numDocs();

		            SearchUtil.fuzzyQuery("title", "Human", 0.1f);

		        } catch (Exception e) {
		          //  assertTrue(e.getMessage(), false);
		        }
		    
		        return null;
	}
	
	
	
	
}
