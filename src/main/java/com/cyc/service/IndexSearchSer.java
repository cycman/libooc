package com.cyc.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.lucene.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cyc.config.ConfigManage;
import com.cyc.domain.EbookIndex;
import com.cyc.hibernate.domain.EbookDomain;
import com.cyc.lucene.IndexUtil;
import com.cyc.lucene.SearchUtil;

@Component
public class IndexSearchSer {
	

	@Autowired
	private RecommendSerivce recommendSerivce ;

	
	// 缓存检索的索引的最大数目
	public static Map<String, Integer> INDEXNUMMAP = new HashMap<>();

	/**
	 * 
	 * 默认进行模糊搜索
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<EbookIndex> IndexSearchDefault(String search, String page,
			String pagesize) throws Exception {

		// IndexUtil.numDocs();
		// 多条查找
		String[] fileds = { "author", "title" };
		List<Document> docs = new ArrayList<>();
		// 获取搜索结果
		int maxIndex = SearchUtil.multiFieldQuery(fileds, search, docs, page,
				pagesize);

		if (INDEXNUMMAP.get(search) == null) {
			INDEXNUMMAP.put(search, maxIndex);
		}

		return ParamIndexToBean(docs);

	}

	/**
	 * 
	 * 定制化进行模糊搜索
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<EbookIndex> IndexSearchDIV(String[] fileds, String search,
			String page, String pagesize) throws Exception {

		// IndexUtil.numDocs();
		// 多条查找
		List<Document> docs = new ArrayList<>();
		// 获取搜索结果
		int maxIndex = SearchUtil.multiFieldQuery(fileds, search, docs, page,
				pagesize);

		if (INDEXNUMMAP.get(search) == null||INDEXNUMMAP.get(search)!=maxIndex) {
			INDEXNUMMAP.put(search, maxIndex);
		}

		return ParamIndexToBean(docs);

	}

	public static List<EbookIndex> ParamIndexToBean(List<Document> docs)
			throws UnsupportedEncodingException {
		List<EbookIndex> indexs = new ArrayList<>();

		Iterator<Document> diIterator = docs.iterator();
		while (diIterator.hasNext()) {
			Document doc = diIterator.next();
			EbookIndex index = new EbookIndex();
			index.setAuthor(doc.get("author"));
			index.setE_id(doc.get("id"));
			index.setTitle(doc.get("title"));
			index.setTopic(doc.get("topic"));
			index.setImgurl(ConfigManage.GLOBAL_STRING_CONFIG
					.get("source_img_heard") + doc.get("ImageUrl"));
			index.setPublisher(doc.get("publisher"));
			index.setIsbn(doc.get("isbn"));
			indexs.add(index);
		}

		return indexs;

	}

	public List<EbookIndex> IndexSearchDIVDuid(String[] fields, String search,
			String page, String pagesize, long uid) throws Exception {
		// TODO Auto-generated method stub

		
		// IndexUtil.numDocs();
		// 多条查找
		List<Document> docs = new ArrayList<>();
		String origin = search;
		//根据用户行为对搜索字段进行修改
		search = getUserSearch(search,uid);
		System.out.println(fields);
		// 获取搜索结果
		int maxIndex = SearchUtil.multiFieldQuery(fields, search, docs, page,
				pagesize);

		if (INDEXNUMMAP.get(origin) == null) {
			INDEXNUMMAP.put(origin, maxIndex);
		}

		return ParamIndexToBean(docs);
	}

	private String getUserSearch(String search,long uid) throws Exception {
		// TODO Auto-generated method stub
		List<EbookDomain>books=recommendSerivce.recommendBooksByUid(uid);
		if(books.size()>=1)
		{
			search=books.get(1).getExtension()+" "+books.get(1).getTopic();
		}
		return search;
	}


}
