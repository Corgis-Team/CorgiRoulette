<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="user" type="com.andersen.corgisteam.corgiroulette.entity.User" scope="request"/>
<jsp:useBean id="teams" type="java.util.List<com.andersen.corgisteam.corgiroulette.entity.Team>" scope="request"/>
<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>

<!DOCTYPE html>
<html>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Edit user"/>
</jsp:include>

<style>
    .form-check-input:checked {
        background-color: red;
        border-color: pink;
    }

    .form-switch .form-check-input {
        background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='-4 -4 8 8'%3e%3ccircle r='3' fill='red'/%3e%3c/svg%3e");
    }
    .form-switch .form-check-input:focus {
        background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='-4 -4 8 8'%3e%3ccircle r='3' fill='pink'/%3e%3c/svg%3e");
    }

</style>

<body>

<jsp:include page="../navbar.jsp"/>

<div class="d-flex justify-content-center">
    <h2>Edit user’s information: </h2>
</div>
<br>

<div class="d-flex justify-content-center">
    <form action="update" method="post">
        <input name="id" value="${user.id}" type="hidden">

        <div class="form-group">
            <label for="name">Name
                <input type="text" class="form-control" id="name" name="name" value="${user.name}"
                       placeholder="Current name (Vlad)" required></label>
        </div>
        <br>
        <div class="form-group">
            <label for="surname">Surname</label>
            <input type="text" class="form-control" id="surname" name="surname" value="${user.surname}"
                   placeholder="Current surname (Nemiro)" required>
        </div>
        <br>
        <div class="form-group">
            <label for="team_id">Team</label>
            <select class="form-control" id="team_id" name="team_id">
                <option value="0"></option>
                <c:forEach var="team" items="${teams}">
                    <option value="${team.id}"
                            <c:if test="${team.id == user.team.id}">selected</c:if>>
                            ${team.name}
                    </option>
                </c:forEach>
            </select>
        </div>
        <br>
        <div class="form-check form-switch">
            <label class="form-check-label" for="isChosen">Was chosen?</label>
            <input class="form-check-input" type="checkbox" id="isChosen" name="isChosen" value="true"
                   <c:if test="${user.chosen}">checked</c:if>>
        </div>
        <br>

        <p class="text-danger"><%= errorMessage%>
        </p>
        <br>

        <div style="text-align: center;">
            <button type="submit" class="btn btn-outline-danger" style="size:14px">Edit user</button>
            <a class="btn btn-outline-secondary" href="<c:url value="/roulette/users"/>">Cancel</a>
        </div>
    </form>
</div>

</body>
</html>
