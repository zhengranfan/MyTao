package com.mytao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mytao.common.pojo.EUDataGridResult;
import com.mytao.common.pojo.MytaoResult;
import com.mytao.pojo.TbItem;
import com.mytao.service.ItemService;

@Controller

public class ItemController {

	@Autowired
	private ItemService itemService;

	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {

		TbItem item = itemService.getItemById(itemId);
		return item;

	}

	@RequestMapping("/item/list")
	@ResponseBody
	public EUDataGridResult getItemList(Integer page, Integer rows) {
		EUDataGridResult result = itemService.getItemList(page, rows);
		return result; 
	}

	
	@RequestMapping(value = "/item/save", method=RequestMethod.POST)
	@ResponseBody
	public MytaoResult createItem(TbItem item,String desc,String itemParams) {
		MytaoResult result = itemService.createItem(item,desc,itemParams);
		return result;
	}
	
}
