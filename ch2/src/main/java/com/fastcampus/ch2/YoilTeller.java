package com.fastcampus.ch2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// 년월일을 입력하면 요일을 알려주는 프로그램
@Controller
public class YoilTeller {
	//public static void main(String[] args) {
	@RequestMapping("/getYoil")
	public void main(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 1. 입력
//		String year = args[0];
//		String month = args[1];
//		String day = args[2];
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		
		int yyyy = Integer.parseInt(year);
		int mm = Integer.parseInt(month);
		int dd = Integer.parseInt(day);
		
		// 2. 작업
		Calendar cal = Calendar.getInstance();
		// getInstance()는 최초에 할당된 하나의 메모리를 계속 쓰는 방식
		// getInstance()를 이용해서 메모리 주소값을 보면 모두 같다. 
		// 이와 대비하여, new 생성자를 이용하면 모두 주소값이 다른 것을 확인 할 수 있다.
		cal.set(yyyy, mm -1, dd);
		
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		// calendar가 가르키는(의미하는) 특정 날짜가 무슨 요일인지 알기 위해 사용 1:일요일, 2:월요일
		char yoil = " 일월화수목금토".charAt(dayOfWeek);
		// charAt이란 string 타입으로 받은 문자열을 char 타입으로 한 글자만 받는 함수
		
		// 3. 출력
		response.setContentType("text/html"); // 응답의 형식을 html로 지정
		response.setCharacterEncoding("utf-8"); // 응답의 인코딩을 utf-8로 지정 --> 안적어주면 한글깨짐
		PrintWriter out = response.getWriter(); // response객체에서 브라우저로의 출력 스트림(out)을 얻는다.
		out.println(year + "년 " + month + "월 " + day + "일은");
		out.println(yoil +" 요일입니다.");
		// System.out.println(year + "년 " + month + "월 " + day + "일은 ");
        // System.out.println(yoil + "요일입니다.");// out.println("<html>");
        // out.println("<head>");
        // out.println("</head>");
        // out.println("<body>");
        // out.println(year + "년 " + month + "월 " + day + "일은 ");
        // out.println(yoil + "요일입니다.");
        // out.println("</body>");
        // out.println("</html>");
        out.close();

	}

}
