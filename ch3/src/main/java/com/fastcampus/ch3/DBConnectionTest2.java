package com.fastcampus.ch3;

import org.springframework.context.*;
import org.springframework.context.support.*;
import org.springframework.jdbc.datasource.*;

import javax.sql.*;
import java.sql.*;


public class DBConnectionTest2 {
    public static void main(String[] args) throws Exception {
        // 스키마의 이름(springbasic)이 다른 경우 알맞게 변경
        // String DB_URL = "jdbc:mysql://localhost:3306/springbasic?useUnicode=true&characterEncoding=utf8";

        // DB의 userid와 pwd를 알맞게 변경
//        String DB_USER = "dksan";
//        String DB_PASSWORD = "dkssudhi!1405";
//        String DB_DRIVER = "com.mysql.jdbc.Driver";
//
//        DriverManagerDataSource ds = new DriverManagerDataSource();
//        ds.setDriverClassName(DB_DRIVER);
//        ds.setUrl(DB_URL);
//        ds.setUsername(DB_USER);
//        ds.setPassword(DB_PASSWORD);
        // ↕ 비교
        // root-context.xml 파일에 dataSource를 bean으로 등록해주고, property 태그로 필요한 값들을 설정해주면 DB를 사용할 수 있다.
        ApplicationContext ac = new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/**/root-context.xml");
        DataSource ds = ac.getBean(DataSource.class);

        Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻는다.

        System.out.println("conn = " + conn);
        // assertTrue(conn != null);
    }
}
// JUnit TestFrameWork 이용시, test를 자동화할 수 있다. TDD(테스트 주도 개발)