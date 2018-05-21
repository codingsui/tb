package com.syl.tb.search.service;

import com.syl.tb.search.bean.Item;
import com.syl.tb.search.bean.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ItemSearchService {

    @Autowired
    private HttpSolrServer httpSolrServer;

    public SearchResult search(String keyWords, Integer page, Integer rows) throws SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("title:"+keyWords+" AND status:1");
        solrQuery.setStart((Math.max(page,1)-1)*rows);
        solrQuery.setRows(rows);

        boolean isHighlighting = !StringUtils.equals("*",keyWords)&&StringUtils.isNotEmpty(keyWords);
        if (isHighlighting){
            solrQuery.setHighlight(true);
            solrQuery.addHighlightField("title");
            solrQuery.setHighlightSimplePre("<em>");
        }
        QueryResponse queryResponse = httpSolrServer.query(solrQuery);
        List<Item> items = queryResponse.getBeans(Item.class);
        if (isHighlighting){
            Map<String,Map<String,List<String>>> map = queryResponse.getHighlighting();
            for (Map.Entry<String,Map<String,List<String>>> highlighting:map.entrySet()){
                for (Item item:items){
                    if (!highlighting.getKey().equals(item.getId().toString())){
                        continue;
                    }
                    item.setTitle(StringUtils.join(highlighting.getValue().get("title"),""));
                    break;
                }
            }
        }
        return new SearchResult(queryResponse.getResults().getNumFound(),items);
    }
}
