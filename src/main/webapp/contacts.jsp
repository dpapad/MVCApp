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
        <%@include file="jsp_includes/global_menu.jsp" %>
        <div>
            Contact id: <c:out value="${id}" />
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
                            <td><c:out value="${contact.fullName}" /></td>    
                            <td style="border: none;">                                
                                <a href="<c:url value="/contacts/${contact.id}"/>">View</a>
                                <a href="<c:url value="/modifyContact?contactId=${contact.id}" />">Modify</a>
                                <a href="<c:url value="/deleteContact?contactId=${contact.id}" />">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>