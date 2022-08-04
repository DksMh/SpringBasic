//package com.fastcampus.ch3;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Scope;
//import org.springframework.context.support.GenericXmlApplicationContext;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
//@Component
//@Scope("prototype")
//class Door {}
//@Component class Engine {}
//@Component class TurboEngine extends Engine {}
//@Component class SuperEngine extends Engine {}
//
//@Component
//class Car {
//    @Value("red") String color;
//    @Value("100") int oil;
//    // @Autowired
//    Engine engine;
//    // @Autowired
//    Door[] doors;
//
//    public Car(){} // -> engine=null, doors=null 이 나옴
//    // 이유 : 생성자가 2개인데 기본 생성자가 있어 기본 생성자로 사용해 생성해 null이 나옴
//
//    @Autowired // -> 생략가능,
//    // 생성자가 여러개면 어느 생성자를 이용하여 주입할지 알려줘야 하기 때문에 @Autowired붙여 명확하게 표시
//    public Car(@Value("red") String color, @Value("100") int oil, Engine engine, Door[] doors) {
//        this.color = color;
//        this.oil = oil;
//        this.engine = engine;
//        this.doors = doors;
//    }
//
//    @Override
//    public String toString() {
//        return "Car{" +
//                "color='" + color + '\'' +
//                ", oil=" + oil +
//                ", engine=" + engine +
//                ", doors=" + Arrays.toString(doors) +
//                '}';
//    }
//}
//
//public class ApplicationContextTest02 { // SpringDI_theory02
//    public static void main(String[] args) {
//        ApplicationContext ac = new GenericXmlApplicationContext("config.xml");
////      Car car = ac.getBean("car", Car.class); // 타입을 지정하면 형변환 안해도됨. 아래의 문장과 동일
//        Car car  = (Car) ac.getBean("car"); // 이름으로 빈 검색
//        Car car2 = (Car) ac.getBean(Car.class);   // 타입으로 빈 검색
//        System.out.println("car = " + car);
//
//    }
//}
