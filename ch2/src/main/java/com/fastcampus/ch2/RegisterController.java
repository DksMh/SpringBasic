package com.fastcampus.ch2;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/register")
public class RegisterController {
	
	@InitBinder // -> 특정 컨트롤러내에서 변환 < WebBindingInitalizer : 모든 컨트롤러내에서 변환
	public void toDate(WebDataBinder binder) {
		// 타입을 변환할때 등록한 커스텀변환기로 이 메서드를 먼저 확인
		// 있으면 여기에 맞게 변환을 하고 없으면 스프링이 가지고 있는 디폴드 변환기로 변환
		// @DateTimeFormat을 지정한 format으로 날짜를 변환할 수 있어 밑 2줄을 주석처리해도 돌아감
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		ConversionService conversionService = binder.getConversionService();
		System.out.println("conversionService="+conversionService);
		// binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false)); // false : 빈값을 허용할 것인지 아닌지
		binder.registerCustomEditor(String[].class, "hobby", new StringArrayPropertyEditor("#")); // #로 구분(구분자를 매개변수로 줘 문자열을 split하여 배열로 저장)
		// PropertyEditor - 양방향 타입 변환이 가능 하다 (String -> 타입 / 타입 -> String) 특정 타입이나 이름의 필드이름 사용가능
		// "hobby"을 넣으면 hoddy 필드에만 적용
		// 디폴트 PropertyEditor - 스프링이 기본적으로 제공
		// 커스텀 PropertyEditor - 사용자가 직접 구현. PropertyEditorSupport를 상속하면 편리
		
		// UserValidator 자동검증
		//binder.setValidator(new UserValidator()); // UserValidator를 WebDataBinder의 로컬validator(컨트롤러 내에서만 사용)로 등록
		//binder.addValidators(new UserValidator()); 
		// 글로벌 Validator로 검증
		List<Validator> vaildatorList = binder.getValidators();
		System.out.println("vaildatorList="+vaildatorList);
		
	} 
	
	@RequestMapping(value="/add", method={RequestMethod.GET, RequestMethod.POST}) // GET, POST 둘다 허용 (디폴트)
	// @RequestMapping("/register/add") // 위와 동일		
	//@GetMapping("/register/add") // 신규 회원 입력화면(get만 사용)
	public String register() {return "registerForm";}  // WEB-INF/views/registerForm.jsp // 주소 연결만 하는 기능을 한다. 
	// servlet-context에서 <view-controller path="/register/add" view-name="registerForm"/> 와 동일하다.
		
	//	@RequestMapping("/register/save") 
		/*	이경우 http://localhost/ch2/register/save?id=asdf&pwd=1234 처럼 get방식으로 가입을 할 수 있어 
			패스워드 노출이나 자동 회원가입이 될 수 있다. */
	//	public String save() {return "registerInfo";}
	// @PostMapping("/register/add") // url 맵핑 충돌이 날 수 있는 상황이지만, 메서드가 다르면 충돌X
	
	//@RequestMapping(value="/save", method=RequestMethod.POST) // get방식으로 가입 불가		
	@PostMapping("/save") // 스프링 4.3부터 추가
	public String save(@Valid User user, BindingResult result, Model m) throws Exception {
		System.out.println("result="+result);
		System.out.println("user="+user);
		
		// 컨트롤러내에서 안하고 UserValidator통해 검증 - 수동검증(UserValidator 직접 생성, validate()직접 호출)
		// UserValidator userValidator = new UserValidator();
		// userValidator.validate(user, result); // BindingResult는 Errors의 자손
		
		// User 객체를 검증한 결과 에러가 있으면 registerForm을 이용해 에러를 보여줘야 함
		if(result.hasErrors()) {
			return "registerForm";
		}
		
		// 1. 유효성 검사
//		if(!isValid(user)) {
//			String msg = URLEncoder.encode("id를 잘못입력하셨습니다.", "utf-8");
//			//return "redirect:/register/add?msg="+msg; // URL재작성(rewriting)
//			m.addAttribute("msg", msg);
//			//return "redirect:/register/add"; // redirect : 재요청
//			return "forward:/register/add";
//		}
		// 2. DB에 신규회원 정보를 저장
		return "registerInfo";
	}

	private boolean isValid(User user) {
		//return false;
		return true;
	}

}
/*
URL인코딩- 퍼센트 인코딩

URL에 포함된 non-ASCII문자를 문자 코드(16진수) 문자열로 변환

	URLEncoder.encode()
김진수  ------------------> "%ea%b9%80(김)%ec%a7%84(진)%ec%88%98(수)"
        <-----------------
	URLDecoer.decode()

URL 인코딩/디코딩 : 문자코드(숫자) <-> 문자열
BASE64 : 6bit씩 끊어서 A-Z , a-z 문자들로 변환 , 바이너리 데이터 -> Text로 변환

URLDecoer.decode()
request.setCharacterEncoding("utf-8"); // 해당 문장 필수
이유 : getParameter를 했을 때 제대로 데이터를 디코딩해서 읽을 수 있음

request.setCharacterEncoding("utf-8"); 
// 해당 문장을 매번 적어주기 불편 -> web.xml에 한글 변환 필터를 넣어줌
 */

