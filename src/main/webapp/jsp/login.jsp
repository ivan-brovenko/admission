<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@taglib prefix="auth" uri="http://light.net/taglib/auth" %>
<fmt:setLocale value=""/>
<fmt:setBundle basename="lan"/>
<c:set var="user" scope="page" value="${auth:user(pageContext.request)}"/>
<html>
<head>
    <title><fmt:message key="auth.page.title"/></title>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/footer.css">
    <link rel="stylesheet" href="/css/login.css">
</head>
<body>
<header>
    <label id="logo">Stud.ua</label>
    <a id="main_page" href="/stud.ua"><fmt:message key="header.main"/> </a>
</header>


<div class="content">
    <h1><fmt:message key="auth.page.title"/> </h1>
    <p><fmt:message key="auth.page.message"/> </p>


    <form action="/stud.ua-login" onsubmit="return checkForm(this)" method="post" id="loginForm">

        <label for="login"><fmt:message key="auth.page.login_form.login"/> :</label>
        <input id="login" type="text" name="login"/>
        <label for="password"><fmt:message key="auth.page.login_form.password"/>:</label>
        <input id="password" type="password" name="mypassword"/>
        <input type="submit" class="bt" value="<fmt:message key="auth.page.login_form.submit"/>"/>
    </form>

    <div class="forgLogin">
        <span id="forgLink" onclick="forg()"><fmt:message key="login.forgot.link"/> </span>
        <div class="innerForg">
            <h4><fmt:message key="login.forgot.headline"/> </h4>
            <br>
            <form action="/servlet?c=forg&language=" method="post" onsubmit="return loginVal(this)">
            <label for="forgotLogin"><fmt:message key="auth.page.login_form.login"/> :</label>
            <input type="text" name="forgotLogin" id="forgotLogin">
            <input type="submit" value="<fmt:message key="contact.button"/>" class="bt">
            </form>
        </div>
    </div>

    <div class="wrongPassword">${errorLoginPassMessage}</div>
    <div class="wrongPassword">${blockedLoginMessage}</div>
    <div class="wrongPassword" style="display: none"><fmt:message key="error.auth.invalid_symbols"/>!!!</div>
    <div class="wrongPassword" style="display:none;"><fmt:message key="reg.error.email"/> </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
<script src="/js/login.js"></script>
</body>
</html>
