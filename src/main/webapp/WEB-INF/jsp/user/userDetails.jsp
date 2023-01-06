<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.time.format.DateTimeFormatter" %>

<jsp:useBean id="user" class="com.andersen.corgisteam.corgiroulette.entity.User" scope="request"/>
<jsp:useBean id="team_name" class="java.lang.String" scope="request"/>

<!DOCTYPE html>
<html>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="User details"/>
</jsp:include>
<body>

<jsp:include page="../navbar.jsp"/>

<div class="d-flex justify-content-center">
    <h2>Information about user: </h2>
</div>
<br>

<div class="d-flex justify-content-center">
    <div class="card" style="width: 35rem; ">

        <div class="card-body">
            <h5 class="card-title">${user.name} ${user.surname}</h5>
            <br>

            <c:if test="${user.team != null}">
                <p class="card-text">${user.team.name}</p>
            </c:if>

            <p class="card-text">
                The last battle date:
                <%= user.getLastDuel().format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"))%>
            </p>
            <br>

            <a class="text-decoration-none card-link link-danger"
               href="${pageContext.request.contextPath}/roulette/users/edit?id=${user.id}">Update</a>
            <a class="text-decoration-none card-link link-danger"
               href="${pageContext.request.contextPath}/roulette/users/delete?id=${user.id}">Delete</a>
        </div>
    </div>
</div>

</body>
</html>