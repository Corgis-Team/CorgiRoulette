<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="team" class="com.andersen.corgisteam.corgiroulette.entity.Team" scope="request"/>

<html>
<head>
    <title>Team details</title>
</head>
<body>
    <p>Id: ${team.id}</p>
    <p>Name: ${team.name}</p>
    <h2>Team members:</h2>
    <c:choose>
        <c:when test="${users != null && !users.isEmpty()}">
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
        </c:when>
        <c:otherwise>
            <H3>There are no users yet.</H3>
        </c:otherwise>
    </c:choose>
    <br>
    <p><a href="${pageContext.request.contextPath}/roulette/teams/edit?id=${team.id}">Edit team</a></p>
    <p><a href="${pageContext.request.contextPath}/roulette/teams/delete?id=${team.id}">Delete team</a></p>
    <p><a href="${pageContext.request.contextPath}/roulette/teams/search">Use search</a></p>
    <p><a href="${pageContext.request.contextPath}/roulette/teams">Show all teams</a></p>
</body>
</html>
