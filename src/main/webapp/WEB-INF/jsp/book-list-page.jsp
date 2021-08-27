<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>

<body>

<h2>${locale.booksListString}</h2>

<br><br><br>

<form:form action="searchBook" modelAttribute="searcher">
    <form:input path="userInput"/>
    <form:select path="searchCriteria">
        <form:option value="byTitle" label="${locale.filterByTitle}"/>
        <form:option value="byAuthor" label="${locale.filterByAuthor}"/>
        <form:option value="byYear" label="${locale.filterByYear}"/>
    </form:select>
    <input type="submit" value="${locale.search}">
</form:form>


<br><br>

<table border="1">
    <tr>
        <th>${locale.bookId}</th>
        <th>${locale.bookTitle}</th>
        <th>${locale.bookAuthor}</th>
        <th>${locale.bookYearOfPublishing}</th>
        <security:authorize access="hasRole('LIBRARIAN')">
            <th>${locale.statusTableHeader}</th>
        </security:authorize>
        <th>
            <form:form action="sort" modelAttribute="listSorter">
                <form:select path="sortMethod">
                    <form:option value="byTitle" label="${locale.filterByTitle}"/>
                    <form:option value="byAuthor" label="${locale.filterByAuthor}"/>
                    <form:option value="byYear" label="${locale.filterByYear}"/>
                </form:select>
                <input type="submit" value="${locale.filter}">
            </form:form>

        </th>
    </tr>

    <c:forEach var="book" items="${allBooks}">

        <c:url var="deleteBookButton" value="/deleteBook">
            <c:param name="bookId" value="${book.bookId}"/>
        </c:url>
        <c:url var="arrangeSubscriptionButton" value="/arrangeSubscription">
            <c:param name="bookId" value="${book.bookId}"/>
        </c:url>
        <c:url var="arrangeCustomSubscriptionRequestButton" value="/arrangeCustomRequest">
            <c:param name="bookId" value="${book.bookId}"/>
        </c:url>
        <c:url var="changeTakenValueButton" value="/changeTakenValue">
            <c:param name="bookId" value="${book.bookId}"/>
        </c:url>

        <tr>
            <td>${book.bookId}</td>
            <td>${book.bookTitle}</td>
            <td>${book.bookAuthor}</td>
            <td>${book.bookYearOfPublishing}</td>

            <security:authorize access="hasRole('LIBRARIAN')">
                <td>
                    <c:if test="${book.taken}"><c:out value="${locale.bookIsAvailable}"/></c:if>
                    <c:if test="${!book.taken}"><c:out value="${locale.bookIsNotAvailable}"/></c:if>
                </td>
            </security:authorize>

            <td>

                <security:authorize access="hasRole('ADMIN')">
                    <input type="button" value="${locale.deleteBook}" onclick="window.location.href = '${deleteBookButton}'">
                </security:authorize>
                <security:authorize access="hasRole('USER')">
                    <input type="button" value="${locale.arrangeSubscriptionButton}" onclick="window.location.href = '${arrangeSubscriptionButton}'"
                    <c:if test="${book.taken}"><c:out value="disabled='disabled'"/></c:if>>
                </security:authorize>
                <security:authorize access="hasRole('USER')">
                    <input type="button" value="${locale.arrangeCustomRequest}" onclick="window.location.href = '${arrangeCustomSubscriptionRequestButton}'"
                </security:authorize>
                <security:authorize access="hasRole('LIBRARIAN')">
                    <input type="button" value="${locale.setTakenButton}" onclick="window.location.href = '${changeTakenValueButton}'"
                </security:authorize>
            </td>
        </tr>
    </c:forEach>

</table>
<br/><br/>
${locale.pagesLabel}
<c:forEach var="number" items="${pagesCount}">
    <c:url var="changePageButton" value="booksList">
        <c:param name="pageNumber" value="${number}"/>
    </c:url>
    <input type="button" value="${number}" onclick="window.location.href = '${changePageButton}'"/>
    &nbsp;&nbsp;
</c:forEach>

<br><br>
<security:authorize access="hasRole('ADMIN')">
    <input type="button" value="${locale.addNewBook}" onclick="window.location.href = '/enterInfoForNewBook'">
</security:authorize>

<br><br><br>

<form:form action="getUnfilteredBookList" modelAttribute="locale">

    <input type="submit" value="${locale.removeFiltersButton}"/>

</form:form>

<br><br>

<form:form action="/homepage_again" modelAttribute="locale">

    <input type="submit" value="${locale.toHomePage}"/>

</form:form>

</body>

</html>