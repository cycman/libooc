package com.cyc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cyc.core.CoreControlJob;
import com.cyc.core.event.BaseEvent;
import com.cyc.core.event.DLRecordEvent;
import com.cyc.core.event.EventListener;
import com.cyc.core.event.EventType;
import com.cyc.core.event.InnitDLoadEvent;
import com.cyc.core.event.PrepareDLRJob;
import com.cyc.hibernate.domain.DownLoadRecordDomian;
import com.cyc.hibernate.domain.Ed2kStateDomain;
import com.cyc.hibernate.imp.DloadHibernateImp;
import com.cyc.hibernate.imp.Ed2kStateHibernateImp;

@Component
public class DLoadService implements EventListener {

	@Autowired
	DloadHibernateImp dloadHibernateImp;

	@Autowired
	Ed2kStateHibernateImp ed2kStateHibernateImp;

	public DLoadService() {
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

						BaseEvent event = new InnitDLoadEvent(getundownload());
						CoreControlJob.Instance().fireEvent(event);
						break;

					}
				}
			}
		}).start();

	}

	/**
	 * 添加下载记录
	 */
	public void addDLoadRecord(DownLoadRecordDomian loadRecordDomian)
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
			if("下载失败".equals(domain.getState())||"未下载".equals(domain.getState()))
			{
				event = new DLRecordEvent(EventType.DLRECORDSTORED, domain);
				fireEvent(event);
			}
			if("下载成功".endsWith(domain.getState()))
			{
				event = new DLRecordEvent(EventType.DOWNLOADSUCCESS, domain);
				fireEvent(event);
			}
		}

	}

	public List<Ed2kStateDomain> getundownload() {
		String[] colums = new String[] { "state", "state", "state","state"};
		String[] values = new String[] { "准备下载", "未下载", "下载失败","正在下载" };
		return ed2kStateHibernateImp.findByColumsOr(colums, values);

	}

	/**
	 * 更新下载记录
	 */
	public void updateDLoadRecord(DownLoadRecordDomian loadRecordDomian)
			throws Exception {

		dloadHibernateImp.update(loadRecordDomian);
	}

	public boolean eb2kIsDown() {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public void handelEvent(BaseEvent event) {
		// TODO Auto-generated method stub
		switch (event.getState()) {
		// 下载记录已经被储存
		case EventType.DLRECORDSTORED:
			PrepareDLRJob dlrJob = new PrepareDLRJob();
			dlrJob.setEvent_state(EventType.PREPAREDLR);
			if(!isdownloadsuccessed(event.getEd2kStateDomain().getEd2k())){
			dlrJob.setEd2kStateDomain(event.getEd2kStateDomain());
			fireEvent(dlrJob);
		}
			break;
		case EventType.DOWONING:
			event.getEd2kStateDomain().setState("正在下载");
			if(!isdownloadsuccessed(event.getEd2kStateDomain().getEd2k()))
			ed2kStateHibernateImp.update(event.getEd2kStateDomain());

			break;
		case EventType.DOWNLOADSUCCESS:
			event.getEd2kStateDomain().setState("下载成功");
			if(!isdownloadsuccessed(event.getEd2kStateDomain().getEd2k()))
			ed2kStateHibernateImp.update(event.getEd2kStateDomain());

			break;
		case EventType.DOWOFAILD:
			event.getEd2kStateDomain().setState("下载失败");
			if(!isdownloadsuccessed(event.getEd2kStateDomain().getEd2k()))
			ed2kStateHibernateImp.update(event.getEd2kStateDomain());

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
	
	public boolean isdownloadsuccessed(String ed2k)
	{
		Ed2kStateDomain domian= ed2kStateHibernateImp.findByEd2k(ed2k);
		if(domian!=null&&"下载成功".equals( domian.getState()))
			return true;
		
		return false;
	}
	

}
