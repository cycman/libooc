package com.cyc.hibernate.imp;

import java.util.List;

 

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.cyc.hibernate.core.BaseHibernateDAOImp;
import com.cyc.hibernate.domain.DownLoadRecordDomian;
import com.cyc.hibernate.domain.Ed2kStateDomain;

@Component
public class Ed2kStateHibernateImp extends BaseHibernateDAOImp<Ed2kStateDomain> {

	public Ed2kStateDomain findByEd2k(String ed2k) {
		Session session = null;
		try {
			session = getSession();
			String hql = "from ed2k e where ed2k = ?";
			Query query = session.createQuery(hql);
			query.setString(0, ed2k);
			List<Ed2kStateDomain> ed2kStateDomains = query.list();

			return ed2kStateDomains.size() > 0 ? ed2kStateDomains.get(0) : null;
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
	
	
	
	public List<Ed2kStateDomain> findByColumLike(String Colum,String value)
	{
		Session session = null;
		try {
			session = getSession();
			String hql = "from ed2k where "+Colum+" like  '%"+value+"%'";
			Query query = session.createQuery(hql);
			 
			List<Ed2kStateDomain> ed2kStateDomains = query.list();

			return ed2kStateDomains;
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
	public List<Ed2kStateDomain> findByColum(String Colum,String value)
	{
		Session session = null;
		try {
			session = getSession();
			String hql = "from ed2k e where "+Colum+" = ?";
			Query query = session.createQuery(hql);
			query.setString(0, value);
			List<Ed2kStateDomain> ed2kStateDomains = query.list();

			return ed2kStateDomains;
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
	
	public List<Ed2kStateDomain> findByColumsOr(String[] Colums,String[] values)
	{
		Session session = null;
		try {
			session = getSession();
			String hql = "from ed2k e where 1 = 0 ";
			for(String colum:Colums)
			{
				hql+=  " or "+colum+"= ? ";
				
			}
			
			
			
			Query query = session.createQuery(hql);
			for(int i=0;i<values.length;i++)
			query.setString(i, values[i]);
			List<Ed2kStateDomain> ed2kStateDomains = query.list();

			return ed2kStateDomains;
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
	@Override
	public void save(Ed2kStateDomain domian)
	{
		
		 Session session = getSession();
		// Transaction transaction =session.beginTransaction();
		domian.setId( (Integer)session.save(domian));
		 //transaction.commit();
	     session.close();
		
	}
	

}
