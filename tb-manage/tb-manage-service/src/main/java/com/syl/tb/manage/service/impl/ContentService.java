package com.syl.tb.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syl.tb.common.bean.Result;
import com.syl.tb.manage.mapper.ContentMapper;
import com.syl.tb.manage.pojo.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentService extends BaseService<Content> {

    @Autowired
    private ContentMapper contentMapper;

    public Result queryContentList(Long id, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        List<Content> list = contentMapper.queryContentList(id);
        PageInfo<Content> pageInfo = new PageInfo<>(list);
        return new Result(pageInfo.getTotal(),pageInfo.getList());
    }
}
