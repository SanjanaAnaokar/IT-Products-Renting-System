<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>
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
.button {
  background-color: #696969;
  border: none;
  color: white;
  padding: 8px 8px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 15px;
  margin: 4px 2px;
  cursor: pointer;
  border-radius: 8px;
}
</style>
</head>
<body>
<a href="${pageContext.request.contextPath}/">Back</a><br/>
<h1> Login Page</h1>

<form:form action="${pageContext.request.contextPath}/login" modelAttribute="loginUser"> 
<h4 style="color:red"><c:out value="${error}"></c:out></h4><br/>
    <label for="role">Select role*:</label>
	<select name="role">
	<option value="Student">Student</option>
	<option value="Manager">Manager</option>
	</select>
	<br/>
	<br/>
	<label for="emailid">Email Id*:</label>
            <input type="text" name="emailid" id="emailid" required="required" />

            <br><br>
	<label for="password">Password*:</label>
            <input type="password" name="password" id="password" required="required"/>
            <br><br>
	<input class="button" type="submit" id ="submit" value="Login">
	<br/>
</form:form>

</body>
</html>