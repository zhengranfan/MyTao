package com.mytao.rest.service;

import java.util.List;

import com.mytao.pojo.TbContent;

public interface ContentService {

	List<TbContent> getContentList(long contentCid);
	
}
