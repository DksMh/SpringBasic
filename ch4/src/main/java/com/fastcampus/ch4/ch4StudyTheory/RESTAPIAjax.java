package com.fastcampus.ch4.ch4StudyTheory;

public class RESTAPIAjax {
/*
    [ JSON이란? ]
    자바스크립트 객체 표기법(JavaScript Object Notation(JSON))
    XML --> 복잡, data보다 Tag가 더 많음.
    JSON --> 간단

    { 속성명1:속성값1, 속성명2:속성값2, ...} // JSON 표기법

    [{속성명:속성값, ...}, {속성명:속성값, ...}, ...] //객체 배열
    {키1:{속성명:속성값, ...}, 키2:{속성명:속성값, ...}, ...} //MAP


    [ stringify(), parse() ]
    JS객체를 서버로 전송하려면, 직렬화(data를 문자열로 변환)가 필요
    서버가 보낸 데이터(JSON문자열)를 JS객체로 변환할 때, 역직렬화가 필요

    JSON.stringify() - 객체를 JSON문자열로 변환(직렬화, JS객체 -> 문자열)
    JSON.parse() - JSON문자열을 객체로 변환(역직렬화, 문자열 -> JS객체)

                                    JSON.stringify()
                                  -------------------->
   {name:"John", age:30} //JS객체                        '{"name":"John", "age":30}' //string
                                  <--------------------
                                       JSON.parse()

*/
/*

    [ Ajax란? ]
    Asynchronous JavaScript And XML - 요즘은 JSON을 주로 사용
    비동기 통신으로 데이터를 주고받기 위한 기술
    웹페이지 전체(data+UI)가 아닌 일부(data)만 업데이트 가능
    ex)게시판의 바뀐 댓글만 가져오도록
    
    // 동기  : 요청 ---> 처리 ----> 응답 // 응답이 올 동안 대기
    // 비동기 : 요청후 응답까지 기다리지 않고 요청할 수 있음 // 대기 X, 처리 끝난 거 알 수 있는 것 : 콜백 함수

    [ jQuery를 이용한 Ajax ]
    $(document).ready(function(){
        let person = {name:"abc", age:10};
        let person2 = {};

        ${"#sendBtn").click(function() {
            $.ajax({
                type:'POST', //요청 메서드
                url: '/ch4/send', //요청 URI
                headers : {"content-type":"application/json"}, //요청 헤더
                dataType : 'text', //전송할 데이터 타입
                data : JSON.stringify(person), //서버로 전송할 데이터 stringify()로 직렬화 필요
                // 콜백함수
                success : function(result) {person2 = JSON.parse(result); //서버로부터 응답이 도착하면 호출될 함수
                                            alert(result); }              // result는 서버가 전송한 데이터
                error : function() {alert("error")} //에러가 발생했을 때 호출될 함수
            }); //$.ajax()
 
            alert("the request is sent")
        });
    });

    [ Ajax요청과 응답 과정 ]
    //jackson-databind
    //메서드에 @ResponseBody
    @PostMapping("/send")
    @ResponseBody
    public Person test(@RequestBody Person p) {
        System.out.println("p="+p);
        p.setName("ABC");
        p.setAge(p.getAge()+10);

        return p;
    }

    @RestController
    @ResponseBody대신, 클래스에 @RestController 사용 가능

    [이전]
    @Controller
    public class SimpleRestController {
        @PostMapping("/send")
        @ResponseBody
        public Person test(@RequestBody Person p) {
            System.out.println("p = " + p);
            p.setName("ABC");
            p.setAge(p.getAge() + 10);

            return p;
        }
    }
    ↓
    [이후]
    @RestController
    public class SimpleRestController {
        @PostMapping("/send")
        public Person test(@RequestBody Person p) {
            System.out.println("p = " + p);
            p.setName("ABC");
            p.setAge(p.getAge() + 10);

            return p; // 객체 반환
        }
    }
*/
/*
    [ REST란? ]
    Representational State Transfer
    웹서비스 디자인 아키텍쳐 접근 방식
    프로토콜에 독립적이며, 주로 HTTP를 사용해서 구현
    ★ 리소스 중심의 API디자인 - HTTP메서드로 수행할 작업을 정의

    리소스 - 명사
    POST(Write 쓰기), GET(Read 읽기), PUT(파일 업로드), DELETE(파일 삭제) - 동사
    PATCH(수정)

    리소스                 POST            GET                PUT                       DELETE
    /customers            새 고객 만들기    모든 고객 검색       고객 대량 업데이트          모든 고객 제거

    /customers/1          Error           고객 1에 대한       고객 1이 있는 경우          고객1 제거
                                          세부 정보검색       고객1의 세부 정보 업데이트

    /customers/1/orders   고객1에 대한      고객 1에 대한      고객 1의 주문 대량 업데이트   고객1의 모든 주문 제거
                          주문 만들기       모든 주문 검색 

    [ REST API란? ]
    REST규약을 준수하는 API

    [ RESTful API 설계 ]

    //이전 방법
    작업  URI                     HTTP메서드     설명
    읽기  /comment/read?cno=번호   GET          지정된 번호의 댓글을 보여준다
    쓰기  /comment/write          POST         작성한 게시물을 저장한다
    삭제  /comment/remove         POST         댓글을 삭제한다
    수정  /comment/modify         POST         수정한 게시물을 저장한다

    ↓ // URI에 있는 동사가 사라지고 URI에는 명사만 남고 HTTP메서드가 동사를 담당

    //Restful API
    작업  URI                     HTTP메서드     설명
    읽기  /comments               GET           모든 댓글을 보여준다
    읽기  /comments/{cno}         GET           지정된 번호의 댓글을 보여준다
    쓰기  /comments               POST          새로운 댓글을 저장한다
    삭제  /comments/{cno}         DELETE        지정된 번호의 댓글을 삭제한다
    수정  /comments/{cno}         PUT/PATCH     수정된 댓글을 저장한다



 */
}
