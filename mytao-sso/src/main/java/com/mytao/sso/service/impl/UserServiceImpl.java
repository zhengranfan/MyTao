package com.mytao.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.mytao.common.pojo.MytaoResult;
import com.mytao.common.utils.CookieUtils;
import com.mytao.common.utils.JsonUtils;
import com.mytao.mapper.TbUserMapper;
import com.mytao.pojo.TbUser;
import com.mytao.pojo.TbUserExample;
import com.mytao.pojo.TbUserExample.Criteria;
import com.mytao.sso.dao.JedisClient;
import com.mytao.sso.service.UserService;


@Service
public class UserServiceImpl implements UserService {

    @Value("${REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;
    @Value("${SSO_SESSION_EXPIRE}")
    private Integer SSO_SESSION_EXPIRE;



    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private JedisClient redisDao;

    @Override
    public MytaoResult checkData(String content, Integer type) {
        //创建查询条件
        TbUserExample example = new TbUserExample();
        Criteria criteria = example.createCriteria();
        if (type == 1) {
            criteria.andUsernameEqualTo(content);
        } else if (type == 2) {
            criteria.andPhoneEqualTo(content);
        } else {
            criteria.andEmailEqualTo(content);
        }

        //执行查询
        Boolean canBeUse = null;
        List<TbUser> userList = userMapper.selectByExample(example);
        if (userList == null || userList.size() == 0) {
            canBeUse = true;
        } else {
            canBeUse = false;
        }
        return MytaoResult.ok(canBeUse);
    }
    
    @Override
    public MytaoResult addUser(TbUser user) {
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //密码md5化之后存储在数据库
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));

        userMapper.insert(user);

        return MytaoResult.ok();
    }
    
    @Override
    public MytaoResult userLogin(HttpServletRequest request, HttpServletResponse response,String username, String password) {
        //查询该用户
        TbUserExample example = new TbUserExample();
        Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> userList = userMapper.selectByExample(example);

        //用户不存在
        if (userList == null || userList.isEmpty()) {
            return MytaoResult.build(400, "用户名或密码错误");
        }

        TbUser user = userList.get(0);
        String md5 = DigestUtils.md5DigestAsHex(password.getBytes());
        //密码不匹配
        if (!user.getPassword().equals(md5)) {
            return MytaoResult.build(400, "用户名或密码错误");
        }

        //生成用户token
        String token = UUID.randomUUID().toString();
        //redis中需要清空用户的密码
        user.setPassword(null);
        //用户信息写入redis
        String key = REDIS_USER_SESSION_KEY + ":" + token;
        redisDao.set(key, JsonUtils.objectToJson(user));
        redisDao.expire(key, SSO_SESSION_EXPIRE);
		//添加写cookie的逻辑，cookie的有效期是关闭浏览器就失效。
		CookieUtils.setCookie(request, response, "TT_TOKEN", token);
        return MytaoResult.ok(token);
    }
    @Override
    public MytaoResult getUser(String token) {
        //从redis中查询用户信息
        String json = redisDao.get(REDIS_USER_SESSION_KEY + ":" + token);

        if (StringUtils.isBlank(json)) {
            return MytaoResult.build(400, "此session已经过期，请重新登录");
        }

        //更新过期时间
        redisDao.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);

        return MytaoResult.ok(JsonUtils.jsonToPojo(json, TbUser.class));

    }
    @Override
    public MytaoResult userLogout(HttpServletRequest request, HttpServletResponse response,String token) {
        //删除redis中的key
        String key = REDIS_USER_SESSION_KEY + ":" + token;
        redisDao.del(key);
        //删除cookie
        CookieUtils.deleteCookie(request, response, "TT_TOKEN");
        return MytaoResult.ok();
    }



}
