<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value=""/>
<fmt:setBundle basename="lan"/>

<html>
<head>
    <title><fmt:message key="management.title"/> </title>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/footer.css">
    <link rel="stylesheet" href="/css/content.css">
    <link rel="stylesheet" href="/css/mangement.css">
</head>
<body>
<header>
    <label id="logo">Stud.ua</label>
    <a id="main_page" href="/stud.ua"><fmt:message key="header.main"/> </a>
    <a id="admission" href="/stud.ua/admission"><fmt:message key="management.header.admission"/> </a>
    <a id="blackList" href="/stud.ua/black-list"><fmt:message key="management.header.blacklist"/> </a>
    <a href="/stud.ua/top5" id="st5"><fmt:message key="management.best.5"/> </a>
</header>
<div class="content">
<div class="departments">
    <h3><fmt:message key="management.departments.headline"/> </h3>
    <div class="inner"><fmt:message key="management.departments.text"/></div>
    <a href="/stud.ua/departments"><button class="bt"><fmt:message key="management.departments.button"/> </button></a>
</div>
<div class="students">
    <h3><fmt:message key="management.entrants.headline"/> </h3>
    <div class="inner"><fmt:message key="management.entrants.text"/></div>
    <a href="/stud.ua/entrants"><button class="bt"><fmt:message key="management.entrants.button"/> </button></a>
</div>


</div>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>
