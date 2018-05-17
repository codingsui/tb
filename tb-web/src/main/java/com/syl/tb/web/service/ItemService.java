package com.syl.tb.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.syl.tb.common.service.ApiService;
import com.syl.tb.common.service.RedisService;
import com.syl.tb.manage.pojo.Item;
import com.syl.tb.manage.pojo.ItemDesc;
import com.syl.tb.manage.pojo.ItemParamItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ItemService {

    @Autowired
    private ApiService apiService;

    @Autowired
    private RedisService redisService;

    public static final String REDIS_KEY = "TB_WEB_ITEM_DETAIL_";

    private static final Integer REDIS_TIME = 60 * 60 * 24;

    @Value("${TB_MANAGE_URL}")
    private String TB_MANAGE_URL;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public com.syl.tb.web.bean.Item queryById(Long itemId) {
        String key = REDIS_KEY +itemId;
        try {
            String cache = redisService.get(key);
            if (StringUtils.isNotEmpty(cache)){
                return MAPPER.readValue(cache,com.syl.tb.web.bean.Item.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        String url = TB_MANAGE_URL+"/api/item/"+itemId;
        System.out.println("url------------------"+url);
        try {
            String result = apiService.doGet(url);
            System.out.println("response_________________________");
            System.out.println(result);
            if (StringUtils.isEmpty(result)){
                return null;
            }
           try {
               redisService.set(key,result,REDIS_TIME);
           }catch (Exception e){
                e.printStackTrace();
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
            if (StringUtils.isEmpty(result)){
                return null;
            }
            return MAPPER.readValue(result,ItemDesc.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String queryItemParamItemByItemId(Long itemId) {
        String url = TB_MANAGE_URL + "/api/item/param/item/"+itemId;
        try {
            String result = apiService.doGet(url);
            if (StringUtils.isEmpty(result))
                return null;
            ItemParamItem item =  MAPPER.readValue(result,ItemParamItem.class);
            String json = item.getParamData();

            ArrayNode arrayNode = (ArrayNode) MAPPER.readTree(json);
            StringBuilder sb = new StringBuilder();
            sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" class=\"Ptable\"></tbody>");
            for (JsonNode param : arrayNode){
                sb.append("<tr><th class=\"tdTitle\" colspan=\"2\">" + param.get("group").asText() + "</th></tr>");
                ArrayNode params = (ArrayNode) param.get("params");
                for (JsonNode p : params){
                    sb.append("<tr><td class=\"tdTitle\">" + p.get("k").asText() + "</td><td>"
                        + p.get("v").asText() + "</td></tr>");
                }
            }
            sb.append("</tbody></table>");
            System.out.println(sb.toString());
            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
