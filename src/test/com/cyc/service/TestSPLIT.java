package com.cyc.service;

import com.cyc.util.OssClientUtil;

public class TestSPLIT {

	public static void main(String[] args) {
		
		try {
			OssClientUtil.UploadFile("F:\\aa\\aa.jnt", "aa");
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
