<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="user" class="com.andersen.corgisteam.corgiroulette.entity.User" scope="request"/>
<jsp:useBean id="team_name" class="java.lang.String" scope="request"/>

<html>
<head>
    <title>User details</title>
</head>
<body>
<p>Id: ${user.id}</p>
<p>Name: ${user.name}</p>
<p>Surname: ${user.surname}</p>
<c:if test="${user.team != null}">
    <p>Team Name: ${user.team.name}</p>
</c:if>

<p><a href="${pageContext.request.contextPath}/roulette/users/edit?id=${user.id}">Edit</a></p>
<p><a href="${pageContext.request.contextPath}/roulette/users/delete?id=${user.id}">Delete</a></p>
<p><a href="${pageContext.request.contextPath}/roulette/users/search">Use search</a></p>
<p><a href="${pageContext.request.contextPath}/roulette/users">Show all users</a></p>
</body>
</html>