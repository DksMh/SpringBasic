package com.fastcampus.ch3.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

@Service
public class TxService {
    @Autowired A1Dao a1Dao;
    @Autowired B1Dao b1Dao;
    /*
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertA1withTx() throws Exception{
        a1Dao.insert(1,100); // 성공
        insertB1withTx();
        // a1Dao.insert(1,200); // 실패
        a1Dao.insert(2,200); // 성공
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertB1withTx() throws Exception{
        b1Dao.insert(1,100); // 성공
        b1Dao.insert(2,200); // 성공
    }
     */
    // => 결과 REQUIRED라 하나의 Tx로 a2가 실패라 모두 취소(어디서  실패가 되더라도)되어 아무 것도 안들어간다
    @Autowired DataSource ds;
    public void insertA1withTx() throws Exception{
        PlatformTransactionManager tm = new DataSourceTransactionManager(ds);
        DefaultTransactionDefinition txd = new DefaultTransactionDefinition();
        txd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = tm.getTransaction(txd);
        // Tx 시작
        try {
            a1Dao.insert(1,100); // 성공
            insertB1withTx();
            a1Dao.insert(1,200); // 실패
            tm.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            tm.rollback(status);
        } finally {
        }
    }
    // @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void insertB1withTx() throws Exception{
        PlatformTransactionManager tm = new DataSourceTransactionManager(ds);
        DefaultTransactionDefinition txd = new DefaultTransactionDefinition();
        txd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = tm.getTransaction(txd);
        // Tx 시작
        try {
            b1Dao.insert(1,100); // 성공
            b1Dao.insert(2,200); // 성공
            tm.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            tm.rollback(status);
        } finally {
        }
    }
    // => 결과 별개의 Tx로 Tx1(a1Dao)은 실패해 들어가지 않고 Tx2(b1Dao)는 성공해 들어간다
    /*
    @Transactional이 동작하지 않은 이유 
    같은 클래스에 속한 메서드끼리의 호출(내부호출)때문
    프록시 방식(디폴트)의 AOP는 내부호출인 경우 Advice가 적용되지 않음. 그래서 Tx가 적용되지 않는 것
    두 메서드를 별도의 클래스로 분리하면 Tx가 적용됨
     */

    public void insertA1WithoutTx() throws Exception{
        a1Dao.insert(1,200);
        a1Dao.insert(1,100);
    }
    
    @Transactional(rollbackFor = Exception.class) // 있어야 rollback이 된다, Exception을 rollback
    // @Transactional // RuntimeException, Error만 rollback
    public void insertA1WithTxFail() throws Exception{
        a1Dao.insert(1,100); // 성공
        a1Dao.insert(1,200); // 실패해서 둘다 안들어가야함
        // throw new Exception(); // RuntimeException이 아니라 rollback이 안되어 들어감
        // throw new RuntimeException(); // 성공했지만 rollback이 되어 아무 것도 안들어감
    }

    @Transactional
    public void insertA1WithTxSuccess() throws Exception{
        a1Dao.insert(1,100);
        a1Dao.insert(2,200);
    }

}
