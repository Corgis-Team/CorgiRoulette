<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="user" type="com.andersen.corgisteam.corgiroulette.entity.User" scope="request"/>
<jsp:useBean id="teams" type="java.util.List<com.andersen.corgisteam.corgiroulette.entity.Team>" scope="request"/>
<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>

<html>
<head>
    <title>Edit user</title>
</head>
<body>
<form action="update" method="post">
    <input name="id" value="${user.id}" type="hidden">
    <label for="name">Name</label>
    <input id="name" name="name" value="${user.name}" type="text">
    <br><br>

    <label for="surname">Surname</label>
    <input id="surname" name="surname" value="${user.surname}" type="text">
    <br><br>

    <label for="team_id">Team</label>
    <select id="team_id" name="team_id">
        <option value="0"></option>
        <c:forEach var="team" items="${teams}">
            <option value="${team.id}"
                    <c:if test="${team.id == user.team.id}">selected</c:if>>
                    ${team.name}
            </option>
        </c:forEach>
    </select>
    <br><br>

    <button type="submit">Edit user</button>
    <a href="<c:url value="/roulette/users"/> ">
        <input type="button" value="Cancel"/>
    </a>
    <p><%= errorMessage%></p>
</form>
</body>
</html>
