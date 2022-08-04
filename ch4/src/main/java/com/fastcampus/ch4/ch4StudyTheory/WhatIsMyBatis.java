package com.fastcampus.ch4.ch4StudyTheory;

public class WhatIsMyBatis {
/*
    1. MyBatis란?
    자바코드 <--> SQL(별도의 xml로 분리)
    SQL Mapping Framework - Easy & Simple
    자바 코드로부터 SQL문을 분리해서 관리
    매개변수 설정과 쿼리 결과를 읽어오는 코드를 제거(getInt() X, getString() X)
    작성할 코드가 줄어서 생산성 향상 & 유지 보수 편리

    //이전의 방법
    //sql, java가 한 소스코드에 섞여있음.
    public int insertUser(User user) {
        int rowCnt = FAIL;

        Connection conn = null;
        PreparedStatement pstmt = null;

        String sql = "insert into user_info values(?,?,?,?,?,?,new Date())"; //sql
        // java
        try {
            conn = ds.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getPwd());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, new java.sql.Date(user.getBirth.getTime()));
            pstmt.setString(6, user.getSns());

            return pstmt.executeUpdate();
        } catch(SQLException e) {
            e.printStrackTrace();
            return FAIL;
        } finally {
            close(pstmt, conn);
        }
    }

    // ↓

    // 새로운 방법 -> 유지 보수 유리
    // XML문서에 SQL만 따로 떼어 냄
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
                "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    <mapper namespace="com.fastcampus.ch4.dao.UserMapper">
        <insert id="insert" parameterType="com.fastcampus.ch4.domain.UserDto>
        INSERT INTO user_info
        VALUES ( #{id}, #{pwd}, #{name}, #{email}, #{birth}, #{sns}, now());
        </insert>

    //새로운 자바코드
    @Repository
    public class UserDaoImpl implements UserDao {
        @Autowired
        private SqlSession session;
        private static String namespace = "com.fastcampus.ch4.dao.UserMapper.";

        @Override
        public int insert(User user) {
            return session.insert(namespace + "insert", user);
            // "insert" ==>  insert id="insert"의 insert 이다
        }
    }

 */
/*
    SqlSessionFactoryBean과 SqlSessionTemplate

    (1)mybatis가 제공
    SqlSessionFactory,SqlSession 모두 interface,
    SqlSessionFactory - SqlSession을 생성해서 제공
        --> openSession()메서드 호출해서 SqlSession 반환
    SqlSession - SQL명령을 수행하는데 필요한 메서드 제공

    (2)mybatis-spring이 제공, 해당 인터페이스(SqlSessionFactory,SqlSession)를 구현한 것
    SqlSessionFactoryBean - SqlSessionFactory를 Spring에서 사용하기 위한 빈
    SqlSessionTemplate - SQL명령을 수행하는데 필요한 메서드 제공. thread-safe
    (BoardDao, UserDao가 SqlSessionTemplate를 공유 가능. thread-safe(멀티쓰레드에 안전)이기 때문)

    // root-context.xml에 등록
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="mapperLocations" value="classpath:mapper/*Mapper.xml"/>
        <!-- mapper.xml(sql문들 들어있는 xml문서) 위치 지정 -->
    </bean>
    
    <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
        <constructor-arg ref="sqlSessionFactory"/>
    </bean>

 */
/*
    SqlSession의 주요 메서드

    int insert() : insert문을 실행하고, insert된 행의 개수를 반환
    int delete() : delete문을 실행하고, delete된 행의 개수를 반환
    int update() : update문을 실행하고, update된 행의 개수를 반환
    T selectOne() : 하나의 행을 반환하는 select에 사용 또는 parameter로 SQL에 binding될 값 제공
    List selectList() : 여러행을 반환하는 select에 사용 또는 parameter로 SQL에 binding될 값 제공
    MapM<K,V> selectMap() : 여러행을 반환하는 select에 사용 keyCol에 Map의 key로 사용할 컬럼 지정

 */
/*
    Mapper XML의 작성
    // boardMapper.xml 미사용
    public class BoardDaoImpl implements BoardDao {
        @Autowired
        pricate SqlSession session;
        pricate static String namespace
                  = "com.fastcampus.ch4.dao.BoardMapper.";

        public String getServerTime() throws Exception {
            return session.selectOne(namespace+"now");
        } //T selectOne(String statement)

        public int count() throws Exception {
            return session.selectOne(namespace+"count");
        } //T selectOne(String statement)

        public int insert(BoardDto dto) throws Exception {
            return session.insert(namespace+"insert", dto);
        } //int insert(String statement, Object parameter)

        public BoardDto select(Integer bno) throws Exception {
            return session.selectOne(namespace+"select", bno);
        } //T selectOne(String statement, Object parameter)

    // boardMapper.xml을 사용
    <mapper namespace="com.fastcampus.ch4.dao.BoardMapper">
        <select id="now" resultType="string">
            SELECT now()
        </select>

        <select id="count" resultType="int">
            SELECT count(*) FROM board
        </select>

        <insert id="insert" parameterType="com.fastcampus.ch4.domain.BoardDto">
            INSERT INTO board
                (title, content, writer)
            VALUES
                (#{title}, #{content}, #{witer})
        </insert>

        <select id="select" parameterType="int"
                            resultType="com.fastcampus.ch4.domain.BoardDto">
            SELECT bno, title, content, writer, view_cnt, commnet_cnt, reg_date
            FROM board
            WHERE bno = #{bno}
        </select>
 */
/*
    <typeAliases>로 이름 짧게 하기
    mybatis-config.xml
    //별명은 대소문자 구별 X
    //실제 이름은 대소문자 구별 O
    <typeAliases>
        <typeAlias alias="BoardDto" type="com.fastcampus.ch4.domain.BoardDto"/>
    </typeAliases>
 */
/*
    MyBatis의 동적 쿼리 (1) - <sql>과 <include> : 공통 부분을 정의하고 포함시켜 재사용

    [ 원래 방법 ]
    <select id="select" parameterType="int" resultType="BoardDto">
        SELECT bno, title, content, writer, view_cnt, comment_cnt, reg_date
        FROM board
        WHERE bno = #{bno}
    </select>

    <select id="selectPage" parameterType="map">
        SELECT bno, title, content, writer, view_cnt, commnet_cnt, reg_date
        FROM board
        ORDER BY reg_date DESC, bno DESC
        LIMIT #{offset}, #{pageSize}
    </select>

    ↓ MyBatis 사용

    [ 변경 ]
    <sql id="selectFromBoard"> //공통 부분을 <sql>로 정의
        SELECT bno, title, content, writer, view_cnt, comment_cnt, reg_date
        FROM board
    </sql>

    <select id="select" parameterType="int" resultType="BoardDto">
        <include refid="selectFromBoard"/> // <include>로 포함시켜 재사용
        WHERE bno = #{bno}
    </select>

 */
/*
    MyBatis의 동적 쿼리 - <if>
    <select id="searchResultCnt" paramterType="SearchCondition" resultType="int">
        SELECT count(*)
        FROM board
        WHERE true
        <if test='option=="A"'>
            AND (title LIKE concat('%', #{keyword}, '%')
            OR content LIKE concat('%', #{keyword}, '%'))
        </if>
        <if test='option=="T"'>
            AND title LIKE concat('%', #{keyword}, '%')
        </if>
        <if test='option=="W"'>
            AND writer LIKE concat('%', #{keyword}, '%')
        </if>
    </select>

    ※ 이 경우 중복 될 수 있어 <choose> <when>사용이 더 적합

    MyBatis의 동적 쿼리 - <choose> <when>
    [ 와일드카드 ]
    Oracle : %(여러글자, 0개 이상), ?(한글자)
    MySql : %(여러글자, 0개 이상), _(언더스코어)(한글자)
    ex) 'title%' ==> title2(O), title(O)
    ex) 'title' ==> title2(O), title(X)

    <select id="searchResultCnt" parameterType="SearchCondition" resultType="int">
        SELECT count(*)
        FROM board
        WHERE true
        <choose> // if-else if 와 비슷
            <when test='option=="T"'>
                AND title LIKE concat('%', #{keyword}, '%')
            </when>
            <when test='option=="W"'>
                AND writer LIKE concat('%', ${keyword}, '%')
            </when>
            <otherwise>
                AND (title LIKE concat('%', #{keyword}, '%')
                OR content LIKE concat('%', #{keyword}, '%'))
            </otherwise>
        </choose>
    </select>
 */
/*
    MyBatis의 동적 쿼리 - <foreach>

    <select id="getSelected" resultType="BoardDto">
        SELECT bno, title, content, writer, view_cnt, comment_cnt, reg_date
        FROM board
        WHERE bno IN // -> IN 사용법 : where bno in (1, 2, 3)
        // BUT, 1,2,3이 아니라 여러개가 들어가는데 그 수를 모르는 경우 foreach사용
        <foreach collection="array" item="bno" open="(" close=")" separator=",">
            #{bno}
        </foreach>
        ORDER BY reg_date DESC, bno DESC
    </select>

    // Repository 계층(DAO)
    public List<BoardDto> getSelected(Integer[] bnoArr) throws Exception { // sql 작성 후, array라 배열로 넘겨줌
        return session.selectList(namespace + "getSelected", bnoArr);
    }

    // Service 계층
    List<BoardDto> list = boardDao.getSelected(new Integer[]{1,2,3});

 */
}
