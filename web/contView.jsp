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
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="header.jsp"%>
        <div>
            <table>
                <thead>
                    <tr>
                        <th>Contact ID</th>
                        
                        <th>Full Name</th>
                        <%--
                        <th>Nickname</th>
                        <th>Notes</th>
                        --%>
                    </tr>
                </thead>
                <tbody>                    
                    <c:forEach var="contact" items="${contactsList}">
                        <tr>
                            <td><c:out value="${contact.contid}" /></td>
                            <td><c:out value="${contact.fullname}" /></td>    
                        
                            
                            <td style="border: none;">
                                <div>
                                    <form method="post" action="ContactDetailsServlet">
                                        <input type="hidden" id="detailsId" name="detailsId" value="${contact.contid}"> 
                                        <input type="submit" value="Details"> 
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>