package com.mytao.rest.service.iml;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mytao.common.pojo.MytaoResult;
import com.mytao.common.utils.JsonUtils;
import com.mytao.mapper.TbItemDescMapper;
import com.mytao.mapper.TbItemMapper;
import com.mytao.mapper.TbItemParamItemMapper;
import com.mytao.pojo.TbItem;
import com.mytao.pojo.TbItemDesc;
import com.mytao.pojo.TbItemParamItem;
import com.mytao.pojo.TbItemParamItemExample;
import com.mytao.pojo.TbItemParamItemExample.Criteria;
import com.mytao.rest.dao.JedisClient;
import com.mytao.rest.service.ItemService;



@Service 
public class ItemServiceImpl implements ItemService {

	   @Autowired
	    private TbItemMapper itemMapper;
	    @Autowired
	    private TbItemDescMapper itemDescMapper;
	    @Autowired
	    private TbItemParamItemMapper itemParamItemMapper;
	    @Autowired
	    private JedisClient redisDao;

	    @Value("${REDIS_ITEM_KEY}")
	    private String REDIS_ITEM_KEY;
	    @Value("${ITEM_BASE_KEY}")
	    private String ITEM_BASE_KEY;
	    @Value("${ITEM_DESC_KEY}")
	    private String ITEM_DESC_KEY;
	    @Value("${ITEM_PARAM_KEY}")
	    private String ITEM_PARAM_KEY;


	    @Value("${REDIS_ITEM_EXPIRE}")
	    private Integer REDIS_ITEM_EXPIRE;


	    @Override
	    public MytaoResult getItemBaseInfo(long itemId) {
	        //该商品redis中商品基本信息对应的key
	        String key = REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_BASE_KEY;

	        //缓存中读取
	        try {
	            String json = redisDao.get(key);
	            if (!StringUtils.isBlank(json)) {
	                TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
	                return MytaoResult.ok(item);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        //查询基本信息
	        TbItem item = itemMapper.selectByPrimaryKey(itemId);

	        //写入缓存
	        try {
	            redisDao.set(key, JsonUtils.objectToJson(item));
	            redisDao.expire(key, REDIS_ITEM_EXPIRE);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return MytaoResult.ok(item);
	    }

	    @Override
	    public MytaoResult getItemDesc(long itemId) {
	        //该商品redis中商品描述对应的key
	        String key = REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_DESC_KEY;

	        //读取缓存
	        try {
	            String json = redisDao.get(key);
	            if (!StringUtils.isBlank(json)) {
	                TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
	                return MytaoResult.ok(itemDesc);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        //查询基本信息
	        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);

	        //写入缓存
	        try {
	            redisDao.set(key, JsonUtils.objectToJson(itemDesc));
	            redisDao.expire(key, REDIS_ITEM_EXPIRE);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return MytaoResult.ok(itemDesc);
	    }

	    @Override
	    public MytaoResult getItemParam(long itemId) {
	        //该商品redis中商品参数对应的key
	        String key = REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_PARAM_KEY;

	        //读取缓存
	        try {
	            String json = redisDao.get(key);
	            if (!StringUtils.isBlank(json)) {
	                TbItemParamItem itemParamItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
	                return MytaoResult.ok(itemParamItem);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        //查询基本信息
	        TbItemParamItem itemParamItem = null;

	        TbItemParamItemExample example = new TbItemParamItemExample();
	        Criteria criteria = example.createCriteria();
	        criteria.andItemIdEqualTo(itemId);
	        List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
	        if (list != null && list.size() > 0) {
	            itemParamItem = list.get(0);
	        }

	        //写入缓存
	        try {
	            redisDao.set(key, JsonUtils.objectToJson(itemParamItem));
	            redisDao.expire(key, REDIS_ITEM_EXPIRE);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return MytaoResult.ok(itemParamItem);
	    }

	}
	
