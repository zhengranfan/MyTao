package com.mytao.portal.service;

import com.mytao.portal.pojo.SearchResult;

public interface SearchService {

	 SearchResult search(String queryString, int page);
	
}
