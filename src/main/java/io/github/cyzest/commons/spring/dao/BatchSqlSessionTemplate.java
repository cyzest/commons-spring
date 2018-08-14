package io.github.cyzest.commons.spring.dao;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

/**
 * MyBatis Batch SqlSessionTemplate
 */
public class BatchSqlSessionTemplate extends SqlSessionTemplate {

    public BatchSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory, ExecutorType.BATCH);
    }

}
