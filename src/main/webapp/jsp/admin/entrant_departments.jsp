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
            <h2><fmt:message key="header.departments"/> </h2>
            <table>
                <tr>

                    <td><fmt:message key="entrants.departments.table.department"/></td>
                    <td><fmt:message key="entrants.departments.table.univer"/> </td>
                    <td><fmt:message key="entrants.info.table.city"/> </td>
                    <td><fmt:message key="entrants.departments.table.priority"/> </td>
                </tr>
                <c:forEach var="dep" items="${deps}">
                    <tr>
                        <td>${dep.departmentName}</td>
                        <td>${dep.univerName}</td>
                        <td>${dep.univerCity}</td>
                        <td>${dep.priority}</td>
                    </tr>
                </c:forEach>
            </table>
            <a href="/stud.ua/entrants"><input type="button" class="cancelBt" value="<fmt:message key="departments.cancel"/> "></a>


        </div>
    </div>

</div>

</body>
</html>
