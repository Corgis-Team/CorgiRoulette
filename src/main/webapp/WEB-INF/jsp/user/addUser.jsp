<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="teams" type="java.util.List<com.andersen.corgisteam.corgiroulette.entity.Team>" scope="request"/>
<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>

<!DOCTYPE html>
<html>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Add user"/>
</jsp:include>
<body>

<jsp:include page="../navbar.jsp"/>

<div class="d-flex justify-content-center">
    <h2>Add new user: </h2>
</div>
<br>

<div class="d-flex justify-content-center">
    <form action="create" method="post">
        <div class="form-group">
            <label for="name">Name
                <input type="text" class="form-control" id="name" name="name" placeholder="Enter name" required></label>
        </div>
        <br>
        <div class="form-group">
            <label for="surname">Surname</label>
            <input type="text" class="form-control" id="surname" name="surname" placeholder="Enter surname" required>
        </div>
        <br>
        <div class="form-group">
            <label for="team_id">Team</label>
            <select class="form-control" id="team_id" name="team_id">
                <option value="0"></option>
                <c:forEach var="team" items="${teams}">
                    <option value="${team.id}">${team.name}</option>
                </c:forEach>
            </select>
        </div>
        <br>

        <p class="text-danger"><%= errorMessage%>
        </p>
        <br>

        <div style="text-align: center;">
            <button type="submit" class="btn btn-outline-danger" style="size:14px">Create user</button>
            <a class="btn btn-outline-secondary" href="<c:url value="/roulette/users"/>">Cancel</a>
        </div>
    </form>
</div>
</body>
</html>
