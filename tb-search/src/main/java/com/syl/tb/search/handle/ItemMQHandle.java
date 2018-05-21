package com.syl.tb.search.handle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syl.tb.common.service.RedisService;
import com.syl.tb.search.bean.Item;
import com.syl.tb.search.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class ItemMQHandle {


    @Autowired
    private HttpSolrServer httpSolrServer;
    @Autowired
    ItemService itemService;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public void execute(String msg){
        try {
            JsonNode jsonNode = MAPPER.readTree(msg);

            Long itemId = jsonNode.get("itemId").asLong();
            String type = jsonNode.get("type").asText();
            if (StringUtils.equals(type,"insert")||StringUtils.equals(type,"update")){
                Item item = itemService.queryById(itemId);
                httpSolrServer.addBean(item);
                httpSolrServer.commit();
            }else if (StringUtils.equals(type,"delete")){
                httpSolrServer.deleteById(String.valueOf(itemId));
                httpSolrServer.commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
