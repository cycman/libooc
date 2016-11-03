package com.cyc.core;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.cyc.config.ConfigManage;
import com.cyc.config.ConfigType;
import com.cyc.core.event.BaseEvent;
import com.cyc.core.event.DLRecordEvent;
import com.cyc.core.event.EventType;
import com.cyc.core.event.UploadEvent;
import com.cyc.hibernate.domain.DownLoadRecordDomian;
import com.cyc.hibernate.domain.Ed2kStateDomain;
import com.cyc.util.OssClientUtil;
import com.mysql.fabric.xmlrpc.base.Array;

/**
 * @author Administrator 文件正在下载 监听文件列表是否存在迅雷临时下载 超过规定时间没有发现临时文件判定为下载失败
 */
public class ULoadingJob implements Job {

	private Logger logger;

	private CoreControlJob coreControlJob;

	private Ed2kStateDomain domian;

	private String monitorPath;

	private static List<String> downingfiles = new ArrayList<>();
	public static Integer threads = 4;
	static {
		threads = Integer.valueOf(ConfigManage.GLOBAL_STRING_CONFIG
				.get(ConfigType.JOB_THREAD_NUM));

	}

	public ULoadingJob(CoreControlJob job, Ed2kStateDomain domian) {
		// TODO Auto-generated constructor stub

		this.coreControlJob = job;
		this.domian = domian;
		innit();

	}

	@Override
	public void run() {
		String originFileName = null;
		synchronized (threads) {
			threads--;
		}
		try {
			if (downingfiles.contains(domian.getFilePath()))
				return;
			if (threads <= 0)
				return;
			else {
				originFileName = domian.getFilePath().split("/")[1];
				if (originFileName == null) {
					return;
				}
				String upPath = ConfigManage.GLOBAL_STRING_CONFIG
						.get(ConfigType.JOB_MONITORPATH);

				File file = new File(upPath, originFileName);
				if (!file.exists()) {
					logger.warn(file.getAbsoluteFile() + "准备上传的文件不存在");
					throw new Exception();
				}
				// 更改文件名准备上传

				File destfile = new File(upPath, originFileName + "uploading");
				file.renameTo(destfile);
			    File deleteFile = new File(upPath,originFileName+"deleting");
				if (OssClientUtil.UploadFile(destfile, domian.getFilePath())) {

					destfile.renameTo(deleteFile);

				}
				

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.warn(domian.getFilePath() + "文件上传失败");
			BaseEvent event = new UploadEvent(domian);
			event.setEvent_state(EventType.UPLOADEXCEPTION);
			coreControlJob.nofied(event);
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			logger.warn(domian.getFilePath() + "文件上传失败");
			BaseEvent event = new UploadEvent(domian);
			event.setEvent_state(EventType.UPLOADEXCEPTION);
			coreControlJob.nofied(event);
			e.printStackTrace();
		} finally {
			synchronized (threads) {
				threads++;
				downingfiles.remove(originFileName);
			}
		}

	}

	private boolean doCheckDown(String monitorPath2) {
		// TODO Auto-generated method stub
		File directory = new File(monitorPath2);
		return directory.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return ((name.startsWith(domian.getEd2k().split("\\|")[2]) && name
						.endsWith("xltd")));

			}
		}).length > 0;

	}

	@Override
	public void innit() {
		// TODO Auto-generated method stub

		logger = Logger.getLogger(this.getClass().getName());

	}

}
