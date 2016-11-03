package com.yyp.mvc.controller;

import java.util.logging.Logger;

import javax.enterprise.inject.New;
import javax.json.Json;

import org.apache.commons.logging.Log;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.cyc.config.ConfigManage;
import com.cyc.domain.Ed2kLinkDelegator;
import com.cyc.exception.MyException;
import com.cyc.hibernate.domain.EbookDomain;
import com.cyc.hibernate.domain.Ed2kStateDomain;
import com.cyc.hibernate.imp.EbookHibernateImp;
import com.cyc.service.BuyService;
import com.cyc.service.DLoadService;
import com.cyc.service.EbookService;
import com.cyc.service.Ed2kLinkService;
import com.cyc.service.EmailService;
import com.cyc.service.UpLoadService;
import com.cyc.util.OssClientUtil;
import com.cyc.view.BaseView;
import com.cyc.view.EbookView;
import com.cyc.view.Ed2kLinkView;
import com.cyc.view.ErrorView;
import com.cyc.view.LinkIsFreeView;
 
@Controller
@RequestMapping("/ebook")
public class EbookBusinessControl extends BaseHandleExceptionControl {

	@Autowired
	private EbookService ebookService;
	@Autowired
	private Ed2kLinkService ed2kLinkService;
	@Autowired
	private BuyService buyService;
	@Autowired
	private EmailService emailService;
	
	
 	private Logger log = Logger.getLogger(this.getClass().getName());

	@RequestMapping(value = "/detail", produces = "text/html;charset=gbk")
	@ResponseBody
	public String findebook(String ebid) throws Exception {
		log.info("ebid>>>>>>>" + ebid);
		EbookDomain ebookDomain;
		EbookView ebookView = null;
		try {
			ebookDomain = ebookService.findbyEid(ebid);
			ebookView = new EbookView(ebookDomain);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			throw e;

		}

		return JSON.toJSONString(ebookView);
	}

	@RequestMapping(value = "/linkIsFree", produces = "text/html;charset=gbk")
	@ResponseBody
	public String geted2kLinkIsFree(String ebid)  {
		try {
			Ed2kStateDomain ed2kStateDomain = ed2kLinkService.storedAndFind(ebid);
			boolean isup = Ed2kStateDomain.UP_SUCCESSED.equals(ed2kStateDomain.getState_upload());
			if(isup)
			return JSON.toJSONString(new LinkIsFreeView( "false"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return JSON.toJSONString(new LinkIsFreeView(true + ""));

		}
		catch (Throwable e) {
			// TODO: handle exception
			e.printStackTrace();
			return JSON.toJSONString(new LinkIsFreeView(true + ""));
		}
		return JSON.toJSONString(new LinkIsFreeView(true + ""));

 	}

	/**
	 * 获取免费书籍
	 * @param ebid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/freedownlink", produces = "text/html;charset=gbk")
	@ResponseBody
	public String geted2kLink(String ebid) throws Exception {
		log.info("获取下载链接+ebid>>>>>>>" + ebid);
 			log.info("buycode为空为选择免费免费图书");
 			
			String downpath = ebookService.findbyEid(ebid).Ed2k();
			Ed2kStateDomain ed2kStateDomain = ed2kLinkService
					.storedAndFind(ebid);
			if( Ed2kStateDomain.UP_SUCCESSED.equals(ed2kStateDomain.getState_upload()))
			{
				log.info("书本已经成功上传不为免费书籍");
				throw new MyException(MyException.ERROR_BUYCODE_USELESS,new Exception(), this.getClass());
			}
			Ed2kLinkDelegator delegator = new Ed2kLinkDelegator(
					ed2kStateDomain, downpath, true);
			Ed2kLinkView elinkView = new Ed2kLinkView(delegator);
			
			return JSON.toJSONString(elinkView);
  			
	}
	
	
	@RequestMapping(value = "/chargedownlink", produces = "text/html;charset=gbk")
	@ResponseBody
	public String geted2kLink(String ebid, String buycode,String email) throws Exception {
		log.info("buycode不为为空收费图书");
		if (!CheckCode(buycode,ebid)) {
			throw new MyException(MyException.ERROR_BUYCODE_USELESS,
					new Exception(), this.getClass());
		}
		if(email==null|email.trim().equals(""))
		{
			throw new MyException(MyException.ERROR_BUY_EMIALNON,
					new Exception(), this.getClass());
		}
		Ed2kStateDomain ed2kStateDomain = ed2kLinkService
				.storedAndFind(ebid);
		String downpath=null;
		if(!OssClientUtil.isUp(ed2kStateDomain.getFilePath())){
			downpath= ed2kStateDomain.getEd2k();
		}else{
		    downpath = ConfigManage.GLOBAL_STRING_CONFIG.get("osspath")
				+ ed2kStateDomain.getFilePath();
		}
		buyService.buy(buycode,ebid);
		emailService.saveAndSend(email, downpath, buycode, ed2kStateDomain);
		Ed2kLinkDelegator delegator = new Ed2kLinkDelegator(
				ed2kStateDomain, downpath, true);
		Ed2kLinkView elinkView = new Ed2kLinkView(delegator);
		return JSON.toJSONString(elinkView);
	}
	
	
	/**
	 * 获取书籍的封面主要是针对那些封面未上传到服务器的图书
	 * @param ebid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getimgurl", produces = "text/html;charset=gbk")
	@ResponseBody
	public String getimg(String ebid) throws Exception {
 		
		
		return ebookService.getimg(ebid);
	}
	
	
	//检验是否为有效兑换码
	private boolean CheckCode(String buycode,String ebid) {
		// TODO Auto-generated method stub
		return buyService.CodeIsUseful(buycode,ebid);
	}

}
