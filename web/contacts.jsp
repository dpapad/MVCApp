<%-- 
    Document   : contView
    Created on : Jul 7, 2015, 3:53:28 PM
    Author     : dpa
--%>

<%@include file="jsp_includes/taglibs.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My contact list demo</title>
        
        <%@include file="jsp_includes/head.jsp"%>
    </head>
    <body>
        <%@include file="header.jsp"%>
        <div>
            Contact id: <c:out value="${contactId}" />
            <br />
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
                                <a href="<c:url value="/contacts/${contact.contactId}"/>">View</a>
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