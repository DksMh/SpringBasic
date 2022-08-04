package com.fastcampus.ch2;

import java.io.IOException;
import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class YoilTellerMVC5 {// http://localhost/ch2/getYoilMVC5
	// 그래서 예외처리
	@ExceptionHandler(Exception.class)
	public String cathcer(Exception ex) {
		ex.printStackTrace();
		return "yoilError";
	}
	@RequestMapping("/getYoilMVC5") 
	//public String main(@ModelAttribute("myDate") MyDate date,Model model) { //원래 키를 써줘야함
	//public String main(@ModelAttribute MyDate date, Model model) { // 위와 동일, 반환 타입은 String
	public String main(MyDate date, Model model) { // 위와 동일
		// @ModelAttribute : 생략가능 MyDate date : 참조형 변수
		System.out.println("myDate="+date);
		
		// 1. 유효성 검사
		if(!isValid(date)) {
			return "yoilError";
		}
		
		// @ModelAttribute 사용하여 이 부분이 필요없어짐
        // 2. 처리
        // char yoil = getYoil(date);
        
        // 3. 계산한 결과를 model에 저장
        // model.addAttribute("mydate", date);
        // model.addAttribute("yoil", yoil);
        
        // 4. 결과를 보여줄 view를 지정
        //mv.setViewName("yoil");
        
        //return mv;
        return "yoil"; // /WEB-INF/views/yoil.jsp
        
    }
	
	private boolean isValid(MyDate date) {
		return isValid(date.getYear(), date.getMonth(), date.getDay());
	}
	//  @ModelAttribute("yoil")을 적어주면 메소드 자동 호출, 그 결과를 모델에 저장
	private @ModelAttribute("yoil") char getYoil(MyDate date) { 
		return getYoil(date.getYear(), date.getMonth(), date.getDay());
	}
	
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