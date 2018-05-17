package com.syl.tb.manage.service.impl;

import com.syl.tb.manage.pojo.ContentCategory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentCategoryService extends BaseService<ContentCategory> {

    public boolean saveContentCategory(ContentCategory contentCategory) {
        contentCategory.setId(null);
        contentCategory.setIsParent(false);
        contentCategory.setSortOrder(1);
        contentCategory.setStatus(1);
        int count1 = save(contentCategory);

        //判断父节点是否为true
        ContentCategory parent = queryById(contentCategory.getParentId());
        int count2 = 0;
        if (!parent.getIsParent()){
            parent.setIsParent(true);
            count2 = update(parent);
        }
        return count1 == 1 && count2 ==1;
    }

    public void deleteAllById(ContentCategory contentCategory) {
        List<Object> ids = new ArrayList<>();
        ids.add(contentCategory.getId());
        this.findAllChild(ids,contentCategory.getId());
        deleteByIds(ids,ContentCategory.class,"id");

        //判断是否还有兄弟节点
        ContentCategory c = new ContentCategory();
        c.setParentId(c.getParentId());
        List<ContentCategory> l = queryListByWhere(c);
        if (l == null || l.isEmpty()){
            ContentCategory parent = new ContentCategory();
            parent.setId(c.getParentId());
            parent.setIsParent(false);
            updateSelective(parent);
        }
    }

    private void findAllChild(List<Object> ids,Long id){
        ContentCategory c = new ContentCategory();
        c.setParentId(id);
        List<ContentCategory> list = queryListByWhere(c);
        for (ContentCategory item : list){
            ids.add(item.getId());
            if (item.getIsParent()){
                findAllChild(ids,item.getId());
            }
        }
    }
}
