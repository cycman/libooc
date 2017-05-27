package com.cyc.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;

import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFOperator;
import org.pdfbox.util.PDFTextStripper;

public class PdfUtil {
	public static File readFdf(String file ,String preFile ,int endPage) throws Exception {
		// 是否排序
		boolean sort = false;
		// pdf文件名
		String pdfFile = file;
		// 输入文本文件名称
		String textFile = preFile;
		// 编码方式
		String encoding = "UTF-8";
		// 开始提取页数
		int startPage = 1;
		// 结束提取页数
 		// 文件输入流，生成文本文件
		Writer output = null;
		// 内存中存储的PDF Document
		PDDocument document = null;
		File outFile =null;
		try {
			try {
				// 首先当作一个URL来装载文件，如果得到异常再从本地文件系统//去装载文件
				URL url = new URL(pdfFile);
				// 注意参数已不是以前版本中的URL.而是File。
				document = PDDocument.load(pdfFile);
				 
			} catch (MalformedURLException e) {
				// 如果作为URL装载得到异常则从文件系统装载
				// 注意参数已不是以前版本中的URL.而是File。
				document = PDDocument.load(pdfFile);
	
			}
			outFile= new File(textFile);
			// 文件输入流，写入文件倒textFile
			output = new OutputStreamWriter(new FileOutputStream(outFile),
					encoding);
			// PDFTextStripper来提取文本
			PDFTextStripper stripper = null;
			stripper = new PDFTextStripper();
			// 设置是否排序
			stripper.setSortByPosition(sort);
			// 设置起始页
			stripper.setStartPage(startPage);
			// 设置结束页
			stripper.setEndPage(endPage);
			// 调用PDFTextStripper的writeText提取并输出文本
			stripper.writeText(document, output);
		} finally {
			if (output != null) {
				output.flush();
				// 关闭输出流
				output.close();
			}
			if (document != null) {
				// 关闭PDF Document
				document.close();
			}
		}
		return outFile;
	
	}

	public static File mkPreView(File source, int max) throws Exception {
		return readFdf(source.getAbsolutePath(),source.getAbsolutePath()+"pre",max);
	}
	
	public static void main(String[] args) {
		try {
			readFdf("D:\\eclipesej2ee\\eclipse\\b.pdf", "D:\\eclipesej2ee\\eclipse\\a.pdf", 3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	 
}