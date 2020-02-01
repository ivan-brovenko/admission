<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value=""/>
<fmt:setBundle basename="lan"/>

<html>
<head>
    <title><fmt:message key="succ.title"/> </title>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/footer.css">
    <link rel="stylesheet" href="/css/success.css">
</head>
<body>
<header>
    <label id="logo">Stud.ua</label>
    <a id="main_page" href="/stud.ua"><fmt:message key="header.main"/> </a>
    <a id="enter" href="/stud.ua-login"><fmt:message key="succ.header.enter"/> </a>
</header>
<div class="content">
    <h1><fmt:message key="email.confirm.headline"/> </h1>
    <div class="inner"><fmt:message key="email.confirm.text"/></div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>
