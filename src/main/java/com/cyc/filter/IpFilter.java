package com.cyc.filter;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.hibernate.annotations.Check;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cyc.config.ConfigManage;
import com.cyc.hibernate.domain.IpDomain;
import com.cyc.hibernate.imp.IpHibernateImp;

public class IpFilter extends OncePerRequestFilter {
	private Logger log;
	private IpHibernateImp iphim = new IpHibernateImp();

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		log = Logger.getLogger(IpFilter.class.getName());
		log.info("监控ip是否频繁下载");
		String ip = request.getRemoteAddr();
		log.info(ip + "尝试获取下载链接");
		if(ConfigManage.GLOBAL_STRING_CONFIG.get("ipcheck").equals("false"))
		{
			filterChain.doFilter(request, response);
			return ;
		}
		
		if (CheckipAndStore(ip)&&Boolean.valueOf(ConfigManage.GLOBAL_STRING_CONFIG.get("ipcheck"))) {
			filterChain.doFilter(request, response);
		} else {
			response.getOutputStream()
					.write("一天不能下载太多了哦~把资源让给其他有用的人"
							.getBytes("gbk"));
		}

	}

	private boolean CheckipAndStore(String ip) {
		// TODO Auto-generated method stub
		try {
			IpDomain ipDomain = iphim.findByIp(ip);
			if (ipDomain == null) {
				iphim.save(new IpDomain(ip,new Date()));
				return true;
			} else {
				// 清理昨天的记录
 				iphim.ClearIp(ipDomain);
				ipDomain.addTimes();
				if (ipDomain.getDownTimes() <= Integer
						.valueOf(ConfigManage.GLOBAL_STRING_CONFIG
								.get("downtimesallows"))) {
					iphim.update(ipDomain);
					return true;
				}
				

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}

}
