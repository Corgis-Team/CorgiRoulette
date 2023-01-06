<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="errorMessage" scope="request" type="java.lang.String"/>

<!DOCTYPE html>
<html>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="notFound"/>
</jsp:include>
<body>

<jsp:include page="navbar.jsp"/>

<div class="d-flex justify-content-center">

    <h1>404 - Not Found</h1>
</div>

<div class="d-flex justify-content-center">
    <p>${errorMessage}</p>
</div>

</body>
</html>
