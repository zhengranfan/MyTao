package com.mytao.rest.service.iml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mytao.common.pojo.MytaoResult;
import com.mytao.common.utils.ExceptionUtil;
import com.mytao.rest.dao.JedisClient;
import com.mytao.rest.service.RedisService;

@Service
public class RedisServiceimpl implements RedisService {

	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${INDEX_CONTENT_KET}")
	private String INDEX_CONTENT_KET;
	
	@Override
	public MytaoResult syncContent(long contentCid) {
	
		try {
			jedisClient.hdel(INDEX_CONTENT_KET , contentCid+"");
			
		} catch (Exception e) {
			e.printStackTrace();
			return MytaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		
		
		return MytaoResult.ok();
	}

}
