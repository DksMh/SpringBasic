package com.fastcampus.ch3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
// ContextConfiguration : xml 설정파일 위치 지정
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DBConnectionTest2Test {
    @Autowired
    DataSource ds;
    @Test
    public void insertUserTest() throws Exception{
        User user = new User("asdf","1234","abc","aaaa@aaa.com",new Date(),"fb",new Date());
        deleteAll();
        int rowCnt = insertUser(user);

        assertTrue(rowCnt==1); // 한줄 저장하니까 1이 반환되어야함
    }

    @Test
    public void selectUserTest() throws Exception{
        deleteAll();
        User user = new User("asdf2","1234","abc","aaaa@aaa.com",new Date(),"fb",new Date());
        int rowCnt = insertUser(user);
        User user2 = selectUser("asdf2");

        assertTrue(user.getId().equals("asdf2"));
    }

    @Test
    public void deleteUserTest() throws Exception {
        deleteAll();
        int rowCnt = deleteUser("asdf");

        assertTrue(rowCnt==0);

        User user = new User("asdf2","1234","abc","aaaa@aaa.com",new Date(),"fb",new Date());
        rowCnt = insertUser(user);
        assertTrue(rowCnt==1); // 삽입이 되었다면 1

        rowCnt = deleteUser(user.getId());
        assertTrue(rowCnt==1); // 삭제가 되었다면 1

        assertTrue(selectUser(user.getId())==null);
    }

    // update 과제
    @Test
    public void updateUserTest() throws Exception {
        User user = new User("asdf3","1234","abc","aaaa@aaa.com",new Date(),"facebook", new Date());
        deleteAll();
        int rowCnt = insertUser(user);
        assertTrue(rowCnt==1);

        User user2 = new User("asdf3", "1111", "ass", "bbbb@bbb.com", new Date(), "kakao", new Date());
        rowCnt = updateUser(user2);
        assertTrue(rowCnt==1);

    }

    //매개변수로 받은 사용자 정보로 user_info테이블을 update하는 메서드
    public int updateUser(User user) throws Exception {
        Connection conn = ds.getConnection();

        String sql = "update user_info " + "set pwd = ?, name=?, email=?, birth =?, sns=?, reg_date=? " + "where id = ? ";
        PreparedStatement pstmt = conn.prepareStatement(sql); //SQL Injection공격, 성능향상
        pstmt.setString(1, user.getPwd());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getEmail());
        pstmt.setDate(4, new java.sql.Date(user.getBirth().getTime()));
        pstmt.setString(5, user.getSns());
        pstmt.setTimestamp(6, new java.sql.Timestamp(user.getReg_date().getTime()));
        pstmt.setString(7, user.getId());

        int rowCnt = pstmt.executeUpdate(); //insert, delete, update

        return rowCnt;
    }

    public int deleteUser(String id) throws Exception {
        Connection conn = ds.getConnection();

        String sql = "delete from user_info where id=? ";

        PreparedStatement pstmt = conn.prepareStatement(sql); //SQL Injection공격, 성능향상
        pstmt.setString(1, id);
        // int rowCnt = pstmt.executeUpdate();
        // return rowCnt;
        return pstmt.executeUpdate();
    }


    public User selectUser(String id) throws Exception {
        Connection conn = ds.getConnection();

        String sql = "select * from user_info where id=? ";

        PreparedStatement pstmt = conn.prepareStatement(sql); //SQL Injection공격, 성능향상
        pstmt.setString(1,id);
        ResultSet rs = pstmt.executeQuery(); // select

        if(rs.next()){
            User user = new User();
            user.setId(rs.getString(1));
            user.setPwd(rs.getString(2));
            user.setName(rs.getString(3));
            user.setEmail(rs.getString(4));
            user.setBirth(new Date(rs.getDate(5).getTime()));
            user.setSns(rs.getString(6));
            user.setReg_date(new Date(rs.getTimestamp(7).getTime()));

            return user;
        }
        return null;
    }

    private void deleteAll() throws Exception{
        Connection conn = ds.getConnection();
        String sql = "delete from user_info";

        PreparedStatement pstmt = conn.prepareStatement(sql); //SQL Injection공격, 성능향상
        pstmt.executeUpdate();

    }
    @Test
    public void transactionTest() throws Exception{
        Connection conn = null;
        try {
            deleteAll();
            conn = ds.getConnection();
            conn.setAutoCommit(false); //conn.setAutoCommit(true); --> 이 기본, 첫번째 executeUpdate만 성공하게 된다

            String sql = "insert into user_info values (?,?,?,?,?,?,now())";

            PreparedStatement pstmt = conn.prepareStatement(sql);  //SQL Injection공격 방지, 성능향상, SQL문을 간결하게 작성
            pstmt.setString(1, "asdf");
            pstmt.setString(2, "1234");
            pstmt.setString(3, "abc");
            pstmt.setString(4, "aaa@aaa.com");
            pstmt.setDate(5, new java.sql.Date(new Date().getTime()));
            pstmt.setString(6, "fb");

            int rowCnt = pstmt.executeUpdate(); //insert, delete, update

            pstmt.setString(1, "asdf"); // 1. 아이디를 똑같이 주면 2번째 executeUpdate에서 에러 발생
            rowCnt = pstmt.executeUpdate(); //insert, delete, update

            conn.commit();

        } catch (Exception e) {
            conn.rollback(); // 2. rollback을 하므로 작업이 취소가 됨
            e.printStackTrace();
        } finally {

        }

    }
    //
    // 사용자 정보를 user_info 테이블에 저장하는 매서드
    public int insertUser(User user) throws Exception {
        Connection conn = ds.getConnection();
//        insert into user_info (id, pwd, name, email, birth, sns, reg_date)
//        values ('asdf22','1234','smith','aaa@aaa.com','2021-01-01','facebook',now());
        String sql = "insert into user_info values (?,?,?,?,?,?,now())";

        PreparedStatement pstmt = conn.prepareStatement(sql);  //SQL Injection공격 방지, 성능향상, SQL문을 간결하게 작성
        // Prepared Statement를 활용하면 쿼리의 문법 처리 과정이 미리 컴파일이 되어 있기 때문에, 외부 입력값으로 SQL 관련 구문이나 특수문자가 들어와도 그것은 SQL 문법으로서 역할을 할 수 없기 때문
        pstmt.setString(1,user.getId());
        pstmt.setString(2,user.getPwd());
        pstmt.setString(3,user.getName());
        pstmt.setString(4,user.getEmail());
        pstmt.setDate(5, new java.sql.Date(user.getBirth().getTime()));
        pstmt.setString(6, user.getSns());

        int rowCnt = pstmt.executeUpdate(); // insert / delete / update를 할때 사용

        return rowCnt;
    }

    @Test
    public void springJdbcConnectionTest() throws Exception {
//        ApplicationContext ac = new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/**/root-context.xml");
//        DataSource ds = ac.getBean(DataSource.class);

        Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻는다.

        System.out.println("conn = " + conn);
        assertTrue(conn != null); // 괄호 안의 조건식이 true면 테스트 성공 아니면 실패, 꼭 들어가야함 : Test가 성공했는지 assert로 확인
    }
}