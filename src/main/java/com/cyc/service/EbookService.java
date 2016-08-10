package com.cyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cyc.hibernate.EbookHibernateImp;

@Component
public class EbookService {

	@Autowired
	private EbookHibernateImp ebookHibernateImp;
	
	
	
	
}
