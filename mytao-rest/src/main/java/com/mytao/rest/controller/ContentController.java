package com.mytao.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mytao.common.pojo.MytaoResult;
import com.mytao.common.utils.ExceptionUtil;
import com.mytao.pojo.TbContent;
import com.mytao.rest.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContentService contentService;

	@RequestMapping("/list/{contentCategoryId}")
	@ResponseBody
	public MytaoResult getContentList(@PathVariable long contentCategoryId) {

		try {
			List<TbContent> results = contentService.getContentList(contentCategoryId);
			return MytaoResult.ok(results);
		} catch (Exception e) {
			e.printStackTrace();
			return MytaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}

	}

}
