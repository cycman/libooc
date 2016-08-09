package com.cyc.hibernate;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Data access object (DAO) for domain model
 * @author MyEclipse Persistence Tools
 */
@Component
public class BaseHibernateDAO implements IBaseHibernateDAO {
	
	    // DAO组件进行持久化操作底层依赖的SessionFactory组件
	     @Autowired
		private SessionFactory sessionFactory ;
		protected int rowsCount;

		public void setSessionFactory(SessionFactory sessionFactory) {
			this.sessionFactory = sessionFactory;
		}

		// spring 建议调用sessionFactory.getCurrentSession()方法得到方式
		// 还有sessionFactory.openSession()
		public Session getSession() {
			return sessionFactory.getCurrentSession();
		}
		
		
	
}