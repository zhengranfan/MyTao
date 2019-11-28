package com.mytao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mytao.common.pojo.EUTreeNode;
import com.mytao.common.pojo.MytaoResult;
import com.mytao.mapper.TbContentCategoryMapper;
import com.mytao.mapper.TbContentMapper;
import com.mytao.pojo.TbContentCategory;
import com.mytao.pojo.TbContentCategoryExample;
import com.mytao.pojo.TbContentCategoryExample.Criteria;
import com.mytao.pojo.TbContentExample;

import com.mytao.service.ContentCategoryService;



@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	
	@Autowired	
	private TbContentCategoryMapper tbContentCategoryMapper;
	
	
	
	@Override
	public List<EUTreeNode> getCategoryList(long parentId) {
		
		TbContentCategoryExample example =new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		List<EUTreeNode> resultList = new ArrayList<EUTreeNode>();
		if(list != null && list.size() > 0) {
			for(TbContentCategory category : list) {
				EUTreeNode node = new EUTreeNode();
				node.setId(category.getId());
				node.setText(category.getName());
				node.setState(category.getIsParent()?"closed":"open");
				resultList.add(node);
			}	
		}
	
		
		return resultList;
	}

	@Override
	public MytaoResult insertConentCategory(long parentId, String name) {
		TbContentCategory tbContentCategory = new TbContentCategory();
		tbContentCategory.setName(name);
		tbContentCategory.setIsParent(false);
		tbContentCategory.setStatus(1);
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setSortOrder(1);
		tbContentCategory.setUpdated(new Date());
		tbContentCategory.setCreated(new Date());	
		tbContentCategoryMapper.insert(tbContentCategory);
		
		
		TbContentCategory parentCategory = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parentCategory.getIsParent()) {
			parentCategory.setIsParent(true);
			tbContentCategoryMapper.updateByPrimaryKey(parentCategory);
		}
		
		
		return MytaoResult.ok(tbContentCategory);
	}

	@Override
	public MytaoResult deleteContentCategory(long id) {
		
		
		 TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
	     Long parentId = tbContentCategory.getParentId();
	     tbContentCategoryMapper.deleteByPrimaryKey(id);
		 TbContentCategoryExample example = new TbContentCategoryExample();
		  Criteria criteria = example.createCriteria();
		  criteria.andParentIdEqualTo(parentId);
		  
		  List<TbContentCategory>  list  = tbContentCategoryMapper.selectByExample(example);
	
		  
		  if(list.isEmpty()) {
			  TbContentCategory parentCat = tbContentCategoryMapper.selectByPrimaryKey(parentId);
			  parentCat.setIsParent(false);
			  parentCat.setUpdated(new Date());
			  tbContentCategoryMapper.updateByPrimaryKey(parentCat);
		  }
		  
		
		  
		  return MytaoResult.ok(tbContentCategory);
		

	}

	@Override
	public MytaoResult updateContentCategory(long id, String name) {
		
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		
		TbContentCategory tbContentCategory = new TbContentCategory();
		tbContentCategory.setName(name);
		tbContentCategory.setUpdated(new Date());
		tbContentCategoryMapper.updateByExampleSelective(tbContentCategory, example);
		return MytaoResult.ok(tbContentCategory);
	}

}
