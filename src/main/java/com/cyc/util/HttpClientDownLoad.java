package com.cyc.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.httpclient.HttpClient;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.cyc.config.ConfigManage;
import com.cyc.hibernate.domain.EbookDomain;

public class HttpClientDownLoad {

	private static final Logger LOG = LogManager.getLogger(HttpClient.class);
	public static CloseableHttpClient httpClient = null;
	public static HttpClientContext context = null;
	public static CookieStore cookieStore = null;
	public static RequestConfig requestConfig = null;

	static {
		init();
	}

	private static void init() {

		context = HttpClientContext.create();
		cookieStore = new BasicCookieStore();
		// 配置超时时间（连接服务端超时1秒，请求数据返回超时2秒）
		requestConfig = RequestConfig.custom().setConnectTimeout(1200000)
				.setSocketTimeout(600000).setConnectionRequestTimeout(600000)
				.build();
		// 设置默认跳转以及存储cookie
		httpClient = HttpClientBuilder.create()
				.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
				.setRedirectStrategy(new DefaultRedirectStrategy())
				.setDefaultRequestConfig(requestConfig)
				.setDefaultCookieStore(cookieStore).build();
	}

	public static String toString(CloseableHttpResponse httpResponse)
			throws ParseException, IOException {
		// 获取响应消息实体
		HttpEntity entity = httpResponse.getEntity();
		if (entity != null)
			return EntityUtils.toString(entity);
		else
			return null;
	}

	public static File downLibgenBoook(EbookDomain domain) throws Exception {
		init();
		File file = null;
		if (domain == null) {
			return file;
		}
		String extension = domain.getExtension();
		String md5 = domain.getMD5();
		String host = ConfigManage.GLOBAL_STRING_CONFIG.get("libHost");
		CloseableHttpResponse response = HttpClientDownLoad.get(host
				+ "ads.php?md5=" + md5);

		String resourceUrl = regrexFromContent(response);
		System.out.println(resourceUrl);
		response.close();
		CloseableHttpResponse xxxx = HttpClientDownLoad.get(host + resourceUrl);
		InputStream inputStream = xxxx.getEntity().getContent();
		byte[] date = new byte[1024];
		int len = 0;
		File outfile = new File(md5 + "." + extension);
		OutputStream outputStream = new FileOutputStream(outfile);
		while ((len = inputStream.read(date)) != -1) {
			outputStream.write(date, 0, len);
		}
		outputStream.close();
		xxxx.close();
		return outfile;
	}

	/**
	 * 把结果console出来
	 * 
	 * @param httpResponse
	 * @throws ParseException
	 * @throws IOException
	 */
	public static void printResponse(HttpResponse httpResponse)
			throws ParseException, IOException {
		// 获取响应消息实体
		HttpEntity entity = httpResponse.getEntity();
		// 响应状态
		System.out.println("status:" + httpResponse.getStatusLine());
		System.out.println("headers:");
		HeaderIterator iterator = httpResponse.headerIterator();
		while (iterator.hasNext()) {
			System.out.println("\t" + iterator.next());
		}
		// 判断响应实体是否为空
		if (entity != null) {
			String responseString = EntityUtils.toString(entity);
			System.out.println("response length:" + responseString.length());
			System.out.println("response content:"
					+ responseString.replace("\r\n", ""));
		}
		System.out
				.println("------------------------------------------------------------------------------------------\r\n");
	}

	public static CloseableHttpResponse get(String url)
			throws ClientProtocolException, IOException {
		HttpGet httpget = new HttpGet(url);
		CloseableHttpResponse response = httpClient.execute(httpget, context);
		try {
			cookieStore = context.getCookieStore();
			List<Cookie> cookies = cookieStore.getCookies();
			for (Cookie cookie : cookies) {
				LOG.debug("key:" + cookie.getName() + "  value:"
						+ cookie.getValue());
			}
		} finally {
			// response.close();
		}
		return response;
	}

	public static String regrexFromContent(CloseableHttpResponse response)
			throws ParseException, IOException {
		String urlString = null;
		HttpEntity entity = response.getEntity();
		String responseString = null;
		if (entity != null) {
			responseString = EntityUtils.toString(entity);
			System.out.println("response content:"
					+ responseString.replace("\r\n", ""));
		}

		// 正则表达式规则
		String regEx = "<a href='([^>]*key[^>]*)'>";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(responseString);
		// 查找字符串中是否有匹配正则表达式的字符/字符串
		while (matcher.find()) {
			urlString = (matcher.group(1));
		}

		return urlString;

	}

