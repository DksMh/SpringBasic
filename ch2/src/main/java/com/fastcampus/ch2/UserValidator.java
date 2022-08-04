package com.fastcampus.ch2;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
//		return User.class.equals(clazz); // 검증하려는 객체가 User타입인지 확인
		return User.class.isAssignableFrom(clazz); // clazz가 User 또는 그 자손인지 확인
	}

	@Override
	public void validate(Object target, Errors errors) {
		System.out.println("UserValidator.validate() is called");

		User user = (User)target;

		String id = user.getId();

		// if(id==null || "".equals(id.trim())) {
		// errors.rejectValue("id", "required");
		// }
		// rejectIfEmptyOrWhitespace : 값이 null이거나 길이가 0이거나 값이 공백 문자로 구성되어 있는 경우 에러 코드를 추가
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pwd", "required");

		if (id == null || id.length() < 5 || id.length() > 12) {
			errors.rejectValue("id", "invalidLength");
		}
	}
}

/*
	[데이터의 변환과 검증]
	[Validator]
	객체를 검증하기 위한 인터페이스. 객체 검증기(validator)구현에 사용
	
	public interface Validator {
		//이 검증기로 검증가능한 객체인지 알려주는 메서드
		boolean supports(Class<?> clazz);
		//객체를 검증하는 메서드 - target: 검증할 객체, errors:검증시 발생한 에러저장소
		void validate(Object target, Errors errors);
	}
	
	[Validator를 이용한 검증 - 수동]
	@PostMapping("/register/add")
	public String save(Model model, User user, BindingResult result) {
		String msg="";
		String id = user.getId();
		if(id==null || "".equals(id.trim())) {
			model.addAttribute("msg", "id는 필수 입력항목입니다.");
			return "redirect:/register/add";
		}
		if(id==null||id.length()<5||id.length()>12) {
			model.addAttribute("msg","id의 길이는 5글자이상 12글자 미만입니다.");
			return"redirect:/register/add";
		}
	
	=> 수동검증(분리)
	UserValidator userValidator = new UserValidator();
	userValidator.validate(user, result); //validator로 검증
	if(result.hasErrors()) { //에러가 있으면
		return "registerForm";
	}
	
	[Validator를 이용한 검증 - 자동]
	자동검증을 사용하려면 검증하려는 객체 앞에 @Valid 애너테이션만 붙이면 된다.
	EX) public String save(Model model, @Valid User user, BindingResult result) {
	
	[글로벌 Validator]
	하나의 Validator로 여러 객체를 검증할 때, 글로벌 Validator로 등록
	
	[글로벌 Validator로 등록 방법]
	Servlet-context.xml 설정 파일 안에 globalValidator을 bean으로 등록해줘야 한다.
	<annotation-driven validator="globalValidator"/>
	<beans:bean id="globalValidator" class="com.fastcampus.ch2.GlobalValidator"/>
	
	[글로벌 Validator와 로컬 Validator를 동시에 적용하는 방법]
	@InitBinder
	public void toDate(WebDataBinder binder) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
	
		binder.addValidators(new UserValidator()); // 로컬validator를 WebDataBinder에 등록
	
	[MessageSource]
	다양한 리소스에서 메세지를 읽기 위한 인터페이스
	프로퍼티 파일을 메시지 소스로 하는 ResourceBundleMessageSource를 servlet-context.xml에 등록
	
	
	
*/

