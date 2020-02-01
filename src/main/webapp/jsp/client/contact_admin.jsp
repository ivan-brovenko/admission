<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value=""/>
<fmt:setBundle basename="lan"/>

<html>
<head>
    <title><fmt:message key="header.contact"/> </title>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/footer.css">
    <link rel="stylesheet" href="/css/content.css">
    <link rel="stylesheet" href="/css/contacts.css">
</head>
<body>
<header>
    <label id="logo">Stud.ua</label>
    <a id="main_page" href="/stud.ua"><fmt:message key="header.main"/></a>
    <a id="acc" href="/stud.ua-account"><fmt:message key="header.account"/> </a>
</header>
<div class="content">
    <h2><fmt:message key="contact.header"/> </h2>
    <div class="inner">
        <form action="/servlet?c=contact&language=" method="post" onsubmit="return emailVal(this)">
            <h5><fmt:message key="contact.pass"/> </h5>
            <input class="field" id="pass" type="password" name="pass">
            <h5><fmt:message key="contact.subject"/> </h5>
            <input class="field" type="text" id="subject" name="subject">
            <textarea class="field" name="message" id="area"></textarea>
            <input class="bt" type="submit" value="<fmt:message key="contact.button"/> ">
        </form>
    </div>
    <div class="rightInner">
        <h5><fmt:message key="contact.rules"/> </h5>
        <p> <fmt:message key="contact.text"/> </p>
        <p><a href="https://myaccount.google.com/lesssecureapps">lesssecureapp.com</a></p>
    </div>
    <div class="notif"><fmt:message key="reg.error.all"/> </div>
    <div class="notif" id="visibleNotif">${notifGood}</div>
    <div class="notif" id="visibleNotif1">${notifWrong}</div>

</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
<script src="/js/contacts.js"></script>
</body>
</html>
