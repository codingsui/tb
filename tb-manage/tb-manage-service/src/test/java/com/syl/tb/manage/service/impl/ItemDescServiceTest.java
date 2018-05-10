package com.syl.tb.manage.service.impl;

import com.syl.tb.manage.pojo.ItemDesc;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations ={"classpath:spring/spring-*.xml"})
public class ItemDescServiceTest {
    @Autowired
    private ItemDescService itemDescService;
    @Test
    public void test(){
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemDesc("das");
        itemDesc.setItemId(100L);

        Integer count = itemDescService.save(itemDesc);

        EasyMock.expect(count).andReturn(1);

        
    }

}