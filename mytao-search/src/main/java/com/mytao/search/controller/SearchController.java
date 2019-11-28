package com.mytao.search.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mytao.common.pojo.MytaoResult;
import com.mytao.common.utils.ExceptionUtil;
import com.mytao.search.pojo.SearchResult;
import com.mytao.search.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@RequestMapping(value="/query", method=RequestMethod.GET)
	@ResponseBody
	public MytaoResult search(@RequestParam("q")String queryString, 
			@RequestParam(defaultValue="1")Integer page, 
			@RequestParam(defaultValue="60")Integer rows) {
		//查询条件不能为空
		if (StringUtils.isBlank(queryString)) {
			return MytaoResult.build(400, "查询条件不能为空");
		}
		SearchResult searchResult = null;
		try {
			searchResult = searchService.search(new String(queryString.getBytes("iso8859-1"),"utf-8"), page, rows);
		} catch (Exception e) {
			e.printStackTrace();
			return MytaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return MytaoResult.ok(searchResult);
		
	}
	
}