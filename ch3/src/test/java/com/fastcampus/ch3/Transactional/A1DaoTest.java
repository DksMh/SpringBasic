package com.fastcampus.ch3.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class A1DaoTest {
    @Autowired
    A1Dao a1Dao;

    @Autowired
    DataSource ds;

    @Autowired
    DataSourceTransactionManager tm;

    @Test
    public void insertTest() throws Exception {
        // TxManager 생성
        // PlatformTransactionManager tm = new DataSourceTransactionManager(ds);
        TransactionStatus status = tm.getTransaction(new DefaultTransactionDefinition());
        // Tx 시작
        try {
            a1Dao.deleteAll();
            a1Dao.insert(1,100); // 성공
            a1Dao.insert(2,200);
            // a1Dao.insert(1,200); // 실패
            tm.commit(status);
        } catch(Exception e) {
            e.printStackTrace();
            tm.rollback(status);
        }finally {

        }
    }
    // 같은 트랜잭션으로 묶여있어 성공했어도 롤백이 되어 아무 것도 안들어간다

}