	public static void main(String[] args) throws Exception {

		// 用户登陆
		CloseableHttpResponse response = HttpClientDownLoad
				.get("http://libgen.io/ads.php?md5=65548EE3600CD9C66EDEF27EE9349326");

		// printResponse(response);
		String resourceUrl = regrexFromContent(response);
		System.out.println(resourceUrl);
		printCookies();
		response.close();
		CloseableHttpResponse xxxx = HttpClientDownLoad.get("http://libgen.io"
				+ resourceUrl);

		InputStream inputStream = xxxx.getEntity().getContent();
		byte[] date = new byte[1024];
		int len = 0;
		File outfile = new File("out.pdf");
		OutputStream outputStream = new FileOutputStream(outfile);
		while ((len = inputStream.read(date)) != -1) {
			outputStream.write(date, 0, len);
		}

		xxxx.close();

	}

	public static OutputStream directWriteOutput(EbookDomain domain,
			OutputStream outputStream) throws Exception {
		init();
		if (domain == null) {
			return outputStream;
		}
 		String md5 = domain.getMD5();
		String host = ConfigManage.GLOBAL_STRING_CONFIG.get("libHost");
		String url = host + "ads.php?md5=" + md5;
 		CloseableHttpResponse second = null;
		if (!OssClientUtil.fileIsUp("hanzhou", "liboocbooks", domain.getURL())) {
 			CloseableHttpResponse response = HttpClientDownLoad.get(url);
			String resourceUrl = regrexFromContent(response);
			System.out.println(resourceUrl);
			response.close();
			url = host + resourceUrl;

		} else {

			url = ConfigManage.GLOBAL_STRING_CONFIG.get("bookosspath")
					+ domain.getURL();

		}
		second = HttpClientDownLoad.get(url);
		InputStream inputStream = second.getEntity().getContent();
		byte[] date = new byte[1024];
		int len = 0;
  
		while ((len = inputStream.read(date)) != -1) {
			outputStream.write(date, 0, len);
			 
		}
		 
		second.close();
		return outputStream;
	}

	public static OutputStream directWriteOutputAndUpload(EbookDomain domain,
			OutputStream outputStream) throws Exception {
		init();
		if (domain == null) {
			return outputStream;
		}
		String extension = domain.getExtension();
		String md5 = domain.getMD5();
		String host = ConfigManage.GLOBAL_STRING_CONFIG.get("libHost");
		String url = host + "ads.php?md5=" + md5;
		boolean isup = false;
		CloseableHttpResponse second = null;
		if (!OssClientUtil.fileIsUp("hanzhou", "liboocbooks", domain.getURL())) {
			CloseableHttpResponse response = HttpClientDownLoad.get(url);
			String resourceUrl = regrexFromContent(response);
			System.out.println(resourceUrl);
			response.close();
			url = host + resourceUrl;

		} else {
			isup = true;
			url = ConfigManage.GLOBAL_STRING_CONFIG.get("bookosspath")
					+ domain.getURL();

		}
		second = HttpClientDownLoad.get(url);
		InputStream inputStream = second.getEntity().getContent();
		byte[] date = new byte[1024*1024];
		int len = 0;
		File outfile = new File(md5 + "." + extension);
		FileOutputStream fileOutputStream = new FileOutputStream(outfile);

		while ((len = inputStream.read(date)) != -1) {
			outputStream.write(date, 0, len);
			if (!isup){
				fileOutputStream.write(date, 0, len);
				fileOutputStream.flush();
			}
		}
		if (!isup) {
			fileOutputStream.close();
			OssClientUtil.UploadFile("hanzhou", "liboocbooks", outfile,
					domain.getURL());
			outfile.delete();
		}
		second.close();
		return outputStream;
	}

	public static void printCookies() {
		System.out.println("headers:");
		cookieStore = context.getCookieStore();
		List<Cookie> cookies = cookieStore.getCookies();
		for (Cookie cookie : cookies) {
			System.out.println("key:" + cookie.getName() + "  value:"
					+ cookie.getValue());
		}
	}

}
