<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>

<!DOCTYPE html>
<html>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Show search Users"/>
</jsp:include>
<body>
<jsp:include page="../navbar.jsp"/>
<div class="d-flex justify-content-center">
    <h2>Users search result: </h2>
</div>

<div class="d-flex justify-content-center">
    <table class="table">
        <thead>
        <tr>
            <th scope="col"><b>Name</b></th>
            <th scope="col"><b>Surname</b></th>
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
                <td><a href="${pageContext.request.contextPath}/roulette/users/details?id=${user.id}">Details</a></td>
                <td><a href="${pageContext.request.contextPath}/roulette/users/edit?id=${user.id}">Edit</a></td>
                <td><a href="${pageContext.request.contextPath}/roulette/users/delete?id=${user.id}">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<br>
<br>

<p><%= errorMessage%>
</p>
</body>
</html>
