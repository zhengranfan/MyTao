package com.mytao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mytao.common.pojo.EUDataGridResult;
import com.mytao.mapper.TbItemMapper;
import com.mytao.pojo.TbItem;
import com.mytao.pojo.TbItemExample;
import com.mytao.pojo.TbItemExample.Criteria;
import com.mytao.service.ItemService;

@Service
public class ItermServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;

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

}
