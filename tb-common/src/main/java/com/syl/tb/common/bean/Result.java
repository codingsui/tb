package com.syl.tb.common.bean;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.beans.Transient;
import java.util.List;

public class Result {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private Long total;
    private List<?> rows;

    public Result(Long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }
    public Result(Integer total, List<?> rows) {
        this.total = Long.valueOf(total);
        this.rows = rows;
    }
    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public static Result formatToList(String jsonData,Class<?> clazz){
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("rows");
            List<?> list = null;
            if (data.isArray() && data.size() >0){
                list = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class,clazz));
            }
            return new Result(jsonNode.get("total").intValue(),list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
