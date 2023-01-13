<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>

<html>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Edit mark"/>
</jsp:include>
<body>

<jsp:include page="../navbar.jsp"/>

<div class="d-flex justify-content-center">
    <h2>Edit mark: </h2>
</div>

<div class="d-flex justify-content-center">
    <form action="update" method="post">
        <input name="id" value="${mark.id}" type="hidden">

        <div class="form-group">
            <label for="mark">Mark
                <input type="number" min="0" step="0.5" class="form-control" id="mark" name="mark" value="${mark.mark}"
                       placeholder="Enter mark..." required></label>
        </div>
        <br>

        <p class="text-danger"><%= errorMessage%>
        </p>
        <br>

        <div style="text-align: center;">
            <button type="submit" class="btn btn-outline-danger" style="size:14px">Update mark</button>
            <a class="btn btn-outline-secondary" href="<c:url value="/roulette/marks"/>">Users' overall marks</a>
        </div>
    </form>
</div>

</body>
</html>