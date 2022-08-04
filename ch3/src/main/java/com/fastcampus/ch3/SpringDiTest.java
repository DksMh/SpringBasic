//package com.fastcampus.ch3;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.GenericXmlApplicationContext;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.Arrays;
//
//// @Component("engine")
//class Engine{} // = <bean id="engine" class="com.fastcampus.ch3.Engine"/>
//@Component class SuperEngine extends Engine{}
//@Component class TurboEngine extends Engine{}
//@Component class Door{}
//@Component
//class Car{
//    @Value("red") String color;
//    // @Value 애너테이션을 사용하면 정수형 타입도 문자열처럼 쌍따옴표를 사용하지만,
//    //실제로 값이 들어갈때는 정수형 타입으로 변환되어 오류가 발생하지 않는다
//    @Value("100") int oil;
//    // @Autowired //byType
//    // @Qualifier("superEngine") //타입으로 먼저 검색 후 n개면 @Qualifier 애너테이션을 사용하여 bean의 name을 입력해 어떤 후보를 사용할 건지 결정해주면 오류가 미발생
//    @Resource(name="superEngine") // byName, @Qualifier ->  @Resource 애너테이션 대체 가능
//    Engine engine; // byType - 오류 미발생
//    /* 오류 미발생 이유
//    @Autowired 애너테이션을 사용하면 Type으로 먼저 검색하고 일치하는 객체가 여러개일 경우,
//    Name으로 다시 검색(ex : engine, superEngine, turboEngine)해 객체를 찾아주기 때문에 타입이 일치하는 객체가 여러개여도 오류가 발생하지 않는다.
//     */
//    @Autowired Door[] doors;
//
//    public Car(){} //기본 생성자를 꼭 만들어주자
//    public Car(String color, int oil, Engine engine, Door[] doors) {
//        this.color = color;
//        this.oil = oil;
//        this.engine = engine;
//        this.doors = doors;
//    }
//
//    public void setColor(String color) {
//        this.color = color;
//    }
//
//    public void setOil(int oil) {
//        this.oil = oil;
//    }
//
//    public void setEngine(Engine engine) {
//        this.engine = engine;
//    }
//
//    public void setDoors(Door[] doors) {
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
//
//public class SpringDiTest {
//    public static void main(String[] args) {
//        ApplicationContext ac = new GenericXmlApplicationContext("config.xml");
//        // Car car = (Car) ac.getBean("car"); // byName, 아래와 같은 문장
//        Car car = ac.getBean("car", Car.class); // byName
//        // Car car2 = (Car) ac.getBean(Car.class); // byType
//
//        // Engine engine = (Engine) ac.getBean("engine"); // byName
//        //Engine engine = (Engine) ac.getBean(Engine.class); // byType --> 오류 발생 : 같은 타입이 3개라 에러
//        /* 오류 발생 이유
//        일치하는 타입이 여러 개일 경우, Type으로 bean을 찾게되면 오류 발생
//        타입이 일치하는 객체가 여러 개 있을 경우, Name으로 bean을 찾아야 오류 발생하지 않는다.
//        */
//        // Engine engine = (Engine) ac.getBean("superEngine"); // byName
//
//        // Door door = (Door) ac.getBean("door");
//
//        // car.setColor("red");
//        // car.setOil(100);
//        // car.setEngine(engine);
//        // car.setDoors(new Door[]{ac.getBean("door", Door.class), (Door) ac.getBean("door")});
//        System.out.println("car = " + car);
//        // System.out.println("car2 = " + car2);
//        // System.out.println("engine = " + engine);
//        // System.out.println("door = " + door);
//        // System.out.println("SuperEngine = " + engine);
//    }
//}
