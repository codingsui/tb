package com.syl.tb.manage.web;

import com.syl.tb.common.bean.Result;
import com.syl.tb.manage.pojo.Content;
import com.syl.tb.manage.service.impl.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveContent(Content content){
        try {
            content.setId(null);
            contentService.save(content);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Result> queryContentList(@RequestParam("categoryId")Long id
            , @RequestParam(value = "page",defaultValue = "1")Integer page
            , @RequestParam(value = "rows",defaultValue = "30")Integer rows){
        try {
            Result result = contentService.queryContentList(id,page,rows);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
