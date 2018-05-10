package com.syl.tb.manage.mapper;

import com.syl.tb.manage.pojo.Content;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ContentMapper extends Mapper<Content> {
    public List<Content> queryContentList(Long id);
}