<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value=""/>
<fmt:setBundle basename="lan"/>

<fmt:message key="error.browser.title" var="title" scope="page"/>
<fmt:message key="error.browser.message" var="message" scope="page"/>
<fmt:message key="error.back" var="back" scope="page"/>

<html>
<head>
    <title>${title}</title>
    <link rel="stylesheet" href="/css/error.css" />
</head>
<body>
<h1>${title}</h1>
<p>${message}</p>
<a href="${pageContext.request.contextPath}/stud.ua">${back}</a>
</body>
</html>
