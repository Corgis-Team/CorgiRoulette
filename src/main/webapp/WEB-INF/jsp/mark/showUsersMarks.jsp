<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>

<html>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Show users' overall marks"/>
</jsp:include>
<body>

<jsp:include page="../navbar.jsp"/>

<div class="d-flex justify-content-center">
    <h2>Users' overall marks: </h2>
</div>

<div class="d-flex justify-content-center">
    <c:choose>
        <c:when test="${marks != null && !marks.isEmpty()}">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col"><b>Name</b></th>
                    <th scope="col"><b>Team</b></th>
                    <th scope="col"><b>Mark</b></th>
                    <th scope="col"><b>User's details</b></th>
                    <th scope="col"><b>Team's details</b></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${marks}" var="mark">
                    <tr>
                        <td><c:out value="${mark.user.name} ${mark.user.surname}"/></td>
                        <td><c:out value="${mark.user.team.name}"/></td>
                        <td><c:out value="${mark.mark}"/></td>
                        <td><a class="text-decoration-none link-danger"
                               href="${pageContext.request.contextPath}/roulette/users/details?id=${mark.user.id}">User's
                            details</a>
                        </td>
                        <td><a class="text-decoration-none link-danger"
                               href="${pageContext.request.contextPath}/roulette/teams/details?id=${mark.user.team.id}">Team's
                            details</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <br>
        </c:when>
        <c:otherwise>
            <div class="d-flex justify-content-center">
                <H4>There are no marks.</H4>
            </div>
            <br>
        </c:otherwise>
    </c:choose>
    <br>
</div>

<p><%= errorMessage%>
</p>

</body>
</html>