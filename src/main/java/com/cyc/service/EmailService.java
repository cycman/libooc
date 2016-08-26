package com.cyc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cyc.core.CoreControlJob;
import com.cyc.core.event.BaseEvent;
import com.cyc.core.event.DLRecordEvent;
import com.cyc.core.event.EventListener;
import com.cyc.core.event.EventType;
import com.cyc.core.event.InnitDLoadEvent;
import com.cyc.core.event.PrepareDLRJob;
import com.cyc.event.EmailEvent;
import com.cyc.hibernate.domain.DownLoadRecordDomian;
import com.cyc.hibernate.domain.Ed2kStateDomain;
import com.cyc.hibernate.imp.DloadHibernateImp;
import com.cyc.hibernate.imp.Ed2kStateHibernateImp;

@Component
public class EmailService implements EventListener {

	@Autowired
	DloadHibernateImp dloadHibernateImp;

	@Autowired
	Ed2kStateHibernateImp ed2kStateHibernateImp;

	private Logger log = Logger.getLogger(this.getClass().getName());

	public EmailService() {
		regist(this);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				while (true) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (dloadHibernateImp != null) {

						BaseEvent event = new EmailEvent(getEmailunSend());
						event.setEvent_state(EventType.SENDEMIALS);
						CoreControlJob.Instance().fireEvent(event);
						break;

					}
				}
			}
		}).start();

	}

	/**
	 * 发送邮件
	 */
	public void SendEmail(DownLoadRecordDomian loadRecordDomian)
			throws Exception {
		dloadHibernateImp.save(loadRecordDomian);
		DLRecordEvent event = null;
		Ed2kStateDomain domain = ed2kStateHibernateImp
				.findByEd2k(loadRecordDomian.getEb2k());
		if (domain == null) {

			domain = new Ed2kStateDomain(loadRecordDomian.getEb_id(),
					loadRecordDomian.getEb2k(), "未下载");
			domain.setEbid(loadRecordDomian.getEb_id());
			ed2kStateHibernateImp.save(domain);
			event = new DLRecordEvent(EventType.DLRECORDSTORED, domain);
			fireEvent(event);
		} else {
			if ("下载失败".equals(domain.getState())
					|| "未下载".equals(domain.getState())) {
				event = new DLRecordEvent(EventType.DLRECORDSTORED, domain);
				fireEvent(event);
			}
		}

	}

	/**
	 * 获取数据库中未发送的邮件并且可发送的
	 * @return
	 */
	public List<DownLoadRecordDomian> getEmailunSend() {
		
		String[] colums = new String[] { "email1IsSend", "email2IsSend"  };
		String[] values = new String[] { "0","0" };
		List<DownLoadRecordDomian>domians= dloadHibernateImp.findbyColumsOrGroupByColum(colums, values,"id");
		List<DownLoadRecordDomian>senDomians=new ArrayList<>();
		for(DownLoadRecordDomian domian: domians)
		{
			if(eb2kIsDown(domian.getEb2k())){
				senDomians.add(domian);
			}
			
		}
		return senDomians;
	}
	/**
	 * 获取数据库中指定ed2k未发送的邮件并且可发送的
	 * @return
	 */
	public List<DownLoadRecordDomian> getEmailunSend(String ed2k) {
		
		String[] colums = new String[] { "email1IsSend", "email2IsSend"  };
		String[] values = new String[] { "0","0" };
		List<DownLoadRecordDomian>domians= dloadHibernateImp.findbyColumsOrGroupByColum(colums, values,"id");
		List<DownLoadRecordDomian>senDomians=new ArrayList<>();
		for(DownLoadRecordDomian domian: domians)
		{
			if(ed2k.equals(domian.getEb2k())){
				senDomians.add(domian);
			}
			
		}
		return senDomians;
	}

	 

	public boolean eb2kIsDown(String ed2k) {
		// TODO Auto-generated method stub

		return isdownloadsuccessed(ed2k);
	}

	@Override
	public void handelEvent(BaseEvent event) {
		// TODO Auto-generated method stub
		switch (event.getState()) {
		 //下载成功
		case EventType.DOWNLOADSUCCESS:
				List<DownLoadRecordDomian> senddomians =getEmailunSend(event.getEd2kStateDomain().getEd2k());
				EmailEvent emailEvent = new EmailEvent(senddomians);
				emailEvent.setEvent_state(EventType.SENDEMIALS);
				CoreControlJob.Instance().fireEvent(emailEvent);

			break;
		case EventType.EMAIL1SENDSUCCESSED :
		case EventType.EMAIL2SENDSUCCESSED :
		case EventType.EMAIL1SENDFAILD :
		case EventType.EMAIL2SENDFAILD :	
		
			DownLoadRecordDomian domian= ((EmailEvent)event).getDomian();
			 
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

	public boolean isdownloadsuccessed(String ed2k) {
		Ed2kStateDomain domian = ed2kStateHibernateImp.findByEd2k(ed2k);
		if (domian != null && "下载成功".equals(domian.getState()))
			return true;

		return false;
	}

}
