package com.mytao.portal.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mytao.portal.pojo.SearchResult;
import com.mytao.portal.service.SearchService;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")
    public String search(@RequestParam("q") String queryString, @RequestParam(defaultValue = "1") Integer page,
            Model model) {
    
    	
    	try {
			queryString = new String(queryString.getBytes("iso8859-1"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        SearchResult searchResult = searchService.search(queryString, page);

        model.addAttribute("query", queryString);
        model.addAttribute("totalPages", searchResult.getPageCount());
        model.addAttribute("itemList", searchResult.getItemList());
        model.addAttribute("page", page);

        return "search";
    }
}

