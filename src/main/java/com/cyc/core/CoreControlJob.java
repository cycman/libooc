package com.cyc.core;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.inject.Instance;

import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.logging.Log;

import antlr.debug.Event;

import com.cyc.config.ConfigManage;
import com.cyc.core.event.BaseEvent;
import com.cyc.core.event.EmailEvent;
import com.cyc.core.event.EventListener;
import com.cyc.core.event.EventType;
import com.cyc.core.event.InnitDLoadEvent;
import com.cyc.core.event.UploadEvent;
import com.cyc.hibernate.domain.DownLoadRecordDomian;
import com.cyc.hibernate.domain.Ed2kStateDomain;
import com.cyc.util.JavaEmail;
import com.mysql.fabric.xmlrpc.base.Array;

public class CoreControlJob extends Thread implements EventListener {

	private Logger log;

	private  ZJPFileMonitor monitor;
	// 事件列表
	private static List<BaseEvent> events = new ArrayList<>();
	// 待刪除時間列表
	private static List<BaseEvent> deltesEvents = new ArrayList<>();

	// 唯一实例
	private static CoreControlJob controlJob = new CoreControlJob();

	// 下载job最大数目
	public static int MAX_DLOAD_ITEMS = 10;

	private static ExecutorService jobs_fixedThreadPool = Executors
			.newCachedThreadPool();;
	// 上传job最大数目

	public static int MAX_ULOAD_ITEMS = 10;

	// 邮箱job最大数目

	public static int MAX_EMAIL_ITEMN = 10;

	private static List<EventListener> listeners = new ArrayList<>();

	private static DLRJob mainJob;

	private static EmailJob emailJob;

 
	
	private int monitorTime;
	public void regist(EventListener listener) {
		if (!listeners.contains(this)) {
			listeners.add(this);
		}

		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
		if(monitor!=null)
		{
			if(listener.getMonitorPath()!=null)
			monitor.monitor( listener.getMonitorPath(), listener);
		}
		 
	}

	public void fireEvent(BaseEvent event) {

		for (EventListener listener : listeners) {
			listener.handelEvent(event);
		}
	}

