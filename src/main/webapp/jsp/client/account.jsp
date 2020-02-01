<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="auth" uri="http://light.net/taglib/auth" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="upload" uri="http://brovenko.net/taglib/upload" %>

<fmt:setLocale value=""/>
<fmt:setBundle basename="lan"/>

<c:set var="user" scope="page" value="${auth:user(pageContext.request)}"/>
<c:set var="state" scope="page" value="${upload:upload(pageContext.request)}"/>

<html>
<head>
    <title><fmt:message key="account.title"/> </title>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/footer.css">
    <link rel="stylesheet" href="/css/content.css">
    <link rel="stylesheet" href="/css/account.css">

</head>
<body>

<header>
    <label id="logo">Stud.ua</label>
    <a id="main_page" href="/stud.ua"><fmt:message key="header.main"/> </a>
    <c:if test="${user.role eq 'reg_entrant'}">
        <a href="/servlet?c=watchInfo&language=" id="atestatInformation"><fmt:message key="account.reg.header.information"/> </a>
        <a href="/servlet?c=watchZno&language=" id="znoSavedApplications"><fmt:message key="account.reg.header.application"/> </a>
    </c:if>
</header>

<div class="content">
    <h1><fmt:message key="account.headline"/> </h1>
    <c:if test="${user.role eq 'entrant'}">
    <%@ include file="/WEB-INF/jspf/atestat.jspf"%>
        <%@ include file="/WEB-INF/jspf/atestat_scan.jspf"%>
    <%@ include file="/WEB-INF/jspf/zno_results.jspf"%>
    </c:if>
    <c:if test="${user.role eq 'reg_entrant'}">
        <%@include file="/WEB-INF/jspf/inform_zno_atestat.jspf"%>
        <%--<%@ include file="/WEB-INF/jspf/saved_applications.jspf"%>--%>
    </c:if>
    <c:if test="${user.role eq 'reg_entrant' or user.role eq 'entrant'}">
        <%@ include file="/WEB-INF/jspf/aplication.jspf"%>
    </c:if>
    <c:if test="${user.role eq 'admis'}">
        <%@ include file="/WEB-INF/jspf/admis_notiffication.jspf"%>
    </c:if>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>
<script src="/js/account.js"></script>
</body>
</html>
