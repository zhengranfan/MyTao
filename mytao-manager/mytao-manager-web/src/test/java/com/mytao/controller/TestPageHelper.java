package com.mytao.controller;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mytao.mapper.TbItemMapper;
import com.mytao.pojo.TbItem;
import com.mytao.pojo.TbItemExample;

public class TestPageHelper {

	
	@Test
	public void  testPageHelper() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		TbItemMapper tbItemMapper =applicationContext.getBean(TbItemMapper.class);
		
		TbItemExample example = new TbItemExample();
		PageHelper.startPage(2, 10);
		
		List<TbItem> list = tbItemMapper.selectByExample(example);
		for(TbItem tbItem : list) {
			System.out.println(tbItem.getTitle());		
		}
		
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
		long total = pageInfo.getTotal();
		System.out.println("共有商品信息：" + total);
		
	}
	
}
