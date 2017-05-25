package com.cyc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cyc.hibernate.domain.BuyPermitDomain;
import com.cyc.hibernate.imp.BuyPermitHibernateImp;

@Component
public class BuyService {

	@Autowired
	private BuyPermitHibernateImp buyPermitHibernateImp;

	public boolean CodeIsUseful(String code,String ebid)
	{
		List<BuyPermitDomain> domains = buyPermitHibernateImp.findbyColum("code", code);
		if(code!=null&&domains!=null&&domains.size()>0)
		{
			for(BuyPermitDomain domain: domains)
			{
				if(code.equals(domain.getCode())&&domain.getState_use().equals(BuyPermitDomain.CODE_USEFUL))
				{
					return true;
				}else if(code.equals(domain.getCode())&&domain.getState_use().equals(BuyPermitDomain.CODE_USEED)&&domain.getEbid().equals(ebid))
				{
					return true;		
				}
			}
		}
		return false;
	}

	public void buy(String buycode, String ebid) {
		// TODO Auto-generated method stub
		List<BuyPermitDomain> domains = buyPermitHibernateImp.findbyColum("code", buycode);
		domains.get(0).setEbid(ebid);
		domains.get(0).setState_use(BuyPermitDomain.CODE_USEED);
		buyPermitHibernateImp.update(domains.get(0));
	}
}
