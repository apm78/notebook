<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Notebook - Login</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <c:url var="urlStyles" value="/login-styles.css"/>
    <link rel="stylesheet" href="${urlStyles}">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
<body>

<div class="vertical-center">
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h2 class="text-center">Login to Notebook</h2>
                    </div>
                    <div class="panel-body">
                        <% if ("true".equals(request.getParameter("login-error")))
                        { %>
                        <div class="alert alert-danger text-center">
                            Wrong password or user name.
                        </div>
                        <% } %>

                        <form class="form-horizontal" role="form" method="post" action="j_security_check"
                              name="loginForm">
                            <div class="form-group">
                                <label class="control-label col-md-4" for="username">User name:</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" name="j_username"
                                           id="username" placeholder="Enter user name">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-4" for="pwd">Password:</label>
                                <div class="col-md-8">
                                    <input type="password" class="form-control" name="j_password"
                                           id="pwd" placeholder="Enter password">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-offset-4 col-md-8">
                                    <button type="submit" class="btn btn-default">Submit</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
