<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>

<html>
<head>
    <title>Notebook - Login</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <!-- don't remove!!! -->
    <%--<meta name="unauthorized" content="true">--%>

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
    <h2 class="text-center">Login to Notebook</h2>
    <form class="form-horizontal" role="form" method="post" action="j_security_check">
        <div class="form-group">
            <label class="control-label col-sm-2" for="username">User name:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="j_username"
                       id="username" placeholder="Enter user name">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="pwd">Password:</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" name="j_password"
                       id="pwd" placeholder="Enter password">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Submit</button>
            </div>
        </div>
    </form>
</div>

<%--<div class="login clearfix">--%>
    <%--<form method="post" action="j_security_check" name="loginForm" role=form>--%>
        <%--<div class="form-group">--%>
            <%--<input id="username" type="text" name="j_username" placeholder="Benutzername">--%>
            <%--<label for="username"><span class="icon-user"></span></label>--%>
        <%--</div>--%>

        <%--<div class="form-group">--%>
            <%--<input id="password" type="password" name="j_password" placeholder="Passwort">--%>
            <%--<label for="password"><span class="icon-lock"></span></label>--%>
        <%--</div>--%>

        <%--<input type="submit" class="btn btn-lg btn-block btn-default" value="Anmelden">--%>
    <%--</form>--%>
<%--</div>--%>

</body>
</html>
