package com.mytao.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mytao.common.utils.JsonUtils;
import com.mytao.service.PictureService;

@Controller
public class PictureController {

	@Autowired
	private  PictureService pictureService;
	
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String pictureUpload(MultipartFile uploadFile){
		Map<String, Object> resultMap = pictureService.uploadPicture(uploadFile);
		//为了保证兼容性需要吧json转为String
		return JsonUtils.objectToJson(resultMap);
		
	}
}
