package com.fastcampus.ch3.SpringDI_theory;

import org.springframework.context.annotation.Bean;

import javax.servlet.Servlet;
import javax.swing.*;
import java.beans.Beans;

public class SpringDI_theory01 {
    /*
    1. 빈(bean)이란?

    -JavaBeans
    재사용 가능한 컴포넌트, 상태(iv), getter&setter, no-args constructor

    -Servlet & JSP bean
    MVC의 Model, EL, scope, JSP container가 관리

    -EJB(Enterprise Java Beans)
    복잡한 규칙, EJB container가 관리

    -Spring Bean
    POJO(Plain Old Java Object). 단순, 독립적, Spring container가 관리
    */

    /*
    2. BeanFactory와 ApplicationContext

    - Bean
    Spring Container가 관리하는 객체, xml에 빈을 정의 -> Spring Container

    - Spring Container
    Bean 저장소, Bean을 저장, 관리(생성, 소멸, 연결)
    생성, 소멸 -> Bean의 Lifecycle
    연결 -> @Autowired

    - BeanFactory
    Bean을 생성, 연결 등의 기본기능을 정의한 인터페이스

    - ApplicationContext
    BeanFactory를 확장하여 여러 기능을 추가 정의 (Spring Container와 ApplicationContext는 거의 같은 말)

    - ApplicationContext 종류
    [non-Web]
    XML : GenericXmlApplicationContext
    Java Config(자바코드) : AnnotationConfigApplicationContext

    [Web]
    Xml : XmlWebApplicationContext
    Java Config : AnnotionConfigWebApplicationContext

    */

    /*
    Root AC와 Servlet AC
    Root AC와 Servlet AC가 초기화가 잘되었는지 로그를 통해 확인해 각 파일 설정에 문제가 있는지 확인 가능

    Spring MVC <= Web.xml
    root-context.xml : new XmlWebApplicationContext() - 부모
    servlet-context.xml : new XmlWebApplicationContext() - 자식
    (root-context.xml은 부모, servlet-context.xml은 자식)
    자식에서 찾고 없으면 부모에서 찾는다.
    부모는 공통으로 쓰이는 bean(non-web) db관련 bean 등등
    자식은 개별로 쓰이는 bean

     */

    /*
    ApplicationContext의 주요 메서드
    
    int      getBeanDefinitionCount() // 정의된 빈의 갯수
    String[] getBeanDefinitionNames() // 정의된 빈의 이름

    Object getBean() //빈 얻기

    boolean isPrototype()
            isSingleton()
            isTypeMatch() //타입 확인
            containsBean() //빈이 있는지
            containsBeanDefinition()
            containsLocalBean()

    Annotation findAnnotationOnBean()

    */
}
