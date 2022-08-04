<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<title>JSTL</title>
</head>
<body>
<c:set var="to"   value="10"/> 
<!-- 변수 to 라는 값을 10으로 셋팅 -->
<!-- EL은 lv 사용 못하므료 저장소에 저장, 10뒤에 scope ="page" 생략된 상태 -->
<c:set var="arr"  value="10,20,30,40,50,60,70"/> 
<c:forEach var="i" begin="1" end="${to}">
	${i} <!-- 1부터 10까지 출력 -->
</c:forEach>
<br>
<c:if test="${not empty arr}">
	<c:forEach var="elem" items="${arr}" varStatus="status">
		${status.count}. arr[${status.index}]=${elem}<BR>
	</c:forEach>
</c:if>	
<c:if test="${param.msg != null}">
	msg=${param.msg} 
	msg=<c:out value="${param.msg}"/> 
	<!-- msg=<p>asdf</p>로 입력을 하면 태그로 해석하지 않음 
		 그래서 script 공격에 방어할 수 있다. -->
</c:if>
<br>
<c:if test="${param.msg == null}">메시지가 없습니다.<br></c:if>
<c:set var="age" value="${param.age}"/>
<c:choose>
	<c:when test="${age >= 19}">성인입니다.</c:when>
	<c:when test="${0 <= age && age < 19}">성인이 아닙니다.</c:when>
	<c:otherwise>값이 유효하지 않습니다.</c:otherwise>
</c:choose>
<br>
<c:set var="now" value="<%=new java.util.Date() %>"/>
Server time is <fmt:formatDate value="${now}" type="both" pattern="yyyy/MM/dd HH:mm:ss"/>	
</body>
</html>

<!-- 
1 2 3 4 5 6 7 8 9 10
1. arr[0]=10
2. arr[1]=20
3. arr[2]=30
4. arr[3]=40
5. arr[4]=50
6. arr[5]=60
7. arr[6]=70
메시지가 없습니다. : 이 경우는 http://localhost/ch2/jstl.jsp
msg=asdf msg=asdf : 이 경우는 http://localhost/ch2/jstl.jsp?msg=asdf

값이 유효하지 않습니다. : http://localhost/ch2/jstl.jsp?msg=asdf
성인이 아닙니다. : 이 경우는 http://localhost/ch2/jstl.jsp?msg=asdf&age=18
Server time is 2022/07/12 21:59:46
 -->
	
	