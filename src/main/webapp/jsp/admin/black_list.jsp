<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value=""/>
<fmt:setBundle basename="lan"/>

<html>
<head>
    <title><fmt:message key="management.header.blacklist"/> </title>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/footer.css">
    <link rel="stylesheet" href="/css/content.css">
    <link rel="stylesheet" href="/css/black_list.css">
</head>
<body>
<header>
    <label id="logo">Stud.ua</label>
    <a id="main_page" href="/stud.ua"><fmt:message key="header.main"/> </a>
    <a href="/stud.ua/management" id="management"><fmt:message key="header.management"/></a>
</header>
<div class="content">
    <h2><fmt:message key="management.header.blacklist"/> </h2>
    <br>
    <table>
        <tr>
            <td>id</td>
            <td>email</td>
            <td><fmt:message key="management.black.list.button"/> </td>
        </tr>
        <c:forEach var="block" items="${blockList}">
            <tr>
                <td>${block.id}</td>
                <td>${block.email}</td>
                <td><a href="/stud.ua/unblock&id=${block.id}"><input class="bt" type="button" value="<fmt:message key="management.black.list.button"/> "></a></td>
            </tr>
        </c:forEach>
    </table>
    <div class="text">
        <p><fmt:message key="management.black.list.text"/> </p>
    </div>

</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>
