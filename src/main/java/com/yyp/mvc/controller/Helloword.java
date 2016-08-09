package com.yyp.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/hello")
public class Helloword {

	@RequestMapping(value="hello",produces="text/html;charset=gbk")  // 瀛恟equest璇锋眰url锛屾嫾鎺ュ悗绛変环浜�test3/login.do
      
     public String forjson()
 	{
        
 		
 		
 		
 		return  "hello";
 	}
	
}
