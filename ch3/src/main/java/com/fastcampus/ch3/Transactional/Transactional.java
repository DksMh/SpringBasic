package com.fastcampus.ch3.Transactional;

import com.fastcampus.ch3.LoginController;
import com.fastcampus.ch3.RegisterController;
import com.fastcampus.ch3.UserDao;
import org.springframework.transaction.annotation.Propagation;

public class Transactional {
/*
    서비스 계층(Layer)의 분리와 @Transactional
    서비스 계층(Layer)의 분리 - 비지니스 로직의 분리

    비즈니스 계층이란?
    고객의 요구사항을 반영하는 계층
    프레젠테이션 계층(Controller, View) 와 영속 계층(VO, DAO, Mapper)의 중간 다리 역할
    로직을 기준으로 해서 처리

    LoginController/RegisterController --> Presentation layer(Data를 보여주는 계층)
    // @Controller - 컨트롤러, Presentation영역
    ↕
    UserDao 계층 분리의 관점으로 보면 영속 계층(Presistence Layer)에 속함(데이터베이스에 접근하는 역할)
    // @Repository - Dao, Persistence영역, Data Access Layer(영속 계층) , CRUD 기능만 가지고 있어 비지니스로직 적합 X
    ↕
    DB

    ※ 컨트롤러의 역할에 비지니스로직은 포함 X -> 3개의 계층으로 분리해야 함

    RegisterController --> Presentation layer
    - userService: UserService // 주입
    ↕
    UserService --> Service Layer(비즈니스 로직) / Dao를 불러 처리
    - userDao: UserDao // 주입
    - userHistoryDao: UserHistoryDao // 주입
    ↕           ↕
    UserDao     UserHistoryDao --> Presistence Layer(영속 계층)
    ↕           ↕
         DB
    user_info   user_history
    
    서비스 계층이 없으면 컨트롤러에서 userDao.insertUser(user)를 해줘야한다
    서비스 계층에 registerUser() 메서드를 만들고 해당 코드를 넣어주면
    컨트롤러에서는 registerUser()를 호출해서 사용하기만 하면 된다
    // 회원 정보가 바뀌어도 컨틀롤러는 단순 호출만 하므로 바뀌는 것이 없어진다

    [ Transaction 적용에 유리 ]
    User가 insert될 때, User의 history도 insert 되게 하려면 Tx를 사용해야 한다
    Tx를 사용하기 위해서는 서비스 계층을 사용하는 것이 유리
    컨트롤러에 Tx를 쓰면 너무 복잡해짐

    RegisterController --> @Controller
    UserService --> @Service
    UserDao --> @Repository
    셋 다 @Component로 <component-scan>이 가능

 */
/*
    TransactionManager란?
    DAO의 각 메서드는 개별 Connection을 사용하는데
    n개의 메서드를 하나의 Connection을 사용하게 하기 위해서 Tx를 사용해야 한다

    Tx는 1개의 Connection에서 처리되어야 한다
    --> TransactionManager가 필요(같은 Tx면 같은 Connection이 사용할 수 있게 관리)

    DAO에서 Connection을 얻거나 반환할 때 DataSourceUtils를 사용해야 함
    [EX]
    //이전 방법
    conn = ds.getConnection();
    try { if(conn!=null) conn.close(); }
    catch(SQLException e) {e.printStackTrace();}

    ↓ 아래처럼 변환

    //DataSourceUtils 사용
    conn = DataSourceUtils.getConnection(ds);
    DataSourceUtils.releaseConnection(conn, ds);

*/
/*
    TransactionManager로 Transaction 적용하기

    public void insertWithTx() throws Exception {
        // Tx매니저 직접 생성
        PlatformTransactionManager tm = new DataSourceTransactionManager(ds); // DataSourceTransactionManager(ds) : TxManager 생성
        TransactionStatus status = tm.getTransaction(new DefaultTransactionDefinition()); // DefaultTransactionDefinition() : Tx의 속성 정의

        //Tx 시작
        try {
            a1Dao.insert(1,100);
            a1Dao.insert(1,200);
            t.commit(status); // Tx 끝 - 성공(커밋)
        } catch(Exception ex) {
            tm.rollback(status); // Tx 끝 - 실패(롤백)
        }
`
    // Tx매니저를 빈으로 등록
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <!-- @Transactional 사용 가능 -->
    <tx:annotataion-driven/>

 */
/*
    @Transactional로 Transaction적용하기
    AOP를 이용한 핵심 기능과 부가 기능의 분리

    public void insertWithTx() throws Exception {
        PlatformTransactionManager tm = new DataSourceTransactionManager(ds);
        TransactionStatus status = tm.getTransaction(new DefaultTransactionDefinition());

        try {
            a1Dao.insert(1,100);
            a1Dao.insert(1,200);
            t.commit(status);
        } catch(Exception ex) {
            tm.rollback(status);
        }
    }
    ↓  AOP로 핵심기능 분리
    @Transactional
    public void insertWithTx() throws Exception {
        a1Dao.insert(1,100);
        a1Dao.insert(1,200);
    } //핵심 로직만 집중 가능

    @Transactional은 클래스나 인터페이스에도 붙일 수 있음
        - 클래스에 붙이면 클래스 내의 모든 메서드에 적용
        - 인터페이스를 구현하는 클래스의 모든 메서드에 적용
 */
/*
    [ @Transactional의 속성 ]

    propagation : Tx의 경계(boundary)를 설정하는 방법을 지정
    isolation : Tx의 isolation level을 지정. DEFAULT(DB설정 따름), READ_UNCOMMITTED, READ_COMMITTED, REPEATABLE_READ(디폴트), SERIALIZABLE
    readOnly : Tx이 데이터를 읽기만 하는 경우, true로 지정하면 성능 향상
    rollbackFor : 지정된 예외가 발생 시, Tx를 rollback / RuntimeException, Error는 자동 rollback.
    noRollbackFor : 지정된 예외가 발생해도 Tx을 rollback하지 않음
    timeout : 지정된 시간(초) 내에 Tx이 종료되지 않으면, Tx를 강제 종료

    [ propagation속성의 값 ]

    REQUIRED : Tx이 진행중이면 참여, 없으면 새로운 Tx 시작(디폴트)
    REQUIRES_NEW : Tx이 진행 중이건 아니건, 새로 Tx 시작(Tx 안에 다른 Tx)
    NESTED : Tx이 진행 중이면, Tx의 내부 Tx로 실행(Tx안에 subTx)(같은 Tx)
    MANDATORY : 반드시 진행 중인 Tx내에서만 실행가능 아니면 예외 발생
    SUPPORTS : Tx이 진행 중이건 아니건 상관없이 실행
    NOT_SUPPORTED : Tx없이 처리, Tx이 진행 중이면 잠시 중단(suspend)
    NEVER : Tx없이 처리, Tx이 진행 중이면 예외 발생

*/
/*
    REQUIRED와 REQUIREDS_NEW (1) - REQUIRED

    // 두 Tx가 같은 Tx처럼 돌아감
    // 예외 발생 시 처음으로 rollback
    // REQUIRED : Tx가 기존에 있으면 새로 Tx 안만듬
    Ⅰ (1이 2를 호출)
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertA1WithTx() throws Exception {
        a1Dao.insert(1, 100); // A1
        insertB1WithTx(); // 2를 호출
        a1Dao.insert(1, 200); // A2
    }
    Ⅱ
    @Transactional(propagation = Propagation.REQUIRED) {
        b1Dao.insert(1, 100); // B1
        b1Dao.insert(1, 200); // B2
    }

        ㅣ A1 - B1 - B2 - A2 ㅣ
     Tx1 시작              Tx1 끝

*/
/*
    REQUIRED와 REQUIRES_NEW(2) - REQUIRES_NEW

    //REQUIRED가 기본값이라 생략가능 @Transactional로 써도 됨.
    Ⅰ (1이 2를 호출)
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertA1WithTx() throw Exception {
        a1Dao.insert(1, 100); // A1
        insertB1WithTx(); // 2를 호출
        a1Dao.insert(1, 200); // A2
    }
    Ⅱ //REQUIRES_NEW : 새로운 Tx가 필요
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertB1WithTx() throws Exception {
        b1Dao.insert(1, 100); // B1
        b1Dao.insert(1, 200); // B2
    }
    //결과 2개의 Tx
        ㅣ   A1   ㅣ    B1 - B2   ㅣ    A2   ㅣ
     Tx1 시작   Tx2 시작        Tx2 끝     Tx1 끝
 */
}
