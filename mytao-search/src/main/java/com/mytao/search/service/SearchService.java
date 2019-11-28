package com.mytao.search.service;

import com.mytao.search.pojo.SearchResult;

public interface SearchService {
	public SearchResult search(String queryString, int page, int rows) throws Exception;
}
