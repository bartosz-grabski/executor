<!DOCTYPE HTML>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<%@ include file="/WEB-INF/views/home/header.jsp" %>
	<h3>Choose your problems, Your Highness!</h3>
 	
	<table class="table">
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
 	
 	<!-- 
	<form class="form-horizontal" name='f' action="<c:url value='/register' />"
		method='POST'>
			<fieldset>
			 
			</fieldset>
	</form>
	
	 -->
</body>
</html>