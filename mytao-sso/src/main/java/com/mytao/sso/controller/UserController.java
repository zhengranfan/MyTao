package com.mytao.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mytao.common.pojo.MytaoResult;
import com.mytao.common.utils.ExceptionUtil;
import com.mytao.pojo.TbUser;
import com.mytao.sso.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public Object checkData(@PathVariable String param, @PathVariable Integer type, String callback) {
    

       MytaoResult result = null;

        //参数有效性校验
        if (StringUtils.isBlank(param)) {
            result =MytaoResult.build(400, "内容不能为空");
        }
        if (type == null) {
            result = MytaoResult.build(400, "类型不能为空");
        } else if (type != 1 && type != 2 && type != 3) {
            result = MytaoResult.build(400, "类型错误");
        }
        //校验出错
        if (result != null) {
            return getResult(callback, result);

        }

        //调用服务
        try {
            result = userService.checkData(param, type);
        } catch (Exception e) {
            result = MytaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }

        return getResult(callback, result);
    }
    /**
     * jsonp调用则返回包含TaoTaoResult的js片段，否则返回TaoTaoResult
     */
    private Object getResult(String callback, MytaoResult result) {
        if (callback != null) {
            //jsonp调用
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
           
            return mappingJacksonValue;
        } else {
            return result;
        }
    }
    
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public MytaoResult addUser(TbUser user) {
   
        try {
        	MytaoResult result = userService.addUser(user);
            return result;
        } catch (Exception e) {
            return MytaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public MytaoResult userLogin(HttpServletRequest request, HttpServletResponse response,String username, String password) {
        try {
        	MytaoResult result = userService.userLogin(request,response,username, password);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return MytaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/token/{token}")
    @ResponseBody
    public Object getUser(@PathVariable String token, String callback) {
    	MytaoResult result = null;
        try {
            result = userService.getUser(token);
        } catch (Exception e) {
            e.printStackTrace();
            result = MytaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }

        return getResult(callback, result);
    }
    
    @RequestMapping("/logout/{token}")
    @ResponseBody
    public Object userLogout(HttpServletRequest request, HttpServletResponse response,@PathVariable String token, String callback) {

        MytaoResult result = null;
        try {
            result = userService.userLogout(request,response,token);
        } catch (Exception e) {
            e.printStackTrace();
            result = MytaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }

        return getResult(callback, result);
    }


    
}

