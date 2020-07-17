<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Request Product</title>
<style>
body {
	background-color: #FFF5EE;
	text-align: center;
}

h1 {
	color: black;
}

h4 {
	color: red;
}

table {
	border-collapse: collapse;
	width: 98%;
}

table, th, td {
	text-align: center;
	border: 1px solid black;
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
	<a href="${pageContext.request.contextPath}/studenthome">Back</a>
	<br />
	<h1>IT Product Management System</h1>
	<h3>Logged in as: ${sessionScope.role} : ${sessionScope.user}</h3>
	<h2>Available Products</h2>
	<table>
		<tr>
			<th>Product Id</th>
			<th>Product Name</th>
		</tr>

		<c:if test="${!requestScope.productList.isEmpty()}">
			<c:forEach var="product" items="${requestScope.productListStudent}">
				<c:if test="${product.rentedquantity < product.quantity}">
					<tr>
						<td><c:out value="${product.id}" /></td>
						<td><c:out value="${product.productName}" /></td>
					</tr>
				</c:if>
			</c:forEach>
		</c:if>
	</table>
	<h2>Product request form</h2>
	
	<h4><c:out value="${error}"/></h4>
	<form action="raiserequest" method="POST">

		<label>Enter Product Id*:</label>
		<input type="number" name="id" required />
		<br>
		<br>

		<input class="button" type="submit" value="Request"pattern="^[0-9]+" />
		<input class="button" type="reset">
		<br>
		<br>

	</form>
</body>
</html>