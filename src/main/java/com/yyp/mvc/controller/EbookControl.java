package com.yyp.mvc.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.cyc.config.ConfigManage;
import com.cyc.domain.Ed2kLinkDelegator;
import com.cyc.exception.MyException;
import com.cyc.hibernate.domain.EbookDomain;
import com.cyc.hibernate.domain.Ed2kStateDomain;
import com.cyc.service.BuyService;
import com.cyc.service.EbookService;
import com.cyc.service.Ed2kLinkService;
import com.cyc.service.EmailService;
import com.cyc.service.RecommendSerivce;
import com.cyc.util.OssClientUtil;
import com.cyc.view.EbookView;
import com.cyc.view.Ed2kLinkView;
import com.cyc.view.LinkIsFreeView;
import com.sun.istack.FinalArrayList;

@Controller
@RequestMapping("/ebook")
public class EbookControl extends BaseHandleExceptionControl {

	@Autowired
	private EbookService ebookService;
	@Autowired
	private RecommendSerivce recommendSerivce;
	@Autowired
	private Ed2kLinkService ed2kLinkService;
	@Autowired
	private BuyService buyService;
	@Autowired
	private EmailService emailService;

	private Logger log = Logger.getLogger(EbookControl.class);

	@RequestMapping(value = "/detail", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String findebook(String ebid) throws Exception {
		log.log(Priority.toPriority("FILEOUT"), "ebid>>>>>>>" + ebid);
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

	@RequestMapping(value = "/recoment", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String recoment(String uid) throws Exception {
		log.log(Priority.toPriority("FILEOUT"), "uid>>>>>>>" + uid);
		List<Long> ids = null;
		try {
			ids = recommendSerivce
					.recomentBookIDs(Long.valueOf(uid), "comment");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

		return JSON.toJSONString(ids);
	}

	@RequestMapping(value = "/linkIsFree", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String geted2kLinkIsFree(String ebid) {
		try {
			Ed2kStateDomain ed2kStateDomain = ed2kLinkService
					.storedAndFind(ebid);
			boolean isup = Ed2kStateDomain.UP_SUCCESSED.equals(ed2kStateDomain
					.getState_upload());
			if (isup)
				return JSON.toJSONString(new LinkIsFreeView("false"));
		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return JSON.toJSONString(new LinkIsFreeView(true + ""));

		} catch (Throwable e) {
			// TODO: handle exception
			e.printStackTrace();
			return JSON.toJSONString(new LinkIsFreeView(true + ""));
		}
		return JSON.toJSONString(new LinkIsFreeView(true + ""));

	}

	/**
	 * 获取免费书籍
	 * 
	 * @param ebid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/freedownlink", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String geted2kLink(String ebid) throws Exception {
		log.info("获取下载链接+ebid>>>>>>>" + ebid);
		log.info("buycode为空为选择免费免费图书");

		String downpath = ebookService.findbyEid(ebid).Ed2k();
		Ed2kStateDomain ed2kStateDomain = ed2kLinkService.storedAndFind(ebid);

		Ed2kLinkDelegator delegator = new Ed2kLinkDelegator(ed2kStateDomain,
				downpath, true);
		Ed2kLinkView elinkView = new Ed2kLinkView(delegator);

		return JSON.toJSONString(elinkView);

	}

	@RequestMapping(value = "/chargedownlink", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String geted2kLink(String ebid, String buycode, String email)
			throws Exception {
		log.info("buycode不为为空收费图书");
		if (!CheckCode(buycode, ebid)) {
			throw new MyException(MyException.ERROR_BUYCODE_USELESS,
					new Exception(), this.getClass());
		}
		if (email == null | email.trim().equals("")) {
			throw new MyException(MyException.ERROR_BUY_EMIALNON,
					new Exception(), this.getClass());
		}
		Ed2kStateDomain ed2kStateDomain = ed2kLinkService.storedAndFind(ebid);
		String downpath = null;
		if (!OssClientUtil.isUp(ed2kStateDomain.getFilePath())) {
			downpath = ed2kStateDomain.getEd2k();
		} else {
			downpath = ConfigManage.GLOBAL_STRING_CONFIG.get("osspath")
					+ ed2kStateDomain.getFilePath();
		}
		buyService.buy(buycode, ebid);
		emailService.saveAndSend(email, downpath, buycode, ed2kStateDomain);
		Ed2kLinkDelegator delegator = new Ed2kLinkDelegator(ed2kStateDomain,
				downpath, true);
		Ed2kLinkView elinkView = new Ed2kLinkView(delegator);
		return JSON.toJSONString(elinkView);
	}

	/**
	 * 获取书籍的封面主要是针对那些封面未上传到服务器的图书
	 * 
	 * @param ebid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getimgurl", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String getimg(String ebid) throws Exception {

		return ebookService.getimg(ebid);
	}

	// 检验是否为有效兑换码
	private boolean CheckCode(String buycode, String ebid) {
		// TODO Auto-generated method stub
		return buyService.CodeIsUseful(buycode, ebid);
	}

	@RequestMapping("download")
	public ResponseEntity<byte[]> download() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "dict.txt");
		return new ResponseEntity<byte[]>(
				FileUtils.readFileToByteArray(new File("d://out.pdf")),
				headers, HttpStatus.CREATED);
	}

	@RequestMapping("download3")
	public void download3(int bid) throws IOException {
		ebookService.downAndUploadLibgenBook(bid, false);
	}

	// only for pdf
	@RequestMapping("preview")
	public void preview(HttpServletResponse res, int bid,String fileName) throws Exception {
		OutputStream os = res.getOutputStream();
		try {
			res.reset();
			res.setHeader("Content-Disposition",
					"attachment; filename="+fileName);
			res.setContentType("application/octet-stream; charset=utf-8");
			res.setHeader("Access-Control-Allow-Origin", "*");
			res.setHeader("Access-Control-Expose-Headers", "Accept-Ranges");
			res.setHeader("Access-Control-Expose-Headers", "Content-Length");
			if (ebookService.bookIsUp(bid)) {
				res.addHeader("location",
						ConfigManage.GLOBAL_STRING_CONFIG.get("bookosspath")
								+ ebookService.findbyEid(bid + "").getURL());
				res.setStatus(302);
			} else {
				ebookService.directWriteOutput(bid, os,res);
			}
			os.flush();
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}

	@RequestMapping(value = "/preview2", method = RequestMethod.GET)
	public WebAsyncTask<String> preview2(final HttpServletResponse res,
			final int bid, final String fileName) {
		System.out.println("/longtimetask被调用 thread id is : "
				+ Thread.currentThread().getId());
		Callable<String> callable = new Callable<String>() {
			public String call() throws Exception {
				OutputStream os = res.getOutputStream();
				try {

					res.reset();
					res.setHeader("Content-Disposition",
							"attachment; filename=" + fileName);
					res.setContentType("application/octet-stream; charset=utf-8");
					res.setHeader("Access-Control-Allow-Origin", "*");
					res.setHeader("Access-Control-Expose-Headers", "Content-Length");
					res.setHeader("Access-Control-Expose-Headers",
							"Accept-Ranges");
					if (ebookService.bookIsUp(bid)) {
						res.addHeader(
								"location",
								ConfigManage.GLOBAL_STRING_CONFIG
										.get("bookosspath")
										+ ebookService.findbyEid(bid + "")
												.getURL());
						res.setStatus(302);
					} else {
						ebookService.directWriteOutputAndUpload(bid, os,res);
					}
					os.flush();

				} finally {
					if (os != null) {
						os.close();
					}
				}
				System.out.println("执行成功 thread id is : "
						+ Thread.currentThread().getId());
				return "success";
			}
		};

		return new WebAsyncTask(1000000, callable);
	}

}
