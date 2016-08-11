package com.cyc.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.springframework.web.filter.OncePerRequestFilter;

public class Originfilter extends OncePerRequestFilter {
	private Logger log ; 
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		log= Logger.getLogger(Originfilter.class.getName());
		log.info("originFileter  设置  Access-Control-Allow-Origin 为 *");
		 response.setHeader("Access-Control-Allow-Origin", "*"); 
		 filterChain.doFilter(request, response);
	}

}
