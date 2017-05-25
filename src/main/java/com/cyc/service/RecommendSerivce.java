package com.cyc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cyc.hibernate.domain.BookTopic;
import com.cyc.hibernate.domain.EbookDomain;
import com.cyc.hibernate.imp.EbookHibernateImp;
import com.cyc.hibernate.imp.RecordHibernateImp;
import com.cyc.recoment.RecommenderCore;
import com.mysql.fabric.xmlrpc.base.Array;

@Component
public class RecommendSerivce {

	@Autowired
	RecordHibernateImp recordHibernateImp;
	@Autowired
	EbookHibernateImp ebookHibernateImp;

	@Autowired
	DataSource dataSource;
	private Logger logger = Logger.getLogger(this.getClass().getName());

	private static String RECORD_DB_NAME = "behavior_record";
	private static String COMMENT_DB_NAME = "book_comment";

	// 推荐书籍
	public List<EbookDomain> recommendBooksByUid(long uid) throws Exception {
		logger.log(Level.INFO, "recommend books by uid");
		// private Integer id;
		// private Long uid;
		// private Long eid;
		// private Integer typeId;
		// private Integer statusId;
		// private Integer boost;
		// private Integer createTime;
		DataModel sqlDataModel = new MySQLJDBCDataModel(dataSource,
				RECORD_DB_NAME, "uid", "eid", "boost", "createTime");
		List<RecommendedItem> items = RecommenderCore.svd(uid, sqlDataModel);

		List<EbookDomain> books = new ArrayList<EbookDomain>();
		for (RecommendedItem item : items) {
			books.add(ebookHibernateImp.findByColum("eb_ID", item.getItemID()));
		}
		return books;
	}

	// 推荐类目
	public List<BookTopic> recomentTopicsByUid(long uid) {
		logger.log(Level.INFO, "recommend topics by uid");

		return null;
	}

	// 推荐作者
	public void recomentAuthorsByUid(Integer uid) {
		logger.log(Level.INFO, "recommend authors by uid");

	}

	public List<Long> recomentBookIDs(Long uid, String behaviorType)
			throws Exception {
		// TODO Auto-generated method stub
		String dbName = RECORD_DB_NAME;
		String bookId="eid";
		if (behaviorType == "comment") {
			dbName = COMMENT_DB_NAME;
			bookId="bid";
			
		}
		DataModel sqlDataModel = new MySQLJDBCDataModel(dataSource,
				dbName, "uid",bookId, "boost", "createTime");

		List<RecommendedItem> items = RecommenderCore.svd(uid, sqlDataModel);

		List<Long> ids = new ArrayList<Long>();
		for (RecommendedItem item : items) {
			ids.add( item.getItemID());
		}
		return ids;

	}

}
