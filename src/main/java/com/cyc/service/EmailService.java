package com.cyc.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cyc.core.CoreControlJob;
import com.cyc.core.event.BaseEvent;
import com.cyc.core.event.DLRecordEvent;
import com.cyc.core.event.EmailEvent;
import com.cyc.core.event.EventListener;
import com.cyc.core.event.EventType;
import com.cyc.core.event.InnitDLoadEvent;
import com.cyc.core.event.PrepareDLRJob;
import com.cyc.domain.Ed2kLinkDelegator;
import com.cyc.hibernate.domain.DownLoadRecordDomian;
import com.cyc.hibernate.domain.EbookDomain;
import com.cyc.hibernate.domain.Ed2kStateDomain;
import com.cyc.hibernate.imp.DloadHibernateImp;
import com.cyc.hibernate.imp.EbookHibernateImp;
import com.cyc.hibernate.imp.Ed2kStateHibernateImp;
import com.cyc.util.JavaEmail;

/**
 * @author Administrator
 *
 */
@Component
public class EmailService implements EventListener {

	@Autowired
	DloadHibernateImp dloadHibernateImp;

	@Autowired
	Ed2kStateHibernateImp ed2kStateHibernateImp;
	@Autowired
	EbookHibernateImp ebookHibernateImp;
	
	private String monitorPath;

	private Logger log = Logger.getLogger(this.getClass().getName());

	public EmailService() {
//		regist(this);
//		new Thread(new Runnable() {
//			//初始化email服务
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//
//				while (true) {
//					try {
//						Thread.sleep(2000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					if (dloadHibernateImp != null) {
//
//						BaseEvent event = new EmailEvent(getEmailunSend());
//						event.setEvent_state(EventType.SENDEMIALS);
//						CoreControlJob.Instance().fireEvent(event);
//						break;
//
//					}
//				}
//			}
//		}).start();

	}

	/**
	 * 发送邮件
	 */
	public void SendEmail(DownLoadRecordDomian loadRecordDomian)
			throws Exception {
		// dloadHibernateImp.save(loadRecordDomian);
		if (eb2kIsUp(loadRecordDomian.getEd2k())) {
			BaseEvent event = new EmailEvent(
					getEmailunSend(loadRecordDomian.getEd2k()));
			event.setEvent_state(EventType.SENDEMIALS);
			CoreControlJob.Instance().fireEvent(event);
		}
	}

	
	
	
	public void saveAndSend(String email ,String downpath,String buycode,Ed2kStateDomain stateDomain) throws Exception
	{
		DownLoadRecordDomian domian = new DownLoadRecordDomian();
		domian.setEd2k(downpath);
		domian.setEmail1(email);
		domian.setEb_id(stateDomain.getEbid());
		domian.setPathStored(stateDomain.getFilePath());
		domian.setTimeCreated(new Date());
		dloadHibernateImp.save(domian);
		DoSendEmail(domian,downpath,buycode);
		
	}
	
	
	
	private void DoSendEmail(DownLoadRecordDomian domian, String downpath,
			String buycode)throws Exception {
		// TODO Auto-generated method stub
		EbookDomain ebookDomain= ebookHibernateImp.findByColum("eb_id", domian.getEb_id());
		
		
		if(JavaEmail.sendBuyEmailTo(domian.getEmail1(),buycode,downpath,ebookDomain))
		{
			domian.setEmail2IsSend(true);
			domian.setEmail1IsSuccessed(true);
			dloadHibernateImp.update(domian);
			
		}else{
			domian.setEmail2IsSend(false);
			domian.setEmail1IsSuccessed(false);
			dloadHibernateImp.update(domian);
		}
		
		
	}

	/**
	 * 获取数据库中未发送的邮件并且可发送的
	 * 
	 * @return
	 */
	public List<DownLoadRecordDomian> getEmailunSend() {

		String[] colums = new String[] { "email1IsSend", "email2IsSend" };
		String[] values = new String[] { "0", "0" };
		List<DownLoadRecordDomian> domians = dloadHibernateImp
				.findbyColumsOrGroupByColum(colums, values, "id");
		List<DownLoadRecordDomian> senDomians = new ArrayList<>();
		for (DownLoadRecordDomian domian : domians) {
			if (eb2kIsUp(domian.getEd2k())) {
				senDomians.add(domian);
			}

		}
		return senDomians;
	}

