package com.mytao.order.service;

import java.util.List;

import com.mytao.common.pojo.MytaoResult;
import com.mytao.pojo.TbOrder;
import com.mytao.pojo.TbOrderItem;
import com.mytao.pojo.TbOrderShipping;

public interface OrderService {

	MytaoResult createOrder(TbOrder order, List<TbOrderItem> itemList, TbOrderShipping orderShipping);
	
}
