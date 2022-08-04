package com.fastcampus.ch2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// 1. 원격 호출 가능한 프로그램으로 등록
@Controller
public class Hello {
	int iv = 10; // 인스턴스 변수
	static int cv = 20;
	
	// 2. URL과 메서드를 연결
	@RequestMapping("/hello")
	private void main() { // 인스턴스 메서드 - iv, cv 둘다 사용가능
//		static을 안써도 되는 이유
//		public void main() {
//		static을 안써도 되는 이유는 호출했을 때
//		인스턴스 메소드이다.
//		인스턴스 메소드는 객체 생성후 호출
//		이는 중간에 누군가 객체 생성을 해준다는 의미(톰캣 내부에서 해준다)
//		그다음에 메서드 호출
//		public 이 아니라 private 을 사용해도 되는 이유
//		private해도 되는 건 이 메소드를 외부에서 호출가능하게 하겠다는 @RequestMapping 때문
//		그래서 접근제어자가 무엇이든지 호출하게 된다.
//		내부에서는 private이고 외부에서는 호출할 수 있다.
		System.out.println("Hello :-D -- private"); // -> 콘솔에 출력됨
		System.out.println(cv); // OK
		//System.out.println(iv); // OK
	}
	
	public static void main2() { // static메서드 - cv만 사용가능
		System.out.println(cv); // OK
		//System.out.println(iv); // 에러
	}
}
