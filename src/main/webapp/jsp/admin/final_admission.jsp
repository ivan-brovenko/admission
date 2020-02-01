<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value=""/>
<fmt:setBundle basename="lan"/>

<html>
<head>
    <title><fmt:message key="final.admission.headline"/> </title>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/footer.css">
    <link rel="stylesheet" href="/css/content.css">
    <link rel="stylesheet" href="/css/entrants.css">
    <link rel="stylesheet" href="/css/final_admission.css">
</head>
<body>
<header>
    <label id="logo">Stud.ua</label>
    <a id="main_page" href="/stud.ua"><fmt:message key="header.main"/> </a>
    <a href="/stud.ua/management" id="management"><fmt:message key="header.management"/></a>
</header>
<div class="content">
    <h2><fmt:message key="final.admission.headline"/> </h2>
<table>
    <tr>
        <td><fmt:message key="final.admission.entrant.id"/> </td>
        <td>email</td>
        <td><fmt:message key="entrants.departments.table.department"/> </td>
        <td><fmt:message key="entrants.departments.table.univer"/> </td>
        <td><fmt:message key="entrants.departments.table.priority"/> </td>
        <td><fmt:message key="final.admission.education.form"/> </td>
    </tr>
    <c:forEach var="ad" items="${adm}">
        <tr>
            <td>${ad.entrantId}</td>
            <td>${ad.email}</td>
            <td>${ad.departmentName}</td>
            <td>${ad.uneiveName}</td>
            <td>${ad.priority}</td>
            <td>${ad.price}</td>
        </tr>
    </c:forEach>
</table>
    <a href="/servlet?c=send&language="><input type="button" class="bt" value="<fmt:message key="final.admission.button"/> "></a>
    <div class="notifWrong">${messagesSent}</div>
    <div class="bottomBlock"></div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>
