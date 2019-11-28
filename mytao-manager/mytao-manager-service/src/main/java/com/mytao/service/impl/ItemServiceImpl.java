package com.mytao.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mytao.common.pojo.EUDataGridResult;
import com.mytao.common.pojo.MytaoResult;
import com.mytao.common.utils.IDUtils;
import com.mytao.mapper.TbItemDescMapper;
import com.mytao.mapper.TbItemMapper;
import com.mytao.mapper.TbItemParamItemMapper;
import com.mytao.mapper.TbItemParamMapper;
import com.mytao.pojo.TbItem;
import com.mytao.pojo.TbItemDesc;
import com.mytao.pojo.TbItemDescExample;
import com.mytao.pojo.TbItemExample;
import com.mytao.pojo.TbItemParamItem;
import com.mytao.pojo.TbItemExample.Criteria;
import com.mytao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;

	@Autowired
	private TbItemDescMapper itemDescmapper;

	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public TbItem getItemById(long itemId) {
//		TbItem item =itemMapper.selectByPrimaryKey(itemId);

		TbItemExample tbItemExample = new TbItemExample();
		// 添加查询条件
		Criteria criteria = tbItemExample.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(tbItemExample);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public EUDataGridResult getItemList(int page, int rows) {

		// 查询商品列表
		TbItemExample tbItemExample = new TbItemExample();
		// 分页处理
		PageHelper.startPage(page, rows);

		List<TbItem> list = itemMapper.selectByExample(tbItemExample);
		// 创建一个返回值对象
		EUDataGridResult euDataGridResult = new EUDataGridResult();
		euDataGridResult.setRows(list);

		// 曲记录总数
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
		euDataGridResult.setTotal(pageInfo.getTotal());
		return euDataGridResult;

	}

	@Override
	public MytaoResult createItem(TbItem tbItem, String desc, String itemParams) {
		// 补全Item
		Long itemId = IDUtils.genItemId();
		tbItem.setId(itemId);
		tbItem.setStatus((byte) 1);
		tbItem.setCreated(new Date());
		tbItem.setUpdated(new Date());

		// 插入数据库

		itemMapper.insert(tbItem);
		MytaoResult result = insertItemDesc(itemId, desc);
		if (result.getStatus() != 200) {
			throw new RuntimeException();
		}
		result = insetItemParamItem(itemId, itemParams);
		if (result.getStatus() != 200) {
			throw new RuntimeException();
		}

		return MytaoResult.ok();
	}

	private MytaoResult insertItemDesc(Long itemId, String desc) {
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(itemId);
		tbItemDesc.setCreated(new Date());
		tbItemDesc.setUpdated(new Date());
		tbItemDesc.setItemDesc(desc);
		itemDescmapper.insert(tbItemDesc);
		return MytaoResult.ok();
	}

	private MytaoResult insetItemParamItem(Long itemId, String itemParam) {
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setCreated(new Date());
		itemParamItem.setUpdated(new Date());
		itemParamItemMapper.insert(itemParamItem);

		return MytaoResult.ok();

	}

}
