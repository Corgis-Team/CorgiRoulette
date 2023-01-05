<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>
<jsp:useBean id="team" class="com.andersen.corgisteam.corgiroulette.entity.Team" scope="request"/>

<html>
<head>
  <title>Add team</title>
</head>
<body>
<form action="update" method="post">
  <input name="id" value="${team.id}" type="hidden">
  <br><br>
  <label for="name">Name</label>
  <input id="name" name="name" type="text" value="${team.name}">
  <br><br>
  <button type="submit">Save changes</button>
  <a href="<c:url value="/roulette/teams/details?id=${team.id}"/>">
    <input type="button" value="Cancel"/>
  </a>
  <p><%= errorMessage%></p>
</form>
</body>
</html>
