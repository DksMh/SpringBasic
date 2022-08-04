package com.fastcampus.ch2;

import java.lang.reflect.Method;

public class PrivateMethodCall {
	public static void main(String[] args) throws Exception {
//		Hello hello = new Hello();
//		hello.main(); // private이라 외부 호출이 불가해 에러
		
		// Reflection API를 사용 - 클래스 정보를 얻고 다룰 수 있는 강력한 기능 제공
		// java.lang.reflect패키지 제공
		// Hello 클래스이 Class객체(클래스의 정보를 담고 있는 객체)를 얻어온다.)
		Class helloClass = Class.forName("com.fastcampus.ch2.Hello");
		// 클래스 파일(*.class)이 메모리에 올라갈 때, 클래스 파일마다 Class 객체가 하나씩 생성된다.
		Hello hello = (Hello) helloClass.newInstance(); // Class 객체가 가진 정보로 객체 생성
		// 반환타입(newInstance)이 Object라 형변환이 필요함
		Method main = helloClass.getDeclaredMethod("main"); // main 메소드에 대한 정보를 가져옴
		main.setAccessible(true); // private인 main()을 호출가능하게 한다.
		
		main.invoke(hello); // hello.main() 과 같다.
		
	}
}
