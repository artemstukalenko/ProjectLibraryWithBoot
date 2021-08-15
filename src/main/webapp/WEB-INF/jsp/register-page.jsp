<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>

<body>

<h2>${locale.registration}</h2>

<form:form action="/registerNewUser" modelAttribute="potentialUser" method="post">

    ${locale.loginUsername}: <form:input path="username"/>
    <form:errors path="username"/>

    <b <c:if test="${!loginNotValid}"><c:out value="hidden='true'"/></c:if>>>
        Login is taken, try again
    </b>

    <br><br>
    ${locale.loginPassword}: <form:password path="password"/>
    <form:errors path="password"/>


    <br><br>

    <input type="submit" value="OK">

</form:form>
<br><br><br>

<form:form action="/login">
    <input type="submit" value="${locale.cancel}">
</form:form>

</body>

</html>