	private CoreControlJob() {
		synchronized (CoreControlJob.class) {
			innit();
			log.info("核心控制器正在初始化");
			mainJob = new DLRJob(this);
			emailJob = new EmailJob(this);
			
			innitStartJob();
			this.start();
			try {
				monitor.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			log.info("核心控制器初始化完毕");
		}
	}

	public static CoreControlJob Instance() {

		if (controlJob != null)
			return controlJob;

		return new CoreControlJob();
	}

	@Override
	public synchronized void start() {
		// TODO Auto-generated method stub

		super.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {

			try {
				synchronized (events) {
					if (events != null && events.size() > 0) {
						log.info("fireevent" + events.get(0).getState());
						fireEvent(events.get(0));
						removeEvent(events.get(0));

					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.log(Level.WARNING, "fireevent" + events.get(0).getState());
				e.printStackTrace();
			} finally {
				if (events != null && events.size() > 0) {
					removeEvent(events.get(0));
				}

			}

		}

	}

	private void addremoveEvent(BaseEvent event) {
		// TODO Auto-generated method stub
		synchronized (deltesEvents) {

			if (deltesEvents != null) {
				deltesEvents.add(event);
			}
		}
	}

	/**
	 * 初始化线程
	 */
	private void innitStartJob() {
		// TODO Auto-generated method stub
		// dload_fixedThreadPool.

	}

	/**
	 * 数据准备
	 */
	private void innit() {
		// TODO Auto-generated method stub
		// log準備
		log = Logger.getLogger(this.getClass().getName());
 		monitorTime = Integer.valueOf(ConfigManage.GLOBAL_STRING_CONFIG.get("job_monitor_time"));
		try {
			log.info("corecontroljob  monitor 开始初始化");
			monitor = new ZJPFileMonitor(monitorTime);
			log.info("corecontroljob  monitor 开始初完毕");

			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	       

	}

	public void nofied(BaseEvent event) {
		// TODO Auto-generated method stub
		// addEvent(event);
		fireEvent(event);

	}

	private void removeEvent(BaseEvent event) {
		// TODO Auto-generated method stub
		synchronized (events) {
			if (events != null && events.contains(event)) {

				events.remove(event);

			}
		}
	}

	private void addEvent(BaseEvent event) {
		// TODO Auto-generated method stub

		if (events != null) {
			synchronized (events) {

				events.add(event);
			}

		}

	}

	@Override
	public void handelEvent(BaseEvent event) {
		// TODO Auto-generated method stub
		switch (event.getEvent_state()) {
		// 准备进行下载处理
		case EventType.PREPAREDLR:
			if (mainJob != null)
				mainJob.addjob(event.getEd2kStateDomain());
			else {
				mainJob = new DLRJob(this);
				mainJob.addjob(event.getEd2kStateDomain());
			}

			break;
 
			
		case EventType.UPLOADLIST:
			for(Ed2kStateDomain domain: ((UploadEvent)event).getDomians())
			jobs_fixedThreadPool.submit(new ULoadingJob(this, domain));
			
			break;
			
		case EventType.UPLOADDOMAIN:
			jobs_fixedThreadPool.submit(new ULoadingJob(this, event
					.getEd2kStateDomain()));
		     break;
			
		case EventType.INNITDLOAD:
			InnitDLoadEvent event2 = (InnitDLoadEvent) event;
			for (Ed2kStateDomain domian : event2.getDomians())
				if (mainJob != null)
					mainJob.addjob(domian);
				else {
					mainJob = new DLRJob(this);
					mainJob.addjob(domian);
				}
			break;
		case EventType.SENDEMIALS:
			EmailEvent event3 = (EmailEvent) event;
			for (DownLoadRecordDomian domian : event3.getDomians())
				if (emailJob != null)
					emailJob.addjob(domian);
				else {
					emailJob = new EmailJob(this);
					emailJob.addjob(domian);
				}

			break;

		default:
			break;
		}

	}

 

	class EmailJob extends Thread {
		private CoreControlJob coreContrl;
		private List<DownLoadRecordDomian> jobs = new ArrayList<>(
				coreContrl.MAX_EMAIL_ITEMN);

		public EmailJob(CoreControlJob coreControlJob) {
			// TODO Auto-generated constructor stub
			this.coreContrl = coreControlJob;
			this.start();
		}

		public void addjob(DownLoadRecordDomian job) {
			// TODO Auto-generated method stub
			synchronized (jobs) {
				if (jobs.contains(job))
					return;
				jobs.add(job);
				jobs.notifyAll();
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				synchronized (jobs) {
					DownLoadRecordDomian recordDomian = null;
					try {
						if (jobs.size() <= 0)
							jobs.wait();
						recordDomian = jobs.get(0);
						if (recordDomian.getEmail1() != null
								&& !recordDomian.isEmail1IsSend()) {
							log.info("准备发送" + recordDomian.getEd2k()
									+ "到email1" + recordDomian.getEmail1());
							DoSendEmail(recordDomian.getEd2k(),
									recordDomian.getEmail1());
							recordDomian.setEmail1IsSend(true);
							EmailEvent event = new EmailEvent(recordDomian);
							event.setEvent_state(EventType.EMAIL1SENDSUCCESSED);
							log.info("发送成功" + recordDomian.getEd2k()
									+ "到email1" + recordDomian.getEmail1());
							recordDomian.setEmail1IsSend(true);
							controlJob.nofied(event);

						}
						if (recordDomian.getEmail2() != null
								&& !recordDomian.isEmail2IsSend()) {
							log.info("准备发送" + recordDomian.getEd2k()
									+ "到email2" + recordDomian.getEmail2());

							DoSendEmail(recordDomian.getEd2k(),
									recordDomian.getEmail2());
							EmailEvent event = new EmailEvent(recordDomian);
							event.setEvent_state(EventType.EMAIL2SENDSUCCESSED);
							log.info("发送成功" + recordDomian.getEd2k()
									+ "到email2" + recordDomian.getEmail2());
							recordDomian.setEmail2IsSend(true);

							controlJob.nofied(event);

						}

						// event.setEvent_state(EventType.DOWONINGUNCHECK);
						// event.setEd2kStateDomain(recordDomian);
						// log.info(recordDomian.getEd2k()+"下载中");

					} catch (Exception e) {
						log.warning("发生了未知异常导致了email发送失败"
								+ recordDomian.getEd2k()
								+ recordDomian.getEmail1()
								+ recordDomian.getEmail2());
						// TODO Auto-generated catch block
						// EmailEvent event = new
						// EmailEvent(((EmailEvent)e).getDomians());
						BaseEvent event = new BaseEvent();
						event.setEvent_state(EventType.EMAILFAILD);
						// event.setEd2kStateDomain(recordDomian);
						controlJob.nofied(event);
						e.printStackTrace();
					} finally {
						if (recordDomian != null)
							jobs.remove(0);
					}

				}

			}

		}

		/**
		 * 发送邮件
		 * 
		 * @param ed2k
		 * @param email
		 * @throws Exception
		 */
		private void DoSendEmail(String ed2k, String email) throws Exception {
			// TODO Auto-generated method stub
			try {
				JavaEmail.Doemail();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.warning("发送失败" + ed2k + "===========" + "email");
				throw e;
			}

		}

	}

	class DLRJob extends Thread {
		private CoreControlJob coreContrl;
		private List<Ed2kStateDomain> jobs = new ArrayList<>(
				coreContrl.MAX_DLOAD_ITEMS);

		public DLRJob(CoreControlJob coreControlJob) {
			// TODO Auto-generated constructor stub
			this.coreContrl = coreControlJob;
			this.start();
		}

		public void addjob(Ed2kStateDomain job) {
			// TODO Auto-generated method stub
			synchronized (jobs) {
				if (jobs.contains(job))
					return;
				jobs.add(job);
				jobs.notifyAll();
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				synchronized (jobs) {
					Ed2kStateDomain recordDomian = null;
					try {
						if (jobs.size() <= 0)
							jobs.wait();
						recordDomian = jobs.get(0);
						log.info("准备下载" + recordDomian.getEd2k());
						sleep(2000);
						dodownload(recordDomian.getEd2k());
						sleep(5000);						 
						log.info(recordDomian.getEd2k() + "下载中");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						BaseEvent event = new BaseEvent();
						event.setEvent_state(EventType.DOWOFAILD);
						event.setEd2kStateDomain(recordDomian);
						controlJob.nofied(event);
						e.printStackTrace();
					} finally {
						if (recordDomian != null)
							jobs.remove(0);
					}

				}

			}

		}

		private void dodownload(String eb2k) throws InterruptedException {
			// TODO Auto-generated method stub
			Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();
			// TODO Auto-generated method stub
			Thread.sleep(2000);
			Transferable t = new StringSelection(eb2k);
			sysc.setContents(t, null);

		}

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
