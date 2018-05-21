package com.syl.tb.web.handle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syl.tb.common.service.RedisService;
import com.syl.tb.web.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class ItemMQHandle {

    @Autowired
    private RedisService redisService;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public void execute(String msg){
        try {
            JsonNode jsonNode = MAPPER.readTree(msg);
            Long itemId = jsonNode.get("itemId").asLong();
            String key = ItemService.REDIS_KEY + itemId;
            this.redisService.del(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
