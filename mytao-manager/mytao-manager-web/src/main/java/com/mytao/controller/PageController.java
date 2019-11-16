package com.mytao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PageController {
	
	
	/**
	 * 打开首页
	 */
	
	
	@RequestMapping("/")
	public String showIndex() {
		return "index";	
	}
	
	
	/**
	 * 展示其他页面
	 * @param page
	 * @return
	 */
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page) {	
		
	    System.out.println("showPage......." + page);
		return page;
	}
	

}
