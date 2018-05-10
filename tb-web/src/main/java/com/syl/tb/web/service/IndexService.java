package com.syl.tb.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.syl.tb.common.bean.Result;
import com.syl.tb.manage.pojo.Content;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndexService {

    @Autowired
    private ApiService apiService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Value("${TB_MANAGE_URL}")
    private String TB_MANAGE_URL;
    @Value("${INDEX_AD1_URL}")
    private String INDEX_AD1_URL;
    @Value("${INDEX_AD2_URL}")
    private String INDEX_AD2_URL;
    public String queryIndexAD1(){
        try {
            String url = TB_MANAGE_URL+INDEX_AD1_URL;
            String jsonData = null;
            jsonData = apiService.doGet(url);
            if (StringUtils.isEmpty(jsonData)){
                return null;
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            ArrayNode rows = (ArrayNode) jsonNode.get("rows");
            List<Map<String,Object>> result = new ArrayList<>();
            for (JsonNode item : rows){
                Map<String ,Object> map = new LinkedHashMap();
                map.put("srcB",item.get("pic").asText());
                map.put("height",240);
                map.put("alt",item.get("title").asText());
                map.put("width",670);
                map.put("src",item.get("pic").asText());
                map.put("widthB",550);
                map.put("href",item.get("url").asText());
                map.put("heighB",240);
                result.add(map);
            }
            return MAPPER.writeValueAsString(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String queryIndexAD2() {
        try {
            String url = TB_MANAGE_URL+INDEX_AD2_URL;
            String jsonData = null;
            jsonData = apiService.doGet(url);
            if (StringUtils.isEmpty(jsonData)){
                return null;
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            ArrayNode rows = (ArrayNode) jsonNode.get("rows");
            List<Map<String,Object>> result = new ArrayList<>();
            for (JsonNode item : rows){
                Map<String ,Object> map = new LinkedHashMap();
                map.put("width",310);
                map.put("height",70);
                map.put("src",item.get("pic").asText());
                map.put("href",item.get("url").asText());
                map.put("alt",item.get("title").asText());
                map.put("widthB",210);
                map.put("heighB",70);
                map.put("srcB",item.get("pic").asText());
                result.add(map);
            }
            return MAPPER.writeValueAsString(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 封装
     * @return
     */
    public String queryIndexAD22() {
        try {
            String url = TB_MANAGE_URL+INDEX_AD2_URL;
            String jsonData = null;
            jsonData = apiService.doGet(url);
            if (StringUtils.isEmpty(jsonData)){
                return null;
            }
            Result r = Result.formatToList(jsonData, Content.class);
            List<Content> list = (List<Content>) r.getRows();
            List<Map<String,Object>> result = new ArrayList<>();
            for (Content item : list){
                Map<String ,Object> map = new LinkedHashMap();
                map.put("width",310);
                map.put("height",70);
                map.put("src",item.getPic());
                map.put("href",item.getUrl());
                map.put("alt",item.getTitle());
                map.put("widthB",210);
                map.put("heighB",70);
                map.put("srcB",item.getPic());
                result.add(map);
            }
            return MAPPER.writeValueAsString(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
