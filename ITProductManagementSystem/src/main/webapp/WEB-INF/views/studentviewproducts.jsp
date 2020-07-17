<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View Products</title>
<style>
body {
	background-color: #FFF5EE;
	text-align: center;
}

h1 {
	color: black;
}

table {
	border-collapse: collapse;
	width: 98%;
}

table, th, td {
	text-align: center;
	border: 1px solid black;
}
</style>
</head>
<body>
	<a href="${pageContext.request.contextPath}/studenthome">Back</a>
	<br />
	<h1>IT Product Management System</h1>
	<h3>Logged in as: ${sessionScope.role} : ${sessionScope.user}</h3>
	<h2>View Products</h2>
	<table>
		<tr>
			<th>Product Id</th>
			<th>Product Name</th>
		</tr>

		<c:if test="${!requestScope.productList.isEmpty()}">
			<c:forEach var="product" items="${requestScope.productListStudent}">
				<tr>
					<td><c:out value="${product.id}" /></td>
					<td><c:out value="${product.productName}" /></td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
</body>
</html>