<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="team" class="com.andersen.corgisteam.corgiroulette.entity.Team" scope="request"/>

<!DOCTYPE html>
<html>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Team details"/>
</jsp:include>
<body>

<jsp:include page="../navbar.jsp"/>

<div class="d-flex justify-content-center">
    <h2>Information about team: </h2>
</div>
<br>

<div class="d-flex justify-content-center">
    <div class="card" style="width: 35rem; ">

        <div class="card-body">
            <h4 class="card-title">${team.name}</h4>
            <br>
            <h5 class="card-text">Members</h5>
            <c:choose>
                <c:when test="${team.userList != null && !team.userList.isEmpty()}">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Surname</th>
                            <th>Details</th>
                            <th>Edit</th>
                            <th>Delete</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach items="${team.userList}" var="user">
                            <tr>
                                <td><c:out value="${user.name}"/></td>
                                <td><c:out value="${user.surname}"/></td>
                                <td><a class="text-decoration-none link-danger"
                                       href="${pageContext.request.contextPath}/roulette/users/details?id=${user.id}">Details</a>
                                </td>
                                <td><a class="text-decoration-none link-danger"
                                       href="${pageContext.request.contextPath}/roulette/users/edit?id=${user.id}">Edit</a></td>
                                <td><a class="text-decoration-none link-danger"
                                       href="${pageContext.request.contextPath}/roulette/users/delete?id=${user.id}">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <p>There are no users yet.</p>
                </c:otherwise>
            </c:choose>
            <br>
            <a class="text-decoration-none card-link link-danger"
               href="${pageContext.request.contextPath}/roulette/teams/edit?id=${team.id}">Update</a>
            <a class="text-decoration-none card-link link-danger"
               href="${pageContext.request.contextPath}/roulette/teams/delete?id=${team.id}">Delete</a>
        </div>
    </div>
</div>

</body>
</html>
