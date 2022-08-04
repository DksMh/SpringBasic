package com.fastcampus.ch3;

public interface UserDao {
    int deleteUser(String id);

    User selectUser(String id);

    // 사용자 정보를 user_info테이블에 저장하는 메서드
    int insertUser(User user);

    // 매개변수로 받은 사용자 정보로 user_info테이블을 update하는 메서드
    int updateUser(User user);

    void deleteAll() throws Exception;
}
/*
DAO(Data Access Object) 란?

데이터(data)에 접근(access)하기 위한 객체(object)
Database에 저장된 데이터를 읽기, 쓰기, 삭제, 변경을 수행(CRUD)
DB테이블당 하나의 DAO를 작성(1:1)
(EX)
LoginController/RegisterController --> Presentation layer(Data를 보여주는 계층)
    ↕
UserDao 계층 분리의 관점으로 보면 영속 계층(Presistence Layer)에 속함(데이터베이스에 접근하는 역할)
    ↕
    DB

분리한 이유 : 관심사, 변하는 것과 변하지 않는 것, 중복코드 분리 --> 변경에 유리
 */