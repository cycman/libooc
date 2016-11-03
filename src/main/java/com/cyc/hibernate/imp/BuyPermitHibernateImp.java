package com.cyc.hibernate.imp;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.cyc.hibernate.core.BaseHibernateDAOImp;
import com.cyc.hibernate.domain.BuyPermitDomain;
import com.cyc.hibernate.domain.DownLoadRecordDomian;
@Component
public class BuyPermitHibernateImp extends BaseHibernateDAOImp<BuyPermitDomain> {

	
	public List<BuyPermitDomain> findbyColum(String colum, String value)
	{
		Session session=null;
		try {
			String hql = "from BuyPermit as bp where 1=1";
			
			session = getSession();
			 
 				hql+= " and  "+colum+"= \'"+value+"\'";
			 
 			
			 List<BuyPermitDomain>domians =session.createQuery(hql).list();
//				List<String> eb2ks = new ArrayList<>();
//
//			 for(DownLoadRecordDomian domian:domians)
//			 {
//				 eb2ks.add(domian.getEb2k());
//			 }
			 
 			return domians;
			 
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
