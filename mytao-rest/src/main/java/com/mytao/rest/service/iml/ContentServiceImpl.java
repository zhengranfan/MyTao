package com.mytao.rest.service.iml;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mytao.common.utils.JsonUtils;
import com.mytao.mapper.TbContentMapper;
import com.mytao.pojo.TbContent;
import com.mytao.pojo.TbContentExample;
import com.mytao.pojo.TbContentExample.Criteria;
import com.mytao.rest.dao.JedisClient;
import com.mytao.rest.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper tbContentMapper;

	@Value("${INDEX_CONTENT_KET}")
	private String INDEX_CONTENT_KET;

	@Autowired
	private JedisClient jedisClient;

	@Override
	public List<TbContent> getContentList(long contentCid) {

		try {
			String result =jedisClient.hget(INDEX_CONTENT_KET, contentCid + "");
			if(!StringUtils.isBlank(result)) {
			List<TbContent> contents = JsonUtils.jsonToList(result, TbContent.class);
			return contents;			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(contentCid);

		List<TbContent> list = tbContentMapper.selectByExample(example);

		try {
				String cacheString = JsonUtils.objectToJson(list);
				jedisClient.hset(INDEX_CONTENT_KET, contentCid + "", cacheString);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
