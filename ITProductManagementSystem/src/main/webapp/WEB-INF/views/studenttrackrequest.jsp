<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Track Product Request</title>
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
	<%-- <c:out value="Sessionscope user is${sessionScope.user}"/> --%>
	<h2>Request History</h2>
	<table>
		<tr>
			<th>Request Id</th>
			<th>Product ID</th>
			<th>Product Name</th>
			<th>Student ID</th>
			<th>Student Name</th>
			<th>Status</th>
			<th>Comments</th>
			<th>Request Date</th>
			<th>Processed Date</th>
			<th>Expected Return Date</th>
			<th>Actual Return Date</th>
			<th>Confirm Return Date</th>
			<th>Fine</th>
		</tr>
		<c:if test="${!productrequestList.isEmpty()}">
			<c:forEach var="productRequest" items="${productrequestList}">
			<c:if test="${productRequest.student.emailId.equals(sessionScope.user)}">
				<tr>
					<td>${productRequest.id}</td>
					<td><c:out value="${productRequest.product.id}" /></td>
					<td><c:out value="${productRequest.product.productName}" /></td>
					<td><c:out value="${productRequest.student.id}" /></td>
					<td><c:out value="${productRequest.student.emailId}" /></td>
					<td>${productRequest.status}</td>
					<td>${productRequest.comments}</td>
					<td>${productRequest.requestdate}</td>
					<td>${productRequest.approvaldate}</td>
					<td>${productRequest.expectedReturnDate}</td>
					<td>${productRequest.actualReturnDate}</td>
					<td>${productRequest.confirmReturnDate}</td>
					<td>${productRequest.fine}</td>
				</tr>
				</c:if>
			</c:forEach>
		</c:if>
	</table>
</body>
</html>