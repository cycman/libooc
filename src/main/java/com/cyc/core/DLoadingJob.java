package com.cyc.core;

import java.io.File;
import java.io.FilenameFilter;
  



import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.cyc.config.ConfigManage;
import com.cyc.config.ConfigType;
import com.cyc.core.event.DLRecordEvent;
import com.cyc.core.event.EventType;
import com.cyc.hibernate.domain.DownLoadRecordDomian;
import com.cyc.hibernate.domain.Ed2kStateDomain;

/**
 * @author Administrator
 *	文件正在下载
 *	监听文件列表是否存在迅雷临时下载
 *	超过规定时间没有发现临时文件判定为下载失败
 */
public class DLoadingJob implements Job , ConfigType{

	
	private Logger logger;
	
	private CoreControlJob coreControlJob;

	private Ed2kStateDomain domian;
	
	private String monitorPath;
	
	//每一次监视前等待的时间
	private Integer timewait;
	//监视的次数
	private Integer waittimes;
	
	public DLoadingJob(CoreControlJob job,Ed2kStateDomain domian) {
		// TODO Auto-generated constructor stub
		this.coreControlJob=job;
		this.domian=domian;
		innit();
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int i=0;
		while(waittimes>0)
		{
			
			i++;
			 
			logger.log(Level.INFO, i+"检测是否正在下载成功");
		 
			waittimes--;
			try {
				Thread.sleep(timewait);
				if(doCheckDown(this.monitorPath))
				{
					logger.info("检测正在下载成功是");
					DLRecordEvent event = new DLRecordEvent(EventType.DOWONING, this.domian);
					coreControlJob.nofied(event);
					break;
				}
				else {
					if(waittimes==0)
					{
						logger.info("正在下载失败"+this.domian.getEd2k());
						DLRecordEvent event = new DLRecordEvent(EventType.DOWOFAILD, this.domian);
						coreControlJob.nofied(event);
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	private boolean doCheckDown(String monitorPath2) {
		// TODO Auto-generated method stub
		File directory= new File(monitorPath2);
		return directory.list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return ((name.startsWith(domian.getEd2k().split("\\|")[2])&&name.endsWith("xltd")));
					
				 
			}
		}).length>0;
		
	}

	@Override
	public void innit() {
		// TODO Auto-generated method stub
		this.timewait=Integer.valueOf( ConfigManage.GLOBAL_STRING_CONFIG.get(ConfigType.JOB_TIMEWAIT));
		this.waittimes=Integer.valueOf(ConfigManage.GLOBAL_STRING_CONFIG.get(ConfigType.JOB_WAITTIMES));
		this.monitorPath=ConfigManage.GLOBAL_STRING_CONFIG.get(ConfigType.JOB_MONITORPATH);
		logger= Logger.getLogger(this.getClass().getName());
		 
	}

}
