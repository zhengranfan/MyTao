package com.mytao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mytao.common.pojo.MytaoResult;
import com.mytao.common.utils.HttpClientUtil;
import com.mytao.common.utils.JsonUtils;
import com.mytao.pojo.TbItem;
import com.mytao.pojo.TbItemDesc;
import com.mytao.pojo.TbItemParamItem;
import com.mytao.portal.pojo.Item;
import com.mytao.portal.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${ITEM_BASE_URL}")
    private String ITEM_BASE_URL;
    @Value("${ITEM_DESC_URL}")
    private String ITEM_DESC_URL;
    @Value("${ITEM_PARAM_URL}")
    private String ITEM_PARAM_URL;

    @Override
    public Item getItemBase(long itemId) {
        try {
            String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_BASE_URL + itemId);
            if (!StringUtils.isBlank(json)) {
                MytaoResult taotaoResult = MytaoResult.formatToPojo(json, TbItem.class);
                if (taotaoResult.getStatus() == 200) {
                    TbItem tbItem = (TbItem) taotaoResult.getData();

                    Item item = new Item();
                    item.setImage(tbItem.getImage());
                    item.setId(String.valueOf(tbItem.getId()));
                    item.setPrice(tbItem.getPrice());
                    item.setSellPoint(tbItem.getSellPoint());
                    item.setTitle(tbItem.getTitle());

                    return item;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public String getItemDesc(Long itemId) {
        try {
            String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_DESC_URL + itemId);
            if (!StringUtils.isBlank(json)) {
            	MytaoResult taotaoResult = MytaoResult.formatToPojo(json, TbItemDesc.class);
                if (taotaoResult.getStatus() == 200) {
                    TbItemDesc itemDesc = (TbItemDesc) taotaoResult.getData();
                    String desc = itemDesc.getItemDesc();
                    return desc;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public String getItemParam(Long itemId) {
        try {
            String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_PARAM_URL + itemId);
            if (!StringUtils.isBlank(json)) {
            	MytaoResult taotaoResult = MytaoResult.formatToPojo(json, TbItemParamItem.class);
                if (taotaoResult.getStatus() == 200) {
                    TbItemParamItem itemParamItem = (TbItemParamItem) taotaoResult.getData();
                    String paramData = itemParamItem.getParamData();

                    //生成html
                    List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
                    StringBuffer sb = new StringBuffer();
                    sb.append(
                            "<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
                    sb.append("    <tbody>\n");
                    for (Map m1 : jsonList) {
                        sb.append("        <tr>\n");
                        sb.append("            <th class=\"tdTitle\" colspan=\"2\">" + m1.get("group") + "</th>\n");
                        sb.append("        </tr>\n");
                        List<Map> list2 = (List<Map>) m1.get("params");
                        for (Map m2 : list2) {
                            sb.append("        <tr>\n");
                            sb.append("            <td class=\"tdTitle\">" + m2.get("k") + "</td>\n");
                            sb.append("            <td>" + m2.get("v") + "</td>\n");
                            sb.append("        </tr>\n");
                        }
                    }
                    sb.append("    </tbody>\n");
                    sb.append("</table>");
                    //返回html片段
                    return sb.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
        
    }
    
}
