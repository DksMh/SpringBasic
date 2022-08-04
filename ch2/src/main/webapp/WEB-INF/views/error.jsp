<%@ page contentType="text/html;charset=utf-8" isErrorPage="true"%>
<%-- isErrorPage="true" : 에러일 때 사용하는 페이지 --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>error.jsp</title>
</head>
<body>
<h1>예외가 발생했습니다.</h1>
<!-- 
isErrorPage="true" 을 사용하면 
모델을 통해서 예외객체를 전달을 하지 않아도 예외객체 사용가능
발생한 예외 : ${ex}<br> -->
발생한 예외 : ${pageContext.exception}<br>
예외 메시지 : ${pageContext.exception.message}<br>
<ol>
<c:forEach items="${pageContext.exception.stackTrace}" var="i">
	<li>${i.toString()}</li>
</c:forEach>
</ol>
</body>
</html>

