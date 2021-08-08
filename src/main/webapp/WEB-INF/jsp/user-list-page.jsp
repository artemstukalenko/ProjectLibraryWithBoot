<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>

<body>

<h2>${locale.usersListString}</h2>

<br><br><br>

<table border="1">
    <tr>
        <th>${locale.usernameTableHeader}</th>
        <th>${locale.statusTableHeader}</th>
        <th>${locale.userFirstName}</th>
        <th>${locale.userLastName}</th>
        <th>${locale.userEmail}</th>
        <th>${locale.userPhoneNumber}</th>
        <th>${locale.userAddress}</th>
        <th>${locale.userPenalty}</th>
    </tr>

    <c:forEach var="user" items="${allUsers}">

        <c:url var="blockButton" value="/blockUser">
            <c:param name="userName" value="${user.username}"/>
        </c:url>
        <c:url var="unblockButton" value="/unblockUser">
            <c:param name="userName" value="${user.username}"/>
        </c:url>
        <c:url var="deleteButton" value="/deleteUser">
            <c:param name="userName" value="${user.username}"/>
        </c:url>
        <c:url var="makeLibrarianButton" value="/makeUserLibrarian">
            <c:param name="userName" value="${user.username}"/>
        </c:url>
        <c:url var="depriveLibrarianPrivilegesButton" value="/depriveLibrarianRole">
            <c:param name="userName" value="${user.username}"/>
        </c:url>

        <tr>
            <td>${user.username}</td>
            <td>${user.enabled}</td>
            <td>${user.userDetails.userFirstName}</td>
            <td>${user.userDetails.userLastName}</td>
            <td>${user.userDetails.userEmail}</td>
            <td>${user.userDetails.userPhoneNumber}</td>
            <td>${user.userDetails.userAddress}</td>
            <td>${user.userDetails.userPenalty}</td>

            <td>
                <input type="button" value="${locale.blockButton}" onclick="window.location.href = '${blockButton}'"/>
                <input type="button" value="${locale.unblockButton}" onclick="window.location.href = '${unblockButton}'"/>
                <input type="button" value="${locale.deleteUserButton}" onclick="window.location.href = '${deleteButton}'">
                <input type="button" value="Make him librarian" onclick="window.location.href = '${makeLibrarianButton}'">
                <input type="button" value="Make him not librarian" onclick="window.location.href = '${depriveLibrarianPrivilegesButton}'">
            </td>
        </tr>

    </c:forEach>
</table>
<br>
<form:form action="/homepage_again" modelAttribute="locale">

    <input type="submit" value="${locale.toHomePage}"/>

</form:form>

</body>

</html>