package com.cyc.view;

import com.cyc.domain.EbookDomain;
import com.cyc.domain.EbookDomianDelegator;

public class EbookView extends BaseView {

	
	//EbookDomain 类的封装类
	private EbookDomianDelegator delegato;

	public EbookView(EbookDomain ebookDomain) throws Exception{
		// TODO Auto-generated constructor stub
		 
			this.delegato=new EbookDomianDelegator(ebookDomain);
		 
	}

	public EbookDomianDelegator getDelegato() {
		return delegato;
	}

	public void setDelegato(EbookDomianDelegator delegator) {
		this.delegato = delegator;
	}
	
	
	
}
