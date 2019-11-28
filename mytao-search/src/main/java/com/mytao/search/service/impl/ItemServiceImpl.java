package com.mytao.search.service.impl;

import java.awt.ItemSelectable;
import java.awt.event.ItemListener;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mytao.common.pojo.MytaoResult;
import com.mytao.common.utils.ExceptionUtil;
import com.mytao.search.mapper.ItemMapper;
import com.mytao.search.pojo.Item;
import com.mytao.search.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SolrServer solrServer;

    @Override
    public MytaoResult importAllItems() {
        //从mysql中查询商品
        List<Item> itemList = itemMapper.getItemList();

        try {
            //将商品写入solr的索引
            for (Item item : itemList) {
                SolrInputDocument document = new SolrInputDocument();

                document.setField("id", item.getId());
                document.setField("item_title", item.getTitle());
                document.setField("item_sell_point", item.getSellPoint());
                document.setField("item_price", item.getPrice());
                document.setField("item_image", item.getImage());
                document.setField("item_category_name", item.getCategoryName());
                document.setField("item_desc", item.getItemDesc());

                solrServer.add(document);
            }
            //提交修改
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return MytaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }

        return MytaoResult.ok();
    }
}

