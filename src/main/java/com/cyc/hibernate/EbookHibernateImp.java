package com.cyc.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.cyc.domain.EbookDomain;
import com.cyc.exception.MyException;
import com.cyc.hibernate.core.BaseHibernateDAOImp;

@Component
public  class EbookHibernateImp extends BaseHibernateDAOImp<EbookDomain> implements IebookDao{

	
	
	
	/* colum 数据库中表的列
	 * param 要查询的列的值
	 * (non-Javadoc)
	 * @see com.cyc.hibernate.IebookDao#findByColum(java.lang.String, java.lang.Object)
	 */
	@Override
	public EbookDomain findByColum(String colum,Object  params) throws Exception {
		// TODO Auto-generated method stub
		Session session=null;
		try {
			String hql = "from ebook_t as eb where 1=1";
			
			session = getSession();
			if(params!=null)
			{
				hql+= " and "+colum+"="+params;
				
			}
			 
			
			List<EbookDomain> domains = session.createQuery(hql).list();
			if(domains.size()>0)
			return domains.get(0);
			else {
			throw 	new MyException(MyException.ERROR_EBID_NULL,new Exception(), getClass());
					
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		finally{
			
			if(session!=null)
				session.close();
			
		}
		 
	}
 

	
	
	
	
	
	
}
