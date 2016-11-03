package com.cyc.view;

import com.cyc.domain.Ed2kLinkDelegator;
import com.cyc.hibernate.domain.Ed2kStateDomain;

public class Ed2kLinkView extends BaseView{

	private Ed2kLinkDelegator ed2kLinkDelegator;
	
	
	public Ed2kLinkView(Ed2kLinkDelegator ed2kStateDomain) {
		// TODO Auto-generated constructor stub
		this.ed2kLinkDelegator = ed2kStateDomain;
	}


	public Ed2kLinkDelegator getEd2kLinkDelegator() {
		return ed2kLinkDelegator;
	}


	public void setEd2kLinkDelegator(Ed2kLinkDelegator ed2kLinkDelegator) {
		this.ed2kLinkDelegator = ed2kLinkDelegator;
	}
	
	

}
