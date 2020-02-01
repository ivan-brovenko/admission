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
    <link rel="stylesheet" href="/css/entrant_departments.css">
</head>
<body>
<header>
    <label id="logo">Stud.ua</label>
    <a id="main_page" href="/stud.ua"><fmt:message key="header.main"/> </a>
</header>
<div class="content">
    <div class="modal" id="myModal">
        <div class="inner">
            <h2><fmt:message key="entrants.departments.info.headline"/> </h2>

            <div class="number">
                <fmt:message key="information.atestat.number"/> ${atestat.id}<br>
                <fmt:message key="information.atestat.averagemark"/> ${atestat.avgMark}
            </div>
            <div class="scanPic">
                <img src="/archive/${atestat.image}" alt="certificate scan" class="pic">
            </div>

            <table>
                <tr>
                    <td><fmt:message key="entrants.info.table.fname"/> </td>
                    <td><fmt:message key="entrants.info.table.lname"/> </td>
                    <td><fmt:message key="entrants.info.table.patronymic"/> </td>
                    <td><fmt:message key="entrants.info.table.city"/> </td>
                    <td><fmt:message key="entrants.info.table.region"/> </td>
                    <td><fmt:message key="entrants.info.table.school"/> </td>
                </tr>
                <tr>
                    <td>${ent.firstName}</td>
                    <td>${ent.lastName}</td>
                    <td>${ent.patronymic}</td>
                    <td>${ent.city}</td>
                    <td>${ent.region}</td>
                    <td>${ent.school}</td>
                </tr>



            </table>
            <a href="/stud.ua/entrants"><input type="button" class="cancelBt" value="<fmt:message key="departments.cancel"/> "></a>


        </div>
    </div>

</div>

</body>
</html>
