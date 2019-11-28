package com.mytao.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.mytao.common.utils.FtpUtil;

public class testFtpClient {

	
	@Test
	public void testFtpClient() throws Exception {
		
//		InputStream inputStream = new FileInputStream(new File("/Users/ia/Desktop/test.png"));
//		
//		System.out.println(FtpUtil.uploadFile("192.168.199.230", 21, "ftpuser", "ftpuser",
//				"/home/ftpuser/www/images", "/2019/11/19", "hello15.jpg", inputStream));
		
		
		
		System.out.println(FtpUtil.downloadFile("192.168.199.230", 21, 
				"ftpuser", "ftpuser", "/home/ftpuser/www/images", "nginx.doc", "/Users/ia/Desktop/"));

		
//		System.out.println("testFtpClient");
//		
//		FTPClient ftpClient = new FTPClient();
//		ftpClient.connect("192.168.199.230",21);
//		ftpClient.login("ftpuser", "ftpuser");
//		
//		InputStream inputStream = new FileInputStream(new File("/Users/ia/Desktop/test.png"));
//		
//		ftpClient.changeWorkingDirectory("/home/ftpuser/www/images");
//		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//		ftpClient.storeFile("hello.jpg", inputStream);	
//		ftpClient.logout();
	}
	
	
}
