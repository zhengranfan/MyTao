package com.mytao.portal.service;

import com.mytao.pojo.TbUser;

public interface UserService {
	TbUser getUser(String token);
}
