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
            <form action="${pageContext.request.contextPath}/roulette/opponent/change" method="post">
                <input type="hidden" name="userToChangeId" value="${pair.user.id}">
                <input type="hidden" name="userToSaveId" value="${pair.opponent.id}">
                <button type="submit" class="btn btn-link link-secondary">Change</button>
            </form>
            <h2> ${pair.user.name} ${pair.user.surname} vs ${pair.opponent.name} ${pair.opponent.surname}</h2>
            <form action="${pageContext.request.contextPath}/roulette/opponent/change" method="post">
                <input type="hidden" name="userToChangeId" value="${pair.opponent.id}">
                <input type="hidden" name="userToSaveId" value="${pair.user.id}">
                <button type="submit" class="btn btn-link link-secondary">Change</button>
            </form>
        </div>
        <br>
        <div class="d-flex justify-content-center">
            <form action="${pageContext.request.contextPath}/roulette/marks/create" method="post">
                <div class="d-flex justify-content-center">
                    <div class="form-group me-2">
                        <input type="hidden" id="userOneId" name="userOneId" value="${pair.user.id}"/>
                        <label for="markOne">${pair.user.name}'s mark</label><br>
                        <input type="number" min="0" value="0" step="0.5" class="form-control" id="markOne"
                               name="markOne" placeholder="Mark for user 1" required>
                    </div>
                    <div class="form-group">
                        <input type="hidden" id="userTwoId" name="userTwoId" value="${pair.opponent.id}"/>
                        <label for="markTwo">${pair.opponent.name}'s mark</label><br>
                        <input type="number" min="0" value="0" step="0.5" class="form-control" id="markTwo"
                               name="markTwo" placeholder="Mark for user 2" required>
                    </div>
                </div>
                <br>
                <div style="text-align: center;">
                    <button type="submit" class="btn btn-outline-danger" style="size:14px">Submit marks</button>
                </div>
            </form>
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