<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<jsp:include page="WEB-INF/jsp/header.jsp">
    <jsp:param name="title" value="Corgi Roulette"/>
</jsp:include>
<body>

<jsp:include page="WEB-INF/jsp/navbar.jsp"/>

<div class="d-flex justify-content-center">
    <H2>Corgi Roulette</H2>
</div>

<br>

<div class="d-flex justify-content-center">
    <form class="d-flex" action="${pageContext.request.contextPath}/roulette" method="get">
        <button class="btn btn-danger" style="font-size: 24px;" type="submit">Create pair of opponents</button>
    </form>
</div>

<br>
<br>
<div class="d-flex justify-content-center">
    <H3>The pair:</H3>
</div>
<br>

<c:choose>
    <c:when test="${pair != null}">
        <div class="d-flex justify-content-center">
            <div class="d-flex justify-content-center">
                <H4> ${pair.user.name} ${pair.user.surname} vs ${pair.opponent.name} ${pair.opponent.surname}</H4>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="d-flex justify-content-center">
            <H4>There is no pair.</H4>
        </div>
        <br>
    </c:otherwise>
</c:choose>

</body>
</html>