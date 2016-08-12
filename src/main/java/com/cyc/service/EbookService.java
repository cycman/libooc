package com.cyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cyc.domain.EbookDomain;
import com.cyc.exception.MyException;
import com.cyc.hibernate.EbookHibernateImp;

@Component
public class EbookService {

	@Autowired
	private EbookHibernateImp ebookHibernateImp;

	public  EbookDomain findbyEid(String ebid) throws Exception {
		// TODO Auto-generated method stub
		if(!isNumeric(ebid))
		{
			MyException exception= new MyException(new Exception(), getClass());
			exception.setERROR_CODE(MyException.ERROR_EBID_NOT_NUM);
			throw exception;
		}
		
		EbookDomain domain=	ebookHibernateImp.findByColum(EbookHibernateImp.EBOOK_COLUM_EID, ebid);	
		return domain;
	}
	
	public static boolean isNumeric(String str){  
		  for (int i = str.length();--i>=0;){    
		   if (!Character.isDigit(str.charAt(i))){  
		    return false;  
		   }  
		  }  
		  return true;  
		}  
	
	
}
