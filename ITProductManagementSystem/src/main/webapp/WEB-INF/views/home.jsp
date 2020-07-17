<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%-- <%@ page session="false" %> --%>
<html>
<head>
	<title>Home Page</title>
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

<h1>Welcome to IT Product Management System</h1>

<h3>LOGIN: </h3> 
<form:form action="${pageContext.request.contextPath}/login" method="get"> 
	<input class ="button" type="submit" value="Login" /><br/>
</form:form>

<h3>REGISTER:</h3>
<ul >
<li><a href="${pageContext.request.contextPath}/registerstudent">STUDENT</a></li><br/>
<li><a href="${pageContext.request.contextPath}/registermanager">MANAGER</a></li>
</ul>
<br/>
<br/>

</body>
</html>
