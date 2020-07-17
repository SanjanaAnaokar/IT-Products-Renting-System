<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Product</title>
<style>
body {background-color: #FFF5EE;
		text-align: center;
		}
h1   {color: black ;}
h4   {color: red ;}
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
<a href="${pageContext.request.contextPath}/managerhome">Back</a><br/>
<h1> IT Product Management System</h1>
<h3>Logged in as: ${sessionScope.role} : ${sessionScope.user}</h3>
<h2> Update Product Quantity Form</h2>
<h4><c:out value="${error}"/></h4>
<%-- <form:form  method="POST" action="updateproduct" commandName="product">

<label>Product Id*:</label>
<form:input type="number" path="id" /><form:errors path="id"/>
<br><br>

<label>How many more products you want to add*:</label>
<form:input type="number" path="quantity"/><form:errors path="quantity"/>
<br><br>

<input class="button" type="submit" value="Update" />
<input class="button" type="Reset">
<br><br>

</form:form> --%>

<form action="updateproduct" method="POST">
		<label>Enter Product ID*:</label> <input type="number" name="id"required /> <br> <br> 
		<label>How many more products you want to add*:</label> <input type="number" name="quantity" min ="1" required /><br> <br>
		<input class="button" type="submit" value="Update" />
		<input class="button" type="reset">
</form>

</body>
</html>