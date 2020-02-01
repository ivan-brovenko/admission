<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value=""/>
<fmt:setBundle basename="lan"/>

<html>
<head>
    <title><fmt:message key="entrants.title"/> </title>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/footer.css">
    <link rel="stylesheet" href="/css/content.css">
    <link rel="stylesheet" href="/css/entrants.css">
</head>
<body>
<header>
    <label id="logo">Stud.ua</label>
    <a id="main_page" href="/stud.ua"><fmt:message key="header.main"/> </a>
    <a href="/stud.ua/management" id="management"><fmt:message key="header.management"/></a>
</header>
<div class="content">
    <h2><fmt:message key="entrants.headline"/> </h2>
    <div class="entrants">
        <form>
            <table>
                <tr>
                    <td>Id</td>
                    <td>Email</td>
                    <td><fmt:message key="entrants.table.mark"/> </td>
                    <td><fmt:message key="entrants.table.info"/> </td>
                    <%--<td><fmt:message key="entrants.table.atestat"/> </td>--%>
                    <td><fmt:message key="entrants.table.departments"/> </td>
                    <td><fmt:message key="entrnats.marks"/></td>
                    <td><fmt:message key="entrants.table.applications"/> </td>
                    <td><fmt:message key="entrants.table.block"/> </td>
                    <td><fmt:message key="entrants.table.add"/> </td>
                </tr>
                <c:forEach var="ad" items="${admiss}">
                    <tr>
                        <td>${ad.id}</td>
                        <td>${ad.email}</td>
                        <td>${ad.finalMark}</td>
                        <td><a href="/stud.ua/info&id=${ad.id}"><fmt:message key="entrants.table.info"/> </a></td>
                        <%--<td><a href="/servlet?c=entAtestat&id=${ad.id}&language="><fmt:message key="entrants.table.atestat"/> </a></td>--%>
                        <td><a href="/stud.ua/entrant-departments&id=${ad.id}"><fmt:message key="entrants.table.departments"/> </a></td>
                        <td><a href="/stud.ua/entrant-zno&id=${ad.id}"><fmt:message key="entrnats.marks"/> </a></td>
                        <td>${ad.applications}</td>
                        <td><a href="/servlet?c=block&id=${ad.id}&language="><input type="button" class="bt block" value="<fmt:message key="entrants.table.block"/> "></a></td>
                        <td><a href="/servlet?c=add2adm&id=${ad.id}&language="><input type="button" class="bt add" value="<fmt:message key="entrants.table.add"/> "></a></td>
                    </tr>
                </c:forEach>
            </table>
        </form>
        <div class="notif">${noUniver}</div>
        <div class="notif">${noInternet}</div>
    </div>
    <div class="rules"><fmt:message key="entrants.text"/> </div><div class="bottomBlock"></div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>
