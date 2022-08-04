package com.fastcampus.ch2;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

// 년월일을 입력하면 요일을 알려주는 프로그램
@Controller
public class YoilTellerMVC2 {// http://localhost/ch2/getYoilMVC2
	// 그래서 예외처리
	@ExceptionHandler(Exception.class)
	public String cathcer(Exception ex) {
		ex.printStackTrace();
		return "yoilError";
	}
	@RequestMapping("/getYoilMVC2") 
	public String main(@RequestParam(required = true) int year,
			@RequestParam(required = true) int month,
			@RequestParam(required = true) int day,Model model) throws IOException {
			// -> /getYoilMVC만 하면 400(클라이언트 오류) 발생함
	//public String main(int year,int month,int day,Model model) throws IOException {
	//public void main(int year,int month,int day,Model model) throws IOException {
	//public ModelAndView main(int year,int month,int day) throws IOException {
		// 여기서 String은 view이름
		// String 대신 void를 사용하면 mapping된 주소로 해석된다.
        // ModelAndView은 모델과 뷰를 합침
		ModelAndView mv = new ModelAndView(); // 모델생성

		// 1. 유효성 검사
		if(!isValid(year, month, day)) {
			return "yoilError";
		}
        // 2. 처리 요일 계산
        char yoil = getYoil(year, month, day);
        
        // 3. 계산한 결과를 model에 저장
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("day", day);
        model.addAttribute("yoil", yoil);
//        mv.addObject("year", year);
//        mv.addObject("month", month);
//        mv.addObject("day", day);
//        mv.addObject("yoil", yoil);
        
        // 4. 결과를 보여줄 view를 지정
        //mv.setViewName("yoil");
        
        //return mv;
        return "yoil"; // /WEB-INF/views/yoil.jsp
        
    }
	
	// Refactor -> Extract Method로 별도의 메소드로 만듬
	private char getYoil(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        //char yoil = " 일월화수목금토".charAt(dayOfWeek);
        return " 일월화수목금토".charAt(dayOfWeek);
	}
	
	private boolean isValid(int year, int month, int day) {    
    	if(year==-1 || month==-1 || day==-1) 
    		return false;
    	
    	return (1<=month && month<=12) && (1<=day && day<=31); // 간단히 체크 
    }
}