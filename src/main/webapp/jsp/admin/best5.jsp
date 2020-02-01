<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value=""/>
<fmt:setBundle basename="lan"/>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/footer.css">
    <link rel="stylesheet" href="/css/content.css">
    <link rel="stylesheet" href="/css/black_list.css">
</head>
<body>
<header>
    <label id="logo">Stud.ua</label>
    <a id="main_page" href="/stud.ua"><fmt:message key="header.main"/> </a>
    <a href="/stud.ua/management" id="management"><fmt:message key="header.management"/></a>

</header>
<div class="content">
    <h2><fmt:message key="management.best.5"/> </h2>
    <table>
        <tr>
            <td>email</td>
            <td>final mark</td>
        </tr>
        <c:forEach var="adm" items="${admisList}">
            <tr>
                <td>${adm.email}</td>
                <td>${adm.finalMark}</td>
            </tr>
        </c:forEach>
    </table>

</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>
