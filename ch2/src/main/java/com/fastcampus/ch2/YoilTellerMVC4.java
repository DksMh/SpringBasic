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

@Controller
public class YoilTellerMVC4 {// http://localhost/ch2/getYoilMVC4
	// 그래서 예외처리
	@ExceptionHandler(Exception.class)
	public String cathcer(Exception ex) {
		ex.printStackTrace();
		return "yoilError";
	}
	@RequestMapping("/getYoilMVC4") 
	public String main(MyDate date,Model model) {
	//public String main(int year,int month,int day,Model model) throws IOException {
		
		// 1. 유효성 검사
		if(!isValid(date)) {
			return "yoilError";
		}
        // 2. 처리
        char yoil = getYoil(date);
        
        // 3. 계산한 결과를 model에 저장
        model.addAttribute("mydate", date);
        model.addAttribute("yoil", yoil);
        
        // 4. 결과를 보여줄 view를 지정
        
        return "yoil"; // /WEB-INF/views/yoil.jsp
        
    }
	
	private boolean isValid(MyDate date) {
		return isValid(date.getYear(), date.getMonth(), date.getDay());
	}
	
	private char getYoil(MyDate date) {
		return getYoil(date.getYear(), date.getMonth(), date.getDay());
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