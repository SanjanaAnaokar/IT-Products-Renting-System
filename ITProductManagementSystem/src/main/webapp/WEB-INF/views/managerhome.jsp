<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Student Home Page</title>
<style>
body {
	background-color: #FFF5EE;
	text-align: center;
}

h1 {
	color: black;
}

ul {
	list-style: none;
	padding: 0;
	margin: 0;
}
</style>
</head>
<body>
<%    //This part is to check if user is authenticated even if the browsers back button or refresh is clicked
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma", "no-cache");
response.setHeader("Expires", "0");
if (session.getAttribute("role") == null) {
    response.sendRedirect("/itproductmanagement/login?");
} else {
    System.out.println("User is authenticated");
}
%>
	<h1>IT Product Management System - Manager HomePage</h1>
	<%-- <h2>Welcome ${requestScope.user}</h1> --%>
	<h2>Welcome ${sessionScope.role} : ${sessionScope.user}</h2>
	<ul style="list-style-type: none";>
		<li><a
			href="${pageContext.request.contextPath}/login/managerhome/addproduct">Add
				new products</a></li>
		<li><a
			href="${pageContext.request.contextPath}/login/managerhome/updateproduct">Update
				products</a></li>
		<li><a
			href="${pageContext.request.contextPath}/login/managerhome/viewproduct">View
				Products</a></li>
		<li><a
			href="${pageContext.request.contextPath}/login/managerhome/deleteproduct">Delete
				Products</a></li>
		<li><a
			href="${pageContext.request.contextPath}/login/managerhome/managerequest">Manage
				student requests</a></li>
		<li><a
			href="${pageContext.request.contextPath}/login/managerhome/managereturns">Manage
				student returns</a></li>
		<li><a
			href="${pageContext.request.contextPath}/login/managerhome/viewallrequests">View
				all requests</a></li>
		<li><a href="${pageContext.request.contextPath}/home">Logout</a></li>
	</ul>

</body>
</html>