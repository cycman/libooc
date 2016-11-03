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
import com.cyc.hibernate.domain.Ed2kStateDomain;
import com.cyc.hibernate.imp.DloadHibernateImp;
import com.cyc.hibernate.imp.Ed2kStateHibernateImp;
import com.cyc.util.OssClientUtil;

@Component
public class UpLoadService implements EventListener {

	@Autowired
	DloadHibernateImp dloadHibernateImp;

	@Autowired
	Ed2kStateHibernateImp ed2kStateHibernateImp;

	private Logger logger = Logger.getLogger(this.getClass().getName());

	public UpLoadService() {
		//regist(this);
//		new Thread(new Runnable() {
//
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
//						UploadEvent event = new UploadEvent(
//								getUnUploadAndUseful());
//						CoreControlJob.Instance().fireEvent(event);
//						break;
//
//					}
//				}
//			}
//		}).start();

	}

	public List<Ed2kStateDomain> getUnUploadAndUseful() {
		String[] colums = new String[] { "state_upload", "state_upload",
				"state_upload" };
		String[] values = new String[] { "未上传", "上传失败", "正在上传" };

		List<Ed2kStateDomain> domains = ed2kStateHibernateImp.findByColumsOr(
				colums, values);

		List<Ed2kStateDomain> resultDomains = new ArrayList<>();
		for (Ed2kStateDomain domain : domains) {
			if (checkIsDLoad(domain.getEd2k()))
				resultDomains.add(domain);

		}
		return resultDomains;
	}

	/**
	 * 检查指定ed2k是否下载成功
	 * 
	 * @param ed2k
	 * @return
	 */
	private boolean checkIsDLoad(String ed2k) {
		// TODO Auto-generated method stub
		Ed2kStateDomain domian = ed2kStateHibernateImp.findByEd2k(ed2k);
		if (domian != null && Ed2kStateDomain.UP_SUCCESSED.equals(domian.getState()))
			return true;

		return false;
	}

	@Override
	public void handelEvent(BaseEvent event) {
		// TODO Auto-generated method stub
		switch (event.getState()) {

		case EventType.DOWNLOADSUCCESS:
			if (checkIsCanUping(event.getEd2kStateDomain().getEd2k())) {
				// 通知单个上传事件
				if (!OSSIsSuccessed(event.getEd2kStateDomain())) {
					event.setEvent_state(EventType.UPLOADDOMAIN);
					fireEvent(event);
				}
			}
			break;

		// 检查是否上传成功
		case EventType.CHECKUPLOAD:
		case EventType.UPLOADEXCEPTION:
			if (OSSIsSuccessed(event.getEd2kStateDomain())) {
				event.getEd2kStateDomain().setState_upload(Ed2kStateDomain.UP_SUCCESSED);
				ed2kStateHibernateImp.update(event.getEd2kStateDomain());
				event.setEvent_state(EventType.UPLOADSUCCESS);
				fireEvent(event);
			} else {
				event.getEd2kStateDomain().setState_upload(Ed2kStateDomain.UP_FAILED);
				ed2kStateHibernateImp.update(event.getEd2kStateDomain());
				event.setEvent_state(EventType.UPLOADFAILD);
				fireEvent(event);
			}

			break;

		default:
			break;
		}
	}

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
	}

	@Override
	public void onFileCreate(File arg0) {
		// 文件不存在就返回
		if (!arg0.exists())
			return;
		// TODO Auto-generated method stub
		if (arg0.getAbsolutePath().endsWith("uploading")) {
			if (arg0.getName().split("\\.").length >= 2) {
				String ed2k = arg0.getName().split("\\.")[0];
				logger.info("检测到" + ed2k + "正在上传");
				List<Ed2kStateDomain> domains = ed2kStateHibernateImp
						.findByColumLike("ed2k", ed2k);
				if (domains == null || domains.size() == 0) {
					return;
				}
				Ed2kStateDomain domain = domains.get(0);
				if (!domain.getEd2k().contains(ed2k)) {
					logger.warning("检测错误" + ed2k);
					return;
				}
				if(OssClientUtil.isUp(domain.getFilePath()))
				{
					domain.setState_upload(Ed2kStateDomain.UP_SUCCESSED);
					ed2kStateHibernateImp.update(domain);
					BaseEvent event = new BaseEvent();
					event.setEd2kStateDomain(domain);
					// 通知事件正在上传
					event.setEvent_state(EventType.UPLOADSUCCESS);
					arg0.delete();
					fireEvent(event);
					return;
				}
				domain.setState_upload("正在上传");
				ed2kStateHibernateImp.update(domain);
				BaseEvent event = new BaseEvent();
				event.setEd2kStateDomain(domain);
				// 通知事件正在上传
				event.setEvent_state(EventType.UPLOADING);
				fireEvent(event);

			}
		}

		if (arg0.getAbsolutePath().endsWith("deleting")) {
			if (arg0.getName().split("\\.").length >= 2) {
				String ed2k = arg0.getName().split("\\.")[0];
				logger.info("检测到" + ed2k + "正在上传");
				List<Ed2kStateDomain> domains = ed2kStateHibernateImp
						.findByColumLike("ed2k", ed2k);
				if (domains == null || domains.size() == 0) {
					return;
				}
				Ed2kStateDomain domain = domains.get(0);
				if (!domain.getEd2k().contains(ed2k)) {
					logger.warning("检测错误" + ed2k);
					return;
				}
				domain.setState_upload(Ed2kStateDomain.UP_SUCCESSED);
				ed2kStateHibernateImp.update(domain);
				BaseEvent event = new BaseEvent();
				event.setEd2kStateDomain(domain);
				// 通知事件正在下载
				event.setEvent_state(EventType.UPLOADSUCCESS);
				// 删除下载
				arg0.delete();
				fireEvent(event);

			}
		}

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
		return ConfigManage.GLOBAL_STRING_CONFIG.get("job_monitorpath");
	}

	public Ed2kStateDomain getDownlink(String ebid) {
		// TODO Auto-generated method stub
		
		
		
		
		return null;
	}

}
