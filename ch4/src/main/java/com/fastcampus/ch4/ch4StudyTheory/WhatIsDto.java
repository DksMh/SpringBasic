package com.fastcampus.ch4.ch4StudyTheory;

public class WhatIsDto {
/*
    DTO란? Data Transfer Object
    계층간의 데이터를 주고 받기 위해 사용되는 객체

    관심사의 역할에 따라 계층 분리
    @Controller
        요청과 응답을 처리
        데이터 유효성 검증
        실행 흐름을 제어(redirect & forward)
    @Service
        비지니스 로직 담당
        트랜잭션 처리(Tx)
        예외 발생 시 3가지 방법
            Service에서 처리
            Controller로 보냄
            Service, Controller 두 곳에서 처리(예외 되던지기)
    @Repository
        순수 Data Acess 기능(DAO)
        조회, 등록, 수정, 삭제
        예외처리를 안함.
        예외가 발생하면 무조건 Service 계층으로 보냄.

    DTO는 각 계층간에 데이터를 전달하는 역할.
 */
/*
     #{}와 ${}의 차이 --> boardMapper.xml
    [ #{} SQL --> Injection공격 방지, 타입을 알아서 ?에 넣어줌 ]
    <insert id="insert" parameterType="BoardDto">
        INSERT INTO board (title, content, writer)
        VALUES (#{title}, #{content}, #{writer})
    </insert>

    // ↓ 이렇게 변환
    String sql = "INSERT INTO board "
                + " (title, content, writer) "
                + "VALUES "
                + " (?, ?, ?)";
    PreparedStatement pstmt = conn.prepareStatement(sql); // -> 값에 대해서만 ?
    int result = pstmt.executeUpdate();


    [ ${} --> 유연한 코드, 제약이 적음 ]
    <insert id="insert" parameterType="BoardDto">
        INSERT INTO board
            (title, content, writer)
        VALUES
            ('${title}', '${content}', '${writer}')
    </insert>

     // ↓ 이렇게 변환
    String sql = "INSERT INTO board "
                + " (title, content, writer) "
                + "VALUES "
                + " ('"+title+"', '"+content+"', '"+writer+"')";
    Statement stmt = conn.createStatement();
    int result = stmt.executeUpdate(sql);
*/
/*
    XML의 특수 문자 처리
    XML내의 특수 문자(<,>,&,...)는 &lt; &gt;로 변환 필요
    또는 특수문자가 포함된 쿼리를 <![CDATA[와]]>로 감싼다

    [EX]
    // &lt;, &gt;
    <update id="update" parameterType="BoardDto">
        UPDATE board
        SET title = #{title}
            , content = #{content}
            , up_date = now()
        WHERE bno = #{bno} and bno &lt; &gt; 0
    </update>

    // <![CDATA[ ]]>
    <update id="update" parameterType="BoardDto">
        <![CDATA[
        UPDATE board
        SET title = #{title},
            content = #{content},
            up_date = now()
        WHERE bno = #{bno} and bno <> 0
        ]]>
    </update>
*/
}
