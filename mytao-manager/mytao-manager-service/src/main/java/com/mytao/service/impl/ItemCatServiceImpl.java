package com.mytao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mytao.common.pojo.EUTreeNode;
import com.mytao.mapper.TbItemCatMapper;
import com.mytao.pojo.TbItemCat;
import com.mytao.pojo.TbItemCatExample;
import com.mytao.pojo.TbItemCatExample.Criteria;
import com.mytao.service.ItemCatService;


@Service
public class ItemCatServiceImpl implements ItemCatService{

	
	@Autowired
	private TbItemCatMapper  tbItemCatMapper;
	
	@Override
	public List<EUTreeNode> getCatList(Long parentId) {

		//创建查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat>  list =tbItemCatMapper.selectByExample(example);
		List<EUTreeNode> resultList = new ArrayList<EUTreeNode>();
		
		for(TbItemCat tbItemCat : list){
			EUTreeNode node = new EUTreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		
		return resultList;
	}

}
