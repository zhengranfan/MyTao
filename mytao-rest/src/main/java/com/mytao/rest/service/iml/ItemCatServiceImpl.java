package com.mytao.rest.service.iml;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mytao.mapper.TbItemCatMapper;
import com.mytao.pojo.TbItemCat;
import com.mytao.pojo.TbItemCatExample;
import com.mytao.pojo.TbItemCatExample.Criteria;
import com.mytao.rest.pojo.CatNode;
import com.mytao.rest.pojo.CatResult;
import com.mytao.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
		
	private List<Object> getList(long parentId){	
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> itemCats =itemCatMapper.selectByExample(example);
		List<Object> resultList = new ArrayList<Object>();
		int count = 0;
		for(TbItemCat itemCat : itemCats) {
		
			if(itemCat.getIsParent()) {		
				CatNode catNode = new CatNode();	
				if(parentId == 0) {
					catNode.setName("<a href='/products/"+itemCat.getId()+".html'>"+ itemCat.getName()+"</a>");
				}else {
					catNode.setName(itemCat.getName());
				}
				catNode.setUrl("/products/"+itemCat.getId()+".html");
				catNode.setItem(getList(itemCat.getId()));
				
				resultList.add(catNode);
				count ++;
				//只取14条记录
				if(parentId == 0 && count >= 14) {
					break;
				}
				
			}else {	
				resultList.add("/products/"+itemCat.getId()+".html|" + itemCat.getName());
			}
			
		}
	
		return resultList;
	}
	
	
	
	
	@Override
	public CatResult getItemCatList() {
		CatResult catResult = new CatResult();
		//查询分类列表
		catResult.setData(getList(0));
		return catResult;

	}
	
	/**
	 * 查询分类列表
	 * <p>Title: getCatList</p>
	 * <p>Description: </p>
	 * @param parentId
	 * @return
	 */
	private List<?> getCatList(long parentId) {
		//创建查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		//返回值list
		List resultList = new ArrayList<>();
		//向list中添加节点
		for (TbItemCat tbItemCat : list) {
			//判断是否为父节点
			if (tbItemCat.getIsParent()) {
				CatNode catNode = new CatNode();
				if (parentId == 0) {
					catNode.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
				} else {
					catNode.setName(tbItemCat.getName());
				}
				catNode.setUrl("/products/"+tbItemCat.getId()+".html");
				catNode.setItem(getCatList(tbItemCat.getId()));
				
				resultList.add(catNode);
			//如果是叶子节点
			} else {
				resultList.add("/products/"+tbItemCat.getId()+".html|" + tbItemCat.getName());
			}
		}
		return resultList;
	}

}
