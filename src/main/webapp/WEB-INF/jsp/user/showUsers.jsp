<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>

<html>
<head>
    <title>Show all users</title>
</head>
<body>
<div>
    <h2>Users: </h2>

    <c:choose>
        <c:when test="${users != null && !users.isEmpty()}">
            <table>
                <thead>
                <tr>
                    <th><b>Name</b></th>
                    <th><b>Surname</b></th>
                    <th><b>Team</b></th>
                    <th><b>Show</b></th>
                    <th><b>Detele</b></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td><c:out value="${user.name}"/></td>
                        <td><c:out value="${user.surname}"/></td>
                        <td><c:out value="${user.team.name}"/></td>
                        <td><a href="${pageContext.request.contextPath}/users/details?id=${user.id}">Show</a></td>
                        <td><a href="${pageContext.request.contextPath}/users/delete?id=${user.id}">Delete</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <br>
        </c:when>
        <c:otherwise>
            <H3>There are no users.</H3>
            <br>
        </c:otherwise>
    </c:choose>
    <br>

    <div>
        <a href="<c:url value="/users/new"/> ">
            <button>Add new user</button>
        </a>
    </div>

    <p><%= errorMessage%></p>
</div>

</body>
</html>