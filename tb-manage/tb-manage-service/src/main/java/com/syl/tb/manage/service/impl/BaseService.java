package com.syl.tb.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syl.tb.manage.pojo.BasePojo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

public class BaseService<T extends BasePojo> {

    protected Logger logger = Logger.getLogger(this.getClass());



    @Autowired
    protected Mapper<T> mapper;

    public T queryById(Long id){
        return mapper.selectByPrimaryKey(id);
    }

    public List<T> queryAll(){
        return mapper.selectAll();
    }

    public T quertOne(T record){
        return mapper.selectOne(record);
    }

    public List<T> queryListByWhere(T t){
        logger.info("++++"+t);

        return mapper.select(t);
    }

    public PageInfo<T> queryPageListByWhere(Integer page,Integer rows){
        PageHelper.startPage(page,rows);
        List<T> list = mapper.selectAll();
        return new PageInfo<>(list);
    }

    public Integer saveSelective(T t){
        t.setCreated(new Date());
        t.setUpdated(t.getCreated());
        return mapper.insertSelective(t);
    }
    public Integer save(T t){
        t.setCreated(new Date());
        t.setUpdated(t.getCreated());
        return mapper.insert(t);
    }
    public Integer update(T t){
        t.setUpdated(new Date());
        t.setCreated(null);
        return mapper.updateByPrimaryKey(t);
    }
    public Integer updateSelective(T t){
        t.setUpdated(new Date());
        t.setCreated(null);
        return mapper.updateByPrimaryKeySelective(t);
    }

    public Integer deleteById(Long id){
        return mapper.deleteByPrimaryKey(id);
    }

    public Integer deleteByIds(List<Object> list,Class<T> clazz,String property){
        Example example = new Example(clazz);
        example.createCriteria().andIn(property,list);
        return mapper.deleteByExample(example);
    }

    public Integer deleteByWhere(T t){
        return mapper.delete(t);
    }
}
