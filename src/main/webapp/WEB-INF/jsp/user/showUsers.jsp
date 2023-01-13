<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>

<!DOCTYPE html>
<html>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Show All Users"/>
</jsp:include>
<body>

<jsp:include page="../navbar.jsp"/>

<div class="container">
    <div class="d-flex justify-content-center">
        <h2>Users: </h2>
    </div>

    <div class="d-flex justify-content-end">
        <a class="btn btn-outline-danger" href="${pageContext.request.contextPath}/roulette/users/refresh">Refresh</a>
    </div>

    <div class="d-flex justify-content-center">
        <c:choose>
            <c:when test="${users != null && !users.isEmpty()}">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col"><b>Name</b></th>
                        <th scope="col"><b>Surname</b></th>
                        <th scope="col"><b>Was chosen?</b></th>
                        <th scope="col"><b>Team</b></th>
                        <th scope="col"><b>Details</b></th>
                        <th scope="col"><b>Edit</b></th>
                        <th scope="col"><b>Delete</b></th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td><c:out value="${user.name}"/></td>
                            <td><c:out value="${user.surname}"/></td>
                            <td><c:out value="${user.chosen ? 'Yes' : 'No'}"/></td>
                            <td><c:out value="${user.team.name}"/></td>
                            <td><a class="text-decoration-none link-danger"
                                   href="${pageContext.request.contextPath}/roulette/users/details?id=${user.id}">Details</a>
                            </td>
                            <td><a class="text-decoration-none link-danger"
                                   href="${pageContext.request.contextPath}/roulette/users/edit?id=${user.id}">Edit</a>
                            </td>
                            <td><a class="text-decoration-none link-danger"
                                   href="${pageContext.request.contextPath}/roulette/users/delete?id=${user.id}">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <br>
            </c:when>
            <c:otherwise>
                <div class="d-flex justify-content-center">
                    <H4>There are no users.</H4>
                </div>
                <br>
            </c:otherwise>
        </c:choose>
        <br>
    </div>

    <div class="d-flex justify-content-center">
        <a class="text-decoration-none link-danger" href="<c:url value="/roulette/users/new"/> ">
            Add new user
        </a>
    </div>

    <p><%= errorMessage%>
    </p></div>

</body>
</html>