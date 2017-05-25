package com.cyc.hibernate.imp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.cyc.exception.MyException;
import com.cyc.hibernate.core.BaseHibernateDAOImp;
import com.cyc.hibernate.dao.IebookDao;
import com.cyc.hibernate.domain.BookTopic;
import com.cyc.hibernate.domain.EbookDomain;
import com.cyc.hibernate.domain.Ed2kStateDomain;

@Component
public  class TopicHibernateImp extends BaseHibernateDAOImp<BookTopic>{

	
	
	public BookTopic findByTopicId(String id) {
		Session session = null;
		try {
			session = getSession();
			String hql = "from book_topic where topicId=? and lang=?";
			Query query = session.createQuery(hql);
			query.setString(0, id);
			query.setString(1, "en"); 

			List<BookTopic> topics = query.list();

			return topics.size() > 0 ? topics.get(0) : null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { 
			if (session != null) {
				session.close();
			}
		}
		return null;

	}
	
	
	
}
