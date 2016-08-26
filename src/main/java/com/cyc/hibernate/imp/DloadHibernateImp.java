package com.cyc.hibernate.imp;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.cyc.exception.MyException;
import com.cyc.hibernate.core.BaseHibernateDAOImp;
import com.cyc.hibernate.dao.IDloadRecordDao;
import com.cyc.hibernate.domain.DownLoadRecordDomian;
import com.cyc.hibernate.domain.EbookDomain;

@Component
public class DloadHibernateImp extends BaseHibernateDAOImp<DownLoadRecordDomian> implements IDloadRecordDao {

	@Override
	public void save(DownLoadRecordDomian domian)
	{
		 Session session = getSession();
		 domian.setId( (Integer) session.save(domian));
	     session.close();
		
	}
	
	public List<DownLoadRecordDomian> findbyColumOr(String colum, String value)
	{
		Session session=null;
		try {
			String hql = "from DLoadRecord as dl where 1=0";
			
			session = getSession();
			 
 				hql+= " or  "+colum+"= \'"+value+"\'";
			 
			 hql+= " group by "+colum;
			
			 List<DownLoadRecordDomian>domians =session.createQuery(hql).list();
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
	
	public  List<DownLoadRecordDomian> findbyColumsOrGroupByColum(String[] colums,String[] values,String colum)
	{
		Session session=null;
		try {
			String hql = "from DLoadRecord as dl where 1=0 ";
			
			session = getSession();
			if(values!=null&&colums!=null&&values.length==colums.length)
			{
				for(int i=0;i<colums.length;i++)
				hql+= " or  "+colums[i]+"= \'"+values[i]+"\'";
				
			}
			 hql+= " group by "+colum;
			
			 List<DownLoadRecordDomian>domians =session.createQuery(hql).list();
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


	public void updateAllByColum(String updateculum, String updatevalue,String wherecolum,String wherevalue) {
		// TODO Auto-generated method stub
		updateColumbyhql(updateculum,updatevalue,wherecolum,wherevalue);
	}


	private void updateColumbyhql(String colum,
			String value, String wherecolum, String wherevalue) {
		// TODO Auto-generated method stub
		Session session=null;
		try {
			session = getSession();
			String hql = "update DLoadRecord d set d."+colum+" = ?"
					+ "where d."+wherecolum+" = ?";
			Query q = session.createQuery(hql);			
			
			 
			 q.setString(0, value);
 			 q.setString(1, wherevalue);
			 q.executeUpdate();			 
			 
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

	public List<DownLoadRecordDomian> findbyColumsAndGroupByColum(
			String[] colums, String[] values, String colum) {
		// TODO Auto-generated method stub

		Session session=null;
		try {
			String hql = "from DLoadRecord as dl where 1=1";
			
			session = getSession();
			if(values!=null&&colums!=null&&values.length==colums.length)
			{
				for(int i=0;i<colums.length;i++)
				hql+= " and  "+colums[i]+"= \'"+values[i]+"\'";
				
			}
			 hql+= " group by "+colum;
			
			 List<DownLoadRecordDomian>domians =session.createQuery(hql).list();
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
