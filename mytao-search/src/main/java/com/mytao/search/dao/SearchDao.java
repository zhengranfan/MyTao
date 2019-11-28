package com.mytao.search.dao;



import org.apache.solr.client.solrj.SolrQuery;

import com.mytao.search.pojo.SearchResult;

public interface SearchDao {
	SearchResult search(SolrQuery query) throws Exception;
}
