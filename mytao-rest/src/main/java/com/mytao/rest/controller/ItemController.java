package com.mytao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mytao.common.pojo.MytaoResult;
import com.mytao.rest.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/base/{itemId}")
    @ResponseBody
    public MytaoResult getItemBaseInfo(@PathVariable Long itemId) {
    	MytaoResult result = itemService.getItemBaseInfo(itemId);
        return result;
    }

    @RequestMapping("/desc/{itemId}")
    @ResponseBody
    public MytaoResult getItemDesc(@PathVariable Long itemId) {
    	MytaoResult result = itemService.getItemDesc(itemId);
        return result;
    }

    @RequestMapping("/param/{itemId}")
    @ResponseBody
    public MytaoResult getItemParam(@PathVariable Long itemId) {
    	MytaoResult result = itemService.getItemParam(itemId);
        return result;
    }

}
