<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html data-ng-app="App">
<head>
    <title>Login Error</title>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <!-- CSS -->
    <%--<c:url var="urlNormalize" value="/css/normalize.css"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="${urlNormalize}">--%>

    <%--<c:url var="urlBootstrap" value="/css/bootstrap.min.css"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="${urlBootstrap}">--%>

    <%--<c:url var="urlFontAwesome" value="/css/font-awesome.min.css"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="${urlFontAwesome}">--%>

    <%--<c:url var="urlAppCss" value="/css/app.css"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="${urlAppCss}">--%>
</head>
<body>

<div class="container">
    <h2 class="text-center">Login Error</h2>
    <p class="text-center bg-danger">
        Wrong password or user name. Please try again:
        <c:url var="urlApplication" value="/index.html"/>
        <a href="${urlApplication}">Login</a>.
    </p>
</div>

</body>
</html>
