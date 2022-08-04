package com.fastcampus.ch2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
// Session에 id가 있는지 확인
public class BoardController {
	@GetMapping("/list")
	public String list(HttpServletRequest request) {
		if(!loginCheck(request)) {
			//return "redirect:/login/login"; // 로그인을 안했으면 로그인 화면으로 이동
			return "redirect:/login/login?toURL="+request.getRequestURL(); // 로그인을 안했으면 로그인 화면으로 이동
		}

		return "boardList"; // 로그인 한 상태이면, 게시판 화면으로 이동
	}
	
	private boolean loginCheck(HttpServletRequest request) {
		// 1. 세션을 얻어서
		HttpSession session = request.getSession();
		// 2. 세션에 id가 있는지 확인
//		if(session.getAttribute("id")!=null) {
//			return true;
//		}else {return false;}
		return session.getAttribute("id")!=null; // 위와 동일
		
	}
}

/*
[세션이란]
서로 관련된 요청(독립적(관계X)),응답들을 하나로 묶은 것 - 쿠키를 이용
browser마다 개별 저장소(session객체)를 서버에서 제공(1:1)
(쿠키: 브라우저에 저장, 세션: 서버에 저장)

[세션 생성 과정]
브라우저가 요청을 하면 서버가 무조건 세션 객체(저장소)를 만듬
세션 ID가 담긴 쿠키를 생성해서 브라우저로 보내고 브라우저에 쿠키가 저장.
브라우저에서 요청을 보낼 때 세션ID(JESSIONID)가 담긴 쿠키를 같이 보냄.
서버는 JSSESIONID로 확인.
(같은 PC라도 다른 브라우저라면 다른 SESSION ID를 보냄)

[세션 객체 얻기]
HttpSession session = request.getSession();
session.setAttribute("id","asdf");

[세션 종료]
수동 종료 : invalidate() <- 즉시 종료
	    setMaxInactiveInterval(int interval) <-- 지정된 시간(초)후에 예약 종료
자동 종료 : Timeout // web.xml에서 설정, 분단위
<session-config> 
	<session-timeout>30</session-timeout>
</session-config>

세션이 끝나고 나면 세로운 세션ID가 발급

[쿠키 vs 세션]
쿠키 : 브라우저에 저장, 서버부담X, 보안에 불리, 서버 다중화에 유리
세션(HttpSession) : 서버에 저장, 서버부담O, 보안에 유리, 서버 다중화에 불리
서버 다중화에 불리한 이유 : 서버가 여러개라 s1에 저장이 되고 s2에 session 저장되면 에러 발생 --> 두 session 객체간의 동기화 필요


[모든 쿠키 차단인 경우]
새로 고침을 할때마다 세션이 바뀐다.
때문에 GET으로 세션 아이디를 URL뒤에 붙여서 보내야 함.

우리가 직접 붙여주기 힘든데 페이지 소스 검사를 하면 붙어있는 이유는 
<form action="<c:url value="/login/login"/>"에서 url 태그가 자동으로 세션을 붙여줌
서버쪽에서 처리해주는 것이다.

뷰를 작성할 때는 쿠키를 허용하지않는 브라우저를 대비하여 url태그를 이용해야한다.

[모든 쿠키가 허용인 경우]
요청에 쿠키가 와서 서버는 쿠키가 허용됨을 알고 굳이 URL뒤에 JSSESIONID을 붙이지않는다.

[세션을 시작할까?]
session = "true" or session = "false"?
세션은 서버에 부담이 많이 됨 --> 세션 유지기간이 가능한 짧아야 함
(세션 있을 때(세션이 시작한 이후)) 
true, false 둘 다 세션 생성 안함.
(세션 없을 때) 
true(디폴트)는 세션 생성, false는 생성 안함

--> true, false는 세션을 "시작"할까? 에 대한 답

세션이 필요없는 페이지에 session="false"를 주면
세션시간을 줄일 수 있다.
(ex) 
home -> login -> boardController -> board -> home -> login
여기서 home이나 login 페이지에 session="false"

일단 세션이 시작한 경우에는
session=false가 중간에 있어도 상관없음. 이 의미는 단지 세로운 세션을 생성하지않는다는 의미

[즉, session=false란]
1. 세션에 필요없는 jsp화면에 사용
2. 기존 세션에 영향X

*/