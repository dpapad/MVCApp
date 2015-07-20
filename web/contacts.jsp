<%-- 
    Document   : contView
    Created on : Jul 7, 2015, 3:53:28 PM
    Author     : dpa
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="style.css" rel="stylesheet" type="text/css"/>
        <title>My contact list demo</title>
    </head>
    <body>
        <%@include file="header.jsp"%>
        <div>
            <table>
                <thead>
                    <tr>
                        <th>Full Name</th>
                    </tr>
                </thead>
                <tbody>                    
                    <c:forEach var="contact" items="${contactsList}">
                        <tr>
                            <td><c:out value="${contact.fullname}" /></td>    
                            <td style="border: none;">                                
                                <a href="<c:url value="/viewContact?contactId=${contact.contactId}"/>">View</a>
                                <a href="<c:url value="/modifyContact?contactId=${contact.contactId}" />">Modify</a>
                                <a href="<c:url value="/deleteContact?contactId=${contact.contactId}" />">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>