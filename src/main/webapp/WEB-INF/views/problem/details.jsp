<!DOCTYPE HTML>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">


<%@ include file="/WEB-INF/views/home/header.jsp" %>
<body>
	
	<h3>This is the chosen problem, Your Highness!</h3>
 	
 	<c:choose>
			<c:when test="${problem != null}">
				<div class="problemDetails">
					<div id="id">
						ID : ${problem.id}
					</div></br>
					<div id="name">
						Name : ${problem.name}
					</div></br>
					<div id="author">
						AUTOR : 
					</div></br>
					<div id="content">
						Content : ${problem.content}
					</div></br>
					<div id="sendSubmit">
						<a href='/submit/send?id=${problem.id}'>Send your submit </a>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				nie ma takiego problemu... :(
			</c:otherwise>
		</c:choose>
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
			        <td> </td>
			    </tr>
			</c:forEach>
		</c:if>
		 -->
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