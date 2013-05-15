<!DOCTYPE HTML>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<%@ include file="/WEB-INF/views/home/header.jsp" %>
<body onload='document.f.j_username.focus();'>
	
	<h3>Log in, Your Highness!</h3>
 
	<c:if test="${not empty error}">
		<div class="alert alert-error">  
			<a class="close" data-dismiss="alert">×</a>  
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