package com.fastcampus.ch3.SpringDI_imitate;
import java.io.FileReader;
import java.util.Properties;

// 변경에 유리한 코드(1) - 다형성, factory method
/*
//변경 포인트 2개
SportsCar car = new SportsCar(); --> Truck car = new Truck();
==>
//다형성 --> 변경에 유리
//변경 포인트 1개
Car car = new SportsCar(); --> Car car = new Truck();
==>
Car car = getCar(); // getCar()메서드 하나만 고치면 됨
static Car getCar() {
	return new SportsCar();
}
*/

// 변경에 유리한 코드(2) - Map과 외부 파일
class Car {}
class SportsCar extends Car{}
class Truck extends Car {}
class Engine {}

public class Main1 {
    public static void main(String[] args) throws Exception {
        //Car car = getCar();
        Car car = (Car)getObject("car");
        Engine engine = (Engine)getObject("engine");
        System.out.println("car = " + car);
        System.out.println("engine = " + engine);
    }

    static Object getObject(String key) throws Exception {
        Properties p = new Properties();
        p.load(new FileReader("config.txt"));

        Class clazz = Class.forName(p.getProperty(key));

        return clazz.newInstance();
    }

    static Car getCar() throws Exception {
        // config.txt를 읽어 Properties에 저장
        // Properties는 키-값(key-value)이 (string, string) 타입만 다를 뿐 Map과 똑같은 구조
        Properties p = new Properties();
        p.load(new FileReader("config.txt"));
        /*
		config.txt
		car=com.fastcampus.ch3.SpringDI_imitate.SportsCar
        engine=com.fastcampus.ch3.SpringDI_imitate.Engine

		car => key / com.fastcampus.ch3.diCopy1.SportsCar => value
		*/

        // 클래스 객체(설계도)를 얻어서
        Class clazz = Class.forName(p.getProperty("car"));
        return (Car)clazz.newInstance(); // 객체를 생성해 반환
    }
}

/*
OOP(객체지향 개념)가 등장하게 된 계기는 변경에 유리한 코드를 작성하기 위해서이다.
분리는
1. 변하는 것 / 변하지 않는 것
2. 관심사의 분리
3. 중복 코드(공통 코드)의 분리
를 잘하여야 객체지향적인 설계를 잘할 수 있다.
 */