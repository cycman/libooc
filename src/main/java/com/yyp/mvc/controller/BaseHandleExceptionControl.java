package com.yyp.mvc.controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.cyc.exception.MyException;
import com.cyc.view.ErrorView;

public class BaseHandleExceptionControl {

	
	 
	
	private Logger log = Logger.getLogger(this.getClass().getName());

	@ExceptionHandler 
	@ResponseBody
    public String exp(HttpServletRequest request, Exception ex) {  
            // 根据不同错误转向不同页面  
        	
			ex.printStackTrace();
			//生成errorview
			log.info(ex.getMessage());
            return JSON.toJSONString(new ErrorView(ex));  
	}
}
