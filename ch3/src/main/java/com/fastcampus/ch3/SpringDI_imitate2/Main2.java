package com.fastcampus.ch3.SpringDI_imitate2;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

// 객체 컨테이너(ApplicationContext) 만들기
//객체 컨테이너 : 객체 저장소

class Car {}
class Truck extends Car {}
class SportsCar extends Car{}
class Engine {}

class AppContext {
    Map map; //객체 저장소

    AppContext(){
        try {
            Properties p = new Properties();
            p.load(new FileReader("config.txt"));

            //Properties에 저장된 내용을 Map에 저장
            map = new HashMap(p);

            //반복문으로 클래스 이름을 얻어서 객체를 생성해서 다시 map에 저장
            for(Object key : map.keySet()){
                Class clazz = Class.forName((String)map.get(key));
                map.put(key, clazz.newInstance());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    Object getBean(String key){ // map에 담겨있는 객체 반환
        return map.get(key);
    }
}

public class Main2 {
    public static void main(String[] args) throws Exception {
        AppContext ac = new AppContext();
        Car car = (Car)ac.getBean("car");
        Engine engine = (Engine)ac.getBean("engine");
        // getObject -> getBean로 바꾼 것 / Bean(자바빈에서 온 것, 자바빈은 객체이다.)
        System.out.println("car = " + car);
        System.out.println("engine = " + engine);

    }
}