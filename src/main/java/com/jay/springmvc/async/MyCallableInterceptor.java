package com.jay.springmvc.async;

import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.CallableProcessingInterceptor;

public class MyCallableInterceptor implements CallableProcessingInterceptor
// --- 自定义Callable拦截器 ， 可在这里处理Callable任务的超时问题
{
	@Override
	public <T> Object handleTimeout(NativeWebRequest request, Callable<T> task)
			throws Exception {
		HttpServletResponse servletResponse = request
				.getNativeResponse(HttpServletResponse.class);
		if (!servletResponse.isCommitted()) {
			servletResponse.setContentType("text/plain;charset=utf-8");
			servletResponse.getWriter().write("超时了");
			servletResponse.getWriter().close();
		}
		return null;
	}

	@Override
	public <T> void beforeConcurrentHandling(NativeWebRequest request,
			Callable<T> task) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void preProcess(NativeWebRequest request, Callable<T> task)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void postProcess(NativeWebRequest request, Callable<T> task,
			Object concurrentResult) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void afterCompletion(NativeWebRequest request, Callable<T> task)
			throws Exception {
		// TODO Auto-generated method stub

	}
}