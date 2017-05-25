/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved/
 */
package com.cyc.lucene;

import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.util.Version;

import com.cyc.config.ConfigManage;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 查询工具类
 *
 * @author baoxing.gbx
 * @version $Id:SearchUtil.java, V 0.1 2015-11-16 14:09 baoxing.gbx Exp $$
 */
public class SearchUtil extends BaseUtil {

	/**
	 * 多条搜索
	 * 
	 * @param fields
	 * @param value
	 * @throws Exception
	 */
	public static void multiFieldQuery(String[] fields, String value,
			List<Document> docs) throws Exception {
		try {

			// 创建search
			IndexSearcher searcher = getIndexSearcher();

			QueryParser queryParser = new MultiFieldQueryParser(
					Version.LUCENE_35, fields, new StandardAnalyzer(
							Version.LUCENE_35));

			queryParser.setDefaultOperator(QueryParser.AND_OPERATOR);
			Query query = queryParser.parse(value);

			// 查询

			TopDocs topDocs = searcher.search(query, 1000);

			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			logger.info("查询到条数=" + scoreDocs.length);

			for (ScoreDoc scoreDoc : scoreDocs) {
				Document doc = searcher.doc(scoreDoc.doc);
				// logger.info("doc信息：" + " docId=" + scoreDoc.doc + " id=" +
				// doc.get("id") + " author="
				// + doc.get("author") + " name=" + doc.get("name") +
				// " content="
				// + doc.get("content"));
				docs.add(doc);
			}
		} catch (Exception e) {
			logger.error("termQuery查询失败");
			throw e;
		}
	}

	/**
	 * 多条搜索
	 * 
	 * @param fields
	 * @param value
	 * @throws Exception
	 */
	public static void queryParser(String field, String value) throws Exception {
		try {

			// 创建search
			IndexSearcher searcher = getIndexSearcher();

			QueryParser queryParser = new QueryParser(Version.LUCENE_35, field,
					new StandardAnalyzer(Version.LUCENE_35));

			queryParser.setDefaultOperator(QueryParser.AND_OPERATOR);
			Query query = queryParser.parse(value);

			// 查询
			TopDocs topDocs = searcher.search(query, 100);

			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			logger.info("查询到条数=" + scoreDocs.length);

			for (ScoreDoc scoreDoc : scoreDocs) {
				Document doc = searcher.doc(scoreDoc.doc);
				logger.info("doc信息：" + " docId=" + scoreDoc.doc + " id="
						+ doc.get("id") + " author=" + doc.get("author")
						+ " name=" + doc.get("name") + " content="
						+ doc.get("content"));
			}
		} catch (Exception e) {
			logger.error("termQuery查询失败");
			throw e;
		}
	}

	/**
	 * 重载原先的方法使用了分页。默认搜索的结果都为1000条为上线
	 * 
	 * @param fileds
	 * @param search
	 * @param docs
	 * @param page
	 * @param pagesize
	 * @throws Exception
	 * @return 返回查询到的记录数
	 */
	private static String MAX_INDEX_NUM = "maxindex";
	private static String[] indexColumns = { "title", "author", "isbn",
			"topic", "publisher" };

	public static int multiFieldQuery(String[] fileds, String search,
			List<Document> docs, String page, String pagesize) throws Exception {
		try {
			if (fileds == null) {
				fileds = indexColumns;
			}
			for (String field : fileds) {
				if (field.equals("default")) {
					fileds = indexColumns;
					break;
				}
			}
			// 创建search
			IndexSearcher searcher = getIndexSearcher();

			QueryParser queryParser = new MultiFieldQueryParser(
					Version.LUCENE_35, fileds, new StandardAnalyzer(
							Version.LUCENE_35));

			queryParser.setDefaultOperator(QueryParser.OR_OPERATOR);
			Query query = queryParser.parse(transformSolrMetacharactor(search));
			// 查询
			TopDocs topDocs = searcher.search(query, Integer
					.valueOf(ConfigManage.GLOBAL_STRING_CONFIG
							.get(MAX_INDEX_NUM)));

			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			logger.info("查询到条数=" + scoreDocs.length);

			int temp = 0;
			for (ScoreDoc scoreDoc : scoreDocs) {
				if (temp < (Integer.valueOf(page) * Integer.valueOf(pagesize))
						&& temp >= ((Integer.valueOf(page) - 1) * Integer
								.valueOf(pagesize))) {
					Document doc = searcher.doc(scoreDoc.doc);
					docs.add(doc);
				}
				temp++;
				if (temp > (Integer.valueOf(page) * Integer.valueOf(pagesize)))
					break;
			}
			return scoreDocs.length;
		} catch (Exception e) {
			logger.error("termQuery查询失败");
			throw e;
		}// TODO Auto-generated method stub

	}

	  /**
     * 转义Solr/Lucene的保留运算字符
     * 保留字符有+ - && || ! ( ) { } [ ] ^ ” ~ * ? : \
     * @param input
     * @return 转义后的字符串
     */
    public static String transformSolrMetacharactor(String input){
        StringBuffer sb = new StringBuffer();
        String regex = "[+\\-&|!(){}\\[\\]^\"~*?:(\\)]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while(matcher.find()){
            matcher.appendReplacement(sb, "\\\\"+matcher.group());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
	
}