package com.syl.tb.manage.service.dao;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
//import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("dao")
public class Dao implements IDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 保存对象
     *
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Object save(String str, Object obj) throws Exception {
        return sqlSessionTemplate.insert(str, obj);
    }

    /**
     * 保存对象,获取原值
     *
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public Object saveBack(String str, Object obj) throws Exception {
        sqlSessionTemplate.insert(str, obj);
        return obj;
    }

    /**
     * 批量更新
     *
     * @param str
     * @param objs
     * @return
     * @throws Exception
     */
    public Object batchSave(String str, List<?> objs) throws Exception {
        return sqlSessionTemplate.insert(str, objs);
    }

    /**
     * 修改对象
     *
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Object update(String str, Object obj) throws Exception {
        return sqlSessionTemplate.update(str, obj);
    }

    /**
     * 批量更新
     *
     * @param str
     * @param objs
     * @return
     * @throws Exception
     */
    public void batchUpdate(String str, List<?> objs) throws Exception {
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        // 批量执行器
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        try {
            if (objs != null) {
                for (int i = 0, size = objs.size(); i < size; i++) {
                    sqlSession.update(str, objs.get(i));
                }
                sqlSession.flushStatements();
                sqlSession.commit();
                sqlSession.clearCache();
            }
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 批量删除
     *
     * @param str
     * @param objs
     * @return
     * @throws Exception
     */
    public Object batchDelete(String str, List<?> objs) throws Exception {
        return sqlSessionTemplate.delete(str, objs);
    }

    /**
     * 删除对象
     *
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Object delete(String str, Object obj) throws Exception {
        return sqlSessionTemplate.delete(str, obj);
    }

    /**
     * 查找对象
     *
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Object findForObject(String str, Object obj) throws Exception {
        return sqlSessionTemplate.selectOne(str, obj);
    }

    /**
     * 查找对象
     *
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Object findForList(String str, Object obj) throws Exception {
        return sqlSessionTemplate.selectList(str, obj);
    }

    /**
     * 查找对象
     *
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Object findForMap(String str, Object obj, String key, String value) throws Exception {
        return sqlSessionTemplate.selectMap(str, obj, key);
    }


}
