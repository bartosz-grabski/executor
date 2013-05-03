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
<body onload='document.f.j_username.focus();'>
	<%@ include file="/WEB-INF/views/home/header.jsp" %>
	<h3>Log in, Your Highness!</h3>
 
	<c:if test="${not empty error}">
		<div class="alert alert-error">  
			<a class="close" data-dismiss="alert">�</a>  
			Your login attempt was not successful, try again.<br /> Caused :
			${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message} 
		</div> 
	</c:if>
 
	<form class="form-horizontal" name='f' action="<c:url value='j_spring_security_check' />"
		method='POST'>
			<fieldset>
			<div class="control-group">
				<label class="control-label" for="username">Username : </label>
				<div class="controls">
				<input type='text' id='username' name='j_username' value='' class="input-xlarge">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="password">Password : </label>
				<div class="controls">
				<input type='password' name='j_password' id='password' class="input-xlarge"/>
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