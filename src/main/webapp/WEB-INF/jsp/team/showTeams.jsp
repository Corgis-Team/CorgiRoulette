<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>

<html>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Show all teams"/>
</jsp:include>
<body>

<jsp:include page="../navbar.jsp"/>

<div class="d-flex justify-content-center">
    <h2>Teams: </h2>
</div>

<div class="d-flex justify-content-center">
    <c:choose>
        <c:when test="${teams != null && !teams.isEmpty()}">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col"><b>Id</b></th>
                    <th scope="col"><b>Name</b></th>
                    <th scope="col"><b>Details</b></th>
                    <th scope="col"><b>Edit</b></th>
                    <th scope="col"><b>Delete</b></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${teams}" var="team">
                    <tr>
                        <td><c:out value="${team.id}"/></td>
                        <td><c:out value="${team.name}"/></td>
                        <td><a class="text-decoration-none link-danger"
                               href="${pageContext.request.contextPath}/roulette/teams/details?id=${team.id}">Details</a>
                        </td>
                        <td><a class="text-decoration-none link-danger"
                               href="${pageContext.request.contextPath}/roulette/teams/edit?id=${team.id}">Edit</a>
                        <td><a class="text-decoration-none link-danger"
                               href="${pageContext.request.contextPath}/roulette/teams/delete?id=${team.id}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <br>
        </c:when>
        <c:otherwise>
            <div class="d-flex justify-content-center">
                <H4>There are no teams.</H4>
            </div>
            <br>
        </c:otherwise>
    </c:choose>
    <br>
</div>

<div class="d-flex justify-content-center">
    <a class="text-decoration-none link-danger" href="<c:url value="/roulette/teams/new"/>">
        Add new team
    </a>
</div>

<p><%= errorMessage%>
</p>

</body>
</html>