<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>

<body>

<h2>${locale.subscriptionsListString}</h2>

<br><br><br>

<table border="1">

    <tr>
        <th>${locale.subscriptionId}</th>
        <th>${locale.usernameTableHeader}</th>
        <th>${locale.bookId}</th>
        <th>${locale.bookTitle}</th>
        <th>${locale.bookAuthor}</th>
        <th>${locale.startOfThePeriod}</th>
        <th>${locale.endOfThePeriod}</th>
        <th>${locale.isExpired}</th>
    </tr>

    <c:forEach var="subscription" items="${allSubscriptions}">

        <tr>
            <td>${subscription.subscriptionId}</td>
            <td>${subscription.username}</td>
            <td>${subscription.bookId}</td>
            <td>${subscription.title}</td>
            <td>${subscription.author}</td>
            <td>${subscription.startOfThePeriod}</td>
            <td>${subscription.endOfThePeriod}</td>
            <td>
                <c:if test="${subscription.expired}"><c:out value="+"/></c:if>
                <c:if test="${!subscription.expired}"><c:out value="-"/></c:if>
            </td>
        </tr>

    </c:forEach>

</table>
<br><br>
<h2>${locale.requestsHeader}</h2>
<br>
<table border="1">

    <tr>
        <th>${locale.subscriptionId}</th>
        <th>${locale.usernameTableHeader}</th>
        <th>${locale.bookId}</th>
        <th>${locale.bookTitle}</th>
        <th>${locale.bookAuthor}</th>
        <th>${locale.startOfThePeriod}</th>
        <th>${locale.endOfThePeriod}</th>
    </tr>

    <c:forEach var="subscriptionRequest" items="${allRequests}">

        <c:url var="acceptRequestButton" value="/acceptRequest">
            <c:param name="requestId" value="${subscriptionRequest.customSubscriptionId}"/>
        </c:url>
        <c:url var="denyRequestButton" value="/denyRequest">
            <c:param name="requestId" value="${subscriptionRequest.customSubscriptionId}"/>
        </c:url>

        <tr>
            <td>${subscriptionRequest.customSubscriptionId}</td>
            <td>${subscriptionRequest.username}</td>
            <td>${subscriptionRequest.bookId}</td>
            <td>${subscriptionRequest.title}</td>
            <td>${subscriptionRequest.author}</td>
            <td>${subscriptionRequest.startOfThePeriod}</td>
            <td>${subscriptionRequest.endOfThePeriod}</td>

            <td>
                <input type="button" value="${locale.acceptRequestButton}" onclick="window.location.href = '${acceptRequestButton}'"/>
                <input type="button" value="${locale.denyRequestButton}" onclick="window.location.href = '${denyRequestButton}'"/>
            </td>
        </tr>

    </c:forEach>

</table>

<br><br><br>
<form:form action="/homepage_again" modelAttribute="locale">

    <input type="submit" value="${locale.toHomePage}"/>

</form:form>

</body>

</html>