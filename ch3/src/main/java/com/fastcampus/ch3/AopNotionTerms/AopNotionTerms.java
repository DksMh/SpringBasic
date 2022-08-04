package com.fastcampus.ch3.AopNotionTerms;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;

public class AopNotionTerms {
    /*
    공통 코드의 분리
    여러 메서드에 공통 코드를 추가해야 한다면?

    class MyClass {
	//핵심기능과 부가기능이 같이 들어 있음
	//관심사(핵심, 부가)를 나눠야 함
    void aaa() {
        System.out.println("[befor]{"); // 부가기능
        System.out.println("aaa() is called."); // 핵심기능
        System.out.println("}[after]"); // 부가기능
    }

        void aaa2() {
            System.out.println("[befor]{");
            System.out.println("aaa2() is called.");
            System.out.println("}[after]");
        }

        void bbb() {
            System.out.println("[befor]{");
            System.out.println("bbb() is called.");
            System.out.println("}[after]");
        }
    }
     */

    // ↓ 이렇게 분리(관심사, 변하는 것&변하지않는 것, 공통 코드)해야한다

    /*
    //부가기능 분리
    class MyAdvice {
        void invoke(Method m, Object obj, Object... args) throws Exception {
            System.out.println("[befor]{"); // 부가기능
            m.invoke(obj, args); //메서드 호출
            System.out.println("}[after]"); // 부가기능
        }
    }

    //주기능 분리
    //중복코드 제거
    class MyClass2 {
        void aaa() {
            System.out.println("aaa() is called.");
        }

        void aaa2() {
            System.out.println("aaa2() is called.");
        }

        void bbb() {
            System.out.println("bbb() is called.");
        }
    }

     */

    /*
    2. 코드를 자동으로 추가한다면, 어디에?
        - 맨 앞, 맨 끝 (항상 정해져있음)
        - 중간에는 추가 할 수 없다
        - 맨 앞 자동 추가 : Before Advice
        - 맨 끝 자동 추가 : After Advice
        - 맨 앞 + 맨 끝 자동 추가 : Around Advice(Before + After)

    [EX]
    public int deleteUser(String id) throws Exception {
        // 1. 맨 앞(before) 자동 추가 --> Before Advice
        Connection conn = ds.getConnection();

        String sql = "delete from user_info where id=? ";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        return pstmt.executeUpdate();
        // 2. 맨 끝(after) 자동 추가 ---> After Advice
    }

    */

    /*
    [ AOP란? ]
    관점 지향 프로그래밍
    부가 기능(advice)을 동적으로 추가해주는 기술.  // 핵심기능 ↔ 부가기능
    (동적 추가 : 코드가 실행되는 과정에서 자동으로 추가)
    메서드의 시작 또는 끝에 자동으로 코드(advice)를 추가


    [ AOP 관련 용어 ]
    target : advice가 추가딜 객체.
    advice : target에 동적으로 추가될 부가 기능(코드)
    join point : advice가 추가(join)될 대상(메서드)
    pointcut : join point들을 정의한 패턴.(execution(* com.fastcampus.*.*(..))
    proxy : target에 advice가 동적으로 추가되어 생성된 객체
    weaving : target에 advice를 추가해서 proxy를 생성하는 것

    (OOP, AOP 모두 변경에 유리한 코드를 만들기 위해 분리하는 것)


    [ Advice의 종류 ]
    Advice의 설정은 XML과 애너테이션, 두 가지 방법으로 가능

    around advice : @Around, 메서드의 시작과 끝 부분에 추가
    before advice : @Before, 메서드의 시작 부분에 추가
    after advice : @After, 메서드의 끝 부분에 추가
    after returning : @AfterReturning, 예외 발생하지 않을 시 실행되는 부가 기능 --> try블럭 끝에 들어감
    after throwing : @AfterThrowing, 예외 발생시 실행되는 부가 기능 --> catch 블럭에 들어감

     */

    /*
    pointcut expresstion
    advice가 추가될 메서드를 지정하기 위한 패턴
    execution(반환타입 패키지명.클래스명.메서드명(매개변수 목록)) --> 반환 타입 앞에 접근 제어자 삽입가능(생략가능)
    [EX]
    //@Order(1), @Order(2) ... // -> advice가 어떤 순으로 적용될지 정할 수 있음
    public class LoggingAdvice{
        @Around("execution(* com.fastcampus.ch3.aop.*.*(..))") --> 패턴  // (..) 에서 ..은 개수상관 X 모든 타입 OK
        //ProceedingJointPoint pjp //pjp는 메서드의 모든 정보
        public Object methodCallLog(ProceedingJoinPoint pjp) throws Throwable {
            long start = System.currentTimeMillis();
            System.out.println("<<[start] "
                    + pjp.getSignature().getName() + Arrays.deepToString(pjp.getArgs()));

            Object result = pjp.proceed(); //메서드 호출

            System.out.println("result = " + result);
            System.out.println("[end]>> " + (System.currentTimeMillis() - start) + "ms");
            return result; //메서드 호출결과 반환
        }
    }

    */

}
