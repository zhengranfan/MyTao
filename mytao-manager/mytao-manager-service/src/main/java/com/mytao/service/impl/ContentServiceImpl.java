package com.mytao.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mytao.common.pojo.EUDataGridResult;
import com.mytao.common.pojo.MytaoResult;
import com.mytao.common.utils.HttpClientUtil;
import com.mytao.mapper.TbContentMapper;
import com.mytao.pojo.TbContent;
import com.mytao.pojo.TbContentExample;
import com.mytao.pojo.TbContentExample.Criteria;
import com.mytao.service.ContentService;
import com.sun.tools.example.debug.tty.TTYResources;


@Service
public class ContentServiceImpl implements ContentService {

	@Autowired 
	private TbContentMapper tbContentMapper;
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;
	
	@Override
	public MytaoResult insertContent(TbContent content) {
	
		content.setCreated(new Date());
		content.setUpdated(new Date());
		
	    tbContentMapper.insert(content);
	    
	    try {
	      //添加缓存同步逻辑
		  HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+content.getCategoryId());
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
		return MytaoResult.ok();
	}

	@Override
	public EUDataGridResult fingContentList(long categoryId, int page, int rows) {
		
		PageHelper.startPage(page, rows);
		
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
			
		List<TbContent> list = tbContentMapper.selectByExample(example);
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(list);
		long total = pageInfo.getTotal();
		
		EUDataGridResult euDataGridResult = new EUDataGridResult();
		euDataGridResult.setRows(list);
		euDataGridResult.setTotal(total);
		return euDataGridResult;

	}
	

}
