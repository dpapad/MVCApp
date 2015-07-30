<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    
        <ul>
            <li>Exception: <c:out value="${exception}" /></li>
            <li>Exception message: <c:out value="${errorMessage}" /></li>
            <li>Request URI: <c:out value="${requestUri}" /></li>
            <li>Servlet name: <c:out value="${servlet}" /></li>
        </ul>
    </body>
</html>
