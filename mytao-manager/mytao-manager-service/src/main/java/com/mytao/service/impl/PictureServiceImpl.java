package com.mytao.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mytao.common.utils.FtpUtil;
import com.mytao.common.utils.IDUtils;
import com.mytao.service.PictureService;


@Service
public class PictureServiceImpl implements PictureService {

	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;

	@Override
	public Map<String,Object> uploadPicture(MultipartFile multipartFile) {

		Map<String,Object> reMap = new HashMap<>();
		try {
			// 生成一个新文件名
			// 原始文件名
			String oldName = multipartFile.getOriginalFilename();
			// 生成新的文件名
			String newName = IDUtils.genImageName();
			newName = newName + oldName.substring(oldName.lastIndexOf("."));

			String imagePath = new DateTime().toString("yyyy/MM/dd");
			// 图片上传

			boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH,
					imagePath , newName, multipartFile.getInputStream());

			if (!result) {
				reMap.put("error", 1);
				reMap.put("message", "上传失败");
				return reMap;
			}
			reMap.put("error", 0);
			reMap.put("url", IMAGE_BASE_URL + imagePath + "/" + newName);
			return reMap;
		} catch (IOException e) {
			reMap.put("error", 1);
			reMap.put("message", "上传发生异常");
			return reMap;
		}
	}

}
