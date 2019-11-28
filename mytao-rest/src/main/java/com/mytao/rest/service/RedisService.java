package com.mytao.rest.service;

import com.mytao.common.pojo.MytaoResult;

public interface RedisService {

		MytaoResult syncContent(long contentCid);
	
}
