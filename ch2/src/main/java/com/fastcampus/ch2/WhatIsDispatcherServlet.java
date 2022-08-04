package com.fastcampus.ch2;

public class WhatIsDispatcherServlet {
	/*
		[DispatcherServlet(디스패처 서블릿)]
		디스패처 서블릿은 HTTP 프로토콜로 들어오는 모든 요청을 가장 먼저 받아 
		적합한 컨트롤러에 위임해주는 프론트 컨트롤러(Front Controller)
		
		[DispatcherServlet(디스패처 서블릿)의 장점]
		DispatcherServlet이 해당 어플리케이션으로 들어오는 모든 요청을 핸들링해주고 공통 작업을 처리면서 상당히 편리하게 이용
		
		[Spring MVC의 요청 처리 과정]
		// 해당 그림은 resources/img/WhatIsDispatcherServlet.png에 위치
		[HandlerMapping, HandlerAdapter]
		
		HandlerMapping은 Map의 형태로(key=URL, value=메서드) 저장해 놓고 있다.
		
		요청이("/ch2/register/add) 오면 DispatcherServlet이 받아
		HandlerMapping에게서 URL에 맞는 메서드를 찾아 메서드 정보를 받아서
		그 메서드에 맞는 HandlerAdapter를 찾는다.
		
		그 HandlerAdapter과 연결된 서블릿이나 컨트롤러로 연결하는데
		HandlerAdapter로 인해 느슨한 연결(변경에 유리)이다.
		
		1. 클라이언트의 요청을 디스패처 서블릿이 받음
		2. 요청 정보를 통해 요청을 위임할 컨트롤러를 찾음
		3. 요청을 컨트롤러로 위임할 핸들러 어댑터를 찾아서 전달함
		4. 핸들러 어댑터가 컨트롤러로 요청을 위임함
		5. 비지니스 로직을 처리함
		6. 컨트롤러가 반환값을 반환함
		7 .HandlerAdapter가 반환값을 처리함
		8. 서버의 응답을 클라이언트로 반환함
		
		[ViewResolver]
		ViewResolver 중에 하나가 InternalResourceViewResolverd(Servlet-context.xml)
		
		DispatcherServlet이 Controller에서 "registerForm"을 받으면
		ViewResolver에게 물어보고
		ViewResolver는 정확한 주소("WEB-INF/views/registerForm.jsp")를 알려주고, 
		DispatcherServlet은 registerForm.jsp로 데이터가 담겨있는 모델을 전달한다.
		registerForm.jsp은 모델을 이용해서 응답 결과를 만들고 클라이언트에 응답한다.
		
		[JstlView(View 인터페이스)]
		느슨한 결합(변경 유리)
		DispatcherServlet과 registerForm.jsp 사이에 존재
		
		[DispatcherServlet의 소스 분석]
		DispatcherServlet.class는 spring-webmvc-5.0.7.RELEASE.jar에 포함
		소스 파일 위치 - org/springframework/web/servlet/DispatcherServlet.java
		기본 전략 - org/springframework/web/servlet/DispatcherServlet.properties
		(기본 전략 : 기본적으로 사용하는 클래스)
		
		주요 메서드
		initStrategies() - 기본 전략을 초기화
		doService() - doDispatch() 호출
		doDispatch() - 실제 요청을 처리
		processDispatchResult() - 예외가 발생했는지 확인, 발생하지 않았으면 render() 호출
		render() - 응답결과를 생성해서 전송 
		
		--> 차례대로 호출해서 응답결과를 최종적으로 클라이언트에게 보여줌
	*/
}
