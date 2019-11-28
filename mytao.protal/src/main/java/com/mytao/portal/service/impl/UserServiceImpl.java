package com.mytao.portal.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mytao.common.pojo.MytaoResult;
import com.mytao.common.utils.HttpClientUtil;
import com.mytao.pojo.TbUser;
import com.mytao.portal.service.UserService;

@Service
public class UserServiceImpl implements UserService {

   // private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${SSO_BASE_URL}")
    private String SSO_BASE_URL;
    @Value("${SSO_USER_TOKEN}")
    private String SSO_USER_TOKEN;

    @Override
    public TbUser getUser(String token) {
        try {
            String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN + token);
            if (!StringUtils.isBlank(json)) {
            	MytaoResult result = MytaoResult.formatToPojo(json, TbUser.class);
                if (result.getStatus() == 200) {
                    TbUser user = (TbUser) result.getData();
                    return user;
                }
            }
           // LOGGER.debug("get json from sso: {}", json);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }


}
