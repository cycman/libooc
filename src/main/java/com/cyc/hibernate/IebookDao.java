package com.cyc.hibernate;

import com.cyc.domain.EbookDomain;

public interface IebookDao {

	//colum eid
	public static String EBOOK_COLUM_EID="eid";
	//colum md5
	public static String EBOOK_COLUM_MD5="md5";
	//colum id
	public static String EBOOK_COLUM_ID="id";
	
	
	EbookDomain findByColum(String colum,Object  params);
	
	
	
	
}
