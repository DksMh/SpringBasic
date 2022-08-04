package com.fastcampus.ch3.SpringDI_imitate4;

// 객체 찾기 - by Name, by Type
/*
AppContext ac=new AppContext();
Car car=(Car)ac.getBean("car"); //이름(id)으로 찾기
Car ca2=(Car)ac.getBean("Car.class"); //타입을 찾기

Object getBean(String id){ //이름으로 찾기
    return map.get(id);
}

Object getBean(Class clazz){ //타입으로 찾기
    for(Object obj:map.values()){
        if(clazz.isinstance(obj)) //obj instanceof clazz
        return obj;
    }
    return null;
}
*/

// 객체 자동 연결 하기(1) - @Autowired
// @Autowired
// 맵을 뒤져 각각의 타입에 맞는 객체주소를 찾아 참조변수에 대입
// by Type, Value를 instanceof로 뒤져서 찾음
/*
// 1. 수동 연결
AppContext ac = new AppContext(); // 객체 저장소 만듬

Car car = (Car)ac.getBean("car"); // 객체를 불러옴
Engine engine = (Engine)ac.getBean("engine");
Door door = (Door)ac.getBean("door");

car.engine = engine; // 수동으로 객체 참조 연결
car.door = door;

class Car {
    Engine engine;
    Door door;
}

// 2. 자동 연결
class Car {
	@Autowired Engine engine;
	@Autowired Door door;
}
*/

/*
// 객체 자동 연결 하기(2) - @Resource
by Name, 해당 Key를 찾아서 value를 대입
첫글자를 소문자로 바꾼게 이름으로 사용됨

Class Car {
    @Resource Engine engine; (= @Resource name = "engine")이 생략됨 Engine ~
    @Resource Door door;
}
Class Car {
    @Reousrce(name="engine2") Engine engine; // 직접 다른 이름을 지정해 줄수도 있음
    @Resource Door door;
}
*/