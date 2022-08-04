package com.fastcampus.ch2;

import java.io.FileNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ExceptionController {
	// try-catch해주는 메서드
	@ExceptionHandler({NullPointerException.class,  FileNotFoundException.class}) 
	// @ExceptionHandler : 어떤 예외일 때 호츨하는지, Controller 내에서만 사용 가능
	public String cathcer2(Exception ex, Model m) {
		m.addAttribute("ex", ex); // 모델에 담아서 뷰로 전달
		return "error";
	}
	@ExceptionHandler(Exception.class) 
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 200-> 500 , INTERNAL_SERVER_ERROR : 500
	public String cathcer(Exception ex, Model m) {
		System.out.println("catcher in ExceptionController");
		System.out.println("m="+m);
		//m.addAttribute("ex", ex);
		
		return "error";
	}
	
	@RequestMapping("/ex")
	public String main(Model m) throws Exception {
		// cathcer2(Exception ex, Model m) 과 public String main(Model m)의 모델은 서로 다른 객체
		m.addAttribute("msg", "message from ExceptionController.main");
		throw new Exception("예외가 발생했습니다.");
		// 해당 에러는 가까운 곳에 있는 메서드가 처리한다 
		// 여기서는 GlobalCatcher가 아닌 내부에서 처리
	}
	
	@RequestMapping("/ex2")
	public String main2() throws Exception {
		//throw new NullPointerException("예외가 발생했습니다.");
		throw new FileNotFoundException("예외가 발생했습니다.");
	}
}
