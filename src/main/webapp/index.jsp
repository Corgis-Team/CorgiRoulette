<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Corgi Roulette</title>
</head>
<body>
<h1>Corgi Roulette</h1>
<ul>
    <li><a href="<c:url value="/roulette/users"/>">Users</a> </li>
    <li><a href="<c:url value="/roulette/teams"/>">Teams</a> </li>
    <li><a href="<c:url value="/roulette/history"/>">History</a> </li>
</ul>
</body>
</html>