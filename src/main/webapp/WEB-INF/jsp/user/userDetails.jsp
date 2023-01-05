<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="user" class="com.andersen.corgisteam.corgiroulette.dto.UserDto" scope="request"/>
<jsp:useBean id="team_name" class="java.lang.String" scope="request"/>

<html>
<head>
  <title>Team details</title>
</head>
<body>
<p>Id: ${user.id}</p>
<p>Name: ${user.name}</p>
<p>Surname: ${user.surname}</p>
<p>Team Name: ${team_name}</p>

<p><a href="${pageContext.request.contextPath}/roulette/users/edit?id=${user.id}">Edit</a></p>
<p><a href="${pageContext.request.contextPath}/roulette/users/delete?id=${user.id}">Delete</a></p>
<p><a href="${pageContext.request.contextPath}/roulette/users/search">Use search</a></p>
<p><a href="${pageContext.request.contextPath}/roulette/users">Show all users</a></p>
</body>
</html>