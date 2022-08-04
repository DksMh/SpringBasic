package com.fastcampus.ch3.SpringDI_theory;

public class SpringDI_theory02 {
    /*
    IoC와 DI
    - 제어의 역전 IoC(Inversion of Control)
    제어의 흐름을 전통적인 방식과 다르게 뒤바꾸는 것.
    IoC는 Framework 코드가 사용자 코드를 호출하는 것을 말한다.

    [전통적인 방식] --> 사용자 코드가 Framework 코드를 호출
    Car car = new Car();
    //사용자코드가 라이브러리 호출
    car.turboDrive(); //호출

    //라이브러리 --> 잘 변하지 않는 코드
    void turboDrive(){
        engine = new TurboEngine(); // -> TurboEngine() : 잘 변하는 코드
        engine.start();
    }

    [IoC] --> Framework 코드가 사용자 코드를 호출
    Car car = new Car();
    car.drive(new SuperEngine()); // SuperEngine() -> TruboEngine() 바뀌어도 괜찮음
    // 대신 메서드가 사용할 부분을 사용자 코드에서 제공해줘야함 그것이 DI

    //라이브러리가 사용자코드를 호출
    //변하지 않는 코드
    void drive(Engine engine) {
        engine.start(); //호출
    }

    - 의존성 주입 DI(Dependency Injection)
    사용할 객체를 외부에서 주입받는 것

    1. 수동 주입
    Car car = new Car();
    car.drive(new SuperEngine()); //수동 주입

    void drive(Engine engine) {
        engine.start(); //호출
    }

    2. 자동 주입
    Car car = new Car();
    car.drive(new SuperEngine());

    @Autowired  //자동 주입
    void drive(Engine engine) {
        engine.start();
    }
    */

    /*
    스프링 애너테이션 - @Autowired
    인스턴스 변수(iv), setter, 참조형 매개변수를 가진 생성자, 메서드에 적용
    알맞는 bean을 찾아서 넣어줌
    Spring container에서 타입으로 빈을 검색해서 참조변수에 자동 주입(DI)
    검색된 빈이 n개이면, 그 중에 참조변수와 이름이 일치하는 것을 주입

    주입 대상이 변수일 때(Engine engine), 검색된 빈이 1개가 아니면 예외 발생.
    주입 대상이 배열일 때(Engine[] engines), 검색된 빈이 n개라도 예외 발생X.

    // @Autowired가 engine, doors에 자동 주입.
    // 생성자의 @Autowired는 생략 가능
    // setter나 iv에 각각 직접 @Autowired를 붙이는 것 보다 생성자로 주입받도록 하는 것이 좋다.(주입받을 bean을 까먹을 수 있기 때문)
    @Autowired // -> 생략가능 , 만약 생성자가 여러개면 어느 생성자를 이용하여 주입할지 알려줘야 하기 때문에 @Autowired붙여 명확하게 표시
    public Car(@Value("red") String color, @Value("100") int oil, Engine engine, Door[] doors) {
        this.color=color;
        this.oil=oil;
        this.engine=engine;
        this.doors=doors;
    }

    //@Autowired가 engine, doors에 자동 주입
    @Autowired
    public void setEngineAndDoor(Engine engine, Door[] doors) {
        this.engine=engine;
        this.doors=doors;
    }

    class Car{
        @Autowired Engine engine;
        @Autowired Engine[] engines;

        @Autowired(required =  false) // 기본값은 @Autowired(required=true) false로 하면 주입할 빈을 못찾아도 예외 발생 X
        SuperEngine superEngine;
    }

    */

}
