package com.cyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cyc.hibernate.imp.IpHibernateImp;

@Component
public class IpService {

	@Autowired
	IpHibernateImp imp;
	
	public boolean checkip(String ip)
	{
		
		
		return false;
	}
	
}
