package com.mytao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mytao.common.pojo.EUTreeNode;
import com.mytao.common.pojo.MytaoResult;
import com.mytao.service.ContentCategoryService;



@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

	
	
	@Autowired
	private ContentCategoryService  contentCategoryService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> getContentCatList(@RequestParam(value="id",defaultValue="0")Long parentId){
		
		List<EUTreeNode> list = contentCategoryService.getCategoryList(parentId);
		return list;
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public MytaoResult createContentCategory(Long parentId, String name) {
		MytaoResult result =contentCategoryService.insertConentCategory(parentId, name);
		return result;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public MytaoResult deleteContentCategory(Long parentId,long id){
	return	contentCategoryService.deleteContentCategory( id);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public MytaoResult updateContentCategory(Long id,String name) {
	return contentCategoryService.updateContentCategory(id, name);
	}
	
	
	
	
}
