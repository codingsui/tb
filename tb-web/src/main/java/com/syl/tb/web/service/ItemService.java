package com.syl.tb.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syl.tb.manage.pojo.Item;
import com.syl.tb.manage.pojo.ItemDesc;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ItemService {

    @Autowired
    private ApiService apiService;


    @Value("${TB_MANAGE_URL}")
    private String TB_MANAGE_URL;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public com.syl.tb.web.bean.Item queryById(Long itemId) {
        String url = TB_MANAGE_URL+"/api/item/"+itemId;
        System.out.println("url------------------"+url);
        try {
            String result = apiService.doGet(url);
            System.out.println("response_________________________");
            System.out.println(result);
            if (StringUtils.isEmpty(result)){
                return null;
            }
            return MAPPER.readValue(result, com.syl.tb.web.bean.Item.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ItemDesc queryDescByItemId(Long itemId) {
        String url = TB_MANAGE_URL+"/api/item/desc/"+itemId;
        try {
            String result = apiService.doGet(url);
            return MAPPER.readValue(result,ItemDesc.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
