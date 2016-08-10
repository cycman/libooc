package com.cyc.hibernate.core;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cyc.domain.EbookDomain;


/**
 * Data access object (DAO) for domain model
 * @author MyEclipse Persistence Tools
 */
@Component
public class BaseHibernateDAOImp<T> implements IBaseHibernateDAO<T> {
	
	    // DAO组件进行持久化操作底层依赖的SessionFactory组件
	     @Autowired
		private static SessionFactory sessionFactory ;
		protected int rowsCount;

		public void setSessionFactory(SessionFactory sessionFactory) {
			this.sessionFactory = sessionFactory;
		}

		// spring 建议调用sessionFactory.getCurrentSession()方法得到方式
		// 还有sessionFactory.openSession()
		public Session getSession() {
			return sessionFactory.openSession();
		}
         
		@Override
		public void save(T t) {
			// TODO Auto-generated method stub
			getSession().save(t);
		}

		@Override
		public void delete(T t) {
			// TODO Auto-generated method stub
			getSession().delete(t);
		}

		/**
		 * 使用hql 语句进行分页查询操作
		 * 
		 * @param hql
		 *            需要查询的hql语句
		 * @param pageNo
		 *            查询第pageNo页的记录
		 * @param pageSize
		 *            每页需要显示的记录数
		 * @return 当前页的所有记录
		 */
		@SuppressWarnings("unchecked")
		public List<T> findByPage(String hql, int pageNo, int pageSize) {
			// 创建查询
			return getSession().createQuery(hql)
					// 执行分页
					.setFirstResult((pageNo - 1) * pageSize)
					.setMaxResults(pageSize).list();
		}

		/**
		 * 使用hql 语句进行分页查询操作
		 * 
		 * @param hql
		 *            需要查询的hql语句
		 * @param params
		 *            如果hql带占位符参数，params用于传入占位符参数
		 * @param pageNo
		 *            查询第pageNo页的记录
		 * @param pageSize
		 *            每页需要显示的记录数
		 * @return 当前页的所有记录
		 */
		@SuppressWarnings("unchecked")
		public List<T> findByPage(String hql, int pageNo, int pageSize,
				Object... params) {
			// 创建查询
			Query query = getSession().createQuery(hql);
			// 为包含占位符的HQL语句设置参数
			for (int i = 0, len = params.length; i < len; i++) {
				query.setParameter(i + "", params[i]);
			}
			// 执行分页，并返回查询结果
			return query.setFirstResult((pageNo - 1) * pageSize)
					.setMaxResults(pageSize).list();
		}

		 
		
		
		
	
}