package com.mytao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mytao.common.pojo.EUDataGridResult;
import com.mytao.common.pojo.MytaoResult;
import com.mytao.mapper.TbItemCatMapper;
import com.mytao.mapper.TbItemParamMapper;
import com.mytao.pojo.ItemParamWithName;
import com.mytao.pojo.TbItemCat;
import com.mytao.pojo.TbItemCatExample;
import com.mytao.pojo.TbItemParam;
import com.mytao.pojo.TbItemParamExample;
import com.mytao.pojo.TbItemParamExample.Criteria;
import com.mytao.service.ItemParamService;

/**
 * 
 * @author ia
 *
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {

	
	
	@Autowired
	private TbItemParamMapper itemParamMapper;
	
	@Autowired TbItemCatMapper itemCatMapper;
	
	
	
	@Override
	public MytaoResult getItemParamByCid(long cid) {
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		if(list != null&& list.size() > 0) {
			return MytaoResult.ok(list.get(0));
		}
		
		
		return MytaoResult.ok();
	}



	@Override
	public MytaoResult insertItemParam(TbItemParam itemParam) {
		
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		itemParamMapper.insert(itemParam);
		
		return MytaoResult.ok();
	}
	
	@Override
	  public EUDataGridResult getItemParamList(int page, int rows) {
	        //分页处理
	        PageHelper.startPage(page, rows);
	        List<TbItemParam> paramList = itemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());

	        //查询规格对应的名称并封装
	        List<ItemParamWithName> paramWithNameList = new ArrayList<>(paramList.size());
	        for (TbItemParam itemParam : paramList) {
	            String name = getItemCatName(itemParam.getItemCatId());
	            paramWithNameList.add(new ItemParamWithName(itemParam, name));
	        }

	        //总记录条数
	        PageInfo<TbItemParam> pageInfo = new PageInfo<>(paramList);
	        long total = pageInfo.getTotal();

	        EUDataGridResult result = new EUDataGridResult();
	        result.setTotal(total);
	        result.setRows(paramWithNameList);

	        return result;

	    }
	    private String getItemCatName(long itemCatId) {
	        TbItemCatExample example = new TbItemCatExample();
	        TbItemCatExample.Criteria criteria = example.createCriteria();
	        criteria.andIdEqualTo(itemCatId);

	        List<TbItemCat> list = itemCatMapper.selectByExample(example);
	        if (list != null && !list.isEmpty()) {
	            return list.get(0).getName();
	        }
	        return null;
	    }

	

}
