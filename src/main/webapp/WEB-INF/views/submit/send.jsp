<!DOCTYPE HTML>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<%@ include file="/WEB-INF/views/home/header.jsp" %>
<body>

	<h3>Send your submit, Your Highness!</h3>
 	<!-- 
	<table class="data">
		<tr>
		    <th>Problem ID</th>
		    <th>Problem Name</th>
		    <th>Problem Author </th>
		    <th>Show Content</th>
		    <th>Send Submit</th>
		</tr>
		<c:if  test="${!empty problemList}">
			<c:forEach items="${problemList}" var="prob">
			    <tr>
			        <td>${prob.id}</td>
			        <td>${prob.name}</td>
			        <td>AUTOR</td>
			        <td><a href='/problem/details?id=${prob.id}'>content</a> </td>
			        <td><a href='/submit/send?id=${prob.id}'>send submit </a> </td>
			    </tr>
			</c:forEach>
		</c:if>
	</table>
	 -->
 	<form class="form-horizontal" name='f' action="<c:url value='/register' />"
		method='POST'>
			<!-- <fieldset>
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
			 -->
	</form>
 	
 	
 	<!-- 
	<form class="form-horizontal" name='f' action="<c:url value='/register' />"
		method='POST'>
			<fieldset>
			 
			</fieldset>
	</form>
	
	 -->
</body>
</html>