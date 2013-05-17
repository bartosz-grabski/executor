<!DOCTYPE HTML>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<%@ include file="/WEB-INF/views/home/header.jsp" %>
<body>

    <c:choose>
      <c:when test="${not empty registered}">
        <div class="alert alert-success">
          <a class="close" data-dismiss="alert">�</a>
          <strong>Success!</strong>You have successfully done it.
        </div>
      </c:when>
      <c:when test="${condition2}">
        <div class="alert alert-error">
            <a class="close" data-dismiss="alert">�</a>
        	<strong>Error! </strong>Try again!
        </div>
      </c:when>
    </c:choose>
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
			<div class="control-group">
            	<label class="control-label" for="password">Email : </label>
            		<div class="controls">
            			<input type='text' name='email' id='email' class="input-xlarge"/>
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