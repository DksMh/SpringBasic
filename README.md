# [SpringBasic]

- Spring, IntelliJ, MySQL, MyBatis, Tomcat을 사용
- Spring MVC으로 회원가입, 로그인, 게시판, 댓글을 만드는 연습을 하였습니다.
#### 출처 : [스프링의 정석 : 남궁성과 끝까지 간다]


## [사용된 SQL ERD Diagram]

![springbasic](https://user-images.githubusercontent.com/96456838/182907928-f6cc990e-b296-4c15-9b51-8f6fd3ee15c4.png)


- user_info(id)와 board(writer)가 연결
- user_info(id)와 comment(commenter)가 연결
- board(bno)와 comment(bno)가 연결

### [로그인 화면]
![login](https://user-images.githubusercontent.com/96456838/182909500-c8868771-ce76-4455-98cc-bc9c3387a1b1.png)


### [회원가입 화면]
![register](https://user-images.githubusercontent.com/96456838/182910037-f3ea7670-67d8-41db-a386-fb3aa6c7622f.png)

### [게시판 화면]
#### [게시판 화면 - 기본]
![board](https://user-images.githubusercontent.com/96456838/182910920-65321feb-3cdd-4a62-b8c5-4667b0a78c61.png)

#### [게시판 화면 - 수정]
![board_mod](https://user-images.githubusercontent.com/96456838/182910957-a290edf1-566d-4a37-a35a-0d55e1136205.png)

#### [게시판 화면 - 삭제]
![board_del](https://user-images.githubusercontent.com/96456838/182910982-dfb32b1f-9aae-4fd5-9229-12bbca12cb27.png)

- CRUD 기능을 구현했습니다.

## [댓글 화면]
![image](https://user-images.githubusercontent.com/96456838/182912447-2c828fc0-5406-4709-ab1b-0819d9c0ad07.png)

- 꾸미지 않고 crud기능만 구현한 상태입니다.
