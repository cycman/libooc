package com.cyc.view;

import com.cyc.core.event.BaseEvent;

public class LinkIsFreeView extends BaseView {

	private String IsFree;

	
	public LinkIsFreeView(String IsFree) {
		// TODO Auto-generated constructor stub
		this.IsFree =IsFree;
	}
	public  String getIsFree() {
		return this.IsFree;
	}

	public  void setIsFree(String IsFree) {
		this.IsFree = IsFree;
	}
	
}
