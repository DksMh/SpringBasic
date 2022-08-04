package com.fastcampus.ch2;

import java.io.IOException;
import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class YoilTellerMVC6 {// http://localhost/ch2/getYoilMVC6
	// 그래서 예외처리
	@ExceptionHandler(Exception.class)
	public String cathcer(Exception ex,BindingResult result) {
		System.out.println("result="+result);
		FieldError error = result.getFieldError();
		
		System.out.println("Code=" + error.getCode());
		System.out.println("Field=" + error.getField());
		System.out.println("Msg=" + error.getDefaultMessage());
		ex.printStackTrace();
		return "yoilError";
	}
	@RequestMapping("/getYoilMVC6") 
	//public String main(@ModelAttribute("myDate") MyDate date,Model model) throws IOException { //원래 키를 써줘야함
	public String main(MyDate date, BindingResult result){ 
		System.out.println("result="+result);
		
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

/*
public String main(MyDate date, BindingResult result){
 
WebDataBinder
http://localhost/ch2/getYoilMVC5?year=2021&month=10&day=1을 하면
name : "year" value : "2021"로 value가 string
MyDate는  2021 --> year로 int 인데
WebDataBinder을 거친다.
WebDataBinder은 1. 타입 변환 / 2. 데이터 검증을 한다.
타입 변환을 하면 BindingResult에 결과나 에러가 있으면 저장
BindingResult을 컨트롤러에 넘겨줄 수 있다.
그럼 컨트롤러에서는 그 작업 결과를 가지고 처리
그리고 BindingResult는 바인딩할 객체 바로 뒤에 온다. 
*/