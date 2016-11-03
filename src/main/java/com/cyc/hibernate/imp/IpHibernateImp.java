package com.cyc.hibernate.imp;

import java.util.Date;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.cyc.hibernate.core.BaseHibernateDAOImp;
import com.cyc.hibernate.domain.IpDomain;

@Component
public class IpHibernateImp extends BaseHibernateDAOImp<IpDomain> {

	
	public IpDomain  findByIp(String ip) throws Exception
	{
		IpDomain domain = null;
		Session session = getSession();
		domain= (IpDomain) session.get(IpDomain.class, ip);
		session.close();
		return domain;
		
	}
	
	public void ClearIp(IpDomain domain)
	{
		Date date = new Date();
		if( ! (date.getDay()==domain.getDate().getDay()))
		{
			domain.setDownTimes(0);
			update(domain);
		}
		
		
	}
	
}
