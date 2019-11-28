package com.mytao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.mytao.common.utils.JsonUtils;
import com.mytao.rest.pojo.CatResult;
import com.mytao.rest.service.ItemCatService;

@Controller
public class ItemCatController {

	
	@Autowired
	private ItemCatService itemCatService;
	
	
//	@RequestMapping(value="/itemcat/list",
//			produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
//	@ResponseBody
//	public String getItemCatList(String callbak) {
//	  CatResult catResult =itemCatService.getItemCatList();
//	  String json = JsonUtils.objectToJson(catResult);
//	  String result =callbak + "(" + json + ")";
//	  return result;
//		
//	}
	
	@RequestMapping(value="/itemcat/list")
	@ResponseBody
	public Object getItemCatList(String callbak) {
	  CatResult catResult =itemCatService.getItemCatList();
	  MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(catResult);
	  mappingJacksonValue.setJsonpFunction(callbak);
	 
	  return mappingJacksonValue;
		
	}
	
	
}
