<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>
<jsp:useBean id="team" class="com.andersen.corgisteam.corgiroulette.entity.Team" scope="request"/>

<!DOCTYPE html>
<html>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Edit team"/>
</jsp:include>
<body>

<jsp:include page="../navbar.jsp"/>

<div class="d-flex justify-content-center">
    <h2>Edit team's information: </h2>
</div>
<br>

<div class="d-flex justify-content-center">
    <form action="update" method="post">
        <input name="id" value="${team.id}" type="hidden">

        <div class="form-group">
            <label for="name">Name
                <input type="text" class="form-control" id="name" name="name" value="${team.name}"
                       placeholder="Current name (Red)" required></label>
        </div>
        <br>

        <p class="text-danger"><%= errorMessage%>
        </p>
        <br>

        <div style="text-align: center;">
            <button type="submit" class="btn btn-outline-danger" style="size:14px">Update team</button>
            <a class="btn btn-outline-secondary" href="<c:url value="/roulette/teams"/>">Cancel</a>
        </div>
    </form>
</div>
</body>
</html>
