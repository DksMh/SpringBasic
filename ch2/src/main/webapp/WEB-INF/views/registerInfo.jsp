<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- param => user -->
<h1>id=${user.id}</h1>
<h1>pwd=${user.pwd}</h1>
<h1>name=${user.name}</h1>
<h1>email=${user.email}</h1>
<h1>birth=${user.birth}</h1>
<h1>hobby=${user.hobby}</h1>
<h1>sns=${user.sns}</h1>
<!-- <h1>sns=${paramValues.sns}</h1>
<h1>sns=${paramValues.sns[0]}</h1>
<h1>sns=${paramValues.sns[1]}</h1>
<h1>sns=${paramValues.sns[2]}</h1>
 -->
<!-- param.sns하면 sns하나만 나온다 
값이 여러개일 때는 String[] snsArr = request.getParameterValues로 가져와야함
EL인 경우는 ${paramValues.sns}가 있다. paramValues.sns 은 배열이라
paramValues.sns[0] , paramValues.sns[1] 이렇게 붙여줘야한다.
-->
</body>
</html>