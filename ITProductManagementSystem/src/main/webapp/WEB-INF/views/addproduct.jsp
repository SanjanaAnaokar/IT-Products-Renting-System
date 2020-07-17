<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add products</title>
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
<a href="${pageContext.request.contextPath}/managerhome">Back</a><br/>
<h1> IT Product Management System</h1>
<h3>Logged in as: ${sessionScope.role} : ${sessionScope.user}</h3>
<h2> Add Products Form</h2>
<form:form  method="POST" action="addproduct" commandName="product">

<label>Product Name*:</label>
<form:input type="text" path="productName" /><form:errors path="productName"/>
<br><br>

<label>Quantity*:</label>
<form:input type="number" path="quantity"/><form:errors path="quantity"/>
<br><br>

<input class="button" type="submit" value="Add" />
<input class="button" type="Reset">
<br><br>

</form:form>
</body>
</html>