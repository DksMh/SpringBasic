package com.fastcampus.ch2;

import java.io.FileNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.BAD_REQUEST) // 500->400 , 400 Bad Request.
class MyException extends RuntimeException{
	MyException(String msg){
		super(msg);
	}
	MyException(){this("");}
}

@Controller
public class ExceptionController2 {
	@RequestMapping("/ex3")
	public String main() throws Exception {
		//throw new Exception("예외가 발생했습니다.");
		throw new MyException("예외가 발생했습니다.");
	}
	
	@RequestMapping("/ex4")
	public String main2() throws Exception {
		//throw new NullPointerException("예외가 발생했습니다.");
		throw new FileNotFoundException("예외가 발생했습니다.");
	}
}

/*
[@ExceptionHandler와 @ControllerAdvice]
예외 처리를 위한 메서드를 작성하고 @ExceptionHandler를 붙인다.

@ControllerAdvice로 전역(모든 컨트롤러) 예외 처리 클래스 작성 가능(패키지 지정 가능)
예외 처리 메서드가 중복인 경우, 컨트롤러 내의 예외 처리 메서드가 우선.


[@ResonseStatus]
응답 메세지의 상태 코드를 변경할 때 사용

1. 예외 처리 메서드
예외처리를 하면 200(성공)이 나옴. --> 405로 변경
@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED) //405 Method Not Allowed.
@ExceptionHandler({NullPointerException.class, ClassCastException.class}) {
public String catcher2(Exception ex, Model m)
	m.addAttribute("ex", ex);
	return "error";
}

2. 사용자 정의 예외 클래스
사용자 정의 예외 클래스는 기본 500(디폴트). --> 400으로 변경
@ResponseStatus(HttpStatus.BAD_REQUEST) //400 Bad Request.
class MyException extends RuntimeException {
	MyExeption(String msg) {
		super(msg);
	}
	
	MyException() {
		this("");
	}
}

[상태 코드별 뷰 맵핑]
web.xml 에서 에러코드 별로 해당 에러발생시 페이지로 넘어가게 만든다

[예외 종류별 뷰 맵핑]
servlet-context.xml에서 SimpleMappingExceptionResolver등록
	
<beans:bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
<beans:property name="defaultErrorView" value="error"/>
	<beans:property name="exceptionMappings">
			<beans:props>
			<beans:prop key="com.fastcampus.ch2.MyException">error400</beans:prop>
			</beans:props>
	</beans:property>
<beans:property name="statusCodes">
	<beans:props>
			<beans:prop key="error400">404</beans:prop>
	</beans:props>
</beans:property>
</beans:bean>

--> MyException이 예외종류, error400이 에러뷰

[ExceptionResolver]
DispatcherServlet에서 예외를 처리하기 위해서 
handlerExceptionResolver로 등록되어 있는 것을 찾아봄(순서대로)
1. ExceptionHandlerExceptionResolver
2. ResponseStatusExceptionResolver
3. DefaultHandlerExceptionResolver
--> 스프링의 예외처리 기본전략

[스프링에서의 예외 처리]
컨트롤러 메서드 내에서 try-catch로 처리
컨트롤러에 @ExceptionHandler메서드가 처리
@ControllerAdvice클래스의 @ExceptionHandler메서드가 처리
예외 종류별로 뷰 지정 - SimpleMappingExceptionResolver
응답 상태 코드별로 뷰 지정 - <error-page> - web.xml

*/

