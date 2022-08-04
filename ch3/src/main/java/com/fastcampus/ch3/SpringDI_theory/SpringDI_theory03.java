package com.fastcampus.ch3.SpringDI_theory;

public class SpringDI_theory03 {
    /*
    스프링 애너테이션 - @Resource
    Spring container에서 "이름(by Name)"으로 빈을 검색해서 참조변수에 자동 주입(DI)
    일치하는 이름의 빈이 없으면, 예외 발생
    아래 @Autowired에 @Qualifier("name")붙인 것과 같음

    class Car {
        @Resource(name="superEngine")
        Engine engine;
    } 
    ↕ 비교
    // spring 방식
    class Car {
        // @Autowired로 byType으로 검색 후,
        //같은 타입의 빈이 n개면 @Qualifier("이름")으로 주입할 빈을 지정.
        @Autowired
        @Qualifier("superEngine")
        Engine engine;
    }

    class Car{
        // @Resource(name="engine")
        //이름 생략 가능(생략하면 참조변수 engine이 빈의 이름이 됨)
        @Resource
        Engine engine;
    }
    */

    /*
    스프링 애너테이션 - @Component
    <component-scan>로 @Component가 클래스를 자동 검색해서 빈으로 등록
    @Controller, @Service, @Repository, @ControllerAdvice의 메타 애너테이션
    <component-scan>을 하면 @Controller, @Service, @Repository, @ControllerAdvice가 붙으면 자동 등록됨.
    (ex. homeController가 자동 등록됨.)

    <context:component-scan base-package="com.fastcampus.ch3" /> //서브 패키지까지 검색

    // <bean id="superEngine" class="com.fastcampus.ch3.SuperEngine"/>
    // @Component("superEngine") // id 생략 가능, class의 첫글자를 소문자로 바꾼 id를 빈에 저장.
       @Component
       class SuperEngine extends Engine {}

    */

    /*
    스프링 애너테이션 - @Value, @PropertySource

    // System.getenv 호출하면 맵으로 환경변수들이 저장
    Map<String, String> env = System.getenv();

    // systemProperties -> 시스템 프로퍼티들이 담긴 프로퍼티 객체가 저장
    Properties prop = System.getPorperties();
    ac.getBean(SysInfo.class)

    @Component
    @PropertySource("setting.properties")
    class SysInfo {
        //사용자의 시간대를 가져옴
        @Value("#{systemProperties['user.timezone']}")
        String timeZone;

        //현재 작업 디렉토리를 읽어옴
        @Value("#{systemEnvironment['PWD']}")
        String curDir;

    [외부 파일 ]
    [src/main/resources/setting.properties]
    // key, value 형태
    autosaveDir = /autosave
    autosave=true
    autosaveInterval=30

        //외부 파일 주입
        //자동 저장되는 폴더
        @Value("${autosaveDir}") 
        String autosaveDir;

        //자동 저장되는 주기
        @Value("${autosaveInterval}")
        int autosaveInterval; // 30으로 대입해서 들어감

        //자동 저장 유무
        @Value("${autosave}")
        boolean autosave; // "true"이 boolean true로 바뀌어 대입

    */

    /*
    스프링 애너테이션 vs 표준 애너테이션(JSR-330)(Java Spec Request)
    스프링 애너테이션 -> 스프링 제공
    표준 애너테이션 -> java제공
    javax.inject-1.jar - @Inject, @Named, @Qualifier, @Scope, @Singleton
    annotations-api.jar - @Resource, @ManagedBean, @PreDestroy, @PostContruct

    스프링 애너테이션	    표준 애너테이션	        비고
    @Autowired	        @Inject	                @Inject에는 required속성이 없음
    Qualifier	        @Qualifier, @Named	    스프링의 @Qualifier는 @Named와 유사
    	                @Resource	            스프링에는 이름 검색이 없음
    @Scope("singleton")	@Singleton	            표준에서는 prototype이 디폴트
    @Component	        @Named, @ManagedBean	표준에서는 반드시 이름이 있어야 함

     */

    /*
    빈의 초기화 - <property>와 setter
    <property>를 이용한 빈 초기화. setter를 이용
    Class Car {
        String color;
        int oil;
        Engine engine;
        Door[] door;
    }

    //아래 setter와 <property>를 이용한 빈 초기화는 같은 코드 : property태그를 사용하면 setter를 따로 호출하지않아도 괜찮음
    Car car = new Car();
    car.setColor("red");
    car.setOil(100);
    car.setEngine(engine);
    car.setDoors(new Door[]{new Door(), new Door()};
    ------------------------------------------------------
    //위의 세터와 같은 코드.
    //<property>설정 파일에 저장.
    //car.setColor("red"); 처럼 세터를 따로 호출 안해도 됨.
    <bean id="car" class="com.fastcampus.ch3.Car">
        <property name="color" value="red"/>
        <property name="oil" value="100"/>
        <property name="engine" ref="engine"/>
        <property name="doors">
            <array value-type="com.fastcampus.ch3.Door">
                <ref bean="door"/>
                <ref bean="door"/>
            </array>
        </property>
    </bean>
    <bean id="engine" class="com.fastcampus.ch3.Engine"/>
    <bean id="door" class="com.fastcampus.ch3.Door" scope="prototype"/>

     */

    /*
    빈의 초기화 - <contructor-arg>와 생성자
    <construgtor-arg>를 이용한 빈 초기화. 생성자를 이용.(생성자가 있어야 태그 사용 가능)

    public Car(String color, int oil, Engine engine, Door[] doors)
        this.color = color;
        this.oil = oil;
        this.engine = engine;
        this.doors = doors;
    }
    -------------------------------------------------------------
    //위 생성자를 이용하는 코드.
    <bean id="car" class="com.fastcampus.ch3.Car">
        <constructor-arg name="color" value="red"/>
        <constructor-arg name="oil" value="100"/>
        <constructor-arg name="engine" ref="engine"/>
        <constructor-arg name="doors">
            <array value-type="com.fastcampus.ch3.Door">
                <ref bean="door"/>
                <ref bean="door"/>
            </array>
        </constructor-arg>
    </bean>
    <bean id="engine" class="com.fastcampus.ch3.Engine"/>
    <bean id="door" class="com.fastcampus.ch3.Door" scope="prototype"/>
     */

    /*
    빈의 초기화 - <list>, <set>, <map>

    - <list>, String 또는 기본형일 때 빈의 초기화
    <property name="colors">
        <list>
            <value>red</value>
            <value>green</value>
            <value>blue</value>
            <value>white</value>
        </list>
    </property>

    - <list>, 참조형일 때 빈의 초기화
    <property name="engines">
        <list>
            <ref bean="superEngine"/>
            <ref bean="turboEngine"/>
        </list>
    </property>

    - <set>을 사용한 빈의 초기화
    <property name="engines">
        <set>
            <ref bean="superEngine"/>
            <ref bean="turboEngine"/>
        </set>
    </property>

    - <map>, 기본형일 때 빈의 초기화
    <property name="doorColors">
        <map>
            <entry key="left" value="red"/>
            <entry key="right" value="blue"/>
        </map>
    </property>

    - <map>, 참조형일 때 빈의 초기화
    <property name="doors">
        <map>
            <entry key="left" value-ref="door"/>
            <entry key="right" value-ref="door"/>
        </map>
    </property>

     */

}
