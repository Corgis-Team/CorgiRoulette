<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="teams" type="java.util.List<com.andersen.corgisteam.corgiroulette.entity.Team>" scope="request"/>
<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>

<html>
<head>
    <title>Add user</title>
</head>
<body>
<form action="create" method="post">
    <label for="name">Name</label>
    <input id="name" name="name" type="text">
    <br><br>

    <label for="surname">Surname</label>
    <input id="surname" name="surname" type="text">
    <br><br>

    <label for="team_id">Team</label>
    <select id="team_id" name="team_id">
        <option value="0"></option>
        <c:forEach var="team" items="${teams}">
            <option value="${team.id}">${team.name}</option>
        </c:forEach>
    </select>
    <br><br>

    <button type="submit">Add user</button>
    <a href="<c:url value="/roulette/users"/> ">
        <input type="button" value="Cancel"/>
    </a>
    <p><%= errorMessage%></p>
</form>
</body>
</html>
