package io.github.cyzest.commons.spring.dao;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * MyBatis DAO 추상 클래스
 */
public abstract class MyBatisDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    private BatchSqlSessionTemplate batchSqlSessionTemplate;
    
    protected final SqlSession getSqlSession() {
        return sqlSessionTemplate;
    }

    protected final SqlSession getBatchSqlSession() {
        return batchSqlSessionTemplate;
    }
    
    protected final String getQueryStatement(String queryId) {
        return this.getClass().getName()+"."+queryId;
    }

}