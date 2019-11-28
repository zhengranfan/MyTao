package com.mytao.portal.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mytao.common.pojo.MytaoResult;
import com.mytao.common.utils.HttpClientUtil;
import com.mytao.common.utils.JsonUtils;
import com.mytao.pojo.TbContent;
import com.mytao.portal.service.ContentService;


@Service
public class ContentServiceImpl implements ContentService {
	
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${REST_INDEX_AD_URL}")
	private String REST_INDEX_AD_URL;

	@Override
	public String getContentList() {
		
		String result = HttpClientUtil.doGet(REST_BASE_URL + REST_INDEX_AD_URL);
		try {
			MytaoResult mytaoResult  = MytaoResult.formatToList(result, TbContent.class);
			List<TbContent> list = (List<TbContent>) mytaoResult.getData();
			List<Map<String,Object>> resultList = new ArrayList<>();
			for(TbContent tbContent : list) {
				Map<String,Object> map = new HashMap<>();
				map.put("src", tbContent.getPic());
				map.put("height", 240);
				map.put("width", 670);
				map.put("srcB", tbContent.getPic2());
				map.put("widthB", 550);
				map.put("heightB", 240);
				map.put("href", tbContent.getUrl());
				map.put("alt", tbContent.getSubTitle());
				resultList.add(map);			
			}
			return JsonUtils.objectToJson(resultList);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
