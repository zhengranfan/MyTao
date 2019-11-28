package com.mytao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mytao.common.pojo.EUDataGridResult;
import com.mytao.common.pojo.MytaoResult;
import com.mytao.pojo.TbItemParam;
import com.mytao.service.ItemParamService;


/**
 * 商品规格参数模版管理
 * @author ia
 *
 */

@Controller
@RequestMapping("/item/param")
public class ItemParamController {

	
	@Autowired
	private ItemParamService itemParamService;
	
	@RequestMapping("/query/itemcatid/{itemCatId}")
	@ResponseBody
	public MytaoResult getItemParamByCid(@PathVariable Long itemCatId){		
		MytaoResult result = itemParamService.getItemParamByCid(itemCatId);
		return result;
	}
	
	@RequestMapping("/save/{cid}")
	@ResponseBody
	public MytaoResult insertItemParam(@PathVariable Long cid ,String paramData) {
		TbItemParam itemParam = new TbItemParam();
		itemParam.setItemCatId(cid);
		itemParam.setParamData(paramData);
		MytaoResult result = itemParamService.insertItemParam(itemParam);
		return result;
		
	}
	
	   @RequestMapping("/list")
	   @ResponseBody
	    public EUDataGridResult getItemList(Integer page, Integer rows) {
	        return itemParamService.getItemParamList(page, rows);
	    }

	
	
}
