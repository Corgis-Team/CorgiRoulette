<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>

<html>
<head>
    <title>Add user</title>
</head>
<body>
<form action="save" method="post">
    <label for="name">Name</label>
    <input id="name" name="name" type="text">
    <br><br>
    <label for="surname">Surname</label>
    <input id="surname" name="surname" type="text">
    <br><br>
    <label for="team_id">Team id</label>
    <input id="team_id" name="team_id" type="number">
    <br><br>

    <button type="submit">Add user</button>
    <a href="<c:url value="/users"/> ">
        <input type="button" value="Cancel"/>
    </a>
    <p><%= errorMessage%></p>
</form>
</body>
</html>
