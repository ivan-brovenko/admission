<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value=""/>
<fmt:setBundle basename="lan"/>

<html>
<head>
    <title>Title</title>
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
            <h3><fmt:message key="information.atestat.zno.headline"/> </h3>
            <table>
                <c:forEach var="subject" items="${subjects}">
                    <tr>
                        <td> <fmt:message key="information.atestat.zno.subject"/> ${subject.name}</td>
                        <td> <fmt:message key="information.atestat.zno.mark"/> ${subject.mark}</td>
                    </tr>
                </c:forEach>
            </table>
            <a href="/stud.ua/entrants"><input type="button" class="cancelBt" value="<fmt:message key="departments.cancel"/> "></a>
        </div>
    </div>
</div>

</body>
</html>
