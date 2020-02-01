<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value=""/>
<fmt:setBundle basename="lan"/>

<html>
<head>
    <title><fmt:message key="departments.title"/> </title>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/footer.css">
    <link rel="stylesheet" href="/css/content.css">
    <link rel="stylesheet" href="/css/department_management.css">
    <link rel="stylesheet" href="/css/update_modal.css">

</head>
<body>

<header>
    <label id="logo">Stud.ua</label>
    <a id="main_page" href="/stud.ua"><fmt:message key="header.main"/> </a>
</header>
<div class="content">
    <div class="modal" id="myModal">

    <div class="update">
        <form method="post" action="/servlet?c=update&id=${oldId}&language=" onsubmit="return validateUpdate(this)">
            <h2><fmt:message key="update.departments.headline"/> ${oldId}</h2>
            <table>
                <tr>
                    <td>Id</td>
                    <td><fmt:message key="departments.name"/> </td>
                    <td><fmt:message key="departments.all"/> </td>
                    <td><fmt:message key="departments.free"/> </td>
                </tr>
                <tr>
                    <td>${oldId}</td>
                    <td><input type="text" name="upName" class="upField" value="${oldName}"></td>
                    <td><input type="text" name="upAll" class="upField" value="${oldAll}"></td>
                    <td><input type="text" name="upFree" class="upField" value="${oldFree}"></td>
                </tr>
            </table>
            <input   id="updateButton" type="submit" class="bt submit" value="<fmt:message key="update.departments.button.up"/> " >
            <a href="/servlet?c=depManage&language="><input class="bt cancel" value="<fmt:message key="departments.cancel"/> "></a>
        </form>

        <div id="notif"><fmt:message key="departments.notif"/> </div>
        <div class="notif" id="notif5"><fmt:message key="departments.notif3"/> </div>
        <div class="notif" id="notif6"><fmt:message key="departments.notif4"/></div>
        <div class="notif1"><fmt:message key="departments.notif5"/> </div>
        <div class="notif1"><fmt:message key="departments.notif6"/> </div>
        <div class="notif1"><fmt:message key="error.auth.invalid_symbols"/> </div>
        <div class="notif1"><fmt:message key="departments.notif2"/> </div>
        <div class="notif1" id="duplicateName">${duplicateName}</div>

    </div>
    </div>
</div>
<script src="/js/department_management.js"></script>
</body>
</html>
