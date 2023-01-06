<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}">CorgiTeam</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="<c:url value="/roulette/users"/>">Users</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="<c:url value="/roulette/teams"/>">Teams</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled" aria-current="page" href="<c:url value="/roulette/history"/>">History</a>
                </li>

            </ul>
            <form class="d-flex" action="${pageContext.request.contextPath}/roulette/users/search/results" method="get">
                <input class="form-control me-2" name="full_name" type="search" placeholder="Search by user name"
                       aria-label="Search">
                <button class="btn btn-outline-danger" type="submit">Search</button>
            </form>
        </div>
    </div>
</nav>
<br>
