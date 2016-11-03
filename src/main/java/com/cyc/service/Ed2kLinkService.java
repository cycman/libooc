package com.cyc.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import antlr.debug.Event;

import com.cyc.config.ConfigManage;
import com.cyc.core.CoreControlJob;
import com.cyc.core.event.BaseEvent;
import com.cyc.core.event.DLRecordEvent;
import com.cyc.core.event.EventListener;
import com.cyc.core.event.EventType;
import com.cyc.core.event.InnitDLoadEvent;
import com.cyc.core.event.PrepareDLRJob;
import com.cyc.core.event.UploadEvent;
import com.cyc.hibernate.domain.DownLoadRecordDomian;
import com.cyc.hibernate.domain.EbookDomain;
import com.cyc.hibernate.domain.Ed2kStateDomain;
import com.cyc.hibernate.imp.DloadHibernateImp;
import com.cyc.hibernate.imp.EbookHibernateImp;
import com.cyc.hibernate.imp.Ed2kStateHibernateImp;
import com.cyc.util.OssClientUtil;

@Component
public class Ed2kLinkService  {

 

	@Autowired
	Ed2kStateHibernateImp ed2kStateHibernateImp;
	@Autowired
	EbookHibernateImp ebookHibernateImp;

 

 

	/**
	 * 检测在oss服务器上是否存在指定的文件
	 * 
	 * @param ed2kStateDomain
	 * @return
	 */
	private boolean OSSIsSuccessed(Ed2kStateDomain ed2kStateDomain) {
		// TODO Auto-generated method stub
		String path = ed2kStateDomain.getFilePath();
		return OssClientUtil.isUp(path);
	}

	/**
	 * 检查指定的ed2k是否是允许上传的
	 * 
	 * @param ed2k
	 * @return
	 */
	public boolean checkIsCanUping(String ed2k) {
		// 首先检测是否下载成功
		if (!isdownloadsuccessed(ed2k))
			return false;
		// 检测是否为可上传状态
		List<Ed2kStateDomain> domains = ed2kStateHibernateImp.findByColum(
				"ed2k", ed2k);
		if (domains != null && domains.size() > 0) {
			String state = domains.get(0).getState_upload();
			if ("上传失败".equals(state) || "未上传".equals(state))
				return true;
		}
		return false;
	}

	 

	public boolean isdownloadsuccessed(String ed2k) {
		Ed2kStateDomain domian = ed2kStateHibernateImp.findByEd2k(ed2k);
		if (domian != null && "下载成功".equals(domian.getState()))
			return true;

		return false;
	}

 	
 

	public Ed2kStateDomain storedAndFind(String ebid) throws Exception {
		// TODO Auto-generated method stub
		List<Ed2kStateDomain> domains = ed2kStateHibernateImp.findByColum("ebid", ebid);
		Ed2kStateDomain domain = null  ;
		if(domains==null||domains.size()==0)
		{
			domain =new Ed2kStateDomain();
			EbookDomain domain2= ebookHibernateImp.findByColum("eb_id", ebid);
			domain.setAuthors(domain2.getAuthors());
			domain.setEbid(domain2.getEb_ID()+"");
			domain.setExtension(domain2.getExtension());
			domain.setFilePath(domain2.getURL());
			domain.setTitle(domain2.getTitle());
			domain.setEd2k(domain2.Ed2k());
			if(!OssClientUtil.isUp(domain.getFilePath()))
			domain.setState_upload(Ed2kStateDomain.UP_NOT);
			else {
				domain.setState_upload(Ed2kStateDomain.UP_SUCCESSED);
			}
			ed2kStateHibernateImp.save(domain);
		}
		if(domains.size()>0)
		domain= domains.get(0);
		if(!OssClientUtil.isUp(domain.getFilePath()))
			domain.setState_upload(Ed2kStateDomain.UP_NOT);
			else {
				domain.setState_upload(Ed2kStateDomain.UP_SUCCESSED);
			}
			ed2kStateHibernateImp.update(domain);
		return domain;
	}

}
