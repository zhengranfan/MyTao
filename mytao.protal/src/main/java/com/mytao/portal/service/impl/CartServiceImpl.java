package com.mytao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mytao.common.pojo.MytaoResult;
import com.mytao.common.utils.CookieUtils;
import com.mytao.common.utils.HttpClientUtil;
import com.mytao.common.utils.JsonUtils;

import com.mytao.pojo.TbItem;
import com.mytao.portal.pojo.CartItem;
import com.mytao.portal.service.CartService;

@Service
public class CartServiceImpl implements CartService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${ITEM_BASE_URL}")
    private String ITEM_BASE_URL;
    @Value("${TT_CART}")
    private String TT_CART;

    @Override
    public MytaoResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
        //从cookie中取购物车商品列表
        List<CartItem> cartItemList = getCartItemList(request);

        //购物车中是否存在商品
        CartItem cartItem = null;
        for (CartItem item : cartItemList) {
            if (item.getId() == itemId) {
                cartItem = item;
                break;
            }
        }

        if (cartItem != null) {
            cartItem.setNum(cartItem.getNum() + num);
        } else {
            cartItem = new CartItem();

            //根据商品id查询商品基本信息
            String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_BASE_URL + itemId);
            if (!StringUtils.isBlank(json)) {
            	MytaoResult taotaoResult = MytaoResult.formatToPojo(json, TbItem.class);
                if (taotaoResult.getStatus() == 200) {
                    TbItem item = (TbItem) taotaoResult.getData();
                    cartItem.setId(item.getId());
                    cartItem.setTitle(item.getTitle());
                    cartItem.setImage(item.getImage() == null ? "" : item.getImage().split(",")[0]);
                    cartItem.setNum(num);
                    cartItem.setPrice(item.getPrice());
                }
                cartItemList.add(cartItem);
            }
        }

        //把购物车写入cookie
        CookieUtils.setCookie(request, response, TT_CART, JsonUtils.objectToJson(cartItemList), true);

        return MytaoResult.ok(cartItemList);

    }

    private List<CartItem> getCartItemList(HttpServletRequest request) {
        String json = CookieUtils.getCookieValue(request, TT_CART, true);
        if (StringUtils.isBlank(json)) {
            return new ArrayList<>();
        }

        try {
            List<CartItem> list = JsonUtils.jsonToList(json, CartItem.class);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    @Override
    public List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response) {
        return getCartItemList(request);

    }
    @Override
    public MytaoResult updateCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
        //从cookie中取购物车商品列表
        List<CartItem> cartItemList = getCartItemList(request);

        //购物车中是否存在商品
        CartItem cartItem = null;
        for (CartItem item : cartItemList) {
            if (item.getId() == itemId) {
                cartItem = item;
                break;
            }
        }

        if (cartItem != null) {
            cartItem.setNum(num);
        } else {
            cartItem = new CartItem();

            //根据商品id查询商品基本信息
            String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_BASE_URL + itemId);
            if (!StringUtils.isBlank(json)) {
            	MytaoResult taotaoResult = MytaoResult.formatToPojo(json, TbItem.class);
                if (taotaoResult.getStatus() == 200) {
                    TbItem item = (TbItem) taotaoResult.getData();
                    cartItem.setId(item.getId());
                    cartItem.setTitle(item.getTitle());
                    cartItem.setImage(item.getImage() == null ? "" : item.getImage().split(",")[0]);
                    cartItem.setNum(num);
                    cartItem.setPrice(item.getPrice());
                }
                cartItemList.add(cartItem);
            }
        }

        //把购物车写入cookie
        CookieUtils.setCookie(request, response, TT_CART, JsonUtils.objectToJson(cartItemList), true);

        return MytaoResult.ok();
    }

    @Override
    public MytaoResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response) {
        //从cookie中取购物车商品列表
        List<CartItem> itemList = getCartItemList(request);
        //从列表中找到此商品并删除
        for (CartItem cartItem : itemList) {
            if (cartItem.getId() == itemId) {
                itemList.remove(cartItem);
                break;
            }
        }

        //把购物车列表重新写入cookie
        CookieUtils.setCookie(request, response, TT_CART, JsonUtils.objectToJson(itemList), true);

        return MytaoResult.ok();

    }

}
