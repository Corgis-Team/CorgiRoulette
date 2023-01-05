<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>

<html>
<head>
  <title>Users search results</title>
</head>
<body>
<h2>Users: </h2>
<table>
  <thead>
  <tr>
    <th><b>Name</b></th>
    <th><b>Surname</b></th>
    <th><b>Details</b></th>
    <th><b>Edit</b></th>
    <th><b>Delete</b></th>
  </tr>
  </thead>

  <tbody>
  <c:forEach items="${users}" var="user">
    <tr>
      <td><c:out value="${user.name}"/></td>
      <td><c:out value="${user.surname}"/></td>
      <td><a href="${pageContext.request.contextPath}/roulette/users/details?id=${user.id}">Details</a></td>
      <td><a href="${pageContext.request.contextPath}/roulette/users/edit?id=${user.id}">Edit</a></td>
      <td><a href="${pageContext.request.contextPath}/roulette/users/delete?id=${user.id}">Delete</a></td>
    </tr>
  </c:forEach>
  </tbody>
</table>
<br>
<br>

<div>
  <a href="<c:url value="/roulette/users/new"/> ">
    <button>Add new user</button>
  </a>
</div>

<p><%= errorMessage%></p>
</body>
</html>
