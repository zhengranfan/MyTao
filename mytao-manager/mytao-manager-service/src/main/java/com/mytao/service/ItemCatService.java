package com.mytao.service;

import java.util.List;

import com.mytao.common.pojo.EUTreeNode;

public interface ItemCatService {

	
	List<EUTreeNode> getCatList(Long parentId);
}
