<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>

<!DOCTYPE html>
<html>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Add user"/>
</jsp:include>
<body>

<jsp:include page="../navbar.jsp"/>

<div class="d-flex justify-content-center">
    <h2>Add new team: </h2>
</div>
<br>

<div class="d-flex justify-content-center">
    <form action="create" method="post">
        <div class="form-group">
            <label for="name">Name
                <input type="text" class="form-control" id="name" name="name" placeholder="Enter name" required></label>
        </div>
        <br>
        <div style="text-align: center;">
            <button type="submit" class="btn btn-outline-danger" style="size:14px">Create team</button>
            <a class="btn btn-outline-secondary" href="<c:url value="/roulette/teams"/>">Cancel</a>
        </div>
    </form>
</div>

</body>
</html>
