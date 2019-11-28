package com.mytao.portal.service;

import com.mytao.portal.pojo.Item;

public interface ItemService {
	Item getItemBase(long itemId);
	String getItemDesc(Long itemId);
	String getItemParam(Long itemId);
}
