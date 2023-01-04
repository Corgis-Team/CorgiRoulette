<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="errorMessage" scope="request" type="java.lang.String"/>

<html>
<head>
    <title>404 - Not Found</title>
</head>
<body>
    <h1>404 - Not Found</h1>
    <p>${errorMessage}</p>
</body>
</html>
