package com.fastcampus.ch3.SpringDI_imitate3;

import com.google.common.reflect.ClassPath;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

// 자동 객체 등록하기 - Component Scanning
/*
@Component(객체 자동등록)
1. 패키지 내의 모든 클래스를 읽어서 Set에 저장
2. 패키지 내에 @Component붙은 클래스 찾기
3. 객체 생성해서 map에 저장

guava 라이브러리를 사용하여, reflection API를 쉽게 사용 할 수 있다
*/
@Component class Car {}
@Component class SportsCar extends Car{}
@Component class Truck extends Car {}
//@Component
class Engine {} // map에 저장되지 않아 null이 나옴

class AppContext {
    Map map; //객체 저장소

    AppContext(){
        map = new HashMap();
        doComponentScan();

    }

    private void doComponentScan() {
        try {
            //1. 패키지내의 클래스 목록을 가져온다
            //2. 반복문으로 클래스를 하나씩 읽어와서 @Component이 붙어 있는지 확인
            //3. @Component가 붙어있으면 객체를 생성해서 map에 저장
            ClassLoader classLoader = AppContext.class.getClassLoader();
            ClassPath classPath = ClassPath.from(classLoader);
            // 1. 패키지 내의 모든 클래스를 읽어서 set에 저장
            Set<ClassPath.ClassInfo> set = classPath.getTopLevelClasses("com.fastcampus.ch3.SpringDI_imitate3");

            for(ClassPath.ClassInfo classInfo : set){ // 2. 패키지 내에 @Component붙은 클래스 찾기
                Class clazz = classInfo.load();
                Component component = (Component)clazz.getAnnotation(Component.class);
                if(component != null){
                    String id = StringUtils.uncapitalize(classInfo.getSimpleName()); // Car -> car
                    map.put(id, clazz.newInstance()); // 3. 객체 생성해서 map에 저장
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    Object getBean(String key){ return map.get(key); } // byName으로 검색

    Object getBean(Class clazz){ // byType 으로 검색
        for(Object obj : map.values()){
            if(clazz.isInstance(obj)){
                return obj;
            }
        }
        return null;
    }
}

public class Main3 {
    public static void main(String[] args) throws Exception {
        AppContext ac = new AppContext();
        Car car = (Car)ac.getBean("car"); //byName으로 객체를 검색
        Car car2 = (Car)ac.getBean(Car.class); //byType으로 객체를 검색
        Engine engine = (Engine)ac.getBean("engine");
        System.out.println("car = " + car);
        System.out.println("engine = " + engine);

    }
}