<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>

<html>
<head>
  <title>Search user</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/roulette/users/search/results" method="get">
  <label for="full_name">Full name</label>
  <input id="full_name" name="full_name" type="text">
  <br><br>
  <button type="submit">Search users</button>
  <a href="<c:url value="/roulette/users"/> ">
    <input type="button" value="Search all"/>
  </a>
  <p><%= errorMessage%></p>
</form>
</body>
</html>
