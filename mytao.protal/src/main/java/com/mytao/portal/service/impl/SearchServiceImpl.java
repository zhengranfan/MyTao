package com.mytao.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mytao.common.pojo.MytaoResult;
import com.mytao.common.utils.HttpClientUtil;
import com.mytao.portal.pojo.SearchResult;
import com.mytao.portal.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

    @Value("${SEARCH_SERVICE_URL}")
    private String SEARCH_SERVICE_URL;

    @Override
    public SearchResult search(String queryString, int page) {
        //设置查询参数
        Map<String, String> params = new HashMap<>();
        params.put("q", queryString);
        params.put("page", String.valueOf(page));

        try {
            //执行查询
            String res = HttpClientUtil.doGet(SEARCH_SERVICE_URL, params);
            MytaoResult taotaoResult = MytaoResult.formatToPojo(res, SearchResult.class);
            if (taotaoResult.getStatus() == 200) {
                SearchResult result = (SearchResult) taotaoResult.getData();
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
