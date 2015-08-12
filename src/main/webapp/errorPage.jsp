<%-- 
    Document   : errorPage
    Created on : Jul 31, 2015, 2:31:00 PM
    Author     : dev-dp
--%>
<%@include file="jsp_includes/taglibs.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error/Exception page</title>
        <%@include file="jsp_includes/head.jsp" %>
    </head>
    <body>
        <%@include file="jsp_includes/global_menu.jsp" %>
        <h1>${header}</h1>
        <strong>Status Code:  ${statusCode}</strong>
        <strong>Requested URI:  ${requestUri}</strong>

        <ul>
            <li>Servlet Name:  ${servletName}</li>
            <li>Exception Name:  ${exceptionName}</li>
            <li>Exception Message:  ${exceptionMessage}</li>
        </ul>
    </body>
</html>
