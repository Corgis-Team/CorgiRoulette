<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>

<html>
<head>
    <title>Show all teams</title>
</head>
<body>
<div>
    <h2>Teams: </h2>

    <c:choose>
        <c:when test="${teams != null && !teams.isEmpty()}">
            <table>
                <thead>
                <tr>
                    <th><b>Id</b></th>
                    <th><b>Name</b></th>
                    <th><b>Show</b></th>
                    <th><b>Detele</b></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${teams}" var="team">
                    <tr>
                        <td><c:out value="${team.id}"/></td>
                        <td><c:out value="${team.name}"/></td>
                        <td><a href="${pageContext.request.contextPath}/teams/details?id=${team.id}">Show</a></td>
                        <td><a href="${pageContext.request.contextPath}/teams/details?id=${team.id}">Show</a></td>
                        <td><a href="${pageContext.request.contextPath}/teams/delete?id=${team.id}">Delete</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <br>
        </c:when>
        <c:otherwise>
            <H3>There are no teams.</H3>
            <br>
        </c:otherwise>
    </c:choose>
    <br>

    <div>
        <a href="<c:url value="/teams/new"/> ">
            <button>Add new team</button>
        </a>
    </div>

    <p><%= errorMessage%></p>
</div>

</body>
</html>