<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>

<html>
<head>
    <title>Team search results</title>
</head>
<body>
<h2>Teams: </h2>
<table>
    <thead>
    <tr>
        <th><b>Name</b></th>
        <th><b>Details</b></th>
        <th><b>Edit</b></th>
        <th><b>Delete</b></th>
    </tr>
    </thead>

    <tbody>
    <c:forEach items="${teams}" var="team">
        <tr>
            <td><c:out value="${team.name}"/></td>
            <td><a href="${pageContext.request.contextPath}/teams/details?id=${user.id}">Details</a></td>
            <td><a href="${pageContext.request.contextPath}/teams/edit?id=${user.id}">Edit</a></td>
            <td><a href="${pageContext.request.contextPath}/teams/delete?id=${user.id}">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br>
<br>

<div>
    <a href="<c:url value="/teams/new"/> ">
        <button>Add new team</button>
    </a>
</div>

<p><%= errorMessage%></p>
</body>
</html>
