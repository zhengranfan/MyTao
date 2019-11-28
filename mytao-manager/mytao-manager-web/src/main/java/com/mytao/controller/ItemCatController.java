package com.mytao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mytao.common.pojo.EUTreeNode;
import com.mytao.service.ItemCatService;

/**
 * 商品分类管理
 * @author ia
 *
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

		@Autowired
		private ItemCatService itemCatService;
		
		
		@RequestMapping("/list")
		@ResponseBody
		private List<EUTreeNode> getCatList(@RequestParam(value="id",defaultValue="0") Long parentId){
			List<EUTreeNode> list = itemCatService.getCatList(parentId);
			
			return list;
		}
		
}
