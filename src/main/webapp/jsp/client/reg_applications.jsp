<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value=""/>
<fmt:setBundle basename="lan"/>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/footer.css">
    <link rel="stylesheet" href="/css/content.css">
    <link rel="stylesheet" href="/css/reg_applications.css">
</head>
<body>
<header>
    <label id="logo">Stud.ua</label>
    <a id="main_page" href="/stud.ua"><fmt:message key="header.main"/></a>
    <a id="acc" href="/stud.ua-account"><fmt:message key="header.account"/> </a>
</header>
<div class="content">
    <%@include file="/WEB-INF/jspf/saved_applications.jspf"%>
</div>
<div class="bottomBlock"></div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>
