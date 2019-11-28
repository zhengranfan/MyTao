package com.mytao.service;

import com.mytao.common.pojo.EUDataGridResult;
import com.mytao.common.pojo.MytaoResult;
import com.mytao.pojo.TbItem;

public interface ItemService {

	TbItem getItemById(long itemId);
	EUDataGridResult getItemList(int page, int rows);
	MytaoResult createItem(TbItem tbItem,String desc,String itemParam);
	
}
