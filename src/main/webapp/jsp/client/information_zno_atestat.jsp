<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value=""/>
<fmt:setBundle basename="lan"/>

<html>
<head>
    <title><fmt:message key="entrants.departments.info.headline"/> </title>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/footer.css">
    <link rel="stylesheet" href="/css/content.css">
    <link rel="stylesheet" href="/css/reg_applications.css">
    <link rel="stylesheet" href="/css/information_zno_atestat.css">
</head>
<body>
<header>
    <label id="logo">Stud.ua</label>
    <a id="main_page" href="/stud.ua"><fmt:message key="header.main"/></a>
    <a id="acc" href="/stud.ua-account"><fmt:message key="header.account"/> </a>
</header>
<div class="content">
    <div class="atestat">
        <h2><fmt:message key="account.reg.information.headline"/> </h2>
        <div class="addedApl">
            <div class="number">
                <fmt:message key="information.atestat.number"/> ${atestat.id}<br>
                <fmt:message key="information.atestat.averagemark"/> ${atestat.avgMark}
            </div>

            <br>
            <br>
            <h3><fmt:message key="information.atestat.zno.headline"/> </h3>
            <table>
                <c:forEach var="subject" items="${subjects}">
                    <tr>
                        <td> <fmt:message key="information.atestat.zno.subject"/> ${subject.name}</td>
                        <td> <fmt:message key="information.atestat.zno.mark"/> ${subject.mark}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>


    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>
