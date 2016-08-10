package com.cyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cyc.domain.EbookDomain;
import com.cyc.hibernate.EbookHibernateImp;

@Component
public class EbookService {

	@Autowired
	private EbookHibernateImp ebookHibernateImp;

	public  EbookDomain findbyEid(String ebid) {
		// TODO Auto-generated method stub
		
		EbookDomain domain=	ebookHibernateImp.findByColum(EbookHibernateImp.EBOOK_COLUM_EID, ebid);	
		return domain;
	}
	
	
	
	
}
