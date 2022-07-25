<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>JSP</title>
</head>
<body>
<% String greeting = (String) request.getAttribute("greeting");
System.out.println(greeting);%>

</body>
</html>