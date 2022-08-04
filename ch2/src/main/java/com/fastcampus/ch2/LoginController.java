package com.fastcampus.ch2;

import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
// Session에 id 저장
public class LoginController {
    @GetMapping("/login")
    public String loginForm() {
        return "loginForm";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
    	// 1. 세션 종료
    	session.invalidate();
    	// 2. 홈으로 이동
    	return "redirect:/";
    	
    }

    @PostMapping("/login")
    public String login(String id, String pwd, String toURL, boolean rememberId, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	System.out.println("id="+id);
		System.out.println("pwd="+pwd);
		System.out.println("rememberId="+rememberId);       
    	//1. id와 pwd를 확인
        //일치하지 않으면, loginForm으로 이동
        if(!loginCheck(id, pwd)) {
            String msg = URLEncoder.encode("id또는 pwd가 일치하지 않습니다.", "utf-8");
            return "redirect:/login/login?msg="+msg;
        }

        // 2-2. id와 pwd가 일치하면
        //세션 객체를 얻어오기
      	HttpSession session = request.getSession();
      	//세션 객체에 id를 저장
      	session.setAttribute("id",id);
      	
        if(rememberId) {
	        // 2-2-1. 쿠키 생성
	        Cookie cookie = new Cookie("id", id); // 넘어온 id를 준다.
	        // 2-2-2. 응답에 저장
	        response.addCookie(cookie);
        }else {
        	// 쿠키 삭제
        	Cookie cookie = new Cookie("id", id);
        	cookie.setMaxAge(0); // 쿠키를 삭제
        	//응답에 저장
        	response.addCookie(cookie);
        }
        // 3. 홈으로 이동
        toURL = toURL == null || toURL.equals("") ? "/" : toURL;
        
        return "redirect:"+toURL;
    }

    private boolean loginCheck(String id, String pwd) {
        return "asdf".equals(id) && "1234".equals(pwd);
    }
}

/*
[쿠키]
이름과 값의 쌍으로 구성된 작은 정보. 아스키 문자만 가능
서버에서 생성 후 전송, 브라우저에 저장. 유효기간 이후 자동 삭제
서버에 요청시 domain,path가 일치(하위경로 포함)하는 경우에만 자동 전송
클라이언트 식별 기술

(EX)
Domain : fastcampus.co.kr
path : /ch2/login
id = asdf ( id : name , asdf : value )
Max-Age : 60*60*24 --> 유효기간 

예시)
서버 : 도서관
처음가면 id카드를 발급
그 후에는 id카드로 책을 빌려야하는데 올때마다 id카드 제출
도서관 id카드면 도서관갈 때 사용하고 그 외의 id카드는 다른 곳에서 사용

[쿠키 생성]
Cookie cookie = new Cookie("id","asdf"); //쿠키 생성
cookie.setMaxAge(60*60*24); //유효기간 설정(초) 현재는 24시간
response.addCookie(cookie); //응답에 쿠키 추가

// 응답에 이렇게 추가됨.
Set-Cookie: id=asdf; Max-Age=86400; Expires=Tue, ... // Max-Age(상대시간)부터는 유효기간,
// Max-Age은 상대시간, Expires=Tue,...는 절대시간


[쿠키 삭제]
Cookie cookie = new Cookie("id",""); //변경할 쿠키와 같은 이름의 쿠키 생성
cookie.setMaxAge(0); //유효기간을 0으로 설정(삭제)
response.addCookie(cookie); //응답에 쿠키 추가

[쿠키 변경]
Cookie cookie = new Cookie("id",""); //변경할 쿠키와 같은 이름 쿠키 생성.
cookie.setValue(URLEncoder.encode("김진수")); //값의 변경
cookie.setDomain("www.fastcampus.co.kr"); //도메인의 변경
cookie.setPath("/ch2"); //경로의 변경
cookie.setMaxAge(60*60*24*7); //유효기간의 변경
response.addCookie(cookie); //응답에 쿠키 추가  

[쿠키 읽어 오기]
Cookie[] cookies = request.getCookies(); //쿠키 읽기 : 쿠키가 여러 개일 수 있어 배열

for(Cookie cookie : cookies) {
	String name = cookie.getName();
	String value = cookie.getValue();
	
	System.out.printf("[cookie]name=%s, value=%s%n", name, value);
}
*/