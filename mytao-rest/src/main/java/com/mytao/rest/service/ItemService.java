package com.mytao.rest.service;

import com.mytao.common.pojo.MytaoResult;

public interface ItemService {

	
	 MytaoResult getItemBaseInfo(long itemId);
	 MytaoResult getItemDesc(long itemId);
	 MytaoResult getItemParam(long itemId);
}