	/**
	 * 获取数据库中指定ed2k未发送的邮件并且可发送的(没有检测是否上传成功)
	 * 
	 * @return
	 */
	public List<DownLoadRecordDomian> getEmailunSend(String ed2k) {

		String[] colums = new String[] { "email1IsSend", "email2IsSend" };
		String[] values = new String[] { "0", "0" };
		List<DownLoadRecordDomian> domians = dloadHibernateImp
				.findbyColumsOrGroupByColum(colums, values, "id");
		List<DownLoadRecordDomian> senDomians = new ArrayList<>();
		if(!eb2kIsDown(ed2k))
			return senDomians;
		for (DownLoadRecordDomian domian : domians) {
			if (ed2k.equals(domian.getEd2k())) {
				senDomians.add(domian);
			}

		}
		return senDomians;
	}

	public boolean eb2kIsDown(String ed2k) {
		// TODO Auto-generated method stub

		return isdownloadsuccessed(ed2k);
	}

	
	/**检查指定ed2k是否上传成功
	 * @param ed2k
	 * @return
	 */
	public boolean  eb2kIsUp(String ed2k) {
		// TODO Auto-generated method stub
		if(isUploadSuccessed(ed2k))
		{
			return true;
		}
		
			return false;
	}
	
	
	@Override
	public void handelEvent(BaseEvent event) {
		// TODO Auto-generated method stub
		switch (event.getState()) {
		// 下载成功
		case EventType.UPLOADSUCCESS:
			//获取数据库中指定ed2k 未发送且ed2k已经上传的记录
			List<DownLoadRecordDomian> senddomians = getEmailunSend(event
					.getEd2kStateDomain().getEd2k());
			EmailEvent emailEvent = new EmailEvent(senddomians);
			emailEvent.setEvent_state(EventType.SENDEMIALS);
			CoreControlJob.Instance().fireEvent(emailEvent);

			break;
		case EventType.EMAIL1SENDSUCCESSED:
		case EventType.EMAIL2SENDSUCCESSED:
		case EventType.EMAIL1SENDFAILD:
		case EventType.EMAIL2SENDFAILD:

			DownLoadRecordDomian domian = ((EmailEvent) event).getDomian();

			dloadHibernateImp.update(domian);

			break;

		default:
			break;
		}
	}

	@Override
	public void regist(EventListener listener) {
		// TODO Auto-generated method stub
		CoreControlJob.Instance().regist(listener);
	}

	@Override
	public void fireEvent(BaseEvent event) {
		// TODO Auto-generated method stub
		CoreControlJob.Instance().nofied(event);

	}
	
	/** 判断指定ed2k是否上传成功
	 * @param ed2k
	 * @return
	 */
	public boolean isUploadSuccessed(String ed2k){
		
		List< Ed2kStateDomain>domains= ed2kStateHibernateImp.findByColum("ed2k", ed2k);
		if(domains!=null&&domains.size()>0&&Ed2kStateDomain.UP_SUCCESSED.equals(domains.get(0).getState_upload()))
		{
			return true;
		}
		return false;
	} 

	public boolean isdownloadsuccessed(String ed2k) {
		Ed2kStateDomain domian = ed2kStateHibernateImp.findByEd2k(ed2k);
		if (domian != null && Ed2kStateDomain.UP_SUCCESSED.equals(domian.getState()))
			return true;

		return false;
	}

	@Override
	public void onDirectoryChange(File arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDirectoryCreate(File arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDirectoryDelete(File arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFileChange(File arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFileCreate(File arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFileDelete(File arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(FileAlterationObserver arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStop(FileAlterationObserver arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getMonitorPath() {
		// TODO Auto-generated method stub
		return null;
	}

}
