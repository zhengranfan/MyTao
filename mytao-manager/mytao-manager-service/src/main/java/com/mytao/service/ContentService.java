package com.mytao.service;

import com.mytao.common.pojo.EUDataGridResult;
import com.mytao.common.pojo.MytaoResult;
import com.mytao.pojo.TbContent;

public interface ContentService {

	MytaoResult insertContent(TbContent content);
	
	EUDataGridResult fingContentList(long categoryId,int page ,int rows);
		
}
