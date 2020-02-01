<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="auth" uri="http://light.net/taglib/auth" %>

<fmt:setLocale value=""/>
<fmt:setBundle basename="lan"/>
<c:set var="user" scope="page" value="${auth:user(pageContext.request)}"/>

<html>
<head>
    <title>Stud.ua</title>
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="/css/footer.css">
</head>

<body onload="f()">
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<div id="slider">
    <div id="line">
        <img src="/img/mpall.jpg" alt="">
    </div>
</div>
<div id="hl">
    <fmt:message key="main.slider.headline"/>
</div>
<div>
    <button id="button1" onclick="buttonRight()">></button>
    <button id="button2" onclick="buttonLeft()"><</button>
</div>
<div class="tabcont">
    <div class="tab"><div class="squareText"><fmt:message key="main.square.registration"/> </div><img src="/img/square1.jpg"></div>
        <div class="tab"><div class="squareText"><fmt:message key="main.square.department"/> </div><img src="/img/square2.jpg"></div>
        <div class="tab"><div class="squareText"><fmt:message key="main.square.form"/> </div><img src="/img/square3.jpg"></div>
    <div class="tab"><div class="squareText"><fmt:message key="main.square.results"/> </div><img src="/img/square4.jpg"></div>
</div>

<div class="footer">

    <div class="rcol">
        <h2><b><fmt:message key="main.nofooter.adress"/> </b></h2>
        <p>
            <fmt:message key="main.nofooter.text"/>
        </p>

    </div>

</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
<script src="/js/slider.js"></script>
<script src="/js/lang.js"></script>

</body>
</html>