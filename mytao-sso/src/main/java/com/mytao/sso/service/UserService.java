package com.mytao.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mytao.common.pojo.MytaoResult;
import com.mytao.pojo.TbUser;

public interface UserService {
	MytaoResult checkData(String content, Integer type);
	MytaoResult addUser(TbUser user);
	MytaoResult userLogin(HttpServletRequest request, HttpServletResponse response,String username, String password) ;
	MytaoResult getUser(String token) ;
	MytaoResult userLogout(HttpServletRequest request, HttpServletResponse response,String token);
}
