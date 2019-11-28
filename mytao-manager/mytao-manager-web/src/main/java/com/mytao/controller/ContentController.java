package com.mytao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mytao.common.pojo.EUDataGridResult;
import com.mytao.common.pojo.MytaoResult;
import com.mytao.pojo.TbContent;
import com.mytao.service.ContentService;

@Controller

@RequestMapping("/content")
public class ContentController {

	
	@Autowired
	private ContentService contentService;
	
	
	@RequestMapping("/save")
	@ResponseBody
	public MytaoResult insertContent(TbContent content ) {
	  return	contentService.insertContent(content);
		
	}
	
	
	@RequestMapping("/query/list")
	@ResponseBody
	public EUDataGridResult findContent(Long categoryId,int page,int rows) {
		
	return	contentService.fingContentList(categoryId, page, rows);
	}
}
