<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manager Registration Page</title>
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
</style>
</head>

<body>
<a href="${pageContext.request.contextPath}/">Back</a><br/>

<h1> IT Product Management System</h1>

<h2> Manager Registration Form</h2>

<form:form  method="POST" action="registermanager" commandName="manager">
			<label>NUID*:</label>
            <form:input type="text" path="nuId"/><form:errors path="nuId"/>
            <br><br>
            
            <label>First Name*:</label>
            <form:input type="text" path="firstName" /><form:errors path="firstName"/>
            
            <br><br>


            <label>Last Name*:</label>
            <form:input type="text" path="lastName"/><form:errors path="lastName"/>
            <br><br>


            <label>Email Id*:</label>
            <form:input type="text" path="emailId" /><form:errors path="emailId"/>
            <br><br>


            <label>Password*:</label>
            <form:input type="password" name="password" path="password"/><form:errors path="password"/>
            <br><br>

            <input class="button" type="submit" value="Register" />
            <input class="button" type="reset">
            <br><br>
            
</form:form>
</body>
</html>