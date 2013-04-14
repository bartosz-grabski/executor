<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Spring 3 MVC Series - Contact Manager </title>
</head>
<body>
 
<h2>Contact Manager</h2>
 
<form:form method="post" action="add.html" commandName="car">
 
    <table>
    <tr>
        <td><form:label path="firstname">FirstName</form:label></td>
        <td><form:input path="firstname" /></td>
    </tr>
    <tr>
        <td><form:label path="lastname">LastName</form:label></td>
        <td><form:input path="lastname" /></td>
    </tr>
    <tr>
        <td colspan="2">
            <input type="submit" value="Add Car"/>
        </td>
    </tr>
</table> 
</form:form>
 
     
<h3>CARS</h3>
<c:if  test="${!empty carList}">
<table class="data">
<tr>
    <th>Name</th>
    <th>&nbsp;</th>
</tr>
<c:forEach items="${carList}" var="car">
    <tr>
        <td>${car.lastname}, ${car.firstname} </td>
    </tr>
</c:forEach>
</table>
</c:if>
 
</body>
</html>