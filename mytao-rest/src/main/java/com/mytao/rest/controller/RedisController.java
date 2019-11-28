package com.mytao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mytao.common.pojo.MytaoResult;
import com.mytao.rest.service.RedisService;

@Controller
@RequestMapping("/cache/sync")
public class RedisController {

	@Autowired
	private RedisService redisService;
	
	@RequestMapping("/content/{contentCid}")
	@ResponseBody
	public MytaoResult contentCacheSync(@PathVariable Long contentCid) {
		return	redisService.syncContent(contentCid);
		
	}
}
