<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

</head>
<body>
<header>
    <label id="logo">Stud.ua</label>
    <a id="main_page" href="/stud.ua"><fmt:message key="header.main"/> </a>
    <a href="/stud.ua/management" id="management"><fmt:message key="header.management"/></a>

</header>
<div class="content">
    <div class="departmentTable">
        <h2><fmt:message key="departments.headline"/> </h2>

        <form method="post"  action="/servlet?c=add&language=">
            <table>
                <tr>
                    <td>Id</td>
                    <td><fmt:message key="departments.name"/> </td>
                    <td><fmt:message key="departments.all"/> </td>
                    <td><fmt:message key="departments.free"/> </td>
                    <td><fmt:message key="departments.delete"/> </td>
                    <td><fmt:message key="departments.edit"/> </td>
                </tr>

                <c:forEach var="dep" items="${departments}">
                    <tr>

                        <td>${dep.id}</td>
                        <td>${dep.name}</td>
                        <td>${dep.allPlaces}</td>
                        <td>${dep.freePlaces}</td>
                        <td><a href="/servlet?c=del&id=${dep.id}&language=" ><input class="del" type="button" value="<fmt:message key="departments.delete"/> " ></a></td>

                        <td><a href="/stud.ua/edit&id=${dep.id}&name=${dep.name}&all=${dep.allPlaces}&free=${dep.freePlaces}"><input type="button" class=" edit" value="<fmt:message key="depаrtments.change.button"/> "></a>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td class="editional"><input type="text" name="id" class="addFields"></td>
                    <td class="editional"><input type="text" name="name" class="addFields"></td>
                    <td class="editional"><input type="text" name="all" class="addFields"></td>
                    <td class="editional"><input type="text" name="free" class="addFields"></td>
                </tr>


            </table>
            <input type="submit" class="bt submit" id="submitBt" value="<fmt:message key="departments.save"/> " name="submitButton" onclick="return validation() ">
            <button class="bt submit"  onclick="return showEditional()" id="addButton"><fmt:message key="departments.add"/> </button>
            <button class="bt cancel" id="cancelButton" onclick="return cancelFunc()"><fmt:message key="departments.cancel"/> </button>
            <div id="notif"><fmt:message key="departments.notif"/> </div>
            <div id="notif1">${duplicateId}</div>
            <div class="notif1"><fmt:message key="departments.notif2"/> </div>
            <div class="notif1"><fmt:message key="departments.notif3"/> </div>
            <div class="notif1"><fmt:message key="departments.notif4"/> </div>
            <div class="notif1"><fmt:message key="departments.notif5"/> </div>
            <div class="notif1"><fmt:message key="departments.notif6"/> </div>
            <div class="notif1"><fmt:message key="error.auth.invalid_symbols"/> </div>

        </form>

        <div class="buttonSort">
            <h2><fmt:message key="account.application.sort.headline"/> </h2>
            <a href="/stud.ua/departments"><input type="button" value="<fmt:message key="departments.sort.number"/> " class="bt"></a>
            <a href="/stud.ua/departments&s=name"><input type="button" value="<fmt:message key="account.application.sort.byname"/> " class="bt"></a>
            <a href="/stud.ua/departments&s=all"><input type="button" value="<fmt:message key="account.application.sort.all"/> " class="bt"></a>
            <a href="/stud.ua/departments&s=free"><input type="button" value="<fmt:message key="account.application.sort.free"/> " class="bt"></a>

            <a href="/stud.ua/departments&s=numbDesc"><input type="button" value="<fmt:message key="departments.sort.number.d"/> " class="bt"></a>
            <a href="/stud.ua/departments&s=nameDesc"><input type="button" value="<fmt:message key="account.application.sort.byname.d"/> " class="bt"></a>
            <a href="/stud.ua/departments&s=allDesc"><input type="button" value="<fmt:message key="account.application.sort.all.d"/> " class="bt"></a>
            <a href="/stud.ua/departments&s=freeDesc"><input type="button" value="<fmt:message key="account.application.sort.free.d"/> " class="bt"></a>
        </div>


    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
<script src="/js/department_management.js"></script>
</body>
</html>
