<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value=""/>
<fmt:setBundle basename="lan"/>


<html>
<head>
    <title><fmt:message key="rules.title"/> </title>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/footer.css">
    <link rel="stylesheet" href="/css/content.css">
    <link rel="stylesheet" href="/css/rules.css">

</head>
<body>
<header>
    <label id="logo">Stud.ua</label>
    <a id="main_page" href="/stud.ua"><fmt:message key="header.main"/> </a>
</header>
<div class="content">
    <h2><fmt:message key="rules.headline"/> </h2>
    <div class="inner" id="innerPrint">
        <p><fmt:message key="rules.text1"/></p>
        <p> <fmt:message key="account.application.warnign"/></p>
        <p><fmt:message key="rules.text3"/> </p>


    </div>
    <button onclick="printContent('innerPrint')" class="bt"><fmt:message key="rules.button"/> </button>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
<script src="/js/rules.js"></script>
</body>
</html>
