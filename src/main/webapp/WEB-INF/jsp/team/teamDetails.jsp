<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="team" class="com.andersen.corgisteam.corgiroulette.entity.Team" scope="request"/>

<html>
<head>
    <title>Team details</title>
</head>
<body>
    <p>Id: ${team.id}</p>
    <p>Name: ${team.name}</p>

    <p><a href="${pageContext.request.contextPath}/teams/edit?id=${team.id}">Edit</a></p>
    <p><a href="${pageContext.request.contextPath}/teams/delete?id=${team.id}">Delete</a></p>
    <p><a href="${pageContext.request.contextPath}/teams/search">Use search</a></p>
    <p><a href="${pageContext.request.contextPath}/teams">Show all teams</a></p>
</body>
</html>
