<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Student Home Page</title>
<style>
body {background-color: #FFF5EE;
		text-align: center;
		}
h1   {color: black ;}

ul{
list-style: none;
padding: 0;
  margin: 0;
}

</style>
</head>
<body>
<h1>IT Product Management System  - Student HomePage</h1>
<%-- <h2>Welcome ${requestScope.user}</h1> --%>
<h2>Welcome ${sessionScope.role} : ${sessionScope.user}</h2>
<ul style="list-style-type:none";>
  <li><a href="${pageContext.request.contextPath}/login/studenthome/viewproduct">View Products</a></li>
  <li><a href="${pageContext.request.contextPath}/login/studenthome/searchproduct">Search Product</a></li>
  <li><a href="${pageContext.request.contextPath}/login/studenthome/requestproduct">Request Product</a></li>
  <li><a href="${pageContext.request.contextPath}/login/studenthome/trackrequest">Track Request</a></li>
  <li><a href="${pageContext.request.contextPath}/login/studenthome/returnproduct">Return Product</a></li>
  <li><a href="${pageContext.request.contextPath}/home">Logout</a></li>
</ul>

</body>
</html>