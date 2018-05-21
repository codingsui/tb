package com.syl.tb.cart.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syl.tb.cart.pojo.Item;
import com.syl.tb.common.service.ApiService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ItemService {

    @Autowired
    private ApiService apiService;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public Item queryById(Long itemId){
        String url = "http://localhost:8080/api/item/"+itemId;
        try {
            String jsonData = apiService.doGet(url);
            if (StringUtils.isNotEmpty(jsonData)){
                return MAPPER.readValue(jsonData,Item.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
