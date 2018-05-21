package com.syl.tb.search.controller;

import com.syl.tb.search.bean.Item;
import com.syl.tb.search.bean.SearchResult;
import com.syl.tb.search.service.ItemSearchService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class SearchController {


    public static final Integer ROWS = 32;
    @Autowired
    private ItemSearchService itemSearchService;

    @RequestMapping(value = "search",method = RequestMethod.GET)
    public ModelAndView search(@RequestParam("q")String keyWords,
                               @RequestParam(value = "page",defaultValue = "1")Integer page){
        ModelAndView mv = new ModelAndView("search");
        mv.addObject("query",keyWords);

        SearchResult searchResult = null;
        try {
            keyWords = new String(keyWords.getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            keyWords="";
        }
        try {
            searchResult = itemSearchService.search(keyWords,page,ROWS);
        } catch (SolrServerException e) {
            e.printStackTrace();
            searchResult = new SearchResult(0L,null);
        }

        mv.addObject("itemList",searchResult.getList());

        mv.addObject("page",page);

        int pages = searchResult.getTotal().intValue() % ROWS == 0 ? searchResult.getTotal().intValue()/ROWS : searchResult.getTotal().intValue()/ROWS+1;
        mv.addObject("pages",pages);

        return mv;
    }
}
