package com.syl.tb.manage.web;

import com.syl.tb.manage.pojo.Content;
import com.syl.tb.manage.pojo.ContentCategory;
import com.syl.tb.manage.service.impl.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("content/category")
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ContentCategory>> queryListById(@RequestParam(value = "id",defaultValue = "0")Long pid){
        try {
            ContentCategory contentCategory = new ContentCategory();
            contentCategory.setParentId(pid);
            List<ContentCategory> list = contentCategoryService.queryListByWhere(contentCategory);
            if (null == list || list.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ContentCategory> saveContentCategory(ContentCategory contentCategory) {
        try {
            boolean b = contentCategoryService.saveContentCategory(contentCategory);
            if (b){
                return ResponseEntity.status(HttpStatus.CREATED).body(contentCategory);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateContentCategory(
            @RequestParam("id") Long id,@RequestParam("name") String name) {
        try {
            ContentCategory c = new ContentCategory();
            c.setId(id);
            c.setName(name);
            contentCategoryService.updateSelective(c);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> updateContentCategory(ContentCategory contentCategory){
        try {
            contentCategoryService.deleteAllById(contentCategory);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
