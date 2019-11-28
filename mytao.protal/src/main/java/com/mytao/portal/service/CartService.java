package com.mytao.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mytao.common.pojo.MytaoResult;
import com.mytao.portal.pojo.CartItem;



public interface CartService {

	MytaoResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response);

	List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response);

	MytaoResult updateCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response);

	MytaoResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response);

}
