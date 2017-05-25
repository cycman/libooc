/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved/
 */
package com.cyc.service;


import java.util.List;

import org.apache.lucene.document.Document;
import org.junit.Test;

 

import static org.junit.Assert.assertTrue;
import com.cyc.lucene.SearchUtil;

import junit.framework.TestCase;

/**
 * 更多例子 请看： http://www.cnblogs.com/E-star/p/4969458.html
 *
 * @author baoxing.gbx
 * @version $Id:SearchUtilTest.java, V 0.1 2015-11-16 14:37 baoxing.gbx Exp $$
 */
public class SearchUtilTest  {

   
  

	/**
	 * 测试多条件查询
	 */
    @Test
	public void testMultiFieldQuery() {

    	
		try {
			String[] fields = { "name", "author", "content" };
			SearchUtil.multiFieldQuery(fields, "java",null);

		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * 测试多条件查询
	 */
	 
	public void testQueryParser() {

		try {

			SearchUtil.queryParser("name", "Java AND program6");

			SearchUtil.queryParser("name", "Java AND author:lisi6");

		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	public static void multiFieldQuery(String[] fileds, String search,
			List<Document> docs) {
		// TODO Auto-generated method stub
		
	}


}