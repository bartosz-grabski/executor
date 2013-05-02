<!DOCTYPE HTML>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <title>Schweizer SGS 1-29</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
		<script src="resources/js/bootstrap.js"></script>
		<script src="resources/js/bootstrap.min.js"></script>
		<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
        <!-- Le styles -->
		<link href="resources/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css" />
        <link href="resources/css/bootstrap.min.css" rel="stylesheet" type="text/css" />

        <style type="text/css">
            body {
                padding-top: 60px;
                padding-bottom: 40px;
              }
              .sidebar-nav {
                padding: 9px 0;
              }
        </style>
</head>
<body>
	<div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container-fluid">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> <span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
                    </a>
                    <a class="brand" href="#">Schweizer SGS 1-29</a>
                    <div class="nav-collapse collapse">
                        <c:if test="${not empty username}">
                        		<p class="navbar-text pull-right">Logged in as
                                    <a href="#" class="navbar-link">${username}</a>
                                </p>
                        </c:if>

                        <ul class="nav">
                            <li>
                                <a href="/home">Home</a>
                            </li>
                            <li>
                                <a href="/login">Log in</a>
                            </li>
                            <li class="active">
                                <a href="/register">Register</a>
                            </li>
                        </ul>
                    </div>
                    <!--/.nav-collapse -->
                </div>
            </div>
        </div>
	<c:if test="${not empty registered}">
		<div class="alert alert-success">  
			<a class="close" data-dismiss="alert">×</a>  
		<strong>Success!</strong>You have successfully done it.  
		</div>  
	</c:if>
	<c:if test="${not empty error}">
		<div class="alert alert-error">  
			<a class="close" data-dismiss="alert">×</a>  
		<strong>Error!</strong>Try again!  
		</div>  
	</c:if>
	<h3>Register, Your Highness!</h3>
 
	<form class="form-horizontal" name='f' action="<c:url value='/register' />"
		method='POST'>
			<fieldset>
			<div class="control-group">
				<label class="control-label" for="username">Username : </label>
				<div class="controls">
				<input type='text' id='username' name='username' value='' class="input-xlarge">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="password">Password : </label>
				<div class="controls">
				<input type='password' name='password' id='password' class="input-xlarge"/>
				</div>
			</div> 			
			<div class="form-actions">  
				<button type="submit" class="btn btn-primary">Save changes</button>  
				<button class="btn">Cancel</button>  
			</div> 
			</fieldset>
	</form>
</body>
</html>