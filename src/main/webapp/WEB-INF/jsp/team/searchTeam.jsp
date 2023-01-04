<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>

<html>
<head>
    <title>Search team</title>
</head>
<body>
<form action="search" method="get">
    <label for="name">Name</label>
    <input id="name" name="name" type="text">
    <br><br>
    <button type="submit">Search teams</button>
    <a href="<c:url value="/teams"/> ">
      <input type="button" value="Search all"/>
    </a>
    <p><%= errorMessage%></p>
</form>
</body>
</html>
