<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="now" scope="page" class="java.util.Date"/>
${now.year+1900}