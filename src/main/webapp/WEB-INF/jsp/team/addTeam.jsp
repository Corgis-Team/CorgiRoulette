<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>

<html>
<head>
  <title>Add team</title>
</head>
<body>
<form action="create" method="post">
  <label for="name">Name</label>
  <input id="name" name="name" type="text">
  <br><br>
  <button type="submit">Add team</button>
  <a href="<c:url value="/roulette/teams"/> ">
    <input type="button" value="Cancel"/>
  </a>
  <p><%= errorMessage%></p>
</form>
</body>
</html>
