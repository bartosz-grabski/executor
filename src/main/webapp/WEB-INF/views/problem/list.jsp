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
	<%@ include file="/WEB-INF/views/home/header.jsp" %>
	<h3>Choose your problems, Your Highness!</h3>
 	
	<table class="data">
		<tr>
		    <th>Problem ID</th>
		    <th>Problem Name</th>
		    <th>Problem content </th>
		</tr>
		<c:if  test="${!empty problemList}">
			<c:forEach items="${problemList}" var="prob">
			    <tr>
			        <td>${prob.id}</td>
			        <td>${prob.name}</td>
			        <td>${prob.content}</td>
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