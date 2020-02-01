<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value=""/>
<fmt:setBundle basename="lan"/>

<html>
<head>
    <title>Sign up</title>
    <link rel="stylesheet" href="/css/footer.css">
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/signup.css">
</head>
<body onload="randNumb()">
<header>
    <label id="logo">Stud.ua</label>
    <a id="main_page" href="/stud.ua"><fmt:message key="header.main"/> </a>
</header>
<div class="content">
    <div class="incontent">
    <h1><fmt:message key="reg.headline"/> </h1>

    <form   onsubmit="return validate(this)" method="post" id="regForm">
            <div class="info">
            <label><fmt:message key="reg.fname"/> </label>
            <br>
            <input type="text" class="form" name="firstName" id="name">
            <br>
            <label><fmt:message key="reg.lname"/> </label>
            <br>
            <input class="form" type="text" name="lastName" id="lastName">
            <br>
            <label><fmt:message key="reg.pat"/> </label>
            <br>
            <input class="form" type="text" name="patronymic" id="patronymic">
            <br>
            <label><fmt:message key="reg.school"/> </label>
            <br>
            <input class="form" type="text" name="schoolName" id="schName">
            <br>
            <label><fmt:message key="reg.city"/> </label>
            <br>
            <input class="form" type="text" name="city" id="city">
            <br>
            <label><fmt:message key="reg.region"/> </label>
            <br>
            <input class="form" type="text" name="region" id="region">

            <div class="captcha">
                <h5><fmt:message key="reg.captcha"/> </h5>
                <span class="form" id="digit1"></span> +
                <span class="form" id="digit2"></span> =
                <span><input class="captchaInput" id="answer" type="text"></span>
            </div>

        <br>
        </div>
        <div class="password">
            <label>E-mail</label>
            <br>
            <input class="form" type="text" name="email" id="email">
            <br>
        <label><fmt:message key="reg.pass"/> </label>
        <br>
        <input class="form" type="password" name="password" id="password">
        <br>
        <label><fmt:message key="reg.rpass"/> </label>
        <br>
        <input class="form" type="password" name="rpassword" id="rpassword">
        <br>
        <%--<label>Scan</label>--%>
        <%--<br>--%>
        <%--<input class="form" type="file" name="scan" value="select file">--%>
        <%--<br>--%>

        <input class="form bt" type="submit" name="submit" value="<fmt:message key="reg.signup"/> ">
        </div>

        <%--ФИО, email, город, область, название учебного заведения--%>

    </form>

        <div class="notif">
            <div class="wrongPassword" id="fillAll"><fmt:message key="reg.error.all"/> </div>
            <div class="wrongPassword" id="forbSymb"><fmt:message key="reg.error.forb"/> </div>
            <div class="wrongPassword" id="noMatch"><fmt:message key="reg.error.match"/> </div>
            <div class="wrongPassword" id="wrongEmail"><fmt:message key="reg.error.email"/> </div>
            <div class="wrongPassword" id="login2">${duplicateLogin}</div>
            <div class="wrongPassword" id="less8"><fmt:message key="reg.error.less8"/> </div>
            <div class="wrongPassword" id="captchaNotif"><fmt:message key="reg.error.captcha"/> </div>
            <div class="wrongPassword" id="less30"><fmt:message key="reg.error.less30"/> </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/jspf/footer.jspf"%>
<script src="/js/reg.js"></script>
</body>
</html>