/*
[redirect]
300번대 : redirect (다른 URL로 재요청) / 응답해더만 있고 바디는 없음

1. 요청(request) (/ch2/write.jsp) --> 수동

2. 응답 write.jsp
(EX)
HTTP/1.1.302
Location : /ch2/login.jsp
Content-Language : ko-KR
Content-Length : 0 Date Sat,13 Nov 2021 05:33:12 GMT

3. 요청 request (/ch2/login.jsp) --> 자동, get으로 요청
브라우저가 자동으로 해더를 읽어서 /ch2/login.jsp으로 요청

1과 3의 객체(request)는 다름 / 1번이 GET, POST 상관없이 redirect 로 요청한 것은 GET

4. 응답 login.jsp 

예시)
고객이 서비스 센터에 전화했는데 자신 부서 담당이 아니니 타 부서 연락처로 연락달라는 것
고객은 2번 말해야 함.

[forward] - MVC 패턴
1. 요청(request) (/ch2/write.jsp) 

2. 전달 (request뿐만 아니라 response도 같이 전달해줌) foward 
write.jsp가 자기가 처리할 것이 아닌 경우나 일부만 처리하고 login.jsp에 foward(전달)
write.jsp에서 어떤 값을 저장할 수 있음 그것을 login.jsp에 foward(전달)
write.jsp는 Controller 역할을 함

여기서 request 객체가 Model역할을 함

3. 응답 
그럼 login.jsp에서 요청을 받아서 처리하고 응답
login.jsp 에서 결과를 보여주는 View 역할

예시
고객이 분실신고로 한도조회하는 부서에 전화, 한도 조회하는 부서에서 분실신고 부서로 조용히 연결해주는 것
그래서 분실신고 부서에서 처리해준 것 / 고객은 똑같은 말을 할 다시 필요가 없음

스프링이 forward을 통해 MVC패턴 구현

Controller가 처리를 하고 그 결과를 Model에 담아서 View에 전달하면 
View에서 Model을 담아서 최종결과를 응답

*/

/*
[Converter와 ConversionService]
Converter - 단방향 타입 변환(타입A -> 타입B), stateless(iv사용X).

PropertyEditor(양방향)의 단점을 개선(stateful(iv사용) - stateless) 
// stateful : 인스턴스 변수(iv)를 사용하는 것
// iv를 사용한다는 것은 싱글톤을 사용할 수 없다.

왜?
인스턴스 변수 (instance variable) (= 객체 변수)
클래스 내에 선언된 변수
객체 생성 시마다 매번 새로운 변수가 생성됨
클래스 변수와 달리 공유되지 않음

싱글턴
전역변수처럼 사용될 수 있는데, 
싱글턴 패턴을 사용하면 사용할 때 자원을 할당하고, 자신이 원할 때 자원을 해제할 수 있다. 즉, 전역 변수의 장점을 가지면서 전역 변수의 단점을 보완할 수 있다.
최초 한번만 메모리를 할당하기 위해 static으로 선언하고
생성자를 private으로 선언하여 재생성이 불가능하게 만든 후 getInstance()로 받아 쓸 수 있다.

클래스 변수(Static 멤버)
클래스 내에 Static 키워드로 선언된 변수
처음 JVM이 실행되어 클래스가 메모리에 올라갈 때 ~ 프로그램이 종료될 때까지 유지
클래스가 여러 번 생성되어도 Static 변수는 처음 딱 한 번만 생성됨
동일한 클래스의 모든 객체들에 의해서 공유됨


[ConversionService()]
타입 변환 서비스를 제공, 여러 Converter를 등록 가능
WebDataBinder에 DefaultFormattingConversionService이 기본 등록
모든 컨트롤러 내에서의 변환 - ConfigurableWebBindingInitializer를 설정해서 사용
특정 컨트롤러 내에서의 변환 - 컨트롤러에 @InitBinder가 붙은 메서드를 작성

[Formatter]
양방향 타입 변환(String -> 타입, 타입 -> String)
바인딩할 필드에 적용 - @NumberFormat, @DateTimeFormat

PropertyEditor : 양방향
Converter : 단방향
Formatter : 양방향, @NumberFormat, @DateTimeFormat

우선순위
1. 커스텀 Property Editor
2. ConversionService
3. 디폴트 Property Editor
항상 가까운 쪽이 우선하여 데이터를 처리한다.
*/

